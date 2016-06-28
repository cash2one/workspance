/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.service.market;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.market.MarketCompanyDto;

public interface MarketCompanyService {
	public List<MarketCompanyDto> queryNewCompany(Integer marketId, Integer size);
	
	public List<Market> queryMarketByCompanyId(Integer companyId);
	
	public Integer insertMarketCompany(Integer marketId, Integer companyId);
	/**
	 * 市场商户、优质市场商户推荐
	 * @param page
	 * @param marketId
	 * @param type
	 * @return
	 */
	public PageDto<CompanyDto> PageSearchCompanyByCondition(PageDto<CompanyDto> page,Integer marketId, Integer type);
	

	public Integer countMarketCompany();

	/**
	 * 后台所属该市场下的商户
	 * @param page
	 * @param marketId
	 * @return
	 */
	public PageDto<CompanyDto> pageCompanyByAdminMarketId(PageDto<CompanyDto>page,Integer marketId,String membershipCode,Integer isPerson);
    /**
     * 后台搜索市场中所有商户
     * @param page
     * @param companyAccount
     * @param companyName
     * @return
     */
    public PageDto<MarketCompanyDto> queryListMarketByadmin(PageDto<MarketCompanyDto>page,CompanyAccount companyAccount,String companyName);
    /**
     * 该公司退出该市场
     * @param marketId
     * @param companyId
     * @param isQuit
     * @return
     */
    public Integer updateIsQuitByBothId(Integer marketId,Integer companyId,Integer isQuit);

    /**
     * 获取第一个市场
     * @param companyId
     * @return
     */
	public Market queryFirstMarketByCompanyId(Integer companyId);
}
