/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.service.company.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyValidate;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.persist.auth.UserDao;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CompanyPriceDAO;
import com.ast.ast1949.persist.company.CompanyValidateDao;
import com.ast.ast1949.persist.company.CrmCompanySvrDao;
import com.ast.ast1949.persist.company.InquiryCountDao;
import com.ast.ast1949.persist.log.AuthAdminDao;
import com.ast.ast1949.persist.oauth.OauthAccessDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.sample.AccountDAO;
import com.ast.ast1949.persist.sample.SampleDao;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.util.AlgorithmUtils;
import com.ast.ast1949.util.Assert;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.RandomUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

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
	@Resource
	private AuthAdminDao authAdminDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private CrmCompanySvrDao crmCompanySvrDao;
	@Resource
	private SampleDao sampleDao;
	
	@Resource
	private CompanyPriceDAO companyPriceDAO;
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource 
	private BbsPostReplyDao bbsPostReplyDao;
    @Resource
    private InquiryCountDao inquiryCountDao;
    @Resource
    private AccountDAO accountDAO;
    @Resource
    private OauthAccessDao oauthAccessDao;
	@Override
	public CompanyAccount queryAdminAccountByCompanyId(Integer id) {
		Assert.notNull(id, "the id can not be null");
		//根据公司ID查找公司账户的帐号(判断帐号是否存在)
		String judge=companyAccountDao.queryCompanyAccountByCompanyId(id);
		//帐号存在执行 queryAdminAccountByCompanyId 
		if(judge!=null&&!judge.equals("")){
			return companyAccountDao.queryAdminAccountByCompanyId(id);
		}
		//帐号不存在，创建初始帐号
		else{
		CompanyAccount account=new CompanyAccount();
		//生成数据表不能为空的字段
		String at=id+"asto";
		account.setAccount(at);
		account.setCompanyId(id);
		account.setContact("先生");
		account.setIsAdmin("0");
		account.setNumLogin(1);
		//写入数据
		companyAccountDao.insertAccount(account);
		//返回
		return companyAccountDao.queryAdminAccountByCompanyId(id);
		}
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
		if (StringUtils.isContainCNChar(username)) {
			throw new RegisterException("用户名为中文");
		}
		Integer i = 0;
		Integer j = 0;
		i = userDao.countUserByAccount(username);
		if (i != null && i.intValue() > 0) {
			throw new RegisterException("用户名 " + username + " 已被注册，请更换用户名！");
		}
		i = userDao.countUserByEmail(email);
		if (i != null && i.intValue() > 0) {
			throw new RegisterException("邮件地址(email) " + email
					+ " 已被注册，请更换邮件地址！");
		}
		// 验证帐号和手机号码是否重复
		i = userDao.countUserByMobile(account.getAccount());
		// 验证手机号码是否重复
		j = userDao.countUserByMobile(account.getMobile());
		if (i != null && i.intValue() > 0 && j != null && j.intValue() > 0) {
			throw new RegisterException("手机号码重复!");
		}

		AuthUser user = new AuthUser();
		// 添加删除账户中空格
		user.setUsername(username.trim());
		user.setEmail(email);
		user.setSteping(1); // 还没注册完
		user.setAccount("");
		user.setMobile(account.getMobile());
		user.setPassword(MD5.encode(password));
		user.setIsperson(0);
		Integer userId = userDao.insertUser(user);

//		try {

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
		if (StringUtils.isEmpty(company.getAreaCode())) {
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
		if (companyId==null||companyId<1) {
			throw new RegisterException("公司信息注册失败");
		}

		account.setCompanyId(companyId);
		account.setAccount(username);
		if (StringUtils.isEmpty(account.getContact())) {
			account.setContact("");
		}
		account.setIsAdmin("1");
		account.setNumLogin(0);
		Integer accountId = companyAccountDao.insertAccount(account);
		if (accountId==null||accountId<1) {
			throw new RegisterException("帐号信息注册失败");
		}

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
//		} catch (Exception e) {
//			e.printStackTrace();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("content", user.getUsername() + "error:" + e.getMessage());
//			MailUtil.getInstance().sendMail("注册用户Bug",
//					"zz91.reg.bug@asto.mail", "blank", map,
//					MailUtil.PRIORITY_HEIGHT);
//			userDao.deleteById(userId);
//			return null;
//		}

		return username;
	}

	@Override
	public String registerUserAccount(String username, String password,
			String password2, CompanyAccount account, Company company, String ip)
			throws Exception {

		// 校验用户名，email等
		// 注册auth_user, company, company_account
		if (StringUtils.isEmpty(username)) {
			throw new RegisterException("用户名不能为空！");
		}

		if (StringUtils.isEmpty(password)) {
			throw new RegisterException("注册密码不能为空！");
		}
		if (StringUtils.isContainCNChar(username)) {
			throw new RegisterException("用户名为中文");
		}
		Integer i = 0;
		Integer j = 0;
		i = userDao.countUserByAccount(username);
		if (i != null && i.intValue() > 0) {
			throw new RegisterException("用户名 " + username + " 已被注册，请更换用户名！");
		}
		// 验证帐号和手机号码是否重复
		i = userDao.countUserByMobile(account.getAccount());
		// 验证手机号码是否重复
		j = userDao.countUserByMobile(account.getMobile());

		if (i != null && i.intValue() > 0 && j != null && j.intValue() > 0) {
			throw new RegisterException("手机号码重复!");
		}

		AuthUser user = new AuthUser();
		// 添加删除账户中空格
		user.setUsername(username.trim());
		user.setEmail("");
		user.setSteping(1); // 还没注册完
		user.setAccount("");
		user.setPassword(MD5.encode(password));
		user.setMobile(account.getMobile());
		user.setIsperson(1);// 个人
		Integer userId = userDao.insertUser(user);

//		try {
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
		if (StringUtils.isEmpty(company.getAreaCode())) {
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
		if (companyId==null||companyId<1) {
			throw new RegisterException("公司信息注册失败");
		}

		account.setCompanyId(companyId);
		account.setAccount(username);
		if (StringUtils.isEmpty(account.getContact())) {
			account.setContact("");
		}
		account.setIsAdmin("1");
		account.setNumLogin(0);
		Integer accountId = companyAccountDao.insertAccount(account);
		if (accountId==null||accountId<1) {
			throw new RegisterException("帐号信息注册失败");
		}

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

//		} catch (Exception e) {
//			e.printStackTrace();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("content", user.getUsername() + "error:" + e.getMessage());
//			MailUtil.getInstance().sendMail("注册用户Bug",
//					"zz91.reg.bug@asto.mail", "blank", map,
//					MailUtil.PRIORITY_HEIGHT);
//			userDao.deleteById(userId);
//			return null;
//		}
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
			if (StringUtils.isNotEmpty(resultAccount)) {
				break;
			}

			resultAccount = userDao.validateByMobile(username, password);

		} while (false);

		if (StringUtils.isEmpty(resultAccount)) {
			throw new AuthorizeException(AuthorizeException.ERROR_LOGIN);
		}

		Integer i = companyDAO.isBlocked(resultAccount);

		if (i == null || i.intValue() <= 0) {
			Integer hasAccount = authAdminDao.countAccount(resultAccount);
			if (hasAccount <= 0) {
				throw new AuthorizeException(AuthorizeException.BLOCKED);
			}
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
		Integer i = userDao.updatePasswordByUsername(account,
				MD5.encode(newPassword));
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
		//根据公司ID查找公司账户的帐号(判断帐号是否存在)
		String judge=companyAccountDao.queryCompanyAccountByCompanyId(companyId);
		//帐号存在执行 queryAdminAccountByCompanyId 
		if(judge!=null&&!judge.equals("")){
			return companyAccountDao.queryAccountOfCompany(companyId);
		}
		//帐号不存在，创建初始帐号
		else{
		CompanyAccount account=new CompanyAccount();
		//生成数据表不能为空的字段
		String at=companyId+"asto";
		account.setAccount(at);
		account.setCompanyId(companyId);
		account.setContact("先生");
		account.setIsAdmin("0");
		account.setNumLogin(1);
		//写入数据
		companyAccountDao.insertAccount(account);
		//返回
		return companyAccountDao.queryAccountOfCompany(companyId);
		}
	}

	@Override
	public void updateLoginInfo(String account, String ip) {
		companyAccountDao.updateLoginInfo(account, ip);
	}

	@Override
	public PageDto<CompanyDto> queryAccountByAdmin(CompanyAccount account,
			CompanyAccountSearchDto searchDto, PageDto<CompanyDto> page) {

		// 终点时间处理
		if (searchDto != null && StringUtils.isNotEmpty(searchDto.getRegTo())) {
			try {
				searchDto.setRegTo(DateUtil.toString(
						DateUtil.getDateAfterDays(DateUtil.getDate(
								searchDto.getRegTo(), "yyyy-MM-dd"), 1),
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				searchDto.setRegTo(DateUtil.toString(new Date(), "yyyy-MM-dd"));
			}
		}

		List<CompanyAccount> accountList = companyAccountDao.queryAccountByAdmin(account, searchDto, page);
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
		page.setTotalRecords(companyAccountDao.queryAccountByAdminCount(account, searchDto));
		return page;
	}

	@Override
	public boolean resetPasswordByAdmin(String account, String password) {
		Integer i = 0;
		try {
			userDao.updatePasswordByUsername(account,
					AlgorithmUtils.MD5(password, AlgorithmUtils.LENGTH));
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
		//根据公司ID查找公司账户的帐号(判断帐号是否存在)
				String judge=companyAccountDao.queryCompanyAccountByCompanyId(id);
				//帐号存在执行 queryAdminAccountByCompanyId 
				if(judge!=null&&!judge.equals("")){
					return companyAccountDao.queryAccountByCompanyId(id);
				}
				//帐号不存在，创建初始帐号
				else{
				CompanyAccount account=new CompanyAccount();
				//生成数据表不能为空的字段
				String at=id+"asto";
				account.setAccount(at);
				account.setCompanyId(id);
				account.setContact("先生");
				account.setIsAdmin("0");
				account.setNumLogin(1);
				//写入数据
				companyAccountDao.insertAccount(account);
				//返回
				return companyAccountDao.queryAccountByCompanyId(id);
				}
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
		MemcachedUtils.getInstance().getClient()
				.set(ticket, 6 * 60 * 60, ssoUser);
		return ssoUser;
	}

	@Override
	public Integer checkEmail(String username, String email) throws Exception {

		if (StringUtils.isEmpty(email)) {
			throw new RegisterException("邮件地址(email)不能为空！");
		}

		Integer i = 0;
		i = userDao.countUserByEmail(email);
		userDao.updateEmail(username, email);
		return i;
	}

	@Override
	public Integer updateInfoByaccount(CompanyAccount account) {
		try {
			return companyAccountDao.updateInfoByaccount(account);
		} catch (Exception e) {
		}
		return 0;
	}
	
//	public Integer updateTestInfoByaccount(CompanyAccount account) throws Exception{
//		Integer i = 0;
//		for (;i<10;i++) {
//			OauthAccess obj = new OauthAccess();
//			obj.setCode(""+i);
//			obj.setOpenId(""+i);
//			obj.setOpenType(""+i);
//			obj.setTargetAccount(""+i);
//			oauthAccessDao.insert(obj);
//			if(i==9){
//				throw new Exception("====error when updaa=======");
//			}
//		}
//		return 0;
//	}

	@Override
	public Integer countUserByEmail(String email) {

		return companyAccountDao.countUserByEmail(email);
	}
	/**
	 * 交易中心公司库搜索
	 * 涉及多张表查询，在计算分页总数(page.TotalRecords)是花费时间较长
	 * 没有companyAccount表中的字段查询则查询company表
	 * 有companyAccount表中的字段查询则查询companyaccount表
	 * 将有涉及多张吧的查询功能单独使用（使用这个功能查询则其它的附带条件查询无效）
	 * 回帖，微信绑定 发布帖子等查询单独出来查询
	 */
    @Override
    public PageDto<CompanyDto> queryAccountByAdminSearch(CompanyAccount account,CompanyAccountSearchDto searchDto,
    		Company company, PageDto<CompanyDto> page){
         
    	// 终点时间处理
		if (searchDto != null && StringUtils.isNotEmpty(searchDto.getRegTo())) {
			try {
				searchDto.setRegTo(DateUtil.toString(
						DateUtil.getDateAfterDays(DateUtil.getDate(
								searchDto.getRegTo(), "yyyy-MM-dd"), 1),
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				searchDto.setRegTo(DateUtil.toString(new Date(), "yyyy-MM-dd"));
			}
		}
		//最后登录时间 处理
		if (searchDto != null && StringUtils.isNotEmpty(searchDto.getLoginTo())) {
			try {
				searchDto.setLoginTo(DateUtil.toString(
						DateUtil.getDateAfterDays(DateUtil.getDate(
								searchDto.getLoginTo(), "yyyy-MM-dd"), 1),
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				searchDto.setLoginTo(DateUtil.toString(new Date(), "yyyy-MM-dd"));
			}
		}

		if (searchDto != null) {
			if (StringUtils.isNotEmpty(searchDto.getCrmCode())) {

				Integer svrcode = Integer.valueOf(searchDto.getCrmCode());
				switch (svrcode) {
				case 0:
					searchDto.setCrmServiceCode("1"); // 过期高会
					break;
				case 1:
					company.setMembershipCode("100510021000");// 银牌品牌通
					break;
				case 2:
					company.setMembershipCode("10051001");// 再生通
					break;
				case 3:
					searchDto.setCrmServiceCode("1006");// 简版再生通
					break;
				case 4:
					company.setMembershipCode("10051003");// 来电宝
					break;
				case 5:
					searchDto.setCrmServiceCode("10001002");// 百度优化
					break;
				case 6:
					company.setMembershipCode("10051000");// 普通会员
					break;
				case 7:
					company.setMembershipCode("100510021001");// 金牌品牌通
					break;
				case 8:
					company.setMembershipCode("100510021002");// 钻石品牌通
					break;
				default:
					break;
				}
			}
			
			if("1".equals(searchDto.getReceive())){
				searchDto.setBbsreplyFlag("1");// 需要用到bbspostreply表
			}

			if (StringUtils.isNotEmpty(searchDto.getPublish())) {
				Integer recCode = Integer.valueOf(searchDto.getPublish());
				switch (recCode) {
				case 1: // 有发布供求
					searchDto.setProductsFlag("1");// 需要用到products表
					break;
				case 2: // 无发布供求
					searchDto.setProductsFlag("1");// 需要用到products表
					break;
				case 3: // 有发布报价
					searchDto.setCompanypriceFlag("1");// 需要用到companyprice表
					break;
				case 4: // 有发布帖子
					searchDto.setBbspostFlag("1");// 需要用到bbspost表
					break;
				case 5: // 有回帖
					searchDto.setBbsreplyFlag("1");// 需要用到bbspostreply表
					break;
				case 6: // 有发布询价
					searchDto.setInquriyCountFlag("1");// 需要用到inquiryCount表
					break;
				case 7: // 无发布询价
					searchDto.setInquriyCountFlag("1");// 需要用到inquiryCount表
					break;
				case 8: // 无发布报价
					searchDto.setCompanypriceFlag("1");;// 需要用到companyprice表
					break;
				default:
					break;
				}
			}
		}
		//判断是否用Companyaccout表还是用company表连接查询
		boolean accountFlage = false;
		if (account != null) {
			if (StringUtils.isNotEmpty(account.getAccount())) {
				accountFlage = true;
			}
			if (StringUtils.isNotEmpty(account.getEmail())) {
				accountFlage = true;
			}
			if (StringUtils.isNotEmpty(account.getMobile())) {
				accountFlage = true;
			}
			
			if (StringUtils.isNotEmpty(searchDto.getNumLoginFrom())) {
				accountFlage = true;
			}
			if (StringUtils.isNotEmpty(searchDto.getNumLoginTo())) {
				accountFlage = true;
			}
			if (StringUtils.isNotEmpty(searchDto.getLoginFrom())) {
				accountFlage = true;
			}
			if (StringUtils.isNotEmpty(searchDto.getLoginTo())) {
				accountFlage = true;
			}
		}
		do {
			//公司库公司名字关键字搜索
			if (searchDto!=null&&StringUtils.isNotEmpty(searchDto.getKeyword())) {
				page=companyService.companyBySearchEngine(company,searchDto.getKeyword(), page);
				break;
			}
			// 使用oauth_access表查询微信绑定的公司
			if (StringUtils.isNotEmpty(searchDto.getWeixin())) {
				List<OauthAccess> list = oauthAccessDao.queryTargetAccount(page);
				List<CompanyDto> resultList = new ArrayList<CompanyDto>();
				if (list != null) {
					for (OauthAccess oa : list) {
						if (oa != null && oa.getTargetAccount() != null) {
							CompanyDto dto = new CompanyDto();
							CompanyAccount ca = companyAccountDao.queryAccountByAccount(oa.getTargetAccount());
							if (ca == null) {
								ca = new CompanyAccount();
							}
							Company c = new Company();
							Integer num = 0;
							if (ca.getCompanyId() != null) {
								c = companyService.querySimpleCompanyById(ca.getCompanyId());
								num = productsDAO.countProductsByCompanyId(ca.getCompanyId());
							}
							dto.setCompany(c);
							dto.setAccount(ca);

							dto.setNumProducts(num);
							resultList.add(dto);
						}
					}
				}
				page.setRecords(resultList);
				page.setTotalRecords(oauthAccessDao.queryTargetAccountCount());
				break;
			}
			
			
			// 关于发贴
			if (StringUtils.isNotEmpty(searchDto.getBbspostFlag())) {
				List<Integer> list = bbsPostDAO.bbsPostCompany(page);
				List<CompanyDto> resultList = new ArrayList<CompanyDto>();
				if (list != null) {
					for (Integer i : list) {
						if (i != null && i.intValue() > 0) {
							CompanyDto dto = new CompanyDto();
							Company c = companyService.querySimpleCompanyById(i);
							if (c == null) {
								c = new Company();
							}

							CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(i);
							if (ca == null) {
								ca = new CompanyAccount();
							}
							dto.setCompany(c);
							dto.setAccount(ca);
							Integer num = productsDAO.countProductsByCompanyId(i);
							dto.setNumProducts(num);
							resultList.add(dto);
						}
					}
				}
				page.setRecords(resultList);
				page.setTotalRecords(bbsPostDAO.bbsPostCompanyCount());
				break;
			}
			
			
			// 关于回贴
			if (StringUtils.isNotEmpty(searchDto.getBbsreplyFlag())) {
				List<Integer> list;
                if("5".equals(searchDto.getPublish())){
				 list = bbsPostReplyDao.bbsPostReplyCompany(searchDto, page);
                }
                else  {
                   list = bbsPostReplyDao.receviceReplyCompany(searchDto, page);
                		   }
				List<CompanyDto> resultList = new ArrayList<CompanyDto>();
				if (list != null) {
					for (Integer i : list) {
						if (i != null && i.intValue() > 0) {
							CompanyDto dto = new CompanyDto();
							Company c = companyService.querySimpleCompanyById(i);
							if (c == null) {
								c = new Company();
							}

							CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(i);
							if (ca == null) {
								ca = new CompanyAccount();
							}
							dto.setCompany(c);
							dto.setAccount(ca);
							Integer num = productsDAO.countProductsByCompanyId(i);
							dto.setNumProducts(num);
							resultList.add(dto);
						}
					}
				}
				page.setRecords(resultList);
				  //有回帖公司数量
				 if("5".equals(searchDto.getPublish())){
					 page.setTotalRecords(bbsPostReplyDao.bbsPostReplyCompanyCount(searchDto));
				 }else{  //收到回帖公司数量
					 page.setTotalRecords(bbsPostReplyDao.receviceReplyCompanyCount(searchDto)); 
				 }
				break;
			}
			
			
			//使用companyAccount表查询
			if (accountFlage || StringUtils.isNotEmpty(searchDto.getIsPerson())) {
				List<CompanyAccount> accountList = companyAccountDao.queryAccountByAdmin(account, searchDto, page);
				List<CompanyDto> resultList = new ArrayList<CompanyDto>();

				for (CompanyAccount a : accountList) {
					CompanyDto dto = new CompanyDto();
					dto.setAccount(a);
					if (a != null && a.getCompanyId() != null&& a.getCompanyId().intValue() > 0) {
						Company c = companyService.querySimpleCompanyById(a
								.getCompanyId());
						if (c == null) {
							c = new Company();
						}
						dto.setCompany(c);
						Integer num = productsDAO.countProductsByCompanyId(a.getCompanyId());
						dto.setNumProducts(num);
					}
					resultList.add(dto);

				}
				page.setRecords(resultList);
				page.setTotalRecords(companyAccountDao.queryAccountByAdminCount(account, searchDto));

			}
             //使用company表连接查询
			else {
				List<Company> companyList = companyDAO.queryCompanyByAdminSearch(company, searchDto, page);
				List<CompanyDto> resultList = new ArrayList<CompanyDto>();
				for (Company c : companyList) {
					CompanyDto dto = new CompanyDto();
					dto.setCompany(c);
					if (c != null && c.getId() != null&& c.getId().intValue() > 0) {
						CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(c.getId());
						if (ca == null) {
							ca = new CompanyAccount();
						}
						dto.setAccount(ca);
						Integer num = productsDAO.countProductsByCompanyId(c.getId());
						dto.setNumProducts(num);
					}
					resultList.add(dto);
				}
				page.setRecords(resultList);
				//查询发布供求和发布报价在计算分页总数有点卡
				//计算发布了供求的公司数量
				if(StringUtils.isNotEmpty(searchDto.getProductsFlag())){
					Integer numProductsCompany= productsDAO.productsCompanyCount();
					if("1".equals(searchDto.getPublish())){
						page.setTotalRecords(numProductsCompany);
						break;
					}
					//没有发布供求的公司
					else{
						//总公司数量
						Integer companyCount=companyDAO.CompanyCount();
						page.setTotalRecords(companyCount-numProductsCompany);
						break;
					}
				}
				//计算发布报价的公司数量
				else if(StringUtils.isNotEmpty(searchDto.getCompanypriceFlag())){
					Integer numCompanyPrice= companyPriceDAO.companyPriceCount();
					if("3".equals(searchDto.getPublish())){
						page.setTotalRecords(numCompanyPrice);
						break;
					}
					//没有发布报价的公司
					else{
						//总公司数量
						Integer companyCount=companyDAO.CompanyCount();
						page.setTotalRecords(companyCount-numCompanyPrice);
						break;
					}
					
				}
				else{
					page.setTotalRecords(companyDAO.queryCompanyCountByAdminSearch(company, searchDto));
				}
			}
		} while (false);
    		  
    		    return page;
    			
    }

	@Override
	public Integer queryCompanyIdByNameAndPassw(String username, String password) {
		return companyAccountDao.queryCompanyIdByNameAndPassw(username, password);
	}

	@Override
	public void updateAccountByAccount(CompanyAccount account) {
		companyAccountDao.updateAccountByAccount(account);
	}
}
