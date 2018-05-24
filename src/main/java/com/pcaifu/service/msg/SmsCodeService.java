package com.pcaifu.service.msg;

import com.pcaifu.dto.SmsCodeModel;

public interface SmsCodeService {

	String saveSmsCode(SmsCodeModel smsCodeModel) throws Exception;

	String checkUseSmsCode(SmsCodeModel smsCodeModel) throws Exception;

	/**
	 * 
	 * @param smsCodeModel
	 * @throws Exception
	 */
	void useSmsCode(SmsCodeModel smsCodeModel) throws Exception;

}
