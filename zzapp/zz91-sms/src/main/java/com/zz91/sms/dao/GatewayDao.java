package com.zz91.sms.dao;

import java.util.List;

import com.zz91.sms.domain.Gateway;

public interface GatewayDao {
	
	public Integer insert(Gateway gateway);
	
	public Integer update(Gateway gateway);
	
	public Integer delete(Integer id);
	
	public Integer updateEnabled(Integer id, Integer status);
	
	public List<Gateway> query(Integer enabled);
	
	public Gateway queryOne(Integer id);
	
}
