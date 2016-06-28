package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.dto.trade.SubnetCategoryDto;

public interface SubnetCategoryService {
	
	/**
	 * 
	 * 函数名称：queryCategoryByParentId
	 * 功能描述：[根据父类查询所有子类]
	 * 输入参数：@param parentId 父类Id
	 * 		   @param size 显示数量
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId,Integer size);

	/**
	 * 
	 * 函数名称：queryAllCategory
	 * 功能描述：[查询类类别]
	 * 输入参数：@param id
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<SubnetCategoryDto> queryAllCategory(Integer id);
	
	/**
	 * 
	 * 函数名称：queryCategoryByCode
	 * 功能描述：[根据Code查询类别]
	 * 输入参数：@param code 类别编号
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public SubnetCategory queryCategoryByCode(String code);

	/**
	 * 
	 * 函数名称：querySubCateByParentId
	 * 功能描述：[根据id查询类别 用于SEO]
	 * 输入参数：@param id 编号
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public SubnetCategory querySubCateById(Integer id);
	
}
