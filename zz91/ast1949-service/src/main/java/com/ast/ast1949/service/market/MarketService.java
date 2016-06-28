/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.service.market;

import java.util.List;

import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.MarketDo;
import com.ast.ast1949.domain.market.MarketSubscribe;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketSearchDto;
import com.ast.ast1949.dto.market.MarketSubscribeDto;

public interface MarketService {
	
	final static Integer TYPE_SUBSCRIBE_KEYWORD = 1;// 订阅类型关键字
	final static Integer TYPE_SUBSCRIBE_MARKET = 2;// 订阅类型市场
	
	public Integer countMarketByCondition(String area, String industry, String category);
	
	public List<Market> queryMarketByCondition(String industry, String area, Integer size);
	/**
	 * 弹框标志 正常 0，完善 1，不匹配 2
	 * @param companyId
	 * @return
	 */
	public Integer getBoxFlag(Integer companyId);
	
	public PageDto<Market> pageSearchOfMarket(String provice, String category ,String industry, Integer flag,Integer id, PageDto<Market> page,String keywords,Integer dir);
	
	public Market queryMarketById(Integer id);
	public Market queryMarketByWords(String words);
	
	public PageDto<Market> queryAllMarket(PageDto<Market> page,Integer type);
	
	public MarketDo queryNumByProvince(String area);
	/**
     * 根据地区或者所属类别获取市场详情
     * @param area
     * @param category
     * @return
     */
    public List<Market> queryMarketByProOrCate(String area,String category);


	public PageDto<Market> pageQueryMark(Market market,PageDto<Market>page,Integer hasPic);
	/**
	 * 修改市场
	 * @param market
	 * @return
	 */
	public Integer updateMarket(Market market);
	/**
	 * 插入市场
	 * @param market
	 * @return
	 */
	public Integer insertMarket(Market market);
	/**
	 * 统计供求的数量
	 * @return
	 */
	public Integer sumProductNum();
	
	/**
	 * 插入一条关注
	 * @param companyId 公司id
	 * @param type 类型1订阅关键字 2订阅市场
	 * @param key
	 * @return
	 */
	public Integer insertSubscribe(Integer companyId,Integer type,String key);
	
	/**
	 * 删除一条关注，其实没有删除，只是将标记修改为1
	 * @param id
	 * @param companyId
	 * @return
	 */
	public Integer deleteSubscribe(Integer id,Integer companyId);
	
	/**
	 * 根据公司 id，检索size条数的订阅信息
	 * @param companyId
	 * @param type 订阅类型 1订阅关键字 2订阅市场
	 * @param size 条数，默认是5条，不可为空
	 * @return
	 */
	public List<MarketSubscribe> querySubscribeByCompanyId(Integer companyId,Integer type,Integer size);
	
	/**
	 * 后台检索方法，管理用户的订阅信息
	 * @param searchDto
	 * @param page
	 * @return
	 */
	public PageDto<MarketSubscribeDto> pageSubscribeByAdmin(MarketSearchDto searchDto,PageDto<MarketSubscribeDto> page);


}
