package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.dto.company.CategoryGardenDTO;

public interface CategoryGardenService {
	/**
	 *
	 * @param id
	 * @return
	 */
	public CategoryGardenDO queryCategoryGardenById(int id);

	/**
	 *                园区列表信息
	 * @param userDTO 参数包括<br/>
	 * 			1、查询条件：searchName 所需搜索用户的名称，
	 *            industryName 按产业类别查询，
	 *             areaName 按省市查询
	 *            gardenTypeName 按园区名称查询 <br/>
	 * 			2、分页参数：startIndex 开始位置；pageSize 每页显示数...
	 * @return  有记录：返回所有符合条件的记录集
	 *          无记录：返回null
	 */
	public List<CategoryGardenDTO> queryCategoryGardenByCondition(CategoryGardenDTO pageParam);

	/**
	 *              <h3>获取园区类别数量</h3>
	 *              按照搜索条件查询记录数量
	 * @param pageParam 参数:1、查询条件 2、分页参数.
	 * @return 当pageParam.searchName不为空，返回园区类别的记录总数
	 * 			当upageParam.searchName为空，返回表中所有记录总数
	 * 			当表中没有任何记录，返回"0"
	 */
	public int getCategoryGardenRecordCountByCondition(CategoryGardenDTO pageParam);

	/**
	 *        添加园区类别
	 * @param categoryGardenDO
	 * @return
	 *          成功：返回影响行数；<br/>
	 * 			失败：返回0.
	 */
	public int insertCategoryGrden(CategoryGardenDO categoryGardenDO);

	/**
	 *         修改园区类别
	 * @param  categoryGardenDO
	 * @return
	 *          成功：返回影响行数；<br/>
	 * 			失败：返回0.
	 */
	public int updateCategoryGrden(CategoryGardenDO categoryGardenDO);

	/**
	 *           批量删除 删除园区类别
	 * @param ids
	 * @return  0表示没有删除任何数据,大于0表示删除的行数
	 *
	 * IllegalArgumentException
	 */
	public int batchDeleteCategoryGrdenById(int []entities);

//	/**
//	 * 根据areaCode查询园区类别
//	 * @param id为areaCode,为空抛出异常
//	 * @return 园区类别集合,没查询到数据返回为null
//	 */
//	public List<CategoryGardenDO> queryCategoryGardenByAreaCode(CategoryGardenDO categoryGardenDO);
	
	/**
	 * 根据categoryGardenDO查询园区类别
	 * @param categoryGardenDO包含查询条件industryCode，areaCode，gardenTypeCode，可为空
	 * @return 园区类别集合,没查询到数据返回为null
	 */
	public List<CategoryGardenDO> queryCategoryGardenBySomeCode(CategoryGardenDO categoryGardenDO);
	
	/**
	 * 根据areaCode查询园区类别（用户注册的下拉框）
	 * @param id为areaCode,为空抛出异常
	 * @return 园区类别集合,没查询到数据返回为null
	 */
//	public List<CategoryGardenDO> queryCategoryGardenByAreaCodeFroRegister(CategoryGardenDO categoryGardenDO);
	
	public List<CategoryGardenDO> queryCategoryGardenByAreaCode(String areaCode);
}
