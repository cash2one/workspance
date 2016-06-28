/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-24
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.products.ProductsExportInquiryService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.yuanliao.YuanLiaoExportInquiryService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class InquiryController extends BaseController {

	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private ProductsExportInquiryService productsExportInquiryService;
	@Resource
	private YuanLiaoExportInquiryService yuanLiaoExportInquiryService;

	/**
	 * 处理状态为处理中
	 */
	final String STATUS_IN_PROCESS = "1";

	@RequestMapping
	public void list() {}

	/**
	 * 带分页,按照InquiryDTO和Inquiry的查询条件返回询盘结果
	 * 
	 * @param inquiryDTO
	 *            :包含查询条件,从页面传过来,也可以没有,没有时按照默认规则查询询盘信息<br/>
	 *            询盘时间_开始(searchStartDate),询盘时间_结束(searchEndDate),发件人Email(senderEmail),收件人Email(receiverEmail),
	 *            距离当前时间X天(deadline),是否有发布供求信息(hasProducts)
	 * @param inquiryDO
	 *            :包含以下查询条件: 询盘对象类型(beInquiredType),导出状态(exportStatus:默认非已处理),群发标记(batchSendType)<br/>
	 *            inquiryDO将被保存到DTO里做为查询条件提供给service
	 * @param model
	 * @return @see {@link PageDto}对象的JSON格式,属性records保存查询出来的结果,查询结果按照发送者分组排序
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView listInquiry(PageDto page, InquiryDto inquiryDto, Inquiry inquiry,String numbers,
			Map<String, Object> out) throws IOException {
		if (page == null) {
			page = new PageDto(AstConst.PAGE_SIZE);
			page.setDir("desc");
			page.setSort("id");
		} else {
			if(page.getDir()==null) {
				page.setDir("desc");
			}
			if(page.getSort()==null) {
				page.setSort("id");
			}
		}
		
//		if(inquiryDto.getIsPublished()!=null || "".equals(inquiryDto.getIsPublished())) {
//			if(StringUtils.isNumber(numbers)) {
//				int days = Integer.parseInt(numbers);
//				inquiryDto.setProductsPublishedTime(DateUtil.getDateAfterDays(new Date(), -days));
//			}
//		}
		
//		inquiryDto.setPage(page);
//		inquiryDO.setBatchSendType("0");
		inquiryDto.setInquiry(inquiry);
		
		page = inquiryService.pageInquiryByList(inquiryDto, page);
//		page.setTotalRecords(inquiryService.countInquiryByConditions(inquiryDto));
//		page.setRecords(inquiryService.queryInquiryByConditions(inquiryDto));
		return printJson(page, out);
	}

	/**
	 * 从一条现有的询盘信息生成另一些供求信息的询盘
	 * 
	 * @param inquiryDO
	 *            :现有的询盘信息
	 * @param productsArray
	 *            :类似"1|1,2|1,3|2"这样的字符串,用逗号分隔不同的(供求ID|公司ID),表示将在这些供求信息上添加询盘信息<br/>
	 *            如果为null或"",将不做生成询盘信息的处理
	 * @param model
	 * @return ExtResult对象的JSON格式<br/>
	 *         ExtResult.data保存创建失败的产品ID列表<br/>
	 *         只有当所有询盘都创建成功时ExtResult.success才为true
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView createInquiryForProductsByInquiry(HttpServletRequest request,Integer productId,Inquiry inquiry,String productsArray, Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		try {
			inquiryService.insertInquiryForProductsByInquiry(productId,inquiry, productsArray,sessionUser.getAccount());
			result.setSuccess(true);
		} catch (Exception e) {
			result.setData(e.getMessage());
		}

		return printJson(result, model);
	}
	
	/**
	 * 从一条现有的询盘信息生成另一些供求信息的询盘(对于原料供求而言)
	 * @param request
	 * @param productId
	 * @param inquiry
	 * @param productsArray
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView createInquiryForYuanLiaoByInquiry(HttpServletRequest request,Integer productId,Inquiry inquiry,String productsArray, Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		try {
			inquiryService.insertInquiryForYuanLiaoByInquiry(productId,inquiry, productsArray,sessionUser.getAccount());
			result.setSuccess(true);
		} catch (Exception e) {
			result.setData(e.getMessage());
		}

		return printJson(result, model);
	}

	// 同名的参数提交过来以什么类型接收
	@RequestMapping
	public ModelAndView createInquirysForProductsByProducts(String productsArray,
			Map<String, Object> model) throws IOException {
//		List<Inquiry> inquiryDOList = null;
		ExtResult result = new ExtResult();
//		try {
//			inquiryService.createInquirysForProductsByProducts(inquiryDOList, productsArray);
//			result.setSuccess(true);
//		} catch (Exception e) {
//			e.getMessage();
//		}

		return printJson(result, model);
	}

	/**
	 * 列出符合条件的供求信息
	 * 
	 * @param productsDTO
	 *            : 为页面提供下列查询条件:关键字(inKeyword),排除关键字(noKeyword),产品类别ID数组(categoryArray)<br />
	 *            productsDTO.categoryArray:"1,2,3"的形式,保存选中的类别
	 * @param companyDO
	 *            : 为页面提供下列查询条件:公司名称(name),城市(areaCode)
	 * @param productsDO
	 *            : 为页面提供下列查询条件:供求类型(productsTypeCode)
	 * @param model
	 * @return PageDto对象的JSON格式<br />
	 *         records里保存List<ProductsDTO>信息
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listProducts(PageDto<ProductsDto> page, Company company, ProductsDO products,Map<String, Object> model) throws IOException {
		
		company.setIsBlock("0"); // 公司未禁用
		
		page=productsService.pageProductsWithCompanyForInquiry(company, products, page);
		return printJson(page, model);
	}

	@RequestMapping
	public ModelAndView createProductsFromInquiry(ProductsDO productsDO,
			CategoryProductsDO categoryProductsDO, ProductsPicDO productsPicDO,
			Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		Integer id=productsService.publishProductsFromInquiry(productsDO);
		
		if (id!=null && id.intValue() > 0) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}

	/**
	 * 改变处理状态
	 * 
	 * @param inquiryArray
	 *            :需要被处理的询盘ID数组,以"1,2,3"的格式从页面提交过来,为""或null时不做处理
	 * @param exportStatus
	 *            :需要更改的状态,0:未处理,1:处理中,2:已处理<br/>
	 *            当状态为@see {@link InquiryService#STATUS_IN_PROCESS}时,需要同时查询登录用户的账号信息,保存到处理人(exportPerson)字段
	 * @param model
	 * @return ExtResult对象,data保存处理失败的询盘ID号,只有当所有询盘都处理成功的时候才设置success为true
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView changeExportStatus(HttpServletRequest request, String inquiryArray,
			String exportStatus, Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
//		if (inquiryArray != null && !"".equals(inquiryArray)) {
////			String exportPerson = null;
////			if(STATUS_IN_PROCESS.equals(exportStatus)){
////			}
////			String exportPerson = getCachedAuthUser(request).getUsername();
//			SessionUser sessionUser = getCachedUser(request);
//			Integer impact = inquiryService.updateInquiryExportStatus(inquiryArray, exportStatus, sessionUser.getAccount());
//			if(impact!=null && impact.intValue()>0){
//				result.setSuccess(true);
//				String[] inqueryIds=inquiryArray.split(",");
//				for(String id:inqueryIds){
//					InquiryDTO inquiry = inquiryService.queryInquiryDetailById(Integer.valueOf(id));
//					//TODO 给每一个审核通过的留言人加分
//					scoreChangeDetailsService.saveChangeDetails(
//							new ScoreChangeDetailsDo(inquiry.getSender().getCompanyId(), 
//									null, "get_post_inquiry", null, Integer.valueOf(id), null));
//				}
//			}
//		}

		return printJson(result, model);
	}
	
	/**
	 * 永久删除询盘信息
	 * @param model
	 * @param ids 要删除的询盘信息编号，如:1,2,3,5,6
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model, String ids) throws IOException{
		ExtResult result= new ExtResult();
		
//		String[] entities=ids.split(",");
//		int impacted=0;
//		for(int ii=0;ii<entities.length;ii++){
//			if(StringUtils.isNumber(entities[ii])){
//				Integer im=inquiryService.deleteInquiryById(Integer.valueOf(entities[ii]));
//				if(im.intValue()>0){
//					impacted++;
//				}
//			}
//		}
//
//		if(impacted==entities.length) {
//			result.setSuccess(true);
//		}
		
		return printJson(result, model);
	}

	/**
	 * 逻辑删除询盘信息
	 * 
	 * @param inquiryArray
	 *            :待删除的询盘ID数组,以"1,2,3"的格式从页面提交过来,为""或null时不做处理
	 * @param isdel
	 *            :页面提交过来的删除标记,1:表示删除
	 * @param model
	 * @return ExtResult对象,data保存删除失败的询盘ID号,只有当所有询盘都删除成功的时候才设置success为true
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteInquiry(String inquiryArray, String isdel, Map<String, Object> model)
			throws IOException {
		ExtResult result = new ExtResult();

//		if ((StringUtils.distinctStringArray(inquiryArray.split(",")).length) == inquiryService
//				.updateInquirysDeleteStatus(InquiryService.ALL_DEL,inquiryArray, isdel).intValue()) {
//			result.setSuccess(true);
//		}

		return printJson(result, model);
	}

	/**
	 * 将询盘添加到某个询盘分组
	 * 
	 * @param inquiryArray
	 *            :要被添加到询盘分组的询盘ID数组,以"1,2,3"的格式从页面提交过来,为""或null时不做处理
	 * @param groupId
	 *            :分组ID
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView moveToGroup(String inquiryArray, Integer groupId, Map<String, Object> model)
			throws IOException {
		ExtResult result = new ExtResult();
//		if (inquiryArray == null || "".equals(inquiryArray)) {
//			result.setSuccess(false);
//		} else {
//			String[] str = inquiryArray.split(",");
//			if (str.length == inquiryService.updateInquiryGroup(inquiryArray, groupId).intValue()) {
//				result.setSuccess(true);
//			}
//		}

		return printJson(result, model);
	}
	
	/**
	 * 询盘转供求
	 * @param model
	 * @param productsId
	 * @param companyId
	 * @param contactsId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView edit(Map<String, Object> model, String productsId, String companyId,
			String contactsId,String account,String inquiryId) throws UnsupportedEncodingException {
		model.put("productsId", productsId);
		model.put("companyId", companyId);
		model.put("contactsId", contactsId);
		model.put("account", account);
		model.put("inquiryId", inquiryId);
		
		return null;
	}
	
	/**
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView simpleproduct(Map<String, Object> model, String id)
			throws IOException {
		model.put("id", id);
		return null;
	}
	
	/**
	 * 通过编号获取供求标题和内容
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView getSimpleProductRecord(Map<String, Object> model, Integer id)
			throws IOException {
		PageDto page = new PageDto();
		List<ProductsDO> list = new ArrayList<ProductsDO>();
		ProductsDO records = productsService.queryProductsById(id);
		list.add(records);
		page.setRecords(list);
//		if (StringUtils.isNumber(id)) {
//		}
		return printJson(page, model);
	}
	
	/**
	 * 新建供求信息（询盘转供求）
	 * @param productsDO
	 * @param request
	 * @param adminTags
	 * @param companyTags
	 * @param categoryProductsDO
	 * @param isRecommendToMagazine
	 * @param out
	 * @param realTimeStr
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView addProduct(ProductsDO productsDO, HttpServletRequest request,
			String adminTags, String companyTags, CategoryProductsDO categoryProductsDO,
			String isRecommendToMagazine, Map<String, Object> out,
			String realTimeStr,String inquiryId) throws IOException, ParseException {
		ExtResult result = new ExtResult();
		
		productsDO.setTags(companyTags);
		productsDO.setTagsAdmin(adminTags);
		if(productsDO.getProvideStatus()==null){
			productsDO.setProvideStatus("0");
		}
		@SuppressWarnings("unused")
		Integer companyId=companyAccountService.queryCompanyIdByAccount(productsDO.getAccount());
		
//		if(companyId!=null && companyId.intValue()>0) {
//			productsDO.setCompanyId(companyId);
//			
//			Integer i = productsService.publishProductsFromInquiry(productsDO);
//			
//			if(i>0){
//				productsDO.setId(i);
//				//更新客户标签
//				addTags("10351001", companyTags, productsDO, request);
//				//更新管理员标签
//				addTags("10351004", adminTags, productsDO, request);
//				//将询盘设置成已导出状态
//				if(StringUtils.isNumber(inquiryId)) {
//					String exportPerson = getCachedUser(request).getAccount();
//					inquiryService.updateInquiryExportStatus(inquiryId, "2", exportPerson);
//				}
//
//				result.setSuccess(true);
//			}
//		}
		
		return printJson(result, out);
	}
	
	/**
	 * 添加标签
	 * @param code
	 * @param tagsName
	 * @param productsDO
	 * @param request
	 */
//	private void addTags(String code, String tagsName,ProductsDO productsDO, HttpServletRequest request) {
//		
//		int pdtid = productsDO.getId();
//		//获取标签列表
//		String[] tagNames = StringUtils.distinctStringArray(tagsName.split(","));
//		tagsArticleService.deleteTagsArticleRelationByArticleId(code, pdtid);
//		
//		SessionUser sessionUser = getCachedUser(request);
//		//设置,添加标签关联信息
//		for (String tagName : tagNames) {
//			TagsArticleRelation relation = new TagsArticleRelation();
//			relation.setArticleModuleCode(code);// 10351001 ,供求信息
//			relation.setArticleCategoryCode(productsDO.getCategoryProductsMainCode());
////			relation.setArtiIntegercleId(pdtid);
//			relation.setArticleTitle(productsDO.getTitle());
//			relation.setTagName(tagName);
////			relation.setCreator(authUser.getId());
//			try {
//				tagsArticleService.insertTagsArticleRelation(relation);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * 获取指点询盘的内容
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView querySimpleInquiry(Map<String, Object> model,String id) throws IOException{
		ExtResult result =new ExtResult();
		
//		if(StringUtils.isNumber(id)){
//			Inquiry inquiry = inquiryService.queryInquiryContentById(Integer.parseInt(id));
//			if(inquiry!=null) {
//				result.setSuccess(true);
//				result.setData(inquiry.getContent());
//			}
//		}
		
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView queryInquiry(HttpServletRequest request, Map<String, Object> out, 
			Inquiry inquiry, PageDto<Inquiry> page) throws IOException{
		if(StringUtils.isNotEmpty(inquiry.getReceiverAccount())){
			inquiry.setReceiverAccount(URLDecoder.decode(inquiry.getReceiverAccount(), HttpUtils.CHARSET_UTF8));
		}
		if(StringUtils.isNotEmpty(inquiry.getSenderAccount())){
			inquiry.setSenderAccount(URLDecoder.decode(inquiry.getSenderAccount(), HttpUtils.CHARSET_UTF8));
		}
		page = inquiryService.pageInquiryByAdmin(inquiry, page);
		return printJson(page, out);
	}
	
	/**
	 * 根据账户搜索接收方 邮箱 email
	 * @param out
	 * @param account
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryInquiryEmailByAccount(Map<String,Object>out,String account) throws IOException{
		CompanyAccount companyAccount= companyAccountService.queryAccountByAccount(account);
		return printJson(companyAccount,out);
	}
	
	@RequestMapping
	public ModelAndView listOfCompany(HttpServletRequest request, Map<String, Object> out, Integer companyId, Integer readOnly){

		out.put("companyId", companyId);
		out.put("readOnly", readOnly);
		
		CompanyAccount account=companyAccountService.queryAdminAccountByCompanyId(companyId);
		if(account!=null){
			try {
				out.put("account", URLEncoder.encode(account.getAccount(), HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
		}
		
		return null;
	}
	
	/**
	 * 公司导出的询盘
	 */
	@RequestMapping
	public ModelAndView listOfCompanyExport(HttpServletRequest request, Map<String, Object> out, Integer companyId){

		out.put("companyId", companyId);
		return null;
	}
	
	/**
	 * 供求导出的询盘
	 */
	@RequestMapping
	public ModelAndView listOfProductExport(HttpServletRequest request, Map<String, Object> out, Integer productId){
		out.put("productId", productId);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryExportInquiry(Map<String, Object> out,PageDto<ProductsDto> page,Integer productId,Integer companyId) throws IOException, ParseException{
		if (page == null) {
			page = new PageDto<ProductsDto>(AstConst.PAGE_SIZE);
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}

		if (page.getSort() == null) {
			page.setSort("id");
		}
		
		page = productsExportInquiryService.pageProductsExport(productId, companyId, page);
		
		return printJson(page, out);
	}
	
	/**
	 * 原料供求导出的询盘
	 */
	@RequestMapping
	public ModelAndView listOfYuanLiaoExport(HttpServletRequest request, Map<String, Object> out, Integer yuanLiaoId){
		out.put("yuanLiaoId", yuanLiaoId);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryExportYuanLiaoInquiry(Map<String, Object> out,PageDto<YuanliaoDto> page,Integer yuanLiaoId,Integer companyId) throws IOException, ParseException{
		if (page == null) {
			page = new PageDto<YuanliaoDto>(AstConst.PAGE_SIZE);
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}

		if (page.getSort() == null) {
			page.setSort("id");
		}
		
		page = yuanLiaoExportInquiryService.pageYuanLiaoExport(yuanLiaoId, companyId, page);
		
		return printJson(page, out);
	}
	
}
