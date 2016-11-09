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

/**
 * ����
 * @author pc
 *
 */
@Entity
@Table(name="biz_order") 
public class Order extends BaseEntity{

	//�����̱��
	private int agentId;
	
	//�������
	private String flowNo;
	
	//���ű��
	private String groupNo;
	
	//�ֻ��
	private String mobile;
	
	//��ֵ���
	private long money;
	
	private Date createTime;

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
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("flowNo", flowNo);
		json.put("mobile", mobile);
		json.put("money", money);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		json.put("createTime", dateFormat.format(createTime));
		json.put("status", "充值中");
		return json;
	}
}
