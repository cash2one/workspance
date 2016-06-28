/**
 * 
 */
package com.ast.ast1949.esite.controller.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.esite.controller.BaseController;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.domain.company.EsiteFriendLinkDo;
import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.products.ProductsSeriesDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CompanyUploadFileDAO;
import com.ast.ast1949.persist.company.EsiteFriendLinkDao;
import com.ast.ast1949.persist.company.EsiteNewsDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsSeriesDAO;
import com.ast.ast1949.service.company.CompanyAccountContactService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * @author root
 *
 */
@Controller
public class EsiteController extends BaseController {
	
	@Resource
	private CreditIntegralDetailsService creditIntegralDetailsService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyUploadFileDAO companyUploadFileDAO;
	@Resource
	private ProductsSeriesDAO productsSeriesDAO;
	@Resource
	private EsiteNewsDao esiteNewsDao;
	@Resource
	private EsiteFriendLinkDao esiteFriendLinkDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private CompanyAccountContactService companyAccountContactService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private CompanyAttestService companyAttestService;
	@Resource
	private CreditFileService creditFileService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private ProductsService productsService;
	
	
	@RequestMapping
	public ModelAndView companyInfo(HttpServletRequest request, Map<String, Object> out, Integer cid,
			PageDto<CompanyAccountContact> page) throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		do {
			if(cid==null){
				break;
			}
			CompanyAccount account = companyAccountService.queryAdminAccountByCompanyId(cid);
			Company company=companyService.querySimpleCompanyById(cid);
			
			if(StringUtils.isNotEmpty(company.getAreaCode())){
				if(company.getAreaCode().length()>=8){
					map.put("country",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,8)));
				}
				if(company.getAreaCode().length()>=12){
					map.put("province",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,12)));
				}
				if(company.getAreaCode().length()>=16){
					map.put("city",CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,16)));
				}
			}
			
			map.put("company", company);
			map.put("account", account);
			//获取时间戳
			Long time=new Date().getTime();
			map.put("time", time);
			page = companyAccountContactService.pageContactByCompany(account.getAccount(), page,"0");
			map.put("page", page);
			//Integer integral= creditIntegralDetailsService.countIntegralByCompany(cid);
			//if(integral==null){
			//	integral=0;
			//}
			//map.put("attestIntegral", integral);
			//认证证书 获取诚信指数
			CompanyAttest companyAttest = new CompanyAttest();
			companyAttest.setCompanyId(company.getId());
			companyAttest.setCheckStatus("1");
			CompanyAttest attest = companyAttestService.queryOneInfo(companyAttest);
			// 荣誉证书
			List<CreditFileDo> fileList = creditFileService.queryFileByCompany(company.getId());
			// 审核通过证书
			List<CreditFileDo> passList = new ArrayList<CreditFileDo>();
			// 未过期证书数量
			int notExpiredNum = 0;
			for (CreditFileDo file : fileList) {
				if ("1".equals(file.getCheckStatus())) {
					passList.add(file);
				}
			}
			// 未过期证书
			for (CreditFileDo file : passList) {
				if (file.getEndTime() != null&& file.getEndTime().getTime() < new Date().getTime()){ 
					continue;
				}else{
					notExpiredNum++;
				}
			}
			Integer attestIntegral = 0;
			if (attest != null && CompanyAttestService.STATE_PASS.equals(attest.getCheckStatus())) {
				attestIntegral += 10;
			}
			
			if (companyAttestService.validatePassOrNot(company.getId())) {
				map.put("isAttest", 1);
			}
			
			attestIntegral += notExpiredNum * 5 + creditIntegralDetailsService.countIntegralByOperationKey(company.getId(),"service_zst");
			map.put("attestIntegral", attestIntegral);
			
			do{
				// 普会访问 判断 访问页面的公司 是否高会(再生铜、简版再生通)已过期
				Boolean isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ZST_CODE);
				if(isView){
					map.put("isOut", 1);
					break;
				}
				isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.JBZST_CODE);
				if(isView){
					map.put("isOut", 1);
					break;
				}
				isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.ESITE_CODE);
				if(isView){
					map.put("isOut", 1);
					break;
				}
				isView = crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.BAIDU_CODE);
				if(isView){
					map.put("isOut", 1);
					break;
				}
				SsoUser ssoUser = getCachedUser(request);
				if (ssoUser!=null&&!"10051000".equals(ssoUser.getMembershipCode())) {
					map.put("isOut", 1);
					break;
				}
				map.put("isOut", 0); // 没有查看权限号码不显示 显示为登陆可见
			}while(false);
			
			// 产业带入口
			List<Market> marketList = marketCompanyService.queryMarketByCompanyId(cid);
			if (marketList!=null&& marketList.size()>0) {
				map.put("market", marketList.get(marketList.size()-1));
			}
			// 高会的服务年限标志
			int flag;
			String member = companyService.queryMembershipOfCompany(cid);
			if (crmCompanySvrService.validateEsitePeriod(cid)
					&& "10051000".equals(member)) {
				flag = 0;
			} else {
				flag = 1;
			}
			map.put("flag", flag);
		} while (false);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView companyPic(HttpServletRequest request, Map<String, Object> out, Integer cid) throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", companyUploadFileDAO.queryByCompanyId(cid));
		return printJson(map, out);
	}
	
	/**
	 * 门市部获取公司详细介绍
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView companyDetails(HttpServletRequest request, Map<String, Object> out, Integer cid) throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		do {
			if(cid==null){
				break;
			}
			
			// 获取包含html的公司详细介绍
			String details=companyService.queryDetails(cid);
			map.put("details", details);
			//清楚html格式 获得无html格式的公司详细介绍
			String result = Jsoup.clean(details, Whitelist.none());
			map.put("detailsNoHtml", result);
		} while (false);
		return printJson(map, out);
	}
	
	
	@RequestMapping
	public ModelAndView productCategory(HttpServletRequest request, Map<String, Object> out, Integer cid, Integer size) throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		List<ProductsSeriesDO> list = productsSeriesDAO.querySeriesOfCompany(cid);
		List<ProductsSeriesDO> nlist = new ArrayList<ProductsSeriesDO>();
		for (ProductsSeriesDO obj:list) {
			if(productsSeriesDAO.querySeriesContacts(obj.getId())>0){
				nlist.add(obj);
			}
		}
		map.put("list", nlist);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView newsList(HttpServletRequest request, Map<String, Object> out, Integer cid, Integer size) throws IOException{
		if(size==null || size==0){
			size=10;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		PageDto<EsiteNewsDo> page = new PageDto<EsiteNewsDo>();
		page.setPageSize(size); // 读取数量需确定
		page.setStartIndex(0);
		page.setSort("gmt_created");
		page.setDir("desc");
		List<EsiteNewsDo> list=esiteNewsDao.queryNewsByCompany(cid, page);
		List<EsiteNewsDo> nlist = new ArrayList<EsiteNewsDo>();
		for(EsiteNewsDo obj:list){
			obj.setDomain(companyService.queryDomainOfCompany(obj.getCompanyId()).getDomainZz91());
			nlist.add(obj);
		}
		map.put("list", nlist);
		map.put("listSize", list.size());
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView friendLink(HttpServletRequest request, Map<String, Object> out, Integer cid, Integer size) throws IOException{
		if(size==null || size==0){
			size=10;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		List<EsiteFriendLinkDo> list=esiteFriendLinkDao.queryFriendLinkByCompany(cid,size);
		map.put("list", list);
		map.put("listSize", list.size());
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView productsList(HttpServletRequest request, Map<String, Object> out, Integer cid,String kw,Integer sid, Integer size,PageDto<ProductsDto> page) throws IOException{
		if(size==null || size==0){
			size=10;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		if(crmCompanySvrService.validatePeriod(cid, CrmSvrService.CRM_SP)){
			list=productsDAO.queryProductsWithPicByCompanyForSp(cid, kw, sid, page);
		}else{
//			page.setSort("p.id");
//			page.setDir("desc");
//			page.setPageSize(size);
//			list = productsService.pageProductsWithPicByCompanyEsiteWithDetails(cid, kw,sid, page).getRecords();
			list=productsDAO.queryProductsWithPicByCompany(cid, size);
		}
		map.put("list", list);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView test(HttpServletRequest request, Map<String, Object> out, Integer cid, Integer size){
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		result.setData("test");
		out.put("result", result);
		return new ModelAndView("submit");
	}
}
