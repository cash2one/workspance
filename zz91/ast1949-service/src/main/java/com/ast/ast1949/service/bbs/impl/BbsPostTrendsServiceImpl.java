/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostTrends;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostTrendsDao;
import com.ast.ast1949.service.bbs.BbsPostTrendsService;
@Component("bbsPostTrendsService")
public class BbsPostTrendsServiceImpl implements BbsPostTrendsService {
	@Resource
	private BbsPostTrendsDao bbsPostTrendsDao;
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Override
	public List<BbsPostDO> queryTrendsByCompanyId(Integer companyId, Integer size) {
		//动态集
		List<BbsPostTrends> list=bbsPostTrendsDao.queryTrendsByCompanyId(companyId, size);
		//帖子集
		List<BbsPostDO> listPost=new ArrayList<BbsPostDO>();
		for(BbsPostTrends trends:list){
			BbsPostDO post=bbsPostDAO.queryPostById(trends.getBbsPostId());
			if(post!=null){
				post.setCheckPerson(trends.getAction());
				listPost.add(post);
			}
		}
		return listPost;
	}
	@Override
	public PageDto<BbsPostDO> ListBbsPostByCompanyId(Integer companyId, PageDto<BbsPostDO> page) {
		List<BbsPostTrends> list=bbsPostTrendsDao.queryListTrendsByCompanyId(companyId, page);
		//帖子集
		List<BbsPostDO> listPost=new ArrayList<BbsPostDO>();
		for(BbsPostTrends trends:list){
			BbsPostDO post=bbsPostDAO.queryPostById(trends.getBbsPostId());
			if(post!=null){
				post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
				post.setCheckPerson(trends.getAction());
				listPost.add(post);
			}
		}
		page.setRecords(listPost);
		page.setTotalRecords(bbsPostTrendsDao.countListTrendsByCompanyId(companyId, page));
		return page;
	}
	

}
