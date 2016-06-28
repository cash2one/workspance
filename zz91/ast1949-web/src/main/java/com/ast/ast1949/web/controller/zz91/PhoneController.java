package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.InquiryTask;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.InquiryTaskService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-7-2
 */
@Controller
public class PhoneController extends BaseController {

	// 接口私钥：
	private static String apiKey = "a16d751a0e879ca533aeba5f0e985c5e";
	// 用户ID：userkey
	private static String userKey = "11683e186d";
	// 组装string 的map
	private static Map<String, String> map = new TreeMap<String, String>();

	static {
		map.put("userkey", userKey);
	}
	
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

	@RequestMapping
	public void index(Map<String, Object> out) {
		
	}
	
	@RequestMapping
	public ModelAndView query(Map<String, Object>out,Phone phone,PageDto<Phone> page) throws IOException{
		page = phoneService.pageList(phone, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView getBill(Map<String, Object> out,String from,String to) throws ParseException, HttpException, IOException {
		String sign = getSign(DateUtil.getDate(from, "yyyy-MM-dd"), DateUtil.getDate(to, "yyyy-MM-dd"), null, null);
		String str = "&start_time="+from+" 00:00:00&end_time="+to+" 00:00:00&sign="+sign;
		String url = "http://api.maxvox.com.cn/api/pci_pull/getCallByDateTime?userkey="
			+ userKey
			+ str.replaceAll(" ", "%20");
		String result = HttpUtils.getInstance().httpGet(url,HttpUtils.CHARSET_UTF8);
		JSONObject js = JSONObject.fromObject(result);
		JSONArray jsa = JSONArray.fromObject(js.get("result"));
		for (int i=0;i<jsa.size();i++) {
			JSONObject obj = JSONObject.fromObject(jsa.get(i));
			if(!"0".equals(obj.get("state"))){
				MathTool mt = new MathTool();
				PhoneLog phoneLog = new PhoneLog();
				Double f = obj.getDouble("call_fee")/0.15;
				Double fdd = mt.mul( f, 2.8).doubleValue();
				BigDecimal bd = new BigDecimal(fdd);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneLog.setCallFee(bd.toString());
				phoneLog.setCallSn(obj.getString("call_sn"));
				phoneLog.setEndTime(DateUtil.getDate(obj.getString("end_time"), "yyyy-MM-dd HH:mm:ss"));
				phoneLog.setCallerId(obj.getString("call_passid"));
				phoneLog.setStartTime(DateUtil.getDate(obj.getString("start_time"), "yyyy-MM-dd HH:mm:ss"));
				phoneLog.setTel(obj.getString("tel"));
				String ext =obj.getString("ext");
				if(StringUtils.isNotEmpty(ext)){
					phoneLog.setTel(phoneLog.getTel()+"-"+ext);
				}
				phoneLogService.insert(phoneLog);
			}
		}
		return printJs(jsa.toString(), out);
	}
	
	@RequestMapping
	public ModelAndView add(Map<String, Object>out,Phone phone) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = 0;
		if(phone.getId()!=null&&phone.getId()>0){
			i = phoneService.update(phone);
		}else{
			i = phoneService.insert(phone);
		}
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);

	}

	@RequestMapping
	public ModelAndView init(Map<String, Object>out,Integer companyId,Integer id) throws IOException{
		List<Phone> list = new ArrayList<Phone>();
		do {
			if(id==null&&companyId==null){
				break;
			}
			Phone phone = phoneService.queryById(id);
			if(phone==null){
				phone = new Phone();
				if(companyId!=null){
					phone.setCompanyId(companyId);
					phone.setAccount(companyAccountService.queryAccountByCompanyId(companyId).getAccount());
				}
			}

			list.add(phone);

		} while (false);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView deleteById(Integer id,Map<String, Object>out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer i = phoneService.delete(id);
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 
	 *userkey=11683e186d tel=4008676666 ext= start_time=2012-08-01 00:00:00
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
	public void batchInquiry(PageDto<ProductsDto> page,ProductsDO product,Map<String, Object> out,String keyword) throws UnsupportedEncodingException{
		if(StringUtils.isNotEmpty(keyword)){
			out.put("keyword", StringUtils.decryptUrlParameter(keyword));
		}
		out.put("product",product);
		if(StringUtils.isNotEmpty(product.getTitle())){
			product.setTitle(StringUtils.decryptUrlParameter(product.getTitle()));
			out.put("titleEncode", URLEncoder.encode(product.getTitle(), HttpUtils.CHARSET_UTF8));
			out.put("page",productsService.pageLHProductsBySearchEngine(product, null, null, page));
		}
	}
	
	@RequestMapping
	public ModelAndView doSendToTask(String id,Integer companyId,String title,String content,Map<String, Object>out) throws IOException{
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		InquiryTask inquiryTask = new InquiryTask();
		inquiryTask.setCompanyId(companyId);
		inquiryTask.setTargetType(InquiryTaskService.TARGET_TYPE_COMPANY);
		inquiryTask.setTitle(StringUtils.decryptUrlParameter(title));
		content = content.replaceAll("astoand", "&");
		inquiryTask.setContent(StringUtils.decryptUrlParameter(content));
		inquiryTask.setPostStatus("0");
		Integer count = 0;
		for(Integer targetId : ids){
			inquiryTask.setTargetId(targetId);
			Integer i = inquiryTaskService.addNewInquiryTask(inquiryTask);
			if(i>0){
				count++;
			}
		}
		return printJs("alert('提交成功"+count+"条询盘')", out);
	}
	
	@RequestMapping
	public ModelAndView doAllSendToTask(String keyword,Integer companyId,String title,String content,Map<String, Object>out) throws IOException{
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
		page = productsService.pageLHProductsBySearchEngine(product, null, null, page);
		for(ProductsDto dto : page.getRecords()){
			inquiryTask.setTargetId(dto.getProducts().getCompanyId());
			Integer i = inquiryTaskService.addNewInquiryTask(inquiryTask);
			if(i>0){
				count++;
			}
		}
		return printJs("alert('提交成功"+count+"条询盘')", out);
	}

	public static void main(String[] args) throws HttpException, IOException {
		PhoneController a = new PhoneController();
		String sign = a.getSign(new Date(), new Date(), null, null);
		String str = "&start_time=2013-07-02 00:00:00&end_time=2013-07-02 00:02:00&sign="+sign;
		String url = "http://api.maxvox.com.cn/api/pci_pull/getCallByDateTime?userkey="
			+ userKey
			+ str.replaceAll(" ", "%20");
		String result = HttpUtils.getInstance().httpGet(url,HttpUtils.CHARSET_UTF8);
		System.out.println(result);
	}

}
