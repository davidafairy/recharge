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
 * 充值卡
 * @author pc
 *
 */
@Entity
@Table(name="biz_recharge_card",schema="cs") 
@SequenceGenerator(name = "biz_recharge_card_seq", sequenceName = "biz_recharge_card_seq", allocationSize = 1, initialValue = 100)
public class RechargeCard {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_recharge_card_seq")
	@Column(name = "ID",  nullable = false, length = 20) 
	private int id;
	
	//卡号
	private String cardNo;
	
	//卡密
	private String cardPwd;
	
	//面额
	private long amount;
	
	//省份代码，暂时不用
	private String provinceCode;
	
	//城市编码
	private String cityCode;
	
	//使用状态。1：未使用；2：已使用
	private int useState;
	
	//使用时间
	private Date useTime;
	
	private String mobile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getUseState() {
		return useState;
	}

	public void setUseState(int useState) {
		this.useState = useState;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
