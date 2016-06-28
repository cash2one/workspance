package com.zz91.crm.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.functors.IfClosure;
import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.controller.BaseController;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.Param;
import com.zz91.crm.domain.ParamType;
import com.zz91.crm.dto.CrmSaleDataDto;
import com.zz91.crm.dto.ExtResult;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.crm.service.ParamService;
import com.zz91.crm.service.ParamTypeService;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class ParamController extends BaseController{
	
	private static final String API_HOST="http://huanbaoadmin.zz91.com:8080/ep-admin/api";
	private static final String PARAM_TYPES_ACCOUNT="automatically_assigned_account";
	private static final String DATA_INPUT_CONFIG="data_input_config";
	private static final String HUANBAO_DEPT_CODE="huanbao_dept_code";
	
	@Resource
	private ParamService paramService;
	@Resource
	private ParamTypeService paramTypeService;
	@Resource
	private CrmSaleCompService crmSaleCompService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView listParamByTypes(Map<String, Object> out,HttpServletRequest request,String types){
		PageDto<Param> page = new PageDto<Param>();
		page.setRecords(paramService.queryParamByTypes(types, null));
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView listParamType(Map<String, Object> out,HttpServletRequest request){
		PageDto<ParamType> page = new PageDto<ParamType>();
		page.setRecords(paramTypeService.queryAllParamType());
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView createParamType(Map<String, Object> out,HttpServletRequest request,ParamType paramType){
		ExtResult result = new ExtResult();
		if (paramTypeService.isExistByKey(paramType.getKey())){
				result.setSuccess(false);
		}else {
			Integer i=paramTypeService.createParamType(paramType);
			if (i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateParamType(Map<String, Object> out,HttpServletRequest request,ParamType paramType){
		ExtResult result=new ExtResult();
		String oldKey = paramTypeService.queryKeyById(paramType.getId());
		if (oldKey != null && !oldKey.equals(paramType.getKey()) && paramTypeService.isExistByKey(paramType.getKey())) {
			result.setSuccess(false);
		} else {
			if (oldKey != null && oldKey.equals(paramType.getKey())) {
				paramType.setKey(null);
			}
			Integer i=paramTypeService.updateParamType(paramType,oldKey);
			if (i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteParamType(Map<String, Object> out,HttpServletRequest request,String key){
		ExtResult result=new ExtResult();
		Integer i=paramTypeService.deleteParamTypeByKey(key);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createParam(Map<String, Object> out,HttpServletRequest request,Param param){
		ExtResult result = new ExtResult();
		if (paramService.isExistByKey(param.getTypes(), param.getKey())) {
			result.setSuccess(false);
		} else {
			Integer i=paramService.createParam(param);
			if (i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateParam(Map<String, Object> out,HttpServletRequest request,Param param){
		ExtResult result=new ExtResult();
		String oldKey=paramService.queryKeyById(param.getId());
		if (oldKey != null && !oldKey.equals(param.getKey()) && paramService.isExistByKey(param.getTypes(), param.getKey())) {
			result.setSuccess(false);
		} else {
			if (oldKey != null && oldKey.equals(param.getKey())) {
				param.setKey(null);
			}
			Integer i=paramService.updateParam(param);
			if (i!=null && i.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteParam(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result=new ExtResult();
		Integer i=paramService.deleteParamById(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView updateWWWParam(Map<String, Object> out,HttpServletRequest request,String types){
		ExtResult result=new ExtResult();
		if(StringUtils.isNotEmpty(types)){
			int resultInteger = 0;
			if (PARAM_TYPES_ACCOUNT.equals(types)) {
				String dept = paramService.queryValueByKey(DATA_INPUT_CONFIG, HUANBAO_DEPT_CODE);
				List<JSONObject> list = (List<JSONObject>) AuthUtils.getInstance().queryStaffByDept(dept);
				for (JSONObject object:list) {
					Param param = new Param();
					param.setKey(object.getString("account"));
					param.setName(object.getString("name"));
					param.setValue(object.getString("deptCode"));
					param.setTypes(types);
					if(paramService.isExistByKey(param.getTypes(), param.getKey())) {
						if(paramService.updateParamByKey(param) > 0){
							resultInteger++;
						}
					} else {
						if(paramService.createParam(param)>0){
							resultInteger++;
						}
					}
				}
			} else {
				//与外网参数表同步
				String responseText = null;
				try {
					responseText = HttpUtils.getInstance().httpGet(API_HOST+"/params.htm?types="+types, HttpUtils.CHARSET_UTF8);
				} catch (HttpException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONArray jsonarray=JSONArray.fromObject(responseText);
				
				for (Iterator iter = jsonarray.iterator(); iter.hasNext();) {
					JSONObject object = (JSONObject) iter.next();
					Param param = new Param();
					param.setKey(object.getString("key"));
					param.setName(object.getString("name"));
					param.setTypes(object.getString("types"));
					param.setValue(object.getString("value"));
					if(paramService.isExistByKey(param.getTypes(), param.getKey())) {
						if(paramService.updateParamByKey(param) > 0){
							resultInteger++;
						}
					} else {
						if(paramService.createParam(param)>0){
							resultInteger++;
						}
					}
				}
			}
			if (resultInteger > 0) {
				result.setData(resultInteger);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryParamByTypes(Map<String, Object> out,HttpServletRequest request,String types){
		List<Param> list=paramService.queryParamByTypes(types, null);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryPersons(Map<String, Object> out,HttpServletRequest request){
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		Map<String, String> m = new HashMap<String, String>();
		m.put("account", "");
		m.put("name", "全部");
		list.add(m);
		
		List<JSONObject> list1=AuthUtils.getInstance().queryStaffByDept("");
		//查询全部人员信息
		boolean iscs=false;
		for (JSONObject object : list1) {
			Map<String, String> m0 = new HashMap<String, String>();
			m0.put("account", object.getString("account"));
			m0.put("name", object.getString("name"));
			m0.put("deptCode", object.getString("deptCode"));
			list.add(m0);
			if(getCachedUser(request).getAccount().equals(object.getString("account"))){
				iscs=true;
			}
		}
		
		if(!iscs){
			Map<String, String> m0 = new HashMap<String, String>();
			m0.put("account", getCachedUser(request).getAccount());
			m0.put("name", getCachedUser(request).getName());
			list.add(m0);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView updateDeptCode(Map<String, Object> out,	HttpServletRequest request,Integer s,Integer f){
		List<Param> list =paramService.queryParamByTypes("crm_use_dept", 1);
		out.put("list", list);
		out.put("sCount", s);
		out.put("fCount", f);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doUpdateDeptCode(Map<String, Object> out,HttpServletRequest request,String dc){
		List<JSONObject> list = (List<JSONObject>) AuthUtils.getInstance().queryStaffByDept(dc);
		Integer sucCount=0;
		Integer failCount=0;
		for (JSONObject object:list) {
			//数据库查询人员及部门信息 存在记录继续执行
			CrmSaleDataDto account=crmSaleCompService.querySaleNameAndSaleDept(object.getString("account"));
			
			if (account==null){
				continue;
			}
			
			//部门信息对比 部门编号相同,不更新 编号不同,进行数据更新 
			if (account.getSaleDept().equals(object.getString("deptCode"))){
				continue;
			}else{
			//更新status=1的账户人员部门
				Integer count=crmSaleCompService.updateSaleDept(object.getString("account"),account.getSaleDept(),object.getString("deptCode"));
				if (count>0){
					sucCount++;
				}
				else {
					failCount++;
				}
			}
		}
		return new ModelAndView("redirect:updateDeptCode.htm?s="+sucCount+"&f="+failCount);
	}
}
