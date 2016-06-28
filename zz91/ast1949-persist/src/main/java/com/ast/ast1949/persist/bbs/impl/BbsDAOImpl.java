/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-18 by liulei
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategoryDO;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.bbs.BbsPostDTO;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.persist.bbs.BbsDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author liulei
 * 
 */
@Component("bbsDao")
public class BbsDAOImpl extends SqlMapClientDaoSupport implements BbsDAO {


	public final static int DEFAULT_BATCH_SIZE = 20;

	public Integer insertBbsPostReply(BbsPostReplyDO bbsPostReplyDO) {
		Assert.notNull(bbsPostReplyDO, "The bbsPostReplyDO can not be null");
		return (Integer) getSqlMapClientTemplate().insert("bbs.insertBbsPostReply",bbsPostReplyDO);
	}

	public Integer insertBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO) {
		Assert.notNull(bbsUserProfilerDO, "The bbsUserProfilerDO can not be null");
		return (Integer) getSqlMapClientTemplate().insert("bbs.insertBbsUserProfiler",bbsUserProfilerDO);
	}

	public BbsPostCategoryDO queryBbsPostCategoryById(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return (BbsPostCategoryDO) getSqlMapClientTemplate().queryForObject(
				"bbs.queryBbsPostCategoryById", id);
	}

	public Integer updateBbsUserPicturePath(String account, String picturePath) {
		Assert.notNull(account, "The id can not be null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("picturePath", picturePath);
		return getSqlMapClientTemplate().update("bbs.updateBbsUserPicturePath", map);
	}

	public Integer insertBbsSign(BbsSignDO bbsSignDO) {
		Assert.notNull(bbsSignDO, "The bbsSignDO can not be null");
		return (Integer) getSqlMapClientTemplate().insert("bbs.insertBbsSign", bbsSignDO);
	}

	@SuppressWarnings("unchecked")
	public List<BbsSignDO> queryBbsSignByAccount(String account, Integer startIndex, Integer size,
			String sort, String dir) {
		Assert.notNull(account, "The account can not be null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("startIndex", startIndex);
		map.put("size", size);
		map.put("sort", sort);
		map.put("dir", dir);
		return getSqlMapClientTemplate().queryForList("bbs.queryBbsSignByAccount", map);
	}

	public Integer updateSomeBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO) {
		Assert.notNull(bbsUserProfilerDO, "The bbsUserProfilerDO can not be null");
		return getSqlMapClientTemplate().update("bbs.updateSomeBbsUserProfiler", bbsUserProfilerDO);
	}

	public Integer queryBbsSignByAccountCount(String account) {
		Assert.notNull(account, "The account can not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("bbs.queryBbsSignByAccountCount", account);
	}


	@SuppressWarnings("unchecked")
	public List<BbsPostDTO> queryUserNicknameByReply(String account, Integer startIndex,
			Integer size, String sort, String dir) {
		Assert.notNull(account, "The account can not be null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("startIndex", startIndex);
		map.put("size", size);
		map.put("sort", sort);
		map.put("dir", dir);
		return getSqlMapClientTemplate().queryForList("bbs.queryUserNicknameByReply", map);
	}
	
	public Integer updateBbsPostReplyCount(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return getSqlMapClientTemplate().update("bbs.updateBbsPostReplyCount", id);
	}
	
	public Integer updateBbsPostReplyCountForDel(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return getSqlMapClientTemplate().update("bbs.updateBbsPostReplyCountForDel", id);
	}

	public Integer updateReplyCount (Integer id , Integer replyCount) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("replyCount", replyCount);
		return getSqlMapClientTemplate().update("bbs.updateReplyCount", map);
	}
	public Integer updateBbsPostVisitedCount(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return getSqlMapClientTemplate().update("bbs.updateBbsPostVisitedCount", id);
	}

	public Integer updateBbsPostReplyTime(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return getSqlMapClientTemplate().update("bbs.updateBbsPostReplyTime", id);
	}

	public Integer updateBbsUserProfilerReplyNumber(String account) {
		Assert.notNull(account, "The account can not be null");
		return getSqlMapClientTemplate().update("bbs.updateBbsUserProfilerReplyNumber", account);
	}
	
	public Integer updateBbsUserProfilerReplyNumberForDel(String account) {
		Assert.notNull(account, "The account can not be null");
		return getSqlMapClientTemplate().update("bbs.updateBbsUserProfilerReplyNumberForDel", account);
	}

	public Integer countBbsPost(WeeklyDTO weeklyDTO) {
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject("bbs.countBbsPost",weeklyDTO).toString());
	}

	@SuppressWarnings("unchecked")
	public List<BbsPostDO> listBbsPostByPage(WeeklyDTO weeklyDTO) {
         Assert.notNull(weeklyDTO, "weeklyDTO is not null");
		return getSqlMapClientTemplate().queryForList("bbs.listBbsPostByPage", weeklyDTO);
	}

	public BbsUserProfilerDO queryBbsUserProfilerById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (BbsUserProfilerDO) getSqlMapClientTemplate().queryForObject("bbs.queryBbsUserProfilerById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostDTO> queryNewBbsOnWeek(Date firstDate,Date lastDate,Integer size) {
		Assert.notNull(size, "size is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("firstDate", firstDate);
		map.put("lastDate", lastDate);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList("bbs.queryNewBbsOnWeek", map);
	}

	@Override
	public String queryUserProfilerPictureByCompanyId(Integer companyId) {
		
		return (String) getSqlMapClientTemplate()
			.queryForObject("bbs.queryUserProfilerPictureByCompanyId", companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostCategoryDO> queryAllBbsPostCategory(){
		return getSqlMapClientTemplate().queryForList("bbs.queryAllBbsPostCategory");
	}

}
