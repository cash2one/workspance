package com.ast.ast1949.service.exhibit;

import java.util.List;

import com.ast.ast1949.domain.exhibit.PreviouExhibitors;
import com.ast.ast1949.dto.PageDto;

public interface PreviouExhibitorsService {
	public Integer insert (PreviouExhibitors previouExhibitors);
	
	public PageDto<PreviouExhibitors> queryList(PageDto<PreviouExhibitors> page,PreviouExhibitors previouExhibitors);
	
	public PreviouExhibitors queryPreviouExhibitorsById(Integer id);
	
	public Integer updatePreviouExhibitors(PreviouExhibitors previouExhibitors);
	
	public Integer delPreviouExhibitors(Integer id);
	
	public List<PreviouExhibitors> queryAllList(PreviouExhibitors previouExhibitors);
	
	

}
