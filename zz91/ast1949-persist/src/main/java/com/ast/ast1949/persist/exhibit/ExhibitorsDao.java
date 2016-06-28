package com.ast.ast1949.persist.exhibit;

import java.util.List;

import com.ast.ast1949.domain.exhibit.Exhibitors;
import com.ast.ast1949.dto.PageDto;

public interface ExhibitorsDao {
	
	public Integer insert(Exhibitors exhibitors);
	
	public List<Exhibitors> queryAllExhibitors(PageDto<Exhibitors> page,Exhibitors exhibitors);
	
	public Integer queryAllExhibitorsCount(Exhibitors exhibitors);
	
	public List<Exhibitors> queryList(String type);

}
