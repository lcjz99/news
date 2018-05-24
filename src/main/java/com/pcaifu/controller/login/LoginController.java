package com.pcaifu.controller.login;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.commonutils.base.BaseController;
import com.pcaifu.commonutils.base.JRedisTemplateTools;
import com.pcaifu.commonutils.common.RespBaseModel;
import com.pcaifu.commonutils.common.constants.Constants;
import com.pcaifu.commonutils.common.utils.GsonUtils;
import com.pcaifu.commonutils.common.utils.StringUtils;
import com.pcaifu.commonutils.exception.GlobalExceptionEnum;
import com.pcaifu.dto.SmsCodeModel;
import com.pcaifu.dto.UserReqDto;
import com.pcaifu.service.msg.SmsCodeService;
import com.pcaifu.service.user.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2018年01月15日 下午1:47:21  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Api(description = "用户登录接口Api")
@RestController
@RequestMapping("/login/")
public class LoginController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private SmsCodeService smsCodeService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private JRedisTemplateTools redisTemplate;
	private static String toppic = "https://cdn.paicaifu.com/webapp/image/app/Avatar.png";

	/**
	 * 用户登录
	 * 
	 * @param phone
	 * @param terminalType
	 * @param smsCode
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户快捷登录/注册", notes = "根据UserReqDto请求对象创建用户/用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "UserReqDto.phone", required = true, value = "手机号", dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "UserReqDto.terminalType", required = true, value = "终端类型", dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "UserReqDto.smsCode", required = true, value = "短信验证码", dataType = "String", paramType = "form") })
	@RequestMapping(value = "quickLogin", method = RequestMethod.POST)
	public RespBaseModel login(@Valid @RequestBody UserReqDto dto, BindingResult result, HttpServletRequest request,
			Model model, HttpServletResponse response) throws Exception {
		RespBaseModel resp = new RespBaseModel();
		if (dto == null) {
			logger.info("参数不能为空");
			return respErrorMsg(result);
		}
		// 验证短信验证码
		SmsCodeModel smsCode = new SmsCodeModel();
		String smsSeq = redisTemplate.get(Constants.smsType + dto.getPhone());
		smsCode.setUcSmsCode(dto.getSmsCode());
		smsCode.setUcPhoneNo(Long.valueOf(dto.getPhone()));
		if (StringUtils.isNotBlank(smsSeq)) {
			smsCode.setUcSmsSeq(Long.valueOf(smsSeq));
		}
		smsCode.setUcSmsType(Constants.smsType);
		String respCode = smsCodeService.checkUseSmsCode(smsCode);
		if (!Constants.UC_SMS.STATUS_0000.equals(respCode)) {
			resp.setResponseMsg(Constants.getConstantName("UC_SMS_STATUS", respCode));
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		} else {
			UserInfo user = userInfoService.queryUserInfo(dto.getPhone());
			String msg = "登录";
			if (user == null) {
				dto.setHeadpicLink(toppic);
				dto.setGoldcoinNum(188l);
				user = userInfoService.doSaveUser(dto);
				logger.info("当前用户：{}注册成功，发送奖励金币:{}", dto.getPhone(), user.getUiGoldcoinNum());
				msg = "注册";
				saveGoldCoinInfo(user);
				user.setNew(true);

				String today = getToday();
				redisTemplate.set("pk1005_" + user.getUiUserId() + "_" + today, "200", 24 * 3600);
				redisTemplate.set("pk1006_" + user.getUiUserId() + "_" + today, "100", 24 * 3600);
			}
			// 验证码使用失效
			smsCodeService.useSmsCode(smsCode);
			logger.info("当前用户：{}{}成功", dto.getPhone(), msg);
			resp.setResponseCode(GlobalExceptionEnum.SUCCESS.getCode());
			resp.setResponseMsg(msg + "成功");
			resp.setData(GsonUtils.getJson(user));

		}
		return resp;
	}

	@Async
	public void saveGoldCoinInfo(UserInfo user) throws InterruptedException {
		Thread.sleep(3000);
		userInfoService.doSaveGoldCoinInfo(user);
		logger.info("注册金币发送完毕");
	}

	private String getToday() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(cal.getTime());
	}

}
