/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-11 下午02:17:40
 */
package com.ast1949.shebei.news.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.domain.NewsCategory;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.NewsCategoryService;
import com.ast1949.shebei.service.NewsService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class ZiXunController extends BaseController {

	@Resource
	private NewsService newsService;
	@Resource
	private NewsCategoryService newsCategoryService;
	
	@RequestMapping
	public ModelAndView list(Map<String, Object> out,String category,Short type,PageDto<News> page){
		
		out.put("category", category);
		NewsCategory newsCategory= newsCategoryService.queryNameAndKeywordsByCode(category);
		if (newsCategory!=null){
			out.put("categoryName", newsCategory.getName());
			out.put("type", type);
			page=newsService.pageNews(category,type,page);
			out.put("page", page);
			
			Integer size;
			size=page.getStart()/page.getLimit()+1;
			
			String[] title={newsCategory.getKeywords(),"第"+size+"页"};
			String[] keywords={newsCategory.getKeywords()};
			String[] description={newsCategory.getKeywords()};
			SeoUtil.getInstance().buildSeo("zxlist", title, keywords, description, out);
		}
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView zxdetails(Map<String, Object> out,Integer id){
		
		News news=newsService.queryNewsById(id,NewsService.ZIXUN_TYPE);
		out.put("news", news);
		
		if (news!=null){
			//查询上一篇文章
			News onNews=newsService.queryOnNewsById(id, news.getCategoryCode(),NewsService.ZIXUN_TYPE);
			out.put("onNews", onNews);
			//查询下一篇文章
			News downNews=newsService.queryDownNewsById(id, news.getCategoryCode(),NewsService.ZIXUN_TYPE);
			out.put("downNews", downNews);
			
			if (StringUtils.isNotEmpty(news.getDetailsQuery())){
				if (news.getDetailsQuery().length()>80){
					news.setDetailsQuery(news.getDetails().substring(0, 80)+"...");
				}
			}
			
			String[] title={news.getTitle()};
			String[] keywords={""};
			String[] description={news.getDetailsQuery()};
			SeoUtil.getInstance().buildSeo("zxdetails", title, keywords, description, out);
		}
		return null;
	}
}
