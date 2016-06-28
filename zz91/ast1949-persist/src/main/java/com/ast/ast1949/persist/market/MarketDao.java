/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.persist.market;

import java.util.List;

import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.dto.PageDto;

public interface MarketDao {
	/**
	 * 计算各种条件下市场的个数
	 * @param area
	 * @param industry
	 * @param category
	 * @return
	 */
	public Integer countMarketByCondition(String area, String industry, String category);
	/**
	 * 添加市场
	 * @param market
	 * @return
	 */
	public Integer insertMarket(Market market);
	/**
	 * 该市场信息
	 * @param id
	 * @return
	 */
	public Market queryMarketById(Integer id);
	/**
	 * 该市场信息
	 * @param words
	 * @return
	 */
	public Market queryMarketByWords(String words);
	/**
	 * 同行业市场，按商户数降序
	 * @param industry
	 * @param size
	 * @return
	 */
	public List<Market> queryMarketByCondition(String industry, String area, Integer size);
	/**
	 * 未删除的所有市场
	 * @param page
	 * @param type
	 * @return
	 */
	public List<Market> queryAllMarket(PageDto<Market> page,Integer type);
	/**
	 * 未删除市场数
	 * @param page
	 * @param type
	 * @return
	 */
    public Integer countAllMarket(PageDto<Market> page,Integer type);
    /**
     * 某省市场数
     * @param area
     * @return
     */
    public Integer countMarketByProvice(String area);
    /**
     * 某省商户数
     * @param area
     * @return
     */
    public Integer countCompanyByProvice(String area);
    /**
     * 某省供求数
     * @param area
     * @return
     */
    public Integer countProductByProvice(String area);
    /**
     * 根据地区或者所属类别获取市场详情
     * @param area
     * @param category
     * @return
     */
    public List<Market> queryMarketByProOrCate(String area,String category);
     /**
	 * 搜索数据库中的所有市场
	 * @param market
	 * @param page
	 * @return
	 */
	public List<Market> pageQueryMarket(Market market,PageDto<Market> page,Integer hasPic);
	
	/**
	 * 统计数据库中的所有市场总数
	 * @param market
	 * @return
	 */
	public Integer pageQueryMarketCount(Market market,Integer hasPic);
	
	/**
	 * 更改市场信息
	 * @param market
	 * @return
	 */
	public Integer updateMarkt(Market market);
	/**
	 * 更新入驻该市场的公司数
	 * @param id
	 * @param companyNum
	 * @return
	 */
	public Integer updateCompanyByMarketId(Integer id,Integer companyNum);
	/**
	 * 统计供求的数量
	 * @return
	 */
	public Integer sumProductNum();


}
