package com.thridrecharge.service.ordermanager.recharge;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

import com.ofpay.util.encoding.AES;
import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.RechargeCard;
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
		
//		rechargeDao = RechargeContext.getRechargeContext().getBean("rechargeDao");
//		orderDao = RechargeContext.getRechargeContext().getBean("orderDao");
	}
	
	@Override
	public void run() {
		log.info("=======================================================================================================================================");
		log.info("=========================================��ʼ���ܳ�ֵ(�ֻ��ţ�"+order.getMobile()+")===========================================================");
		log.info("=======================================================================================================================================");
		log.info("********������  ����"+order.getAgentId()+"��");
		log.info("********������  ����"+order.getFlowNo()+"��");
		log.info("********�ֻ���  ����"+order.getMobile()+"��");
		log.info("********��ֵ����"+order.getMoney()+"��");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		log.info("********����ʱ�䣺��"+sdf.format(order.getCreateTime())+"��");
		log.info("********���ܳ�ֵ��ʼʱ�䣺��"+sdf.format(Calendar.getInstance().getTime())+"��");
		
		
		
//		String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//		AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
//		log.info("********��ֵ���ԣ���"+RechargeStrategy.getStrategyDesc(areaCode.getRechargeStrategy())+"��");
		
		//��ֵ��ʷ
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
		
//		// ����Ƿ��ص�
//		OrderHis repeatOrder = orderDao.findOrderHisByFlowNo(order.getFlowNo());
//		if (null != repeatOrder) {
//			log.info("********�ظ�����-->��"+repeatOrder.getId()+"�ظ�");
//			orderHis.setRechargeTime(Calendar.getInstance().getTime());
//			orderHis.setResult(2); //��ֵʧ��
//			orderHis.setErrorCode(ErrorCode.REPEAT.getErrorCode());
//		} else {
//			switch(areaCode.getRechargeStrategy()) {
//			case 1:
//				try {
//					log.info("********��ֵ��ʽ����Socket�ӿڳ�ֵ��");
//					////////////////////////////////////////////
//					socketRecharge(order,orderHis);
//					/////////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //��ֵ�ɹ�
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch (RechargeException re) {
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(2); //��ֵʧ��
//					orderHis.setErrorCode(re.getErrorCode());
//				}
//				break;
//			case 2:
//				try {
//					log.info("********��ֵ��ʽ����Socket�ӿڳ�ֵ��");
//					//////////////////////////////////////
//					socketRecharge(order,orderHis);
//					////////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //��ֵ�ɹ�
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch(RechargeException re) {
//					log.info("********Socket�ӿڳ�ֵʧ��");
//					try {
//						log.info("********��ֵ��ʽ�������ܳ�ֵ��");
//						////////////////////////////////////
//						cardRecharge(order,orderHis);
//						////////////////////////////////////
//						orderHis.setRechargeTime(Calendar.getInstance().getTime());
//						orderHis.setResult(1); //��ֵ�ɹ�
//						orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//					} catch (RechargeException e) {
//						orderHis.setRechargeTime(Calendar.getInstance().getTime());
//						orderHis.setResult(2); //��ֵʧ��
//						orderHis.setErrorCode(e.getErrorCode());
//					}
//				}
//				break;
//			case 3:
//				log.info("********��ֵ��ʽ�������ܳ�ֵ��");
//				try {
//					//////////////////////////////////////////
//					cardRecharge(order,orderHis);
//					//////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //��ֵ�ɹ�
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch (RechargeException e) {
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(2); //��ֵʧ��
//					orderHis.setErrorCode(e.getErrorCode());
//				}
//				break;
//			case 4:
//				log.info("********��ֵ��ʽ�������ܳ�ֵ��");
//				try {
//					//////////////////////////////////////////
//					cardRecharge(order,orderHis);
//					/////////////////////////////////////////////
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(1); //��ֵ�ɹ�
//					orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//				} catch (RechargeException e) {
//					log.info("********���ܳ�ֵʧ��");
//					if (e.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) { //�����ʱ����Ҫ�˹�ȷ�ϣ����ܼ���ʹ�ýӿڳ�ֵ
//						orderHis.setRechargeTime(Calendar.getInstance().getTime());
//						orderHis.setResult(2); //��ֵʧ��
//						orderHis.setErrorCode(e.getErrorCode());
//					} else {
//						try {
//							log.info("********��ֵ��ʽ����Socket�ӿڳ�ֵ��");
//							///////////////////////////////////////////////
//							socketRecharge(order,orderHis);
//							////////////////////////////////////////////////
//							orderHis.setRechargeTime(Calendar.getInstance().getTime());
//							orderHis.setResult(1); //��ֵ�ɹ�
//							orderHis.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
//						} catch(RechargeException re) {
//							orderHis.setRechargeTime(Calendar.getInstance().getTime());
//							orderHis.setResult(2); //��ֵʧ��
//							orderHis.setErrorCode(re.getErrorCode());
//						}
//					}
//					
//				}
//				break;
//				default:
//					log.info("********����ʶ��ĳ�ֵ��ʽ");
//					orderHis.setRechargeTime(Calendar.getInstance().getTime());
//					orderHis.setResult(2); //��ֵʧ��
//					orderHis.setErrorCode(ErrorCode.RECHARGEFAIL.getErrorCode());
//					break;
//			}
//		}
		
		boolean rechargeResult = cardRecharge(order);
		
		if (rechargeResult) {
			order.setStatus(4); //��ֵ���µ��ɹ�
			order.setDealResult(1);  //��ֵ�ɹ�
			order.setRechargeTime(Calendar.getInstance().getTime());
		} else {
			order.setStatus(3); //��ֵ���  ---����µ�ʧ�ܵĻ�����ֱ�ӳ�ֵ���
			order.setDealResult(2);  //��ֵʧ��
			order.setRechargeTime(Calendar.getInstance().getTime());
		}
		/////////////////////////////////////////////////////�����ã���������Ҫ�ָ��������
//		orderHis.setCallback(1);
		//��¼�ѳ�ֵ������ʷ
		RechargePool.getInstance().processResult(order);
//		orderDao.saveOrder(order);
		
		log.info("********��ֵ���ʱ�䣺��"+sdf.format(order.getRechargeTime())+"��");
	}
	
	/**
	 * Socket��ֵ
	 * @param order
	 * @return
	 * @throws RechargeException
	 */
//	private void socketRecharge(Order order,OrderHis oh) throws RechargeException {
//		try {
//			AgentInterfaceManager agentInterfaceManager = RechargeContext.getRechargeContext().getBean("agentInterfaceManager");
//			agentInterfaceManager.deducting(order.getAgentId(), order.getFlowNo(), order.getGroupNo(), order.getMobile(), order.getMoney(),oh);
//			
//			oh.setRechargeType(1); //�ӿڳ�ֵ
//			
//			log.info("********��ֵ��������ӿڳ�ֵ�ɹ���");
//		
//		} catch(RechargeException re) {
//			log.info("********��ֵ��������ӿڳ�ֵʧ�ܣ�"+ErrorCode.getDesc(re.getErrorCode())+"��");
//			throw re;
//		} catch (Exception e) {
//			log.info("********��ֵ��������ӿڳ�ֵʧ�ܣ�ϵͳ�쳣��");
//			log.error(e);
//			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
//		} 
//	}
//	
//	/**
//	 * ���ܳ�ֵ
//	 * @param order
//	 * @return
//	 * @throws RechargeException
//	 */
//	private void cardRecharge(Order order,OrderHis oh) throws RechargeException {
//		String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//		AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
//		
//		if (null == areaCode) {
//			log.info("********�ú��벻�������ֵ���з�Χ��");
//			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
//		}
//		RechargeCard rechargeCard = rechargeDao.getRechargeCard(areaCode.getCityCode(),order.getMoney());
//		if(null == rechargeCard) {
//			log.info("********�ú������ڳ���û�п���ʹ�õĳ�ֵ��");
//			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
//		}
//		
//		try {
//			CardRechargeManager cardRechargeManager = RechargeContext.getRechargeContext().getBean("cardRechargeManager");
//			cardRechargeManager.rechargeByCard(order.getAgentId(),order.getMobile(), rechargeCard.getCardNo(), rechargeCard.getCardPwd(), areaCode.getCityCode(),order.getMoney(),oh);
//		} catch (RechargeException re) {
//			if (re.getErrorCode() == ErrorCode.RECHARGECARD.getErrorCode()) {
//				log.info("********�ó�ֵ�������ã�"+rechargeCard.getCardNo());
//				rechargeCard.setUseState(3); //�ó�ֵ��������
//				rechargeCard.setUseTime(Calendar.getInstance().getTime());
//				rechargeDao.updateRechargeCard(rechargeCard);
//			}else if (re.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
//				log.info("********�ó�ֵ�������ã�"+rechargeCard.getCardNo());
//				rechargeCard.setUseState(4); //����
//				rechargeCard.setUseTime(Calendar.getInstance().getTime());
//				rechargeDao.updateRechargeCard(rechargeCard);
//			}
//			throw re;
//		}
//		
//		
//		oh.setRechargeType(2); //���ܳ�ֵ
//		oh.setCardNo(rechargeCard.getCardNo());
//		oh.setCardPwd(rechargeCard.getCardPwd());
//		
//		//�޸ĳ�ֵ��״̬
//		rechargeCard.setUseState(2); //��ʹ��
//		rechargeCard.setUseTime(Calendar.getInstance().getTime());
//		rechargeCard.setMobile(order.getMobile());
//		rechargeDao.updateRechargeCard(rechargeCard);
//		
//		//��¼BIZ_DEAL_RECORD��======================================================================
//		DealRecord dealRecord = new DealRecord();
//		dealRecord.setGroupCode(order.getGroupNo());
//		dealRecord.setDealType(2); //��ֵ����ֵ
//		dealRecord.setDealTime(Calendar.getInstance().getTime());
//		dealRecord.setCreateType(1);
//		dealRecord.setIp(areaCodeDesc);
//		dealRecord.setPhoneNo(order.getMobile());
//		dealRecord.setAmount(order.getMoney());
//		dealRecord.setStatus(6);//��ֵȫ���ɹ���
//		dealRecord.setDeleted(1);
//		dealRecord.setTransCode(order.getFlowNo());
//		dealRecord.setSmallBillNo(order.getFlowNo());
//		dealRecord.setCardId(rechargeCard.getId());
//		dealRecord.setShopCode(getShopNo());
//		rechargeDao.saveDealRecord(dealRecord);
//		log.info("********�޸ĳ�ֵ��״̬�ɹ�");
//		log.info("********��ֵ����������ܳ�ֵ�ɹ���");
//		
//	}
	
	private boolean cardRecharge(Order order) {
		try {
			
			AgentMemory agentMemory = AgentMemory.getAgentMemory();
			Agent agent = agentMemory.getAgent(order.getAgentId());
			String rechargeUrl = "http://api2.ofpay.com/recharge/rechargeorder.do";
			
			String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
			AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
			RechargeCard rechargeCard = rechargeDao.getRechargeCard(areaCode.getCityCode(),order.getMoney());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String rechargeParam = "userid=A1404230&cardno=" + rechargeCard.getCardNo() + "&cardpass=" + rechargeCard.getCardPwd() + "&chargeno=" +order.getMobile()
								+ "&parvalue="+order.getMoney() / 100 + "&sporderid=" + order.getFlowNo() + "&mobilenotype=1&";
			
			String sKey = MD5Utils.encodeByMD5(rechargeParam+"&key=Abcd1234");
			sKey = sKey.substring(0,16);
			String sign = AES.encrypt(rechargeParam, sKey, "0102030405060708");
			rechargeUrl = rechargeUrl + "?" + rechargeParam + "&cardid=1231601&mobilenotype=1&md5str="+sign+"&returl=http://58.240.17.142:28080/recharge/cardRechargeCallback.do"+"&noticenumber=";
			
			//�ص�
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
			HttpGet rechargeGet = new HttpGet(rechargeUrl);
			log.info("********���ܳ�ֵ��ַ��"+rechargeUrl);
		
			HttpResponse response = httpClient.execute(rechargeGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String resultStr = EntityUtils.toString(response.getEntity()); 
				log.info("********���ܳ�ֵ�����·����ؽ����"+resultStr);
				if ("<retcode>1</retcode>".equalsIgnoreCase(resultStr)) {
					log.info("********���ܳ�ֵ�����·��ɹ�");
					
					//�޸ĳ�ֵ��״̬
					rechargeCard.setUseState(RechargeCardStatus.OCCUPY.intValue()); //Ԥռ
					rechargeCard.setUseTime(Calendar.getInstance().getTime());
					rechargeCard.setMobile(order.getMobile());
					rechargeDao.updateRechargeCard(rechargeCard);
					return true;
				} 
			} 
			log.info("********���ܳ�ֵ�����·�ʧ�ܣ�"+response.getStatusLine().getStatusCode());
			return false;
			
		} catch (Exception e) {
			log.info("********���ܳ�ֵ�����·�ʧ�ܣ�");
			log.error(e);
			return false;
		}
		
		
	}
	
//	//��ȡһ��������ŵ���
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
