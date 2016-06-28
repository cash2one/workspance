/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-18
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-18
 */
public interface EsiteNewsService {

	public PageDto<EsiteNewsDo> pageNewsByCompany(Integer companyId,
			PageDto<EsiteNewsDo> page);
	
	public Integer insertNews(EsiteNewsDo news);
	
	public Integer updateNewsById(EsiteNewsDo news);
	
	public Integer deleteNewsByIdAndCompany(Integer id, Integer companyId);
	
	public EsiteNewsDo queryOneNewsById(Integer id);
	
	/*****************
	 * 在动态的详细页面  查询上一条 动态
	 * @param id
	 * @return
	 */
	public EsiteNewsDo queryLastNewsById(Integer id,Integer companyId);
	
	/*****************
	 * 在动态的详细页面  查询下一条 动态
	 * @param id
	 * @return
	 */
	public EsiteNewsDo queryNextNewsById(Integer id,Integer companyId);
	
	/**
	 * 最新企业资讯
	 * @param size
	 * @return
	 */
	public List<EsiteNewsDo> queryList(Integer size);
	/**
	 * 查询公司所有信息
	 */
	public List<EsiteNewsDo> querybyCompanyAll(Integer companyId);
}
