/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 9, 2010 by Rolyer.
 */
package com.ast.ast1949.service.news.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsDO;
import com.ast.ast1949.persist.news.NewsDAO;
import com.ast.ast1949.service.news.NewsService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("newsService")
public class NewsServiceImpl implements NewsService {
	
	@Resource
	private NewsDAO newsDAO;
//	@Resource
//	private TitleStyleService titleStyleService;

//	public Integer deleteNewsById(Integer id) {
//		return newsDAO.deleteNewsById(id);
//	}

	public Integer insertNews(NewsDO news) {
		return newsDAO.insertNews(news);
	}

//	public NewsDO queryNewsById(Integer id) {
//		return newsDAO.queryNewsById(id);
//	}

//	public Integer updateNewsById(NewsDO news) {
//		return newsDAO.updateNewsById(news);
//	}

	public Integer update(NewsDO news) {
		NewsDO n = newsDAO.queryNewsById(news.getId());
		if(n!=null&&n.getId().intValue()>0){
			return newsDAO.updateNewsById(news);
		} else {
			return newsDAO.insertNews(news);
		}
	}

//	@Override
//	public List<NewsForFrontDTO> queryNewsListForFront(
//			NewsForFrontDTO newsForFrontDTO) {
//		
//		if(newsForFrontDTO.getPage()==null) {
//			PageDto page = new PageDto(AstConst.PAGE_SIZE);
//			page.setSort("n.gmt_order");
//			page.setDir("asc");
//			newsForFrontDTO.setPage(page);
//		} else {
//			if(newsForFrontDTO.getPage().getPageSize()==null) {
//				newsForFrontDTO.getPage().setPageSize(AstConst.PAGE_SIZE);
//			}
//			if(newsForFrontDTO.getPage().getSort()==null) {
//				newsForFrontDTO.getPage().setSort("n.gmt_order");
//			}
//			if(newsForFrontDTO.getPage().getDir()==null) {
//				newsForFrontDTO.getPage().setDir("asc");
//			}
//		}
//		
//		return newsDAO.queryNewsListForFront(newsForFrontDTO);
//	}

//	@Override
//	public List<NewsForFrontDTO> queryNewsListForFront(Integer moduleId,
//			String tradeCode,Date gmtPublished,Integer startIndex, Integer num, String sort, String dir) {
//		// Assert.notNull(moduleId, "the moduleId must not be null");
//		// Assert.notNull(tradeCode, "the tradeCode must not be null");
//		Assert.notNull(num, "the num must not be null");
//		
//		if (StringUtils.isEmpty(sort)) {
//			sort = "n.gmt_order";
//		}
//		if (StringUtils.isEmpty(dir)) {
//			dir = "asc";
//		}
//		if(startIndex==null) {
//			startIndex=0;
//		}
//
//		NewsForFrontDTO dto = new NewsForFrontDTO();
//		dto.setModuleId(moduleId);
//		dto.setTradeCode(tradeCode);
//		dto.setGmtPublished(gmtPublished);
//		
//		PageDto page = new PageDto(num);
//		page.setStartIndex(startIndex);
//		page.setDir(dir);
//		page.setSort(sort);
//		dto.setPage(page);
//
//		return queryNewsListForFront(dto);
//	}
	
//	public List<NewsForFrontDTO> queryNewsListForFront(Integer moduleId,
//			String tradeCode, Date gmtPublished,Integer startIndex,Integer num, String sort, String dir,
//			boolean showStyle) {
//		Assert.notNull(num, "the num must not be null");
//		
//		List<NewsForFrontDTO> list = queryNewsListForFront(moduleId, tradeCode,gmtPublished, startIndex, num, sort, dir);
//		
//		//获取样式信息
//		if(showStyle){
//			for (NewsForFrontDTO lt : list) {
////				list = new ArrayList<NewsForFrontDTO>();
//				if(lt.getNews()!=null&&lt.getNews().getTitleStyleId()!=null&&lt.getNews().getTitleStyleId().intValue()>0){
//					TitleStyleDO style = titleStyleDAO.queryTitleStyle(new TitleStyleDO(lt.getNews().getTitleStyleId()));
//					lt.setStyle(style);
//				}
//			}
//		}
//
//		return list;
//	}

	public List<NewsDO> queryNewsByTitle(String title, Integer number) {
		Assert.notNull(title, "the title must not be null");
		Assert.notNull(number, "the number must not be null");
		return newsDAO.queryNewsByTitle(title, number);
	}

	@Override
	public List<NewsDO> queryNewsByModuleId(Integer moduleId, Integer size) {
		return newsDAO.queryNewsByModuleId(moduleId, size);
	}

}
