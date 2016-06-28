/**
 * 
 */
package com.zz91.top.app.service;

import com.zz91.top.app.domain.AdminUser;
import com.zz91.top.app.domain.SessionUser;

/**
 * @author mays
 *
 */
public interface AdminUserService {

	public AdminUser loginCheck(String username, String originPassword);
	
	public SessionUser buildSessionUser(AdminUser adminUser);
}
