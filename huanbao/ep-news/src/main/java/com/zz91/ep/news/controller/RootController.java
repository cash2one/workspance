/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.news.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.common.Video;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.NewsNormDto;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsSearchDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.common.VideoService;
import com.zz91.ep.service.comp.CompNewsService;
import com.zz91.ep.service.mblog.MBlogInfoService;
import com.zz91.ep.service.mblog.MBlogService;
import com.zz91.ep.service.news.NewsService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * 项目名称：中国环保网 模块编号：业务控制层 模块描述：首页默认控制类。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class RootController extends BaseController {

	@Resource
	private SolrService solrService;

	@Resource
	private NewsService newsService;
	
	@Resource
	private CompNewsService compNewsService; 
	@Resource
	private PhotoService photoService;
	@Resource
	private VideoService videoService;
	@Resource
	private MBlogInfoService mBlogInfoService;
	@Resource
	private MBlogService mBlogService;
	
	final static String q_code = "100010041002";
	final static String h_code = "100010041000";
	final static String J_code = "100010041001";
  
	final static String URL = "http://v.ku6.com/special/show_4027224/XFlByjLKGajkK_o-.html";
	/**
	 * 函数名称：index 功能描述：访问首页时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO
		
		    List<News> list=new ArrayList<News>();
		    
		  
			// 技术参数
			List<News> jList = newsService.queryNewsByCode(J_code,(Integer)6);
	         
			// 前沿技术
			List<News> qList = newsService.queryNewsByCode(q_code,(Integer)6);
			
			// 环保百科
			List<News> hList = newsService.queryNewsByCode(h_code,(Integer)6);
			
			list.addAll(jList);
			list.addAll(qList);
			list.addAll(hList);
			
			Map<Long ,Object>  map1=new  TreeMap<Long,Object>().descendingMap();
			
			for (News news : list) {
				
				map1.put(news.getGmtPublish().getTime(), news);
			}
			
			
			out.put("list", map1);
			
			
		SeoUtil.getInstance().buildSeo("index", null, null, null, out);
		return null;
	}

	/**
	 * 函数名称：list 功能描述：访问列表时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,Map<String, Object> out, String categoryCode,
			String categoryKey, PageDto<NewsSearchDto> page)
			throws SolrServerException {
		// 根据类别查别查询信息数（分页）
		// 如果不是推荐资讯
		
		String categoryString = "";
		if (StringUtils.isEmpty(categoryKey)) {
			page = newsService.pageNewsByCategory(categoryCode, page);
			out.put("page", page);
			categoryString = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_NEWS, categoryCode);
			out.put("categoryCode", categoryCode);
		} else {
			// 推荐资讯信息
			page = newsService.queryValueByTypeAndKey(categoryKey, page);
			out.put("page", page);
			categoryString = ParamUtils.getInstance().getValue(
					"news_recommend_type", categoryKey);
			out.put("categoryCode", categoryKey);
		}
	 	out.put("category", categoryString);
	 	 
	 	if(categoryString==null || StringUtils.isEmpty(categoryString)){
	 		return new ModelAndView("redirect:" + "http://www.huanbao.com/news/zhuanti/index.htm");
	 	}
	   // SEO	
		String[] titleParam = { categoryString };
		String[] keywordsParam = { categoryString };
		String[] descriptionParam = { categoryString };
		SeoUtil.getInstance().buildSeo("list", titleParam, keywordsParam,
				descriptionParam, out);
		
		return null;
	}

	/**
	 * 函数名称：search 功能描述：访问列表时，查询相关数据。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException
	 *             UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView search(Map<String, Object> out, String keywords,
			PageDto<NewsNormDto> page) throws UnsupportedEncodingException,
			SolrServerException {
		if (keywords == null || keywords == "") {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("news"));
		}
		
//		keywords=URLEncoder.encode(keywords, "utf-8");
		keywords=new String(keywords.toString().toLowerCase().getBytes("ISO-8859-1"), "UTF-8");
		out.put("keywords",keywords);
		
		page = solrService.pageNewsBySearch(keywords, null, page);
		
		
		out.put("page", page);
		
		// SEO
		String[] titleParam = { keywords };
		String[] keywordsParam = { keywords };
		String[] descriptionParam = { keywords };
		SeoUtil.getInstance().buildSeo("list", titleParam, keywordsParam,
				descriptionParam, out);
		
		return null;
	}

	@RequestMapping
	public ModelAndView details(Map<String, Object> out, Integer id) {
		// 查询资讯详细信息
		do {
			if (id == null || id.intValue() <= 0) {
				break;
			}
			
			News news = newsService.queryNewDetailsById(id);
			// 资讯不存在   ||   资讯取消发布
			if (news == null||news.getPauseStatus()==0) {
				break;
			}

			// 更新浏览数
			newsService.updateViewCountById(id);
//			news.setDetails(Jsoup.clean(news.getDetails(), Whitelist.none()));
			out.put("news", news);
			//过滤调用户添加的任何关于html的标签
			
			out.put("category", CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_NEWS, news.getCategoryCode()));
			// 查询上一篇文章
			News onNews = newsService.queryPrevNewsById(id,
					news.getCategoryCode());
			out.put("onNews", onNews);
			// 查询下一篇文章
			News downNews = newsService.queryNextNewsById(id,
					news.getCategoryCode());
			out.put("downNews", downNews);
			
			// 视频地址
			Video video = videoService.queryByTypeAndId(id, VideoService.TYPE_NEWS);
			if(video!=null){
				out.put("videoContent", video.getContent());
			}
			
			// SEO
			String[] titleParam = { news.getTitle() };
			String[] keywordsParam = { news.getTags() };
			String description = "";
			if (StringUtils.isEmpty(news.getDescription())) {
				description = Jsoup.clean(news.getDetails(), Whitelist.none());
			} else {
				description = news.getDescription();
			}
			if (StringUtils.isEmpty(news.getDescription())
					&& description.length() > 100) {
				description = description.substring(0, 100);
			}
			String[] descriptionParam = { description };
			SeoUtil.getInstance().buildSeo("details", titleParam,
					keywordsParam, descriptionParam, out);
			return null;
		} while (false);
		return new ModelAndView("redirect:" + AddressTool.getAddress("news"));
	}

	/**
	 * 函数名称：resourceNotFound 功能描述：发生404错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView resourceNotFound(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	/**
	 * 函数名称：uncaughtException 功能描述：发生异常错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView uncaughtException(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}
	
	/**
	 * 函数名称：compNewsList 功能描述：企业动态列表页
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/02/13　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView compNewsList(HttpServletRequest request,
			Map<String, Object> out, String categoryCode, Integer cid,
			PageDto<CompNews> page,String keywords) {
		page = compNewsService.pageCompNewsByCid(cid, "1000", keywords, (short)1, (short)1, (short)1, page);
		out.put("page", page);
		out.put("titlename", "企业动态");
		SeoUtil.getInstance().buildSeo("compNewsList", null, null,
				null, out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView share(HttpServletRequest request,
			Map<String, Object> out,Integer newsId){
		Integer size=3;
		News news = newsService.queryNewDetailsById(newsId);
	    
		List<Photo> pList=new ArrayList<Photo>();
		List<Photo> photoList=photoService.queryPhotoByTargetType("news", newsId, size);
		for (Photo photo : photoList) {
			if(news.getDetails().indexOf(photo.getPath())!=-1){
				if(pList.size()<3){
					pList.add(photo);
				}else{
					break;
				}
			}
		}
		out.put("pList", pList);
		out.put("photoList", photoList);
		out.put("news",news);
		
		
		SeoUtil.getInstance().buildSeo("share", null, null,null, out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView ajaxDoShare(HttpServletRequest request,
			Map<String, Object> out,String textContent,String photos,Integer newsId,String newsTitle){
		ExtResult result=new ExtResult();
		do {
			EpAuthUser user = getCachedUser(request);
			if(user==null){
				result.setData("请登入！");
				result.setSuccess(false);
				return printJson(result, out);
			}
			MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
			if(info!=null && info.getIsDelete().equals("1")){
				result.setData("该帐号已经冻结，请更换帐号！");
				result.setSuccess(false);
				return printJson(result, out);
			}
			if (info == null) {
				result.setData("你还没有微交流帐号，请注册微交流帐号！");
				result.setSuccess(false);
				return printJson(result, out);
			} else if (info != null && StringUtils.isEmpty(info.getName())) {
				result.setData("你的信息还不完善，请完善你的微交流信息！");
				result.setSuccess(false);
				return printJson(result, out);
			}
			//去空格
			// 存入数据库去空格
			String contents = "";
			textContent = textContent.trim().replaceAll("：", ":");
			for (int i = 0; i < textContent.length(); i++) {
				if (i != 0 && textContent.charAt(i - 1) == ' '&& textContent.charAt(i) == ' ') {
					continue;
				} else {
					contents += textContent.charAt(i);
				}
			}
			contents= contents.replace(" ", "");
			contents = contents.replace("\n", "");
			String a="<a href=\"http://www.huanbao.com/news/details"+newsId+".htm\" style=\"color:#006633;\" target=\"_blank\">http://www.huanbao.com/news/details"+newsId+".htm</a>";
			MBlog mBlog=new MBlog();
			mBlog.setInfoId(info.getId());
			mBlog.setPhotoPath(photos);
			mBlog.setType("1");
			if(contents.length()>0){
				mBlog.setContent(contents+" "+a);
			}else{
				mBlog.setContent(newsTitle+" "+a);
			}	
			Integer id = mBlogService.insert(mBlog);
			if(id != null && id.intValue() > 0){
				result.setSuccess(true);
			}
		} while (false);
		
		return printJson(result, out);
	}
}