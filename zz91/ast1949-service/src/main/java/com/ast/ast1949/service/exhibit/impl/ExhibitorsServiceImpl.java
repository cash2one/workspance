package com.ast.ast1949.service.exhibit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.exhibit.Exhibitors;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.exhibit.ExhibitorsDao;
import com.ast.ast1949.service.exhibit.ExhibitorsService;

@Component("exhibitorsService")
public class ExhibitorsServiceImpl implements ExhibitorsService {
	@Resource
	private ExhibitorsDao exhibitorsDao;
	@Override
	public Integer insert(Exhibitors exhibitors) {
		return exhibitorsDao.insert(exhibitors);
	}
	@Override
	public PageDto<Exhibitors> queryAllExhibitors(PageDto<Exhibitors> page,
			Exhibitors exhibitors) {
		List<Exhibitors> list=exhibitorsDao.queryAllExhibitors(page, exhibitors);
		page.setRecords(list);
		page.setTotalRecords(exhibitorsDao.queryAllExhibitorsCount(exhibitors));
		return page;
	}
	
	@Override
	public List<Exhibitors> queryList(String type) {
		return exhibitorsDao.queryList(type);
		
	}

}
