package com.pcaifu.commonutils.exception;

@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {

	private GlobalExceptionEnum globalExceptionEnum;

	public GlobalException(GlobalExceptionEnum globalExceptionEnum) {
		super();
		this.globalExceptionEnum = globalExceptionEnum;
	}

	public GlobalExceptionEnum getGlobalExceptionEnum() {
		return globalExceptionEnum;
	}

}
