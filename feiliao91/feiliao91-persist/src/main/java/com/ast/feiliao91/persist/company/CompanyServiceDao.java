/**
 * @author zhujq
 * @date 2016-01-27
 */
package com.ast.feiliao91.persist.company;

import java.util.List;

import com.ast.feiliao91.domain.company.CompanyService;

public interface CompanyServiceDao {
	/**
	 * 创建公司服务
	 * @param CompanyService
	 * @return
	 */
	public Integer insertCompanyService(CompanyService companyService);
	/**
     * 根据ID查询服务
     * @param id
	 * @return CompanyService
     */
	public CompanyService queryCompanyServiceById(Integer id);
	/**
     * 根据company_id,与所要查询的服务条数pageSize获得服务列表(返回列表，一个公司可有多个服务)
     * @param company_id,pageSize
	 * @return List<CompanyService>
     */
	public List<CompanyService> queryCompanyServiceListByCompanyId(Integer companyId,Integer pageSize);
	/**
     * 根据companyId,service_code返回count数,判断改公司是否拥有该服务
     * @param companyId,serviceCode
	 * @return Integer
     */
	public Integer queryServiceCount(Integer companyId,String serviceCode);
	
	/**
	 * 开启服务
	 */
	public Integer updateToOpen(Integer companyId,String serviceCode);
	
	/**
	 * 关闭服务
	 */
	public Integer updateToClose(Integer companyId,String serviceCode);
}
