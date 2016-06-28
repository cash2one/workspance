/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.service.facade;

public interface CategoryAccessor {

	String ACCESS_FROM_MEMCACHE = "memcached";
	String ACCESS_FROM_MEMORY = "memory";
	
	public void putObject(String key, Object value);
	
	public Object holdObject(String key);
}
