/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25
 */
package com.ast.ast1949.persist.price;

import java.util.List;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceCategoryMinDto;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface PriceCategoryDAO {
	/**
	 * 添加一条资讯类别记录
	 * @param priceCategoryDO <br/>
	 * 		类别名称“name”不能为null或""，否则抛出异常;<br/>
	 * 		当parentId 等于“0”时，说明该类为父类；<br/>
	 * 		当parentId 大于“0”时，说明该类为子类.
	 * @return 当返回值大于0时，添加成功;否则,添加失败。
	 * @throws IllegalArgumentException
	 */
	public Integer insertPriceCategory(PriceCategoryDO priceCategoryDO) throws IllegalArgumentException;
	/**
	 * 删除一条资讯类别记录
	 * @param id 不能为null或"",且必须为整形，否则抛出异常;<br/>
	 * @return 当返回值大于0时，删除成功;否则,删除失败。
	 * @throws IllegalArgumentException
	 */
	public Integer deletePriceCategoryById(Integer id) throws IllegalArgumentException;
	/**
	 * 批量删指定除资讯类别记录
	 * @param entities 不能为null或""，否则抛出异常;<br/>
	 * @return 当返回值大于0时，删除成功;否则,删除失败。
	 * @throws IllegalArgumentException
	 */
//	public Integer batchDeletePriceCategoryById(int[] entities);
	/**
	 * 更新指定的一条资讯类别记录
	 * @param priceCategoryDO
	 * 		id 不能为null或"",且必须为整形，否则抛出异常;<br/>
	 * 		类别名称“name”不能为null或""，否则抛出异常;<br/>
	 * @return 当返回值大于0时，删除成功;否则,删除失败。
	 * @throws IllegalArgumentException
	 */
	public Integer updatePriceCategoryById(PriceCategoryDO priceCategoryDO) throws IllegalArgumentException;
	/**
	 * 读取指定资讯类别记录
	 * @param id 不能为null或"",且必须为整形，否则抛出异常;<br/>
	 * @return 资讯类别信息
	 * @throws IllegalArgumentException
	 */
	public PriceCategoryDO queryPriceCategoryById(Integer id) throws IllegalArgumentException;
	/**
	 * 按条件查询资讯类别
	 * @param priceCategoryDO
	 * @return 返回所有资讯类别信息,没有数据时List长度为“0”.
	 */
//	public List<PriceCategoryDO> queryPriceCategoryByCondition(PriceCategoryDTO priceCategoryDO);
	/**
	 * 按条件查询资讯类别总数
	 * @param priceCategoryDO
	 * @return 返回类别总数,没有数据时返回“0”.
	 */
//	public Integer getPriceCategoryRecordConutByCondition(PriceCategoryDTO priceCategoryDO);

	/**
	 * 标记此类是否删除
	 * @param id 要标记的类别编号<br/>
	 * 	id 不能为null或"",且必须为整形，否则抛出异常;<br/>
	 * @param isDelete <br/>
	 * 		当isDelete值为0时，表示删除；<br/>
	 * 		当isDelete值为1时，表示不删除。
	 * @return 当返回值大于0时，更新成功;否则,更新失败。
	 */
	public Integer updatePriceCategoryIsDeleteById(Integer id,short isDelete) throws IllegalArgumentException;

	/**
	 * 根据父节点读取指定资讯类别记录
	 * @param id 不能为null或"",且必须为整形，否则抛出异常;<br/>
	 * @return 资讯类别信息
	 * @throws IllegalArgumentException
	 */
	public List<PriceCategoryDO> queryPriceCategoryByParentId(Integer id) throws IllegalArgumentException;
	
//	public List<PriceCategoryLinkDO> queryPriceCategoryLink(PriceCategoryLinkDO priceCategoryLinkDO);
	
	/**
	 * 读取指定资讯类别记录
	 * @param id 不能为null或"",且必须为整形，否则抛出异常;<br/>
	 * @return 资讯类别信息
	 */
	public PriceCategoryDTO queryPriceCategoryDtoById(Integer id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<PriceCategoryDO> queryAssistPriceCategoryByTypeId(Integer id);
	
	/**
	 * 统计信息
	 * @param priceCategoryDto
	 * @return
	 */
//	public Integer countPriceCategoryByCondition(PriceCategoryDTO priceCategoryDto);
	
	/**
	 * 获取所关联的列别的编号
	 * @param id
	 * @return
	 */
//	public List<Integer> queryAssistPriceCategoryIdByTypeId(Integer id);
	
	/**
	 * 根据父节点统计指定类别记录
	 * @param id 不能为null或""<br/>
	 * @return 总数量
	 */
//	public Integer countPriceCategoryByParentId(Integer id);
	public List<PriceCategoryMinDto> queryPriceCategoryByParentIdOrderList(Integer parentId);
	public Integer queryParentIdById(Integer id);
	public Integer countChild(Integer parentId);
	
	public Integer queryIdByName(String name);
	public String queryTagNameByTypeId(Integer id);
	
	public Integer updateSearchLabel(String key,Integer id);
	
	public String queryForPinyin(Integer id);
}
