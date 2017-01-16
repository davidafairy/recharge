package com.thridrecharge.service.ordermanager.recharge;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thridrecharge.service.cardrecharge.CardRechargeManager;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.ordermanager.OrderDao;
import com.thridrecharge.service.socketrecharge.AgentInterfaceManager;

@Controller
public class RechargeManager {

	private Log log = LogFactory.getLog("recharge");
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private RechargeDao rechargeDao;
	
	//Socket��ֵ������
	@Autowired
	private AgentInterfaceManager agentInterfaceManager;
	
	//���ܳ�ֵ������
	@Autowired
	private CardRechargeManager cardRechargeManager;
	
	public void startRecharge() {
		while(true) {
			try {
				//�����ڴ����Ѿ���ɵĶ���
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				List<Order> orderHisList = RechargePool.getInstance().getOrderHisList();
				log.info("@@@@@@@@@@@@@�����ڴ����Ѿ���ֵ��ɵĶ�����"+orderHisList.size());
				for (Order order : orderHisList) {
//					if (orderHis.getResult() == 1) {
//						log.info("@@@@@@@@@@@@@��ֵ�ɹ�������ۿ"+orderHis.getMobile());
//						rechargeDao.deducting(orderHis.getAgentId(), orderHis.getMoney(),orderHis);
//					}
					orderDao.saveOrder(order);  
//					orderDao.cleanOrderById(orderHis.getOrderId());
					log.info("@@@@@@@@@@@@@�����ڴ����Ѿ���ɵĶ������ֻ��ţ�"+order.getMobile()+";������:"+order.getFlowNo());
					
				}
				log.info("@@@@@@@@@@@@@�ڴ����Ѿ���ɵĶ�������ɹ�");
				
				//�����µĶ���
				int idlesse = RechargePool.getInstance().getIdlesseQueue();
				
				log.info("@@@@@@@@@@@@@�ܶ�������"+RechargePool.THREAD_QUEUE+";���ж�������"+idlesse);
				log.info("@@@@@@@@@@@@@�̳߳��Ƿ���У�"+RechargePool.getInstance().isStoped());
				if(idlesse > 0) {
					log.info("@@@@@@@@@@@@@�����µĴ�������");
					List<Integer> agentIds = orderDao.getAgentIdList();
					if (agentIds.size() > 0) {
						String ids = "";
						for (Integer agentId : agentIds) {
							ids += String.valueOf(agentId)+",";
						}
						log.info("@@@@@@@@@@@@@��������������ID��"+ids);
						List<Order> orders = orderDao.getCardRechargeOrders(idlesse,agentIds);
						log.info("@@@@@@@@@@@@@����"+orders.size()+"���µĴ���ֵ����");
						if (orders.size() > 0) {
							RechargePool.getInstance().processRecharge(orders);
							
						} else {
							log.info("@@@@@@@@@@@@@û�в�ѯ���ɳ�ֵ�������ȴ�2��");
							try {
								Thread.currentThread().sleep(2000);
							} catch (InterruptedException e) {
								//Do nothing
							}
						}
					} else {
						log.info("@@@@@@@@@@@@@û�з��������Ĵ�����");
						log.info("@@@@@@@@@@@@@�ڴ���û�п��ж��У��ȴ�2��");
						try {
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							//Do nothing
						}
					}
					
				}else {
					log.info("@@@@@@@@@@@@@�ڴ���û�п��ж��У��ȴ�2��");
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e) {
						//Do nothing
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
	}
	
	
//	/**
//	 * ��������ֵ
//	 * @param order ������Ķ���
//	 * @return ����������ʷ�������
//	 */
//	private void recharge(Order order) {
//		
//		log.info("=======================================================================================================================================");
//		log.info("=========================================��ʼ��ֵ(�ֻ��ţ�"+order.getMobile()+")===========================================================");
//		log.info("=======================================================================================================================================");
//		log.info("********������  ����"+order.getAgentId()+"��");
//		log.info("********������  ����"+order.getFlowNo()+"��");
//		log.info("********�ֻ���  ����"+order.getMobile()+"��");
//		log.info("********��ֵ����"+order.getMoney()+"��");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		log.info("********����ʱ�䣺��"+sdf.format(order.getCreateTime())+"��");
//		log.info("********��ֵ��ʼʱ�䣺��"+sdf.format(Calendar.getInstance().getTime())+"��");
//		
//		String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
//		AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
//		log.info("********��ֵ���ԣ���"+RechargeStrategy.getStrategyDesc(areaCode.getRechargeStrategy())+"��");
//		
//		//��ֵ��ʷ
//		OrderHis orderHis = new OrderHis();
//		orderHis.setAgentId(order.getAgentId());
//		orderHis.setFlowNo(order.getFlowNo());
//		orderHis.setMobile(order.getMobile());
//		orderHis.setMoney(order.getMoney());
//		orderHis.setCreateTime(order.getCreateTime());
//		orderHis.setOrderId(order.getId());
//		
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
//		
//		
//		if (callback(orderHis)) {
//			orderHis.setCallback(1); //�ص��ɹ�
//		} else {
//			orderHis.setCallback(2); //�ص�ʧ��
//		}
//
//		//��¼�ѳ�ֵ������ʷ
//		orderDao.saveOrderHis(orderHis);
//		
//		log.info("********��ֵ���ʱ�䣺��"+sdf.format(orderHis.getRechargeTime())+"��");
//	}
//	
//	/**
//	 * Socket��ֵ
//	 * @param order
//	 * @return
//	 * @throws RechargeException
//	 */
//	private void socketRecharge(Order order,OrderHis oh) throws RechargeException {
//		try {
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
//			cardRechargeManager.rechargeByCard(order.getAgentId(),order.getMobile(), rechargeCard.getCardNo(), rechargeCard.getCardPwd(), areaCode.getCityCode(),order.getMoney(),oh);
//		} catch (RechargeException re) {
//			if (re.getErrorCode() == ErrorCode.RECHARGECARD.getErrorCode()) {
//				log.info("********�ó�ֵ�������ã�"+rechargeCard.getCardNo());
//				rechargeCard.setUseState(3); //�ó�ֵ��������
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
//	
//	private boolean callback(OrderHis orderHis) {
//		AgentMemory agentMemory = AgentMemory.getAgentMemory();
//		Agent agent = agentMemory.getAgent(orderHis.getAgentId());
//		String callback = agent.getCallback();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String callbackParam = "userid=" + agent.getLoginName() + "&orderid=" + orderHis.getOrderId() + "&sporderid=" + orderHis.getFlowNo()
//				+ "&merchantsubmittime=" + sdf.format(orderHis.getRechargeTime()) + "&result=" + orderHis.getErrorCode();
//		
//		String sign = MD5Utils.encodeByMD5(callbackParam+"&key=Abcd1234");
//		callback = callback + "?" + callbackParam + "&sign="+sign;
//		
//		//�ص�
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		httpClient.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8);
//		httpClient.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
//		httpClient.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
//		httpClient.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,HTTP.UTF_8);
//		HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
//		HttpGet callbackGet = new HttpGet(callback);
//		log.info("********�ص���ַ��"+callback);
//		try {
//			HttpResponse response = httpClient.execute(callbackGet);
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				String resultStr = EntityUtils.toString(response.getEntity()); 
//				log.info("********�ص����ؽ����"+resultStr);
//				if ("success".equalsIgnoreCase(resultStr)) {
//					log.info("********�ص���ַ�ɹ�");
//					return true;
//				} 
//			} 
//			log.info("********�ص���ַʧ�ܣ�"+response.getStatusLine().getStatusCode());
//			return false;
//			
//		} catch (Exception e) {
//			log.info("********�ص���ַʧ�ܣ�");
//			log.error(e);
//			return false;
//		}
//		
//		
//	}
//	
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
