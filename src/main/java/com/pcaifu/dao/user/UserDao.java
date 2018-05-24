package com.pcaifu.dao.user;

import com.paicaifu.news.entity.UserInfo;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2017年11月8日 上午11:10:16  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public interface UserDao {

	UserInfo queryUserInfo(String phone);

}
