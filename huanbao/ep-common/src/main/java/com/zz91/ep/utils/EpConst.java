/**
 * 
 */
package com.zz91.ep.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mays (mays@asto.com.cn)
 *
 * Created at 2012-12-12
 */
public class EpConst implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476902260059538096L;
	
	public final static String VALIDATE_CODE_KEY = "validatecodes";
	
	public final static String REGIST_TOKEN_KEY="regist_token";
	
	public final static Map<String, String> REGIST_ERROR_MAP = new HashMap<String, String>();
	static{
		REGIST_ERROR_MAP.put("0", "请按照流程填写信息，不要重复提交！");
		REGIST_ERROR_MAP.put("1", "您的验证码填写不正确，请重新填写！");
		REGIST_ERROR_MAP.put("2", "您的注册信息已提交，但服务器没有正确响应，请多试几次！");
		REGIST_ERROR_MAP.put("3", "用户名重复！");
		
	}
}
