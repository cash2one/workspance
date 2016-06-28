/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 下午07:45:41
 */
package com.ast1949.shebei.news.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.domain.NewsCategory;
import com.ast1949.shebei.news.controller.BaseController;
import com.ast1949.shebei.service.NewsCategoryService;
import com.ast1949.shebei.service.NewsService;

@Controller
public class NewsFragmentController extends BaseController {

	@Resource
	private NewsService newsService;
	@Resource
	private NewsCategoryService newsCategoryService;
	
	@RequestMapping
	public ModelAndView queryNewsByCategoryAndType(Map<String, Object> out,
			HttpServletRequest request,String categoryCode,Short type,Integer size,Short flag) throws IOException{
		if (size>20){
			size=20;
		}	
		List<News> list=newsService.queryNewsByCategoryAndType(categoryCode, type, size,flag);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView queryAllNewsCategory(Map<String, Object> out,
			HttpServletRequest request) throws IOException{
		List<NewsCategory> list=newsCategoryService.queryAllNewsCategory();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
}
