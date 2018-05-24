package com.pcaifu.service.msg.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.smscloud.json.JSONHttpClient;
import com.paicaifu.news.entity.AppMsgInfo;
import com.paicaifu.news.entity.AppMsgPushDevice;
import com.paicaifu.news.entity.CallInterfaceLogs;
import com.paicaifu.news.entity.SysSmsConfig;
import com.pcaifu.commonutils.common.constants.Constants.TERMINAL_TYPE;
import com.pcaifu.commonutils.common.utils.GsonUtils;
import com.pcaifu.commonutils.common.utils.StringUtils;
import com.pcaifu.commonutils.push.UmenPushUtils;
import com.pcaifu.dao.msg.SmsHandleDao;
import com.pcaifu.dto.MsgLogDto;
import com.pcaifu.dto.SmsCodeModel;
import com.pcaifu.service.msg.SmsCodeService;
import com.pcaifu.service.msg.SmsHandleService;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2018  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2018年1月18日 下午3:38:13  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Service
@Transactional
public class SmsHandleServiceImpl implements SmsHandleService {
	private static final Logger logger = LoggerFactory.getLogger(SmsHandleServiceImpl.class);

	/* 功能类型 */
	@Value("${account}")
	private String account;// 用户名（必填）
	@Value("${password}")
	private String password;// 密码（必填）
	public static String subcode = ""; // 子号码（必填）
	public static String msgid = ""; // 短信id，查询短信状态报告时需要，（可选）
	public static String sendtime = ""; // 定时发送时间（可选）

	/* 营销类型 */
	@Value("${accountMarketing}")
	private String accountMarketing;// 用户名（必填）
	@Value("${passwordMarketing}")
	private String passwordMarketing;// 密码（必填）

	@Value("${sign}")
	protected String sign;

	@Autowired
	private SmsHandleDao smsHandleDao;

	private String tempCode = "Captcha";
	@Autowired
	private SmsCodeService smsCodeService;

	/**
	 * 发送短信
	 * 
	 * @return
	 */
	public boolean sendMsgInfo(MsgLogDto dto, String code) {
		if (null != dto) {
			logger.info("手机号：{},请求终端:{},请求报文：{},订单流水号：{}", dto.getCilMpV(), dto.getCilTerminalType(),
					dto.getCilReqMsgV(), dto.getCilReqNoV());
		}

		String phoneNum = dto.getCilMpV();
		if (StringUtils.isBlank(phoneNum)) {
			logger.info("手机号为空");
			return false;
		}

		if (!isMobile(phoneNum)) {
			logger.info("手机号格式不正确{}：", phoneNum);
			return false;
		}

		// 业务流水号重复校验
		if (StringUtils.isNotBlank(dto.getCilReqNoV())) {
			boolean flag = this.smsHandleDao.existMsgLog(phoneNum, dto.getCilReqNoV());
			if (flag) {
				logger.info("{} 该业务流水号已成功，无法重复请求", dto.getCilReqNoV());
				return false;
			}
		}

		SysSmsConfig conf = smsHandleDao.findSms(tempCode);
		String message = conf.getSscContent();
		message = MessageFormat.format(message, code);
		// 发送短信
		String status = this.sendCustomizedSms(dto.getCilMpV(), message, sign, dto.getCilReqNoV());
		Map<String, String> map = GsonUtils.fromJson(status, Map.class);
		logger.info("发送手机号{},短信返回信息：{},msgId={}---记录成功", dto.getCilMpV(), map.get("desc"), map.get("msgid"));
		if (map.get("msgid").toString() != null && !"".equals(map.get("msgid").toString())) {
			// 记录日志
			CallInterfaceLogs entity = new CallInterfaceLogs();
			entity.setCilTerminalType(dto.getCilTerminalType());
			entity.setCilMpV(dto.getCilMpV());
			entity.setCilTypeV(dto.getCilType());
			entity.setCilReqMsgV(message);
			entity.setCilReqNoV(dto.getCilReqNoV());
			smsHandleDao.saveMsgLogs(entity);
			logger.info("发送手机号:{}---记录成功", dto.getCilMpV());
			return true;
		}

		return false;

	}

	/**
	 * 消息推送
	 */
	@Override
	public boolean pushMsg(MsgLogDto dto) {
		AppMsgInfo app = smsHandleDao.findMsgInfo(dto.getPushId());
		if (null != app) {
			logger.info("编号pushID：{},push报文：{}", app.getAmiId(), app.getAmiContent());
		}
		List<AppMsgPushDevice> lists = smsHandleDao.getPushDevice();

		Boolean msgflag = false;
		if (null != lists && lists.size() > 0) {
			for (AppMsgPushDevice device : lists) {
				try {
					if (TERMINAL_TYPE.TYPE_2.equals(device.getAmpdDeviceType())) {
						msgflag=UmenPushUtils.pushIOS(app.getAmiContent(), true, device.getAmpdDeviceToken());
					} else if (TERMINAL_TYPE.TYPE_3.equals(device.getAmpdDeviceType())) {
						msgflag=UmenPushUtils.pushAndroid(app.getAmiTitle(), app.getAmiContent(), true,
								device.getAmpdDeviceToken());
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("消息编号{},[内容为:{}]推送出错:", app.getAmiContent(), app.getAmiId());
					msgflag = false;
				}

			}
		}
		return msgflag;
	}

	@Override
	public Map sendMsg(MsgLogDto dto) {
		// 发送短信
		String code = StringUtils.getRamdomCode();
		logger.info("向手机号【" + dto.getCilMpV() + "】发送‘短信验证码’通知");
		SmsCodeModel smsCode = new SmsCodeModel();
		smsCode.setUcPhoneNo(Long.valueOf(dto.getCilMpV()));
		smsCode.setUcSmsCode(code);
		smsCode.setUcSmsType(tempCode);
		String smsSeq = null;
		try {
			smsSeq = smsCodeService.saveSmsCode(smsCode);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		boolean flag = sendMsgInfo(dto, code);
		map.put("smsSeq", smsSeq);
		map.put("flag", flag);
		return map;

	}

	/**
	 * 发送自定义短息信息
	 * 
	 * @param phoneNum
	 * @param content
	 * @return
	 */
	public String sendCustomizedSms(String phoneNum, String content, String sign, String msgId) {
		String sendRes = "ok";
		JSONHttpClient jsonHttpClient = JSONHttpClient.getInstance("wt.3tong.net");
		sendRes = jsonHttpClient.sendSms(account, password, phoneNum, content, sign, subcode, msgId);
		logger.info("短信接口响应信息：" + sendRes);
		return sendRes;
	}

	private boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][2,3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 用户获取短信验证码重复点击请求操作
	 */
	@Override
	public boolean queryValidTime(String smsType, String phoneNum) {
		long t = 0l;
		try {
			String theMaxReqTime = smsHandleDao.queryValidTime(smsType, phoneNum);
			logger.info("本次操作与当前用户最后一次请求的时间是否相差60秒");
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotBlank(theMaxReqTime)) {
				logger.info("---上一次请求时间" + theMaxReqTime);
				t = sd.parse(theMaxReqTime).getTime();
				long t2 = new Date().getTime();
				logger.info((60 < (t2 - t) / 1000) + "");
				return 60 < (t2 - t) / 1000;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 用户获取短信验证码重复点击请求操作
	 */
	@Override
	public boolean queryReqCounts(String smsType, String phoneNum) throws Exception {
		String counts = smsHandleDao.queryReqCounts(smsType, phoneNum);
		if (Integer.parseInt(counts) < 10) {
			return true;
		} else {
			return false;
		}
	}
}
