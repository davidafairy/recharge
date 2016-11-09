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
	 * 充值接口
	 * 
	 * result
	 * 		0：成功
	 * 		1：小票号或柜员编号超长
	 * 		2：网络错误
	 * 		3：信息发送失败
	 * 		4：信息接收失败
	 * 		5：前置系统处理失败
	 * 		6：充值失败
	 * 		7: 代理商用户密码不正确
	 * 		9：服务器不可用
	 * 		10:金额过大或过小
	 *
	 */
	public int recharge(@WebParam(name = "agentName") String agentName,@WebParam(name = "flowNo") String flowNo,@WebParam(name = "phoneNo") String phoneNo,@WebParam(name = "money") long money) {
//		log.info("==============================接收请求：============================");
//		log.info("========agentName【"+agentName+"】");
//		log.info("========flowNo【"+flowNo+"】");
//		log.info("========phoneNo【"+phoneNo+"】");
//		log.info("========money【"+money+"】");
//		try {
//			if(!"ch001".equals(agentName)&&phoneNo.startsWith("156510") || phoneNo.startsWith("156511")
//					 || phoneNo.startsWith("156512") || phoneNo.startsWith("156513")
//					 || phoneNo.startsWith("156514") || phoneNo.startsWith("156515")
//					 || phoneNo.startsWith("156516") || phoneNo.startsWith("1709")) { //屏蔽18651网段的用户
//				log.info("========充值失败：暂时不支持15651、1709网段的用户");
//				return ErrorCode.SERVERUNAVAILABLE.getErrorCode();
//			}
//			
//			//如果金额大于1000元或小于10元,则直接报错
//			if (money > 100000 || money < 1000) {
//				log.info("========充值失败：ErrorCode:【"+ErrorCode.MONEYFAIL.getErrorCode()+"】，Desc：【金额过大或过小】");
//				return ErrorCode.MONEYFAIL.getErrorCode();
//			}
//			
//			AgentMemory agentMemory = AgentMemory.getAgentMemory();
//			Agent agent = agentMemory.getAgent(agentName);
//			
//			try {
//				
//				//检查代理商最小可充值金额
////				log.info("========开始检查代理商最小可充值金额：agentName【"+agentName+"】========");
////				boolean rechargeAmountState = orderManager.checkRechargeAmount(agent.getId(),money);
////				if(!rechargeAmountState) {
////					log.info("========充值失败：ErrorCode:【"+ErrorCode.MONEYFAIL.getErrorCode()+"】，Desc：【金额低于代理商最小可充值金额】");
////					return ErrorCode.MONEYFAIL.getErrorCode();
////				}
//				
//				//检查代理商状态是否可用
//				log.info("========开始检查代理商状态：agentName【"+agentName+"】========");
//				boolean agentState = orderManager.checkAgentState(agent.getId());
//				if (!agentState) {
//					log.info("========充值失败：该代理商【"+agentName+"】暂时不可用");
//					return ErrorCode.SERVERUNAVAILABLE.getErrorCode();
//				}
//				
//				//检查代理商状态是否可用
//				log.info("========开始检查代理商余额：agentName【"+agentName+"】========");
//				boolean agentBalance = orderManager.checkAgentBalance(agent.getId());
//				if (!agentBalance) {
//					log.info("========充值失败：该代理商【"+agentName+"】余额不足");
//					return ErrorCode.BALANCE.getErrorCode();
//				}
//				
//				//开始充值
//				log.info("========开始记录订单：phoneNo【"+phoneNo+"】，money【"+money+"】========");
//				Order order = new Order();
//				order.setAgentId(agent.getId());
//				order.setCreateTime(Calendar.getInstance().getTime());
//				order.setFlowNo(flowNo);
//				order.setGroupNo(agent.getGroupno());
//				order.setMobile(phoneNo);
//				order.setMoney(money);
//				orderManager.saveOrder(order);
////				agentInterfaceManager.deducting(agent.getId(),flowNo,agent.getGroupno(),phoneNo, money);
//				log.info("========订单记录完成：phoneNo【"+phoneNo+"】，money【"+money+"】========");
//				
//				return ErrorCode.SUCCESS.getErrorCode(); //成功
//			} catch (RechargeException e) {
//				log.info("========订单接收失败：ErrorCode:【"+e.getErrorCode()+"】，Desc：【"+ErrorCode.getDesc(e.getErrorCode())+"】");
//				return e.getErrorCode();
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			log.info("========订单接收失败：ErrorCode:【"+ErrorCode.PORTALFAIL.getErrorCode()+"】，Desc：【前置系统处理失败】");
//			return ErrorCode.PORTALFAIL.getErrorCode();
//		}
		return ErrorCode.PORTALFAIL.getErrorCode();
	}
	
	/**
	 * 
	 * @param flowNo
	 * @return
	 * 0:没有该订单
	 * 1:排队中
	 * 2:充值中
	 * 3:充值成功
	 * 4:充值失败
	 * 5:其他异常
	 * 6:可疑
	 */
	public int searchRechargeResult(@WebParam(name = "flowNo") String flowNo) {
		log.info("========订单查询:"+flowNo);
		if (StringUtils.isBlank(flowNo)) {
			log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【订单号为空】");
			return 0;
		}
		Order order = orderManager.searchOrder(flowNo);
		if (null != order) {
			if (order.getStatus() == 1) {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【排队中】");
				return 1;
			}
			if (order.getStatus() == 2) {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【充值中】");
				return 2;
			} 
			log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【其他异常】");
			return 5;
		}else {
			OrderHis orderHis = orderManager.searchOrderHis(flowNo);
			if (null != orderHis) {
				if(orderHis.getResult() == 1) {
					log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【充值成功】");
					return 3;
				}
				if(orderHis.getResult() == 2) {
					if (orderHis.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
						log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【可疑】");
						return 6;//可疑
					}
					log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【充值失败】");
					return 4;
				}
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【其他异常】");
				return 5;
			} else {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【没有该订单】");
				return 0;
			}
		}
	}

}
