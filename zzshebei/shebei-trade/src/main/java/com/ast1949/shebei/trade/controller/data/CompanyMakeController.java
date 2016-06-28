/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-7-24 上午11:05:29
 */
package com.ast1949.shebei.trade.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedHashMap;
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

import com.ast1949.shebei.domain.CategoryProducts;
import com.ast1949.shebei.dto.data.CompanyMakeMap;
import com.ast1949.shebei.service.CategoryProductsService;
import com.ast1949.shebei.service.DataGatherService;
import com.zz91.util.lang.StringUtils;

@Controller
public class CompanyMakeController {

	@Resource
	private DataGatherService dataGatherService;
	@Resource
	private CategoryProductsService categoryProductsService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView  initImpt(Map<String, Object> out,HttpServletRequest request){
		List<CategoryProducts> list=categoryProductsService.queryAllCategorys((short)0, "1000");
		out.put("list", list);
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, CompanyMakeMap compMap){
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptCompanys(request, compMap);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		return null;
	}
	
	private Map<String, String> imptCompanys(HttpServletRequest request, CompanyMakeMap compMap){
		
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		MultipartFile file = multipartRequest.getFile("excel");
		
		Map<String, String> errorInfo=new LinkedHashMap<String, String>();
		
		try {
				InputStream in=new BufferedInputStream(file.getInputStream());
				HSSFWorkbook wb=new HSSFWorkbook(in);
				HSSFSheet sheet=wb.getSheetAt(0);
				
				int f=sheet.getFirstRowNum();
				int l=sheet.getLastRowNum();
				in.close();
				HSSFRow row;
				long seed=System.currentTimeMillis();
				Date dt=dataGatherService.queryCompanyMaxGetShow();
				if (dt!=null && dt.getTime()>seed){
					seed = dt.getTime();   // dt.getTime/1000得到秒数，Date类型的getTime()返回毫秒数
				}
				
				for(int i=f+1;i<=l;i++){
					row = sheet.getRow(i);
					if(row==null){
						continue;
					}
					
					String account=null;
					if (row.getCell(compMap.getAccount())!=null){
						try {
							Double d=row.getCell(compMap.getName()).getNumericCellValue();
							NumberFormat format = new DecimalFormat("#");
							account=format.format(d);
						} catch (Exception e) {
							account=row.getCell(compMap.getAccount()).getRichStringCellValue().getString();
						}
					}
					
					if (StringUtils.isEmpty(account)){
						continue;
					}
					
					if (StringUtils.isNotEmpty(account)){
						long nseed=dataGatherService.createCompanys(compMap, account,row, seed);
						if(nseed>0){
							seed=nseed;
							errorInfo.put(row.getCell(0).getNumericCellValue()+" "+account, "success");
						}else{
							errorInfo.put(row.getCell(0).getNumericCellValue()+" "+account, "error");
						}
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return errorInfo;
	}
}
