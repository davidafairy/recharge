package com.thridrecharge.service.enums;

public enum RechargeStrategy {

	SOCKET_JUST(1,"接口充值"),SOCKET_FIRST(2,"接口优先"),CARD_JUST(3,"卡密充值"),CARD_FIRST(4,"卡密优先");
	
	private int strategy;
	private String strategyDesc;
	
	private RechargeStrategy(int strategy,String strategyDesc) {
		this.strategy = strategy;
		this.strategyDesc = strategyDesc;
	}

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public String getStrategyDesc() {
		return strategyDesc;
	}

	public void setStrategyDesc(String strategyDesc) {
		this.strategyDesc = strategyDesc;
	}
	
	public static String getStrategyDesc(int strategy) {
		switch(strategy) {
			case 1: return SOCKET_JUST.getStrategyDesc();
			case 2: return SOCKET_FIRST.getStrategyDesc();
			case 3: return CARD_JUST.getStrategyDesc();
			case 4: return CARD_FIRST.getStrategyDesc();
			default: return "没有匹配到对应充值策略";
		}
	}
}
