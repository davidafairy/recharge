package com.thridrecharge.service.ordermanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.thridrecharge.service.RechargeContext;
import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.cardrecharge.CardRechargeManager;
import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;
import com.thridrecharge.service.entity.DealRecord;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.entity.RechargeCard;
import com.thridrecharge.service.entity.ShopNo;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.enums.RechargeStrategy;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.memory.AreaCodeMemory;
import com.thridrecharge.service.socketrecharge.AgentInterfaceManager;
import com.thridrecharge.service.utils.MD5Utils;

public class RechargeRunnable implements Runnable {

	private Log log = LogFactory.getLog("recharge");
	
	private Order order;
	
//	private RechargeDao rechargeDao;
//	
//	private OrderDao orderDao;
	
	public RechargeRunnable(Order order) {
		this.order = order;
		
//		rechargeDao = RechargeContext.getRechargeContext().getBean("rechargeDao");
//		orderDao = RechargeContext.getRechargeContext().getBean("orderDao");
	}
	
	@Override
	public void run() {
		log.info("=======================================================================================================================================");
		log.info("=========================================开始回调(手机号："+order.getMobile()+")===========================================================");
		log.info("=======================================================================================================================================");
		log.info("********代理商  ：【"+order.getAgentId()+"】");
		log.info("********订单号  ：【"+order.getFlowNo()+"】");
		log.info("********手机号  ：【"+order.getMobile()+"】");
		log.info("********充值金额：【"+order.getMoney()+"】");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		log.info("********订单时间：【"+sdf.format(order.getCreateTime())+"】");
		log.info("********回调开始时间：【"+sdf.format(Calendar.getInstance().getTime())+"】");
		
		
		
//		String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//		AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
//		log.info("********充值策略：【"+RechargeStrategy.getStrategyDesc(areaCode.getRechargeStrategy())+"】");
		
		//充值历史
		OrderHis orderHis = new OrderHis();
		orderHis.setAgentId(order.getAgentId());
		orderHis.setFlowNo(order.getFlowNo());
		orderHis.setMobile(order.getMobile());
		orderHis.setMoney(order.getMoney());
		orderHis.setCreateTime(order.getCreateTime());
		orderHis.setOrderId(order.getId());
		orderHis.setRechargeTime(order.getRechargeTime());
		orderHis.setResult(order.getDealResult());
		if (order.getDealResult() == 1) {
			orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
		} else {
			orderHis.setErrorCode(ErrorCode.RECHARGEFAIL.getErrorCode());
			
		}
		
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
		
		boolean callbackResult = false;
		for (int i=0;i<3;i++) {  //最多重试3次
			callbackResult = callback(orderHis);
			if (callbackResult) {
				break;
			}
		}
		
		if (callbackResult) {
			orderHis.setCallback(1); //回调成功
		} else {
			orderHis.setCallback(2); //回调失败
		}
		/////////////////////////////////////////////////////测试用，测试完需要恢复上面代码
//		orderHis.setCallback(1);
		//记录已充值订单历史
		RechargeService.getRechargeService().processResult(orderHis);
//		orderDao.saveOrderHis(orderHis);
		
		log.info("********充值完成时间：【"+sdf.format(orderHis.getRechargeTime())+"】");
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
	
	private boolean callback(OrderHis orderHis) {
		AgentMemory agentMemory = AgentMemory.getAgentMemory();
		Agent agent = agentMemory.getAgent(orderHis.getAgentId());
		String callback = agent.getCallback();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String callbackParam = "userid=" + agent.getLoginName() + "&orderid=" + orderHis.getOrderId() + "&sporderid=" + orderHis.getFlowNo()
				+ "&merchantsubmittime=" + sdf.format(orderHis.getRechargeTime()) + "&result=" + orderHis.getErrorCode();
		
		String sign = MD5Utils.encodeByMD5(callbackParam+"&key=Abcd1234");
		callback = callback + "?" + callbackParam + "&sign="+sign;
		
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
		HttpGet callbackGet = new HttpGet(callback);
		log.info("********回调地址："+callback);
		try {
			HttpResponse response = httpClient.execute(callbackGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String resultStr = EntityUtils.toString(response.getEntity()); 
				log.info("********回调返回结果："+resultStr);
				if ("success".equalsIgnoreCase(resultStr)) {
					log.info("********回调地址成功");
					return true;
				} 
			} 
			log.info("********回调地址失败："+response.getStatusLine().getStatusCode());
			return false;
			
		} catch (Exception e) {
			log.info("********回调地址失败：");
			log.error(e);
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

}
