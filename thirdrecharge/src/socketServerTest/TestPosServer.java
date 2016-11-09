package socketServerTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TestPosServer{
	
	public String hostName="localhost";
	public Integer port=1122;
	
	private String unicomHostName="211.0.0.195";
	private Integer unicomPort=5555;
	
	private String agentCode="34b06q8";
	


	public String getHostName() {
		return hostName;
	}



	public Integer getPort() {
		return port;
	}


	ServerSocket server;
	public void afterPropertiesSet() throws Exception {
	
		server = new ServerSocket();
		InetSocketAddress addr = new InetSocketAddress(hostName,port);
		server.bind(addr);//l0.19.116.125
		
		System.out.println("Test Pos Server["+addr.getHostName()+"|"+ addr.getAddress().getHostAddress()+":"+port+"] 已启动......................");
		
		Thread t = new Thread(){
			public void run(){
				try {
					starServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestPosServer server = new TestPosServer();
		if(args!=null && args.length==2){
			server.hostName = args[0];
			server.port=Integer.valueOf(args[1]);
		}
		
		try {
			
			server.afterPropertiesSet();
			server.starServer();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void starServer() throws IOException {
	
		
		
		while (true) {
			Socket socket = server.accept();
			
			System.out.println("已有客户端在连接"+socket.getInetAddress().toString()+ ":"+socket.getPort());
			
			TestPosServerThread st = new TestPosServerThread(socket,this.unicomHostName,this.unicomPort,this.agentCode);
			new Thread(st).start();
		}
	}

}
