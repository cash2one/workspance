/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-12-21 上午09:20:05
 */
package com.zz91.crm.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.controller.BaseController;
import com.zz91.crm.domain.Param;
import com.zz91.crm.dto.data.CrmCompanyMakeMap;
import com.zz91.crm.service.DataGatherService;
import com.zz91.crm.service.ParamService;

@Controller
public class DataImportController extends BaseController {
	
	@Resource
	private ParamService paramService;
	
	@Resource
	private DataGatherService dataGatherService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView initImpt(HttpServletRequest request, Map<String, Object> out){
		List<Param> industryList=paramService.queryParamByTypes("comp_industry", 1);
		List<Param> bussinessList=paramService.queryParamByTypes("company_industry_code", 1);
		out.put("industryList", industryList);
		out.put("bussinessList", bussinessList);
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, CrmCompanyMakeMap compMap){
		
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptComp(request, compMap);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		
		return null;
	}
	
	private Map<String, String> imptComp(HttpServletRequest request, CrmCompanyMakeMap compMap){
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		MultipartFile file = multipartRequest.getFile("excel");
		
		Map<String, String> errorInfo=new HashMap<String, String>();
		InputStream in=null;
		try {
			in=new BufferedInputStream(file.getInputStream());
			
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			
			int f=sheet.getFirstRowNum();
			int l=sheet.getLastRowNum();
			
			HSSFRow row;
			
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				
				String cname="";
				if (row.getCell(compMap.getCname())!=null){
					cname=row.getCell(compMap.getCname()).getRichStringCellValue().getString();
				}
					
				boolean  flag= dataGatherService.createCrmCompany(compMap, row,getCachedUser(request).getAccount());
				if (flag){
					errorInfo.put(cname+" ","-------success");
				}else{
					errorInfo.put(cname,"-------error");
					continue;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return errorInfo;
	}
}
