package com.ast.feiliao91.service.commom;

import java.util.List;

import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.dto.ExtTreeDto;

public interface CategoryService {
	/**
	 * 插入信息
	 * 
	 * @param category表
	 * @return
	 */
	public Integer insert(Category category);

	/**
	 * 根据ID查询
	 */
	public Category selectById(Integer id);
	
	public Integer updateCategory(Category category);
	
	/**
	 * 检索所有类别（未删除）
	 * @return
	 */
	public List<Category> queryAllCategory();
	
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
	 */
	public Integer delete(String code);

	public Category selectByCode(String code);

}
