package com.thridrecharge.service.cardrecharge;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.ordermanager.RechargeDao;

@Controller
public class CardRechargeManager {
	private static String DLLPATH = null;
	private String LIBPATH = null;
	private int libIndex = 0;
	public int CODELENTH = 8;
	public boolean test = false;
	public byte[] OUTINIT = null;

	private DefaultHttpClient httpClient = new DefaultHttpClient();
	private String cookie;
	private String codeSessionID;
	private int downloadRetry = 0;

	private Log log = LogFactory.getLog("recharge");
	
	
	@Autowired
	private RechargeDao rechargeDao;
	
	public CardRechargeManager() {
		init("10010upay.lib");
		
		HttpParams params=new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 10);
		ThreadSafeClientConnManager tsccm=new ThreadSafeClientConnManager();
		tsccm.setMaxTotal(50);
		this.httpClient=new DefaultHttpClient(tsccm,params);
		
		httpClient.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8);
		httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
		httpClient.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
		httpClient.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,HTTP.UTF_8);
		HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
		
	}
	
	public void init(String libName) {
		DLLPATH = IOTool.getRootPath("dll/Sunday");
		System.out.println("DLLPATH:" + DLLPATH);
		LIBPATH = IOTool.getRootPath("dll/" + libName);
		libIndex = CodeRec.INSTANCE.LoadLibFromFile(LIBPATH, "123");
	}

	public void init(String libName, String dllPath) {
		DLLPATH = IOTool.getRootPath("dll/" + dllPath);
		System.out.println("DLLPATH:" + DLLPATH);
		LIBPATH = IOTool.getRootPath("dll/" + libName);
		libIndex = CodeRec.INSTANCE.LoadLibFromFile(LIBPATH, "123");
	}

	public interface CodeRec extends StdCallLibrary {
		CodeRec INSTANCE = (CodeRec) Native.loadLibrary(DLLPATH, CodeRec.class);

		int LoadLibFromFile(String path, String pwd);

		int LoadLibFromFile(String pwd);

		boolean GetCodeFromBuffer(int index, byte[] img, int len, byte[] code);

		boolean GetCodeFromFile(int index, String file, byte[] code);
	}

	private String executeHttpMethod(HttpUriRequest httpMethod)throws Exception {
		HttpEntity entity = null;
		String resultStr = "";
		try {
			HttpResponse response = httpClient.execute(httpMethod);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			entity = response.getEntity();
			resultStr = EntityUtils.toString(response.getEntity()); 
			
			StringBuffer sb = new StringBuffer();
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
	        for(Cookie cookie: cookies)
	            sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
	        
	        this.cookie = sb.toString();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}  finally {  
	        if (entity != null) {  
	        	try {
	        		entity.consumeContent();// 释放连接  
				} catch (Exception e2) {
					log.error(e2);
					e2.printStackTrace();
				}
	             
	        }  
	  
	    } 
		
		return resultStr;
		
	}
	public String getSecstate() {
		
		try {
			log.info("########(Step 2)开始获取Secstate");
			HttpGet secstateGet = new HttpGet("https://upay.10010.com/npfweb/npfcellweb/phone_recharge_fill.htm?orignalresource=2&&menuId=000200010003");
			String result = executeHttpMethod(secstateGet);
			String secstatePatternStr = "<input\\s+id=\"secstate\"\\s+name=\"secstate.state\"\\s+type=\"hidden\"\\s+value=(\"[^\"]*\")/>";
			Pattern secstatePattern = Pattern.compile(secstatePatternStr);
			Matcher secstateMatcher = secstatePattern.matcher(result);
			if (secstateMatcher.find()) {
				String secstateStr = secstateMatcher.group(0);
				secstateStr = secstateStr.substring(secstateStr.indexOf("value=\"")+7,secstateStr.lastIndexOf("\""));
				log.info("########(Step 2)获取Secstate成功："+secstateStr);
				return secstateStr;
			}
			log.info("########(Step 2)获取Secstate失败");
			return null;
		} catch (Exception e) {
			log.info("########(Step 2)获取Secstate失败");
			log.error(e);
			return null;
		}
		
	}
	
	//解析验证码
	public synchronized String getCode(byte[] imgbs,String filePath) {
		log.info("########(Step 4)开始解析验证码");
		long begin = System.currentTimeMillis();
		byte[] code = new byte[CODELENTH];
		String rtnCode = null;
		boolean result = CodeRec.INSTANCE.GetCodeFromBuffer(libIndex, imgbs,
				imgbs.length, code);
		if (result) {
			long end = System.currentTimeMillis();
			try {
				rtnCode = new String(code, "GBK");
				File tmpFile = new File(filePath);
				tmpFile.delete();
			} catch (UnsupportedEncodingException e) {
				log.error(e);
				e.printStackTrace();
				return null;
			}
			if (rtnCode == null) {
				return null;
			}
			rtnCode = rtnCode.trim();
			log.info("########(Step 4)开始解析验证码完成。识别时间:" + (end - begin) + "ms 识别结果:" + rtnCode);
			return rtnCode.trim();
		}else {
			log.info("########(Step 4)解析验证码失败");
			return null;
		}
		
	}

	public boolean initConnection() {
		try {
			HttpGet get = new HttpGet("http://www.10010.com");
			String result = executeHttpMethod(get);
			if (!"".equals(result)) {
				log.info("########(Step 1)测试连接：连接成功");
				return true;
			} else {
				log.info("########(Step 1)测试连接：连接失败");
				return false;
			}
		} catch (Exception e) {
			log.info("########(Step 1)测试连接：连接失败");
			log.error(e);
			return false;
		}
		
			
		
	}
	//获取
	//下载验证码
	public String downloadImage() {
		
		log.info("########(Step 3)开始下载验证码");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		String time = format.format(c.getTime());
		HttpGet getMethod = new HttpGet(
				"https://upay.10010.com/npfweb/NpfCellWeb/NpfverifyCode/getVerifyCode.action?timestamp="
						+ time);
		try {
			// 执行getMethod
			HttpResponse response = httpClient.execute(getMethod);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ response.getStatusLine());
			}
			
			// 读取内容
			String tmpFilePath = this.getClass().getResource("/").toURI().getPath().replaceAll("classes/", "")+"tmp";
			File tmpDir = new File(tmpFilePath);
			if (!tmpDir.exists()) {
				tmpDir.mkdirs();
			}
			String picName = tmpFilePath + "\\" + time + ".gif";
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			OutputStream outStream = new FileOutputStream(picName);
			IOUtils.copy(inputStream, outStream);
			outStream.close();
			log.info("########(Step 3)验证码下载成功："+time + ".gif");
			return picName;
		} catch (Exception e) {
			log.info("########(Step 3)验证码下载失败");
			log.error(e);
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return null;
	}

	//验证验证码
	public boolean checkVerifyCode(String checkCode) {
		
		try {
			log.info("########(Step 5)开始对验证码进行验证");
			HttpPost checkVerifyCodePost = new HttpPost("https://upay.10010.com/npfweb/NpfCellWeb/NpfverifyCode/CheckVerifyCodePre");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
			nvps.add(new BasicNameValuePair("callback", "getResult"));  
			nvps.add(new BasicNameValuePair("verifyCode", checkCode)); 
			nvps.add(new BasicNameValuePair("verifyCodeCookie", codeSessionID));
			
			checkVerifyCodePost.setEntity(new UrlEncodedFormEntity(nvps));
			
			String response = executeHttpMethod(checkVerifyCodePost);
			
			if (!"getResult( '')".equals(response)) {
				log.info("########(Step 5)验证码进行验证不匹配");
				return false;
			}
			log.info("########(Step 5)验证码进行验证成功");
			return true;
		} catch (Exception e) {
			log.info("########(Step 5)验证码进行验证失败");
			log.error(e);
			return false;
		}
		
	}
	
	/**
	 * GET方法中，特殊字符转换
	 * 1. +  URL 中+号表示空格 %2B  
	 * 2. 空格 URL中的空格可以用+号或者编码 %20  
	 * 3. /  分隔目录和子目录 %2F   
	 * 4. ?  分隔实际的 URL 和参数 %3F   
	 * 5. % 指定特殊字符 %25   
	 * 6. # 表示书签 %23   
	 * 7. & URL 中指定的参数间的分隔符 %26   
	 * 8. = URL 中指定参数的值 %3D
	 * @param str
	 * @return
	 */
	public String transferString(String str) {
		str = str.replaceAll("\\+", "%2B");
		str = str.replaceAll("\\ ", "%20");
		str = str.replaceAll("\\/", "%2F");
		str = str.replaceAll("\\?", "%3F");
		str = str.replaceAll("\\%", "%25");
		str = str.replaceAll("\\#", "%23");
		str = str.replaceAll("\\&", "%26");
		str = str.replaceAll("\\=", "%3D");
		str = str.replaceAll("\\^", "%5E");
		str = str.replaceAll("\\\\", "%5C");
		return str;
	}
	
	//验证充值信息
	public String rechargeCheck(String secstate,String pCardPwd, String pTel, String checkCode,String cityCode){
		
		try {
			log.info("########(Step 6)充值前验证-》开始reChargeCheck");
			String condition = "secstate.state="+transferString(secstate)+"&commonBean.userChooseMode=0&commonBean.phoneNo="+pTel+"&commonBean.provinceCode=034"
					+ "&commonBean.cityCode="+cityCode+"&rechargeMode=%E7%94%A8%E5%85%85%E5%80%BC%E5%8D%A1&cardBean.cardValueCode=04&offerPriceStrHidden=98.50&cardBean.cardValue=100"
							+ "&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=150&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1"
							+ "&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&MaxThreshold05=1&MinThreshold05=1&actionPayFeeInfo.actionConfigId="
							+ "&commonBean.payAmount=98.50&invoiceBean.need_invoice=1&invoiceBean.invoice_type=&invoiceBean.is_mailing=1&saveflag=false"
							+ "&postBean.receiver_name=&postBean.receiver_phone=&postBean.post_code=&postBean.receiver_addr=&invoiceBean.invoice_head="
							+ "&invoiceBean.card_type=0&invoiceBean.id_cardno=&rechargeBean.cardPwd="+pCardPwd+"&verifyCode="+checkCode+"&commonBean.bussineType=";
			
			HttpGet get = new HttpGet("https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeCheck?"+condition);

			String resultStr = executeHttpMethod(get);
			if (resultStr.contains("\"out\":\"success\"")) {
				log.info("########(Step 6)充值前验证-》reChargeCheck成功");
				return resultStr.substring(resultStr.indexOf("{\"secstate\":\"")+13,resultStr.indexOf("\",\"out\":\"success\""));
			}
			log.info("########(Step 6)充值前验证-》reChargeCheck失败，有可能充值卡以北使用。");
			return null;
		} catch (Exception e) {
			log.info("########(Step 6)充值前验证-》reChargeCheck失败");
			log.error(e);
			return null;
		}
		
		
	}
	
	public boolean rechargeApply(String secstate,String pCardPwd, String pTel, String checkCode,String cityCode){
		try {
			log.info("########(Step 7)充值前确认-》开始rechargeApply");
			HttpPost post = new HttpPost("https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeApplay");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			secstate = secstate.replaceAll("\\\\n", "\n");
			nvps.add(new BasicNameValuePair("secstate.state",secstate));
			nvps.add(new BasicNameValuePair("commonBean.userChooseMode","0"));
			nvps.add(new BasicNameValuePair("commonBean.phoneNo",pTel));
			nvps.add(new BasicNameValuePair("commonBean.provinceCode","034"));
			nvps.add(new BasicNameValuePair("commonBean.cityCode",cityCode));
			nvps.add(new BasicNameValuePair("rechargeMode","%E7%94%A8%E5%85%85%E5%80%BC%E5%8D%A1"));
			nvps.add(new BasicNameValuePair("cardBean.cardValueCode","04"));
			nvps.add(new BasicNameValuePair("offerPriceStrHidden","98.50"));
			nvps.add(new BasicNameValuePair("cardBean.cardValue","100"));
			nvps.add(new BasicNameValuePair("cardBean.minCardNum","1"));
			nvps.add(new BasicNameValuePair("cardBean.maxCardNum","3"));
			nvps.add(new BasicNameValuePair("MaxThreshold01","150"));
			nvps.add(new BasicNameValuePair("MinThreshold01","1"));
			nvps.add(new BasicNameValuePair("MaxThreshold02","10"));
			nvps.add(new BasicNameValuePair("MinThreshold02","1"));
			nvps.add(new BasicNameValuePair("MaxThreshold03","6"));
			nvps.add(new BasicNameValuePair("MinThreshold03","1"));
			nvps.add(new BasicNameValuePair("MaxThreshold04","3"));
			nvps.add(new BasicNameValuePair("MinThreshold04","1"));
			nvps.add(new BasicNameValuePair("MaxThreshold05","1"));
			nvps.add(new BasicNameValuePair("MinThreshold05","1"));
			nvps.add(new BasicNameValuePair("actionPayFeeInfo.actionConfigId",""));
			nvps.add(new BasicNameValuePair("commonBean.payAmount","98.50"));
			nvps.add(new BasicNameValuePair("invoiceBean.need_invoice","1"));
			nvps.add(new BasicNameValuePair("invoiceBean.invoice_type",""));
			nvps.add(new BasicNameValuePair("invoiceBean.is_mailing","1"));
			nvps.add(new BasicNameValuePair("saveflag","false"));
			nvps.add(new BasicNameValuePair("postBean.receiver_name",""));
			nvps.add(new BasicNameValuePair("postBean.receiver_phone",""));
			nvps.add(new BasicNameValuePair("postBean.post_code",""));
			nvps.add(new BasicNameValuePair("postBean.receiver_addr",""));
			nvps.add(new BasicNameValuePair("invoiceBean.invoice_head",""));
			nvps.add(new BasicNameValuePair("invoiceBean.card_type","0"));
			nvps.add(new BasicNameValuePair("invoiceBean.id_cardno",""));
			nvps.add(new BasicNameValuePair("rechargeBean.cardPwd",pCardPwd));
			nvps.add(new BasicNameValuePair("verifyCode",checkCode));
			nvps.add(new BasicNameValuePair("commonBean.bussineType",""));
			
			post.setEntity(new UrlEncodedFormEntity(nvps));
			
			String resultStr = executeHttpMethod(post);
			if (resultStr.contains(secstate)) {
				log.info("########(Step 7)充值前确认-》rechargeApply成功");
				return true;
			} else {
				log.info("########(Step 7)充值前确认-》rechargeApply失败");
				return false;
			}
		} catch (Exception e) {
			log.info("########(Step 7)充值前确认-》rechargeApply失败");
			log.error(e);
			return false;
		}
		
			
			
	}
	
	//充值
	public boolean comfirmRecharge(String secstate) {
		
		try {
			log.info("########(Step 8)开始充值");
			HttpPost post = new HttpPost("https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeConfirm");
			post.addHeader("Cookie", cookie);
			post.addHeader("Cookie","WT_FPC=id=23fa297f9774ba2b21a1397347759553:lv=1398155419110:ss=1398155205341; _n3fa_cid=6b78106b99b746bf89ed3e18ba8252d3; _n3fa_ext=ft=1397347760; _n3fa_lvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1398087149,1398098269,1398150335,1398154703; Hm_lvt_9208c8c641bfb0560ce7884c36938d9d=1398063658,1398083443,1398150336,1398155206; __utma=231252639.672497684.1397347760.1398150335.1398155206.12; __utmz=231252639.1398053450.4.2.utmcsr=10010.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmv=231252639.Beijing; piw=%7B%22login_name%22%3A%22186****5099%22%2C%22nickName%22%3A%22186****5099%22%2C%22rme%22%3A%7B%22ac%22%3A%22%22%2C%22at%22%3A%22%22%2C%22pt%22%3A%2201%22%2C%22u%22%3A%2218652005099%22%7D%2C%22verifyState%22%3A%22%22%7D; guide=true; mallcity=11|110; _n3fa_lpvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1398155419; __utmb=231252639.6.10.1398155206; __utmc=231252639; Hm_lpvt_9208c8c641bfb0560ce7884c36938d9d=1398155420; codeSessionID="+codeSessionID);
			
			secstate = secstate.replaceAll("\\\\n", "\n");
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
			nvps.add(new BasicNameValuePair("secstate.state",secstate));
			post.setEntity(new UrlEncodedFormEntity(nvps));

					
			String resultStr = this.executeHttpMethod(post);
			
			if (resultStr.contains("您已成功交费")) {
				log.info("########(Step 8)充值成功");
				return true;
			} else {
				log.info("########(Step 8)充值失败");
				log.info("########(Step 8)result:"+resultStr);
				return false;
			}
		} catch (Exception e) {
			log.info("########(Step 8)充值失败");
			log.error(e);
			return false;
		}
		
	}
	
	private boolean checkResult(String secstate,String pCardPwd, String pTel, String checkCode,String cityCode) {
		
		try {
			log.info("########(Step 9)充值完成后，验证充值结果-》开始checkResult");
			String condition = "secstate.state="+transferString(secstate)+"&commonBean.userChooseMode=0&commonBean.phoneNo="+pTel+"&commonBean.provinceCode=034"
					+ "&commonBean.cityCode="+cityCode+"&rechargeMode=%E7%94%A8%E5%85%85%E5%80%BC%E5%8D%A1&cardBean.cardValueCode=04&offerPriceStrHidden=98.50&cardBean.cardValue=100"
							+ "&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=150&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1"
							+ "&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&MaxThreshold05=1&MinThreshold05=1&actionPayFeeInfo.actionConfigId="
							+ "&commonBean.payAmount=98.50&invoiceBean.need_invoice=1&invoiceBean.invoice_type=&invoiceBean.is_mailing=1&saveflag=false"
							+ "&postBean.receiver_name=&postBean.receiver_phone=&postBean.post_code=&postBean.receiver_addr=&invoiceBean.invoice_head="
							+ "&invoiceBean.card_type=0&invoiceBean.id_cardno=&rechargeBean.cardPwd="+pCardPwd+"&commonBean.bussineType=";
			
			HttpGet get = new HttpGet("https://upay.10010.com/npfweb/NpfCellWeb/reCharge/reChargeCheck?"+condition);

			String resultStr = executeHttpMethod(get);
			if (resultStr.contains("您输入的充值卡已充值")) {
				log.info("########(Step 9)充值完成后，验证充值结果-》checkResult成功");
				return true;
			}
			log.info("########(Step 9)充值完成后，验证充值结果-》checkResult失败，充值不成功。");
			return false;
		} catch (Exception e) {
			log.info("########(Step 9)充值完成后，验证充值结果-》checkResult失败");
			log.error(e);
			return false;
		}
	}
	private void rechargeByCardOnce(String mobile,String cardNo,String cardPwd,String cityCode) throws RechargeException {
		//测试连接
		if(!initConnection()) {
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		//获取secstate
		String secstate = getSecstate();
		if(null == secstate) {
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		//下载验证码
		String imagePath = downloadImage();
		if (null == imagePath) {
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		//解析验证码
		byte[] bs = IOTool.getContent(imagePath);
		String checkCode = getCode(bs,imagePath);
		
		//对验证码进行验证
		boolean isVerifyCode = checkVerifyCode(checkCode);
		if(!isVerifyCode) {
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		//获取secstateNew
		String secstateNew = rechargeCheck(secstate,cardPwd, mobile,checkCode,cityCode);
		if (null == secstateNew) {
			throw new RechargeException(ErrorCode.RECHARGECARD);
		}
		
		boolean isApply = rechargeApply(secstateNew,cardPwd, mobile,checkCode,cityCode);
		if (!isApply) {
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		boolean success = comfirmRecharge(secstateNew);
		
//		if (!success) {
//			success = checkResult(secstate,cardPwd, mobile,checkCode,cityCode);
//		}
		
		if (success) {
			log.info("########充值成功");
		}else {
			log.info("########充值失败");
			throw new RechargeException(ErrorCode.TIMEOUT);
		}
	}
	public void rechargeByCard(int agentId,String mobile,String cardNo,String cardPwd,String cityCode,long money,OrderHis orderHis) throws RechargeException {
		
		log.info("################进入卡密充值###############");
		log.info("########代  理  商：【"+agentId+"】");
		log.info("########手  机  号：【"+mobile+"】");
		log.info("########卡      号：【"+cardNo+"】");
		log.info("########卡      密：【"+cardPwd+"】");
		log.info("######## 城市编码 ：【"+cityCode+"】");
		log.info("########金      额：【"+money+"】");
		log.info("########################################");
		
		//数据库扣款,如果失败会直接抛异常出去
		//rechargeDao.deducting(agentId, money,orderHis);
				
		//重试3次
		int retryCount = 3;
		while(retryCount > 0) {
			try {
				rechargeByCardOnce(mobile,cardNo,cardPwd,cityCode);
				return;
			} catch (Exception e) {
				if (e instanceof RechargeException) {
					RechargeException re = (RechargeException)e;
					if (re.getErrorCode() == ErrorCode.RECHARGECARD.getErrorCode()) {
						log.info("########卡密可能已被使用");
						throw re;
					}
					if (re.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
						log.info("########充值返回失败，可能已经充值成功，请人工核实！");
						throw re;
					}
				}
				retryCount--;
			}
		}
		
		log.info("########卡密充值重试3次不成功");
		throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
	}
	
	
	
	public static void main(String[] args) throws IOException, HttpException {
		CardRechargeManager recCodeAuto = new CardRechargeManager();
		recCodeAuto.rechargeByCardOnce("13218888000","","3403628688084615485","340");
		
	}

}
