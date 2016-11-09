package com.thridrecharge.service.socket;

import java.util.List;

public class ByteUtils {
	
	
	
	public static void addString2Bytes(String cmd,List<Byte> bytes){
		byte[] bs = cmd.getBytes();
		for (int i = 0; i < bs.length; i++) {
			bytes.add( bs[i] );
		}
	}
	
	public static void addInteger2Bytes(int n,List<Byte> bytes){
		
			bytes.add((byte) (n >> 24));
			bytes.add((byte) (n >> 16));
			bytes.add((byte) (n >> 8));
			bytes.add((byte) n);
	
	}
	
	public static void addShort2Bytes(int n,List<Byte> bytes){
		bytes.add((byte) (n >> 8));
		bytes.add((byte) n);
	}
	
	public static String byteToStr(byte[] b2) {
		String s = "";
		for (int i = 0; i < b2.length; i++) {
			int ib = b2[i] & 0xFF;
			String s1 = Integer.toHexString(ib);
			if(s1.length()==1){
				s = s+"0"+s1;		
			}else{
				s = s+s1;	
			}
		}
		return s;
	}
	
	public static void main(String[] args){
		byte[] b2 = new byte[2];
		b2[0]=(byte) (345 >> 8);
		
		b2[1]=(byte) 345;
		System.out.println(byteToStr(b2));
	}

	public static Integer byteToInt(byte[] b2) {
		String s = "";
		for (int i = 0; i < b2.length; i++) {
			String s1 = Integer.toHexString((int) b2[i]);
			if (s1.length() == 1) {
				s = s + "0" + s1;
			} else {
				s = s + s1;
			}

		}
		return Integer.parseInt(s, 16);
	}

	/**
	 * byte to int
	 * 
	 * @param b
	 *            寰�杞���㈢��瀛������扮��
	 * @param offset
	 *            ���绉婚��锛�瀛������扮��涓�寮�濮�杞���㈢��浣�缃�
	 * @return
	 */
	public static int byte2int(byte b[], int offset) {
		return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8
				| (b[offset + 1] & 0xff) << 16 | (b[offset] & 0xff) << 24;
	}

	/**
	 * int to byte
	 * 
	 * @param n寰�杞���㈢����村舰������
	 * @param buf
	 *            杞���㈠�����������瀛������扮��
	 * @param offset
	 *            ���绉婚��锛�瀛������扮��涓�寮�濮�瀛���剧��浣�缃�
	 */
	public static void int2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) (n >> 24);
		buf[offset + 1] = (byte) (n >> 16);
		buf[offset + 2] = (byte) (n >> 8);
		buf[offset + 3] = (byte) n;
	}

	public static int byte2short(byte b[]){
		return b[ 1] & 0xff | (b[0] & 0xff) << 8;
		
	}
	/**
	 * @returntype void
	 * @param n
	 *            寰�杞���㈢��short������
	 * @param buf
	 *            杞���㈠��瀛���剧��byte��扮��
	 * @param offset���绉婚��锛�瀛������扮��涓�寮�濮�瀛���剧��浣�缃�
	 */
	public static void short2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) (n >> 8);
		buf[offset + 1] = (byte) n;
	}

	/**
	 * 
	 * @param buf
	 * @return
	 */
	public static String byte2Hex(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (byte b : buf) {
			if (b == 0) {
				sb.append("00");
			} else if (b == -1) {
				sb.append("FF");
			} else {
				String str = Integer.toHexString(b).toUpperCase();
				// sb.append(a);
				if (str.length() == 8) {
					str = str.substring(6, 8);
				} else if (str.length() < 2) {
					str = "0" + str;
				}
				sb.append(str);

			}
			sb.append(" ");
		}
		sb.append("}");
		return sb.toString();
	}

	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}

	/**
	 * convert signed one byte into a hexadecimal digit
	 * 
	 * @param b
	 *            byte
	 * @return convert result
	 */
	public static String byteToHex(byte b) {
		int i = b & 0xFF;
		String hex = Integer.toHexString(i);
		if (hex.length() > 1) {
			return hex;
		}
		return "0" + hex;
	}

	/**
	 * convert signed 4 bytes into a 32-bit integer
	 * 
	 * @param buf
	 *            bytes buffer
	 * @param pos
	 *            beginning <code>byte</code>> for converting
	 * @return convert result
	 */
	public static long unsigned4BytesToInt(byte[] buf, int pos) {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		int index = pos;
		firstByte = (0x000000FF & ((int) buf[index]));
		secondByte = (0x000000FF & ((int) buf[index + 1]));
		thirdByte = (0x000000FF & ((int) buf[index + 2]));
		fourthByte = (0x000000FF & ((int) buf[index + 3]));
		index = index + 4;
		return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
	}

	public static long bytes2long(byte[] b) {

		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
		}
		return res;
	}

	public static byte[] long2bytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - i * 8));
		}
		return b;
	}

	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 0] & 0xff) << 56)
				| (((long) bb[index + 1] & 0xff) << 48)
				| (((long) bb[index + 2] & 0xff) << 40)
				| (((long) bb[index + 3] & 0xff) << 32)
				| (((long) bb[index + 4] & 0xff) << 24)
				| (((long) bb[index + 5] & 0xff) << 16)
				| (((long) bb[index + 6] & 0xff) << 8) 
				| (((long) bb[index + 7] & 0xff) << 0));
	}

	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 0] = (byte) (x >> 56);
		bb[index + 1] = (byte) (x >> 48);
		bb[index + 2] = (byte) (x >> 40);
		bb[index + 3] = (byte) (x >> 32);
		bb[index + 4] = (byte) (x >> 24);
		bb[index + 5] = (byte) (x >> 16);
		bb[index + 6] = (byte) (x >> 8);
		bb[index + 7] = (byte) (x >> 0);
	}

	public static void putShort(byte b[], short s, int index) {
		b[index] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	public static short getShort(byte[] b, int index) {
		return (short) (((b[index] << 8) | b[index + 1] & 0xff));
	}

}
