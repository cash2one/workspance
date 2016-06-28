/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.comp;

import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;


/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface CompNewsService {

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

    /**
     * 根据公司ID查询公司的发布的信息
     * @param cid
     * @param type 类型(1000:公司动态,1001:技术文章,1002:成功案例)
     * @param pause(是否发布0:未发布,1:发布)
     * @param check(是否审核0:未审核,1:审核通过,2:审核不通过)
     * @param delete(是否审核0:删除,1:未删除)
     * @param page
     * @return
     */
//	public PageDto<CompNews> pageCompNewsByCid(Integer cid, String type, String keywords,
//			Short pause, Short check, Short delete,PageDto<CompNews> page);

//	public CompNews queryCompNewsById(Integer id);

//	public Integer createArticle(CompNews compNews);

	public Integer deleteArticle(Integer id, Integer companyId);

//	public Integer publishArticle(Integer id, Integer companyId, Short status);

//	public Integer updateArticle(CompNews compNews);

	public Integer updateCheckStatus(Integer id,Short status, String account);

	public CompNews queryDetailsById(Integer id);

	public Integer updateContent(Integer id, String content, String title,String categoryCode,String tags);

	public PageDto<CompNewsDto> pageCompNewsByAdmin(Integer cid,String type,
			String title, Short pause, Short check, Short delete,
			PageDto<CompNewsDto> page);
	
	public Integer queryNewCount(Integer cid,Short pause,Short check,Short delete);
}