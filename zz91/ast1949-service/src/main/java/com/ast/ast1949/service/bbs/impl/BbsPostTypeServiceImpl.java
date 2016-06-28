package com.ast.ast1949.service.bbs.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostType;
import com.ast.ast1949.persist.bbs.BbsPostTypeDao;
import com.ast.ast1949.service.bbs.BbsPostTypeService;

@Component("bbsPostTypeService")
public class BbsPostTypeServiceImpl implements BbsPostTypeService {

	@Resource
	private BbsPostTypeDao bbsPostTypeDao;

	@Override
	public String queryNameById(String id) {
		Integer i = Integer.valueOf(id);
		if(i==null){
			return "";
		}
		BbsPostType bp = bbsPostTypeDao.queryPostTypeById(i);
		if (bp != null) {
			return bp.getName();
		}
		return "";
	}

}
