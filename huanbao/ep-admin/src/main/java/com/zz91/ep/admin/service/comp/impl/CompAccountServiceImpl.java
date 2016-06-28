package com.zz91.ep.admin.service.comp.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompAccountDao;
import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.util.lang.StringUtils;

@Component("compAccountService")
public class CompAccountServiceImpl implements CompAccountService {

	@Resource private CompAccountDao compAccountDao;
	
//	@Override
//	public CompAccount getCompAccountByAccount(String account) {
//		CompAccount compAccount = null;
//		if(StringUtils.isNotEmpty(account)) {
//			compAccount = compAccountDao.queryCompAccountByAccount(account);
//		}
//		return compAccount;
//	}

	@Override
	public Integer getAccountIdByAccount(String account) {
		Integer accountId = null;
		try {
			if ( StringUtils.isNotEmpty(account)) {
				accountId = compAccountDao.getAccountIdByAccount(account);
			} else {
				return null;
			}
		} catch (Exception e) {
			//异常处理待添加		
			}
		return accountId;
	}

	@Override
	public CompAccount validateAccount(String account, String password,
			String ip) throws Exception{
		//识别登录email or account
		
		Integer i=compAccountDao.countUser(account, password);
		if(i==null || i.intValue()<=0){
			throw new Exception("errorAccount"); //帐号或密码有错误
		}
		CompAccount compAccount=null;
		if(StringUtils.isEmail(account)) {
				compAccount = compAccountDao.queryCompAccountByEmail(account);
		} else {
				compAccount = compAccountDao.queryCompAccountByAccount(account);
		}
		if(compAccount==null){
			throw new Exception("brockAccount"); //帐号有问题
		}
		compAccount.setLoginIp(ip);
		compAccountDao.updateCompAccount(compAccount);
		return compAccount;
	}

//	@Override
//	public Integer getCompanyIdByAccountId(Integer accountId) {
//		Integer companyId= 0;
//		try {
//			if ( StringUtils.isNotEmpty(accountId.toString()) ) {
//				companyId = compAccountDao.getCompanyIdByAccountId(accountId);
//			} else {
//				return companyId;
//			}
//		} catch (Exception e) {
//			//异常处理待添加		
//			}
//		return companyId;
//	}

	@Override
	public CompAccount getCompAccountByCid(Integer cid) {
		CompAccount compAccount = null;
		if(cid!=null && !"".equals(cid)) {
			compAccount = compAccountDao.queryCompAccountByCid(cid);
		}
		return compAccount;
	}

//	@Override
//	public Integer updateAccountPwd(Integer uid, String password,
//			String newPassword) {
//		return compAccountDao.updateAccountPwd(uid, password, newPassword);
//	}

//	@Override
//	public Integer updateCompAccount(CompAccount account) {
//		Assert.notNull(account, "the account can not be null");
//		Assert.notNull(account.getId(), "the account.getId() can not be null");
//		return compAccountDao.updateBaseCompAccount(account);
//	}

//	@Override
//	public CompAccount queryAccountDetails(String account) {
//		if(StringUtils.isEmpty(account)){
//			return null;
//		}
//		return compAccountDao.queryAccountDetails(account);
//	}

	@Override
	public Integer queryCidByAccount(String account) {
		return compAccountDao.queryCidByAccount(account);
	}
	
//	@Override
//	public Integer queryCountCompAcountByEmail(String email) {
//		return compAccountDao.queryCountCompAcountByEmail(email);
//	}

//	@Override
//	public Integer createCompAccount(CompAccount compAccount) {
//		return compAccountDao.insertCompAccount(compAccount);
//	}

//	@Override
//	public String queryAccountNameByCid(Integer cid) {
//		return compAccountDao.queryAccountNameByCid(cid);
//	}

//	@Override
//	public String queryNameByUid(Integer uid) {
//		return compAccountDao.queryNameByUid(uid);
//	}

//	@Override
//	public Integer queryUidByCid(Integer cid) {
//		return compAccountDao.queryUidByCid(cid);
//	}

//	@Override
//	public Integer queryWeekAddAccount() {
//		return compAccountDao.queryWeekAddAccount();
//	}

	@Override
	public Integer updateAccount(CompAccount account) {
		return compAccountDao.updateAccount(account);
	}

//	@Override
//	public Integer queryAllAddAccount() {
//		return compAccountDao.queryAllAddAccount();
//	}

//	@Override
//	public String queryAccountById(Integer uid) {
//		return compAccountDao.queryAccountById(uid);
//	}

//	@Override
//	public Integer countUserByEmail(String email) {
//		Assert.notNull(email, "email不能为空!");
//		return compAccountDao.countUserByEmail(email);
//	}

//	@Override
//	public String generateResetPwdKey(String emailOrAccount) {
//		Assert.notNull(emailOrAccount, "emailOrAccount can not be null");
//		CompAccount compAccount = null;
//		if(emailOrAccount.contains("@")) {
//			compAccount = compAccountDao.queryCompAccountByEmail(emailOrAccount);
//		} else {
//			compAccount = compAccountDao.queryCompAccountByAccount(emailOrAccount);
//		}
//		if(compAccount==null){
//			return null;
//		}
//
//		String key = null;
//		try {
//			Random random = new Random(1000);
//			String s=DateUtil.getSecTimeMillis()+""+random.nextInt();
//			key = AlgorithmUtils.MD5(s, AlgorithmUtils.LENGTH);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//		ResetPwd resetPwd = new ResetPwd();
//		if(emailOrAccount.contains("@")) {
//			resetPwd.setEmail(emailOrAccount);
//		} else {
//			resetPwd.setEmail(compAccount.getEmail());
//		}
//		resetPwd.setAuthKey(key);
//		resetPwd.setUid(compAccount.getId());
//		resetPwd.setAccount(compAccount.getAccount());
//
//		Integer i = compAccountDao.generateResetPwdKey(resetPwd);
//		if(i!=null && i.intValue()>0){
//			return key;
//		}else{
//			return null;
//		}
//	}

//	@Override
//	public ResetPwd listResetPwdByKey(String k) {
//		Assert.notNull(k, "the key 'k' can not be null");
//		return compAccountDao.listResetPwdByKey(k);
//	}

//	@Override
//	public Boolean validatePwdKey(String am, String k ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//		Assert.notNull(am, "the key 'am' can not be null");
//		Assert.notNull(k, "key can not be null");
//		do {
//
//			ResetPwd resetPwd = compAccountDao.listResetPwdByKey(k);
//			//验证Key是否存在
//			if(resetPwd==null){
//				break;
//			}
//			//验证是否过期
//			long start=DateUtil.getMillis(resetPwd.getGmtCreated());
//			long now=DateUtil.getMillis(new Date());
//			long twoday=2*60*60*24*1000;
//			if((now-start)>twoday){
//				break;
//			}
//			//验证用户是否正确
//			if(!am.equals(AlgorithmUtils.MD5(resetPwd.getEmail()+resetPwd.getAccount(), AlgorithmUtils.LENGTH))){
//				break;
//			}
//			return true;
//		} while (false);
//
//		return false;
//	}

//	@Override
//	public void sendResetPasswordMail(String key, String email, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//		Assert.notNull(key, "key can not be null");
//		Assert.notNull(email, "email can not be null");
//	
//		ResetPwd resetPwd = compAccountDao.listResetPwdByKey(key);
//		if(resetPwd!=null){
//			String resetPwdUrl = "http://" + url + "/user/validateKeyIsValid.htm?am=" + 
//			AlgorithmUtils.MD5(resetPwd.getEmail()+resetPwd.getAccount(), AlgorithmUtils.LENGTH) + "&k=" + key;
//			CompAccount compAccount = compAccountDao.queryCompAccountByEmail(email);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("compAccount", compAccount);
//			map.put("resetPwdUrl", resetPwdUrl);
//			MailUtil.getInstance().sendMail(MailArga.TITLE_RESET_PASSWORD, compAccount.getEmail(), 
//					MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE, MailArga.TEMPLATE_REPWD_CODE, map, MailUtil.PRIORITY_HEIGHT);
//			
//		}
//	}

//	@Override
//	public Boolean resetPasswordFromRetPwdKey(String k, String newPwd) {
//		Assert.notNull(k, "key can not be null");
//		Assert.notNull(newPwd, "newPwd can not be null");
//		ResetPwd resetPwd = compAccountDao.listResetPwdByKey(k);
//		if(compAccountDao.resetPassword(resetPwd.getAccount(), newPwd)>0) {
//			compAccountDao.deleteResetPwdByKey(k);
//			return true;
//		}
//		return false;
//	}

//	@Override
//	public boolean validatePwdKeyForSetPwd(String key) {
//		Assert.notNull(key, "key can not be null");
//		do {
//
//			ResetPwd resetPwd = compAccountDao.listResetPwdByKey(key);
//			//验证Key是否存在
//			if(resetPwd==null){
//				break;
//			}
//			//验证是否过期
//			long start=DateUtil.getMillis(resetPwd.getGmtCreated());
//			long now=DateUtil.getMillis(new Date());
//			long twoday=2*60*60*24*1000;
//			if((now-start)>twoday){
//				break;
//			}
//			return true;
//		} while (false);
//
//		return false;
//	}

	@Override
	public Integer queryMaxId() {
		return compAccountDao.queryMaxId();
	}

	@Override
	public String queryPasswordClearByCid(Integer cid) {
		return compAccountDao.queryPasswordClearByCid(cid);
	}

	@Override
	public CompAccount queryAccountByEmail(String email) {
		return compAccountDao.queryAccountByEmail(email);
	}
	
}
