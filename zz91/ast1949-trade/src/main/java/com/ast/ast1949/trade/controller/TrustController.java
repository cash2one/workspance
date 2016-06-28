package com.ast.ast1949.trade.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.ast.ast1949.service.trust.TrustSellService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

import net.sf.json.JSONArray;

@Controller
public class TrustController extends BaseController {
	@Resource
	private TrustBuyService trustBuyService;
	@Resource
	private TrustSellService trustSellService;

	@RequestMapping
	public ModelAndView index(TrustBuySearchDto searchDto,Map<String, Object> out,HttpServletRequest request, PageDto<TrustBuyDto> page) throws Exception {
		SsoUser ssoUser = getCachedUser(request);
		page.setPageSize(10);
		searchDto.setIsFront(true);
		page.setSort("gmt_refresh");
		page.setDir("desc");
		searchDto.setIsPause(0);
		page = trustBuyService.page(searchDto,page);
		if(ssoUser!=null){
			// 该用户供过货的采购
			List<Integer> list = trustSellService.queryBuyIdByCompanyId(ssoUser.getCompanyId());
			for(TrustBuyDto dto:page.getRecords()){
				if(list.contains(dto.getTrustBuy().getId())){
					dto.setIsGong("1");
				}else{
					dto.setIsGong("0");
				}
			}
		}
		out.put("page", page);
		// 交易进展实时播报
		PageDto<TrustBuyDto> pageTrade = new PageDto<TrustBuyDto>();
		TrustBuySearchDto cDto = new TrustBuySearchDto();
		pageTrade.setPageSize(10);
		cDto.setIsFront(true);
		cDto.setIsPause(0);
		pageTrade.setSort("gmt_modified");
		pageTrade.setDir("desc");
		pageTrade = trustBuyService.page(cDto, pageTrade);
		out.put("pageTrade", pageTrade.getRecords());
		
		// 登陆状态下获取该公司采购供货信息
		if (ssoUser!=null) {
			out.put("buyNum", trustBuyService.countByCompanyId(ssoUser.getCompanyId()));
			out.put("sellNum", trustSellService.countByCompanyId(ssoUser.getCompanyId()));
		}
		
		// seo配置
		SeoUtil.getInstance().buildSeo("trust", out);
		
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doEntrust(Map<String, Object> out,HttpServletRequest request, String detail,String companyName,String companyContact,String mobile) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		do {
			if (StringUtils.isNotEmpty(detail) && !StringUtils.isContainCNChar(detail)) {
				detail = StringUtils.decryptUrlParameter(detail);
			}
			if (StringUtils.isNotEmpty(companyName) && !StringUtils.isContainCNChar(companyName)) {
				companyName = StringUtils.decryptUrlParameter(companyName);
			}
			if (StringUtils.isNotEmpty(companyContact) && !StringUtils.isContainCNChar(companyContact)) {
				companyContact = StringUtils.decryptUrlParameter(companyContact);
			}
			if (StringUtils.isNotEmpty(mobile) && !StringUtils.isContainCNChar(mobile)) {
				mobile = StringUtils.decryptUrlParameter(mobile);
			}
			if (StringUtils.isNotEmpty(mobile)) {
				Integer i = trustBuyService.publishBuyWithoutLogin(companyName, companyContact, mobile, detail);
				map.put("sw", i);
			}else{
				SsoUser user = getCachedUser(request);
				if (user == null) {
					break;
				}
				Integer i = trustBuyService.publishBuy(user.getCompanyId(), detail);
				map.put("sw", i);
			}
		} while (false);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView doEntrustForTZS(Map<String, Object> out,HttpServletRequest request, String detail,String companyName,String companyContact,String mobile) throws IOException {
		String str = "var result=";
		do {
			if (StringUtils.isNotEmpty(detail) && !StringUtils.isContainCNChar(detail)) {
				detail = StringUtils.decryptUrlParameter(detail);
			}
			if (StringUtils.isNotEmpty(companyName) && !StringUtils.isContainCNChar(companyName)) {
				companyName = StringUtils.decryptUrlParameter(companyName);
			}
			if (StringUtils.isNotEmpty(companyContact) && !StringUtils.isContainCNChar(companyContact)) {
				companyContact = StringUtils.decryptUrlParameter(companyContact);
			}
			if (StringUtils.isNotEmpty(mobile) && !StringUtils.isContainCNChar(mobile)) {
				mobile = StringUtils.decryptUrlParameter(mobile);
			}
			Integer i = 0;
			if (StringUtils.isNotEmpty(mobile)) {
				i = trustBuyService.publishBuyWithoutLogin(companyName, companyContact, mobile, detail);
			}
			str = str+i+";";
		} while (false);
		return printJs(str, out);
	}

	@RequestMapping
	public ModelAndView form(Map<String, Object> out,HttpServletRequest request, Integer id) {
		SsoUser user = getCachedUser(request);
		Integer loginFlag = 0;
		if (user != null) {
			loginFlag = 1;
		}
		out.put("loginFlag", loginFlag);
		out.put("mid", id);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doForm(Map<String, Object> out,	HttpServletRequest request, Integer id, String content)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SsoUser user = getCachedUser(request);
		if (user==null) {
			return printJson(map, out);
		}
		Integer i = trustSellService.publishTrustSell(user.getCompanyId(), id,content);
		map.put("sw", i);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView doFormTZS(Map<String, Object> out,	HttpServletRequest request, Integer buyId, String content)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer i = trustSellService.publishTrustSell(0, buyId,content);
		map.put("sw", i);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView form2(Map<String, Object> out,HttpServletRequest request) {
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView submit(Map<String, Object> out,HttpServletRequest request,String details) throws UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(details)&&!StringUtils.isContainCNChar(details)) {
			details = StringUtils.decryptUrlParameter(details);
		}
		out.put("details", details);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView success(Map<String, Object> out,HttpServletRequest request) {
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return new ModelAndView("submitCallback");
	}
	
	/**
	 * 获取采购频道最新采购单
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView queryLatestBuy(Map<String, Object>out,Integer size) throws IOException{
		if (size>50) {
			size = 50;
		}
		if (size<1) {
			size=1;
		}
		PageDto<TrustBuyDto> page = new PageDto<TrustBuyDto>();
		page.setPageSize(size);
		TrustBuySearchDto searchDto = new TrustBuySearchDto();
		searchDto.setIsFront(true);
		page.setSort("gmt_refresh");
		page.setDir("desc");
		searchDto.setIsPause(0);
		page = trustBuyService.pageSimple(searchDto,page);
		return printJson(JSONArray.fromObject(page.getRecords()), out);
	}
	
	/**
	 * 获取采购频道最新采购单
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView queryBuyForJson(Map<String, Object>out,Integer id) throws IOException{
		return printJson(trustBuyService.queryTrustById(id), out);
	}
	
	@RequestMapping
	public ModelAndView queryIngBuy(Map<String, Object> out,Integer size) throws IOException{
		if (size>50) {
			size = 50;
		}
		if (size<1) {
			size=1;
		}
		// 交易进展实时播报
		PageDto<TrustBuyDto> pageTrade = new PageDto<TrustBuyDto>();
		TrustBuySearchDto cDto = new TrustBuySearchDto();
		pageTrade.setPageSize(size);
		cDto.setIsFront(true);
		pageTrade.setSort("gmt_modified");
		pageTrade.setDir("desc");
		//暂不发布的信息，无论什么交易状态都不能在前台显示
		cDto.setIsPause(0);
		pageTrade = trustBuyService.page(cDto, pageTrade);
//		out.put("pageTrade", pageTrade.getRecords());
		return printJson(JSONArray.fromObject(pageTrade.getRecords()), out);
	}
}
