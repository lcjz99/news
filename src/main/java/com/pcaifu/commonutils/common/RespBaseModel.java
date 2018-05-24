package com.pcaifu.commonutils.common;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2017年9月5日 下午1:32:43  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class RespBaseModel {

	protected String responseCode;

	protected String responseMsg;

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	@Override
	public String toString() {
		return "RespBaseModel [responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", data=" + data + "]";
	}

}
