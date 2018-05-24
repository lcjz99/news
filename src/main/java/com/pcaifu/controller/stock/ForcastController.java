package com.pcaifu.controller.stock;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pcaifu.commonutils.base.BaseController;
import com.pcaifu.commonutils.common.RespBaseModel;
import com.pcaifu.commonutils.common.constants.Constants;
import com.pcaifu.commonutils.exception.GlobalExceptionEnum;
import com.pcaifu.dto.ForecastDto;
import com.pcaifu.service.stock.ForecastService;

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
@Api(description = "股票预测接口Api")
@RestController
@RequestMapping("/stock/")
public class ForcastController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ForcastController.class);

	@Autowired
	private ForecastService forecastService;

	/**
	 * 股票预测
	 * 
	 * @param dto
	 * @param result
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "股票预测", notes = "根据ForecastDto请求对象创建预测记录")
	@RequestMapping(value = "forecast", method = RequestMethod.POST)
	public RespBaseModel forecast(
			@Valid @RequestBody @ApiParam(name = "ForecastDto", value = "发布预测参数") ForecastDto dto,
			BindingResult result, HttpServletRequest request, Model model, HttpServletResponse response)
			throws Exception {

		RespBaseModel resp = new RespBaseModel();

		if (result.hasErrors()) {
			return respErrorMsg(result);
		}

		if ("2".equals(dto.getFromType()) && StringUtils.isBlank(dto.getPkSerial())) {
			resp.setResponseMsg("参与PK的预测记录场次不能为空");
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		}

		if (!"1003".equals(dto.getForecastType()) && StringUtils.isBlank(dto.getForecastValue())) {
			resp.setResponseMsg("预测涨跌的记录预测值不能为空");
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		}

		// 这支股票是否停盘
		Map<String, Object> stockInfo = forecastService.getStockInfo(dto.getStockCode());
		if ("03".equals((String) stockInfo.get("stockStatus"))) {
			resp.setResponseMsg("这只股票今天停牌不能预测哦~");
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		}

		// 用户今日预测次数<20
		long times = forecastService.getTodayForecastTimes(dto.getUserId());
		if (times >= 20) {
			resp.setResponseMsg("您今天预测次数用完啦~");
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		}

		if ("2".equals(dto.getFromType())) {
			// 是否已参与本场次的pk
			boolean flagPk = forecastService.isPkPublished(dto.getUserId(), dto.getPkSerial());
			if (flagPk) {
				resp.setResponseMsg("您不能参与自己发起的" + dto.getStockName()
						+ Constants.getConstantName("FORCAST_PERIOD_TYPE", dto.getPeriodType()) + "的pk");
				resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
				return resp;

			}

			flagPk = forecastService.isPkJoined(dto.getUserId(), dto.getPkSerial());
			if (flagPk) {
				resp.setResponseMsg("您已经参与过" + dto.getStockName()
						+ Constants.getConstantName("FORCAST_PERIOD_TYPE", dto.getPeriodType()) + "的pk啦");
				resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
				return resp;

			}
		} else {
			// 是否已发布过该交易时长的预测 提示：您今天已经预测过X个交易日后的涨跌啦
			boolean flag = forecastService.isPublished(dto.getUserId(), dto.getStockCode(), dto.getExpectDate(),
					dto.getPeriodType());
			if (flag) {
				resp.setResponseMsg("您已经预测过" + dto.getStockName()
						+ Constants.getConstantName("FORCAST_PERIOD_TYPE", dto.getPeriodType()) + "后的涨跌啦");
				resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
				return resp;
			}
		}

		// 发布成功，记录该笔预测，扣减用户金币数 用户剩余金币数≥10
		Integer forecastId;
		try {
			if ("1".equals(dto.getFromType())) {
				forecastId = forecastService.doForecast(dto);
			} else {
				forecastId = forecastService.doPk(dto);
			}
		} catch (ParseException e) {
			log.error("发布预测记录失败：" + e.getMessage(), e);
			resp.setResponseMsg("系统忙，请确稍后重试~");
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		} catch (Exception e) {
			log.error("发布预测记录失败：" + e.getMessage(), e);
			resp.setResponseMsg("您的可用金币不够啦~");
			resp.setResponseCode(GlobalExceptionEnum.FAIL.getCode());
			return resp;
		}

		// 自动为用户关注该只股票
		try {
			forecastService.doAttention(dto);
		} catch (Exception e) {
			log.error("发布预测记录关注失败：" + e.getMessage(), e);
		}

		resp.setResponseCode(GlobalExceptionEnum.SUCCESS.getCode());
		resp.setResponseMsg("发布预测成功");
		resp.setData(forecastId.toString());

		return resp;
	}

}
