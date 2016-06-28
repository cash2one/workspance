/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.service.company.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyValidate;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.persist.auth.UserDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CompanyValidateDao;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.util.AlgorithmUtils;
import com.ast.ast1949.util.Assert;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.RandomUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-4-28
 */
@Component("companyAccountService")
public class CompanyAccountServiceImpl implements CompanyAccountService {

	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyService companyService;
	@Resource
	private UserDao userDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyValidateDao companyValidateDao;

	@Override
	public CompanyAccount queryAdminAccountByCompanyId(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return companyAccountDao.queryAdminAccountByCompanyId(id);
	}

	@Override
	public CompanyAccount queryAccountByAccount(String account) {
		Assert.notNull(account, "the account can not be null");
		return companyAccountDao.queryAccountByAccount(account);
	}

	@Override
	public Integer queryCompanyIdByEmail(String email) {
		Assert.notNull(email, "the email can not be null");
		return companyAccountDao.queryCompanyIdByEmail(email);
	}

	@Override
	public Integer queryCompanyIdByAccount(String account) {
		// Assert.notNull(account, "the account can not be null");
		return companyAccountDao.queryCompanyIdByAccount(account);
	}

	@Override
	public SsoUser initSessionUser(String account) {
		Assert.notNull(account, "the account can not be null");
		do {
			CompanyAccount companyAccount = companyAccountDao
					.queryAccountByAccount(account);
			if (companyAccount == null) {
				break;
			}
			Company company = companyService
					.querySimpleCompanyById(companyAccount.getCompanyId());
			if (company == null) {
				break;
			}
			SsoUser ssoUser = new SsoUser();
			ssoUser.setAccount(companyAccount.getAccount());
			ssoUser.setAccountId(companyAccount.getId());
			ssoUser.setAreaCode(company.getAreaCode());
			ssoUser.setCompanyId(company.getId());
			ssoUser.setEmail(companyAccount.getEmail());
			ssoUser.setMembershipCode(company.getMembershipCode());
			ssoUser.setServiceCode(company.getServiceCode());
			ssoUser.setZstFlag(company.getZstFlag());
			return ssoUser;
		} while (false);

		return null;
	}

	@Override
	public String registerUser(String username, String email, String password,
			String password2, CompanyAccount account, Company company, String ip)
			throws Exception {
		// 校验用户名，email等
		// 注册auth_user, company, company_account
		if (StringUtils.isEmpty(username)) {
			throw new RegisterException("用户名不能为空！");
		}
		if (StringUtils.isEmpty(email)) {
			throw new RegisterException("邮件地址(email)不能为空！");
		}
		if (StringUtils.isEmpty(password)) {
			throw new RegisterException("注册密码不能为空！");
		}
		if(StringUtils.isContainCNChar(username)){
			throw new RegisterException("用户名为中文");
		}
		Integer i = 0;
		i = userDao.countUserByAccount(username);
		if (i != null && i.intValue() > 0) {
			throw new RegisterException("用户名 " + username + " 已被注册，请更换用户名！");
		}
		i = userDao.countUserByEmail(email);
		if (i != null && i.intValue() > 0) {
			throw new RegisterException("邮件地址(email) " + email
					+ " 已被注册，请更换邮件地址！");
		}
		AuthUser user = new AuthUser();
		// 添加删除账户中空格
		user.setUsername(username.trim());
		user.setEmail(email);
		user.setSteping(1); // 还没注册完
		user.setAccount("");
		user.setPassword(MD5.encode(password));
		Integer userId = userDao.insertUser(user);

		try {

			String telphone = account.getTel();

			// 公司名称自动填充
			do {
				// 公司名称为空
				if (StringUtils.isNotEmpty(company.getName())) {
					break;
				}
				// 联系人不为空
				if (StringUtils.isEmpty(account.getContact())) {
					break;
				}
				// 不包含 中文
				if (!StringUtils.isContainCNChar(account.getContact())) {
					company.setName("个体经营");
					break;
				}
				// 包含中文，长度不大于 2 个
				if (account.getContact().length() < 2) {
					company.setName("个体经营");
				}
				company.setName("个体经营（" + account.getContact() + "）");
			} while (false);

			// 根据手机号填充地区和地址
			if(StringUtils.isEmpty(company.getAreaCode())){
				company.setAreaCode(companyService.getMobileLocation(telphone));
			}
			if (StringUtils.isEmpty(company.getAddress())
					&& StringUtils.isNotEmpty(company.getAreaCode())) {
				String address = "";
				String code = company.getAreaCode();
				if (code.length() >= 8) {
					address += CategoryFacade.getInstance().getValue(
							code.substring(0, 8));
				}
				if (code.length() >= 12) {
					address += CategoryFacade.getInstance().getValue(
							code.substring(0, 12));
				}
				if (code.length() >= 16) {
					address += CategoryFacade.getInstance().getValue(
							code.substring(0, 16));
				}
				company.setAddress(address);
			}

			Integer companyId = companyService.registerCompany(company);

			account.setCompanyId(companyId);
			account.setAccount(username);
			if (StringUtils.isEmpty(account.getContact())) {
				account.setContact("");
			}
			account.setIsAdmin("1");
			account.setNumLogin(0);
			companyAccountDao.insertAccount(account);
			
			// 写入validate信息
			CompanyValidate cv = new CompanyValidate();
			cv.setAccount(username);
			cv.setCompanyId(companyId);
			cv.setVcode(RandomUtils.random(6));
			cv.setVcodeKey(UUID.randomUUID().toString());
			cv.setGmtRegister(new Date());
			cv.setRegisterIp(ip);
			cv.setActived(0);
			cv.setActivedType(0);
			companyValidateDao.insert(cv);
			userDao.updateSteping(userId, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", user.getUsername()+"error:"+e.getMessage());
			MailUtil.getInstance().sendMail("注册用户Bug","zz91.reg.bug@asto.mail", "blank", map,MailUtil.PRIORITY_HEIGHT);
			userDao.deleteById(userId);
			return null;
		}

		return username;
	}

	@Override
	public Integer updateAccountByUser(CompanyAccount account) {
		Assert.notNull(account, "the object account can not be null");
		return companyAccountDao.updateAccountByUser(account);
	}

	@Override
	public String validateUser(String username, String password)
			throws AuthorizeException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		if (StringUtils.isEmpty(username)) {
			throw new AuthorizeException(AuthorizeException.NEED_ACCOUNT);
		}
		if (StringUtils.isEmpty(password)) {
			throw new AuthorizeException(AuthorizeException.NEED_PASS);
		}

		// TODO 判断是否已激活

		String resultAccount = null;

		do {
			resultAccount = userDao.validateByEmail(username, password);
			if (StringUtils.isNotEmpty(resultAccount)) {
				break;
			}

			resultAccount = userDao.validateByUsername(username, password);
			if (StringUtils.isNotEmpty(resultAccount)) {
				break;
			}

			resultAccount = userDao.validateByAccount(username, password);
		} while (false);

		if (StringUtils.isEmpty(resultAccount)) {
			throw new AuthorizeException(AuthorizeException.ERROR_LOGIN);
		}

		Integer i = companyDAO.isBlocked(resultAccount);

		if (i == null || i.intValue() <= 0) {
			throw new AuthorizeException(AuthorizeException.BLOCKED);
		}

		return resultAccount;
	}

	@Override
	public Integer countAccountOfMobile(String mobile) {
		Assert.notNull(mobile, "the mobile can not be null");
		return companyAccountDao.countAccountOfMobile(mobile);
	}

	@Override
	public String currentEmail(CompanyAccount account) {
		if (account != null) {
			if (account.getIsUseBackEmail() != null
					&& account.getIsUseBackEmail().equals("1")) {
				return account.getBackEmail();
			} else {
				return account.getEmail();
			}
		} else {
			return "";
		}
	}

	@Override
	public boolean changePassword(String account, String originalPassword,
			String newPassword, String verifyPassword)
			throws AuthorizeException, NoSuchAlgorithmException,
			UnsupportedEncodingException, AuthorizeException {
		Assert.hasText(account, "username can not be empty");
		Assert.hasText(originalPassword, "originalPassword can not be empty");
		Assert.hasText(newPassword, "newPassword can not be empty");
		Assert.hasText(verifyPassword, "verifyPassword can not be empty");

		AuthUser user = userDao.queryUserByUsername(account);
		if (user == null) {
			throw new AuthorizeException("账户存在问题，请联系客服！");
		}

		if (!MD5.encode(originalPassword).equals(user.getPassword())) {
			throw new AuthorizeException("原密码不正确");
		}
		if (!newPassword.equals(verifyPassword)) {
			throw new AuthorizeException("两次密码输入不正确");
		}
		Integer i = userDao.updatePasswordByUsername(account, MD5
				.encode(newPassword));
		companyAccountDao.updatePassword(account, newPassword);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer updateAccountByAdmin(CompanyAccount account) {
		Assert.notNull(account, "the object account can not be null");

		return companyAccountDao.updateAccountByAdmin(account);
	}

	@Override
	public List<CompanyAccount> queryAccountOfCompany(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return companyAccountDao.queryAccountOfCompany(companyId);
	}

	@Override
	public void updateLoginInfo(String account, String ip) {
		companyAccountDao.updateLoginInfo(account, ip);
	}

	@Override
	public PageDto<CompanyDto> queryAccountByAdmin(CompanyAccount account,
			PageDto<CompanyDto> page) {
		List<CompanyAccount> accountList = companyAccountDao
				.queryAccountByAdmin(account, page);
		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for (CompanyAccount a : accountList) {
			CompanyDto dto = new CompanyDto();
			dto.setAccount(a);
			Company c = companyService.querySimpleCompanyById(a.getCompanyId());
			if (c == null) {
				c = new Company();
			}
			dto.setCompany(c);

			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyAccountDao
				.queryAccountByAdminCount(account));
		return page;
	}

	@Override
	public boolean resetPasswordByAdmin(String account, String password) {
		Integer i = 0;
		try {
			userDao.updatePasswordByUsername(account, AlgorithmUtils.MD5(
					password, AlgorithmUtils.LENGTH));
			i = companyAccountDao.updatePassword(account, password);
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer queryComapnyIdByMobile(String mobile) {
		Assert.notNull(mobile, "the mobile can not be null");
		return companyAccountDao.queryCompanyIdByMobile(mobile);
	}

	@Override
	public CompanyAccount queryAccountByProductId(Integer productId) {
		return companyAccountDao.queryAccountByProductId(productId);
	}

	@Override
	public CompanyAccount queryAccountByCompanyId(Integer id) {
		return companyAccountDao.queryAccountByCompanyId(id);
	}

	@Override
	public SsoUser validateQQLogin(String account, String ip) {
		// 初始化 账号登录信息
		SsoUser ssoUser = initSessionUser(account);

		if (ssoUser == null) {
			return null;
		}

		// 生成ticket
		String key = UUID.randomUUID().toString();
		String ticket = null;
		try {
			ticket = MD5.encode(account + key); // TODO 增加私钥以保证安全
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}

		updateLoginInfo(account, ip);

		LogUtil.getInstance().log(String.valueOf(ssoUser.getCompanyId()),
				"login", ip);

		ssoUser.setTicket(ticket);
		// 保存6小时
		MemcachedUtils.getInstance().getClient().set(ticket, 6 * 60 * 60,
				ssoUser);
		return ssoUser;
	}

}
