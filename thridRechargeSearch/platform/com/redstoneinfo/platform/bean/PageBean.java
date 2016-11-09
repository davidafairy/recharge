package com.redstoneinfo.platform.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageBean {
	
	private int page;
	
	private int rows;
	
	private int total;
	
	private List result = new ArrayList();
	
	/**
	 * key String 查询的字段名，
	 * 			  查询方式：
	 * 			  equals 页面中需要用condition.eq.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  like 页面中需要用condition.lk.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  greater 页面中需要用condition.gt.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  less 页面中需要用condition.lt.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  greater-equal 页面中需要用condition.ge.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  less-equal 页面中需要用condition.le.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  not equals 页面中需要用condition.not.xxx形式书写，xxx表示实体中的属性名称。
	 * 			  not like 页面中需要用condition.notlk.xxx形式书写，xxx表示实体中的属性名称。
	 */
	private Map<String,String> condition = new HashMap<String,String>();

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public Map<String,String> getCondition() {
		return condition;
	}

	public void setCondition(Map<String,String> condition) {
		this.condition = condition;
	}
	
	public void addCondition(String key,String value) {
		condition.put(key, value);
	}
}
