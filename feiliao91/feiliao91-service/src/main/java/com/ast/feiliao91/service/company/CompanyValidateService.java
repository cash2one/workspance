/**
 * @author shiqp
 * @date 2016-01-09
 */
package com.ast.feiliao91.service.company;

import java.util.Map;

import com.ast.feiliao91.domain.company.CompanyValidateSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyValidateDto;

public interface CompanyValidateService {

	final static String TYPE_EMAIL = "1001";

	/**
	 * 短信发送验证码
	 * 
	 * @param mobile
	 * @param code
	 *            默认　０　短信找回密码模板　１　短信解绑模板　２　使用短信绑定模板　 4 设置支付密码　5 修改支付密码　
	 * @return
	 */
	public void sendCodeByMobile(String mobile, String code);

	/**
	 * 结算宝短信验证模板
	 * 
	 * @param mobile
	 * @param map
	 */
	public void sendAllMobile(String mobile, Map<String, Object> map);

	/**
	 * 邮箱发送 验证码 用于找回密码 解除邮箱绑定　绑定邮箱解除
	 * 
	 * @param mobile
	 * @param code
	 *            默认为０　使用密码找回模板 １　使用邮箱解除模板　　２　使用邮箱绑定模板
	 * @return
	 */
	public void sendCodeByEmail(String email, String account, String code);

	/**
	 * 验证验证码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	public Integer validateByMobile(String mobile, String vcode);

	/**
	 * 验证验证码2.0
	 * 
	 * @param targetName
	 *            验证名
	 * @param vcode
	 *            验证码
	 * @param type
	 *            验证类型 邮箱1001
	 * @return
	 */
	Integer validateByType(String targetName, String vcode, String type);
	
	/**
	 * 后台搜索验证码
	 * @param page
	 * @param searchDto
	 * @return
	 */
	public PageDto<CompanyValidateDto> pageBySearchByAdmin(
			PageDto<CompanyValidateDto> page, CompanyValidateSearch searchDto);
	
}
