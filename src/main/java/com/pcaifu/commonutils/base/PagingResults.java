package com.pcaifu.commonutils.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页结果
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2015  
 * Company:		拍拍贷
 * Author:		denghongbing
 * Version:		1.0  
 * Create at:	2015年9月7日 下午2:57:53  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class PagingResults<T> implements Serializable {

	private static final long serialVersionUID = -5796589303939138685L;

	/**
	 * 分页结果
	 */
	private List<T> list = null;

	/**
	 * 参数列表
	 */
	private Map<String, Object> params = new LinkedHashMap<String, Object>();

	/**
	 * 分页相关
	 */
	private Paging paging = new Paging();

	/**
	 * 汇总信息
	 */
	private Map<String, Object> summary = new LinkedHashMap<String, Object>();

	public PagingResults() {

	}

	public PagingResults(Paging paging) {
		super();
		if (null != paging) {
			this.paging = paging;
		}
	}

	/**
	 * @return the {@link #list}
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the {@link #list} to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * @return the {@link #params}
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the {@link #params} to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * @return the {@link #paging}
	 */
	public Paging getPaging() {
		return paging;
	}

	/**
	 * @param paging
	 *            the {@link #paging} to set
	 */
	public void setPaging(Paging paging) {
		if (null != paging) {
			this.paging = paging;
		}
	}

	/**
	 * @return the {@link #summary}
	 */
	public Map<String, Object> getSummary() {
		return summary;
	}

	/**
	 * @param params
	 *            the {@link #summary} to set
	 */
	private void setSummary(Map<String, Object> summary) {
		this.summary = summary;
	}

}
