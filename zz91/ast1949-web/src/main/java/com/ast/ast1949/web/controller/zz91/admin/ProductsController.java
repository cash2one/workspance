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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsExportInquiry;
import com.ast.ast1949.domain.products.ProductsHide;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.products.ProductsStar;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.market.MarketPicService;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.market.ProductMarketService;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsExportInquiryService;
import com.ast.ast1949.service.products.ProductsHideService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsRubService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.products.ProductsSpotService;
import com.ast.ast1949.service.products.ProductsStarService;
import com.ast.ast1949.service.products.ProductsZstExpiredService;
import com.ast.ast1949.service.sample.SampleService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.site.CategoryService;
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

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sourceforge.pinyin4j.PinyinHelper;

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
	@Resource
	private CompanyAccountService companyAccountService;
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
	@Resource
	private ProductsExportInquiryService productsExportInquiryService;
	@Resource
	private SampleService sampleService;
	@Resource
	private ProductsHideService productsHideService;
	@Resource
	private MarketService marketService;
	@Resource
	private MarketPicService marketPicService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private ProductMarketService productMarketService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProductsStarService productsStarService;

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
	public void list(Map<String, Object> model,String account,String from,String to,String status,HttpServletRequest request) {
		model.put("model", UPLOAD_MODEL);
		// model.put("imageServer",
		// MemcachedUtils.getInstance().getClient().get(
		// "baseConfig.resource_url"));
		model.put("imageServer", AddressTool.getAddress("resources"));
		model.put("account", account);
		model.put("status", status);
		model.put("from", from);
		model.put("to", to);
		if(AuthUtils.getInstance().authorizeRight("check_products", request, null)){
			model.put("haveRight", 1);
		}
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
	
	/**
	 * 导出供求列表
	 */
	@RequestMapping
	public ModelAndView listExportInquiry(Map<String, Object> model){
		model.put("model", UPLOAD_MODEL);
		model.put("imageServer", AddressTool.getAddress("resources"));
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView edit(String productid, String companyid, String account,
			Map<String, Object> model,HttpServletRequest request) {
		model.put("productId", productid);
		model.put("companyId", companyid);
		model.put("account", account);
		model.put("model", UPLOAD_MODEL);
		model.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		// 验证是否为样品信息
		if (StringUtils.isNotEmpty(productid)&&StringUtils.isNumber(productid)) {
			Sample sample = sampleService.queryByIdOrProductId(null, Integer.valueOf(productid));
			if (sample!=null&&0==sample.getIsDel()&&StringUtils.isEmpty(sample.getUnpassReason())) {
				model.put("sample", sample);
			}
		}
		// 审核权限
		if(AuthUtils.getInstance().authorizeRight("check_products", request, null)){
			model.put("haveRight", 1);
		}
		return new ModelAndView();
	}
	
	@RequestMapping
	public void editRub(String productid, String companyid, String account,
			Map<String, Object> model) {
		model.put("productId", productid);
		model.put("companyId", companyid);
		model.put("account", account);
		model.put("model", UPLOAD_MODEL);
		model.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
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
		
		ProductsDto productsDTO = productsService.queryProductsDetailsById(id);
		String categoryLable = companyPriceService.getCategoryCode(productsDTO.getProducts().getCategoryProductsMainCode());
		
		// 判断供求是否已经存在与 companyprice 表中
		CompanyPriceDO cDo = companyPriceService.queryCompanyPriceByProductId(id,categoryLable);
		if(cDo!=null){
			productsService.updateProductsIsShowInPrice(id, "1");
		}else{
			productsService.updateProductsIsShowInPrice(id, "0");
		}

		Company c = companyService.queryCompanyById(productsDTO.getProducts().getCompanyId());
		c.setIntroduction("");
		productsDTO.setCompany(c);
		
		// 样品信息
		Sample sample = sampleService.queryByIdOrProductId(null, productsDTO.getProducts().getId());
		if (sample!=null) {
			if (StringUtils.isNotEmpty(sample.getAreaCode())) {
				String areaCode = sample.getAreaCode();
				String areaLabel = "";
				if (areaCode.length() >= 12) {
					areaLabel += CategoryFacade.getInstance().getValue(areaCode.substring(0, 12));
				}
				if (areaCode.length() >= 16) {
					areaLabel += CategoryFacade.getInstance().getValue(areaCode.substring(0, 16));
				}
				productsDTO.setSampleAreaLabel(areaLabel);
			}
			productsDTO.setSample(sample);
		}else{
			productsDTO.setSample(new Sample());
		}
		
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
    public ModelAndView loadMoreProp(Integer id,PageDto<ProductAddProperties> page, Map<String, Object> map)
            throws IOException {
       
       List<ProductAddProperties> paps = productAddPropertiesService.queryByPid(id);
     /*  ProductsDO productsDO = productsService.queryProductsById(id);
       String code = productsDO.getCategoryProductsMainCode();
        if (code.length() >= 4 && !code.substring(0, 4).equals("1001")) {
            List<ProductAddProperties> productAddProperties = productAddPropertiesService.queryByPid(id);
            for (int i = 0 ; i < productAddProperties.size(); i++) {
                if (productAddProperties.get(i).getProperty().equals("交易说明")) {
                    continue;
                }
                if (code.substring(0, 4).equals("1000")) {
                    if (productAddProperties.get(i).getProperty().equals("形态")) {
                       continue;
                    }
                    if (i >= 0 && productAddProperties.get(i).getProperty().equals("品位")) {
                        continue;
                    }
                }
                
                paps.add(productAddProperties.get(i));
                
            }
        }*/
        
        page.setRecords(paps);

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
		do {
			SessionUser sessionUser = getCachedUser(request);
			if(!AuthUtils.getInstance().authorizeRight("check_products", request, null)){
				break;
			}
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
			} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateIsDel(HttpServletRequest request,Map<String,Object>out,String status,Integer productId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			SessionUser sessionUser = getCachedUser(request);
			if(!AuthUtils.getInstance().authorizeRight("check_products", request, null)){
				break;
			}
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
		} while (false);
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
		do {
			// 验证该人员是否有审核权限
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
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
				// 退回供求 ，同时退回样品
				sampleService.checkSample(productId, SampleService.IS_DEL, "");
			}
			
			// 审核过后清空 过期高会products_zst_expired 数据表中的数据
			productsZstExpiredService.deleteByProductId(productId);
			
			result.setSuccess(true);
		} while (false);
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
		do {
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
			}
			
			Integer i = productsSpotService.addOneSpot(productId);
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
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
		do {
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
			}
			ProductsSpot productsSpot = productsSpotService.queryByProductId(productId);
			if(productsSpot==null){
				return printJson(result, out);
			}
			Integer i = productsSpotService.removeOneSpot(productsSpot.getId());
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
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
			Map<String, Object> out, String selectTime, String from, String to,String exprieFlag)
			throws IOException, ParseException {

		if (pageDTO == null) {
			pageDTO = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}
		
		if (pageDTO.getDir() == null) {
			pageDTO.setDir("asc");
		}

		if (pageDTO.getSort() == null) {
			pageDTO.setSort("id");
		}

		if (company.getMembershipCode() == null) {
			company.setMembershipCode("10051000");
		}
		//exprieFlag 供求过期标志  1:已过期， 0：未过期
		if (products!=null&&"2".equals(products.getIsPause())) {
			products.setHide("1");
			products.setIsPause(null);
		}
		pageDTO = productsService.pageProductsByAdmin(company, products,statusArray, pageDTO, from, to, selectTime);
		return printJson(pageDTO, out);
	}
	
	@RequestMapping
	public ModelAndView listProductsExportInquiry(ProductsDO products, Company company,
			PageDto<ProductsDto> pageDTO, String statusArray,
			Map<String, Object> out, String selectTime, String from, String to)
			throws IOException, ParseException {

		if (pageDTO == null) {
			pageDTO = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}
		if (pageDTO.getDir() == null) {
			pageDTO.setDir("asc");
		}

		if (pageDTO.getSort() == null) {
			pageDTO.setSort("id");
		}

		if (company.getMembershipCode() == null) {
			company.setMembershipCode("10051000");
		}
		
		pageDTO = productsService.pageProductsByAdmin(company, products,"1", pageDTO, from, to, selectTime);
		for (ProductsDto dto:pageDTO.getRecords()) {
			dto.setCountInquiry(productsExportInquiryService.countByProductId(dto.getProducts().getId()));
			ProductsExportInquiry productsExportInquiry =  productsExportInquiryService.queryByProductId(dto.getProducts().getId());
			if(productsExportInquiry!=null){
				dto.setGmtInquiryStr(DateUtil.toString(productsExportInquiry.getGmtCreated(), "yyyy-MM-dd"));
			}
		}

		return printJson(pageDTO, out);
	}
	
	/**
	 * 批量到处供求为询盘
	 */
	@RequestMapping
	public ModelAndView batchExportInquiry(HttpServletRequest request,ProductsDO products, Company company,
			PageDto<ProductsDto> pageDTO, String statusArray,
			Map<String, Object> out, String selectTime, String from, String to,Integer productId)
			throws IOException, ParseException {
		
		SessionUser sessionUser = getCachedUser(request);
		
		ExtResult result = new ExtResult();
		
		if (pageDTO == null) {
			pageDTO = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}
		if (pageDTO.getDir() == null) {
			pageDTO.setDir("asc");
		}

		if (pageDTO.getSort() == null) {
			pageDTO.setSort("id");
		}

		if (company.getMembershipCode() == null) {
			company.setMembershipCode("10051000");
		}

		// 最大限制1000条 每一次群发 转1000条
		pageDTO.setPageSize(1000);

		products.setIsDel(false); // 未删除
		products.setIsPause("0"); // 已发布
		company.setIsBlock("0"); // 公司未禁用
		
		pageDTO = productsService.pageProductsByAdmin(company, products,statusArray, pageDTO, from, to, selectTime);
		String targetIdStr ="";
		for (ProductsDto dto:pageDTO.getRecords()) {
			targetIdStr = targetIdStr + dto.getProducts().getId()+",";
		}
		Integer [] targetIdArray = StringUtils.StringToIntegerArray(targetIdStr);
		Integer i = productsExportInquiryService.exportInquiry(productId, targetIdArray,sessionUser.getAccount());
		if(i>0){
			result.setSuccess(true);
			result.setData(i);
		}

		return printJson(result, out);
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
	public ModelAndView updateProduct(ProductsDO productsDO,SpotInfo spotInfo,String grade,
	        String addShape,String addTransaction, String addLevel, Integer postlimittime,
			HttpServletRequest request, CategoryProductsDO categoryProductsDO, 
			String isRecommendToMagazine, Map<String, Object> out,
			String realTimeStr,Integer sampleId,Sample sample) throws IOException, ParseException {
		
		ExtResult result = new ExtResult();
		do {
			// 检验是否有审核供求的权限
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
			}
			
			if (productsDO.getProvideStatus() == null) {
				productsDO.setProvideStatus("0");
			}
			if (productsDO.getShipDay() == null && productsDO.getStrShipDay() != "") {
			    productsDO.setShipDay(Integer.valueOf(productsDO.getStrShipDay()));
			}
			Integer i = productsService.updateProductByAdmin(productsDO);
			String mainCode = productsDO.getCategoryProductsMainCode();
			if(ProductsService.GOOD_TYPE_CODE_SPOT.equals(productsDO.getGoodsTypeCode())){
			    ProductsSpot productsSpot = productsSpotService.queryByProductId(productsDO.getId());
		        if(productsSpot != null) {
		            spotInfo.setSpotId(productsSpot.getId());
		            if (mainCode.length()>=4 && mainCode.substring(0, 4).equals("1000")) {
	                    spotInfo.setLevel(" ");
	                } else if (mainCode.length()>=4 && !mainCode.substring(0, 4).equals("1001")){
	                    spotInfo.setShape(" ");
	                    spotInfo.setLevel(" ");
	                }
		            spotInfoService.addOrEditOneSpotInfo(spotInfo);
		        }
			} else {
			    spotInfo.setShape(addShape);
			    spotInfo.setTransaction(addTransaction);
			    spotInfo.setLevel(addLevel);
	            productAddPropertiesService.updateOrAddProperty(productsDO, spotInfo);
	        }
	        if (mainCode.length()>=4 && mainCode.substring(0, 4).equals("1000")) {
	            ProductAddProperties pap = productAddPropertiesService.queryByPidAndProperty(productsDO.getId(), "品位");
	            if (pap != null) {
	                pap.setId(pap.getId());
	                pap.setProperty("品位");
	                pap.setContent(grade);
	                productAddPropertiesService.updateProperties(pap);
	                if (pap.getIsDel()) {
	                    productAddPropertiesService.updateIsDelById("0", pap.getId());
	                }
	            } else {
	                ProductAddProperties productAddProperties = new ProductAddProperties();
	                productAddProperties.setPid(productsDO.getId());
	                productAddProperties.setProperty("品位");
	                productAddProperties.setContent(grade);
	                productAddPropertiesService.addProperties(productAddProperties);
	            }
	        } else {
	            ProductAddProperties pap = productAddPropertiesService.queryByPidAndProperty(productsDO.getId(), "品位");
	            if (pap != null) {
	                productAddPropertiesService.updateIsDelById("1", pap.getId());
	            }
	        }
	        
	        // 样品更新
	        if (sampleId!=null&&sampleId>0) {
				sample.setId(sampleId);
	        	sampleService.editSample(sample);
			}
			if (i != null && i > 0) {
				// // 更新客户标签
				// addTags("10351001", productsDO.getTags(), productsDO, request);
				// // 更新管理员标签
				// addTags("10351004", productsDO.getTagsAdmin(), productsDO,
				// request);
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateMarket(Market market,Map<String, Object>out,HttpServletRequest request ,Integer isDel) throws IOException{
		
		ExtResult result = new ExtResult();
		do {
			// 检验是否有审核供求的权限
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
			}
			  if (market!=null&&market.getId()!=null&&market.getId().intValue()>0) {
				  market.setIsDel(isDel);
				 Integer i= marketService.updateMarket(market);
				 if (i!=null&&i.intValue()>0) {
					result.setSuccess(true);
				}
				
			}
		} while (false);
		return printJson(result, out);
		
	}
	@RequestMapping
	public ModelAndView insertMarket(Market market,Map<String, Object>out,HttpServletRequest request,String areaCode) throws IOException{
		ExtResult result = new ExtResult();
		do {
			// 检验是否有审核供求的权限
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
			}
			if (areaCode!=null) {
				String area=null;
				if (areaCode.length()>=12) {
					CategoryDO categoryDO=categoryService.queryCategoryByCode(areaCode.substring(0, 12));
					if (categoryDO!=null) {
						area=categoryDO.getLabel();
					}
				}
				if (areaCode.length()>=16) {
					CategoryDO category=categoryService.queryCategoryByCode(areaCode.substring(0, 16));
					if (category!=null) {
						area=area+" "+category.getLabel();
					}
				}
				market.setArea(area);
			}
			 //将市场名称转成拼音首字母集
		    String convert = "";  
	        for (int j = 0; j < market.getName().length(); j++) {  
	            char word = market.getName().charAt(j);  
	            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	            if (pinyinArray != null) {  
	                convert += pinyinArray[0].charAt(0);  
	            } else {  
	                convert += word;  
	            }  
	        }  
	        Integer flag = 0;
	        Integer fi = 1;
	        //判断该市场拼音是否存在
	        if(StringUtils.isEmpty(convert)){
	        	break;
	        }
	        do{
	        	Market m = marketService.queryMarketByWords(convert);
	        	if(m!=null){
	        		Integer ff = fi - 1;
	        		convert = convert.substring(0, ff.toString().length());
	        		convert = convert + String.valueOf(fi);
	        		fi ++;
	        	}else{
	        		flag = 1;
	        	}
	        }while(flag!=1);
	        market.setWords(convert);
	        market.setCheckStatus(1);
	        market.setCompanyId(0);
			Integer i= marketService.insertMarket(market);
			if (i!=null&&i.intValue()>0) {
				result.setSuccess(true);
			}
				
		
		} while (false);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView updateMarketDelStatus(HttpServletRequest request,Map<String, Object>out,Integer id,Integer isDel) throws IOException{
		ExtResult result = new ExtResult();
		do {
			// 检验是否有审核供求的权限
			if(!AuthUtils.getInstance().authorizeRight("check_products",request, null)){
				break;
			}
			Market market=new Market();
			market.setId(id);
			market.setIsDel(isDel);
			  if (market!=null&&market.getId()!=null&&market.getId().intValue()>0) {
				 Integer i= marketService.updateMarket(market);
				 if (i!=null&&i.intValue()>0) {
					result.setSuccess(true);
				}

			}
		} while (false);
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

	@RequestMapping
	public ModelAndView saveCompanyPrice(Map<String, Object> out,
			CompanyPriceDO companyPrice,HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();
		ProductsDO products = productsService.queryProductsById(companyPrice.getProductId());
		do {
			if (products == null) {
				break;
			}
			//获取对应企业报价的主要类别code
			String categoryLable = "";
			// 获取供求类别对应的企业报价类别
			categoryLable = companyPriceService.getCategoryCode(products.getCategoryProductsMainCode());
			// 企业报价推荐 一次即可 不可重复
			CompanyPriceDO pdo = companyPriceService.queryCompanyPriceByProductId(products.getId(),categoryLable);
			if(pdo!=null){
				break;
			}
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
	
	@RequestMapping
	public ModelAndView listMarketPic(Map<String, Object>out,Integer marketId,HttpServletRequest request) throws IOException{
		PageDto<MarketPic> page=new PageDto<MarketPic>();
		List<MarketPic> list=new ArrayList<MarketPic>();
		if (marketId!=null&&marketId.intValue()>0) {
			list=marketPicService.queryPicByMarketId(marketId);
		}
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
		if(AuthUtils.getInstance().authorizeRight("huzhu_edit", request, null)){
			out.put("readOnly", 1);
		}
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
	public ModelAndView defaultMarketPic(Map<String, Object>out,String idArrayStr,Integer marketId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer id = Integer.valueOf(idArrayStr);
			if(id==null){
				break;
			}
			if(marketId==null){
				break;
			}
			
			Integer i =marketPicService.updateMarketDefaultPic(marketId, id);
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refusePic(Map<String, Object>out,String idArrayStr,String unpassReason,Integer productId) throws IOException, ParseException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			
			ProductsStar ps = productsStarService.queryByProductsId(productId);
			Integer score = 0;
			int j =0;
			if (ps!=null) {
				score = ps.getScore();
				List<ProductsPicDO> list = productsPicService.queryProductPicInfoByProductsId(productId);
				for ( ProductsPicDO obj : list ) {
					if(!ProductsPicService.CHECK_STATUS_NO_PASS.equals(obj.getCheckStatus())){
						j++;
					}
				}
			}
			
			Integer i = productsPicService.batchUpdatePicStatus(idArrayStr, unpassReason, ProductsPicService.CHECK_STATUS_NO_PASS);
			if(i==null||i<1){
				break;
			}
			
			if(ps!=null){
				
				// 更新成功重新计算分数
				List<ProductsPicDO> list = productsPicService.queryProductPicInfoByProductsId(productId);
				int k = 0;
				for ( ProductsPicDO obj : list ) {
					if(!ProductsPicService.CHECK_STATUS_NO_PASS.equals(obj.getCheckStatus())){
						k++;
					}
				}
				
				productsStarService.updateByProductsId(productId, score-(j-k)*5);
				
			}
			
			result.setSuccess(true);
			
			ProductsDO product = productsService.queryProductsById(productId);
			if (product ==null) {
				break;
			}
			Company company = companyService.queryCompanyById(product.getCompanyId());
			if (company==null) {
				break;
			}
			productsService.refreshProducts(product.getId(), company.getId(), company.getMembershipCode());
		} while (false);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView delMarketPic(Map<String, Object>out,String idArrayStr,Integer marketId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			Integer i = marketPicService.batchDelMarketPicById(idArrayStr);
			if(i==null||i<1){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView checkMarketPic(Map<String, Object>out,String idArrayStr,Integer marketId) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			marketPicService.delMarketPicById(Integer.valueOf(idArrayStr), 1);
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refuseAllPic(Map<String, Object>out,Integer productId,String unpassReason) throws IOException, ParseException{
		ExtResult result = new ExtResult();
		do {
			if(productId==null){
				break;
			}
			
			// 获取星级分数
			ProductsStar ps = productsStarService.queryByProductsId(productId);
			Integer score = 0;
			int j =0;
			if (ps!=null) {
				score = ps.getScore();
				List<ProductsPicDO> list = productsPicService.queryProductPicInfoByProductsId(productId);
				for ( ProductsPicDO obj : list ) {
					if(!ProductsPicService.CHECK_STATUS_NO_PASS.equals(obj.getCheckStatus())){
						j++;
					}
				}
			}
			
			Integer i = productsPicService.batchUpdatePicStatusByProductId(productId, unpassReason, ProductsPicService.CHECK_STATUS_NO_PASS);
			if(i==null||i<1){
				break;
			}
			
			if(ps!=null){
				productsStarService.updateByProductsId(productId, score-j*5);
			}
			
			result.setSuccess(true);
			ProductsDO product = productsService.queryProductsById(productId);
			if (product ==null) {
				break;
			}
			Company company = companyService.queryCompanyById(product.getCompanyId());
			if (company==null) {
				break;
			}
			productsService.refreshProducts(product.getId(), company.getId(), company.getMembershipCode());
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
	
	@RequestMapping
	public ModelAndView delYP(Map<String, Object>out,Integer productId,HttpServletRequest request,String sampleReason) throws IOException, ParseException{
		ExtResult result = new ExtResult();
		Integer i = sampleService.checkSample(productId, SampleService.IS_DEL,sampleReason);
		do {
			if (i<=0) {
				break;
			}
			ProductsDO product = productsService.queryProductsById(productId);
			productsService.updateProductByAdmin(product);
			result.setSuccess(true);
			if (product ==null) {
				break;
			}
			Company company = companyService.queryCompanyById(product.getCompanyId());
			if (company==null) {
				break;
			}
			productsService.refreshProducts(product.getId(), company.getId(), company.getMembershipCode());
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView passYP(Map<String, Object>out,Integer productId,HttpServletRequest request) throws IOException, ParseException{
		ExtResult result = new ExtResult();
		Integer i = sampleService.checkSample(productId, SampleService.IS_NO_DEL,null);
		do {
			if (i<=0) {
				break;
			}
			ProductsDO product = productsService.queryProductsById(productId);
			productsService.updateProductByAdmin(product);
			result.setSuccess(true);
			if (product ==null) {
				break;
			}
			Company company = companyService.queryCompanyById(product.getCompanyId());
			if (company==null) {
				break;
			}
			productsService.refreshProducts(product.getId(), company.getId(), company.getMembershipCode());
		} while (false);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView hideProducts(Map<String, Object>out,Integer productId,Integer companyId,HttpServletRequest request) throws IOException{
		ExtResult result=new ExtResult();
		do {
			if(companyId==null){
				break;
			}
			//判断该公司是否为普会
			List<CrmCompanySvr> list=crmCompanySvrService.querySvrByCompanyId(companyId);
			if(list==null||list.size()<=0){
				
				if(productId!=null&&productId.intValue()>0){
					ProductsHide productsHide=new ProductsHide();
					productsHide.setProductId(productId);
					productsHide.setCompanyId(companyId);
					Integer i=productsHideService.insert(productsHide);
					if(i!=null&&i.intValue()>0){
						result.setSuccess(true);
					}
				}
			}
		} while (false);
		
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView downLoadData(Map<String, Object>out, HttpServletRequest request,HttpServletResponse response,String refreshFrom,String refreshTo) throws IOException, RowsExceededException, WriteException{
		if(StringUtils.isNotEmpty(refreshFrom)&&StringUtils.isNotEmpty(refreshTo)){
			//String[] from = refreshFrom.split("-");
			//String[] to = refreshTo.split("-");
			//判断只能导出一个月的数据
			//if(from[1].equals(to[1])){
				List<CompanyAccount> list=productsService.downLoadCompanyAccountByProduct(refreshFrom, refreshTo);
				//生成Excel
				//提供下载
				String fileNme=refreshFrom+"-"+refreshTo;
				response.setContentType("application/msexcel");
				response.setHeader("Content-disposition", "attachment; filename="+fileNme+".xls");
			
				OutputStream os = response.getOutputStream();
		
				WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
				WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
				ws.addCell(new Label(0,0,"联系人"));
				ws.addCell(new Label(1,0,"手机号码"));
				ws.addCell(new Label(2,0,"邮箱"));
		
				int i=1;
				for(CompanyAccount companyAccount :list){
					ws.addCell(new Label(0,i,companyAccount.getContact()));
					ws.addCell(new Label(1,i,companyAccount.getMobile()));
					ws.addCell(new Label(2,i,companyAccount.getEmail()));
					i++;
				}
				//现在可以写了   
				wwb.write();
				//写完后关闭   
				wwb.close();
				//输出流也关闭吧   
				os.close(); 
		}
		//}
			return null;
	}

	@RequestMapping
	public ModelAndView cancelHideProducts(Map<String, Object>out,Integer productId,Integer companyId,HttpServletRequest request) throws IOException{
		ExtResult result=new ExtResult();
			
				if(productId!=null&&productId.intValue()>0){
					
					Integer i=productsHideService.delete(productId);
					if(i!=null&&i.intValue()>0){
						result.setSuccess(true);
					}
				}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView marketIndex(Map<String, Object>out,HttpServletRequest request){
		return null;
	}
	@RequestMapping
	public ModelAndView addMarket(Map<String, Object>out,HttpServletRequest request){
		return null;
	}
	@RequestMapping
	public ModelAndView listMarket(Map<String, Object>out,HttpServletRequest request,Market market,Integer hasPic,PageDto<Market> page,String areaCode) throws IOException{
		// hasPic 1：标示该市场有图 0:无图
		if (market!=null&&StringUtils.isNotEmpty(market.getIndustry())) {
			if (market.getIndustry().equals("废旧二手设备")) {
				market.setIndustry("二手设备");
			}
		}
		if (areaCode!=null) {
			String area=null;
			if (areaCode.length()>=12) {
				CategoryDO categoryDO=categoryService.queryCategoryByCode(areaCode.substring(0, 12));
				if (categoryDO!=null) {
					area=categoryDO.getLabel();
				}
			}
			if (areaCode.length()>=16) {
				CategoryDO category=categoryService.queryCategoryByCode(areaCode.substring(0, 16));
				if (category!=null) {
					area=area+" "+category.getLabel();
				}
			}
			market.setArea(area);
		}
		page.setDir("desc");
		page.setSort("m.id");
		//根据帐号获取公司id
		if(StringUtils.isNotEmpty(market.getCompanyAccount())){
			CompanyAccount account = companyAccountService.queryAccountByAccount(market.getCompanyAccount());
			if(account!=null){
				market.setCompanyAccount(String.valueOf(account.getCompanyId()));
			}else{
				market.setCompanyAccount("-1");
			}
		}
		page=marketService.pageQueryMark(market,page,hasPic);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView editMarket(Map<String, Object>out,HttpServletRequest request,Integer marketId){
		out.put("marketId", marketId);
		return null;
	}
	@RequestMapping
	public ModelAndView queryMarketById(Map<String, Object>out,HttpServletRequest request,Integer id,PageDto<Market> page) throws IOException{
		Market market=null;
		if(id!=null&id.intValue()>0){
			market=marketService.queryMarketById(id);
		}
		List<Market> list=new ArrayList<Market>();
		list.add(market);
		page.setRecords(list);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView marketCompany(Map<String, Object>out,HttpServletRequest request,Integer marketId){
		out.put("marketId",marketId );
		return null;
	}
	@RequestMapping
	public ModelAndView queryCompanyBymarketId(Map<String, Object>out,HttpServletRequest request,Integer marketId,PageDto<CompanyDto> page,String membershipCode,Integer isPerson) throws IOException{
		page=marketCompanyService.pageCompanyByAdminMarketId(page, marketId,membershipCode,isPerson);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView productsMarket(Map<String, Object>out,HttpServletRequest request,Integer marketId, Integer readOnly){
		out.put("marketId", marketId);
		out.put("readOnly", readOnly);
		return null;
	}
	@RequestMapping
	public ModelAndView listProductsMarket(Map<String, Object>out,HttpServletRequest request,PageDto<ProductsDto> pageDTO, Integer marketId, Integer isVip,Integer isYP)
			throws IOException, ParseException {


		pageDTO=productMarketService.pageSearchProductsByAdmin(pageDTO, marketId, null, null, 0, isVip, isYP);
		return printJson(pageDTO, out);
		
	}
}