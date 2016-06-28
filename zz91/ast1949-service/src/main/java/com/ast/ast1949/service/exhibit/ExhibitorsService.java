package com.ast.ast1949.service.exhibit;

import java.util.List;

import com.ast.ast1949.domain.exhibit.Exhibitors;
import com.ast.ast1949.dto.PageDto;

public interface ExhibitorsService {
	public Integer insert(Exhibitors exhibitors);
	
	public PageDto<Exhibitors> queryAllExhibitors(PageDto<Exhibitors>page,Exhibitors exhibitors);
	
	public List<Exhibitors> queryList(String type);

}
