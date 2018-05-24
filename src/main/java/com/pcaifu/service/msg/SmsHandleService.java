package com.pcaifu.service.msg;

import java.util.Map;

import com.pcaifu.dto.MsgLogDto;

public interface SmsHandleService {

	boolean pushMsg(MsgLogDto dto);

	boolean queryValidTime(String smsType, String phoneNum) throws Exception;

	boolean queryReqCounts(String smsType, String phoneNum) throws Exception;

	Map sendMsg(MsgLogDto dto);

}
