package com.thridrecharge.service;

import java.util.Calendar;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.ordermanager.OrderManager;
import com.thridrecharge.service.socketrecharge.AgentInterfaceManager;


@WebService(endpointInterface = "com.thridrecharge.service.IRechargeService", serviceName = "RechargeService")
@SOAPBinding(style = Style.RPC)
public class RechargeServiceImpl implements IRechargeService {

	private Log log = LogFactory.getLog("order");
	
	@Autowired
	private AgentInterfaceManager agentInterfaceManager;
	
	@Autowired
	private OrderManager orderManager;
	@Override
	/**
	 * ��ֵ�ӿ�
	 * 
	 * result
	 * 		0���ɹ�
	 * 		1��СƱ�Ż��Ա��ų���
	 * 		2���������
	 * 		3����Ϣ����ʧ��
	 * 		4����Ϣ����ʧ��
	 * 		5��ǰ��ϵͳ����ʧ��
	 * 		6����ֵʧ��
	 * 		7: �������û����벻��ȷ
	 * 		9��������������
	 * 		10:��������С
	 *
	 */
	public int recharge(@WebParam(name = "agentName") String agentName,@WebParam(name = "flowNo") String flowNo,@WebParam(name = "phoneNo") String phoneNo,@WebParam(name = "money") long money) {
//		log.info("==============================��������============================");
//		log.info("========agentName��"+agentName+"��");
//		log.info("========flowNo��"+flowNo+"��");
//		log.info("========phoneNo��"+phoneNo+"��");
//		log.info("========money��"+money+"��");
//		try {
//			if(!"ch001".equals(agentName)&&phoneNo.startsWith("156510") || phoneNo.startsWith("156511")
//					 || phoneNo.startsWith("156512") || phoneNo.startsWith("156513")
//					 || phoneNo.startsWith("156514") || phoneNo.startsWith("156515")
//					 || phoneNo.startsWith("156516") || phoneNo.startsWith("1709")) { //����18651���ε��û�
//				log.info("========��ֵʧ�ܣ���ʱ��֧��15651��1709���ε��û�");
//				return ErrorCode.SERVERUNAVAILABLE.getErrorCode();
//			}
//			
//			//���������1000Ԫ��С��10Ԫ,��ֱ�ӱ���
//			if (money > 100000 || money < 1000) {
//				log.info("========��ֵʧ�ܣ�ErrorCode:��"+ErrorCode.MONEYFAIL.getErrorCode()+"����Desc������������С��");
//				return ErrorCode.MONEYFAIL.getErrorCode();
//			}
//			
//			AgentMemory agentMemory = AgentMemory.getAgentMemory();
//			Agent agent = agentMemory.getAgent(agentName);
//			
//			try {
//				
//				//����������С�ɳ�ֵ���
////				log.info("========��ʼ����������С�ɳ�ֵ��agentName��"+agentName+"��========");
////				boolean rechargeAmountState = orderManager.checkRechargeAmount(agent.getId(),money);
////				if(!rechargeAmountState) {
////					log.info("========��ֵʧ�ܣ�ErrorCode:��"+ErrorCode.MONEYFAIL.getErrorCode()+"����Desc���������ڴ�������С�ɳ�ֵ��");
////					return ErrorCode.MONEYFAIL.getErrorCode();
////				}
//				
//				//��������״̬�Ƿ����
//				log.info("========��ʼ��������״̬��agentName��"+agentName+"��========");
//				boolean agentState = orderManager.checkAgentState(agent.getId());
//				if (!agentState) {
//					log.info("========��ֵʧ�ܣ��ô����̡�"+agentName+"����ʱ������");
//					return ErrorCode.SERVERUNAVAILABLE.getErrorCode();
//				}
//				
//				//��������״̬�Ƿ����
//				log.info("========��ʼ����������agentName��"+agentName+"��========");
//				boolean agentBalance = orderManager.checkAgentBalance(agent.getId());
//				if (!agentBalance) {
//					log.info("========��ֵʧ�ܣ��ô����̡�"+agentName+"������");
//					return ErrorCode.BALANCE.getErrorCode();
//				}
//				
//				//��ʼ��ֵ
//				log.info("========��ʼ��¼������phoneNo��"+phoneNo+"����money��"+money+"��========");
//				Order order = new Order();
//				order.setAgentId(agent.getId());
//				order.setCreateTime(Calendar.getInstance().getTime());
//				order.setFlowNo(flowNo);
//				order.setGroupNo(agent.getGroupno());
//				order.setMobile(phoneNo);
//				order.setMoney(money);
//				orderManager.saveOrder(order);
////				agentInterfaceManager.deducting(agent.getId(),flowNo,agent.getGroupno(),phoneNo, money);
//				log.info("========������¼��ɣ�phoneNo��"+phoneNo+"����money��"+money+"��========");
//				
//				return ErrorCode.SUCCESS.getErrorCode(); //�ɹ�
//			} catch (RechargeException e) {
//				log.info("========��������ʧ�ܣ�ErrorCode:��"+e.getErrorCode()+"����Desc����"+ErrorCode.getDesc(e.getErrorCode())+"��");
//				return e.getErrorCode();
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			log.info("========��������ʧ�ܣ�ErrorCode:��"+ErrorCode.PORTALFAIL.getErrorCode()+"����Desc����ǰ��ϵͳ����ʧ�ܡ�");
//			return ErrorCode.PORTALFAIL.getErrorCode();
//		}
		return ErrorCode.PORTALFAIL.getErrorCode();
	}
	
	/**
	 * 
	 * @param flowNo
	 * @return
	 * 0:û�иö���
	 * 1:�Ŷ���
	 * 2:��ֵ��
	 * 3:��ֵ�ɹ�
	 * 4:��ֵʧ��
	 * 5:�����쳣
	 * 6:����
	 */
	public int searchRechargeResult(@WebParam(name = "flowNo") String flowNo) {
		log.info("========������ѯ:"+flowNo);
		if (StringUtils.isBlank(flowNo)) {
			log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:��������Ϊ�ա�");
			return 0;
		}
		Order order = orderManager.searchOrder(flowNo);
		if (null != order) {
			if (order.getStatus() == 1) {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:���Ŷ��С�");
				return 1;
			}
			if (order.getStatus() == 2) {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:����ֵ�С�");
				return 2;
			} 
			log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�������쳣��");
			return 5;
		}else {
			OrderHis orderHis = orderManager.searchOrderHis(flowNo);
			if (null != orderHis) {
				if(orderHis.getResult() == 1) {
					log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:����ֵ�ɹ���");
					return 3;
				}
				if(orderHis.getResult() == 2) {
					if (orderHis.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
						log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�����ɡ�");
						return 6;//����
					}
					log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:����ֵʧ�ܡ�");
					return 4;
				}
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�������쳣��");
				return 5;
			} else {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:��û�иö�����");
				return 0;
			}
		}
	}

}
