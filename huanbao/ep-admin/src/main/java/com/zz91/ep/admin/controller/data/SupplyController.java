/**
 * 
 */
package com.zz91.ep.admin.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.sys.DataGatherService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.data.CompAccountMakeMap;
import com.zz91.ep.dto.data.CompMakeMap;
import com.zz91.ep.dto.data.TradeSupplyMakeMap;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.StringUtils;

/**
 * 供应信息伪造对应的controller
 * 
 * @return
 */
@Controller
public class SupplyController extends BaseController {
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private CompAccountService compAccountService;
	@Resource 
	private DataGatherService dataGatherService;
	@Resource
	private ParamService paramService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView details(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView save(HttpServletRequest request, Map<String, Object> out, 
			TradeSupply tradeSupply, CompProfile compProfile, CompAccount compAccount, @RequestParam("companyName")String companyName, @RequestParam("telephone")String telephone
			, @RequestParam("categoryName")String categoryName, @RequestParam("provinceName")String provinceName, @RequestParam("areaName")String areaName,@RequestParam("gmtCheckStr") String gmtCheckStr, @RequestParam("gmtPublishStr") String gmtPublishStr){
		
		if(StringUtils.isNotEmpty(gmtCheckStr)){
			try {
				tradeSupply.setGmtCheck(DateUtil.getDate(gmtCheckStr, "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(StringUtils.isNotEmpty(gmtPublishStr)){
			try {
				tradeSupply.setGmtPublish((DateUtil.getDate(gmtPublishStr, "yyyy-MM-dd HH:mm:ss")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		ExtResult result=new ExtResult();
		compProfile.setName(companyName);
		if(StringUtils.isNotEmpty(telephone)) {
			if(telephone.contains("-")) {
				String[] phoneNumber = telephone.split("-");
				compAccount.setPhoneArea(phoneNumber[0]);
				compAccount.setPhone(phoneNumber[1]);
			} else {
				compAccount.setPhone(telephone);
			}
		}	
		compProfile.setProvinceCode(dataGatherService.getProvinceCodeByProvinceName(provinceName));
		compProfile.setAreaCode(dataGatherService.getAreaCodeByAreaName(areaName));
		Integer cid = dataGatherService.saveOrUpdateCompProfile(compProfile, compAccount);
		if(cid>0) {
			tradeSupply.setCid(cid);
			compAccount = compAccountService.getCompAccountByCid(cid);
			tradeSupply.setUid(compAccount.getId());
			tradeSupply.setProvinceCode(dataGatherService.getProvinceCodeByProvinceName(provinceName));
			tradeSupply.setAreaCode(dataGatherService.getAreaCodeByAreaName(areaName));
			tradeSupply.setCategoryCode(dataGatherService.getSupplyCategoryCodeByCategoryName(categoryName));
			tradeSupply.setValidDays((short)30);	//信息有效期默认为1年
			tradeSupply.setTagsSys("");
			tradeSupply.setCheckStatus((short)1);
			tradeSupply.setPropertyQuery("");
			if(tradeSupplyService.createTradeSupply(tradeSupply)>0)
				result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView querySupply(HttpServletRequest request, Map<String, Object> out, PageDto<TradeSupply> page){
		page=tradeSupplyService.pageSupplys(page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView deleteSupply(HttpServletRequest request, Map<String, Object> out, Integer id){
		boolean isDeleteSuccess = dataGatherService.deleteSupplyById(id);
		ExtResult result = new ExtResult();
		if(isDeleteSuccess){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView validateMobile(HttpServletRequest request, Map<String, Object> out, String mobile){
		ExtResult result=new ExtResult();
		if(dataGatherService.validateMobileExist(mobile)) {
			result.setSuccess(true);
		} else result.setSuccess(false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView initImpt(HttpServletRequest request, Map<String, Object> out){
		List<Param> list=paramService.queryParamByType(REGISTER_TYPE);
		out.put("list", list);
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, 
			TradeSupplyMakeMap tradeMap, String sourceFlag, 
			Integer isRight, CompAccountMakeMap accountMap, CompMakeMap compMap,
			Integer compDetails,Integer mainUse){
		compMap.setDetails(compDetails);
		
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptSupply(request, tradeMap, sourceFlag, isRight, accountMap, compMap,mainUse);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		return null;
	}

	private Map<String, String> imptSupply(HttpServletRequest request, TradeSupplyMakeMap tradeMap, 
			String sourceFlag, Integer isRight, CompAccountMakeMap accountMap, CompMakeMap compMap,Integer mainUse){
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		MultipartFile file = multipartRequest.getFile("excel");
		
		if(StringUtils.isEmpty(sourceFlag)){
			sourceFlag="ep";
		}
		
		Map<String, String> errorInfo=new LinkedHashMap<String, String>();
		
		try {
			InputStream in=new BufferedInputStream(file.getInputStream());
			
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			
			int f=sheet.getFirstRowNum();
			int l=sheet.getLastRowNum();
			in.close();
			HSSFRow row;
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				
				String account=null;
				if (row.getCell(accountMap.getAccount())!=null){
					try {
						Double d=row.getCell(accountMap.getAccount()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						account=format.format(d);
					} catch (Exception e) {
						account=row.getCell(accountMap.getAccount()).getRichStringCellValue().toString();
					}
				}
				
				if(StringUtils.isEmpty(account)){
					break;
				}
				
				Integer cid=compAccountService.queryCidByAccount(account);
				Integer uid=0;
				if(cid==null){
					cid=0;
				}else{
					uid=compAccountService.getAccountIdByAccount(account);
					if(uid==null){
						uid=0;
					}
				}
				
				if(uid.intValue()==0 && cid.intValue()==0){
					
					cid = dataGatherService.createCompProfile(compMap, account, row,sourceFlag,mainUse);
					
					if(cid==null){
						//没有保存的信息
						errorInfo.put(row.getCell(accountMap.getAccount()).getRichStringCellValue().toString(), "Error:Company");
						continue;
					}
					
					if(dataGatherService.createCompAccount(cid, account, accountMap, row)){
						uid=compAccountService.getAccountIdByAccount(account);
					}else{
						continue;
					}

				}
				
				if(dataGatherService.createTradeSupply(cid, uid, tradeMap, row,sourceFlag)){
					errorInfo.put(row.getCell(0).getNumericCellValue()+" "+account, "success");
				}else{
					errorInfo.put(row.getCell(0).getNumericCellValue()+" "+account, "error");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return errorInfo;
	}
}
