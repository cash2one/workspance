/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-22
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.price.PriceTemplateService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
@Controller
public class PriceController extends BaseController {
	@Autowired
	private PriceService priceService;
	@Resource
	private PriceCategoryService priceCategoryService;
	@Resource
	private PriceTemplateService priceTemplateService;
	
	final static String PRICE_OPERTION = "post_price";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final static String URL_FOAMAT = "http://jiage.zz91.com/detail/";
	final static String URL_FIX = ".html";

	/**
	 * 初始化列表页
	 * 
	 * @param modelget
	 */
	@RequestMapping
	public void list(Map<String, Object> model) {
		model.put("upload_filetype", AstConst.UPLOAD_FILETYPE_IMG);
		model.put("upload_model", WebConst.UPLOAD_MODEL_PRICE);
		model.put(
				"upload_url",
				MemcachedUtils.getInstance().getClient()
						.get("baseConfig.resource_url"));
	}

	/**
	 * 读取列表
	 * 
	 * @param priceDO
	 * @param page
	 * @param priceDto
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(PriceDO priceDO, PageDto<PriceDTO> page,
			PriceDTO priceDto, Map<String, Object> model) throws IOException {
		if (page == null) {
			page = new PageDto<PriceDTO>(AstConst.PAGE_SIZE);
		} else {
			if (page.getStartIndex() == null) {
				page.setStartIndex(0);
			}
			if (page.getPageSize() == null) {
				page.setPageSize(20);
			}
		}
		page.setSort("gmt_order");
		page.setDir("desc");

		priceDto.setPage(page);
		priceDto.setPrice(priceDO);
		page.setTotalRecords(10000);//
		page.setRecords(priceService.queryMiniPriceByCondition(priceDto));

		return printJson(page, model);
	}

	/**
	 * 初始化编辑页面
	 * 
	 * @param priceId
	 * @param id
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView edit(Integer priceId, Integer id,
			Map<String, Object> out) throws UnsupportedEncodingException {
		out.put("priceId", priceId);
		out.put("proId", id);
//		if (id != null) {		
//			PriceDTO price = priceService.queryPriceByIdForEdit(id);
//			if (price != null) {
//				out.put("title", price.getPrice().getTitle());
//				out.put("tags", price.getPrice().getTags());
//				out.put("assistTypeName", price.getAssistTypeName());
//				out.put("typeName", price.getTypeName());
//				out.put("typeId", price.getPrice().getTypeId());
//			}
		//}

		return null;
	}

	/**
	 * 添加报价
	 * 
	 * @param model
	 * @param price
	 * @param gmtCreated
	 * @param gmtOrder
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView add(HttpServletRequest request,
			Map<String, Object> model, PriceDO price) throws IOException,
			ParseException {

		ExtResult result = new ExtResult();
		do {
			
			// 判断重复添加报价
			if(priceService.forbidDoublePub(price.getTitle(), null)){
				break;
			}
			
			// 取出文本编辑器中的内容并且除去前导和内容后面的空格
			String content = price.getContent();
			content.trim();
			// 截取处理后的内容的前十六位,判断是否和给定的字符串相等.如果相当则将其取出.
			Integer impress = 0;
			String contentSub = content;
			Integer startInteger = "<p>\n\t&nbsp;</p>\n".length();
			if (StringUtils.isNotEmpty(contentSub) && contentSub.length() >= 16) {
				contentSub = content.substring(0, startInteger);
			}
			// 使用搜索引擎的方法把数据组装需要的三条数据
			int typeId = price.getTypeId();
			String priceSearchKey = null;
			String zaobaoSearchKeyString = null;
			String wanbaoSearchKeyString =null;
			String code = priceService.queryPriceTtpePlasticOrMetal(typeId);
			if (StringUtils.isNotEmpty(code)) {
				if ("废金属".equals(code)) {
					priceSearchKey = "金属";
					zaobaoSearchKeyString = "金属早参";
					wanbaoSearchKeyString ="金属晚报";
				} else {
					if ("废塑料".equals(code)) {
						priceSearchKey = "塑料";
					}
					if ("废纸".equals(code)) {
						priceSearchKey = "废纸";
					}
					if ("废橡胶".equals(code)) {
						priceSearchKey = "橡胶";
					}
					if ("原油".equals(code)) {
						priceSearchKey = "原油";
					}
					zaobaoSearchKeyString = "塑料早参";
					wanbaoSearchKeyString = "废塑料晚报";
				}
			}
			if ("<p>\n\t&nbsp;</p>\n".equalsIgnoreCase(contentSub)) {
				content = content.substring(startInteger, content.length());
			}
//			相关文章模版删除
//			content = content + priceService.queryContentByPageEngine(priceSearchKey,zaobaoSearchKeyString,wanbaoSearchKeyString);
			
			price.setContent(content);
			impress = priceService.insertPrice(price);

			if (impress != null && impress > 0) {
				result.setSuccess(true);
				// 日志系统 记录发布报价相关信息 mongo日志
				SessionUser sessionUser = getCachedUser(request);
				LogUtil.getInstance().mongo(
						sessionUser.getAccount(),
						PRICE_OPERTION,
						null,
						"{'id':'" + impress + "','date':'"
								+ DateUtil.toString(new Date(), DATE_FORMAT)
								+ "'}");
				
				// 文本日志 监控mongo与文本日志数据丢失情况
				LogUtil.getInstance().log(
						sessionUser.getAccount(), PRICE_OPERTION, 
						null, 
						"{'id':'" + impress + "','date':'"
						+ DateUtil.toString(new Date(), DATE_FORMAT)
						+ "'}");
			}
		} while (false);
		return printJson(result, model);
	}
	
	/**
	 * 添加上下模版报价
	 * @param request
	 * @param model
	 * @param price
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addAndTemplate(HttpServletRequest request,
			Map<String, Object> model, PriceDO price) throws IOException{
		ExtResult result = new ExtResult();
		do {
			
			// 判断重复添加报价
			if(priceService.forbidDoublePub(price.getTitle(), null)){
				break;
			}
			
			// 取出文本编辑器中的内容并且除去前导和内容后面的空格
			String content = price.getContent();
			content.trim();
			// 截取处理后的内容的前十六位,判断是否和给定的字符串相等.如果相当则将其取出.
			Integer impress = 0;
			String contentSub = content;
			Integer startInteger = "<p>\n\t&nbsp;</p>\n".length();
			if (StringUtils.isNotEmpty(contentSub) && contentSub.length() >= 16) {
				contentSub = content.substring(0, startInteger);
			}
			// 使用搜索引擎的方法把数据组装需要的三条数据
			int typeId = price.getTypeId();
			String priceSearchKey = null;
			String zaobaoSearchKeyString = null;
			String wanbaoSearchKeyString =null;
			String code = priceService.queryPriceTtpePlasticOrMetal(typeId);
			if (StringUtils.isNotEmpty(code)) {
				if ("废金属".equals(code)) {
					priceSearchKey = "金属";
					zaobaoSearchKeyString = "金属早参";
					wanbaoSearchKeyString ="金属晚报";
				} else {
					if ("废塑料".equals(code)) {
						priceSearchKey = "塑料";
					}
					if ("废纸".equals(code)) {
						priceSearchKey = "废纸";
					}
					if ("废橡胶".equals(code)) {
						priceSearchKey = "橡胶";
					}
					if ("原油".equals(code)) {
						priceSearchKey = "原油";
					}
					zaobaoSearchKeyString = "塑料早参";
					wanbaoSearchKeyString = "废塑料晚报";
				}
			}
			if ("<p>\n\t&nbsp;</p>\n".equalsIgnoreCase(contentSub)) {
				content = content.substring(startInteger, content.length());
			}
//			content = content + priceService.buildTemplateContent(priceSearchKey,zaobaoSearchKeyString,wanbaoSearchKeyString, price.getTags());
			
			price.setContent(content);
			impress = priceService.insertPrice(price);
			
			if (impress != null && impress > 0) {
				result.setSuccess(true);
				// 日志系统 记录发布报价相关信息 mongo日志
				SessionUser sessionUser = getCachedUser(request);
				LogUtil.getInstance().mongo(
						sessionUser.getAccount(),
						PRICE_OPERTION,
						null,
						"{'id':'" + impress + "','date':'"
								+ DateUtil.toString(new Date(), DATE_FORMAT)
								+ "'}");
				
				// 文本日志 监控mongo与文本日志数据丢失情况
				LogUtil.getInstance().log(
						sessionUser.getAccount(), PRICE_OPERTION, 
						null, 
						"{'id':'" + impress + "','date':'"
						+ DateUtil.toString(new Date(), DATE_FORMAT)
						+ "'}");
				
				// 添加报价成功，添加数据进入关系表
				priceTemplateService.insert(impress);
			}
		} while (false);
		return printJson(result, model);
	}

	/**
	 * 删除报价
	 * 
	 * @param ids
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(String ids, Map<String, Object> model)
			throws IOException {

		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}

		ExtResult result = new ExtResult();
		int impress = priceService.batchDeleteUserById(i);
		if (impress == entities.length) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}

	/**
	 * 读取指定报价
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSingleRecord(Integer id, Map<String, Object> model)
			throws IOException {
		PriceDTO price = priceService.queryPriceByIdForEdit(id);

		List<PriceDTO> list = new ArrayList<PriceDTO>();
		list.add(price);

		PageDto<PriceDTO> page = new PageDto<PriceDTO>();
		page.setRecords(list);

		return printJson(page, model);
	}
	
	@RequestMapping
	public ModelAndView getSingleRecords(Integer id, Map<String, Object> model)
			throws IOException {
		PriceDTO price = priceService.queryPriceByIdForEdit(id);
		price.getPrice().setContent("");
		price.getPrice().setGmtOrder(new Date());
		price.getPrice().setGmtCreated(new Date());
		List<PriceDTO> list = new ArrayList<PriceDTO>();
		list.add(price);

		PageDto<PriceDTO> page = new PageDto<PriceDTO>();
		page.setRecords(list);

		return printJson(page, model);
	}

	/**
	 * 更新报价
	 * 
	 * @param model
	 * @param price
	 * @param gmtCreated
	 * @param gmtOrder
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "update.htm", method = RequestMethod.POST)
	public ModelAndView update(Map<String, Object> model, PriceDO price)
			throws IOException, ParseException {
		// 时间转化
		// if (StringUtils.isNotEmpty(gmtCreated)) {
		// price.setGmtCreated(DateUtil.getDate(gmtCreated,
		// "yyyy-MM-dd hh:mm"));
		// } else {
		// price.setGmtCreated(DateUtil.getDate(new Date(),
		// "yyyy-MM-dd hh:mm"));
		// }
		// if (StringUtils.isNotEmpty(gmtOrder)) {
		// price.setGmtOrder(DateUtil.getDate(gmtOrder, "yyyy-MM-dd hh:mm"));
		// } else {
		// price.setGmtOrder(DateUtil.getDate(new Date(), "yyyy-MM-dd hh:mm"));
		// }

		ExtResult result = new ExtResult();
		if(price.getAssistTypeId()==null){
			price.setAssistTypeId(0);
		}
		if (priceService.updatePriceById(price) > 0) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}

	/**
	 * 审核报价
	 * 
	 * @param ids
	 * @param isChecked
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView check(String ids, String isChecked,
			Map<String, Object> out) throws IOException {

		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int impacted = 0;
		for (int ii = 0; ii < entities.length; ii++) {
			if (StringUtils.isNumber(entities[ii])) {
				Integer im = priceService.updateIsCheckedById(
						(Integer.valueOf(entities[ii])), isChecked);
				if (im.intValue() > 0) {
					impacted++;
				}
			}
		}
		if (impacted != entities.length) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		result.setData(impacted);
		return printJson(result, out);
	}
	 
	/**
	 * 导出表格报价为excel文件
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping
	public void exportTable(HttpServletResponse response,Map<String, Object>map,Integer id) throws IOException, RowsExceededException, WriteException{
		do {
			PriceDTO dto = priceService.queryPriceByIdForEdit(id);
			if(dto==null||dto.getPrice()==null||StringUtils.isEmpty(dto.getPrice().getContent())){
				break;
			}
			
			// 获取内容
			String content = dto.getPrice().getContent();
			// 取小写
			content = content.toLowerCase();
			// 正则 取table内容
			Pattern pattern = Pattern.compile("\\<table[^}]+\\/table>");
			Matcher matcher = pattern.matcher(content);
			matcher.find();
			content = matcher.group();
			// 给行加标记
			content = content.replace("</tr>","</tr>,");
			// 给列标记
			content = content.replace("</td>","</td>|");
			// 清空多余html内容
			content = Jsoup.clean(content, Whitelist.none());
			
			//生成Excel
			//提供下载
			response.setContentType("application/msexcel");
			
			OutputStream os = response.getOutputStream();
			
			WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
			WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
			
			// 组装excel行列
			String [] excelContent = content.split(",");
			for (int i = 0; i < excelContent.length; i++) {
				String [] rows = excelContent[i].split("\\|");
				for (int j = 0; j < rows.length; j++) {
					ws.addCell(new Label(j,i,rows[j]));
				}
			}
			
			//现在可以写了   
			wwb.write();
			//写完后关闭   
			wwb.close();
			//输出流也关闭吧   
			os.close(); 
		} while (false);
	}
	
	@RequestMapping
	public ModelAndView exportData(HttpServletRequest request,HttpServletResponse response,String from ,String to ) throws IOException, RowsExceededException, WriteException{
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		// 检索所有list
		List<PriceDO> list = priceService.queryListByFromTo(from, to);
		ws.addCell(new Label(0,0,"标题"));
		ws.addCell(new Label(1,0,"网址"));
		ws.addCell(new Label(2,0,"主类别"));
		ws.addCell(new Label(3,0,"辅助类别"));
		ws.addCell(new Label(4,0,"流量"));
		ws.addCell(new Label(5,0,"UV"));
		int i=1;
		for(PriceDO obj:list){
			ws.addCell(new Label(0,i,obj.getTitle()));
			ws.addCell(new Label(1,i,URL_FOAMAT+obj.getId()+URL_FIX));
			ws.addCell(new Label(2,i,priceCategoryService.queryTypeNameByTypeId(obj.getTypeId())));
			ws.addCell(new Label(3,i,priceCategoryService.queryTypeNameByTypeId(obj.getAssistTypeId())));
			//获取流量的值
			Integer realClickNumber=obj.getRealClickNumber();
			//判断流量值是否为空
			if(realClickNumber==null){
				realClickNumber=0;
			}
			ws.addCell(new jxl.write.Number(4,i,realClickNumber));
			ws.addCell(new jxl.write.Number(5,i,obj.getIp()));
			i++;
		}
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置和内容   
//		ws.addCell(labelCF);//将Label写入sheet中   
		//Label的构造函数Label(int x, int y,String aString)  xy意同读的时候的xy,aString是写入的内容.   
//		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);//设置写入字体   
//		WritableCellFormat wcfF = new WritableCellFormat(wf);//设置CellFormat   
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置,内容和格式   
		//Label的另一构造函数Label(int c, int r, String cont, CellFormat st)可以对写入内容进行格式化,设置字体及其它的属性.   
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}
}
