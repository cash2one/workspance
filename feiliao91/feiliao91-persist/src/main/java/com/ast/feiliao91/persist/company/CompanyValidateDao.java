/**
 * @author shiqp
 * @date 2016-01-09
 */
package com.ast.feiliao91.persist.company;

import java.util.List;

import com.ast.feiliao91.domain.company.CompanyValidate;
import com.ast.feiliao91.domain.company.CompanyValidateSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyValidateDto;

public interface CompanyValidateDao {
	/**
	 * 创建验证信息
	 * @param validate
	 * @return
	 */
	public Integer insertValidate(CompanyValidate validate);
	/**
	 * 根据name和type检索最新的验证信息
	 * @param targetName
	 * @param targetType
	 * @return
	 */
	public CompanyValidate queryValidateByNameAndType(String targetName, String targetType);
	
	/**
	 * 更新验证状态
	 * @param id
	 * @return
	 */
	public Integer updateValidateById(Integer id);
	
	/**
	 * 后台验证码列表
	 * @param page
	 * @param searchDto
	 * @return
	 */
	public List<CompanyValidate> querycompanyValidateByAdmin(
			PageDto<CompanyValidateDto> page, CompanyValidateSearch searchDto);
	
	/**
	 * 后台验证码条数
	 * @param searchDto
	 * @return
	 */
	public Integer countcompanyValidateByAdmin(CompanyValidateSearch searchDto);
}
