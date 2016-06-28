/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
@Component("bbsPostReplyService")
public class BbsPostReplyServiceImpl implements BbsPostReplyService {
	
	/**
	 * 已审核
	 */
	private static final String CHECK_STATUS_TRUE="1";
	/**
	 * 未删除
	 */
	private static final String IS_DEL_FALSE="0";
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;
	
	@Override
	public PageDto<BbsPostReplyDto> pageReplyOfPost(Integer postId, String iconTemplate, PageDto<BbsPostReplyDto> page) {
		List<BbsPostReplyDto> list=bbsPostReplyDao.queryReplyOfPost(postId, page);
		for(BbsPostReplyDto dto:list){
			Pattern p1 = Pattern.compile("\\[\\w\\d\\]");
			String mycontent = Jsoup.clean(dto.getReply().getContent(), Whitelist.basicWithImages());
			Matcher m = p1.matcher(mycontent);
			MessageFormat format = new MessageFormat(iconTemplate);
			while (m.find()) {
				String tmp=m.group(0).replace("[", "").replace("]", "");
				mycontent = mycontent.replace(m.group(0), format.format(new String[]{tmp}));
			}
			mycontent.replace("\n", "<br />");
			dto.getReply().setContent(mycontent);
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostReplyDao.queryReplyOfPostCount(postId));
		return page;
	}
	
	@Override
	public PageDto<BbsPostReplyDO> pageReplyByAdmin(BbsPostReplyDO reply,
			PageDto<BbsPostReplyDO> page) {
		page.setRecords(bbsPostReplyDao.queryReplyByAdmin(reply, page));
		page.setTotalRecords(bbsPostReplyDao.queryReplyByAdminCount(reply));
		return page;
	}

	@Override
	public Integer createReplyByAdmin(BbsPostReplyDO reply,String admin) {
		Assert.notNull(reply, "the reply must not be null");
		reply.setCompanyId(0);
		reply.setAccount(admin);
		reply.setIsDel(IS_DEL_FALSE);
		reply.setCheckPerson(admin);
		reply.setCheckStatus(CHECK_STATUS_TRUE);
		return bbsPostReplyDao.createReplyByAdmin(reply);
	}

	@Override
	public Integer deleteByAdmin(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return bbsPostReplyDao.deleteByAdmin(id);
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkstatus,
			String admin) {
		Assert.notNull(id, "the id must not be null");
		if(checkstatus==null){
			return null;
		}
		return bbsPostReplyDao.updateCheckStatus(id, checkstatus, admin);
	}

	@Override
	public Integer updateReplyByAdmin(BbsPostReplyDO reply) {
		Assert.notNull(reply, "the reply must not be null");
		
		return bbsPostReplyDao.updateReplyByAdmin(reply);
	}

	@Override
	public PageDto<BbsPostDO> pageReplyByUser(String account, String checkStatus,
			PageDto<BbsPostDO> page) {
		page.setSort("gmt_created");
		page.setDir("desc");
		page.setPageSize(10);
		
		page.setRecords(bbsPostReplyDao.queryReplyByUser(account, checkStatus, page));
		page.setTotalRecords(bbsPostReplyDao.queryReplyByUserCount(account, checkStatus));
		
		return page;
	}

	@Override
	public Integer queryBbsPostIdByReplyId(Integer id) {
		return bbsPostReplyDao.queryBbsPostByReplyId(id);
	}

	@Override
	public Integer countMyreply(String account, String checkStatus) {
		return bbsPostReplyDao.queryReplyByUserCount(account, checkStatus);
	}
}
