/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-19 by liulei
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
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
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.dataindex.DataIndexDao;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

import net.sf.json.JSONObject;

/**
 * @author liulei
 * 
 */
@Component("bbsService")
public class BbsServiceImpl implements BbsService {

	public final static int DEFAULT_BATCH_SIZE = 20;
	public final static int DEFAULT_QUERY_BBS_TAGS_SIZE = 1000;
	public final static String CHECK_STATUS_UNPASS = "3";

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
	BbsPostReplyService bbsPostReplyService;
	@Resource
	BbsPostService bbsPostService;
	@Resource
	private DataIndexDao dataIndexDao;
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;
	
	public static Map<Integer, String> INDEX_MAP=new HashMap<Integer, String>();
	static{
		INDEX_MAP.put(1, "10041002");
		INDEX_MAP.put(2, "10041003");
		INDEX_MAP.put(3, "10041004");
		INDEX_MAP.put(4, "10041005");
		INDEX_MAP.put(5, "10131001");
	}


	public Integer insertBbsPostReply(BbsPostReplyDO bbsPostReplyDO) {
		
		// 公司id为空 不能回复
		if(bbsPostReplyDO.getCompanyId()==null){
			return 0;
		}
		// 公司帐号为空 不能回复
		if(StringUtils.isEmpty(bbsPostReplyDO.getAccount())){
			return 0;
		}
		
		// 获取 全通过 开关标志
		String autoPass = ParamUtils.getInstance().getValue("bbs_config", "check_status");
		if(StringUtils.isNotEmpty(autoPass)&&StringUtils.isNumber(autoPass)){
			bbsPostReplyDO.setCheckStatus(autoPass);
		}else{
			String membershipCode = companyDAO.queryMembershipOfCompany(bbsPostReplyDO.getCompanyId());
			if ("".equals(membershipCode) || membershipCode == null
					|| AstConst.COMMON_MEMBERSHIP_CODE.equals(membershipCode)) {
				bbsPostReplyDO.setCheckStatus("0");
			} else {
				bbsPostReplyDO.setCheckStatus("1");
			}
		}
		
		bbsPostReplyDO.setIsDel("0");
		bbsPostReplyDO.setUnpassReason("0");
		bbsPostReplyDO.setUncheckedCheckStatus("0");
		// 处理回复正文
		if(StringUtils.isNotEmpty(bbsPostReplyDO.getContent())){
			//处理回帖敏感词
			bbsPostReplyDO =  checkPostByAdmin(bbsPostReplyDO);
			String content = bbsPostReplyDO.getContent();
			content = Jsoup.clean(content, Whitelist.none().addTags("li","ul","ol","p").addAttributes("a", "href","target","class"));
			bbsPostReplyDO.setContent(content);
			bbsPostReplyDO.setCheckTime(new Date());
		}else{
			return 0;
		}
		bbsDAO.updateBbsPostReplyTime(bbsPostReplyDO.getBbsPostId());
		
		Integer i = bbsDAO.insertBbsPostReply(bbsPostReplyDO);
		if(i>0){
			// 如果回复成功，给用户加分
			if ("1".equals(bbsPostReplyDO.getCheckStatus())) {
				// 用户信息回复数加1
				bbsService.updateBbsUserProfilerReplyNumber(bbsPostReplyDO.getAccount());
				// 主贴信息回复数加1
				bbsService.updateBbsPostReplyCount(bbsPostReplyDO.getBbsPostId());
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(bbsPostReplyDO.getCompanyId(), null, "get_post_bbs_reply", null, bbsPostReplyDO.getBbsPostId(), null));
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

	public Integer updateReplyCount (Integer id , Integer replyCount) {
		Assert.notNull(id, "The id can not be null");
		return bbsDAO.updateReplyCount(id, replyCount);
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
			if(bbsPostDO==null){
				continue;
			}
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

	@Override
	public Boolean countBbsInfo(Map<String, Object> out, String account,Integer companyId) {
		if(StringUtils.isEmpty(account)){
			return false;
		}
		Integer i = 0;
		//回复帖子数
		i = bbsPostReplyService.countMyreply(account,null, 2);
		Integer j=bbsPostReplyService.countMyreply(account,null, 3);
		Integer x= bbsPostReplyService.countMyreply(account, null, 106);
		out.put("countReply", i+j+x);
		//回复问答数
		i = bbsPostReplyService.countMyreply(account,null, 1);
		out.put("countReplyQA", i);
		//i = bbsPostService.countBbsPostByCompanyId(companyId, 2);
		//发贴总数
		i = bbsPostService.countBbsByCompanyId(companyId, 2);	
		j=bbsPostService.countBbsByCompanyId(companyId, 3);
		x=bbsPostService.countBbsByCompanyId(companyId, 106);
		out.put("countPosted",i+j+x);
		
		//问答总数
		i = bbsPostService.countBbsByCompanyId(companyId,1);
		out.put("countPostedQA", i);
		return true;
	}
	
	@Override
	public Boolean countBeBbsInfo(Map<String, Object> out, String account) {
		if(StringUtils.isEmpty(account)){
			return false;
		}
		Integer i = 0;
		i = bbsPostReplyDao.countBeReply(account, null);
		out.put("countBeReply", 0<i||i!=null?i:0);
		i = bbsPostReplyDao.countBeReply(account,11);
		out.put("countBeReplyQA", 0<i||i!=null?i:0);
		return true;
	}

	@Override
	public void initBbsPostCategory() {
		List<BbsPostCategoryDO> list = bbsDAO.queryAllBbsPostCategory();
		for(BbsPostCategoryDO obj:list){
			BbsService.BBS_POST_CATEGORY_MAP.put(obj.getId(), obj.getName());
		}
	}
	
	public static void main(String[] args) {
		String str = "<ol style=\"sdfasdasfas\"></ol><ul></ul><li></li>";
		System.out.println(Jsoup.clean(str, Whitelist.basic()));
	}
	
	/**
	 * 回帖敏感词审核
	 * @param post
	 * @return
	 */
	private BbsPostReplyDO checkPostByAdmin(BbsPostReplyDO reply){
		try {
			// 内容敏感词过滤
			boolean mgcFlag = false;
			Set<String> sensitiveSet = new HashSet<String>();
			if (SensitiveUtils.validateSensitiveFilter(reply.getContent())) {
				Map<String, Object> map = SensitiveUtils.getSensitiveFilter(reply.getContent());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) map.get("sensitiveSet");
				for (String obj:s) {
					sensitiveSet.add(obj);
				}
				reply.setContent(map.get("filterValue").toString());
				mgcFlag=true;
			}
			
			// 回帖不通过并设置相关属性
			if (mgcFlag) {
				reply.setCheckStatus(CHECK_STATUS_UNPASS);
				reply.setCheckPerson("admin");
				reply.setUnpassReason("该回帖包含敏感词："+sensitiveSet.toString());
			}
		} catch (Exception e) {
			return reply;
		}
		return reply;
	}
}
