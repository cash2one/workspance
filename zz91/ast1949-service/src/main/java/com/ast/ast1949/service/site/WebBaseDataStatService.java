/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7
 */
package com.ast.ast1949.service.site;

import java.util.Date;
import java.util.Map;

import com.ast.ast1949.domain.site.WebBaseDataStatDo;
import com.ast.ast1949.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-7
 */
public interface WebBaseDataStatService {
	
	/**
	 * 查找某天的网站统计数据
	 */
	public Map<String, Integer> queryDataByDate(Date d);
	
	/**
	 * 查找当天某个类别的统计信息
	 */
	public WebBaseDataStatDo queryTodayDataByCate(String cate);

	public PageDto<WebBaseDataStatDo> pageWebBaseDataStat(PageDto<WebBaseDataStatDo> page,String statCate,Date gmtStatDate);
	
	public void indexTotal(Map<String,Object>out);
}
