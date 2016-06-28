/**
 * @author shiqp
 * @date 2015-05-13
 */
package com.ast.ast1949.myrc.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustSell;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustSellDto;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.ast.ast1949.service.trust.TrustSellService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;

@Controller
public class MytrustController extends BaseController{
	@Resource
	private TrustBuyService trustBuyService;
	@Resource
	private TrustSellService trustSellService;
	@Resource
	private MyrcService myrcService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView purchase(Map<String, Object> out,HttpServletRequest request,TrustBuySearchDto dto,PageDto<TrustBuyDto> page){
		SsoUser user = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, user.getCompanyId());
		dto.setCompanyId(user.getCompanyId());
		page.setPageSize(10);
		page.setSort("gmt_created");
		page.setDir("desc");
		page=trustBuyService.page(dto, page);
		out.put("page", page);
		out.put("searchDto", dto);
		return new ModelAndView();
	}
    @RequestMapping
    public ModelAndView stopFp(Map<String, Object> out,HttpServletRequest req) throws IOException{
    	ExtResult rs = new ExtResult();
    	String id = req.getParameter("id");
    	trustBuyService.batchUpdatePauseById(id, 1);
    	rs.setSuccess(true);
    	return printJson(rs, out);
    }
    @RequestMapping
    public ModelAndView startFp(Map<String, Object> out,HttpServletRequest req) throws IOException{
    	ExtResult rs = new ExtResult();
    	String id = req.getParameter("id");
    	trustBuyService.batchUpdatePauseById(id, 0);
    	rs.setSuccess(true);
    	return printJson(rs, out);
    }
	@RequestMapping
	public ModelAndView doEntrust(HttpServletRequest request,Map<String, Object> out,TrustBuy buy) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		SsoUser user = getCachedUser(request);
		if (StringUtils.isNotEmpty(buy.getDetail()) && !StringUtils.isContainCNChar(buy.getDetail())) {
			// 解密
			buy.setDetail(StringUtils.decryptUrlParameter(buy.getDetail()));
		}
		Integer i=trustBuyService.publishBuy(user.getCompanyId(), buy.getDetail());
		map.put("sw", i);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView supply(HttpServletRequest request,Map<String, Object> out,PageDto<TrustSellDto> page,TrustSell sell){
		SsoUser user = getCachedUser(request);
		sell.setCompanyId(user.getCompanyId());
		page.setPageSize(10);
		page.setSort("ts.gmt_created");
		page.setDir("desc");
		page=trustSellService.pageSupplyByCondition(sell, page);
		out.put("page", page);
		out.put("sell", sell);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView entrust(Map<String, Object> out){
		return new ModelAndView();
	}

}
