/**
 * 
 */
package com.zz91.top.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.top.app.domain.AdminUser;
import com.zz91.top.app.domain.SessionUser;
import com.zz91.top.app.persist.AdminUserMapper;
import com.zz91.top.app.service.AdminUserService;
import com.zz91.top.app.utils.AppConst;

import com.zz91.util.encrypt.MD5;

/**
 * @author mays
 *
 */
@Component("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

	@Resource
	private AdminUserMapper adminUserMapper;
	
	@Override
	public AdminUser loginCheck(String username, String originPassword) {
		String password = "";
		try {
			password = MD5.encode(originPassword, MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		AdminUser user=new AdminUser();
		user.setUsername(username);
		user.setPassword(password);
		Integer i=adminUserMapper.countUser(user);
		if(i!=null && i.intValue()>0){
			return user;
		}
		return null;
	}

	@Override
	public SessionUser buildSessionUser(AdminUser adminUser) {
		
		SessionUser sessionUser = new SessionUser();
		sessionUser.setTaobaoUserId("");
		sessionUser.setTaobaoUserNick(adminUser.getUsername());
		sessionUser.setAccessToken("");
		sessionUser.setAuthFlag(AppConst.AUTH_FLAG);
		
		return sessionUser;
	}

}
