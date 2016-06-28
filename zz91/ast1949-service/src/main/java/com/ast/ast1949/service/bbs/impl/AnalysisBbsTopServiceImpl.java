package com.ast.ast1949.service.bbs.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.AnalysisBbsTop;
import com.ast.ast1949.persist.bbs.AnalysisBbsTopDAO;
import com.ast.ast1949.service.bbs.AnalysisBbsTopService;
import com.zz91.util.datetime.DateUtil;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-8-17 
 */
@Component("analysisBbsTopService")
public class AnalysisBbsTopServiceImpl implements AnalysisBbsTopService {

	@Resource
	private AnalysisBbsTopDAO analysisBbsTopDAO;
	
	@Override
	public List<AnalysisBbsTop> queryBbsTopsByType(String category,Integer max) {
		if(max==null){
			max=10;
		}
		long gmtTarget=DateUtil.getTheDayZero(new Date(), 0);
		return analysisBbsTopDAO.queryBbsTopByType(category,gmtTarget, max);
	}

}
