package com.thridrecharge.service.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PosClientThrad extends Thread {

	static Log log = LogFactory.getLog(PosClientThrad.class);

	DataInputStream in;

	ArrayBlockingQueue<Map> cmdResultQueue;

	/**
	 * ���һ�η�������������ʱ��
	 */
	Integer lastPeerTime = 0;


	/**
	 * ��Ϊһ�β�ѯ���ܻ�ֶ�η��أ����Բ�ѯʱ��Ҫ��cookie��ȥ�� ���ݶԷ����ز�ѯ��ɵ�����󣬲Űѷ��ؽ������ȥ��
	 */
	Map<Integer, List> recordMap = new HashMap<Integer, List>();

	volatile boolean runing = true;

	/**
	 * ͣ������߳�
	 */
	public void stopRead() {
		runing = false;
	}

	public PosClientThrad(InputStream inputStream,
			ArrayBlockingQueue<Map> cmdResultQueue) {

		in = new DataInputStream(new BufferedInputStream(inputStream));
		this.cmdResultQueue = cmdResultQueue;
	}

	public void run() {
		while (runing) {
			doRun();
		}
	}

	@SuppressWarnings("unchecked")
	private void doRun() {
		try {
			if (in.available() > 0) {

				byte[] head = new byte[2];// ����ͷ 'M' 'Z'
				in.readFully(head);

				byte[] lengthBytes = new byte[2];// ����
				in.readFully(lengthBytes);
				int length = ByteUtils.byte2short(lengthBytes)-255;

				byte[] contentBytes = new byte[length];// ����
				in.readFully(contentBytes);

				processResult(new String(head), contentBytes);
			} else {
				try {
					Thread.currentThread().sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (Throwable e) {

			HashMap map = new HashMap();
			map.put(BizConstants.CMD_RESULT, BizConstants.RESULT_FAIL);
			map.put(BizConstants.CMD_MSG, "��������ʱ���ִ��� " + e.toString());
			try {
				cmdResultQueue.put(map);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			log.error("��������ʱ���ִ��� " + e, e);
		}

	}

	private void processResult(String head, byte[] contentBytes)
			throws Exception {

		String result = new String(contentBytes);
		log.debug("#################Client �յ�����" + result
				+ "###################");

		Map resultMap = new HashMap();
		resultMap.put(BizConstants.CMD_RESULT, result);
		cmdResultQueue.put(resultMap);

	}
}
