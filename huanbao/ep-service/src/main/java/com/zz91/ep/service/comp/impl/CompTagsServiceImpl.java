package com.zz91.ep.service.comp.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.comp.CompTagsDao;
import com.zz91.ep.domain.comp.CompTags;
import com.zz91.ep.dto.comp.CompTagsDto;
import com.zz91.ep.service.comp.CompTagsService;

@Component("compTagsService")
public class CompTagsServiceImpl implements CompTagsService {
	
	@Resource
	private CompTagsDao compTagsDao;
	
	@Override
	public List<CompTagsDto> queryCompTags() {
		List<CompTagsDto> allComTag =null;
		//查询出公司标签的最高节点
		List<CompTags> comptags = compTagsDao.queryCompTags(0, SQL_FATHER);
		if(comptags!=null){
		allComTag = new ArrayList<CompTagsDto>();
		CompTagsDto compTagsDto = null;
		for(int i=0;i<comptags.size();i++){
			compTagsDto = new CompTagsDto();
			compTagsDto.setCompTags(comptags.get(i));
			// 查询出父类对应的孙子类
			List<CompTags> grandSon = compTagsDao.queryCompTags(comptags.get(i).getId(), SQL_GRANDSON);
			compTagsDto.setGrandSon(grandSon);
			allComTag.add(compTagsDto);
		}
		}
		return allComTag;
	}

	
	@Override
	public List<CompTags> queryCompTagsById(Integer id) {
		
		List<CompTags> comptags =null;
		if(id==null||id<11){
		comptags = compTagsDao.queryCompTags(0, SQL_FATHER);
		}else{
		comptags = compTagsDao.queryCompTags(id, SQL_SAME);
		}
		return comptags;
	}


	@Override
	public String queryCompkewordsById(Integer id) {
		
		return compTagsDao.queryCompKewordsById(id);
	}
    
}
