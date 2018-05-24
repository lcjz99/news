package com.pcaifu.service.stock.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paicaifu.news.entity.AppForecastNotes;
import com.paicaifu.news.entity.MgrAttention;
import com.paicaifu.news.entity.MgrForecastLog;
import com.paicaifu.news.entity.MgrPkLog;
import com.paicaifu.news.entity.UserCoinDetail;
import com.pcaifu.commonutils.base.JRedisTemplateTools;
import com.pcaifu.commonutils.common.utils.StringUtils;
import com.pcaifu.dao.stock.ForecastDao;
import com.pcaifu.dto.ForecastDto;
import com.pcaifu.dto.ForecastNoteDto;
import com.pcaifu.service.stock.ForecastService;

@Service
@Transactional
public class ForecastServiceImpl implements ForecastService {

	@Autowired
	private ForecastDao forecastDao;

	@Autowired
	private JRedisTemplateTools redisTemplate;

	@Override
	public Map<String, Object> getStockInfo(String stockCode) throws Exception {
		String url = "http://hq.sinajs.cn/list=" + stockCode;

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Call call = client.newCall(request);
		Response response = call.execute();
		String result = response.body().string();
		result = result.substring(result.indexOf("\"") + 1);
		result = result.substring(0, result.indexOf("\""));
		if (StringUtils.isBlank(result)) {
			throw new Exception("预测股票" + stockCode + "查询收盘价出错");
		}
		String[] tmpArr = result.split(",");
		Map<String, Object> retnMap = new HashMap<String, Object>();
		retnMap.put("stockName", tmpArr[0]);// 0：股票名字；
		retnMap.put("openingValue", new BigDecimal(tmpArr[1]));// 1：今日开盘价；
		retnMap.put("closingValue", new BigDecimal(tmpArr[2]));// 2：昨日收盘价；
		retnMap.put("currentValue", new BigDecimal(tmpArr[3]));// 3：当前价格；
		retnMap.put("stockStatus", tmpArr[32]);// 32：停牌状态；
		return retnMap;
	}

	@Override
	public long getTodayForecastTimes(String userId) {
		return forecastDao.getTodayForecastTimes(Long.valueOf(userId));
	}

	@Override
	public boolean isPublished(String userId, String stockCode, String expectDate, String periodType) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return forecastDao.isPublished(Long.valueOf(userId), stockCode, sdf.parse(expectDate), periodType);
	}

	@Override
	public int doPk(ForecastDto dto) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 添加预测记录
		MgrForecastLog forecastLog = new MgrForecastLog();
		forecastLog.setMflUserId(Long.valueOf(dto.getUserId()));
		forecastLog.setMflNickname(dto.getNickName());
		forecastLog.setMflMobile(dto.getMobile());
		forecastLog.setMflFromType(dto.getFromType());
		forecastLog.setMflPublishDate(new Date());
		forecastLog.setMflStockCode(dto.getStockCode());
		forecastLog.setMflStockName(dto.getStockName());
		forecastLog.setMflCurrentValue(new BigDecimal(dto.getCurrentValue()));
		forecastLog.setMflPeriodType(dto.getPeriodType());
		forecastLog.setMflForecastType(dto.getForecastType());
		// 猜涨跌的须预测价格
		if (StringUtils.isNotBlank(dto.getForecastValue())) {
			forecastLog.setMflForecastValue(new BigDecimal(dto.getForecastValue()));
		}
		forecastLog.setMflForecastCondition(dto.getForecastCond());
		if (StringUtils.isNotBlank(dto.getRefArticleId())) {
			forecastLog.setMflArticleId(Integer.parseInt(dto.getRefArticleId()));
		}

		if (StringUtils.isNotBlank(dto.getForecastNote())) {
			forecastLog.setMflNoteNum(1);
		}

		forecastLog.setMflExpectDate(sdf.parse(dto.getExpectDate()));
		forecastLog.setMflPrizeStatus("0");
		forecastLog.setMflNumId(Integer.valueOf(dto.getPkSerial()));
		int forecastId = forecastDao.createForecastLog(forecastLog);

		if (StringUtils.isNotBlank(dto.getForecastNote())) {
			forecastDao.createNote(new AppForecastNotes(forecastId, dto.getForecastNote()));
		}

		// 受邀Pk的需要创建pk记录
		MgrForecastLog tmpLog = forecastDao.getForecastById(Integer.valueOf(dto.getPkSerial()));
		MgrPkLog pkLog = new MgrPkLog();
		pkLog.setMplForecastId(tmpLog.getMflId());
		pkLog.setMplUserId(tmpLog.getMflUserId());
		pkLog.setMplNickname(tmpLog.getMflNickname());
		pkLog.setMplMobile(tmpLog.getMflMobile());
		pkLog.setMplFriendId(Long.valueOf(dto.getUserId()));
		pkLog.setMplFriendNickname(dto.getNickName());
		pkLog.setMplFriendMobile(dto.getMobile());
		pkLog.setMplFriendType(dto.getIsNew());
		pkLog.setMplFriendForecastId(forecastId);
		pkLog.setMplExpectDate(tmpLog.getMflExpectDate());
		pkLog.setMplStockCode(tmpLog.getMflStockCode());
		pkLog.setMplStockName(tmpLog.getMflStockName());
		pkLog.setMplPublishDate(new Date());
		int pkId = forecastDao.createPkLog(pkLog);

		String today = getToday();

		// 邀请PK发放规则：每成功邀请一名好友可获得10枚金币，每日邀请好友金币上限200枚
		if (redisTemplate.ctlDecrby("pk1005_" + tmpLog.getMflUserId() + "_" + today, 10)) {
			// 邀请pk奖励金币
			forecastDao.addCoin(tmpLog.getMflUserId(), 10);

			// 邀请pk奖励金币记录
			forecastDao.createCoinDetail(new UserCoinDetail(tmpLog.getMflUserId(), tmpLog.getMflNickname(), tmpLog
					.getMflMobile(), "1005", "1", 10, new Long(pkId)));
		}

		// 参与PK发放规则：每参与一场可获得10枚金币，每日获得参与金币上限100枚
		if (redisTemplate.ctlDecrby("pk1006_" + dto.getUserId() + "_" + today, 10)) {
			// 邀请pk奖励金币
			forecastDao.addCoin(Long.valueOf(dto.getUserId()), 10);

			// 参与pk奖励金币记录
			forecastDao.createCoinDetail(new UserCoinDetail(Long.valueOf(dto.getUserId()), dto.getNickName(), dto
					.getMobile(), "1006", "1", 10, new Long(pkId)));
		}

		return forecastId;
	}

	@Override
	public int doForecast(ForecastDto dto) throws ParseException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 扣减用户金币
		if (forecastDao.subtractCoin(Long.valueOf(dto.getUserId()), 10) != 1) {
			throw new Exception("用户" + dto.getUserId() + "金币扣减时发生乐观锁异常");
		}

		// 添加预测记录
		MgrForecastLog log = new MgrForecastLog();
		log.setMflUserId(Long.valueOf(dto.getUserId()));
		log.setMflNickname(dto.getNickName());
		log.setMflMobile(dto.getMobile());
		log.setMflFromType(dto.getFromType());
		log.setMflPublishDate(new Date());
		log.setMflStockCode(dto.getStockCode());
		log.setMflStockName(dto.getStockName());
		log.setMflCurrentValue(new BigDecimal(dto.getCurrentValue()));
		log.setMflPeriodType(dto.getPeriodType());
		log.setMflForecastType(dto.getForecastType());
		if (StringUtils.isNotBlank(dto.getForecastValue())) {
			log.setMflForecastValue(new BigDecimal(dto.getForecastValue()));
		}
		log.setMflForecastCondition(dto.getForecastCond());
		if (StringUtils.isNotBlank(dto.getRefArticleId())) {
			log.setMflArticleId(Integer.parseInt(dto.getRefArticleId()));
		}
		if (StringUtils.isNotBlank(dto.getForecastNote())) {
			log.setMflNoteNum(1);
		}
		log.setMflExpectDate(sdf.parse(dto.getExpectDate()));
		log.setMflPrizeStatus("0");
		int forecastId = forecastDao.createForecastLog(log);

		if (StringUtils.isNotBlank(dto.getForecastNote())) {
			forecastDao.createNote(new AppForecastNotes(forecastId, dto.getForecastNote()));
		}

		// 用户金币记录
		forecastDao.createCoinDetail(new UserCoinDetail(Long.valueOf(dto.getUserId()), dto.getNickName(), dto
				.getMobile(), "1003", "2", 10, new Long(forecastId)));

		return forecastId;
	}

	@Override
	public void doAttention(ForecastDto dto) {
		// 查询是否关注过
		String userId = dto.getUserId();
		String stockCode = dto.getStockCode();

		boolean flag = forecastDao.isAttention(Long.valueOf(userId), stockCode);
		if (!flag) {
			// 未关注则添加关注记录
			forecastDao.createAttention(new MgrAttention(Long.valueOf(userId), dto.getNickName(), dto.getMobile(), "2",
					stockCode, dto.getStockName(), "1"));
		}

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

	/*
	 * 是否参与本场pk
	 */
	@Override
	public boolean isPkJoined(String userId, String forecastId) {
		return forecastDao.isPkJoined(userId, forecastId);
	}

	@Override
	public boolean isPkPublished(String userId, String pkSerial) {
		return forecastDao.isPkPublished(userId, pkSerial);
	}

	@Override
	public void doForecastNote(ForecastNoteDto dto) {
		String optType = dto.getOptType();
		if ("1".equals(optType)) {
			// 创建
			forecastDao.createNote(new AppForecastNotes(Integer.valueOf(dto.getForcastId()), dto.getNoteContent()));
			forecastDao.updateNoteNum(dto.getForcastId(), 1);
		} else if ("2".equals(optType)) {
			// 更新
			forecastDao.updateNote(dto.getNoteId(), dto.getNoteContent());
		} else if ("3".equals(optType)) {
			forecastDao.deleteNote(dto.getNoteId());
			forecastDao.updateNoteNum(dto.getForcastId(), -1);
		}

	}

}
