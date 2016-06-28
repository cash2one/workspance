package com.zz91.ep.service.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompTags;
import com.zz91.ep.dto.comp.CompTagsDto;

public interface CompTagsService {
	
	/**
	 * 查询父类
	 */
	static final Integer SQL_FATHER = 0;
	/**
	 * 查询孙子类
	 */
	static final Integer SQL_GRANDSON = 1;
	/**
	 * 查询孙子类的同类 
	 */
	static final Integer SQL_SAME = 2; 
	/**
	 * 查找公司库首页显示的公司标签
	 * @param code
	 * @return
	 */
	public List<CompTagsDto> queryCompTags();
	
	/**
	 * 根据id查询公司标签
	 * @param code
	 * @return
	 */
	public List<CompTags>  queryCompTagsById(Integer id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String queryCompkewordsById(Integer id);
}
