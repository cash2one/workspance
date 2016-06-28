package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.velocity.tools.generic.MathTool;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.CrmOutLog;
import com.ast.ast1949.domain.company.InquiryTask;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.domain.phone.PhoneCallClickLog;
import com.ast.ast1949.domain.phone.PhoneChangeLog;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.domain.phone.PhoneLibrary;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.domain.phone.PhoneNumberChangeLog;
import com.ast.ast1949.domain.phone.PhoneSeoKeyWords;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPhoneOptimizationDto;
import com.ast.ast1949.dto.analysis.AnalysisSerchDto;
import com.ast.ast1949.dto.phone.PhoneChangeLogDto;
import com.ast.ast1949.dto.phone.PhoneDto;
import com.ast.ast1949.dto.phone.PhoneLogDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.analysis.AnalysisPhoneOptimizationService;
import com.ast.ast1949.service.analysis.AnalysisPpcLogService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCsService;
import com.ast.ast1949.service.company.CrmOutLogService;
import com.ast.ast1949.service.company.InquiryTaskService;
import com.ast.ast1949.service.phone.PhoneBlacklistService;
import com.ast.ast1949.service.phone.PhoneCallClickLogService;
import com.ast.ast1949.service.phone.PhoneChangeLogService;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLibraryService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneNumberChangeLogService;
import com.ast.ast1949.service.phone.PhoneSeoKeyWordService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.AES;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.velocity.AddressTool;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * author:kongsj date:2013-7-2
 */
@Controller
public class PhoneController extends  BaseController{

	// 接口私钥：
	private static String apiKey = "a16d751a0e879ca533aeba5f0e985c5e";
	// 用户ID：userkey
	private static String userKey = "11683e186d";
	private static String BLANK_URL_ADD = "http://api.maxvox.com.cn/api/add_private_black";
	private static String BLANK_URL_DEL = "http://api.maxvox.com.cn/api/del_private_black";
	private static String BLANK_URL_OPEN = "http://api.maxvox.com.cn/api/update_black_status";

	private static String BIND_URL = AddressTool.getAddress("phoneCTTOpenUrl")+"/open/bind";
	private static String KEY_URL = AddressTool.getAddress("phoneCTTOpenUrl")+"/open/key";

	// 组装string 的map
	private static Map<String, String> map = new TreeMap<String, String>();

	static {
		map.put("userkey", userKey);
	}
	private static Map<String, String> map1 = new TreeMap<String, String>();

	static {
		map1.put("userkey", userKey);
	}
	final static String TARGET_DATE_FORMAT = "yyyy-MM-dd";
	@Resource
	private PhoneService phoneService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private ProductsService productsService;
	@Resource
	private InquiryTaskService inquiryTaskService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	@Resource
	private PhoneClickLogService phoneClickLogService;
	@Resource
	private AnalysisPpcLogService analysisPpcLogService;
	@Resource
	private PhoneBlacklistService phoneBlacklistService;
	@Resource
	private PhoneCallClickLogService phoneCallClickLogService;
	@Resource
	private PhoneChangeLogService phoneChangeLogService;
	@Resource
	private PhoneLibraryService phoneLibraryService;
	@Resource 
	private CrmCsService crmCsService;
	@Resource
	private CrmOutLogService crmOutLogService;
	@Resource
	private CompanyService companyService;
	@Resource
	private PhoneSeoKeyWordService phoneSeoKeyWordService;
	@Resource
	private AnalysisPhoneOptimizationService analysisPhoneOptimizationService;
	@Resource
	private PhoneNumberChangeLogService phoneNumberChangeLogService;
	
	final static String CS_DEPT_CODE = "10001005";
	private void initCommon(HttpServletRequest request, Map<String, Object> out) {
		Map<String, String> map = AuthUtils.getInstance().queryStaffOfDept(
				CS_DEPT_CODE);
		out.put("csMap", JSONObject.fromObject(map));
		// out.put("csDeptCode", CS_DEPT_CODE);

		out.put("cs", getCachedUser(request).getAccount());
		out.put("csName", getCachedUser(request).getName());
		if (AuthUtils.getInstance().authorizeRight("assign_company", request,
				null)) {
			out.put("asignFlag", "1");
			out.put("allcs", map);
		}
	}
	
	@RequestMapping
	public ModelAndView reassignLdb(HttpServletRequest request,Map<String, Object>out,
			Integer companyId,String csAccount) throws IOException{
		ExtResult result=new ExtResult();
		Boolean b=false;
		//查找这个客户是否有客服
		String oldCsAccount="";
		if (companyId!=null) {
			CrmCs crmCs=crmCsService.queryCsOfCompany(companyId);
			if (crmCs!=null&&StringUtils.isNotEmpty(crmCs.getCsAccount())) {
				oldCsAccount=crmCs.getCsAccount();
			}else {
				CrmOutLog crmOutLog=crmOutLogService.queryCrmoutLogByCompanyId(companyId);
				if(crmOutLog!=null&&crmOutLog.getOldCsAccount()!=null){
					oldCsAccount=crmOutLog.getOldCsAccount();
				}
			}
		}
		if(companyId!=null&&StringUtils.isNotEmpty(csAccount)){
			 b=crmCsService.reassignLdb(csAccount, companyId);
		}

		if(b){
			crmOutLogService.insert(companyId, oldCsAccount,csAccount, CrmOutLogService.STATUS_IN);
		}
	
		result.setSuccess(b);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request) throws ParseException {
		initCommon(request,out);
		out.put("from", null);
		out.put("to", null);
		return null;
	}

	@RequestMapping
	public void csindex(Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView query(Map<String, Object> out, Phone phone,
			PageDto<Phone> page) throws IOException {
		page = phoneService.pageList(phone, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView queryAllPhone(Map<String, Object> out, Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, String from, String to, Company company,String laveFromStr,String laveToStr,String csAccount,String svrFrom,String svrTo)
			throws IOException, ParseException {
		Date froms = null;
		Date tos = null;
		if (from != "" && to != "" && from != null && to != null) {
			from = from.replace("T", " ");
			to = to.replace("T", " ");
			froms = DateUtil.getDate(from, "yyyy-MM-dd HH:mm:ss");
			tos = DateUtil.getDate(to, "yyyy-MM-dd HH:mm:ss");
		}
		float laveFrom=0.0f;
        float laveTo=0.0f;
        if(laveFromStr!=null&&StringUtils.isNumber(laveFromStr)){
        	laveFrom=Float.valueOf(laveFromStr);	
        }
        if(laveToStr!=null&&StringUtils.isNumber(laveToStr)){
        	laveTo=Float.valueOf(laveToStr);
        }
        //服务费到期时间始
        Date svrFroms = null;
        //服务费到期时间终
		Date svrTos = null;
		if (svrFrom!=null) {
			try {
				svrFroms=DateUtil.getDate(svrFrom, "yyyy-MM-dd");
			} catch (Exception e) {
				svrFroms=null;
			}
		}
		if (svrTo!=null) {
			try {
				svrTos=DateUtil.getDate(svrTo, "yyyy-MM-dd");
			} catch (Exception e) {
				svrTos=null;
			}
		}
		page = phoneService.pageQueryList(phone, companyAccount, phoneCostSvr,page, froms, tos, company,laveFrom,laveTo,csAccount,svrFroms,svrTos);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryAllPhoneBs(Map<String, Object> out, Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, String from, String to, Company company,String laveFromStr,String laveToStr,String csAccount)
			throws IOException, ParseException {
		Date froms = null;
		Date tos = null;
		if (from != "" && to != "" && from != null && to != null) {
			from = from.replace("T", " ");
			to = to.replace("T", " ");
			froms = DateUtil.getDate(from, "yyyy-MM-dd HH:mm:ss");
			tos = DateUtil.getDate(to, "yyyy-MM-dd HH:mm:ss");
		}
		float laveFrom=0.0f;
        float laveTo=0.0f;
        if(laveFromStr!=null&&StringUtils.isNumber(laveFromStr)){
        	laveFrom=Float.valueOf(laveFromStr);	
        }
        if(laveToStr!=null&&StringUtils.isNumber(laveToStr)){
        	laveTo=Float.valueOf(laveToStr);
        }
		page = phoneService.pageQueryBsList(phone, companyAccount, phoneCostSvr,page, froms, tos, company,laveFrom,laveTo,csAccount);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView queryAllPhoneMyBs(Map<String, Object> out, Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, String from, String to, Company company,String laveFromStr,String laveToStr,String csAccount,HttpServletRequest request)
			throws IOException, ParseException {
		Date froms = null;
		Date tos = null;
		if (from != "" && to != "" && from != null && to != null) {
			from = from.replace("T", " ");
			to = to.replace("T", " ");
			froms = DateUtil.getDate(from, "yyyy-MM-dd HH:mm:ss");
			tos = DateUtil.getDate(to, "yyyy-MM-dd HH:mm:ss");
		}
		float laveFrom=0.0f;
        float laveTo=0.0f;
        if(laveFromStr!=null&&StringUtils.isNumber(laveFromStr)){
        	laveFrom=Float.valueOf(laveFromStr);	
        }
        if(laveToStr!=null&&StringUtils.isNumber(laveToStr)){
        	laveTo=Float.valueOf(laveToStr);
        }
        csAccount=getCachedUser(request).getAccount();
		page = phoneService.pageQueryBsList(phone, companyAccount, phoneCostSvr,page, froms, tos, company,laveFrom,laveTo,csAccount);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView exportData(HttpServletRequest request,HttpServletResponse response, Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, String from, String to, Company company,String laveFromStr,String laveToStr,String csAccount)
			throws IOException, ParseException, RowsExceededException, WriteException {
		Date froms = null;
		Date tos = null;
		if (StringUtils.isNotEmpty(from)&&StringUtils.isNotEmpty(to)) {
			to=DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd 23:59:59");
			from = from.replace("T", " ");
			to = to.replace("T", " ");
			froms = DateUtil.getDate(from, "yyyy-MM-dd");
			tos = DateUtil.getDate(new Date(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1).getTime()-1), "yyyy-MM-dd HH:mm:ss");
		}
		if(StringUtils.isNotEmpty(from)&&StringUtils.isNotEmpty(to)){
			from=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
			to=DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd");
		}else{
			from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd");
			to=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), "yyyy-MM-dd");
		}
		String filename=null;
		if(from.equals(DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd"))){
			filename=from;
		}else{
			filename=from+"_"+DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd");
		}
		float laveFrom=0.0f;
        float laveTo=0.0f;
        if(laveFromStr!=null&&StringUtils.isNumber(laveFromStr)){
        	laveFrom=Float.valueOf(laveFromStr);	
        }
        if(laveToStr!=null&&StringUtils.isNumber(laveToStr)){
        	laveTo=Float.valueOf(laveToStr);
        }
		List<PhoneDto> list= phoneService.pageQueryListl(phone, companyAccount, phoneCostSvr,page, froms, tos, company,laveFrom,laveTo,csAccount);
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename="+filename+".xls");// 设定输出文件
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		ws.addCell(new Label(0,0,"公司名称"));
		ws.addCell(new Label(1,0,"400电话号码"));
		ws.addCell(new Label(2,0,"已接电话"));
		ws.addCell(new Label(3,0,"未接电话"));
		ws.addCell(new Label(4,0,"电话总量"));
		ws.addCell(new Label(5,0,"接听率"));
		ws.addCell(new Label(6,0,"账户总金额"));
		ws.addCell(new Label(7,0,"账户余额"));
		ws.addCell(new Label(8,0,"消费金额"));
		ws.addCell(new Label(9,0,"pv"));
		ws.addCell(new Label(10,0,"开通日期"));
		ws.addCell(new Label(11,0,"是否过期"));
		ws.addCell(new Label(12,0,"主营业务"));
		int i=1;
		for(PhoneDto obj:list){
			ws.addCell(new Label(0,i,obj.getCompany().getName()));
			ws.addCell(new Label(1,i,obj.getPhone().getTel()));
			ws.addCell(new Label(2,i,obj.getCompany().getAddress()));
			ws.addCell(new Label(3,i,obj.getMissCall()));
			ws.addCell(new Label(4,i,obj.getAllPhone()));
			ws.addCell(new Label(5,i,obj.getPhoneRate()));
			ws.addCell(new Label(6,i,obj.getPhone().getAmount()));
			ws.addCell(new Label(7,i,obj.getPhone().getBalance()));
			ws.addCell(new Label(8,i,obj.getSumAllFee()));
			if(obj.getPv()!=null){
				ws.addCell(new jxl.write.Number(9,i,obj.getPv()));
			}
			if(obj.getCompany().getGmtCreated()!=null){
				ws.addCell(new Label(10,i,DateUtil.toString(obj.getCompany().getGmtCreated(), "yyyy-MM-dd HH:mm:ss")));
			}
			ws.addCell(new Label(11,i,obj.getIsOut()));
			ws.addCell(new Label(12,i,obj.getCompany().getBusiness()));
			i++;
		}
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}

	@RequestMapping
	public ModelAndView queryMyPhone(HttpServletRequest request,
			Map<String, Object> out, Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, String from, String to, Company company,String laveFromStr,String laveToStr,String svrFrom,String svrTo)
			throws IOException, ParseException {
		Date froms = null;
		Date tos = null;
		if (from != "" && to != "" && from != null && to != null) {
			from = from.replace("T", " ");
			to = to.replace("T", " ");
			froms = DateUtil.getDate(from, "yyyy-MM-dd HH:mm:ss");
			tos = DateUtil.getDate(to, "yyyy-MM-dd HH:mm:ss");
		}
		String csAccount = getCachedUser(request).getAccount();
        float laveFrom=0.0f;
        float laveTo=0.0f;
        if(laveFromStr!=null&&StringUtils.isNumber(laveFromStr)){
        	laveFrom=Float.valueOf(laveFromStr);	
        }
        if(laveToStr!=null&&StringUtils.isNumber(laveToStr)){
        	laveTo=Float.valueOf(laveToStr);
        }
        //服务费到期时间始
        Date svrFroms = null;
        //服务费到期时间终
		Date svrTos = null;
		if (svrFrom!=null) {
			try {
				svrFroms=DateUtil.getDate(svrFrom, "yyyy-MM-dd");
			} catch (Exception e) {
				svrFroms=null;
			}
		}
		if (svrTo!=null) {
			try {
				svrTos=DateUtil.getDate(svrTo, "yyyy-MM-dd");
			} catch (Exception e) {
				svrTos=null;
			}
		}
		page = phoneService.pageQueryList(phone, companyAccount,
				phoneCostSvr, page, froms, tos, company,laveFrom,laveTo,csAccount,svrFroms,svrTos);

		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView getBill(Map<String, Object> out, String from, String to)
			throws ParseException, IOException, HttpException {
		String sign = getSign(DateUtil.getDate(from, "yyyy-MM-dd"),
				DateUtil.getDate(to, "yyyy-MM-dd"), null, null);
		String str = "&start_time=" + from + " 00:00:00&end_time=" + to
				+ " 00:00:00&sign=" + sign;
		String url = "http://api.maxvox.com.cn/api/pci_pull/getCallByDateTime?userkey="
				+ userKey + str.replaceAll(" ", "%20");
		String result = HttpUtils.getInstance().httpGet(url,
				HttpUtils.CHARSET_UTF8);
		JSONObject js = JSONObject.fromObject(result);
		JSONArray jsa = JSONArray.fromObject(js.get("result"));
		for (int i = 0; i < jsa.size(); i++) {
			JSONObject obj = JSONObject.fromObject(jsa.get(i));
			if (obj != null && !"0".equals(obj.get("state"))) {
				Phone phone = phoneService.queryByTel(obj.getString("tel"));
				if (phone == null) {
					continue;
				}
				PhoneCostSvr phoneCostSvr = phoneCostSvrService
						.queryByCompanyId(phone.getCompanyId());
				if (phoneCostSvr == null) {
					continue;
				}

				MathTool mt = new MathTool();
				PhoneLog phoneLog = new PhoneLog();
				Double f = obj.getDouble("call_fee") / 0.15;
				Double fdd = mt.mul(f, phoneCostSvr.getTelFee()).doubleValue();
				BigDecimal bd = new BigDecimal(fdd);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneLog.setCallFee(bd.toString());
				phoneLog.setCallSn(obj.getString("call_sn"));
				phoneLog.setEndTime(DateUtil.getDate(obj.getString("end_time"),
						"yyyy-MM-dd HH:mm:ss"));
				phoneLog.setCallerId(obj.getString("call_passid"));
				phoneLog.setStartTime(DateUtil.getDate(
						obj.getString("start_time"), "yyyy-MM-dd HH:mm:ss"));
				phoneLog.setTel(obj.getString("tel"));
				String ext = obj.getString("ext");
				if (StringUtils.isNotEmpty(ext)) {
					phoneLog.setTel(phoneLog.getTel() + "-" + ext);
				}
				Integer k = phoneLogService.insert(phoneLog);
				if (k > 0) {
					// 扣除余额
					phoneCostSvrService.reduceFee(phoneCostSvr.getId(),
							phoneCostSvr.getCompanyId(),
							phoneCostSvr.getTelFee());
				}

			}
		}
		return printJs(jsa.toString(), out);
	}

	@RequestMapping
	public ModelAndView add(Map<String, Object> out, Phone phone,
			String selectTel,String svrEn) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = 0;
		
		//对绑定的手机号码进行处理（以0开头的，0去掉）
		if(phone.getMobile().length()==12&&phone.getMobile().startsWith("01")){
			phone.setMobile(phone.getMobile().substring(1, 12));
		}else if(phone.getMobile().length()>12&&phone.getMobile().startsWith("01")){
			phone.setMobile(phone.getMobile().substring(1));
		}
		if (phone.getId() != null && phone.getId() > 0) {
			i = phoneService.update(phone);
		} else {
			if (StringUtils.isNotEmpty(selectTel)) {
				phone.setTel(selectTel);
			}
			i = phoneService.insert(phone);

			if (i > 0) {
				// 开通铁通400号码，号码库失效标志
				if (StringUtils.isNotEmpty(selectTel)) {
					Integer j = phoneLibraryService.updateForStatusByTel(
							selectTel, PhoneLibraryService.TYPE_STATUS_NO);
					if (j > 0) {
						// 绑定号码 与 400号码
						String called = phoneLibraryService.queryCalledByTel(selectTel);
						try {
							bindPhone(called, phone.getMobile()); // 提交400号码与手机的 关系
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					String tel = "";
					if (StringUtils.isNumber(phone.getTel())) {
						tel = phone.getTel();
					} else {
						String[] telArray = phone.getTel().split("-");
						tel = telArray[0];
					}
					// 开通黑名单功能
					phoneService.openStatus(tel);
					// 搜索400黑名单 将其加入到新开通的400号码中
					phoneService.updateBlickList(tel);
					result.setSuccess(true);
				}
			}
		}
		if (i > 0) {
			result.setSuccess(true);
		}

		return printJson(result, out);

	}

	@RequestMapping
	public ModelAndView init(Map<String, Object> out, Integer companyId,
			Integer id) throws IOException {
		List<Phone> list = new ArrayList<Phone>();
		do {
			if (id == null && companyId == null) {
				break;
			}
			Phone phone = phoneService.queryById(id);
			if (phone == null) {
				phone = new Phone();
				if (companyId != null) {
					phone.setCompanyId(companyId);
					CompanyAccount ca = companyAccountService
							.queryAccountByCompanyId(companyId);
					if (ca != null) {
						phone.setAccount(ca.getAccount());
					}
				}
			}
			list.add(phone);
		} while (false);
		out.put("end", new Date());
		return printJson(list, out);
	}

	/**
	 * 获取400拉清单key
	 * userkey=11683e186d tel=4008676666 ext= start_time=2012-08-01 00:00:00
	 * end_time=2012-08-01 00:02:00
	 */
	private String getSign(Date from, Date to, String tel, String ext) {
		String result = "";
		if (StringUtils.isNotEmpty(tel)) {
			map.put("tel", tel);
		}
		if (StringUtils.isNotEmpty(ext)) {
			map.put("ext", ext);
		}
		map.put("start_time", DateUtil.toString(from, "yyyy-MM-dd 00:00:00"));
		map.put("end_time", DateUtil.toString(to, "yyyy-MM-dd 00:00:00"));
		for (String key : map.keySet()) {
			result = result + key + "=" + map.get(key);
		}
		result += apiKey;
		try {
			result = MD5.encode(result, MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return result;
	}

	@RequestMapping
	public void batchInquiry(PageDto<ProductsDto> page, ProductsDO product,
			Map<String, Object> out, String keyword)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(keyword)) {
			out.put("keyword", StringUtils.decryptUrlParameter(keyword));
		}
		out.put("product", product);
		if (StringUtils.isNotEmpty(product.getTitle())) {
			product.setTitle(StringUtils.decryptUrlParameter(product.getTitle()));
			out.put("titleEncode", URLEncoder.encode(product.getTitle(),
					HttpUtils.CHARSET_UTF8));
			out.put("page", productsService.pageLHProductsBySearchEngine(
					product, null, null, page));
		}
	}

	@RequestMapping
	public ModelAndView doSendToTask(String id, Integer companyId,
			String title, String content, Map<String, Object> out)
			throws IOException {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		InquiryTask inquiryTask = new InquiryTask();
		inquiryTask.setCompanyId(companyId);
		inquiryTask.setTargetType(InquiryTaskService.TARGET_TYPE_COMPANY);
		inquiryTask.setTitle(StringUtils.decryptUrlParameter(title));
		content = content.replaceAll("astoand", "&");
		inquiryTask.setContent(StringUtils.decryptUrlParameter(content));
		inquiryTask.setPostStatus("0");
		Integer count = 0;
		for (Integer targetId : ids) {
			inquiryTask.setTargetId(targetId);
			Integer i = inquiryTaskService.addNewInquiryTask(inquiryTask);
			if (i > 0) {
				count++;
			}
		}
		return printJs("alert('提交成功" + count + "条询盘')", out);
	}

	@RequestMapping
	public ModelAndView doAllSendToTask(String keyword, Integer companyId,
			String title, String content, Map<String, Object> out)
			throws IOException {
		InquiryTask inquiryTask = new InquiryTask();
		inquiryTask.setCompanyId(companyId);
		inquiryTask.setTargetType(InquiryTaskService.TARGET_TYPE_COMPANY);
		inquiryTask.setTitle(StringUtils.decryptUrlParameter(title));
		content = content.replaceAll("astoand", "&");
		inquiryTask.setContent(StringUtils.decryptUrlParameter(content));
		inquiryTask.setPostStatus("0");
		Integer count = 0;
		ProductsDO product = new ProductsDO();
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		product.setTitle(StringUtils.decryptUrlParameter(keyword));
		page.setPageSize(5000);
		page = productsService.pageLHProductsBySearchEngine(product, null,
				null, page);
		for (ProductsDto dto : page.getRecords()) {
			inquiryTask.setTargetId(dto.getProducts().getCompanyId());
			Integer i = inquiryTaskService.addNewInquiryTask(inquiryTask);
			if (i > 0) {
				count++;
			}
		}
		return printJs("alert('提交成功" + count + "条询盘')", out);
	}

	@RequestMapping
	public ModelAndView phoneLog(Map<String, Object> out, PhoneLog phoneLog,
			PageDto<PhoneLog> page, Integer companyId) throws IOException {
		if(companyId!=null&&companyId!=0){
			out.put("companyId", companyId);
		}
		out.put("from", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}

	@RequestMapping
	public ModelAndView exportPhoneLog(HttpServletRequest request,HttpServletResponse response,
			PhoneLog phoneLog, PageDto<PhoneLogDto> page, Integer companyId,
			String from, String to) throws IOException,
			ParseException, RowsExceededException, WriteException {
		to=DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd 23:59:59");
		phoneLog.setStartTime(DateUtil.getDate(from, "yyyy-MM-dd"));
		phoneLog.setEndTime(DateUtil.getDate(to, "yyyy-MM-dd HH:mm:ss"));
		String filename=null;
		if(from.equals(DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd"))){
			filename=from;
		}else{
			filename=from+"_"+DateUtil.toString(DateUtil.getDate(to, "yyyy-MM-dd"), "yyyy-MM-dd");
		}
		List<PhoneLogDto> list=new ArrayList<PhoneLogDto>();
		List<PhoneLogDto> lists=new ArrayList<PhoneLogDto>();
		Map<String, String> map=new HashMap<String,String>();
		Company company=null;
		if (companyId != 0) {
			Phone phone = phoneService.queryByCompanyId(companyId);
			if (phone != null && StringUtils.isNotEmpty(phone.getTel())) {
				phoneLog.setTel(phone.getTel());
			}
		    company=companyService.queryCompanyById(companyId);
			String companyName=null;
			if(company!=null){
				companyName=company.getName();
			}
			list=phoneLogService.exportPhoneLog(companyId,phoneLog, page);
			for(PhoneLogDto pd:list){
				pd.getPhoneLog().setCallSn(companyName);
			}
		}else{
			lists=phoneLogService.exportPhoneLog(companyId,phoneLog, page);
			for(PhoneLogDto pd:lists){
				if(map.keySet().contains(pd.getPhoneLog().getTel())){
					pd.getPhoneLog().setCallSn(map.get(pd.getPhoneLog().getTel()));
					list.add(pd);
				}else{
					Phone phone = phoneService.queryByTel(pd.getPhoneLog().getTel());
					if(phone!=null&&phone.getCompanyId()!=null){
						company=companyService.queryCompanyById(phone.getCompanyId());
						if(company!=null){
							pd.getPhoneLog().setCallSn(company.getName());
							map.put(pd.getPhoneLog().getTel(), company.getName());
							list.add(pd);
						}
					}
				}
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition", "attachment; filename="+filename+".xls");// 设定输出文件
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		ws.addCell(new Label(0,0,"公司名称"));
		ws.addCell(new Label(1,0,"400号码"));
		ws.addCell(new Label(2,0,"开始通话时间"));
		ws.addCell(new Label(3,0,"结束通话时间"));
		ws.addCell(new Label(4,0,"通话时长"));
		ws.addCell(new Label(5,0,"来电号码"));
		ws.addCell(new Label(6,0,"通话费用"));
		ws.addCell(new Label(7,0,"来电地区"));
		int i=1;
		for(PhoneLogDto obj:list){
			ws.addCell(new Label(0,i,obj.getPhoneLog().getCallSn()));
			ws.addCell(new Label(1,i,obj.getPhoneLog().getTel()));
			ws.addCell(new Label(2,i,DateUtil.toString(obj.getPhoneLog().getStartTime(), "yyyy-MM-dd HH:mm:ss")));
			ws.addCell(new Label(3,i,DateUtil.toString(obj.getPhoneLog().getEndTime(), "yyyy-MM-dd HH:mm:ss")));
			ws.addCell(new Label(4,i,obj.getDiffMinute()));
			ws.addCell(new Label(5,i,obj.getPhoneLog().getCallerId()));
			ws.addCell(new Label(6,i,obj.getPhoneLog().getCallFee()));
			ws.addCell(new Label(7,i,obj.getPhoneLog().getAddress()));
			i++;
		}
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}
    
	@RequestMapping
	public ModelAndView queryphoneLog(Map<String, Object> out,
			PhoneLog phoneLog, PageDto<PhoneLogDto> page, Integer companyId,
			String from, String to, String callSnCode) throws IOException,
			ParseException {
		if ((callSnCode != null && !callSnCode.equals("2")) || companyId == null) {
			if (StringUtils.isNotEmpty(from)) {
				Date fromDate = DateUtil.getDate(from, TARGET_DATE_FORMAT);
				phoneLog.setStartTime(fromDate);
			}
			if (StringUtils.isNotEmpty(to)) {
				Date toDate = DateUtil.getDate(to, TARGET_DATE_FORMAT);
				phoneLog.setEndTime(toDate);
			}
		}
		// 已接电话
		if (callSnCode != null && callSnCode.equals("3")) {
			phoneLog.setState("1");
			callSnCode = "1";
		}
		// 未接电话
		if (callSnCode != null && callSnCode.equals("4")) {
			phoneLog.setState("0");
			callSnCode = "1";
		}
		if (StringUtils.isEmpty(page.getDir())) {
			page.setDir("desc");
		}
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("start_time");
		}

		if ((StringUtils.isNotEmpty(callSnCode) && callSnCode.equals("2"))) {
			phoneLog.setCallSn("0");// 查月租
		} else if (StringUtils.isNotEmpty(callSnCode)) {
			phoneLog.setCallSn(callSnCode);
		}

		if (companyId != null) {
			phoneLog.setCompanyId(companyId);
			Phone phone = phoneService.queryByCompanyId(companyId);
			if (phone != null && StringUtils.isNotEmpty(phone.getTel())) {
				phoneLog.setTel(phone.getTel());
			}
		}
		phoneLogService.pageDtoList(phoneLog, page);

		return printJson(page, out);
	}
	
	/**
	 *拉黑来电并设置此通来电为0元
	 * @author zhujq
	 * @param request
	 * @param out
	 * @param ids
	 * @param blackReason
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView insertPhoneBlackList(HttpServletRequest request,Map<String, Object> out, String ids,String callers,String blackReason) throws IOException{
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		do {
			if(StringUtils.isEmpty(ids) || StringUtils.isEmpty(blackReason)){
				break;
			}
			Integer k = phoneLogService.insertPhoneBlackList(ids,callers,sessionUser.getAccount(),blackReason);
			
			if(k.intValue()==0){
				result.setSuccess(false);
				result.setData("拉黑失败");
			}else{
				result.setSuccess(true);
				result.setData("拉黑成功,钱已返充");
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView clickLog(Map<String, Object> out, PhoneLog phoneLog,
			PageDto<PhoneLog> page, Integer companyId) {
		out.put("companyId", companyId);
		out.put("from", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}

	@RequestMapping
	public ModelAndView queryClickLog(HttpServletRequest request,
			Map<String, Object> out, PageDto<PhoneClickLog> page,
			PhoneClickLog phoneClickLog, Integer companyId, String targetFrom,
			String targetTo) throws IOException, ParseException {

		page.setSort("id");
		page.setDir("desc");
		if (StringUtils.isNotEmpty(targetFrom)) {
			Date fromDate = DateUtil.getDate(targetFrom, TARGET_DATE_FORMAT);
			phoneClickLog.setFrom(fromDate);

		}
		if (StringUtils.isNotEmpty(targetTo)) {
			Date toDate = DateUtil.getDate(targetTo, TARGET_DATE_FORMAT);
			phoneClickLog.setTo(toDate);
		}

		if (companyId != 0) {
			Phone phone = phoneService.queryByCompanyId(companyId);
			if (phone != null && StringUtils.isNotEmpty(phone.getTel())) {
				phoneClickLog.setCompanyId(companyId);
			}
		} else {
			phoneClickLog.setCompanyId(null);
		}
		page = phoneClickLogService.pageDtoList(phoneClickLog, page);
		return printJson(page, out);
	}

	/**
	 * 统计流量
	 * */
	@RequestMapping
	public ModelAndView ppcFlow(Map<String, Object> out,
			AnalysisPpcLog analysisPpcLog, PageDto<PhoneLog> page, Integer cid) {
		out.put("cid", cid);
		out.put("from", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}

	@RequestMapping
	public ModelAndView queryPhoneFlow(Map<String, Object> out,
			PageDto<AnalysisPpcLog> page, String from, String to, Integer cid)
			throws IOException, ParseException {

		AnalysisPpcLog analysisPpcLog = new AnalysisPpcLog();
		if (cid != 0) {
			analysisPpcLog.setCid(cid);
		}
		if (StringUtils.isNotEmpty(from)) {
			Date fromDate = DateUtil.getDate(from, TARGET_DATE_FORMAT);
			analysisPpcLog.setFrom(fromDate);
		}
		if (StringUtils.isNotEmpty(to)) {
			Date toDate = DateUtil.getDate(to, TARGET_DATE_FORMAT);
			analysisPpcLog.setTo(toDate);
		}
		page = analysisPpcLogService.queryList(analysisPpcLog, page);

		return printJson(page, out);
	}

	/**
	 * 计算成本
	 * 
	 * @throws IOException
	 * */

	@RequestMapping
	public ModelAndView phoneCost(Map<String, Object> out) throws IOException {
		out.put("from", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}

	@RequestMapping
	public ModelAndView queryPhoneCost(PhoneLog phoneLog,
			Map<String, Object> out, PageDto<PhoneLogDto> page, String from,
			String to) throws IOException, ParseException {
		if (StringUtils.isNotEmpty(from)) {
			Date fromDate = DateUtil.getDate(from, TARGET_DATE_FORMAT);
			phoneLog.setStartTime(fromDate);
		}
		if (StringUtils.isNotEmpty(to)) {
			Date toDate = DateUtil.getDate(to, TARGET_DATE_FORMAT);
			phoneLog.setEndTime(toDate);
		}
		page = phoneLogService.queryPhoneCost(phoneLog, page);

		return printJson(page, out);
	}

	/**
	 * 查询接通率
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * **/
	@RequestMapping
	public ModelAndView queryCallPhoneRate(PhoneLog phoneLog,
			Map<String, Object> out, PageDto<PhoneLogDto> page,
			Integer companyId, String from, String to) throws IOException,
			ParseException {
		if (StringUtils.isNotEmpty(from)) {
			Date fromDate = DateUtil.getDate(from, TARGET_DATE_FORMAT);
			phoneLog.setStartTime(fromDate);
		}
		if (StringUtils.isNotEmpty(to)) {
			Date toDate = DateUtil.getDate(to, TARGET_DATE_FORMAT);
			phoneLog.setEndTime(toDate);
		}

		if (companyId != null) {
			Phone phone = phoneService.queryByCompanyId(companyId);
			if (phone != null && StringUtils.isNotEmpty(phone.getTel())) {
				phoneLog.setTel(phone.getTel());
			}
		}
		page = phoneLogService.queryCallPhoneRate(phoneLog, page);

		return printJson(page, out);
	}

	@RequestMapping
	public void blacklist(Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView queryBlacklist(Map<String, Object> out,
			PageDto<PhoneBlacklist> page, PhoneBlacklist phoneBlacklist)
			throws IOException {
		page.setSort("id");
		page.setDir("desc");
		page = phoneBlacklistService.page(phoneBlacklist, page);
		return printJson(page, out);
	}

	/**
	 * sn yes string 请求流水号 sign yes string 签名（请查看签名认证说明） userkey yes string 用户id
	 * tel yes string 400主机号码 phone yes string 要屏蔽的主叫号码，多个可用","分割 ext no string
	 * 分机号码
	 * 
	 * @param args
	 * @throws IOException
	 * @throws HttpException
	 */
	@RequestMapping
	public ModelAndView batchAddBlackList(Map<String, Object> out, String phone)
			throws HttpException, IOException {
		ExtResult result = new ExtResult();
		if (StringUtils.isEmpty(phone)) {
			return printJson(result, out);
		}

		// 批量插入
		Integer i = phoneBlacklistService.batchInsert(phone);

		if (i > 0) {
			result.setSuccess(true);
		}

		String phoneFix = "0" + phone.replace(",", ",0");

		phone = phone + "," + phoneFix;
		// 搜索出所有的400号码
		PageDto<Phone> page = new PageDto<Phone>();
		page.setPageSize(300); // 处理300名客户的黑名单
		List<Phone> list = phoneService.pageList(new Phone(), page)
				.getRecords();
		for (Phone obj : list) {
			String longStr = String.valueOf(new Date().getTime());
			String tel = "";
			if (StringUtils.isNumber(obj.getTel())) {
				tel = obj.getTel();
			} else {
				String[] telArray = obj.getTel().split("-");
				tel = telArray[0];
			}
			map.put("sn", longStr);
			map.put("tel", tel);
			map.put("phone", phone);

			String urlStr = "";
			for (String key : map.keySet()) {
				urlStr = urlStr + key + "=" + map.get(key);
			}
			urlStr += apiKey;

			try {
				urlStr = MD5.encode(urlStr, MD5.LENGTH_32);
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}

			NameValuePair[] data = new NameValuePair[] {
					new BasicNameValuePair("sn", longStr),
					new BasicNameValuePair("sign", urlStr),
					new BasicNameValuePair("userkey", userKey),
					new BasicNameValuePair("tel", tel),
					new BasicNameValuePair("phone", phone) };
			HttpUtils.getInstance().httpPost(BLANK_URL_ADD, data,
					HttpUtils.CHARSET_UTF8);
		}

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView batchDelBlackList(Map<String, Object> out, String ids)
			throws HttpException, IOException {
		ExtResult result = new ExtResult();
		if (StringUtils.isEmpty(ids)) {
			return printJson(result, out);
		}

		// 批量删除
		Integer i = phoneBlacklistService.batchDelete(ids);

		if (i > 0) {
			result.setSuccess(true);
		}

		String phone = "";
		String[] phoneArray = null;
		if (ids.indexOf(",") != -1) {
			phoneArray = ids.split(",");
		} else {
			phoneArray = new String[] { ids };
		}
		for (String id : phoneArray) {
			try {
				phone = phone + ","
						+ phoneBlacklistService.queryById(Integer.valueOf(id));
			} catch (Exception e) {
				continue;
			}
		}

		// 搜索出所有的400号码
		PageDto<Phone> page = new PageDto<Phone>();
		page.setPageSize(300); // 处理300名客户的黑名单
		List<Phone> list = phoneService.pageList(new Phone(), page)
				.getRecords();
		for (Phone obj : list) {
			String longStr = String.valueOf(new Date().getTime());
			String tel = "";
			if (StringUtils.isNumber(obj.getTel())) {
				tel = obj.getTel();
			} else {
				String[] telArray = obj.getTel().split("-");
				tel = telArray[0];
			}
			map.put("sn", longStr);
			map.put("tel", tel);
			map.put("phone", phone);

			String urlStr = "";
			for (String key : map.keySet()) {
				urlStr = urlStr + key + "=" + map.get(key);
			}
			urlStr += apiKey;

			try {
				urlStr = MD5.encode(urlStr, MD5.LENGTH_32);
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}

			NameValuePair[] data = new NameValuePair[] {
					new BasicNameValuePair("sn", longStr),
					new BasicNameValuePair("sign", urlStr),
					new BasicNameValuePair("userkey", userKey),
					new BasicNameValuePair("tel", tel),
					new BasicNameValuePair("phone", phone) };
			HttpUtils.getInstance().httpPost(BLANK_URL_DEL, data,
					HttpUtils.CHARSET_UTF8);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView batchUpdateStatus(Map<String, Object> out)
			throws IOException, HttpException {
		ExtResult result = new ExtResult();

		// 搜索出所有的400号码
		PageDto<Phone> page = new PageDto<Phone>();
		page.setPageSize(300); // 处理300名客户的黑名单
		List<Phone> list = phoneService.pageList(new Phone(), page)
				.getRecords();
		for (Phone obj : list) {
			String longStr = String.valueOf(new Date().getTime());
			String tel = "";
			if (StringUtils.isNumber(obj.getTel())) {
				tel = obj.getTel();
			} else {
				String[] telArray = obj.getTel().split("-");
				tel = telArray[0];
			}
			map.put("sn", longStr);
			map.put("tel", tel);
			map.put("status", "1");

			String urlStr = "";
			for (String key : map.keySet()) {
				urlStr = urlStr + key + "=" + map.get(key);
			}
			urlStr += apiKey;

			try {
				urlStr = MD5.encode(urlStr, MD5.LENGTH_32);
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}

			NameValuePair[] data = new NameValuePair[] {
					new BasicNameValuePair("sn", longStr),
					new BasicNameValuePair("sign", urlStr),
					new BasicNameValuePair("userkey", userKey),
					new BasicNameValuePair("tel", tel),
					new BasicNameValuePair("status", "1") };
			HttpUtils.getInstance().httpPost(BLANK_URL_OPEN, data,
					HttpUtils.CHARSET_UTF8);
		}

		result.setSuccess(true);

		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView callClickLog(Map<String, Object> out,
			PhoneCallClickLog phoneCallClickLog,
			PageDto<PhoneCallClickLog> page, Integer companyId) {
		out.put("companyId", companyId);
		out.put("from", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		out.put("to", DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT));
		return null;
	}

	@RequestMapping
	public ModelAndView queryCallClickLog(HttpServletRequest request,
			Map<String, Object> out, PageDto<PhoneCallClickLog> page,
			PhoneCallClickLog phoneCallClickLog, Integer companyId,
			String targetFrom, String targetTo) throws IOException,
			ParseException {

		page.setSort("id");
		page.setDir("desc");
		if (StringUtils.isNotEmpty(targetFrom)) {
			Date fromDate = DateUtil.getDate(targetFrom, TARGET_DATE_FORMAT);
			phoneCallClickLog.setFrom(fromDate);

		}
		if (StringUtils.isNotEmpty(targetTo)) {
			Date toDate = DateUtil.getDate(targetTo, TARGET_DATE_FORMAT);
			phoneCallClickLog.setTo(toDate);
		}

		if (companyId != 0) {
			Phone phone = phoneService.queryByCompanyId(companyId);
			if (phone != null && StringUtils.isNotEmpty(phone.getTel())) {
				phoneCallClickLog.setCompanyId(companyId);
			}
		} else {
			phoneCallClickLog.setCompanyId(null);
		}
		page = phoneCallClickLogService.pageCallClickList(phoneCallClickLog,
				page);
		return printJson(page, out);
	}

	@RequestMapping
	public void checkInfo(Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView queryCheckInfo(HttpServletRequest request,Map<String, Object>out,
			PageDto<PhoneChangeLogDto> page,String checkStatus) throws IOException,ParseException{
		page.setSort("id");
		page.setDir("desc");
		 page =phoneChangeLogService.queryAllPhoneChangeLogs(page,checkStatus);
		 return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView isCheck(HttpServletRequest request,Map<String, Object>out,Integer id,String checkStatus) throws IOException{
		ExtResult result = new ExtResult();
		if(id!=null){
			if("2".equals(checkStatus)){
				Integer j=phoneChangeLogService.updateStatus(id, checkStatus);
				if(j.intValue()>0){
					result.setData(true);
					result.setSuccess(true);
				} else {
					result.setSuccess(false);
				}
			}else{
				Integer n=phoneChangeLogService.updateChangeContextByType(id);
				if(n.intValue()>0){
					Integer j=phoneChangeLogService.updateStatus(id, checkStatus);
					if(j.intValue()>0){
						result.setData(true);
						result.setSuccess(true);
					}else{
						result.setSuccess(false);
					}
				}
			}
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryPhoneChangeLogById(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		// Ext后台要printjson 要是page ，不知道什么原因
		PageDto<PhoneChangeLog> page = new PageDto<PhoneChangeLog>();
		List<PhoneChangeLog> list = new ArrayList<PhoneChangeLog>();
		PhoneChangeLog phoneChangeLog = phoneChangeLogService
				.queryPhoChangeLogbyid(id);
		list.add(phoneChangeLog);
		page.setRecords(list);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView updateChangeContent(HttpServletRequest request,
			Map<String, Object> out, Integer id, String changeContent,String changeType)
			throws IOException {
		Integer i=0;
		ExtResult result = new ExtResult();
		if ("2".equals(changeType)) {
			i = phoneChangeLogService.updateContent(id, Jsoup.clean(changeContent, Whitelist.none()));
		}else {
			i = phoneChangeLogService.updateContent(id, changeContent);
		}
		
		if (i.intValue() > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryPhoneLibaryForOpen(Map<String, Object> out)
			throws IOException {
		PageDto<PhoneLibrary> page = new PageDto<PhoneLibrary>();
		PhoneLibrary phoneLibrary = new PhoneLibrary();
		phoneLibrary.setStatus(PhoneLibraryService.TYPE_STATUS_YES);
		page.setPageSize(500);
		List<PhoneLibrary> list = phoneLibraryService.pageList(phoneLibrary,
				page).getRecords();
		return printJson(list, out);
	}

	@RequestMapping
	public void phoneLibrary() {

	}

	@RequestMapping
	public ModelAndView queryPhoneLibary(Map<String, Object> out,
			PageDto<PhoneLibrary> page, PhoneLibrary phoneLibrary,Integer status)
			throws IOException {
		page.setPageSize(20);
		if(status!=null&&status==1){
			phoneLibrary.setStatus(1);
		}
		page = phoneLibraryService.pageList(phoneLibrary, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView delLibrary(Map<String, Object> out, Integer id)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = phoneLibraryService.updateForStatusById(id,
				PhoneLibraryService.TYPE_STATUS_YES);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 提交铁通 座机与 手机号码绑定
	 * @param called
	 * @param mobile
	 * @throws Exception
	 */
	private void bindPhone(String called, String mobile) throws Exception {

		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();

		// 获取 key
		String key = HttpUtils.getInstance().httpGet(KEY_URL,HttpUtils.CHARSET_UTF8);

		// 绑定号码
		jo.put("CALLER", called);
		jo.put("PHONE1", mobile);
		ja.add(jo);

		// aes加密
		String text = AES.encrypt(key, "admin0", "654321", ja.toString());

		// 组装数据
		NameValuePair[] data = new NameValuePair[] {
				new BasicNameValuePair("name", "admin0"),
				new BasicNameValuePair("key", key), new BasicNameValuePair("text", text) };

		
		if (Boolean.valueOf(String.valueOf(MemcachedUtils.getInstance().getClient().get(PhoneService.BIND_FLAG)))) {
			// 提交绑定信息
			HttpUtils.getInstance().httpPost(BIND_URL, data, HttpUtils.CHARSET_UTF8);
		}else{
			System.out.println("url:"+BIND_URL+"data:"+data);
		}
	}
	
	public static void main(String[] args) {
		PhoneController obj = new PhoneController();
		try {
			obj.bindPhone("56214527", "013738194812");
		} catch (Exception e) {
		}
	}

	@RequestMapping
	public ModelAndView queryPhoneLibaryById(Map<String, Object> out, Integer id)
			throws IOException {
		PageDto<PhoneLibrary> page = new PageDto<PhoneLibrary>();
		List<PhoneLibrary> list = new ArrayList<PhoneLibrary>();
		PhoneLibrary phoneLibrary = new PhoneLibrary();
		if (id != null) {
			phoneLibrary = phoneLibraryService.queryById(id);
		}
		list.add(phoneLibrary);
		page.setRecords(list);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView updatePhoneLibraryById(Map<String, Object> out,
			Integer id, String tel, String called) throws IOException {
		ExtResult result = new ExtResult();
		if (id != null) {
			Integer n = phoneLibraryService.updatePhoneLibraryById(id, tel,
					called);
			if (n.intValue() > 0) {
				result.setSuccess(true);
			}
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);

	}
	@RequestMapping
	public ModelAndView delNumber(Map<String, Object>out, Integer id) throws IOException{
		ExtResult result=new ExtResult();
		if(id!=null){
			Integer i=phoneLibraryService.delNumber(id);
			if(i!=null&&i.intValue()>0){
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
			}
		}else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public void seokeyword(HttpServletRequest request,Map<String, Object>out){
		
	}
	
	@RequestMapping
	public ModelAndView seoLog(HttpServletRequest request,Map<String, Object>out,AnalysisSerchDto analysisSerchDto,PageDto<AnalysisPhoneOptimizationDto> page) throws IOException{
//		page = analysisPhoneOptimizationService.selectByAnalysisPhone(page, analysisSerchDto);
//		out.put("page", page);
		return new ModelAndView();
//		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView seoLogData(HttpServletRequest request,Map<String, Object>out,AnalysisSerchDto analysisSerchDto,PageDto<AnalysisPhoneOptimizationDto> page) throws IOException{
		page = analysisPhoneOptimizationService.selectByAnalysisPhone(page, analysisSerchDto);
//		out.put("page", page);
//		return new ModelAndView();
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryPhoneSeoKeyWordById(Map<String,Object>out,Integer id) throws IOException{
		List<PhoneSeoKeyWords> list=new ArrayList<PhoneSeoKeyWords>();
		PageDto<PhoneSeoKeyWords> page=new PageDto<PhoneSeoKeyWords>();
		if (id!=null) {
			PhoneSeoKeyWords phoneSeoKeyWords=phoneSeoKeyWordService.queryPhoneSeoKeyWordById(id);
			list.add(phoneSeoKeyWords);
			page.setRecords(list);
		}
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView queryList(Map<String, Object>out,PageDto<PhoneSeoKeyWords> page, PhoneSeoKeyWords phoneSeoKeyWords) throws IOException{
		page.setSort("id");
		page.setDir("desc");
		page=phoneSeoKeyWordService.queryList(page, phoneSeoKeyWords);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView updateSeoKeyWordContent(Map<String, Object>out,PhoneSeoKeyWords phoneSeoKeyWords) throws IOException{

		ExtResult result=new ExtResult();
		String msg="保存失败";
		if(phoneSeoKeyWords!=null && phoneSeoKeyWords.getId()==null){
			if (StringUtils.isNotEmpty(phoneSeoKeyWords.getTitle())&& StringUtils.isNotEmpty(phoneSeoKeyWords.getPinYin())) {
				
				Integer i=phoneSeoKeyWordService.insert(phoneSeoKeyWords);
				if (i!=null&&i.intValue()>0) {
					 msg="保存成功";
					result.setSuccess(true);
				}else if (i!=null&&i.intValue()==-1) {
					 msg="已存在相同的拼音";
					result.setSuccess(false);
				}
			}
		}else {
			Integer i= phoneSeoKeyWordService.updateSeoKeyWord(phoneSeoKeyWords);
			
			if (i!=null && i.intValue()==1) {
				 msg="保存成功";
				
				result.setSuccess(true);
			}else if (i!=null && i.intValue()==2) {
				 msg="已存在相同的拼音";
				
				result.setSuccess(false);
			}
		}
		result.setData(msg);
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView delSeoKeyWords(Map<String, Object>out,Integer id) throws IOException{
		ExtResult result=new ExtResult();
		if(id!=null&&id.intValue()>0){
			Integer i=phoneSeoKeyWordService.delSeoKeyWordsById(id);
			if(i!=null&&i.intValue()>0){
				result.setSuccess(true);
				result.setData("删除成功");
			}
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView monthCallFee(Map<String, Object>out, HttpServletRequest request){
		return null	;
	}
	@RequestMapping
	public ModelAndView queryPhoneCallFee(Map<String, Object>out, HttpServletRequest request,PageDto<PhoneDto>page,String from,String to) throws ParseException, IOException{
		Date froms = null;
		Date tos = null;
		if (from != "" && to != "" && from != null && to != null) {
			from = from.replace("T", " ");
			to = to.replace("T", " ");
			froms = DateUtil.getDate(from, "yyyy-MM-dd HH:mm:ss");
			tos = DateUtil.getDate(to, "yyyy-MM-dd HH:mm:ss");
		}
		page=phoneService.pagePhoneCallFee(page, froms, tos);
		return printJson(page, out);
	}

	/**
	 * 400号码更换后 ，话单一并拉过去
	 */
	@RequestMapping
	public ModelAndView changeLog(Map<String, Object> out){
		return new ModelAndView();
	}

	/**
	 * 检索更改日志
	 */
	@RequestMapping
	public ModelAndView queryChangeLog(Map<String, Object> out,PhoneNumberChangeLog phoneNumberChangeLog ,PageDto<PhoneNumberChangeLog> page) throws IOException{
		page = phoneNumberChangeLogService.pageByAdmin(phoneNumberChangeLog, page);
		return printJson(page, out);
	}

	/**
	 * 启动一条拉话单任务
	 */
	@RequestMapping
	public ModelAndView startChange(Map<String, Object> out,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = phoneNumberChangeLogService.start(id);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 新增一条拉话单任务
	 */
	@RequestMapping
	public ModelAndView addNumberChange(HttpServletRequest request,Map<String, Object> out,String from ,String to) throws IOException{
		ExtResult result = new ExtResult();
		SessionUser user = getCachedUser(request);
		Integer i = phoneNumberChangeLogService.insert(from, to, user.getAccount());
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

}
