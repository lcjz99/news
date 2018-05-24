package com.pcaifu.dao.stock;

import java.util.Date;

import com.paicaifu.news.entity.AppForecastNotes;
import com.paicaifu.news.entity.MgrAttention;
import com.paicaifu.news.entity.MgrForecastLog;
import com.paicaifu.news.entity.MgrPkLog;
import com.paicaifu.news.entity.UserCoinDetail;

public interface ForecastDao {
	/**
	 * 获取当日预测次数
	 * 
	 * @param userId
	 * @return
	 */
	long getTodayForecastTimes(Long userId);

	/**
	 * 是否预测过某一天的时段
	 * 
	 * @param userId
	 * @param stockCode
	 * @param expectDate
	 * @param periodType
	 * @return
	 */
	boolean isPublished(Long userId, String stockCode, Date expectDate, String periodType);

	/**
	 * 是否关注过该股票
	 * 
	 * @param userId
	 * @param stockCode
	 * @return
	 */
	boolean isAttention(Long userId, String stockCode);

	/**
	 * 新增关注
	 * 
	 * @param mgrAttention
	 */
	void createAttention(MgrAttention mgrAttention);

	/**
	 * 扣减用户金币
	 * 
	 * @param userId
	 * @param coinNum
	 * @return
	 */
	int subtractCoin(Long userId, long coinNum);

	/**
	 * 创建预测记录
	 * 
	 * @param mgrForecastLog
	 * @return
	 */
	int createForecastLog(MgrForecastLog mgrForecastLog);

	/**
	 * 创建用户金币明细
	 * 
	 * @param userCoinDetail
	 */
	void createCoinDetail(UserCoinDetail userCoinDetail);

	/**
	 * 更新pk场次
	 * 
	 * @param forecastId
	 */
	void updatePKNumId(int forecastId);

	/**
	 * 根据Id查询预测记录
	 * 
	 * @param forecastId
	 * @return
	 */
	MgrForecastLog getForecastById(int forecastId);

	/**
	 * 创建Pk记录
	 * 
	 * @param pkLog
	 * @return
	 */
	int createPkLog(MgrPkLog pkLog);

	/**
	 * 奖励金币
	 * 
	 * @param userId
	 * @param coinNum
	 */
	void addCoin(Long userId, long coinNum);

	/**
	 * 获取当日某类型金币总数
	 * 
	 * @param userId
	 * @param transType
	 * @return
	 */
	long getTodayInvitePkReward(Long userId, String transType);

	/**
	 * 是否参与本场pk
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
	 * 创建预测笔记
	 * 
	 * @param appForecastNotes
	 */
	void createNote(AppForecastNotes appForecastNotes);

	/**
	 * 修改预测笔记
	 * 
	 * @param noteId
	 * @param noteContent
	 */
	void updateNote(String noteId, String noteContent);

	/**
	 * 删除预测笔记
	 * 
	 * @param noteId
	 * @param noteContent
	 */
	void deleteNote(String noteId);

	/**
	 * 更新笔记数
	 * 
	 * @param forcastId
	 * @param num
	 */
	void updateNoteNum(String forcastId, int num);

}
