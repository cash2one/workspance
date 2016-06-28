/**
 * 
 */
package com.ast.ast1949.web.controller.zz91;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.domain.phone.PhoneLibrary;
import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.service.market.MarketPicService;
import com.ast.ast1949.service.phone.PhoneLibraryService;
import com.ast.ast1949.service.price.PriceOfferService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.thread.PdfToSwfThread;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.file.FileUtils;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.PicMarkUtils;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Controller
public class CommonController extends BaseController {
	
	@Resource
	private CategoryService categoryService;
	@Resource
	private PhoneLibraryService phoneLibraryService;
	@Resource
	private MarketPicService marketPicService;
	@Resource
	private PriceOfferService priceOfferService;
	private static final String DEFAULTS_CLASS = "excelDefaults";  
	private static StringBuffer lsb = new StringBuffer();
	private static int firstColumn;  
	private static int endColumn; 
	private static Workbook wb;  
	
	@RequestMapping
	public ModelAndView categoryTreeNode(HttpServletRequest request, Map<String, Object> out, 
			String parentCode) throws IOException{

		List<ExtTreeDto> category=categoryService.child(parentCode);
		
		return printJson(category, out);
	}
	
	@RequestMapping
	public ModelAndView categoryCombo(String preCode, Map<String, Object> out) throws IOException {
//		List<CategoryDO> list=new ArrayList<CategoryDO>();
//		CategoryDO category=new CategoryDO();
//		category.setLabel("--请选择--");
//		list.add(category);
		List<CategoryDO> childList=categoryService.queryCategoriesByPreCode(preCode);
//		list.addAll(childList);

		return printJson(childList,out);
	}
	
	@RequestMapping
	public ModelAndView doUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.UPLOAD_MODEL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename);
			result.setSuccess(true);
			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView doMarketUpload(HttpServletRequest request, Map<String, Object> out,Integer marketId) 
			throws IOException{
		ExtResult result=new ExtResult();
		if (marketId!=null&&marketId.intValue()>0) {
			
			String path=MvcUpload.getModalPath(WebConst.UPLOAD_MODEL_DEFAULT);
			String filename=UUID.randomUUID().toString();
			
			try {
				String finalname=MvcUpload.localUpload(request, path, filename);
				
				String picPath=path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname;
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(path + "/" + finalname, path + "/"
						+ finalname, 800, 800);
				MarketPic marketPic=new MarketPic();
				marketPic.setMarketId(marketId);
				marketPic.setPicAddress(picPath);
				marketPic.setPicSource(0);
				marketPic.setIsDefault("0");
				Integer i=marketPicService.insert(marketPic);
				if (i!=null&&i.intValue()>0) {
					result.setData(picPath);
					result.setSuccess(true);
				}
				Color lightGrey = new Color(0, 0, 0);
			
				PicMarkUtils.pressText(path + "/" + finalname,"http://www.zz91.com", "simhei", Font.BOLD, 20,lightGrey, 1, 1, (float) 1.0);
			} catch (Exception e) {
				result.setData(MvcUpload.getErrorMessage(e.getMessage()));
			}
		}
		return printJson(result, out);
	}
	
	/**
	 * 下载中心上传文件
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doXiazaiDocUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.DOWNLOAD_URL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename,"uploadfile", MvcUpload.WHITE_DOC, MvcUpload.BLOCK_ANY, 5*1024);
			result.setSuccess(true);
			String fileUrl = path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname;
			result.setData(fileUrl);
			// 上传pdf成功 自动转换
			PdfToSwfThread.addfile(fileUrl);
			// 读取pdf文字
			PDDocument document = PDDocument.load(MvcUpload.getDestRoot()+fileUrl);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setEndPage(1);
			String text = stripper.getText(document);
			text = Jsoup.clean(text, Whitelist.none());
			String[] content = text.split("市场行情简述");
			if(content.length==2&&StringUtils.isNotEmpty(content[1])){
				result.setData(result.getData()+"|"+content[1]);
			}
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	
	/**
	 * 下载中心上传图片
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doXiazaiPicUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.DOWNLOAD_URL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename);
			result.setSuccess(true);
			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView importPhoneByExcel(HttpServletRequest request, Map<String, Object> out) throws IOException, FileUploadException, BiffException{
		ExtResult result = new ExtResult();
		MultipartRequest multipartRequest = (MultipartRequest) request;
		MultipartFile file = multipartRequest.getFile("uploadfile");
		InputStream in=null;
		int i = 0;
		try {
			in=new BufferedInputStream(file.getInputStream());
			
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			for (; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				String tel = row.getCell(0).toString().trim();
				String called = row.getCell(1).toString().trim();
				tel = tel.replace(".0", "");
				called = called .replace(".0", "");
				PhoneLibrary phoneLibrary = new PhoneLibrary();
				phoneLibrary.setTel(tel);
				phoneLibrary.setCalled(called);
				phoneLibraryService.createPhoneLibrary(phoneLibrary);
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
        if (i>0) {
			result.setData(i);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView doJiageUpload(HttpServletRequest request, Map<String, Object> out,Integer offerId) 
			throws Exception{
		ExtResult result=new ExtResult();
		PriceOffer offer = new PriceOffer();
		offer.setId(offerId);
		MultipartRequest multipartRequest = (MultipartRequest) request;
		//获取上传的文件信息
		MultipartFile file = multipartRequest.getFile("uploadfile");
		//获取文件的名称
		String name = file.getOriginalFilename();
		//获取后缀名
		String[] s=name.split("\\.");
		//给上传的文件重新命名
		String filename = String.valueOf(System.currentTimeMillis())+"."+s[s.length-1];
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
		Integer i=priceOfferService.updateOfferById(offer);
		if(i > 0){
			result.setData(offer.getExcelName());
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public void downloadFile(HttpServletResponse response,Integer offerId)throws IOException {
		do {
			if(offerId==null){
				break;
			}
			PriceOffer offer=priceOfferService.queryOfferById(offerId);
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
	
}
