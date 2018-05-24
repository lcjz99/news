package com.pcaifu.dao.msg;

import com.paicaifu.news.entity.UcSmsCode;

public interface SmsCodeDao {

	void saveSmsCode(UcSmsCode smsCode);

	void useSmsCode(UcSmsCode smsCode) throws Exception;

	UcSmsCode querySms(UcSmsCode smsCode) throws Exception;
}
