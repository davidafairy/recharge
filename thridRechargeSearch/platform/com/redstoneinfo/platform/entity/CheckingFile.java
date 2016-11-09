package com.redstoneinfo.platform.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name="BIZ_CHECKINGFILE") 
@SequenceGenerator(name = "biz_checkingfile_seq", sequenceName = "biz_checkingfile_seq", allocationSize = 1, initialValue = 100)
public class CheckingFile extends BaseEntity {

	private static final long serialVersionUID = -8528682546203040989L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biz_checkingfile_seq")
	@Column(name = "ID",  nullable = false, length = 20) 
	private int id;
	
	private String agentName;
	
	private long agentId;
	
	private String checkingFileCycle;
	
	private Date genTime;
	
	private String fileName;

	private int result;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCheckingFileCycle() {
		return checkingFileCycle;
	}

	public void setCheckingFileCycle(String checkingFileCycle) {
		this.checkingFileCycle = checkingFileCycle;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	
	public long getAgentId() {
		return agentId;
	}

	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("agentName", agentName);
		json.put("checkingFileCycle", checkingFileCycle);
		json.put("fileName", fileName);
		return json;
	}
}
