/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-23
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.products.ProductsSeriesDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsSeriesService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.products.ProductsSpotService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.service.spot.SpotInfoService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.tags.TagsUtils;

@Controller
public class MyproductsController extends BaseController {
	@Resource
	private ProductsService productsService;
	@Resource
	private ProductsSeriesService productsSeriesService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
//	@Resource
//	private CompanyService companyService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CategoryProductsService categoryProductsService;
	@Resource
	private CompanyService companyService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private ProductsSpotService productsSpotService;
	@Resource
	private SpotInfoService spotInfoService;
	@Resource
	private ProductAddPropertiesService productAddPropertiesService;
	/**
	 * 初始化发布供求信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public void office_post1(HttpServletRequest request, Map<String, Object> out, 
			ProductsDO products, String mainCodeLabel, String mainCodeLabels,String reason) throws UnsupportedEncodingException {
		//查看该用户是否为高级用户
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		Boolean result = productsService.queryUserIsAddProducts(sessionUser.getCompanyId(),sessionUser.getMembershipCode());
		out.put("result", result);
		
		try {
			products.setTitle(StringUtils.decryptUrlParameter(products.getTitle()));
			mainCodeLabel = StringUtils.decryptUrlParameter(mainCodeLabel);
			mainCodeLabels = StringUtils.decryptUrlParameter(mainCodeLabels);
		} catch (UnsupportedEncodingException e) {
		}
		
		out.put("products", products);
		out.put("mainCodeLabel", mainCodeLabel);
		out.put("mainCodeLabels", mainCodeLabels);
		// 敏感词验证
//		if(StringUtils.isNotEmpty(reason)){
//			out.put("reason", URLDecoder.decode(reason,"utf-8"));
//		}
	}
	
	/**
	 * 发布供求1 检索关键字匹配的所有类别
	 * 如果不存在三级类别，则显示相应匹配的二级类别列表。
	 * 如果三级类别存在，则显示三级类别列表。
	 * @param out
	 * @param keywords
	 * @return
	 */
	@RequestMapping
	public ModelAndView searchCategoryByKeywordsForPost(
			Map<String, Object> out, String keywords) throws IOException{
		do{
			keywords = StringUtils.decryptUrlParameter(keywords);
			if(StringUtils.isEmpty(keywords)){
				break;
			}
			List<Map<String,Object>> list = categoryProductsService.queryAllCategoryProductsByLabel(keywords);
			List<Map<String, Object>> list2=new ArrayList<Map<String,Object>>();
			if(list.size()>5){
				for(int i =0;i<5;i++){
					list2.add(list.get(i));
				}
				return printJson(list2, out);
			}else {
				return printJson(list, out);
			}
			
		}while(false);
		return null;
	}
	
	@RequestMapping
	public ModelAndView searchCategoryByHistoryForPost(Map<String, Object> out,
			HttpServletRequest request) throws IOException{
		do{
			Integer companyId = getCachedUser(request).getCompanyId();
			if(companyId==null){
				break;
			}
			List<Map<String,Object>> list = categoryProductsService.queryHistoryCategoryByCompanyId(companyId);
			return printJson(list, out);
		}while(false);
		return null;
	}

	/**
	 * 填写详细供求信息请求
	 * 
	 * @param productsDO为基本供求信息
	 * @param out输出结果信息
	 * @throws Exception 
	 */
	@RequestMapping
	public ModelAndView office_post2(ProductsDO products, String mainCodeLabel, String mainCodeLabels,
			HttpServletRequest request, Map<String, Object> out,String provideStatus) throws Exception {
		
		if (products.getProductsTypeCode() == null) {
			return new ModelAndView(new RedirectView("office_post1.htm"));
		}
		
		if(StringUtils.isEmpty(products.getCategoryProductsMainCode())){
			return new ModelAndView(new RedirectView("office_post1.htm"));
		}
		
		
		out.put("manufactureList", CategoryFacade.getInstance().getChild("1011"));

		// 从缓存中获取companyId信息
		SsoUser sessionUser = getCachedUser(request);

//		if (StringUtils.isNotEmpty(sessionUser.getServiceCode())) {
//			out.put("category", CategoryFacade.getInstance().getValue(sessionUser.getServiceCode()));
//		}
		
//		if (sessionUser.getAreaCode() != null) {
//			ParseAreaCode parseAreaCode = new ParseAreaCode();
//			parseAreaCode.parseAreaCode(sessionUser.getAreaCode());
//			
//			products.setLocation(parseAreaCode.getProvince() + parseAreaCode.getCity());
//			
//			if (sessionUser.getAreaCode().length() >= 8) {
//				out.put("country", CategoryFacade.getInstance().getValue(
//						sessionUser.getAreaCode().substring(0, 8)));
//			}
//		}
		
//		out.put("uploadModel", FrontConst.UPLOAD_MODEL_PRODUCTS);
		out.put("productsDO", products);
		out.put("company", companyService.queryCompanyDetailById(sessionUser.getCompanyId()));
		out.put("companyAccount",companyAccountService.queryAccountByAccount(sessionUser.getAccount()));
		
		//会员上传图片的数量
		out.put("uploadPicNum", Integer.valueOf(MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(), "upload_products_picture")));
		
		out.put("mainCodeLabel", mainCodeLabel);
		out.put("mainCodeLabels", mainCodeLabels.replace(",", " > "));
		out.put("provideStatus", provideStatus);
		
		// 相关标签
		out.put("tagsList", TagsUtils.getInstance().queryTagsByTag(mainCodeLabel.replace("/", ""), TagsUtils.ORDER_SEARCH, 10));
		
		try {
			out.put("titleEncode", URLEncoder.encode(products.getTitle(),HttpUtils.CHARSET_UTF8));
			out.put("mainCodeLabelEncode", URLEncoder.encode(mainCodeLabel,HttpUtils.CHARSET_UTF8));
			out.put("mainCodeLabelsEncode", URLEncoder.encode(mainCodeLabels,HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}
		
		return null;
	}

	/**
     * 添加供求信息
     * 
     * @param productsDO为供求信息
     * @param postlimittime为信息有效期时间
     * @return ModelAndView视图
     * @throws Exception 
     */ 
    @RequestMapping
    public ModelAndView publishProducts(ProductsDO products, Integer postlimittime, String grade,
            String picIds, String verifyCode, String verifyCodeKey,Integer togglePrice,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> out,SpotInfo spotInfo ,ProductAddProperties productAddProperties) throws Exception {
        ExtResult result = new ExtResult();

        do {
            SsoUser sessionUser = getCachedUser(request);
            if (sessionUser == null) {
                result.setData("sessionTimeOut");
                break;
            }
            
            // 验证前一张页面是否zz91
            String preUrl = request.getHeader("referer");
            if(StringUtils.isEmpty(preUrl)||StringUtils.isContains(new String[]{"zz91.com","zz9l.com","office_post2"}, preUrl)){
                result.setData("您发布的信息有误，请重试");
                break;
            }
            

            //验证验证码，防止机器注册
            String vcode=String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
            SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);

            if(StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)){
                result.setData("您输入的验证码有错误！");
                break;
            }

            //最后一次添加的时间
            //如果最后一次插入的时间与本次插入时候的系统时间之差timeTemp小于在特定时间则不允许插入:返回一个true
            
            if(!productsService.queryLastGmtCreateTimeByCId(sessionUser.getCompanyId())){
                result.setData("noMore");
                break;
            }
 
            if(!productsService.queryUserIsAddProducts(sessionUser.getCompanyId(),sessionUser.getMembershipCode())){
                result.setData("您今天发布的供求信息已经超过限制！");
                break;
            }

			// 准备数据
			products.setAccount(sessionUser.getAccount());
			products.setCompanyId(sessionUser.getCompanyId());
			Date now = new Date();
			products.setRefreshTime(now);
			// 计算过期时间（本步骤应该在数据库中进行）
			if (postlimittime == -1) {
				products.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",AstConst.DATE_FORMATE_WITH_TIME));
			} else {
				products.setExpireTime(DateUtil.getDateAfterDays(now, postlimittime));
			}
			// 标签分词
			String tagsArr = TagsUtils.getInstance().arrangeTags(products.getTags());
			products.setTags(tagsArr);
			TagsUtils.getInstance().createTags(tagsArr);

            if(togglePrice==null){
                products.setMaxPrice(0f);
            }

            // 信息来源 myrc
            products.setSourceTypeCode("myrc");
            
            // 发布供求信息
            Integer productId = productsService.publishProductsByCompany(products, sessionUser.getMembershipCode(), sessionUser.getAreaCode());

			// 没有发布成功
			if (productId <= 0) {
				result.setData("failureInsert");
				break;
			}
			
			// 发布供求信息 判断简介和主营业务是否为空 截取供求详细
			Company company = companyService.queryCompanyById(sessionUser.getCompanyId());
			if(StringUtils.isEmpty(company.getIntroduction())&&StringUtils.isEmpty(company.getBusiness())){
				company.setIntroduction(products.getDetails());
				String str = Jsoup.clean(products.getDetails(), Whitelist.none());
				if(StringUtils.isNotEmpty(str)&&str.length()>200){
					str = str.substring(0,200);
				}
				company.setBusiness(str);
				companyService.updateCompanyByUser(company);
			}

//          // 添加为报价
//          CompanyPriceDO companyPriceDO = new CompanyPriceDO();
//          companyPriceDO.setProductId(productId);
//          companyPriceDO.setAccount(sessionUser.getAccount());
//          companyPriceDO.setCompanyId(sessionUser.getCompanyId());
//          Company c = new Company();
//          c.setMembershipCode(sessionUser.getMembershipCode());
//          c.setAreaCode(sessionUser.getAreaCode());
//          companyPriceService.addProductsToCompanyPrice(companyPriceDO,c, products);
            
            products.setId(productId);

            Set<Integer> picSet=new HashSet<Integer>();
            String[] pids=picIds.split(",");
            for(String pid: pids){
                if(!"".equals(pid)){
                    picSet.add(Integer.valueOf(pid));
                }
            }
            Integer[] picIdArr=new Integer[picSet.size()];
            
            // 关联上传的图片
            if (StringUtils.isNotEmpty(picIds)) {
                productsService.insertProductsPicRelation(productId,
                        picSet.toArray(picIdArr));
            }

            // 关联标签
//          addTags(tagsArr, products, request);

            result.setData(products);
            result.setSuccess(true);
            // 高级用户可以直接增加积分，因为直接审核通过
            if (!"10051000".equals(sessionUser.getMembershipCode())) {
                scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser.getCompanyId(), null, "get_post_product", null,productId, null));
                List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsId(productId);
                for (ProductsPicDO pic : picList) {
                    scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(sessionUser.getCompanyId(), null,"get_post_product_pic", null, pic.getId(),null));
                }
            }
            
            // 添加现货信息表
            Integer spotId = productsSpotService.addOneSpot(productId); // 获取现货id spot_id
            spotInfo.setSpotId(spotId); // set 现货id
            spotInfoService.addOrEditOneSpotInfo(spotInfo); // 更新现货详细信息
            // 判断添加现货成功后，添加发货地址如company_address表 暂时不做
            
            if (products.getCategoryProductsMainCode().substring(0, 4) != "1001") {
                if (StringUtils.isNotEmpty(productAddProperties.getProperty())) {
                    String[] properties=productAddProperties.getProperty().split(",");
                    String[] contents=productAddProperties.getContent().split(",");
                    for (int i = 0 ; i < properties.length ; i++) {
                        if(!(properties[i].equals("属性")&&contents[i].equals("属性值"))) {
                            productAddProperties.setPid(productId);
                            productAddProperties.setProperty(properties[i]);
                            productAddProperties.setContent(contents[i]);
                            productAddPropertiesService.addProperties(productAddProperties);
                        }
                    }
                }
                if (products.getCategoryProductsMainCode().substring(0, 4).equals("1000")) {
                    productAddProperties.setPid(productId);
                    productAddProperties.setProperty("品位");
                    productAddProperties.setContent(grade);
                    productAddPropertiesService.addProperties(productAddProperties);
                }
            }
            
        } while (false);
        return printJson(result, out);
    }
    
	@RequestMapping
	public ModelAndView office_img(Integer productsId, HttpServletRequest request,
			Map<String, Object> out) {
		// 从缓存中获取account信息
		SsoUser sessionUser = getCachedUser(request);
		ProductsDO productsDO = productsService.queryProductsWithOutDetailsById(productsId);
		if (productsDO != null) {
			if (productsDO.getAccount().equals(sessionUser.getAccount())) {
				List<ProductsPicDO> piclist = productsPicService
						.queryProductPicInfoByProductsId(productsId);
				//查找规则
				String ruleResult = MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(), "upload_products_picture");
				
				//对比结果
				if (piclist != null && piclist.size() >= Integer.valueOf(ruleResult).intValue()) {
					out.put("ruleResult", ruleResult);
					out.put("canUploadStatus", false);
				} else {
					out.put("canUploadStatus", true);
				}
				out.put("productsId", productsId);
				out.put("listsize", piclist.size());
				out.put("piclist", piclist);
				out.put("uploadModel", FrontConst.UPLOAD_MODEL_PRODUCTS);
				out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
						"baseConfig.resource_url"));
				out.put(FrontConst.MYRC_SUBTITLE, "修改图片");
				return null;
			} else {
				out.put("js", "alert('该信息不存在!');history.back();");
				return new ModelAndView("js");
			}
		} else {
			out.put("js", "alert('该信息不存在!');history.back();");
			return new ModelAndView("js");
		}
	}

	@RequestMapping
	public ModelAndView addProductsPicAddr(ProductsPicDO productsPicDO, String fileName,
			Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = null;
		productsPicDO.setPicAddress(fileName);
		if (productsPicDO.getId() == null) {
			productsPicDO.setAlbumId(0);
			productsPicDO.setIsDefault("0");
			productsPicDO.setIsCover("0");
			i = productsPicService.insertProductsPic(productsPicDO);
		} else {
			i = productsPicService.updateProductsPicAddr(productsPicDO);
		}
		result.setSuccess(FrontConst.SUCCESS);
		if (i <= 0) {
			result.setSuccess(FrontConst.FAILDED);
		}

		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView editProductsPicIsDefault(ProductsPicDO productsPicDO,
			Map<String, Object> model) throws IOException {
		// 设置为默认显示为1
		productsPicDO.setIsDefault("1");
		Integer i = productsPicService.updateProductsPicIsDefault(productsPicDO);
		if (i > 0) {
			return new ModelAndView(new RedirectView("office_img.htm?productsId="
					+ productsPicDO.getProductId()));
		}
		model.put(AstConst.ERROR_TEXT, "error text");
		return new ModelAndView("/common/error");
	}

	@RequestMapping
	public ModelAndView editProductsPicName(ProductsPicDO productsPicDO, Map<String, Object> model)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = null;
		i = productsPicService.updateProductsPicName(productsPicDO);
		result.setSuccess(FrontConst.SUCCESS);
		if (i <= 0) {
			result.setSuccess(FrontConst.FAILDED);
		}
		return printJson(result, model);
	}

	/**
	 * 分页查询公司供求信息
	 * 
	 * @param checkStatus
	 *            信息状态类别 0:未审核,1:已审核,2:审核未通过退回,3:暂不发布 默认为1:已审核
	 * @param isExpired
	 *            是否过期信息 1：是，0：否 默认为0：否
	 * @param p
	 *            当前分页
	 * @param out
	 *            为输出结果信息
	 * @throws ParseException
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView office_post_list(String checkStatus, String isExpired, String isPause,
			String isPostFromInquiry, Integer groupId,
			PageDto<ProductsDO> page, HttpServletRequest request, Map<String, Object> out,String result,String title)
			throws ParseException, UnsupportedEncodingException {
		out.put(FrontConst.MYRC_SUBTITLE, "管理供求信息");
		
		//获取公司信息
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());

		// title转码
		if(StringUtils.isNotEmpty(title)){
			title = StringUtils.decryptUrlParameter(title);
//			title = URLDecoder.decode(title, HttpUtils.CHARSET_UTF8);
		}
		
		page=productsService.pageProductsOfCompanyByStatus(sessionUser.getCompanyId(),sessionUser.getAccount(), checkStatus, isExpired, isPause,isPostFromInquiry,groupId,title, page);
		out.put("page", page);
		
		// 供求各类状态数量统计
		out.put("productsCount", productsService.countProductsOfCompanyByStatus(sessionUser.getCompanyId(),
				sessionUser.getAccount(), isPostFromInquiry, page.getTotalRecords()));
		
		// 该列表下，供求图片上传统计 
		out.put("productsPicCount", productsPicService.countProductsPicByProductsId(page.getRecords()));
		
		out.put("checkStatus",checkStatus);
		out.put("isExpired",isExpired);
		out.put("isPause",isPause);
		out.put("groupId", groupId);
		if(StringUtils.isNotEmpty(title)){
			out.put("title", title);
//			out.put("titleEncode", StringUtils.encryptUrlParameter(title));
			out.put("titleEncode", URLEncoder.encode(title, HttpUtils.CHARSET_UTF8));
		}
		out.put("result", result);
		Long interval = Long.valueOf(MemberRuleFacade.getInstance().getValue(sessionUser.getMembershipCode(), "refresh_product_interval"));
		Map<Integer, Boolean> refreshMap=new HashMap<Integer, Boolean>();
		Date now=new Date();
		for(ProductsDO p:page.getRecords()){
			if(p.getRefreshTime()==null){
				refreshMap.put(p.getId(), true);
				continue;
			}
			long intervalNow=now.getTime()-p.getRefreshTime().getTime();
			if(intervalNow>(interval.longValue()*1000)){
				refreshMap.put(p.getId(), true);
			}else{
				refreshMap.put(p.getId(), false);
			}
		}
		out.put("refreshMap", refreshMap);
		
		out.put("seriesList", productsSeriesService.querySeriesOfCompany(sessionUser.getCompanyId()));
		return null;
	}

	@RequestMapping
	public ModelAndView office_post_edit(Integer productsId, HttpServletRequest request,
			Map<String, Object> out) throws ParseException {

		SsoUser sessionUser = getCachedUser(request);
		
		ProductsDO productsDO = productsService.queryProductsById(productsId);
		
		if (productsDO == null) {
			out.put("js", "alert('该信息不存在!');history.back();");
			return new ModelAndView("js");
		}
		
		if (productsDO.getCompanyId()==null || 
				productsDO.getCompanyId().intValue()!=sessionUser.getCompanyId().intValue()) {
			out.put("js", "alert('您不能修改这条信息!');history.back();");
			return new ModelAndView("js");
		}
		
		//获取省市地区code
//		getProductsAreaCode(sessionUser.getAreaCode(), out);
		//获取主类别,辅助类别code
		out.put("manufactureList", categoryService.queryCategoriesByPreCode("1011"));
		//信息类型
		out.put("productsTypeList", categoryService.queryCategoriesByPreCode("1033"));
		//期货现货
		out.put("goodsTypeList", categoryService.queryCategoriesByPreCode("1036"));
		
//		// 获得公司类型label
//		if (sessionUser.getServiceCode() != null) {
//			out.put("companyType", CategoryFacade.getInstance().getValue(
//					sessionUser.getServiceCode()));
//		}
		
//		out.put("tagsInfoList", TagsUtils.getInstance().encodeTags(productsDO.getTags(), "utf-8"));
		
		out.put("day", DateUtil.getIntervalDays(productsDO.getExpireTime(), productsDO.getRefreshTime()));
		out.put("productsDO", productsDO);
		out.put("productsDO", productsDO);
		out.put("company", companyService.queryCompanyDetailById(sessionUser.getCompanyId()));
		out.put("companyAccount", companyAccountService.queryAccountByAccount(sessionUser.getAccount()));
		
//		out.put(FrontConst.MYRC_SUBTITLE, "编辑供求信息");
		
		// 现货id获取
		ProductsSpot productsSpot = productsSpotService.queryByProductId(productsId);
		if(productsSpot!=null&&productsSpot.getId()!=null){
			SpotInfo spotInfo = spotInfoService.queryOneSpotInfo(productsSpot.getId());
			// 制定销售地区截取
			String trans ="";
			if(spotInfo!=null&&StringUtils.isNotEmpty(spotInfo.getTransaction())){
				trans = spotInfo.getTransaction();
			}
			if(StringUtils.isNotEmpty(trans)&&trans.indexOf("指定销售地区")!=-1){
				out.put("trans", trans.replaceAll("指定销售地区", ""));
			}
			out.put("spot", spotInfo);
			
		}
		String isDel= "0";
		List<ProductAddProperties> productAddProperties = productAddPropertiesService.queryByProductId(productsId, isDel);
		out.put("productAddProperties", productAddProperties);
		return null;
	}
	
	@RequestMapping
    public ModelAndView updateIsDel(HttpServletRequest request,Map<String,Object>out,Integer productId , String property) throws IOException{
        ExtResult result = new ExtResult();
        String prop = URLDecoder.decode(property,"utf-8");
        String isDel = "1";
        ProductAddProperties productAddProperties = productAddPropertiesService.queryByPidAndProperty(productId, prop);
        Integer id = productAddProperties.getId();
        Integer i = productAddPropertiesService.updateIsDelById(isDel, id);
        if(i>0){
            result.setSuccess(true);
        }
        
        return printJson(result, out);
    }
	/**
     * 修改供求信息
     * 
     * @param productsDO为供求信息
     * @param postlimittime为信息有效期时间
     * @return ModelAndView视图
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping
    public ModelAndView editProducts(ProductsDO productsDO, String postlimittime,SpotInfo spotInfo,
            Integer togglePrice,ProductAddProperties productAddProperties, String grade, String propertyId,
            HttpServletRequest request, Map<String, Object> out) throws IOException,
            ParseException {
        // 从缓存中获取companyId,account信息
        SsoUser sessionUser = getCachedUser(request);
        productsDO.setCompanyId(sessionUser.getCompanyId());
        Date now = new Date();
        productsDO.setRefreshTime(now); 
        if (postlimittime==null || "-1".equals(postlimittime)) {
            // 有效期为最大时间
            productsDO.setExpireTime(DateUtil.getDate(AstConst.MAX_TIMT,
                    AstConst.DATE_FORMATE_WITH_TIME));
        } else {
            productsDO.setExpireTime(DateUtil.getDateAfterDays(now, Integer.valueOf(postlimittime)));
        }
        productsDO.setTags(TagsUtils.getInstance().arrangeTags(productsDO.getTags()));
        
        if(togglePrice==null){
            productsDO.setMaxPrice(0f);
        }
        productsService.updateProductsByCompany(productsDO, sessionUser.getMembershipCode());
        String mainCode = productsDO.getCategoryProductsMainCode();
        if (mainCode != null) {
            List<ProductsDO> mainList = productsService.queryNewestProducts(mainCode, null, 15);
            for(ProductsDO obj:mainList){
                if(!StringUtils.isNumber(obj.getQuantity())){
                    obj.setQuantityUnit(null);
                }
            }
            out.put("mainList", mainList);
        }
        
        TagsUtils.getInstance().createTags(productsDO.getTags());
        
        out.put("id", productsDO.getId());
        
        int productId = productsDO.getId();
        
        if (productsDO.getCategoryProductsMainCode().substring(0, 4) != "1001") {
            if (StringUtils.isNotEmpty(productAddProperties.getProperty())) {
                String[] properties=productAddProperties.getProperty().split(",");
                String[] contents=productAddProperties.getContent().split(",");
                String[] propertiesId ={};
                if (propertyId != null) {
                    propertiesId = propertyId.split(",");
                }
                for (int i = 0 ; i < properties.length ; i++) {
                    if (propertyId == null || propertiesId.length <= i) {
                        if(!(properties[i].equals("属性")&&contents[i].equals("属性值"))){
                            productAddProperties.setPid(productId);
                            productAddProperties.setProperty(properties[i]);
                            productAddProperties.setContent(contents[i]);
                            productAddPropertiesService.addProperties(productAddProperties);
                        }
                    } else {
                        productAddProperties.setId(Integer.parseInt(propertiesId[i]));
                        productAddProperties.setProperty(properties[i]);
                        productAddProperties.setContent(contents[i]);
                        int j = productAddPropertiesService.updateProperties(productAddProperties);
                        if (j == 0) {
                            break;
                        }
                    }
                }
            }
            if (productsDO.getCategoryProductsMainCode().substring(0, 4).equals("1000")) {
                ProductAddProperties pap = productAddPropertiesService.queryByPidAndProperty(productId, "品位");
                if (pap != null) {
                    productAddProperties.setId(pap.getId());
                    productAddProperties.setProperty("品位");
                    productAddProperties.setContent(grade);
                    productAddPropertiesService.updateProperties(productAddProperties);
                }
               
            }
        }
        ProductsSpot productsSpot = productsSpotService.queryByProductId(productId);
        if (productsSpot !=null) {
            spotInfo.setSpotId(productsSpot.getId());
            int i = spotInfoService.addOrEditOneSpotInfo(spotInfo); // 更新现货详细信息
            if (i != 0) {
                return null;
            }
        }
        
        return null;
    }
	/**
	 * 修改供求信息刷新时间
	 * 
	 * @param ids为供求信息主键
	 * @param isRefreshAll
	 *            是否一键刷新
	 * @param out为输出结果信息
	 * @return ModelAndView视图
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView refreshProducts(Integer id, HttpServletRequest request, 
			Map<String, Object> out) throws ParseException, IOException {
		// 从缓存中获取companyId,account信息
		ExtResult result=new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		
		Integer i=productsService.refreshProducts(id, sessionUser.getCompanyId(), sessionUser.getMembershipCode());
		if(i!=null&&i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView productsIdOfCompany(HttpServletRequest request, Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		result.setData(productsService.queryProductsIdsOfCompany(getCachedUser(request).getCompanyId(), null));
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView pauseProducts(HttpServletRequest request, Map<String, Object> out, String ids, String isPause) throws IOException{
		ExtResult result = new ExtResult();
		Integer impact=productsService.updateProductsIsPause(isPause, StringUtils.StringToIntegerArray(ids));
		if(impact!=null && impact.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

//	@RequestMapping
//	public ModelAndView changeProductsPublishStatus(String productIds, String isPause) {
//		productsService.updateProductsIsPause(isPause, StringUtils.StringToIntegerArray(productIds));
//		return new ModelAndView("myrc/myproducts/office_post_list");
//	}

	/**
	 * 修改发布供求审核状态
	 * 
	 * @param ids为获得的供求系列主键值
	 * @return ModelAndView视图
	 */
//	@RequestMapping
//	public ModelAndView editProductsCheckStatus(@RequestParam(required = false) String productIds) {
//		productsService.updateProductsCheckStatus(productIds);
//		
//		return new ModelAndView("myrc/myproducts/office_post_list");
//	}

	/**
	 * 删除发布供求信息
	 * 
	 * @param ids为获得的供求系列主键值
	 * @return ModelAndView视图
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteProducts(String productIds, Map<String, Object> model,HttpServletRequest request)
			throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		ExtResult result = new ExtResult();
		String[] str = productIds.split(",");
		Integer[] i = new Integer [str.length];
		for (int ii = 0; ii < str.length; ii++) {
			i[ii] = Integer.valueOf(str[ii]);
		}
		
		int j = productsService.batchDeleteProductsByIds(i,ssoUser.getCompanyId());
		result.setSuccess(FrontConst.SUCCESS);
		if (j <= 0) {
			result.setSuccess(FrontConst.FAILDED);
		}
		LogUtil.getInstance().log(ssoUser.getAccount(),"myrc-delete-products", HttpUtils.getInstance().getIpAddr(request), "products:"+productIds);
		return printJson(result, model);
	}

	/**
	 * 查询已分类别和未分类别的供求系列ProductsSeriesDTO信息
	 * 
	 * @param productsSeriesDO为供求系列信息
	 * @param out为输出结果信息
	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping
//	public void offer_group(HttpServletRequest request, String showSeriesName, PageDto page,
//			Map<String, Object> out) {
////		page.setRecords(null);
//		page.setPageSize(15);
//		// 从缓存中获取companyId信息
//		Integer companyId = getCachedUser(request).getCompanyId();
//		ProductsSeriesDO productsSeriesDO = new ProductsSeriesDO();
//		productsSeriesDO.setCompanyId(companyId);
//		// 获取用户的系列列表
//		List<ProductsSeriesDO> userSeriesList = productsSeriesService
//				.queryProductsSeries(productsSeriesDO);
//		out.put("userSeriesList", userSeriesList);
//		try {
//			showSeriesName = StringUtils.decryptUrlParameter(StringUtils
//					.getNotNullValue(showSeriesName));
//		} catch (UnsupportedEncodingException e) {}
//		out.put("showSeriesName", showSeriesName);
//		//根据companyId查询已分类别供求系列详细
//		//如果显示的系列名在系列列表中，查询
//		if (StringUtils.isNotEmpty(showSeriesName))
//			for (ProductsSeriesDO userSeries : userSeriesList) {
//				if (showSeriesName.equals(userSeries.getName())) {
//					//查询系列下的产品分页列表
//					page = productsSeriesService.queryProductInSeriesListBySeriesId(userSeries
//							.getId(), page);
//				}
//			}
//		// 如果没有显示的系列信息列表，根据companyId查询未分类别
//		if (page.getRecords() == null || page.getRecords().size() < 1) {
//			out.put("showSeriesName", "");
//			page = productsSeriesService.queryProductNotInSeriesListByCompanyId(companyId, page);
//		}
//		out.put("page", page);
//		out.put("suffixUrl", "showSeriesName=" + showSeriesName);
//		out.put("productInfoShowList", page.getRecords());
//		out.put(FrontConst.MYRC_SUBTITLE, "管理供求系列");
//	}

	/**
	 * 根据id查询ProductsSeriesDO信息
	 * 
	 * @param id为ProductsSeriesDO主键值
	 * @param out为输出结果信息
	 * @return ModelAndView视图
	 */
//	@RequestMapping
//	public ModelAndView queryProductsSeriesById(@RequestParam(required = false) Integer id,
//			Map<String, Object> out) {
//		// 根据id查询ProductsSeriesDO
//		ProductsSeriesDO productsSeriesDO = productsSeriesService.queryProductsSeriesById(id);
//		out.put("productsSeriesDO", productsSeriesDO);
//		return new ModelAndView("/myproducts/alert_group_update");
//	}

	/**
	 * 修改ProductsSeriesDO信息
	 * 
	 * @param productsSeriesDO为供求系列信息
	 * @return ModelAndView视图
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView alert_group_update(ProductsSeriesDO productsSeriesDO,
//			HttpServletRequest request, Map<String, Object> out) throws IOException {
//		ExtResult result = new ExtResult();
//		if (productsSeriesDO.getId() != null) {
//
//			// 修改ProductsSeriesDO信息，其中包括隐藏属性
//			Integer im = productsSeriesService.updateProductsSeries(productsSeriesDO);
//			if (im.intValue() > 0) {
//				result.setSuccess(true);
//			}
//			//new ModelAndView(new RedirectView("offer_group.htm"));
//		} else {
//			result.setData("该信息不存在!");
//			//			out.put("js", "alert('该信息不存在!');history.back();");
//			//			return new ModelAndView("js");
//		}
//
//		return printJson(result, out);
//	}

	/**
	 * 更新系列排序
	 * 
	 * @param productsSeriesDO
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView updateProductsSeriesOrder(ProductsSeriesDO productsSeriesDO,
//			HttpServletRequest request, Map<String, Object> out) throws IOException {
//		ExtResult result = new ExtResult();
//
//		Integer im = productsSeriesService.updateProductsSeriesOrder(productsSeriesDO);
//		if (im.intValue() > 0) {
//			result.setSuccess(true);
//		}
//
//		return printJson(result, out);
//	}

	/**
	 * 删除ProductsSeriesDO,ProductsSeriesContactsDO信息
	 * 
	 * @param id为ProductsSeriesDO主键
	 * @param productsSeriesContactsId为ProductsSeriesContactsDO主键
	 * @return ModelAndView视图
	 */
//	@RequestMapping
//	public ModelAndView deleteProductsSeries(@RequestParam(required = false) Integer id,
//			HttpServletRequest request, Map<String, Object> model) throws IOException {
//		if (id == null) {
//			model.put(AstConst.ERROR_TEXT, "error text");
//			return new ModelAndView("/common/error");
//		} else {
//			// 删除ProductsSeriesDO
//			Integer i = productsSeriesService.deleteProductsSeries(id);
//			if (i > 0) {
//				return new ModelAndView(new RedirectView("offer_group.htm"));
//			} else {
//				model.put(AstConst.ERROR_TEXT, "error text");
//				return new ModelAndView("/common/error");
//			}
//		}
//	}

	/**
	 * 修改供求系列关联信息
	 * 
	 * @param productsSeriesContactsDO为供求系列关联信息
	 * @return ModelAndView视图
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView editProductsSeriesContacts(
//			ProductsSeriesContactsDO productsSeriesContactsDO, Map<String, Object> model)
//			throws IOException {
//		// 此处可以根据ProductsSeriesDTO获得ProductsSeriesContactsDO信息
//		Integer i = productsSeriesService.updateProductsSeriesContacts(productsSeriesContactsDO);
//		ExtResult result = new ExtResult();
//		if (i > 0) {
//			result.setSuccess(true);
//		}
//		return printJson(result, model);
//	}

//	@RequestMapping
//	public ModelAndView alert_group_create() {
//		return null;
//	}

	/**
	 * 添加ProductsSeriesDO信息
	 * 
	 * @param productsSeriesDO为供求系列信息
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView addProductsSeries(Map<String, Object> model,
//			ProductsSeriesDO productsSeriesDO, HttpServletRequest request) throws IOException {
//		ExtResult result = new ExtResult();
//		// 从缓存中获取companyId,account信息
//		SsoUser sessionUser = getCachedUser(request);
//		productsSeriesDO.setCompanyId(sessionUser.getCompanyId());
//		productsSeriesDO.setAccount(sessionUser.getAccount());
//		productsSeriesDO.setName(productsSeriesDO.getName().trim());
//		// productsSeriesDO.setSeriesDetails(productsSeriesDO.getSeriesDetails().trim());
//		// 此处可以得到ProductsSeriesDO信息，包括隐藏seriesOrder=1，companyId，account
//		if (productsSeriesDO.getCompanyId() != null) {
//			Integer im = productsSeriesService.insertProductsSeries(productsSeriesDO);
//			if (im.intValue() > 0) {
//				result.setSuccess(true);
//			} else {
//				result.setData("添加失败!");
//			}
//		}
//		return printJson(result, model);
//	}

	/**
	 * 添加ProductsSeriesContactsDO信息
	 * 
	 * @param productsSeriesContactsDO为供求系列关联信息
	 * @return ModelAndView视图
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
//	@RequestMapping
//	public ModelAndView addProductsSeriesContacts(
//			ProductsSeriesContactsDO productsSeriesContactsDO, Map<String, Object> model)
//			throws IOException {
//		Integer i = productsSeriesService.insertProductsSeriesContacts(productsSeriesContactsDO);
//		ExtResult result = new ExtResult();
//		if (i > 0) {
//			result.setSuccess(true);
//		}
//		return printJson(result, model);
//	}

//	@RequestMapping
//	public void offer_group_go(@RequestParam(required = false) String ids,
//			HttpServletRequest request, Map<String, Object> out) {
//		// 从缓存中获取companyId,account信息
//		SsoUser sessionUser = getCachedUser(request);
//		ProductsSeriesDO productsSeriesDO = new ProductsSeriesDO();
//		productsSeriesDO.setCompanyId(sessionUser.getCompanyId());
//		List<ProductsSeriesDO> list = productsSeriesService.queryProductsSeries(productsSeriesDO);
//		out.put("ids", ids);
//		out.put("seriesList", list);
//	}

//	@RequestMapping
//	public ModelAndView editProductsSeriesName(Integer id, String name, Map<String, Object> model)
//			throws IOException {
//		ExtResult result = new ExtResult();
//		ProductsSeriesDO productsSeriesDO = new ProductsSeriesDO();
//		productsSeriesDO.setId(id);
//		productsSeriesDO.setName(name);
//
//		if (productsSeriesDO.getId() == null) {
//			model.put(AstConst.ERROR_TEXT, "error text");
//			return new ModelAndView("/common/error");
//		} else {
//			Integer i = productsSeriesService.updateProductsSeriesName(productsSeriesDO);
//			result.setSuccess(FrontConst.SUCCESS);
//			if (i <= 0) {
//				result.setSuccess(FrontConst.FAILDED);
//			}
//			return printJson(result, model);
//		}
//	}

	/**
	 * 删除ProductsSeriesDO,ProductsSeriesContactsDO信息
	 * 
	 * @param id为ProductsSeriesDO主键
	 * @param productsSeriesContactsId为ProductsSeriesContactsDO主键
	 * @return ModelAndView视图
	 */
//	@RequestMapping
//	public ModelAndView deleteProductsSeriesJson(@RequestParam(required = false) Integer id,
//			HttpServletRequest request, Map<String, Object> model) throws IOException {
//		ExtResult result = new ExtResult();
//		if (id == null) {
//			model.put(AstConst.ERROR_TEXT, "error text");
//			return new ModelAndView("/common/error");
//		} else {
//			// 删除ProductsSeriesDO
//			Integer i = productsSeriesService.deleteProductsSeries(id);
//			if (i > 0) {
//				result.setSuccess(FrontConst.SUCCESS);
//				return printJson(result, model);
//			} else {
//				model.put(AstConst.ERROR_TEXT, "error text");
//				return new ModelAndView("/common/error");
//			}
//		}
//	}

//	@RequestMapping
//	public ModelAndView editProductsSeriesContactsStatus(
//			@RequestParam(required = false) String ids,
//			@RequestParam(required = false) Integer productsSeriesId, Map<String, Object> model)
//			throws IOException {
//		ExtResult result = new ExtResult();
//		Integer i = productsSeriesService.editProductsSeriesContactsStatus(ids, productsSeriesId);
//		if (i > 0) {
//			result.setSuccess(FrontConst.SUCCESS);
//			return printJson(result, model);
//		} else {
//			model.put(AstConst.ERROR_TEXT, "error text");
//			return new ModelAndView("/common/error");
//		}
//	}

	/**
	 * 添加标签
	 * 
	 * @param tagsName
	 * @param articleId
	 */
//	private void addTags(String tagsName, ProductsDO productsDO, HttpServletRequest request) {
//		int pdtid = productsDO.getId();
//		//获取标签列表
//		String[] tagNames = StringUtils.distinctStringArray(tagsName.split(","));
//		//获取操作员信息
//		SsoUser sessionUser = getCachedUser(request);
//		//删除与文章相关的所有标签关联信息
//		List<TagsInfoDO> tagsList = tagsArticleService
//				.queryTagListFromTagsArticleRelationByArticleId("10351001", pdtid);
//		List<String> tagNamesQueried = new ArrayList<String>();
//		//tagNamesQueried 存在，tagNames 不存在 删除标签关联
//		List<TagsArticleRelation> deletedTagArtRelList = new ArrayList<TagsArticleRelation>();
//		//tagNamesQueried 不存在，tagNames 存在 新新标签关联
//		List<TagsArticleRelation> newTagArtRelList = new ArrayList<TagsArticleRelation>();
//		for (TagsInfoDO tag : tagsList) {
//			tagNamesQueried.add(tag.getName());
//			if (!StringUtils.isContains(tagNames, tag.getName())) {
//				TagsArticleRelation tagArtRel = new TagsArticleRelation();
//				tagArtRel.setTagId(tag.getId());
//				tagArtRel.setTagName(tag.getName());
//				tagArtRel.setArticleId(pdtid);
//				deletedTagArtRelList.add(tagArtRel);
//			}
//		}
//		tagsArticleService.deleteTagsArticleRelationByArticleId("10351001", pdtid);
//		//设置,添加标签关联信息
//		for (String tagName : tagNames) {
//			TagsArticleRelation relation = new TagsArticleRelation();
//			relation.setArticleModuleCode("10351001");// 10351001 ,供求信息
//			relation.setArticleCategoryCode(productsDO.getCategoryProductsMainCode());
//			relation.setArticleId(pdtid);
//			relation.setArticleTitle(productsDO.getTitle());
//			relation.setTagName(tagName);
//			relation.setCreator(sessionUser.getAccountId());
//			try {
//				tagsArticleService.insertTagsArticleRelation(relation);
//				if (!tagNamesQueried.contains(tagName)) {
//					newTagArtRelList.add(relation);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
////		List<OperationLogInfo> optLogList = OperationLogCreator
////				.createTagLogInfoByTagArticleRelation(OperationLogLevel.INFO.getValue(), request
////						.getRemoteAddr(), request.getContextPath(), cc,
////						TagOptType.DELETE.getCode(), "删除标签和供求文章关联关系", deletedTagArtRelList
////								.toArray(new TagsArticleRelation [] {}));
////		optLogList.addAll(OperationLogCreator.createTagLogInfoByTagArticleRelation(
////				OperationLogLevel.INFO.getValue(), request.getRemoteAddr(), request
////						.getContextPath(), cc, TagOptType.CREATE.getCode(), "新建标签和供求文章关联关系",
////				newTagArtRelList.toArray(new TagsArticleRelation [] {})));
////		operationLogInfoService.insertOperationLogInfos(optLogList);
//	}

	@RequestMapping
	public void postpic_upload() {

	}

	/**
	 * 初始化省市地区code
	 * 
	 * @param companyDO
	 * @param productsDO
	 * @param out
	 */
//	private void getProductsAreaCode(String areaCode, Map<String, Object> out) {
//		if (areaCode != null) {
//			ParseAreaCode parseAreaCode = new ParseAreaCode();
//			parseAreaCode.parseAreaCode(areaCode);
//			out.put("country", parseAreaCode.getCounty());
//			out.put("province", parseAreaCode.getProvince() + " " + parseAreaCode.getCity());
//		}
//	}

	/**
	 * 获得辅助类别code 获得主类别code
	 * 
	 * @param productsDO
	 * @param out
	 */
//	private void getProductsCode(ProductsDO productsDO, Map<String, Object> out) {
//		String code4 = null, code8 = null, code12 = null, code16 = null;
//		// 获得辅助类别code
//		String assistcode = productsDO.getCategoryProductsAssistCode();
//		// 获得主类别code
//		String categoryProductsMainCode = productsDO.getCategoryProductsMainCode();
//		if (categoryProductsMainCode.length() == 4) {
//			// 获得主类别code前四位(第一类code)
//			code4 = categoryProductsMainCode.substring(0, 4);
//		}
//		if (categoryProductsMainCode.length() == 8) {
//			// 获得主类别code前八位(第二类code)
//			code8 = categoryProductsMainCode.substring(0, 8);
//			// 获得主类别code前四位(第一类code)
//			code4 = categoryProductsMainCode.substring(0, 4);
//		}
//		if (categoryProductsMainCode.length() == 12) {
//			// 获得主类别code前十二位(第三类code)
//			code12 = categoryProductsMainCode.substring(0, 12);
//			// 获得主类别code前八位(第二类code)
//			code8 = categoryProductsMainCode.substring(0, 8);
//			// 获得主类别code前四位(第一类code)
//			code4 = categoryProductsMainCode.substring(0, 4);
//		}
//		if (categoryProductsMainCode.length() == 16) {
//			// 获得主类别code前十六位(第四类code)
//			code16 = categoryProductsMainCode.substring(0, 16);
//			// 获得主类别code前十二位(第三类code)
//			code12 = categoryProductsMainCode.substring(0, 12);
//			// 获得主类别code前八位(第二类code)
//			code8 = categoryProductsMainCode.substring(0, 8);
//			// 获得主类别code前四位(第一类code)
//			code4 = categoryProductsMainCode.substring(0, 4);
//		}
//		out.put("code4", code4);
//		out.put("code8", code8);
//		out.put("code12", code12);
//		out.put("code16", code16);
//		out.put("assistcode", assistcode);
//	}

	@RequestMapping
	public ModelAndView validateProductTitle(String title, String productsTypeCode, HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		if(!productsService.isProductsAlreadyExists(title, productsTypeCode, sessionUser.getAccount())){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView office_post_suc(Integer productId, Map<String, Object> out,String from) throws UnsupportedEncodingException {
		ProductsDO productsDO = productsService.queryProductsWithOutDetailsById(productId);
		String mainCode = productsDO.getCategoryProductsMainCode();
		if (mainCode != null) {
			List<ProductsDO> mainList = productsService.queryNewestProducts(mainCode, null, 15);
			for(ProductsDO obj:mainList){
				if(!StringUtils.isNumber(obj.getQuantity())){
					obj.setQuantityUnit(null);
				}
			}
			out.put("mainList", mainList);
		}
		Integer imgCount = productsPicService.countProductPicByProductId(productId);

		String escapeKeywords =URLEncoder.encode(productsDO.getTitle(),"utf-8");
		out.put("escapeKeywords", escapeKeywords);
		out.put("productsDO", productsDO);
		out.put("imgCount", imgCount);
		// 来源自快速发布
		out.put("from", from);
		
		out.put("zst_phone", ParamUtils.getInstance().getValue("site_info_front", "zst_phone"));
		return null;
	}
	
	/**
	 * 删除图片
	 * @param model
	 * @param picids
	 * @param productsId
	 * @return
	 */
	@RequestMapping
	public ModelAndView deletePic(HttpServletRequest request, Map<String, Object> model,String ids,Integer productsId){
		if(StringUtils.isNumber(ids)) {
			Integer[] id=new Integer[1];//picids.split(",");
			id[0]=new Integer(ids);
			productsPicService.batchDeleteProductPicbyId(id);
		}
		
		return new ModelAndView(new RedirectView(request.getContextPath()+"/myproducts/office_img.htm?productsId="+productsId));
	}
	
	@RequestMapping
	public ModelAndView deleteOnlyPic(HttpServletRequest request, Map<String, Object> out,Integer id) throws IOException{
		Integer[] ids=new Integer[1];//picids.split(",");
		ids[0]=id;
		productsPicService.batchDeleteProductPicbyId(ids);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView postProductsPic(Map<String, Object> out,
			ProductsPicDO picture) throws IOException {
		ExtResult result = new ExtResult();
		picture.setIsDefault("0");
		picture.setIsCover("0");
		Integer i = productsPicService.insertProductsPic(picture);
		if (i.intValue() > 0) {
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	
	/************group 重构********/
	
	@RequestMapping
	public ModelAndView createGroup(HttpServletRequest request, Map<String, Object> out, Integer error,String destUrl){
		out.put("destUrl", destUrl);
		out.put("error", error);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doCreateGroup(HttpServletRequest request, Map<String, Object> out, ProductsSeriesDO group,String destUrl){
		//加上数量限制，不允许创建超过X个系列
		SsoUser user=getCachedUser(request);
		group.setAccount(user.getAccount());
		group.setCompanyId(user.getCompanyId());
		
		Integer i=productsSeriesService.createSeries(group);
		
		
		if(i!=null && i.intValue()>0){
//			return new ModelAndView("redirect:"+request.getContextPath()+"/submitCallback.htm");
			return new ModelAndView("redirect:"+"/submitCallback.htm");
		}
		
		out.put("error", 1);
		return new ModelAndView("redirect:createGroup.htm");
	}
	
	@RequestMapping
	public ModelAndView editGroup(HttpServletRequest request, Map<String, Object> out, Integer id, Integer error){
		
		out.put("group", productsSeriesService.queryProductsSeriesById(id));
		out.put("error", error);
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView doEditGroup(HttpServletRequest request, Map<String, Object> out, ProductsSeriesDO group){
		
		SsoUser user=getCachedUser(request);
		group.setAccount(user.getAccount());
		group.setCompanyId(user.getCompanyId());
		
		Integer i=productsSeriesService.updateProductsSeries(group);
		
		if(i!=null && i.intValue()>0){
			return new ModelAndView("redirect:groupList.htm");
		}
		
		out.put("id", group.getId());
		out.put("error", 1);
		return new ModelAndView("redirect:editGroup.htm");
	}
	
//	@RequestMapping
//	public ModelAndView queryGroupCombo(HttpServletRequest request, Map<String, Object> out) throws IOException{
//		List<ProductsSeriesDO> list=productsSeriesService.querySeriesOfCompany(getCachedUser(request).getCompanyId());
//		return printJson(list, out);
//	}
	
	@RequestMapping
	public ModelAndView createGroupContact(HttpServletRequest request, Map<String, Object> out, 
			String ids, Integer error){
		//TODO 查询公司的系列
		
		List<ProductsSeriesDO> list=productsSeriesService.querySeriesOfCompany(getCachedUser(request).getCompanyId());
		out.put("groupList", list);
		out.put("ids", ids);
		out.put("error", error);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doCreateGroupContact(HttpServletRequest request, Map<String, Object> out, String ids, Integer groupId){
		Integer[] idArr=StringUtils.StringToIntegerArray(ids);
		Integer i=productsSeriesService.createSeriesContacts(groupId, idArr);
		
		if(i!=null && i.intValue()>0){
			return new ModelAndView("redirect:/submitCallback.htm");
		}
		
		out.put("error", 1);
		out.put("ids", ids);
		return new ModelAndView("redirect:createGroupContact.htm");
	}
	
	@RequestMapping
	public ModelAndView doDeleteGroupContact(HttpServletRequest request, Map<String, Object> out, 
			String ids, Integer groupId) throws IOException{
		
		Integer[] idArr=StringUtils.StringToIntegerArray(ids);
		Integer i=productsSeriesService.deleteSeriesContacts(groupId, idArr);
		
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteGroup(HttpServletRequest request, Map<String, Object> out, Integer id){
		
		Integer i=productsSeriesService.deleteProductsSeries(id);
		
		if(i==null || i.intValue()<=0){
			out.put("error", 1);
		}
		
//		return new ModelAndView("redirect:"+request.getContextPath()+"/myproducts/groupList.htm");
		return new ModelAndView("redirect:groupList.htm");
	}
	
	@RequestMapping
	public ModelAndView groupList(HttpServletRequest request, Map<String, Object> out, Integer error){
		SsoUser user=getCachedUser(request);
		myrcService.initMyrc(out, user.getCompanyId());//查询是否开通商铺服务
		out.put("error", error);
		
		List<ProductsSeriesDO> list=productsSeriesService.querySeriesOfCompany(getCachedUser(request).getCompanyId());
		out.put("groupList", list);
		
		return null;
	}
	
	/**
	 * 图片置顶
	 * @param request
	 * @param id
	 * @param productsId
	 * @return
	 */
	@RequestMapping
	public ModelAndView stickiePic(HttpServletRequest request,Integer ids,Integer productsId){
		ProductsPicDO p = new ProductsPicDO();
		p.setIsDefault("1");
		p.setId(ids);
		p.setProductId(productsId);
		productsPicService.updateProductsPicIsDefault(p);
		return new ModelAndView(new RedirectView(request.getContextPath()+"/myproducts/office_img.htm?productsId="+productsId));
	}
	
}
