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

	//���������ע�������,��Ҳ���Դ�properties,xml�ļ���ȥ��ȡ,Ҳ���Դ����ݿ���ȥ��ȡ;
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
        //ָ��CXF��ȡ�ͻ��˵�HttpServletRequest : http-request��
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String ipAddress="";
        boolean flag = false;
        if (null != request) {
            ipAddress = getUserIpAddr(request); // ȡ�ͻ���IP��ַ
            logger.info("����ͻ��˵�IP��ַ:" + ipAddress);
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
     * ��ȡIP��ַ�ķ���
     * @param request
     * @return
     */
    private String getUserIpAddr(HttpServletRequest request) {
        //��ȡ��������Ŀͻ��˵�IP��ַ; �ų���request.getRemoteAddr() ���� ��ͨ����Apache,Squid�ȷ����������Ͳ��ܻ�ȡ���ͻ��˵���ʵIP��ַ��
    	if (request.getHeader("x-forwarded-for") == null) {
    		return request.getRemoteAddr();
    	}
    	return request.getHeader("x-forwarded-for");

    }
    
    
}
