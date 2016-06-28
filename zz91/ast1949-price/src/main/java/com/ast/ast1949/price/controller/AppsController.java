package com.ast.ast1949.price.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class AppsController extends BaseController {
	@Resource
	private PriceService priceService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CompanyService companyService;

	@RequestMapping
	public ModelAndView bottomNavigate(HttpServletRequest request,
			Map<String, Object> out, String code) {
		// 悬浮框资讯
		out.put("susList", JSONArray.fromObject(
				priceService.queryPriceByTypeId(RIPINID.get(code), null, null,
						6)).toString());
		// 悬浮框供求
		out.put("suspList", JSONArray.fromObject(
				productsService.queryProductsByMainCode(PRODUCT_CODE.get(code),
						null, 6)).toString());
		out.put("smsCode", PRODUCT_CODE.get(code));
		return null;
	}
	
//	@RequestMapping
//	public ModelAndView validateLog(HttpServletRequest request,
//			Map<String, Object> out,Integer id,HttpServletResponse response) throws IOException {
//		SsoUser ssoUser = getCachedUser(request);
//		Map<String,Object >map = new HashMap<String, Object>();
//		if(ssoUser!=null){
//			map.put("isLogged", 1);
//			map.put("content", priceService.queryPriceByIdForEdit(id).getPrice().getContent());
//		}
//		PageCacheUtil.setNoCDNCache(response);
//		return printJson(map, out);
//	}
	
	final static Map<String, Integer> RIPINID = new HashMap<String, Integer>();
	static {
		RIPINID.put("metal", 32);
		RIPINID.put("plastic", 34);
		RIPINID.put("paper", 36);
	}
	final static Map<String, String> PRODUCT_CODE = new HashMap<String, String>();
	static {
		PRODUCT_CODE.put("metal", "1000");
		PRODUCT_CODE.put("plastic", "1004");
		PRODUCT_CODE.put("paper", "1000");
	}
	
	/**
	 * 东莞塑胶网合作 页面 接口
	 * 查询企业报价
	 * @param keywords
	 * @param areaCode
	 * @param date
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView dgs6(Map<String, Object>out,String title,String areaCode,String date,String size) throws IOException{
		if(StringUtils.isEmpty(size)){
			size = "20";
		}
		if(!StringUtils.isNumber(size)){
			size = "20";
		}
		
		if(Integer.valueOf(size)>20){
			size = "20";
		}
		List<CompanyPriceDO> list = companyPriceService.queryCompanyPriceByRefreshTime(title, Integer.valueOf(size));
		List<JSONObject> newList = new ArrayList<JSONObject>();
		for (CompanyPriceDO obj:list) {
			JSONObject js = new JSONObject();
			js.put("title", obj.getTitle());
			js.put("date", DateUtil.toString(obj.getGmtCreated(), "yyyy-MM-dd"));
			js.put("minPrice", obj.getMinPrice());
			js.put("maxPrice", obj.getMaxPrice());
			js.put("unit", obj.getPriceUnit());
			Company company = companyService.queryCompanyById(obj.getCompanyId());
			if(company==null){
				continue;
			}
			js.put("name", companyService.queryCompanyNameById(obj.getCompanyId()));
			String areaName ="";
			if (StringUtils.isEmpty(obj.getAreaCode())) {
				obj.setAreaCode(company.getAreaCode());
			}
			if (StringUtils.isEmpty(obj.getAreaCode())) {
				continue;
			}
			if(obj.getAreaCode().length()>=8&&!"10011000".equals(obj.getAreaCode().substring(0,8))){
				areaName += CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0,8));
			}
			if(obj.getAreaCode().length()>=12){
				areaName += CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0,12));
			}
			if(obj.getAreaCode().length()>=16){
				areaName += CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0,16));
			}
			if(StringUtils.isEmpty(areaName)){
				continue;
			}
			js.put("areaName", areaName);
			newList.add(js);
		}
		JSONArray ja = JSONArray.fromObject(newList);
		return printJson(ja, out);
	}
	@RequestMapping
    public ModelAndView buildTemplateContent(HttpServletRequest request,
            Map<String, Object> out, String priceSearchKey,
            String zaobaoSearchKeyString, String wanbaoSearchKeyString, String code)
            throws IOException {
       String content = "";
       priceSearchKey = StringUtils.decryptUrlParameter(priceSearchKey);
       zaobaoSearchKeyString = StringUtils.decryptUrlParameter(zaobaoSearchKeyString);
       wanbaoSearchKeyString = StringUtils.decryptUrlParameter(wanbaoSearchKeyString);
       code = StringUtils.decryptUrlParameter(code);
       content = priceService.buildTemplateContent(priceSearchKey, zaobaoSearchKeyString, wanbaoSearchKeyString, code);

       Map<String , Object> contentMap = new HashMap<String, Object>();
       contentMap.put("content", content);
       return printJson(contentMap, out);
    }
    @RequestMapping
    public ModelAndView queryContentByPageEngine(HttpServletRequest request,
            Map<String, Object> out, String priceSearchKey,
            String zaobaoSearchKeyString, String wanbaoSearchKeyString)
            throws IOException {

        String content = "";
        priceSearchKey = StringUtils.decryptUrlParameter(priceSearchKey);
        zaobaoSearchKeyString = StringUtils.decryptUrlParameter(zaobaoSearchKeyString);
        wanbaoSearchKeyString = StringUtils.decryptUrlParameter(wanbaoSearchKeyString);
        content = priceService.queryContentByPageEngine(priceSearchKey, zaobaoSearchKeyString, wanbaoSearchKeyString);

        Map<String , Object> contentMap = new HashMap<String, Object>();
        contentMap.put("content", content);
        return printJson(contentMap, out);
    }
	
}
