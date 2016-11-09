package socketServerTest;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TestPosServerThread implements Runnable {

	static Log log = LogFactory.getLog(TestPosServerThread.class);

	Socket socket;

	OutputStream socketOut;

	InputStream inputStream;


	volatile boolean runing = true;
	
	String unicomHostName;
	Integer unicomPort;
	String agentCode;
	

	public TestPosServerThread(Socket socket,String unicomHostName, Integer unicomPort, String agentCode) {
		this.socket = socket;
		this.unicomHostName = unicomHostName;
		this.unicomPort=unicomPort;
		this.agentCode=agentCode;
	}

	public void run() {

		try {
			socketOut = socket.getOutputStream();
			inputStream = socket.getInputStream();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		DataInputStream in = new DataInputStream(new BufferedInputStream(inputStream));

		log.debug("开始收听消息.....");
		
		while (runing) {
			boolean relValue = receive(in);
			if(relValue == true){
				break;
			}
		}
		
		
		try {
			Thread.currentThread().sleep(500);
			String pos = socket.toString();
			socket.close();
			log.debug("pos client "+pos+" 延时0.5秒后已关闭.....");
		} catch (Exception e) {
			log.error(e,e);
		}
		
	}

	public static int byte2short(byte b[]){
		return b[ 1] & 0xff | (b[0] & 0xff) << 8;
		
	}
	
	boolean receive(DataInputStream in) {
		try {
			if (in.available() > 0) {

				byte[] head = new byte[2];// 命令头 'M' 'Z'
				in.readFully(head);

				byte[] lengthBytes = new byte[2];// 包长
				in.readFully(lengthBytes);
				int length = byte2short(lengthBytes);
				length = length - 255;
				
				byte[] contentBytes = new byte[length];// 内容
				in.readFully(contentBytes);

				processResult(new String(head), contentBytes);
				return true;
				
			}
		} catch (Throwable e) {

			log.error("接受命令时出现错误！ " + e, e);
		}
		return false;
	}

	public static void addShort2Bytes(int n,List<Byte> bytes){
		bytes.add((byte) (n >> 8));
		bytes.add((byte) n);
	}
	
	private void sendCmd(String cmdBody) throws IOException{
		
		List<Byte> bytes = new ArrayList<Byte>();
		//4D 5A --'M' 'Z' 命令头 
		bytes.add((byte)0x4D);	
		bytes.add((byte)0x5A);
	
		addShort2Bytes( cmdBody.getBytes().length+255, bytes );//命令内容长度
		
		byte[] bs = cmdBody.getBytes();  //命令内容
		for (int i = 0; i < bs.length; i++) {
			bytes.add( bs[i] );
		}
		
		byte[] cmdBytes = new byte[bytes.size()];
		
		for (int i=0;i<bytes.size();i++) {
			cmdBytes[i] = bytes.get(i);
		}
		
		socketOut.write(cmdBytes);
		socketOut.flush();
	}
	
	private void processResult( String head, byte[] contentBytes)throws Exception {
		String cmdContent = new String(contentBytes);
		
		System.out.println("#################Server 收到["+socket.toString()+"]发来的命令["+cmdContent+"]###################");
		
		if(cmdContent.startsWith("0010")){//充值命令
			processCharge(cmdContent);
		}
		if(cmdContent.startsWith("0050")){//用户名称查询
			queryUserName(cmdContent);
		}
	}
	
	void queryUserName(String cmdBody){
//		sendCmd("0051|0|朱凡");
//		groupno	集团编号
//		shopno	门店编号
//		Salesman	柜员编号
//		PosNo	收银机编号
//		phoneNo	查询的手机号码
		String[] cmds = StringUtils.split(cmdBody, "|");
		String phoneNo=cmds[5];

//		联通号段有:130 131 132 155 156 185 186 145(网卡)
//		电信号段有:133、153、180、181、189  
		
		String suguoResult = "0051|1|查询失败,用户不是本地号码";
		if(isTel(phoneNo)){
			suguoResult = "0051|2|张电信(余额:100元)";
		}
		
		if(isUnicom(phoneNo)){
			suguoResult = "0051|0|张联通(余额:150元)";
		}
		
		
		try {
			sendCmd(suguoResult);
			log.debug("向苏果pos机 ["+socket.toString()+"]回复["+suguoResult+"]查询命令成功");
		} catch (Exception e) {
			log.error("向苏果回复查询结果时命令时出错"+e,e);
		}
	}

	private boolean isUnicom(String phoneNo) {
		return phoneNo.startsWith("130")==true || phoneNo.startsWith("131")==true ||phoneNo.startsWith("132")==true ||
				phoneNo.startsWith("155")==true ||phoneNo.startsWith("156")==true||
				phoneNo.startsWith("185")==true ||phoneNo.startsWith("186")==true||
				phoneNo.startsWith("145")==true ||phoneNo.startsWith("02566")==true;
	}

	private boolean isTel(String phoneNo) {
		return phoneNo.startsWith("133")==true || phoneNo.startsWith("153")==true ||phoneNo.startsWith("180")==true ||
				phoneNo.startsWith("181")==true ||phoneNo.startsWith("189")==true||
				phoneNo.startsWith("025");
	}
	
	void processCharge(String cmdBody){

		String[] cmds = StringUtils.split(cmdBody, "|");


		String billNo=cmds[1].trim();
		if(billNo.length()<20){
			billNo = StringUtils.rightPad(billNo, 20, "0");//如果小票号不够20位，就在小票号前面补0,发送给联通时，必需是20位的数据
		}

		

		String suguoResult="1|充值失败";//默认为失败
		String phoneNo=cmds[6];

//		联通号段有:130 131 132 155 156 185 186 145(网卡)
//		电信号段有:133、153、180、181、189  

		if(phoneNo.startsWith("133")==true || phoneNo.startsWith("153")==true ||phoneNo.startsWith("180")==true ||
				phoneNo.startsWith("181")==true ||phoneNo.startsWith("189")==true||
				phoneNo.startsWith("025")){
			suguoResult = "2|电信充值成功";
		}
		
		if(isUnicom(phoneNo)){
			suguoResult = "0|联通充值成功";
		}


		try {
			sendCmd("0011|"+suguoResult);
		
			log.debug("向苏果pos机 ["+socket.toString()+"]回复["+suguoResult+"]充值命令成功");
		} catch (Exception e) {
		
			log.error("向苏果回复充值结果时命令时出错"+e,e);
		}finally{
			
		}
		
		
	}

}
