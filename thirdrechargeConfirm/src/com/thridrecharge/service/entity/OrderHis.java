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
	
	//�������
	private int orderId;
	
	//�����̱��
	private int agentId;
	
	//�������
	private String flowNo;
	
	//�ֻ���
	private String mobile;
	
	//��ֵ���
	private long money;
	
	//ʵ�ʿۿ�
	private long amount;
	
	//���
	private long balance;
	
	//��ֵ���͡�1��socket��ֵ��2����ֵ����ֵ
	private int rechargeType;
	
	//���ͨ����ֵ����ֵ�����¼��ֵ����
	private String cardNo;
	
	//���ͨ����ֵ����ֵ�����¼��ֵ������
	private String cardPwd;
	
	//��������ʱ��
	private Date createTime;
	
	//��ֵʱ��
	private Date rechargeTime;
	
	//��ֵ�����1���ɹ���2��ʧ��
	private int result;
	
	//������룬�����0��ʾ�ɹ�
	private int errorCode;
	
	//�ص������1���ص��ɹ���2���ص�ʧ��
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
