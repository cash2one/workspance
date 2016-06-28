/**
 * Created on 2012-11-9
 */
package com.ast.ast1949.trade.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.ContactClickLogService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * @author 闰伍
 * 
 */

@Controller
public class GsjjController extends BaseController {
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private PriceService priceService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneLogService phoneLogService; 
	@Resource
	private PhoneClickLogService phoneClickLogService;

	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	
	@Resource
	private ContactClickLogService  contactClickLogService;

	/*
	 * trade 门市部化
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request,
			Integer tab, PageDto<ProductsDO> page, String productTypeCode,
			Integer companyId) throws HttpException, IOException {

		return new ModelAndView("redirect:http://company.zz91.com/compinfo" + companyId + ".htm");
		
//		SsoUser ssoUser = getCachedUser(request);
//		out.put("ssoUser", ssoUser);
//
//		if (tab == null) {
//			tab = 0;
//		}
//		out.put("tab", tab);
//		out.put("id", companyId);
//		out.put("productTypeCode", productTypeCode);
//		// 公司详情
//		Company company = companyService.queryCompanyById(companyId);
//		do {
//			if(company==null){
//				break;
//			}
//			if(company!=null&&company.getIsBlock()!=null&&"1".equals(company.getIsBlock())){
//				out.put(AstConst.ERROR_TEXT, "对不起，该公司已不做生意或账号被冻结！");
//				break;
//			}
//			if (StringUtils.isNotEmpty(company.getIntroduction())) {
//				company.setIntroduction(Jsoup.clean(company.getIntroduction(),
//						Whitelist.none()));
//			}
//			out.put("company", company);
//			out.put("account", companyAccountService.queryAccountByCompanyId(companyId));
//			//判断是否为一元或者五元来电宝
//			if(crmCompanySvrService.validateLDB(company.getId(), CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE)){
//				Phone phone = phoneService.queryByCompanyId(company.getId());
//				if(phone!=null){
//					out.put("ldbTel", phone.getTel());// 400 电话号码
//				}
//			}
//			// 当前位置
//			String mainCode = codeMap.get(company.getIndustryCode());
//			String mainLabel = CategoryProductsFacade.getInstance().getValue(
//					mainCode);
//			out.put("mainCode", mainCode);
//			out.put("mainLabel", mainLabel);
//
//			// 公司到供求
//			if (tab == 1 || tab == 2) {
//				page.setPageSize(6);
//				out.put("page", productsService.pageProductsByTyepOfCompany(
//						companyId.toString(), productTypeCode, page));
//			}
//
//			// 同行业推荐公司
//			List<Company> companyList = companyService
//					.queryZstMemberByIndustryCode(company.getIndustryCode(), 5);
//			out.put("companyList", companyList);
//
//			// 最新行情保价信息
//			// out.put("priceList",priceService.queryPriceByTitleAndTypeId(null,mainLabel,
//			// "5"));
//			PageDto<PriceDO> pricePage = new PageDto<PriceDO>();
//			pricePage.setPageSize(5);
//			out.put("priceList", priceService.pagePriceBySearchEngine(
//					mainLabel, null, pricePage).getRecords());
//
//			// 相关资讯
//			PageDto<PostDto> p = new PageDto<PostDto>(5);
//			out.put("postList", bbsPostService.pagePostBySearchEngine(
//					mainLabel, p).getRecords());
//			
//			// 判断来电宝客户权限  后台整体只需判断查看的公司是再生通还是来电宝，查看的人是来电宝看过还是没看过。
//			do {
//				Boolean isShow = false;
//				isShow = crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.ZST_CODE);
//				//判断是否为一元或者五元来电宝
//				if(!isShow){
//					//isShow = crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.LDB_CODE);
//					
//					isShow =crmCompanySvrService.validateLDB(companyId, CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE);
//				}
//				if(isShow){
//					out.put("isSee", isShow);
//					break;
//				}
//				
//				if(ssoUser==null){
//					break;
//				}
//				Boolean isView = false;
//			    //JBZST_CODE 是减版再生通 可以免费看联系方式
//				isView = crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.JBZST_CODE);
//				if(!isView){
//					//判断是否为一元或者五元来电宝
//					isView =crmCompanySvrService.validateLDB(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE);
//					if(isView){
//						Phone phone = phoneService.queryByCompanyId(ssoUser.getCompanyId());
//						String balance = phoneLogService.countBalance(phone);
//						PhoneCostSvr phoneCostSvr = phoneCostSvrService.queryByCompanyId(ssoUser.getCompanyId());
//						if(phoneCostSvr==null){
//							break;
//						}
//						out.put("clickFee", phoneCostSvr.getClickFee());
//						PhoneClickLog phoneClickLog = phoneClickLogService.queryById(ssoUser.getCompanyId(), companyId);
//						if(phoneClickLog!=null){
//							out.put("isSee", true);
//						}else{
//							   if(StringUtils.isNotEmpty(balance)&&Double.valueOf(balance)>0){
//								out.put("ldbFlag", 1);
//							   }else{
//								out.put("ldbFlag", 0);
//							    }
//						 }
//					}
//					
//					//积分兑换查看服务 
//					
//					boolean viewAble = contactClickLogService.scoreCvtViewContact(ssoUser.getCompanyId(),ssoUser.getAccount(),company.getId());
//					if(viewAble){
//						out.put("isSee", true);
//					}
//					
//				}else{
//					out.put("isSee", true);
//				 }
//			} while (false);
//
//			// SEO
//			Integer currentPage = page.getStartIndex() / page.getPageSize() + 1;
//			buildSeo(tab, mainLabel, currentPage.toString(), company, out);
//			return new ModelAndView("redirect:http://company.zz91.com/compinfo" + companyId + ".htm");
//		} while (false);
//		return new ModelAndView("/common/error");
	}

	// 组装不同情况的TKD
	private void buildSeo(Integer tab, String mainLabel, String current,
			Company company, Map<String, Object> out) {
		if (tab == 1 || tab == 2) {
			String business = company.getBusiness();
			if (StringUtils.isEmpty(mainLabel)) {
				mainLabel = "信息";
			}
			if (StringUtils.isEmpty(business)) {
				business = company.getName() + "主营业务是" + tabMap.get(tab)
						+ mainLabel;
			}
			if (business.length() > 100) {
				business = business.substring(0, 100);
			}
			SeoUtil.getInstance().buildSeo(
					"gq",
					new String[] { (tabMap.get(tab) + mainLabel),
							company.getName(), current },
					new String[] { (tabMap.get(tab) + mainLabel) },
					new String[] { business }, out);
		}
		if (tab == 0 || tab == 3) {
			String str = company.getIntroduction();
			if (StringUtils.isNotEmpty(str)&&str.length() > 100) {
				str = str.substring(0, 100);
			}else{
				str = company.getName();
			}
			SeoUtil.getInstance().buildSeo("gsjj",
					new String[] { tabMap.get(tab), company.getName() },
					new String[] { company.getName() }, new String[] { str },
					out);
		}
	}

	/*
	 * public String getMainCode(String code){ //供求类别集合大类 Map<String, String>
	 * mapValue = CategoryProductsFacade.getInstance().getChild(""); //反转供求类别集合
	 * Map<String, String> values = new HashMap<String, String>(); for (String o
	 * : mapValue.keySet()) { values.put(mapValue.get(o), o); } //company类别集合
	 * Map<String, String> map= CategoryFacade.getInstance().getChild("1000");
	 * return values.get(map.get(code)); }
	 */
	final static Map<String, String> codeMap = new HashMap<String, String>();
	static {
		codeMap.put("10001001", "1000");// 废金属
		codeMap.put("10001000", "1001");// 废塑料
		codeMap.put("10001003", "1002");// 废旧轮胎与废橡胶
		codeMap.put("10001004", "1003");// 废纺织品与废皮革
		codeMap.put("10001002", "1004");// 废纸
		codeMap.put("10001005", "1005");// 废电子电器
		codeMap.put("10001006", "1006");// 废玻璃
		codeMap.put("10001007", "1007");// 废旧二手设备
		codeMap.put("10001008", "1008");// 其它废料
		codeMap.put("10001009", "1009");// 服务
	}
	// 选项卡的title参数集合
	final static Map<Integer, String> tabMap = new HashMap<Integer, String>();
	static {
		tabMap.put(0, "公司简介");
		tabMap.put(1, "供应");
		tabMap.put(2, "求购");
		tabMap.put(3, "联系方式");
	}

}
