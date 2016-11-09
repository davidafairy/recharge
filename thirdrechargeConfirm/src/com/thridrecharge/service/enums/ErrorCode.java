package com.thridrecharge.service.enums;

/**
 * 		0：成功
 * 		1：小票号或柜员编号超长
 * 		2：网络错误
 * 		3：信息发送失败
 * 		4：信息接收失败
 * 		5：前置系统处理失败
 * 		6：充值失败
 * 		7: 代理商用户密码不正确
 * 		8: 余额不足
 * 		9：服务器不可用
 * 		10：金额过大或过小
 * 		11：重复订单
 * 		12：充值卡充值异常
 * 		13：充值超时
 * @author mac
 *
 */
public enum ErrorCode {

	SUCCESS(0),FLOWNOERROR(1),NETWORKERR(2),MSGSENDFAIL(3),MSGRECEIVEFAIL(4),PORTALFAIL(5),RECHARGEFAIL(6),LOGINFAIL(7),BALANCE(8),SERVERUNAVAILABLE(9),MONEYFAIL(10),REPEAT(11),RECHARGECARD(12),TIMEOUT(13);
	
	private int code;
	
	private ErrorCode(int code) {
		this.code = code;
	}
	
	public int getErrorCode() {
		return code;
	}
	
	public static String getDesc(int errorCode) {
		switch(errorCode) {
			case 0:return "成功";
			case 1:return "小票号或柜员编号超长";
			case 2:return "网络错误";
			case 3:return "信息发送失败";
			case 4:return "信息接收失败";
			case 5:return "前置系统处理失败";
			case 6:return "充值失败";
			case 7:return "代理商用户密码不正确";
			case 8:return "余额不足";
			case 9:return "服务器不可用";
			case 10:return "金额过大或过小";
			case 11:return "重复订单";
			case 12:return "充值卡充值异常";
			case 13:return "充值超时";
			default : return "未知异常";
		}
	}
}
