package com.redstoneinfo.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="biz_agent") 
public class Agent  extends BaseEntity {

	private static final long serialVersionUID = -8103956389940782415L;

	//������ID
	@Id
	@Column(name = "ID", nullable = false, length = 20) 
	private long id;
	
	//���������
	private String name;
	
	//��¼��
	private String loginName;
	
	//��¼����
	private String password;
	
	private String searchPwd;
	
	//���
	private long balance;
	
	//IP��ַ
	private String ip;
	
	//���ź�
	private String groupno;

	
	@Column(name="STATE")
	private long state;
	
	private double discount = 1.0;
	
	private String callback;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getGroupno() {
		return groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getSearchPwd() {
		return searchPwd;
	}

	public void setSearchPwd(String searchPwd) {
		this.searchPwd = searchPwd;
	}
	
	
}
