package com.ast.ast1949.persist.oauth;

import com.ast.ast1949.domain.oauth.OauthAccess;

public interface OauthAccessDao {
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType);
	
	public OauthAccess queryAccessByAccountAndType(String account,String openType);

	public Integer insert(OauthAccess oauthAccess);

	public Integer updateByOpenId(String openId, String targetAccount);
}
