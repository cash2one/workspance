package com.ast.feiliao91.service.company.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

//import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Component;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.persist.company.CompanyAccountDao;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;


@Component("companyAccountService")
public class CompanyAccountServiceImpl implements CompanyAccountService {

	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyInfoDao companyInfoDao;

	@Override
	public Integer doLogin(String account, String password) {
		do {
			// 帐号密码不可为空
			if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
				break;
			}
			String pwdMD5 = "";
			try {
				pwdMD5 = MD5.encode(password);
			} catch (Exception e) {
				break;
			}
			// 1 验证帐号是否存在 feiliao91网
			Integer i = companyAccountDao.selectByAccountAndPassword(account, null, null, pwdMD5);
			if (i == 0) {
				i = companyAccountDao.selectByAccountAndPassword(null, account, null, pwdMD5);
			}
			if (i == 0) {
				i = companyAccountDao.selectByAccountAndPassword(null, null, account, pwdMD5);
			}

			// 1.1 帐号密码正确 进入交易管家
			if (i > 0) {
				return 1;
			}
			// 1.2 不存在 进入步骤2
			// 2 验证帐号是否存在 zz91网
			String result = validateZz91(account, password);
			// 2.3 不存在，返回错误提示帐号密码不正确 进入步骤4
			if (StringUtils.isEmpty(result)) {
				break;
			}
			// 2.2 存在
			JSONObject json = JSONObject.fromObject(result);
			if(json.size()<1){
				break;
			}
			// 2.2.1 则导入zz91账户信息(邮箱，手机号，公司名称，主营，国家地区，企业简介，认证信息【个人/公司】)
			CompanyInfo cInfo = new CompanyInfo();
			cInfo.setArea(json.getString("areaCode")); // 地区
			cInfo.setName(json.getString("name")); // 公司名称
			cInfo.setBusiness(json.getString("business")); // 主营产品
			try {
				cInfo.setIntroduce(json.getString("introduction")); // 公司简介
			} catch (Exception e) {
				cInfo.setIntroduce("");
			}
			CompanyAccount cAccount = new CompanyAccount();
			cAccount.setAccount(account); // 帐号
			cAccount.setPassword(password); // 密码
			cAccount.setPasswordMD5(pwdMD5); // 加密密码
			cAccount.setEmail(json.getString("email")); // 邮箱
			cAccount.setMobile(json.getString("mobile")); // 电话
			cAccount.setIp(json.get("ip")+"");
			// 2.2.2 企业还是个人必须标识 企业详细信息赛入
			String attestStr = json.getString("attest");
			if (!"null".equals(attestStr)) {
				cInfo.setCreditInfo(attestStr); // 认证信息
				JSONObject obj = JSONObject.fromObject(attestStr);
				if ("1".equals(obj.getString("attestType"))) {
					cInfo.setCreditType(1);
				} else {
					cInfo.setCreditType(0);
				}
				if ("1".equals(obj.get("checkStatus"))) {
					cInfo.setCreditStatus(2);
				}
			}
			// 2.2.3 insert 公司
			Integer companyId = companyInfoDao.insertCompanyInfo(cInfo);
			// 2.2.4 insert 帐号
			if (companyId != null && companyId > 0) {
				cAccount.setCompanyId(companyId);
				Integer accountId = insertAccount(cAccount);
				if (accountId <= 0) {
					// 插入帐号失败逻辑 ，暂时不做
					break;
				}
			}
			// 3 绑定帐号正确 进入交易管家
			return 1;
			
		} while (false);
		// 4 登录失败，返回登录页面
		return 0;
	}

	private String validateZz91(String account, String password) {
		try {
			account = URLEncoder.encode(account, HttpUtils.CHARSET_UTF8);
		} catch (Exception e1) {
			return "";
		}
		try {
			password = URLEncoder.encode(password, HttpUtils.CHARSET_UTF8);
		} catch (Exception e1) {
			return "";
		}
		String url = "http://www.zz91.com/app/validateAccount.htm?account=" + account + "&password=" + password;
		String result;
		result = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
		return result;
	}

	@Override
	public Integer insertAccount(CompanyAccount account) {
		if (StringUtils.isEmpty(account.getAccount())) {
			return 0;
		}
		// 验证帐号是否存在
		CompanyAccount obj = companyAccountDao.queryByAccount(account.getAccount());
		if (obj!=null) {
			return 0;
		}
		if (account.getPassword() != null) {
			try {
				account.setPasswordMD5(MD5.encode(account.getPassword()));
			} catch (Exception e) {
				return 0;
			}
		}
		if (StringUtils.isEmpty(account.getMobile())&&StringUtils.isNumber(account.getAccount())) {
			account.setMobile(account.getAccount());
		}
		// 获取ip
		return companyAccountDao.insert(account);
	}

	@Override
	public Integer hasAM(String account, String email) {
		return companyAccountDao.countByMobileOrEmail(account, email);
	}

	@Override
	public SsoUser initSsoUser(String account) {
		if (StringUtils.isEmpty(account)) {
			return null;
		}
		CompanyAccount companyAccount = companyAccountDao.queryByAccount(account);
		if (companyAccount == null || companyAccount.getCompanyId() == null) {
			return null;
		}
		CompanyInfo companyInfo = companyInfoDao.queryById(companyAccount.getCompanyId());
		if (companyInfo == null) {
			return null;
		}
		SsoUser ssoUser = new SsoUser();
		ssoUser.setAccount(account);
		ssoUser.setAccountId(companyAccount.getId());
		ssoUser.setCompanyId(companyInfo.getId());
		ssoUser.setMobile(companyAccount.getMobile());
		return ssoUser;
	}

	@Override
	public CompanyAccount queryAccountByAccount(String account) {
		return companyAccountDao.queryByAccount(account);
	}

	@Override
	public Integer updatePwd(String account, String pwd, String pwdr) {
		if (StringUtils.isEmpty(account)||StringUtils.isEmpty(pwd)||StringUtils.isEmpty(pwdr)) {
			return 0;
		}
		if (!pwd.equals(pwdr)) {
			return 0;
		}
		try {
			return companyAccountDao.updatePwd(account, pwd, MD5.encode(pwd));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return 0;
	}
	
	@Override
	public Integer updatePwd(String account,String pwd){
		try {
			return companyAccountDao.updatePwd(account, pwd, MD5.encode(pwd));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer updatePhone(String account,String phone) {
		return companyAccountDao.updatePhone(account,phone);
	}

	@Override
	public Integer updateEmail(String account,String email) {
		return companyAccountDao.updateEmail(account,email);
	}

	@Override
	public CompanyAccount queryByCompanyIdAndPayPwd(Integer companyId, String payPassword) {
		if (companyId==null||StringUtils.isEmpty(payPassword)) {
			return null;
		}
		try {
			payPassword = MD5.encode(payPassword, MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return companyAccountDao.queryByCompanyIdAndPayPwd(companyId, payPassword);
	}

	@Override
	public Integer updateSumMoney(Integer companyId, Float sumMoney) {
		return companyAccountDao.updateSumMoney(companyId,sumMoney);
	}

	@Override
	public CompanyAccount queryAccountByCompanyId(Integer companyId) {
		if (companyId==null) {
			return null;
		}
		return companyAccountDao.queryByCompanyId(companyId);
	}

	@Override
	public Integer updatePayPwd(String account, String pwd) {
		if(StringUtils.isEmpty(account)||StringUtils.isEmpty(pwd)){
			return 0;
		}
		try {
			return companyAccountDao.updatePayPwd(account, MD5.encode(pwd, MD5.LENGTH_32));
		} catch (NoSuchAlgorithmException e) {
			return 0;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	}

	@Override
	public Integer judgePassWord(Integer companyId) {
		Integer flag=0;
		String pass=companyAccountDao.selectPassWord(companyId);
		if(StringUtils.isNotEmpty(pass)){
			flag=1;
			return flag; 
		}
		return flag;
	}
	
	@Override
	public Integer updateGmtLastLogin(Integer companyId){
		return companyAccountDao.updateGmtLastLogin(companyId);
	}

	@Override
	public Integer updateByCompanyAccount(CompanyAccount companyAccount) {
		return companyAccountDao.updateByCompanyAccount(companyAccount);
	}
	

	// public static void main(String args[]){
	// String url =
	// "http://www.zz91.com/app/validateAccount.htm?account=kongsj&password=123456";
	// String result;
	// try {
	// result = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
	// } catch (HttpException e) {
	// result = "";
	// } catch (IOException e) {
	// result = "";
	// }
	// String str = JSONObject.fromObject(result).getString("attest");
	// System.out.println(JSONObject.fromObject(result).getString("attest"));
	// }
}
