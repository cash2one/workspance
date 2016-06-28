/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.service.company;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.dto.ExtTreeDto;

/**
 * @author yuyonghui
 *
 */
public interface CategoryCompanyPriceService {

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
	public int insertCategoryCompanyPrice(CategoryCompanyPriceDO categories,String preCode)throws IOException;

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
//	public String selectMaxCodeByPreCode(String preCode) throws IOException;
	/**
	 * 获取某个类别下的子类别,并且返回Ext tree需要的数据格式
	 * @param code 父结点编号
	 * @return ext 树结点
	 *		ExtTreeDto.id对应CategoryCompanyPriceDO.id
	 *		ExtTreeDto.text对应CategoryCompanyPriceDO.label
	 *		ExtTreeDto.data对应CategoryCOmpanyPriceDO.data
	 *		ExtTreeDto.leaf = false 表示仍有子节点,true表示无子节点
	 */
	public List<ExtTreeDto> child(String code);
	
	/**
	 * 更新类别信息 默认按照记录ID更新 当CategoryCompanyPriceDO.getId()大于0时更新,否则不更新
	 *
	 * @param CategoryCompanyPriceDO
	 *            待更新类别信息,需要包含ID信息
	 * @param preCode 父类别code
	 * @throws IOException
	 * @return
	 *  1.当preCode为空时,返回根类别的最大Code,4位
	 *  2.当preCode不为空,返回当前父类别下子类别的最大code,如父类别code为1000,当前子类别下有10001002,则返回10001003
	 */

	public String getMaxCode(String preCode) throws IOException;
	
	/**
	 * 
	 *   根据Id 查询企业报价类别信息
	 * @param id 不能为空
	 * @return  CategoryCompanyPriceDO
	 */
	public CategoryCompanyPriceDO selectCategoryCompanyPriceById(Integer id);
	
	public CategoryCompanyPriceDO queryCategoryCompanyPriceByLabel(String label);
	
	public Map<String,String> queryAllCompanyPrice(); 
}
