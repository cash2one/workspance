/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-13
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-13
 */
public interface EsiteNewsDao {

	public List<EsiteNewsDo> queryNewsByCompany(Integer companyId, PageDto page);
	
	public Integer queryNewsByCompanyCount(Integer companyId);
	
	public Integer insertNews(EsiteNewsDo news);
	
	public Integer updateNewsById(EsiteNewsDo news);
	
	public Integer deleteNewsByCompany(Integer id, Integer companyId);
	
	public EsiteNewsDo queryOneNewsById(Integer id);
	
	/*****************
	 * 在动态的详细页面  查询上一条 动态
	 * @param id
	 * @return
	 */
	public EsiteNewsDo queryLastNewsById(Integer id,Integer cid);
	
	/*****************
	 * 在动态的详细页面  查询下一条 动态
	 * @param id
	 * @return
	 */
	public EsiteNewsDo queryNextNewsById(Integer id,Integer cid);

	/**
	 * 最新企业动态
	 * @param size
	 * @return
	 */
	public List<EsiteNewsDo> queryList(Integer size);

	public List<EsiteNewsDo> querybyCompanyAll(Integer companyId);
	
}
