package com.thridrecharge.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="biz_agent",schema="cs") 
@SequenceGenerator(name = "biz_agent_seq", sequenceName = "biz_agent_seq", allocationSize = 1, initialValue = 100)
public class Agent implements Serializable {

	private static final long serialVersionUID = -8103956389940782415L;

	//代理商ID
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_agent_seq")
	@Column(name = "ID", nullable = false, length = 20) 
	private int id;
	
	//代理商名称
	private String name;
	
	//登录名
	private String loginName;
	
	//登录密码
	private String password;
	
	//余额
	private long balance;
	
	//IP地址
	private String ip;
	
	//集团号
	private String groupno;

	//预充值
	@Column(name="PRE_CHARGE")
	private long preCharge;
	
	@Column(name="STATE")
	private long state;
	
	private double discount = 1.0;
	
	private String callback;
	
	@Column(name="MIN_AMOUNT")
	private long minAmount;
	
	//1.表示支持线上线下；2.表示只支持线上
	private long rate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public long getPreCharge() {
		return preCharge;
	}

	public void setPreCharge(long preCharge) {
		this.preCharge = preCharge;
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

	public long getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(long minAmount) {
		this.minAmount = minAmount;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}
	
	
}
