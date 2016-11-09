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
 * ����
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
	
	//�����̱��
	private int agentId;
	
	//�������
	private String flowNo;
	
	//���ű��
	private String groupNo;
	
	//�ֻ���
	private String mobile;
	
	//��ֵ���
	private long money;
	
	private Date createTime;

	//����״̬��1��δ�����ڴ棻2���Ѽ����ڴ�
	private int status = 1;
		
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
	
}