/**
 * 
 */
package com.ast.ast1949.service.company.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyValidate;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyValidateDao;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;

/**
 * @author mays
 *
 */
@Component("companyValidateService")
public class CompanyValidateServiceImpl implements CompanyValidateService {

	@Resource
	private CompanyValidateDao companyValidateDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	
	final static Integer ACTIVE_ADMIN=10;
	final static Integer ACTIVE_EMAIL=20;
	final static Integer ACTIVE_SMS=30;
	
	@Override
	public void sendValidateByEmail(String account, String email) {
		
		if(StringUtils.isEmpty(account)){
			return ;
		}
		
		CompanyValidate cv=companyValidateDao.queryOneByAccount(account);
		
		if(cv==null){
			return ;
		}
		
		if(StringUtils.isEmpty(email)){
			CompanyAccount ac=companyAccountDao.queryAccountByAccount(account);
			if(ac.getIsUseBackEmail()!=null && ac.getIsUseBackEmail().equals("1")){
				email=ac.getBackEmail();
			} else{
				email = ac.getEmail();
			}
		}
		
		String v="";
		try {
			v=MD5.encode(cv.getVcodeKey()+account+cv.getVcode(), MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("activeUrl", "/register/doValidateByEmail.htm?k="+cv.getVcodeKey()+"&v="+v);
		paramMap.put("account", account);
		paramMap.put("email", email);
		
		MailUtil.getInstance().sendMail(null, null, "账户激活", email, null, DateUtil.getDateAfterDays(new Date(), -1)	, "zz91", "zz91-register", paramMap, MailUtil.PRIORITY_HEIGHT);
	}
	
	@Override
	public Boolean validateByEmail(String k, String v) {
		
		do {
			if(StringUtils.isEmpty(k) || StringUtils.isEmpty(v)){
				break;
			}
			
			CompanyValidate cv = companyValidateDao.queryOneByKey(k);
			if(cv==null){
				break;
			}
			
			if(cv.getActived()>0){
				break;
			}
			
			String vv="";
			
			try {
				vv=MD5.encode(cv.getVcodeKey()+cv.getAccount()+cv.getVcode(), MD5.LENGTH_32);
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}
			
			if(!vv.equals(v)){
				break;
			}
			
			companyValidateDao.updateActived(cv.getId(), ACTIVE_EMAIL);
			
			return true;
			
		} while (false);
		
		
		return false;
	}

	@Override
	public Boolean isValidate(String account) {
		
		Integer actived=companyValidateDao.queryActived(account);
		if(actived!=null && actived.intValue()==0){
			return false;
		}
		
		return true;
	}

	@Override
	public Integer countValidateByCondition(String account, Integer activedType, String gmtCreated) {
		return companyValidateDao.countValidateByCondition(account, activedType, gmtCreated);
	}

}
