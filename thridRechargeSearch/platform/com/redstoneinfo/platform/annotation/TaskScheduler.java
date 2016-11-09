/**
 * 
 */
package com.redstoneinfo.platform.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.redstoneinfo.platform.enums.TaskType;


/**
 * @author zhangxiaorong
 * 2014-3-26
 */



@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface TaskScheduler {
	
	public String jobName() default "默认名字";
//	public String group() default "";
//	public TaskType[] taskType() default {TaskType.MANUAL_RUN};

}
