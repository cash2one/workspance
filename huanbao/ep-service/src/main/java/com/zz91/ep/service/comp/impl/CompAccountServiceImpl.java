/*
 * 文件名称：CompAccountServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompAccountDao;
import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.crm.CRMRightDao;
import com.zz91.ep.dao.sys.SysProjectDao;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.ResetPwd;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.util.Assert;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：公司帐号信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("compAccountService")
public class CompAccountServiceImpl implements CompAccountService {

	@Resource 
	private CompAccountDao compAccountDao;
	@Resource
	private CompProfileDao compProfileDao;
	@Resource
	private SysProjectDao sysProjectDao;
	@Resource
	private CRMRightDao	crmRightDao;
	@Override
	public String insertResetPwdKey(String emailOrAccount) {
		Assert.notNull(emailOrAccount, "emailOrAccount can not be null");
		CompAccount compAccount = null;
		// 判断是邮箱验证还是帐号验证
		if(emailOrAccount.contains("@")) {
			compAccount = compAccountDao.queryCompAccountByEmail(emailOrAccount);
		} else {
			compAccount = compAccountDao.queryCompAccountByAccount(emailOrAccount);
		}
		if(compAccount != null){
			// 构建密码重置唯一Key
			String key = null;
			try {
				Random random = new Random(1000);
				String tokey=DateUtil.getSecTimeMillis()+""+random.nextInt();
				key = MD5.encode(tokey,MD5.LENGTH_16);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 插入密码重置表
			ResetPwd resetPwd = new ResetPwd();
			if(emailOrAccount.contains("@")) {
				resetPwd.setEmail(emailOrAccount);
			} else {
				resetPwd.setEmail(compAccount.getEmail());
			}
			resetPwd.setAuthKey(key);
			resetPwd.setUid(compAccount.getId());
			resetPwd.setAccount(compAccount.getAccount());
			Integer id = compAccountDao.insertResetPwd(resetPwd);
			if(id!=null && id.intValue()>0){
				return key;
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public ResetPwd listResetPwdByKey(String key) {
		Assert.notNull(key, "the key 'key' can not be null");
		return compAccountDao.listResetPwdByKey(key);
	}

	@Override
	public void sendResetPasswordMail(String key, String email, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Assert.notNull(key, "key can not be null");
		Assert.notNull(email, "email can not be null");
		ResetPwd resetPwd = compAccountDao.listResetPwdByKey(key);
		if(resetPwd!=null){
			String resetPwdUrl = "http://" + url + "/user/validateKeyIsValid.htm?am=" + 
			MD5.encode(resetPwd.getEmail()+resetPwd.getAccount(),MD5.LENGTH_16) + "&k=" + key;
			CompAccount compAccount = compAccountDao.queryCompAccountByEmail(email);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compAccount", compAccount);
			map.put("resetPwdUrl", resetPwdUrl);
			MailUtil.getInstance().sendMail(MailArga.TITLE_RESET_PASSWORD, compAccount.getEmail(), 
					MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE, MailArga.TEMPLATE_REPWD_CODE, map, MailUtil.PRIORITY_HEIGHT);
		}
	}

	@Override
	public boolean validatePwdKey(String am, String k) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Assert.notNull(am, "the key 'am' can not be null");
		Assert.notNull(k, "key can not be null");
		do {
			ResetPwd resetPwd = compAccountDao.listResetPwdByKey(k);
			//验证Key是否存在
			if(resetPwd==null){
				break;
			}
			//验证是否过期
			long start=DateUtil.getMillis(resetPwd.getGmtCreated());
			long now=DateUtil.getMillis(new Date());
			long twoday=2*60*60*24*1000;
			if((now-start)>twoday){
				break;
			}
			//验证用户是否正确
			if(!am.equals(MD5.encode(resetPwd.getEmail()+resetPwd.getAccount(), MD5.LENGTH_16))){
				break;
			}
			return true;
		} while (false);
		return false;
	}

	@Override
	public boolean resetPasswordFromRetPwdKey(String k, String newPwd) {
		Assert.notNull(k, "key can not be null");
		Assert.notNull(newPwd, "newPwd can not be null");
		do {
			ResetPwd resetPwd = compAccountDao.listResetPwdByKey(k);
			if(resetPwd == null){
				break;
			}
			//验证是否过期
			long start=DateUtil.getMillis(resetPwd.getGmtCreated());
			long now=DateUtil.getMillis(new Date());
			long twoday=2*60*60*24*1000;
			if((now-start)>twoday){
				break;
			}
			if(compAccountDao.resetPassword(resetPwd.getAccount(), newPwd)>0) {
				compAccountDao.deleteResetPwdByKey(k);
				return true;
			}
		} while (false);
		return false;
	}

	@Override
	public Integer queryCountCompAcountByEmail(String email) {
		return compAccountDao.queryCountCompAcountByEmail(email);
	}

	@Override
	public Integer getAccountIdByAccount(String account) {
		
		return compAccountDao.getAccountIdByAccount(account);
	}

	@Override
	public CompAccount getCompAccountByCid(Integer cid) {
		CompAccount compAccount = null;
		if(cid!=null && !"".equals(cid)) {
			compAccount = compAccountDao.queryCompAccountByCid(cid);
		}
		return compAccount;
	}
	
	@Override
	public CompAccount queryAccountDetails(String account) {
		if(StringUtils.isEmpty(account)){
			return null;
		}
		return compAccountDao.queryAccountDetails(account);
	}
	
	@Override
	public String queryAccountById(Integer uid) {
		return compAccountDao.queryAccountById(uid);
	}

	@Override
	public Integer updateCompAccount(CompAccount account) {
		Assert.notNull(account, "the account can not be null");
		Assert.notNull(account.getId(), "the account.getId() can not be null");
		return compAccountDao.updateBaseCompAccount(account);
	}
	
	@Override
	public Integer updateAccountPwd(Integer uid, String password,
			String newPassword) {
		return compAccountDao.updateAccountPwd(uid, password, newPassword);
	}
	
	public List<CompAccount> queryImpCompAccount(Integer maxId) {
		return compAccountDao.queryImpCompAccount(maxId);
	}

	@Override
	public CompAccount validateAccount(String account, String password,
			String ip) throws Exception {
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
		//compAccountDao.updateCompAccount(compAccount);
		return compAccount;
	}

	@Override
	public Integer validateUser(String account, String password) throws AuthorizeException,NoSuchAlgorithmException, UnsupportedEncodingException {
		if(StringUtils.isEmpty(account)){
			throw new AuthorizeException(AuthorizeException.NEED_ACCOUNT);
		}
		if(StringUtils.isEmpty(password)){
			throw new AuthorizeException(AuthorizeException.NEED_PASS);
		}
		
		Integer cid = null;
		if(StringUtils.isEmail(account)){
			cid = compAccountDao.validateByEmail(account, password);
		}else{
			cid = compAccountDao.validateByAccount(account, password);
		}
		
		if(cid==null || cid.intValue()<=0){
			throw new AuthorizeException(AuthorizeException.ERROR_LOGIN);
		}
		
		Integer i = compProfileDao.isDelStatus(cid);
		if(i==null || i.intValue()<=0){
			throw new AuthorizeException(AuthorizeException.BLOCKED);
		}
		return cid;
	}

	@Override
	public EpAuthUser initEpAuthUser(Integer cid,String project) {
		Assert.notNull(cid, "the account can not be null");
		do{
			
			CompAccount compaccount = compAccountDao.getCompAccountByCid(cid);
			if(compaccount==null){
				break;
			}
			
			String memberCode = compProfileDao.getMemberCodeById(cid);
			if(memberCode==null||"".equals(memberCode)){
				break;
			}
			
			EpAuthUser epAuthUser = new EpAuthUser();
			epAuthUser.setUid(compaccount.getId());
			epAuthUser.setCid(compaccount.getCid());
			epAuthUser.setAccount(compaccount.getAccount());
			epAuthUser.setLoginName(compaccount.getName());
			
//			if (StringUtils.isNotEmpty(compProfile.getMemberCodeBlock())) {
//				epAuthUser.setMemberCode(compProfile.getMemberCodeBlock());
//			} else {
			epAuthUser.setMemberCode(memberCode);
//			}
//			String [] crmRightList = getAuthListByCompanyIdAndMemberCode(cid, 
//					memberCode,  project);
//			
//			epAuthUser.setRightList(crmRightList);
			return epAuthUser;
		}while(false);
		return null;
	}
	
	@Override
	public String[] getAuthListByCompanyIdAndMemberCode(Integer companyId,
			String memberCode, String project) {
		String[] authList = {};
		Set<String> set = new HashSet<String>();
		String rightCode=sysProjectDao.queryRightByProject(project);
		try {
			// 如果memberCodeBlock不为空，则不返回服务权限，只返回用户权限
	
			if (StringUtils.isNotEmpty(memberCode)) {	//	如果memberCode不为空，则返回会员权限和服务权限的并集

				List<String> crmSvrRight = crmRightDao
						.queryCrmRightListBycompanyId(companyId, rightCode);
				List<String> crmMemberRight = crmRightDao
						.queryCrmRightListByMemberCode(memberCode, rightCode);
				set = iterAuthList(crmMemberRight,iterAuthList(crmSvrRight,set));
				authList = set.toArray(authList);
			}
		} catch (Exception e) {
			
		}
		return authList;
	}
	
	public  Set<String> iterAuthList(List<String> crmMemberRight,Set<String> set){
		for (String content: crmMemberRight) {
			if (StringUtils.isNotEmpty(content)) {
				CollectionUtils.addAll(set, content.split("\\|"));
			}
		}
		return set;
	}

	@Override
	public void updateLoginInfo(Integer uid, String ip,Integer cid) {
		Assert.notNull(uid, "the account can not be null");
		//Assert.notNull(ip, "the ip can not be null");
		CompAccount compAccount = new CompAccount();
		compAccount.setId(uid);
		compAccount.setLoginIp(ip);
		Integer i=compAccountDao.updateCompAccount(compAccount);
		if(i!=null && i.intValue()>0){
			compProfileDao.updateGmtModifiedById(cid);
		}
	}

	@Override
	public String queryQq(Integer cid) {
		return compAccountDao.queryQq(cid);
	}

	@Override
	public CompAccount querySimplyCompAccountByCid(Integer cid) {
		return compAccountDao.getCompAccountByCid(cid);
	}

	@Override
	public Integer queryUidByCid(Integer cid) {
		return compAccountDao.queryUidByCid(cid);
	}

	@Override
	public Integer queryLoginCountByCid(Integer cid) {
		return compAccountDao.queryLoginCount(cid);
	}

	@Override
	public Integer queryCountCompAcountByCid(Integer cid) {
		Assert.notNull(cid, "the cid can not be null");
		return compAccountDao.queryCountCompAcountByCid(cid);
	}

	@Override
	public Integer queryCountCompAcountByMobile(String mobile) {
		return compAccountDao.queryCountCompAcountByMobile(mobile);
	}
	
	@Override
	public Integer queryCountCompAcountByAccount(String account) {
		return compAccountDao.queryCountCompAcountByAccount(account);
	}
	
}