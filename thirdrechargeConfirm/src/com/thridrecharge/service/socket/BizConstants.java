package com.thridrecharge.service.socket;

/**
 * 此类定义一些常量
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
		public static final int RECHARGE = 1;  //充值，指向联通或苏果帐户充值
		public static final int PAYMENT = 2;   //扣费，是指出日帐后， 从联通或苏果帐户里扣掉相应的钱
	}
	
	/**
	 *帐户充值记录
	 */
	public interface ACCOUNT_RECORD{
		
		/**
		 * 系统充值，指操作员在前台为帐号充值
		 */
		public static final int CHARGE = 1;
		
		/**
		 * 系统自动根据对帐文件进行扣款操作
		 */
		public static final int PAYMENT = 2;
		
		/**
		 * 手工调帐
		 */
		public static final int ADJUSTMENT=3;
	}

	public interface RECORD {
		/**
		 * 记录状态,充值记录不能真正删除，所以使用逻辑删除方式
		 */
		public interface STATUS {
			/**
			 * 1.正常
			 */
			public static final int NORMAL = 1;
			/**
			 * 2.逻辑删除
			 */
			public static final int LOGIC_DELETED = 2;
		}

	}

	/**
	 * 交易记录
	 * 
	 */
	public interface DEAL_RECORD {
		/**
		 * 创建类型
		 * Create_type=2 （说明这个交易记录是对帐时发现帐不平时，由对帐任务创建的）
		 * 默认都是1，表示由充值系统创建的
		 * 
		 */
		public interface CREATE_TYPE{
			public static final int SYSTEM_CREATE = 1;
			
			public static final int TASK_CREATE = 2;
		}
		
		/**
		 * 充值类型
		 * "1"=联通充值，对于南京本地的号码，直接调用联通充值接口就能充值
		 * "2"=充值卡充值,充值卡充值 一期暂不实现 ，主要针对外地手机号码，用充值卡方式进行充值
		 * 3=电信充值
		 */
		public interface DEAL_TYPE{
			public static final int UNICOM_CHARGE = 1;
			
			public static final int CARD_CHARGE = 2;
			
			public static final int TEL_CHARGE=3;
		}
		
		
		/**
		 * 状态
		 * 
		 */
		public interface TYPE_LENGTH {
			/**
			 * 1.直充
			 */
			public static final int DIRECT_COUNT = 20;
			/**
			 * 2.找零
			 */
			public static final int CHARGE_COUNT = 12;
		}

		/**
		 * 状态
		 * 
		 */
		public interface STATUS {
			/**
			 * 1.接收请求
			 */
			public static final int RECEIVE_REQUEST = 1;
			/**
			 * 2.已撤销
			 */
			public static final int DISCHARGE = 2;
			/**
			 * 3.联通充值失败
			 */
			public static final int UNICOM_RECHARGE_FAIL = 3;
			/**
			 * 4.联通充值成功
			 */
			public static final int UNICOM_RECHARGE_SUCCESS = 4;
			/**
			 * 5.苏果充值失败
			 */
			public static final int SUGUO_RECHARGE_FAIL = 5;

			/**
			 * 6.苏果充值成功
			 */
			public static final int SUGUO_RECHARGE_SUCCESS = 6;

		}
	}

	/**
	 * 对账结果
	 * 
	 */
	public interface CHECKED_BILL {

		/**
		 * 状态
		 * 
		 */
		public interface STATUS {
			/**
			 * 1.完全匹配
			 */
			public static final int FULL_MATCH = 1;
			/**
			 * 2.部分匹配
			 */
			public static final int PART_MATH = 2;

		}
	}

	public interface BILL {
		/**
		 * 苏果处理状态
		 */
		public interface sgCheckedStatus {
			/**
			 * 1.处理中
			 */
			public static final int DOING = 1;
			/**
			 * 2.对账完成
			 */
			public static final int SUCCESS = 2;
			/**
			 * 3.对账失败
			 */
			public static final int FAIL = 3;
		}

		/**
		 * 上传联通状态
		 * 
		 */
		public interface ltStatus {
			/**
			 * 1.已生成
			 */
			public static final int DONE = 1;
			/**
			 * 2.上传成功
			 */
			public static final int UPLOAD_SUCCESS = 2;
			/**
			 * 3.上传失败
			 */
			public static final int UPLOAD_FAIL = 3;
		}
	}
}