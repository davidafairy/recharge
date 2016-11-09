package com.redstoneinfo.platform.ssh;

import java.util.List;

import com.redstoneinfo.platform.entity.BaseEntity;

/**
 * CRUD Action基类
 * @author davidafairy
 *
 */
public class RSCRUDAction<T extends BaseEntity> extends RSBaseAction {
	
	//CRUDAction操作的实体模型
	private T t;
	
	//CRUDAction操作的实体模型列表
	private List<T> tList;

}
