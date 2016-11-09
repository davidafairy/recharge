package com.thridrecharge.service.enums;

/**
 * 		0���ɹ�
 * 		1��СƱ�Ż��Ա��ų���
 * 		2���������
 * 		3����Ϣ����ʧ��
 * 		4����Ϣ����ʧ��
 * 		5��ǰ��ϵͳ����ʧ��
 * 		6����ֵʧ��
 * 		7: �������û����벻��ȷ
 * 		8: ����
 * 		9��������������
 * 		10����������С
 * 		11���ظ�����
 * 		12����ֵ����ֵ�쳣
 * 		13����ֵ��ʱ
 * @author mac
 *
 */
public enum ErrorCode {

	SUCCESS(0),FLOWNOERROR(1),NETWORKERR(2),MSGSENDFAIL(3),MSGRECEIVEFAIL(4),PORTALFAIL(5),RECHARGEFAIL(6),LOGINFAIL(7),BALANCE(8),SERVERUNAVAILABLE(9),MONEYFAIL(10),REPEAT(11),RECHARGECARD(12),TIMEOUT(13);
	
	private int code;
	
	private ErrorCode(int code) {
		this.code = code;
	}
	
	public int getErrorCode() {
		return code;
	}
	
	public static String getDesc(int errorCode) {
		switch(errorCode) {
			case 0:return "�ɹ�";
			case 1:return "СƱ�Ż��Ա��ų���";
			case 2:return "�������";
			case 3:return "��Ϣ����ʧ��";
			case 4:return "��Ϣ����ʧ��";
			case 5:return "ǰ��ϵͳ����ʧ��";
			case 6:return "��ֵʧ��";
			case 7:return "�������û����벻��ȷ";
			case 8:return "����";
			case 9:return "������������";
			case 10:return "��������С";
			case 11:return "�ظ�����";
			case 12:return "��ֵ����ֵ�쳣";
			case 13:return "��ֵ��ʱ";
			default : return "δ֪�쳣";
		}
	}
}
