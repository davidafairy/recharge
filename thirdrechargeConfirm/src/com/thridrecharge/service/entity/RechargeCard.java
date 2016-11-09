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
 * ��ֵ��
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
	
	//����
	private String cardNo;
	
	//����
	private String cardPwd;
	
	//���
	private long amount;
	
	//ʡ�ݴ��룬��ʱ����
	private String provinceCode;
	
	//���б���
	private String cityCode;
	
	//ʹ��״̬��1��δʹ�ã�2����ʹ��
	private int useState;
	
	//ʹ��ʱ��
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
