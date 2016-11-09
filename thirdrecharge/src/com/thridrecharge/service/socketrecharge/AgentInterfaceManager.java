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
		
		log.info("################����Socket�ӿڳ�ֵ###############");
		log.info("########��  ��  �̣���"+agentId+"��");
		log.info("########��  ��  �ţ���"+mobile+"��");
		log.info("########��  ˮ  �ţ���"+flowNo+"��");
		log.info("########��  ��  �ţ���"+groupno+"��");
		log.info("########��      ���"+money+"��");
		log.info("########################################");
		boolean isRechargeRate = checkRechargeRate(mobile);
		if (!isRechargeRate) {
			log.info("########��ֵ�������ֵʧ�ܣ��ú������������ǰٷ�֮�ٳɹ���");
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
		}
		
		//���ݿ�ۿ�,���ʧ�ܻ�ֱ�����쳣��ȥ
		//rechargeDao.deducting(agentId, money,orderHis); 
		
		//����Socket��ֵ
		try {
			PosClient client  = new PosClient();
			client.connect("172.168.1.50", "6677");
			//client.connect("127.0.0.1", "1122");
			
			List<Byte> bytes = new ArrayList<Byte>();
			//4D 5A --'M' 'Z' ����ͷ 
			bytes.add((byte)0x4D);	
			bytes.add((byte)0x5A);
			
			String moneyStr = String.valueOf(money);
			
			//СƱ���Զ�����20λ�����λ������20λ�����ں��油��0��
			int flowNoLength = flowNo.length();
			if (flowNoLength < 20) {
				for (int i = flowNoLength+1;i<=20;i++) {
					flowNo = flowNo + "0";
				}
			}
			//�����Ƿ��͵���Ϣ���ݣ���|�ָ�ĵ��ﶨ���˳��
			String cmdBody = "0010|"+flowNo+"|"+groupno+"|"+getShopNo()+"|200|100|"+mobile+"|"+moneyStr+"|1";
			log.info("########��ʼ���ýӿڳ�ֵ********");
			log.info("########cmdBody����"+cmdBody+"��#############");
			//String cmdBody = "0050|400|100|300|200|18951652611";
			
			ByteUtils.addShort2Bytes( cmdBody.getBytes().length+255, bytes );//�������ݳ���
			
			byte[] bs = cmdBody.getBytes();  //��������
			for (int i = 0; i < bs.length; i++) {
				bytes.add( bs[i] );
			}
		
			//��ȡSocket���ý��
			Map resultMap = client.sendCmd(bytes);
			client.close();
			
			//����Socket���ý��Code
			String resultStr = (String)resultMap.get(BizConstants.CMD_RESULT);
			log.info("######�ӿڷ��أ�resultStr��"+resultStr+"��#############");
			try {
				if("0".equals(resultStr)) {
					log.info("######�ӿڷ��أ���ֵ�����·���ʱ#############");
					throw new RechargeException(ErrorCode.TIMEOUT);
				}
				
				String[] str = StringUtils.split(resultStr, "|");
				int resultCode = Integer.valueOf(str[1]);
				if (resultCode > 0) {
					//resultCode����0˵�����쳣
					if (StringUtils.contains(resultStr, "��ֵ�����·���ʱ")) {
						log.info("######�ӿڷ��أ���ֵ�����·���ʱ#############");
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
			throw new RechargeException(ErrorCode.SERVERUNAVAILABLE); //Socket������
		}
		
	}
	
	//��ȡһ��������ŵ���
	private String getShopNo() {
		try{
			Random r = new Random();
			int i = r.nextInt(600);
			return ShopNo.shopNo[i];
		} catch(Exception e) {
			return "300";
		}
	}
	
	//�������ݿ������õĳɹ����ʣ�����жϸú����ܷ��ֵ
	public boolean checkRechargeRate(String phoneNo) {
		try{
			
			String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(phoneNo);
			int rate = agentInterfaceDao.getAreaSuccessRate(areaCodeDesc);
			
			//�������������10�Ժ����´�0��ʼ����
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
