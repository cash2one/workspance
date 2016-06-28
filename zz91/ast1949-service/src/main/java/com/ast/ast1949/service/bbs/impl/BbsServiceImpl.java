/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-19 by liulei
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategoryDO;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.bbs.BbsPostDTO;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.persist.bbs.BbsDAO;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.dataindex.DataIndexDao;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author liulei
 * 
 */
@Component("bbsService")
public class BbsServiceImpl implements BbsService {

	public final static int DEFAULT_BATCH_SIZE = 20;
	public final static int DEFAULT_QUERY_BBS_TAGS_SIZE = 1000;

	@Autowired
	private BbsDAO bbsDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	ScoreChangeDetailsService scoreChangeDetailsService;
	@Autowired
	BbsPostDAO bbsPostDAO;
	
	@Resource
	CompanyAccountService companyAccountService;
	@Resource
	BbsService bbsService;
	
	@Resource
	private DataIndexDao dataIndexDao;
	
	public static Map<Integer, String> INDEX_MAP=new HashMap<Integer, String>();
	static{
		INDEX_MAP.put(1, "10041002");
		INDEX_MAP.put(2, "10041003");
		INDEX_MAP.put(3, "10041004");
		INDEX_MAP.put(4, "10041005");
		INDEX_MAP.put(5, "10131001");
	}


	public Integer insertBbsPostReply(BbsPostReplyDO bbsPostReplyDO) {
		Assert.notNull(bbsPostReplyDO, "The bbsPostReplyDO can not be null");
		String membershipCode = companyDAO.queryMembershipOfCompany(bbsPostReplyDO.getCompanyId());
		if ("".equals(membershipCode) || membershipCode == null
				|| AstConst.COMMON_MEMBERSHIP_CODE.equals(membershipCode)) {
			bbsPostReplyDO.setCheckStatus("0");
		} else {
			bbsPostReplyDO.setCheckStatus("1");
		}
		bbsPostReplyDO.setIsDel("0");
		bbsPostReplyDO.setUnpassReason("0");
		bbsPostReplyDO.setUncheckedCheckStatus("0");
		bbsDAO.updateBbsPostReplyTime(bbsPostReplyDO.getBbsPostId());
		Integer i = bbsDAO.insertBbsPostReply(bbsPostReplyDO);
		if(i>0){
			//TODO 如果回复成功，给用户加分
			if ("1".equals(bbsPostReplyDO.getCheckStatus())) {
				// 用户信息回复数加1
				bbsService.updateBbsUserProfilerReplyNumber(bbsPostReplyDO.getAccount());
				// 主贴信息回复数加1
				bbsService.updateBbsPostReplyCount(bbsPostReplyDO.getBbsPostId());
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(bbsPostReplyDO
						.getCompanyId(), null, "get_post_bbs_reply", null, bbsPostReplyDO
						.getBbsPostId(), null));
			}
		}
		return i;
	}

	public Integer insertBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO) {
		Assert.notNull(bbsUserProfilerDO, "The bbsUserProfilerDO can not be null");
		CompanyAccount account = companyAccountService.queryAccountByAccount(bbsUserProfilerDO.getAccount());
		if (account != null) {
			bbsUserProfilerDO.setCompanyId(account.getCompanyId());
			bbsUserProfilerDO.setTel(account.getTel());
			bbsUserProfilerDO.setMsn(account.getMsn());
			bbsUserProfilerDO.setRealName(account.getContact());
		}
		bbsUserProfilerDO.setIntegral(0);
		bbsUserProfilerDO.setPostNumber(0);
		bbsUserProfilerDO.setEssenceNumber(0);
		bbsUserProfilerDO.setReplyNumber(0);
		return bbsDAO.insertBbsUserProfiler(bbsUserProfilerDO);
	}

	public BbsPostCategoryDO queryBbsPostCategoryById(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return bbsDAO.queryBbsPostCategoryById(id);
	}

	public Integer updateBbsUserPicturePath(String account, String picturePath) {
		Assert.notNull(account, "The account can not be null");
		return bbsDAO.updateBbsUserPicturePath(account, picturePath);
	}

	public Integer insertBbsSign(BbsSignDO bbsSignDO) {
		Assert.notNull(bbsSignDO, "The bbsSignDO can not be null");
		return bbsDAO.insertBbsSign(bbsSignDO);
	}

	public List<BbsSignDO> queryBbsSignByAccount(String account, Integer startIndex, Integer size,
			String sort, String dir) {
		Assert.notNull(account, "The account can not be null");
		return bbsDAO.queryBbsSignByAccount(account, startIndex, size, sort, dir);
	}


	public Integer updateSomeBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO) {
		Assert.notNull(bbsUserProfilerDO, "The bbsUserProfilerDO can not be null");
		return bbsDAO.updateSomeBbsUserProfiler(bbsUserProfilerDO);
	}


	public Integer queryBbsSignByAccountCount(String account) {
		Assert.notNull(account, "The account can not be null")//	public List<BbsPostCategoryDO> queryBbsPostCategory() {
		//		return bbsDAO.queryBbsPostCategory();
		//		}
		;
		return bbsDAO.queryBbsSignByAccountCount(account);
	}


	public List<BbsPostDTO> queryUserNicknameByReply(String account, Integer startIndex,
			Integer size, String sort, String dir) {
		Assert.notNull(account, "The account can not be null");
		return bbsDAO.queryUserNicknameByReply(account, startIndex, size, sort, dir);
	}

	public Integer updateBbsPostReplyCount(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return bbsDAO.updateBbsPostReplyCount(id);
	}
	
	public Integer updateBbsPostReplyCountForDel(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return bbsDAO.updateBbsPostReplyCountForDel(id);
	}

	public Integer updateBbsPostVisitedCount(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return bbsDAO.updateBbsPostVisitedCount(id);
	}

	public Integer updateBbsUserProfilerReplyNumber(String account) {
		Assert.notNull(account, "The account can not be null");
		return bbsDAO.updateBbsUserProfilerReplyNumber(account);
	}
	
	public Integer updateBbsUserProfilerReplyNumberForDel(String account) {
		Assert.notNull(account, "The account can not be null");
		return bbsDAO.updateBbsUserProfilerReplyNumberForDel(account);
	}

	public Integer countBbsPost(WeeklyDTO weeklyDTO) {
		return bbsDAO.countBbsPost(weeklyDTO);
	}

	public List<BbsPostDO> listBbsPostByPage(WeeklyDTO weeklyDTO) {
		Assert.notNull(weeklyDTO, "weeklyDTO is not null");
		return bbsDAO.listBbsPostByPage(weeklyDTO);
	}

	public BbsUserProfilerDO queryBbsUserProfilerById(Integer id) {
		Assert.notNull(id, "id is not null");
		return bbsDAO.queryBbsUserProfilerById(id);
	}


	@Override
	public List<BbsPostDTO> queryNewBbsOnWeek(Date firstDate, Date lastDate, Integer size) {

		return bbsDAO.queryNewBbsOnWeek(firstDate, lastDate, size);
	}

	@Override
	public String queryUserProfilerPictureByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");

		return bbsDAO.queryUserProfilerPictureByCompanyId(companyId);
	}

	@Override
	public List<Map<String, Object>> queryTopPost(Integer categoryId,
			Integer size) {

		String code=INDEX_MAP.get(categoryId);
		if(StringUtils.isEmpty(code)){
			return null;
		}
		if(size==null){
			size=100;
		}
		
		List<DataIndexDO> idxList= dataIndexDao.queryDataIndexByParentCode(code, "%", size);
		
		if(idxList==null || idxList.size()<=0){
			return null;
		}
		
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(DataIndexDO dataIndexDO:idxList){
			BbsPostDO bbsPostDO=new BbsPostDO();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("json", JSONObject.fromObject(dataIndexDO.getTitle()));
			Integer bid=Integer.parseInt((String) JSONObject.fromObject(dataIndexDO.getTitle()).get("bid"));
			bbsPostDO=bbsPostDAO.queryPostById(bid);
			map.put("style", dataIndexDO.getStyle());
			if(StringUtils.isNotEmpty(dataIndexDO.getPic())){
				map.put("pic", dataIndexDO.getPic());
			}
			map.put("view", bbsPostDO.getVisitedCount());
			map.put("reply", bbsPostDO.getReplyCount());
			if(bbsPostDO.getReplyTime()==null){
				map.put("replyTime", DateUtil.toString(bbsPostDO.getPostTime(), "yyyy-MM-dd"));
			}else {
				map.put("replyTime", DateUtil.toString(bbsPostDO.getReplyTime(), "yyyy-MM-dd"));
			}
			map.put("link", dataIndexDO.getLink());
			resultList.add(map);
		}
		
		return resultList;
		
	}

}
