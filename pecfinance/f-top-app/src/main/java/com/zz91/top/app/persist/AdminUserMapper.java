package com.zz91.top.app.persist;

import com.zz91.top.app.domain.AdminUser;

public interface AdminUserMapper {

	public Integer countUser(AdminUser user);
	public Integer updateLastLogin(String username);
	
}
