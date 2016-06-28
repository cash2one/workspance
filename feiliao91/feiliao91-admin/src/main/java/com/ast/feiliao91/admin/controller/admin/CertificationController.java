package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanySearch;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.goods.PictureService;

@Controller
public class CertificationController extends BaseController{
	
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private PictureService pictureService;
	
	/**
	 * 默认页
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView view(Map<String,Object> out){
		return null;
	}
	
	/**
	 * 显示所有的认证信息
	 * @param out
	 * @param page
	 * @param search
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryAllCertification(Map<String,Object> out,PageDto<CompanyDto> page, CompanySearch search) throws IOException{
		search.setCreditType(3);//没有特殊作用，仅仅设置为isNotEmpty
		page = companyInfoService.pageBySearch(page, search);
		return printJson(page, out);
	}
	
	/**
	 * 审核
	 * @param request
	 * @param out
	 * @param ids
	 * @param checkStatus
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateStatus(HttpServletRequest request,Map<String,Object> out,String ids,Integer checkStatus) throws IOException{
		ExtResult result = new ExtResult();
		String s = companyInfoService.updateStatus(ids,checkStatus);
		if(StringUtils.isEmpty(s)){
			result.setSuccess(false);
			result.setData("审核失败");
		}else{
			result.setSuccess(true);
			result.setData("审核成功");
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateDelStatus(HttpServletRequest request,Map<String,Object> out,String ids,Integer checkStatus) throws IOException{
		ExtResult result = new ExtResult();
		String s = companyInfoService.updateStatus(ids,checkStatus);
		if(StringUtils.isEmpty(s)){
			result.setSuccess(false);
			result.setData("删除失败");
		}else{
			result.setSuccess(true);
			result.setData("删除成功");
		}
		return printJson(result, out);
	}
	
	/**
	 * 个体编辑页
	 * @param model
	 * @param id
	 */
	@RequestMapping
	public void editGeTi(Map<String, Object> model,String id){
		model.put("id", id);
		model.put("activeFlg", 0);
	}
	
	/**
	 * 工商编辑页
	 * @param model
	 * @param id
	 */
	@RequestMapping
	public void editGongShang(Map<String, Object> model,String id){
		model.put("id", id);
		model.put("activeFlg", 0);
	}
	
	/**
	 * 初始化个体编辑页
	 * @param id
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView initGeTi(Integer id, Map<String, Object> out,Integer type) throws IOException {
		//将结果保存在list中
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		do {
			// 查询认证信息是否存在
			CompanyInfo companyInfo = companyInfoService.queryInfoByid(id);
			if (companyInfo == null) {
				break;
			}
			JSONObject companyInfoObj = JSONObject.fromObject(companyInfo.getCreditInfo());
			if(companyInfoObj==null){
				break;
			}
			if(companyInfoObj.get("one")==null){
				break;
			}
			//以上保证必有one信息
			String s = companyInfoObj.get("one").toString(); 
			JSONObject oneObj = JSONObject.fromObject(s);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id",id);
			map.put("attestType","个体认证");
			map.put("companyName",oneObj.get("companyName"));
			map.put("companyAddress",oneObj.get("companyAddress"));
			map.put("idCard",oneObj.get("idCard"));
			map.put("name",oneObj.get("name"));
			map.put("maxsex",oneObj.get("maxsex"));
			map.put("code",oneObj.get("code"));
			map.put("phone",oneObj.get("phone"));
			map.put("codePhone",oneObj.get("codePhone"));
			map.put("mobile",oneObj.get("mobile"));
			map.put("operation",oneObj.get("operation"));
			map.put("serviceType",oneObj.get("serviceType"));
			map.put("business",oneObj.get("business"));
			map.put("applicant",oneObj.get("applicant"));
			map.put("picAddress",oneObj.get("picAddress"));
			map.put("picAddressId",oneObj.get("picAddressId"));
			map.put("checkStatus",companyInfo.getCreditStatus());
			list.add(map);
		} while (false);
		return printJson(list, out);
 	}
	
	@RequestMapping
	public ModelAndView initGongShang(Integer id, Map<String, Object> out) throws IOException {
		//将结果保存在list中
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		do {
			// 查询认证信息是否存在
			CompanyInfo companyInfo = companyInfoService.queryInfoByid(id);
			if (companyInfo == null) {
				break;
			}
			JSONObject companyInfoObj = JSONObject.fromObject(companyInfo.getCreditInfo());
			if(companyInfoObj==null){
				break;
			}
			if(companyInfoObj.get("bus")==null){
				break;
			}
			//以上保证必有bus信息
			String s = companyInfoObj.get("bus").toString(); 
			JSONObject busObj = JSONObject.fromObject(s);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id",id);
			map.put("attestType","工商管理注册认证");
			map.put("companyName",busObj.get("companyName"));
			map.put("registerAddress",busObj.get("registerAddress"));//注册地址
			map.put("registerNum",busObj.get("registerNum"));//注册号
			map.put("legal",busObj.get("legal"));// 法人代表
			map.put("registerCapital",busObj.get("registerCapital"));// 注册资本
			map.put("serviceType",busObj.get("serviceType"));// 公司类型
			map.put("establishTimeStr",busObj.get("establishTimeStr"));// 成立时间
			map.put("startTimeStr",busObj.get("startTimeStr"));// 营业期限起始时间
			map.put("endTimeStr",busObj.get("endTimeStr"));// 营业期结束时间
			map.put("organization",busObj.get("organization"));// 登记机关
			map.put("inspectionTimeStr",busObj.get("inspectionTimeStr"));// 年检时间
			map.put("business",busObj.get("business"));// 经营范围
			map.put("applicant",busObj.get("applicant"));// 申请人
			map.put("picAddress",busObj.get("picAddress"));
			map.put("picAddressId",busObj.get("picAddressId"));
			map.put("checkStatus",companyInfo.getCreditStatus());
			list.add(map);
		} while (false);
		return printJson(list, out);
	}
	
	/**
	 * 个体更新
	 * @param model
	 * @param id
	 * @param companyName
	 * @param companyAddress
	 * @param idCard
	 * @param name
	 * @param maxsex
	 * @param code
	 * @param phone
	 * @param codePhone
	 * @param mobile
	 * @param operation
	 * @param serviceType
	 * @param business
	 * @param applicant
	 * @param picAddress
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateGeTi(Map<String, Object> model,
			String id,String companyName,String companyAddress,String idCard,String name,
			String maxsex,String code,String phone,String codePhone,
			String mobile,String operation,String serviceType,String business,String applicant,
			String picAddress,HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		//必须全填
		if (StringUtils.isEmpty(companyName) || StringUtils.isEmpty(companyAddress) || StringUtils.isEmpty(idCard) || StringUtils.isEmpty(name) ||
				StringUtils.isEmpty(maxsex) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(operation) || StringUtils.isEmpty(serviceType)
				|| StringUtils.isEmpty(applicant) || StringUtils.isEmpty(picAddress)) {
			result.setSuccess(false);
			return printJson(result, model);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyName",companyName);
		map.put("companyAddress",companyAddress);
		map.put("idCard",idCard);
		map.put("name",name);
		map.put("maxsex",maxsex);
		map.put("code",code);
		map.put("phone",phone);
		map.put("codePhone",codePhone);
		map.put("mobile",mobile);
		map.put("operation",operation);
		map.put("serviceType",serviceType);
		map.put("business",business);
		map.put("applicant",applicant);
		//处理图片链接
		List<String> picList = getImgStr(picAddress);
		String picStr ="";
		for (String string : picList) {
			picStr=picStr+string+",";
		}
		picStr=picStr.substring(0,picStr.length()-1);
		map.put("picAddress",picStr);
		
		//处理图片链接id
		List<String> picIdList = getImgStrId(picAddress);
		String picIdStr ="";
		for (String string : picIdList) {
			picIdStr=picIdStr+string+",";
		}
		picIdStr=picIdStr.substring(0,picIdStr.length()-1);
		map.put("picAddressId",picIdStr);
		
		JSONObject json = JSONObject.fromObject(map);
		String regit = json.toString();
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(Integer.valueOf(id));
		Map<String, Object> tu = getMap(companyInfo.getCreditInfo());
		if(tu==null){
	    	tu= new HashMap<String, Object>();
	    }
		tu.put("one", regit);
		companyInfo.setCreditInfo(JSONObject.fromObject(tu).toString());
		Integer i = companyInfoService.updateValidate(companyInfo);
		if (i != null && i.intValue()>0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
//		JSONObject busObj = JSONObject.fromObject(map);
//		CompanyInfo companyInfo = companyInfoService.queryInfoByid(Integer.valueOf(id));
//		JSONObject creditInfoObj = JSONObject.fromObject(companyInfo.getCreditInfo());
//		creditInfoObj.put("one",busObj.toString());
//		String creditInfoStr =creditInfoObj.toString();
//		companyInfo.setCreditInfo(creditInfoStr);
//		Integer i = companyInfoService.updateValidate(companyInfo);
//		if (i != null && i.intValue()>0) {
//			result.setSuccess(true);
//		} else {
//			result.setSuccess(false);
//		}
		//仅仅是保存更新
		return printJson(result, model);
	}
	
	
	/**
	 * 
	 * @param model
	 * @param id
	 * @param companyName
	 * @param registerAddress
	 * @param registerNum
	 * @param legal
	 * @param registerCapital
	 * @param serviceType
	 * @param establishTimeStr
	 * @param startTimeStr
	 * @param endTimeStr
	 * @param organization
	 * @param inspectionTimeStr
	 * @param business
	 * @param applicant
	 * @param picAddress
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateGongShang(Map<String, Object> model,
			String id,String companyName,String registerAddress,String registerNum,String legal,String registerCapital,
			String serviceType,String establishTimeStr,String startTimeStr,String endTimeStr,String organization,
			String inspectionTimeStr,String business,String applicant,String picAddress,
			HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		if (StringUtils.isEmpty(companyName) || StringUtils.isEmpty(registerAddress) || StringUtils.isEmpty(registerNum) || StringUtils.isEmpty(legal) 
				|| StringUtils.isEmpty(serviceType) || StringUtils.isEmpty(establishTimeStr) || StringUtils.isEmpty(startTimeStr)
				|| StringUtils.isEmpty(endTimeStr) || StringUtils.isEmpty(organization) 
				|| StringUtils.isEmpty(applicant) || StringUtils.isEmpty(picAddress)) {
			result.setSuccess(false);
			return printJson(result, model);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyName",companyName);
		map.put("registerAddress",registerAddress);
		map.put("registerNum",registerNum);
		map.put("legal",legal);
		map.put("registerCapital",registerCapital);
		map.put("serviceType",serviceType);
		map.put("establishTimeStr",establishTimeStr);
		map.put("startTimeStr",startTimeStr);
		map.put("endTimeStr",endTimeStr);
		map.put("organization",organization);
		map.put("startTimeStr",startTimeStr);
		map.put("inspectionTimeStr",inspectionTimeStr);
		map.put("business",business);
		map.put("applicant",applicant);
		//处理图片链接
		List<String> picList = getImgStr(picAddress);
		String picStr ="";
		for (String string : picList) {
			picStr=picStr+string+",";
		}
		picStr=picStr.substring(0,picStr.length()-1);
		map.put("picAddress",picStr);
		//处理图片链接id
		List<String> picIdList = getImgStrId(picAddress);
		String picIdStr ="";
		for (String string : picIdList) {
			picIdStr=picIdStr+string+",";
		}
		picIdStr=picIdStr.substring(0,picIdStr.length()-1);
		map.put("picAddressId",picIdStr);
		
		JSONObject json = JSONObject.fromObject(map);
		String regit = json.toString();
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(Integer.valueOf(id));
		Map<String, Object> tu = getMap(companyInfo.getCreditInfo());
		if(tu==null){
	    	tu= new HashMap<String, Object>();
	    }
		tu.put("bus", regit);
		companyInfo.setCreditInfo(JSONObject.fromObject(tu).toString());
		Integer i = companyInfoService.updateValidate(companyInfo);
		if (i != null && i.intValue()>0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		//仅仅是保存更新
		result.setSuccess(true);
		return printJson(result, model);
	}
		
	
	
//	@RequestMapping(value = "updateCheckStatus.htm", method = RequestMethod.GET)
//	public ModelAndView updateCheckStatus(Map<String, Object> model, String ids,
//			String checkStatus,String currents, HttpServletRequest request)
//			throws IOException {
//		ExtResult result = new ExtResult();
//		result.setSuccess(true);
//		return printJson(result, model);
//	}
	
	
	// 将json字符串转换为map类型
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}
	//获得picaddress
	public List<String> getImgStr(String html) {
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("src=\"http://img1.taozaisheng.com(.*?)(\"|>|\\s+)").matcher(html);
		while(m.find())
		{
			list.add(m.group(1));
		}
		return list;
	}
	//获得picaddressid
	public List<String> getImgStrId(String html) {
		List<String> list = new ArrayList<String>();
		Matcher n = Pattern.compile("picaddressid=\"(.*?)(\"|>|\\s+)").matcher(html);
		while(n.find())
		{
			list.add(n.group(1));
		}
		return list;
	}
}
