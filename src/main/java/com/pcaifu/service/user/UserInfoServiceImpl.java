package com.pcaifu.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paicaifu.news.TableName;
import com.paicaifu.news.entity.UserCoinDetail;
import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.commonutils.base.JpaBaseDao;
import com.pcaifu.commonutils.common.constants.Constants;
import com.pcaifu.commonutils.common.utils.StringUtils;
import com.pcaifu.dao.user.UserDao;
import com.pcaifu.dto.UserReqDto;

/**
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2017年9月5日 上午10:29:12  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private JpaBaseDao basedao;
	@Autowired
	private UserDao userDao;

	@Override
	public UserInfo queryUserInfo(String phone) {
		return userDao.queryUserInfo(phone);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserInfo doSaveUser(UserReqDto dto) {
		UserInfo info = new UserInfo();
		try {
			info.setUiUserId(Long.valueOf(basedao.generatorKey(TableName.USER_INFO)));
			info.setUiRegMobile(dto.getPhone());
			info.setUiTerminalType(dto.getTerminalType());
			info.setUiHeadpicLink(dto.getHeadpicLink());
			info.setUiGoldcoinNum(dto.getGoldcoinNum());
			info.setUiNickname("股宝" + StringUtils.getRamdomCode());
			basedao.save(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void doSaveGoldCoinInfo(UserInfo user) {
		UserCoinDetail detail = new UserCoinDetail();
		detail.setUcdUserId(user.getUiUserId());
		detail.setUcdCoinNum(user.getUiGoldcoinNum().intValue());
		detail.setUcdMobile(user.getUiRegMobile());
		detail.setUcdType(com.pcaifu.commonutils.common.constants.Constants.COIN_TYPE.COIN_TYPE_1001);
		detail.setUcdBalanceType(Constants.BALANCE_TYPE.BALANCE_TYPE_IN);
		detail.setUcdNickname(user.getUiNickname());
		detail.setUcdTradeId(user.getUiUserId());
		basedao.save(detail);
	}
}
