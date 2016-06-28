package com.ast.ast1949.service.exhibit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.exhibit.PreviouExhibitors;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.exhibit.PreviouExhibitorsDao;
import com.ast.ast1949.service.exhibit.PreviouExhibitorsService;
@Component("previouExhibitorsServiceImpl")
public class PreviouExhibitorsServiceImpl implements PreviouExhibitorsService{
	@Resource
	private PreviouExhibitorsDao previouExhibitorsDao;
	@Override
	public Integer insert(PreviouExhibitors previouExhibitors) {
		return previouExhibitorsDao.insert(previouExhibitors);
	}
	@Override
	public PageDto<PreviouExhibitors> queryList(PageDto<PreviouExhibitors> page, PreviouExhibitors previouExhibitors) {
		List<PreviouExhibitors> list=previouExhibitorsDao.queryList(page, previouExhibitors);
		page.setRecords(list);
		page.setTotalRecords(previouExhibitorsDao.queryListCount(previouExhibitors));
		
		return page;
	}
	@Override
	public PreviouExhibitors queryPreviouExhibitorsById(Integer id) {
		return previouExhibitorsDao.queryPreviouExhibitorsById(id);
	}
	@Override
	public Integer updatePreviouExhibitors(PreviouExhibitors previouExhibitors) {
		
		return previouExhibitorsDao.updatePreviouExhibitors(previouExhibitors);
	}
	@Override
	public Integer delPreviouExhibitors(Integer id){
		return previouExhibitorsDao.delPreviouExhibitors(id);
	}
	@Override
	public List<PreviouExhibitors> queryAllList(PreviouExhibitors previouExhibitors) {
		
		return previouExhibitorsDao.queryAllList(previouExhibitors);
	}
}
