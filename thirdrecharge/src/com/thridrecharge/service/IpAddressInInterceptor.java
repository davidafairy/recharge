package com.thridrecharge.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.thridrecharge.service.memory.AgentMemory;

public class IpAddressInInterceptor extends AbstractPhaseInterceptor<Message> {

	//这个属性是注入进来的,你也可以从properties,xml文件中去读取,也可以从数据库中去获取;
    private List<String> ipList;

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }
    
    private final static Logger logger = LogManager.getLogger(IpAddressInInterceptor.class);
    public IpAddressInInterceptor() {
        super(Phase.RECEIVE);
        setIpList(AgentMemory.getAgentMemory().getAllIp());
    }
    
    public void handleMessage(Message message) throws Fault {
        //指定CXF获取客户端的HttpServletRequest : http-request；
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String ipAddress="";
        boolean flag = false;
        if (null != request) {
            ipAddress = getUserIpAddr(request); // 取客户端IP地址
            logger.info("请求客户端的IP地址:" + ipAddress);
            for (String s : ipList) {
                if (StringUtils.contains(s, ipAddress)) {
                    flag = true;
                    break;
                }
            }
        }
        if(!flag) {
            throw new Fault(new IllegalAccessException("IP address " + ipAddress + " is stint"));
        }
    }

    /**
     * 获取IP地址的方法
     * @param request
     * @return
     */
    private String getUserIpAddr(HttpServletRequest request) {
        //获取经过代理的客户端的IP地址; 排除了request.getRemoteAddr() 方法 在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了
    	if (request.getHeader("x-forwarded-for") == null) {
    		return request.getRemoteAddr();
    	}
    	return request.getHeader("x-forwarded-for");

    }
    
    
}
