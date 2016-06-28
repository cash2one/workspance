package com.ast.ast1949.service.analysis.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.analysis.AnalysisPpcLogDao;
import com.ast.ast1949.service.analysis.AnalysisPpcLogService;

@Component("analysisPPcLogService")
public class AnalysisPpcLogServiceImpl implements AnalysisPpcLogService{

	@Resource
	private AnalysisPpcLogDao analysisPpcLogDao;
	
	@Override
	public Integer queryAllPvByCid(Integer cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDto<AnalysisPpcLog> queryList(AnalysisPpcLog analysisPpcLog,
			PageDto<AnalysisPpcLog> page) {
		List<AnalysisPpcLog> list = analysisPpcLogDao.queryList(analysisPpcLog, page);
		page.setRecords(list);
		page.setTotalRecords(analysisPpcLogDao.queryListCount(analysisPpcLog));
		return page;
	}
	
}
