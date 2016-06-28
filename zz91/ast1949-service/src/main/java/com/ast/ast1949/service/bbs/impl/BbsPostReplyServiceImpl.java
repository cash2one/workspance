/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs.impl;

import java.io.IOException;
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
import com.ast.ast1949.domain.bbs.BbsPostZan;
import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.persist.bbs.BbsPostNoticeRecommendDao;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.bbs.BbsScoreDao;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.lang.SensitiveUtils;

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
	@Resource 
	private CompanyDAO companyDAO;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private BbsScoreDao bbsScoreDao;
	@Resource
	private BbsUserProfilerDao bbsUserProfilerDao;
	@Resource 
	private CompanyAccountDao companyAccountDao;
	@Resource
	private BbsPostNoticeRecommendDao bbsPostNoticeRecommendDao;
	
	@Override
	public PageDto<BbsPostReplyDto> pageReplyOfPost(Integer postId,Integer companyId,String iconTemplate, PageDto<BbsPostReplyDto> page) {
		List<BbsPostReplyDto> list=bbsPostReplyDao.queryReplyOfPost(postId,companyId,page);
		for(BbsPostReplyDto dto:list){
			
			// 回复实例是否为空
			if(dto.getReply()==null){
				continue;
			}
			
			// 处理 回复内容的html样式
			Pattern p1 = Pattern.compile("\\[\\w\\d\\]");
			String mycontent = Jsoup.clean(dto.getReply().getContent(),Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td","a").addAttributes("td","rowspan").addAttributes("a", "href" ,"class","target"));
			Matcher m = p1.matcher(mycontent);
			MessageFormat format = new MessageFormat(iconTemplate);
			while (m.find()) {
				String tmp=m.group(0).replace("[", "").replace("]", "");
				mycontent = mycontent.replace(m.group(0), format.format(new String[]{tmp}));
			}
			mycontent.replace("\n", "<br />");
			dto.getReply().setContent(mycontent);
			
			// 公司id 是否为空
			Integer replyCompanyId = dto.getReply().getCompanyId();
			if (replyCompanyId==null) {
				continue;
			}
			
			//查询公司会员类型
			String membershipCode =companyDAO.queryMembershipOfCompany(dto.getReply().getCompanyId());
			dto.setMembershipCode(membershipCode);
			if(dto.getReply().getCompanyId()>0){
				Company company= companyDAO.queryDomainOfCompany(dto.getReply().getCompanyId());
				if(company!=null){
					dto.setCompany(company);
				}
				if(StringUtils.isNotEmpty(membershipCode) && membershipCode.equals("10051000")){
					ProductsDO product=productsDAO.queryProductsByCidForLatest(dto.getReply().getCompanyId(), null);
					if(product!=null){
						dto.setProductId(product.getId());	
					}
				}
				CompanyAccount companyAccount=companyAccountDao.queryAccountByCompanyId(dto.getReply().getCompanyId());
				if(companyAccount!=null){
					dto.setCompanyAccount(companyAccount);
					if(StringUtils.isNumber(dto.getCompanyAccount().getContact())&&dto.getCompanyAccount().getContact().length()>6){
						dto.getCompanyAccount().setContact(dto.getCompanyAccount().getContact().substring(0, 6));
					}
				}
			}
			if(dto.getProfiler()!=null&&StringUtils.isNumber(dto.getProfiler().getNickname())&&dto.getProfiler().getNickname().length()>6){
				dto.getProfiler().setNickname(dto.getProfiler().getNickname().substring(0, 6));
			}
			if("leicf".equals(dto.getReply().getAccount())){
				dto.getProfiler().setNickname("互助管理员");
			}
			// 计算积分
			BbsScore bbsScore = new BbsScore();
			bbsScore.setCompanyId(replyCompanyId);
			dto.getProfiler().setIntegral(bbsScoreDao.sumScore(bbsScore));
			
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostReplyDao.queryReplyOfPostCount(postId,companyId));
		return page;
	}
	
	@Override
	public PageDto<BbsPostReplyDO> pageReplyByAdmin(BbsPostReplyDO reply,
			PageDto<BbsPostReplyDO> page) {
		List<BbsPostReplyDO> list=bbsPostReplyDao.queryReplyByAdmin(reply, page);
		for(BbsPostReplyDO rely:list){
			//获取发贴人昵称
			if(!"leicf".equals(rely.getAccount())){
				BbsUserProfilerDO profiler= bbsUserProfilerDao.queryProfilerOfAccount(rely.getAccount());
				if(profiler!=null&&StringUtils.isNotEmpty(profiler.getNickname())){
					rely.setNickname(profiler.getNickname());
				}else{
					CompanyAccount ca=companyAccountDao.queryAccountByCompanyId(rely.getCompanyId());
					if(ca!=null&&StringUtils.isNotEmpty(ca.getContact())){
						rely.setNickname(ca.getContact());
					}
				}
			}else{
				rely.setNickname("互助管理员");
			}
			if(StringUtils.isNumber(rely.getNickname())&&rely.getNickname().length()>6){
				rely.setNickname(rely.getNickname().substring(0, 6));
			}
		}
		page.setRecords(list);
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
	public PageDto<BbsPostDO> pageReplyByUser(String account, String checkStatus,Integer categoryId,PageDto<BbsPostDO> page) {
		page.setSort("gmt_created");
		page.setDir("desc");
		page.setPageSize(10);
		
		page.setRecords(bbsPostReplyDao.queryReplyByUser(account, checkStatus,categoryId, page));
		page.setTotalRecords(bbsPostReplyDao.queryReplyByUserCount(account, checkStatus,categoryId));
		
		return page;
	}

	@Override
	public Integer queryBbsPostIdByReplyId(Integer id) {
		return bbsPostReplyDao.queryBbsPostByReplyId(id);
	}

	@Override
	public Integer countMyreply(String account, String checkStatus,Integer bbsPostCategoryId) {
		return bbsPostReplyDao.queryReplyByUserCount(account, checkStatus,bbsPostCategoryId);
	}

	@Override
	public Integer queryReplyOfPostCount(Integer postId, Integer companyId) {
		return bbsPostReplyDao.queryReplyOfPostCount(postId,companyId);
	}
	@Override
	public BbsPostReplyDO queryById(Integer id){
		Assert.notNull(id, "the id must not be null");
		return bbsPostReplyDao.queryById(id);	
	}
	@Override
	public Integer updateIsDel(Integer id, String isDel){
		Assert.notNull(id, "the id must not be null");
		return bbsPostReplyDao.updateIsDel(id, isDel);
	}
	@Override
	public PageDto<BbsPostReplyDO> pageReplyByAccount(String account,String checkStatuse,Integer categoryId,
			PageDto<BbsPostReplyDO> page){
		page.setSort("gmt_created");
		page.setDir("desc");
		page.setPageSize(10);
		
		page.setRecords(bbsPostReplyDao.queryReplyByAccount(account, checkStatuse,categoryId, page));
		page.setTotalRecords(bbsPostReplyDao.queryReplyByUserCount(account, checkStatuse,categoryId));
		
		return page;
	}
	@Override
	public Integer updateReplyByUser(BbsPostReplyDO bbsPostReplyDO,String membershipCode){
		Assert.notNull(bbsPostReplyDO.getId(), "the id can not be null");
		//bbsPostReplyDO.setCheckStatus(MemberRuleFacade.getInstance().getValue(
				//membershipCode, "new_help_bbs_check"));
		//if (bbsPostReplyDO.getCheckStatus() == null
			//	|| StringUtils.isEmpty(bbsPostReplyDO.getCheckStatus())) {
			
		//}
		String content=bbsPostReplyDO.getContent();
		try {
			if(SensitiveUtils.validateSensitiveFilter(content)){
				content=SensitiveUtils.getSensitiveValue(content, "*");
				bbsPostReplyDO.setCheckStatus("0");
			}
		} catch (IOException e) {
		} catch (Exception e) {
		}
		bbsPostReplyDO.setContent(content);
		bbsPostReplyDO.setCheckStatus("0");
		return bbsPostReplyDao.updateReplyByUser(bbsPostReplyDO);
		
	}

	@Override
	public List<BbsPostReplyDO> queryBestAnswerByViewCount(Integer size) {
		List<BbsPostReplyDO> list=bbsPostReplyDao.queryBestAnswerByViewCount(size);
		for(BbsPostReplyDO reply:list){
			reply.setReplyCount(reply.getBbsPostId());
		//获取昵称
		CompanyAccount ca=companyAccountDao.queryAccountByCompanyId(reply.getCompanyId());
		if(ca!=null){
			BbsUserProfilerDO bs=bbsUserProfilerDao.queryProfilerOfAccount(ca.getAccount());
			if(bs!=null&&StringUtils.isNotEmpty(bs.getNickname())){
				reply.setAccount(bs.getNickname());
			}else if(ca.getContact()!=null){
				reply.setAccount(ca.getContact());
			}else{
				reply.setAccount("匿名");
			}
		}else{
			reply.setAccount("匿名");
		}
		}
		return list;
	}

	@Override
	public List<BbsPostReplyDto> queryReplyByReplyId(Integer replyId) {
		List<BbsPostReplyDto> list=bbsPostReplyDao.queryReplyByReplyId(replyId);
		for(BbsPostReplyDto dto:list){
			if(StringUtils.isEmpty(dto.getProfiler().getNickname())){
				CompanyAccount ca=companyAccountDao.queryAccountByCompanyId(dto.getReply().getCompanyId());
				if(ca!=null&&ca.getContact()!=null){
					dto.getProfiler().setNickname(ca.getContact());
				}else{
					dto.getProfiler().setNickname("匿名");
				}
			}
		}
		return list;
	}

	@Override
	public BbsPostReplyDO queryLatestReplyByPostId(Integer bbsPostId) {
		if (bbsPostId==null) {
			return null;
		}
		return 	bbsPostReplyDao.queryLatestReplyByPostId(bbsPostId);
	}

	@Override
	public Integer queryReplyByUserCount(String account, String checkStatus,Integer bbsPostCategoryId) {
		return bbsPostReplyDao.queryReplyByUserCount(account, checkStatus, bbsPostCategoryId);
	}

	@Override
	public Integer countReplyByCompanyId(Integer companyId, Integer categoryId) {
		return bbsPostReplyDao.countReplyByCompanyId(companyId, categoryId);
	}

	@Override
	public List<BbsPostReplyDO> queryReplyByCompanyId(Integer categoryId, Integer companyId, Integer size) {
		return bbsPostReplyDao.queryReplyByCompanyId(categoryId, companyId, size);
	}

	@Override
	public PageDto<BbsPostDO> queryReplyByUser(String account, Integer categoryId, PageDto<BbsPostDO> page) {
		List<BbsPostDO> list=bbsPostReplyDao.queryReplyByUser(account, categoryId, page);
		for(BbsPostDO post:list){
			if(StringUtils.isNotEmpty(post.getTitle())){
				post.setTitle(Jsoup.clean(post.getTitle(), Whitelist.none()));
			}
			if(StringUtils.isNotEmpty(post.getContent())){
				post.setContent(Jsoup.clean(post.getContent(), Whitelist.none()));
			}
		}
		page.setRecords(list);
		page.setTotalRecords(bbsPostReplyDao.countBbsReplyByUser(account, categoryId, null));
		return page;
	}

	@Override
	public Integer zanBbsPostReply(Integer companyId, Integer replyId) {
		//mark标志
		Integer mark=0;
		//获取到该回答的信息
		BbsPostReplyDO reply=bbsPostReplyDao.queryById(replyId);
		//判断该用户有无对该回答点赞
		Integer zanNum=bbsPostReplyDao.countCompanyByZan(companyId, replyId);
		if(zanNum==0){
			//记录此次点赞的信息
			BbsPostZan zan=new BbsPostZan();
			zan.setCompanyId(companyId);
			zan.setReplyId(replyId);
			Integer i=bbsPostReplyDao.insertZan(zan);
			if(i>0){
				//更新点赞数
				if(reply.getZanCount()!=null){
					bbsPostReplyDao.updateZanCount(replyId,(reply.getZanCount()+1));
					mark=reply.getZanCount()+1;
				}else{
					bbsPostReplyDao.updateZanCount(replyId,1);
					mark=1;
				}
			}
		}
		return mark;
	}

}
