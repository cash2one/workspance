package com.ast.ast1949.market.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.MarketDo;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class MapController extends BaseController{
	@Resource
	private MarketService marketService;
	@RequestMapping
	public ModelAndView zsmap(Map<String, Object> out) throws Exception{
		Map<String,String> map=CategoryFacade.getInstance().getChild("10011000");
		 JSONArray json = new JSONArray();
		for(String s:map.keySet()){
			if("100110001031".equals(s)){
				continue;
			}
			JSONObject jo = new JSONObject();
			jo=getPoint(map.get(s),null);
			if(jo!=null){
				jo.put("province",s);
				json.put(jo);
			}
		}
		out.put("json", json);
		SeoUtil.getInstance().buildSeo("zsmap",out);
		return null;
	}
	@RequestMapping
	public ModelAndView zsmapDetail(Map<String, Object> out,String province,String category) throws Exception{
		List<Market> list=marketService.queryMarketByProOrCate(CategoryFacade.getInstance().getValue(province), CATEGORY_MAP.get(category));
		JSONArray json = new JSONArray();
		for(Market market:list){
			JSONObject jo = new JSONObject();
			jo=getPoint(market.getAddress(),market.getId());
			jo.put("name", market.getName());
			jo.put("companyNum", market.getCompanyNum());
			jo.put("productNum", market.getProductNum());
			jo.put("business", market.getBusiness());
			jo.put("id",market.getWords());
			json.put(jo);
		}
		if(StringUtils.isNotEmpty(province)){
			JSONObject point = new JSONObject();
			point=getPoint(CategoryFacade.getInstance().getValue(province),1);
			out.put("point", point);
			if("吉林".equals(CategoryFacade.getInstance().getValue(province))){
				out.put("name", "吉林省");
			}else if("海南".equals(CategoryFacade.getInstance().getValue(province))){
				out.put("name", "海南省");	
			}else{
				out.put("name", CategoryFacade.getInstance().getValue(province));
			}
			out.put("flag", 1);
		}
		if(StringUtils.isNotEmpty(category)){
			out.put("name", CATEGORY_MAP.get(category));
		}
		out.put("json", json);
		out.put("list", list);
		if(StringUtils.isNotEmpty(category)){
			SeoUtil.getInstance().buildSeo("zsmapDetail",new String[]{CATEGORY_MAP.get(category)},new String[]{CATEGORY_MAP.get(category)},new String[]{CATEGORY_MAP.get(category)},out);
		}else if(StringUtils.isNotEmpty(province)){
			SeoUtil.getInstance().buildSeo("zsmapDetail",new String[]{CategoryFacade.getInstance().getValue(province)},new String[]{CategoryFacade.getInstance().getValue(province)},new String[]{CategoryFacade.getInstance().getValue(province)},out);
		}
		return null;
	}
	//根据物理地址获取经纬度坐标
		public JSONObject getPoint(String address,Integer marketId) throws Exception{
			MarketDo marketDo=marketService.queryNumByProvince(address);
			if("河南".equals(address)||"陕西".equals(address) ||"甘肃".equals(address) ||"青海".equals(address) ||"河北".equals(address)||"海南".equals(address)){
				address=address+"省";
			}
			if("宁夏".equals(address)){
				address=address+"回族自治区";
			}
			if("新疆".equals(address)){
				address=address+"维吾尔自治区";
			}
			JSONObject jo = new JSONObject();
			BufferedReader in = null;
			//将地址转换成utf-8的16进制
			address = URLEncoder.encode(address, "UTF-8");
			URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=702632E1add3d4953d0f105f27c294b9");
			in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while((res = in.readLine())!=null){
				sb.append(res.trim());
			}
			String str = sb.toString();
			JSONObject js = JSONObject.fromObject(str);
			JSONObject obj=JSONObject.fromObject(js.get("result"));
			if(obj.toString().contains("location")){
				JSONObject adr=JSONObject.fromObject(obj.get("location"));
				address=URLDecoder.decode(address, "UTF-8");
				if(marketId==null){
					jo.put("marketNum", marketDo.getMarketNum());
					jo.put("companyNum", marketDo.getCompanyNum());
					jo.put("productNum", marketDo.getProductNum());
				}else{
					
				}
				if("河南省".equals(address)||"陕西省".equals(address) ||"甘肃省".equals(address) ||"青海省".equals(address) ||"河北省".equals(address)||"海南".equals(address)){
					address=address.substring(0, address.length()-1);
				}
				if("宁夏回族自治区".equals(address)){
					address=address.substring(0, address.length()-5);
				}
				if("新疆维吾尔自治区".equals(address)){
					address=address.substring(0, address.length()-6);
				}
				jo.put("address", address);
				jo.put("lng", adr.get("lng"));
				jo.put("lat", adr.get("lat"));
			}
			return jo;
		}
		/**
		 * 首页产品类目，自编
		 */
		final static Map<String, String> CATEGORY_MAP = new HashMap<String, String>();
		static {
			CATEGORY_MAP.put("1000", "废金属");
			CATEGORY_MAP.put("10001000", "废钢铁");
			CATEGORY_MAP.put("10001001", "有色金属");
			CATEGORY_MAP.put("10001010", "稀贵金属");
			CATEGORY_MAP.put("10001011", "混合金属");//混合/复合
			CATEGORY_MAP.put("1001", "废塑料");
			CATEGORY_MAP.put("10011000", "再生颗粒");
			CATEGORY_MAP.put("10011001", "塑料助剂");
			CATEGORY_MAP.put("10011010", "废塑料加工设备");//塑料加工设备
			CATEGORY_MAP.put("1002", "二手设备");
			CATEGORY_MAP.put("100210000", "交通工具");
			CATEGORY_MAP.put("100210001", "机床设备");
			CATEGORY_MAP.put("100210010", "工程设备");
			CATEGORY_MAP.put("100210011", "化工设备");
			CATEGORY_MAP.put("100211000", "制冷设备");
			CATEGORY_MAP.put("100211001", "纺织设备");
			CATEGORY_MAP.put("100211010", "电子设备");
			CATEGORY_MAP.put("100211011", "电力设备");
			CATEGORY_MAP.put("100211100", "矿业设备");
			CATEGORY_MAP.put("100211101", "塑料设备");
			CATEGORY_MAP.put("100211110", "印刷设备");
		}
}
