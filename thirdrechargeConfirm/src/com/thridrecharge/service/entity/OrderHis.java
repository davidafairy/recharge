package com.thridrecharge.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="biz_order_his",schema="cs") 
@SequenceGenerator(name = "biz_order_his_seq", sequenceName = "biz_order_his_seq", allocationSize = 1, initialValue = 100)
public class OrderHis {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_order_his_seq")
	@Column(name = "ID",  nullable = false, length = 20) 
	private int id;
	
	//订单编号
	private int orderId;
	
	//代理商编号
	private int agentId;
	
	//订单编号
	private String flowNo;
	
	//手机号
	private String mobile;
	
	//充值金额
	private long money;
	
	//实际扣款
	private long amount;
	
	//余额
	private long balance;
	
	//充值类型。1：socket充值；2：充值卡充值
	private int rechargeType;
	
	//如果通过充值卡充值，则记录充值卡号
	private String cardNo;
	
	//如果通过充值卡充值，则记录充值卡密码
	private String cardPwd;
	
	//订单创建时间
	private Date createTime;
	
	//充值时间
	private Date rechargeTime;
	
	//充值结果。1：成功；2：失败
	private int result;
	
	//错误编码，如果是0表示成功
	private int errorCode;
	
	//回调结果。1：回调成功；2：回调失败
	private int callback;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(int rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public int getCallback() {
		return callback;
	}

	public void setCallback(int callback) {
		this.callback = callback;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
}
