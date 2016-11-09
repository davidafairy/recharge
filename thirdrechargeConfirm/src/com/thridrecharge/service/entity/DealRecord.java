package com.thridrecharge.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="biz_deal_record",schema="cs") 
@SequenceGenerator(name = "biz_deal_record_seq", sequenceName = "sequser.biz_deal_record", allocationSize = 1, initialValue = 100)
public class DealRecord implements Serializable {

	private static final long serialVersionUID = 5939193282687473855L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_deal_record_seq")
	@Column(name = "ID", nullable = false, length = 20) 
	private int id;
	
	@Column(name = "GROUP_CODE")
	private String groupCode;
	
	@Column(name = "SHOP_CODE")
	private String shopCode;
	
	@Column(name = "SALESMAN")
	private String salesman;
	
	@Column(name = "POS_CODE")
	private String posCode;
	
	@Column(name = "DEAL_TYPE")
	private int dealType;
	
	@Column(name = "CREATE_TYPE")
	private int createType;
	
	@Column(name = "IP")
	private String ip;
	
	@Column(name = "DEAL_TIME")
	private Date dealTime;
	
	@Column(name = "SMALL_BILL_NO")
	private String smallBillNo;
	
	@Column(name = "PHONE_NO")
	private String phoneNo;
	
	@Column(name = "AMOUNT")
	private long amount;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "DELETED")
	private int deleted;
	
	@Column(name = "BILL_ID")
	private int billId;
	
	@Column(name = "TRANS_CODE")
	private String transCode;
	
	@Column(name = "CARD_ID")
	private int cardId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public int getDealType() {
		return dealType;
	}

	public void setDealType(int dealType) {
		this.dealType = dealType;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getSmallBillNo() {
		return smallBillNo;
	}

	public void setSmallBillNo(String smallBillNo) {
		this.smallBillNo = smallBillNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	
}
