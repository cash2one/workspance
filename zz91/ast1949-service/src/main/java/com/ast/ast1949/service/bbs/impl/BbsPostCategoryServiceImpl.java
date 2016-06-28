package com.ast.ast1949.service.bbs.impl;

/**
 * @author shiqp
 * @version 创建时间：2014-11-8
 */
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.persist.bbs.BbsPostCategoryDao;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;

@Component("bbsPostCategoryService")
public class BbsPostCategoryServiceImpl implements BbsPostCategoryService {
	@Resource
	private BbsPostCategoryDao bbsPostCategoryDao;
    /**
     * 获取父结点的所有子结点
     */
	@Override
	public List<ExtTreeDto> queryCategoryByParentId(Integer parentId) {
		List<BbsPostCategory> list = bbsPostCategoryDao.queryCategoryByParentId(parentId);
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		Integer code=null;
		for(BbsPostCategory bbp:list){
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(bbp.getId()));
			code=bbsPostCategoryDao.queryMaxCategoryIdByParentId(bbp.getId());
			if(code==null){
				node.setLeaf(true);
			}else{
				node.setLeaf(false);
			}
			node.setText(bbp.getName());
			node.setData(String.valueOf(bbp.getId()));
			nodeList.add(node);
		}
		return nodeList;
	}
    /**
     * 根据结点id获取结点的其他信息
     */
	@Override
	public BbsPostCategory querySimpleCategoryById(Integer id) {
		return bbsPostCategoryDao.querySimpleCategoryById(id);
	}
	/**
	 * 添加新的互助类别
	 */
	@Override
	public Integer insertCategory(BbsPostCategory bbsPostCategory) {
		return bbsPostCategoryDao.insertCategory(bbsPostCategory);
	}
	/**
	 * 更新互助类别的信息
	 */
	@Override
	public Integer updateCategoryById(BbsPostCategory bbsPostCategory) {
		return bbsPostCategoryDao.updateCategoryById(bbsPostCategory);
	}
	@Override
	public List<BbsPostCategory> queryAllCategory() {
		return bbsPostCategoryDao.queryAllCategory();
	}
	@Override
	public List<BbsPostCategory> queryCategorysByParentId(Integer parentId) {
		return bbsPostCategoryDao.queryCategoryByParentId(parentId);
	}
}
