package com.ast.ast1949.exhibit.controller.exhibitchannel;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.exhibit.Exhibitors;
import com.ast.ast1949.domain.exhibit.PreviouExhibitors;
import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.exhibit.controller.BaseController;
import com.ast.ast1949.service.exhibit.ExhibitorsService;
import com.ast.ast1949.service.exhibit.PreviouExhibitorsService;
import com.ast.ast1949.service.information.ExhibitService;
import com.zz91.util.datetime.DateUtil;
@Controller
public class TaiZhouController extends BaseController {
	@Resource
	private ExhibitorsService exhibitorsService;
	@Resource
	private PreviouExhibitorsService previouExhibitorsService;
	@Resource
	private ExhibitService exhibitService;
	
	private void countDay(Map<String, Object> out) throws ParseException{
		Integer day=0;
		day=DateUtil.getIntervalDays(DateUtil.getDate("2016-10-11", "yyyy-MM-dd"), new Date());
		if (day<0) {
			day=0;
		}
		out.put("day", day);
	}
	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		// 参展企业名录
		PreviouExhibitors previouExhibitors=new PreviouExhibitors();
		previouExhibitors.setExhibitTime(2016);
		List<PreviouExhibitors> list=previouExhibitorsService.queryAllList(previouExhibitors);
		out.put("list", list);
	}
	//展会概述
	@RequestMapping
	public ModelAndView exhibitdetail(HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//展商中心
	@RequestMapping
	public ModelAndView exhibitCenter(HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//展商中心下酒店
	@RequestMapping
	public ModelAndView ehotel(HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//展商中心下交通指南
	@RequestMapping
	public ModelAndView etraffic(HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	
	//同期论坛
	@RequestMapping
	public ModelAndView forums (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//同期论坛下的酒店
	@RequestMapping
	public ModelAndView fhotel (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//同期论坛下的交通指南
	@RequestMapping
	public ModelAndView ftraffic (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//新闻资讯
	@RequestMapping
	public ModelAndView newsList (HttpServletRequest request,Map<String, Object>out,PageDto<ExhibitDTO> page) throws ParseException{
		countDay(out);
		page.setStartIndex((page.getCurrentPage()-1)*page.getPageSize());
		ExhibitDO exhibit = new ExhibitDO();
		exhibit.setPlateCategoryCode("10371013");
		page = exhibitService.pageExhibitByAdmin(exhibit, page);
		out.put("page", page);
		return null;
	}
	@RequestMapping
	public ModelAndView newsDetail(HttpServletRequest request,Map<String, Object>out,Integer id) throws ParseException{
		countDay(out);
		ExhibitDTO dto = exhibitService.queryExhibitById(id);
		out.put("exhibitDTO", dto);
		//上一篇
		ExhibitDO up = exhibitService.queryUpNews(dto.getExhibitDO().getPlateCategoryCode(), DateUtil.toString(dto.getExhibitDO().getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
		out.put("up", up);
		//下一篇
		ExhibitDO down= exhibitService.queryDownNews(dto.getExhibitDO().getPlateCategoryCode(), DateUtil.toString(dto.getExhibitDO().getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
		out.put("down", down);
		return null;
	}
	//新闻1
	@RequestMapping
	public ModelAndView newsDetail1(HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//新闻2
	@RequestMapping
	public ModelAndView newsDetail2 (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//新闻3
	@RequestMapping
	public ModelAndView newsDetail3 (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//新闻4
	@RequestMapping
	public ModelAndView newsDetail4 (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//联系我们
	@RequestMapping
	public ModelAndView contactUs (HttpServletRequest request,Map<String, Object>out) throws ParseException{
		countDay(out);
		return null;
	}
	//我要参展
	@RequestMapping
	public ModelAndView joinExhibit (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	//日程安排
	@RequestMapping
	public ModelAndView scheduly (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	//参展流程
	@RequestMapping
	public ModelAndView exhibitProcess (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	//观展中心
	@RequestMapping
	public ModelAndView audienceExhibit (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	//观展中心下的展会介绍
	@RequestMapping
	public ModelAndView aexhibitdetail (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	//观展中心下的交通指南
	@RequestMapping
	public ModelAndView atraffic (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	@RequestMapping
	public ModelAndView resultSuccess (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doJoinExhibit (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=exhibitorsService.insert(exhibitors);
		if (i!=null&&i.intValue()>0) {
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}
	
	
	//展商
	@RequestMapping
	public ModelAndView exhibitors (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		PreviouExhibitors previouExhibitors=new PreviouExhibitors();
		previouExhibitors.setExhibitTime(2015);//参展时间
		//参展展商2015
		List<PreviouExhibitors> PreviouExhibitorsList=previouExhibitorsService.queryAllList(previouExhibitors);
		out.put("PreviouExhibitorsList", PreviouExhibitorsList);
		previouExhibitors=new PreviouExhibitors();
		previouExhibitors.setExhibitTime(2016);//参展时间
		//参展展商2016
		List<PreviouExhibitors> listTZOS=previouExhibitorsService.queryAllList(previouExhibitors);
		out.put("listTZOS", listTZOS);
		return null;
	}
	
	//下载中心
	@RequestMapping
	public ModelAndView newDownload (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
	//往届回顾
	@RequestMapping
	public ModelAndView reback (HttpServletRequest request,Map<String, Object>out,Exhibitors exhibitors) throws IOException, ParseException{
		countDay(out);
		return null;
	}
}
