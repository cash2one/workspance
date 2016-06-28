/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-11
 */
package com.ast.ast1949.service.auth.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.auth.AuthAutoLogin;
import com.ast.ast1949.domain.auth.AuthForgotPassword;
import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.exception.ServiceLayerException;
import com.ast.ast1949.persist.auth.AuthAutoLoginDao;
import com.ast.ast1949.persist.auth.AuthUserBlockLogDao;
import com.ast.ast1949.persist.auth.UserDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.util.AlgorithmUtils;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Component("authService")
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserDao userDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private AuthAutoLoginDao authAutoLoginDao;
	@Resource
	private AuthUserBlockLogDao authUserBlockLogDao;

	@Override
	public Integer countUserByAccount(String account) {
		return userDao.countUserByAccount(account);
	}

	@Override
	public Integer countUserByEmail(String email) {
		return userDao.countUserByEmail(email);
	}

	public String generateForgotPasswordKey(String email) {
		Assert.notNull(email, "email can not be null");

		AuthUser u = userDao.queryUserByEmail(email);
		if (u == null) {
			return null;
		}

		String key = null;
		try {
			Random random = new Random(1000);
			String s = DateUtil.getSecTimeMillis() + "" + random.nextInt();
			key = AlgorithmUtils.MD5(s, AlgorithmUtils.LENGTH);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		AuthForgotPassword o = new AuthForgotPassword();
		o.setEmail(email);
		o.setAuthKey(key);
		o.setUserid(u.getId());
		o.setUsername(u.getUsername());

		Integer i = userDao.generateForgotPasswordKey(o);
		if (i != null && i.intValue() > 0) {
			return key;
		} else {
			return null;
		}
	}

	public AuthForgotPassword listAuthForgotPasswordByKey(String key) {
		Assert.notNull(key, "key can not be null");
		return userDao.listAuthForgotPasswordByKey(key);
	}

	// 插入手机验证码并返回
	public String generateForgotPasswordMobileKey(String email ) {
		Assert.notNull(email, "email can not be null");

		AuthUser u = userDao.queryUserByEmail(email);
		if (u == null) {
			return null;
		}

		String key = null;
		try {
			Random random = new Random(1000);
			String s = DateUtil.getSecTimeMillis() + "" + random.nextInt();
			// 手机key只要5位
			key = AlgorithmUtils.MD5(s, AlgorithmUtils.LENGTH).substring(0, 5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		AuthForgotPassword o = new AuthForgotPassword();
		o.setEmail(email);
		o.setAuthKey(key);
		o.setUserid(u.getId());
		o.setUsername(u.getUsername());

		Integer i = userDao.generateForgotPasswordKey(o);
		if (i != null && i.intValue() > 0) {
			return key;
		} else {
			return null;
		}
	}
	
	// 插入手机验证码并返回
	public String generateForgotPasswordMobileKey(String email,String username) {
		AuthUser u = userDao.queryUserByUsername(username);
		if (u == null) {
			u =userDao.queryUserByEmail(email);
			if (u == null) {
				return null;
			}
		}
		
		String key = null;
		try {
			Random random = new Random(1000);
			String s = DateUtil.getSecTimeMillis() + "" + random.nextInt();
			// 手机key只要5位
			key = AlgorithmUtils.MD5(s, AlgorithmUtils.LENGTH).substring(0, 5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		AuthForgotPassword o = new AuthForgotPassword();
		o.setEmail(email);
		o.setAuthKey(key);
		o.setUserid(u.getId());
		o.setUsername(u.getUsername());
		
		Integer i = userDao.generateForgotPasswordKey(o);
		if (i != null && i.intValue() > 0) {
			return key;
		} else {
			return null;
		}
	}

	public boolean resetPasswordFromForgotPasswordKey(String am, String k,
			String pwd1) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		Assert.notNull(am, "the key 'am' can not be null");
		Assert.notNull(k, "key can not be null");
		Assert.notNull(pwd1, "key can not be null");
		do {

			AuthForgotPassword o = userDao.listAuthForgotPasswordByKey(k);
			// 验证Key是否存在
			if (o == null) {
				break;
			}
			// 验证是否过期
			long start = DateUtil.getMillis(o.getGmtCreated());
			long now = DateUtil.getMillis(new Date());
			long twoday = 2 * 60 * 60 * 24 * 1000;
			if ((now - start) > twoday) {
				break;
			}
			// 验证用户是否正确
			if (!am.equals(AlgorithmUtils.MD5(o.getEmail() + o.getUsername(),
					AlgorithmUtils.LENGTH))) {
				break;
			}
			// 更新密码
			userDao.updatePasswordByUsername(o.getUsername(), AlgorithmUtils
					.MD5(pwd1, AlgorithmUtils.LENGTH));
			companyAccountDao.updatePassword(o.getUsername(), pwd1);
			// companyContactsDAO.updatePasswordByAccount(o.getUsername(),
			// pwd1);
			// 销毁Key
			userDao.deleteAuthForgotPasswordByKey(k);
			return true;
		} while (false);

		return false;
	}

	public void sendResetPasswordMail(String key, String email, String url)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Assert.notNull(key, "key can not be null");
		Assert.notNull(email, "email can not be null");

		AuthForgotPassword o = userDao.listAuthForgotPasswordByKey(key);
		if (o == null) {
			throw new ServiceLayerException("the password can not be reset!");
		}

		// TODO 将邮件内容替换成邮件模块所提供的内容
		String title = "zz91再生网 密码重置";
		// StringBuffer sb=new StringBuffer();
		//	
		// sb.append(o.getUsername()).append("，您好！");
		// sb.append("<br/><br/>");
		// sb.append("请点击以下链接重置密码。");
		// sb.append("<br/><br/>");
		// sb.append("<a href='http://").append(url).append("/user/resetPassword.htm?am=")
		// .append(AlgorithmUtils.MD5(o.getEmail()+o.getUsername(),
		// AlgorithmUtils.LENGTH))
		// .append("&k=").append(key)
		// .append("'>http://").append(url).append("/user/resetPassword.htm?am=")
		// .append(AlgorithmUtils.MD5(o.getEmail()+o.getUsername(),
		// AlgorithmUtils.LENGTH))
		// .append("&k=").append(key).append("</a>");
		// sb.append("<br/><br/>");
		// sb.append("（请在48小时内完成重置，48小时后此邮件失效，您将需要重新提交密码找回）");
		// sb.append("<br/><br/>");
		// sb.append("如果通过点击以上链接无法访问，请将该网址复制并粘贴至新的浏览器窗口中。");
		// sb.append("<br/><br/>");
		// sb.append("您收到这封邮件是因为我们收到了您要找回密码的请求。如果您没有进行相关操作，错误的收到了此邮件，您无需执行任何操作来取消密码找回！您的账户密码将不会被修改！");
		// sb.append("<br/><br/>");
		// sb.append("一旦您重置了您的密码，请确保密码的强度。不要向任何人透露您的密码，或在邮件中填写密码信息。");
		// sb.append("<br/><br/>");
		// sb.append("如果您对您的账户有任何问题或疑问，请联系我们：");
		// sb.append("<br/><br/>");
		// sb.append("http://www.zz91.com");
		//		
		// ZZmail.getInstance().send(
		// ParamUtils.getInstance().getValue("site_email", "pwd_reset_account")
		// , email
		// , ParamUtils.getInstance().getValue("site_email", "pwd_reset_pwd")
		// , title, sb.toString());

		// mailutil 邮件工具
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("am", AlgorithmUtils.MD5(o.getEmail() + o.getUsername(),
				AlgorithmUtils.LENGTH));
		map.put("k", key);
		map.put("url", url);
		map.put("email", email);
		MailUtil.getInstance().sendMail(title, email,"zz91", "zz91-repwd", map,MailUtil.PRIORITY_HEIGHT);
	}

	@Override
	public Integer resetAccount(String userName, String account) {
		Assert.notNull(userName, "username or account can not be null");
		Assert.notNull(account, "username or account can not be null");
		// TODO 重置前验证用户名是否可用
		return userDao.updateAccount(userName, account);
	}

	@Override
	public String queryPassword(String username) {
		return userDao.queryPassword(username);
	}

	@Override
	public AuthUser queryAuthUserByUsername(String username) {
		return userDao.queryUserByUsername(username);
	}

	@Override
	public Boolean resetPasswordByMobileKey(String username, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		userDao.updatePasswordByUsername(username, AlgorithmUtils.MD5(password,
				AlgorithmUtils.LENGTH));
		companyAccountDao.updatePassword(username, password);
		return true;
	}

	@Override
	public boolean validateAutoLoginByCookie(String cookie) {
		do {
			if (StringUtils.isEmpty(cookie)) {
				break;
			}
			AuthAutoLogin obj = authAutoLoginDao.queryByCookie(cookie);
			if (obj == null) {
				break;
			}
			return true;
		} while (false);
		return false;
	}
	
	@Override
	public AuthAutoLogin queryAutoLoginByCookie(String cookie){
		if (StringUtils.isEmpty(cookie)) {
			return null;
		}
		return authAutoLoginDao.queryByCookie(cookie);
	}

	@Override
	public Integer createAutoLogin(Integer companyId, String companyAccount,
			String cookie,String password) {
		if (companyId == null || StringUtils.isEmpty(companyAccount)
				|| StringUtils.isEmpty(cookie)||StringUtils.isEmpty(password)) {
			return 0;
		}
		AuthAutoLogin authAutoLogin = authAutoLoginDao.queryByCookie(cookie);
		if(authAutoLogin==null){
			authAutoLogin = new AuthAutoLogin();
		}
		authAutoLogin.setCompanyAccount(companyAccount);
		authAutoLogin.setCompanyId(companyId);
		authAutoLogin.setCookie(cookie);
		authAutoLogin.setPassword(password);
		return authAutoLoginDao.insert(authAutoLogin);
	}
	
	@Override
	public Integer removeAuthLoginByCookie(String cookie){
		do {
			if(StringUtils.isEmpty(cookie)){
				break;
			}
			AuthAutoLogin obj = authAutoLoginDao.queryByCookie(cookie);
			if(obj==null){
				break;
			}
			return authAutoLoginDao.delete(obj.getId());
		} while (false);
		
		return 0;
		
	}

	@Override
	public boolean validateAuthUserByMobile(String account, String mobile) {
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			if (StringUtils.isEmpty(mobile)) {
				break;
			}
			// 检验帐号是否存在
			AuthUser au = userDao.queryUserByUsername(account);
			if(au==null){
				break;
			}

			// 手机账户不存在可发
			au = userDao.queryUserByMobile(mobile);
			if(au==null){
				return true;
			}

			// 手机号码不是该帐号拥有
			if(!account.equals(au.getUsername())){
				break;
			}

			return true;
		} while (false);
		return false;
	}
	
	@Override
	public Integer updateMobile(String account,String mobile){
		do {
			if (StringUtils.isEmpty(account)) {
				break;
			}
			return userDao.updateMobile(account, mobile);
		} while (false);
		return 0;
	}

	@Override
	public AuthUser queryAuthUserByMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return null;
		}
		return 	userDao.queryUserByMobile(mobile);
	}

	@Override
	public AuthUser queryAuthUserByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		return userDao.queryUserByEmail(email);
	}

	@Override
	public Integer updatePassWordByUsername(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String  passwordcg =AlgorithmUtils.MD5(password, AlgorithmUtils.LENGTH);
		return userDao.updatePasswordByUsername(username, passwordcg);
	}
	
	@Override
	public Integer addUnBindMobileLog(Integer companyId,String mobile){
		if (StringUtils.isEmpty(mobile)) {
			return 0;
		}
		return authUserBlockLogDao.insert(companyId, mobile);
	}

	@Override
	public void sendResetPasswordMailYuanLiao(String key, String email,
			String url) {
		Assert.notNull(key, "key can not be null");
		Assert.notNull(email, "email can not be null");

		AuthForgotPassword o = userDao.listAuthForgotPasswordByKey(key);
		if (o == null) {
			throw new ServiceLayerException("the password can not be reset!");
		}

		// TODO 将邮件内容替换成邮件模块所提供的内容
		String title = "优塑原网 密码重置";

		// mailutil 邮件工具
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("am", AlgorithmUtils.MD5(o.getEmail() + o.getUsername(),
					AlgorithmUtils.LENGTH));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("k", key);
		map.put("url", url);
		map.put("email", email);
		MailUtil.getInstance().sendMail(title, email,"zz91", "yuanliao-repwd", map,MailUtil.PRIORITY_HEIGHT);
	}
	
	/******************** 以下是老代码 ****************/
	// @Autowired
	// private MailAPI mailAPI;

	// final static String PASSWORD_RESET_EMAIL="findPassword@ast1949.com";
	// final static String PASSWORD_RESET_EMAIL_PASSWORD="zj88friend";
	//
	// public void changePassword(String username, String originalPassword,
	// String newPassword, String verifyPassword) throws AuthorizeException,
	// NoSuchAlgorithmException, UnsupportedEncodingException{
	// Assert.hasText(username, "username can not be empty");
	// Assert.hasText(originalPassword, "originalPassword can not be empty");
	// Assert.hasText(newPassword, "newPassword can not be empty");
	// Assert.hasText(verifyPassword, "verifyPassword can not be empty");
	//
	// AuthUser user=userDao.queryUserByUsername(username);
	// if(user==null){
	// throw new AuthorizeException("用户<"+username+">不存在");
	// }
	// if(!AlgorithmUtils.MD5(originalPassword,
	// AlgorithmUtils.LENGTH).equals(user.getPassword())){
	// throw new AuthorizeException("原密码不正确");
	// }
	// if(!newPassword.equals(verifyPassword)){
	// throw new AuthorizeException("两次密码输入不正确");
	// }
	// userDao.updatePasswordByUsername(username,
	// AlgorithmUtils.MD5(newPassword, AlgorithmUtils.LENGTH));
	// }
	//
	// public List<AuthRight> listUserRight(Integer parentRight, String
	// username) {
	// Assert.notNull(parentRight, "parentRight can not be null");
	// Assert.hasText(username, "username can not be empty");
	//
	// List<AuthRight> rightList = userDao.listUserRightByUsername(username);
	// List<AuthRight> myright=new ArrayList<AuthRight>();
	// for(AuthRight r:rightList){
	// if(r.getParentId().intValue() == parentRight.intValue()){
	// myright.add(r);
	// }
	// }
	// return myright;
	// }
	//
	// public List<AuthRight> listUserRight(String username) {
	// Assert.hasText(username, "username can not be empty");
	// return userDao.listUserRightByUsername(username);
	// }
	//
	// public void validateUser(String username, String password)
	// throws AuthorizeException, NoSuchAlgorithmException,
	// UnsupportedEncodingException {
	// if(StringUtils.isEmpty(username)){
	// throw new AuthorizeException("用户名不可以为空");
	// }
	// if(StringUtils.isEmpty(password)){
	// throw new AuthorizeException("密码不可以为空");
	// }
	// AuthUser user;
	// if(StringUtils.isEmail(username)){
	// user=userDao.queryUserByEmail(username);
	// }else{
	// user=userDao.queryUserByUsernameOrAccount(username);
	// //userDao.queryUserByUsername(username);
	// }
	// if(user==null){
	// throw new AuthorizeException("用户<"+username+">不存在");
	// }
	// if(!user.getPassword().equals(
	// AlgorithmUtils.MD5(password, AlgorithmUtils.LENGTH))){
	// throw new AuthorizeException("密码不正确");
	// }
	// if(user.getBlocked().shortValue()==UserDao.BLOCKED_TRUE){
	// throw new AuthorizeException("用户<"+username+">被禁止了");
	// }
	// userDao.updateLoginData(user.getId());
	// }
	//
	// public List<String> listUserRightContent(String username) {
	// Assert.hasText(username, "username can not be empty");
	// List<String> rightContentList=new ArrayList<String>();
	// List<AuthRight> rightList = userDao.listUserRightByUsername(username);
	// for(AuthRight r:rightList){
	// if(StringUtils.isNotEmpty(r.getContent())){
	// String[] str=r.getContent().split("\\|");
	// for(String s:str){
	// rightContentList.add(s);
	// }
	// }
	// }
	// return rightContentList;
	// }
	//
	// public AuthUser listOneUserByUsername(String username) {
	// Assert.notNull(username, "username can not be null");
	// return userDao.queryUserByUsername(username);
	// }
	//
	// public AuthUser listOneUserByEmail(String email) {
	// Assert.notNull(email, "email can not be null");
	// return userDao.queryUserByEmail(email);
	// }
	//
	// @Override
	// public Integer queryUserCountByUsernameOrAccount(String account) {
	// Assert.notNull(account, "username or account can not be null");
	// return userDao.queryUserCountByUsernameOrAccount(account);
	// }
	//
	// @Override
	// public AuthUser queryUserByUsernameOrAccount(String account) {
	// Assert.notNull(account, "username or account can not be null");
	// return userDao.queryUserByUsernameOrAccount(account);
	// }

}
