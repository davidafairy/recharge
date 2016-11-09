package com.thridrecharge.service;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;

import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.socketrecharge.AgentInterfaceDao;

public class ServerPasswordCallback implements CallbackHandler {  
    Logger log = Logger.getLogger(ServerPasswordCallback.class);  
      
    @Autowired
    private AgentInterfaceDao agentInterfaceDao;
      
    @Override  
    public void handle(Callback[] callbacks) throws IOException,  
            UnsupportedCallbackException {  
        log.info("handler passwordcallback method....");  
        WSPasswordCallback wpc = (WSPasswordCallback) callbacks[0];  
        
        Agent agent = AgentMemory.getAgentMemory().getAgent(wpc.getIdentifier());
        if (null == agent) {
        	agent = agentInterfaceDao.findAgentByName(wpc.getIdentifier());
        	if (agent != null) {
        		log.info("agent info:agentName="+agent.getLoginName()+";pwd="+agent.getPassword());  
        		AgentMemory.getAgentMemory().addAgent(agent);
        	} else {
        		throw new SecurityException("No Permission!");
        	}
              
        }  
        /* 
         * �˴��ر�ע��:: 
         * WSPasswordCallback ��passwordType���Ժ�password ���Զ�Ϊnull�� 
         * ��ֻ�ܻ���û�����identifier���� 
         * һ��������߼���ʹ������û��������ݿ��в�ѯ�����룬 
         * Ȼ�������õ�password ���ԣ�WSS4J ���Զ��ȽϿͻ��˴�����ֵ�������õ����ֵ�� 
         * ����ܻ���Ϊʲô����CXF ���ѿͻ����ύ�����봫����������ServerPasswordCallbackHandler �бȽ��أ� 
         * ������Ϊ�ͻ����ύ������������SOAP ��Ϣ���Ѿ�������ΪMD5 ���ַ����� 
         * �������Ҫ�ڻص����������Ƚϣ���ô��һ��Ҫ���ľ��ǰѷ����׼���õ��������ΪMD5 �ַ����� 
         * ����MD5 �㷨������ͬ���Ҳ���в�����⣬�����Ĺ���CXF ��������ɲ��Ǹ����� 
         */  
        wpc.setPassword(agent.getPassword());//��������û���,�����ø��û�����ȷ����,��CXF��֤����  
        String username = wpc.getIdentifier();  
        String password = wpc.getPassword();  
        log.info("username: "+username + "    password: "+password);  
        log.info("User : "+wpc.getIdentifier()+ "  login!!!!!");  
    }
}
