package com.ast.ast1949.front.controller.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.dataindex.ProductsIndex;
import com.ast.ast1949.domain.dataindex.ProductsIndexDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.dataindex.DataIndexCategoryService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.dataindex.ProductsIndexService;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

@Controller
public class ChinaController extends BaseController{
	@Resource
	private InquiryService inquiryService;
	@Resource
	private DataIndexService dataIndexService;
	@Resource
	private DataIndexCategoryService dataIndexCategoryService;
	@Resource
	private ProductsIndexService productsIndexService;
	@Resource
	private CompanyService companyService;
	
	@RequestMapping
	public ModelAndView indexByCode(HttpServletRequest request, Map<String, Object> out,
			String code, Integer size)throws IOException{
		if(size!=null && size.intValue()>200){
			size=200;
		}
		List<DataIndexDO> list=dataIndexService.queryDataByCode(code, null, size);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
	
	@RequestMapping
    public ModelAndView newIndexByCode(HttpServletRequest request, Map<String, Object> out,
            String code, Integer size)throws IOException{
        if(size!=null && size.intValue()>200){
            size=200;
        }
        List<DataIndexDO> list=dataIndexService.queryDataByCode(code, null, size);
        for (DataIndexDO di : list) {
            if (StringUtils.isEmpty(di.getLink())) {
                di.setLink("http://trade.zz91.com/trade/s-"+CNToHexUtil.getInstance().encode(di.getTitle())+".html");
            }
        }
        List<DataIndexCategoryDO> codelist = dataIndexCategoryService.queryDataIndexCategoryByPreCode(code);
        
        if (codelist.size() == 0) {
            DataIndexCategoryDO dic = dataIndexCategoryService.queryDataIndexCategoryByCode(code);
            dic.setLink("http://trade.zz91.com/trade/s-"+CNToHexUtil.getInstance().encode(dic.getLabel())+".html");
            codelist.add(dic);
        } else {
            for (DataIndexCategoryDO dic : codelist) {
                dic.setLink("http://trade.zz91.com/trade/s-"+CNToHexUtil.getInstance().encode(dic.getLabel())+".html");
            }
        }
        
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("list", list);
        map.put("codelist", codelist);
        return printJson(map, out);
    }

	@RequestMapping
	public ModelAndView indexMainByCode(HttpServletRequest request, Map<String, Object> out,
			String code, Integer size)throws IOException{
		if(size!=null && size.intValue()>200){
			size=200;
		}
		List<DataIndexDO> list=dataIndexService.queryDataByCode(code, null, size);
		List<DataIndexCategoryDO> codelist = dataIndexCategoryService.queryDataIndexCategoryByPreCode(code);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("map", getMainCode(list,code));
		getFatherCodeName(codelist,map);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView header(HttpServletRequest request, Map<String, Object> out, String ik){
		if(StringUtils.isEmpty("ik")){
			ik = "index";
		}
		out.put("ik", ik);
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		out.put("list", list);
		out.put("adsPhone", ParamUtils.getInstance().getValue("site_info_front","ads_phone"));
		out.put("modelMap", ParamUtils.getInstance().getValue("asto1949_webapp",ik));
		return null;
	}
	
	@RequestMapping
	public ModelAndView topbar(HttpServletRequest request, Map<String, Object> out){
		//qq :100345758
		out.put("qqClientId", ParamUtils.getInstance().getValue("oauth_config", "qqClientId"));
		return null;
	}
	
	@RequestMapping
	public ModelAndView footer(HttpServletRequest request, Map<String, Object> out,String ik){
		if(StringUtils.isEmpty("ik")){
			ik = "index";
		}
		out.put("ik", ik);
		out.put("adsPhone", ParamUtils.getInstance().getValue("site_info_front","ads_phone"));
		out.put("marketPhone", ParamUtils.getInstance().getValue("site_info_front","market_phone"));
		return null;
	}
	
	private Map<String,List<DataIndexDO>> getMainCode(List<DataIndexDO> list,String mainCode){
		Map<String,List<DataIndexDO>> mapList = new TreeMap<String,List<DataIndexDO>>();
		List<DataIndexDO> newlist = new ArrayList<DataIndexDO>();
		for(DataIndexDO obj:list){
			String code = obj.getCategoryCode();
			Integer length = mainCode.length();
			do{
				if(length + 4 != code.length()){
					break;
				}
				
				newlist = mapList.get(code);
				if(newlist==null){
					newlist = new ArrayList<DataIndexDO>();
				}
				newlist.add(obj);
				mapList.put(code, newlist);
			}while(false);
		}
		return mapList;
	}
	private void  getFatherCodeName(List<DataIndexCategoryDO> list,Map<String,Object> out){
		Map<String,Object> map = new HashMap<String,Object>();
		for(DataIndexCategoryDO obj:list){
			if(map.get(obj.getCode())==null){
				map.put(obj.getCode(), obj.getLabel());
			}
		}
		out.put("nameMap", map);
	}
	@RequestMapping
	public ModelAndView indexProductsByCode(HttpServletRequest request, Map<String, Object> out,
			String code, Integer size)throws IOException{
		if(size!=null && size.intValue()>200){
			size=200;
		}
		List<ProductsIndex> list=productsIndexService.queryProductsDateByCode(code, size);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView indexProductsDtoByCode(HttpServletRequest request, Map<String, Object> out,
			String code, Integer size)throws IOException{
		if(size!=null && size.intValue()>200){
			size=200;
		}
		List<ProductsIndex> list=productsIndexService.queryProductsDateByCode(code, size);
		List<ProductsIndexDto> nlist = new ArrayList<ProductsIndexDto>();
		for(ProductsIndex obj:list){
			ProductsIndexDto dto = new ProductsIndexDto();
			dto.setProductsIndex(obj);
			String name = companyService.queryCompanyNameById(obj.getCompanyId());
			if(StringUtils.isEmpty(name)){
				name = "ZZ91再生网废料商";
			}
			dto.setCompanyName(name);
			nlist.add(dto);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", nlist);
		return printJson(map, out);
	}
	
	//最新开通的再生通会员
	@RequestMapping
	public ModelAndView recentZst(Map<String, Object> out, Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> list=companyService.queryRecentZst(size);
		map.put("list", list);
		return printJson(map, out);
	}
	
}