/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-15
 */
package com.ast.ast1949.persist.auth;

import com.ast.ast1949.domain.auth.AuthForgotPassword;
import com.ast.ast1949.domain.auth.AuthUser;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface UserDao {

	/**
	 * 通过email查询到用户信息
	 * @param email:不可以为null
	 * @return null表示没有对应的用户信息
	 */
	public AuthUser queryUserByEmail(String email);
	
	public Integer generateForgotPasswordKey(AuthForgotPassword obj);

	/**
	 * 检查找回密码key是否存在
	 * @param key
	 * @return
	 */
	public AuthForgotPassword listAuthForgotPasswordByKey(String key);

	/**
	 * 当key使用完后,及时销毁
	 * @param key
	 */
	public void deleteAuthForgotPasswordByKey(String key);
	
	public Integer updatePasswordByUsername(String username, String password);

	/**
	 * 通过用户账户查询到用户信息
	 * @param username:用户账户,不可以为null
	 * @return null表示没有对应的用户信息
	 */
	public AuthUser queryUserByUsername(String username);
	
	
	public Integer updateAccount(String oldAccount, String account);
	
	public Integer countUserByAccount(String account);
	
	public Integer countUserByEmail(String email);
	
	public Integer insertUser(AuthUser user);
	
	public Integer updateSteping(Integer id, Integer steping);
	
	public Integer countUserByEmailLogin(String email, String password);
	
	public Integer countUserByAccountLogin(String username, String password);
	
	public String queryAccountByEmail(String email);
	
	public String queryPassword(String username);

	public String validateByEmail(String username, String password);
	public String validateByUsername(String username, String password);
	public String validateByAccount(String username, String password);

	public Integer updateEmail(String username,String email);
	
	public Integer countUserByMobile(String mobile);
	
	Integer deleteById(Integer id);
	
	/**
	 * 根据电话号码是检索 账户登录
	 * @param mobile
	 * @return
	 */
	public AuthUser queryUserByMobile(String mobile);
	
	/**
	 * 更新手机号码
	 * @param account
	 * @param mobile
	 * @return
	 */
	public Integer updateMobile(String account,String mobile);

	/**
	 * 验证手机登录
	 * @param mobile
	 * @param password
	 * @return
	 */
	String validateByMobile(String mobile, String password);
}