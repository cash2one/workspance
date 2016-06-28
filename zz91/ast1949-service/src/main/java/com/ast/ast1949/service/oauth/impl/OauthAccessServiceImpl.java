package com.ast.ast1949.service.oauth.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.persist.oauth.OauthAccessDao;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.zz91.util.lang.StringUtils;

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
			return oauthAccessDao.insert(oauthAccess);
		}
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

}
