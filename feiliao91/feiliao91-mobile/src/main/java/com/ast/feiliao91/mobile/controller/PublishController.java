/**
 * @author zhujq
 * @data 2016-06-20
 * @describe 发布产品控制器
 */
package com.ast.feiliao91.mobile.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsCategory;
import com.ast.feiliao91.domain.goods.Sample;
import com.ast.feiliao91.mobile.controller.BaseController;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.facade.GCategoryFacade;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.goods.SampleService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class PublishController extends BaseController{
	@Resource
	private AddressService addressService;
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private PictureService pictureService;
	@Resource
	private SampleService sampleService;
	
	/**
	 * 发布页
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView publishGoods(Map<String, Object> out,HttpServletRequest request){
		SsoUser user = getCachedUser(request);
		SeoUtil.getInstance().buildSeo("step1", out);
		//获得默认的发货地址
		Address address  = addressService.selectDefaultDelAddress(user.getCompanyId());
		if (address != null) {
			String addrStr = getArea(address.getAreaCode()) + address.getAddress();
			out.put("addrStr",addrStr);
			out.put("addr",address);
		}
		return null;
	}
	
	private String getArea(String areaCode) {
		String str = "";
		Integer i = 8;
		String tempCode = "";
		do {
			String fix = "";
			if (StringUtils.isEmpty(areaCode)) {
				break;
			}
			i = i + 4;
			if (areaCode.length() < i) {
				break;
			}
			tempCode = areaCode.substring(0, i);
			if (i == 12) {
				fix = "省";
			} else if (i == 16) {
				fix = "市";
			}
			str = str + CategoryFacade.getInstance().getValue(tempCode) + fix;
		} while (true);
		return str;
	}
	
	/**
	 * 保存发布
	 * @param out
	 * @param request
	 * @param goods
	 * @param sampleStr
	 * @param limitDay
	 * @param pImg
	 * @return
	 */
	@RequestMapping
	public ModelAndView doSave(Map<String, Object> out,HttpServletRequest request,Goods goods,String sampleStr,Integer limitDay,String pImg){
		//先插产品,再插样品和图片
		SsoUser user = getCachedUser(request);
		boolean success = false;
		do {
			if (user == null) {
				break;
			}
			goods.setExpireTime(DateUtil.getDateAfterDays(new Date(), limitDay));
			goods.setCompanyId(user.getCompanyId());
			goods.setUnit("吨");
			Integer i = goodsService.createGoods(goods);
			if (i > 0) {
				if (StringUtils.isNotEmpty(pImg)) {
					pictureService.dealPicture(pImg, i, "1");
				}
				if (StringUtils.isNotEmpty(sampleStr)){
					//解析样品字符窜为json,并插入数据库
					boolean sampleReturn = dealSample(sampleStr,i);
					if (!sampleReturn) {
						break;
					}
				}
			}
		} while (false);
		if (success) {
			return new ModelAndView("redirect:/publish/publishSucc.htm");
		}else {
			return new ModelAndView("redirect:/publish/publishGoods.htm");
		}
	}
	
	/**
	 * 处理样品数据
	 * @param sampleStr
	 * @return
	 */
	private boolean dealSample(String sampleStr,Integer goodsId){
		JSONObject jsonObject = JSONObject.fromObject(sampleStr);
		List<Sample> list =new ArrayList<Sample>();
		try {
			JSONArray array = jsonObject.getJSONArray("samples");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object = (JSONObject)array.get(i);
				Sample sample =  (Sample)JSONObject.toBean(object,
						Sample.class);
				if(sample != null){
					sample.setGoodsId(goodsId);
					list.add(sample);
				}
			}
		} catch (Exception e) {
			return false;
		}
		if(list.size()>0){
			//批量插入样品
			Integer i = sampleService.iterateInsert(list);
			if(i > 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 发布成功
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView publishSucc(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView goodsChild(Map<String, Object> out, String parentCode) throws IOException {
		List<GoodsCategory> list = new ArrayList<GoodsCategory>();
		Map<String, String> map = GCategoryFacade.getInstance().getChild(parentCode);
		if (map == null) {
			return printJson(list, out);
		}
		for (Entry<String, String> m : map.entrySet()) {
			GoodsCategory c = new GoodsCategory();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		return printJson(list, out);
	}	
	
}