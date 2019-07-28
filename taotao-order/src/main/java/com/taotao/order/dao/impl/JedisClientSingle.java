package com.taotao.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.order.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient {

	@Autowired
	private JedisPool jedispool;

	@Override
	public String get(String key) {
		Jedis jedis = jedispool.getResource();
		String string = jedis.get(key);
		System.out.println(string);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedispool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hGet(String hKey, String key) {
		Jedis jedis = jedispool.getResource();
		String string = jedis.hget(hKey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hSet(String hKey, String key, String value) {
		Jedis jedis = jedispool.getResource();
		Long result = jedis.hset(hKey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedispool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int seconds) {
		Jedis jedis = jedispool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedispool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis = jedispool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hDel(String hKey, String key) {
		Jedis jedis = jedispool.getResource();
		Long result = jedis.hdel(hKey, key);
		jedis.close();
		return result;
	}

}
