/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-7-24 上午10:55:00
 */
package com.ast1949.shebei.trade.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedHashMap;
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

import com.ast1949.shebei.dto.data.NewsMakeMap;
import com.ast1949.shebei.dto.data.ProductsMakeMap;
import com.ast1949.shebei.service.CompanyService;
import com.ast1949.shebei.service.DataGatherService;
import com.zz91.util.lang.StringUtils;

@Controller
public class ProductsMakeController {
	
	@Resource
	private DataGatherService dataGatherService;
	@Resource
	private CompanyService companyService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView  initImpt(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, ProductsMakeMap productsMap){
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptProducts(request, productsMap);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		return null;
	}
	
	private Map<String, String> imptProducts(HttpServletRequest request, ProductsMakeMap productsMap){
		
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
				Date dt=dataGatherService.queryProductsMaxGmtShow();
				if (dt!=null && dt.getTime()>seed){
					seed = dt.getTime();   // dt.getTime/1000得到秒数，Date类型的getTime()返回毫秒数
				}
				
				for(int i=f+1;i<=l;i++){
					row = sheet.getRow(i);
					if(row==null){
						continue;
					}
					
					String account=null;
					if (row.getCell(productsMap.getAccount())!=null){
						try {
							Double d=row.getCell(productsMap.getAccount()).getNumericCellValue();
							NumberFormat format = new DecimalFormat("#");
							account=format.format(d);
						} catch (Exception e) {
							account=row.getCell(productsMap.getAccount()).getRichStringCellValue().getString();
						}
					}
					
					if (StringUtils.isEmpty(account)){
						continue;
					}
					
					if (StringUtils.isNotEmpty(account)){
						Integer cid=companyService.queryCompIdByAccount(account);
						if (cid!=null && cid>0){
							long nseed=dataGatherService.createProducts(productsMap, cid,row, seed);
							if(nseed>0){
								seed=nseed;
								errorInfo.put(row.getCell(0).getNumericCellValue()+" "+account, "success");
							}else{
								errorInfo.put(row.getCell(0).getNumericCellValue()+" "+account, "error");
							}
						}
					}
					
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return errorInfo;
	}
}
