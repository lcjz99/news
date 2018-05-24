package com.pcaifu.commonutils.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pcaifu.commonutils.common.utils.GsonUtils;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2017  
 * Company:		拍拍贷
 * Author:		Administrator
 * Version:		1.0  
 * Create at:	2017年7月11日 上午11:30:06  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Repository
@SuppressWarnings("rawtypes")
@Transactional
public class OpenDaoImpl implements OpenDao {

	private static final Logger log = LoggerFactory.getLogger(OpenDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EntityManager em;

	/**
	 * 获得查询的结果总数
	 * 
	 * @param conn
	 * @param originalSql
	 * @return
	 */
	@Override
	public int getTotalCount(final String sql, final Object[] values) {
		return jdbcTemplate.queryForObject(sql, values, Integer.class);
	}

	@Override
	public int execute(final String sql, final List<Object> params) {
		return jdbcTemplate.update(sql, params.toArray());
	}

	@Override
	public int execute(final String sql) {
		return jdbcTemplate.update(sql);
	}

	/**
	 * // 执行sql语句返回字符串值
	 */
	@Override
	public String getCommaSQL(String sql) {
		List<String> list = jdbcTemplate.queryForList(sql, String.class);

		if (list.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1);
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public <T> T query(String sql, List<Object> params, Class<T> clazz) {
		// 参数解析
		Object[] args = new Object[params.size()];
		int i = 0;
		for (Object o : params) {
			args[i] = o;
			i++;
		}
		List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql, args);

		if (null == listMap || listMap.isEmpty()) {
			return null;
		}
		try {
			// TODO 更好的实现 将查出来的对象转成实体
			String json = GsonUtils.getJson(listMap.get(0));
			return GsonUtils.fromJson(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Please checked SQL field VS Bean field..");
		}
	}

	@Override
	public void updateObject(final Object entity) {
		// 用来存放sql语句
		StringBuffer sql = new StringBuffer();

		// 用来存放参数值
		List<Object> params = new ArrayList<Object>();

		sql.append("update ");
		Table table = entity.getClass().getAnnotation(Table.class);
		// 获取表名
		if (null == table) {
			throw new RuntimeException("不是Entity对象");
		}
		// 获取注解的表名
		sql.append(table.name());
		sql.append(" set ");

		Object idParam = null;
		String idColumn = null;
		int count = 0;// 计算“，”
		try {
			// 分析列
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}

				// getter method
				Method getter = getGetter(entity.getClass(), fieldName);

				if (getter == null) {
					// 没有get method 的跳过
					continue;
				}

				Column column = getter.getAnnotation(Column.class);
				if (null == column) {
					// 没有列注解的直接跳过
					continue;
				}
				Object value = getter.invoke(entity);
				if (value == null) {
					// 如果参数值是null就直接跳过（不允许覆盖为null值，规范要求更新的每个字段都要有值，没有值就是空字符串）
					continue;
				}
				if (count != 0) {
					sql.append(",");
				}
				// 字段名
				sql.append(column.name());
				sql.append("= ?");
				// 值
				params.add(value);
				// 查找主键
				Id id = getter.getAnnotation(Id.class);
				if (null != id) {
					idColumn = column.name();
					// 主键值
					idParam = value;
				}
				count++;
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}

		sql.append(" where ");
		sql.append(idColumn);
		sql.append(" = ?");
		params.add(idParam);

		Object[] objects = params.toArray();

		// 是debug的才执行
		if (log.isDebugEnabled()) {
			log.debug("params:" + Arrays.toString(objects));
			log.debug("update sql:" + sql.toString());
		}

		// 返回受影响的行数
		jdbcTemplate.update(sql.toString(), params.toArray(objects));
	}

	/**
	 * 获取属性的getter方法
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	private <T> Method getGetter(Class<T> clazz, String fieldName) {

		String getterName = "get" + firstCharToUpperCase(fieldName);
		Method getter = null;
		try {
			getter = clazz.getMethod(getterName);
		} catch (Exception e) {
			log.debug(getterName + " doesn't exist!", e);
		}
		return getter;
	}

	public String firstCharToUpperCase(String string) {
		if (null != string) {
			int length = string.length();
			// 获取第一个转大写
			String first = string.substring(0, 1);
			first = first.toUpperCase();

			// 判断字符长度
			if (1 == length) {
				return first;
			}

			String other = string.substring(1, length);
			return first + other;
		}
		return string;
	}

	@Override
	public <T> T findSingle(String sql, Object... values) {
		return (T) jdbcTemplate.queryForObject(sql, Object.class, values);
	}

}
