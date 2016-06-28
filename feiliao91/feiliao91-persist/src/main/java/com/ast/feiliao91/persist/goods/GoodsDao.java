/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.PageDto;

public interface GoodsDao {
	/**
	 * 创建产品信息
	 * @param goods
	 * @return
	 */
	public Integer insertGoods(Goods goods);
	/**
	 * 某公司发布的最新产品类别
	 * @param companyId
	 * @param size
	 * @return
	 */
	public List<String> queryCategoryByCompanyId(Integer companyId, Integer size);
	/**
	 * 获取产品信息
	 * @param id
	 * @return
	 */
	public Goods queryGoodById(Integer id);
	/**
	 * 更新产品信息
	 * @param goods
	 * @return
	 */
	public Integer updateGoods(Goods goods);

	
	public List<Goods> queryBySearchDto(GoodsSearchDto searchDto,PageDto<GoodsDto>page);
	
	public Integer queryCountBySearchDto(GoodsSearchDto searchDto);
	
	public Goods queryById(Integer id);
	
	public Integer querySuccessOrder(Integer goodsId,Integer companyId);
	/**
	 * 获取产品信息
	 * @param companyId
	 * @param mainCategory
	 * @param size
	 * @return
	 */
	public List<Goods> queryGoodsByCategory(Integer companyId, String mainCategory,Integer size, Integer goodsId);
	
	/**
	 * 获取最新的同类别产品信息
	 * 
	 * @param mainCategory
	 * @param size
	 * @param goodsId
	 * @return List<Goods>
	 */
	public List<Goods> queryNewGoodsBySameCategory(String mainCategory,Integer size, Integer goodsId);
	/**
	 * 获取销量最高的同类别产品信息
	 * 
	 * @param mainCategory
	 * @param size
	 * @param goodsId
	 * @return List<Goods>
	 */
	public List<Object> queryHighSalesGoodsBySameCategory(String mainCategory,Integer size, Integer goodsId);
	/**
	 * 获取随机的同类别产品信息
	 * 
	 * @param mainCategory
	 * @param size
	 * @param goodsId
	 * @return List<Goods>
	 */
	public List<Goods> queryRandomGoodsBySameCategory(String mainCategory,Integer size, Integer goodsId);
	
	/**
	 * 更新删除，上架状态
	 * @param id
	 * @param isDel
	 * @param isSell
	 * @return
	 */
	public Integer updateStatusByUser(Integer id, Integer isDel, Integer isSell,Integer isgarbage);
	/**
	 * 审核产品信息
	 * @param id
	 * @param checkPerson
	 * @param checkStatus
	 * @return
	 */
	public Integer updateStatus(Integer id,String checkPerson,Integer checkStatus);
	/**
	 * 下单或取消订单时加减商品数量
	 * @param goodsId
	 * @param 新的quantity
	 * @return
	 */
	public Integer updateGoodsQuantityByGoodsId(String quantity,Integer goodsId);
}
