package com.zz91.ep.admin.controller.comp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
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

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.comp.CompCategoryService;
import com.zz91.ep.admin.service.comp.CompNewsService;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.admin.service.comp.SubnetCompRecommendService;
import com.zz91.ep.admin.service.credit.CreditFileService;
import com.zz91.ep.admin.service.exhibit.ExhibitService;
import com.zz91.ep.admin.service.news.NewsService;
import com.zz91.ep.admin.service.trade.MessageService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.admin.service.trade.TradeBuyService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.admin.util.SolrUpdateUtils;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.CreditFile;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.trade.TradeBuyDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.velocity.AddressTool;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2011-11-18
 */
@Controller
public class CompController extends BaseController {

	@Resource
	private CompProfileService compProfileService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private TradeBuyService tradeBuyService;
	@Resource
	private CompCategoryService compCategoryService;
	@Resource
	private CompNewsService  compNewsService;
	@Resource
	private PhotoService  photoService;
	@Resource
	private CreditFileService creditFileService;
	@Resource
	private MessageService messageService;
	@Resource
	private NewsService newsService;
	@Resource
	private ExhibitService exhibitService;
	@Resource
	private SubnetCompRecommendService subnetCompRecommendService;

	final static Map<String, String> CATEGORYMAP_MAP=new HashMap<String, String>();
	static{
		CATEGORYMAP_MAP.put("1000", "公司动态");
		CATEGORYMAP_MAP.put("1001", "技术文章");
		CATEGORYMAP_MAP.put("1002", "成功案例");
	}
	
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		Map<String, String> map = compCategoryService
				.listAllCompCategoryToMap();
		out.put("recommendCompCategory", map);
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("ws", "污水处理网");
		map1.put("ys", "原水处理网");
		map1.put("kq", "空气净化网");
		map1.put("yj", "材料药剂网");
		map1.put("beng", "泵网");
		map1.put("fa", "阀网");
		map1.put("fj", "风机网");
		map1.put("hw", "环卫设备网");
		map1.put("yqyb", "仪器仪表网");
		map1.put("zh", "综合设备网");
		out.put("subnetRec", map1);
		return null;
	}

	
	
	@RequestMapping
	public ModelAndView queryComp(Map<String, Object> out,
			HttpServletRequest request, PageDto<CompProfileDto> page,
			String name, String memberCode,String from,String to, String registerCode, String phone,
			String businessCode, String industryCode,Integer delStatus,String memberCodeBlock, 
			String email, String recommendCode,Integer otherSearch,Short serviceType,
			String subnetCategory,Integer messagetime,Short chainId) {
		String account = null;
		if (StringUtils.isEmpty(from)) {
		    from = "1900-01-01 00:00:00";
		}
		if (StringUtils.isEmpty(to)) {
		    to = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		if(StringUtils.isNotEmpty(email)&&!email.contains("@")) {
			account = email;
			email = null;
		}
		page = compProfileService.pageCompDetails(page, name, memberCode,from,to,
				registerCode, phone, businessCode, industryCode,delStatus,memberCodeBlock,
				email,recommendCode,account,otherSearch,serviceType,subnetCategory,messagetime,chainId);
		
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView details(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView profileDetails(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView queryFullProfile(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		
		List<CompProfileDto> list = new ArrayList<CompProfileDto>();
		CompProfile profile = compProfileService.queryFullProfile(id);
		CompProfileDto dto = compProfileService.builDto(profile);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("data", "company"+id);
		param.put("sort", "time");							
		param.put("dir", "desc");
		dto.setLogInfo(queryLogs(param));
		list.add(dto);
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView queryCompanyAccount(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		CompAccount account = compAccountService.getCompAccountByCid(id);
		return printJson(account, out);
	}

	   @RequestMapping
	    public ModelAndView adminLogin(HttpServletRequest request,
	            Map<String, Object> out, String account) {
	        if (StringUtils.isEmpty(account)) {
	            return new ModelAndView("redirect:"
	                    + AddressTool.getAddress("www"));
	        }
	        String password =compAccountService.queryPasswordClearByCid(compAccountService.queryCidByAccount(account));
	        out.put("account", account);
	        out.put("password", password);
	        return new ModelAndView("redirect:" + AddressTool.getAddress("myesite")
	                + "/adminlogin/adminLogin.htm");
	    }
	/**
	 * 搜索联系方式相同的公司  暂时不用
	 * @param out
	 * @param request
	 * @param id
	 * @return
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView queryLikeCompany(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		List<CompProfileDto> list = new ArrayList<CompProfileDto>();
//		List<CompProfileDto> list=compProfileService.queryCommonCompByContacts(id);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView createProfile(Map<String, Object> out,
			HttpServletRequest request, CompProfile compProfile) {
		ExtResult result = new ExtResult();
		Integer i = compProfileService.insertCompProfile(compProfile);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateProfile(Map<String, Object> out,
			HttpServletRequest request, CompProfile compProfile , Short serviceType) {
		ExtResult result = new ExtResult();
		String string = Jsoup.clean(compProfile.getDetails(), Whitelist
	 			.none());
	    compProfile.setDetailsQuery(string.replace(" ", ""));
	    if (serviceType != null) {
            if (serviceType == 0) {
                compProfile.setMainSupply((short) 1);
            } else if (serviceType == 1) {
                compProfile.setMainBuy((short) 1);
            } else if (serviceType == 2) {
                compProfile.setMainSupply((short) 1);
                compProfile.setMainBuy((short) 1);
            } else {
                compProfile.setMainSupply((short) 0);
                compProfile.setMainBuy((short) 0);
            }
        }
		Integer i = compProfileService.updateCompProfile(compProfile);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
			result.setData(compProfile.getDetailsQuery());
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateAccount(Map<String, Object> out,
			HttpServletRequest request, CompAccount account) {
		ExtResult result = new ExtResult();
		if (account.getPasswordClear() != null) {
			try {
				account.setPassword(MD5.encode(account.getPasswordClear()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Integer i = compAccountService.updateAccount(account);
		if (i != null && i.intValue() > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView compSupply(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView queryCompSupply(Map<String, Object> out,
			HttpServletRequest request, PageDto<TradeSupplyDto> page,
			Integer cid, String title, Integer checkStatus,Short type) { 
		page = tradeSupplyService.pageSupplyByAdmin(cid, title, checkStatus,type,page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView compBuy(Map<String, Object> out,
			HttpServletRequest request, Integer id) {
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView queryCompBuy(Map<String, Object> out,
			HttpServletRequest request, PageDto<TradeBuyDto> page, Integer cid,
			String title, Integer checkStatus) {
		page = tradeBuyService.pageBuyByAdmin(cid, title, checkStatus, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateDelStatusAndMemberCodeBlock(Map<String, Object> out,HttpServletRequest request,Integer id,Integer code){
		
		ExtResult result=new ExtResult();
		String memberCodeBlock = "100010001005";
		Integer j = 0;
		Integer i=compProfileService.updateDelStatus(id, code);
		if (code != 0) {
		    j = compProfileService.updateMemberCodeBlock(id, memberCodeBlock);
		} else {
		    j = compProfileService.updateMemberCodeBlock(id, "");
		}
		 if (j == null){
             result.setSuccess(false);
         }
		
		if (i!=null && i.intValue()>0){
			String sb = "删除";
			if(code != 1){
				
				sb = "取消删除";
			}
			LogUtil.getInstance().mongo(getCachedUser(request).getName(), sb, null, "company"+id);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refreshAllSearch(HttpServletRequest request,
			Map<String, Object> out) {
		ExtResult result = new ExtResult();

		// 验证是否在更新如果在更新，传null验证是否正在更新中
		try {
			if (!SolrUpdateUtils.runUpdateSolr(SolrUpdateUtils.COMPANY,
					null)) {
				if (!SolrUpdateUtils.runUpdateSolr(
						SolrUpdateUtils.COMPANY,
						SolrUpdateUtils.FULL_IMPORT)) {
					result.setSuccess(true);
				} else {
					result.setData("已经在更新!");
				}
			}
		} catch (IOException e) {
			result.setData(e);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView refreshPartSearch(HttpServletRequest request,
			Map<String, Object> out) {
		ExtResult result = new ExtResult();

		// 验证是否在更新如果在更新，传null验证是否正在更新中
		try {
			if (!SolrUpdateUtils.runUpdateSolr(SolrUpdateUtils.COMPANY,
					null)) {
				if (!SolrUpdateUtils.runUpdateSolr(
						SolrUpdateUtils.COMPANY,                                       
						SolrUpdateUtils.DELTA_IMPORT)) {
					result.setSuccess(true);
				} else {
					result.setData("已经在更新!");
				}
			}
		} catch (IOException e) {
			result.setData(e);
		}
		return printJson(result, out);
	}
	/**
	 * 推荐公司
	 * @param request
	 * @param out
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping
	public ModelAndView recommendCompCategory(HttpServletRequest request, Map<String, Object> out,
			Integer id, String type){
		Integer i = compProfileService.updateCompRecommend(id, type);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 取消推荐
	 * @param requestkkk
	 * @param out
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping
	public ModelAndView cancelRecommendCompCategory(HttpServletRequest request, Map<String, Object> out,
			Integer id){
		Integer i = compProfileService.cancelRecommendComp(id);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView compNews(Map<String, Object> out,HttpServletRequest request) {
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryCompNews(Map<String, Object> out,HttpServletRequest request,String categoryCode,String title,Short pause,
			Integer cid,Short check,Short delete,PageDto<CompNewsDto> page) {
		page=compNewsService.pageCompNewsByAdmin(cid,categoryCode, title, pause, check,delete, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView deleteCompNews(Map<String, Object> out,HttpServletRequest request,Integer id,Integer cid) {
		ExtResult result = new ExtResult();
		Integer i=compNewsService.deleteArticle(id, cid);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateCheckStatus(Map<String, Object> out,HttpServletRequest request,Integer id,Short status) {
		ExtResult result = new ExtResult();
		String account=getCachedUser(request).getAccount();
		Integer i=compNewsService.updateCheckStatus(id,status,AuthUtils.getInstance().queryStaffNameOfAccount(account));
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView content(Map<String, Object> out,HttpServletRequest request,Integer id,String success) {
		out.put("category", CATEGORYMAP_MAP);
		out.put("id", id);
		out.put("compNews", compNewsService.queryDetailsById(id));
		out.put("success", success);
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView updateNewsDetails(Map<String, Object> out,HttpServletRequest request,Integer id,String content,String title,String categoryCode,String tags) {
		Integer i=compNewsService.updateContent(id, content,title,categoryCode,tags);
		if(i!=null && i.intValue()>0){
			out.put("success", "1");
		}else{
			out.put("success", "0");
		}
		out.put("id", id);
		return new ModelAndView("redirect:content.htm");
	}
	
	@RequestMapping
	public ModelAndView upload(Map<String, Object> out,HttpServletRequest request,Integer id) {
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView uploadLogo(Map<String, Object> out,HttpServletRequest request,String title,Integer targetId) {
		ExtResult result = new ExtResult();
		String filename=String.valueOf(System.currentTimeMillis());
    	String path=buildPath("huanbao");
    	String uploadedFile=null;
		try {
			uploadedFile= MvcUpload.localUpload(request, UPLOAD_ROOT+path, filename);
		} catch (Exception e) {
			out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
		}
    	if(StringUtils.isNotEmpty(uploadedFile)){
    		Photo photo=new Photo();
    		photo.setUid(0);
    		photo.setCid(targetId);
    		if (StringUtils.isNotEmpty(title)){
    			photo.setTitle(title);
    		}
    		photo.setPhotoAlbumId(0);
    		photo.setPath(path+"/"+uploadedFile);
    		photo.setTargetId(targetId);
    		photo.setTargetType(PhotoService.TARGET_LOGO);
			Integer i=photoService.createPhoto(photo);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
				result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+uploadedFile);
			}
    	}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView uploadCertificates(Map<String, Object> out,HttpServletRequest request,String title,Integer targetId) {
		ExtResult result = new ExtResult();
		String filename=String.valueOf(System.currentTimeMillis());
    	String path=buildPath("credit");
    	String uploadedFile=null;
		try {
			uploadedFile= MvcUpload.localUpload(request, UPLOAD_ROOT+path, filename);
		} catch (Exception e) {
			out.put("data", MvcUpload.getErrorMessage(e.getMessage()));
		}
    	if(StringUtils.isNotEmpty(uploadedFile)){
    		CreditFile cf = new CreditFile();
    		cf.setCid(targetId);
    		cf.setAccount(getCachedUser(request).getAccount());
    		cf.setFileName(title);
    		cf.setPicture(path+"/"+uploadedFile);
    		Integer i=creditFileService.createCreditFile(cf);
			if(i!=null && i.intValue()>0){
				result.setSuccess(true);
				result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+uploadedFile);
			}
    	}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryLogo(Map<String, Object> out,HttpServletRequest request,Integer targerId) {
		List<Photo> list=photoService.queryPhotoByTargetType(PhotoService.TARGET_LOGO, targerId);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryBookList(Map<String, Object> out,HttpServletRequest request,Integer cid,Short check) {
		List<CreditFile> list=creditFileService.queryCreditFileByCid(cid, null, check);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView deleteLogo(Map<String, Object> out,HttpServletRequest request,Integer id) {
		ExtResult result = new ExtResult();
		Integer i=photoService.deletePhotoStatusById(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
    public ModelAndView cancelDeleteLogo(Map<String, Object> out,HttpServletRequest request,Integer id) {
        ExtResult result = new ExtResult();
        Integer i=photoService.cancelDeletePhotoStatusById(id);
        if (i!=null && i.intValue()>0){
            result.setSuccess(true);
        }
        return printJson(result, out);
    }
	
	@RequestMapping
	public ModelAndView deleteBookList(Map<String, Object> out,HttpServletRequest request,Integer id,Integer cid,String path) {
		ExtResult result = new ExtResult();
		Integer i=creditFileService.deleteCreditById(id, cid, UPLOAD_ROOT+path);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateBookCheckStatus(Map<String, Object> out,HttpServletRequest request,Integer id,Integer cid,Short status) {
		ExtResult result = new ExtResult();
		Integer i=creditFileService.updateCheckStatus(id, cid, status,getCachedUser(request).getAccount());
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView compSend(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView compReceive(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView compNew(Map<String, Object> out,HttpServletRequest request,Integer id) {
		out.put("id", id);
		return null;
	}
	
	//发送周报
	@RequestMapping
	public ModelAndView sendWeekly(Map<String, Object> out,HttpServletRequest request,String email,
			long lastLogin,String contact,Integer loginCount,Integer week,String details,Integer cid,String account) throws ParseException{
		 ExtResult result = new ExtResult();
		 String lastDate=null;
		if (lastLogin!=0){
			lastDate=DateUtil.toString(DateUtil.getDate(new Date(lastLogin), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
		}
    	  //向询盘留言接收者发送邮件
    	  Map<String, Object> map = new HashMap<String, Object>();
    	  map.put("week", week);
    	  map.put("contact", contact);
    	  map.put("details", details);
    	  map.put("lastLogin", lastDate);
    	  map.put("loginCount", loginCount);
    	  map.put("account", account);
    	  map.put("newsCount", compNewsService.queryNewCount(cid, (short)1, (short)1, (short)1));
    	  map.put("supplyCount",tradeSupplyService.querySupplyCount(cid));
    	  map.put("buyCount", tradeBuyService.querySupplyCount(cid));
    	  map.put("receiveCount", messageService.queryNoReadCount(cid,null));
    	  map.put("noReadCount", messageService.queryNoReadCount(cid,(short)0));
    	  //焦点,热点
    	  List<News> fucusOrHot=newsService.queryRecommendNewByWeekly("todayfocus", 5,0,null);
    	  for (News n : fucusOrHot) {
			if (n.getTitle().length()>22)
				n.setTitleIndex(n.getTitle().substring(0, 22));
    	  }
    	  map.put("fucusOrHot", fucusOrHot);
    	  //推荐展会
    	  List<Exhibit> reExhibit=exhibitService.queryExhibitByRecommend("recommendexhibit", 5);
    	  for (Exhibit e : reExhibit) {
    		if (e.getName().length()>22)
    			e.setName(e.getName().substring(0, 22));
		  }
    	  map.put("reExhibit", reExhibit);
    	  //市场行情
    	  List<News> market=newsService.queryRecommendNewByWeekly(null,5,null,"10001002");
    	  for (News n1 : market) {
			if (n1.getTitle().length()>22)
				n1.setTitle(n1.getTitle().substring(0, 22));
    	  }
    	  map.put("market", market);
    	  //最新加入企业
    	  List<CompProfile> newestComp= compProfileService.queryNewestComp(5,null);
    	  for (CompProfile p : newestComp) {
			if (p.getName().length()>22)
				p.setName(p.getName().substring(0, 22));
    	  }
    	  map.put("newestComp", newestComp);
    	  //最新产品信息
    	  List<TradeSupply> newestProduct=tradeSupplyService.queryTopNewestSupply(null, 5);
    	  for (TradeSupply s : newestProduct) {
			if (s.getTitle().length()>22)
				s.setTitle(s.getTitle().substring(0, 22));
    	  }
    	  map.put("newestProduct", newestProduct);
    	  //最新买家
    	  List<CompProfile> newestBuyer=compProfileService.queryNewestComp(5, "1");
    	  for (CompProfile p1 : newestBuyer) {
    		  if (p1.getName().length()>22)
  				p1.setName(p1.getName().substring(0, 22));
    	  }
    	  map.put("newestBuyer", newestBuyer);
    	  MailUtil.getInstance().sendMail(MailArga.TITLE_WEEKLY, email,  MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
  				MailArga.TEMPLATE_WEEKLY_CODE, map, MailUtil.PRIORITY_HEIGHT);
	      result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 子网推荐
	 * @return
	 */
	@RequestMapping
	public ModelAndView subRecommend(Map<String, Object> out,HttpServletRequest request,Integer id,String type){
		ExtResult result = new ExtResult();
		Integer i=subnetCompRecommendService.createdSubRecComp(id,type);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 删除推荐信息
	 * @return
	 */
	@RequestMapping
	public ModelAndView cancelSubRecommend(Map<String, Object> out,HttpServletRequest request,Integer cid){
		ExtResult result = new ExtResult();
		Integer i=subnetCompRecommendService.deleteSubRecComp(cid);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	/**
	 * 解除公司冻结帐号
	 * @return
	 */
	@RequestMapping
	public ModelAndView updateCompCode(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result=new ExtResult();
		Integer i=compProfileService.updateMemberCodeBlock(id, "");
		Integer j=compProfileService.updateDelStatus(id, 0);
		if(i!=null && i.intValue()>0 && j != null && j.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
