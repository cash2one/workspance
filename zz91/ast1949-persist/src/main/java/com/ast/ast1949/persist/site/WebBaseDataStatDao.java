/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7
 */
package com.ast.ast1949.persist.site;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.site.WebBaseDataStatDo;
import com.ast.ast1949.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-7
 */
public interface WebBaseDataStatDao {

	public WebBaseDataStatDo queryDataByCate(String cate, Date d) ;
	
	public List<WebBaseDataStatDo> queryDataByDate(Date d);

	public List<WebBaseDataStatDo> queryWebBaseDataStat(String statCate,Date statDate,PageDto<WebBaseDataStatDo> page);
	
	public Integer queryWebBaseDataStatCount(String statCate,Date statDate);
	
	public Integer queryWeekPublish(Map<String,Object>map);
}
