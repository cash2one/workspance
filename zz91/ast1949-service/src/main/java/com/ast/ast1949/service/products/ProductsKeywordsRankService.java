/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-26.
 */
package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsKeywordsRankDO;
import com.ast.ast1949.dto.products.ProductsCompanyDTO;
import com.ast.ast1949.dto.products.ProductsKeywordsRankDTO;

/**
 * 关键字排名接口
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface ProductsKeywordsRankService {
	/**
	 * 添加一条关键字排名记录
	 * @param productsKeywordsRank 关键字排名详情信息<br>
	 * 		关键字name不能为空；<br/>
	 * 		供求编号productId不能为空；<br/>
	 * 		标王类型type不能为空；<br/>
	 * 		时间段startTime，endTime不能为空。<br/>
	 * @return 添加成功返回新增记录的编号。
	 */
	public Integer insertProductsKeywordsRank(ProductsKeywordsRankDO productsKeywordsRank);
	/**
	 * 根据编号删除一条记录
	 * @param id 要删除记录的编号，参数不能为空。
	 * @return 
	 * 		删除成功返回被删除记录的总数；
	 * 		删除失败返回“0”。
	 */
	public Integer deleteProductsKeywordsRankById(Integer id);
	/**
	 * 根据编号修改一条记录
	 * @param productsKeywordsRank 关键字排名详情信息<br/>
	 * 		参数id不能为空
	 * @return
	 */
	public Integer updateProductsKeywordsRankById(ProductsKeywordsRankDO productsKeywordsRank);
	/**
	 * 根据编号查询一条记录
	 * @param id 编号，参数不能为空。
	 * @return 
	 * 		成功返回该对象，否则返回null。
	 */
	public ProductsKeywordsRankDO queryProductsKeywordsRankById(Integer id);
	/**
	 * 根据特定条件查询记录
	 * @param condition 查询条件
	 * @return 成功返回所有符合条件的结果集，否则返回null。
	 */
	public List<ProductsKeywordsRankDTO> queryProductsKeywordsRankByConditions(ProductsKeywordsRankDTO condition);
	/**
	 * 根据特定条件统计记录总数
	 * @param condition 查询条件
	 * @return 成功返回所有符合条件记录的总数，否则返回“0”。
	 */
	public Integer countProductsKeywordsRankByConditions(ProductsKeywordsRankDTO condition);
	/**
	 * 根据编号更新一条记录的审核状态
	 * @param id 所要更新记录的编号，不能为空。
	 * @param value 审核状态，
	 * 		“1” 代表已审核；
	 * 		“0” 代表未审核。
	 * @return
	 */
	public Boolean updateCheckedById(String id,String value);
	/**
	 * 根据供求编号查询该供求已搞买的关键字排名记录
	 * @param id 供求编号，不能为空。
	 * @return 成功返回所有符合条件的结果集，否则返回null。
	 */
//	public List<ProductsKeywordsRankDO> queryProductsKeywordsRankByProductId(Integer id);
	
	/**
	 * 根据关键字，和购买的关键字类型（标王类型）查询供求信息
	 * @param keywords
	 * @param buiedType
	 * @param topNum
	 * @return
	 */
	public List<ProductsCompanyDTO> queryProductsByKeywordsAndBuiedType(String keywords,
			String buiedType, int topNum);
	
	public List<Integer> queryProductsId(String keywords,String buiedType, Integer maxSize);
	//查询公司开通的服务 
	public List<ProductsKeywordsRankDTO> queryProductsKeywordsRankByCompanyId(Integer companyId);
}
