package com.ast.ast1949.yuanliao.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsExpire;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.persist.price.PriceDAO;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsExpireService;
import com.ast.ast1949.service.yuanliao.ProvincePinyinService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class PriceController extends BaseController {
	@Resource
	private PriceService priceService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private ProvincePinyinService provincePinyinService;
	@Resource
	private PriceDAO priceDAO;
	@Resource
	private ProductsExpireService productsExpireService;
	@Resource
	private YuanLiaoService yuanLiaoService;
	final static Map<String, String> CODE_MAP = new HashMap<String, String>();
	static {
		CODE_MAP.put("9", "ABS");
		CODE_MAP.put("10", "pp");
		CODE_MAP.put("11", "LLDPE");
		CODE_MAP.put("12", "PVC");
		CODE_MAP.put("13", "PS");
		CODE_MAP.put("14", "LDPE");
		CODE_MAP.put("15", "PC");
		CODE_MAP.put("16", "HDPE");
		CODE_MAP.put("17", "PE");
		CODE_MAP.put("18", "ABS/PS");
		CODE_MAP.put("19", "国内石化");
		CODE_MAP.put("20", "东莞");
		CODE_MAP.put("21", "余姚");
		CODE_MAP.put("22", "齐鲁");
		CODE_MAP.put("23", "临沂");
		CODE_MAP.put("24", "顺德");
		CODE_MAP.put("25", "杭州");
		CODE_MAP.put("26", "汕头");
		CODE_MAP.put("27", "上海");
		CODE_MAP.put("28", "广州");
		CODE_MAP.put("29", "北京");
	}

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, CompanyPriceSearchDTO dto,
			PageDto<CompanyPriceSearchDTO> page, String code) {
		// 每日价格行情今日
		List<PriceDO> list = priceService.queryEveryDayHq(217);

		// 热点行情
		List<PriceDO> redian = priceService.queryRedian();
		// 每日价格行情昨日
		List<PriceDO> list2 = priceService.queryEveryDayHq2(217);

		// 国内塑料市场价格
		List<PriceDO> gn = priceService.queryListByTypeIdFour();

		// 余姚塑料城价格
		List<PriceDO> yuyao = priceService.queryByTypeIdYYC(324);

		// 国内石化出厂价
		List<PriceDO> shihua = priceService.queryByTypeIdYYC(61);

		// 塑料期货价格
		List<PriceDO> qh = priceService.queryListByTypeId(233, 7);

		// // 昨天期货国际收盘价
		// List<PriceDO> sp = priceService.queryListByTypeIdThree(233);

		// 国内塑料市场概况
		List<PriceDO> sc = priceService.queryListByTypeIdGK();

		// 塑料新料市场行情
		List<PriceDO> hq = priceService.queryListByTypeIdHq();

		if (StringUtils.isEmpty(code)) {
			code = "100010001006";
		}
		dto.setCategoryCompanyPriceCode(code);
		// 企业报价
		page.setSort("refresh_time");
		page.setPageSize(8);
		page = companyPriceService.pageCompanyPriceDtoBySearchEngine(dto, page);
		List<CompanyPriceSearchDTO> qiye = page.getRecords();

		// 市场评论
		List<PriceDO> pinglun = priceService.queryListByTypeIdComments();
		// 原油期货价格
		List<PriceDO> js = priceDAO.queryListByTypeId(190, 7);
		out.put("js", js);
		// // 塑料资讯
		// String detail = null;
		// try {
		// detail = HttpUtils
		// .getInstance()
		// .httpGet(
		// AddressTool.getAddress("pyapp")
		// + "/news/javagetnewslist_json.html?typeid2=155&num=7",
		// HttpUtils.CHARSET_UTF8);
		// } catch (Exception e) {
		// detail = null;
		// }
		// if (StringUtils.isNotEmpty(detail)) {
		// JSONObject json = JSONObject.fromObject(detail);
		// JSONArray js = JSONArray.fromObject(json.get("list"));
		// out.put("js", js);
		// }
		out.put("hq", hq);
		out.put("list", list);
		out.put("list2", list2);
		out.put("redian", redian);
		out.put("gn", gn);
		// out.put("sp", sp);
		out.put("sc", sc);
		out.put("yuyao", yuyao);
		out.put("shihua", shihua);
		out.put("qh", qh);
		out.put("qiye", qiye);
		out.put("pinglun", pinglun);
		SeoUtil.getInstance().buildSeo("hqIndex", out);
		return null;
	}

	@RequestMapping
	public ModelAndView clist(HttpServletRequest request,
			CompanyPriceSearchDTO dto, Map<String, Object> out,
			PageDto<CompanyPriceSearchDTO> page, String cp, String pro,
			String searchCode) throws Exception {
		// 省份-拼音关系
		Map<String, String> mapProvince = provincePinyinService
				.queryAllProvincePinyin();
		// 公司报价code与labal的关系
		Map<String, String> mapPrice = categoryCompanyPriceService
				.queryAllCompanyPrice();
		out.put("mapPrice", mapPrice);
		String code = "";
		String dq = "";
		// String min="";
		if (StringUtils.isNotEmpty(cp)) {
			if (cp.equals("qttysy")) {
				dto.setCategoryCompanyPriceCode("100410001006");
			} else if (cp.equals("qtgcsy")) {
				dto.setCategoryCompanyPriceCode("100410011007");
			} else if (cp.equals("qttzsl")) {
				dto.setCategoryCompanyPriceCode("100410021004");
			} else if (cp.equals("qtslhj")) {
				dto.setCategoryCompanyPriceCode("100410031004");
			} else if (cp.equals("qbtysl")) {
				dto.setCategoryCompanyPriceCode("10041000");
			} else if (cp.equals("qbgcsy")) {
				dto.setCategoryCompanyPriceCode("10041001");
			} else if (cp.equals("qbtzsl")) {
				dto.setCategoryCompanyPriceCode("10041002");
			} else if (cp.equals("qbslhj")) {
				dto.setCategoryCompanyPriceCode("10041003");
			} else {
				// if(mainCode.contains("/")){
				// min=mainCode.replace("/", "\\/");
				// }else{
				// min=mainCode;
				// }
				code = mapPrice.get(cp);
				dto.setCategoryCompanyPriceCode(code);
			}
		}
		if (StringUtils.isNotEmpty(pro)) {
			if (pro.equals("qbdq")) {
				dto.setAreaCode("");
			} else {
				dq = mapProvince.get(pro);
				dto.setAreaCode(dq);
			}
		}
		if (StringUtils.isNotEmpty(searchCode)) {
			dto.setCategoryCompanyPriceCode(searchCode);
		}
		page.setPageSize(10);
		page.setStartIndex((page.getCurrentPage() - 1) * page.getPageSize());
		page = companyPriceService.pageCompanyPriceDtoBySearchEngine(dto, page);
		List<CompanyPriceSearchDTO> list = page.getRecords();
		// for (CompanyPriceSearchDTO s : list) {
		// if (StringUtils.isNotEmpty(s.getPrice())
		// && s.getPrice().indexOf("元") != -1) {
		// s.setPrice(s.getPrice().substring(0, s.getPrice().indexOf("元")));
		// }
		// }
		String code1 = null;
		if (StringUtils.isNotEmpty(searchCode)) {
			cp = mapPrice.get(searchCode);
			if (StringUtils.isNotEmpty(cp)) {
				if (cp.equals("通用塑料")) {
					cp = "qbtysl";
				} else if (cp.equals("工程塑料")) {
					cp = "qbgcsy";
				} else if (cp.equals("特种塑料")) {
					cp = "qbtzsl";
				} else if (cp.equals("塑料合金")) {
					cp = "qbslhj";
				}
				if (searchCode.equals("100410001006")) {
					cp = "qttysy";
				} else if (searchCode.equals("100410011007")) {
					cp = "qtgcsy";
				} else if (searchCode.equals("100410021004")) {
					cp = "qttzsl";
				} else if (searchCode.equals("100410031004")) {
					cp = "qtslhj";
				}
			}
		}
		out.put("code", cp);
		if (StringUtils.isNotEmpty(cp)) {
			if (cp.equalsIgnoreCase("qbtysl")) {
				code1 = "通用塑料";
			} else if (cp.equalsIgnoreCase("qbgcsy")) {
				code1 = "工程塑料";
			} else if (cp.equalsIgnoreCase("qbtzsl")) {
				code1 = "特种塑料";
			} else if (cp.equalsIgnoreCase("qbslhj")) {
				code1 = "塑料合金";
			} else if (cp.equalsIgnoreCase("qttysy")) {
				code1 = "其它通用塑料";
			} else if (cp.equalsIgnoreCase("qtgcsy")) {
				code1 = "其它工程塑料";
			} else if (cp.equalsIgnoreCase("qttzsl")) {
				code1 = "其它特种塑料";
			} else if (cp.equalsIgnoreCase("qtslhj")) {
				code1 = "其它塑料合金";
			} else {
				code1 = cp;
			}
		}
		out.put("page", page);
		out.put("pro", pro);
		out.put("code1", code1);

		// 三要素
		String[] seoContent = new String[2];
		seoContent[0] = "";
		if (StringUtils.isNotEmpty(code1) || StringUtils.isNotEmpty(dq)) {
			if (StringUtils.isNotEmpty(dq)) {
				if (dq.length() > 12) {
					seoContent[0] = seoContent[0]
							+ CategoryFacade.getInstance().getValue(
									dq.substring(0, 12))
							+ CategoryFacade.getInstance().getValue(
									dq.substring(0, 16));
				} else if (dq.length() > 8 && dq.length() < 13) {
					seoContent[0] = seoContent[0]
							+ CategoryFacade.getInstance().getValue(
									dq.substring(0, 8))
							+ CategoryFacade.getInstance().getValue(
									dq.substring(0, 12));
				} else {
					if (dq.length() == 8) {
						seoContent[0] = seoContent[0]
								+ CategoryFacade.getInstance().getValue(
										dq.substring(0, 8));
					}
				}
			}
			if (StringUtils.isNotEmpty(code1)) {
				seoContent[0] = seoContent[0] + code1;
			}
			if (page.getCurrentPage() != null && page.getCurrentPage() != 1) {
				seoContent[1] = String.valueOf(page.getCurrentPage());
				SeoUtil.getInstance().buildSeo("clist4", seoContent,
						seoContent, seoContent, out);
			} else {
				SeoUtil.getInstance().buildSeo("clist2", seoContent,
						seoContent, seoContent, out);
			}
		} else {
			if (page.getCurrentPage() != null && page.getCurrentPage() != 1) {
				seoContent[0] = String.valueOf(page.getCurrentPage());
				SeoUtil.getInstance().buildSeo("clist3", seoContent,
						seoContent, seoContent, out);
			} else {
				SeoUtil.getInstance().buildSeo("clist", out);
			}
		}
		return null;
	}

	// 根据code获取企业报价
	@RequestMapping
	public ModelAndView queryByCode(Map<String, Object> out,
			CompanyPriceSearchDTO dto, PageDto<CompanyPriceSearchDTO> page,
			String code) throws IOException {
		// 公司报价code与labal的关系
		Map<String, Object> map = new HashMap<String, Object>();
		do {
			Map<String, String> mapPrice = categoryCompanyPriceService
					.queryAllCompanyPrice();
			String mainCode = "";
			if (code != null) {
				mainCode = mapPrice.get(code);
				if (mainCode == null || mainCode == "") {
					break;
				}
				dto.setCategoryCompanyPriceCode(mainCode);
			}
			// 企业报价
			page.setSort("refresh_time");
			page.setPageSize(8);
			page = companyPriceService.pageCompanyPriceDtoBySearchEngine(dto,
					page);
			List<CompanyPriceSearchDTO> qiye = page.getRecords();
			map.put("list", qiye);
		} while (false);
		return printJson(map, out);
	}

	// 根据关键字收索行情报价　　行情报价列表页
	@RequestMapping
	public ModelAndView list(Map<String, Object> out, PageDto<PriceDO> page,
			String code) throws UnsupportedEncodingException {
		// if (StringUtils.isNotEmpty(keyWords)) {
		// keyWords = StringUtils.decryptUrlParameter(keyWords);
		// }
		TreeMap<Integer, Object> li = new TreeMap<Integer, Object>();
		String word = "";
		do {
			// 塑料资讯
			String detail = null;
			try {
				detail = HttpUtils
						.getInstance()
						.httpGet(
								AddressTool.getAddress("pyapp")
										+ "/news/javagetnewslist_json.html?typeid2=155&num=11",
								HttpUtils.CHARSET_UTF8);
			} catch (Exception e) {
				detail = null;
			}
			if (StringUtils.isNotEmpty(detail)) {
				JSONObject json = JSONObject.fromObject(detail);
				JSONArray js = JSONArray.fromObject(json.get("list"));
				out.put("js", js);
			}
			if (StringUtils.isNotEmpty(code) && StringUtils.isNber(code)
					&& Integer.valueOf(code) < 9) {
				page.setSort("gmtCreated");
				page.setPageSize(20);
				page.setStartIndex((page.getCurrentPage() - 1)
						* page.getPageSize());
				if (code.equals("0")) {
					word = "每日价格行情";
					page.setStartIndex((page.getCurrentPage() - 1)
							* page.getPageSize());
					// 每日行情报价　　必须包括PP、PVC、HDP、ABS/PS、LLDPE
					page.setPageSize(10000);
					page.setStartIndex(0);
					page = priceService.pagePriceByTypeTwo(217, null, null,
							page);
					page.setPageSize(20);
					page.setStartIndex((page.getCurrentPage() - 1)
							* page.getPageSize());
					// 进行page组装
					try {
						page.setTotalRecords(totalRecords(page));
						page.setRecords(getRecords(page));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (code.equals("3")) {
					// 余姚城行情报价
					word = "余姚塑料城价格";
					page = priceService.pagePriceByTypeTwo(324, null, null,
							page);
				} else if (code.equals("1")) {
					// 期货报价
					word = "塑料期货价格";
					page = priceService.pagePriceByTypeTwo(233, null, null,
							page);
				} else if (code.equals("4")) {
					// 国内石化出厂价
					word = "国内石化出厂价";
					page = priceService
							.pagePriceByTypeTwo(61, null, null, page);
				} else if (code.equals("8")) {
					// 原油期货价格
					word = "原油期货价格";
					page = priceService.pagePriceByTypeTwo(190, null, null,
							page);
				} else if (code.equals("5")) {
					// 国内塑料市场概况
					word = "国内塑料市场概况";
					page.setStartIndex((page.getCurrentPage() - 1)
							* page.getPageSize());
					String code1 = "217";
					page.setPageSize(10000);
					page.setStartIndex(0);
					page = priceService.pagePriceByTypeTwo(
							Integer.valueOf(code1), null, null, page);
					page.setPageSize(20);
					page.setStartIndex((page.getCurrentPage() - 1)
							* page.getPageSize());
					page.setTotalRecords(totalRecordsTwo(page));
					page.setRecords(getRecordsTwo(page));
				} else if (code.equals("7")) {
					word = "国内塑料市场评论";
					String code1 = "217,34";
					String[] ar = code1.split(",");
					for (int i = 0; i < ar.length; i++) {
						page.setStartIndex(0);
						page = priceService.pagePriceByTypeTwo(
								Integer.valueOf(ar[i]), null, null, page);
						if (page.getRecords() != null) {
							for (PriceDO s : page.getRecords()) {
								if (s.getTitle() != null
										&& s.getTitle().contains("评论")) {
									li.put(s.getId(), s);
								}
							}
						}
					}
					page.setStartIndex((page.getCurrentPage() - 1)
							* page.getPageSize());
					// 进行page组装
					List<PriceDO> list = new ArrayList<PriceDO>();
					List<PriceDO> list2 = new ArrayList<PriceDO>();
					Set<Integer> set = li.descendingKeySet();
					Iterator<Integer> iter = set.iterator();
					// 进行排序
					while (iter.hasNext()) {
						list.add((PriceDO) li.get(iter.next()));
					}
					// 进行分页page组装
					if ((page.getStartIndex() + page.getPageSize()) > list
							.size()) {
						for (int i = page.getStartIndex(); i < list.size(); i++) {
							list2.add(list.get(i));
						}
					} else {
						for (int i = page.getStartIndex(); i < (page
								.getStartIndex() + page.getPageSize()); i++) {
							list2.add(list.get(i));
						}
					}
					page.setPageSize(20);
					page.setRecords(list2);
					page.setTotalRecords(list.size());
				} else if (code.equals("6")) {
					word = "塑料新料市场行情";
					String arr[] = new String[] { "333", "334", "335", "336" };
					for (int i = 0; i < arr.length; i++) {
						page.setStartIndex(0);
						page = priceService.pagePriceByTypeTwo(
								Integer.valueOf(arr[i]), null, null, page);
						if (page.getRecords() != null) {
							for (PriceDO s : page.getRecords()) {
								li.put(s.getId(), s);
							}
						}
					}

					page.setStartIndex((page.getCurrentPage() - 1)
							* page.getPageSize());
					// 进行page组装
					List<PriceDO> list = new ArrayList<PriceDO>();
					List<PriceDO> list2 = new ArrayList<PriceDO>();
					Set<Integer> set = li.descendingKeySet();
					Iterator<Integer> iter = set.iterator();
					// 进行排序
					while (iter.hasNext()) {
						list.add((PriceDO) li.get(iter.next()));
					}

					// 进行分页page组装
					if ((page.getStartIndex() + page.getPageSize()) > list
							.size()) {
						for (int i = page.getStartIndex(); i < list.size(); i++) {
							list2.add(list.get(i));
						}
					} else {
						for (int i = page.getStartIndex(); i < (page
								.getStartIndex() + page.getPageSize()); i++) {
							list2.add(list.get(i));
						}
					}
					page.setPageSize(20);
					page.setRecords(list2);
					page.setTotalRecords(list.size());
				} else if (code.equals("2")) {
					word = "国内塑料市场价格";
					String[] ar = "111,112,113,114,115,119,120,121,126"
							.split(",");
					// 数据组装
					for (int i = 0; i < ar.length; i++) {
						List<PriceDO> pp = priceService
								.queryListByTypeIdHalfYear(
										Integer.valueOf(ar[i]), 6);
						if (pp != null) {
							for (PriceDO s : pp) {
								li.put(s.getId(), s);
							}
						}
					}
					// 进行page组装
					List<PriceDO> list = new ArrayList<PriceDO>();
					List<PriceDO> list2 = new ArrayList<PriceDO>();
					Set<Integer> set = li.descendingKeySet();
					Iterator<Integer> iter = set.iterator();
					// 进行排序
					while (iter.hasNext()) {
						list.add((PriceDO) li.get(iter.next()));
					}
					// 进行分页page组装
					if ((page.getStartIndex() + page.getPageSize()) > list
							.size()) {
						for (int i = page.getStartIndex(); i < list.size(); i++) {
							list2.add(list.get(i));
						}
					} else {
						for (int i = page.getStartIndex(); i < (page
								.getStartIndex() + page.getPageSize()); i++) {
							list2.add(list.get(i));
						}
					}
					page.setRecords(list2);
					page.setTotalRecords(list.size());
				}
				out.put("page", page);
				out.put("falg", 1);
				String[] title = new String[3];
				String tit = "";
				if (word.contains("价格")) {
					tit = word.replace("价格", "");
				} else {
					tit = word;
				}
				title[0] = word;
				title[1] = tit;
				if (page.getCurrentPage() != null && page.getCurrentPage() != 1) {
					title[2] = String.valueOf(page.getCurrentPage());
					SeoUtil.getInstance().buildSeo("pricelist2", title, title,
							title, out);
				} else {
					SeoUtil.getInstance().buildSeo("pricelist", title, title,
							title, out);
				}
				break;
			}
			if (code == null) {
				break;
			}
			word = CODE_MAP.get(code);
			// 根据标题关键字收索行情报价
			page = priceService.pagePriceByTitleSearchEngine(word, page);
			String[] title = new String[3];
			title[0] = word + "塑料价格";
			title[1] = word + "塑料";
			if (page.getCurrentPage() != null && page.getCurrentPage() != 1) {
				title[2] = String.valueOf(page.getCurrentPage());
				SeoUtil.getInstance().buildSeo("pricelist2", title, title,
						title, out);
			} else {
				SeoUtil.getInstance().buildSeo("pricelist", title, title,
						title, out);
			}
			out.put("page", page);
		} while (false);
		// 企业报价
		List<CompanyPriceDO> list = companyPriceService.queryByCode("1004", 8);
		out.put("word", word);
		out.put("code", code);
		out.put("list", list);
		return null;
	}

	private List<PriceDO> getRecordsTwo(PageDto<PriceDO> page) {
		List<PriceDO> list = new ArrayList<PriceDO>();
		List<PriceDO> list2 = new ArrayList<PriceDO>();
		if (page.getRecords() != null) {
			for (PriceDO s : page.getRecords()) {
				if (s.getTitle().contains("全国各地PE市场概况")
						|| s.getTitle().contains("全国各地PP市场概况")
						|| s.getTitle().contains("全国各地ABS/PS市场概况")) {
					list.add(s);
				}
			}
		}
		if ((page.getStartIndex() + page.getPageSize()) <= list.size()) {
			for (int i = page.getStartIndex(); i < (page.getStartIndex() + page
					.getPageSize()); i++) {
				list2.add(list.get(i));
			}
		} else {
			for (int i = page.getStartIndex(); i < list.size(); i++) {
				list2.add(list.get(i));
			}
		}
		return list2;
	}

	private Integer totalRecordsTwo(PageDto<PriceDO> page) {
		List<PriceDO> list = new ArrayList<PriceDO>();
		if (page.getRecords() != null) {
			for (PriceDO s : page.getRecords()) {
				if (s.getTitle().contains("全国各地PE市场概况")
						|| s.getTitle().contains("全国各地PP市场概况")
						|| s.getTitle().contains("全国各地ABS/PS市场概况")) {
					list.add(s);
				}
			}
		}
		return list.size();
	}

	private Integer totalRecords(PageDto<PriceDO> page) throws ParseException {
		// SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		// String date = "2016-05-01";
		// Date date2 = fomart.parse(date);
		List<PriceDO> list = new ArrayList<PriceDO>();
		if (page.getRecords() != null) {
			for (PriceDO s : page.getRecords()) {
				if (s.getTitle().contains("【PVC】")
						|| s.getTitle().contains("【ABS/PS】")
						|| s.getTitle().contains("【PP】")
						|| s.getTitle().contains("【HDPE】")
						|| s.getTitle().contains("【LLDPE】")) {
					// if (s.getGmtCreated() != null
					// && s.getGmtCreated().getTime() > date2.getTime()) {
					list.add(s);
					// }
				}
			}
		}
		return list.size();
	}

	private List<PriceDO> getRecords(PageDto<PriceDO> page)
			throws ParseException {
		// SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		// String date = "2016-05-01";
		// Date date2 = fomart.parse(date);
		List<PriceDO> list = new ArrayList<PriceDO>();
		List<PriceDO> list2 = new ArrayList<PriceDO>();
		if (page.getRecords() != null) {
			for (PriceDO s : page.getRecords()) {
				if (s.getTitle().contains("【PVC】")
						|| s.getTitle().contains("【ABS/PS】")
						|| s.getTitle().contains("【PP】")
						|| s.getTitle().contains("【HDPE】")
						|| s.getTitle().contains("【LLDPE】")) {
					// if (s.getGmtCreated() != null
					// && s.getGmtCreated().getTime() > date2.getTime()) {
					list.add(s);
					// }
				}
			}
		}
		if ((page.getStartIndex() + page.getPageSize()) <= list.size()) {
			for (int i = page.getStartIndex(); i < (page.getStartIndex() + page
					.getPageSize()); i++) {
				list2.add(list.get(i));
			}
		} else {
			for (int i = page.getStartIndex(); i < list.size(); i++) {
				list2.add(list.get(i));
			}
		}
		return list2;
	}

	// 行情报价最终页
	@RequestMapping
	public ModelAndView priceDetail(Map<String, Object> out, Integer id,
			String keyword) throws UnsupportedEncodingException {
		if (id == null) {
			return null;
		}
		if (StringUtils.isNotEmpty(keyword)) {
			keyword = StringUtils.decryptUrlParameter(keyword);
		}
		PriceDTO price = priceService.queryPriceByIdForEdit(id);
		if (price != null && price.getPrice().getContent() != null) {
			price.getPrice().setContent(
					price.getPrice().getContent().replace("zz91再生网", "")
							.replace("ZZ91再生网", ""));
		}
		Integer i = null;
		if (price != null) {
			i = price.getPrice().getTypeId();
		}
		if (i != null) {
			// 该类别下的最相关行情
			List<PriceDO> zuixin = priceService.queryListByTypeId(i, 8);
			out.put("zuixin", zuixin);
		}
		// 最新企业报价
		List<CompanyPriceDO> list = companyPriceService.queryByCode("1004", 8);
		// 行情报价最终页　上一篇
		PriceDO list3 = priceService.queryOnPriceById(id);
		// 行情报价最终页　下一篇
		PriceDO list2 = priceService.queryDownPriceById(id);
		out.put("keyword", keyword);
		out.put("list", list);
		out.put("list3", list3);
		out.put("list2", list2);
		out.put("price", price);
		String[] title = new String[2];
		title[0] = "";
		if (price != null && price.getPrice() != null) {
			title[0] = price.getPrice().getTitle();
		}
		title[1] = keyword;
		SeoUtil.getInstance().buildSeo("priceDetail", title, title, title, out);
		return null;
	}

	// 评论最终页
	@RequestMapping
	public ModelAndView reviewDetail(Map<String, Object> out, Integer id,
			String keyword) {
		if (id == null) {
			return null;
		}
		PriceDTO price = priceService.queryPriceByIdForEdit(id);
		if (price != null && price.getPrice().getContent() != null) {
			price.getPrice().setContent(
					price.getPrice().getContent().replace("zz91再生网", "")
							.replace("ZZ91再生网", ""));
		}
		Integer i = null;
		if (price != null) {
			i = price.getPrice().getTypeId();
		}
		if (i != null) {
			// 该类别下的最相关行情
			List<PriceDO> zuixin = priceService.queryListByTypeId(i, 8);
			out.put("zuixin", zuixin);
		}
		// 最新企业报价
		List<CompanyPriceDO> list = companyPriceService.queryByCode("1004", 8);
		// 行情报价最终页　上一篇
		PriceDO list3 = priceService.queryOnPriceById(id);
		// 行情报价最终页　下一篇
		PriceDO list2 = priceService.queryDownPriceById(id);
		out.put("keyword", keyword);
		out.put("list", list);
		out.put("list3", list3);
		out.put("list2", list2);
		out.put("price", price);
		String[] title = { price.getPrice().getTitle(), keyword };
		SeoUtil.getInstance().buildSeo("priceDetail", title, title, title, out);
		return null;
	}

	// 根据关键字收索行情报价　　行情报价列表页
	@RequestMapping
	public ModelAndView search(Map<String, Object> out, String keyword,
			PageDto<PriceDO> page, CompanyPriceSearchDTO dto,
			PageDto<CompanyPriceSearchDTO> page2, String flag)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(keyword)) {
			keyword = StringUtils.decryptUrlParameter(keyword);
		}
		if (flag == null) {
			flag = "0";
		}
		do {
			if (keyword == null) {
				break;
			}
			if (flag == null || flag != null && flag.equals("0")) {
				// 根据标题关键字收索行情报价
				page = priceService.pagePriceByTitleSearchEngine(keyword, page);
				out.put("page", page);
			} else if (flag != null && flag.equals("1")) {
				// 获取企业报价
				dto.setKeywords(keyword);
				if (page2.getCurrentPage() == null) {
					page2.setCurrentPage(1);
				}
				page2.setSort("refresh_time");
				page2.setPageSize(8);
				page2.setStartIndex((page2.getCurrentPage() - 1)
						* page2.getPageSize());
				page2 = companyPriceService.pageCompanyPriceDtoBySearchEngine(
						dto, page2);
				out.put("page2", page2);
			}
		} while (false);

		// 塑料资讯
		String detail = null;
		try {
			detail = HttpUtils
					.getInstance()
					.httpGet(
							AddressTool.getAddress("pyapp")
									+ "/news/javagetnewslist_json.html?typeid2=155&num=7",
							HttpUtils.CHARSET_UTF8);
		} catch (Exception e) {
			detail = null;
		}
		if (StringUtils.isNotEmpty(detail)) {
			JSONObject json = JSONObject.fromObject(detail);
			JSONArray js = JSONArray.fromObject(json.get("list"));
			out.put("js", js);
		}

		// 可能感兴趣的
		List<PriceDO> price = priceService.queryBytypeIdNewPrice();
		List<PriceDO> price2 = priceService.queryProvin();
		// 企业报价
		List<CompanyPriceDO> list = companyPriceService.queryByCode("1004", 8);
		out.put("flag", flag);
		out.put("price", price);
		out.put("price2", price2);
		out.put("keyword", keyword);
		out.put("list", list);
		String[] title = new String[1];
		title[0] = "";
		if (keyword != null) {
			title[0] = keyword;
		}
		SeoUtil.getInstance().buildSeo("search", title, null, null, out);
		return null;
	}

	@RequestMapping
	public ModelAndView priceList(Map<String, Object> out,
			HttpServletRequest request) {

		SeoUtil.getInstance().buildSeo(out);
		return null;
	}

	@RequestMapping
	public ModelAndView comDetail(Map<String, Object> out,
			HttpServletRequest request, Integer id, String mainCode) {
		do {
			if (id == null) {
				break;
			}
			if (StringUtils.isNotEmpty(mainCode)) {
				try {
					mainCode = StringUtils.decryptUrlParameter(mainCode);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// 企业报价详情
			CompanyPriceDO companyPriceDO = companyPriceService
					.queryCompanyPriceById(id);
			if (companyPriceDO == null) {
				break;
			}
			// 过期天数
			ProductsExpire productsExpire = productsExpireService
					.queryByProductsId(companyPriceDO.getProductId());
			out.put("companyPriceDO", companyPriceDO);
			out.put("productsExpire", productsExpire);

			// 最新塑料市场价格
			List<PriceDO> list2 = priceService.querySexProvin();
			out.put("list2", list2);
			// Map<String, String> map = categoryCompanyPriceService
			// .queryAllCompanyPrice();
			// 相关类别最新报价
			// String category = null;

			if (companyPriceDO != null) {
				List<CompanyPriceDO> list3 = companyPriceService.queryByCode(
						companyPriceDO.getCategoryCompanyPriceCode(), 6);
				out.put("list3", list3);
				// category = map
				// .get(companyPriceDO.getCategoryCompanyPriceCode());
			}

			// 最新原料供求
			List<Yuanliao> list5 = yuanLiaoService.queryNewSize(6);
			out.put("list5", list5);

			// 该公司的其他公司报价
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", companyPriceDO.getId());
			param.put("companyId", companyPriceDO.getCompanyId());
			List<CompanyPriceDO> list = companyPriceService
					.queryCompanyPriceByCompanyId(param);
			out.put("list", list);
			String[] title = new String[2];
			title[0] = "";
			title[1] = "";
			if (companyPriceDO != null) {
				title[0] = companyPriceDO.getTitle() + "_";
				if (companyPriceDO.getTitle() == null) {
					title[0] = "";
				}
				if (companyPriceDO.getAreaCode() != null) {
					title[1] = title[1] + companyPriceDO.getAreaCode();
				}
				if (companyPriceDO.getDetails() != null) {
					if (companyPriceDO.getDetails().length() > 80) {
						title[1] = title[1]
								+ companyPriceDO.getDetails().substring(0, 80);
					} else {
						title[1] = title[1] + companyPriceDO.getDetails();
					}
				}
			}
			SeoUtil.getInstance().buildSeo("comDetail", title, title, title,
					out);
		} while (false);
		// 类别中的拼音，与编码之间的关系
		Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
		out.put("code", mainCode);
		out.put("mapMain", mapMain);

		return null;
	}

	@RequestMapping
	public ModelAndView productChild(HttpServletRequest request,
			Map<String, Object> out, String parentCode) throws IOException {
		List<CategoryCompanyPriceDO> list = categoryCompanyPriceService
				.queryCategoryCompanyPriceByCode(parentCode);
		// List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		// if (map == null) {
		// return printJson(list, out);
		// }
		// for (Entry<String, String> m : map.entrySet()) {
		// CategoryYuanliao c = new CategoryYuanliao();
		// c.setCode(m.getKey());
		// c.setLabel(m.getValue());
		// list.add(c);
		// }
		return printJson(list, out);
	}
}
