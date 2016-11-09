package com.thridrecharge.service.ordermanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;

public class RechargeService {

	private Log log = LogFactory.getLog("recharge");
	
	//最大内存队列数
	public static int THREAD_QUEUE = 100;
	
	//最大线程数
	public static int THREAD_POOL_MAX = 30;
	
	private static RechargeService rechargeService = new RechargeService();
	
	private ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(THREAD_POOL_MAX);
	
	private boolean isLock = false;
	
	private List<OrderHis> resultOrder =  Collections.synchronizedList(new ArrayList<OrderHis>(20000));
	
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(); 
	
	private final Lock w = rwl.writeLock();
	
	private final Lock r = rwl.readLock();
	
	private RechargeService() {
		
	}
	
	public static RechargeService getRechargeService() {
		return rechargeService;
	}
	
	public int getIdlesseQueue() {
		return THREAD_QUEUE - exec.getQueue().size();
	}
	
	public void processRecharge(List<Order> orders) {
		
		for (Order order : orders) {
			exec.execute(new RechargeRunnable(order));
		}
	}

	public boolean isStoped() {
		if (isLock && exec.getQueue().size() == 0 && exec.getActiveCount() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void processResult(OrderHis orderHis) {
		r.lock();
		try{
			log.info("记录订单历史到内存:"+orderHis.getFlowNo());
			resultOrder.add(orderHis);
		}finally {
			r.unlock();
		}
	}
	
	public List<OrderHis> getOrderHisList() {
		w.lock();
		List<OrderHis> tmpOrderHis = new ArrayList<OrderHis>();
		try {
			tmpOrderHis.addAll(resultOrder);
			resultOrder.clear();
		} finally {
			w.unlock();
		}
		return tmpOrderHis;
	}

	public void lock(){
		this.isLock = true;
	}
	
	public void unlock() {
		this.isLock = false;
	}
	
	public boolean isLock() {
		return this.isLock;
	}
	
	public boolean haveIdle() {
		return getIdlesseQueue()>0;
	}
}
