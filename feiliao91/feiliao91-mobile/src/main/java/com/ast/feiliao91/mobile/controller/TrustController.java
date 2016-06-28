package com.ast.feiliao91.mobile.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

@Controller
public class TrustController extends BaseController{
	
	@RequestMapping
	public ModelAndView cgDetail(Map<String, Object>out,Integer id){
		do {
			String result = HttpUtils.getInstance().httpGet("http://caigou.zz91.com/queryBuyForJson.htm?id="+id,HttpUtils.CHARSET_UTF8);
			if (StringUtils.isEmpty(result) || "{}".equals(result)||"null".equals(result)) {
				break;
			}
			JSONObject jo = JSONObject.fromObject(result);
			out.put("trustBuy", jo);
			out.put("trustPoint",getPoint(jo.getString("areaCode")));
		} while (false);
		return new ModelAndView();
	}
	
	//根据物理地址获取经纬度坐标
	private JSONObject getPoint(String areaCode){
		String address = CategoryFacade.getInstance().getValue(areaCode);
		if (StringUtils.isEmpty(address)) {
			return null;
		}
		if("河南".equals(address)||"陕西".equals(address) ||"甘肃".equals(address) ||"青海".equals(address) ||"河北".equals(address)||"海南".equals(address)){
			address=address+"省";
		}
		if("宁夏".equals(address)){
			address=address+"回族自治区";
		}
		if("新疆".equals(address)){
			address=address+"维吾尔自治区";
		}
		try {
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
		} catch (Exception e) {
			return null;
		}
	}
}