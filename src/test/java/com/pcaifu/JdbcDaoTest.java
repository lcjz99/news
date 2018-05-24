package com.pcaifu;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.paicaifu.news.entity.AppMsgInfo;
import com.paicaifu.news.entity.AppMsgPushDevice;
import com.pcaifu.commonutils.base.JRedisTemplateTools;
import com.pcaifu.commonutils.base.JpaBaseDao;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍财富
 * Author:		Administrator
 * Version:		1.0  
 * Create at:	2017年7月11日 上午14:35:42  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Transactional
public class JdbcDaoTest extends TestBaseCase {
	private static final Logger logger = LoggerFactory.getLogger(JdbcDaoTest.class);
	@Autowired
	private JpaBaseDao jpaBaseDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void test1() {
		String hql="from AppMsgPushDevice where  isactive=1";
		List<AppMsgPushDevice> list= jpaBaseDao.listByHql(hql, null);
		for (AppMsgPushDevice pushApp : list) {
			System.out.println(pushApp.getAmpdDeviceName());
		}
	}
	
	@Test
	public void test2() {
		String hql = "from AppMsgInfo where isactive=1 and amiId=? ";
		String pushId="672";
		AppMsgInfo app = jpaBaseDao.findSingle(hql, Long.valueOf(pushId));
			System.out.println(app.getAmiContent());
	}

	@Autowired
	private JRedisTemplateTools redisTemplate;

	@Test
	public void test15() {
		redisTemplate.set("go", "哈哈", 123);
		System.out.println("------" + redisTemplate.get("go"));
	}
}