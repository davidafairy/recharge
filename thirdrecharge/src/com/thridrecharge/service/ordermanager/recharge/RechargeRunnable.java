package com.thridrecharge.service.ordermanager.recharge;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.chainsaw.Main;

import com.ofpay.util.encoding.AES;
import com.thridrecharge.service.RechargeContext;
import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.RechargeCard;
import com.thridrecharge.service.enums.DealResult;
import com.thridrecharge.service.enums.RechargeCardStatus;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.memory.AreaCodeMemory;
import com.thridrecharge.service.ordermanager.OrderDao;
import com.thridrecharge.service.utils.MD5Utils;

public class RechargeRunnable implements Runnable {

	private Log log = LogFactory.getLog("recharge");
	
	private Order order;
	
	private RechargeDao rechargeDao;
//	
	private OrderDao orderDao;
	
	public RechargeRunnable(Order order) {
		this.order = order;
		
		rechargeDao = RechargeContext.getRechargeContext().getBean("rechargeDao");
//		orderDao = RechargeContext.getRechargeContext().getBean("orderDao");
	}
	
	@Override
	public void run() {
		log.info("=======================================================================================================================================");
		log.info("=========================================开始卡密充值(手机号："+order.getMobile()+")===========================================================");
		log.info("=======================================================================================================================================");
		log.info("********代理商  ：【"+order.getAgentId()+"】");
		log.info("********订单号  ：【"+order.getFlowNo()+"】");
		log.info("********手机号  ：【"+order.getMobile()+"】");
		log.info("********充值金额：【"+order.getMoney()+"】");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		log.info("********订单时间：【"+sdf.format(order.getCreateTime())+"】");
		log.info("********卡密充值开始时间：【"+sdf.format(Calendar.getInstance().getTime())+"】");
		
		
		
//		String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//		AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
//		log.info("********充值策略：【"+RechargeStrategy.getStrategyDesc(areaCode.getRechargeStrategy())+"】");
		
		//充值历史
//		OrderHis orderHis = new OrderHis();
//		orderHis.setAgentId(order.getAgentId());
//		orderHis.setFlowNo(order.getFlowNo());
//		orderHis.setMobile(order.getMobile());
//		orderHis.setMoney(order.getMoney());
//		orderHis.setCreateTime(order.getCreateTime());
//		orderHis.setOrderId(order.getId());
//		orderHis.setRechargeTime(order.getRechargeTime());
//		orderHis.setResult(order.getDealResult());
//		if (order.getDealResult() == 1) {
//			orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//		} else {
//			orderHis.setErrorCode(ErrorCode.RECHARGEFAIL.getErrorCode());
//			
//		}
		
//		// 检查是否重单
//		OrderHis repeatOrder = orderDao.findOrderHisByFlowNo(order.getFlowNo());
//		if (null != repeatOrder) {
//			log.info("********重复订单-->跟"+repeatOrder.getId()+"重复");
//			orderHis.setRechargeTime(Calendar.getInstance().getTime());
//			orderHis.setResult(2); //充值失败
//			orderHis.setErrorCode(ErrorCode.REPEAT.getErrorCode());
//		} else {
//			switch(areaCode.getRechargeStrategy()) {
//			case 1:
//				try {
//					log.info("********充值方式：【Socket接口充值】");
//					////////////////////////////////////////////
//					socketRecharge(order,orderHis);
//					/////////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //充值成功
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch (RechargeException re) {
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(2); //充值失败
//					orderHis.setErrorCode(re.getErrorCode());
//				}
//				break;
//			case 2:
//				try {
//					log.info("********充值方式：【Socket接口充值】");
//					//////////////////////////////////////
//					socketRecharge(order,orderHis);
//					////////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //充值成功
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch(RechargeException re) {
//					log.info("********Socket接口充值失败");
//					try {
//						log.info("********充值方式：【卡密充值】");
//						////////////////////////////////////
//						cardRecharge(order,orderHis);
//						////////////////////////////////////
//						orderHis.setRechargeTime(Calendar.getInstance().getTime());
//						orderHis.setResult(1); //充值成功
//						orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//					} catch (RechargeException e) {
//						orderHis.setRechargeTime(Calendar.getInstance().getTime());
//						orderHis.setResult(2); //充值失败
//						orderHis.setErrorCode(e.getErrorCode());
//					}
//				}
//				break;
//			case 3:
//				log.info("********充值方式：【卡密充值】");
//				try {
//					//////////////////////////////////////////
//					cardRecharge(order,orderHis);
//					//////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //充值成功
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch (RechargeException e) {
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(2); //充值失败
//					orderHis.setErrorCode(e.getErrorCode());
//				}
//				break;
//			case 4:
//				log.info("********充值方式：【卡密充值】");
//				try {
//					//////////////////////////////////////////
//					cardRecharge(order,orderHis);
//					/////////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //充值成功
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch (RechargeException e) {
//					log.info("********卡密充值失败");
//					if (e.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) { //如果超时，需要人工确认，不能继续使用接口充值
//						orderHis.setRechargeTime(Calendar.getInstance().getTime());
//						orderHis.setResult(2); //充值失败
//						orderHis.setErrorCode(e.getErrorCode());
//					} else {
//						try {
//							log.info("********充值方式：【Socket接口充值】");
//							///////////////////////////////////////////////
//							socketRecharge(order,orderHis);
//							////////////////////////////////////////////////
//							orderHis.setRechargeTime(Calendar.getInstance().getTime());
//							orderHis.setResult(1); //充值成功
//							orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//						} catch(RechargeException re) {
//							orderHis.setRechargeTime(Calendar.getInstance().getTime());
//							orderHis.setResult(2); //充值失败
//							orderHis.setErrorCode(re.getErrorCode());
//						}
//					}
//					
//				}
//				break;
//				default:
//					log.info("********不能识别的充值方式");
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(2); //充值失败
//					orderHis.setErrorCode(ErrorCode.RECHARGEFAIL.getErrorCode());
//					break;
//			}
//		}
		
		boolean rechargeResult = cardRecharge(order);
		
		//如果充值失败，则记录充值时间，朱凡那边会根据充值时间写历史表
		//如果充值成功，则什么都不变，等待回调
		if (!rechargeResult) {
			order.setDealResult(2);  //充值失败
			order.setRechargeTime(Calendar.getInstance().getTime());
		} 
		/////////////////////////////////////////////////////测试用，测试完需要恢复上面代码
//		orderHis.setCallback(1);
		//记录已充值订单历史
		RechargePool.getInstance().processResult(order);
//		orderDao.saveOrder(order);
		
		log.info("********充值完成时间：【"+sdf.format(order.getRechargeTime())+"】");
	}
	
	/**
	 * Socket充值
	 * @param order
	 * @return
	 * @throws RechargeException
	 */
//	private void socketRecharge(Order order,OrderHis oh) throws RechargeException {
//		try {
//			AgentInterfaceManager agentInterfaceManager = RechargeContext.getRechargeContext().getBean("agentInterfaceManager");
//			agentInterfaceManager.deducting(order.getAgentId(), order.getFlowNo(), order.getGroupNo(), order.getMobile(), order.getMoney(),oh);
//			
//			oh.setRechargeType(1); //接口充值
//			
//			log.info("********充值结果：【接口充值成功】");
//		
//		} catch(RechargeException re) {
//			log.info("********充值结果：【接口充值失败："+ErrorCode.getDesc(re.getErrorCode())+"】");
//			throw re;
//		} catch (Exception e) {
//			log.info("********充值结果：【接口充值失败：系统异常】");
//			log.error(e);
//			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
//		} 
//	}
//	
//	/**
//	 * 卡密充值
//	 * @param order
//	 * @return
//	 * @throws RechargeException
//	 */
//	private void cardRecharge(Order order,OrderHis oh) throws RechargeException {
//		String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//		AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
//		
//		if (null == areaCode) {
//			log.info("********该号码不在允许充值城市范围内");
//			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
//		}
//		RechargeCard rechargeCard = rechargeDao.getRechargeCard(areaCode.getCityCode(),order.getMoney());
//		if(null == rechargeCard) {
//			log.info("********该号码所在城市没有可以使用的充值卡");
//			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
//		}
//		
//		try {
//			CardRechargeManager cardRechargeManager = RechargeContext.getRechargeContext().getBean("cardRechargeManager");
//			cardRechargeManager.rechargeByCard(order.getAgentId(),order.getMobile(), rechargeCard.getCardNo(), rechargeCard.getCardPwd(), areaCode.getCityCode(),order.getMoney(),oh);
//		} catch (RechargeException re) {
//			if (re.getErrorCode() == ErrorCode.RECHARGECARD.getErrorCode()) {
//				log.info("********该充值卡不可用："+rechargeCard.getCardNo());
//				rechargeCard.setUseState(3); //该充值卡不可用
//				rechargeCard.setUseTime(Calendar.getInstance().getTime());
//				rechargeDao.updateRechargeCard(rechargeCard);
//			}else if (re.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
//				log.info("********该充值卡不可用："+rechargeCard.getCardNo());
//				rechargeCard.setUseState(4); //可疑
//				rechargeCard.setUseTime(Calendar.getInstance().getTime());
//				rechargeDao.updateRechargeCard(rechargeCard);
//			}
//			throw re;
//		}
//		
//		
//		oh.setRechargeType(2); //卡密充值
//		oh.setCardNo(rechargeCard.getCardNo());
//		oh.setCardPwd(rechargeCard.getCardPwd());
//		
//		//修改充值卡状态
//		rechargeCard.setUseState(2); //已使用
//		rechargeCard.setUseTime(Calendar.getInstance().getTime());
//		rechargeCard.setMobile(order.getMobile());
//		rechargeDao.updateRechargeCard(rechargeCard);
//		
//		//记录BIZ_DEAL_RECORD表======================================================================
//		DealRecord dealRecord = new DealRecord();
//		dealRecord.setGroupCode(order.getGroupNo());
//		dealRecord.setDealType(2); //充值卡充值
//		dealRecord.setDealTime(Calendar.getInstance().getTime());
//		dealRecord.setCreateType(1);
//		dealRecord.setIp(areaCodeDesc);
//		dealRecord.setPhoneNo(order.getMobile());
//		dealRecord.setAmount(order.getMoney());
//		dealRecord.setStatus(6);//充值全部成功了
//		dealRecord.setDeleted(1);
//		dealRecord.setTransCode(order.getFlowNo());
//		dealRecord.setSmallBillNo(order.getFlowNo());
//		dealRecord.setCardId(rechargeCard.getId());
//		dealRecord.setShopCode(getShopNo());
//		rechargeDao.saveDealRecord(dealRecord);
//		log.info("********修改充值卡状态成功");
//		log.info("********充值结果：【卡密充值成功】");
//		
//	}
	
	private boolean cardRecharge(Order order) {
		try {
			
			AgentMemory agentMemory = AgentMemory.getAgentMemory();
			Agent agent = agentMemory.getAgent(order.getAgentId());
			String rechargeUrl = "http://api2.ofpay.com/recharge/rechargeorder.do";
			
//			String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//			AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
			RechargeCard rechargeCard = rechargeDao.getRechargeCard(order.getMoney());
			
			if (null == rechargeCard) {
				return false;
			}
			//AES加密key 假设商户约定密钥为SSS0JKEU9TR7H244ORNMU94MQ6HXDDDD
	        String key = MD5Utils.encodeByMD5("Abcd1234").substring(0, 16).toLowerCase();
	        
			//加密cardno
	        String sCardNo = AES.encrypt(rechargeCard.getCardNo(), key, "0102030405060708");
	        
	        //加密cardpass
	        String sCardpass = AES.encrypt(rechargeCard.getCardPwd(), key, "0102030405060708");
			
	        //加密md5str
			String md5Source = "A1404230" + rechargeCard.getCardNo() + rechargeCard.getCardPwd() + order.getMobile() +order.getMoney() / 100 + order.getFlowNo()+"Abcd1234";
			
			String sign = MD5Utils.encodeByMD5(md5Source).toUpperCase();
			
			//回调
			HttpParams params=new BasicHttpParams();
			ConnManagerParams.setMaxTotalConnections(params, 50);
			ThreadSafeClientConnManager tsccm=new ThreadSafeClientConnManager();
			tsccm.setMaxTotal(50);
			DefaultHttpClient httpClient=new DefaultHttpClient(tsccm,params);
			
			httpClient.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8);
			httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
			httpClient.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
			httpClient.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,HTTP.UTF_8);
			HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
			HttpPost rechargePost = new HttpPost(rechargeUrl);
			
		
			List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
			nvps.add(new BasicNameValuePair("userid", "A1404230"));  
			nvps.add(new BasicNameValuePair("cardno", sCardNo)); 
			nvps.add(new BasicNameValuePair("cardpass", sCardpass));
			nvps.add(new BasicNameValuePair("chargeno", order.getMobile()));
			nvps.add(new BasicNameValuePair("parvalue", String.valueOf(order.getMoney() / 100)));
			nvps.add(new BasicNameValuePair("sporderid", order.getFlowNo()));
			nvps.add(new BasicNameValuePair("mobilenotype", "1"));
			nvps.add(new BasicNameValuePair("cardid", "1231601"));
			nvps.add(new BasicNameValuePair("md5str", sign));
			nvps.add(new BasicNameValuePair("returl", "http://58.240.17.142:8080/recharge/cardRechargeCallback.do"));
			nvps.add(new BasicNameValuePair("noticenumber", ""));
			
			log.info("********卡密充值请求参数==>userid："+"A1404230");
			log.info("********卡密充值请求参数==>cardno："+rechargeCard.getCardNo()+";sCardno:"+sCardNo);
			log.info("********卡密充值请求参数==>cardpass："+rechargeCard.getCardPwd()+";sCardpass:"+sCardpass);
			log.info("********卡密充值请求参数==>chargeno："+order.getMobile());
			log.info("********卡密充值请求参数==>parvalue："+String.valueOf(order.getMoney() / 100));
			log.info("********卡密充值请求参数==>sporderid："+order.getFlowNo());
			log.info("********卡密充值请求参数==>mobilenotype："+"1");
			log.info("********卡密充值请求参数==>cardid："+"1231601");
			log.info("********卡密充值请求参数==>md5str："+sign);
			log.info("********卡密充值请求参数==>returl："+"http://58.240.17.142:8080/recharge/cardRechargeCallback.do");
			log.info("********卡密充值请求参数==>noticenumber："+"");
			
			
			rechargePost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(rechargePost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String resultStr = EntityUtils.toString(response.getEntity()); 
				log.info("********卡密充值订单下发返回结果："+resultStr);
				if (resultStr.contains("<retcode>1</retcode>")) {
					log.info("********卡密充值订单下发成功");
					
					//修改充值卡状态
					rechargeCard.setUseTime(Calendar.getInstance().getTime());
					rechargeCard.setMobile(order.getMobile());
					rechargeCard.setCityCode(order.getMobile());  //cityCode字段已经不用了，为了后台展示，把手机号码也放到这个字段里面来
					rechargeCard.setFlowNo(order.getFlowNo());
					rechargeDao.updateRechargeCard(rechargeCard);
					return true;
				} 
			} 
			
			//修改充值卡状态
			rechargeCard.setUseState(RechargeCardStatus.UNUSE.intValue()); //失败就改成未使用
			rechargeDao.updateRechargeCard(rechargeCard);
			
			log.info("********卡密充值订单下发失败："+response.getStatusLine().getStatusCode());
			return false;
			
		} catch (Exception e) {
			log.info("********卡密充值订单下发失败：");
			e.printStackTrace();
			log.error("exception:",e);
			return false;
		}
		
		
	}
	
//	//获取一个随机的门店编号
//	private String getShopNo() {
//		try{
//			Random r = new Random();
//			int i = r.nextInt(600);
//			return ShopNo.shopNo[i];
//		} catch(Exception e) {
//			return "300";
//		}
//	}
	
	public static void main(String[] args) {
		
        
		//加密cardno
        try {
        	String tmp = "A1404230341601073199500340215873335668960713218888000"+50 + "CHt011913218888000";
        	String key = MD5Utils.encodeByMD5(tmp).toUpperCase();
			 System.out.print(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
	}

}
