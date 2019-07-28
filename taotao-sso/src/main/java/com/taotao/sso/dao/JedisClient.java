package com.taotao.sso.dao;

import java.io.IOException;

public interface JedisClient {
	String get(String key);
	String set(String key, String value);
	String hGet(String hKey, String key);
	long hSet(String hKey, String key, String value);
	long incr(String key);
	long expire(String key, int seconds);
	long ttl(String key);
	long del(String key);
	long hDel(String hKey, String key);
}
