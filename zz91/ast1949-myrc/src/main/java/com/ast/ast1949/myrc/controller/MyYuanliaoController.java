package com.ast.ast1949.myrc.controller;
/**
 * @author shiqp
 * @date 2015-08-21
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.yuanliao.CategoryYuanliaoService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.service.yuanliao.YuanliaoPicService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;
@Controller
public class MyYuanliaoController extends BaseController{
	@Resource
	private CategoryYuanliaoService categoryYuanliaoService;
	@Resource
	private YuanLiaoService yuanliaoService;
	@Resource
	private YuanliaoPicService yuanliaoPicService;
	@Resource
	private MarketCompanyService marketCompanyService;
	
	@RequestMapping
	public void index(Map<String,Object> out,HttpServletRequest request){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		Map<String,String> map=YuanliaoFacade.getInstance().getChild("20091000");
		for(String key:map.keySet()){
			out.put("key", map.get(key));
		}
	}
	
	@RequestMapping
	public void office_post1(Map<String,Object> out,HttpServletRequest request){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		Map<String,String> map=YuanliaoFacade.getInstance().getChild("20091000");
		for(String key:map.keySet()){
			out.put("key", map.get(key));
		}
		//常用的类别
		Map<String,String> mapc = yuanliaoService.queryYuanliaoCategory(sessionUser.getCompanyId());
		out.put("mapc", mapc);
		out.put("hrefUrl", "###");
	}

	@RequestMapping
	public ModelAndView queryCategoryByKeyword(Map<String,Object> out,HttpServletRequest request,String keyword) throws IOException{
		do{
			keyword = StringUtils.decryptUrlParameter(keyword);
			if (StringUtils.isEmpty(keyword)) {
				break;
			}
			List<Map<String, Object>> list = categoryYuanliaoService.queryCategoryYuanliaoByKeyword(keyword);
			if(list.size()==0){
				list = new ArrayList<Map<String, Object>>();
			}
			return printJson(list, out);
		}while(false);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryCategoryYuanliaoByParentCode(Map<String,Object> out,HttpServletRequest request,String parentCode) throws IOException{
		Map<String,String> map = YuanliaoFacade.getInstance().getChild(parentCode);
		if(map==null){
			map = new HashMap<String,String>();
		}
		List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		for(String s : map.keySet()){
			CategoryYuanliao yl = new CategoryYuanliao();
			yl.setCode(s);
			yl.setLabel(map.get(s));
			list.add(yl);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView office_post2(Map<String,Object> out,HttpServletRequest request,Yuanliao yuanliao){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		//类别
		String label = "";
		if(yuanliao.getCategoryYuanliaoCode().length()==8){
			label = YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode());
		}else if(yuanliao.getCategoryYuanliaoCode().length()==12){
			label = YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode().substring(0, 8)) + "&nbsp;>&nbsp;" + YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode());
		}else if(yuanliao.getCategoryYuanliaoCode().length()==16){
			label = YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode().substring(0, 8)) + "&nbsp;>&nbsp;" + YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode().substring(0, 12)) + "&nbsp;>&nbsp;" + YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode());
		}
		Map<String,String> mapc =YuanliaoFacade.getInstance().getChild(yuanliao.getCategoryYuanliaoCode().substring(0, 8));
		List<String> listc = new ArrayList<String>();
		for(String s:mapc.keySet()){
			listc.add(s);
		}
		out.put("listc", listc);
		out.put("label", label);
		out.put("title", YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode()));
		out.put("yuanliao", yuanliao);
		return null;
	}
	
	@RequestMapping
	public ModelAndView publishYuanliao(Map<String,Object> out,HttpServletRequest request,Yuanliao yuanliao,String pics,Integer time,String intro,Integer priceFlag){
		if(time!=null){
			//过期时间
			yuanliao.setExpireTime(DateUtil.getDateAfterDays(new Date(), time));
		}
		if(StringUtils.isNotEmpty(intro)){
			yuanliao.setTradeIntro(intro);
		}
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		yuanliao.setAccount(sessionUser.getAccount());
		yuanliao.setCompanyId(sessionUser.getCompanyId());
		yuanliao.setSourceTypeCode(0);
		if(StringUtils.isNotEmpty(yuanliao.getTags())){
			String tags = "";
			for(String s:yuanliao.getTags().split(",")){
				if(StringUtils.isNotEmpty(s)){
					if(StringUtils.isNotEmpty(tags)){
						tags = tags + "," + s;
					}else{
						tags = s;
					}
					
				}
			}
			yuanliao.setTags(tags);
		}
		//品牌销售
		if(StringUtils.isNotEmpty(yuanliao.getCategoryAssistDesc1())&&yuanliao.getSalesMode()==0){
			if("其他".equals(YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryMainDesc()))){
				yuanliao.setCategoryAssistDesc(yuanliao.getCategoryAssistDesc1());
			}
		}else if(yuanliao.getSalesMode()==0){
			yuanliao.setCategoryAssistDesc("");
		}
		//自产销售
		if(yuanliao.getSalesMode()==1){
			yuanliao.setCategoryMainDesc("");
		}
		if(priceFlag==null){
			yuanliao.setPrice(yuanliao.getMinPrice());
			yuanliao.setMinPrice(null);
			yuanliao.setMinPrice(null);
		}
		Integer i = yuanliaoService.insertYuanliao(yuanliao);
		if(i>0){
			//图片的上传
			String[] picAddress = pics.split(",");
			for(String s : picAddress){
				YuanliaoPic pic = new YuanliaoPic();
				if(s.equals(picAddress[0])){
					pic.setIsDefault(1);
				}else{
					pic.setIsDefault(0);
				}
				pic.setYuanliaoId(i);
				pic.setPicAddress(s);
				yuanliaoPicService.insertYuanliaoPic(pic);
			}
			out.put("id", i);
			return new ModelAndView("redirect:"+"/myyuanliao/office_suc.htm");
		}
		return new ModelAndView("redirect:"+"/myyuanliao/office_post2.htm");
	}
	
	@RequestMapping
	public ModelAndView office_suc(Map<String,Object> out,HttpServletRequest request,Integer id){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		out.put("id", id);
		//原料供求详情
		Yuanliao yl = yuanliaoService.queryYuanliaoById(id);
		//信息描述字数
		 Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);  //去掉html标签
	     Matcher m_html = p_html.matcher(yl.getDescription());  
		if( m_html.replaceAll("").length()>30){
			out.put("len", "len");
		}
		//该原料供求有无图片
		YuanliaoPic pic = new YuanliaoPic();
		pic.setYuanliaoId(id);
		pic.setIsDel(0);
		List<YuanliaoPic> list = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(pic, null);
		if(list.size()>0){
			out.put("pic", "pic");
		}
		//入驻的市场
		List<Market> listm=marketCompanyService.queryMarketByCompanyId(sessionUser.getCompanyId());
		if(listm.size()>0){
			out.put("market", "market");
		}
		return null;
	}
	
	@RequestMapping
	public ModelAndView office_post_list(Map<String,Object> out,HttpServletRequest request,PageDto<YuanliaoDto> page,Yuanliao yuanliao,YuanLiaoSearch search){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		yuanliao.setCompanyId(sessionUser.getCompanyId());
		page.setPageSize(10);
		page.setSort("gmt_modified");
		page.setDir("desc");
		page = yuanliaoService.pageYaunliao(page, yuanliao, search);
		//判断是否发布过原料供求
		Yuanliao yl = new Yuanliao();
		yl.setCompanyId(sessionUser.getCompanyId());
		Integer has = yuanliaoService.countYuanliaoList(yl, null);
		if(has!=null&&has>0){
			out.put("has", "has");
		}
		out.put("page", page);
		out.put("search", search);
		return null;
	}
	
	@RequestMapping
	public ModelAndView office_img(Map<String,Object> out,HttpServletRequest request,Integer yuanliaoId){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		out.put("yuanliaoId", yuanliaoId);
		YuanliaoPic pic = new YuanliaoPic();
		pic.setIsDel(0);
		pic.setYuanliaoId(yuanliaoId);
		List<YuanliaoPic> piclist = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(pic, null);
		out.put("piclist", piclist);
		out.put("limitCount", 5-piclist.size());
		return null;
	}
	
	@RequestMapping
	public ModelAndView office_post_edit(Map<String,Object> out,HttpServletRequest request,Integer id) throws Exception{
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		out.put("account", sessionUser.getAccount());
		Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(id);
		if(yuanliao.getMinPrice()==null){
			yuanliao.setMinPrice(yuanliao.getPrice());
		}
		//类别
		String label = "";
		if(yuanliao.getCategoryYuanliaoCode().length()==8){
			label = YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode());
		}else if(yuanliao.getCategoryYuanliaoCode().length()==12){
			label = YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode().substring(0, 8)) + "&nbsp;>&nbsp;" + YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode());
		}else if(yuanliao.getCategoryYuanliaoCode().length()==16){
			label = YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode().substring(0, 8)) + "&nbsp;>&nbsp;" + YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode().substring(0, 12)) + "&nbsp;>&nbsp;" + YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryYuanliaoCode());
		}
		out.put("label", label);
		Map<String,String> mapc =YuanliaoFacade.getInstance().getChild(yuanliao.getCategoryYuanliaoCode().substring(0, 8));
		List<String> listc = new ArrayList<String>();
		for(String s:mapc.keySet()){
			listc.add(s);
		}
		if (yuanliao.getExpireTime()!=null&&yuanliao.getRefreshTime()!=null) {
			out.put("day",DateUtil.getIntervalDays(yuanliao.getExpireTime(),yuanliao.getRefreshTime()));
		}
		out.put("listc", listc);
		String[] tags = yuanliao.getTags().split(",");
		List<String> listTag = new ArrayList<String>();
		for(String s : tags){
			if(StringUtils.isNotEmpty(s)){
				listTag.add(s);
			}
		}
		out.put("tagSize", listTag.size());
		for(int i=listTag.size();i<5;i++){
			listTag.add("");
		}
		out.put("listTag", listTag);
		out.put("yuanliao", yuanliao);
		YuanliaoPic pic = new YuanliaoPic();
		pic.setYuanliaoId(yuanliao.getId());
		pic.setIsDel(0);
		List<YuanliaoPic> listylp = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(pic, null);
		out.put("limitCount", 5-listylp.size());
		for(int i=listylp.size();i<5;i++){
			listylp.add(new YuanliaoPic());
		}
		out.put("listpic", listylp);
		return null;
	}
	@RequestMapping
	public ModelAndView doYuanliaoEdit(Map<String,Object> out,HttpServletRequest request,Yuanliao yuanliao,Integer time,String pics,String intro,Integer priceFlag) throws Exception{
		Yuanliao yl = yuanliaoService.queryYuanliaoById(yuanliao.getId());
		if(DateUtil.getIntervalDays(yl.getExpireTime(),yl.getRefreshTime())!=time){
			yuanliao.setExpireTime(DateUtil.getDateAfterDays(yl.getRefreshTime(), time));
		}
		if(StringUtils.isNotEmpty(intro)&&!"on".equals(intro)){
			yuanliao.setTradeIntro(intro);
		}
		//品牌销售
		if(StringUtils.isNotEmpty(yuanliao.getCategoryAssistDesc1())&&yuanliao.getSalesMode()==0){
			if("其他".equals(YuanliaoFacade.getInstance().getValue(yuanliao.getCategoryMainDesc()))){
				yuanliao.setCategoryAssistDesc(yuanliao.getCategoryAssistDesc1());
			}
		}else if(yuanliao.getSalesMode()==0){
			yuanliao.setCategoryAssistDesc("");
		}
		//自产销售
		if(yuanliao.getSalesMode()==1){
			yuanliao.setCategoryMainDesc("");
		}
		//设置成未审核
		yuanliao.setCheckStatus(0);
		if(priceFlag==null){
			yuanliao.setPrice(yuanliao.getMinPrice());
			yuanliao.setMinPrice(null);
			yuanliao.setMaxPrice(null);
		}
		Integer i = yuanliaoService.updateYuanliao(yuanliao);
		if(i>0){
			YuanliaoPic pic = new YuanliaoPic();
			pic.setIsDel(0);
			pic.setYuanliaoId(yuanliao.getId());
			List<YuanliaoPic> list = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(pic, null);
			List<String> listpic = new ArrayList<String>();
			for(YuanliaoPic ylp:list){
				listpic.add(ylp.getPicAddress());
			}
			for(String s : pics.split(",")){
				if(!listpic.contains(s)){
					YuanliaoPic yp = new YuanliaoPic();
					yp.setYuanliaoId(yuanliao.getId());
					yp.setPicAddress(s);
					yuanliaoPicService.insertYuanliaoPic(yp);
				}
			}
		}
		out.put("id", yuanliao.getId());
		return new ModelAndView("redirect:"+"/myyuanliao/office_suc.htm"); 
	}
	
	@RequestMapping
	public ModelAndView updateYuanliao(Map<String,Object> out,HttpServletRequest request,Yuanliao yuanliao,Integer refresh) throws Exception{
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		do{
			if(refresh!=null){
				Yuanliao yl = yuanliaoService.queryYuanliaoById(yuanliao.getId());
				Integer time = DateUtil.getIntervalDays(yl.getExpireTime(),yl.getRefreshTime());
				Date now = new Date();
				long intervalNow = now.getTime() - yl.getRefreshTime().getTime();
				yuanliao.setExpireTime(DateUtil.getDateAfterDays(new Date(), time));
				yuanliao.setRefreshTime(new Date());
				yuanliao.setGmtModified(new Date());
				yuanliao.setPrice(yl.getPrice());
				yuanliao.setMinPrice(yl.getMinPrice());
				yuanliao.setMaxPrice(yl.getMaxPrice());
				Long interval = Long.valueOf(MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(), "refresh_product_interval"));
				if (intervalNow > (interval.longValue() * 1000)) {
					yuanliaoService.updateYuanliao(yuanliao);
				}
				result.setSuccess(true);
				break;
			}
			Integer i = yuanliaoService.updateYuanliao(yuanliao);
			if(i > 0){
				result.setSuccess(true);
			}
		}while(false);
		return printJson(result, out); 
	}
	@RequestMapping
	public ModelAndView updateYuanliaoPic(Map<String,Object> out,HttpServletRequest request,YuanliaoPic pic,Integer editFlag) throws Exception{
		ExtResult result = new ExtResult();
		if(pic.getYuanliaoId()!=null){
			YuanliaoPic ylp = new YuanliaoPic();
			ylp.setYuanliaoId(pic.getYuanliaoId());
			ylp.setIsDefault(0);
			yuanliaoPicService.updateYuanliaoPic(ylp);
			pic.setIsDefault(1);
		}
		pic.setYuanliaoId(null);
		pic.setCheckStatus(0);
		Integer i = yuanliaoPicService.updateYuanliaoPic(pic);
		if(i>0){
			result.setSuccess(true);
		}
		if(editFlag!=null){
			return new ModelAndView("redirect:"+"/myyuanliao/office_img.htm?yuanliaoId=" + editFlag);
		}
		return printJson(result, out); 
	}
	@RequestMapping
	public ModelAndView insertYuanliaoPic(Map<String,Object> out,HttpServletRequest request,YuanliaoPic pic) throws Exception{
		ExtResult result = new ExtResult();
		pic.setIsDefault(0);
		Integer i = yuanliaoPicService.insertYuanliaoPic(pic);
		if(i > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryCategoryYuanliao(Map<String,Object> out,HttpServletRequest request,String code) throws Exception{
		Map<String,String> mapy = YuanliaoFacade.getInstance().getChild(code);
		//类别属性(厂家（产地）)
		List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		for(String cc : mapy.keySet()){
			CategoryYuanliao yl = new CategoryYuanliao();
			yl.setCode(cc);
			yl.setLabel(mapy.get(cc));
			list.add(yl);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryCategory(Map<String,Object> out,HttpServletRequest request,String code) throws Exception{
		Map<String,String> mapy = CategoryFacade.getInstance().getChild(code);
		List<CategoryDO> list = new ArrayList<CategoryDO>();
		for(String cc : mapy.keySet()){
			CategoryDO c = new CategoryDO();
			c.setCode(cc);
			c.setLabel(mapy.get(cc));
			list.add(c);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView refreshYuanliao(Map<String,Object> out,HttpServletRequest request,Integer id) throws Exception{
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean isRefresh = false;
		Integer flag = 0;
		do{
			if(sessionUser==null){
				break;
			}
			Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(id);
			if(yuanliao==null){
				break;
			}
			if (StringUtils.isEmpty(sessionUser.getMembershipCode())) {
				sessionUser.setMembershipCode("10051000");
			}
			Date now = new Date();
			Long interval = Long.valueOf(MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(), "refresh_product_interval"));
			long intervalNow = now.getTime() - yuanliao.getRefreshTime().getTime();
			if (intervalNow > (interval.longValue() * 1000)) {
				isRefresh = true;
			}
			if (!isRefresh) {
				if("10051000".equals(sessionUser.getMembershipCode())){
					flag = 1; //一天后继续刷新
				}else{
					flag = 2; //10分钟后继续刷新
				}
				break;
			}
			Integer day = DateUtil.getIntervalDays(yuanliao.getExpireTime(),yuanliao.getRefreshTime());
			yuanliao.setRefreshTime(new Date());
			yuanliao.setExpireTime(DateUtil.getDateAfterDays(new Date(), day));
			yuanliao.setGmtModified(new Date());
			yuanliaoService.updateYuanliao(yuanliao);
			
		}while(false);
		map.put("isRefresh", isRefresh);
		map.put("flag", flag);
		return  printJson(map, out);
	}
}
