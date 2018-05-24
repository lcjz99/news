package com.pcaifu.commonutils.base;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paicaifu.news.TableName;

@Repository
public class JpaBaseDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	/*
	 * 
	 * 保存PO
	 */
	public Serializable save(Object entity) {
		return (Serializable) getSession().save(entity);
	}

	/*
	 * 
	 * 保存或更新PO
	 */
	public void saveOrUpdate(Object entity) {
		getSession().saveOrUpdate(entity);
	}

	/*
	 * 
	 * 更新PO
	 */
	public void update(Object entity) {
		getSession().update(entity);
	}

	/*
	 * 
	 * 根据id删除PO
	 */
	public void delete(Serializable id, Class clazz) {
		getSession().delete(this.get(id, clazz));
	}

	/*
	 * 
	 * 删除PO
	 */
	public void deleteObject(Object entity) {
		getSession().delete(entity);
	}

	/*
	 * 
	 * 根据id判断PO是否存在
	 */
	public boolean exists(Serializable id, Class clazz) {
		return get(id, clazz) != null;
	}

	/*
	 * 
	 * 根据id加载PO
	 */
	// @SuppressWarnings("unchecked")
	// public <T> T load(Serializable id, Class<T> clazz) {
	// // return getSession().load(clazz, id);
	// }

	/*
	 * 
	 * 根据id获取PO
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Serializable id, Class<T> clazz) {
		return (T) getSession().get(clazz, id);
	}

	/**
	 * 创建一个Criteria 实例
	 * 
	 * @param cl
	 * @return
	 */
	public Criteria getCriteria(Class cl) {
		return getSession().createCriteria(cl);
	}

	/**
	 * 获取单表全部数据
	 * 
	 * @param criteria
	 * @return
	 */
	public List list(Criteria criteria) {
		return criteria.list();
	}

	/*
	 * 示例程序 根据Criteria查询条件，获取PO总数,
	 */
	public int countAll(Criteria criteria) {
		return Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
	}

	public Object getSingleBySql(CharSequence queryString, Object[] values) {
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		setParameter(query, values);
		@SuppressWarnings("rawtypes")
		List list = query.setMaxResults(1).list();
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@SuppressWarnings("hiding")
	public List listByHql(CharSequence queryString, Object[] values) {
		Query query = getSession().createQuery(queryString.toString());
		setParameter(query, values);
		return query.list();
	}

	/**
	 * 利用原生sql查询，查询字段名或者别名需要跟bean中属性一一对应。
	 *
	 * @param cl
	 * @param queryString
	 * @param values
	 * @return
	 */
	public <T> List<T> listBySql(Class<T> cl, CharSequence queryString, Object[] values) {
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		setParameter(query, values);
		AddScalar.addSclar(query, cl);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	public List listBySql(CharSequence queryString, Object[] values) {
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		for (int i = 0; i < values.length; ++i) {
			query.setParameter(i, values[i]);
		}
		return query.list();
	}

	/**
	 * 批量更新或者插入
	 *
	 * @param sql
	 * @param bpss
	 */
	public void batchUpdate(String sql, BatchPreparedStatementSetter bpss) {
		jdbcTemplate.batchUpdate(sql, bpss);
	}

	/*
	 * 
	 * 强制清空session
	 */
	public void flush() {
		getSession().flush();
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized Long generatorKey(TableName tableName) {
		String sql1 = "select nextval('" + tableName + "')";
		Integer nextval = jdbcTemplate.queryForObject(sql1, Integer.class);
		String ymd = sdf.format(new Date());
		// 查询记录
		if (null == nextval || 0 == nextval.intValue()) {
			// 创建记录
			nextval = 10000001;
			String sql2 = "insert into sequence(id,name) value(" + nextval + ",'" + tableName + "')";
			jdbcTemplate.execute(sql2);
		}
		return Long.parseLong(ymd + nextval);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T findSingle(final String hql, final Object... values) {
		Query query = getSession().createQuery(hql.toString());
		setParameter(query, values);
		query.setMaxResults(1);
		List list = query.list();
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		} else {
			return null;
		}

	}

	/*
	 * 
	 * 创建Criteria实例
	 */

	public Criteria createCriteria(Class entityClass) {
		return getSession().createCriteria(entityClass);
	}

	/**
	 * 根据id加载PO
	 */
	@SuppressWarnings("unchecked")
	public <T> T load(Serializable id, Class<T> clazz) {
		return getSession().load(clazz, id);
	}

	/**
	 * 查询单列，eg. 总数，平均值
	 */
	public Object getSingleColumnBySql(CharSequence queryString, Object[] values) {
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		setParameter(query, values);
		return query.uniqueResult();
	}

	/**
	 * 查询单列，eg. 总数，平均值
	 */
	public Object getSingleColumnByHql(CharSequence queryString, Object[] values) {
		Query query = getSession().createQuery(queryString.toString());
		setParameter(query, values);
		return query.uniqueResult();
	}

	/**
	 * 查询单条记录 查询列名的别名跟实体必须完全一致
	 */
	public <T> T getSingleBySql(Class<T> clazz, CharSequence queryString, Object[] values) {
		SQLQuery query = getSession().createSQLQuery(queryString.toString());
		setParameter(query, values);
		query.setMaxResults(1);
		AddScalar.addSclar(query, clazz);
		List<T> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取单个实体，根据查询语句及参数获取。 查询列名的别名跟实体必须完全一致（如 select a.uid as uid form Admin as
	 * a , AdminRole as ar where a.uid=ar.adminId and a.uid=?
	 */
	public <T> T getSingleByHql(Class<T> clazz, CharSequence queryString, Object... params) {
		Query query = getSession().createQuery(queryString.toString());
		setParameter(query, params);
		query.setMaxResults(1);
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		List<T> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取实体列表，根据查询语句及参数获取。 查询列名的别名跟实体必须完全一致（如 select a.uid as uid form Admin as
	 * a , AdminRole as ar where a.uid=ar.adminId and a.uid=?
	 */
	public <T> List<T> listByHql(Class<T> cl, CharSequence queryString, Object... params) {
		Query query = getSession().createQuery(queryString.toString());
		setParameter(query, params);
		query.setResultTransformer(Transformers.aliasToBean(cl));
		return query.list();
	}

	@SuppressWarnings({ "unchecked" })
	public <T> Pagination<T> paginationByHql(Class<T> clazz, CharSequence queryString, int pageIndex, int pageSize,
			Object... params) {
		Query query = getSession().createQuery(queryString.toString());

		if ((pageSize > 0) && (pageIndex > 0)) {
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageIndex * pageSize);
		}

		setParameter(query, params);
		query.setResultTransformer(Transformers.aliasToBean(clazz));

		List<T> items = query.list();
		Long rowsCount = (Long) getSingleColumnByHql(getCountStr(queryString.toString()), params);
		Pagination<T> pagination = new Pagination((long) pageIndex, (long) pageSize, rowsCount);
		pagination.setItems(items);
		return pagination;
	}

	/**
	 * 返回指定对象数据集合，查询字段名或者别名与指定对象字段一致。
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> Pagination<T> paginationBySql(Class<T> clazz, final CharSequence queryString, final Object[] values,
			int pageIndex, int pageSize) {
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
		if ((pageSize > 0) && (pageIndex > 0)) {
			sqlQuery.setFirstResult((pageIndex - 1) * pageSize);
			sqlQuery.setMaxResults(pageIndex * pageSize);
		}
		setParameter(sqlQuery, values);
		AddScalar.addSclar(sqlQuery, clazz);
		List<T> items = sqlQuery.list();
		BigInteger rowsCount = (BigInteger) getSingleColumnBySql(getCountStr(queryString.toString()), values);

		Pagination<T> pagination = new Pagination((long) pageIndex, (long) pageSize, rowsCount.longValue());
		pagination.setItems(items);
		return pagination;
	}

	/**
	 * native sql excute
	 */
	public int executeUpdateSql(String sql, final Object[] values) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		setParameter(sqlQuery, values);
		return sqlQuery.executeUpdate();
	}

	/**
	*/
	public int executeUpdateHql(String hql, final Object[] values) {
		Query query = getSession().createQuery(hql);
		setParameter(query, values);
		return query.executeUpdate();
	}

	/**
	 * 清空session
	 */

	public void clear() {
		getSession().clear();
	}

	private String getCountStr(String queryStr) {
		StringBuilder countSql = new StringBuilder("select count(1) ")
				.append(queryStr.substring(queryStr.toLowerCase().indexOf("from")));
		return countSql.toString();
	}

	private Query setParameter(SQLQuery query, Object[] values) {
		if (values == null || values.length == 0) {
			return query;
		}
		int i = 0;
		for (Object value : values) {
			query.setParameter(i, value);
			i++;
		}
		return query;
	}

	private Query setParameter(Query query, Object[] values) {
		if (values == null || values.length == 0) {
			return query;
		}
		int i = 0;
		for (Object value : values) {
			query.setParameter(i, value);
			i++;
		}
		return query;
	}

}
