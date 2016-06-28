package com.ast.ast1949.web.controller.zz91.analysis;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;

@Controller
public class AnalysisPpcLogController extends BaseController{
	
	final static String TARGET_DATE_FORMAT="yyyy-MM-dd";
	
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,AnalysisPpcLog analysisPpcLog,PageDto<PhoneLog>page,Integer cid){
		out.put("cid",cid );
		out.put("from", DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}
	
}
