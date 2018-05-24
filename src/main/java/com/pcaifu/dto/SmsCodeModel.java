package com.pcaifu.dto;

import java.io.Serializable;

public class SmsCodeModel implements Serializable {

	/**
	 * 短信验证码序列号
	 */
	private Long ucSmsSeq;

	/**
	 * 验证码业务类型
	 */
	private String ucSmsType;

	/**
	 * 手机号码
	 */
	private Long ucPhoneNo;

	/**
	 * 手机验证码
	 */
	private String ucSmsCode;

	public SmsCodeModel() {
		super();
	}

	public SmsCodeModel(Long ucSmsSeq, String ucSmsType, Long ucPhoneNo, String ucSmsCode) {
		super();
		this.ucSmsSeq = ucSmsSeq;
		this.ucSmsType = ucSmsType;
		this.ucPhoneNo = ucPhoneNo;
		this.ucSmsCode = ucSmsCode;
	}

	public Long getUcSmsSeq() {
		return ucSmsSeq;
	}

	public void setUcSmsSeq(Long ucSmsSeq) {
		this.ucSmsSeq = ucSmsSeq;
	}

	public String getUcSmsType() {
		return ucSmsType;
	}

	public void setUcSmsType(String ucSmsType) {
		this.ucSmsType = ucSmsType;
	}

	public Long getUcPhoneNo() {
		return ucPhoneNo;
	}

	public void setUcPhoneNo(Long ucPhoneNo) {
		this.ucPhoneNo = ucPhoneNo;
	}

	public String getUcSmsCode() {
		return ucSmsCode;
	}

	public void setUcSmsCode(String ucSmsCode) {
		this.ucSmsCode = ucSmsCode;
	}

	@Override
	public String toString() {
		return "SmsCodeModel [ucSmsSeq=" + ucSmsSeq + ", ucSmsType=" + ucSmsType + ", ucPhoneNo=" + ucPhoneNo
				+ ", ucSmsCode=" + ucSmsCode + "]";
	}

}
