/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface CompNewsDao {

    /**
     * 根据公司ID查询公司的发布的信息
     * @param cid
     * @param type 类型(1000:公司动态,1001:技术文章,1002:成功案例)
     * @param pause(是否发布0:未发布,1:发布)
     * @param check(是否审核0:未审核,1:审核通过,2:审核不通过)
     * @param size(0为所有)
     * @return
     */
//    public List<CompNews> queryCompNewsByCid(Integer cid, String type, Short pause, Short check, Integer size);

//	public List<CompNews> queryCompNewsByCid(Integer cid, String type, String keywords, Short pause, Short check,Short delete, PageDto<CompNews> page);

//	public Integer queryCompNewsByCidCount(Integer cid, String type, String keywords, Short pause, Short check, Short delete);

//	public CompNews queryCompNewsById(Integer id);

//	public Integer insertArticle(CompNews compNews);

	public Integer updateDeleteStatusByCid(Integer id, Integer cid,Short status);
//	public Integer updatePauseStatusByCid(Integer id, Integer cid, Short status);

//	public Integer updateArticle(CompNews compNews);

	public Integer updateCheckStatus(Integer id, Short status, String account);

	public CompNews queryDetailsById(Integer id);

	public Integer updateContent(Integer id, String content, String title,String categoryCode,String tags);

	/**
	 * 后台文章列表
	 * @param cid 
	 */
	public List<CompNewsDto> queryCompNewsByAdmin(Integer cid, String type,
			String title, Short pause, Short check, Short delete,
			PageDto<CompNewsDto> page);

	/**
	 * 文章列表数量
	 * @param cid 
	 */
	public Integer queryCompNewsCountByAdmin(Integer cid, String type,
			String title, Short pause, Short check, Short delete);
}