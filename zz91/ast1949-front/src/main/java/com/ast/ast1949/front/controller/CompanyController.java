package com.ast.ast1949.front.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class CompanyController extends BaseController {

	@Resource
	private CategoryService categoryService;

	private final static String DEFAULT_CITY_CODE = "10011000";

	/**
	 * 公司黄页 首页面
	 * 
	 * @param out
	 * @param searchName
	 *            搜索关键字
	 * @param industryCode
	 *            所属行业
	 * @param areaCode
	 *            所属地区
	 * @param gardenCode
	 *            所属园区
	 * @param p
	 *            分页号
	 * @param pc
	 *            产品分类CODE
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, String keywords, Company company, PageDto page)
			throws UnsupportedEncodingException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("company")+"/index.htm");
	}

	final static int DEFAULT_PRODUCTS_LIST = 12;

	@SuppressWarnings("rawtypes")
	@RequestMapping
	public ModelAndView compinfo(HttpServletRequest request, String id, PageDto page,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("company")+"/compinfo"+id+".htm");
	}


	@RequestMapping
	public ModelAndView getProvince(Map<String, Object> model) throws IOException {
		return printJson(JSONArray.fromObject(categoryService.child(DEFAULT_CITY_CODE)), model);
	}

//	@RequestMapping
//	public ModelAndView getGardens(String code, Map<String, Object> model) throws IOException {
//		CategoryGardenDO categoryGardenDO = new CategoryGardenDO();
//		categoryGardenDO.setAreaCode(code);
//		List<CategoryGardenDO> list = categoryGardenService
//				.queryCategoryGardenByAreaCode(categoryGardenDO);
//		return printJson(list, model);
//	}

	@RequestMapping(value = "getCity.htm", method = RequestMethod.GET)
	public ModelAndView getCity(Map<String, Object> model, String pid) throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.child(pid));
		return printJson(json, model);
	}

	@RequestMapping(value = "getCounty.htm", method = RequestMethod.GET)
	public ModelAndView getCounty(Map<String, Object> model, String cid) throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.child(cid));
		return printJson(json, model);
	}

	@RequestMapping(value = "getIndustryCodeByCode.htm", method = RequestMethod.GET)
	public ModelAndView getIndustryCodeByCode(String code, Map<String, Object> out)
			throws IOException {
		JSONArray json = JSONArray.fromObject(categoryService.queryCategoriesByPreCode(code));
		return printJson(json, out);
	}

	/**
	 * <br />
	 * 创建图片联系方式 <br />
	 * 当用户访问公司详细信息页面和供求详细页面，并在访问过程中点击查看联系方式 <br />
	 * 按钮时，系统通过这个请求将contact,(tel_country_code,tel_area_code,tel), <br />
	 * mobile,email生成图片联系方式及对应的key，并存放到缓存中（1分钟） <br />
	 * <br />
	 * 规则如下 <br />
	 * 发布者 查看者 结果 限制(每天) <br />
	 * 再生通 普通 true 无限制 <br />
	 * 再生通 再生通 true 无限制 <br />
	 * 普通 普通 false 0 <br />
	 * 普通 再生通 true 200（普通会员） <br />
	 * <br />
	 * 当结果为true时，帮助用户查找联系方式 <br />
	 * 当结果为true时，记录操作AnalysisOptNumDailyService.OPT_BE_VIEWED_CONTACT的次数 <br />
	 * 当结果为true，并且发布者为普通会员时，记录操作AnalysisOptNumDailyService.OPT_VIEW_CONTACT_PAID_FALSE的次数
	 * 
	 * @param cid
	 * @return ExtResult.success：true 表示联系方式生成成功,此时，ExtResult.data中存放的对象 是: CompanyContactsDO对象对应的Json对象
	 *         ExtResult.success：false 表示联系方式生成失败，错误原因由ExtResult.data提供
	 */
	@RequestMapping
	public ModelAndView createImageContactInfo(Map<String, Object> out, Integer cid,
			HttpServletRequest request) throws IOException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("company")+"/createImageContactInfo.htm");
	}

	final static int INFO_CACHE_TIME = 60;

	String sendInfoToCache(String info) {
		if (StringUtils.isNotEmpty(info)) {
			String key = UUID.randomUUID().toString();
			MemcachedUtils.getInstance().getClient().set(key, INFO_CACHE_TIME, info);
			return key;
		}
		return null;
	}

	/**
	 * <br />
	 * 获取图片联系方式 <br />
	 * 当用户访问公司详细信息页面和供求详细页面，点击了查看联系方式按钮，并成功返 <br />
	 * 回时，系统通过这个请求，分别获取同一公司信息的不同联系方式
	 * 
	 * @param type
	 *            ：联系方式类型，null或空不获取信息
	 * @param key
	 *            ：每个联系方式对应的key，null或空则不获取任何信息
	 * @return
	 */
	@RequestMapping
	public ModelAndView viewContactInfo(Map<String, Object> out, String type, String key,
			HttpServletResponse response) throws IOException {

		String s = (String) MemcachedUtils.getInstance().getClient().get(key);
		if (s == null) {
			return null;
		}
		MemcachedUtils.getInstance().getClient().delete(key);

		int width = s.getBytes().length * 8 + 2;
		int height = 16;

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		g2.setColor(Color.black);
		g2.drawString(s, 2, 13);
		ImageIO.write(bi, "jpg", response.getOutputStream());
		return null;
	}

}