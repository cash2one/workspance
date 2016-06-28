package com.ast.ast1949.web.controller.zz91.auto;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.autocaiji.AutoCaiji;
import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.autocaiji.AutoCaijiService;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.price.PriceTemplateService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.analzyer.IKAnalzyerUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * author:kongsj date:2013-6-25
 */
@Controller
public class CaijiController extends BaseController {

	final static String PRICE_OPERTION = "post_price";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Resource
	PriceTemplateService priceTemplateService;
	@Resource
	private PriceCategoryService priceCategoryService;
	@Resource
	private PriceService priceService;

	@Resource
	private AutoCaijiService autoCaijiService;

	@RequestMapping
	public ModelAndView caiji_log(HttpServletRequest request, Map<String, Object> out) {
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView load_caiji_log(HttpServletRequest request, Map<String, Object> out, PageDto<AutoCaiji> page,
			String from, String to) throws ParseException, IOException {
		if (page.getPageSize() == null) {
			page.setPageSize(AstConst.PAGE_SIZE);
		}

		if (page.getStartIndex() == null) {
			page.setStartIndex(0);
		}
		page.setRecords(autoCaijiService.queryListByFromTo(from, to));
		return printJson(page, out);
	}

	/*
	 * @RequestMapping public ModelAndView failure(HttpServletRequest
	 * request,Map<String,Object>out ){ return new ModelAndView(); }
	 * 
	 * @RequestMapping public ModelAndView load_failure(HttpServletRequest
	 * request,Map<String,Object>out , PageDto<AutoCaiji> page ,String
	 * from,String to) throws ParseException, IOException{ if
	 * (page.getPageSize() == null) { page.setPageSize(AstConst.PAGE_SIZE); }
	 * 
	 * if (page.getStartIndex() == null) { page.setStartIndex(0); }
	 * page=autoCaijiService.queryPageByFromTo(page,from, to ,TYPE_FAILURE);
	 * List<AutoCaiji> list = page.getRecords();
	 * 
	 * List<AutoCaiji> newList = new ArrayList<AutoCaiji>(); Integer flg = 0 ;
	 * for (AutoCaiji autoCaiji : list) { String title = autoCaiji.getTitle();
	 * String url = autoCaiji.getUrl(); if (newList.size() == 0) {
	 * autoCaiji.setNum(1); newList.add(autoCaiji); } else { for (AutoCaiji
	 * otherautoCaiji : newList) { if(url.equals(otherautoCaiji.getUrl()) &&
	 * title.equals(otherautoCaiji.getTitle())) {
	 * otherautoCaiji.setNum(otherautoCaiji.getNum()+1); flg = 1; break; } } if
	 * (flg == 0) { autoCaiji.setNum(1); newList.add(autoCaiji); } flg = 0; }
	 * 
	 * } page.setRecords(newList); page.setTotalRecords(page.getTotalRecords());
	 * return printJson(page, out); }
	 * 
	 * @RequestMapping public ModelAndView exportFailureData(HttpServletRequest
	 * request,HttpServletResponse response,String from,String to) throws
	 * IOException, RowsExceededException, WriteException, ParseException{
	 * response.setContentType("application/msexcel"); OutputStream os =
	 * response.getOutputStream(); WritableWorkbook wwb =
	 * Workbook.createWorkbook(os);//创建可写工作薄 WritableSheet ws =
	 * wwb.createSheet("sheet1", 0);//创建可写工作表
	 * 
	 * // 检索所有list List<AutoCaiji> list =
	 * autoCaijiService.queryListByFromTo(from, to,TYPE_FAILURE);
	 * List<AutoCaiji> newList = new ArrayList<AutoCaiji>(); for (AutoCaiji
	 * autoCaiji : list) { Integer flg = 0 ; String title =
	 * autoCaiji.getTitle(); String url = autoCaiji.getUrl(); if (newList.size()
	 * == 0) { autoCaiji.setNum(1); newList.add(autoCaiji); } else { for
	 * (AutoCaiji otherautoCaiji : newList) {
	 * if(url.equals(otherautoCaiji.getUrl()) &&
	 * title.equals(otherautoCaiji.getTitle())) {
	 * otherautoCaiji.setNum(otherautoCaiji.getNum()+1); flg = 1; break; } } if
	 * (flg == 0) { autoCaiji.setNum(1); newList.add(autoCaiji); } }
	 * 
	 * } ws.addCell(new Label(0,0,"名称")); ws.addCell(new Label(1,0,"次数"));
	 * ws.addCell(new Label(2,0,"来源网站地址")); int i=1; for(AutoCaiji obj:newList){
	 * int start = obj.getUrl().indexOf("http"); int end =
	 * obj.getUrl().indexOf("\">"); WritableHyperlink link = new
	 * WritableHyperlink(2, i, new URL(obj.getUrl().substring(start, end)));
	 * ws.addHyperlink(link); ws.addCell(new Label(0,i,obj.getTitle()));
	 * ws.addCell(new Label(1,i,obj.getNum().toString())); ws.addCell(new
	 * Label(2,i,obj.getUrl().substring(end+2, obj.getUrl().length()-4))); i++;
	 * }
	 * 
	 * wwb.write(); //写完后关闭 wwb.close(); //输出流也关闭吧 os.close(); return null; }
	 */
	@RequestMapping
	public ModelAndView exportLogData(HttpServletRequest request, HttpServletResponse response, String from, String to)
			throws IOException, RowsExceededException, WriteException, ParseException {
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);// 创建可写工作薄
		WritableSheet ws = wwb.createSheet("sheet1", 0);// 创建可写工作表

		// 检索所有list
		List<AutoCaiji> list = autoCaijiService.queryListByFromTo(from, to);

		ws.addCell(new Label(0, 0, "名称"));
		ws.addCell(new Label(1, 0, "次数"));
		ws.addCell(new Label(2, 0, "来源网站地址"));
		ws.addCell(new Label(3, 0, "预设时间"));
		ws.addCell(new Label(4, 0, "抓取最早时间"));
		ws.addCell(new Label(5, 0, "抓取最晚时间"));
		int i = 1;
		for (AutoCaiji obj : list) {
			int start = obj.getUrl().indexOf("http");
			int end = obj.getUrl().indexOf("\" target=\"_blank\">");
			WritableHyperlink link = new WritableHyperlink(2, i, new URL(obj.getUrl().substring(start, end)));
			ws.addHyperlink(link);
			ws.addCell(new Label(0, i, obj.getTitle()));
			if (obj.getNum() == null) {
				ws.addCell(new Label(1, i, "0"));
			} else {
				ws.addCell(new Label(1, i, obj.getNum().toString()));
			}
			ws.addCell(new Label(2, i, obj.getUrl().substring(end + 18, obj.getUrl().length() - 4)));
			ws.addCell(new Label(3, i, obj.getDefaultTime()));
			ws.addCell(new Label(4, i, obj.getEarlyTime()));
			ws.addCell(new Label(5, i, obj.getLateTime()));
			i++;
		}

		wwb.write();
		// 写完后关闭
		wwb.close();
		// 输出流也关闭吧
		os.close();
		return null;
	}

	final static Map<String, String> OMETAL_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		OMETAL_MAP.put("charset", "gb2312");
		// 采集地址
		OMETAL_MAP.put("url", "http://www.ometal.com/bin/new/searchkey.asp?type=");
		// 后台类别对应采集地址 的key
		OMETAL_MAP.put("81", "%C9%CF%BA%A3%CF%D6%BB%F5-%D6%F7%D2%AA%BD%F0%CA%F4%B7%DB%C4%A9"); // 金属粉末
		OMETAL_MAP.put("80", "%C9%CF%BA%A3%CF%D6%BB%F5-%D0%A1%BD%F0%CA%F4"); // 小金属
		OMETAL_MAP.put("86", "%C9%CF%BA%A3%CF%D6%BB%F5-%B9%F3%BD%F0%CA%F4"); // 贵金属
		OMETAL_MAP.put("79", "%C9%CF%BA%A3%CF%D6%BB%F5-%BB%F9%B1%BE%BD%F0%CA%F4"); // 基本金属
		OMETAL_MAP.put("210", "%B3%A4%BD%AD%D3%D0%C9%AB"); // 基本金属
	}

	@RequestMapping
	public ModelAndView caiji_ometal(Integer typeId, Map<String, Object> out) throws ParseException {
		do {

			String content = "";
			String url = OMETAL_MAP.get("url") + OMETAL_MAP.get(String.valueOf(typeId));
			content = HttpUtils.getInstance().httpGet(url, OMETAL_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf("搜索结果：");
			Integer end = content.indexOf("上一页");
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</tr>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-M-d");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1) {
					list.add(s);
				}
			}
			if (list.size() < 1) {
				break;
			}
			for (String link : list) {
				Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");
				Matcher matcher = pattern.matcher(link);
				String linkStr = "";
				if (matcher.find()) {
					linkStr = matcher.group();
				}
				if (StringUtils.isEmpty(linkStr)) {
					break;
				}

				String[] alink = linkStr.split("\"");

				String resultLink = "http://www.ometal.com" + alink[1];
				String resultContent = "";
				resultContent = HttpUtils.getInstance().httpGet(resultLink, OMETAL_MAP.get("charset"));
				Integer cStart = resultContent.indexOf("<div id=\"fontzoom\">");
				Integer cEnd = resultContent.indexOf("(您想天天");
				resultContent = resultContent.substring(cStart, cEnd);
				out.put("resultContent", resultContent);
				start = resultContent.indexOf("<TABLE");
				end = resultContent.indexOf("</TABLE>");
				resultContent = resultContent.substring(start, end + 8);
				resultContent = Jsoup.clean(resultContent,
						Whitelist.none().addTags("div", "p", "ul", "li", "br", "table", "th", "tr", "td")
								.addAttributes("td", "rowspan"));

				resultContent = resultContent.replace("(全球金属网 OMETAL.COM)", "");
				resultContent = resultContent.replace("全球金属网", "");
				resultContent = resultContent.replace("OMETAL.COM", "");

				PriceDO priceDO = new PriceDO();
				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);
				String title = "";
				if (typeId == 210) {
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					if (resultTitle.indexOf("上午") != -1) {
						title = DateUtil.toString(new Date(), "MM月dd日") + "(上午)上海" + typeName + "价格";
					} else if (resultTitle.indexOf("下午") != -1) {
						title = DateUtil.toString(new Date(), "MM月dd日") + "(下午)上海" + typeName + "价格";
					}
				} else {
					title = DateUtil.toString(new Date(), "MM月dd日") + typeName + "行情";
					title = title.replaceAll("上海", "上海现货(SMM)");
				}
				priceDO.setTitle(title);
				priceDO.setTypeId(typeId);
				priceDO.setTags(typeName);
				priceDO.setContent(resultContent);

				// 执行插入
				out.put("result", doInsert(priceDO, true));
			}
		} while (false);
		// return new ModelAndView("result");
		return null;
	}

	final static Map<String, String> FEIJIU_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		FEIJIU_MAP.put("charset", "gb2312");
		// 采集地址
		FEIJIU_MAP.put("url", "http://baojia.feijiu.net/");
		// 后台类别对应采集地址 的key
		FEIJIU_MAP.put("20", "price-p-cid-bjcid2.29-dqid-.html"); // 全国废塑料行情
		FEIJIU_MAP.put("62", "price-p-cid-bjcid2.32-dqid-.html");
		FEIJIU_MAP.put("63", "price-p-cid-bjcid2.33-dqid-.html");

	}

	/**
	 * 废旧网塑料报价采集
	 * 
	 * @param feijiuTypeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_feijiu(Integer feijiuTypeId, Map<String, Object> out) throws ParseException {
		do {

			String content = "";
			String url = FEIJIU_MAP.get("url") + FEIJIU_MAP.get(String.valueOf(feijiuTypeId));
			content = HttpUtils.getInstance().httpGet(url, FEIJIU_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf("废塑料价格行情</p>");
			Integer end = content
					.indexOf("<!-- AspNetPager V7.0 for VS2008  Copyright:2003-2007 Webdiyer (www.webdiyer.com) -->");
			if (start >= end) {
				out.put("linkContent", content + "start:" + start + "|end:" + end);
				break;
			}
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</li>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1) {
					list.add(s);
				}
			}

			if (list.size() == 0) {
				break;
			}
			String typeName = priceCategoryService.queryTypeNameByTypeId(feijiuTypeId);
			for (String s : list) {
				do {
					Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\'([^\"]*?)\'.+>(.*?)</a>");

					Matcher matcher = pattern.matcher(s);

					String linkStr = "";
					if (matcher.find()) {
						linkStr = matcher.group();
					}
					if (StringUtils.isEmpty(linkStr)) {
						break;
					}

					String[] alink = linkStr.split("\'");

					String resultLink = alink[1];
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					String resultContent = "";
					resultContent = HttpUtils.getInstance().httpGet(resultLink, FEIJIU_MAP.get("charset"));
					start = resultContent.indexOf("<TABLE");
					end = resultContent.indexOf("</TABLE>");
					resultContent = resultContent.substring(start, end + 8);
					resultContent.replace("&nbsp;", "");
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("p", "ul", "li", "br", "table", "tbody", "th", "tr", "td", "b")
									.addAttributes("td", "rowspan", "colspan")
									.addAttributes("tr", "rowspan", "colspan"));
					if ("62".equals(feijiuTypeId + "") || "63".equals(feijiuTypeId + "")) {
						resultContent = "<p>元/吨</p>" + resultContent;
					}
					out.put("resultContent", resultContent);

					PriceDO priceDO = new PriceDO();
					resultTitle = resultTitle.replaceAll("塑料价格", "废塑料市场价格");
					resultTitle = resultTitle.replaceAll("价格行情", "价格");
					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(feijiuTypeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, true));
				} while (false);
			}

		} while (false);
		// return new ModelAndView("result");
		return null;
	}

	final static Map<String, String> ALBB_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		ALBB_MAP.put("charset", "gb2312");
		// 采集地址
		ALBB_MAP.put("url", "http://info.1688.com");
		// 后台类别对应采集地址 的key
		ALBB_MAP.put("217", "/tags_list/v5003220-l18935.html"); // 全国废塑料行情
	}

	/**
	 * 1688(阿里巴巴) 塑料报价采集
	 * 
	 * @param albbTypeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_albb(Integer albbTypeId, Map<String, Object> out) throws ParseException {
		do {

			String content = "";
			String url = ALBB_MAP.get("url") + ALBB_MAP.get(String.valueOf(albbTypeId));
			content = HttpUtils.getInstance().httpGet(url, ALBB_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf("<dl class=\"li23 listcontent1\">");
			Integer end = content.indexOf("<!--分页 start-->");
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("<li>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			String formatZero = DateUtil.toString(new Date(), "yyyy-M-d");
			String formatTitle = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
					if (s.indexOf("塑料原料") == -1) {
						list.add(s);
					}
				}
			}

			if (list.size() == 0) {
				break;
			}
			String typeName = priceCategoryService.queryTypeNameByTypeId(albbTypeId);
			for (String s : list) {
				do {
					Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");

					Matcher matcher = pattern.matcher(s);

					String linkStr = "";
					if (matcher.find()) {
						linkStr = matcher.group();
					}
					if (StringUtils.isEmpty(linkStr)) {
						break;
					}

					String[] alink = linkStr.split("\"");

					String resultLink = ALBB_MAP.get("url") + alink[1];
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					resultTitle = resultTitle.replace(" ", "");
					for (int i = 0; i < resultTitle.length(); i++) {
						if (resultTitle.charAt(i) >= 'A' && resultTitle.charAt(i) <= 'Z') {
							resultTitle = resultTitle.substring(i, resultTitle.length());
							break;
						}
					}
					resultTitle = formatTitle + resultTitle;
					resultTitle = resultTitle.replace("点评", "评论");
					resultTitle = resultTitle.replace("PP粉", "PP粉料");
					resultTitle = resultTitle.replace("PP粒", "PP");
					if (resultTitle.indexOf("现货") == -1) {
						resultTitle = resultTitle.replace("市场", "现货市场");
					}

					String resultContent = "";
					resultContent = HttpUtils.getInstance().httpGet(resultLink, ALBB_MAP.get("charset"));
					start = resultContent.indexOf("<div class=\"d-content\">");
					end = resultContent.indexOf("<span class=\"editor\">");
					resultContent = resultContent.substring(start, end);
					// resultContent = resultContent.toLowerCase();
					resultContent = Jsoup.clean(resultContent, Whitelist.none()
							.addTags("p", "ul", "li", "br", "table", "th", "tr", "td").addAttributes("td", "rowspan"));
					if (resultContent.indexOf("<table") == -1) {
						resultContent = resultContent.replace(" ", "");
					}
					out.put("resultContent", resultContent);

					PriceDO priceDO = new PriceDO();
					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(albbTypeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, false));
				} while (false);
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> CS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		CS_MAP.put("charset", "gb2312");
		// 采集地址
		CS_MAP.put("url", "http://www.cs.com.cn/qhsc/zzqh/");
		// 后台类别对应采集地址 的key
		CS_MAP.put("216", ""); // 全国废金属行情
	}

	/**
	 * 中证期货
	 * 
	 * @param csTypeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_cs(Integer csTypeId, Map<String, Object> out) throws ParseException {
		do {

			String content = "";
			String url = CS_MAP.get("url") + CS_MAP.get(String.valueOf(csTypeId));
			content = HttpUtils.getInstance().httpGet(url, CS_MAP.get("charset"));
			if (StringUtils.isEmpty(content)) {
				break;
			}
			Integer start = content.indexOf("<!-- 左侧 -->");
			Integer end = content.indexOf("<!-- 翻页 -->");
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("<li><span class=\"ctime\">");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "MM-dd");
			String formatZero = DateUtil.toString(new Date(), "M-d");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
					list.add(s);
				}
			}

			if (list.size() == 0) {
				break;
			}
			String typeName = priceCategoryService.queryTypeNameByTypeId(csTypeId);
			Map<Integer, Object> moMap = new TreeMap<Integer, Object>().descendingMap(); // 12点之前的资讯
			Map<Integer, Object> evMap = new TreeMap<Integer, Object>().descendingMap(); // 12点之后的资讯

			for (String s : list) {
				do {

					Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\".([^\"]*?)\".*>(.*?)</a>");

					Matcher matcher = pattern.matcher(s);

					String linkStr = "";
					if (matcher.find()) {
						linkStr = matcher.group();
					}
					if (StringUtils.isEmpty(linkStr)) {
						break;
					}

					String[] alink = linkStr.split("\"");

					String resultLink = CS_MAP.get("url") + alink[1].substring(2, alink[1].length());
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					String resultContent = "";
					resultContent = HttpUtils.getInstance().httpGet(resultLink, CS_MAP.get("charset"));
					start = resultContent.indexOf("<div class=\"Dtext z_content\" id=ozoom1 style=\"ZOOM: 100%\">");
					end = resultContent.indexOf("<!-- 附件列表 -->");
					resultContent = resultContent.substring(start, end);
					// resultContent = resultContent.toLowerCase();
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("div", "p", "ul", "li", "br", "table", "th", "tr", "td")
									.addAttributes("td", "rowspan"));
					out.put("resultContent", resultContent);

					PriceDO priceDO = new PriceDO();
					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(csTypeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);
					// 判断资讯时间段 12点前还是12点之后
					String[] timeStr = s.split("\\)");
					Integer hour = Integer.valueOf(timeStr[0].substring(7, 9));
					if (hour <= 12) {
						moMap.put(priceDO.getContent().length(), priceDO);
					} else {
						evMap.put(priceDO.getContent().length(), priceDO);
					}

				} while (false);
			}

			String dateStr = DateUtil.toString(new Date(), "MM月dd日");
			// 执行循环插入
			Integer j = 0;
			for (Integer i : moMap.keySet()) {
				do {
					PriceDO priceDO = (PriceDO) moMap.get(i);
					if (priceDO == null) {
						break;
					}
					boolean flag = false;
					if (j == 0) {
						// 期货市场
						j++;
						flag = true;
						priceDO.setTitle(dateStr + "期货市场早评：" + priceDO.getTitle());
					} else {
						// 期铜、铝、铅、锌
						// 06月27日期铝市场早间评论：美国经济数据拖累 期铝继续走低
						String title = priceDO.getTitle();
						if (title.indexOf("铜") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期铜市场早间评论：" + priceDO.getTitle());
						}
						if (title.indexOf("铝") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期铝市场早间评论：" + priceDO.getTitle());
						}
						if (title.indexOf("铅") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期铅市场早间评论：" + priceDO.getTitle());
						}
						if (title.indexOf("锌") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期锌市场早间评论：" + priceDO.getTitle());
						}
					}
					if (flag) {
						out.put("result", doInsert(priceDO, false));
					}
				} while (false);
			}
			j = 0;
			for (Integer i : evMap.keySet()) {
				do {
					PriceDO priceDO = (PriceDO) evMap.get(i);
					if (priceDO == null) {
						break;
					}
					boolean flag = false;
					if (j == 0) {
						// 期货市场
						j++;
						flag = true;
						priceDO.setTitle(dateStr + "期货市场日评：" + priceDO.getTitle());
					} else {
						// 期铜、铝、铅、锌
						// 06月27日期铝市场早间评论：美国经济数据拖累 期铝继续走低
						String title = priceDO.getTitle();
						if (title.indexOf("铜") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期铜市场日评：" + priceDO.getTitle());
						}
						if (title.indexOf("铝") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期铝市场日评：" + priceDO.getTitle());
						}
						if (title.indexOf("铅") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期铅市场日评：" + priceDO.getTitle());
						}
						if (title.indexOf("锌") != -1) {
							flag = true;
							priceDO.setTitle(dateStr + "期锌市场日评：" + priceDO.getTitle());
						}
					}
					if (flag) {
						out.put("result", doInsert(priceDO, false));
					}
				} while (false);
			}

		} while (false);
		return null;
	}

	final static Map<String, String> XGX_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		XGX_MAP.put("charset", "utf-8");
		// 采集地址
		XGX_MAP.put("url", "http://www.96369.net");
		// 后台类别对应采集地址 的key
		XGX_MAP.put("216", "/news/news_list.aspx?channelid=2&columnid=9"); // 全国废金属行情
	}

	/**
	 * 新干线 http://www.96369.net/news/news_list.aspx?channelid=2&columnid=9
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_XGX(Integer typeId, Map<String, Object> out) throws ParseException {
		do {

			String content = "";
			String url = XGX_MAP.get("url") + XGX_MAP.get(String.valueOf(typeId));
			content = HttpUtils.getInstance().httpGet(url, XGX_MAP.get("charset"));
			if (StringUtils.isEmpty(content)) {
				break;
			}
			Integer start = content.indexOf("每日分析</strong> 共");
			Integer end = content.indexOf("PagerList");
			String result = subContent(content, start, end);
			out.put("linkContent", result);
			String[] str = result.split("<div class=\"lb_c_banner\">");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "MM月dd日");
			String formatZero = DateUtil.toString(new Date(), "M月d日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
					if (s.indexOf("-早报") != -1 || s.indexOf("-早报-晚报") != -1) {
						list.add(s);
					}
				}
			}

			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultLink = XGX_MAP.get("url") + alink[3];
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						resultContent = HttpUtils.getInstance().httpGet(resultLink, XGX_MAP.get("charset"));
						start = resultContent.indexOf("<div id=\"context\">");
						end = resultContent.indexOf("<div id=\"Disclaimer\">");
						resultContent = subContent(resultContent, start, end);

						// 内容重点摘取
						String tempContent = Jsoup.clean(resultContent, Whitelist.none().addTags("strong"));
						String[] tempArray = tempContent.split("strong");
						String tempFix = "：";
						String tempTitle = "";
						if (resultTitle.indexOf("晚报") != -1) {
							for (String tStr : tempArray) {
								if (tStr.indexOf("<") != -1 && tStr.indexOf(">") != -1 && tStr.indexOf("[") != -1
										&& tStr.indexOf("]") != -1) {
									String[] tempArrayZKH = tStr.split("]");
									if (tempArrayZKH.length >= 2) {
										tempArrayZKH = tempArrayZKH[1].split("<");
										tempTitle = tempArrayZKH[0];
									}
								}
							}
							resultTitle = formatZero + "钢材市场日评";
							if (StringUtils.isNotEmpty(tempTitle)) {
								resultTitle = resultTitle + tempFix + tempTitle;
							}
						} else if (resultTitle.indexOf("早报") != -1) {
							for (String tStr : tempArray) {
								if (tStr.indexOf("<") != -1 && tStr.indexOf(">") != -1 && tStr.indexOf("[") != -1
										&& tStr.indexOf("]") != -1) {
									String[] tempArrayZKH = tStr.split("]");
									if (tempArrayZKH.length >= 2) {
										tempArrayZKH = tempArrayZKH[1].split("<");
										tempTitle = tempTitle + " " + tempArrayZKH[0];
									}
								}
							}
							resultTitle = formatZero + "钢材市场早间评论";
							if (StringUtils.isNotEmpty(tempTitle.trim())) {
								resultTitle = resultTitle + tempFix + tempTitle;
							}
						}

						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("p", "ul", "li", "br", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan", "colspan"));
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> STEELCN_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		STEELCN_MAP.put("charset", "utf-8");
		// 采集地址
		STEELCN_MAP.put("url", "http://news.steelcn.com/domestic.html");
		// 后台类别对应采集地址 的key
		STEELCN_MAP.put("216", ""); // 全国废金属行情

		STEELCN_MAP.put("listStart", "<h2 id=\"h2_item\"><span></span>国内钢市</h2>"); // 列表页开头
		STEELCN_MAP.put("listEnd", "<div id=\"Fenye\">"); // 列表页结尾

		STEELCN_MAP.put("contentStart", "<div class=\"art_main\">");
		STEELCN_MAP.put("contentEnd", "<div class=\"art_tags\">");

	}

	/**
	 * 中国钢材网
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_steelcn(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			// 获取list 列表
			String result = getListContent(STEELCN_MAP, typeId);
			out.put("linkContent", result);
			// 分解list 为多个元素
			String[] str = result.split("<li><span>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "MM月dd日");
			String formatZero = DateUtil.toString(new Date(), "M月d日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
					list.add(s);
					// 最新10条
					if (list.size() >= 10) {
						break;
					}
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						resultContent = getContent(STEELCN_MAP, alink[1]);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						if (resultContent == null) {
							continue;
						}
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("p", "ul", "li", "br", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));

						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> KMS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		KMS_MAP.put("charset", "utf-8");
		// 采集地址
		KMS_MAP.put("url", "http://www.kms88.com/tags/yanghuatiepijiage");
		// 后台类别对应采集地址 的key
		KMS_MAP.put("42", ""); // 全国废金属行情

		KMS_MAP.put("listStart", "<div class=\"list\">"); // 列表页开头
		KMS_MAP.put("listEnd", "<div class=\"show_page\">"); // 列表页结尾

		KMS_MAP.put("contentStart", "<div class=\"article\">");
		KMS_MAP.put("contentEnd", "<div class=\"menu_kz\">");

	}

	/**
	 * 全国氧化铁皮采集 矿秘书网
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_kms(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			// 获取list 列表
			String result = getListContent(KMS_MAP, typeId);
			out.put("linkContent", result);
			// 分解list 为多个元素
			String[] str = result.split("<li><span>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "MM月dd日");
			String formatZero = DateUtil.toString(new Date(), "M月d日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
					list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = formatZero + "全国氧化铁皮价格行情汇总";
						String resultContent = "";
						resultContent = getContent(KMS_MAP, alink[1]);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("p", "ul", "li", "br", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> ALBB_YS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		ALBB_YS_MAP.put("charset", "gb2312");
		// 采集地址
		ALBB_YS_MAP.put("url", "http://info.1688.com/news/newsListByTags/v6-l1441.html,"
				+ "http://info.1688.com/news/newsListByTags/v6-l1428.html,"
				+ "http://info.1688.com/news/newsListByTags/v6-l1440.html,"
				+ "http://info.1688.com/news/newsListByTags/v6-l1439.html,"
				+ "http://info.1688.com/news/newsListByTags/v6-l1438.html,"
				+ "http://s.1688.com/news/-B9E3D6DDD3D0C9AB.html," + "http://s.1688.com/news/-C0A5C3F7D3D0C9AB.html,"
				+ "http://s.1688.com/news/-CEE4BABAD3D0C9AB.html," + "http://s.1688.com/news/-CEF7B0B2D3D0C9AB.html,"
				+ "http://s.1688.com/news/-D6D8C7ECD3D0C9AB.html");

		ALBB_YS_MAP.put("contentUrl", "http://info.1688.com");

		ALBB_YS_MAP.put("65", "");

		ALBB_YS_MAP.put("listStart", "<dl class=\"li23 listcontent1\">"); // 列表页开头
		ALBB_YS_MAP.put("listEnd", "<div class=\"cl\"></div>"); // 列表页结尾
		ALBB_YS_MAP.put("split", "<li><span class=\"r\">");

		ALBB_YS_MAP.put("listStartS", "<div class=\"info-offer\">"); // 列表页开头
		ALBB_YS_MAP.put("listEndS", "<div class=\"pagination\">"); // 列表页结尾
		ALBB_YS_MAP.put("splitS", "<h3 class=\"title\">");

		ALBB_YS_MAP.put("contentStart", "<div class=\"detail\">");
		ALBB_YS_MAP.put("contentEnd", "(责任编辑：");
		ALBB_YS_MAP.put("finalContentStart", "<table style=\"width: 88");
		ALBB_YS_MAP.put("finalContentEnd", "<span class=\"editor\">");

	}

	/**
	 * 阿里巴巴有色金属 采集
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_albb_ys(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			// 获取list 列表
			// String result = getListContent(ALBB_YS_MAP, typeId);
			String content = "";
			String urlSplit = ALBB_YS_MAP.get("url");
			String[] urlArray = urlSplit.split(",");
			for (String urlStr : urlArray) {
				content = HttpUtils.getInstance().httpGet(urlStr, ALBB_YS_MAP.get("charset"));
				if (StringUtils.isEmpty(content)) {
					continue;
				}
				String fix = "";
				if (urlStr.indexOf("s.1688.com") != -1) {
					fix = "S";
				}
				Integer start = content.indexOf(ALBB_YS_MAP.get("listStart" + fix));
				Integer end = content.indexOf(ALBB_YS_MAP.get("listEnd" + fix));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", result);
				// 分解list 为多个元素
				String[] str = result.split(ALBB_YS_MAP.get("split" + fix));

				// 检查日期是否对应今天
				String format = DateUtil.toString(new Date(), "MM月dd日");
				String formatZero = DateUtil.toString(new Date(), "M月d日");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
						if (s.indexOf("市场") != -1) {
							list.add(s);
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							resultTitle = resultTitle.replace("行情", "价格");
							String resultContent = "";
							if (urlStr.indexOf("s.1688.com") != -1) {
								resultContent = getContent(ALBB_YS_MAP, alink[1]);
							} else {
								resultContent = getContent(ALBB_YS_MAP, ALBB_YS_MAP.get("contentUrl") + alink[1]);
							}
							int finalStart;
							if (resultContent.contains("&nbsp;<br/><br/>")) {
								finalStart = resultContent.indexOf("&nbsp;<br/><br/>");
							} else if (resultContent.contains("<table border=\"1\"")) {
								finalStart = resultContent.indexOf("<table border=\"1\"");
							} else {
								finalStart = resultContent.indexOf(ALBB_YS_MAP.get("finalContentStart"));
							}

							int finalEnd = resultContent.indexOf(ALBB_YS_MAP.get("finalContentEnd"));
							resultContent = subContent(resultContent, finalStart, finalEnd);
							// 获取内容
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "ul", "li", "table", "th", "tr", "td")
											.addAttributes("td", "colspan"));
							/*
							 * String str1 = resultContent.substring(0,
							 * resultContent.indexOf("<tr")); String str2 =
							 * resultContent.substring(resultContent.indexOf(
							 * "</tr")+5,resultContent.length());
							 * out.put("resultContent", resultContent);
							 * resultContent = str1 + str2;
							 */

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> FB_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		FB_MAP.put("charset", "utf-8");
		FB_MAP.put("web", "http://www.f139.com");
		FB_MAP.put("url", "http://www.f139.com/news/list.do?");// 采集地址
		FB_MAP.put("40", "channelID=43,44&categoryID=135,48|" + "channelID=43,44&categoryID=135,49");
		FB_MAP.put("41",
				"channelID=43,44&categoryID=135,48|" + "channelID=43,44&categoryID=135,50|"
						+ "channelID=43,44&categoryID=135,49|" + "channelID=43,44&categoryID=135,53|"
						+ "channelID=43,44&categoryID=135,54");
		FB_MAP.put("52",
				"channelID=45,46,34&categoryID=135,48|" + "channelID=45,46,34&categoryID=135,50|"
						+ "channelID=45,46,34&categoryID=135,51|" + "channelID=45,46,34&categoryID=135,53|"
						+ "channelID=45,46,34&categoryID=135,54");
		FB_MAP.put("43", "channelID=45,46,34&categoryID=135,48|" + "channelID=45,46,34&categoryID=135,50|"
				+ "channelID=45,46,34&categoryID=135,49");
		FB_MAP.put("308", "channelID=45,46,34&categoryID=135,48|" + "channelID=45,46,34&categoryID=135,50");

		FB_MAP.put("listStart", "<h1 style=\" font-size:22px; color:#000000; margin:30px 50px;\"></h1>"); // 列表页开头
		FB_MAP.put("listEnd", "<p style=\"background:none; padding:0px 0 0 60px; float: left; \" align=\"center\">"); // 列表页结尾

		FB_MAP.put("split", "</li>");

		FB_MAP.put("contentStart", "<!-- 文章内容begin-->");
		FB_MAP.put("contentEnd", "<!-- 订阅 -->");

	}

	/**
	 * 富宝网 采集
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@RequestMapping
	public ModelAndView caiji_fb(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {
			String content = "";
			String urlSplit = FB_MAP.get("" + typeId);
			String[] urlArray = urlSplit.split("\\|");
			for (String urlStr : urlArray) {
				try {
					content = httpClientHtml(FB_MAP.get("url") + urlStr, FB_MAP.get("charset"));
				} catch (IOException e) {
					content = null;
				}
				if (content == null) {
					continue;
				}
				Integer start = content.indexOf(FB_MAP.get("listStart"));
				Integer end = content.indexOf(FB_MAP.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", result);
				// 分解list 为多个元素
				String[] str = result.split(FB_MAP.get("split"));

				// 检查日期是否对应今天
				String format = DateUtil.toString(new Date(), "MM月dd日");
				String formatZero = DateUtil.toString(new Date(), "M月d日");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
						if (typeId == 40) {
							if (s.indexOf("临沂市场废铜价格") != -1 || s.indexOf("江苏市场废铜价格") != -1
									|| s.indexOf("上海市场废铜价格") != -1 || s.indexOf("河北市场废铜") != -1) {
								list.add(s);
							}
						}
						if (typeId == 41) {
							if (s.indexOf("山东市场废铝") != -1 || s.indexOf("浙江市场废铝") != -1 || s.indexOf("上海市场废铝") != -1
									|| s.indexOf("河北市场废铝") != -1 || s.indexOf("广东市场废铝") != -1
									|| s.indexOf("重庆市场废铝") != -1 || s.indexOf("西安市场废铝") != -1) {
								list.add(s);
							}
						}
						if (typeId == 52) {
							if (s.indexOf("山东市场废电瓶") != -1 || s.indexOf("浙江市场废电瓶") != -1 || s.indexOf("广东市场废电瓶") != -1
									|| s.indexOf("河南市场废电瓶") != -1 || s.indexOf("四川市场废电瓶") != -1
									|| s.indexOf("重庆市场废电瓶") != -1 || s.indexOf("陕西市场废电瓶") != -1) {
								list.add(s);
							}
						}
						if (typeId == 43) {
							if (s.indexOf("山东临沂市场废锌") != -1 || s.indexOf("广东市场废锌") != -1 || s.indexOf("河北市场粗锌") != -1) {
								list.add(s);
							}
						}
						if (typeId == 308) {
							if (s.indexOf("山东市场废锡") != -1 || s.indexOf("广东市场废锡") != -1) {
								list.add(s);
							}
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							if (typeId == 41 || typeId == 40) {
								resultTitle = resultTitle.replace("市场", "地区");
								resultTitle = resultTitle.replace("行情", "");
								resultTitle = resultTitle.replace("山东临沂", "山东");
							}
							if (typeId == 52) {
								resultTitle = resultTitle.replace("价格行情", "市场动态");
							}
							if (typeId == 43) {
								if (resultTitle.indexOf("广东") != -1 || resultTitle.indexOf("河北") != -1) {
									resultTitle = resultTitle.replace("价格行情", "市场动态");
								}
							}
							if (typeId == 308) {
								if (resultTitle.indexOf("广东") != -1) {
									resultTitle = resultTitle.replace("价格行情", "市场动态");
								}
							}
							String resultContent = "";
							if (alink[5].indexOf("http://") != -1) {
								resultContent = httpClientHtml(alink[5], FB_MAP.get("charset"));
							} else {
								resultContent = httpClientHtml(FB_MAP.get("web") + alink[5], FB_MAP.get("charset"));
							}
							start = resultContent.indexOf(FB_MAP.get("contentStart"));
							end = resultContent.indexOf(FB_MAP.get("contentEnd"));
							resultContent = subContent(resultContent, start, end);
							if (StringUtils.isEmpty(resultContent)) {
								continue;
							}
							// 获取内容
							// resultContent = resultContent.toLowerCase();
							resultContent = Jsoup.clean(resultContent, Whitelist.none()
									.addTags("p", "table", "th", "tr", "br", "td").addAttributes("td", "rowspan"));
							if (resultContent.indexOf("<p> 富宝资讯") != -1 && resultContent.indexOf("<p>富宝资讯免责声明") != -1) {
								resultContent = subContent(resultContent, resultContent.indexOf("<p> 富宝资讯"),
										resultContent.indexOf("<p>富宝资讯免责声明"));
							} else
								if (resultContent.indexOf("<table") != -1 && resultContent.indexOf("</table>") != -1) {
								resultContent = subContent(resultContent, resultContent.indexOf("<table"),
										resultContent.indexOf("</table>") + 8);
							}
							resultContent = resultContent.replaceAll("富宝资讯", "ZZ91再生网");
							resultContent = resultContent.replaceAll("\\(作者：富宝铅研究小组\\)", "");
							resultContent = resultContent.replaceAll("\\(作者：富宝锌研究小组\\)", "");
							resultContent = resultContent.replaceAll("富宝", "");

							out.put("resultContent", resultContent);

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> ZHIJIN_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		ZHIJIN_MAP.put("charset", "utf-8");
		ZHIJIN_MAP.put("web", "http://www.zhijinsteel.com");
		ZHIJIN_MAP.put("url", "http://www.zhijinsteel.com/gangchang/cghz/index.html");// 采集地址
		ZHIJIN_MAP.put("279", "");

		ZHIJIN_MAP.put("listStart", "<ul class=\"list lh24 f14\">"); // 列表页开头
		ZHIJIN_MAP.put("listEnd", "上一页"); // 列表页结尾

		ZHIJIN_MAP.put("split", "<li>");

		ZHIJIN_MAP.put("contentStart", "<div class=\"content\" id='content'>");
		ZHIJIN_MAP.put("contentEnd", "</table>");

	}

	/**
	 * 志金网 采集
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_zhijin(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String result = getListContent(ZHIJIN_MAP, typeId);
			if (StringUtils.isEmpty(result)) {
				continue;
			}
			out.put("linkContent", result);
			// 分解list 为多个元素
			String[] str = result.split(ZHIJIN_MAP.get("split"));

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "MM月dd日");
			String formatZero = DateUtil.toString(new Date(), "M月d日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
					if (s.indexOf("生铁") != -1 || s.indexOf("废钢") != -1) {
						list.add(s);
					}
				}
			}
			if (list.size() == 0) {
				continue;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						resultTitle = resultTitle.replace("格汇总", "");
						resultTitle = resultTitle.replace("全国", "");
						String resultContent = "";
						resultContent = getContent(ZHIJIN_MAP, alink[1]);
						if (StringUtils.isEmpty(resultContent)) {
							continue;
						}
						// 获取内容
						resultContent = Jsoup.clean(resultContent, Whitelist.none()
								.addTags("p", "table", "th", "tr", "td", "b").addAttributes("td", "rowspan"));
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						if (resultTitle.indexOf("废钢") != -1) {
							out.put("result", doInsert(priceDO, true));
						} else {
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> ZS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		ZS_MAP.put("charset", "gb2312");
		ZS_MAP.put("url", "http://www.l-zzz.com");// 采集地址
		ZS_MAP.put("217", "http://www.l-zzz.com/plastic/list.jsp?nChannelID=278,"
				+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=250");
		ZS_MAP.put("233",
				"http://www.l-zzz.com/plastic/list.jsp?nChannelID=487,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=488,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=489,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=490");

		ZS_MAP.put("listStart",
				"<td width=\"67%\"  background=\"../images/09new/list_4.gif\" ><img src=\"../images/09new/list_3.gif\" width=\"36\" height=\"28\" /></td>"); // 列表页开头
		ZS_MAP.put("listEnd", "<td height=\"25\" align=\"center\" style=\"padding-top:4px;\">"); // 列表页结尾

		ZS_MAP.put("split", "<table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");

		ZS_MAP.put("contentStart", "<DIV class=NewsContent>");
		ZS_MAP.put("contentEnd", "（责任编辑");

	}

	/**
	 * 中塑资讯
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_lzzz(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String typeValue = ZS_MAP.get("" + typeId);
			String[] linkArray = typeValue.split(",");
			for (String url : linkArray) {
				content = HttpUtils.getInstance().httpGet(url, ZS_MAP.get("charset"), 20000, 20000);
				if (content == null) {
					return null;
				}
				Integer start = content.indexOf(ZS_MAP.get("listStart"));
				Integer end = content.indexOf(ZS_MAP.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", content);
				// 分解list 为多个元素
				String[] str = result.split(ZS_MAP.get("split"));

				// 检查日期是否对应今天
				String format = DateUtil.toString(new Date(), "MM-dd");
				String formatCN = DateUtil.toString(new Date(), "MM月dd日");
				String formatZero = DateUtil.toString(new Date(), "M-d");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
						if (typeId == 217) {
							if (s.indexOf("ABS/PS市场概况") != -1 || s.indexOf("PE市场概况") != -1 || s.indexOf("PP市场概况") != -1
									|| s.indexOf("【PP】") != -1 || s.indexOf("【PVC】") != -1 || s.indexOf("【HDPE】") != -1
									|| s.indexOf("【LLDPE】") != -1 || s.indexOf("【ABS/PS】") != -1) {
								list.add(s);
							}
						} else if (typeId == 233) {
							if (s.indexOf("PE市场收盘价") != -1 || s.indexOf("PP市场收盘价") != -1 || s.indexOf("香港PP美金") != -1
									|| s.indexOf("香港PE美金") != -1 || s.indexOf("香港PS美金") != -1
									|| s.indexOf("香港ABS美金") != -1) {
								list.add(s);
							}
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = "";
							if (typeId == 217) {
								resultTitle = Jsoup.clean(linkStr, Whitelist.none());
								resultTitle = resultTitle.replace("各地", "全国各地");
							} else if (typeId == 233) {
								resultTitle = Jsoup.clean(linkStr, Whitelist.none());
								String[] titleArray = resultTitle.split("日");
								if (titleArray.length > 1) {
									resultTitle = formatCN + titleArray[1];
									resultTitle = resultTitle.replace("香港PE美金报价", "香港PE市场收盘价");
								}
							}

							String resultContent = "";
							resultContent = getContent(ZS_MAP, ZS_MAP.get("url") + alink[1]);
							if (StringUtils.isEmpty(resultContent)) {
								continue;
							}
							// 获取内容
							// resultContent = resultContent.toLowerCase();
							resultContent = resultContent.replaceAll("&nbsp;", "");
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "table", "th", "tr", "td", "b", "br")
											.addAttributes("td", "rowspan", "colspan"));
							out.put("resultContent", resultContent);

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> METALSCRAP_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		METALSCRAP_MAP.put("charset", "GB2312");
		// 采集地址
		METALSCRAP_MAP.put("url", "http://www.metalscrap.com.cn/chinese/news.asp");

		METALSCRAP_MAP.put("listStart", "<td width=\"366\" valign=\"top\">"); // 列表页开头
		METALSCRAP_MAP.put("listEnd", "<td width=\"362\" valign=\"top\">"); // 列表页结尾
		METALSCRAP_MAP.put("contentStart", "<td height=\"20\"><hr style=\"color:#CCCCCC;\" /></td>"); // 内容开头
		METALSCRAP_MAP.put("contentEnd",
				"  <table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin:30px 0px\">");
		METALSCRAP_MAP.put("titleStart", "<td width=\"724\" valign=\"top\">"); // 标题开头
		METALSCRAP_MAP.put("titleEnd", "<!-- Baidu Button BEGIN -->");

	}

	/**
	 * 中国钢材网
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_metalscrap(Integer typeId, Map<String, Object> out) throws ParseException {
		do {

			String content = "";
			String url = METALSCRAP_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, METALSCRAP_MAP.get("charset"));
			if (StringUtils.isEmpty(content)) {
				break;
			}
			Integer start = content.indexOf(METALSCRAP_MAP.get("listStart"));
			Integer end = content.indexOf(METALSCRAP_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("<tr>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			// String format = DateUtil.toString(new Date(), "MM-dd");
			String newDate = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1) {
					if (s.contains("国内废铅锌") || s.contains("国内废不锈钢") || s.contains("LME市场报道：")
							|| s.contains("COMEX市场报道：")) {
						list.add(s);
					}
				}
			}
			if (list.size() < 1) {
				break;
			}
			for (String link : list) {
				String linkStr = getAlink(link);
				String[] alink = linkStr.split("\'");
				if (alink.length > 0) {
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					if (resultTitle.contains("废铅锌")) {
						resultTitle = newDate + "废铅锌市场分析预测";
					} else if (resultTitle.contains("国内废不锈钢")) {
						resultTitle = newDate + "废不锈钢市场分析预测";
					}
					String resultLink = "http://www.metalscrap.com.cn/chinese/" + alink[1];
					String resultContent = "";
					resultContent = HttpUtils.getInstance().httpGet(resultLink, METALSCRAP_MAP.get("charset"));
					if (resultTitle.contains("...")) {
						Integer tStart = resultContent.indexOf(METALSCRAP_MAP.get("titleStart"));
						Integer tEnd = resultContent.indexOf(METALSCRAP_MAP.get("titleEnd"));
						String tResult = resultContent.substring(tStart, tEnd);
						String[] title1 = tResult.split("</font>");
						String[] title2 = title1[0].split("<font size=\"4\" style=\"line-height:25px\">");
						resultTitle = title2[1];
					}
					Integer cStart = resultContent.indexOf(METALSCRAP_MAP.get("contentStart"));
					Integer cEnd = resultContent.indexOf(METALSCRAP_MAP.get("contentEnd"));
					resultContent = resultContent.substring(cStart, cEnd);
					out.put("resultContent", resultContent);
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("p", "br").addAttributes("td", "rowspan"));
					if (resultContent.contains("<p><br />")) {
						resultContent = resultContent.replace("<p><br />", "<p>");
					}
					if (resultTitle.contains("COMEX市场报道")) {
						resultContent = resultContent.replace("<BR>", "</p><p>");
						resultContent = resultContent.replace("<br />", "</p><p>");
					}
					PriceDO priceDO = new PriceDO();
					String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(typeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, false));
				}

			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> CNAL_METAL_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		CNAL_METAL_MAP.put("charset", "utf-8");
		// CNAL_METAL_MAP.put("url", "http://info.1688.com");// 采集地址
		CNAL_METAL_MAP.put("216", "http://market.cnal.com/other/index.shtml,"
				+ "http://market.cnal.com/other/index_2.shtml," + "http://market.cnal.com/other/index_3.shtml");

		CNAL_METAL_MAP.put("listStart", "<div id=\"marketlistbody\">"); // 列表页开头
		CNAL_METAL_MAP.put("listEnd", "<div class=\"epages\">"); // 列表页结尾

		CNAL_METAL_MAP.put("split", "</li>");

		CNAL_METAL_MAP.put("contentStart", "<div class=\"marketcontent\">");
		CNAL_METAL_MAP.put("contentEnd", "(责任编辑：");
	}

	@RequestMapping
	public ModelAndView caiji_cnal_metal(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String typeValue = CNAL_METAL_MAP.get("" + typeId);
			String[] linkArray = typeValue.split(",");
			for (String url : linkArray) {
				content = HttpUtils.getInstance().httpGet(url, CNAL_METAL_MAP.get("charset"), 20000, 20000);
				if (content == null) {
					return null;
				}
				Integer start = content.indexOf(CNAL_METAL_MAP.get("listStart"));
				Integer end = content.indexOf(CNAL_METAL_MAP.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", content);
				// 分解list 为多个元素
				String[] str = result.split(CNAL_METAL_MAP.get("split"));

				// 检查日期是否对应今天
				String formatDate = DateUtil.toString(new Date(), "yyyy-MM-dd");
				String format = DateUtil.toString(new Date(), "MM月dd日");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(formatDate) != -1) {
						if (s.indexOf("国际废旧金属现货行情") != -1) {
							list.add(s);
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							resultTitle = format + "国际废旧金属现货行情";
							String resultContent = "";
							/*
							 * String resultLink = alink[1];
							 * out.put("resultLink", resultLink);
							 */
							resultContent = getContent(CNAL_METAL_MAP, alink[1]);
							if (StringUtils.isEmpty(resultContent)) {
								continue;
							}
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "table", "th", "tr", "td", "b", "br")
											.addAttributes("td", "rowspan", "colspan"));
							out.put("resultContent", resultContent);

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> OMETAL_METAL_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		OMETAL_METAL_MAP.put("charset", "gb2312");
		// 测试用的地址
		// OMETAL_METAL_MAP.put("216",
		// "http://www.ometal.com/bin/new/more.asp?t=15&pos=1718642&act=next&page=5");
		OMETAL_METAL_MAP.put("216",
				"http://www.ometal.com/news_analyse.htm,"
						+ "http://www.ometal.com/bin/new/more.asp?t=15&pos=1719148&act=next&page=2,"
						+ "http://www.ometal.com/bin/new/more.asp?t=15&pos=1719047&act=next&page=3");
		OMETAL_METAL_MAP.put("listStart",
				"<table width=\"100%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=\"s9\">"); // 列表页开头
		OMETAL_METAL_MAP.put("listEnd", "下一页"); // 列表页结尾

		OMETAL_METAL_MAP.put("split1", "</tr>");
		OMETAL_METAL_MAP.put("split2", "<br>");

		OMETAL_METAL_MAP.put("contentStart", "<div id=\"fontzoom\">");
		OMETAL_METAL_MAP.put("contentEnd", "■电话:");
	}

	@RequestMapping
	public ModelAndView caiji_ometal_metal(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String typeValue = OMETAL_METAL_MAP.get("" + typeId);
			String[] linkArray = typeValue.split(",");
			for (String url : linkArray) {
				content = HttpUtils.getInstance().httpGet(url, OMETAL_METAL_MAP.get("charset"), 20000, 20000);
				if (content == null) {
					return null;
				}
				Integer start = content.indexOf(OMETAL_METAL_MAP.get("listStart"));
				Integer end = content.indexOf(OMETAL_METAL_MAP.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", content);
				// 分解list 为多个元素
				String[] str;
				if (url.equals("http://www.ometal.com/news_analyse.htm")) {
					str = result.split(OMETAL_METAL_MAP.get("split2"));
				} else {
					str = result.split(OMETAL_METAL_MAP.get("split1"));
				}
				// 检查日期是否对应今天
				String formatDate = DateUtil.toString(new Date(), "yyyy-M-d");
				String format = DateUtil.toString(new Date(), "MM月dd日");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(formatDate) != -1) {
						if (s.indexOf("废旧金属现货市场综述") != -1) {
							list.add(s);
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							resultTitle = format + "废旧金属现货市场综述（铜铝锌不锈钢）";
							String resultContent = "";
							resultContent = getContent(OMETAL_METAL_MAP, "http://www.ometal.com" + alink[1]);
							if (StringUtils.isEmpty(resultContent)) {
								continue;
							}
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "div", "table", "th", "tr", "td", "b", "br")
											.addAttributes("td", "rowspan", "colspan"));
							out.put("resultContent", resultContent);

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> IRON_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		IRON_MAP.put("charset", "gbk");
		IRON_MAP.put("url", "http://info.1688.com/tags_list/v6-l13649.html");// 采集地址
	}

	@RequestMapping
	public ModelAndView caiji_albb_iron(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {

			String content = "";
			try {
				content = httpClientHtml(IRON_MAP.get("url"), IRON_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf("<dl class=\"li23 listcontent1\">");
			Integer end = content.indexOf("<!--分页 start-->");
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("<li>");

			// 检查日期是否对应今天
			// String format = DateUtil.toString(new Date(), "yyyy-MM-09");
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1) {
					if (typeId == 66) {
						if (s.contains("全国铸造生铁价格") || s.contains("全国炼钢生铁价格")) {
							list.add(s);
						}
					} else if (typeId == 45) {
						if (s.contains("华东") || s.contains("华北") || s.contains("东北") || s.contains("中南及其它地区")
								|| s.contains("全国各地废钢价格行情汇总") || s.contains("重废") || s.contains("统料")
								|| s.contains("中废")) {
							list.add(s);
						}
					}

				}
			}
			if (list.size() < 1) {
				break;
			}
			for (String link : list) {
				Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");
				Matcher matcher = pattern.matcher(link);
				String linkStr = "";
				if (matcher.find()) {
					linkStr = matcher.group();
				}
				if (StringUtils.isEmpty(linkStr)) {
					break;
				}

				String[] alink = linkStr.split("\"");
				if (alink.length > 0) {
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					if (resultTitle.contains("全国各地废钢价格行情汇总")) {
						resultTitle = resultTitle.replace("行情汇总", "");
					}
					String resultLink = "http://info.1688.com" + alink[1];
					String resultContent = "";
					try {
						resultContent = httpClientHtml(resultLink, IRON_MAP.get("charset"));
					} catch (IOException e) {
					}
					Integer cStart = resultContent.indexOf("<div class=\"d-content\">");
					Integer cEnd = resultContent.indexOf("(责任编辑：");
					resultContent = resultContent.substring(cStart, cEnd);
					out.put("resultContent", resultContent);
					/*
					 * start = resultContent.indexOf("<table"); end =
					 * resultContent.indexOf("</table>"); resultContent =
					 * resultContent.substring(start, end+8);
					 */
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("div", "p", "ul", "li", "br", "table", "th", "tr", "td")
									.addAttributes("td", "rowspan"));
					if (!(resultTitle.contains("华东") || resultTitle.contains("华北") || resultTitle.contains("东北")
							|| resultTitle.contains("中南及其它地区") || resultTitle.contains("全国各地废钢价格行情汇总"))) {
						resultContent = resultContent.replace("<td>趋势图</td>", "");
					} else {
						resultContent = resultContent.replace("<br>", "");
						resultContent = resultContent.replace("<br />", "");
						resultContent = resultContent.replace("<p>", "<p style=\"text-align: center;\">");
						resultContent = resultContent.replace("</table>", "</table><p style=\"text-align: center;\">");
						resultContent = resultContent.replace("<table>", "</p><table>");
					}

					PriceDO priceDO = new PriceDO();
					String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(typeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					if (!(resultTitle.contains("华东") || resultTitle.contains("华北") || resultTitle.contains("东北")
							|| resultTitle.contains("中南及其它地区"))) {
						out.put("result", doInsert(priceDO, true));
					}
					out.put("result", doInsert(priceDO, false));
				}

			}
		} while (false);
		// return new ModelAndView("result");
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> PLASTIC_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		PLASTIC_MAP.put("charset", "gbk");
		PLASTIC_MAP.put("url",
				"http://www.l-zzz.com/plastic/list.jsp?nChannelID=260,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=255,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=254,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=259,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=257,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=261,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=256,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=258,"
						+ "http://www.l-zzz.com/plastic/list.jsp?nChannelID=262");
	}

	@RequestMapping
	public ModelAndView caiji_lzzz_plastic(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			// 获取list 列表
			String content = "";
			String urlSplit = PLASTIC_MAP.get("url");
			String[] urlArray = urlSplit.split(",");
			for (String urlStr : urlArray) {
				content = HttpUtils.getInstance().httpGet(urlStr, PLASTIC_MAP.get("charset"));
				if (content == null) {
					break;
				}
				Integer start = content.indexOf("<td align=\"left\" valign=\"top\" ");
				Integer end = content.indexOf("当前第1页");
				String result = content.substring(start, end);
				out.put("linkContent", result);
				String[] str = result.split("</table>");

				// 检查日期是否对应今天
				String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
				List<String> list = new ArrayList<String>();
				for (String s : str) {
					if (s.indexOf(format) != -1) {
						list.add(s);
					}
				}

				if (list.size() < 1) {
					break;
				}
				for (String link : list) {
					Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");
					Matcher matcher = pattern.matcher(link);
					String linkStr = "";
					if (matcher.find()) {
						linkStr = matcher.group();
					}
					if (StringUtils.isEmpty(linkStr)) {
						break;
					}

					String[] alink = linkStr.split("\"");
					String dateStr = DateUtil.toString(new Date(), "MM月dd日");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						int tempStart = resultTitle.indexOf("【");
						int tempEnd = resultTitle.indexOf("】");
						resultTitle = dateStr + resultTitle.substring(tempStart + 1, tempEnd) + "塑料市场价";
						String typeName = "";
						if (resultTitle.contains("东莞")) {
							typeId = 111;
						} else if (resultTitle.contains("北京")) {
							typeId = 112;
						} else if (resultTitle.contains("广州")) {
							typeId = 113;
						} else if (resultTitle.contains("上海")) {
							typeId = 115;
						} else if (resultTitle.contains("汕头")) {
							typeId = 118;
						} else if (resultTitle.contains("杭州")) {
							typeId = 119;
						} else if (resultTitle.contains("顺德")) {
							typeId = 120;
						} else if (resultTitle.contains("临沂")) {
							typeId = 121;
						} else if (resultTitle.contains("齐鲁化工城")) {
							typeId = 126;
						}
						typeName = priceCategoryService.queryTypeNameByTypeId(typeId);
						String resultLink = "http://www.l-zzz.com" + alink[1];
						String resultContent = "";
						resultContent = HttpUtils.getInstance().httpGet(resultLink, PLASTIC_MAP.get("charset"));
						Integer cStart = resultContent.indexOf("<DIV class=NewsContent>");
						Integer cEnd = resultContent.indexOf("（责任编辑：");
						resultContent = resultContent.substring(cStart, cEnd);
						out.put("resultContent", resultContent);
						start = resultContent.indexOf("<TABLE");
						end = resultContent.indexOf("</TABLE>");
						resultContent = resultContent.substring(start, end + 8);
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "li", "br", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));

						PriceDO priceDO = new PriceDO();

						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}

				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> RECYCLEDPLASTIC_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		RECYCLEDPLASTIC_MAP.put("charset", "gbk");
		RECYCLEDPLASTIC_MAP.put("url", "http://info.1688.com");// 采集地址
		RECYCLEDPLASTIC_MAP.put("98",
				"http://info.1688.com/nlist/n90.html," + "http://info.1688.com/nlist/n79.html,"
						+ "http://info.1688.com/nlist/n84.html," + "http://info.1688.com/nlist/n80.html,"
						+ "http://info.1688.com/nlist/n83.html," + "http://info.1688.com/nlist/n78.html");
		RECYCLEDPLASTIC_MAP.put("listStart", "<div class=\"content\">"); // 列表页开头
		RECYCLEDPLASTIC_MAP.put("listEnd", "<div class=\"module mod-paging\">"); // 列表页结尾

		RECYCLEDPLASTIC_MAP.put("split", "</li>");

		RECYCLEDPLASTIC_MAP.put("contentStart", "<div class=\"d-content\">");
		RECYCLEDPLASTIC_MAP.put("contentEnd", "(责任编辑：");
	}

	@RequestMapping
	public ModelAndView caiji_albb_recycledPlasticOne(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String typeValue = RECYCLEDPLASTIC_MAP.get("" + typeId);
			String[] linkArray = typeValue.split(",");
			for (String url : linkArray) {
				content = HttpUtils.getInstance().httpGet(url, RECYCLEDPLASTIC_MAP.get("charset"), 20000, 20000);
				if (content == null) {
					return null;
				}
				Integer start = content.indexOf(RECYCLEDPLASTIC_MAP.get("listStart"));
				Integer end = content.indexOf(RECYCLEDPLASTIC_MAP.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", content);
				// 分解list 为多个元素
				String[] str = result.split(RECYCLEDPLASTIC_MAP.get("split"));

				// 检查日期是否对应今天
				String format = DateUtil.toString(new Date(), "MM-dd");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(format) != -1) {

						if (s.indexOf("各地PE再生") != -1 || s.indexOf("各地PVC再生") != -1 || s.indexOf("各地PP再生") != -1
								|| s.indexOf("各地ABS再生") != -1 || s.indexOf("各地PET再生") != -1
								|| s.indexOf("各地PS再生") != -1) {
							list.add(s);
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							resultTitle = resultTitle.replace("各地", "");
							resultTitle = resultTitle.replace("最新报价", "价格");
							String resultContent = "";
							/*
							 * String resultLink = alink[1];
							 * out.put("resultLink", resultLink);
							 */
							resultContent = getContent(RECYCLEDPLASTIC_MAP, alink[1]);
							if (StringUtils.isEmpty(resultContent)) {
								continue;
							}
							// 获取内容
							// resultContent = resultContent.toLowerCase();
							resultContent = resultContent.replaceAll("&nbsp;", "");
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "table", "th", "tr", "td", "b", "br")
											.addAttributes("td", "rowspan", "colspan"));
							resultContent = resultContent.replace(" <td><b>趋势图</b></td>", "");
							resultContent = resultContent.replace(" <td>趋势图</td>", "");
							out.put("resultContent", resultContent);

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> RECYCLEDPLASTIC_MAP2 = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		RECYCLEDPLASTIC_MAP2.put("charset", "gbk");
		RECYCLEDPLASTIC_MAP2.put("url", "http://info.1688.com");// 采集地址
		RECYCLEDPLASTIC_MAP2.put("98",
				"http://s.1688.com/news/-455053D4ECC1A3CAD0B3A1BCDBB8F1.html?pageSize=20&postTime=3,"
						+ "http://s.1688.com/news/-50502D52B9DCC6C6CBE9C1A3CAD0B3A1BCDBB8F1.html?pageSize=20&postTime=3");
		RECYCLEDPLASTIC_MAP2.put("listStart", "<div id=\"offerList\" class=\"offerList\">"); // 列表页开头
		RECYCLEDPLASTIC_MAP2.put("listEnd", "<div id=\"p4poffer\">"); // 列表页结尾

		RECYCLEDPLASTIC_MAP2.put("split", "<div class=\"offer-content\">");

		RECYCLEDPLASTIC_MAP2.put("contentStart", "<div class=\"d-content\">");
		RECYCLEDPLASTIC_MAP2.put("contentEnd", "(责任编辑：");
	}

	@RequestMapping
	public ModelAndView caiji_albb_recycledPlasticTwo(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String typeValue = RECYCLEDPLASTIC_MAP2.get("" + typeId);
			String[] linkArray = typeValue.split(",");
			for (String url : linkArray) {
				content = HttpUtils.getInstance().httpGet(url, RECYCLEDPLASTIC_MAP2.get("charset"), 20000, 20000);
				if (content == null) {
					return null;
				}
				Integer start = content.indexOf(RECYCLEDPLASTIC_MAP2.get("listStart"));
				Integer end = content.indexOf(RECYCLEDPLASTIC_MAP2.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", content);
				// 分解list 为多个元素
				String[] str = result.split(RECYCLEDPLASTIC_MAP2.get("split"));

				// 检查日期是否对应今天
				String format = DateUtil.toString(new Date(), "MM-dd");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(format) != -1) {

						if (s.indexOf("EPS造粒市场价格") != -1 || s.indexOf("PP-R管破碎粒市场价格") != -1) {
							list.add(s);
						}
					}
				}
				if (list.size() == 0) {
					continue;
				}

				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split("\"");
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							if (resultTitle.indexOf("EPS") != -1) {
								resultTitle = resultTitle.replace("造粒市场价格", "再生塑料价格");
							} else {
								resultTitle = resultTitle.replace("管破碎粒市场价格", "再生塑料价格");
							}
							String resultContent = "";
							resultContent = getContent(RECYCLEDPLASTIC_MAP2, alink[1]);
							if (StringUtils.isEmpty(resultContent)) {
								continue;
							}
							// 获取内容
							// resultContent = resultContent.toLowerCase();
							resultContent = resultContent.replaceAll("&nbsp;", "");
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "table", "th", "tr", "td", "b", "br")
											.addAttributes("td", "rowspan", "colspan"));
							resultContent = resultContent.replace(" <td><b>趋势图</b></td>", "");
							resultContent = resultContent.replace(" <td>趋势图</td>", "");
							out.put("resultContent", resultContent);

							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> DISCUSS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		DISCUSS_MAP.put("charset", "gb2312");
		DISCUSS_MAP.put("url", "http://www.cs.com.cn/qhsc/zzqh/");// 采集地址
		DISCUSS_MAP.put("contentLink", "http://www.cs.com.cn/qhsc/zzqh/");
		DISCUSS_MAP.put("listStart", "<!-- 左侧 -->"); // 列表页开头
		DISCUSS_MAP.put("listEnd", "<!-- 翻页 -->"); // 列表页结尾
		DISCUSS_MAP.put("contentStart", "<!-- 正文 -->");
		DISCUSS_MAP.put("contentEnd", "<!-- 附件列表 -->");
		DISCUSS_MAP.put("split", "<li>");
		DISCUSS_MAP.put("splitLink", "\"");
	}

	@RequestMapping
	public ModelAndView caiji_cs_discussMorning(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {

			String content = "";
			try {
				content = httpClientHtml(DISCUSS_MAP.get("url"), DISCUSS_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(DISCUSS_MAP.get("listStart"));
			Integer end = content.indexOf(DISCUSS_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split(DISCUSS_MAP.get("split"));

			// 检查日期是否对应今天
			// String format = DateUtil.toString(new Date(), "yyyy-MM-09");
			String formatDate = DateUtil.toString(new Date(), "MM-dd");
			String newDate = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(formatDate) != -1) {
					if (typeId == 34) {
						// 页面有时大小写不一的时候
						if (s.contains("连塑") || s.contains("pvc") || s.contains("PVC") || s.contains("PTA")
								|| s.contains("pta")) {
							list.add(s);
						}
					} else if (typeId == 220) {
						if (s.contains("日胶") || s.contains("橡胶") || s.contains("沪胶")) {
							list.add(s);
						}
					}

				}
			}
			if (list.size() < 1) {
				break;
			}
			for (String link : list) {
				Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".*?>(.*?)</a>");
				Matcher matcher = pattern.matcher(link);
				String linkStr = "";
				if (matcher.find()) {
					linkStr = matcher.group();
				}
				if (StringUtils.isEmpty(linkStr)) {
					break;
				}

				String[] alink = linkStr.split(DISCUSS_MAP.get("splitLink"));
				if (alink.length > 0) {
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					if (resultTitle.contains("连塑")) {
						resultTitle = newDate + "塑料市场早间评论：" + resultTitle;
					} else if (resultTitle.contains("pvc") || resultTitle.contains("PVC")) {
						resultTitle = newDate + "PVC市场早间评论：" + resultTitle;
					} else if (resultTitle.contains("PTA") || resultTitle.contains("pta")) {
						resultTitle = newDate + "PTA市场早间评论：" + resultTitle;
					} else {
						resultTitle = newDate + "橡胶市场早间评论：" + resultTitle;
					}
					String resultLink = DISCUSS_MAP.get("url") + alink[1].substring(2, alink[1].length());
					String resultContent = "";
					try {
						resultContent = httpClientHtml(resultLink, DISCUSS_MAP.get("charset"));
					} catch (IOException e) {
					}
					Integer cStart = resultContent.indexOf(DISCUSS_MAP.get("contentStart"));
					Integer cEnd = resultContent.indexOf(DISCUSS_MAP.get("contentEnd"));
					resultContent = resultContent.substring(cStart, cEnd - 1);
					out.put("resultContent", resultContent);
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("div", "p", "br").addAttributes("td", "rowspan"));
					PriceDO priceDO = new PriceDO();
					String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(typeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, false));
				}

			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> DISCUSSEVENING_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		DISCUSSEVENING_MAP.put("charset", "utf-8");
		DISCUSSEVENING_MAP.put("url", "http://www.rdqh.com/pinglun.aspx");// 采集地址
		DISCUSSEVENING_MAP.put("contentLink", "http://www.rdqh.com");
		DISCUSSEVENING_MAP.put("listStart", "<div class=\"t3\"></div>"); // 列表页开头
		DISCUSSEVENING_MAP.put("listEnd", "上一页"); // 列表页结尾
		DISCUSSEVENING_MAP.put("contentStart", "<div id=\"boxvideo\" style=\"text-align: center\">");
		DISCUSSEVENING_MAP.put("contentEnd", "本报告");
		DISCUSSEVENING_MAP.put("split", "<div class=\"tt\">");
		DISCUSSEVENING_MAP.put("splitLink", "\'");
	}

	@RequestMapping
	public ModelAndView caiji_cs_discussEvening(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {

			String content = "";
			String strContent = "";
			// 检查日期是否对应今天
			String formatDate = DateUtil.toString(new Date(), "yyyy-M-d");
			// String format = DateUtil.toString(new Date(), "MM-dd");
			String newDate = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();
			String typeValue = DISCUSSEVENING_MAP.get("url");
			String[] linkArray = typeValue.split(",");
			for (String url : linkArray) {
				content = HttpUtils.getInstance().httpGet(url, DISCUSSEVENING_MAP.get("charset"), 20000, 20000);
				if (content == null) {
					continue;
				}
				Integer start = content.indexOf(DISCUSSEVENING_MAP.get("listStart"));
				Integer end = content.indexOf(DISCUSSEVENING_MAP.get("listEnd"));
				String result = content.substring(start, end);
				out.put("linkContent", result);
				String[] str = result.split(DISCUSSEVENING_MAP.get("split"));

				for (String s : str) {
					if (s.indexOf(formatDate) != -1) {
						if (typeId == 34) {
							// 有一个问题，怎么查看字母时不分大小写
							if (s.contains("连塑") || s.contains("pvc") || s.contains("PVC") || s.contains("pta")
									|| s.contains("PTA")) {
								list.add(s);
							}
						} else if (typeId == 220) {
							if (s.contains("日胶") || s.contains("橡胶") || s.contains("沪胶")) {
								list.add(s);
							}
						}

					}
				}

				if (list.size() < 1) {
					continue;
				}
			}
			for (String strList : list) {
				strContent += strList;
			}
			/*
			 * 标题没有抓取信息标签而内容有的情况 主要做法：将网页内容全部抓取，根据信息标签查看内容是否有，如果有，则将该篇放如该信息标签中
			 */
			if (typeId == 34) {
				while (!(strContent.contains("连塑") && (strContent.contains("PVC") || strContent.contains("pvc"))
						&& (strContent.contains("PTA") || strContent.contains("pta")))) {
					List<Map<String, String>> listcontent = httpClientAllConten(DISCUSSEVENING_MAP, formatDate);
					String resultTitle = "";
					String resultContent = "";
					if (!(strContent.contains("连塑"))) {
						for (int i = 0; i < listcontent.size(); i++) {
							Map<String, String> allContentMap = listcontent.get(i);
							resultTitle = allContentMap.get("resultTitle");
							resultContent = allContentMap.get(resultTitle);
							if (resultContent.contains("连塑")) {
								resultTitle = newDate + "塑料市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
								strContent += "连塑";
								break;
							}
						}
					}
					if (!((strContent.contains("PVC") || strContent.contains("pvc")))) {
						for (int i = 0; i < listcontent.size(); i++) {
							Map<String, String> allContentMap = listcontent.get(i);
							resultTitle = allContentMap.get("resultTitle");
							resultContent = allContentMap.get(resultTitle);
							if (resultContent.contains("PVC") || resultContent.contains("pvc")) {
								resultTitle = newDate + "PVC市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
								strContent += "PVC";
								break;
							}
						}
					}
					if (!((strContent.contains("PTA") || strContent.contains("pta")))) {
						for (int i = 0; i < listcontent.size(); i++) {
							Map<String, String> allContentMap = listcontent.get(i);
							resultTitle = allContentMap.get("resultTitle");
							resultContent = allContentMap.get(resultTitle);
							if (resultContent.contains("PTA") || resultContent.contains("pta")) {
								resultTitle = newDate + "PTA市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
								strContent += "PTA";
								break;
							}
						}
					}
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("div", "p", "ul", "li", "br", "table", "th", "tr", "td")
									.addAttributes("td", "rowspan"));
					if (resultContent.contains("免责声明") || resultContent.contains("免责声明：")) {
						resultContent = resultContent.replace("免责声明：", "");
						resultContent = resultContent.replace("免责声明", "");
					}
					PriceDO priceDO = new PriceDO();
					String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(typeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, false));
				}
			}

			for (String link : list) {
				String linkStr = getAlink(link);
				String[] alink = linkStr.split(DISCUSSEVENING_MAP.get("splitLink"));
				if (alink.length > 0) {
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					if (resultTitle.contains("连塑")) {
						resultTitle = newDate + "塑料市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
					} else if (resultTitle.contains("PVC") || resultTitle.contains("pvc")) {
						resultTitle = newDate + "PVC市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
					} else if (resultTitle.contains("PTA") || resultTitle.contains("pta")) {
						resultTitle = newDate + "PTA市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
					} else {
						resultTitle = newDate + "橡胶市场晚间评论：" + resultTitle.substring(5, resultTitle.length());
					}
					String resultLink = DISCUSSEVENING_MAP.get("contentLink") + alink[1];
					String resultContent = "";
					resultContent = HttpUtils.getInstance().httpGet(resultLink, DISCUSSEVENING_MAP.get("charset"));
					Integer cStart = resultContent.indexOf(DISCUSSEVENING_MAP.get("contentStart"));
					Integer cEnd = resultContent.indexOf(DISCUSSEVENING_MAP.get("contentEnd"));
					resultContent = resultContent.substring(cStart, cEnd - 1);
					out.put("resultContent", resultContent);
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("div", "p", "ul", "li", "br", "table", "th", "tr", "td")
									.addAttributes("td", "rowspan"));
					if (resultContent.contains("免责声明") || resultContent.contains("免责声明：")) {
						resultContent = resultContent.replace("免责声明：", "");
						resultContent = resultContent.replace("免责声明", "");
					}
					PriceDO priceDO = new PriceDO();
					String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(typeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, false));
				}

			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> LME_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		LME_MAP.put("charset", "gb2312");
		LME_MAP.put("url", "http://www.ometal.com/bin/new/searchkey.asp?type=lme&newsort=13");// 采集地址
		LME_MAP.put("listStart", "搜索结果："); // 列表页开头
		LME_MAP.put("listEnd", "上一页"); // 列表页结尾
		LME_MAP.put("contentStart", "<div id=\"fontzoom\">");
		LME_MAP.put("contentEnd", "OMETAL.COM)");
	}

	@RequestMapping
	public ModelAndView caiji_ometal_LME(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {

			String content = "";
			try {
				content = httpClientHtml(LME_MAP.get("url"), LME_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(LME_MAP.get("listStart"));
			Integer end = content.indexOf(LME_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("<tr>");

			// 检查日期是否对应今天
			String formatDate = DateUtil.toString(new Date(), "yyyy-M-d");
			String newDate = DateUtil.toString(new Date(), "MM月dd日");
			int week = DateUtil.getDayOfWeekForDate(new Date());
			String otherDate = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -2), "yyyy-M-d");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (week == 2) {
					if (s.indexOf(otherDate) != -1) {
						if (s.contains("LME基本金属收盘行情")) {
							list.add(s);
						}
					}
				}
				if (s.indexOf(formatDate) != -1) {
					if ((s.contains("LME基本金属最新报价")
							&& (s.contains("9:50") || s.contains("11:50") || s.contains("14:50")))
							|| s.contains("LME基本金属收盘行情")) {
						list.add(s);
					}
				}
			}
			if (list.size() < 1) {
				break;
			}

			for (String link : list) {
				String linkStr = getAlink(link);
				String[] alink = linkStr.split("\"");
				if (alink.length > 0) {
					String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
					if (resultTitle.contains("9:50")) {
						resultTitle = newDate + "伦敦LME基本金属最新报价" + "9:50";
					} else if (resultTitle.contains("11:50")) {
						resultTitle = newDate + "伦敦LME基本金属最新报价" + "11:50";
					} else if (resultTitle.contains("14:50")) {
						resultTitle = newDate + "伦敦LME基本金属最新报价" + "14:50";
					} else {
						String date = dateFormat(resultTitle);
						resultTitle = "伦敦LME基本金属收盘行情" + date;
					}
					out.put("resultTitle", resultTitle);
					String resultLink = "http://www.ometal.com" + alink[1];
					String resultContent = "";
					try {
						resultContent = httpClientHtml(resultLink, LME_MAP.get("charset"));
					} catch (IOException e) {
					}
					Integer cStart = resultContent.indexOf(LME_MAP.get("contentStart"));
					Integer cEnd = resultContent.indexOf(LME_MAP.get("contentEnd"));
					resultContent = resultContent.substring(cStart, cEnd - 1);
					out.put("resultContent", resultContent);
					resultContent = Jsoup.clean(resultContent,
							Whitelist.none().addTags("div", "p", "br").addAttributes("td", "rowspan"));
					resultContent = resultContent.replace("\n ", "");
					resultContent = resultContent.replace("(全球金属网", "");
					// resultContent = resultContent.replace("<br /> <br />",
					// "<br /><br />");
					if (!resultContent.contains("<p>") && resultContent.contains("标题时间为北京时间")) {
						resultContent = resultContent.replace("<br /><br />", "<br />");
						resultContent = resultContent.replace("<div>", "<div><p>");
						resultContent = resultContent.replace("<br />", "</p><p>");
						resultContent = resultContent.replace("</div>", "</p></div>");
					} else if (!resultContent.contains("标题时间为北京时间")) {
						resultContent = resultContent.replace("<br /><br /><br /><br /><br />", "");
					}
					PriceDO priceDO = new PriceDO();
					String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

					priceDO.setTitle(resultTitle);
					priceDO.setTypeId(typeId);
					priceDO.setTags(typeName);
					priceDO.setContent(resultContent);

					// 执行插入
					out.put("result", doInsert(priceDO, false));
					// System.out.println(week);
				}

			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	// 第四期开始
	final static Map<String, String> OILEXPRESS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		OILEXPRESS_MAP.put("charset", "GBK");
		// 采集地址
		OILEXPRESS_MAP.put("url", "http://www.l-zzz.com/shiyou/list.jsp?nChannelID=405");
		OILEXPRESS_MAP.put("contentUrl", "http://www.l-zzz.com");
		OILEXPRESS_MAP.put("listStart", "<td align=\"left\" valign=\"top\" style=\"border:#CCCCCC 1px solid;\">"); // 列表页开头
		OILEXPRESS_MAP.put("listEnd", "<td height=\"25\" align=\"center\" style=\"padding-top:4px;\">"); // 列表页结尾

		OILEXPRESS_MAP.put("contentStart", "<DIV class=NewsContent>");
		OILEXPRESS_MAP.put("contentEnd", "（责任编辑");

	}

	/**
	 * 油价快报
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	@RequestMapping
	public ModelAndView caiji_lzzz_oilexpress(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException {
		do {
			String content = "";
			try {
				String url = OILEXPRESS_MAP.get("url");
				content = httpClientHtml(url, OILEXPRESS_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(OILEXPRESS_MAP.get("listStart"));
			Integer end = content.indexOf(OILEXPRESS_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</table>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1) {
					list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						resultTitle = resultTitle.substring(5, resultTitle.length());
						String resultContent = "";
						String newUrl = OILEXPRESS_MAP.get("contentUrl") + alink[3];
						resultContent = getContent(OILEXPRESS_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						if (resultContent == null) {
							continue;
						}
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						resultContent = resultContent.replace("电子盘北京时间", "截至：电子盘北京时间");
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> OILCITY_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		OILCITY_MAP.put("charset", "GBK");
		// 采集地址
		OILCITY_MAP.put("url", "http://www.l-zzz.com/shiyou/list.jsp?nChannelID=1084");
		OILCITY_MAP.put("contentUrl", "http://www.l-zzz.com");
		OILCITY_MAP.put("listStart", "<td align=\"left\" valign=\"top\" style=\"border:#CCCCCC 1px solid;\">"); // 列表页开头
		OILCITY_MAP.put("listEnd", "<td height=\"25\" align=\"center\" style=\"padding-top:4px;\">"); // 列表页结尾

		OILCITY_MAP.put("contentStart", "<DIV class=NewsContent>");
		OILCITY_MAP.put("contentEnd", "（责任编辑");

	}

	/**
	 * 油价快报
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	@RequestMapping
	public ModelAndView caiji_lzzz_oilcity(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException {
		do {
			String content = "";
			try {
				String url = OILCITY_MAP.get("url");
				content = httpClientHtml(url, OILCITY_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(OILCITY_MAP.get("listStart"));
			Integer end = content.indexOf(OILCITY_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</table>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1) {
					list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						String newUrl = OILCITY_MAP.get("contentUrl") + alink[3];
						resultContent = getContent(OILCITY_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> SHFE_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		SHFE_MAP.put("charset", "GBK");
		SHFE_MAP.put("69", "http://www.shfe.com.cn/statements/delaymarket_zn.html");
		SHFE_MAP.put("71", "http://www.shfe.com.cn/statements/delaymarket_al.html");
		SHFE_MAP.put("72", "http://www.shfe.com.cn/statements/delaymarket_cu.html");
		SHFE_MAP.put("206", "http://www.shfe.com.cn/statements/delaymarket_rb.html");

		SHFE_MAP.put("contentStart",
				"<table width=\"100%\" border=\"1\" bordercolor=\"#000000\" class=\"mytable\" align=\"center\">");
		SHFE_MAP.put("contentEnd", "</table>");

	}

	@RequestMapping
	public ModelAndView caiji_shfe(Integer typeId, Map<String, Object> out) throws ParseException, URISyntaxException {
		do {
			String content = "";
			try {
				String url = SHFE_MAP.get(String.valueOf(typeId));
				content = httpClientHtml(url, SHFE_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			String contentResult = "";
			Integer contentStart = content.indexOf(SHFE_MAP.get("contentStart"));
			Integer contentEnd = content.indexOf(SHFE_MAP.get("contentEnd"));
			contentResult = subContent(content, contentStart, contentEnd + 8);
			if (StringUtils.isEmpty(contentResult)) {
				break;
			}
			out.put("linkContent", content);

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			String formatCN = DateUtil.toString(new Date(), "MM月dd日");

			if (contentResult.indexOf(format) != -1) {
				String resultTitle = "";
				int indexStart = contentResult.indexOf(format);
				String time = contentResult.substring(indexStart + 11, indexStart + 13);
				if (!StringUtils.isNumber(time)) {
					time = "09:30";
				} else {
					if (Integer.parseInt(time) >= 11) {
						time = Integer.parseInt(time) - 1 + ":30";
					} else {
						time = "09:30";
					}
				}
				if (typeId == 69) {
					resultTitle = formatCN + "沪锌最新报价" + time;
				} else if (typeId == 71) {
					resultTitle = formatCN + "沪铝最新报价" + time;
				} else if (typeId == 72) {
					resultTitle = formatCN + "沪铜最新报价" + time;
				} else if (typeId == 206) {
					resultTitle = formatCN + "沪钢最新报价" + time;
				}
				String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

				contentResult = contentResult.replace("\n", "");
				contentResult = contentResult.replace("<tr>     <td colspan=\"7\">", "<tr><td colspan=\"7\">");
				Integer start = contentResult.indexOf("<td colspan=\"7\">");
				Integer end = contentResult.indexOf("</font></a>");
				String resultContentFirst = contentResult.substring(0, start - 4);
				String resultContentEnd = contentResult.substring(end, contentResult.length());
				String resultContent = resultContentFirst + resultContentEnd;
				resultContent = Jsoup.clean(resultContent, Whitelist.none()
						.addTags("p", "table", "th", "tr", "td", "b", "br").addAttributes("td", "rowspan"));
				out.put("resultContent", resultContent);
				resultContent = resultContent.replace("<td> </td>", "");
				resultContent = resultContent.replace("<td>【走势图】</td>", "");
				PriceDO priceDO = new PriceDO();
				priceDO.setTitle(resultTitle);
				priceDO.setTypeId(typeId);
				priceDO.setTags(typeName);
				priceDO.setContent(resultContent);

				// 执行插入
				out.put("result", doInsert(priceDO, true));
			}
		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> HUATONG_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		HUATONG_MAP.put("charset", "gb2312");
		// 采集地址
		HUATONG_MAP.put("url",
				"http://www.ometal.com/bin/new/searchkey.asp?type=%C9%CF%BA%A3%BB%AA%CD%A8%B2%AC%D2%F8%BD%BB%D2%D7%CA%D0%B3%A1");
		HUATONG_MAP.put("contentUrl", "http://www.ometal.com");
		HUATONG_MAP.put("listStart", "搜索结果："); // 列表页开头
		HUATONG_MAP.put("listEnd", "上一页"); // 列表页结尾

		HUATONG_MAP.put("contentStart", "<div id=\"fontzoom\">");
		HUATONG_MAP.put("contentEnd", "OMETAL.COM)");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_ometal_huatong(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String url = HUATONG_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, HUATONG_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(HUATONG_MAP.get("listStart"));
			Integer end = content.indexOf(HUATONG_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</tr>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-M-d");
			String formatDate = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1) {
					list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						if (resultTitle.contains("10:00")) {
							resultTitle = formatDate + "华通贵金属价格" + "(上午)";
						} else {
							resultTitle = formatDate + "华通贵金属价格" + "(下午)";
						}
						String resultContent = "";
						String newUrl = HUATONG_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(HUATONG_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "br", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						// resultContent =
						// resultContent.replace("上海华通铂银交易市场有色金属现货行情", "");
						resultContent = resultContent.replace("\n", "");
						resultContent = resultContent.replace("  ", " ");
						resultContent = resultContent.replace("(全球金属网", "");
						resultContent = resultContent.replace("<br />上海华通铂银交易市场贵金属现货价 <br />", "");
						resultContent = resultContent.replace("华通铂银现货白银定盘价和结算平均价", " 单位：银(元/千克)  钯，铑，钌，铱（元/克）");
						if (resultContent.contains("昨结算价")) {
							resultContent = resultContent.replace("昨结算价", "结算价");
						}
						resultContent = resultContent.replace("<br />品名 规格 价格范围 结算价 交割地 产地 <br />", "");
						resultContent = resultContent.replace("  ", " ");
						resultContent = resultContent.replace("国内定盘价", " 价格");
						resultContent = resultContent.replace("结算平均价", "昨结算价");
						resultContent = resultContent.replace("<div>", "<table><tr><td>");
						resultContent = resultContent.replace("<br />", "</td></tr><tr><td>");
						resultContent = resultContent.replace(" ", "</td><td>");
						resultContent = resultContent.replace("</div>", "</td></tr></table>");
						resultContent = resultContent.replace("<td></td>", "");
						resultContent = resultContent.replace("<tr></tr>", "");
						resultContent = resultContent.replace("<td>单位：银(元/千克)</td><td>钯，铑，钌，铱（元/克）</td>",
								"<td colspan=\"6\"> <p> 单位：银(元/千克)&nbsp; 钯，铑，钌，铱（元/克）</p> </td>");
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> FEIJS_SCRAP_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		FEIJS_SCRAP_MAP.put("charset", "gb2312");
		// 采集地址
		FEIJS_SCRAP_MAP.put("url", "http://www.feijs.com/news/feitiejiage.asp");
		FEIJS_SCRAP_MAP.put("contentUrl", "http://www.feijs.com/news/");
		FEIJS_SCRAP_MAP.put("listStart", "<td align=\"center\" bgcolor=\"#FFFFFF\">"); // 列表页开头
		FEIJS_SCRAP_MAP.put("listEnd", "<td height=\"25\" align=\"right\" bgcolor=\"#F1F1F1\">"); // 列表页结尾

		FEIJS_SCRAP_MAP.put("contentStart", "<td align=\"center\" bgcolor=\"#FFFFFF\">");
		FEIJS_SCRAP_MAP.put("contentEnd", "相关文章：");

		FEIJS_SCRAP_MAP.put("split", "<tr>");
		FEIJS_SCRAP_MAP.put("splitLink", "\"");
	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_feijs_scrap(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String url = FEIJS_SCRAP_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, FEIJS_SCRAP_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(FEIJS_SCRAP_MAP.get("listStart"));
			Integer end = content.indexOf(FEIJS_SCRAP_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split(FEIJS_SCRAP_MAP.get("split"));

			// 检查日期是否对应今天
			String formatDate = DateUtil.toString(new Date(), "yyyy-M-d");
			String format = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(formatDate) != -1) {
					if (s.contains("废铁") && !s.contains("佛山地区"))
						list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			String resultContent = "";
			String threeResultContent = "";
			int tag = 0;

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split(FEIJS_SCRAP_MAP.get("splitLink"));
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						if (resultTitle.contains("江西")) {
							resultTitle = format + "江西地区废铁价格";
						} else if (resultTitle.contains("山西")) {
							resultTitle = format + "山西地区废铁价格";
						} else if (resultTitle.contains("湖北")) {
							resultTitle = format + "湖北地区废铁价格";
						} else if (resultTitle.contains("佛山")) {
							resultTitle = format + "佛山地区废铁价格";
						} else if (resultTitle.contains("山东")) {
							resultTitle = format + "山东地区废铁价格";
						} else if (resultTitle.contains("广东")) {
							resultTitle = format + "广东地区废铁价格";
						} else if (resultTitle.contains("河南")) {
							resultTitle = format + "河南地区废铁价格";
						} else if (resultTitle.contains("四川")) {
							resultTitle = format + "四川地区废铁价格";
						} else if (resultTitle.contains("江苏")) {
							resultTitle = format + "江苏地区废铁价格";
						} else if (resultTitle.contains("浙江")) {
							resultTitle = format + "浙江地区废铁价格";
						} else if (resultTitle.contains("上海")) {
							resultTitle = format + "上海地区废铁价格";
						}

						String newUrl = FEIJS_SCRAP_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(FEIJS_SCRAP_MAP, newUrl);
						if (StringUtils.isEmpty(resultContent)) {
							continue;
						}
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "br", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						int resultStart = resultContent.indexOf("<div>");
						int resultEnd = resultContent.indexOf("</div>");
						resultContent = resultContent.substring(resultStart, resultEnd + 6);
						while (resultContent.lastIndexOf("<div>") != 0) {
							resultContent = resultContent.substring(5, resultContent.length());
							if (resultContent.indexOf("<div>") != 0) {
								int starttag = resultContent.indexOf("<div>");
								resultContent = resultContent.replace(resultContent.substring(0, starttag), "");
							}
						}
						String[] splitContent = resultContent.split("；");
						for (int j = 0; j < splitContent.length; j++) {
							StringBuilder sb = new StringBuilder(splitContent[j]);
							for (int i = 0; i < sb.length(); i++) {
								if (sb.charAt(i) >= '0' && sb.charAt(i) <= '9') {
									sb.insert(i, " ");
									break;
								}
							}
							splitContent[j] = sb.toString();
						}
						String lastContent = "";
						int titleSta = resultTitle.indexOf("日");
						int titleEn = resultTitle.indexOf("废铁");
						String title = resultTitle.substring(titleSta + 1, titleEn);
						if (resultTitle.contains("江苏") || resultTitle.contains("浙江") || resultTitle.contains("上海")) {
							for (String strcont : splitContent) {
								strcont = strcont.replace(">", ">" + title + " ");
								strcont = strcont.replace("</div>" + title + " ", "</div>");
								lastContent += strcont;
							}
							threeResultContent += lastContent;
							tag++;
							if (tag == 3) {
								resultTitle = format + "江浙沪地区废铁价格";
								resultContent = threeResultContent;
								resultContent = resultContent.replace("\n", "");
								resultContent = resultContent.replace("<br />", "<br>");
								resultContent = resultContent.replace("</div><div>", "<br>");
								resultContent = resultContent.replace("/", " ");
								resultContent = resultContent.replace("< div>", "</div>");
								resultContent = resultContent.replace("<div>",
										"<table><tr><td>地区</td><td>品种</td><td>最低价</td><td>最高价</td></tr><tr><td>");
								resultContent = resultContent.replace("<br>", "</td></tr><tr><td>");
								resultContent = resultContent.replace(" ", "</td><td>");
								resultContent = resultContent.replace("</div>", "</td></tr></table>");
								resultContent = resultContent.replace("<td></td>", "");
								resultContent = resultContent.replace("<tr></tr>", "");
							} else {
								continue;
							}
						} else {
							for (String strcont : splitContent) {
								lastContent += strcont;
								resultContent = lastContent;
							}
							resultContent = resultContent.replace("\n", "");

							resultContent = resultContent.replace("<br />", "<br>");
							resultContent = resultContent.replace("<br/>", "<br>");
							resultContent = resultContent.replace("/", " ");
							resultContent = resultContent.replace("< div>", "</div>");
							resultContent = resultContent.replace("<div>",
									"<table><tr><td>地区</td><td>品种</td><td>最低价</td><td>最高价</td></tr><tr><td>" + title
											+ "</td><td>");
							resultContent = resultContent.replace("<br>", "</td></tr><tr><td>" + title + "</td><td>");
							resultContent = resultContent.replace(" ", "</td><td>");
							resultContent = resultContent.replace("</div>", "</td></tr></table>");
							resultContent = resultContent.replace("<td></td>", "");
							resultContent = resultContent.replace("<tr></tr>", "");
						}
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> HUATONGSPOT_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		HUATONGSPOT_MAP.put("charset", "gb2312");
		// 采集地址
		HUATONGSPOT_MAP.put("url", "http://www.ometal.com/bin/new/searchkey.asp?type=%BB%AA%CD%A8%D3%D0%C9%AB");
		HUATONGSPOT_MAP.put("contentUrl", "http://www.ometal.com");
		HUATONGSPOT_MAP.put("listStart", "搜索结果："); // 列表页开头
		HUATONGSPOT_MAP.put("listEnd", "上一页"); // 列表页结尾

		HUATONGSPOT_MAP.put("contentStart", "<div id=\"fontzoom\">");
		HUATONGSPOT_MAP.put("contentEnd", "OMETAL.COM)");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_ometal_huatongspot(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String url = HUATONGSPOT_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, HUATONGSPOT_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(HUATONGSPOT_MAP.get("listStart"));
			Integer end = content.indexOf(HUATONGSPOT_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</tr>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-M-d");
			String formatDate = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1) {
					list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						if (resultTitle.contains("上午")) {
							resultTitle = formatDate + "华通有色金属价格" + "(上午)";
						} else if (resultTitle.contains("下午")) {
							resultTitle = formatDate + "华通有色金属价格" + "(下午)";
						}
						String resultContent = "";
						String newUrl = HUATONGSPOT_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(HUATONGSPOT_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "br", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));

						if (resultContent.indexOf("单位:元/吨") != -1) {
							resultContent = resultContent.replace("单位:元/吨", "单位：元/吨");
						}
						int resultEnd = resultContent.indexOf("单位：元/吨");
						resultContent = resultContent.replace(resultContent.substring(5, resultEnd), "");
						resultContent = resultContent.replace("(全球金属网", "");
						resultContent = resultContent
								.replace("------------------------------------------------------------------", "");
						resultContent = resultContent.replace("&nbsp;", "");
						resultContent = resultContent.replace("\n", "");
						resultContent = resultContent.replace("  ", " ");
						resultContent = resultContent.replace("<div>", "<table><tr><td>");
						resultContent = resultContent.replace("<br />", "</td></tr><tr><td>");
						resultContent = resultContent.replace(" ", "</td><td>");
						resultContent = resultContent.replace("</div>", "</td></tr></table>");
						resultContent = resultContent.replace("<td></td>", "");
						resultContent = resultContent.replace("<tr></tr>", "");
						resultContent = resultContent.replace("<td>单位：元/吨</td>", "<td colspan=\"8\">单位：元/吨</td>");
						out.put("resultContent", resultContent);

						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> WASTEPLASTICS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		WASTEPLASTICS_MAP.put("charset", "utf-8");
		// 采集地址
		WASTEPLASTICS_MAP.put("url", "http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-2");
		WASTEPLASTICS_MAP.put("contentUrl", "http://china.worldscrap.com/modules/cn/plastic/");
		WASTEPLASTICS_MAP.put("listStart", "<td width=\"625\" valign=\"top\">"); // 列表页开头
		WASTEPLASTICS_MAP.put("listEnd", "alt=\"上一页\""); // 列表页结尾

		WASTEPLASTICS_MAP.put("contentStart", "<td class=\"line_height_new\">");
		WASTEPLASTICS_MAP.put("contentEnd", "（worldscrap.cn）");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_worldscrap_wasteplastics(Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String url = WASTEPLASTICS_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, WASTEPLASTICS_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(WASTEPLASTICS_MAP.get("listStart"));
			Integer end = content.indexOf(WASTEPLASTICS_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("</tr>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "MM-dd");
			List<String> list = new ArrayList<String>();

			for (String s : str) {
				if (s.indexOf(format) != -1) {
					if (s.contains("浙江") || s.contains("河北") || s.contains("山东") || s.contains("湖南") || s.contains("广东")
							|| s.contains("江苏")) {
						list.add(s);
					}
				}
			}
			if (list.size() == 0) {
				break;
			}

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						String newUrl = WASTEPLASTICS_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(WASTEPLASTICS_MAP, newUrl);
						if (StringUtils.isEmpty(resultContent)) {
							continue;
						}
						resultContent = resultContent.replaceAll("<td class=\"line_height_new\">", "<p>");
						resultContent = resultContent.concat("</p>");
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "p", "ul", "br", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						if (resultContent.contains("世界再生网讯")) {
							resultContent = resultContent.replace("世界再生网讯", "");
						}
						out.put("resultContent", resultContent);
						String typeName = "";
						Integer typeId = 0;
						if (resultTitle.contains("浙江")) {
							typeId = 127;
						} else if (resultTitle.contains("河北")) {
							typeId = 138;
						} else if (resultTitle.contains("山东")) {
							typeId = 132;
						} else if (resultTitle.contains("广东")) {
							typeId = 130;
						} else if (resultTitle.contains("江苏")) {
							typeId = 128;
						} else {
							typeId = 133;
						}
						typeName = priceCategoryService.queryTypeNameByTypeId(typeId);
						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> YUYAOPLASTICS_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		YUYAOPLASTICS_MAP.put("charset", "gbk");
		// 采集地址
		YUYAOPLASTICS_MAP.put("url", "http://info.1688.com/tags_list/v5003220-l15736.html");
		YUYAOPLASTICS_MAP.put("contentUrl", "http://info.1688.com");
		YUYAOPLASTICS_MAP.put("listStart", "<dl class=\"li23 listcontent1\">"); // 列表页开头
		YUYAOPLASTICS_MAP.put("listEnd", "<div id=\"pagelist\" style="); // 列表页结尾

		YUYAOPLASTICS_MAP.put("contentStart", "<div class=\"d-content\">");
		YUYAOPLASTICS_MAP.put("contentEnd", "(责任编辑：");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_albb_yuyaoplastics(Integer typeId, Map<String, Object> out) throws ParseException {
		do {
			String content = "";
			String url = YUYAOPLASTICS_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, YUYAOPLASTICS_MAP.get("charset"));
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(YUYAOPLASTICS_MAP.get("listStart"));
			Integer end = content.indexOf(YUYAOPLASTICS_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split("<li>");

			// 检查日期是否对应今天
			String format = DateUtil.toString(new Date(), "yyyy-MM-dd");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(format) != -1) {
					if (s.contains("TPU最新报价") || s.contains("PVC最新报价") || s.contains("ABS最新报价") || s.contains("PC最新报价")
							|| s.contains("PMMA最新报价") || s.contains("EVA最新报价") || s.contains("PP最新报价")
							|| s.contains("HIPS最新报价") || s.contains("PET最新报价") || s.contains("GPPS最新报价")
							|| s.contains("LDPE最新报价") || s.contains("LLDPE最新报价") || s.contains("HDPE最新报价")) {
						list.add(s);
					}
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split("\"");
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						if (resultTitle.contains("工程塑料:")) {
							resultTitle = resultTitle.replace("工程塑料:", "");
						}
						String resultContent = "";
						String newUrl = YUYAOPLASTICS_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(YUYAOPLASTICS_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent, Whitelist.none()
								.addTags("ul", "br", "li", "table", "th", "tr", "td").addAttributes("td", "rowspan"));
						out.put("resultContent", resultContent);
						resultContent = resultContent.replace("<td>趋势图</td>", "");
						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> PETRIFACTION_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		PETRIFACTION_MAP.put("charset", "GBK");

		// 采集地址 正式
		PETRIFACTION_MAP.put("url", "http://s.1688.com/news/-B3F6B3A7BCDB.html?pageSize=20&postTime=3");
		PETRIFACTION_MAP.put("contentUrl", "");
		PETRIFACTION_MAP.put("listStart", "<div id=\"offerList\" class=\"offerList\">"); // 列表页开头
		PETRIFACTION_MAP.put("listEnd", "<div id=\"sw_mod_pagination\" class=\"sw-mod-pagination\">"); // 列表页结尾

		PETRIFACTION_MAP.put("contentStart", "<div class=\"d-content\">");
		PETRIFACTION_MAP.put("contentEnd", "(责任编辑");
		PETRIFACTION_MAP.put("split", "<div class=\"offer-content\">");
		PETRIFACTION_MAP.put("splitLink", "\"");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_albb_petrifaction(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {
			String content = "";
			try {
				String url = PETRIFACTION_MAP.get("url");
				content = httpClientHtml(url, PETRIFACTION_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(PETRIFACTION_MAP.get("listStart"));
			Integer end = content.indexOf(PETRIFACTION_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split(PETRIFACTION_MAP.get("split"));

			// 检查日期是否对应今天
			String formatDate = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(formatDate) != -1) {
					if (s.contains("企业PVC<font color=red>出厂价") || s.contains("企业ABS<font color=red>出厂价")
							|| s.contains("企业PP<font color=red>出厂价") || s.contains("LLDPE<font color=red>出厂价")
							|| s.contains("企业LDPE<font color=red>出厂价") || s.contains("企业HDPE<font color=red>出厂价")
							|| s.contains("企业GPPS<font color=red>出厂价") || s.contains("企业HIPS<font color=red>出厂价")) {
						list.add(s);
					}
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split(PETRIFACTION_MAP.get("splitLink"));
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						String newUrl = PETRIFACTION_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(PETRIFACTION_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent, Whitelist.none()
								.addTags("ul", "br", "li", "table", "th", "tr", "td").addAttributes("td", "rowspan"));
						out.put("resultContent", resultContent);
						resultContent = resultContent.replace("<td>趋势图</td>", "");
						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> FEIJIUPAGER_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		FEIJIUPAGER_MAP.put("charset", "gb2312");
		// 采集地址
		FEIJIUPAGER_MAP.put("url", "http://news.feijiu.net/news-p-cid42.56.45-.html");
		FEIJIUPAGER_MAP.put("contentUrl", "");
		FEIJIUPAGER_MAP.put("listStart", "<div class=\"zixun_wk\">"); // 列表页开头
		FEIJIUPAGER_MAP.put("listEnd", "<div id=\"ctl00_ContentPlaceHolder1_AspNetPager1\" class=\"anpager\">"); // 列表页结尾

		FEIJIUPAGER_MAP.put("contentStart", " 编辑：");
		FEIJIUPAGER_MAP.put("contentEnd", "<span class=\"jiathis_txt\">分享到：</span>");
		FEIJIUPAGER_MAP.put("split", "<li>");
		FEIJIUPAGER_MAP.put("splitLink", "\"");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_feijiu_pager(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {
			String content = "";
			try {
				String url = FEIJIUPAGER_MAP.get("url");
				content = httpClientHtml(url, FEIJIUPAGER_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(FEIJIUPAGER_MAP.get("listStart"));
			Integer end = content.indexOf(FEIJIUPAGER_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split(FEIJIUPAGER_MAP.get("split"));

			// 检查日期是否对应今天
			String formatDate = DateUtil.toString(new Date(), "yyyy-M-d");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(formatDate) != -1) {
					list.add(s);
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split(FEIJIUPAGER_MAP.get("splitLink"));
					if (alink.length > 0) {
						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						String newUrl = FEIJIUPAGER_MAP.get("contentUrl") + alink[1];
						resultContent = getContent(FEIJIUPAGER_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent,
								Whitelist.none().addTags("div", "ul", "br", "li", "table", "th", "tr", "td")
										.addAttributes("td", "rowspan"));
						resultContent = resultContent.replace("\n", "");
						resultContent = resultContent.replace(" ", "");
						resultContent = resultContent.replace("<div></div>", "");
						int resultstart = resultContent.indexOf("<div>");
						resultContent = resultContent.substring(resultstart, resultContent.length());
						out.put("resultContent", resultContent);
						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> DOMESTICRUBBER_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		DOMESTICRUBBER_MAP.put("charset", "GBK");
		// 采集地址
		DOMESTICRUBBER_MAP.put("url", "http://www.l-zzz.com/chemical/list.jsp?nChannelID=1992");
		DOMESTICRUBBER_MAP.put("contentUrl", "http://www.l-zzz.com");
		DOMESTICRUBBER_MAP.put("listStart", "<td align=\"left\" valign=\"top\" style=\"border:#CCCCCC 1px solid;\">"); // 列表页开头
		DOMESTICRUBBER_MAP.put("listEnd", "<td height=\"25\" align=\"center\" style=\"padding-top:4px;\">"); // 列表页结尾

		DOMESTICRUBBER_MAP.put("contentStart", "<DIV class=NewsContent>");
		DOMESTICRUBBER_MAP.put("contentEnd", "（责任编辑");
		DOMESTICRUBBER_MAP.put("split", "</table>");
		DOMESTICRUBBER_MAP.put("splitLink", "\"");

	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_lzzz_domesticrubber(Integer typeId, Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {
			String content = "";
			try {
				String url = DOMESTICRUBBER_MAP.get("url");
				content = httpClientHtml(url, DOMESTICRUBBER_MAP.get("charset"));
			} catch (IOException e) {
				content = null;
			}
			if (content == null) {
				break;
			}
			Integer start = content.indexOf(DOMESTICRUBBER_MAP.get("listStart"));
			Integer end = content.indexOf(DOMESTICRUBBER_MAP.get("listEnd"));
			String result = content.substring(start, end);
			out.put("linkContent", result);
			String[] str = result.split(DOMESTICRUBBER_MAP.get("split"));

			// 检查日期是否对应今天
			String formatDate = DateUtil.toString(new Date(), "yyyy-MM-dd");
			String format = DateUtil.toString(new Date(), "MM月dd日");
			List<String> list = new ArrayList<String>();
			for (String s : str) {
				if (s.indexOf(formatDate) != -1) {
					list.add(s);
					if (list.size() > 7) {
						break;
					}
				}
			}
			if (list.size() == 0) {
				break;
			}

			String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);

			for (String aStr : list) {
				String linkStr = getAlink(aStr);
				if (StringUtils.isEmpty(linkStr)) {
					continue;
				} else {
					String[] alink = linkStr.split(DOMESTICRUBBER_MAP.get("splitLink"));
					if (alink.length > 0) {
						String resultTitle = format + Jsoup.clean(linkStr, Whitelist.none());
						String resultContent = "";
						String newUrl = DOMESTICRUBBER_MAP.get("contentUrl") + alink[3];
						resultContent = getContent(DOMESTICRUBBER_MAP, newUrl);
						// 获取内容
						// resultContent = resultContent.toLowerCase();
						resultContent = Jsoup.clean(resultContent, Whitelist.none()
								.addTags("p", "ul", "li", "table", "th", "tr", "td").addAttributes("td", "rowspan"));
						resultContent = resultContent.replace("<p></p>", "");
						out.put("resultContent", resultContent);
						PriceDO priceDO = new PriceDO();
						priceDO.setTitle(resultTitle);
						priceDO.setTypeId(typeId);
						priceDO.setTags(typeName);
						priceDO.setContent(resultContent);

						// 执行插入
						out.put("result", doInsert(priceDO, false));
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	final static Map<String, String> EACHCOUNTRY_MAP = new HashMap<String, String>();

	static {
		// httpget 使用的编码 不然会有乱码
		EACHCOUNTRY_MAP.put("charset", "GBK");
		// 采集地址
		EACHCOUNTRY_MAP.put("url",
				"http://baojia.feijiu.net/price-p-cid-bjcid3.95-dqid-.html,"
						+ "http://baojia.feijiu.net/price-p-cid-bjcid3.94-dqid-.html,"
						+ "http://baojia.feijiu.net/price-p-cid-bjcid3.92-dqid-.html,"
						+ "http://baojia.feijiu.net/price-p-cid-bjcid3.96-dqid-.html");
		EACHCOUNTRY_MAP.put("contentUrl", "");
		EACHCOUNTRY_MAP.put("listStart", "<div class=\"l_newsmaindetails\">"); // 列表页开头
		EACHCOUNTRY_MAP.put("listEnd", "<div id=\"ctl00_ContentPlaceHolder1_pagerTop\""); // 列表页结尾

		EACHCOUNTRY_MAP.put("contentStart", "<div class=\"l_newsdetails\"");
		EACHCOUNTRY_MAP.put("contentEnd", "<div class=\"l_newsmainbom\">");
		EACHCOUNTRY_MAP.put("split", "<li>");
		EACHCOUNTRY_MAP.put("splitLink", "\'");
	}

	/**
	 * 
	 * @param typeId
	 * @param out
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping
	public ModelAndView caiji_feijiu_eachcountry(Map<String, Object> out)
			throws ParseException, URISyntaxException, ClientProtocolException, IOException {
		do {
			String content = "";
			String urlSplit = EACHCOUNTRY_MAP.get("url");
			String[] urlArray = urlSplit.split(",");
			for (String urlStr : urlArray) {
				content = HttpUtils.getInstance().httpGet(urlStr, EACHCOUNTRY_MAP.get("charset"));
				if (content == null) {
					continue;
				}
				Integer start = content.indexOf(EACHCOUNTRY_MAP.get("listStart"));
				Integer end = content.indexOf(EACHCOUNTRY_MAP.get("listEnd"));
				String result = subContent(content, start, end);
				if (StringUtils.isEmpty(result)) {
					continue;
				}
				out.put("linkContent", result);
				// 分解list 为多个元素
				String[] str = result.split(EACHCOUNTRY_MAP.get("split"));

				// 检查日期是否对应今天
				String formatDate = DateUtil.toString(new Date(), "yyyy-MM-dd");
				List<String> list = new ArrayList<String>();

				for (String s : str) {
					if (s.indexOf(formatDate) != -1) {
						list.add(s);
					}
				}
				if (list.size() == 0) {
					continue;
				}

				for (String aStr : list) {
					String linkStr = getAlink(aStr);
					if (StringUtils.isEmpty(linkStr)) {
						continue;
					} else {
						String[] alink = linkStr.split(EACHCOUNTRY_MAP.get("splitLink"));
						if (alink.length > 0) {
							String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
							resultTitle = resultTitle.replace("最新市场价", "市场价格");
							resultTitle = resultTitle.replace("最新废纸价格", "废纸市场价格");
							String resultContent = "";
							String newUrl = EACHCOUNTRY_MAP.get("contentUrl") + alink[1];

							resultContent = getContent(EACHCOUNTRY_MAP, newUrl);

							// 获取内容
							resultContent = Jsoup.clean(resultContent,
									Whitelist.none().addTags("p", "ul", "li", "table", "th", "tr", "td")
											.addAttributes("td", "rowspan"));
							int resultStart = resultContent.indexOf("<table>");
							int resultEnd = resultContent.indexOf("<p></p>");
							resultContent = resultContent.substring(resultStart, resultEnd);
							out.put("resultContent", resultContent);
							Integer typeId = 0;
							if (resultTitle.contains("国内日废")) {
								typeId = 27;
							} else if (resultTitle.contains("国内美废")) {
								typeId = 28;
							} else if (resultTitle.contains("国外")) {
								typeId = 29;
							} else if (resultTitle.contains("国内欧废")) {
								typeId = 26;
							}
							String typeName = priceCategoryService.queryTypeNameByTypeId(typeId);
							PriceDO priceDO = new PriceDO();
							priceDO.setTitle(resultTitle);
							priceDO.setTypeId(typeId);
							priceDO.setTags(typeName);
							priceDO.setContent(resultContent);

							// 执行插入
							out.put("result", doInsert(priceDO, false));
						}
					}
				}
			}

		} while (false);
		return new ModelAndView("zz91/auto/caiji/caiji_result");
	}

	// @RequestMapping
	// public ModelAndView caiji_kms88(Integer typeId, Map<String, Object> out)
	// {
	// do {
	//
	// } while (false);
	// return new ModelAndView("zz91/auto/caiji/caiji_result");
	// }

	/**
	 * 插入报价 通用接口
	 * 
	 * @param priceDO
	 * @return
	 * @throws ParseException
	 */
	private Integer doInsert(PriceDO priceDO, Boolean isUpDown) throws ParseException {
		// 标题 MM月dd日 格式构建
		priceDO.setTitle(getDateFormatTitle(priceDO.getTitle()));
		String priceSearchKey = null;
		String zaobaoSearchKeyString = null;
		String wanbaoSearchKeyString = null;
		String code = priceService.queryPriceTtpePlasticOrMetal(priceDO.getTypeId());
		if (StringUtils.isNotEmpty(code)) {
			if ("废金属".equals(code)) {
				priceSearchKey = "金属";
				zaobaoSearchKeyString = "金属早参";
				wanbaoSearchKeyString = "金属晚报";
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

		// 标签构建tags
		List<String> list = IKAnalzyerUtils.getAnalzyerList(priceDO.getTitle());
		Set<String> sList = new HashSet<String>();
		for (String s : list) {
			String str = s.toUpperCase();
			sList.add(str);
		}
		for (String string : sList) {
			if (string.indexOf("月") == -1 && string.indexOf("日") == -1 && !StringUtils.isNumber(string)
					&& string.length() > 1 && string.indexOf(".") == -1 && string.indexOf("年") == -1) {
				priceDO.setTags(priceDO.getTags() + "," + string);
			}
		}

		// 插入辅助类别

		Integer parentId = priceCategoryService.queryParentIdById(priceDO.getTypeId());
		while (parentId != null && parentId != 5 && parentId != 6) {
			parentId = priceCategoryService.queryParentIdById(parentId);
		}
		if (parentId != null) {
			if (parentId == 5) {
				parentId = 3;
			} else if (parentId == 6) {
				parentId = 4;
			}
			List<PriceCategoryDO> newsCategory = priceCategoryService.queryPriceCategoryByParentId(parentId);
			String tag = priceDO.getTags();
			String[] tags = tag.split(",");
			String tmpStr = "";
			for (String str : tags) {
				for (PriceCategoryDO catdo : newsCategory) {

					if (parentId == 3) {
						if (str.contains("浙江") || str.contains("上海") || str.contains("江苏") || str.contains("沪")
								|| str.contains("无锡")) {
							priceDO.setAssistTypeId(53);
							break;
						} else if (str.contains("佛山") || str.contains("广州")) {
							priceDO.setAssistTypeId(180);
							break;
						} else if (str.contains("河南")) {
							priceDO.setAssistTypeId(58);
							break;
						} else if (str.contains("西安")) {
							priceDO.setAssistTypeId(253);
							break;
						} else if (str.contains("昆明")) {
							priceDO.setAssistTypeId(252);
							break;
						} else if (str.contains("沈阳")) {
							priceDO.setAssistTypeId(254);
							break;
						} else if (str.contains("济南")) {
							priceDO.setAssistTypeId(314);
							break;
						} else if (str.contains("武汉")) {
							priceDO.setAssistTypeId(262);
							break;
						} else if (str.equals("临沂") || str.equals("山东")) {
							tmpStr = tmpStr + str;
							if (str.contains("临沂")) {
								priceDO.setAssistTypeId(55);
							} else if (str.contains("山东")) {
								priceDO.setAssistTypeId(314);
							}
							if (tmpStr.contains("临沂") && tmpStr.contains("山东")) {
								priceDO.setAssistTypeId(55);
								tmpStr = "";
							}
							break;
						} else if (str.contains("天津") || str.contains("山东")) {
							tmpStr = tmpStr + str;
							if (str.contains("天津")) {
								priceDO.setAssistTypeId(239);
							} else if (str.contains("山东")) {
								priceDO.setAssistTypeId(314);
							}
							if (tmpStr.contains("天津") && tmpStr.contains("山东")) {
								priceDO.setAssistTypeId(181);
								tmpStr = "";
							}
							break;
						} else if (str.contains("华东") || str.contains("华北")) {
							priceDO.setAssistTypeId(179);
							break;
						}
					}
					if (parentId == 4) {
						if (str.equals("HDPE")) {
							priceDO.setAssistTypeId(295);
							break;
						} else if (str.equals("LLDPE")) {
							priceDO.setAssistTypeId(304);
							break;
						} else if (str.equals("LDPE")) {
							priceDO.setAssistTypeId(292);
							break;
						} else if (str.equals("PET")) {
							priceDO.setAssistTypeId(290);
							break;
						} else if (str.equals("ABS") || str.equals("PS")) {
							tmpStr = tmpStr + str;
							if (str.equals("ABS")) {
								priceDO.setAssistTypeId(296);
							} else if (str.equals("PS")) {
								priceDO.setAssistTypeId(294);
							}
							if (tmpStr.contains("ABS") && tmpStr.contains("PS")) {
								priceDO.setAssistTypeId(313);
								tmpStr = "";
							}
							break;
						}
					}
					if (str.contains(catdo.getName())) {
						priceDO.setAssistTypeId(catdo.getId());
						break;
					}
				}
			}
		}
		// 去除多余的空格
		priceDO.setContent(priceDO.getContent().replace("&nbsp;", ""));
		priceDO.setContent(priceDO.getContent().replace("　", ""));
		priceDO.setContent(priceDO.getContent().replace("<p>\n\t&nbsp;</p>\n", ""));

		// br换行处理
		priceDO.setContent(priceDO.getContent().replace("<br>", "<br>　　"));
		priceDO.setContent(priceDO.getContent().replace("<br/>", "<br/>　　"));
		priceDO.setContent(priceDO.getContent().replace("<br />", "<br />　　"));

		if (isUpDown) {
			code = priceCategoryService.queryTypeNameByTypeId(priceDO.getTypeId());
			priceDO.setContent(priceDO.getContent() + priceService.buildTemplateContent(priceSearchKey,
					zaobaoSearchKeyString, wanbaoSearchKeyString, code));
		} else {
			priceDO.setContent(priceDO.getContent() + priceService.queryContentByPageEngine(priceSearchKey,
					zaobaoSearchKeyString, wanbaoSearchKeyString));
		}

		// 判断重复添加报价
		if (priceService.forbidDoublePub(priceDO.getTitle(), null)) {
			return 0;
		}

		priceDO.setGmtCreated(new Date());
		priceDO.setGmtOrder(new Date());

		Integer impress = priceService.insertPrice(priceDO);

		if (impress != null && impress > 0) {
			// 日志系统 记录发布报价相关信息 mongo日志
			LogUtil.getInstance().mongo("admin-auto", PRICE_OPERTION, null,
					"{'id':'" + impress + "','date':'" + DateUtil.toString(new Date(), DATE_FORMAT) + "'}");

			// 文本日志 监控mongo与文本日志数据丢失情况
			LogUtil.getInstance().log("admin-auto", PRICE_OPERTION, null,
					"{'id':'" + impress + "','date':'" + DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
		}
		if (isUpDown) {
			// 添加报价成功，添加数据进入关系表
			priceTemplateService.insert(impress);
		}
		return impress;
	}

	private String getListContent(Map<String, String> map, Integer typeId) {
		String content = "";
		try {
			String url = map.get("url") + map.get("" + typeId);
			try {
				content = httpClientHtml(url, map.get("charset"));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			content = null;
		}
		if (content == null) {
			return null;
		}
		Integer start = content.indexOf(map.get("listStart"));
		Integer end = content.indexOf(map.get("listEnd"));
		String result = subContent(content, start, end);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		return result;
	}

	private String getContent(Map<String, String> map, String url) {
		String content = "";
		content = HttpUtils.getInstance().httpGet(url, map.get("charset"));
		if (content == null) {
			return null;
		}
		Integer start = content.indexOf(map.get("contentStart"));
		Integer end = content.indexOf(map.get("contentEnd"));
		content = subContent(content, start, end);
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		return content;
	}

	private String subContent(String content, Integer start, Integer end) {
		if (StringUtils.isEmpty(content) || start >= end) {
			return null;
		}
		String result = content.substring(start, end);
		return result;
	}

	private String getAlink(String str) {
		str = str.trim();
		Integer start = str.indexOf("<a");
		Integer end = str.indexOf("</a>");
		str = subContent(str, start, end + 4);
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		return str;
	}

	@RequestMapping
	public void index(Map<String, Object> out) {

	}

	private String httpClientHtml(String url, String strCharset)
			throws URISyntaxException, ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpProtocolParams.setUserAgent(httpclient.getParams(),
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		String charset = strCharset;
		// if (null != params && params.length >= 1) {
		// charset = params[0];
		// }
		HttpGet httpget = new HttpGet();
		String content = "";
		httpget.setURI(new java.net.URI(url));
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
			content = EntityUtils.toString(entity, charset);
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return content;
	}

	// 读取网页中list全部的内容
	private List<Map<String, String>> httpClientAllConten(Map<String, String> map, String formatDate)
			throws URISyntaxException, ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		List<Map<String, String>> listcontent = new ArrayList<Map<String, String>>();
		HttpProtocolParams.setUserAgent(httpclient.getParams(),
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		String charset = map.get("charset");
		// if (null != params && params.length >= 1) {
		// charset = params[0];
		// }
		HttpGet httpget = new HttpGet();
		String content = "";
		String typeValue = map.get("url");

		List<String> list = new ArrayList<String>();

		String[] linkArr = typeValue.split(",");

		for (String url : linkArr) {
			httpget.setURI(new java.net.URI(url));
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
				content = EntityUtils.toString(entity, charset);
				httpget.abort();
				httpclient.getConnectionManager().shutdown();
			}
			Integer start = content.indexOf(map.get("listStart"));
			Integer end = content.indexOf(map.get("listEnd"));
			String result = content.substring(start, end);
			String[] str = result.split(map.get("split"));

			for (String s : str) {
				if (s.indexOf(formatDate) != -1) {
					list.add(s);
				}
			}
			if (list.size() < 1) {
				return null;
			}
		}
		String resultContent = "";
		for (String link : list) {
			String linkStr = getAlink(link);
			String[] alink = linkStr.split(map.get("splitLink"));
			if (alink.length > 0) {
				String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
				String contentLink = "";
				for (String trueLink : alink) {
					if (trueLink.contains("</a>")) {
						trueLink = trueLink.replace("</a>", "");
					}
					if (trueLink.contains("/")) {
						contentLink = trueLink;
					}
				}
				String resultLink = urlFormat(map.get("contentLink") + contentLink);

				try {
					resultContent = httpClientHtml(resultLink, map.get("charset"));
				} catch (IOException e) {
				}
				Integer cStart = resultContent.indexOf(map.get("contentStart"));
				Integer cEnd = resultContent.indexOf(map.get("contentEnd"));
				resultContent = resultContent.substring(cStart, cEnd);
				Map<String, String> allContentMap = new HashMap<String, String>();
				allContentMap.put(resultTitle, resultContent);
				allContentMap.put("resultTitle", resultTitle);
				listcontent.add(allContentMap);
			}
		}
		return listcontent;
	}

	private String getDateFormatTitle(String title) {
		do {
			if (title.indexOf("日") == -1 || title.indexOf("月") == -1) {
				break;
			}
			if (!StringUtils.isNumber(title.substring((title.indexOf("日")) - 1, title.indexOf("日")))
					&& !StringUtils.isNumber(title.substring((title.indexOf("月")) - 1, title.indexOf("月")))) {
				break;
			}
			String[] strArray = title.split("日");
			String[] resultArray = strArray[0].split("月");
			for (int i = 0; i < resultArray.length; i++) {
				if (resultArray[i].length() == 1) {
					resultArray[i] = "0" + resultArray[i];
				} else if (resultArray[i].length() > 2) { // 判断如果日期在后面则直接返回title。开发者：周宗坤
															// 修改日期：2013-09-11
					String months = resultArray[i].substring(resultArray[i].length() - 2, resultArray[i].length());
					String day = resultArray[i + 1];
					if (resultArray[i + 1].length() == 1) {
						day = "0" + resultArray[i + 1];
					}
					if (!StringUtils.isNumber(months)) {
						months = "0" + resultArray[i].substring(resultArray[i].length() - 1, resultArray[i].length());
						title = resultArray[i].substring(0, resultArray[i].length() - 1) + months + "月" + day + "日";
						return title;
					}
					return resultArray[i] + "月" + day + "日";
				}
			}
			String result = "";
			if (resultArray.length == 2 && strArray.length >= 2) {
				result = resultArray[0] + "月" + resultArray[1] + "日" + strArray[1];
			}
			if (strArray.length > 2) {
				int i = 0;
				do {
					if (i >= strArray.length) {
						break;
					}
					if (i > 1) {
						result = result + "日" + strArray[i];
					}
					i++;
				} while (true);
			}
			return result;
		} while (false);
		return title;
	}

	private String urlFormat(String url) {
		String newUrl = url;
		if (newUrl.contains("/./")) {
			newUrl = newUrl.replace("/./", "");
		}
		return newUrl;
	}

	private String dateFormat(String title) {
		do {
			if (title.indexOf("日") == -1 || title.indexOf("月") == -1) {
				break;
			}
			int monthIndex = title.indexOf("月");
			int dayIndex = title.indexOf("日");
			String month = title.substring(monthIndex - 2, monthIndex);
			String day = title.substring(dayIndex - 2, dayIndex);
			if (!month.matches("-?\\d+\\.?\\d*")) {
				month = title.substring(monthIndex - 1, monthIndex);
			}
			if (!day.matches("-?\\d+\\.?\\d*")) {
				day = "0" + title.substring(dayIndex - 1, dayIndex);
			}
			String result = month + "月" + day + "日";
			return result;
		} while (false);
		return title;
	}

	public static void main(String[] args) throws HttpException, IOException, URISyntaxException {
		// System.out.println(HttpUtils.getInstance().httpGet("http://www.f139.com/news/list.do?channelID=43,44",
		// "utf-8"));
		// System.out.println(HttpUtils.getInstance().httpGet("http://www.f139.com",
		// "utf-8"));
		// NameValuePair
		// NameValuePair[] data = { new NameValuePair("channelID", "43,44"), new
		// NameValuePair("categoryID", "135,48")};
		// String content =
		// HttpUtils.getInstance().httpPost("http://www.f139.com/news/list.do?",
		// data, HttpUtils.CHARSET_DEFAULT);
		// System.out.println(content);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpProtocolParams.setUserAgent(httpclient.getParams(),
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		String charset = "UTF-8";
		// if (null != params && params.length >= 1) {
		// charset = params[0];
		// }
		HttpGet httpget = new HttpGet();
		String content = "";
		httpget.setURI(new java.net.URI("http://www.f139.com/news/list.do?channelID=43,44&categoryID=135,48"));
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
			content = EntityUtils.toString(entity, charset);
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println(content);
	}
}