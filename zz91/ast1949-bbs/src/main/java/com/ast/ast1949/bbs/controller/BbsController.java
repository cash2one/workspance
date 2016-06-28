/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-20 by liulei
 */
package com.ast.ast1949.bbs.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.bbs.BbsPostCategoryDO;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.bbs.ReportsDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.bbs.BbsPostDTO;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;
import com.ast.ast1949.service.bbs.BbsPostNoticeRecommendService;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsPostTagsService;
import com.ast.ast1949.service.bbs.BbsPostTypeService;
import com.ast.ast1949.service.bbs.BbsScoreService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.bbs.BbsViewLogService;
import com.ast.ast1949.service.bbs.ReportsService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.download.DownloadInfoService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.BbsConst;
import com.ast.ast1949.util.NavConst;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * @author liulei
 */
@Controller
public class BbsController extends BaseController {
	// Boolean success = true;
	@Autowired
	private BbsService bbsService;
	@Autowired
	private ReportsService reportsService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Autowired
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsPostReplyService bbsPostReplyService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private CompanyService companyService;
	@Resource
	private BbsViewLogService bbsViewLogService;
	@Resource
	private BbsPostTypeService bbsPostTypeService;
	@Resource
	private ProductsService productsService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private DownloadInfoService downloadInfoService;
	@Resource
	private DataIndexService dataIndexService;
	@Resource
	private AuthService authService;
	@Resource
	private BbsScoreService bbsScoreService;
	@Resource
	private BbsPostNoticeRecommendService bbsPostNoticeRecommendService;
	@Resource
	private BbsPostTagsService bbsPostTagsService;
	@Resource
	private BbsPostCategoryService bbsPostCategoryService;
	

	private final static Integer DEFAULT_CATEGORY_ID = 1;

	/**
	 * 初始化bbs首页面
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out)
			throws UnsupportedEncodingException {
		out.put("CategoryMap", BbsService.BBS_POST_CATEGORY_MAP);

		initBaseInfo(out, getCachedUser(request));

		// 判断是否登入
		SsoUser sessionUser = getCachedUser(request);
		out.put("sessionUser", sessionUser);
		BbsUserProfilerDO bbsUserProfilerDO = new BbsUserProfilerDO();
		if (sessionUser != null) {
			// 个人基本信息
			bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			// 查询出个人签名
			List<BbsSignDO> bbsSignDOList = bbsService.queryBbsSignByAccount(
					sessionUser.getAccount(), 0, BbsConst.DEFAULT_POST_MY_SIZE,

					BbsConst.DEFAULT_MODIFIED_TIME_SORT,
					BbsConst.DEFAULT_POST_DIR);
			if (bbsSignDOList.size() > 0) {
				out.put("bbsSingn", bbsSignDOList.get(0));
			}

			initBbsUserProfiler(bbsUserProfilerDO, out,
					sessionUser.getAccount());
			//关注数（个人中心）
			BbsPostNoticeRecommend bbs=new BbsPostNoticeRecommend();
			bbs.setState(0);
			bbs.setType(1);
			bbs.setCategory(2);
			bbs.setCompanyId(sessionUser.getCompanyId());
			bbs.setContentId(sessionUser.getCompanyId());
			Integer notice=bbsPostNoticeRecommendService.countNumbyCompanyId(bbs);
			out.put("notice", notice);
			//被关注数
			Integer recom=bbsPostNoticeRecommendService.countNumByContentId(bbs);
			out.put("recom", recom);
			
		}
		//政策法规
		PageDto<BbsPostDO> page =  new PageDto<BbsPostDO>();
		page.setPageSize(5);
		page.setStartIndex(0);
		page=bbsPostService.pagePostByNewSearchEngine("政策法规", page, null, 2);
		List<BbsPostDO> list = page.getRecords();
		List<BbsPostDO> postByType3 = bbsPostService
				.queryPostWithContentByType("3", 4);
		out.put("postByType3", postByType3);
        //类别
		Map<Integer,String> map=getMapCategory();
		out.put("map", map);
		out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		out.put("list", list);
		out.put("url", "index.htm");
		SeoUtil.getInstance().buildSeo(out);
		return new ModelAndView();
	}

	/**
	 * bbs我的头像
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView oldmyalterimg(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "myalterimg.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 修改头像路径
	 */
	@RequestMapping
	public ModelAndView myupdateimg(String path, HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		Integer i = null;
		ExtResult result = new ExtResult();

		SsoUser sessionUser = getCachedUser(request);
		if (!bbsUserProfilerService.isProfilerExist(sessionUser.getAccount())) {
			bbsUserProfilerService.createEmptyProfilerByUser(sessionUser
					.getAccount());
		}

		i = bbsService.updateBbsUserPicturePath(sessionUser.getAccount(), path);

		if (i > 0) {
			scoreChangeDetailsService
					.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser
							.getCompanyId(), null, "base_bbs_user_avatar",
							null, null, null));
			result.setSuccess(true);

		}
		return printJson(result, out);
	}

	/**
	 * bbs我的基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView oldmyalterinfo(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面
		if (sessionUser == null) {
			out.put("url", "myalterinfo.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 修改基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myupdateinfo(BbsUserProfilerDO bbsUserProfilerDO,
			HttpServletRequest request, Map<String, Object> model)
			throws UnsupportedEncodingException {
		Integer i = null;
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		bbsUserProfilerDO.setAccount(sessionUser.getAccount());
		if (!bbsUserProfilerService.isProfilerExist(sessionUser.getAccount())) {
			// 添加信息
			i = bbsService.insertBbsUserProfiler(bbsUserProfilerDO);
			if (i != null && i.intValue() > 0) {
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser
								.getCompanyId(), null, "base_bbs_profiler",
								null, null, null));
			}
		} else {
			// 修改信息
			i = bbsService.updateSomeBbsUserProfiler(bbsUserProfilerDO);
		}

		if (i != null && i.intValue() > 0) {
			return new ModelAndView(new RedirectView("myalterinfo.htm"));
		}
		return new ModelAndView("/common/error");
	}

	/**
	 * bbs我发表的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myposted(PageDto<BbsPostDO> page,
			HttpServletRequest request, Map<String, Object> out)
			throws UnsupportedEncodingException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "myposted.htm");
			return new ModelAndView("/login");
		}
		initBbsUserProfiler(bbsUserProfilerService.queryProfilerOfAccount(sessionUser.getAccount()), out, sessionUser.getAccount());

		commonHead(out);
		initBaseInfo(out, getCachedUser(request));

		indexBbsUserProfiler(bbsUserProfilerService.queryProfilerOfAccount(sessionUser.getAccount()), out, sessionUser.getAccount());
		page = bbsPostService.pagePostByUser(sessionUser.getAccount(), null,"0", null, null, null, page);
		out.put("page", page);
		out.put("bbsPostCount", page.getTotalRecords());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("post", "post");
		return null;
	}

	/**
	 * 添加企业签名
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView addsign(BbsSignDO bbsSignDO,
			HttpServletRequest request, Map<String, Object> model)
			throws UnsupportedEncodingException {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = sessionUser.getCompanyId();
		String account = sessionUser.getAccount();
		bbsSignDO.setCompanyId(companyId);
		bbsSignDO.setAccount(account);
		Integer i = bbsService.insertBbsSign(bbsSignDO);
		if (i > 0) {
			return new ModelAndView(new RedirectView("mysign.htm"));
		}
		return new ModelAndView("/common/error");
	}

	/**
	 * bbs我的企业标签
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView oldmysign(String p, HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "mysign.htm");
			return new ModelAndView("/login");
		}
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));

		if (p == null || !StringUtils.isNumber(p)) {
			p = "1";
		}

		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		Integer startIndex = (Integer.valueOf(p) - 1)
				* BbsConst.DEFAULT_POST_MY_SIZE;

		Integer totalRecords = bbsService
				.queryBbsSignByAccountCount(sessionUser.getAccount());
		List<BbsSignDO> bbsSignDOList = bbsService.queryBbsSignByAccount(
				sessionUser.getAccount(), startIndex,
				BbsConst.DEFAULT_POST_MY_SIZE,

				BbsConst.DEFAULT_MODIFIED_TIME_SORT, BbsConst.DEFAULT_POST_DIR);
		for (BbsSignDO o : bbsSignDOList) {
			o.setSignName(StringUtils.controlLength(o.getSignName(), 100));
		}
		// 计算总page大小

		Integer totalPages = totalRecords / BbsConst.DEFAULT_POST_MY_SIZE + 1;
		if (totalRecords % BbsConst.DEFAULT_POST_MY_SIZE == 0) {
			totalPages--;
		}
		out.put("totalPages", totalPages);
		out.put("currentPage", Integer.valueOf(p));
		out.put("bbsSignDOList", bbsSignDOList);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		return null;
	}

	/**
	 * 根据标签查看帖子
	 * 
	 * @param tagsId
	 * @param p
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView bbsTags(Integer tagsId, String p, String title,
			String sign, Map<String, Object> out, HttpServletRequest request)
			throws UnsupportedEncodingException {

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "bbsTags.htm");
			return new ModelAndView("/login");
		}
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		String keywords = "";
		if (StringUtils.isNotEmpty(title)) {
			keywords = StringUtils.decryptUrlParameter(title);
		} else if (tagsId != null) {
			// 查询标签名
			// BbsTagsDO bbsTagsDO = bbsService.queryBbsTagsById(tagsId);
			keywords = "废电器";
		}
		keywords = URLEncoder.encode(keywords, "utf-8");
		return new ModelAndView("redirect:search.htm?keywords=" + keywords);

	}

	/**
	 * 根据类别查看帖子
	 * 
	 * @param categoryId
	 * @param p
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView bbsCategory(Integer categoryId, PageDto<PostDto> page,
			Map<String, Object> out, HttpServletRequest request, Integer typeQA)
			throws UnsupportedEncodingException {

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {

			// 个人基本信息

			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());

			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
			initBbsUserProfiler(bbsUserProfilerDO, out,
					sessionUser.getAccount());

		}
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));

		if (categoryId == null) {
			categoryId = 1;
		}
		page.setPageSize(30);

		BbsPostCategoryDO bbsPostCategoryDO = bbsService
				.queryBbsPostCategoryById(categoryId);
		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle(bbsPostCategoryDO.getName() + "_互助社区_废料生意人的交流平台_第"
				+ currentpage + "页_ZZ91再生网");
		headDTO.setPageKeywords(bbsPostCategoryDO.getName()
				+ "废料市场动态, 废料供应, 废料求购");
		headDTO.setPageDescription("ZZ91再生网旗下互助社区的废料动态商务交流区,在这里你可以找到废料市场动态，废料供应、废料求购，废料价格等废料动态。");
		setPageHead(headDTO, out);
		out.put("category", bbsPostCategoryDO);

		if (page.getSort() == null) {
			page.setSort("post_time");
		}
		page.setDir("desc");

		// 最热问答
		if (categoryId == 11 && typeQA != null && typeQA == 3) {
			page.setSort("reply_count");
		}
		page = bbsPostService.pagePostByCategory(categoryId, typeQA, page);
		for(PostDto obj :page.getRecords()){
			CompanyAccount companyAccount=companyAccountService.queryAccountByCompanyId(obj.getPost().getCompanyId());
            if(companyAccount!=null){
			   obj.setContact(companyAccount.getContact());
            }
			if(StringUtils.isNotEmpty(obj.getProfiler().getNickname())){
				if(StringUtils.isEmail(obj.getProfiler().getNickname())||StringUtils.isNumber(obj.getProfiler().getNickname())){
						if(obj.getProfiler().getNickname().length()>7){
							obj.getProfiler().setNickname(obj.getProfiler().getNickname().substring(0,7));
						}
			    } 
			}
			
		}

		out.put("page", page);

		out.put("topPost", bbsService.queryTopPost(categoryId, 3));
		out.put("categoryId", categoryId);
		out.put("categoryName",
				BbsService.BBS_POST_CATEGORY_MAP.get(categoryId));

		// PageCacheUtil.setCDNCache(response, PageCacheUtil.MAX_AGE_MIN * 30);
		if (categoryId == 11) {
			if (typeQA != null) {
				out.put("typeQA", typeQA);
			}
			return new ModelAndView("bbsCategoryQA");
		} else {
			return new ModelAndView();
		}
	}

	/**
	 * 发表帖子
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView postReply(String categoryId,
			HttpServletRequest request, Map<String, Object> out, Integer vId)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);

		if (sessionUser == null) {
			if (categoryId != null) {
				out.put("url", "postReply_c" + categoryId + ".htm");
//				if (categoryId != null) {
//				} else {
//					out.put("url", "postReply.htm");
//				}
			} else if (vId != null) {
				out.put("url", "viewReply" + vId + ".htm");
//				if (vId != null) {
//				}
//				else {
//					out.put("url", "viewReply" + vId + ".htm");
//				}
			}else{
				out.put("url", "postReply_c" + categoryId + ".htm");
			}
			return new ModelAndView("/login");
		}
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		Integer id = DEFAULT_CATEGORY_ID;
		if (StringUtils.isNumber(categoryId)) {
			id = Integer.parseInt(categoryId);

		}

		BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
				.queryProfilerOfAccount(sessionUser.getAccount());

		out.put("bbsUserProfilerDO", bbsUserProfilerDO);

		if (categoryId != null) {
			BbsPostCategoryDO bbsPostCategoryDO = bbsService
					.queryBbsPostCategoryById(id);
			out.put("bbsPostCategoryDO", bbsPostCategoryDO);
		}
		out.put("id", "upfile");
		out.put("categoryId", categoryId);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("uploadModelPosted", BbsConst.UPLOAD_MODEL_POSTED);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));

		// seo
		SeoUtil.getInstance().buildSeo(out);

		return null;
	}

	/**
	 * 发表新帖 或者 新问题
	 * 
	 * @param bbsPostDO
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView addPostBbs(BbsPostDO bbsPostDO, String tagsName,
			Map<String, Object> out, HttpServletRequest request,HttpServletResponse response,String verifyCode,String str)
			throws IOException {
        Integer fla=Integer.valueOf(request.getParameter("fla"));
		// 从缓存中获取account信息
		SsoUser ssoUser = getCachedUser(request);
		ExtResult rs = new ExtResult();
		if (ssoUser==null) {
			return new ModelAndView("redirect:"+request.getHeader("referer"));
		}
		
		if(bbsPostDO.getContent()!=null&&bbsPostDO.getContent().contains("**")){
			bbsPostDO.setContent(bbsPostDO.getContent().replace("**", "&"));
		}
		if(bbsPostDO.getContent()!=null&&bbsPostDO.getContent().contains("~~*")){
			bbsPostDO.setContent(bbsPostDO.getContent().replace("~~*", "%"));
		}
		// 验证验证码，防止机器注册
		String vcode = String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
		SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
		if(fla!=3){
		if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode)|| !verifyCode.equalsIgnoreCase(vcode)) {
			 rs.setData("验证码错误");
			 rs.setSuccess(false);
			 return printJson(rs, out);
		}
		}
		//判断是否从问答首页提问标志
		Integer mark=0;
		if(bbsPostDO.getId()!=null&&StringUtils.isNotEmpty(bbsPostDO.getTitle())&&!StringUtils.isContainCNChar(bbsPostDO.getTitle())){
			bbsPostDO.setTitle(StringUtils.decryptUrlParameter(bbsPostDO.getTitle()));
			bbsPostDO.setContent(StringUtils.decryptUrlParameter(bbsPostDO.getContent()));
			mark=1;
		}
		if (tagsName == null) {
			tagsName = "";
		}else{
			tagsName=tagsName.replaceAll("，",",");
		}
		tagsName = TagsUtils.getInstance().arrangeTags(bbsPostDO.getTags());
		if(bbsPostDO.getBbsPostCategoryId()==null){
			bbsPostDO.setBbsPostCategoryId(2);
			BbsPostCategory categoey=bbsPostCategoryService.querySimpleCategoryById(bbsPostDO.getBbsPostAssistId());
			bbsPostDO.setCategory("社区"+","+categoey.getName());
		}
		if(bbsPostDO.getBbsPostCategoryId()==1){
			bbsPostDO.setCategory("废料问答");
		}
		if(bbsPostDO.getBbsPostCategoryId()==4){
			bbsPostDO.setBbsPostCategoryId(4);
			bbsPostDO.setCategory("商圈");
		}
		bbsPostDO.setAccount(ssoUser.getAccount());
		bbsPostDO.setCompanyId(ssoUser.getCompanyId());
		bbsPostDO.setTags(tagsName);
		Integer id = bbsPostService.createPostByUser(bbsPostDO,ssoUser.getMembershipCode());
		if (id > 0) {
			// 发表成功 创建新标签
			bbsPostTagsService.dealTags(tagsName, bbsPostDO.getBbsPostCategoryId());
			// 发表成功 添加积分
			bbsPostDO.setId(id);
			bbsScoreService.postScore(bbsPostDO);
		}
		
		// 根据发布类别（问题|帖子）选择相应列表页面
		if (1 == bbsPostDO.getBbsPostCategoryId()) {
			rs.setData("/myhuzhu/list_qa.htm");
		} else if(106 == bbsPostDO.getBbsPostCategoryId()){
			rs.setData("/myhuzhu/postSq.html");
		}else {
			rs.setData("/myhuzhu/mytiezi/posted.html");
		}
		if(mark==1){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mark", mark);
			return printJson(map, out);
		}else{
			rs.setSuccess(true);
			return printJson(rs, out);
		}
	}

	/**
	 * 登入页面
	 */
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out) {
		//页面缓存cdn
		response.addHeader( "Cache-Control", "no-cache" );
	    response.setDateHeader("Expires", 0);
		String url=request.getParameter("url");
		// 查询热点标签
		commonHead(out);
		out.put("url", url);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doLogin(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String url=request.getParameter("url");
        do{
        	SsoUser ssoUser = null;
        	if (StringUtils.isEmpty(password)) {
				out.put("error", AuthorizeException
						.getMessage(AuthorizeException.NEED_PASS));
				break;
			}
        
		try {
			ssoUser = SsoUtils.getInstance().validateUser(response, username,
					password, null, HttpUtils.getInstance().getIpAddr(request)); // HttpUtils.getInstance().getIpAddr(request)
		} 
		catch (NoSuchAlgorithmException e) {
		} catch (AuthorizeException e) {
			out.put("error", AuthorizeException.getMessage(e.getMessage()));
		} catch (IOException e) {
		}
		if (ssoUser != null) {
			//登陆信息记录
			LogUtil.getInstance().log(""+ssoUser.getCompanyId(), "login", HttpUtils.getInstance().getIpAddr(request),"type:'huzhu-login';account:"+ssoUser.getAccount()); 
			setSessionUser(request, ssoUser);
			if (StringUtils.isEmpty(url)) {
				url = AddressTool.getAddress("huzhu");
			}
			return new ModelAndView("redirect:" + url);
		}
		} while(false);
			out.put("url", url);
			out.put("error", "1");
			out.put("username", username);
			commonHead(out);
			return new ModelAndView("login");
		
	}

	/**
	 * 回复帖子
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView replyBbs(BbsPostReplyDO bbsPostReplyDO,
			String reContent, HttpServletRequest request,
			Map<String, Object> out, Integer reCompanyId)
			throws UnsupportedEncodingException {

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "replyBbs.htm");
			return new ModelAndView("/login");
		}
		if (bbsPostReplyDO.getBbsPostId() == null) {
			return new ModelAndView("redirect:" + "/index.htm");
		}
		bbsPostReplyDO.setCompanyId(sessionUser.getCompanyId());
		bbsPostReplyDO.setAccount(sessionUser.getAccount());
		String content = bbsPostReplyDO.getContent();// 主要用户发布私信的时候用
		if (StringUtils.isNotEmpty(reContent)) {
			bbsPostReplyDO.setContent(reContent + bbsPostReplyDO.getContent());
		}
		// 查询热点标签
		commonHead(out);
		indexBbsUserProfiler(bbsUserProfilerService.queryProfilerOfAccount(sessionUser.getAccount()), out, sessionUser.getAccount());
		// if (bbsPostReplyDO.getContent() != null) {
		// String safe = Jsoup.clean(bbsPostReplyDO.getContent(), Whitelist
		// .basicWithImages());
		// safe = Jsoup.parse(safe).text();
		// bbsPostReplyDO.setContent(safe);
		// }

		Integer i = bbsService.insertBbsPostReply(bbsPostReplyDO);
		if (i > 0) {
			
			// 积分计算
			bbsPostReplyDO.setId(i);
			bbsScoreService.replyScore(bbsPostReplyDO);
			
			// BBS用户不存在，添加该用户
			if (!bbsUserProfilerService.isProfilerExist(sessionUser.getAccount())) {
				bbsUserProfilerService.createEmptyProfilerByUser(sessionUser.getAccount());
			}
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
			out.put("bbsPostReplyDO", bbsPostReplyDO);
			out.put("link", "detail/" + bbsPostReplyDO.getBbsPostId() + ".html");

			// 添加私信
			if (reCompanyId != null) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryUserByCompanyId(reCompanyId);
				Inquiry inquiry = new Inquiry();
				inquiry.setTitle("你有一封来自互助社区的回复");
				inquiry.setContent("你有一封来自互助社区"
						+ sessionUser.getAccount()
						+ "的回复，内容如下：<br/>"
						+ content
						+ "更多内容详情请登录 <a href=\"http://test.zz9l.com:8080/viewReply"
						+ bbsPostReplyDO.getBbsPostId()
						+ ".htm\" style=\"color:#1e52bf\">http://test.zz9l.com:8080/viewReply"
						+ bbsPostReplyDO.getBbsPostId() + ".htm</a>");
				inquiry.setReceiverAccount(profiler.getAccount());// 接收者
				inquiry.setBeInquiredType("5");// 针对个人
				inquiry.setSenderAccount("0"); // 系统发送者为0
				inquiry.setBeInquiredId(profiler.getCompanyId());// 对象者id为默认id
				inquiryService.inquiryByUser(inquiry,
						sessionUser.getCompanyId());

			}

			return null;
		} else {
			return new ModelAndView("/common/error");
		}

	}

	/**
	 * bbs我的帖子回复
	 * 
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping
	public ModelAndView oldmyreply(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {

			out.put("url", "myalterimg.htm");
			return new ModelAndView("/login");
		}
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		// if (p == null || !StringUtils.isNumber(p)) {
		// p = "1";
		// }

		// 查询热点标签
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("countMyposted", bbsPostService.countMyposted(
				sessionUser.getAccount(), null, null, null));
		page = bbsPostReplyService.pageReplyByUser(sessionUser.getAccount(),
				null, null, page);
		out.put("page", page);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("url", "myreply.htm");
		out.put("post", "post");
		return null;
	}
	@RequestMapping
	public ModelAndView myreply(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostReplyDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {

			out.put("url", "myalterimg.htm");
			return new ModelAndView("/login");
		}
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		// 查询热点标签
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("countMyposted", bbsPostService.countMyposted(
				sessionUser.getAccount(), null, null, null));
		page = bbsPostReplyService.pageReplyByAccount(sessionUser.getAccount(),
				null, null, page);
		out.put("page", page);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("url", "myreply.htm");
		out.put("post", "post");
		return null;
	}

	/**
	 * 他人发表的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView othersposted(Integer id, PageDto<BbsPostDO> page,
			HttpServletRequest request, Map<String, Object> out, String type)
			throws UnsupportedEncodingException {
		// 他人账号
		if (id == null) {
			return new ModelAndView("/common/error");
		}

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "othersposted" + id + ".htm");
			return new ModelAndView("/login");
		}
		BbsUserProfilerDO bbsUser = bbsService.queryBbsUserProfilerById(id);
		out.put("bbsUser", bbsUser);
		// 谁回复我的帖子
		if (bbsUser != null) {
			List<BbsPostDTO> bbsReplyList = bbsService
					.queryUserNicknameByReply(bbsUser.getAccount(),
							BbsConst.DEFAULT_START_INDEX,
							BbsConst.DEFAULT_SIZE,
							BbsConst.DEFAULT_POST_MODIFIED_SORT,
							BbsConst.DEFAULT_POST_DIR);
			out.put("bbsReplyList", bbsReplyList);
		}

		if ("2".equals(type)) {
			page = bbsPostService.pagePostByUser(bbsUser.getAccount(), "1",
					"0", 11, null, null, page);
		} else {
			page = bbsPostService.pagePostByUser(bbsUser.getAccount(), "1",
					"0", null, null, null, page);
		}
		out.put("page", page);

		out.put("categoryMap", BbsService.BBS_POST_CATEGORY_MAP);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("id", id);
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("url", "othersposted" + id + ".htm");
		out.put("type", type);

		// 设置页面头部信息 seo
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle("再生互助_废料生意人的交流平台_ZZ91再生网");
		headDTO.setPageKeywords("再生互助,互助,交流,技术,问题");
		headDTO.setPageDescription("的再生互助,旨在为广大用户提供一个再生废料领域内的商务交流平台和技术沟通的展台，再生互助促使会员进行积极的沟通交流,共同解决日常生产、贸易过程中碰到的各类技术性和商务性问题。");
		setPageHead(headDTO, out);

		// 查询热点标签
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));

		return null;
	}

	/**
	 * 他人回复的帖子
	 * 
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping
	public ModelAndView othersreply(Integer id, PageDto<BbsPostDO> page,
			HttpServletRequest request, Map<String, Object> out, String type)
			throws UnsupportedEncodingException {
		// 他人账号
		if (id == null) {
			return new ModelAndView("/common/error");
		}
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "othersreply.htm");
			return new ModelAndView("/login");
		}
		BbsUserProfilerDO bbsUser = bbsService.queryBbsUserProfilerById(id);
		out.put("bbsUser", bbsUser);
		// 谁回复我的帖子
		if (bbsUser != null) {
			List<BbsPostDTO> bbsReplyList = bbsService
					.queryUserNicknameByReply(bbsUser.getAccount(),
							BbsConst.DEFAULT_START_INDEX,
							BbsConst.DEFAULT_SIZE,
							BbsConst.DEFAULT_POST_MODIFIED_SORT,
							BbsConst.DEFAULT_POST_DIR);
			out.put("bbsReplyList", bbsReplyList);
		}

		if ("2".equals(type)) {
			page = bbsPostReplyService.pageReplyByUser(bbsUser.getAccount(),
					"1", 11, page);
		} else {
			page = bbsPostReplyService.pageReplyByUser(bbsUser.getAccount(),
					"1", null, page);
		}
		out.put("page", page);

		out.put("categoryMap", BbsService.BBS_POST_CATEGORY_MAP);

		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("id", id);
		out.put("url", "othersreply" + id + ".htm");
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("type", type);

		// 设置页面头部信息 seo
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle("再生互助_废料生意人的交流平台_ZZ91再生网");
		headDTO.setPageKeywords("再生互助,互助,交流,技术,问题");
		headDTO.setPageDescription("的再生互助,旨在为广大用户提供一个再生废料领域内的商务交流平台和技术沟通的展台，再生互助促使会员进行积极的沟通交流,共同解决日常生产、贸易过程中碰到的各类技术性和商务性问题。");
		setPageHead(headDTO, out);

		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		return null;
	}

	/**
	 * 编辑帖子：初始化
	 * 
	 * @param postId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "postEdit.htm", method = RequestMethod.GET)
	public ModelAndView postEdit(Integer postId, HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {

			return printJs("alert('对不起，您还没有登录！')", out);
		}
		BbsPostDO bbsPostDO = bbsPostService.queryPostById(postId);
		if (!sessionUser.getAccount().equals(bbsPostDO.getAccount())) {
			return printJs("alert('对不起，您无权修改此帖！')", out);
		} else {
			out.put("bbsPostDO", bbsPostDO);
			out.put("resourceUrl", (String) MemcachedUtils.getInstance()
					.getClient().get("baseConfig.resource_url"));
			return null;
		}
	}

	/**
	 * 编辑帖子
	 * 
	 * @param postId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "postEdit.htm", method = RequestMethod.POST)
	public ModelAndView postEdit(BbsPostDO bbsPostDO,
			HttpServletRequest request, Map<String, Object> out)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		Integer i = bbsPostService.updatePostByUser(bbsPostDO,sessionUser.getMembershipCode());
		if ("10051000".equals(sessionUser.getMembershipCode())) {
			bbsPostService.updateCheckStatusForFront(bbsPostDO.getId(), "0");
		}

		// 查询客户会员类型
		if (i != null && i.intValue() > 0) {
			
			// 修改帖子积分变动
			BbsPostDO bbsPost = bbsPostService.queryPostById(bbsPostDO.getId());
			bbsScoreService.postScore(bbsPost);
			
			return printJs("alert('修改成功！');parent.onHideBox();parent.location.reload();",out);
		} else {
			return printJs("alert('修改失败！');parent.document.getElementById(\"close\").click();",out);
		}
	}
	/**
	 * 编辑回帖
	 * @param replyId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "replyEdit.htm", method = RequestMethod.GET)
	public ModelAndView replyEdit(Integer replyId, HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		if(sessionUser==null){
			return printJs("alert('对不起，您还没有登录！')", out);
		}
		// 从缓存中获取account信息
		BbsPostReplyDO bbsPostReplyDO=bbsPostReplyService.queryById(replyId);
		if (!sessionUser.getAccount().equals(bbsPostReplyDO.getAccount())) {
			return printJs("alert('对不起，您无权修改此帖！')", out);
		}
		else{
			out.put("bbsPostReplyDo", bbsPostReplyDO);
			out.put("resourceUrl", (String) MemcachedUtils.getInstance()
					.getClient().get("baseConfig.resource_url"));
			return null;
		}
	}
	@RequestMapping(value = "replyEdit.htm", method = RequestMethod.POST)
	public ModelAndView replyEdit(BbsPostReplyDO bbsPostReplyDO,
			HttpServletRequest request, Map<String, Object> out)
			throws IOException {
		SsoUser sessionUser=getCachedUser(request);
		Integer i=bbsPostReplyService.updateReplyByUser(bbsPostReplyDO, sessionUser.getMembershipCode());
		if (i != null && i.intValue() > 0) {
			return printJs("alert('修改成功！');parent.onHideBox();parent.location.reload();",out);
		} else {
			return printJs("alert('修改失败！');parent.document.getElementById(\"close\").click();",out);
		}
		
	}
	/**
	 * 删除帖子
	 * 
	 * @param postId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "postDel.htm", method = RequestMethod.GET)
	public ModelAndView postDel(Integer postId, HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return printJs("alert('对不起，您还没有登录！');window.location.href='/login.htm';",out);
		} else {
			BbsPostDO bbsPostDO = bbsPostService.queryPostById(postId);
			// 判断问答还是帖子
			String tagTitle = "帖子";
			String url = "/myhuzhu/mytiezi/posted.html";
			if (bbsPostDO.getBbsPostCategoryId() == 1) {
				tagTitle = "问答";
				url = "/myhuzhu/mywenda/posted.html";
			}else if(bbsPostDO.getBbsPostCategoryId() == 106){
				tagTitle = "商圈";
				url = "/myhuzhu/postSq.html";
			}
			if (!sessionUser.getAccount().equals(bbsPostDO.getAccount())) {
				return printJs("alert('对不起，您无权删除此" + tagTitle + "！');window.location.href='" + url + "';", out);
			} else {
				bbsUserProfilerService.updateBbsReplyCount(sessionUser.getAccount());
//				Integer im = bbsPostService.deleteBbsPost(postId);  真删除
				Integer im = bbsPostService.updateIsDel(postId, "1"); // 假删除
				if (im.intValue() > 0) {
					// 删除成功扣除积分
					bbsScoreService.delPost(postId, 1);
					
					bbsUserProfilerService.updatePostNumber(sessionUser.getAccount());
					return printJs("alert('" + tagTitle + "已成功删除！');window.location.href='" + url + "';",out);
				} else {
					return printJs("alert('删除失败！');window.location.href='" + url + "';", out);
				}
			}
		}
	}
	/**
	 * 删除回帖
	 * @param replyId
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "replyDel.htm", method = RequestMethod.GET)
	public ModelAndView replyDel(Integer replyId, HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return printJs("alert('对不起，您还没有登录！');window.location.href='/login.htm';",out);
		} else {
			BbsPostReplyDO bbsPostReplyDO=bbsPostReplyService.queryById(replyId);
			BbsPostDO bbsPostDO = bbsPostService.queryPostById(bbsPostReplyDO.getBbsPostId());
			// 判断问答还是帖子
			String tagTitle = "帖子";
			String url = "/myreply.htm";
			if (bbsPostDO.getBbsPostCategoryId() == 11) {
				tagTitle = "问答";
				url = "/myhuzhu/list_qa.htm";
			}
			if (!sessionUser.getAccount().equals(bbsPostReplyDO.getAccount())) {
				return printJs("alert('对不起，您无权删除此" + tagTitle + "！');window.location.href='" + url + "';", out);
			} else {
				bbsUserProfilerService.updateBbsReplyCount(sessionUser.getAccount());
//				Integer im = bbsPostService.deleteBbsPost(postId);  真删除
				Integer im = bbsPostReplyService.updateIsDel(replyId, "1"); // 假删除
				if (im.intValue() > 0) {
					// 删除成功扣除积分
					bbsScoreService.delreply(replyId, 1);
					
					bbsUserProfilerService.updatePostNumber(sessionUser.getAccount());
					return printJs("alert('" + tagTitle + "已成功删除！');window.location.href='" + url + "';",out);
				} else {
					return printJs("alert('删除失败！');window.location.href='" + url + "';", out);
				}
			}
		}
	}

	/**
	 * 图片上传页面初始化
	 * 
	 * @param out
	 * @param model
	 *            所属模块，如：bbs，ads，news
	 * @param filetype
	 *            文件类型，如：img，doc，zip
	 */
	@RequestMapping
	public void ajaxUpload(Map<String, Object> out, String model,
			String filetype) {

		out.put("filetype", filetype);
		out.put("model", model);
	}

	/**
	 * 
	 * @param out
	 * @param id
	 *            被举报信息ID
	 * @param reportsDO
	 * @param type
	 *            举报类型 （0代表举报帖子，1代表举报留言）
	 * @param account
	 *            举报人帐号
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping
	public void reports(Map<String, Object> out, Integer id,
			ReportsDO reportsDO, String type, HttpServletRequest request)
			throws IOException {
		out.put("id", id);
		out.put("account", getCachedUser(request).getAccount());

		out.put("type", type);
	}

	/**
	 * 提交举报信息
	 */
	@RequestMapping
	public ModelAndView reports_sub(ReportsDO reportsDO,
			Map<String, Object> out, HttpServletRequest request)
			throws IOException {
		reportsDO.setIp(HttpUtils.getInstance().getIpAddr(request));
		Integer i = reportsService.insertReportsDO(reportsDO);
		if (i > 0) {
			return printJs(
					"alert('举报成功，谢谢你的参与，我们将会尽快核实和处理！');parent.location.reload();",
					out);
		} else {
			return printJs("alert('举报失败！');history.back();", out);
		}
	}

	@RequestMapping
	public void other(Map<String, Object> out, HttpServletRequest request) {
		// 查询热点标签
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));
		// 判断是否登入
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}

	}

	/**
	 * 我的互助社区
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void indexBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO,
			Map<String, Object> out, String loginedAccount)
			throws UnsupportedEncodingException {
		out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		// 谁回复我的帖子
		List<BbsPostDTO> bbsReplyList = bbsService.queryUserNicknameByReply(
				loginedAccount, BbsConst.DEFAULT_START_INDEX,
				BbsConst.DEFAULT_SIZE, BbsConst.DEFAULT_POST_MODIFIED_SORT,
				BbsConst.DEFAULT_POST_DIR);
		out.put("countReply",
				bbsPostReplyService.countMyreply(loginedAccount, null, null));
		out.put("countPosted",
				bbsPostService.countMyposted(loginedAccount, null, null, null));
		out.put("bbsReplyList", bbsReplyList);
		out.put("bbsReplyCount", bbsReplyList.size());
	}

	/**
	 * 
	 * @param searchTit
	 *            搜索关键字
	 * @param searchSorts
	 *            搜索类型 0>帖子 1>社区 2>会员
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView search(Map<String, Object> out,
			HttpServletRequest request) {
		//避免XSS攻击
		PageDto<PostDto> page=new PageDto<PostDto>();
		String keywords=request.getParameter("keywords");
		//分页的页面记录开始索引
		String startIndexString=request.getParameter("startIndex");
		if(StringUtils.isNotEmpty(startIndexString)){
			Integer startIndex=Integer.valueOf(startIndexString);
			page.setStartIndex(startIndex);
		}
		//分页的页面size
		String pageSizeString=request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSizeString)){
			Integer pageSize=Integer.valueOf(pageSizeString);
			page.setStartIndex(pageSize);
		}
		
		if (StringUtils.isEmpty(keywords)) {
			return null;
		}

		try {
			keywords = StringUtils.decryptUrlParameter(keywords);
			keywords=keywords.replaceAll("[\\pP\\p{Punct}]", " ");
			out.put("keywords", keywords);
			out.put("encodeKeywords", URLEncoder.encode(keywords, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
		//page = bbsPostService.pagePostBySearchEngine(keywords, page);
		// /如果page查询结果为空，
		if (page.getRecords() == null && "最新话题".equals(keywords)) {
			page = bbsPostService.pageTheNewsPost(16, page);
		}
		if ("互助周报".equals(keywords)) {
			page.setPageSize(16);
			page = bbsPostService.pageMorePostByType("7", page);
		}
		if (page!= null&&page.getRecords()!=null) {
			for (PostDto obj : page.getRecords()) {
				obj.getPost().setContent(
						Jsoup.clean(obj.getPost().getContent(),
								Whitelist.none()));
			}
		}
		out.put("page", page);

		// 查询热点标签
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {

			// 个人基本信息
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}

		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.BBS);
		headDTO.setPageTitle(keywords + ",互助社区_废料论坛_废料生意人的交流平台_第" + currentpage
				+ "页_ZZ91再生网");
		headDTO.setPageKeywords(keywords + ",再生互助,互助社区,废料论坛,互助月刊," + keywords
				+ "问题,商业防骗,废料再生技术");
		headDTO.setPageDescription("ZZ91再生网旗下再生互助社区，在这里你可以找到废料再生技术、"
				+ "废料贸易问题解答、商业防骗技术手段。每月发布互助月刊，报道每月热点关注的废料信息。");

		setPageHead(headDTO, out);
		return null;
	}

	/**
	 * 根据post_type 检索列表 分页 周报、月刊
	 */
	@RequestMapping
	public ModelAndView list(Map<String, Object> out,
			HttpServletRequest request, String postType, PageDto<PostDto> page) {

		page = bbsPostService.pageMorePostByType(postType, page);
		// /如果page查询结果为空
		for (PostDto obj : page.getRecords()) {
			obj.getPost().setContent(
					Jsoup.clean(obj.getPost().getContent(), Whitelist.none()));
		}
		out.put("page", page);

		// 查询热点标签
		commonHead(out);
		initBaseInfo(out, getCachedUser(request));

		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {

			// 个人基本信息
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService
					.queryProfilerOfAccount(sessionUser.getAccount());
			out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		}
		String keywords = bbsPostTypeService.queryNameById(postType);
		int currentpage = page.getStartIndex() / page.getPageSize() + 1;
		SeoUtil.getInstance().buildSeo("list",
				new String[] { keywords, String.valueOf(currentpage) },
				new String[] { keywords }, new String[] {}, out);
		out.put("postType", postType);

		return null;
	}

	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}

	// 互助最终页
	@RequestMapping
	public ModelAndView viewReply(HttpServletRequest request,
			Map<String, Object> out, Integer postId, Integer companyId,
			PageDto<BbsPostReplyDto> page) throws UnsupportedEncodingException {

		do {
			if (postId == null) {
				break;
			}
			out.put("companyId", companyId);
			// 根据id查询
			BbsPostDO bbsPostDO = bbsPostService.queryPostById(postId);
			if (bbsPostDO == null||"1".equals(bbsPostDO.getIsDel())) {
				break;
			}
			if ("11".equals(bbsPostDO.getPostType())) {
				String url = AddressTool.getAddress("mingxing") + "/detail"
						+ postId + "-t.htm";
				return new ModelAndView("redirect:" + url);
			}
			// 登入者的信息
			SsoUser sessionUser = getCachedUser(request);
			Integer integral = null;
			if (sessionUser != null) {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryProfilerOfAccount(sessionUser.getAccount());
				integral = profiler.getIntegral();
				out.put("bbsUserProfilerDO", profiler);
				bbsService.countBbsInfo(out, sessionUser.getAccount(),null);
			}

			if (bbsPostDO.getIntegral() > 0) {
				if (sessionUser == null) {
					return new ModelAndView("/login");
				}

				if (!bbsPostDO.getAccount().equals(sessionUser.getAccount())) {
					if (integral == null) {
						integral = bbsUserProfilerService
								.queryIntegralByAccount(sessionUser
										.getAccount());
					}
					if (integral.intValue() < bbsPostDO.getIntegral()
							.intValue()) {
						break;
					}
				}
			}
			// 导航
			commonHead(out);

			if (bbsPostDO != null) {
				BbsUserProfilerDO bProfiler = bbsUserProfilerService
						.queryProfilerOfAccount(bbsPostDO.getAccount());
				// 获取楼主的所在公司的联系人
				CompanyAccount contact = companyAccountService
						.queryAccountByAccount(bProfiler.getAccount());
				out.put("contact", contact);

				// 楼主信息
				bProfiler.setAccount(URLEncoder.encode(bProfiler.getAccount(),
						HttpUtils.CHARSET_UTF8));
				if(StringUtils.isNotEmpty(bProfiler.getNickname())){
					if(StringUtils.isEmail(bProfiler.getNickname())||StringUtils.isNumber(bProfiler.getNickname())){
							if(bProfiler.getNickname().length()>7){
								bProfiler.setNickname(bProfiler.getNickname().substring(0,7));
							}
				    } 
				}
				out.put("bProfiler", bProfiler);
				if (bProfiler.getCompanyId() != null
						&& bProfiler.getCompanyId().intValue() > 0) {
					String membershipCode = companyService
							.queryMembershipOfCompany(bProfiler.getCompanyId());
					out.put("membershipCode", membershipCode);
					Company company = companyService
							.queryDomainOfCompany(bProfiler.getCompanyId());
					out.put("company", company);
					if (StringUtils.isNotEmpty(membershipCode)
							&& membershipCode.equals("10051000")) {
						ProductsDO productsDO = productsService
								.queryProductsByCidForLatest(
										bProfiler.getCompanyId(), null);
						out.put("productsDO", productsDO);
					}

				}
			}

			if (bbsPostDO.getCheckStatus() == null
					|| "0".equals(bbsPostDO.getCheckStatus())
					|| "3".equals(bbsPostDO.getCheckStatus())) {
				break;
			}

			Document doc = Jsoup.parse(bbsPostDO.getContent());
			bbsPostDO.setContent(doc.select("body").toString());
			out.put("bbsPostDO", bbsPostDO);
			out.put("postAccount", URLEncoder.encode(bbsPostDO.getAccount(),HttpUtils.CHARSET_UTF8));

			if (bbsPostDO.getBbsPostCategoryId() == null) {
				bbsPostDO.setBbsPostCategoryId(1);
			}

			
			BbsPostCategoryDO category = bbsService.queryBbsPostCategoryById(bbsPostDO.getBbsPostCategoryId());
			if (category==null) {
				category = bbsService.queryBbsPostCategoryById(1);
			}
			out.put("bbsPostCategory", category);

			page.setPageSize(5);
			Integer currentpage = page.getStartIndex() / page.getPageSize() + 1;
			// 设置头部信息
			PageHeadDTO headDTO = new PageHeadDTO();
			headDTO.setPageTitle(bbsPostDO.getTitle()
					+ "_"
					+ category.getName()
					+ "_互助社区_第"
					+ currentpage
					+ "页"
					+ ParamUtils.getInstance().getValue("site_info_front",
							"site_name"));
			headDTO.setPageKeywords(bbsPostDO.getTitle() + ","
					+ category.getName());
			if (doc.select("body").text().length() > 100) {
				headDTO.setPageDescription(doc.select("body").text()
						.substring(0, 100));
			} else {
				headDTO.setPageDescription(doc.select("body").text());
			}
			setPageHead(headDTO, out);

			Map<String, String> maps = TagsUtils.getInstance().encodeTags(
					bbsPostDO.getTags(), "utf-8");
			out.put("bbsTagsDOList",
					TagsUtils.getInstance().encodeTags(bbsPostDO.getTags(),
							"utf-8"));
			// 查询出相关帖子
			String tags = "";
			if (maps.keySet().size() > 0) {
				for (String string : maps.keySet()) {
					if (tags.length() > 0) {
						break;
					} else {
						tags += string;
					}
				}
			}
			// 根据帖子查询
			if (tags.length() > 0) {
				PageDto<PostDto> postPage = new PageDto<PostDto>();
				//postPage = bbsPostService.pagePostBySearchEngine(tags, postPage);
				if (postPage.getRecords().size() == 12) {
					out.put("postPage", postPage);
				} else {
					// put list的id防止重复
					Set<Integer> idSet = new HashSet<Integer>();
					List<PostDto> list = postPage.getRecords();
					for (PostDto obj : list) {
						idSet.add(obj.getPost().getId());
					}
					// 重新检索
					PageDto<PostDto> postPages = new PageDto<PostDto>();
					postPages.setDir("desc");
					if (postPages.getSort() == null) {
						postPages.setSort("reply_time");
					}
					postPages = bbsPostService.pagePostByCategory(
							category.getId(), null, postPages);

					// 判断是否重复补充list 到12条为止
					for (PostDto obj : postPages.getRecords()) {
						if (idSet.contains(obj.getPost().getId())) {
							continue;
						}
						list.add(obj);
					}
					postPages.setRecords(list);
					out.put("postPage", postPages);
				}
			} else {
				PageDto<PostDto> postPage = new PageDto<PostDto>();
				if (postPage.getSort() == null) {
					postPage.setSort("reply_time");
				}
				postPage.setDir("desc");
				postPage = bbsPostService.pagePostByCategory(category.getId(),
						null, postPage);
				out.put("postPage", postPage);
			}

			// 判断等级
			String memberShip = companyService
					.queryMembershipOfCompany(bbsPostDO.getCompanyId());
			out.put("memberShip", memberShip);

			if (StringUtils.isNotEmpty(bbsPostDO.getTags())) {
				if (bbsPostDO.getTags().indexOf(",") > 0) {
					String tag[] = bbsPostDO.getTags().split(",");
					out.put("theKeyword",
							URLEncoder.encode(tag[1].toString(), "utf-8"));
				}
			}

			bbsService.updateBbsPostVisitedCount(postId);
			// 从缓存中获取account信息
			List<BbsSignDO> bbsSignDOList = bbsService.queryBbsSignByAccount(
					bbsPostDO.getAccount(), 0, 1,
					BbsConst.DEFAULT_MODIFIED_TIME_SORT,
					BbsConst.DEFAULT_POST_DIR);
			out.put("bbsSignDOList", bbsSignDOList);

			String iconTemplate = "<img src=\"http://img0.zz91.com/bbs/images/bbs/{0}.jpg\" />";

			// 判断是否问答帖子
			String url = "";
			if (11 == bbsPostDO.getBbsPostCategoryId()) {
				url = "viewQA";
			} else {
				page.setDir("asc");
			}

			page = bbsPostReplyService.pageReplyOfPost(postId, companyId,iconTemplate, page);

			Set<String> accountSet = new HashSet<String>();
			for (BbsPostReplyDto dto : page.getRecords()) {
				if(StringUtils.isNotEmpty(dto.getProfiler().getNickname())){
					if(StringUtils.isEmail(dto.getProfiler().getNickname())||StringUtils.isNumber(dto.getProfiler().getNickname())){
							if(dto.getProfiler().getNickname().length()>7){
								dto.getProfiler().setNickname(dto.getProfiler().getNickname().substring(0,7));
							}
				    } 
				}
				accountSet.add(dto.getReply().getAccount());
				// 获取一下回答问题的联系人
				CompanyAccount ca = companyAccountService.queryAccountByAccount(dto.getReply().getAccount());
				if(ca==null){
					continue;
				}
				dto.setCompanyAccount(ca);
				// 获取一下发贴的用户的注册时间
				Company company = companyService.queryCompanyById(ca.getCompanyId());
				dto.setCompany(company);
			}
			Map<String, List<BbsPostDO>> recentPost = bbsPostService
					.queryRecentPostOfAccounts(accountSet);
			out.put("page", page);
			out.put("recentPost", recentPost);

			bbsViewLogService.viewLog(bbsPostDO.getId());
			if (StringUtils.isNotEmpty(url)) {
				return new ModelAndView(url);
			} else {
				return new ModelAndView();
			}

		} while (true);
		out.put("errorsign", 1);
		return new ModelAndView("/common/error");

	}

	/**
	 * bbs我的头像
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myalterimg(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "myalterimg.htm");
			return new ModelAndView("/login");
		}
		// 公告
		PageDto<DataIndexDO> pages = new PageDto<DataIndexDO>();
		DataIndexDO index = new DataIndexDO();
		index.setCategoryCode("10041006");
		pages.setPageSize(4);
		pages = dataIndexService.pageDataIndex(index, pages);
		out.put("page", pages);

		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		out.put("info", "info");
		return null;
	}

	/**
	 * bbs我的基本信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView myalterinfo(HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面
		if (sessionUser == null) {
			out.put("url", "myalterinfo.htm");
			return new ModelAndView("/login");
		}

		// 公告
		PageDto<DataIndexDO> pages = new PageDto<DataIndexDO>();
		DataIndexDO index = new DataIndexDO();
		index.setCategoryCode("10041006");
		pages.setPageSize(4);
		pages = dataIndexService.pageDataIndex(index, pages);
		out.put("page", pages);

		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		out.put("info", "info");
		return null;
	}

	/**
	 * bbs我的企业标签
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView mysign(String p, HttpServletRequest request,
			Map<String, Object> out, PageDto<BbsPostDO> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息

		SsoUser sessionUser = getCachedUser(request);

		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "mysign.htm");
			return new ModelAndView("/login");
		}
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		commonHead(out);

		if (p == null || !StringUtils.isNumber(p)) {
			p = "1";
		}

		// 公告
		PageDto<DataIndexDO> pages = new PageDto<DataIndexDO>();
		DataIndexDO index = new DataIndexDO();
		index.setCategoryCode("10041006");
		pages.setPageSize(4);
		pages = dataIndexService.pageDataIndex(index, pages);
		out.put("page", pages);

		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		Integer startIndex = (Integer.valueOf(p) - 1)
				* BbsConst.DEFAULT_POST_MY_SIZE;

		Integer totalRecords = bbsService
				.queryBbsSignByAccountCount(sessionUser.getAccount());
		List<BbsSignDO> bbsSignDOList = bbsService.queryBbsSignByAccount(
				sessionUser.getAccount(), startIndex,
				BbsConst.DEFAULT_POST_MY_SIZE,

				BbsConst.DEFAULT_MODIFIED_TIME_SORT, BbsConst.DEFAULT_POST_DIR);
		for (BbsSignDO o : bbsSignDOList) {
			o.setSignName(StringUtils.controlLength(o.getSignName(), 100));
		}
		// 计算总page大小

		Integer totalPages = totalRecords / BbsConst.DEFAULT_POST_MY_SIZE + 1;
		if (totalRecords % BbsConst.DEFAULT_POST_MY_SIZE == 0) {
			totalPages--;
		}
		out.put("totalPages", totalPages);
		out.put("currentPage", Integer.valueOf(p));
		out.put("bbsSignDOList", bbsSignDOList);
		if (bbsSignDOList.size() > 0) {
			if (StringUtils.isNotEmpty(bbsSignDOList.get(0).getSignName())) {
				out.put("signName", bbsSignDOList.get(0).getSignName());
			}
		}
		out.put("uploadModel", BbsConst.UPLOAD_MODEL_TOUXIANG);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
		out.put("info", "info");
		return null;
	}

	/***
	 * 私信列表页 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView message(HttpServletRequest request,
			Map<String, Object> out, PageDto<InquiryDto> page, String isViewed)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "message.htm");
			return new ModelAndView("/login");
		}
		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		// 收件箱
		Inquiry inquiry = new Inquiry();
		inquiry.setIsReceiverDel("0");
		if (StringUtils.isNotEmpty(isViewed)) {
			inquiry.setIsViewed(isViewed);
		}
		inquiry.setReceiverAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");
		page.setPageSize(10);
		page = inquiryService.pageInquiryByUser(inquiry, page);
		for (InquiryDto InquiryDto : page.getRecords()) {
			// 根据信息查查询出收件人
			if (InquiryDto.getInquiry().getSenderAccount().equals("0")) {
				InquiryDto.getInquiry().setSenderAccount("互助社区");
			} else {
				BbsUserProfilerDO profiler = bbsUserProfilerService
						.queryProfilerOfAccount(InquiryDto.getInquiry()
								.getSenderAccount());
				if (StringUtils.isNotEmpty(profiler.getNickname())) {
					InquiryDto.getInquiry().setSenderAccount(
							profiler.getNickname());
				} else {
					continue;
				}
			}
		}
		out.put("page", page);
		out.put("message", "message");

		return null;
	}

	/***
	 * 发件箱列表页 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView mySendMessage(HttpServletRequest request,
			Map<String, Object> out, PageDto<InquiryDto> page)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		// 为空返回登入页面`
		if (sessionUser == null) {
			out.put("url", "message.htm");
			return new ModelAndView("/login");
		}
		bbsService.countBbsInfo(out, sessionUser.getAccount(),null);
		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		// 发件箱
		Inquiry inquiry = new Inquiry();
		inquiry.setIsSenderDel("0");
		inquiry.setSenderAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");
		page.setPageSize(15);
		page = inquiryService.pageInquiryByUser(inquiry, page);
		for (InquiryDto InquiryDto : page.getRecords()) {
			// 根据信息查查询出发件人
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(InquiryDto.getInquiry()
							.getReceiverAccount());
			if (StringUtils.isNotEmpty(profiler.getNickname())) {
				InquiryDto.getInquiry().setReceiverAccount(
						profiler.getNickname());
			} else {
				continue;
			}

		}
		out.put("page", page);
		out.put("message", "message");
		return null;
	}

	/***
	 * 删除发件箱 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView removeSentInquiry(String ids, Map<String, Object> model)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer impact = 0;
		Integer[] idArr = StringUtils.StringToIntegerArray(ids);
		for (Integer id : idArr) {
			inquiryService.removeSentInquiry(id, true);
			impact++;
		}
		if (impact.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	/***
	 * 删除收件箱 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView removeReceiveInquiry(String ids,
			Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		Integer impact = 0;
		Integer[] idArr = StringUtils.StringToIntegerArray(ids);
		for (Integer id : idArr) {
			inquiryService.removeReceivedInquiry(id, true);
			impact++;
		}
		if (impact.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	/***
	 * 私信列表页 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView sendMessage(HttpServletRequest request,
			Map<String, Object> out, Integer postId, String error,
			Integer companyId) throws UnsupportedEncodingException {
		if (error != null) {
			out.put("error", error);
		}
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		// 为空返回登入页面`
		if (sessionUser == null) {
			if (postId != null) {
				out.put("url", "sendMessage" + postId + ".htm");
			} else {
				out.put("url", "sendMessage.htm");
			}
			return new ModelAndView("/login");
		}

		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		if (postId != null) {
			// 读取文章内容
			BbsPostDO bbsPost = bbsPostService.queryPostById(postId);
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(bbsPost.getAccount());
			out.put("profiler", profiler);
			out.put("bbsPost", bbsPost);
		} else if (companyId != null) {
			companyAccountService.queryAdminAccountByCompanyId(companyId);
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryUserByCompanyId(companyId);
			out.put("profiler", profiler);
		}

		out.put("message", "message");
		return null;
	}

	/***
	 * 私信列表页 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView doSendMessage(HttpServletRequest request,
			Map<String, Object> out, String accountName, Inquiry inquiry,
			Integer postId) throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return new ModelAndView("/login");
		}
		Integer userCount = bbsUserProfilerService
				.countUserProfilerByAccount(accountName);
		if (userCount == null || userCount == 0) {
			if (postId != null) {
				out.put("postId", postId);
				out.put("error", "1");
				return new ModelAndView("redirect:/sendMessage" + postId
						+ ".htm");
			} else {
				out.put("error", "1");
				return new ModelAndView("redirect:/sendMessage.htm");
			}
		}
		// 根据名字查询出bbsUserPost;
		BbsUserProfilerDO bbsUserProfiler = bbsUserProfilerService
				.queryUserByAccount(accountName);
		inquiry.setReceiverAccount(bbsUserProfiler.getAccount());// 接收者
		inquiry.setBeInquiredType("5");// 针对个人
		inquiry.setSenderAccount(sessionUser.getAccount()); // 发送者
		inquiry.setBeInquiredId(bbsUserProfiler.getCompanyId());// 对象者id为默认id
		Integer i = inquiryService.inquiryByUser(inquiry,
				sessionUser.getCompanyId());
		if (i > 0) {
			return new ModelAndView("redirect:/mySendMessage.htm");
		}

		return new ModelAndView("redirect:/mySendMessage.htm");
	}

	/***
	 * 私信最终页 *
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView showmessage(HttpServletRequest request,
			Map<String, Object> out, Integer id)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			out.put("url", "showmessage" + id + ".htm");
			return new ModelAndView("/login");
		}
		out.put("id", id);

		initBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());
		bbsService.countBbsInfo(out, sessionUser.getAccount(),null);

		commonHead(out);
		indexBbsUserProfiler(
				bbsUserProfilerService.queryProfilerOfAccount(sessionUser
						.getAccount()), out, sessionUser.getAccount());

		Inquiry inquiry = inquiryService.queryOneInquiry(id);
		if (sessionUser.getAccount().equals(inquiry.getReceiverAccount())) {
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(inquiry.getSenderAccount());
			out.put("profiler", profiler);
		} else {
			BbsUserProfilerDO profiler = bbsUserProfilerService
					.queryProfilerOfAccount(inquiry.getReceiverAccount());
			out.put("profiler", profiler);
		}

		out.put("inquiry", inquiry);
		List<InquiryDto> inquiryList = inquiryService.queryConversation(inquiry
				.getConversationGroup());
		for (InquiryDto inquiryDto : inquiryList) {
			BbsUserProfilerDO profilerDo = bbsUserProfilerService
					.queryProfilerOfAccount(inquiryDto.getInquiry()
							.getSenderAccount());
			if (profilerDo == null) {
				continue;
			}
			inquiryDto.setProfiler(profilerDo);

		}
		out.put("inquiryList", inquiryList);
		if (StringUtils.isEmpty(inquiry.getIsViewed())
				|| "0".equals(inquiry.getIsViewed())) {
			inquiryService.makeAsViewed(id, true);
		}
		out.put("message", "message");

		// 收件箱
		if (sessionUser.getAccount().equals(inquiry.getReceiverAccount())) {
			// 收件箱上一篇
			Inquiry onInquiry = inquiryService.queryOnMessageById(id,
					sessionUser.getAccount(), null, "5");
			out.put("onInquiry", onInquiry);
			// 收件箱下一篇
			Inquiry downInquiry = inquiryService.queryDownMessageById(id,
					sessionUser.getAccount(), null, "5");
			out.put("downInquiry", downInquiry);
		} else {
			// 发件箱上一篇
			Inquiry onInquiry = inquiryService.queryOnMessageById(id, null,
					sessionUser.getAccount(), "5");
			out.put("onInquiry", onInquiry);
			// 发件箱下一篇
			Inquiry downInquiry = inquiryService.queryDownMessageById(id, null,
					sessionUser.getAccount(), "5");
			out.put("downInquiry", downInquiry);
			out.put("showSent", "showSent");
		}

		return null;

	}

	/***
	 * 回复私信 *
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping
	public ModelAndView doReSendMessage(HttpServletRequest request,
			Map<String, Object> out, Inquiry inquiry, Integer inquiryId)
			throws UnsupportedEncodingException {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return new ModelAndView("/login");
		}
		BbsUserProfilerDO profilerDo = bbsUserProfilerService
				.queryProfilerOfAccount(inquiry.getReceiverAccount());
		inquiry.setSenderAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");// 针对回复
		inquiry.setBeInquiredId(profilerDo.getCompanyId());// 对象者id为默认id

		Integer i = inquiryService.inquiryByUser(inquiry,
				sessionUser.getCompanyId());
		if (i != null && i.intValue() > 0) {
			return new ModelAndView("redirect:/mySendMessage.htm");
		}
		return new ModelAndView("redirect:/mySendMessage.htm");
	}

	// 清空所有的发件箱
	@RequestMapping
	public ModelAndView ajaxDeleteMessage(HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return new ModelAndView("/login");
		}
		ExtResult result = new ExtResult();
		Inquiry inquiry = new Inquiry();
		inquiry.setIsSenderDel("0");
		inquiry.setSenderAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");
		List<InquiryDto> dtoList = inquiryService
				.queryInquiryListByUser(inquiry);
		Integer nub = 0;
		for (InquiryDto inquiryDto : dtoList) {
			inquiryService.removeSentInquiry(inquiryDto.getInquiry().getId(),
					true);
			nub++;
		}
		if (nub.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	// 清空所有的收件箱
	@RequestMapping
	public ModelAndView ajaxDeleteAllMessage(HttpServletRequest request,
			Map<String, Object> out) throws IOException {

		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser == null) {
			return new ModelAndView("/login");
		}
		ExtResult result = new ExtResult();

		Inquiry inquiry = new Inquiry();
		inquiry.setIsReceiverDel("0");
		inquiry.setReceiverAccount(sessionUser.getAccount());
		inquiry.setBeInquiredType("5");

		List<InquiryDto> dtoList = inquiryService
				.queryInquiryListByUser(inquiry);
		Integer nub = 0;
		for (InquiryDto inquiryDto : dtoList) {
			inquiryService.removeReceivedInquiry(inquiryDto.getInquiry()
					.getId(), true);
			nub++;
		}
		if (nub.intValue() > 0) {
			result.setSuccess(true);
		}

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView logout(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
		//页面缓存cdn
		response.addHeader( "Cache-Control", "no-cache" );
	    response.setDateHeader("Expires", 0);
		PageCacheUtil.setNoCache(response);
		
		//清除cookie
		String cookie = HttpUtils.getInstance().getCookie(request, AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);
		
		SsoUtils.getInstance().logout(request, response, null);
		SsoUtils.getInstance().clearnSessionUser(request, null);
		String url=request.getHeader("referer");
		return new ModelAndView("redirect:" + url);
	}

	private void initBaseInfo(Map<String, Object> out, SsoUser ssoUser) {
		if (ssoUser != null) {
			bbsService.countBbsInfo(out, ssoUser.getAccount(),null);
		}
	}

	/**
	 * 初始化用户信息
	 */
	private void initBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO,
			Map<String, Object> out, String loginedAccount) {
		out.put("bbsUserProfilerDO", bbsUserProfilerDO);
		out.put("CategoryMap", BbsService.BBS_POST_CATEGORY_MAP);
		bbsService.countBbsInfo(out, loginedAccount,null);
		Integer i = 0;
		i = bbsPostService.countMyposted(loginedAccount, "1", null, null);
		out.put("passPost", i);
		i = bbsPostService.countMyposted(loginedAccount, "1", null, 11);
		out.put("passQA", i);
	}
	/**
	 * 类别库
	 * @return
	 */
	public Map<Integer,String> getMapCategory(){
		Map<Integer,String> map=new HashMap<Integer,String>();
		List<BbsPostCategory> list=bbsPostCategoryService.queryAllCategory();
		for(BbsPostCategory ca:list){
			map.put(ca.getId(), ca.getName());
		}
		return map;
	}
	

}
