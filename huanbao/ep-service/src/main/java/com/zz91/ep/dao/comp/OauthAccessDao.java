package com.zz91.ep.dao.comp;

import com.zz91.ep.domain.comp.OauthAccess;

public interface OauthAccessDao {
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType);
	
	public OauthAccess queryAccessByAccountAndType(String account,String openType);

	public Integer insert(OauthAccess oauthAccess);

	public Integer updateByOpenId(String openId, String targetAccount);
}
