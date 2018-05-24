package com.pcaifu.commonutils.base;

import java.util.List;

/**
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍拍贷
 * Author:		Administrator
 * Version:		1.0  
 * Create at:	2017年7月11日 上午11:27:54  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public interface OpenDao {

	int getTotalCount(String sql, Object[] values);

	int execute(String sql, List<Object> params);

	int execute(String sql);

	String getCommaSQL(String sql);

	public <T> T query(String sql, List<Object> params, Class<T> clazz);

	void updateObject(Object entity);

	public <T> T findSingle(String sql, Object[] values);

}
