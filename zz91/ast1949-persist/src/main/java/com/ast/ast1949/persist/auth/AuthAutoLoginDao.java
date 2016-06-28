package com.ast.ast1949.persist.auth;

import com.ast.ast1949.domain.auth.AuthAutoLogin;

public interface AuthAutoLoginDao {
	public AuthAutoLogin queryByCookie(String cookie);

	public Integer insert(AuthAutoLogin authAutoLogin);
	
	public Integer update(AuthAutoLogin authAutoLogin);
	
	public Integer delete(Integer id);
}
