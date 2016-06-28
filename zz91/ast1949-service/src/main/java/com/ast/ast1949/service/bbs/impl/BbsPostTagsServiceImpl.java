/**
 * @author shiqp 日期：2014-11-11
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.bbs.BbsPostTagsDao;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;
import com.ast.ast1949.service.bbs.BbsPostTagsService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("bbsPostTagsService")
public class BbsPostTagsServiceImpl implements BbsPostTagsService {
	@Resource
	private BbsPostTagsDao bbsPostTagsDao;
	@Resource
	private BbsPostCategoryService bbsPostCategoryService;

	@Override
	public List<BbsPostTags> queryTagsByArticleC(Integer size) {
		return bbsPostTagsDao.queryTagsByArticleC(size);
	}

	@Override
	public BbsPostTags queryTagByName(String tagName,Integer category,Integer isDel) {
		return bbsPostTagsDao.queryTagByName(tagName,category,0);
	}

	@Override
	public List<BbsPostTags> queryTagByCategory(Integer category, Integer size) {
		return bbsPostTagsDao.queryTagByCategory(category, size);
	}

	@Override
	public void dealTags(String tag, Integer categoryId) {
		//如果多个标签拆成标签组
		tag=tag.replace(",", "，");
		String[] tags=tag.split("，");
		BbsPostTags bbsPostTags=new BbsPostTags();
		//判断有无该标签，有的执行更新操作，没有的插入新标签
		for(String str:tags){
			bbsPostTags=bbsPostTagsDao.queryTagByName(str,categoryId,0);
			if(bbsPostTags!=null){
			//有，更新文章数
				bbsPostTags.setArticleCount((bbsPostTags.getArticleCount()+1));
				bbsPostTagsDao.updateTag(bbsPostTags);
			}else{
			//没有，插入新标签
				bbsPostTags=new BbsPostTags();
				bbsPostTags.setArticleCount(1);
				bbsPostTags.setNoticeCount(0);
				bbsPostTags.setCategory(categoryId);
				bbsPostTags.setTagName(str);
				if(StringUtils.isNotEmpty(bbsPostTags.getTagName())){
					bbsPostTagsDao.insertTag(bbsPostTags);
				}
			}
		}
		
	}

	@Override
	public BbsPostTags queryTagById(Integer id) {
		return bbsPostTagsDao.queryTagById(id);
	}

	@Override
	public List<BbsPostTags> queryTagsByMark(Integer size) {
		return bbsPostTagsDao.queryTagsByMark(size);
	}

	@Override
	public PageDto<BbsPostTags> pageTagsBySearchEngine(BbsPostTags bbsPostTags,PageDto<BbsPostTags> page) {
		
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<BbsPostTags> list = new ArrayList<BbsPostTags>();
		try {
			
			if (StringUtils.isNotEmpty(bbsPostTags.getTagName())) {
				sb.append("@(tag_name,name) ").append(bbsPostTags.getTagName());
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 1000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "id desc");

			SphinxResult res = cl.Query(sb.toString(), "bbs_post_tags");

			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					BbsPostTags obj = bbsPostTagsDao.queryTagById(Integer.valueOf(""+info.docId));
					if (obj != null) {
						BbsPostCategory bpc =  bbsPostCategoryService.querySimpleCategoryById(obj.getCategory());
						obj.setTagName(obj.getTagName()+"("+bpc.getName()+")");
					}
					if (obj!=null) {
						list.add(obj);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			return page;
		}
		return page;
	}

	@Override
	public Integer up(Integer id) {
		BbsPostTags obj =  bbsPostTagsDao.queryTagById(id);
		if (obj!=null) {
			return bbsPostTagsDao.updateNoticeCountById(id, obj.getNoticeCount()+1);
		}
		return 0;
	}

	@Override
	public Integer down(Integer id) {
		BbsPostTags obj =  bbsPostTagsDao.queryTagById(id);
		if (obj!=null&&obj.getNoticeCount()>0) {
			return bbsPostTagsDao.updateNoticeCountById(id, obj.getNoticeCount()-1);
		}
		return 0;
	}

	@Override
	public PageDto<BbsPostTags> pageTagsByCategory(Integer category, PageDto<BbsPostTags> page) {
		page.setPageSize(20);
		page.setSort("gmt_modified");
		page.setDir("desc");
		page.setRecords(bbsPostTagsDao.queryAllTagsByCategory(category, page));
		page.setTotalRecords(bbsPostTagsDao.countAllTagsByCategory(category));
		return page;
	}

	@Override
	public Integer addTags(BbsPostTags tags) {
		//标签状态 0表示已存在，1表示添加成功，2表示添加失败
		tags.setArticleCount(0);
		tags.setNoticeCount(0);
		Integer i=0;
		//判断标签是否存在
		BbsPostTags tag=bbsPostTagsDao.queryTagByName(tags.getTagName(), tags.getCategory(),null);
		if(tag!=null){
			if(tag.getIsDel()==1){
				bbsPostTagsDao.updateIsDelById(tag.getId(), 0);
				i=1;
			}
		}else{
			Integer in=bbsPostTagsDao.insertTag(tags);
			if(in>0){
				i=1;
			}else{
				i=2;
			}
		}
		return i;
	}

	@Override
	public Integer deleteTags(Integer id) {
		return bbsPostTagsDao.updateIsDelById(id, 1);
	}

	@Override
	public Integer updateNameAndCategory(BbsPostTags tags) {
		return bbsPostTagsDao.updateNameAndCategory(tags);
	}

	@Override
	public Integer updateTag(BbsPostTags bbsPostTags) {
		return bbsPostTagsDao.updateTag(bbsPostTags);
	}

}
