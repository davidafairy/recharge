package com.thridrecharge.service.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thridrecharge.service.entity.Agent;

public class AgentMemory {

	private static AgentMemory agentMemory = new AgentMemory();
	
	private Map<String,Agent> agentMap = new HashMap<String,Agent>();
	
	private Map<Integer,Agent> agentIdMap = new HashMap<Integer,Agent>();
	
	private List<String> allAgentIp = new ArrayList<String>();
	
	private AgentMemory() {
		
	}
	
	public static AgentMemory getAgentMemory() {
		return agentMemory;
	}
	
	public void addAgent(Agent agent) {
		agentMap.put(agent.getLoginName(), agent);
		agentIdMap.put(agent.getId(), agent);
		allAgentIp.add(agent.getIp());
	}
	
	public Agent getAgent(String loginName) {
		return agentMap.get(loginName);
	}
	
	public Agent getAgent(int agentId) {
		return agentIdMap.get(agentId);
	}
	
	/**
	 * 获取所有代理商IP
	 * @return
	 */
	public List<String> getAllIp() {
		return allAgentIp;
	}
}
