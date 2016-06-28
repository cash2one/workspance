/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 8, 2010 by Rolyer.
 */
package com.ast.ast1949.persist.news;

import java.util.Map;

import com.ast.ast1949.domain.news.NewsRelateModuleDO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface NewsRelateModuleDAO {
	/**
	 * 添加记录
	 * @param newsRelateModule
	 * @return
	 */
	public Integer insertNewsRelateModule(NewsRelateModuleDO newsRelateModule);
	/**
	 * 删除记录
	 * @param param 参数：选择不为空参数作为条件，但不可全为空值。<br/>
	 * 	id 编号<br/>
	 * 	newsId 新闻编号<br/>
	 * 	moduleId 模块编号<br/>
	 * 	tradeId 行业编号<br/>
	 * @return
	 */
	public Integer deleteNewsRelateModuleByMap(Map<String, Object> param);/**
	 * 查询记录
	 * @param param 参数：选择不为空参数作为条件，但不可全为空值。<br/>
	 * 	id 编号<br/>
	 * 	newsId 新闻编号<br/>
	 * 	moduleId 模块编号<br/>
	 * 	tradeId 行业编号<br/>
	 * @return
	 */
//	public NewsRelateModuleDO queryNewsRelateModuleByMap(Map<String, Object> param);
	/**
	 * 更新记录
	 * @param newsRelateModule
	 * @return
	 */
	public Integer updateNewsRelateModule(NewsRelateModuleDO newsRelateModule);
	/**
	 * 查询指定记录
	 * @param newsRelateModule
	 * @return
	 */
	public NewsRelateModuleDO queryNewsRelateModule(NewsRelateModuleDO newsRelateModule);
}
