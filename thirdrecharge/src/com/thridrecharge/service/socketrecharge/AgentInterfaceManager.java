package com.thridrecharge.service.socketrecharge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.entity.ShopNo;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.memory.AreaCodeMemory;
import com.thridrecharge.service.ordermanager.RechargeDao;
import com.thridrecharge.service.socket.BizConstants;
import com.thridrecharge.service.socket.ByteUtils;
import com.thridrecharge.service.socket.PosClient;

@Controller
public class AgentInterfaceManager {

	private Log log = LogFactory.getLog("recharge");
	
	private static int count = 0;
	
	@Autowired
	private AgentInterfaceDao agentInterfaceDao;
	
	@Autowired
	private RechargeDao rechargeDao;
	
	
	public void deducting(int agentId,String flowNo,String groupno,String mobile,long money,OrderHis orderHis) throws RechargeException{
		
		log.info("################进入Socket接口充值###############");
		log.info("########代  理  商：【"+agentId+"】");
		log.info("########手  机  号：【"+mobile+"】");
		log.info("########流  水  号：【"+flowNo+"】");
		log.info("########集  团  号：【"+groupno+"】");
		log.info("########金      额：【"+money+"】");
		log.info("########################################");
		boolean isRechargeRate = checkRechargeRate(mobile);
		if (!isRechargeRate) {
			log.info("########充值结果：充值失败（该号码所在区域不是百分之百成功）");
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		//数据库扣款,如果失败会直接抛异常出去
		//rechargeDao.deducting(agentId, money,orderHis); 
		
		//调用Socket充值
		try {
			PosClient client  = new PosClient();
			client.connect("172.168.1.50", "6677");
			//client.connect("127.0.0.1", "1122");
			
			List<Byte> bytes = new ArrayList<Byte>();
			//4D 5A --'M' 'Z' 命令头 
			bytes.add((byte)0x4D);	
			bytes.add((byte)0x5A);
			
			String moneyStr = String.valueOf(money);
			
			//小票号自动补齐20位，如果位数不足20位，则在后面补“0”
			int flowNoLength = flowNo.length();
			if (flowNoLength < 20) {
				for (int i = flowNoLength+1;i<=20;i++) {
					flowNo = flowNo + "0";
				}
			}
			//以下是发送的消息内容，用|分割按文档里定义的顺序发
			String cmdBody = "0010|"+flowNo+"|"+groupno+"|"+getShopNo()+"|200|100|"+mobile+"|"+moneyStr+"|1";
			log.info("########开始调用接口充值********");
			log.info("########cmdBody：【"+cmdBody+"】#############");
			//String cmdBody = "0050|400|100|300|200|18951652611";
			
			ByteUtils.addShort2Bytes( cmdBody.getBytes().length+255, bytes );//命令内容长度
			
			byte[] bs = cmdBody.getBytes();  //命令内容
			for (int i = 0; i < bs.length; i++) {
				bytes.add( bs[i] );
			}
		
			//获取Socket调用结果
			Map resultMap = client.sendCmd(bytes);
			client.close();
			
			//返回Socket调用结果Code
			String resultStr = (String)resultMap.get(BizConstants.CMD_RESULT);
			log.info("######接口返回：resultStr【"+resultStr+"】#############");
			try {
				if("0".equals(resultStr)) {
					log.info("######接口返回：充值命令下发超时#############");
					throw new RechargeException(ErrorCode.TIMEOUT);
				}
				
				String[] str = StringUtils.split(resultStr, "|");
				int resultCode = Integer.valueOf(str[1]);
				if (resultCode > 0) {
					//resultCode大于0说明有异常
					if (StringUtils.contains(resultStr, "充值命令下发超时")) {
						log.info("######接口返回：充值命令下发超时#############");
						throw new RechargeException(ErrorCode.TIMEOUT);
					} else {
						throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
					}
					
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				if (e instanceof RechargeException) {
					throw e;
				}
				throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			if (e instanceof RechargeException) {
				throw (RechargeException)e;
			}
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE); //Socket不可用
		}
		
	}
	
	//获取一个随机的门店编号
	private String getShopNo() {
		try{
			Random r = new Random();
			int i = r.nextInt(600);
			return ShopNo.shopNo[i];
		} catch(Exception e) {
			return "300";
		}
	}
	
	//根据数据库中配置的成功概率，随机判断该号码能否充值
	public boolean checkRechargeRate(String phoneNo) {
		try{
			
			String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(phoneNo);
			int rate = agentInterfaceDao.getAreaSuccessRate(areaCodeDesc);
			
			//如果计数器到达10以后，重新从0开始技术
			if (count == 10) {
				count = 0;
			}
			count++;
			if (count <= rate) {
				return true;
			}
			
			return false;
		} catch(Exception e) {
			return false;
		}
	}
}
