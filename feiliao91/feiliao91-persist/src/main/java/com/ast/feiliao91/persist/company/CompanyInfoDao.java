/**
 * @author shiqp
 * @date 2016-01-11
 */
package com.ast.feiliao91.persist.company;

import java.util.List;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanySearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;

public interface CompanyInfoDao {
	/**
	 * 创建公司信息
	 * @param companyInfo
	 * @return
	 */
	public Integer insertCompanyInfo(CompanyInfo companyInfo);
	
	/**
	 * 根据id检索公司信息
	 */
	public CompanyInfo queryById(Integer id);
    /**
     *修改／添加　　公司或者个人认证信息
     * @return
     */
	public Integer updateValidate(CompanyInfo companyInfo);
	/**
	 * 公司列表
	 * @param page
	 * @param search
	 * @return
	 */
	public List<CompanyInfo> queryCompanyList(PageDto<CompanyDto> page, CompanySearch search);
	/**
	 * 公司列表集合
	 * @param search
	 * @return
	 */
	public Integer countCompanyList(CompanySearch search);

	/**
	 * 不检索用户 资质认证信息
	 */
	public CompanyInfo queryWithoutCheckInfoById(Integer id);
	
	/**
	 * 审核公司认证(可多选)
	 * @param ids
	 * @param checkStatus
	 * @return
	 */
	public Integer updateStatus(Integer valueOf, Integer checkStatus);
	
	/**
	 * 更新公司信息删除状态
	 * @param valueOf
	 * @param checkStatus
	 * @return
	 */
	public Integer updateDelStatus(Integer valueOf, Integer checkStatus);
	
	/**
	 * 后台保存公司信息
	 * @param companyInfo
	 * @return
	 */
	public Integer updateCompanyByAdmin(CompanyInfo companyInfo);
}
