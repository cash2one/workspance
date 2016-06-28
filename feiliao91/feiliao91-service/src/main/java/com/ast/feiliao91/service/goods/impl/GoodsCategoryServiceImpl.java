/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.service.goods.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.GoodsCategory;
import com.ast.feiliao91.dto.ExtTreeDto;
import com.ast.feiliao91.persist.goods.GoodsCategoryDao;
import com.ast.feiliao91.service.goods.GoodsCategoryService;

@Component("goodsCategoryService")
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
	@Resource
	private GoodsCategoryDao goodsCategoryDao;

	@Override
	public List<GoodsCategory> queryAllGoodsCategory() {
		return goodsCategoryDao.queryAllGoodsCategory();
	}

	@Override
	public List<GoodsCategory> queryGoodsCategoryByKeyword(String keyword, Integer size, Integer length) {
		return goodsCategoryDao.queryGoodsCategoryByKeyword(keyword, size, length);
	}
	
	@Override
	public List<ExtTreeDto> childByAdmin(String parentCode){
		List<GoodsCategory> list = goodsCategoryDao.queryCategoryByParentCode(parentCode);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (GoodsCategory l : list) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(l.getId()));
			node.setLeaf(false);
			node.setText(l.getLabel());
			node.setData(l.getCode());
			treeList.add(node);
		}
		return treeList;
	}
}
