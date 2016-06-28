package com.ast.ast1949.service.oauth;

import java.util.HashMap;
import java.util.Map;

import com.ast.ast1949.domain.oauth.OauthAccess;

public interface OauthAccessService {
	
	final static String OPEN_TYPE_QQ = "qq.com";
	final static String OPEN_TYPE_WEIXIN = "weixin.qq.com";
	final static String OPEN_TYPE_MOBILE = "mobile";
	
	final static Map<String, String> OPEN_ID_MAP = new HashMap<String, String>();
	
	
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType);
	
	public OauthAccess queryAccessByAccountAndType(String account,String openType);

	public Integer addOneAccess(String openId,String openType,String targetAccount);

	public Integer updateByOpenId(String openId, String targetAccount);

	public Integer addOneMobileAccess(String openId, String openType,String targetAccount);

	/**
	 * 验证微信验证码
	 * @param code
	 * @param account
	 * @return
	 */
	public boolean validateWXCode(String code, String account);
	//解绑微信
	public void deleteByTargetAccount(String targetAccount);
}
