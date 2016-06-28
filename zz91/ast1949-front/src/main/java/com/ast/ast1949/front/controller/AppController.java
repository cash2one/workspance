/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2013-3-5 上午10:39:18
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.analysis.AnalysisPhoneOptimizationService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.PayOrderService;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

import net.sf.json.JSONObject;

/**
 * 敏感词过滤工具 需要服务器本地有敏感词词库文件 "/usr/data/keylimit/limit"
 * 
 * @author LeiTeng
 * @date 2013-03-7
 * 
 */

@Controller
public class AppController extends BaseController {
	
	@Resource
	private PayOrderService payOrderService;
	@Resource
	private AnalysisPhoneOptimizationService analysisPhoneOptimizationService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyAttestService companyAttestService;
	
	@RequestMapping
	public ModelAndView doSentive(HttpServletResponse response,
			HttpServletRequest request, Map<String, Object> out,
			String pasString) throws Exception {
		// 返回ajax数据结果
		ExtResult result = new ExtResult();
		// 获取传递的参数,通过URLDecoder类的decode方法解决中文乱码问题
		pasString = URLDecoder.decode(pasString, HttpUtils.CHARSET_UTF8);
		// 定义过滤后的字符串
		// String varifiedString =pasString;

		do {
			// 判断参数如果为空,直接跳出循环
			if (StringUtils.isEmpty(pasString)) {
				break;
			}
			// 对非空字符串进行替换
			pasString = SensitiveUtils.getSensitiveValue(pasString, "*");
			result.setData(pasString);
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

	/**
	 * 用户行为分析记录
	 * companyId
	 * vipPv 值为1 表示统计高会流量的标志 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView log(HttpServletRequest request, Map<String, Object> out,Integer companyId,Integer vipPv)throws IOException {

		JSONObject js = new JSONObject();

		SsoUser ssoUser = getCachedUser(request);

		String account = "";
		if (ssoUser != null) {
			account = ssoUser.getAccount();
		}

		String ip = HttpUtils.getInstance().getIpAddr(request);
		String url = request.getHeader("referer");
		//vipPv 值为1 表示统计高会流量的标志  0表示不用统计
		if(vipPv==null){
			vipPv=0;
		}
		js.put("url", url);
		js.put("date", new Date().getTime());
		js.put("account", account);//访问者帐号
		js.put("companyId", companyId);//被访问的公司id
		js.put("vipPv", vipPv);
		
		
		// 交易中心 hex 转码
		String sort = "http://trade.zz91.com/trade/s-";
		String key = "";
		String keyword = "";
		// 判断url是否以sort字符串开头
		if (StringUtils.isNotEmpty(url)&&url.startsWith(sort)) {
			// 截掉sort代表的字符串
			key = url.substring(sort.length());
			Integer i = 0;
			// 获取字符’.‘为止的字符串
			while (key.charAt(i) != '.') {
				i++;
			}
			key = key.substring(0, i);
			// 转换成中文
			CNToHexUtil a = new CNToHexUtil();
			keyword = a.decode(key);
			js.put("keyword", keyword);
		}
		if (StringUtils.isNotEmpty(url)) {
			LogUtil.getInstance().log("admin", "log", ip, js.toString(),"zz91Analysis");
		}
		return new ModelAndView("json");
	}

	@RequestMapping
	public ModelAndView payBack(Map<String, Object>out,HttpServletRequest request,HttpServletResponse response){
		String noOrder = request.getParameter("no_order");
		String dtOrder = request.getParameter("dt_order");
		String nameGoods = request.getParameter("name_goods");
		do {

			SsoUser ssoUser = getCachedUser(request);
			if (ssoUser==null) {
				ssoUser = new SsoUser();
				ssoUser.setCompanyId(0);
			}

			if (StringUtils.isEmpty(noOrder)) {
				break;
			}

			payOrderService.createOrder(noOrder, dtOrder, ssoUser.getCompanyId());
			out.put("noOrder", noOrder);
			out.put("nameGoods", nameGoods);

		} while (false);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView mongoClickPhone(String tel,HttpServletRequest request,Map<String, Object> out) throws IOException, ParseException{
		do {
			if (StringUtils.isEmpty(tel)) {
				break;
			}
			JSONObject jb = new JSONObject();
			jb.put("date", DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			jb.put("time", new Date().getTime());
			jb.put("tel", tel);
			LogUtil.getInstance().mongo("phone_400", "clickToShowNumber", HttpUtils.getInstance().getIpAddr(request), jb.toString());
//			测试是否有数据
//			Map<String, Object> param=new HashMap<String, Object>();
//			String from = ""+DateUtil.getDate(new Date(), "yyyy-MM-dd").getTime();
//			String to = ""+DateUtil.getDate(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd").getTime();
//			param.put("time",LogUtil.getInstance().mgCompare(">=",from,"<=",to));	//逻辑运算符查询
//			JSONObject res = LogUtil.getInstance().readMongo(param, 0, 100);
//			@SuppressWarnings("unchecked")
//			List<JSONObject> list =res.getJSONArray("records");
//			for ( JSONObject obj : list ) {
//				System.out.println(obj);
//			}
		} while (false);
		return printJs("", out);
	}
	
	/**
	 * 记录ppc页面被访问的情况
	 * @param request
	 * @param out
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView logPpc(HttpServletRequest request,Map<String, Object> out,String url) throws IOException{
		do {
			if (StringUtils.isEmpty(url)) {
				break;
			}
			if (url.indexOf("ppc")==-1) {
				break;
			}
			JSONObject jb = new JSONObject();
			jb.put("date", DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			jb.put("time", new Date().getTime());
			jb.put("url", url);
			LogUtil.getInstance().log("phone_400", "access", HttpUtils.getInstance().getIpAddr(request), jb.toString(),"400");// 记录访问页面
			// 存在优化推荐 数据，insert进数据库
//			url = "http://www.zz91.com/ppc/s-7070e7a0b4e7a28ee69699?utm_source=baid&utm_medium=sem&utm_term=pp%E7%A0%B4%E7%A2%8E%E6%96%99&utm_content=%E7%A0%B4%E7%A2%8E%E6%96%99&utm_campaign=%E5%BA%9F%E5%A1%91%E6%96%99";
			if (url.indexOf("utm_source")==-1) {
				break;
			}
			String firstPage = url.substring(0, url.indexOf("?"));
			url = url.substring(url.indexOf("?")+1, url.length());
			String[] urlArray = url.split("\\^and\\^");
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < urlArray.length; i++) {
				String [] keyArray = urlArray[i].split("=");
				if (keyArray.length==2) {
					if (StringUtils.isNotEmpty(keyArray[1])&&!StringUtils.isContainCNChar(keyArray[1])) {
						String deUrl = StringUtils.decryptUrlParameter(keyArray[1]);
						String decodeUrl = URLDecoder.decode(keyArray[1], "utf-8");
						if (StringUtils.isContainCNChar(deUrl)) {
							keyArray[1] = deUrl;
						}else if (StringUtils.isContainCNChar(decodeUrl)) {
							keyArray[1] = decodeUrl;
						}
					}
					map.put(keyArray[0], keyArray[1]);
				}
			}
			//{utm_content=破碎料, utm_medium=sem, utm_campaign=废塑料, utm_source=baid, utm_term=pp破碎料}
			analysisPhoneOptimizationService.createOneRecord(HttpUtils.getInstance().getIpAddr(request), map.get("utm_source").toString(), map.get("utm_term").toString(), map.get("utm_content").toString(), map.get("utm_campaign").toString(),firstPage);
			
		} while (false);
		return printJson(new ExtResult(), out);
	}
	
	/**
	 * 验证zz91是否存在帐号，一期
	 * 后期需要对链接安全性添加控制逻辑，确保数据安全
	 * @param request
	 * @param account
	 * @param password
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView validateAccount(HttpServletRequest request,String account,String password,Map<String, Object> out) throws IOException{
		JSONObject obj = new JSONObject();
		do {
			//验证用户登录信息是否正确
			String result=null;
			try {
				result = companyAccountService.validateUser(account, MD5.encode(password));
			} catch (NoSuchAlgorithmException e1) {
			} catch (UnsupportedEncodingException e1) {
			} catch (AuthorizeException e1) {
			}
			if (StringUtils.isEmpty(result)) {
				break;
			}
			// 存在zz91网站  (邮箱，手机号，公司名称，主营，国家地区，企业简介，认证信息【个人/公司】)
			CompanyAccount ca = companyAccountService.queryAccountByAccount(result);
			if (ca==null||ca.getCompanyId()==null) {
				break;
			}
			Company c = companyService.queryCompanyById(ca.getCompanyId());
			if (c==null) {
				break;
			}
			CompanyAttest cAttest = companyAttestService.queryOneInfoForFeiliao91(c.getId());
			obj.put("email", ca.getEmail());
			obj.put("mobile", ca.getMobile());
			obj.put("name", c.getName());
			obj.put("business", c.getBusiness());
			obj.put("areaCode", c.getAreaCode());
			obj.put("introduce", c.getIntroduction());
			obj.put("attest", JSONObject.fromObject(cAttest));
			obj.put("ip", HttpUtils.getInstance().getIpAddr(request));
		} while (false);
		return printJson(obj, out);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		CompanyAttest ca =new CompanyAttest();
		ca.setCompanyId(1);
		System.out.println(JSONObject.fromObject(ca));
	}

}