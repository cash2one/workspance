/**
 * @author shiqp
 * @date 2015-03-09
 */
package com.ast.ast1949.api.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.market.ProductMarketService;
import com.ast.ast1949.util.StringUtils;

@Controller
public class MarketController extends BaseController {
	@Resource
	private DataIndexService dataIndexService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private MarketService marketService;
	@Resource
	private ProductMarketService productMarketService;

	/**
	 * 首页热门市场推荐
	 * 
	 * @param request
	 * @param out
	 * @param code
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView indexByCode(HttpServletRequest request, Map<String, Object> out, String code, Integer size)
			throws IOException {
		List<DataIndexDO> list = dataIndexService.queryDataByCode(code, null, size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryNewCompany(HttpServletRequest request, Map<String, Object> out, Integer marketId,
			Integer size) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", marketCompanyService.queryNewCompany(marketId, size));
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryMarketByCondition(Map<String, Object> out, String industry, String area, Integer size)
			throws IOException {
		if (size == null) {
			size = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(industry) && !StringUtils.isContainCNChar(industry)) {
			// 解密
			industry = StringUtils.decryptUrlParameter(industry);
		}
		if (StringUtils.isNotEmpty(area) && !StringUtils.isContainCNChar(area)) {
			// 解密
			area = StringUtils.decryptUrlParameter(area);
		}
		map.put("list", marketService.queryMarketByCondition(industry, area, size));
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryCompanyByCondition(Map<String, Object> out, Integer id, Integer size, Integer type)
			throws IOException {
		if (size == null) {
			size = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		PageDto<CompanyDto> page = new PageDto<CompanyDto>();
		page.setPageSize(size);
		page = marketCompanyService.PageSearchCompanyByCondition(page, id, type);
		map.put("list", page.getRecords());
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryProductByCondition(Map<String, Object> out, Integer id, Integer size, String type,
			String industry) throws IOException {
		if (size == null) {
			size = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null) {
			map.put("list", productMarketService.queryProductMarketBySize(size));
		} else {
			if (id != null && type != null) {
				Market market = marketService.queryMarketById(id);
				if (market != null) {
					if ("废金属".equals(market.getIndustry())) {
						industry = "1000";
					} else if ("废塑料".equals(market.getIndustry())) {
						industry = "1001";
					} else if ("二手设备".equals(market.getIndustry())) {
						industry = "1007";
					}
				}
			}
			PageDto<ProductsDto> page = new PageDto<ProductsDto>();
			page.setPageSize(size);
			page = productMarketService.pageSearchProducts(page, id, type, industry, 0, null, null);
			map.put("list", page.getRecords());
		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryMarketAndCompany(Map<String, Object> out, String industry, String province)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapn = new HashMap<String, Object>();
		PageDto<Market> pageM = new PageDto<Market>();
		pageM.setPageSize(10);
		PageDto<CompanyDto> pageC = new PageDto<CompanyDto>();
		pageC.setPageSize(3);
		if ("10001000".equals(industry) || "10001001".equals(industry) || "10001007".equals(industry)) {
			if ("10001000".equals(industry)) {
				industry = "废塑料";
			} else if ("10001001".equals(industry)) {
				industry = "废金属";
			} else if ("10001001".equals(industry)) {
				industry = "二手设备";
			}
		}
		if (StringUtils.isNotEmpty(province) && province.length() > 12) {
			province = province.substring(0, 12);
		}
		if (StringUtils.isNotEmpty(province) && province.length() > 11) {
			province = CategoryFacade.getInstance().getValue(province);
		}
		pageM = marketService.pageSearchOfMarket(province, null, industry, 0, null, pageM, null, 0);
		if (pageM.getRecords() != null) {
			for (Market market : pageM.getRecords()) {
				pageC = marketCompanyService.PageSearchCompanyByCondition(pageC, market.getId(), 3);
				mapn.put(market.getWords(), pageC.getRecords());
			}
		}
		map.put("pageM", pageM.getRecords());
		map.put("mapn", mapn);
		return printJson(map, out);
	}

}
