/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-9 by Ryan.
 */
package com.ast.ast1949.service.site;

import java.io.IOException;
import java.util.List;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.site.CategoryDTO;

/**
 * 类别服务类
 *
 * @author Ryan
 *
 */
public interface CategoryService {
	
	final static String DOWNLOAD_CENTER = "20051000";

	/**
	 * 根据前缀Code选择所有对应的直接子类别
	 * @param preCode
	 * 			父类别Code
	 * @return  有记录：返回所有符合条件的记录集；<br/>
	 * 			无记录：返回null。

	 */

	public List<CategoryDO> queryCategoriesByPreCode(String preCode);

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
	public int insertCategory(CategoryDO categoryDO,String preCode) throws IOException ;

	/**
	 * 修改类别
	 *
	 * @param id
	 * @return
	 */
	public int updateCategory(CategoryDO categoryDO);

	/**
	 * 删除类别
	 *
	 * @param code
	 * @return
	 */
	public int deleteCategoryByCode(String code);

	/**
	 * 根据前缀Code取与其匹配的下级类别的最大code
	 * 	@return
	 *  1.当preCode为空时,返回根类别的最大Code,4位
	 *  2.当preCode不为空,返回当前父类别下子类别的最大code,如父类别code为1000,当前子类别下有10001002,则返回10001003
	 */
//	public String queryMaxCodeByPreCode(String str) throws IOException;

	/**
	 * 根据id选择对象
	 * @param id
	 * @return
	 */
	public CategoryDO queryCategoryById(int id);


	/**
	 * 根据分页获取category
	 *
	 */
	public List<CategoryDO> queryCategoriesByCondition(CategoryDTO categoryDTO);

	/**
	 * 统计记录数
	 *
	 * @param map
	 * @return
	 */
//	public int queryRecordCountByCondition(CategoryDTO categoryDTO);

	/**
	 * 获取某个类别下的子类别,并且返回Ext tree需要的数据格式
	 * @param code 父结点编号
	 * @return ext 树结点
	 *		ExtTreeDto.id对应CategoryDo.id
	 *		ExtTreeDto.text对应CategoryDo.label
	 *		ExtTreeDto.data对应CategoryDo.data
	 *		ExtTreeDto.leaf = false 表示仍有子节点,true表示无子节点
	 */
	public List<ExtTreeDto> child(String code);
	/**
	 * 获取某个类别下的子类别,并且返回Ext tree需要的数据格式 (专门zz91后台交易中心里的公司库做的地区搜索)
	 * @param code 父结点编号
	 * @return ext 树结点
	 *		ExtTreeDto.id对应CategoryDo.id
	 *		ExtTreeDto.text对应CategoryDo.label
	 *		ExtTreeDto.data对应CategoryDo.data
	 *		ExtTreeDto.leaf = false 表示仍有子节点,true表示无子节点
	 */
	public List<ExtTreeDto> childSearch(String code);

	/**
	 * 更新类别信息 默认按照记录ID更新 当categoryDo.getId()大于0时更新,否则不更新
	 *
	 * @param categoryDO
	 *            待更新类别信息,需要包含ID信息
	 * @param preCode 父类别code
	 * @throws IOException
	 * @return
	 *  1.当preCode为空时,返回根类别的最大Code,4位
	 *  2.当preCode不为空,返回当前父类别下子类别的最大code,如父类别code为1000,当前子类别下有10001002,则返回10001003
	 */

	public String getMaxCode(String preCode) throws IOException;

	/**
	 *    根据code 查询 label值
	 * @param code
	 * @return   成功返回结果
	 *           失败返回 null
	 *
	 */
	public CategoryDO queryCategoryByCode(String code);
	
//	public List<CategoryDO> queryCategoriesByParentCode(String parentCode);

	public List<CategoryDO> queryCategoryList();
	
	public String queryCodeByLabel(String label);
	
}
