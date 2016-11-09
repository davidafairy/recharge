package com.thridrecharge.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="biz_phone_no",schema="cs") 
@SequenceGenerator(name = "biz_phone_no_seq", sequenceName = "biz_phone_no_seq", allocationSize = 1, initialValue = 100)
public class AreaCode {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_phone_no_seq")
	@Column(name = "ID",  nullable = false, length = 20) 
	private int id;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PHONE_NO")
	private String phoneNo;
	
	@Column(name="SUCCESS_RATE")
	private int rate;
	
	//充值策略。1：接口充值；2：接口优先；3：仅限卡密；4：卡密优先
	@Column(name="RECHARGE_STRATEGY")
	private int rechargeStrategy;

	//城市编码。
	//南京340
	//苏州450
	//无锡330
	//常州440
	//南通358
	//泰州445
	//扬州430
	//盐城348
	//镇江343
	//宿迁349
	//淮安354
	//徐州350
	//连云港346
	@Column(name="CITY_CODE")
	private String cityCode;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getRechargeStrategy() {
		return rechargeStrategy;
	}

	public void setRechargeStrategy(int rechargeStrategy) {
		this.rechargeStrategy = rechargeStrategy;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
}
