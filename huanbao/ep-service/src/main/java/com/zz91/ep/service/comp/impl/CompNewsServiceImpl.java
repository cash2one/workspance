/*
 * 文件名称：CompNewsServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompNewsDao;
import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * 项目名称：中国环保网 模块编号：数据操作Service层 模块描述：公司资讯信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
 * 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("compNewsService")
public class CompNewsServiceImpl implements CompNewsService {

	final static Integer TEN_THOUSAND = 10000;
	final static Integer Fifty_THOUSAND = 50000;
	final static Integer ONE_HUNDRED_THOUSAND_LIMIT = 100000;
	final static Integer MILLIONS_LIMIT = 1000000;
	final static Integer ONE_THOUSAND = 5000;

	@Resource
	private CompNewsDao compNewsDao;

	@Resource
	private CompProfileDao compProfileDao;

	@Override
	public List<CompNews> queryNewestCompNews(String categoryCode, Integer cid,
			Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return compNewsDao.queryNewestCompNews(categoryCode, cid, size);
	}

	@Override
	public List<CompNews> queryNewestCompNewsSize(Integer size) {
		if (size == null) {
			size = 50;
		}
		return compNewsDao.queryNewestCompNewsSize(size);
	}

	@Override
	public List<CompNews> queryNewestCompNewsTop(Integer size) {
		if (size == null) {
			size = 50;
		}
		return compNewsDao.queryNewestCompNewsTop(size);
	}

	@Override
	public PageDto<CompNews> pageCompNewsByCid(Integer cid, String type,
			String keywords, Short pause, Short check, Short delete,
			PageDto<CompNews> page) {
		if (page.getSort() == null) {
			page.setSort("cn.gmt_publish");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}
		page.setRecords(compNewsDao.queryCompNewsByCid(cid, type, keywords,
				pause, check, delete, page));
		page.setTotals(compNewsDao.queryCompNewsByCidCount(cid, type, keywords,
				pause, delete, check));
		return page;
	}

	@Override
	public CompNews queryCompNewsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return compNewsDao.queryCompNewsById(id);
	}

	@Override
	public Integer createArticle(CompNews compNews) {
		Assert.notNull(compNews, "the compNews can not be null");
		Assert.notNull(compNews.getCategoryCode(),
				"the compNews.getCategoryCode() can not be null");
		Assert.notNull(compNews.getCid(),
				"the compNews.getCid() can not be null");

		return compNewsDao.insertArticle(compNews);
	}

	@Override
	public Integer updateArticle(CompNews compNews) {
		Assert.notNull(compNews, "the compNews can not be null");
		Assert.notNull(compNews.getCategoryCode(),
				"the compNews.getCategoryCode can not be null");
		Assert
				.notNull(compNews.getId(),
						"the compNews.getId() can not be null");
		Assert.notNull(compNews.getCid(),
				"the compNews.getCid() can not be null");
		return compNewsDao.updateArticle(compNews);
	}

	@Override
	public ExtResult deleteArticle(Integer compNewsId, Integer companyId) {
		ExtResult result = new ExtResult();
		do {
			if (compNewsId == null) {
				break;
			}
			Integer count = compNewsDao.updateDeleteStatusByCid(compNewsId,
					companyId, (short) 0);
			if (count != null && count > 0) {
				result.setSuccess(true);
			}
		} while (false);
		return result;
	}

	@Override
	public ExtResult publishArticle(Integer compNewsId, Integer companyId,
			Short status) {
		ExtResult result = null;
		do {
			if (compNewsId == null) {
				break;
			}
			Integer count = compNewsDao.updatePauseStatusByCid(compNewsId,
					companyId, status);
			if (count != null && count > 0) {
				result = new ExtResult();
				result.setSuccess(true);
			}
		} while (false);
		return result;
	}
    @Override
    public Boolean validateTitleRepeat(Integer companyId, String title) {
        Integer i = compNewsDao.countForCidAndTitle(companyId, title.replaceAll("\\s", ""));
        if (i == null || i == 0) {
            return true;
        } else {
            return false;
        }
    }

	@Override
	public CompNews queryNextCompNewsById(Integer id, Integer cid,
			String categoryCode) {
		Assert.notNull(id, "the id can not be null");
		return compNewsDao.queryNextCompNewsById(id, cid, categoryCode);
	}

	@Override
	public CompNews queryPrevCompNewsById(Integer id, Integer cid,
			String categoryCode) {
		Assert.notNull(id, "the id can not be null");
		return compNewsDao.queryPrevCompNewsById(id, cid, categoryCode);
	}

	@Override
	public Integer updateViewCountById(Integer id) {
		return compNewsDao.updateViewCountById(id);
	}

	@Override
	public PageDto<CompNewsDto> pageCompNewsForArticle(PageDto<CompNewsDto> page) {
		page.setRecords(compNewsDao.queryCompNewsForArticle(page));
		page.setTotals(compNewsDao.queryCompNewsForArticleCount());
		return page;
	}

	@Override
	public List<CompNews> queryWeekForArticle(Integer size) {
		if (size == null) {
			size = 20;
		}
		return compNewsDao.queryWeekForArticle(size);
	}

	@Override
	public PageDto<CompNewsDto> pageBySearchEngine(String keywords,
			PageDto<CompNewsDto> page) {
		
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<CompNewsDto> dtoList = new ArrayList<CompNewsDto>();
		try {
			if (StringUtils.isNotEmpty(keywords)) {
				keywords = keywords.replaceAll("/", "");
				keywords = keywords.replace("%", "");
				keywords = keywords.replace("\\", "");
				keywords = keywords.replace("-", "");
				keywords = keywords.replace("(", "");
				keywords = keywords.replace(")", "");
				sb.append("@(title,tags) ").append(keywords);
			}                
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0, page.getLimit(), ONE_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_created desc");
			SphinxResult res = cl.Query(sb.toString(), "compNewsForArticles");
			if (res == null) {
				return page;
			}
        
			List<String> resultList = new ArrayList<String>();
			Integer preValue = res.totalFound;
			if (preValue != null || preValue != 0) {
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					resultList.add(String.valueOf("" + info.docId));
				}
			} // 总数量
			page.setTotals(res.totalFound);

			for (String id : resultList) {

				if (StringUtils.isNotEmpty(id) && !StringUtils.isNumber(id)) {
					continue;
				}

				CompNews compNews = compNewsDao.queryCompNewsById(Integer
						.valueOf(id));
				if (compNews == null) {
					continue;
				}
				CompNewsDto compNewsDto = new CompNewsDto();

				compNewsDto.setCompNews(compNews);
				CompProfile compProfile = compProfileDao.queryShortCompProfileById(compNews.getCid());
				compNewsDto.setCompName(compProfile.getName());
				compNewsDto.setMemberCode(compProfile.getMemberCode());
				dtoList.add(compNewsDto);

			}// 数据集合
			page.setRecords(dtoList);

		} catch (SphinxException e) {

			return null;
		}

		return page;
	}

}
