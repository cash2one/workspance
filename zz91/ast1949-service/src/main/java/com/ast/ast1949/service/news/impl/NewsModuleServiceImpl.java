/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.service.news.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsModuleDO;
import com.ast.ast1949.dto.ExtCheckBoxTreeDto;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.persist.news.NewsModuleDAO;
import com.ast.ast1949.service.news.NewsModuleService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.StringUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("newsModuleService")
public class NewsModuleServiceImpl implements NewsModuleService {
	@Autowired
	private NewsModuleDAO newsModuleDAO;

//	public Integer countNewsModuleListByParentId(NewsModuleDTO newsModuleDTO) {
//		return newsModuleDAO.countNewsModuleListByParentId(newsModuleDTO);
//	}

	public Integer deleteNewsModuleById(Integer id) {
		return newsModuleDAO.deleteNewsModuleById(id);
	}

	public Integer insertNewsModule(NewsModuleDO newsModule) {
		if(newsModule.getParentId()==null){
			newsModule.setParentId(0);
		}
		return newsModuleDAO.insertNewsModule(newsModule);
	}

	public NewsModuleDO queryNewsModuleById(Integer id) {
		return newsModuleDAO.queryNewsModuleById(id);
	}

	public List<NewsModuleDO> queryNewsModuleByParentId(Integer id) {
		return newsModuleDAO.queryNewsModuleByParentId(id);
	}

//	public List<NewsModuleDTO> queryNewsModuleListByParentId(
//			NewsModuleDTO newsModuleDTO) {
//		return newsModuleDAO.queryNewsModuleListByParentId(newsModuleDTO);
//	}

	public Integer updateNewsModuleById(NewsModuleDO newsModule) {
		if(newsModule.getParentId()==null){
			newsModule.setParentId(0);
		}
		return newsModuleDAO.updateNewsModuleById(newsModule);
	}

	public List<ExtTreeDto> queryExtTreeChildNodeByParentId(Integer id) {
		Assert.notNull(id, "The parent id must not be null.");
		List<NewsModuleDO> newsModuleDO = queryNewsModuleByParentId(id);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for(NewsModuleDO n:newsModuleDO){
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-"+String.valueOf(n.getId()));
			node.setLeaf(false);
			node.setText(n.getName());
			node.setData(n.getId().toString());
			treeList.add(node);
		}
		return treeList;
	}

	public List<ExtCheckBoxTreeDto> queryExtCheckBoxTreeChildNodeByParentId(
			Integer parentId, String ids) {
		Assert.notNull(parentId, "The parent id must not be null.");
		
		String[] entities=ids.split(",");
		
		List<NewsModuleDO> newsModuleDO = queryNewsModuleByParentId(parentId);
		List<ExtCheckBoxTreeDto> treeList = new ArrayList<ExtCheckBoxTreeDto>();
		for(NewsModuleDO n:newsModuleDO){
			ExtCheckBoxTreeDto node = new ExtCheckBoxTreeDto();
			node.setId(String.valueOf(n.getId()));
			node.setText(n.getName());
			
			Integer count=newsModuleDAO.countNewsModuleByParentId(n.getId());
			node.setCls(count.intValue()==0?"folder":"file");
			node.setLeaf(count.intValue()==0?true:false);
			
			//设置选中
			if(entities.length>0){
				for(int ii=0;ii<entities.length;ii++){
					if(StringUtils.isNumber(entities[ii])){
						if(Integer.valueOf(entities[ii]).intValue()==n.getId().intValue()){
							node.setChecked(true);
						}
					}
				}
			} else {
				node.setChecked(false);
			}
			
			//设置子节点
			List<ExtCheckBoxTreeDto> children = queryExtCheckBoxTreeChildNodeByParentId(n.getId(),ids);
			node.setChildren(children);
			treeList.add(node);
		}
		
		return treeList;
	}

//	public List<NewsModuleDO> queryNewsModuleListByNewsId(Integer id) {
//		return newsModuleDAO.queryNewsModuleListByNewsId(id);
//	}

//	@Override
//	public Integer countNewsModuleByParentId(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		return newsModuleDAO.countNewsModuleByParentId(id);
//	}

}
