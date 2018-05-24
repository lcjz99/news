package com.pcaifu.commonutils.common.constants;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2018  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2018年1月18日 下午3:58:31  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class Constants {
	public static String smsType = "Captcha";

	/**
	 * 预测时长
	 * 
	 */
	public static final class FORCAST_PERIOD_TYPE {

		@ConstantTag(name = "1个交易日", type = "FORCAST_PERIOD_TYPE")
		public static final String FORCAST_PERIOD_TYPE_1001 = "1001";

		@ConstantTag(name = "3个交易日", type = "FORCAST_PERIOD_TYPE")
		public static final String FORCAST_PERIOD_TYPE_1002 = "1002";

		@ConstantTag(name = "5个交易日", type = "FORCAST_PERIOD_TYPE")
		public static final String FORCAST_PERIOD_TYPE_1003 = "1003";

		@ConstantTag(name = "10个交易日", type = "FORCAST_PERIOD_TYPE")
		public static final String FORCAST_PERIOD_TYPE_1004 = "1004";

		@ConstantTag(name = "20个交易日", type = "FORCAST_PERIOD_TYPE")
		public static final String FORCAST_PERIOD_TYPE_1005 = "1005";
	}

	public static final class APP_MSG {
		@ConstantTag(name = "全体", type = "APP_MSG_TARGET")
		public static final String APP_MSG_TARGET_1001 = "1001";
		@ConstantTag(name = "单独用户", type = "APP_MSG_TARGET")
		public static final String APP_MSG_TARGET_1002 = "1002";

		@ConstantTag(name = "未推送", type = "APP_MSG_PUSH")
		public static final String APP_MSG_PUSH_0 = "0";
		@ConstantTag(name = "已推送", type = "APP_MSG_PUSH")
		public static final String APP_MSG_PUSH_1 = "1";

		@ConstantTag(name = "未审核", type = "APP_MSG_AUTH")
		public static final String APP_MSG_AUTH_0 = "0";
		@ConstantTag(name = "已审核", type = "APP_MSG_AUTH")
		public static final String APP_MSG_AUTH_1 = "1";

		@ConstantTag(name = "未读", type = "APP_MSG_READ")
		public static final String APP_MSG_READ_0 = "0";
		@ConstantTag(name = "已读", type = "APP_MSG_READ")
		public static final String APP_MSG_READ_1 = "1";
	}

	public static final class UC_SMS {
		/**
		 * UC_SMS_STATUS认证状态 0000：验证通过；0001：验证码过期；0002：验证码错误；0003：验证码已使用
		 */
		@ConstantTag(name = "验证通过", type = "UC_SMS_STATUS")
		public static final String STATUS_0000 = "0000";
		/**
		 * UC_SMS_STATUS认证状态 0000：验证通过；0001：验证码过期；0002：验证码错误；0003：验证码已使用
		 */
		@ConstantTag(name = "短信验证码已失效", type = "UC_SMS_STATUS")
		public static final String STATUS_0001 = "0001";
		/**
		 * UC_SMS_STATUS认证状态 0000：验证通过；0001：验证码过期；0002：验证码错误；0003：验证码已使用
		 */
		@ConstantTag(name = "短信验证码错误", type = "UC_SMS_STATUS")
		public static final String STATUS_0002 = "0002";
		/**
		 * UC_SMS_STATUS认证状态 0000：验证通过；0001：验证码过期；0002：验证码错误；0003：验证码已使用
		 */
		@ConstantTag(name = "短信验证码已使用", type = "UC_SMS_STATUS")
		public static final String STATUS_0003 = "0003";
	}

	public static final class UABO_STATUS {
		@ConstantTag(name = "成功", type = "UABO_STATUS_V")
		public static final String UABO_STATUS_1000 = "1000";

		@ConstantTag(name = "失败", type = "UABO_STATUS_V")
		public static final String UABO_STATUS_2000 = "2000";

	}

	/**
	 * 通用YES_NO
	 */
	public static final class YES_NO {

		@ConstantTag(name = "是", type = "YES_NO")
		public static final String YES = "1";

		@ConstantTag(name = "否", type = "YES_NO")
		public static final String NO = "0";
	}

	/**
	 * 消息推送
	 */
	public static final class UMP {
		@ConstantTag(name = "关闭", type = "UMP_TEMPLATE_STATUS")
		public static final String UMP_STATUS_0 = "0";

		@ConstantTag(name = "打开", type = "UMP_TEMPLATE_STATUS")
		public static final String UMP_STATUS_1 = "1";
	}

	/**
	 * 邀请码
	 */
	public static final class MIC {
		@ConstantTag(name = "有效", type = "AIC_CODE_STATUS_C")
		public static final String AIC_STATUS_1 = "1";

		@ConstantTag(name = "过期", type = "AIC_CODE_STATUS_C")
		public static final String AIC_STATUS_2 = "2";

		@ConstantTag(name = "已使用", type = "AIC_CODE_STATUS_C")
		public static final String AIC_STATUS_3 = "3";
	}

	/***
	 * 终端类型
	 */
	public static final class TERMINAL_TYPE {
		@ConstantTag(name = "APP-IOS", type = "TERMINAL_TYPE")
		public static final String TYPE_2 = "2";
		@ConstantTag(name = "APP-Android", type = "TERMINAL_TYPE")
		public static final String TYPE_3 = "3";
	}

	/**
	 * 提现处理类型
	 */
	public static final class CASH_DEAL_TYPE {
		@ConstantTag(name = "手工处理", type = "CASH_DEAL_TYPE")
		public static final String CASH_DEAL_HANDLER = "1";

		@ConstantTag(name = "系统自动处理", type = "CASH_DEAL_TYPE")
		public static final String CASH_DEAL_AUTO = "2";

	}

	/**
	 * 收支类型
	 */
	public static final class BALANCE_TYPE {

		@ConstantTag(name = "收入", type = "BALANCE_TYPE")
		public static final String BALANCE_TYPE_IN = "1";

		@ConstantTag(name = "支出", type = "BALANCE_TYPE")
		public static final String BALANCE_TYPE_OUT = "2";

	}

	/**
	 * 金币类型
	 */
	public static final class COIN_TYPE {

		@ConstantTag(name = "注册", type = "COIN_TYPE")
		public static final String COIN_TYPE_1001 = "1001";

		@ConstantTag(name = "签到", type = "COIN_TYPE")
		public static final String COIN_TYPE_1002 = "1002";

		@ConstantTag(name = "发布预测", type = "COIN_TYPE")
		public static final String COIN_TYPE_1003 = "1003";

		@ConstantTag(name = "预测结果", type = "COIN_TYPE")
		public static final String COIN_TYPE_1004 = "1004";

		@ConstantTag(name = "邀请好友PK", type = "COIN_TYPE")
		public static final String COIN_TYPE_1005 = "1005";

		@ConstantTag(name = "参与PK", type = "COIN_TYPE")
		public static final String COIN_TYPE_1006 = "1006";

	}

	/**
	 * 快钱充值手续费率
	 */
	public static final double QFEE_RATE = 0.15;

	/**
	 * 富友最低手续费
	 */
	public static final BigDecimal FY_MINCHARGE_FEE = new BigDecimal(2);

	/**
	 * 存放 1001=网银
	 */
	private static Map<Object, String> typeData = new LinkedHashMap<Object, String>();

	/**
	 * 存放 [{1001=网银}, {1002=快捷}, {1003=代扣}]
	 */
	private static Map<Object, List<Map<Object, String>>> listData = new LinkedHashMap<Object, List<Map<Object, String>>>();

	// 初始化
	static {
		Class<Constants> clazz = Constants.class;
		Class<?>[] clazzs = clazz.getDeclaredClasses();

		for (int i = 0; i < clazzs.length; i++) {

			Field[] fields = clazzs[i].getDeclaredFields();

			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];

				// 查看当前域是否存在常量定义标签
				ConstantTag constantsTag = field.getAnnotation(ConstantTag.class);
				if (constantsTag == null) {
					continue;
				}

				// 判断域是否为静态类型
				if (!Modifier.isStatic(field.getModifiers())) {
					throw new RuntimeException("类型[" + field.getDeclaringClass() + "]的域[" + field + "]不是静态类型,无法取得其值!");
				}

				// 取得域的值
				Object constantsValue;
				try {
					constantsValue = field.get(null);
				} catch (Exception e) {
					throw new RuntimeException("取得静态域[" + field + "]的值时发生异常:", e);
				}

				String type = constantsTag.type();
				String name = constantsTag.name();
				String value = String.valueOf(constantsValue);

				// 存值
				String key = generatorKey(type, value);
				String obj = typeData.get(key);
				if (null != obj) {
					throw new RuntimeException("KEY [" + key + "]之前已经存在");
				}
				typeData.put(key, name);

				// 存列表值
				List<Map<Object, String>> list = listData.get(type);

				if (null == list) {
					// 之前不存在则创建一个list
					list = new ArrayList<Map<Object, String>>();
					listData.put(type, list);
				}

				Map<Object, String> map = new HashMap<Object, String>();
				map.put(value, name);
				list.add(map);
			}
		}
	}

	// 生成key
	private static String generatorKey(String type, String value) {
		return type + "$" + value;
	}

	/**
	 * 翻译
	 *
	 * @param type
	 * @param value
	 * @return
	 */
	public static String getConstantName(String type, String value) {
		return typeData.get(generatorKey(type, value));
	}

	/**
	 * 翻译
	 *
	 * @param type
	 * @param value
	 * @return
	 */
	public static String getConstantName(String type, Long value) {
		return typeData.get(generatorKey(type, value.toString()));
	}

	/**
	 * 获取列表
	 *
	 * @param type
	 * @return
	 */
	public static List<Map<Object, String>> getConstantList(String type) {
		return listData.get(type);
	}

	/**
	 * 获取列表
	 *
	 * @param type
	 * @return
	 */
	public static Map<Object, String> getConstantMap(String type) {
		List<Map<Object, String>> list = listData.get(type);
		if (null == list || list.isEmpty()) {
			return null;
		}
		Map<Object, String> result = new LinkedHashMap<Object, String>();
		for (Map<Object, String> map : list) {
			result.putAll(map);
		}
		return result;
	}

	/**
	 * 转换优惠券使用范围
	 *
	 * @param ranges
	 * @return
	 */
	public static String changeUseRange(String[] ranges) {
		String values = "";
		for (int j = 0; j < ranges.length; j++) {
			String a = ranges[j];
			values += Constants.getConstantName("PRODUCT_TYPE", a.trim()) + "|";
		}
		return values.substring(0, values.length() - 1);
	}

	// public static void main(String[] args) {
	// String ss = "";
	// // Test
	// String v = Constants.AA.STATUS_0;
	// System.out.println(v);
	// System.out.println("基金类型转码：" + getConstantName("FUND_TYPE_CODE",
	// Constants.FUND_TYPE_CODE.FUND_TYPE_1003));
	// System.out.println("中文" + getConstantName("UC_SMS_STATUS",
	// Constants.UC_SMS.STATUS_0002));
	// System.out.println(Arrays.toString(getConstantList("BUS_TRANS_TYPE").toArray()));
	// System.out.println(Arrays.toString(getConstantList("BUS_TRANS_TYPE").toArray()));
	//
	// System.out.println(getConstantMap("USER_REG_TERMINAL").toString());
	// }
}
