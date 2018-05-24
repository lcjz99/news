package com.pcaifu.dao.stock.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paicaifu.news.entity.AppForecastNotes;
import com.paicaifu.news.entity.MgrAttention;
import com.paicaifu.news.entity.MgrForecastLog;
import com.paicaifu.news.entity.MgrPkLog;
import com.paicaifu.news.entity.UserCoinDetail;
import com.pcaifu.commonutils.base.JpaBaseDao;
import com.pcaifu.dao.stock.ForecastDao;

@Repository
public class ForecastDaoImpl implements ForecastDao {

	@Autowired
	private JpaBaseDao jpaBaseDao;

	@Override
	public long getTodayForecastTimes(Long userId) {
		return jpaBaseDao
				.findSingle(
						"select count(*) as recCount from MgrForecastLog where mflPublishDate>=CURRENT_DATE and mflUserId=? and mflFromType='1'",
						new Object[] { userId });
	}

	@Override
	public boolean isPublished(Long userId, String stockCode, Date expectDate, String periodType) {
		return (Long) jpaBaseDao
				.findSingle(
						"select count(*) as recCount from MgrForecastLog where mflExpectDate=? and mflUserId=? and mflStockCode=? and mflPeriodType=? and mflFromType='1'",
						new Object[] { expectDate, userId, stockCode, periodType }) > 0;
	}

	@Override
	public boolean isAttention(Long userId, String stockCode) {
		return (Long) jpaBaseDao.findSingle(
				"select count(*) as recCount from MgrAttention where maUserId=? and maStockCode=? and maStatus='1'",
				new Object[] { userId, stockCode }) > 0;
	}

	@Override
	public void createAttention(MgrAttention mgrAttention) {
		jpaBaseDao.save(mgrAttention);

	}

	@Override
	public int subtractCoin(Long userId, long coinNum) {
		return jpaBaseDao.executeUpdateHql(
				"update UserInfo set uiGoldcoinNum=uiGoldcoinNum-? where uiUserId=? and uiGoldcoinNum>=?",
				new Object[] { coinNum, userId, coinNum });
	}

	@Override
	public int createForecastLog(MgrForecastLog mgrForecastLog) {
		return (Integer) jpaBaseDao.save(mgrForecastLog);
	}

	@Override
	public void createCoinDetail(UserCoinDetail userCoinDetail) {
		jpaBaseDao.save(userCoinDetail);

	}

	@Override
	public void updatePKNumId(int forecastId) {
		jpaBaseDao.executeUpdateHql("update MgrForecastLog set mflNumId=? where mflId=?", new Object[] { forecastId,
				forecastId });

	}

	@Override
	public MgrForecastLog getForecastById(int forecastId) {
		return jpaBaseDao.get(forecastId, MgrForecastLog.class);
	}

	@Override
	public int createPkLog(MgrPkLog pkLog) {
		return (Integer) jpaBaseDao.save(pkLog);

	}

	@Override
	public void addCoin(Long userId, long coinNum) {
		jpaBaseDao.executeUpdateHql("update UserInfo set uiGoldcoinNum=uiGoldcoinNum+? where uiUserId=?", new Object[] {
				coinNum, userId });

	}

	@Override
	public long getTodayInvitePkReward(Long userId, String transType) {
		return jpaBaseDao
				.findSingle(
						"select count(*) as recCount from UserCoinDetail where inserttime>=CURRENT_DATE and ucdUserId=? and ucdType=?",
						new Object[] { userId, transType });
	}

	/*
	 * 是否参与本场pk
	 */
	@Override
	public boolean isPkJoined(String userId, String forecastId) {
		return (Long) jpaBaseDao
				.findSingle(
						"select count(*) as recCount from MgrForecastLog where mflUserId =? and  mflNumId=? and isactive='1'  ",
						new Object[] { Long.valueOf(userId), Integer.valueOf(forecastId) }) > 0;
	}

	@Override
	public boolean isPkPublished(String userId, String pkSerial) {
		return (Long) jpaBaseDao.findSingle(
				"select count(*) as recCount from MgrForecastLog where mflUserId =? and  mflId=? and isactive='1'  ",
				new Object[] { Long.valueOf(userId), Integer.valueOf(pkSerial) }) > 0;
	}

	@Override
	public void createNote(AppForecastNotes appForecastNotes) {
		jpaBaseDao.save(appForecastNotes);

	}

	@Override
	public void updateNote(String noteId, String noteContent) {
		jpaBaseDao.executeUpdateHql("update AppForecastNotes set afnNote=? where afnId=?", new Object[] { noteContent,
				Long.valueOf(noteId) });

	}

	@Override
	public void deleteNote(String noteId) {
		jpaBaseDao.executeUpdateHql("update AppForecastNotes set isactive=0 where afnId=?",
				new Object[] { Long.valueOf(noteId) });

	}

	@Override
	public void updateNoteNum(String forcastId, int num) {
		jpaBaseDao.executeUpdateHql("update MgrForecastLog set mflNoteNum=mflNoteNum+? where mflId=?", new Object[] {
				num, Long.valueOf(forcastId) });

	}
}
