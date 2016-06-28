package com.zz91.crm.controller.sale;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.controller.BaseController;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.CommCompanyDto;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmCompanyDto;
import com.zz91.crm.dto.ExtResult;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.service.CrmCompanyService;
import com.zz91.crm.service.CrmSaleCompService;
import com.zz91.crm.service.ParamService;
import com.zz91.crm.service.SysLogService;
import com.zz91.crm.util.LogConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2012-1-9
 */
@Controller
public class CommonController extends BaseController {
	
	@Resource
	private ParamService paramService;
	@Resource
	private CrmCompanyService crmCompanyService;
	@Resource
	private CrmSaleCompService crmSaleCompService;
	@Resource
	private SysLogService sysLogService;
	
	final static String API_HOST="http://huanbaoadmin.zz91.com:8081/ep-admin/api";
	
	//搜索公司(公海和全部客户的搜索)
	@RequestMapping
	public ModelAndView searchComp(Map<String, Object> out, HttpServletRequest request,CompanySearchDto searchdto,
			PageDto<CommCompanyDto> page,String sr,Integer sortMode){
		
		if (CrmCompanyDao.CTYPE_PUBLIC.equals(searchdto.getCtype()) && !isAbleSelect()) {
			return printJson(null, out);
		} else {
			 crmCompanyService.timeHandle(searchdto);
			 crmCompanyService.sortByField(null, page, sr, sortMode);
			if (searchdto.getSaleStatus()!=null){
				if (searchdto.getSaleStatus()==2){
					searchdto.setSaleStatus(null);
				}
				searchdto.setSaleStatus(searchdto.getSaleStatus());
			}else {
				searchdto.setSaleStatus(CrmCompanyDao.SALE_STATUS_TRUE);
			}
			if (searchdto.getStatus()!=null){
				if (searchdto.getStatus()==2){
					searchdto.setStatus(null);
				}
				searchdto.setStatus(searchdto.getStatus());
			}
			if (searchdto.getRepeatId()!=null){
				if(searchdto.getRepeatId()==2){
					searchdto.setRepeatId(null);
				}
			}else {
				searchdto.setRepeatId(0);
			}
			if (searchdto.getStar()!=null && searchdto.getStar()==6){
				page=crmCompanyService.searchOnceFourOrFive(searchdto, page);
			}else{
				page=crmCompanyService.searchCommCompany(searchdto, page);
			}
			return printJson(page, out);
		}
	}
	
	//判断是否在公海挑选时间内
	private boolean isAbleSelect() {
		String abletimes = paramService.queryValueByKey("data_input_config", "crm_time");
		if (StringUtils.isNotEmpty(abletimes)) {
			String times[] = abletimes.split(";");
			String nowtime = DateUtil.toString(new Date(), "HH:mm");
			for (String valtime:times) {
				String valtimes[] = valtime.split("-");
				if (nowtime.compareTo(valtimes[0]) > 0 && nowtime.compareTo(valtimes[1]) < 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	//公海挑选客户
	@RequestMapping
	public ModelAndView seaComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_PUBLIC);
		out.put("search", search);
		return null;
	}
	//查看所有客户
	@RequestMapping
	public ModelAndView allCompList(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setSaleStatus((short)2);
		search.setRepeatId(2);
		out.put("search", search);
		return null;
	}
	
	public void setLog(HttpServletRequest request,SysLog log){
		String ip=HttpUtils.getInstance().getIpAddr(request);
		String account=getCachedUser(request).getAccount();
		String deptCode=getCachedUser(request).getDeptCode();
		String name=getCachedUser(request).getName();
		log.setSaleAccount(account);
		log.setSaleDept(deptCode);
		log.setSaleIp(ip);
		log.setSaleName(name);
	}
	
	//放入个人库
	@RequestMapping
	public ModelAndView createCrmSaleComp(Map<String, Object> out, HttpServletRequest request,
			Integer id,long gmtContact,long gmtNextContact) throws ParseException{
		Date contact = null;
		Date nextContact = null;
		if(gmtContact!=0){
			contact=DateUtil.getDate(new Date(gmtContact), "yyyy-MM-dd HH:mm:ss");
		}
		if (gmtNextContact!=0){
			nextContact=DateUtil.getDate(new Date(gmtNextContact), "yyyy-MM-dd HH:mm:ss");
		}
		ExtResult result= new ExtResult();
		CrmCompany comp=crmCompanyService.queryCrmCompById(id);
		
		if (comp.getCtype()==2) {
			if(isAbleSelect()){
				CrmSaleComp(id, request, contact, nextContact, result);
			}else {
				result.setSuccess(false);
				result.setData("已过公海挑选时间，挑选失败！");
			}
		}
		if (comp.getCtype()==5) {
				CrmSaleComp(id, request, contact, nextContact, result);
		}
		
		//如果是非销售 更新sale_status=0 (代表销售客户)
		if (comp.getSaleStatus()==1){
			Integer count=crmCompanyService.updateSaleStatusById(id, CrmCompanyDao.SALE_STATUS_TRUE);
			if (count!=null && count.intValue()>0){
				CrmSaleComp(id, request, contact, nextContact, result);
			}
		}
		return printJson(result, out);
	}
	
	public void CrmSaleComp(Integer id,HttpServletRequest request,Date contact,Date nextContact,ExtResult result){
		CrmSaleComp crmSaleComp = new CrmSaleComp();
		crmSaleComp.setCid(id);
		crmSaleComp.setStatus(CrmSaleCompService.STATUS_ABLE);
		crmSaleComp.setSaleType(CrmSaleCompDao.SALE_TYPE1);
		crmSaleComp.setSaleAccount(getCachedUser(request).getAccount());
		crmSaleComp.setSaleDept(getCachedUser(request).getDeptCode());
		crmSaleComp.setSaleName(getCachedUser(request).getName());
		crmSaleComp.setCompanyType(CrmSaleCompService.COMPANY_TYPE_NORMAL);
		crmSaleComp.setGmtContact(contact);
		crmSaleComp.setGmtNextContact(nextContact);
		Integer i=crmSaleCompService.createCrmSaleComp(crmSaleComp,(short)1);
		if (i!=null && i.intValue()>0) {
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.PUT_PERSON_BOX);
			setLog(request, log);
			log.setDetails("放入个人库！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setData("操作成功！");
		}
	}
	
	//未激活客户
	@RequestMapping
	public ModelAndView disActiveComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_DISACTIVE);
		search.setStatus((short)2);
		out.put("search", search);
		return null;
	}
	
	//已审核通过废品池客户
	@RequestMapping
	public ModelAndView checkSucWasteComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setCtype(CrmCompanyDao.CTYPE_DISABLE);
		search.setDisableStatus(CrmSaleCompService.WASTE_STATUS);
		search.setStatus(CrmSaleCompService.STATUS_DISABLE);
		out.put("search", search);
		return null;
	}
	
	//放入公海(针对未激活客户,只更新所在库)
	@RequestMapping
	public ModelAndView updateAutoBlack(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result=new ExtResult();
		Integer i=crmCompanyService.updateCtypeById(id, CrmCompanyDao.CTYPE_PUBLIC);
		if (i!=null && i.intValue()>0){
			SysLog log=new SysLog();
			log.setTargetId(id);
			log.setOperation(LogConst.PUT_PUBLIC_SEA);
			setLog(request, log);
			log.setDetails("放入公海！");
			sysLogService.createSysLog(log);
			result.setSuccess(true);
		}
		else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}
	
	//非销售客户
	@RequestMapping
	public ModelAndView notSaleComp(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setSaleStatus(CrmCompanyDao.SALE_STATUS_FALSE);
		out.put("search", search);
		return null;
	}
	
	//重复客户
	@RequestMapping
	public ModelAndView repeatComp(Map<String, Object> out, HttpServletRequest request){
		String deptCode=getCachedUser(request).getDeptCode();
		if (deptCode.length()==8 || deptCode.length()==12 || deptCode.length()==16){
			out.put("put", 1);
		}
		CompanySearchDto search = new CompanySearchDto();
		search.setRepeatId(1);
		out.put("search", search);
		return null;
	}
	
	//曾是4星,5星的客户(6判定值,为6时查询)
	@RequestMapping
	public ModelAndView fourOrFiveByOldStar(Map<String, Object> out, HttpServletRequest request){
		CompanySearchDto search = new CompanySearchDto();
		search.setStar((short)6);
		out.put("search", search);
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping
	public ModelAndView exportData(Map<String, Object> out, HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String filename="crm导出表格";
		HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(filename);
        
        HSSFRow row=null;
        HSSFCell cell=null;
        
		//查询的登录次数>=1 废品池客户除外 非销售客户除外
		List<CrmCompanyDto> list=crmCompanyService.querySimplyComp();
		CrmCompanyDto dto=null;
        //列头
        row = sheet.createRow((short)(0));
        
        cell = row.createCell((short)(0));
        cell.setCellValue("会员账户");
        cell = row.createCell((short)(1));    
        cell.setCellValue("会员密码");
        cell = row.createCell((short)(2));    
        cell.setCellValue("公司名称");
        cell = row.createCell((short)(3));    
        cell.setCellValue("所属行业");
        cell = row.createCell((short)(4));    
        cell.setCellValue("联系人");
        cell = row.createCell((short)(5));    
        cell.setCellValue("电话");
        cell = row.createCell((short)(6));    
        cell.setCellValue("手机");
        cell = row.createCell((short)(7));    
        cell.setCellValue("传真");
        cell = row.createCell((short)(8));    
        cell.setCellValue("地址");
        cell = row.createCell((short)(9));    
        cell.setCellValue("登录次数");
        cell = row.createCell((short)(10));    
        cell.setCellValue("最近登录时间");
        cell = row.createCell((short)(11));    
        cell.setCellValue("注册时间");
        cell = row.createCell((short)(12));    
        cell.setCellValue("星级");
        cell = row.createCell((short)(13));    
        cell.setCellValue("所在库");
        cell = row.createCell((short)(14));    
        cell.setCellValue("销售归属");
        
        short length=(short)list.size();
        
        for(int i=1;i<length;i++){
            dto=(CrmCompanyDto)list.get(i);
            
            row = sheet.createRow((short)i);
                
            cell = row.createCell((short)(0));
            cell.setCellValue(dto.getAccount());
            
            String responseText="";
            String url=API_HOST+"/crm/queryPwdByCid.htm?cid="+dto.getCid();
            try {
            	responseText = HttpUtils.getInstance().httpGet(url, HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
//				throw new Exception(e.getMessage()+" URL:"+url+"  account:"+dto.getAccount());
				continue;//如果http连接超时,跳过本次循环,进入下次循环
			}
			
			if(StringUtils.isEmpty(responseText) || !responseText.startsWith("{")){
				break;
			}
			
			String pwd="";
			if (StringUtils.isNotEmpty(responseText)){
				JSONObject object = JSONObject.fromObject(responseText);
				pwd = object.getString("pwd");
			}else {
				pwd="空";
			}
            
            cell = row.createCell((short)(1));    
            cell.setCellValue(pwd);
            cell = row.createCell((short)(2));    
            cell.setCellValue(dto.getCname());
            cell = row.createCell((short)(3));    
            cell.setCellValue(dto.getIndustryName());
            cell = row.createCell((short)(4));    
            cell.setCellValue(dto.getName());
            cell = row.createCell((short)(5));    
            cell.setCellValue(dto.getPhone());
            cell = row.createCell((short)(6));    
            cell.setCellValue(dto.getMobile());
            cell = row.createCell((short)(7));    
            cell.setCellValue(dto.getFax());
            cell = row.createCell((short)(8));    
            cell.setCellValue(dto.getAddress());
            cell = row.createCell((short)(9));    
            cell.setCellValue(dto.getLoginCount());
            cell = row.createCell((short)(10));    
            cell.setCellValue(DateUtil.toString(dto.getGmtLogin(), "yyyy-MM-dd HH:mm:ss"));
            cell = row.createCell((short)(11));    
            cell.setCellValue(DateUtil.toString(dto.getGmtRegister(), "yyyy-MM-dd HH:mm:ss"));
            cell = row.createCell((short)(12));    
            cell.setCellValue(dto.getStar());
            cell = row.createCell((short)(13));    
            cell.setCellValue(dto.getCtype());
            cell = row.createCell((short)(14));    
            cell.setCellValue(dto.getSaleName());  
            
        }    
        
        //写文件
        FileOutputStream fileOut = new FileOutputStream("/root/"+filename+DateUtil.toString(new Date(), "yyyyMMdd")+".xls");
        wb.write(fileOut);
        fileOut.close(); 
        
        ExtResult result = new ExtResult();
        result.setSuccess(true);
		return printJson(result, out);
	}
	
}
