/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.persist.market;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.market.MarketCompany;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.market.MarketCompanyDto;

public interface MarketCompanyDao {
	/**
	 * 最新加入市场的商户
	 * 
	 * @param marketId
	 * @param size
	 * @return
	 */
	public List<MarketCompany> queryNewComapny(Integer marketId, Integer size);
    /**
     * 添加市场商户
     * @param marketId
     * @param companyId
     * @return
     */
	public Integer insertMarketCompany(Integer marketId, Integer companyId);
	/**
	 * 某用户已加入的市场
	 * @param companyId
	 * @return
	 */
	public List<Integer> queryMarketByCompanyId(Integer companyId);
	
	/**
	 * 某用户第一个市场
	 * @param companyId
	 * @return
	 */
	public Integer queryFirstMarketByCompanyId(Integer companyId);
	/**
	 * 市场商户关系
	 * @param id
	 * @return
	 */
	public MarketCompany queryMarketCompanyById(Integer id);

	/**
	 * 检索用户与市场的关系
	 * @param marketId
	 * @param companyId
	 * @return
	 */
	public MarketCompany queryMarketCompanyByBothId(Integer marketId,Integer companyId);
	/**
	 * 用户是否退出某市场
	 * @param marketId
	 * @param companyId
	 * @param isQuit
	 * @return
	 */
	public Integer updateIsQuitByBothId(Integer marketId,Integer companyId,Integer isQuit);
	/**
	 * 加入市场的商户数
	 * @return
	 */
	public Integer countMarketCompany();
	
	/**
	 * 后台搜索该市场下的公司
	 * @param page
	 * @param marketId
	 * @return
	 */
	public List<MarketCompany> pageCompanyByAdminMarketId(PageDto<CompanyDto>page,Integer marketId,String membershipCode,Integer isPerson);
	/**
	 * 后台统计该市场下的公司数
	 * @param marketId
	 * @return
	 */
	public Integer pageCompanyByAdminMarketIdCount(Integer marketId,String membershipCode,Integer isPerson);
	
	/**
	 * 后台搜索市场中的公司
	 * @param page
	 * @param marketId
	 * @return
	 */
	public List<MarketCompany> queryListMarketByadmin(PageDto<MarketCompanyDto>page,CompanyAccount companyAccount,String companyName);
	
	/**
	 * 后台搜索市场中的公司数量
	 * @param page
	 * @param marketId
	 * @return
	 */
	public Integer queryListMarketByadminCount(CompanyAccount companyAccount,String companyName);
	
	List<MarketCompany> queryNewCompany2(Integer size);

}
