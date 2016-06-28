/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsCategory;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.AddressDto;
import com.ast.feiliao91.dto.goods.ShoppingDto;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.facade.GCategoryFacade;
import com.ast.feiliao91.service.goods.GoodsCategoryService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.goods.ShoppingService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class GoodsController extends BaseController {
	@Resource
	private GoodsService goodsService;
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private PictureService pictureService;
	@Resource
	private AddressService addressService;
	@Resource
	private ShoppingService shoppingService;
	@Resource
	private CompanyInfoService companyInfoService;
	/**
	 * 产品发布第一步
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView post_step1(Map<String, Object> out, HttpServletRequest request) {
		SsoUser user = getCachedUser(request);
		//判断是否完善地址信息和认证信息
		List<Address> address=addressService.selectByDelId(user.getCompanyId());
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(user.getCompanyId());
		Integer i = companyInfo.getCreditStatus();
		if(address.size()<1||i!=2){
			return new ModelAndView("redirect:"+AddressTool.getAddress("trade")+"/certification/rzxinxi.htm");
		}
		// 常用类别获取
		List<String> lists = goodsService.queryCategoryByCompanyId(user.getCompanyId(), 5);
		Map<String, String> maps = new HashMap<String, String>();
		for (String s : lists) {
			if (s.length() > 12) {
				maps.put(s,
						GCategoryFacade.getInstance().getValue(s.substring(0, 8)) + " > "
								+ GCategoryFacade.getInstance().getValue(s.substring(0, 12)) + " > "
								+ GCategoryFacade.getInstance().getValue(s.substring(0, 16)));
			} else if (s.length() > 8) {
				maps.put(s, GCategoryFacade.getInstance().getValue(s.substring(0, 8)) + " > "
						+ GCategoryFacade.getInstance().getValue(s.substring(0, 12)));
			}
		}
		out.put("maps", maps);
		SeoUtil.getInstance().buildSeo("step1", out);
		return null;
	}

	/**
	 * 产品发布第二步
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView post_step2(Map<String, Object> out, String mainCategory, HttpServletRequest request) {
		SsoUser user = getCachedUser(request);
		String firstTag = "";
		String secondTag = "";
		String thirdTag = "";
		// 一级类别
		firstTag = GCategoryFacade.getInstance().getValue(mainCategory.substring(0, 8));
		if (mainCategory.length() > 8) {
			// 二级类别
			secondTag = GCategoryFacade.getInstance().getValue(mainCategory.substring(0, 12));
		}
		if (mainCategory.length() > 12) {
			// 三级类别
			thirdTag = GCategoryFacade.getInstance().getValue(mainCategory.substring(0, 16));
		}
		// 图片信息
		PageDto<Picture> page = new PageDto<Picture>();
		page.setPageSize(10);
		page.setSort("gmt_created");
		page.setDir("desc");
		page = pictureService.pagePictureList(page, user.getCompanyId(), null);
		// 图片页数
		out.put("size", page.getTotalPages());
		// 图片列表
		out.put("list", page.getRecords());
		out.put("firstTag", firstTag);
		out.put("secondTag", secondTag);
		out.put("thirdTag", thirdTag);
		out.put("mainCategory", mainCategory);
		SeoUtil.getInstance().buildSeo("step2", out);
		return null;
	}
	
	/**
	 * 处理第二步信息
	 * 
	 * @param request
	 * @param goods
	 * @param limitDay
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView publishGoods(HttpServletRequest request, Goods goods, Integer limitDay, String bgImg, String pImg,Map<String,Object> out,String attributeName,String attributeValue)
			throws ParseException, IOException {
		ExtResult result = new ExtResult();
		SsoUser user = getCachedUser(request);
		do {
			if (user == null) {
				break;
			}
			Boolean val = validateGoods(goods, limitDay, pImg);
			if (!val) {
				break;
			}
			// 有效期
			goods.setExpireTime(DateUtil.getDateAfterDays(new Date(), limitDay));
			goods.setCompanyId(user.getCompanyId());
			
			// 处理附加属性
			try {
				goods.setGoodAttribute(goods.getGoodAttribute()+getAttributeFromPostStep2(attributeName, attributeValue));
			} catch (Exception e) {
				
			}
			
			Integer i = goodsService.createGoods(goods);
			if (i > 0) {
				// 图片处理
				// 1、检测报告
				if (StringUtils.isNotEmpty(bgImg)) {
					pictureService.dealPicture(bgImg, i, "2");
				}
				// 2、产品图片
				if (StringUtils.isNotEmpty(pImg)) {
					pictureService.dealPicture(pImg, i, "1");
				}
			}
			result.setSuccess(true);
			result.setData(i);
//			return new ModelAndView("redirect:" + "/goods/post_suc.htm");
		} while (false);
//		return new ModelAndView("redirect:" + "/goods/post_step1.htm");
		return printJson(result, out);
	}

//	/**
//	 * 处理第二步信息  ///未使用///未使用///未使用///未使用///未使用///未使用
//	 * 
//	 * @param request
//	 * @param goods
//	 * @param limitDay
//	 * @return
//	 * @throws ParseException
//	 */
//	@RequestMapping
//	public ModelAndView doStep2(HttpServletRequest request, Goods goods, Integer limitDay, String bgImg, String pImg,String id)throws ParseException {
//		SsoUser user = getCachedUser(request);
//		do {
//			if (user == null) {
//				break;
//			}
//			Boolean val = validateGoods(goods, limitDay, pImg);
//			if (!val) {
//				break;
//			}
//			if (goods.getId() == null) { // 发布产品
//				// 有效期
//				goods.setExpireTime(DateUtil.getDateAfterDays(new Date(), limitDay));
//				goods.setCompanyId(user.getCompanyId());
//				Integer i = goodsService.createGoods(goods);
//				if (i > 0) {
//					// 图片处理
//					// 1、检测报告
//					if (StringUtils.isNotEmpty(bgImg)) {
//						pictureService.dealPicture(bgImg, i, "1");
//					}
//					// 2、产品图片
//					if (StringUtils.isNotEmpty(pImg)) {
//						pictureService.dealPicture(pImg, i, "2");
//					}
//				}
//				return new ModelAndView("redirect:" + "/goods/post_suc.htm");
//			} else { ///未使用
//				// 编辑产品
//				// 产品信息
//				Goods gd = goodsService.queryGoodById(goods.getId());
//				// 获取有效期
//				Integer day = DateUtil.getIntervalDays(gd.getExpireTime(), gd.getRefreshTime());
//				if (day != limitDay) {
//					goods.setExpireTime(DateUtil.getDateAfterDays(gd.getRefreshTime(), limitDay));
//				}
//				goods.setCheckStatus(0);
//				// 保存修改的信息
//				Integer i = goodsService.updateGoods(goods);
//				// 处理图片信息
//				if (i > 0) {
//					// 图片处理
//					// 1、检测报告
//					if (StringUtils.isNotEmpty(bgImg)) {
//						pictureService.dealPicture(bgImg, i, "1");
//					}
//					// 2、产品图片
//					if (StringUtils.isNotEmpty(pImg)) {
//						pictureService.dealPicture(pImg, i, "2");
//					}
//				}
//				return new ModelAndView("redirect:" + "/goods/post_suc.htm?id=" + goods.getId());
//			}
//		} while (false);
//		return new ModelAndView("redirect:" + "/goods/post_step1.htm");
//	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Goods goods, Integer limitDay, String bgImg, String pImg)throws ParseException {
		do {
			try {
				
//			Boolean val = validateGoods(goods, limitDay, pImg);
//			if (!val) {
//				break;
//			}
			// 编辑产品
			// 产品信息
			Goods gd = goodsService.queryGoodById(goods.getId());
			// 获取有效期
			Integer day = DateUtil.getIntervalDays(gd.getExpireTime(), gd.getRefreshTime());
			if (day != limitDay) {
				goods.setExpireTime(DateUtil.getDateAfterDays(gd.getRefreshTime(), limitDay));
			}
			goods.setCheckStatus(0);
			// 保存修改的信息
			Integer i = goodsService.updateGoods(goods);
			//删除原来属于改产品的图片（设is_del=1）
			//pictureService.deleteAllPicInThisGoods(goods.getId());
			// 处理图片信息
			if (i > 0) {
				// 图片处理
				// 1、检测报告
				if (StringUtils.isNotEmpty(bgImg)) {
					pictureService.dealPicture(bgImg,goods.getId(), "2");
				}
				// 2、产品图片
				if (StringUtils.isNotEmpty(pImg)) {
					pictureService.dealPicture(pImg,goods.getId(), "1");
				}
			}
			return new ModelAndView("redirect:" + "/goods/post_suc.htm?id=" + goods.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (false);
		return new ModelAndView("redirect:" + "/goods/post_edit.htm?id=" + goods.getId());
	}

	/**
	 * 产品发布成功
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView post_suc(Map<String, Object> out, Integer id) {
		out.put("id", id);
		SeoUtil.getInstance().buildSeo("post_suc", out);
		return null;
	}

	/**
	 * 产品编辑
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView post_edit(HttpServletRequest request, Map<String, Object> out, Integer id) {
		SeoUtil.getInstance().buildSeo("common", new String[]{"修改商品"}, new String[]{}, new String[]{}, out);
		SsoUser user = getCachedUser(request);
		Goods good = goodsService.queryGoodById(id);
		good.setDetail(Jsoup.clean(good.getDetail(), Whitelist.relaxed().addTags("p")));
		good.setDetail(good.getDetail().replace("\n", ""));
		out.put("good", good);
		// 类别处理
		String firstTag = "";
		String secondTag = "";
		String thirdTag = "";
		// 一级类别
		firstTag = GCategoryFacade.getInstance().getValue(good.getMainCategory().substring(0, 8));
		if (good.getMainCategory().length() > 8) {
			// 二级类别
			secondTag = GCategoryFacade.getInstance().getValue(good.getMainCategory().substring(0, 12));
		}
		if (good.getMainCategory().length() > 12) {
			// 三级类别
			thirdTag = GCategoryFacade.getInstance().getValue(good.getMainCategory().substring(0, 16));
		}
		// 图片信息
		PageDto<Picture> page = new PageDto<Picture>();
		page.setPageSize(10);
		page.setSort("gmt_created");
		page.setDir("desc");
		page = pictureService.pagePictureList(page, user.getCompanyId(), null);
		// 图片页数
		out.put("size", page.getTotalPages());
		// 图片列表
		out.put("piclist", page.getRecords());
		out.put("firstTag", firstTag);
		out.put("secondTag", secondTag);
		out.put("thirdTag", thirdTag);
		// 产品图片
		List<Picture> plist = pictureService.queryPictureByCondition(good.getId(), "1", user.getCompanyId(), 5);
		out.put("plist", plist);
		// 检测报告
		List<Picture> bglist = pictureService.queryPictureByCondition(good.getId(), "2", user.getCompanyId(), 5);
		out.put("bglist", bglist);
		// 有效时间
		try {
			out.put("limitDay", DateUtil.getIntervalDays(good.getExpireTime(), good.getRefreshTime()));
		} catch (ParseException e) {
			out.put("limitDay", "0");
		}
		// 处理产品属性
		Map<String, String> list = new LinkedHashMap<String, String>();
		if (StringUtils.isNotEmpty(good.getGoodAttribute())) {
			String[] string = good.getGoodAttribute().split("\\|");// 办自动化和全开放的属性分开
			for (String s : string) {
				s = s.replace("：", ":");
				if (!"|".equals(s) && (!":".equals(s)) && s.contains(":") && StringUtils.isNotEmpty(s)) {
					if (s.split(":").length > 1) {
						list.put(Jsoup.clean(s.split(":")[0], Whitelist.none()) , Jsoup.clean(s.split(":")[1], Whitelist.none()));
					} else {
						list.put(Jsoup.clean(s.split(":")[0], Whitelist.none()), "");
					}
				}
			}
		}
		out.put("list", list);
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

	@RequestMapping
	public ModelAndView catChild(Map<String, Object> out, String parentCode) throws IOException {
		List<Category> list = new ArrayList<Category>();
		Map<String, String> map = CategoryFacade.getInstance().getChild(parentCode);
		if (map == null) {
			return printJson(list, out);
		}
		for (Entry<String, String> m : map.entrySet()) {
			Category c = new Category();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView search(Map<String, Object> out, String keyword) throws IOException {
		if (!StringUtils.isContainCNChar(keyword)) {
			// 解密
			keyword = StringUtils.decryptUrlParameter(keyword);
		}
		List<GoodsCategory> list = goodsCategoryService.queryGoodsCategoryByKeyword(keyword, 5, 8);
		List<Map<String, String>> listM = new ArrayList<Map<String, String>>();
		for (GoodsCategory cat : list) {
			Map<String, String> map = new HashMap<String, String>();
			// 三级
			map.put("code", cat.getCode());
			if (cat.getCode().length() > 12) {
				map.put("label",
						GCategoryFacade.getInstance().getValue(cat.getCode().substring(0, 8)) + " &gt; "
								+ GCategoryFacade.getInstance().getValue(cat.getCode().substring(0, 12)) + " &gt; "
								+ GCategoryFacade.getInstance().getValue(cat.getCode().substring(0, 16)));
			} else if (cat.getCode().length() > 8) { // 二级
				map.put("label", GCategoryFacade.getInstance().getValue(cat.getCode().substring(0, 8)) + " &gt; "
						+ GCategoryFacade.getInstance().getValue(cat.getCode().substring(0, 12)));
			}
			listM.add(map);
		}
		return printJson(listM, out);
	}

	/**
	 * 图片翻页列表
	 * 
	 * @param out
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listPic(Map<String, Object> out, String index, String type, HttpServletRequest request)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		do {
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null) {
				break;
			}

			PageDto<Picture> page = new PageDto<Picture>();
			page.setPageSize(10);
			page.setStartIndex((Integer.valueOf(index) - 1) * page.getPageSize());
			page.setSort("gmt_created");
			page.setDir("desc");
			page = pictureService.pagePictureList(page, ssoUser.getCompanyId(), type);
			List<String> list = new ArrayList<String>();
			for (Picture pic : page.getRecords()) {
				list.add(pic.getPicAddress());
			}
			String pString = "<span class=\"page-do\" index=\"1\"><</span>";
			if (page.getTotalPages() > 1) {
				if (Integer.valueOf(index) == page.getTotalPages() && page.getTotalPages() > 2) {
					pString = pString + "<span class=\"morepage\"><i class=\"dianmore\">...</i></span>";
				}
				if (Integer.valueOf(index) > 1) {
					pString = pString + "<span class=\"page-do\">" + (Integer.valueOf(index) - 1) + "</span>";
				}
				pString = pString + "<span class=\"page-do this-page\">" + index + "</span>";
				if (Integer.valueOf(index) == 1) {
					pString = pString + "<span class=\"page-do\">" + (Integer.valueOf(index) + 1) + "</span>";
				}
				if (Integer.valueOf(index) < page.getTotalPages() && page.getTotalPages() > 2) {
					pString = pString + "<span class=\"morepage\"><i class=\"dianmore\">...</i></span>";
				}
			} else {
				pString = pString + "<span class=\"page-do this-page\">1</span>";
			}
			pString = pString + "<span class=\"page-do\" index=\"" + page.getTotalPages() + "\">></span>";
			map.put("list", list);
			map.put("pageString", pString);
		} while (false);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView getProperty(Map<String, Object> out, String code) throws IOException {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		// 类别,外网数据库的产品父类别为1002
		Map<String, String> category = CategoryFacade.getInstance().getChild("1002");
		Map<String, String> label = new HashMap<String, String>();
		for (String s : category.keySet()) {
			label.put(category.get(s), s);
		}
		if ("1002".equals(code.substring(0, 4))) {
			// 三级属性
			Map<String, String> chmap = CategoryFacade.getInstance().getChild(code);
			List<Map<String, String>> chlist = new ArrayList<Map<String, String>>();
			if (chmap != null) {
				for (String s : chmap.keySet()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("code", chmap.get(s));
					map.put("label", chmap.get(s));
					chlist.add(map);
				}
			}
			resultmap.put("chlist", chlist);
		} else if(code.length()>=12) {
			String lcode = label.get(GCategoryFacade.getInstance().getValue(code.substring(0, 12)));
			String a = lcode + "1001";
			// 用途级别
			Map<String, String> umap = CategoryFacade.getInstance().getChild(a);
			if (umap != null) {
				List<Map<String, String>> ulist = new ArrayList<Map<String, String>>();
				for (String s : umap.keySet()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("code", s);
					map.put("label", umap.get(s));
					ulist.add(map);
				}
				resultmap.put("ulist", ulist);
			}
			// 加工级别
			Map<String, String> pmap = CategoryFacade.getInstance().getChild(lcode + "1000");
			if (pmap != null) {
				List<Map<String, String>> plist = new ArrayList<Map<String, String>>();
				for (String s : pmap.keySet()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("code", s);
					map.put("label", pmap.get(s));
					plist.add(map);
				}
				resultmap.put("plist", plist);
			}
			// 特性级别
			Map<String, String> cmap = CategoryFacade.getInstance().getChild(lcode + "1002");
			if (cmap != null) {
				List<Map<String, String>> clist = new ArrayList<Map<String, String>>();
				for (String s : cmap.keySet()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("code", s);
					map.put("label", cmap.get(s));
					clist.add(map);
				}
				resultmap.put("clist", clist);
			}
		}
		return printJson(resultmap, out);
	}

	/**
	 * 验证产品信息不能为空
	 * 
	 * @param goods
	 * @param limitDay
	 * @param pImg
	 * @return
	 */
	public boolean validateGoods(Goods goods, Integer limitDay, String pImg) {
		// 有效期
		if (limitDay == null) {
			return false;
		}
		// 产品图片
		if (StringUtils.isEmpty(pImg)) {
			return false;
		}
		// 产品标题
		if (StringUtils.isEmpty(goods.getTitle())) {
			return false;
		}
		// 产品类别
		if (StringUtils.isEmpty(goods.getMainCategory())) {
			return false;
		}
		// 进口属性
		if (goods.getIsImport() == null) {
			return false;
		}
		// 级别
		if (StringUtils.isEmpty(goods.getLevel())) {
			return false;
		}
		// 颜色
		if (StringUtils.isEmpty(goods.getColor())) {
			return false;
		}
		// 形态
		if (StringUtils.isEmpty(goods.getForm())) {
			return false;
		}
		// 计量单位
		if (StringUtils.isEmpty(goods.getUnit())) {
			return false;
		}
		// 价 格
		if (StringUtils.isEmpty(goods.getPrice())) {
			return false;
		}
		// 报价是否含税
		if (goods.getHasTax() == null) {
			return false;
		}
		// 可售数量
		if (StringUtils.isEmpty(goods.getQuantity())) {
			return false;
		}
		// 最小起订量
		if (StringUtils.isEmpty(goods.getNumber())) {
			return false;
		}
		// 供货方式
		if (StringUtils.isEmpty(goods.getProvideCode())) {
			return false;
		}
		// 产品详情
		if (StringUtils.isEmpty(goods.getDetail())) {
			return false;
		}
		return true;
	}

	@RequestMapping
	public ModelAndView post_list(Map<String, Object> out, GoodsSearchDto searchDto, PageDto<GoodsDto> page,
			HttpServletRequest request) {
		SeoUtil.getInstance().buildSeo("post_list", out);
		page.setSort("id");
		page.setDir("desc");
		page.setPageSize(6);
		SsoUser ssoUser = getCachedUser(request);
		searchDto.setCompanyId(ssoUser.getCompanyId());
		if (searchDto.getIsDel() == null) {
			searchDto.setIsDel(0);
		}
		page = goodsService.pageBySearch(searchDto, page);
		out.put("page", page);
		out.put("searchDto", searchDto);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView updateGoodsByStatus(Map<String, Object> out, Integer id, Integer type) throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			if (type == null) {
				break;
			}
			Integer i = goodsService.updateStatusByUser(id, type);
			if (i > 0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView buy_good(HttpServletRequest request, Map<String, Object> out,String id,String attribute,String number) {
		do {
			// 登录信息
			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser == null) {
				break;
			}
			try {
				if (StringUtils.isEmpty(id)) {
					id = request.getParameter("id");
				}
				if (StringUtils.isEmpty(attribute)) {
					attribute = request.getParameter("attribute");
				}
				if (StringUtils.isEmpty(number)) {
					number = request.getParameter("number");
				}
				// 产品信息
				GoodsDto dto = goodsService.queryGoodsDtoById(Integer.valueOf(id));
				out.put("dto", dto);
				// 获取收货地址
				List<AddressDto> addrList = addressService.queryAddressByCondition(ssoUser.getCompanyId(), 0);
				out.put("addrList", addrList);
				out.put("attribute", attribute);
				out.put("number", number);
				SeoUtil.getInstance().buildSeo("common",new String[]{"订单确定"},new String[]{"订单确定"},new String[]{"订单确定"}, out);
			} catch (Exception e) {
				return new ModelAndView("redirect:"+AddressTool.getAddress("www"));
			}
		} while (false);
		return null;
	}
	
	@RequestMapping
	public ModelAndView buy_goods(HttpServletRequest request, Map<String, Object> out,String ids) {
		do {
			// 用户信息
			SsoUser ssoUser = getCachedUser(request);
			try {
				out.put("ids",ids);
				List<ShoppingDto> resultList = shoppingService.queryShopping(ids);
				out.put("resultList",resultList);
				// 获取收货地址
				List<AddressDto> addrList = addressService.queryAddressByCondition(ssoUser.getCompanyId(), 0);
				out.put("addrList", addrList);
				SeoUtil.getInstance().buildSeo("common",new String[]{"订单确定"},new String[]{"订单确定"},new String[]{"订单确定"}, out);
			} catch (Exception e) {
				return new ModelAndView("redirect:"+AddressTool.getAddress("www"));
			}
		} while (false);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView getAddr(Map<String, Object> out, Integer id) throws IOException {
		AddressDto dto = new AddressDto();
		do {
			if (id == null) {
				break;
			}
			Address addr = addressService.selectAddress(id);
			if (addr != null) {
				dto.setAddress(addr);
				if (StringUtils.isNotEmpty(addr.getTel())) {
					String[] telString = addr.getTel().split("-");
					dto.setPreTel(telString[0]);
					if (telString.length > 1) {
						dto.setMidTel(telString[1]);
					}
					if (telString.length > 2) {
						dto.setPostTel(telString[2]);
					}
				}
			}
		} while (false);
		return printJson(dto, out);
	}
	
	private String getAttributeFromPostStep2(String name,String value){
		String str = "|";
		String [] nameArray = name.split(",");
		String [] valueArray = value.split(",");
		for (int i=0;i<nameArray.length;i++) {
			str = str + nameArray[i]+"："+valueArray[i]+"|";
		}
		return str;
	}
	
	@RequestMapping
	public ModelAndView updateTargetIdZero(Map<String, Object> out, String picId) throws IOException {
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(picId)){
				break;
			}
			Integer i = pictureService.updateTargetIdZeroById(Integer.valueOf(picId));
			if(i.equals(1)){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

}
