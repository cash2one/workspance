package com.zz91.ep.admin.service.news.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.news.NewsDao;
import com.zz91.ep.admin.dao.news.NewsRecommendDao;
import com.zz91.ep.admin.service.news.NewsService;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.NewsRecommend;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.util.Assert;
import com.zz91.util.analzyer.IKAnalzyerUtils;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-9-28 
 */
@Component("newsService")
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsDao newsDao;
	@Resource
	private NewsRecommendDao newsRecommendDao;
	
//	@Override
//	public List<News> queryNewsByRecommend(String type, Integer max) {
//		Assert.notNull(type, "The type can not be null");
//		if (max == null || max.intValue()==0) {
//			max=MAX_SIZE;
//		}
//		return newsDao.queryNewsByRecommend(type, max);
//	}

//	@Override
//	public List<News> queryTopNews(Integer max) {
//		if (max == null || max.intValue()==0) {
//			max=MAX_SIZE;
//		}
//		return newsDao.queryTopNews(max);
//	}

//	@Override
//	public PageDto<NewsDto> pageNewsByCategory(String categoryCode,
//			PageDto<NewsDto> page) {
//		
//		if(page.getLimit()==null){
//			page.setLimit(20);
//		}
//		if(page.getSort()==null){
//			page.setSort("n.gmt_publish");
//		}
//		if(page.getDir()==null){
//			page.setDir("desc");
//		}
//		page.setRecords(buildNewsDto(newsDao.queryNewsByCategory(categoryCode, page)));
//		page.setTotals(newsDao.queryNewsByCategoryCount(categoryCode));
//		return page;
//	}
	
//	@Override
//	public PageDto<NewsDto> pageNewsByCategoryKey(String categoryKey,
//			PageDto<NewsDto> page) {
//		if(page.getLimit()==null){
//			page.setLimit(20);
//		}
//		if(page.getSort()==null){
//			page.setSort("gmt_publish");
//		}
//		if(page.getDir()==null){
//			page.setDir("desc");
//		}
//		page.setRecords(buildNewsDto(newsDao.queryNewsByCategoryKey(categoryKey, page)));
//		page.setTotals(newsDao.queryNewsByCategoryKeyCount(categoryKey));
//		return page;
//	}

	@Override
	public News queryNewDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return newsDao.queryNewDetailsById(id);
	}

//	@Override
//	public List<News> queryRelatedNewsByTag(String tag, Integer max) {
//		Assert.notNull(tag, "the tag can not be null");
//		if (max == null || max.intValue()==0) {
//			max=MAX_SIZE;
//		}
//		
//		if(StringUtils.isEmpty(tag)){
//			return new ArrayList<News>();
//		}
//		
//		String[] tagsArr=tag.split(",");
//		return newsDao.queryRelatedNewsByTag(tagsArr[0], max);
//	}

//	@Override
//	public List<News> queryNewestNewsByCategory(String key, Integer max) {
//		Assert.notNull(key, "the key can not be null");
//		if (max == null || max.intValue()==0) {
//			max=MAX_SIZE;
//		}
//		return newsDao.queryNewestNewsByCategory(key, max);
//	}

//	@Override
//	public News queryOnNewsById(Integer id, String categoryCode) {
//		return newsDao.queryOnNewsById(id, categoryCode);
//	}

//	@Override
//	public News queryDownNewsById(Integer id, String categoryCode) {
//		return newsDao.queryDownNewsById(id, categoryCode);
//	}

//	@Override
//	public PageDto<NewsDto> pageNewsBySearchEngine(String keywords,PageDto<NewsDto> page) {
//		Assert.notNull(keywords, "the keywords can not be null");
//		page.setSort("gmt_publish");
//		page.setDir("desc");
//		page.setRecords(buildNewsDto(newsDao.queryNewsByTitle(keywords,page)));
//		page.setTotals(newsDao.queryNewsByTitleCount(keywords));
//		return page;
//	}

	@Override
	public List<NewsDto> buildNewsDto(List<News> list) {
		if(list==null){
			return null;
		}
		List<NewsDto> listDto=new ArrayList<NewsDto>();
		for(News n:list){
			NewsDto dto=new NewsDto();
			dto.setNews(n);
			dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, n.getCategoryCode()));
			listDto.add(dto);
		}
		return listDto;
	}
	
	@Override
	public Integer createNewsByAdmin(News news, String admin) {
		
		Assert.notNull(news, "the news can not be null");
		Assert.notNull(news.getCategoryCode(), "the news.getCategoryCode can not be null");
			
			// 标签构建tags
			if(StringUtils.isNotEmpty(news.getTitle())&&StringUtils.isEmpty(news.getTags())){
				List<String> list = IKAnalzyerUtils.getAnalzyerList(news.getTitle());
				Set<String> sList = new HashSet<String>();
				for (String s : list) {
					String str = s.toUpperCase();
					sList.add(str);
				}
				for (String string : sList) {
					if (string.indexOf("月") == -1 && string.indexOf("日") == -1
							&& !StringUtils.isNumber(string) && string.length() > 1 && string.indexOf(".")==-1 && string.indexOf("年") ==-1) {
						if(StringUtils.isEmpty(news.getTags())){
							news.setTags(string);
						}else{
							news.setTags(news.getTags() + "," + string);
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(admin)){
				news.setAdminAccount(admin);
				news.setAdminName(AuthUtils.getInstance().queryStaffNameOfAccount(admin));
			}
			if(news.getPauseStatus()==null || news.getPauseStatus()!=1) {
				news.setPauseStatus(0);
			}
			news.setGmtPublish(new Date());
			
			if(newsDao.countByTitle(news.getTitle())>0){
				return 0;
			}
			
			return newsDao.insertNewsByAdmin(news);
	}

	@Override
	public Integer updateNews(News news) {
		Assert.notNull(news.getId(), "the news.getId can not be null");
		if (news.getViewCount()==null) {
			news.setViewCount(0);
		} 
		if (news.getPauseStatus()==null && news.getViewCount()<=0) {
			news.setPauseStatus(0);
		}
		if(news.getPauseStatus()==null || news.getPauseStatus()!=1) {
			news.setPauseStatus(0);
		}
		news.setGmtModified(new Date());
		//TODO 发布时间应该从页面传过来，但现在出现格式转换问题，暂时用下面处理方法代替
		news .setGmtPublish(newsDao.queryNewDetailsById(news.getId()).getGmtPublish());
		return newsDao.updateNews(news);
	}

	@Override
	public Integer deleteNewsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return newsDao.deleteNewsById(id);
	}

	@Override
	public Integer updateStatusOfNews(Integer id, Integer status) {
		Assert.notNull(id, "the id can not be null");
		if(status != 1){
			status=0;
		}
		return newsDao.updateStatusOfNews(id, status);
	}
	
	@Override
	public PageDto<NewsDto> pageNewsByAdmin(String code,String title,Integer status,String type,PageDto<NewsDto> page) {
		NewsDto dto = new NewsDto(); 
		News news=new News();
		news.setCategoryCode(code);
		news.setTitle(title);
		news.setPauseStatus(status);
		dto.setNews(news);
		NewsRecommend recommend = new NewsRecommend();
		recommend.setType(type);
		dto.setNewsRecommend(recommend);
		
		List<NewsDto> list=newsDao.queryNewsByAdmin(dto,page);
		for(NewsDto obj:list){
			obj.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_NEWS, obj.getNews().getCategoryCode()));
		}
		
		page.setRecords(list);
		page.setTotals(newsDao.queryNewsByAdminCount(dto));
		
		return page;
	}

	@Override
	public Integer updateNewsRecommend(Integer id, String type) {
		Integer rtnInteger = 0;
		if(id!=null && StringUtils.isNotEmpty(type)) {
			NewsRecommend newsRecommend = newsRecommendDao.queryRecommendByNewsIdAndType(id, type);
			if(newsRecommend!=null) {
				rtnInteger = 1;
			} else {
				rtnInteger = newsRecommendDao.insertRecommend(new NewsRecommend(null, id, type, null, null));
			}
		}
		return rtnInteger;
	}

	@Override
	public Integer cancelRecommendNews(Integer id,Integer cid) {
		Integer rtnInteger = 0;
		if(id!=null) {
			rtnInteger = newsRecommendDao.deleteRecommendById(id,cid);
		}
		return rtnInteger;
	}

//	@Override
//	public Integer updateViewCountById(Integer id) {
//		Assert.notNull(id, "the id can not be null");
//		return newsDao.updateViewCountById(id);
//	}

//	@Override
//	public List<News> queryNewByKeywords(String keywords, Integer size) {
//		return newsDao.queryNewByKeywords(keywords,size);
//	}

	@Override
	public List<News> queryRecommendNewByWeekly(String type, Integer size,
			Integer recommend, String category) {
		return newsDao.queryRecommendNewByWeekly(type,size,recommend,category);
	}

	@Override
	public Integer countByTitle(String title) {
		return newsDao.countByTitle(title);
	}

	@Override
	public List<News> queryListByFromTo(String fromStr, String toStr) {
		Date from = null, to = null;
		if (StringUtils.isNotEmpty(fromStr)) {
			try {
				from = DateUtil.getDate(fromStr, "yyyy-MM-dd");
			} catch (ParseException e) {
				from = new Date();
			}
		}
		if (StringUtils.isNotEmpty(toStr)) {
			try {
				to = DateUtil.getDate(toStr, "yyyy-MM-dd");
				to = DateUtil.getDateAfterDays(to, 1);
			} catch (ParseException e) {
				to = new Date();
			}
		}
		Integer id;
		try {
			id = DateUtil.getIntervalDays(from, to);
		} catch (ParseException e) {
			id = 31;
		}
		if (id.intValue() > 30) {
			from = DateUtil.getDateAfterDays(to, -30);
		}
		return newsDao.queryListByFromTo(DateUtil.toString(from, "yyyy-MM-dd"), DateUtil.toString(to,"yyyy-MM-dd"));
	}

}
