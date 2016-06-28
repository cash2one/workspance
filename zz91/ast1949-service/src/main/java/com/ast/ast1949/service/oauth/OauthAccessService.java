package com.ast.ast1949.service.oauth;

import com.ast.ast1949.domain.oauth.OauthAccess;

public interface OauthAccessService {
	
	final static String OPEN_TYPE_QQ = "qq.com";
	
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType);
	
	public OauthAccess queryAccessByAccountAndType(String account,String openType);

	public Integer addOneAccess(String openId,String openType,String targetAccount);

	public Integer updateByOpenId(String openId, String targetAccount);
}
