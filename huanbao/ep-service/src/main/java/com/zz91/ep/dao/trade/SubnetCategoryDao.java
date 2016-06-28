package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetCategory;

public interface SubnetCategoryDao {
	
	public List<SubnetCategory> queryCategoryByParentId(Integer parentId,Integer size);

	public SubnetCategory queryCategoryByCode(String code);

	public SubnetCategory querySubCateById(Integer id);
}
