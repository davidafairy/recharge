/**
 * 
 */
package com.redstoneinfo.platform.bean;

import com.redstoneinfo.platform.exception.JobException;

/**
 * @author zhangxiaorong
 * 2014-3-26
 */
public interface TimerTask {

	/** 
     * 执行具体的任务处理 
     * @throws JobException 
     */  
    public void execute() throws JobException;
}
