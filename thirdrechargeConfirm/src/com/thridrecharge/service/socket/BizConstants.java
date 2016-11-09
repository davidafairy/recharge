package com.thridrecharge.service.socket;

/**
 * ���ඨ��һЩ����
 */
public interface BizConstants {

	String CMD_RESULT = "result";

	String CMD_MSG = "Message";

	String CMD_RECORD = "record";

	String RESULT_SUCCESS = "1";

	String RESULT_FAIL = "0";
	
	public interface ACCOUNT{
		public static final int SUGUO = 1;
		public static final int UNICOM = 2;
	}
	
	public interface RECHARGE_RECORD{
		public static final int RECHARGE = 1;  //��ֵ��ָ����ͨ���չ��ʻ���ֵ
		public static final int PAYMENT = 2;   //�۷ѣ���ָ�����ʺ� ����ͨ���չ��ʻ���۵���Ӧ��Ǯ
	}
	
	/**
	 *�ʻ���ֵ��¼
	 */
	public interface ACCOUNT_RECORD{
		
		/**
		 * ϵͳ��ֵ��ָ����Ա��ǰ̨Ϊ�ʺų�ֵ
		 */
		public static final int CHARGE = 1;
		
		/**
		 * ϵͳ�Զ����ݶ����ļ����пۿ����
		 */
		public static final int PAYMENT = 2;
		
		/**
		 * �ֹ�����
		 */
		public static final int ADJUSTMENT=3;
	}

	public interface RECORD {
		/**
		 * ��¼״̬,��ֵ��¼��������ɾ��������ʹ���߼�ɾ����ʽ
		 */
		public interface STATUS {
			/**
			 * 1.����
			 */
			public static final int NORMAL = 1;
			/**
			 * 2.�߼�ɾ��
			 */
			public static final int LOGIC_DELETED = 2;
		}

	}

	/**
	 * ���׼�¼
	 * 
	 */
	public interface DEAL_RECORD {
		/**
		 * ��������
		 * Create_type=2 ��˵��������׼�¼�Ƕ���ʱ�����ʲ�ƽʱ���ɶ������񴴽��ģ�
		 * Ĭ�϶���1����ʾ�ɳ�ֵϵͳ������
		 * 
		 */
		public interface CREATE_TYPE{
			public static final int SYSTEM_CREATE = 1;
			
			public static final int TASK_CREATE = 2;
		}
		
		/**
		 * ��ֵ����
		 * "1"=��ͨ��ֵ�������Ͼ����صĺ��룬ֱ�ӵ�����ͨ��ֵ�ӿھ��ܳ�ֵ
		 * "2"=��ֵ����ֵ,��ֵ����ֵ һ���ݲ�ʵ�� ����Ҫ�������ֻ����룬�ó�ֵ����ʽ���г�ֵ
		 * 3=���ų�ֵ
		 */
		public interface DEAL_TYPE{
			public static final int UNICOM_CHARGE = 1;
			
			public static final int CARD_CHARGE = 2;
			
			public static final int TEL_CHARGE=3;
		}
		
		
		/**
		 * ״̬
		 * 
		 */
		public interface TYPE_LENGTH {
			/**
			 * 1.ֱ��
			 */
			public static final int DIRECT_COUNT = 20;
			/**
			 * 2.����
			 */
			public static final int CHARGE_COUNT = 12;
		}

		/**
		 * ״̬
		 * 
		 */
		public interface STATUS {
			/**
			 * 1.��������
			 */
			public static final int RECEIVE_REQUEST = 1;
			/**
			 * 2.�ѳ���
			 */
			public static final int DISCHARGE = 2;
			/**
			 * 3.��ͨ��ֵʧ��
			 */
			public static final int UNICOM_RECHARGE_FAIL = 3;
			/**
			 * 4.��ͨ��ֵ�ɹ�
			 */
			public static final int UNICOM_RECHARGE_SUCCESS = 4;
			/**
			 * 5.�չ���ֵʧ��
			 */
			public static final int SUGUO_RECHARGE_FAIL = 5;

			/**
			 * 6.�չ���ֵ�ɹ�
			 */
			public static final int SUGUO_RECHARGE_SUCCESS = 6;

		}
	}

	/**
	 * ���˽��
	 * 
	 */
	public interface CHECKED_BILL {

		/**
		 * ״̬
		 * 
		 */
		public interface STATUS {
			/**
			 * 1.��ȫƥ��
			 */
			public static final int FULL_MATCH = 1;
			/**
			 * 2.����ƥ��
			 */
			public static final int PART_MATH = 2;

		}
	}

	public interface BILL {
		/**
		 * �չ�����״̬
		 */
		public interface sgCheckedStatus {
			/**
			 * 1.������
			 */
			public static final int DOING = 1;
			/**
			 * 2.�������
			 */
			public static final int SUCCESS = 2;
			/**
			 * 3.����ʧ��
			 */
			public static final int FAIL = 3;
		}

		/**
		 * �ϴ���ͨ״̬
		 * 
		 */
		public interface ltStatus {
			/**
			 * 1.������
			 */
			public static final int DONE = 1;
			/**
			 * 2.�ϴ��ɹ�
			 */
			public static final int UPLOAD_SUCCESS = 2;
			/**
			 * 3.�ϴ�ʧ��
			 */
			public static final int UPLOAD_FAIL = 3;
		}
	}
}