package com.pcaifu.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2018  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2018年1月16日 下午12:53:48  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class UserReqDto implements Serializable {

	private String userId;
	private String nickName;
	@NotBlank
	private String phone;
	@NotBlank
	private String terminalType;
	private Long goldcoinNum;
	private String headpicLink;
	@NotBlank
	private String smsCode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public Long getGoldcoinNum() {
		return goldcoinNum;
	}

	public void setGoldcoinNum(Long goldcoinNum) {
		this.goldcoinNum = goldcoinNum;
	}

	public String getHeadpicLink() {
		return headpicLink;
	}

	public void setHeadpicLink(String headpicLink) {
		this.headpicLink = headpicLink;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
