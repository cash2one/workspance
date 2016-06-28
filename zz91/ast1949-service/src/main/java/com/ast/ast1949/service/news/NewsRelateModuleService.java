/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.service.news;

import com.ast.ast1949.domain.news.NewsRelateModuleDO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface NewsRelateModuleService {
	/**
	 * 添加记录
	 * @param newsRelateModule
	 * @return
	 */
//	public Integer insertNewsRelateModule(NewsRelateModuleDO newsRelateModule);
	/**
	 * 删除记录
	 * 	@param id 编号<br/>
	 * 	@param newsId 新闻编号<br/>
	 * 	@param moduleId 模块编号<br/>
	 * 	@param tradeId 行业编号<br/>
	 * 说明：选择不为空参数作为条件，但不可全为空值。<br/>
	 * @return
	 */
	public Integer deleteNewsRelateModuleByMap(Integer id, Integer newsId, Integer moduleId, Integer tradeId);/**
	 * 查询记录
	 * 	@param id 编号<br/>
	 * 	@param newsId 新闻编号<br/>
	 * 	@param moduleId 模块编号<br/>
	 * 	@param tradeId 行业编号<br/>
	 * 说明：选择不为空参数作为条件，但不可全为空值。<br/>
	 * @return
	 */
//	public NewsRelateModuleDO queryNewsRelateModuleByMap(Integer id, Integer newsId, Integer moduleId, Integer tradeId);
	/**
	 * 更新记录
	 * @param newsRelateModule
	 * @return
	 */
//	public Integer updateNewsRelateModule(NewsRelateModuleDO newsRelateModule);
	/**
	 * 查询指定记录
	 * @param newsRelateModule
	 * @return
	 */
//	public NewsRelateModuleDO queryNewsRelateModule(NewsRelateModuleDO newsRelateModule);
	/**
	 * 插入记录，会先判断是否又记录已经存在。
	 * @param newsRelateModule
	 * @return
	 */
	public Integer insert(NewsRelateModuleDO newsRelateModule);
	
	/**
	 * 修改记录，会先判断是否又记录已经存在。
	 * @param newsRelateModule
	 * @return
	 */
//	public Integer update(NewsRelateModuleDO newsRelateModule);
}
