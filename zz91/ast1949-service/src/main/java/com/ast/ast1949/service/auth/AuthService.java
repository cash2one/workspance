/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-11
 */
package com.ast.ast1949.service.auth;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.ast.ast1949.domain.auth.AuthForgotPassword;
import com.ast.ast1949.domain.auth.AuthUser;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface AuthService {

	/**
	 * 通过用户名查询用户权限
	 * @param username:用户账户,不可以为null或"",否则抛出异常
	 * @return
	 */
//	public List<AuthRight> listUserRight(String username);
//
//	/**
//	 * 查询用户的所有权限信息,并仅返回规则列表
//	 * @param username:用户账户,不可以为null或""
//	 * @return
//	 */
//	public List<String> listUserRightContent(String username);
//
//	/**
//	 * 查询某个用户的某个权限的子权限,或查询某个权限下的子权限
//	 * @param parentRight:父节点编号,为null时表示根节点
//	 * @param username:用户名为null或""时,返回父权限ID等于parentRight的所有子权限
//	 * @return
//	 */
//	public List<AuthRight> listUserRight(Integer parentRight, String username);
//
//	/**
//	 * 更改密码
//	 * @param username:要更改密码的用户账户,不可以为null或""
//	 * @param originalPassword:原始密码,不可以为null或""
//	 * @param newPassword:符合密码规则的新密码,不可以为null或""
//	 * @param verifyPassword:确认新密码,与newPassword一样
//	 */
//	public void changePassword(String username, String originalPassword, String newPassword, String verifyPassword)
//		throws AuthorizeException, NoSuchAlgorithmException, UnsupportedEncodingException;
//
//	/**
//	 * @param username:可以是邮箱的格式,也可以是账号的形式,不可以为null
//	 * @param password:不可以为null或""
//	 * @throws AuthorizeException
//	 * @throws NoSuchAlgorithmException
//	 * @throws UnsupportedEncodingException
//	 */
//	public void validateUser(String username, String password)
//		throws AuthorizeException, NoSuchAlgorithmException, UnsupportedEncodingException;
//
//	/**
//	 * 通过用户名查询账户信息
//	 * @param username:用户名,不能为null和""
//	 * @return
//	 */
//	public AuthUser listOneUserByUsername(String username);
//	
//	public AuthUser queryUserByUsernameOrAccount(String account);
//
//	/**
//	 * 通过email查询账户信息
//	 * @param email:不能为null和""
//	 * @return
//	 */
//	public AuthUser listOneUserByEmail(String email);
	
	/******************以下代码有用****************/

	/**
	 * 生成密码找回key,并保存到数据库
	 * @return 生成的key
	 */
	public String generateForgotPasswordKey(String email);
	
	//插入手机验证码并返回
	public String generateForgotPasswordMobileKey(String email);

	/**
	 * 检查找回密码key是否存在
	 * @param key
	 * @return
	 */
	public AuthForgotPassword listAuthForgotPasswordByKey(String key);

	/**
	 * 密码重置,用户找回密码
	 * @param am
	 * @param k
	 * @param pwd1:新密码
	 * @return
	 */
	public boolean resetPasswordFromForgotPasswordKey(String am, String k, String pwd1)
		throws NoSuchAlgorithmException, UnsupportedEncodingException;

	/**
	 * 发送密码重置email给用户
	 * @param key:重置Key
	 * @param email:重置email
	 */
	public void sendResetPasswordMail(String key, String email, String url)
		throws NoSuchAlgorithmException, UnsupportedEncodingException;

	/**
	 * 查询指定用户名或帐号名的数量
	 * @param account
	 * @return
	 */
	public Integer countUserByAccount(String account);
	
	public Integer countUserByEmail(String email);
	
	public Integer resetAccount(String username, String account);

	public String queryPassword(String username);
	
	public AuthUser queryAuthUserByUsername(String username);
	
	public Boolean resetPasswordByMobileKey (String username, String password)
	        throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
