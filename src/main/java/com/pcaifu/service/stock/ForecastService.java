package com.pcaifu.service.stock;

import java.text.ParseException;
import java.util.Map;

import com.pcaifu.dto.ForecastDto;
import com.pcaifu.dto.ForecastNoteDto;

public interface ForecastService {
	/**
	 * 查询股票信息
	 * 
	 * @param stockCode
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getStockInfo(String stockCode) throws Exception;

	/**
	 * 查询当日已发布次数
	 * 
	 * @param userId
	 * @return
	 */
	long getTodayForecastTimes(String userId);

	/**
	 * 是否重复发布
	 * 
	 * @param userId
	 * @param expectDate
	 * @param stockCode
	 * @param periodType
	 * @return
	 * @throws Exception
	 */
	boolean isPublished(String userId, String stockCode, String expectDate, String periodType) throws Exception;

	/**
	 * 发布预测
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	int doForecast(ForecastDto dto) throws ParseException, Exception;

	/**
	 * 自动关注股票
	 * 
	 * @param dto
	 */
	void doAttention(ForecastDto dto);

	/**
	 * 受邀pk
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	int doPk(ForecastDto dto) throws ParseException;

	/**
	 * 是否参与过本场次pk
	 * 
	 * @param forecastId
	 * @return
	 */
	boolean isPkJoined(String userId, String forecastId);

	/**
	 * 是否本人发起的pk
	 * 
	 * @param userId
	 * @param pkSerial
	 * @return
	 */
	boolean isPkPublished(String userId, String pkSerial);

	/**
	 * 股票预测记录
	 * 
	 * @param dto
	 */
	void doForecastNote(ForecastNoteDto dto);

}
