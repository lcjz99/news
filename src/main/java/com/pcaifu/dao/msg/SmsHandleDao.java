package com.pcaifu.dao.msg;

import java.util.List;

import com.paicaifu.news.entity.AppMsgInfo;
import com.paicaifu.news.entity.AppMsgPushDevice;
import com.paicaifu.news.entity.CallInterfaceLogs;
import com.paicaifu.news.entity.SysSmsConfig;

public interface SmsHandleDao {

	/**
	 * 判断请求是否重复
	 * 
	 * @param phoneNum
	 * @param cilReqNoV
	 * @return
	 */
	boolean existMsgLog(String phoneNum, String cilReqNoV);

	String findSmsType(String cilOrderIdN);

	SysSmsConfig findSms(String cilOrderIdN);

	AppMsgPushDevice doIsActiveUser(String userId);

	long create(AppMsgInfo msg);

	void saveMsgLogs(CallInterfaceLogs entity);

	AppMsgInfo findMsgInfo(String pushId);

	String queryValidTime(String smsType, String phoneNo) throws Exception;

	String queryReqCounts(String smsType, String phoneNo) throws Exception;

	List<AppMsgPushDevice> getPushDevice();

}
