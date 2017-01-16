package com.thridrecharge.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 订单
 * @author pc
 *
 */
@Entity
@Table(name="biz_order",schema="cs") 
@SequenceGenerator(name = "biz_order_seq", sequenceName = "biz_order_seq", allocationSize = 1, initialValue = 100)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_order_seq")
	@Column(name = "ID", nullable = false, length = 20) 
	private int id;
	
	//代理商编号
	private int agentId;
	
	//订单编号
	private String flowNo;
	
	//集团编号
	private String groupNo;
	
	//手机号
	private String mobile;
	
	//充值金额
	private long money;
	
	//创建时间
	private Date createTime;
	
	//充值时间
	@Column(name="RECHARGE_TIME")
	private Date rechargeTime;

	//加载状态。1：未加载内存；2：已加载内存；3:已充值完成
	private int status = 1;
	
	//充值结果，默认为0，1：充值成功；2充值失败
	@Column(name="DEAL_RESULT")
	private int dealResult = 0;
	
	//1:线下；2:线上。默认线下
	private int channel = 1;
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDealResult() {
		return dealResult;
	}

	public void setDealResult(int dealResult) {
		this.dealResult = dealResult;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	
	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", agentId=" + agentId + ", flowNo=" + flowNo + ", groupNo=" + groupNo + ", mobile="
				+ mobile + ", money=" + money + ", createTime=" + createTime + ", rechargeTime=" + rechargeTime
				+ ", status=" + status + ", dealResult=" + dealResult + ", channel=" + channel + "]";
	}

	
	
}
