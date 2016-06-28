package com.ast.feiliao91.service.company;

import java.util.List;

import com.ast.feiliao91.domain.company.CompanyService;

public interface CompanyServiceService {
	
	final static String SEVEN_DAY_SERVICE = "10041000";
	final static String BZJ_SERVICE = "10041001";
	
	/**
	 * 创建公司服务
	 * @param companyService
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
     * 根据company_id查询服务(返回列表，一个公司可有多个服务)
     * @param company_id,要查询的服务个数pageSize,pageSize大于50时，只取50条
	 * @return List<CompanyService>
     */
	public List<CompanyService> queryCompanyServiceListByCompanyId(Integer companyId,Integer pageSize);
	
	/**
	 * 检验是否含有相应code所指向的服务
	 * @param companyId
	 * @param code
	 * @return
	 */
	public boolean validateServiceByCode(Integer companyId,String code);
	
	/**
	 * 开通一个服务
	 * @param companyId
	 * @param code 服务名
	 */
	public Integer createByCode(Integer companyId,String code);
	
	/**
	 * 
	 * @param companyId
	 * @param code
	 * @return
	 */
	Integer closeByCode(Integer companyId, String code);
}
