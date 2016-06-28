/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.myrc.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.ParseAreaCode;
import com.ast.ast1949.service.price.PriceOfferService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.file.FileUtils;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;


/**
 * @author yuyonghui
 * 
 */
@Controller
public class MycompanypriceController extends BaseController {

	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private ProductsService productsService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private CompanyService companyService;
	@Resource
	private PriceOfferService priceOfferService;
	@Resource
	private MyfavoriteService myfavoriteService;
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DEFAULTS_CLASS = "excelDefaults";  
	private static StringBuffer lsb = new StringBuffer();
	private static int firstColumn;  
	private static int endColumn; 
	private static Integer pageindex = 0; 
	private static Workbook wb;  

	/**
	 * 获取本公司的所有报价分页信息 ，截止有效时间倒排
	 * 
	 * @param page
	 * @param out
	 * @param request
	 * @throws ParseException
	 */

	/**
	 * 初始化areaCode
	 * 
	 * @param companyPriceDO
	 *            不能为空
	 * @param out
	 */
	public void initAreaCode(CompanyPriceDO companyPriceDO,
			Map<String, Object> out) {
		if (companyPriceDO != null) {

			if (companyPriceDO.getCategoryCompanyPriceCode() != null
					&& companyPriceDO.getCategoryCompanyPriceCode().length() == 4) {
				out.put("threeCode",
						companyPriceDO.getCategoryCompanyPriceCode());
			}
			if (companyPriceDO.getCategoryCompanyPriceCode() != null
					&& companyPriceDO.getCategoryCompanyPriceCode().length() == 8) {
				out.put("secondCode",
						companyPriceDO.getCategoryCompanyPriceCode());
				out.put("threeCode",
						companyPriceDO.getCategoryCompanyPriceCode().substring(
								0,
								companyPriceDO.getCategoryCompanyPriceCode()
										.length() - 4));
			}
			if (companyPriceDO.getCategoryCompanyPriceCode() != null
					&& companyPriceDO.getCategoryCompanyPriceCode().length() == 12) {
				out.put("firstCode",
						companyPriceDO.getCategoryCompanyPriceCode());
				out.put("secondCode",
						companyPriceDO.getCategoryCompanyPriceCode().substring(
								0,
								companyPriceDO.getCategoryCompanyPriceCode()
										.length() - 4));
				out.put("threeCode",
						companyPriceDO.getCategoryCompanyPriceCode().substring(
								0,
								companyPriceDO.getCategoryCompanyPriceCode()
										.length() - 8));
			}

			if (companyPriceDO.getAreaCode() != null
					&& companyPriceDO.getAreaCode().length() > 4) {
				out.put("ccode", companyPriceDO.getAreaCode());
				out.put("pcode",
						companyPriceDO.getAreaCode().substring(0,
								companyPriceDO.getAreaCode().length() - 4));
			}
		}
	}

	/********* up old code **********/

	@RequestMapping
	public ModelAndView queryCategoryCompanyPriceByCode(String parentCode,
			Map<String, Object> map) throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode(parentCode);
		return printJson(list, map);

	}

	@RequestMapping
	public ModelAndView createPrice(Integer productId, Integer error,
			HttpServletRequest request, Map<String, Object> out) {
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());

		if (productId != null && productId.intValue() > 0) {
			ProductsDO products = productsService.queryProductsById(productId);
			out.put("products", products);
		}
		out.put("areaCode", getCachedUser(request).getAreaCode());
		out.put("error", error);

		// 查询信息是否完善

		String checkInfo = companyService.validateCompanyInfo(sessionUser);
		out.put("checkInfo", checkInfo);

		return null;
	}

	/****************
	 * 添加报价
	 * 
	 * @param request
	 * @param out
	 * @param companyPrice
	 * @param expired
	 * @return
	 */
	@RequestMapping
	public ModelAndView doCreate(HttpServletRequest request,
			Map<String, Object> out, CompanyPriceDO companyPrice, String expired) {

		if (companyPrice.getRefreshTime() == null) {
			companyPrice.setRefreshTime(new Date());
		}
		if ("-1".equals(expired)) {// 有效期为最大时间
			try {
				companyPrice.setExpiredTime(DateUtil.getDate(AstConst.MAX_TIMT,
						AstConst.DATE_FORMATE_WITH_TIME));
			} catch (ParseException e) {
			}
		} else {
			companyPrice.setExpiredTime(DateUtil.getDateAfterDays(new Date(),
					Integer.valueOf(expired)));
		}

		SsoUser sessionUser = getCachedUser(request);
		companyPrice.setCompanyId(sessionUser.getCompanyId());
		companyPrice.setAccount(sessionUser.getAccount());
		Integer i = companyPriceService.insertCompanyPrice(
				sessionUser.getMembershipCode(), companyPrice);
		//获取ip地址
		String ip=HttpUtils.getInstance().getIpAddr(request);
		if (i > 0) {
			// 添加企业报价的日志信息
			LogUtil.getInstance()
					.log("myrc",
							"myrc-operate",
							ip,
							"{'account':'"
									+ sessionUser.getAccount()
									+ "','operatype_id':'5','pro_id':'"
									+ companyPrice.getProductId()
									+ "','gmt_created':'"
									+ DateUtil
											.toString(new Date(), DATE_FORMAT)
									+ "'}","myrc");
		}
		if (i != null && i.intValue() > 0) {
			return new ModelAndView("redirect:list.htm");
		}
		out.put("error", 1);

		out.put("productId", companyPrice.getProductId());

		return new ModelAndView("redirect:createPrice.htm");

	}

	@RequestMapping
	public ModelAndView updatePrice(HttpServletRequest request,
			Map<String, Object> out, Integer id, Integer error) {
		CompanyPriceDO companyPrice = companyPriceService
				.queryCompanyPriceById(id);
		if (companyPrice == null) {
			out.put("error", 2);
			return null;
		}

		Integer days = 20;
		try {
			days = companyPriceService.queryCompanyPriceValidityTimeById(
					companyPrice.getRefreshTime(),
					companyPrice.getExpiredTime());
		} catch (ParseException e) {
		}
		out.put("days", days);

		out.put("companyPrice", companyPrice);

		out.put("error", error);
		// initAreaCode(companyPrice, out);
		// if(StringUtils.isNumber(companyPrice.getMaxPrice())
		// && Integer.valueOf(companyPrice.getMaxPrice()).intValue()>0){
		// if(StringUtils.isNumber(companyPrice.getMinPrice())
		// && Integer.valueOf(companyPrice.getMinPrice()).intValue()>0){
		// out.put("scropFlag", 1);
		// }
		// }
		return null;
	}

	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request,
			Map<String, Object> out, CompanyPriceDO companyPrice, String expired) {
		SsoUser sessionUser = getCachedUser(request);
		if (companyPrice.getRefreshTime() == null) {
			companyPrice.setRefreshTime(new Date());
		}
		if ("-1".equals(expired)) {// 有效期为最大时间
			try {
				companyPrice.setExpiredTime(DateUtil.getDate(AstConst.MAX_TIMT,
						AstConst.DATE_FORMATE_WITH_TIME));
			} catch (ParseException e) {
			}
		} else {
			companyPrice.setExpiredTime(DateUtil.getDateAfterDays(new Date(),
					Integer.valueOf(expired)));
		}

		// if("10051000".equals(getCachedUser(request).getMembershipCode())){
		// 所有报价一律流入审核状态
		companyPrice.setIsChecked("0");
		// }else{
		// companyPrice.setIsChecked("1");
		// }

		Integer i = companyPriceService.updateCompanyPrice(companyPrice);
		//获取ip地址
	    String ip=HttpUtils.getInstance().getIpAddr(request);
		if (i > 0) {
			// 修改企业报价的日志信息
			LogUtil.getInstance()
					.log("myrc",
							"myrc-operate",
							ip,
							"{'account':'"
									+ sessionUser.getAccount()
									+ "','operatype_id':'6','pro_id':'"
									+ companyPrice.getProductId()
									+ "','gmt_created':'"
									+ DateUtil
											.toString(new Date(), DATE_FORMAT)
									+ "'}","myrc");
		}
		if (i != null && i.intValue() > 0) {
			out.put("error", 0);
			return new ModelAndView("redirect:list.htm");
		}
		out.put("error", 1);
		out.put("id", companyPrice.getId());
		return new ModelAndView("redirect:updatePrice.htm");
	}

	@RequestMapping
	public ModelAndView delete(String ids, HttpServletRequest request,
			Map<String, Object> out) {
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		// 获取account,productId
		CompanyPriceDO cpd = companyPriceService.queryCompanyPriceById(Integer
						.parseInt(ids));
		Integer a = companyPriceService.batchDeleteCompanyPriceById(StringUtils
				.StringToIntegerArray(ids));
		//获取ip地址
		String ip=HttpUtils.getInstance().getIpAddr(request);
		if (a > 0) {
			// 删除企业报价的日志信息
			LogUtil.getInstance()
					.log("myrc",
							"myrc-operate",
							ip,
							"{'account':'"
									+ cpd.getAccount()
									+ "','operatype_id':'7','pro_id':'"
									+ cpd.getProductId()
									+ "','gmt_created':'"
									+ DateUtil
											.toString(new Date(), DATE_FORMAT)
									+ "'}","myrc");

			out.put("error", 0);
		} else {
			out.put("error", 1);
		}
		return new ModelAndView("redirect:list.htm");
	}

	@RequestMapping
	public void list(PageDto<CompanyPriceDtoForMyrc> page, Integer error,
			Map<String, Object> out, HttpServletRequest request)
			throws ParseException {
		SsoUser sessionUser = getCachedUser(request);
		// 查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());

		page = companyPriceService.queryCompanyPriceListByCompanyId(
				sessionUser.getCompanyId(), page);
		List<CompanyPriceDtoForMyrc> companyPriceList = page.getRecords();
		for (CompanyPriceDtoForMyrc companyPrice : companyPriceList) {
			if (StringUtils.isNotEmpty(companyPrice.getAreaCode())) {
				ParseAreaCode parser = new ParseAreaCode();
				parser.parseAreaCode(companyPrice.getAreaCode());
				companyPrice.setCountryName(parser.getContry());
				companyPrice.setProvince(parser.getProvince());
				companyPrice.setCity(parser.getCity());
			}
		}
		out.put("error", error);
		out.put("page", page);
	}
	@RequestMapping
	public ModelAndView priceList(Map<String, Object> out, HttpServletRequest request,PageDto<PriceOffer> page,Integer flag,String keywords) throws Exception{
		if(flag==null){
			flag=0;
		}
		SsoUser sessionUser = getCachedUser(request);
		page.setPageSize(5);
		page.setSort("gmt_created");
		page.setDir("desc");
		if(StringUtils.isNotEmpty(keywords)&&!StringUtils.isContainCNChar(keywords)){
			//解密
			keywords = StringUtils.decryptUrlParameter(keywords);
		}
		if(flag==0){
			//自己的自主报价
			page=priceOfferService.queryOfferByCompanyId(page, sessionUser.getCompanyId(),keywords);
		}else{
			//关注的自主报价
			page=priceOfferService.queryOfferByCompanyIdAndType(page, sessionUser.getCompanyId(), "10091013",keywords); //10091012 10091013
		}
		//关注的自主报价数
		Integer noticeNum=myfavoriteService.countNoticeByCondition(sessionUser.getCompanyId(), "10091013",null);//10091012 10091013
		out.put("noticeNum", noticeNum);
		out.put("page", page);
		out.put("flag", flag);
		out.put("keyword", keywords);
		if(StringUtils.isNotEmpty(keywords)){
			//加密
			keywords=URLEncoder.encode(keywords, HttpUtils.CHARSET_UTF8);
		}
		out.put("keywords", keywords);
		return null;
	}
	@RequestMapping
	public ModelAndView preview(Map<String, Object> out, HttpServletRequest request,Integer id) throws Exception{
		SsoUser sessionUser = getCachedUser(request);
		Company company=companyService.queryCompanyById(sessionUser.getCompanyId());
		if(company!=null&&StringUtils.isNotEmpty(company.getName())){
			out.put("name", company.getName());
		}
		//自主报价的信息
		PriceOffer offer=priceOfferService.queryOfferById(id);
		if(StringUtils.isNotEmpty(offer.getExcelContent())){
			out.put("html", offer.getExcelContent());
		}else{
			out.put("html", "");
		}
		return null;
	}
	@RequestMapping
	public ModelAndView upload(Map<String, Object> out, HttpServletRequest request,PriceOffer offer,Integer id) throws Exception{
		Map<String,String> map=CategoryFacade.getInstance().getChild("2008");//2006 2008
		Map<String,Map<String,String>> mapl=new HashMap<String,Map<String,String>>();
		for(String str:map.keySet()){
			mapl.put(str, CategoryFacade.getInstance().getChild(str));
		}
		out.put("mapl", mapl);
		out.put("map", map);
		if(id!=null&&id>0&&pageindex==0){
			out.put("idx", id);
			pageindex=1;
		}
		if(id==null){
			out.put("idx", 0);
			pageindex=0;
		}
		return null;
	}
	/**
	 * 上传文件到服务器
	 * @param out
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping
	public ModelAndView doUpload(Map<String, Object> out, HttpServletRequest request,PriceOffer offer) throws Exception{
		SsoUser sessionUser = getCachedUser(request);
		MultipartRequest multipartRequest = (MultipartRequest) request;
		//获取上传的文件信息
		MultipartFile file = multipartRequest.getFile("file");
		//获取文件的名称
		String name = file.getOriginalFilename();
		//获取后缀名
		String[] s=name.split("\\.");
		//给上传的文件重新命名
		String filename = String.valueOf(System.currentTimeMillis())+"."+s[s.length-1];
		if(StringUtils.isNotEmpty(name)){
			String destRoot = MvcUpload.getDestRoot();
			String path = destRoot + "/excel/" + MvcUpload.getDateFolder()+"/";
			//创建上传的文件的目录
			FileUtils.makedir(path);
			File upfile = new File(path + filename);
			//文件放到该目录下
			file.transferTo(upfile);
			//将文件的地址存入数据库
			offer.setExcelAddress("/excel/" + MvcUpload.getDateFolder()+"/"+filename);
			offer.setExcelName(name);
			sheet(path + filename);
			offer.setExcelContent(String.valueOf(lsb));
		}
		Integer i=0;
		if(offer.getId()==null){
			offer.setCompanyId(sessionUser.getCompanyId());
		    i=priceOfferService.insertPriceOffer(offer);
			return new ModelAndView("redirect:upload.htm?id="+i);
		}else{
			//将文件的地址存入数据库
			if(StringUtils.isEmpty(offer.getExcelAddress())){
				offer.setExcelAddress("/excel/" + MvcUpload.getDateFolder()+"/"+offer.getExcelName());
			}
			i=priceOfferService.updateOfferById(offer);
			return new ModelAndView("redirect:form.htm?mark="+i+"&id="+offer.getId());
		}
	}
	@RequestMapping
	public ModelAndView form(Map<String, Object> out, HttpServletRequest request,Integer id,Integer mark) throws Exception{
		Map<String,String> map=CategoryFacade.getInstance().getChild("2008");//2006 2008
		Map<String,Map<String,String>> mapl=new HashMap<String,Map<String,String>>();
		for(String str:map.keySet()){
			mapl.put(str, CategoryFacade.getInstance().getChild(str));
		}
		out.put("mapl", mapl);
		out.put("map", map);
		PriceOffer offer=priceOfferService.queryOfferById(id);
		offer.setExcelName(offer.getExcelName());
		out.put("offer", offer);
		if(mark==null){
			mark=0;
		}
		out.put("mark", mark);
		return null;
	}
	@RequestMapping
	public ModelAndView post_suc(Map<String, Object> out, HttpServletRequest request,Integer mark) throws Exception{
		return null;
	}
	@RequestMapping
	public ModelAndView edit_suc(Map<String, Object> out, HttpServletRequest request,Integer id) throws Exception{
		out.put("mark", id);
		return null;
	}
	@RequestMapping
	public ModelAndView del(Map<String, Object> out, HttpServletRequest request,Integer id) throws Exception{
		out.put("mark", id);
		return null;
	}
	@RequestMapping
	public ModelAndView follow_cancel(Map<String, Object> out, HttpServletRequest request,Integer mark,Integer id) throws Exception{
		SsoUser sessionUser = getCachedUser(request);
		Integer mk=0;
		if(mark!=null){
			mk=myfavoriteService.deleteCollection(sessionUser.getCompanyId(), "10091013", id);//10091012 10091013
		}
		out.put("mark", mark);
		out.put("mk", mk);
		out.put("mid", id);
		return null;
	}
	@RequestMapping
	public ModelAndView doDel(Map<String, Object> out, HttpServletRequest request,Integer id) throws Exception{
		Map<String,Integer> map=new HashMap<String,Integer>();
		Integer delId=priceOfferService.updateIsDelByid(id, 1);
		map.put("delId", delId);
		return printJson(map,out);
	}
	@RequestMapping
	public void downloadFile(HttpServletResponse response,Integer id)throws IOException {
		do {
			if(id==null){
				break;
			}
			PriceOffer offer=priceOfferService.queryOfferById(id);
			// 下载次数 +1
			priceOfferService.updateDownloadNumById(offer.getId(), offer.getDownloadNum()+1);
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			if(offer.getExcelAddress().contains(".xsl")){
				response.setContentType("application/ms-excel");
			}
			// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
			response.setHeader("Content-Disposition", "attachment;fileName="+new String(offer.getExcelName().getBytes("utf-8"), "ISO8859-1" ));
			OutputStream out;
			// 通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
			URL url=new URL("http://img1.zz91.com"+offer.getExcelAddress());
			try{
				InputStream is=url.openStream(); 
				out=response.getOutputStream();
				byte bf[]=new byte[1024];
				int length=0;
				while((length=is.read(bf, 0, 1024))!=-1){
					out.write(bf, 0, length);
				}
				is.close();
				out.close();
				out.flush();
			}catch(FileNotFoundException f){
				String s="文件不存在！";
				ByteArrayInputStream is=new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
				out=response.getOutputStream();
				Integer length=is.read();
				out.write(length);
				out.close();
				out.flush();
			}
		} while (false);
	}
	
	 public void printSheet(Sheet sheet) throws IOException {  
	        lsb.append("<table class=\""+DEFAULTS_CLASS+"\">");
	        printCols(sheet);  
	        printSheetContent(sheet);  
	        lsb.append("</table>");
	    }
	    private void printSheetContent(Sheet sheet) throws IOException { 
	        lsb.append("<tbody>");
	        for(int j = 0;j<=sheet.getLastRowNum();j++){ 
	            Row row = sheet.getRow(j);  
	            if(row == null){
	            	continue;
	            }
	            Integer markwidth=0;
	            Integer vel=0;
	            for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
	            	if(StringUtils.isNotEmpty(String.valueOf(getCellValue(row.getCell(i))))){
	            		markwidth++;
	            	}
	            	if(i==(row.getLastCellNum()-1)&&markwidth==0){
	            		markwidth=-1;
	            	}  
	            }
	            if(row.getFirstCellNum()==-1||row.getLastCellNum()==-1){
	            	markwidth=-1;
	            }
	            if(markwidth!=-1){
	            	lsb.append("<tr>");
	            for (int i = firstColumn; i < endColumn; i++) {  
	                Object content = " ";  
	                String attr="";
	                if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {  
	                	 Cell cell = row.getCell(i);  
	                    if (StringUtils.isNotEmpty(String.valueOf(getCellValue(cell)))) { 
	                        content = getCellValue(cell);
	                        vel=row.getLastCellNum()-row.getFirstCellNum();
	                        if(vel==1){
	                        	vel=getMergerCellRegionCol(sheet,row.getRowNum(),i);
	                        }
	                        if(vel>20||vel==0){
	                        	vel=endColumn-firstColumn;
	                        }
	                        if (content.equals(""))  
	                            content = " "; 
	                        if(markwidth==1){
	                        	attr="colspan=\""+vel+"\"";
	                        }
	                    }  
	                } 
	                lsb.append("<td "+attr+">"+content+"</td>");
	            }  
	            lsb.append("</tr>");
	            }
	        }   
	        lsb.append("</tbody>");
	    } 
	    /**
	     * 取得单元格的值
	     * @param cell
	     * @return
	     * @throws IOException
	     */
	    private static Object getCellValue(Cell cell) throws IOException {
	    	Object value="";
	    	if(cell!=null){
	    		switch (cell.getCellType()) {
	    			case Cell.CELL_TYPE_STRING:
	    					value=cell.getRichStringCellValue().getString();
	    					break;
	    			case Cell.CELL_TYPE_NUMERIC:
	    					if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
	    						Date date = cell.getDateCellValue();
	    						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    						value = sdf.format(date);
	    					} else {
	    						value=cell.getNumericCellValue();
	    					}
	    					break;
	    			case Cell.CELL_TYPE_BOOLEAN:
	    					value=cell.getBooleanCellValue();
	    						break;
	    			case Cell.CELL_TYPE_FORMULA:
	    					value=cell.getCellFormula();
	    					break;
	    			default:
	    				value="";
	    		}
	    	}else{
	    		value="";
	    	}
			return value;
	    }
	    private void sheet(String path) throws Exception{
	 		String[] pth = path.split("\\.");
	 		lsb = new StringBuffer();
	 		wb = null;
	 		if ("xlsx".equals(pth[pth.length - 1])||"XLSX".equals(pth[pth.length - 1])) {
	 			wb = new XSSFWorkbook(new FileInputStream(path));

	 		} else if ("xls".equals(pth[pth.length - 1])||"XLS".equals(pth[pth.length - 1])) {
	 			wb = new HSSFWorkbook(new FileInputStream(path));
	 		}
	 		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
	 			String sheetName = wb.getSheetName(sheetIndex); // sheetName
	 			lsb.append(sheetName+"<br />");
	 			Sheet sheet = wb.getSheetAt(sheetIndex);// 获所有的sheet
	 			if(sheet==null){
	 				continue;
	 			}
	 			printSheet(sheet);
	 		}
	    }
	    private void ensureColumnBounds(Sheet sheet) {  
	        Iterator<Row> iter = sheet.rowIterator();  
	        firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);  
	        endColumn = 0;  
	        while (iter.hasNext()) {  
	            Row row = iter.next();  
	            short firstCell = row.getFirstCellNum();  
	            if (firstCell >= 0) {  
	                firstColumn = Math.min(firstColumn, firstCell);  
	                if(row.getLastCellNum()-endColumn<30){
	                	endColumn = Math.max(endColumn, row.getLastCellNum()); 
	                } 
	            }  
	        }  
	    } 
	    
	    private void printCols(Sheet sheet) {   
	        ensureColumnBounds(sheet);  
	    }  
	    /**
	     * 判断单元格在不在合并单元格范围内，如果是，获取其合并的列数。
	     * @param sheet 工作表
	     * @param cellRow 被判断的单元格的行号
	     * @param cellCol 被判断的单元格的列号
	     * @return
	     * @throws IOException
	     */
	    private static int getMergerCellRegionCol(Sheet sheet, int cellRow,int cellCol) throws IOException {
	        int retVal = 0;
	        int sheetMergerCount = sheet.getNumMergedRegions();
	        for (int i = 0; i < sheetMergerCount; i++) {
	        	CellRangeAddress cra = sheet.getMergedRegion(i);
	            int firstRow = cra.getFirstRow();  // 合并单元格CELL起始行
	            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
	            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
	            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
	            if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
	                if (cellCol >= firstCol && cellCol <= lastCol) {
	                    retVal = lastCol - firstCol+1; // 得到合并的列数
	                    break;
	                }
	            }
	        }
	        return retVal;
	    }

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
	}
}
