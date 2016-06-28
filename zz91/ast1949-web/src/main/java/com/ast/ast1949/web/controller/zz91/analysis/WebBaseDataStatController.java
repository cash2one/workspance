package com.ast.ast1949.web.controller.zz91.analysis;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.site.WebBaseDataStatDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.site.WebBaseDataStatService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class WebBaseDataStatController extends BaseController {
	@Resource
	private WebBaseDataStatService webBaseDataStatService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView query(Map<String, Object> out,HttpServletRequest request,PageDto<WebBaseDataStatDo> page,String statCate,String gmtStatDate) throws IOException {
		Date targetDate=null;
		if(StringUtils.isNotEmpty(gmtStatDate)){
			try {
				targetDate= DateUtil.getDate(gmtStatDate, "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			targetDate = DateUtil.getDateAfterDays(new Date(), -1);
			try {
				targetDate = DateUtil.getDate(targetDate, "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		out.put("targetDate",targetDate);
		page = webBaseDataStatService.pageWebBaseDataStat(page, statCate, targetDate);
		return printJson(page, out);
	}
}
