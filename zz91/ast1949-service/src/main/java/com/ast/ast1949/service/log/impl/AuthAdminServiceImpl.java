package com.ast.ast1949.service.log.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.log.AuthAdminDao;
import com.ast.ast1949.service.log.AuthAdminService;

@Component("authAdminService")
public class AuthAdminServiceImpl implements AuthAdminService {
	@Resource
	private AuthAdminDao authAdminDao;

	@Override
	public Integer insertAuthAdmin(String account) {
		return authAdminDao.insertAuthAdmin(account);
	}
}
