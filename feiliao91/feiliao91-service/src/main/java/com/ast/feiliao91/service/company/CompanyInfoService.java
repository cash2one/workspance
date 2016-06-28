/**
 * @author shiqp
 * @date 2016-01-11
 */
package com.ast.feiliao91.service.company;

import java.util.List;

import com.ast.feiliao91.domain.common.DataIndexDO;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanySearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;

public interface CompanyInfoService {
	/**
	 * 创建公司信息
	 * @param companyInfo
	 * @return
	 */
	public Integer insertCompanyInfo(CompanyInfo companyInfo);
	/**
	 * 公司信息
	 * @param id
	 * @return
	 */
	public CompanyInfo queryInfoByid(Integer id);
	
	/**
	 * 获取公司所有信息，包括账户信息
	 */
	public CompanyDto queryCompanyDtoById(Integer id);
    /**
     * 修改／添加　个人公司认证信息
     */
	public Integer updateValidate(CompanyInfo companyInfo);
	
	/**
	 * 根据dataindex提供的id，构建符合首页显示的companydto
	 */
	public List<CompanyDto> bulidCompanyDtoListForIndex(List<DataIndexDO> list);
	/**
	 * 后台公司信息
	 * @param page
	 * @param search
	 * @return
	 */
	public PageDto<CompanyDto> pageBySearch(PageDto<CompanyDto> page,CompanySearch search);
	
	/**
	 * 审核公司认证(可多选)
	 * @param ids
	 * @param checkStatus
	 * @return
	 */
	public String updateStatus(String ids,Integer checkStatus);
	
	/**
	 * 更改删除状态
	 * @param ids
	 * @param checkStatus
	 * @return
	 */
	public String updateDelStatus(String ids,Integer checkStatus);
	/**
	 * 后台获得公司信息
	 * @param id
	 * @return
	 */
	public CompanyDto queryCompanyDtoByIdAdmin(Integer id);
	/**
	 * 后台保存公司信息
	 * @param companyInfo
	 * @return
	 */
	public Integer updateCompanyByAdmin(CompanyInfo companyInfo);
	/**
	 * 后台搜索公司信息
	 * @param page
	 * @param search
	 * @return
	 */
	PageDto<CompanyDto> pageBySearchAdmin(PageDto<CompanyDto> page,
			CompanySearch search);
	
}
