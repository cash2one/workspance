package com.zz91.ep.service.comp;

import com.zz91.ep.domain.comp.OauthAccess;


public interface OauthAccessService {
final static String OPEN_TYPE_QQ = "qq.com";
	
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType);
	
	public OauthAccess queryAccessByAccountAndType(String account,String openType);

	public Integer addOneAccess(String openId,String openType,String targetAccount);

	public Integer updateByOpenId(String openId, String targetAccount);
}
