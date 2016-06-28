package com.ast.feiliao91.trade.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.service.logistics.LogisticsService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class LogisticsController {
	@Resource
	private LogisticsService logisticsService;

	@RequestMapping
	public ModelAndView logistics(Map<String, Object> out,
			HttpServletRequest request,String logisticsCode,String type) throws ParseException {
		if(StringUtils.isEmpty(logisticsCode)){
			return null;
		}
		//更新物流至最新,type=1为发货，type=2为退货
		logisticsService.updaLogisticsByCode(logisticsCode,type);
		Map<String, Object> logistics = logisticsService.selectLogisticsByCode(logisticsCode,type);
		out.put("logistics", logistics);
		SeoUtil.getInstance().buildSeo("logistics", out);
		return null;
	}

	// 给一个Date日期类型获取到星期
	@SuppressWarnings("unused")
	private String getWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(date);
		String week1 = "周"+week.substring(2);
		return week1;
	}
}
