package com.pcaifu.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class MsgLogDto implements Serializable {

	private static final long serialVersionUID = -1865255739550943226L;

	/** 手机号 **/
	@NotBlank
	private String cilMpV;

	/** 终端类型 **/
	@NotBlank
	private String cilTerminalType;

	/** 业务Type **/
	private String cilType;

	/** 请求报文 **/
	private String cilReqMsgV;

	private String cilReqNoV;

	private String userId;
	/** push msgId **/
	private String pushId;

	public String getCilMpV() {
		return cilMpV;
	}

	public void setCilMpV(String cilMpV) {
		this.cilMpV = cilMpV;
	}

	public String getCilTerminalType() {
		return cilTerminalType;
	}

	public void setCilTerminalType(String cilTerminalType) {
		this.cilTerminalType = cilTerminalType;
	}

	public String getCilType() {
		return cilType;
	}

	public void setCilType(String cilType) {
		this.cilType = cilType;
	}

	public String getCilReqMsgV() {
		return cilReqMsgV;
	}

	public void setCilReqMsgV(String cilReqMsgV) {
		this.cilReqMsgV = cilReqMsgV;
	}

	public String getCilReqNoV() {
		return cilReqNoV;
	}

	public void setCilReqNoV(String cilReqNoV) {
		this.cilReqNoV = cilReqNoV;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

}
