/**
 * 
 */
package com.ast.ast1949.api.controller.fragment;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.domain.bbs.AnalysisBbsTop;
import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.service.bbs.AnalysisBbsTopService;
import com.ast.ast1949.service.bbs.BbsPostCategoryService;
import com.ast.ast1949.service.bbs.BbsPostNoticeRecommendService;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsPostTagsService;
import com.ast.ast1949.service.bbs.BbsPostTrendsService;
import com.ast.ast1949.service.bbs.BbsSystemMessageService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 * 
 */
@Controller
public class HuzhuController extends BaseController {
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private AnalysisBbsTopService analysisBbsTopService;

	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Resource
	private BbsPostReplyService bbsPostReplyService;
	@Resource
	private BbsPostTagsService bbsPostTagsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private BbsPostNoticeRecommendService bbsPostNoticeRecommendService;
	@Resource
	private ProductsService productsService;
	@Resource
	private BbsPostTrendsService bbsPostTrendsService;
	@Resource
	private BbsPostCategoryService bbsPostCategoryService;
	@Resource
	private BbsSystemMessageService bbsSystemMessageService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private DataIndexService dataIndexService;

	@RequestMapping
	public ModelAndView indexByCode(HttpServletRequest request, Map<String, Object> out,
			String code, Integer size)throws IOException{
		if(size!=null && size.intValue()>200){
			size=200;
		}
		List<DataIndexDO> list=dataIndexService.queryDataByCode(code, null, size);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("listFirst", list.get(0));
		list.remove(list.get(0));
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 24小时热帖
	 * 
	 * @param out
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotReply(Map<String, Object> out, Integer size,
			Integer categoryId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<BbsPostDO> postDailylist = bbsPostService.query24HourHot(size,
				categoryId);
		map.put("postDailylist", postDailylist);
		return printJson(map, out);
	}

	/**
	 * 24小时最热问答
	 * 
	 * @param out
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotAnswer(Map<String, Object> out, Integer size,
			Integer categoryId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<PostDto> postDailylist = bbsPostService.query24HourHotByAnswer(
				size, categoryId);
		map.put("postDailylist", postDailylist);
		return printJson(map, out);
	}

	/**
	 * 最新资讯
	 * 
	 * @param out
	 * @param categoryId
	 *            类别id
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newestPost(Map<String, Object> out, Integer categoryId,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size == null || size > 50) {
			size = 50;
		}
		List<BbsPostDO> newsList = bbsPostService.querySimplePostByCategory(
				categoryId, size);
		// map.put("newsList", newsList);
		// for (int i = 1; i <= size; i++) {
		// ;
		// newsList.get(0);
		// newsList.get(1);
		// newsList.get(2);
		// newsList.get(3);
		// newsList.get(5);
		//
		//
		// }
		/*int hps = 0;
		for (BbsPostDO obj : newsList) {
			String content = bbsPostService.queryContent(obj.getId());
			obj.setContent(content);
			hps = hasPicture(content);
			if(hps==1){
			   obj.setTitle(obj.getTitle()+"(图)");
			}
			else{
				obj.setTitle(obj.getTitle());
			}
		}
		out.put("hps", hps);*/
		map.put("newsList", newsList);
		return printJson(map, out);

	}

	/*// 判断内容中有无图片
	public int hasPicture(String content) {
		String aimString = "<img src";
		int hp = content.indexOf(aimString);
		if (hp != -1) {
			return 1;

		} else {
			return 0;

		}
	}
*/
	/**
	 * 历史牛帖
	 * 
	 * @param out
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView mostPost(Map<String, Object> out, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<BbsPostDO> allBbsPostList = bbsPostService
				.queryMostViewedPost(size);
		map.put("allBbsPostList", allBbsPostList);
		return printJson(map, out);
	}

	/**
	 * 最新话题
	 * 
	 * @param out
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newPost(Map<String, Object> out, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<BbsPostDO> frontBbsPostList = bbsPostService.queryNewestPost(size);
		map.put("frontBbsPostList", frontBbsPostList);
		return printJson(map, out);
	}

	/**
	 * 本周牛人，牛帖
	 * 
	 * @param out
	 * @param category
	 *            类型：post-牛帖 profile-牛人
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView bbsTop(Map<String, Object> out, String category,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<AnalysisBbsTop> postOneWeeklist = analysisBbsTopService
				.queryBbsTopsByType(category, size);
		map.put("postOneWeeklist", postOneWeeklist);
		return printJson(map, out);
	}

	/**
	 * 最牛网商
	 * 
	 * @param out
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView topByPostNum(Map<String, Object> out, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<BbsUserProfilerDO> allIntegralList = bbsUserProfilerService
				.queryTopByPostNum(6);
		map.put("allIntegralList", allIntegralList);
		return printJson(map, out);
	}

	/**
	 * 根据帖子类型查帖
	 * 
	 * @param out
	 * @param postType
	 *            帖子类型
	 * @param size
	 *            显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView postByType(Map<String, Object> out, String postType,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		List<PostDto> list = bbsPostService.queryPostByType(postType, size);
		map.put("list", list);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView hostPost(Map<String, Object> out, String account,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (size > 50) {
			size = 50;
		}
		if (account == null) {
			account = null;
		}
		List<BbsPostDO> hotBbsPostList = bbsPostService.queryHotPost(account,
				size);
		map.put("list", hotBbsPostList);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView search(HttpServletRequest request,
			Map<String, Object> out, Integer size, String keywords)
			throws IOException {
		if (size == null || size.intValue() <= 0) {
			size = 10;
		}
		if (size.intValue() > 100) {
			size = 100;
		}
		if (StringUtils.isEmpty(keywords)) {
			keywords = "";
		}
		keywords = StringUtils.decryptUrlParameter(keywords);

		PageDto<PostDto> pageDto = new PageDto<PostDto>();
		pageDto.setPageSize(size);

		//pageDto = bbsPostService.pagePostBySearchEngine(keywords, pageDto);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", pageDto.getRecords());
		return printJson(map, out);
	}

	/**
	 * 查询最新的人
	 * 
	 * @throws IOException
	 * */
	@RequestMapping
	public ModelAndView queryNewUser(Map<String, Object> out, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BbsUserProfilerDO> list = bbsUserProfilerService.queryNewUser(size);
		for (BbsUserProfilerDO obj:list) {
			if(StringUtils.isEmpty(obj.getNickname())){
				CompanyAccount ca =  companyAccountService.queryAccountByAccount(obj.getAccount());
				if(StringUtils.isNotEmpty(ca.getContact())){
					obj.setNickname(ca.getContact());
				}else{
					obj.setNickname("匿名");
				}
			}
				
		}
		
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 统计出每个发布的量 新版互助首页 底部类别数据获取
	 * 
	 * @throws IOException
	 * */
	@RequestMapping
	public ModelAndView queryCountPostByTimeAndType(Map<String, Object> out,
			Integer categoryId, String type) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String fromDate = DateUtil.toString(new Date(), "yyyy-MM-dd");
		String toDate = DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), +1), "yyyy-MM-dd");
		// 社区公告
		map.put("todaySQ",
				bbsPostService.queryCountPostByTime(fromDate, toDate, 4));
		map.put("totalSQ", bbsPostService.queryCountPostByTime(null, null, 4));
		map.put("listSQ", bbsPostService.querySimplePostByCategory(4, 1));

		// 纠纷处理
		map.put("todayJF",
				bbsPostService.queryCountPostByTime(fromDate, toDate, 13));
		map.put("totalJF", bbsPostService.queryCountPostByTime(null, null, 13));
		map.put("listJF", bbsPostService.querySimplePostByCategory(13, 1));
		// 意见反馈
		map.put("todayYJ",
				bbsPostService.queryCountPostByTime(fromDate, toDate, 14));
		map.put("totalYJ", bbsPostService.queryCountPostByTime(null, null, 14));
		map.put("listYJ", bbsPostService.querySimplePostByCategory(14, 1));
		// 新手报道
		map.put("todayXS",
				bbsPostService.queryCountPostByTime(fromDate, toDate, 15));
		map.put("totalXS", bbsPostService.queryCountPostByTime(null, null, 15));
		map.put("listXS", bbsPostService.querySimplePostByCategory(15, 1));

		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView countBbsAndQA(Map<String, Object> out)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("countBbs", bbsPostService.countMyposted(null, "1", "0", null));
		map.put("countQA", bbsPostService.countMyposted(null, "1", "0", 11));
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView countBbsByInfo(Map<String, Object> out, String account)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer i = 0;
		i = bbsPostService.countMyposted(account, null, null, null);
		map.put("countBbsPost", 0 < i || i != null ? i : 0);
		i = bbsPostReplyService.countMyreply(account, null, null);
		map.put("countBbsReply", 0 < i || i != null ? i : 0);
		return printJson(map, out);
	}
	/**
	 * 最新问题
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newQue(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		//最新问题
		List<BbsPostDO> ZXlist=bbsPostService.querySimplePostByCategory(1, 5);
		map.put("FirstFYL", ZXlist.get(0));
		ZXlist.remove(ZXlist.get(0));
		map.put("FYLList",ZXlist);
		return printJson(map, out);
	}
	/**
	 * 最热问题
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotQue(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		//最热问题
		List<PostDto> WDRlist=bbsPostService.query24HourHotByAnswer(5, 1);
		map.put("FirstWDR", WDRlist.get(0));
		WDRlist.remove(WDRlist.get(0));
		map.put("WDRList",WDRlist);
		return printJson(map, out);
	}
	/**
	 * 头条焦点
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView headPost(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BbsPostDO>  TTList=bbsPostService.queryPostWithContentByType("3", 5);
		map.put("FirstTT",TTList.get(0));
		TTList.remove(TTList.get(0));
		map.put("TTLIst",TTList);
		return printJson(map, out);
	}
	/**
	 * 首页帖子类信息
	 * @param out
	 * @param assistId
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView bbsPostForBbs(Map<String, Object> out,Integer assistId,Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BbsPostDO>  TTList=bbsPostService.queryBbsPostByAssistId(assistId, size);
		map.put("bbsPost",TTList.get(0));
		TTList.remove(TTList.get(0));
		map.put("postList",TTList);
		return printJson(map, out);
	}
	
	/**
	 * 首页——废料学院
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView feiliaoColloge(Map<String, Object> out) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		PageDto<BbsPostDO> page=new PageDto<BbsPostDO>();
		page.setPageSize(10);
	    page=bbsPostService.pageCollegeByEngine("废料学院", page, 12);
	    for(BbsPostDO post:page.getRecords()){
			if(StringUtils.isNotEmpty(post.getCategory())){
				post.setChildCategory(post.getCategory().split(",")[0]);
			}
		}
		map.put("list",page.getRecords());
		return printJson(map, out);
	}
	/**
	 * 首页——热门关注
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotNotice(Map<String, Object> out) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("RDGZList", bbsPostService.queryBbsPostByNoticeAReplyTime(3));
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView newRecomend(Map<String, Object> out) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		//最新推荐
		BbsPostNoticeRecommend bbs=new BbsPostNoticeRecommend();
		bbs.setType(0);
		bbs.setState(0);
		map.put("NewRec", bbsPostNoticeRecommendService.queryZuiXinRecommendByCondition(bbs, 8));
		return printJson(map, out);
	}
	/**
	 * 等待回答
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView waitAnswer(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("WaitAnswer", bbsPostService.queryWaitAnswerBbsPost(5));
		return printJson(map, out);
	}
	/**
	 * 最新问答
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newQuestion(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("NewQuestion", bbsPostService.querySimplePostByCategory(1, 5));
		return printJson(map, out);
	}
	/**
	 * 问答首页热门关注 
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotNotices(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotNotice", bbsPostService.queryBbsPostByNoticeAReplyTime(5));
		return printJson(map, out);
	}
	/**
	 * 热门问答
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotQuestion(Map<String, Object> out,Integer categoryId) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("HotQuestion", bbsPostService.queryHotBbsPost(5,categoryId));
		return printJson(map, out);
	}
	/**
	 * 热门标签
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView hotTags(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hotTags", bbsPostTagsService.queryTagsByArticleC(16));
		return printJson(map, out);
	}
	/**
	 * 最佳回答者
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView bestAnswer(Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bestAnswer", bbsPostReplyService.queryBestAnswerByViewCount(8));
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView NoticeIndexList(Map<String, Object> out,String account) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		//关注领域，4表示关注的领域
		map.put("NoticeArea", bbsPostNoticeRecommendService.ListNoticeByUser(account, 4, 4));
		//关注的问答，1表示关注的问答
		map.put("LISTQUE", bbsPostNoticeRecommendService.ListNoticeByUser(account, 1, 3));
		//关注的帖子，0表示关注的帖子
		map.put("LISTBBS", bbsPostNoticeRecommendService.ListNoticeByUser(account, 0, 3));
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView hotRecom(Map<String, Object> out) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
	   //精选推荐
		map.put("JXrecom", bbsPostService.queryPostByType(String.valueOf(3), 8));
		return printJson(map, out);
	}
	/**
	 * 根据最终类别获取相应条数的post
	 * @param out
	 * @param assistId
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView bbsPost(Map<String, Object> out,Integer assistId,Integer size) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", bbsPostService.queryBbsPostByAssistId(assistId, size));
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView NewProduct(Map<String, Object> out) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		//最新商机
		map.put("gongying", productsService.queryProductsByType("10331000", 5));
		return printJson(map, out);
	}
	/**
	 * 相关标签
	 * @param out
	 * @param category
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView tags(Map<String, Object> out,Integer category,Integer size) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> maps=new HashMap<String,Object>();
		if(size==null){
			size=4;
		}
		List<BbsPostTags> list=bbsPostTagsService.queryTagByCategory(category, size);
		for(BbsPostTags tag:list){
			if(StringUtils.isNotEmpty(tag.getTagName())){
				maps.put(tag.getTagName(), CNToHexUtil.getInstance().encode(tag.getTagName()));
			}
		}
		//最新商机
		map.put("tags",list);
		map.put("maps",maps);
		return printJson(map, out);
	}
	/**
	 * 个人首页
	 * @param out
	 * @param companyId
	 * @param categoryId
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView personBbsPost(Map<String, Object> out,Integer companyId,Integer categoryId,Integer size) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",bbsPostService.queryBbsPostByCompanyId(companyId, categoryId, size));
		return printJson(map, out);
	}
	/**
	 * 动态
	 * @param out
	 * @param companyId
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView trends(Map<String, Object> out,Integer companyId,Integer size) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",bbsPostTrendsService.queryTrendsByCompanyId(companyId, size));
		return printJson(map, out);
	}
	/**
	 * 学院类别
	 * @param out
	 * @param parentId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView xueyuanCategory(Map<String, Object> out,Integer parentId) throws IOException {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",bbsPostCategoryService.queryCategorysByParentId(parentId));
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView xueyuanIndex(Map<String, Object> out,Integer categoryId,Integer size) throws IOException {
		//根据类别id获取类别名称
		BbsPostCategory category=bbsPostCategoryService.querySimpleCategoryById(categoryId);
		Map<String,Object> map=new HashMap<String,Object>();
	    //根据类别名称获取相应的学院帖子
		PageDto<BbsPostDO> page=new PageDto<BbsPostDO>();
		page.setPageSize(size);
		page= bbsPostService.pagePostByNewSearchEngine(category.getName(), page, 3, 3);
		for(BbsPostDO post:page.getRecords()){
			if(StringUtils.isNotEmpty(post.getCategory())){
				post.setChildCategory(post.getCategory().split(",")[0]);
			}
		}
		map.put("ListCategory",page.getRecords());
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView getTopInfo(Map<String, Object> out,Integer companyId) throws IOException{
		Map<String,Object> map=new HashMap<String,Object>();
		do {
			if (companyId==null) {
				break;
			}
			
			map.put("msgCount", bbsSystemMessageService.getNoReadCount(companyId));
			CompanyAccount ca = companyAccountService.queryAccountByCompanyId(companyId);
			if (ca==null) {
				break;
			}
			Integer i = inquiryService.countUnviewedInquiry("5", ca.getAccount(), companyId);
			map.put("inquiryCount", i);
			
			BbsUserProfilerDO bbsUserProfilerDO = bbsUserProfilerService.queryUserByCompanyId(companyId);
			if (bbsUserProfilerDO==null) {
				break;
			}
			map.put("bbsUserProfiler", bbsUserProfilerDO);
		} while (false);
		return printJson(map, out);
	}
}
