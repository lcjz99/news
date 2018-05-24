package com.pcaifu.service.user;

import com.paicaifu.news.entity.UserInfo;
import com.pcaifu.dto.UserReqDto;

/**
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2017年9月5日 上午10:28:40  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public interface UserInfoService {

	UserInfo queryUserInfo(String phone);

	UserInfo doSaveUser(UserReqDto dto);

	void doSaveGoldCoinInfo(UserInfo user);

}
