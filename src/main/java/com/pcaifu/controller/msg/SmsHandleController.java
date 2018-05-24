package com.pcaifu.controller.msg;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pcaifu.commonutils.base.BaseController;
import com.pcaifu.commonutils.base.JRedisTemplateTools;
import com.pcaifu.commonutils.base.MD5Util;
import com.pcaifu.commonutils.common.RespBaseModel;
import com.pcaifu.commonutils.common.constants.Constants;
import com.pcaifu.commonutils.common.utils.StringUtils;
import com.pcaifu.commonutils.exception.GlobalExceptionEnum;
import com.pcaifu.dto.MsgLogDto;
import com.pcaifu.service.msg.SmsHandleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2018  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2018年1月18日 下午1:46:54  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Api(description = "用户消息推送相关接口Api")
@RestController
@RequestMapping("/smsHandle/")
public class SmsHandleController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SmsHandleController.class);
	@Autowired
	private SmsHandleService smsHandleService;
	@Autowired
	private JRedisTemplateTools redisTemplate;

	/**
	 * 发送短信
	 * 
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "获取短信验证码", notes = "根据手机号请求短信验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "MsgLogDto.cilReqNoV", required = true, value = "手机号", dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "MsgLogDto.cilTerminalType", required = true, value = "终端类型", dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "MsgLogDto.cilReqNoV", required = true, value = "请求流水号", dataType = "String", paramType = "form"), })
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	public RespBaseModel sendMsg(@Valid @RequestBody MsgLogDto dto, BindingResult result) throws Exception {
		RespBaseModel respModel = new RespBaseModel();
		if (dto == null) {
			logger.info("参数不能为空");
			return respErrorMsg(result);
		}
		respModel.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
		String repMsg = "短信发送失败";
		Map map = new HashMap<Object, Object>();
		try {
			if (smsHandleService.queryValidTime(Constants.smsType, dto.getCilMpV())) {
				String key = MD5Util.md5Hex(Constants.smsType + dto.getCilMpV());
				String resp = redisTemplate.get(key);
				log.info("redis缓存数据当前请求的手机号:{},业务类型:{},作为key缓存对象:{}", dto.getCilMpV(), Constants.smsType, resp);
				if (StringUtils.isBlank(resp)) {
					if (smsHandleService.queryReqCounts(Constants.smsType, dto.getCilMpV())) {
						map = smsHandleService.sendMsg(dto);
						redisTemplate.set(Constants.smsType + dto.getCilMpV(), map.get("smsSeq").toString(), 300);
					} else {
						log.info("当天请求的手机号:{},业务类型:{}次数超过10次，缓存起来", dto.getCilMpV(), Constants.smsType);
						redisTemplate.set(key, "valiTime", 300);
						repMsg = "您当前操作过于频繁，请稍后再试！";
						log.error("当前手机号码:{}请求操作过于频繁，请稍后再试", dto.getCilMpV());
						respModel.setResponseMsg(repMsg);
						return respModel;
					}
					boolean b = ((Boolean) map.get("flag")).booleanValue();
					if (b) {
						logger.info("手机号码:{},业务为:{}发送短信成功", dto.getCilMpV(), Constants.smsType);
						repMsg = "短信发送成功！";
						respModel.setResponseCode(GlobalExceptionEnum.SUCCESS.getCode());
					}
				} else {
					repMsg = "您当前操作过于频繁，请稍后再试！";
				}
			} else {
				repMsg = "您当前请求过于频繁，请1分钟后再试！";
			}
		} catch (Exception e) {
			repMsg = "短信服务请求异常！";
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}
		respModel.setResponseMsg(repMsg);
		return respModel;
	}

	/**
	 * 消息推送
	 * 
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "后台消息推送", notes = "根据pushId推送至App终端")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pushId", required = true, value = "消息ID", dataType = "String", paramType = "form") })
	@RequestMapping(value = "/pushMsg", method = RequestMethod.POST)
	public RespBaseModel pushMsg(@Valid @RequestBody MsgLogDto dto, BindingResult result) throws Exception {
		RespBaseModel respModel = new RespBaseModel();
		if (dto == null) {
			logger.info("参数不能为空");
			return respErrorMsg(result);
		}
		boolean flag = false;
		try {
			flag = smsHandleService.pushMsg(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag) {
			respModel.setResponseCode(GlobalExceptionEnum.SUCCESS.getCode());
			respModel.setResponseMsg("推送成功");
			logger.info("业务id：{}消息推送成功", dto.getPushId());
		} else {
			respModel.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			respModel.setResponseMsg("推送失败");
			logger.info("业务id：{}消息推送失败", dto.getPushId());
		}
		return respModel;
	}
}
