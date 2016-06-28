/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25 下午02:47:11
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsRubService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.products.ProductsSpotService;
import com.ast.ast1949.service.products.ProductsZstExpiredService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.spot.SpotInfoService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Controller
public class ProductsController extends BaseController {
	@Autowired
	private ProductsService productsService;
	@Autowired
	private CategoryProductsService categoryProductsService;
	@Autowired
	private ProductsPicService productsPicService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyPriceService companyPriceService;
	@Autowired
	private CrmCompanySvrService crmCompanySvrService;
	@Autowired
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private ProductsZstExpiredService productsZstExpiredService;
	@Resource
	private ProductsSpotService productsSpotService;
	@Resource
	private ProductsRubService productsRubService;
	@Resource
	private ProductAddPropertiesService productAddPropertiesService;
	@Resource
	private SpotInfoService spotInfoService;

	final static String UPLOAD_MODEL = "products";
	final static String CHECK_SUCCESS_OPERTION = "check_products_success";
	final static String CHECK_FAILURE_OPERTION = "check_products_failure";
	final static String COMP_PRICE_CHECK_SUCCESS_OPERTION = "check_compprice_success";
	final static String DEL_PRODUCT_OPERTION = "del_product";
	final static String UNDEL_PRODUCT_OPERTION = "undel_product";
	
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final static String NO_VIP = "10051000";
	
	final static String API_HOST="http://test.zz91.com:8800/web/zz91/admin/products/";
	final static String URL_FOAMAT = "http://trade.zz91.com/productdetails";
	final static String URL_FIX = ".htm";

	/**
	 * 供求列表页
	 */
	@RequestMapping
	public void list(Map<String, Object> model,String account,String from,String to,String status) {
		model.put("model", UPLOAD_MODEL);
		// model.put("imageServer",
		// MemcachedUtils.getInstance().getClient().get(
		// "baseConfig.resource_url"));
		model.put("imageServer", AddressTool.getAddress("resources"));
		model.put("account", account);
		model.put("status", status);
		model.put("from", from);
		model.put("to", to);
	}

	/**
	 * 搜索过期高会的供求
	 */
	@RequestMapping
	public void listZstExpired(Map<String, Object> model) {
		model.put("model", UPLOAD_MODEL);
		model.put("imageServer", AddressTool.getAddress("resources"));
	}
	
	/**
	 * 搜索 现货供求
	 */
	@RequestMapping
	public void listSpot(Map<String, Object> model) {
		model.put("model", UPLOAD_MODEL);
		model.put("imageServer", AddressTool.getAddress("resources"));
	}
	
	/**
	 * 搜索 违规供求
	 */
	@RequestMapping
	public void listRub(Map<String, Object> model) {
		model.put("model", UPLOAD_MODEL);
		model.put("imageServer", AddressTool.getAddress("resources"));
	}
	
	@RequestMapping
	public void edit(String productid, String companyid, String account,
			Map<String, Object> model) {
		model.put("productId", productid);
		model.put("companyId", companyid);
		model.put("account", account);
		model.put("model", UPLOAD_MODEL);
		model.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
	}
	
	@RequestMapping
	public void editRub(String productid, String companyid, String account,
			Map<String, Object> model) {
		model.put("productId", productid);
		model.put("companyId", companyid);
		model.put("account", account);
		model.put("model", UPLOAD_MODEL);
		model.put("resourceUrl", (String) MemcachedUtils.getInstance()
				.getClient().get("baseConfig.resource_url"));
	}

	// @RequestMapping
	// public ModelAndView checkStatusCombo(Map<String, Object> model) throws
	// IOException{
	// PageDto page = new PageDto();
	// return printJson(page, model);
	// }

	@RequestMapping
	public void createInquiry(Map<String, Object> model, Integer id) {
		ProductsDO p = productsService.queryProductsById(id);
		p.setDetails(StringUtils.removeHTML(p.getDetails()));
		model.put("products", p);
	}

	@RequestMapping
	public void createKeywordsRank(Map<String, Object> model, Integer id) {
		ProductsDO p = productsService.queryProductsById(id);
		p.setDetails(StringUtils.removeHTML(p.getDetails()));
		model.put("products", p);
	}

	@RequestMapping
	public void createProductsrare(Map<String, Object> model, Integer id,
			HttpServletRequest request) {
		ProductsDO p = productsService.queryProductsById(id);
		p.setDetails(StringUtils.removeHTML(p.getDetails()));
		model.put("products", p);
		SessionUser sessionUser = getCachedUser(request);
		model.put("adminAccount", sessionUser.getAccount());
	}

	final static String ASSIST_TRUE = "1";

	@RequestMapping
	public ModelAndView assistCombo(Map<String, Object> model, String parentCode)
			throws IOException {
		if (parentCode == null || "0".equals(parentCode)) {
			parentCode = "";
		}
		PageDto<ExtTreeDto> page = new PageDto<ExtTreeDto>();
		page.setRecords(categoryProductsService.child(parentCode, ASSIST_TRUE));
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView init(Integer id, Map<String, Object> map)
			throws IOException {
		
		// 判断供求是否已经存在与 companyprice 表中
		CompanyPriceDO cDo = companyPriceService.queryCompanyPriceByProductId(id);
		if(cDo!=null){
			productsService.updateProductsIsShowInPrice(id, "1");
		}else{
			productsService.updateProductsIsShowInPrice(id, "0");
		}

		ProductsDto productsDTO = productsService.queryProductsDetailsById(id);

		productsDTO.setCompany(companyService.queryCompanyById(productsDTO.getProducts().getCompanyId()));
		
		// 后台标签为空 使用分词工具 暂时不发布
//		if(productsDTO!=null&&productsDTO.getProducts()!=null&&StringUtils.isEmpty(productsDTO.getProducts().getTagsAdmin())){
//			productsDTO.getProducts().setTagsAdmin(productsService.getTagAdmin(productsDTO));
//		}
		
		Boolean boo=crmCompanySvrService.validatePeriod(productsDTO.getProducts().getCompanyId(), "10001002");
		if(boo==true){
			productsDTO.getProducts().setAccount(productsDTO.getProducts().getAccount()+"(百度优化)");
		}
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		list.add(productsDTO);
		page.setRecords(list);

		return printJson(page, map);
	}
	
   @RequestMapping
    public ModelAndView loadMoreProp(Integer id, Map<String, Object> map)
            throws IOException {
       
       List<ProductAddProperties> productAddProperties = new ArrayList<ProductAddProperties>();
       ProductsDO productsDO = productsService.queryProductsById(id);
       String code = productsDO.getCategoryProductsMainCode().substring(0, 4);
        if (code != "1001") {
            productAddProperties = productAddPropertiesService.queryByPid(id);
        }
        for (int i = 0 ; i < productAddProperties.size(); i++) {
            if (productAddProperties.get(i).getProperty().equals("品位")) {
                productAddProperties.remove(i);
            } 
        }
        
        PageDto<ProductAddProperties> page = new PageDto<ProductAddProperties>();
        
        page.setRecords(productAddProperties);

        return printJson(page, map);
    }
   
   //修改用户新增的属性
   @RequestMapping
    public ModelAndView updatePropIsDel(Integer id,
            HttpServletRequest request, Map<String, Object> out
            ) throws IOException, ParseException {
        String isDel= "1";
        Integer i = productAddPropertiesService.updateIsDelById(isDel, id);
        ExtResult result = new ExtResult();
        if (i != null && i > 0) {
            // // 更新客户标签
            // addTags("10351001", productsDO.getTags(), productsDO, request);
            // // 更新管理员标签
            // addTags("10351004", productsDO.getTagsAdmin(), productsDO,
            // request);
            result.setSuccess(true);
        }
        return printJson(result, out);
        
    }
   @RequestMapping
   public ModelAndView resetPropIsDel(Integer id,
           HttpServletRequest request, Map<String, Object> out
           ) throws IOException, ParseException {
       String isDel= "0";
       Integer i = productAddPropertiesService.updateIsDelById(isDel, id);
       ExtResult result = new ExtResult();
       if (i != null && i > 0) {
           // // 更新客户标签
           // addTags("10351001", productsDO.getTags(), productsDO, request);
           // // 更新管理员标签
           // addTags("10351004", productsDO.getTagsAdmin(), productsDO,
           // request);
           result.setSuccess(true);
       }
       return printJson(result, out);
       
   }
   @RequestMapping
    public ModelAndView updateIsDel(Integer id, Map<String, Object> map)
            throws IOException {
        
        ProductsRub productsRub = productsRubService.queryRubByProductId(id); 
        ProductsDto productsDTO = productsRubService.rubToProductsDto(productsRub);

        productsDTO.setCompany(companyService.queryCompanyById(productsDTO.getProducts().getCompanyId()));
//	      Boolean boo=crmCompanySvrService.validatePeriod(productsDTO.getProducts().getCompanyId(), "10001002");
//	      if(boo==true){
//	          productsDTO.getProducts().setAccount(productsDTO.getProducts().getAccount()+"(百度优化)");
//	      }
        PageDto<ProductsDto> page = new PageDto<ProductsDto>();
        List<ProductsDto> list = new ArrayList<ProductsDto>();
        list.add(productsDTO);
        page.setRecords(list);
        return printJson(page, map);
    }
   
	@RequestMapping
	public ModelAndView initForRubEdit(Integer id, Map<String, Object> map)
			throws IOException {
		
		ProductsRub productsRub = productsRubService.queryRubByProductId(id); 
		ProductsDto productsDTO = productsRubService.rubToProductsDto(productsRub);

		productsDTO.setCompany(companyService.queryCompanyById(productsDTO.getProducts().getCompanyId()));
//		Boolean boo=crmCompanySvrService.validatePeriod(productsDTO.getProducts().getCompanyId(), "10001002");
//		if(boo==true){
//			productsDTO.getProducts().setAccount(productsDTO.getProducts().getAccount()+"(百度优化)");
//		}
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		list.add(productsDTO);
		page.setRecords(list);
		return printJson(page, map);
	}
	
	@RequestMapping
	public ModelAndView updateGaoCheckStatus(Map<String, Object> out,
			String checkStatus, Integer productId, String unpassReason,HttpServletRequest request)
			throws IOException, ParseException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		Integer i=productsService.updateGaoCheckStatusByAdmin(checkStatus, sessionUser.getAccount(), productId,unpassReason);
		if(i>0){
			result.setSuccess(true);
		}
		// 日志记录 审核成功 \ 失败
		String code = productsService.queryProductsById(productId).getCategoryProductsMainCode().substring(0, 4);
		if ("1".equals(checkStatus)) {
			LogUtil.getInstance().mongo(
					sessionUser.getAccount(),
					CHECK_SUCCESS_OPERTION,null,
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(sessionUser.getAccount(), CHECK_SUCCESS_OPERTION,null, 
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
		} else if ("2".equals(checkStatus)) {
			LogUtil.getInstance().mongo(sessionUser.getAccount(),
					CHECK_FAILURE_OPERTION, null,
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(sessionUser.getAccount(), CHECK_FAILURE_OPERTION,null, 
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateIsDel(HttpServletRequest request,Map<String,Object>out,String status,Integer productId) throws IOException{
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		Integer i = productsService.updateProductsIsDelByAdmin(productId, status);
		if(i>0){
			result.setSuccess(true);
		}
		// 日志统计 恢复或者删除供求的情况
		if("1".equals(status)){
			LogUtil.getInstance().mongo(sessionUser.getAccount(),
					DEL_PRODUCT_OPERTION, null,
					"{'productId':'" + productId + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(
					sessionUser.getAccount(), DEL_PRODUCT_OPERTION,null,
					"{'productId':'" + productId + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
		}
		if("0".equals(status)){
			LogUtil.getInstance().mongo(sessionUser.getAccount(),
					UNDEL_PRODUCT_OPERTION, null,
					"{'productId':'" + productId + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(
					sessionUser.getAccount(), UNDEL_PRODUCT_OPERTION,null,
					"{'productId':'" + productId + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateCheckStatus(Map<String, Object> out,
			String checkStatus, Integer productId, String unpassReason,
			String membershipCode, Boolean isDel,Integer companyId, HttpServletRequest request)
			throws IOException, ParseException {
		if (StringUtils.isEmpty(checkStatus)) {
			checkStatus = "0"; // 默认的审核状态
		}

		ExtResult result = new ExtResult();
		
		// 验证该人员是否有审核权限
		if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
			result.setData("没有权限");
			result.setSuccess(false);
			return printJson(result, out);
		}
		
		SessionUser sessionUser = getCachedUser(request);
		// 供求审核
		productsService.updateProductsCheckStatusByAdmin(checkStatus,unpassReason, sessionUser.getAccount(), productId);
		// 高会审核高会供求
		if(StringUtils.isNotEmpty(membershipCode)&&!NO_VIP.equals(membershipCode)){
			productsService.updateGaoCheckStatusByAdmin(checkStatus, sessionUser.getAccount(), productId, unpassReason);
		}
		
		// 给用户增加积分
		scoreChangeDetailsService
				.saveChangeDetails(new ScoreChangeDetailsDo(companyId,
						null, "get_post_product", null, productId, null));
		List<ProductsPicDO> picList = productsPicService
				.queryProductPicInfoByProductsId(productId);
		for (ProductsPicDO pic : picList) {
			scoreChangeDetailsService
					.saveChangeDetails(new ScoreChangeDetailsDo(companyId,
							null, "get_post_product_pic", null,
							pic.getId(), null));
		}

		// 日志系统记录审核情况
		String code = "";
		String productCode = productsService.queryProductsById(productId).getCategoryProductsMainCode();
		if(productCode.length()>=4){
			code = productsService.queryProductsById(productId).getCategoryProductsMainCode().substring(0, 4);
		}
		//审核成功 \ 失败
		if ("1".equals(checkStatus)) {
			LogUtil.getInstance().mongo(
					sessionUser.getAccount(),
					CHECK_SUCCESS_OPERTION,null,
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(sessionUser.getAccount(), CHECK_SUCCESS_OPERTION, null,
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
		} else if ("2".equals(checkStatus)) {
			LogUtil.getInstance().mongo(sessionUser.getAccount(),
					CHECK_FAILURE_OPERTION, null,
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(sessionUser.getAccount(), CHECK_FAILURE_OPERTION, null, 
					"{'productId':'" + productId + "','productTypecode':'" + code + "','date':'" + DateUtil.toString(new Date(),DATE_FORMAT)+"'}");
		}
		
		// 审核过后清空 过期高会products_zst_expired 数据表中的数据
		productsZstExpiredService.deleteByProductId(productId);
		
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 推荐为现货供求
	 * @param request
	 * @param out
	 * @param productId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateToSpot(HttpServletRequest request,Map<String,Object>out,Integer productId) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = productsSpotService.addOneSpot(productId);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	/**
	 * 取消现货供求
	 * @param request
	 * @param out
	 * @param productId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateOutSpot(HttpServletRequest request,Map<String,Object>out,Integer productId) throws IOException{
		ExtResult result = new ExtResult();
		ProductsSpot productsSpot = productsSpotService.queryByProductId(productId);
		if(productsSpot==null){
			return printJson(result, out);
		}
		Integer i = productsSpotService.removeOneSpot(productsSpot.getId());
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	

	/**
	 * 添加或修改供求页
	 * 
	 * @param productId
	 * <br>
	 *            1.添加时,为null <br>
	 *            2.修改时,为对应的id
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addOrEdit(Integer productId, Map<String, Object> out)
			throws IOException {
		ProductsDO productsDO = productsService.queryProductsById(productId);
		return printJson(productsDO, out);
	}

	/**
	 * 搜索供求信息,查询条件分4部分,分别包含在productsDTO,productsDO,companyContactsDO和companyDO里<br>
	 * 查询的结果以JSON的格式打印出来.
	 * 
	 * @param productsDTO
	 *            搜索条件:<br>
	 *            <ol>
	 *            <li>供求类别(树结构):categoryProductsCode</li>
	 *            <li>供求附加类别(树结构):categoryProductsAppendId</li>
	 *            <li>是否为稀缺信息:isRare</li>
	 *            <li>搜索时间字段名:如发布时间或刷新时间(下拉列表):searchTimeField</li>
	 *            <li>搜索时间范围-开始时间:startTime</li>
	 *            <li>搜索时间范围-结束时间:endTime</li>
	 *            <li>排序字段名:sortField</li>
	 *            </ol>
	 * @param pageDTO
	 *            分页参数
	 * @param productsDO
	 *            搜索条件:<br>
	 *            <ol>
	 *            <li>供求标题:title</li>
	 *            <li>详细说明:details</li>
	 *            <li>信息来源(下拉列表):sourceTypeCode</li>
	 *            <li>供求类型(下拉列表):productsTypeCode</li>
	 *            <li>是否为查看受限时才发布的供求:isPostWhenViewLimit</li>
	 *            <li>是否为询盘导出的供求:isPostFromInquiry</li>
	 *            </ol>
	 * @param companyDO
	 *            搜索条件:<br>
	 *            <ol>
	 *            <li>公司所处地区(树结构):areaCode</li>
	 *            <li>发布供求的公司名:name</li>
	 *            </ol>
	 * @param companyContactsDO
	 *            搜索条件:<br>
	 *            <ol>
	 *            <li>发布公司的email:email</li>
	 *            <li>发布公司的地址:address</li>
	 *            </ol>
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView listProducts(ProductsDO products, Company company,
			PageDto<ProductsDto> pageDTO, String statusArray,
			Map<String, Object> out, String selectTime, String from, String to)
			throws IOException, ParseException {

		if (pageDTO == null) {
			pageDTO = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}
		// do{
		// if(checkStatus==null){
		// break;
		// }
		// if(checkStatus==1){
		// pageDTO.setSort("check_time");
		// }
		// if(checkStatus==2){
		// pageDTO.setSort("real_time");
		// }
		// if(checkStatus==3){
		// pageDTO.setSort("refresh_time");
		// }
		// }while(false);
		if (pageDTO.getDir() == null) {
			pageDTO.setDir("asc");
		}

		if (pageDTO.getSort() == null) {
			pageDTO.setSort("id");
		}

		if (company.getMembershipCode() == null) {
			company.setMembershipCode("10051000");
		}
		
		pageDTO = productsService.pageProductsByAdmin(company, products,
				statusArray, pageDTO, from, to, selectTime);
		return printJson(pageDTO, out);
	}

	/**
	 * 过期高会 供求搜索
	 */
	@RequestMapping
	public ModelAndView listProductsZstExpried(PageDto<ProductsDto> pageDTO,
			Integer isRecheck, ProductsDO products,Map<String, Object> out) throws IOException,
			ParseException {

		if (pageDTO == null) {
			pageDTO = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}

		if (pageDTO.getDir() == null) {
			pageDTO.setDir("asc");
		}

		if (pageDTO.getSort() == null) {
			pageDTO.setSort("id");
		}

		pageDTO = productsService.pageProductsByAdminZstExpried(isRecheck,
				pageDTO,products);

		return printJson(pageDTO, out);
	}
	
	/**
	 * 现货供求搜索
	 */
	@RequestMapping
	public ModelAndView listProductsSpot(PageDto<ProductsDto> page,
			Company company,ProductsDO products,Integer min,Integer max,String isStatus,Map<String, Object> out) throws IOException,
			ParseException {
		if (page == null) {
			page = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}

		if (page.getDir() == null) {
			page.setDir("desc");
		}

		if (page.getSort() == null) {
			page.setSort("id");
		}
		
		if(StringUtils.isEmpty(company.getMembershipCode())){
			company.setMembershipCode("10051000");
		}
		page = productsService.pageProductsForSpotByAdmin(company,products,page,min,max,isStatus);

		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView listProductsRub(PageDto<ProductsDto> page,Map<String, Object>out,ProductsRub productsRub) throws IOException{

		if (page == null) {
			page = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}

		if (page.getDir() == null) {
			page.setDir("desc");
		}

		if (page.getSort() == null) {
			page.setSort("id");
		}

		page = productsRubService.pageRubByAdmin(productsRub, page);
//		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
//		page.setTotalRecords(pPage.getTotalRecords());
//		page.setRecords(list);
		return printJson(page, out);
	}

	/**
	 * 伪删除供求
	 * 
	 * @param ids
	 *            待删除的id串,以逗号隔开
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 */
	// @RequestMapping
	// public ModelAndView fakeDeleteProducts(String ids, Map<String, Object>
	// out)
	// throws IOException {
	// ExtResult result = new ExtResult();
	// String[] idArray = ids.split(",");
	// Integer[] newIds = ConvertUtils.stringArrayToIntegerArray(idArray);
	// Integer i = productsService.batchFakeDeleteProductsByIds(newIds);
	// if (i != null && i.intValue() > 0) {
	// result.setSuccess(true);
	// }
	// return printJson(result, out);
	// }

	/**
	 * 真实删除供求 后台不使用删除
	 * 
	 * @param ids
	 *            待删除的id串,以逗号隔开
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView realDeleteProducts(HttpServletRequest request,String ids, Map<String, Object> out)
			throws IOException {
//		SessionUser sessionUser = getCachedUser(request);
		ExtResult result = new ExtResult();
//		String[] idArray = ids.split(",");
//		Integer[] newIds = ConvertUtils.stringArrayToIntegerArray(idArray);
//		Integer i = productsService.batchDeleteProductsByIds(newIds,0);
//		if (i != null && i.intValue() > 0) {
//			result.setSuccess(true);
//		}
		return printJson(result, out);
	}

	/**
	 * 添加或修改供求信息 <br />
	 * 如果productsDO的ID不为null,则为修改,否则为添加.
	 * 
	 * @param productsDO
	 *            如果productsDO的ID不为null,则为修改,否则为添加.
	 * @param productsCategoryDO
	 *            供求类别
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView updateProduct(ProductsDO productsDO,SpotInfo spotInfo,String grade,Integer postlimittime,
			HttpServletRequest request, CategoryProductsDO categoryProductsDO, 
			String isRecommendToMagazine, Map<String, Object> out,
			String realTimeStr) throws IOException, ParseException {

		if (productsDO.getProvideStatus() == null) {
			productsDO.setProvideStatus("0");
		}
		if (productsDO.getShipDay() == null && productsDO.getStrShipDay() != "") {
		    productsDO.setShipDay(Integer.valueOf(productsDO.getStrShipDay()));
		}
		Integer i = productsService.updateProductByAdmin(productsDO);
		ProductsSpot productsSpot = productsSpotService.queryByProductId(productsDO.getId());
		
		if(productsSpot != null) {
		    spotInfo.setSpotId(productsSpot.getId());
		    spotInfoService.addOrEditOneSpotInfo(spotInfo);
		}
		
		
        if (productsDO.getCategoryProductsMainCode().substring(0, 4).equals("1000")) {
            ProductAddProperties pap = productAddPropertiesService.queryByPidAndProperty(productsDO.getId(), "品位");
            if (pap != null) {
                pap.setId(pap.getId());
                pap.setProperty("品位");
                pap.setContent(grade);
                productAddPropertiesService.updateProperties(pap);
            }
            
        }
            
		ExtResult result = new ExtResult();
		if (i != null && i > 0) {
			// // 更新客户标签
			// addTags("10351001", productsDO.getTags(), productsDO, request);
			// // 更新管理员标签
			// addTags("10351004", productsDO.getTagsAdmin(), productsDO,
			// request);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	//修改用户新增的属性
   @RequestMapping
    public ModelAndView updateOneOtherProp(ProductAddProperties productAddProperties,
            HttpServletRequest request, Map<String, Object> out
            ) throws IOException, ParseException {

        Integer i = productAddPropertiesService.updateProperties(productAddProperties);
        ExtResult result = new ExtResult();
        if (i != null && i > 0) {
            // // 更新客户标签
            // addTags("10351001", productsDO.getTags(), productsDO, request);
            // // 更新管理员标签
            // addTags("10351004", productsDO.getTagsAdmin(), productsDO,
            // request);
            result.setSuccess(true);
        }
        return printJson(result, out);
        
    }
	@RequestMapping
	public ModelAndView updateProductRub(ProductsDO productsDO,
			HttpServletRequest request, CategoryProductsDO categoryProductsDO,
			String isRecommendToMagazine, Map<String, Object> out,
			String realTimeStr) throws IOException, ParseException {
		ProductsRub productsRub = productsRubService.productsToRub(productsDO);
		Integer i = productsRubService.editProductsRub(productsRub);
		ExtResult result = new ExtResult();
		if (i != null && i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	// private void addTags(String code, String tagsName, ProductsDO productsDO,
	// HttpServletRequest request) {
	// int pdtid = productsDO.getId();
	//
	// // 获取标签列表
	// String[] tagNames = StringUtils
	// .distinctStringArray(tagsName.split(","));
	// tagsArticleService.deleteTagsArticleRelationByArticleId(code, pdtid);
	//
	// // SessionUser sessionUser = getCachedUser(request);
	// // 设置,添加标签关联信息
	// for (String tagName : tagNames) {
	// TagsArticleRelation relation = new TagsArticleRelation();
	// relation.setArticleModuleCode(code);// 10351001 ,供求信息
	// relation.setArticleCategoryCode(productsDO
	// .getCategoryProductsMainCode());
	// relation.setArticleId(pdtid);
	// relation.setArticleTitle(productsDO.getTitle());
	// relation.setTagName(tagName);
	// // relation.setCreator(authUser.getId());
	// try {
	// tagsArticleService.insertTagsArticleRelation(relation);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }

	final static String DEFAULT_COMPANY_PRICE_CATEGORY = "1003";

	@RequestMapping
	public ModelAndView saveCompanyPrice(Map<String, Object> out,
			CompanyPriceDO companyPrice,HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();
		ProductsDO products = productsService.queryProductsById(companyPrice.getProductId());
		do {
			if (products == null) {
				break;
			}
			
			// 企业报价推荐 一次即可 不可重复
			CompanyPriceDO pdo = companyPriceService.queryCompanyPriceByProductId(products.getId());
			if(pdo!=null){
				break;
			}
			
			//获取对应企业报价的主要类别code
			String categoryLable = "";
			// 获取供求类别对应的企业报价类别
			categoryLable = getCategoryCode(products.getCategoryProductsMainCode());
			companyPrice.setCategoryCompanyPriceCode(categoryLable);
			Company company = companyService.querySimpleCompanyById(products.getCompanyId());
			if (company == null) {
				break;
			}
			if (StringUtils.isEmpty(company.getAreaCode())){
				break;
			}
			// 地区
			companyPrice.setAreaCode(company.getAreaCode());
			// 公司account
			companyPrice.setAccount(products.getAccount());
			// 公司Id
			companyPrice.setCompanyId(products.getCompanyId());
			// 审核状态
			companyPrice.setIsChecked("1");
			//标题
			companyPrice.setTitle(products.getTitle());
			// 价格单位
			String priceUnit = StringUtils.isNotEmpty(products.getPriceUnit())?products.getPriceUnit():"元";
			String quanlityUnit = StringUtils.isNotEmpty(products.getQuantityUnit())?products.getQuantityUnit():"吨";
			companyPrice.setPriceUnit(priceUnit+"/"+quanlityUnit);
			// 供求详细
			companyPrice.setDetails(products.getDetails());
			// 最小价格
			if (products.getMinPrice() !=null&&products.getMinPrice()>0) {
				companyPrice.setMinPrice(products.getMinPrice()+"");
			}
			// 最大价格
			if (products.getMaxPrice() != null&&products.getMaxPrice()>0) {
				companyPrice.setMaxPrice(products.getMaxPrice()+"");
			}
			// insert 入 企业报价 company_price表
			Integer i = companyPriceService.insertCompanyPriceByAdmin(companyPrice);
			// 记录日志 推荐供求为企业报价 企业报价通过数 +1
			if(i>0){
				SessionUser sessionUser = getCachedUser(request);
				LogUtil.getInstance().mongo(sessionUser.getAccount(), COMP_PRICE_CHECK_SUCCESS_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT)+",countryCode:"+company.getAreaCode());
				LogUtil.getInstance().log(sessionUser.getAccount(), COMP_PRICE_CHECK_SUCCESS_OPERTION,
						null,"id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT)+",countryCode:"+company.getAreaCode());
			}
			// 推荐为报价的字段置 1
			productsService.updateProductsIsShowInPrice(products.getId(), "1");
			result.setSuccess(true);
		} while (false);

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView deleteProductImage(Map<String, Object> out,
			String imageArrays) throws IOException {
		ExtResult result = new ExtResult();
		// TODO 删除供求信息对应的图片
		Integer i = productsPicService.batchDeleteProductPicbyId(StringUtils
				.StringToIntegerArray(imageArrays));
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 选择对应供求下的所有图片
	 * 
	 * @param productId
	 *            供求ID
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listProductsPic(Integer productId,
			Map<String, Object> out) throws IOException {
		// 除了返回所有图片外,还要返回它对应的相册信息
		PageDto<ProductsPicDO> page = new PageDto<ProductsPicDO>();
		List<ProductsPicDO> list = productsPicService.queryProductPicInfoByProductsId(productId);
		page.setRecords(list);
		return printJson(page, out);
	}

	/**
	 * 添加或删除供求图片<br />
	 * 如果productsPicDO的ID不为null,则为修改,否则为添加.
	 * 
	 * @param productsPicDO
	 *            添加或修改后的图片模型,如果productsPicDO的ID不为null,则为修改,否则为添加.
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView createOrEditProductsPic(ProductsPicDO productsPicDO,
			Map<String, Object> out) throws IOException {
		// 图片修改时,要把旧的图片物理删除掉
		return printJson(null, out);
	}

	/**
	 * 供求转化为询盘
	 * 
	 * @param inquiryDO
	 *            询盘内容
	 * @param toProductsId
	 *            询盘所发送的目标供求id
	 * @param out
	 *            用来保存输出JSON的输出对象
	 * @return 用来显示JSON的视图模型
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView createInquiryFromProducts(Inquiry inquiryDO,
			String toProductsId, Map<String, Object> out) throws IOException {
		return printJson(null, out);
	}

	@RequestMapping
	public ModelAndView queryById(Integer productId, Map<String, Object> out)
			throws IOException {
		ProductsPicDTO productsPicDTO = new ProductsPicDTO();
		if (productId != null) {
			productsPicDTO = productsPicService
					.queryProductsPicDetails(productId);
		}
		// List<ProductsPicDO> list =
		// productsPicService.queryProductPicByproductId(productsId);
		return printJson(productsPicDTO, out);
	}

	@RequestMapping
	public ModelAndView listOfCompany(HttpServletRequest request,
			Map<String, Object> out, Integer companyId, Integer readOnly) {
		out.put("companyId", companyId);
		out.put("readOnly", readOnly);
		return null;
	}
	
	@RequestMapping
	public ModelAndView updateTe(HttpServletRequest request,Map<String, Object> out,Integer productId,String isTe) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=productsSpotService.updateIsTeByProductsId(isTe, productId);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateYou(HttpServletRequest request,Map<String, Object> out,Integer productId,String isYou) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=productsSpotService.updateIsYouByProductsId(isYou, productId);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateRe(HttpServletRequest request,Map<String, Object> out,Integer productId,String isHot) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=productsSpotService.updateIsHotByProductsId(isHot, productId);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateBail(HttpServletRequest request,Map<String, Object> out,Integer productId,String isBail) throws IOException{
		ExtResult result=new ExtResult();
		Integer i=productsSpotService.updateIsBailByProductsId(isBail, productId);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/****
	 * 根据传递过来的Code ，获取到对应的企业报价的Code，并返回
	 * 	1. 传string，mianCode，
	 *  2. 死循环
	 *  3. 判断循环条件
	 *  4. break 条件(1)找到类别；(2)长度为0
	 * */
	private String getCategoryCode(String mainCode){
		
		String categoryLable = "";
		int strLen = mainCode.length();
		//是否设置为默认值，
		boolean isDefault = false;
		if(strLen == 4){
			categoryLable=categoryCode.get(mainCode);
			if(StringUtils.isEmpty(categoryLable)){
				isDefault = true;
			}
		}
		else if(strLen > 4){
			categoryLable=categoryCode.get(mainCode);
			if(StringUtils.isEmpty(categoryLable)){
				String str = mainCode.substring(0, strLen-4);
				do{
					categoryLable=categoryCode.get(str);
					str = str.substring(0, str.length()-4);
					if(StringUtils.isNotEmpty(categoryLable)||str.length() == 0){
						break;
					}
				}while(true);
			}
		}
		if(isDefault||StringUtils.isEmpty(categoryLable)) {
			categoryLable=DEFAULT_COMPANY_PRICE_CATEGORY;
		}
		
		return categoryLable;
	}
	
	//trade ,companyPrice 的Code对应情况map集合
	final static Map<String, String> categoryCode = new HashMap<String, String>();
	static {
		categoryCode.put("1001", "1000");
		categoryCode.put("1000", "1001");
		categoryCode.put("1004", "1002");
		categoryCode.put( "100110001004" , "100010001000");//ABS
		categoryCode.put( "100110001005" , "100010001001");//EVA
		categoryCode.put( "100110011000" , "100010001002");//PA
		categoryCode.put( "100110011004" , "100010001003");//PC
		categoryCode.put( "100110001001" , "100010001004");//PE
		categoryCode.put( "100110011001" , "100010001005");//PET
		categoryCode.put( "100110001000" , "100010001006");//PP
		categoryCode.put( "100110001003" , "100010001007");//PS
		categoryCode.put( "100110001002" , "100010001008");//PVC
		categoryCode.put( "10011005" , "100010001009");//塑料助剂

		categoryCode.put( "100110041003" , "100010011000");//ABS再生颗粒
		categoryCode.put( "100110041005" , "100010011001");//EVA颗粒
		categoryCode.put( "100110041006" , "100010011002");//PA颗粒
		categoryCode.put( "100110041009" , "100010011003");//PC颗粒
		categoryCode.put( "100110041001" , "100010011004");//PE颗粒
		categoryCode.put( "100110041007" , "100010011005");//PET颗粒
		categoryCode.put( "100110041000" , "100010011006");//PP颗粒
		categoryCode.put( "100110041004" , "100010011007");//PS颗粒
		categoryCode.put( "100110041002" , "100010011008");//PVC颗粒
		categoryCode.put( "100110041010" , "100010011009");//其它颗粒
		
		categoryCode.put( "100010001000" , "10011001");//铁
		categoryCode.put( "100010011000" , "10011002");//铜
		categoryCode.put( "100010011001" , "10011003");//铝
		categoryCode.put( "100010011005" , "10011004");//铅
		categoryCode.put( "100010011008" , "10011005");//锌
		categoryCode.put( "100010011002" , "10011006");//锡
		categoryCode.put( "100010011007" , "10011007");//镍
		
		categoryCode.put( "100410041002" , "10021000");//牛皮纸
		categoryCode.put( "100410041003" , "10021001");//瓦楞纸
		categoryCode.put( "100410041005" , "10021002");//淋膜纸
		categoryCode.put( "100410041006" , "10021003");//商标纸
		categoryCode.put( "100410041008" , "10021004");//利乐包
		categoryCode.put( "100410051001" , "10021005");//书刊
		categoryCode.put( "100410051003" , "10021006");//新闻纸
		categoryCode.put( "100410051000" , "10021007");//废报纸
		categoryCode.put( "10041003" , "10021008");//其它
		categoryCode.put( "100210031002" , "10031000");//橡胶颗粒
		categoryCode.put( "1011" , "10031001");//轮胎
		categoryCode.put( "100310021005" , "10031002");//旧衣服
		categoryCode.put( "10031007" , "10031003");//牛仔裤
		categoryCode.put( "10061001" , "10031004");//破碎玻
		categoryCode.put( "10061007" , "10031005");//玻璃瓶
		categoryCode.put( "100510001008" , "10031006");//电池
		categoryCode.put( "100510001004" , "10031007");//线路板
		
	}
	
	@RequestMapping
	public ModelAndView addProductToRub(HttpServletRequest request,Map<String, Object>out,Integer productId) throws IOException, ParseException{
		ExtResult result =  new ExtResult();
		do {
			ProductsDO productsDO = productsService.queryProductsById(productId);
			ProductsRub productsRub =  productsRubService.productsToRub(productsDO);
			Integer i = productsRubService.addProductsRub(productsRub);
			if(i!=null&&i>0){
				result.setSuccess(true);
				result.setData("信息已移至垃圾(Rub)页面");
			}else{
				// 更新已存在的违规垃圾信息
				productsRubService.editProductsRub(productsRub);
				result.setData("该信息已经在rub页面中");
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView removeProductToRub(HttpServletRequest request,Map<String, Object> out, Integer productId) throws IOException {
		ExtResult result = new ExtResult();
		do {
			ProductsRub productsRub = productsRubService.queryRubByProductId(productId);
			if (productsRub == null) {
				break;
			}
			Integer i = productsRubService.deleteProductsRubByProductId(productId);
			if (i > 0) {
				result.setSuccess(true);
				result.setData("信息已从垃圾(Rub)页面移出");
			}
		} while (false);
		return printJson(result, out);
	}
	
	public static void main(String[] args) {
		String a = "100110001002";
		ProductsController ab = new ProductsController();
		System.out.print(ab.getCategoryCode(a));
	}
	
	@RequestMapping
	public ModelAndView exportData(Map<String, Object> out, HttpServletRequest request,HttpServletResponse response,
			CompanyAccount companyAccount,Company company,ProductsDO products,ProductsSpot productsSpot,
			Integer min,Integer max,String isStatus,PageDto<ProductsDto> page) throws Exception{
		if (company.getMembershipCode() == null) {
			company.setMembershipCode("10051000");
		}
		if("undefined".equals(products.getCategoryProductsMainCode())){
			products.setCategoryProductsMainCode("");
		}
		page=productsService.queryProductForexportByAdmin(company, products, page,min,max,isStatus);
		//生成Excel
		//提供下载
		response.setContentType("application/msexcel");
		
		OutputStream os = response.getOutputStream();
		
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
		ws.addCell(new Label(0,0,"供/求"));
		ws.addCell(new Label(1,0,"产品类目"));
		ws.addCell(new Label(2,0,"供求标题"));
		ws.addCell(new Label(3,0,"最小价格（元/吨）"));
		ws.addCell(new Label(4,0,"最大价格（元/吨）"));
		ws.addCell(new Label(5,0,"数量（吨）"));
		ws.addCell(new Label(6,0,"地区"));
		ws.addCell(new Label(7,0,"公司"));
		ws.addCell(new Label(8,0,"联系人"));
		ws.addCell(new Label(9,0,"电话"));
		ws.addCell(new Label(10,0,"刷新时间"));
		ws.addCell(new Label(11,0,"特"));
		ws.addCell(new Label(12,0,"优"));
		ws.addCell(new Label(13,0,"热"));
		ws.addCell(new Label(14,0,"保"));
		int i=1;
		for(ProductsDto comp:page.getRecords()){
			ws.addCell(new Label(0,i,comp.getProductsTypeLabel()));
			ws.addCell(new Label(1,i,comp.getCategoryProductsMainLabel()));
			ws.addCell(new Label(2,i,comp.getProducts().getTitle()));
			ws.addCell(new Label(3,i,String.valueOf(comp.getProducts().getMinPrice())));
			ws.addCell(new Label(4,i,String.valueOf(comp.getProducts().getMaxPrice())));
			ws.addCell(new Label(5,i,comp.getProducts().getQuantity()));
			ws.addCell(new Label(6,i,comp.getProducts().getLocation()));
			ws.addCell(new Label(7,i,comp.getCompany().getName()));
			ws.addCell(new Label(8,i,comp.getCompanyContacts().getContact()));
			ws.addCell(new Label(9,i,comp.getCompanyContacts().getMobile()));
			ws.addCell(new Label(10,i,DateUtil.toString(comp.getProducts().getRefreshTime(), "yyyy-MM-dd HH:mm:ss")));
			ws.addCell(new Label(11,i,comp.getProductsSpot().getIsTe()));
			ws.addCell(new Label(12,i,comp.getProductsSpot().getIsYou()));
			ws.addCell(new Label(13,i,comp.getProductsSpot().getIsHot()));
			ws.addCell(new Label(14,i,comp.getProductsSpot().getIsBail()));
			i++;
		}
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置和内容   
//		ws.addCell(labelCF);//将Label写入sheet中   
		//Label的构造函数Label(int x, int y,String aString)  xy意同读的时候的xy,aString是写入的内容.   
//		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);//设置写入字体   
//		WritableCellFormat wcfF = new WritableCellFormat(wf);//设置CellFormat   
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置,内容和格式   
		//Label的另一构造函数Label(int c, int r, String cont, CellFormat st)可以对写入内容进行格式化,设置字体及其它的属性.   
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}
	
	/**
	 * 图片审核通过
	 * @param out
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView passPic(Map<String, Object>out,String idArrayStr) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			Integer i = productsPicService.batchUpdatePicStatus(idArrayStr, "", ProductsPicService.CHECK_STATUS_PASS);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView defaultPic(Map<String, Object>out,String idArrayStr,ProductsPicDO productsPicDO) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer id = Integer.valueOf(idArrayStr);
			if(id==null){
				break;
			}
			productsPicDO.setIsDefault("1");
			productsPicDO.setId(id);
			Integer i = productsPicService.updateProductsPicIsDefault(productsPicDO);
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refusePic(Map<String, Object>out,String idArrayStr,String unpassReason) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			Integer i = productsPicService.batchUpdatePicStatus(idArrayStr, unpassReason, ProductsPicService.CHECK_STATUS_NO_PASS);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refuseAllPic(Map<String, Object>out,Integer productId,String unpassReason) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(productId==null){
				break;
			}
			Integer i = productsPicService.batchUpdatePicStatusByProductId(productId, unpassReason, ProductsPicService.CHECK_STATUS_NO_PASS);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView exportPassData(HttpServletRequest request,HttpServletResponse response,String date) throws IOException, RowsExceededException, WriteException{
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
		// 检索所有list
		List<ProductsDO> list =productsService.queryPassProductsByDate(date);
		int i=0;
		for(ProductsDO obj:list){
			ws.addCell(new Label(0,i,URL_FOAMAT+obj.getId()+URL_FIX));
			i++;
		}
		wwb.write();
		//写完后关闭
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}

}