package com.pcaifu.service.msg.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paicaifu.news.entity.UcSmsCode;
import com.pcaifu.commonutils.common.utils.SmsRandomCode;
import com.pcaifu.dao.msg.SmsCodeDao;
import com.pcaifu.dto.SmsCodeModel;
import com.pcaifu.service.msg.SmsCodeService;

@Service
public class SmsCodeServiceImpl implements SmsCodeService {

	@Autowired
	private SmsCodeDao smsCodeDao;

	private static SimpleDateFormat sdf = new SimpleDateFormat();

	private static Logger logger = LoggerFactory.getLogger(SmsCodeServiceImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveSmsCode(SmsCodeModel smsCodeModel) throws Exception {
		Long currentMillons = new Date().getTime();
		Long fiveMinutes = (long) (1000 * 60 * 5);
		UcSmsCode smsCode = new UcSmsCode();
		BeanUtils.copyProperties(smsCodeModel, smsCode);
		smsCode.setUcSmsTimeOut(new Timestamp(currentMillons + fiveMinutes));
		Long smsSeq = null;
		try {
			smsSeq = getSmsSeq();
			smsCode.setUcSmsSeq(smsSeq);
			smsCodeDao.saveSmsCode(smsCode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return smsSeq + "";
	}

	@Transactional
	@Override
	public String checkUseSmsCode(SmsCodeModel smsCodeModel) throws Exception {
		sdf.applyPattern("yyyyMMddHHmmss");
		Date currentMillonsDate = new Date();
		Long currentMillons = currentMillonsDate.getTime();
		UcSmsCode smsCode = new UcSmsCode();
		BeanUtils.copyProperties(smsCodeModel, smsCode);

		UcSmsCode ucSmsCode = smsCodeDao.querySms(smsCode);
		if (null == ucSmsCode) {
			return "0002";
		}
		Timestamp effectiveTime = ucSmsCode.getUcSmsTimeOut();
		Long effectiveMillons = effectiveTime.getTime();
		String smsCodeStatus = ucSmsCode.getUcSmsStatus();

		if (currentMillons < effectiveMillons && smsCodeStatus.equals("0")) {
			return "0000";
		}
		if (smsCodeStatus.equals("1")) {
			return "0003";
		}
		if ((currentMillons > effectiveMillons)) {
			ucSmsCode.setUcSmsEffective("1");
			smsCodeDao.useSmsCode(ucSmsCode);
			return "0001";
		}
		return null;
	}

	@Transactional
	@Override
	public void useSmsCode(SmsCodeModel smsCodeModel) throws Exception {
		UcSmsCode smsCode = new UcSmsCode();
		BeanUtils.copyProperties(smsCodeModel, smsCode);
		UcSmsCode ucSmsCode = smsCodeDao.querySms(smsCode);
		ucSmsCode.setUcSmsStatus("1");
	}

	private Long getSmsSeq() throws InterruptedException {
		return Long.valueOf(SmsRandomCode.getRamdomCode());

	}
}
