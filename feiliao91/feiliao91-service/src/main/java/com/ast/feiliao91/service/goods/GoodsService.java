/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.service.goods;

import java.util.List;

import com.ast.feiliao91.domain.common.DataIndexDO;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsAddProperties;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.dto.PageDto;

public interface GoodsService {
	/**
	 * 获取常用类别
	 * @param companyId
	 * @param size
	 * @return
	 */
	public List<String> queryCategoryByCompanyId(Integer companyId, Integer size);
	/**
	 * 创建产品
	 * @param good
	 * @return
	 */
	public Integer createGoods(Goods good);
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
	
	/**
	 * 分页方法
	 * @param searchDto
	 * @param page
	 * @return
	 */
	public PageDto<GoodsDto> pageBySearch(GoodsSearchDto searchDto,PageDto<GoodsDto> page);
	/**
	 * 分页方法(后台)
	 * @param searchDto
	 * @param page
	 * @return
	 */
	public PageDto<GoodsDto> pageBySearchAdmin(GoodsSearchDto searchDto,PageDto<GoodsDto> page);
	
	/**
	 * 搜索引擎分页
	 */
	public PageDto<GoodsDto> pageBySearchEngine(GoodsSearchDto searchDto,PageDto<GoodsDto> page);
	/**
	 * 获取产品信息
	 * @param id
	 * @return
	 */
	public GoodsDto queryGoodInfoById(Integer id);
	
	/**
	 * 获取产品信息后台
	 * @author zhujq
	 * @param id
	 * @return
	 */
	public GoodsDto queryGoodInfoByIdAdmin(Integer id);
	
	/**
	 * 根据dataindex传入的数据，构建页面需要的goodsDto
	 */
	public List<GoodsDto> buildDtoForIndex(List<DataIndexDO> list);
	/**
	 * 获取产品信息
	 * @param companyId
	 * @param mainCategory
	 * @param size
	 * @param goodsId
	 * @return
	 */
	public List<GoodsDto> listNewGoods(Integer companyId, String mainCategory, Integer size, Integer goodsId);
	/**
	 * 获取订单的产品信息
	 * @param id
	 * @return
	 */
	public GoodsDto queryGoodsDtoById(Integer id);
	
	/**
	 * 更改用户发布状态 删除 上架
	 * @param id
	 * @param type
	 * @return
	 */
	public Integer updateStatusByUser(Integer id,Integer type);
	
	/**
	 * 获取公司地址
	 * @param code
	 * @return
	 */
	public String getCity(String code);
	/**
	 * 审核产品
	 * @param ids
	 * @param checkPerson
	 * @param checkStatus
	 * @return
	 */
	public String updateStatus(String ids,String checkPerson,Integer checkStatus);
	
	/**
	 * 最新产品信息 9条(改为分三页【相同关键字】第一页（type=1）取最新发布的  第二页（type=2）取销量最高的 第三页（type=3）取随机的 By zhujq)
	 * 
	 * @param mainCategory
	 * @param size
	 * @param goodsId
	 * @return List<Goods>
	 */
	public List<GoodsDto> queryTypeBySameCategory(String mainCategory,Integer size, Integer goodsId);
	/**
	 * 最终页随机取四条
	 * 
	 * @param mainCategory
	 * @param size
	 * @param goodsId
	 * @return List<Goods>
	 */
	public List<GoodsDto> queryRandomGoods(String mainCategory,Integer size, Integer goodsId);
	//取销量最高的
//	public List<Object> queryH(String mainCategory,Integer size, Integer goodsId);
	
	/**
	 * 列出该商品的所有自定义属性
	 * @author zhujq
	 * @param id
	 * @return
	 */
	public List<GoodsAddProperties> queryByGoodsAddProperties(Integer id);
}
