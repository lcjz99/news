package com.pcaifu.commonutils.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.pcaifu.commonutils.common.utils.StringUtils;

/**
 * 
 * <pre>
 * Description
 * Copyright:	Copyright (c) 2018  
 * Company:		拍财富
 * Author:		lichao
 * Version:		1.0  
 * Create at:	2018年1月16日 下午1:38:11  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@Component
public class JRedisTemplateTools {
	private static final Logger LOGGER = LoggerFactory.getLogger(JRedisTemplateTools.class);

	@Autowired
	private JedisCluster jedisCluster;

	public long llen(String key) {
		return jedisCluster.llen(key);
	}

	public List<String> lrange(String key, int start, long end) {
		return jedisCluster.lrange(key, start, end);
	}

	public String get(String key) {
		return jedisCluster.get(key);
	}

	public void set(String key, String value, int seconds) {
		jedisCluster.set(key, value);
		if (seconds > 0) {
			jedisCluster.expire(key, seconds);
		}
	}

	public long sadd(String key, String value) {
		return jedisCluster.sadd(key, value);
	}

	public long srem(String key, String value) {
		return jedisCluster.srem(key, value);
	}

	public long rpush(String key, String value) {
		return jedisCluster.rpush(key, value);
	}

	public boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	public void expire(String key, int seconds) {
		jedisCluster.expire(key, seconds);
	}

	public boolean ctlDecrby(String key, long value) {
		String tmpVal = jedisCluster.get(key);
		if (StringUtils.isBlank(tmpVal) || Long.parseLong(tmpVal) < value) {
			return false;
		}
		long result = jedisCluster.decrBy(key, value);
		if (result < 0) {
			result = jedisCluster.incrBy(key, value);
			return false;
		}
		return true;

	}

	public boolean incrby(String key, long value) {
		if (StringUtils.isBlank(jedisCluster.get(key))) {
			return false;
		}
		jedisCluster.incrBy(key, value);
		return true;
	}

	public void set(byte[] key, byte[] value) {
		jedisCluster.set(key, value);
	}

	public byte[] get(byte[] key) {
		return jedisCluster.get(key);
	}

	public void batchDel(String patternkey) {
		TreeSet<String> set = keys(patternkey);
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String keyStr = it.next();
			// System.out.println(keyStr);
			jedisCluster.del(keyStr);
		}
	}

	private TreeSet<String> keys(String pattern) {
		TreeSet<String> keys = new TreeSet<String>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
			} finally {
				connection.close();// 用完一定要close这个链接！！！
			}
		}
		return keys;
	}

	public void lrem(String key, int count, String value) {
		jedisCluster.lrem(key, count, value);
	}

	public void lpop(String key) {
		jedisCluster.lpop(key);
	}

	public void del(String key) {
		jedisCluster.del(key);

	}

}