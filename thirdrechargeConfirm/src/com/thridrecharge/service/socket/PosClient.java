package com.thridrecharge.service.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * �����Ǻ���ͨ��ֵ�ӿڵ�ʵ��
 */
public  class PosClient {
	static Log log = LogFactory.getLog(PosClient.class);
	
	Socket socket = null;

	OutputStream socketOut;

	InputStream inputStream;

	ArrayBlockingQueue<Map> cmdResultQueue = new ArrayBlockingQueue<Map>(1);


	PosClientThrad readThread;
	
	Long timeOut = 120l;
	
	/**
	 * ���һ�η��������ʱ�䣬��Ҫ�����ж��û����ӳ�ʱ������û���ʱ��û���ٷ�������Ͱ����spa���ص�
	 */
	Long lastSendCmdTime = System.currentTimeMillis();

	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 1; i++) {
			sendTest();
			Thread.currentThread().sleep(500);
		}
	}
	public static void sendTest() throws Exception {
		PosClient client  = new PosClient();
		client.connect("127.0.0.1", "3344");
		
//		String flowNo="400";
//		String groupno="100";
//		String shopno="300";
//		String Salesman="200";
//		String PosNo="100";
//		String phoneNo="18651652611";
//		String money="100";
//		Integer type=1;
		
		
		List<Byte> bytes = new ArrayList<Byte>();
		//4D 5A --'M' 'Z' ����ͷ 
		bytes.add((byte)0x4D);	
		bytes.add((byte)0x5A);
		
		//�����Ƿ��͵���Ϣ���ݣ���|�ָ�ĵ��ﶨ���˳��
		//String cmdBody = "0010|400|100|300|200|100|18951652611|100|1";
		
		String cmdBody = "0050|400|100|300|200|18951652611";
		
		ByteUtils.addShort2Bytes( cmdBody.getBytes().length+255, bytes );//�������ݳ���
		
		byte[] bs = cmdBody.getBytes();  //��������
		for (int i = 0; i < bs.length; i++) {
			bytes.add( bs[i] );
		}
	
		client.sendCmd(bytes);
		
		client.close();
		
	}
	
	public void connect(String ip, String port) throws Exception {
		socket = new Socket(ip, Integer.valueOf(port));
	
		socketOut = socket.getOutputStream();
		inputStream = socket.getInputStream();
		

		readThread = new PosClientThrad(inputStream, cmdResultQueue);
		
		readThread.start();
	}
	
	public Map sendCmd(List<Byte> bytes) throws Exception {
		
		byte[] cmdBytes = new byte[bytes.size()];
		
		
		for (int i=0;i<bytes.size();i++) {
			cmdBytes[i] = bytes.get(i);
		}
		
		socketOut.write(cmdBytes);
		socketOut.flush();
		
		lastSendCmdTime = System.currentTimeMillis();
		
		Map map= cmdResultQueue.poll(timeOut, TimeUnit.SECONDS);
		if(map==null){
			map = new HashMap();
			map.put(BizConstants.CMD_RESULT, BizConstants.RESULT_FAIL);
			map.put(BizConstants.CMD_MSG, "�����·���ʱ��");
			
			log.error("�����·���ʱ ��ʱʱ��["+timeOut+"]��");
		}
		return map;
	}	

	
	
	/**
	 * �ص���ǰ������
	 */
	public void close() {

		try {
			if (readThread != null) {
				readThread.stopRead();
			}
			if (socketOut != null) {
				socketOut.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}

		} catch (Exception e) {
			log.error(e, e);
		}
	}

	

	public Long getLastSendCmdTime() {
		return lastSendCmdTime;
	}

}