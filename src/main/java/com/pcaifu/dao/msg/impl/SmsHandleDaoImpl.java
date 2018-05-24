package com.pcaifu.dao.msg.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paicaifu.news.entity.AppMsgInfo;
import com.paicaifu.news.entity.AppMsgPushDevice;
import com.paicaifu.news.entity.CallInterfaceLogs;
import com.paicaifu.news.entity.SysSmsConfig;
import com.pcaifu.commonutils.base.JpaBaseDao;
import com.pcaifu.commonutils.base.OpenDao;
import com.pcaifu.dao.msg.SmsHandleDao;

@Repository
@Transactional
public class SmsHandleDaoImpl implements SmsHandleDao {
	private static final Logger logger = LoggerFactory.getLogger(SmsHandleDaoImpl.class);
	@Autowired
	private JpaBaseDao baseDao;
	@Autowired
	OpenDao openDao;

	@Override
	public boolean existMsgLog(String phoneNum, String cilReqNoV) {
		long recCount = baseDao.findSingle(
				"select count(*) as recCount from CallInterfaceLogs where cilMpV=? AND cilReqNoV=?",
				new Object[] { phoneNum, cilReqNoV });
		return recCount > 0;
	}

	@Override
	public String findSmsType(String cilOrderIdN) {
		String hql = "select sscType from SysSmsConfig where isactive=1 and sscKey=? and sscStatus=? ";
		String message = baseDao.findSingle(hql, cilOrderIdN, "1");
		return message;
	}

	@Override
	public SysSmsConfig findSms(String cilOrderIdN) {
		String hql = "from SysSmsConfig where isactive=1 and sscKey=? and sscStatus=? ";
		SysSmsConfig sms = baseDao.findSingle(hql, cilOrderIdN, "1");
		if (sms != null) {
			return sms;
		} else {
			return null;
		}
	}

	@Override
	public AppMsgInfo findMsgInfo(String pushId) {
		String hql = "from AppMsgInfo where isactive=1 and amiId=? ";
		AppMsgInfo app = baseDao.findSingle(hql, Long.valueOf(pushId));
		if (app != null) {
			return app;
		} else {
			return null;
		}
	}

	@Override
	public AppMsgPushDevice doIsActiveUser(String userId) {
		Calendar date = Calendar.getInstance();
		date.setTime(new Timestamp(new Date().getTime()));
		date.add(Calendar.DATE, -14);
		AppMsgPushDevice appMsgPushDevice = (AppMsgPushDevice) baseDao.findSingle(
				"from AppMsgPushDevice where ampdUserId=? AND isactive=1  and  ampdLastLoginTime > ? ",
				new Object[] { Long.parseLong(userId), date.getTime() });
		return appMsgPushDevice;
	}
	
	@Override
	public List<AppMsgPushDevice> getPushDevice() {
		String hql="from AppMsgPushDevice where  isactive=1";
		List<AppMsgPushDevice> lists= baseDao.listByHql(hql, null);
		return lists;
	}

	@Override
	public long create(AppMsgInfo msg) {
		baseDao.save(msg);
		return msg.getAmiId();
	}

	@Override
	public void saveMsgLogs(CallInterfaceLogs entity) {
		baseDao.save(entity);

	}

	/**
	 * 验证当前用户请求短信验证码时间是否触发限制规则
	 */
	@Override
	public String queryValidTime(String smsType, String phoneNo) throws Exception {
		String hql1 = "select MAX(inserttime) from  uc_sms_code where  uc_sms_type=? and  uc_phone_no=? and DATE_FORMAT(inserttime,'%Y-%m-%d') =CURRENT_DATE";
		Timestamp t = openDao.findSingle(hql1, new Object[] { smsType, Long.valueOf(phoneNo) });
		return t == null ? "" : t.toString();

	}

	/**
	 * 验证当前用户请求短信验证码时间是否触发限制规则
	 */
	@Override
	public String queryReqCounts(String smsType, String phoneNo) throws Exception {
		String counts = "0";
		String hql = "from  UcSmsCode where  ucSmsType=? and  ucPhoneNo=? and DATE_FORMAT(inserttime,'%Y-%m-%d') =CURRENT_DATE";
		counts = baseDao.listByHql(hql, new Object[] { smsType, Long.valueOf(phoneNo) }).size() + "";
		return counts;

	}

}
