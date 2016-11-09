package com.redstoneinfo.platform.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="biz_order_his") 
public class OrderHis extends BaseEntity {

	//�������
	private int orderId;
	
	//�����̱��
	private int agentId;
	
	//�������
	private String flowNo;
	
	//�ֻ��
	private String mobile;
	
	//��ֵ���
	private long money;
	
	//ʵ�ʿۿ�
	private long amount;
	
	//���
	private long balance;
	
	//��ֵ���͡�1��socket��ֵ��2����ֵ����ֵ
	private int rechargeType;
	
	//���ͨ���ֵ����ֵ�����¼��ֵ����
	private String cardNo;
	
	//���ͨ���ֵ����ֵ�����¼��ֵ������
	private String cardPwd;
	
	//��������ʱ��
	private Date createTime;
	
	//��ֵʱ��
	private Date rechargeTime;
	
	//��ֵ���1���ɹ���2��ʧ��
	private int result;
	
	//������룬�����0��ʾ�ɹ�
	private int errorCode;
	
	//�ص����1���ص��ɹ���2���ص�ʧ��
	private int callback;

	public long id;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)  
	public long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("flowNo", flowNo);
		json.put("mobile", mobile);
		json.put("money", ((double)money)/100);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		json.put("createTime", dateFormat.format(createTime));
		json.put("rechargeTime", dateFormat.format(rechargeTime));
		json.put("balance", ((double)balance)/1000);
		if (errorCode == 13) {
			json.put("result", "可疑");
		}else {
			if (result == 1) {
				json.put("result", "充值成功");
			}else {
				json.put("result", "充值失败");
			}
		}
		
		return json;
	}
	
}
