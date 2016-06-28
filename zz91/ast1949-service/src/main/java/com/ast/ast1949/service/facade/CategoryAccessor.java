package com.ast.ast1949.service.facade;

public interface CategoryAccessor {

	String ACCESS_FROM_MEMCACHE = "memcached";
	String ACCESS_FROM_MEMORY = "memory";
	
	public void putObject(String key, Object value);
	
	public Object holdObject(String key);
}
