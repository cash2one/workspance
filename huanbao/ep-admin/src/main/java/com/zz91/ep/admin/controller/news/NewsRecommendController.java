package com.zz91.ep.admin.controller.news;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;

/**
 * 资讯推荐管理controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class NewsRecommendController extends BaseController {

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
}
