/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 上午09:44:38
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.ConfigNotifyDO;
import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.domain.company.SubscribeSmsPriceDO;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.MyfavoriteDTO;
import com.ast.ast1949.dto.company.SubscribeForMyrcDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.ConfigNotifyService;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.service.company.SubscribeSmsPriceService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;

/**
 * 订制信息控制器
 * 
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Controller
public class MysubscribeController extends BaseController {

	@Resource
	private SubscribeService subscribeService;
	@Resource	
	private PriceCategoryService priceCategoryService;
	@Resource
	private MyfavoriteService myfavoriteService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CategoryService categoryService;
//	@Resource
//	private PriceService priceService;
//	@Resource
//	private BbsService bbsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService companyService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private PriceService priceService;
	@Resource
	private SubscribeSmsPriceService subscribeSmsPriceService;
	@Resource
	private ConfigNotifyService configNotifyService;
		
	/**
	 * 商机定制列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView bizList(ProductsDO product, Integer id, PageDto<ProductsDto> page,
			HttpServletRequest request, Map<String, Object> out) throws Exception {
		//leftMenu(request, out);
		out.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		out.put(FrontConst.MYRC_SUBTITLE, "在线商机快递");

		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
//		Company company = companyService.queryCompanyById(sessionUser.getCompanyId());
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
//		out.put("company", company);
		out.put("companyAccount", companyAccount);
		
//		SubscribeDO subscribeDO = null; 
		do {
			List<SubscribeDO> list = subscribeService.querySubscribeByCompanyIdAndSubscribeType(
					sessionUser.getCompanyId(), "0");
			out.put("bizList", list);
			
			if(list==null || list.size()<=0){
				break;
			}
			
			SubscribeDO subscribe=null;
			if(id==null || id.intValue()<=0){
				subscribe=list.get(0);
				id=subscribe.getId();
			}else{
				for(SubscribeDO o:list){
					if(o.getId().intValue()==id.intValue()){
						subscribe=o;
						break;
					}
				}
			}
//			subscribe.get
			product.setProductsTypeCode(subscribe.getProductsTypeCode());
			product.setTitle(subscribe.getKeywords());
			
			page=productsService.pageProductsBySearchEngine(product, subscribe.getAreaCode(),null, page);
			
			out.put("page", page);
			
			out.put("id", id);
		} while (false);
		// 定制关键字列表
		
		
//		if (id == null) {
//			for (SubscribeDO subscribeDO2 : list) {
//				id = subscribeDO2.getId();
//				out.put("ids", id);
//			}
//		} else {
//			out.put("ids", id);
//		}
		// 当前项
//		subscribeDO = list.get(0);
//		if (id != null && list.size()>0) {
//			for(int i=0;i<list.size();i++){
//				if(list.get(i).getId()!=null && list.get(i).getId().intValue()==id.intValue()){
//					subscribeDO=list.get(i);
//					break;
//				}
//			}
//		}

//		if (subscribe != null) {
			//TODO 处理参数
			
//			searchDTO.setKeywords(subscribeDO.getKeywords());
//			searchDTO.setAreaCode(subscribeDO.getAreaCode());
//			if ("10331000".equals(subscribeDO.getProductsTypeCode())) {
//				searchDTO.setBuyOrSale("供应");
//			} else if ("10331001".equals(subscribeDO.getProductsTypeCode())) {
//				searchDTO.setBuyOrSale("求购");
//			} else {
//
//			}
//			if (StringUtils.isEmpty(searchDTO.getKeywords())) {
//				searchDTO.setKeywords("pp");
//			}
			
			
			/**
			 * 搜索，并获得总记录数
			 */
			// TODO 根据订阅信息搜索供求
//			page = productSearchService.search(searchDTO, page);
//			for (Object obj : page.getRecords()) {
//				ProductsListItemForFrontDTO productsListItemForFront = (ProductsListItemForFrontDTO) obj;
//				if (productsListItemForFront.getAreaCode().length() >= 12)
//					productsListItemForFront.setProvince(CategoryFacade.getInstance()
//							.getValue(productsListItemForFront.getAreaCode().substring(0, 12)));
//				if (productsListItemForFront.getAreaCode().length() >= 16) {
//					productsListItemForFront
//							.setArea(CategoryFacade.getInstance().getValue(
//									productsListItemForFront.getAreaCode().substring(0, 16)));
//				}
//				productsListItemForFront.setMembershipCode(companyService.selectCompanyById(
//						Integer.valueOf(productsListItemForFront.getCompanyId()))
//						.getMembershipCode());
//				String details = new StringBuffer().append(
//						bSubstring(productsListItemForFront.getProductsDetails(), 150)).append(
//						"...").toString();
//				out.put("details", details);
//			}
//			String keyword = searchDTO.getKeywords();
			
//			out.put("results", page.getRecords());
//			out.put("stringUtils", new StringUtils());
//			out.put("totalPages", page.getTotalPages());
//			out.put("page", page);
//			out.put("suffixUrl", "&id=" + id + "&keywords=" + keyword);
//		} 
		return null;
	}
	
	/**
	 * 价格定制列表
	 */
	@RequestMapping
	public void priceList(HttpServletRequest request, Map<String, Object> out, PageDto page,
			SubscribeForMyrcDTO subscribeForMyrcDTO) {
		out.put(FrontConst.MYRC_SUBTITLE, "最新在线行情");
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		Company company = companyService.queryCompanyById(sessionUser.getCompanyId());
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		out.put("company", company);
		out.put("companyAccount", companyAccount);
		out.put("zst_phone", ParamUtils.getInstance().getValue("site_info_front", "zst_phone"));
		if (sessionUser != null) {
			page.setPageSize(AstConst.PAGE_SIZE / 4);
			page.setSort("s.id");
			page.setDir("desc");
			subscribeForMyrcDTO.setPage(page);
			subscribeForMyrcDTO.setCompanyId(sessionUser.getCompanyId());
			subscribeForMyrcDTO.setSubscribeType(1);
			List<SubscribeForMyrcDTO> records = subscribeService.querySubscribeForMyrc(subscribeForMyrcDTO);
			Integer totalRecords = subscribeService.countSubscribeForMycrByCondition(subscribeForMyrcDTO);
			page.setTotalRecords(totalRecords);
			out.put("records", records);
			out.put("totalRecords", totalRecords);
			out.put("page", page);
		}
	}

	/**
	 *定制商机
	 */
	@RequestMapping
	public void manageSubscribeBiz(HttpServletRequest request, Map<String, Object> out) {
		//leftMenu(request, out);
		out.put(FrontConst.MYRC_SUBTITLE, "在线商机快递");
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		 
//		CompanyContactsDO ccd = companyContactsService.queryContactByAccount(sessionUser.getAccount());

		// 定制关键字列表
		if (sessionUser != null) {
			List<SubscribeDO> list = subscribeService.querySubscribeByCompanyIdAndSubscribeType(companyAccount
					.getCompanyId(), "0");
			for (SubscribeDO subscribeDO : list) {
				if(subscribeDO.getAreaCode()!=null&&!subscribeDO.getAreaCode().equals("")){
				if (subscribeDO.getAreaCode().length() >= 12) {
					CategoryDO category = categoryService.queryCategoryByCode(subscribeDO
							.getAreaCode());
					subscribeDO.setProvince(category.getLabel());
				}
				}
			}
			if (list != null && list.size() != 0) {
				out.put("isNeverSubscribe", false);
				out.put("bizList", list);
			} else {
				out.put("isNeverSubscribe", true);
			}
			out.put("currentEmail", companyAccountService.currentEmail(companyAccount));
		}
	}

	/**
	 *定制报价
	 */
	@RequestMapping
	public void manageSubscribePrice(HttpServletRequest request, Map<String, Object> out) {
		//	leftMenu(request, out);
		out.put(FrontConst.MYRC_SUBTITLE, "行情订制管理");
		SsoUser sessionUser = getCachedUser(request);
		myrcService.initMyrc(out, sessionUser.getCompanyId());//查询是否开通商铺服务
		//以上从缓存中获取的数据与实际的数据库里保存的数据不一致，所以又重新查了一遍。
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		
		// 定制关键字列表
		if (companyAccount != null) {
			List<SubscribeDO> list = subscribeService.querySubscribeByCompanyIdAndSubscribeType(companyAccount
					.getCompanyId(), "1");
			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
			for (SubscribeDO subscribe : list) {
				String subscribeCategory = "";
				PriceCategoryDO priceCategory = null;
				if (subscribe.getPriceTypeId() != null) {
					priceCategory = priceCategoryService.queryPriceCategoryById(subscribe
							.getPriceTypeId());
					if (priceCategory != null) {
						List<PriceCategoryDO> list2 = priceCategoryService
								.getAllParentPriceCategoryByParentId(priceCategory.getParentId());
						for (PriceCategoryDO category : list2) {
							subscribeCategory += category.getName() + "+";
						}
						subscribeCategory += priceCategory.getName();
					}
				}
				if (subscribe.getPriceAssistTypeId() != null) {
					priceCategory = priceCategoryService.queryPriceCategoryById(subscribe
							.getPriceAssistTypeId());
					if (priceCategory != null) {
						subscribeCategory += " + (" + priceCategory.getName() + ")";
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("subscribeCategory", subscribeCategory);
				map.put("subscribe", subscribe);
				newList.add(map);
			}
			if (newList != null && newList.size() != 0) {
				out.put("isNeverSubscribe", false);
				out.put("priceList", newList);
			} else {
				out.put("isNeverSubscribe", true);
			}
			out.put("currentEmail", companyAccountService.currentEmail(companyAccount));
		}
	}

	/**
	 * 我的篮子
	 * 
	 */
	@RequestMapping
	public void myfavorite(MyfavoriteDTO myfavoriteDTO, String favoriteTypeCode,
			HttpServletRequest request, Map<String, Object> out) {
//		// 左侧菜单
//		//	leftMenu(request, out);
//		out.put(FrontConst.MYRC_SUBTITLE, "我的篮子");
//		SsoUser sessionUser = getCachedUser(request);
//		//查询是否开通商铺服务
//		myrcService.isEsite(out, sessionUser.getCompanyId());
//		MyfavoriteDO myfavoriteDO = new MyfavoriteDO();
//		myfavoriteDTO.setMyfavoriteDO(myfavoriteDO);
//		myfavoriteDO.setCompanyId(sessionUser.getCompanyId());
//		myfavoriteDO.setFavoriteTypeCode(favoriteTypeCode);
//		// 设置起始页码和每页条数
//		myfavoriteDTO.setPageDto(page);
//		List<MyfavoriteDTO> mList = myfavoriteService.queryMyfavoriteByCondition(myfavoriteDTO);
//		// 获得总记录数
//		Integer totalRecords = myfavoriteService.queryMyfavoriteCountByCondition(myfavoriteDTO);
//		myfavoriteDTO.getPageDto().setTotalRecords(totalRecords);
//		// 根据总记录数计算总页数
//		out.put("favoriteTypeCode", favoriteTypeCode);
//		out.put("mList", mList);
//		out.put("page", page);
//		out.put("suffixUrl", "");
	}

	/**
	 * 加入我的篮子
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView insertMyfavorite(Integer id, HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		ProductsDO productsDO = productsService.queryProductsWithOutDetailsById(id);
		MyfavoriteDO myfavoriteDO = new MyfavoriteDO();
		SsoUser sessionUser = getCachedUser(request);
		MyfavoriteDO cDo = myfavoriteService.queryMyfavoriteByMap(id, sessionUser.getCompanyId());
		if (cDo != null) {
			// out.put("result", "已经添加");
			// out.put("link", request.getHeader("Referer"));
			return printJs("alert('已经添加');window.close();", out);
		} else {
			myfavoriteDO.setCompanyId(sessionUser.getCompanyId());
			myfavoriteDO.setAccount(sessionUser.getAccount());
			Integer i = myfavoriteService.insertMyfavorite(myfavoriteDO, productsDO);
			if (i > 0) {
				out.put("result", "添加成功");
			} else {
				out.put("result", "添加失败");
			}
			out.put("link", "myfavorite.htm");

		}

		return null;
	}

	/**
	 * 删除篮子中的信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteMyfavorite(String ids, Map<String, Object> out) throws IOException {
		String[] entities = ids.split(",");
		int[] i = new int [entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		Integer a = myfavoriteService.bathDeleteMyfavoriteById(i);
		if (a > 0) {
			out.put("result", "删除成功");
		} else {
			out.put("result", "删除失败");
		}
		out.put("link", "myfavorite.htm");
		return null;
	}

	/**
	 * 定制信息索引页
	 */
	@RequestMapping
	public void subscribeIndex(HttpServletRequest request, Map<String, Object> out) {
		//		List<AuthRightDTO> listDtos = setMenuChecked(request,
		//				AstConst.LEFTMENU_PARENTID);
		//		out.put("listDTO", listDtos);
		out.put(FrontConst.MYRC_SUBTITLE, "订阅最新商机");
	}

	/**
	 * 删除定制 商机/行情都有使用到 所以跳转用到request.getHeader
	 * @param request
	 * @param id
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteSubscribe(HttpServletRequest request, Integer id,
			Map<String, Object> out) throws IOException {
		subscribeService.deleteSubscribeById(id);
//		return new ModelAndView("redirect:manageSubscribeBiz.htm");
		return printJs("location.href='" + request.getHeader("referer") + "';", out);
	}
	/**
	 * 
	 *  VIP 周报
	 *  
	 */
	@RequestMapping
	public void weekly(Map<String, Object> out){
//		//本周最新的产品信息
//		out.put("productsList", productsService.queryNewProductsOnWeek(10));
//		// 本周最新的与你匹配的客户信息
//		//TODO  与CRM 相关
//		//		本周最新的资讯信息
//		List<BbsPostDTO> bbsList=bbsService.queryNewBbsOnWeek(DateUtil.getFirstDateThisWeek(),DateUtil.getLastDateThisWeek(),AstConst.TAGS_PAGE_SIZE);
//		out.put("bbsList",bbsList);
//		//		本周最新的(报价)市场动态
//		List<PriceDTO> priceList=priceService.queryNewPriceOnWeek(DateUtil.getFirstDateThisWeek(),DateUtil.getLastDateThisWeek(),AstConst.TAGS_PAGE_SIZE);
//		out.put("priceList", priceList);
		out.put(FrontConst.MYRC_SUBTITLE, "VIP周报");
	}
	
	/**
	 * 页面：我的收藏夹页面
	 * 1、分页显示收藏夹信息
	 * 2、根据不同条件检索列表，也必须分页
	 * 3、参数：收藏的资讯类别favorite_type_code，时间段theDay，
	 * @throws UnsupportedEncodingException 
	 *
	 */
	
	final static String FCODE="1009";
	@RequestMapping
	public void favorites(HttpServletRequest request,Map<String, Object> out,
			PageDto<MyfavoriteDO> page,String favoriteTypeCode,Integer theday,String keywords) throws UnsupportedEncodingException{
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		if(StringUtils.isNotEmpty(keywords)){
			keywords = StringUtils.decryptUrlParameter(keywords);
		}
		//查询收藏的数据
//		page.setPageSize(5);
		page = myfavoriteService.pageMyCollect(page, keywords, sessionUser.getCompanyId(), favoriteTypeCode, theday);
		out.put("page", page);
		out.put("favoriteTypeCode", favoriteTypeCode);
		out.put("theday", theday);
		out.put("keywords", keywords);
		out.put("fcode", CategoryFacade.getInstance().getChild(FCODE));
		if(StringUtils.isNotEmpty(keywords)){
			out.put("encodeKeywords", URLEncoder.encode(keywords, "utf-8"));
		}
		
	}
	
	/**
	 * 动作：添加信息 进入 收藏夹
	 * 1、传入参数 insert的数据
	 * 2、判断是否已经存在company_id,favorite_type_code,content_id三个条件
	 * 3、成功去成功页面
	 * 4、已存在去已存在页面
	 */
	@RequestMapping
	public void insertMyCollect(MyfavoriteDO myfavoriteDO,Map<String,Object>map){
		
	}
	
	/**
	 * 动作：删除收藏夹
	 * 1、根据条件删除相应的信息 根据 company_id 和 记录id 其中，company_id 必须存在
	 */
	@RequestMapping
	public ModelAndView deleteMyCollect(HttpServletRequest request,Integer id,Map<String,Object>out) throws IOException{
		SsoUser sessionUser = getCachedUser(request);
		int i = myfavoriteService.deleteMyCollect(sessionUser.getCompanyId(),id);
		// 判断 是否 成功
		if(i>0){
			out.put("result",1);
		}else{
			out.put("result",2);
		}
		return printJson(out, out);

	}
	
	/**
	 * 动作：跳转至收藏页面
	 * 根据company_id,content_id,favorite_type_code
	 */
	@RequestMapping
	public ModelAndView toCollectPage(HttpServletRequest request,
			Map<String, Object> out ,String favoriteTypeCode,Integer contentId) throws IOException{
		String url ="" ;
		do{
			// trade
			if("10091006".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("trade")+"/productdetails"+contentId+".htm";
				break;
			}
			// huzhu 帖子 问答 废料学院
			if("10091005".equals(favoriteTypeCode)||"10091010".equals(favoriteTypeCode)||"10091011".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("huzhu")+"/viewReply"+contentId+".htm";
				break;
			}
			// esite
			if("10091003".equals(favoriteTypeCode)){
				//查找domain_zz91
				Company company = companyService.queryDomainOfCompany(contentId);
				if(StringUtils.isNotEmpty(company.getDomainZz91())){
					url = "http://"+company.getDomainZz91()+".zz91.com/";
				}else{
					url = AddressTool.getAddress("company")+"/compinfo"+contentId+".htm";
				}
				break;
			}
			if("10091004".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("price")+"/priceDetails_"+contentId;
				String type = queryPriceTypeNameById(contentId);
				url = url+"_"+type +".htm";
				break;
			}
			if("10091007".equals(favoriteTypeCode)){
				ProductsDO product = productsService.queryProductsById(contentId);
				Company company = companyService.queryDomainOfCompany(product.getCompanyId());
				url = "http://"+company.getDomainZz91()+".zz91.com/products"+contentId+".htm";
				break;
			}
			// 展会信息
			if("10091008".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("exhibit")+"/details"+contentId+".htm";
				break;
			}
			// 现货信息
			if("10091009".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("xianhuo")+"/detail"+contentId+".htm";
				break;
			}
			
			// 我的篮子 trade 页面跳转
			if("10091000".equals(favoriteTypeCode)||"10091001".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("trade")+"/productdetails"+contentId+".htm";
				break;
			}
			//原料供求收藏　跳转
			if("10091015".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("yuanliao")+"/detail/"+contentId+".html";
				break;
			}
			//原料公司收藏　跳转
			if("10091016".equals(favoriteTypeCode)){
				url = AddressTool.getAddress("yuanliao")+"/firm/detail"+contentId+".htm";
				break;
			}
			url  = request.getHeader("referer");
		}while(false);
		return new ModelAndView("redirect:"+url);
	}
	
	// 根据id 获取应的类别的code
	private String queryPriceTypeNameById(Integer id) {
		String code = "";
		PriceDTO pdto = priceService.queryPriceByIdForEdit(id);

		PriceDO priceDO = pdto == null ? null : pdto.getPrice();
		if (priceDO != null) {
			PriceCategoryDO priceCategoryDO = priceCategoryService
					.queryPriceCategoryById(priceDO.getTypeId());
			if (priceDO.getTypeId() != null && priceDO.getTypeId() <= 8) {
				if (priceCategoryDO != null) {
					code = priceCategoryDO.getName();
				} else {
					code = "";
				}
			} else if (priceCategoryDO != null) {
				Integer parentId = priceCategoryDO.getParentId();
				for (int i = 0; i < 6; i++) {
					priceCategoryDO = priceCategoryService
							.queryPriceCategoryById(parentId);
					if (priceCategoryDO != null) {
						if (priceCategoryDO.getParentId() != null) {
							if (priceCategoryDO.getParentId() == 1) {
								code = priceCategoryDO.getName();
								break;
							} else {
								parentId = priceCategoryDO.getParentId();
							}
						}
					}
				}
			}
		}
		
		if (code.equals("metal") || code.equals("废金属")) {
			code = "metal";
		} else if (code.equals("plastic") || code.equals("废塑料")) {
			code = "plastic";
		} else {
			code = "paper";
		}
		return code;
	}
	
	@RequestMapping
	public void subscribesms(Map<String, Object> out,HttpServletRequest request,String categoryCode){
		SsoUser ssoUser=getCachedUser(request);
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(ssoUser.getAccount());
		out.put("companyAccount", companyAccount);
		List<SubscribeSmsPriceDO> list=subscribeSmsPriceService.querySubscribeSMS(ssoUser.getCompanyId());
		out.put("list", list);
		//还可以订阅的数
		out.put("surplus", 20-list.size());
		//判断是否是信息完善
		String checkInfo=companyService.validateCompanyInfo(ssoUser);
		out.put("checkInfo", checkInfo);
	}
	
	@RequestMapping
	public void manageSubscribeSms(Map<String, Object> out,HttpServletRequest request,String categoryCode){
		
		SsoUser sessionUser = getCachedUser(request);
		
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		out.put("currentEmail", companyAccountService.currentEmail(companyAccount));
		out.put("email", sessionUser.getAccount());
		List<SubscribeSmsPriceDO> list=subscribeSmsPriceService.querySubscribeSMS(sessionUser.getCompanyId());
		for(SubscribeSmsPriceDO subDo:list){
			String responseText=HttpUtils.getInstance().httpGet(AddressTool.getAddress("exadmin")+"/reborn-admin/sms/main/getNameByCode.htm?categoryCode="+subDo.getCategoryCode()+"", HttpUtils.CHARSET_UTF8);
			String reString=responseText.replace("[", "");
			String name=reString.replace("]", "");
			subDo.setName(name);
			subDo.setAreaName(INDEX_MAP.get(subDo.getAreaNodeId()));
		}
		out.put("list", list);
	}
	
	@RequestMapping
	public ModelAndView smsList(Map<String, Object> out,HttpServletRequest request,
			String categoryCode,String areaNodeId,Integer startIndex,Integer total){
		SsoUser sessionUser = getCachedUser(request);
		
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		out.put("companyAccount", companyAccount);
		//定制的短信
		List<SubscribeSmsPriceDO> listsms=subscribeSmsPriceService.querySubscribeSMS(sessionUser.getCompanyId());
		if(listsms.size()>0){
			for(SubscribeSmsPriceDO subDo:listsms){
				String responseText=HttpUtils.getInstance().httpGet(AddressTool.getAddress("exadmin")+"/reborn-admin/sms/main/getNameByCode.htm?categoryCode="+subDo.getCategoryCode()+"", HttpUtils.CHARSET_UTF8);
				String reString=responseText.replace("[", "");
				String name=reString.replace("]", "");
				subDo.setName(name);
				subDo.setAreaName(INDEX_MAP.get(subDo.getAreaNodeId()));
			}
			if(categoryCode==null || categoryCode.equals("")){
				categoryCode=listsms.get(0).getCategoryCode();
			}
		}
		out.put("listsms", listsms);
		out.put("categoryCode", categoryCode);
		out.put("areaNodeId", areaNodeId);
		if(startIndex==null){
			startIndex=0;
		}
		out.put("start", startIndex);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView deleteSubscribeSms(HttpServletRequest request, String categoryCode,
			Map<String, Object> out) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		ExtResult result=new ExtResult();
		String code="";
		if(categoryCode.contains("_")){
			String str[]=categoryCode.split("_");  
			code=str[0];
			Integer areaNodeId=Integer.valueOf(str[1]);
			Integer date=subscribeSmsPriceService.deleteSubscribeSMSByArea(code,areaNodeId,sessionUser.getCompanyId());
			if(date>0){
				result.setSuccess(true);
			}
		}else {
			Integer date=subscribeSmsPriceService.deleteSubscribeSMS(categoryCode, sessionUser.getCompanyId());
			if(date>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteSmsPrice(Map<String, Object> out,HttpServletRequest request,Integer id){
		SsoUser sessionUser = getCachedUser(request);
		subscribeSmsPriceService.deleteSubscribeSMSPrice(id, sessionUser.getCompanyId());
		return new ModelAndView("redirect:manageSubscribeSms.htm");
	}
	
	public static Map<Integer, String> INDEX_MAP=new HashMap<Integer, String>();
	static{
		INDEX_MAP.put(11, "广东");INDEX_MAP.put(12, "浙江");
		INDEX_MAP.put(13, "江苏");INDEX_MAP.put(14, "山东");
		INDEX_MAP.put(1, "江浙沪");INDEX_MAP.put(2, "广东南海");
		INDEX_MAP.put(3, "天津");INDEX_MAP.put(4, "山东临沂");
		INDEX_MAP.put(5, "湖南汨罗");
	}
	
	@RequestMapping
	public ModelAndView sendEmail(Map<String, Object> out,HttpServletRequest request,
			Integer status, String notifyCode) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		Integer i=configNotifyService.updateConfigNotifyForSend(sessionUser.getCompanyId(), notifyCode, status);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView noSendEmail(Map<String, Object> out,HttpServletRequest request,
			Integer status, String notifyCode) throws IOException{
		ExtResult result=new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		Integer i=configNotifyService.updateConfigNotifyForSend(sessionUser.getCompanyId(), notifyCode, status);
		if(i>0){
			result.setSuccess(true);
		}else {
			Integer data=configNotifyService.addConfigNotify(sessionUser.getCompanyId(), notifyCode, 1);
			if(data>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView remind(Map<String, Object> out,HttpServletRequest request){
		SsoUser sessionUser = getCachedUser(request);
		CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
		out.put("currentEmail", companyAccountService.currentEmail(companyAccount));
		List<ConfigNotifyDO> list=configNotifyService.queryConfigNotify(sessionUser.getCompanyId(), null, 1);
		out.put("list", list);
		return new ModelAndView();
	}
}
