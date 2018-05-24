package com.pcaifu.dao.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.commonutils.base.JpaBaseDao;
import com.pcaifu.dao.user.UserDao;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2017年11月8日 上午11:10:25  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	JpaBaseDao jpaBaseDao;

	@Override
	public UserInfo queryUserInfo(String phone) {

		String hql = "  from UserInfo where isactive=1 and uiRegMobile=?";
		return (UserInfo) jpaBaseDao.findSingle(hql, new Object[] { phone });
	}
}
