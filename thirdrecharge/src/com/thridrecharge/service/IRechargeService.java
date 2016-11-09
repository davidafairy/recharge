package com.thridrecharge.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface IRechargeService {

	public int recharge(@WebParam(name = "agentName") String agentName,@WebParam(name = "flowNo") String flowNo,@WebParam(name = "phoneNo") String phoneNo,@WebParam(name = "money") long money);
	
	public int searchRechargeResult(@WebParam(name = "flowNo") String flowNo);
}
