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

		log.debug("��ʼ������Ϣ.....");
		
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
			log.debug("pos client "+pos+" ��ʱ0.5����ѹر�.....");
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

				byte[] head = new byte[2];// ����ͷ 'M' 'Z'
				in.readFully(head);

				byte[] lengthBytes = new byte[2];// ����
				in.readFully(lengthBytes);
				int length = byte2short(lengthBytes);
				length = length - 255;
				
				byte[] contentBytes = new byte[length];// ����
				in.readFully(contentBytes);

				processResult(new String(head), contentBytes);
				return true;
				
			}
		} catch (Throwable e) {

			log.error("��������ʱ���ִ��� " + e, e);
		}
		return false;
	}

	public static void addShort2Bytes(int n,List<Byte> bytes){
		bytes.add((byte) (n >> 8));
		bytes.add((byte) n);
	}
	
	private void sendCmd(String cmdBody) throws IOException{
		
		List<Byte> bytes = new ArrayList<Byte>();
		//4D 5A --'M' 'Z' ����ͷ 
		bytes.add((byte)0x4D);	
		bytes.add((byte)0x5A);
	
		addShort2Bytes( cmdBody.getBytes().length+255, bytes );//�������ݳ���
		
		byte[] bs = cmdBody.getBytes();  //��������
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
		
		System.out.println("#################Server �յ�["+socket.toString()+"]����������["+cmdContent+"]###################");
		
		if(cmdContent.startsWith("0010")){//��ֵ����
			processCharge(cmdContent);
		}
		if(cmdContent.startsWith("0050")){//�û����Ʋ�ѯ
			queryUserName(cmdContent);
		}
	}
	
	void queryUserName(String cmdBody){
//		sendCmd("0051|0|�췲");
//		groupno	���ű��
//		shopno	�ŵ���
//		Salesman	��Ա���
//		PosNo	���������
//		phoneNo	��ѯ���ֻ�����
		String[] cmds = StringUtils.split(cmdBody, "|");
		String phoneNo=cmds[5];

//		��ͨ�Ŷ���:130 131 132 155 156 185 186 145(����)
//		���źŶ���:133��153��180��181��189  
		
		String suguoResult = "0051|1|��ѯʧ��,�û����Ǳ��غ���";
		if(isTel(phoneNo)){
			suguoResult = "0051|2|�ŵ���(���:100Ԫ)";
		}
		
		if(isUnicom(phoneNo)){
			suguoResult = "0051|0|����ͨ(���:150Ԫ)";
		}
		
		
		try {
			sendCmd(suguoResult);
			log.debug("���չ�pos�� ["+socket.toString()+"]�ظ�["+suguoResult+"]��ѯ����ɹ�");
		} catch (Exception e) {
			log.error("���չ��ظ���ѯ���ʱ����ʱ����"+e,e);
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
			billNo = StringUtils.rightPad(billNo, 20, "0");//���СƱ�Ų���20λ������СƱ��ǰ�油0,���͸���ͨʱ��������20λ������
		}

		

		String suguoResult="1|��ֵʧ��";//Ĭ��Ϊʧ��
		String phoneNo=cmds[6];

//		��ͨ�Ŷ���:130 131 132 155 156 185 186 145(����)
//		���źŶ���:133��153��180��181��189  

		if(phoneNo.startsWith("133")==true || phoneNo.startsWith("153")==true ||phoneNo.startsWith("180")==true ||
				phoneNo.startsWith("181")==true ||phoneNo.startsWith("189")==true||
				phoneNo.startsWith("025")){
			suguoResult = "2|���ų�ֵ�ɹ�";
		}
		
		if(isUnicom(phoneNo)){
			suguoResult = "0|��ͨ��ֵ�ɹ�";
		}


		try {
			sendCmd("0011|"+suguoResult);
		
			log.debug("���չ�pos�� ["+socket.toString()+"]�ظ�["+suguoResult+"]��ֵ����ɹ�");
		} catch (Exception e) {
		
			log.error("���չ��ظ���ֵ���ʱ����ʱ����"+e,e);
		}finally{
			
		}
		
		
	}

}
