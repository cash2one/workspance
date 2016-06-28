package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketSearchDto;
import com.ast.ast1949.dto.market.MarketSubscribeDto;
import com.ast.ast1949.persist.market.MarketSubscribeDao;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;

@Controller
public class MarketController extends BaseController {
	@Resource
	private MarketService marketService;
	@Resource
	private MarketSubscribeDao marketSubscribeDao;

	@RequestMapping
	public ModelAndView index() {
		return null;
	}

	@RequestMapping
	public ModelAndView queryAllMarket(Map<String, Object> out, PageDto<Market> page, Integer type) throws IOException {
		page.setPageSize(20);
		page = marketService.queryAllMarket(page, null);
		return printJson(page, out);
	}

//	@RequestMapping
//	public ModelAndView subscribe(Map<String, Object>out) throws IOException{
//		return printJson(marketSubscribeDao.queryByCompanyId(0, 5).size(), out);
//	}
	
	/**
	 * 订阅默认页
	 * @author zhujq
	 * @return
	 */
	@RequestMapping
	public ModelAndView subscribe(){
		return null;
	}
	
	/**
	 * 订阅搜索
	 * @author zhujq
	 * @param out
	 * @param page
	 * @param searchDto
	 * @return
	 */
	@RequestMapping
	public ModelAndView querySubscribe(Map<String, Object> out,MarketSearchDto searchDto,PageDto<MarketSubscribeDto> page) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_created");
		}
		page = marketService.pageSubscribeByAdmin(searchDto,page);
		return printJson(page, out);
	}
	
	
	@RequestMapping
	public ModelAndView change(Map<String, Object>out) throws IOException{
		System.out.println(marketSubscribeDao.updateToDel(6, 1));
		return printJson(new HashMap(), out);
	}
	
	
}
