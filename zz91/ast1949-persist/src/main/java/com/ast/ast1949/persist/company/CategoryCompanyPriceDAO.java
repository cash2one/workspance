/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.persist.company;

import java.io.IOException;
import java.util.List;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;


/**
 * @author yuyonghui
 *
 */
public interface CategoryCompanyPriceDAO {
	
	/**
	 * 根据前缀Code选择所有对应的类别
	 * @param preCode
	 *          父类别Code
	 * @return

	 */
	public List<CategoryCompanyPriceDO> selectCategoriesByPreCode(String preCode);

	/**
	 * 添加类别
	 * @param categoryDO
	 * @param preCode
	 * 		根据这个值计算出要添加的类别的code
	 * @return
	 *      成功:插入的那条记录的ID
	 *      失败:抛出异常
	 * @throws IOException
	 */
	public int insertCategoryCompanyPrice(CategoryCompanyPriceDO categories);

	/**
	 * 修改类别
	 *
	 * @param id
	 * @return
	 */
	public int updateCategoryCompanyPrice(CategoryCompanyPriceDO categoryDO);

	/**
	 * 删除类别
	 *
	 * @param code
	 * 		删除和code匹配的所有类别
	 * @return
	 * 		成功删除的条数
	 */
	public int deleteCategoryCompanyPriceByCode(String code);

	/**
	 * 获取类别下一级的最大可用code
	 *
	 * @param preCode
	 * @return
	 * @throws IOException
	 */
	public String selectMaxCodeByPreCode(String preCode) throws IOException;
	/**
	 * 
	 *   根据Id 查询企业报价类别信息
	 * @param id 不能为空
	 * @return  CategoryCompanyPriceDO
	 */
	public CategoryCompanyPriceDO selectCategoryCompanyPriceById(Integer id);

	/**
	 *   查询所有大类别
	 * @return
	 *    返回 CategoryCompanyPriceDO list集合
	 */
	public List<CategoryCompanyPriceDO> queryCategoryCompanyPrice();

	/**
	 *    根据code 查询子类下的所有企业报价
	 * @param code
	 * @return   返回 CategoryCompanyPriceDO list集合
	 *
	 */
	public List<CategoryCompanyPriceDO> queryCategoryCompanyPriceByCode(String code);

	/**
	 *   根据code查询企业报价 CategoryCompanyPriceDO
	 * @param code
	 * @return  CategoryCompanyPriceDO
	 */
	public CategoryCompanyPriceDO queryByCode(String code);
	
	public CategoryCompanyPriceDO queryCategoryCompanyPriceByLabel(String label);

	public List<CategoryCompanyPriceDO> queryAllCompanyPrice();
}
