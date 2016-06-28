/**
 * 
 */
package com.ast.ast1949.service.company;

/**
 * @author mays
 *
 */
public interface CompanyValidateService {

	/**
	 * 向用户的邮箱发送激活邮件
	 * 发送的k=MD5(vcodeKey+account+vcode)
	 * @param account：not null,用以查找激活信息并组装成URL发送到用户邮箱中
	 * @param email：若为空，则根据 account 查找出用户当前使用的 email 地址
	 */
	public void sendValidateByEmail(String account, String email);
	
	/**
	 * 认证逻辑：v=MD5(vcodeKey+account+vcode)
	 * k => vcodeKey,vcode,account
	 * @param k：用户邮箱接连接点击的key
	 * @param v：验证k是否正确
	 * @return
	 */
	public Boolean validateByEmail(String k, String v);
	
	public Boolean isValidate(String account);
	
	/**
	 * 每天同类别验证的次数
	 * @param account
	 * @param activedType
	 * @param gmtCreated
	 * @return
	 */
	public Integer countValidateByCondition(String account, Integer activedType,String gmtCreated);
	
}
