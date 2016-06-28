package com.ast.ast1949.service.oauth.impl;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.persist.oauth.OauthAccessDao;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.sms.SmsUtil;

@Component("oauthAccessService")
public class OauthAccessServiceImpl implements OauthAccessService {

	@Resource
	private OauthAccessDao oauthAccessDao;

	@Override
	public Integer addOneAccess(String openId, String openType,
			String targetAccount) {
		// 三个参数均不能为空
		if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(openType)
				|| StringUtils.isEmpty(targetAccount)) {
			return null;
		}
		OauthAccess oa = oauthAccessDao.queryAccessByOpenIdAndType(openId, OauthAccessService.OPEN_TYPE_QQ);
		if(oa!=null){
			return oauthAccessDao.updateByOpenId(openId, targetAccount);
		}else{
			OauthAccess oauthAccess = new OauthAccess();
			oauthAccess.setOpenId(openId);
			oauthAccess.setOpenType(openType);
			oauthAccess.setTargetAccount(targetAccount);
			oauthAccess.setCode(null);
			return oauthAccessDao.insert(oauthAccess);
		}
	}
	
	@Override
	public Integer addOneMobileAccess(String openId, String openType,String targetAccount){
		do {
			String number = "";
			Random r = new Random();
			for (int i = 0; i < 6; i++) {
				number = number + r.nextInt(9);
			}
			if(StringUtils.isEmpty(number)){
				break;
			}
			
			// 当天超过五条不发
			Integer i = oauthAccessDao.countAccessByOpenId(openId);
			if(i>=5){
				break;
			}
			SmsUtil.getInstance().sendSms(openId, "欢迎使用ZZ91再生网服务。验证码是："+number+",输入验证码继续完成操作，该验证码30分钟内输入有效。", "yuexin");
			OauthAccess oauthAccess = new OauthAccess();
			oauthAccess.setOpenId(openId);
			oauthAccess.setOpenType(openType);
			oauthAccess.setTargetAccount(targetAccount);
			oauthAccess.setCode(number);
			return oauthAccessDao.insert(oauthAccess);
		} while (false);
		return 0;
	}

	@Override
	public OauthAccess queryAccessByOpenIdAndType(String openId, String openType) {
		return oauthAccessDao.queryAccessByOpenIdAndType(openId, openType);
	}

	@Override
	public Integer updateByOpenId(String openId, String targetAccount) {
		return oauthAccessDao.updateByOpenId(openId, targetAccount);
	}

	@Override
	public OauthAccess queryAccessByAccountAndType(String account,
			String openType) {
		return oauthAccessDao.queryAccessByAccountAndType(account, openType);
	}
	
	@Override
	public boolean validateWXCode(String code,String account){
		Date date = new Date();
		long dateLong = date.getTime()-1000*60*10;
		date.setTime(dateLong);
		String gmtLimit = DateUtil.toString(date, "yyyy-MM-dd HH:mm:ss");
		Integer id = oauthAccessDao.queryByWXCode(code, gmtLimit);
		Integer i = 0;
		if(id!=null){
			i = oauthAccessDao.updateWXTargetAccountById(id, account);
		}
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public void deleteByTargetAccount(String targetAccount) {
		oauthAccessDao.deleteByTargetAccount(targetAccount);
	}

}
