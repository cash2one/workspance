package com.ast.feiliao91.service.auth.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.persist.auth.AuthUserDao;
import com.ast.feiliao91.service.auth.AuthUserService;

@Component("userService")
public class AuthUserServiceImpl implements AuthUserService{
	@Resource
	private AuthUserDao authUserDao;
	
	@Override
	public String queryAccountByEmail(String email){
		return authUserDao.queryAccountByEmail(email);
	}
}	
