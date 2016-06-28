package com.ast.ast1949.yuanliao.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanySearch;
import com.ast.ast1949.dto.yuanliao.SearchDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryMap;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.service.yuanliao.ProvincePinyinService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class CompController extends BaseController {
	@Resource
	private CompanyService companyService;
	@Resource
	private DataIndexService dataIndexService;
	@Resource
	private YuanLiaoService yuanliaoService;
	@Resource
	private ProvincePinyinService provincePinyinService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private MyfavoriteService myfavoriteService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private YuanLiaoService yuanLiaoService;
	final static Map<Integer, String> COMPANY_PRICE_MAP = new HashMap<Integer, String>();
	static {
		COMPANY_PRICE_MAP.put(1, "产品规格、型号：我想了解产品规格、型号，能否发一份详细资料给我参考，谢谢！");
		COMPANY_PRICE_MAP.put(2, "价格：请问贵公司对该产品的报价是多少呢？");
		COMPANY_PRICE_MAP.put(3, "数量：我想了解产品每月的供应或者求购的数量是多少？");
		COMPANY_PRICE_MAP.put(4, "产品测试报告：我想了解产品的性能,能否发一份详细测试报告给我，谢谢！");
		COMPANY_PRICE_MAP.put(5, "图片：我对贵公司的产品非常感兴趣，能否把产品图片发给我参考？");
	}

	@RequestMapping
	public ModelAndView index(Map<String, Object> out, CompanySearch search) {
		search.setIndustryCode("10001010");
		// 最新加入企业
		List<Company> list = companyService.queryCompanySearch(search);
		out.put("obj", list);
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
		// 编号与类别的关系
		out.put("prentMap", CategoryMap.CODE_MAP);
		SeoUtil.getInstance().buildSeo("firmIndex", out);
		return null;
	}

	@RequestMapping
	public ModelAndView indexByCode(HttpServletRequest request,
			Map<String, Object> out, String code, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 优质供应商推荐
		List<DataIndexDO> list = dataIndexService
				.queryDataByCode(code, null, 1);
		List<Company> com = new ArrayList<Company>();
		if (list.size() > 0) {
			for (DataIndexDO li : list) {
				Company company = new Company();
				String ids = li.getTitle();
				if (ids != null) {
					if (ids.endsWith(",")) {
						ids.substring(0, ids.length() - 1);
					}
					if (ids.contains(",")) {
						String arr[] = ids.split(",");
						for (int i = 0; i < arr.length; i++) {
							if (arr[i] == null) {
								continue;
							}
							company = companyService.queryCompanyById(Integer
									.valueOf(arr[i]));
							company.setAreaCode(getArea(company.getAreaCode()));
							com.add(company);
						}
					} else {
						company = companyService.queryCompanyById(Integer
								.valueOf(ids));
						company.setAreaCode(getArea(company.getAreaCode()));
						com.add(company);
					}
				}
			}
			map.put("list", com);
		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, YuanLiaoSearch search,
			Map<String, Object> out, String mainCode, String pro, String code,
			YuanliaoDto yuanliaoDto, PageDto<CompanyDto> page, String searchCode)
			throws Exception {

		search.setCode(null);
		Company company = new Company();
		yuanliaoDto.setCompany(company);
		if (StringUtils.isNotEmpty(search.getKeyword())
				&& !StringUtils.isContainCNChar(search.getKeyword())) {
			search.setKeyword(StringUtils.decryptUrlParameter(search
					.getKeyword()));
			yuanliaoDto.getCompany().setName(search.getKeyword());
		}
		// 省份-拼音关系
		Map<String, String> mapProvince = provincePinyinService
				.queryAllProvincePinyin();
		out.put("propinyin", mapProvince);
		// 类别中的拼音，与编码之间的关系
		Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
		out.put("mapMain", mapMain);
		if (StringUtils.isNotEmpty(code)) {
			code = CategoryMap.CODE_MAP.get(code);
			code = code.toLowerCase();
		}
		if (StringUtils.isEmpty(mainCode) && StringUtils.isNotEmpty(code)) {
			mainCode = mapMain.get(code.toLowerCase());
		}
		if (StringUtils.isNotEmpty(searchCode)) {
			mainCode = searchCode;
			code = mapMain.get(mainCode);
		}
		if (StringUtils.isNotEmpty(mainCode)) {
			search.setCode(mainCode);
			out.put("mainCodeSize", mapMain.get(mainCode));
		}
		if (StringUtils.isNotEmpty(code)) {
			String content = "";
			if (code.equals("gppla")) {
				content = "通用塑料";
			} else if (code.equals("enpla")) {
				content = "工程塑料";
			} else if (code.equals("sppla")) {
				content = "特种塑料";
			} else if (code.equals("alpla")) {
				content = "塑料合金";
			} else if (code.equals("qttysl")) {
				content = "其他通用塑料";
			} else if (code.equals("qtgcsl")) {
				content = "其他工程塑料";
			} else if (code.equals("qttzsl")) {
				content = "其他特种塑料";
			} else if (code.equals("qtslhj")) {
				content = "其他塑料合金";
			} else if (code.equals("slsz")) {
				content = "沙林树脂";
			}
			if (StringUtils.isNotEmpty(content)) {
				yuanliaoDto.getCompany().setName(content);
			} else {
				yuanliaoDto.getCompany().setName(code);
			}
		}
		// 厂家（产地）与编号之间的关系
		Map<String, String> mapC = YuanliaoFacade.CATE_MAP;
		out.put("mapC", mapC);
		if (StringUtils.isNotEmpty(pro)) {
			pro = CategoryMap.CODE_MAP.get(pro);
		}
		String address = "";
		if (StringUtils.isNotEmpty(pro) && !StringUtils.isContainCNChar(pro)) {
			CategoryDO cate = categoryService.queryCategoryByCode(mapProvince
					.get(pro));
			if (cate != null) {
				search.setProvince(cate.getLabel());
				address = cate.getLabel();
				yuanliaoDto.getCompany().setAreaCode(mapProvince.get(pro));
			}
		}

		// if (StringUtils.isEmpty(search.getType())) {
		// search.setType("10331000");
		// }
		// 省份
		Map<String, String> mapp = CategoryFacade.getInstance().getChild(
				"10011000");
		if (mapp == null) {
			mapp = new HashMap<String, String>();
		}
		out.put("mapp", mapp);
		if (yuanliaoDto.getCompany().getIndustryCode() == null) {
			yuanliaoDto.getCompany().setIndustryCode("10001010");
		}
		page = companyService.pageYuanliaoBySearchEngine(yuanliaoDto, page);
		// 优质商家推荐　　高会
		// if (page.getTotalRecords() < 1) {
		// YuanliaoDto com = new YuanliaoDto();
		// Company company2 = new Company();
		// PageDto<CompanyDto> page1 = new PageDto<CompanyDto>();
		// com.setCompany(company2);
		// com.getCompany().setIndustryCode("10001010");
		// com.setMembershipCode("10051000");
		// page1 = companyService.pageYuanliaoBySearchEngine(com, page1);
		// out.put("page1", page1);
		// }
		// 主类别
		Map<String, String> prentMap = YuanliaoFacade.getInstance().getChild(
				"20091000");
		out.put("prentMap", prentMap);
		// 详细类别
		Map<String, String> proMap = new HashMap<String, String>();
		if (mainCode != null) {
			// 子类别
			if (mainCode.length() == 12) {
				proMap = YuanliaoFacade.getInstance().getChild(mainCode);
			} else if (mainCode.length() == 16) {
				proMap = YuanliaoFacade.getInstance().getChild(
						mainCode.substring(0, mainCode.length() - 4));
			}
			out.put("proMap", proMap);
		}
		// if (search.getKeyword() == null) {
		// if (StringUtils.isNotEmpty(mainCode)) {
		// if (mainCode.length() == 12) {
		// search.setKeyword(prentMap.get(mainCode));
		// } else if (mainCode.length() == 16) {
		// search.setKeyword(proMap.get(mainCode));
		// }
		// }
		// }
		out.put("categoryMap", CategoryMap.CODE_MAP);
		if (StringUtils.isNotEmpty(code)) {
			out.put("code", CategoryMap.CODE_MAP.get(code));
		}
		out.put("page", page);
		// out.put("search", search);
		out.put("mainCode", mainCode);
		if (StringUtils.isNotEmpty(pro)) {
			out.put("pro", CategoryMap.CODE_MAP.get(pro));
		}

		// 三要素
		String[] seoContent = new String[4];
		seoContent[0] = "";
		if (StringUtils.isEmpty(search.getKeyword())) {
			String label = "";
			String labe2 = "";
			if (StringUtils.isNotEmpty(code)) {
				if (mapMain.get(code).length() > 12) {
					label = proMap.get(mapMain.get(code));
				}
				labe2 = prentMap.get(mapMain.get(code).substring(0, 12));
			}
			if (label == null) {
				label = "";
			} else {
				label = label + "原料";
			}
			if (labe2 == null) {
				labe2 = "";
			}
			seoContent[0] = label;
			seoContent[1] = address;
			seoContent[2] = labe2;
			if (page.getCurrentPage() != null && page.getCurrentPage() != 1) {
				seoContent[3] = String.valueOf(page.getCurrentPage());
				SeoUtil.getInstance().buildSeo("complist2", seoContent,
						seoContent, seoContent, out);
			} else {
				SeoUtil.getInstance().buildSeo("complist", seoContent,
						seoContent, seoContent, out);
			}
		} else {
			seoContent[0] = search.getKeyword();
			SeoUtil.getInstance().buildSeo("complist3", seoContent, seoContent,
					seoContent, out);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView detail(HttpServletRequest request,
			Map<String, Object> out, Integer companyId, String flag,
			String type, PageDto<YuanliaoDto> page) {
		CategoryFacade cate = CategoryFacade.getInstance();
		Company company = companyService.queryCompanyById(companyId);
		company.setIntroduction(Jsoup.clean(company.getIntroduction(),
				Whitelist.none().addTags("\n")));
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(company.getId());
		String location = "";
		// 地区
		if (StringUtils.isNotEmpty(company.getAreaCode())) {
			if (company.getAreaCode().length() > 12) {
				location = cate
						.getValue(company.getAreaCode().substring(0, 12))
						+ ""
						+ cate.getValue(company.getAreaCode().substring(0, 16));
			} else if (company.getAreaCode().length() > 8) {
				location = cate.getValue(company.getAreaCode().substring(0, 8))
						+ ""
						+ cate.getValue(company.getAreaCode().substring(0, 12));
			} else if (company.getAreaCode().length() == 8) {
				location = cate.getValue(company.getAreaCode().substring(0, 8));
			}
		}
		// 公司类型
		if (StringUtils.isNotEmpty(company.getServiceCode())
				&& company.getServiceCode().length() == 8) {
			String service = cate.getValue(company.getServiceCode());
			out.put("service", service);
		}

		// 获取非公司首页列表数据
		SearchDto search = new SearchDto();
		search.setCompanyId(companyId);
		page.setPageSize(5);
		String searchCode = "";
		// 所有
		if (StringUtils.isEmpty(type) || StringUtils.isNotEmpty(type)
				&& type.equals("0")) {
			searchCode = "20091000";
		}
		// 通用塑料
		if (StringUtils.isNotEmpty(type) && type.equals("1")) {
			searchCode = "200910001000";
		}
		// 工程塑料
		if (StringUtils.isNotEmpty(type) && type.equals("2")) {
			searchCode = "200910001001";
		}
		// 特种塑料
		if (StringUtils.isNotEmpty(type) && type.equals("3")) {
			searchCode = "200910001002";
		}
		// 塑料合金
		if (StringUtils.isNotEmpty(type) && type.equals("4")) {
			searchCode = "200910001003";
		}
		search.setCategoryYuanliaoCode(searchCode);
		page = yuanliaoService.queryYuanliaoSearchDto(search, page);
		out.put("page", page);

		// 获取四个类别的总数
		search = new SearchDto();
		search.setCompanyId(companyId);
		search.setCategoryYuanliaoCode("200910001000");
		Integer count = yuanliaoService.queryYuanliaoSearchDtoCount(search);
		out.put("conut", count);

		search.setCategoryYuanliaoCode("200910001001");
		Integer count2 = yuanliaoService.queryYuanliaoSearchDtoCount(search);
		out.put("conut2", count2);

		search.setCategoryYuanliaoCode("200910001002");
		Integer count3 = yuanliaoService.queryYuanliaoSearchDtoCount(search);
		out.put("conut3", count3);

		search.setCategoryYuanliaoCode("200910001003");
		Integer count4 = yuanliaoService.queryYuanliaoSearchDtoCount(search);
		out.put("conut4", count4);

		// 公司首页获取 最新原料供求
		if (StringUtils.isEmpty(flag)) {
			List<YuanliaoDto> list = yuanliaoService
					.queryYuanliaoBYCompanyIdPic(companyId);
			out.put("list", list);
		}

		// 基本参数put
		out.put("flag", flag);
		out.put("column", type);
		out.put("location", location);
		out.put("company", company);
		out.put("account", account);
		String[] title = new String[3];
		title[0] = "";
		title[1] = "";
		title[2] = "";
		if (company != null && company.getName() != null) {
			title[0] = company.getName() + "_";
			title[1] = company.getName();
		}
		if (company.getBusiness() != null) {
			title[2] = title[0] + location + company.getBusiness();
		}
		SeoUtil.getInstance().buildSeo("compdetail", title, title, title, out);
		if (flag != null) {
			if (flag.equals("1")) {
				if (company != null && company.getName() != null) {
					title[0] = "企业介绍_" + company.getName() + "_";
				}
				title[1] = "企业介绍";
				if (company.getIntroduction() != null) {
					if (company.getIntroduction().length() > 80) {
						title[2] = company.getIntroduction().substring(0, 80);
					} else {
						title[2] = company.getIntroduction();
					}
				}
				SeoUtil.getInstance().buildSeo("compdetail1", title, title,
						title, out);
			} else if (flag.equals("2")) {
				if (company != null && company.getName() != null) {
					title[0] = company.getName() + "_";
				}
				title[2] = "";
				if (type != null) {
					if (type.equals("1")) {
						title[2] = "通用塑料";
					} else if (type.equals("2")) {
						title[2] = "工程塑料";
					} else if (type.equals("3")) {
						title[2] = "特种塑料";
					} else if (type.equals("4")) {
						title[2] = "塑料合金";
					}
				}
				if (company.getBusiness() != null) {
					title[1] = title[0] + location + company.getBusiness();
				}
				if (page.getCurrentPage() != null && page.getCurrentPage() != 1) {
					title[0] = title[0] + "第" + page.getCurrentPage() + "页_";
				}
				SeoUtil.getInstance().buildSeo("compdetail2", title, title,
						title, out);

			} else if (flag.equals("3")) {
				if (company != null && company.getName() != null) {
					title[0] = "_" + company.getName() + "_";
				}
				title[1] = "联系方式";
				title[2] = "";
				if (company.getBusiness() != null) {
					if (company != null && company.getName() != null) {
						title[2] = company.getName() + location
								+ company.getBusiness();
					} else {
						title[2] = location + company.getBusiness();
					}
				}
				SeoUtil.getInstance().buildSeo("compdetail3", title, title,
						title, out);
			}
		}
		return null;
	}

	// 添加收藏夹
	@RequestMapping
	public ModelAndView collectYuanliao(HttpServletRequest request,
			Integer companyId, Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		SsoUser ssoUser = getCachedUser(request);
		Integer i = 0;
		do {
			if (ssoUser == null) {
				map.put("collect", "login");
				break;
			}
			Company company = companyService.queryCompanyById(companyId);
			if (company == null) {
				break;
			}
			boolean boo = myfavoriteService.isExist(ssoUser.getCompanyId(),
					company.getId(), "10091016");
			if (boo) {
				map.put("collect", "has");
				break;
			}
			MyfavoriteDO favor = new MyfavoriteDO();
			favor.setAccount(ssoUser.getAccount());
			favor.setCompanyId(ssoUser.getCompanyId());
			favor.setContentId(company.getId());
			favor.setContentTitle(company.getName());
			favor.setFavoriteTypeCode("10091016");
			i = myfavoriteService.insertMyCollect(favor);
		} while (false);
		if (i > 0) {
			map.put("collect", "success");
		}
		return printJson(map, out);
	}

	// 在线询价页面
	@RequestMapping
	public ModelAndView xunjia(Map<String, Object> out, Integer companyId,
			HttpServletRequest request, Integer yId) throws IOException {
		Company company = companyService.queryCompanyById(companyId);
		CompanyAccount account = companyAccountService
				.queryAccountByCompanyId(companyId);
		if (yId != null) {
			Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(yId);
			out.put("yuanliao", yuanliao);
		}
		// CategoryFacade cate = CategoryFacade.getInstance();
		// String location="";
		// if (company.getAreaCode().length() > 12) {
		// location = cate
		// .getValue(company.getAreaCode().substring(0, 12))
		// + " "
		// + cate.getValue(company.getAreaCode().substring(0, 16));
		// } else if (company.getAreaCode().length() > 8) {
		// location = cate.getValue(company.getAreaCode().substring(0, 8))
		// + " "
		// + cate.getValue(company.getAreaCode().substring(0, 12));
		// } else if (company.getAreaCode().length() == 8) {
		// location = cate.getValue(company.getAreaCode().substring(0, 8));
		// }
		// company.setAreaCode(location);
		out.put("account", account);
		out.put("company", company);
		SeoUtil.getInstance().buildSeo("resetPassword",
				new String[] { "在线询价" }, new String[] { "" },
				new String[] { "" }, out);
		return new ModelAndView();
	}

	// 发送询盘
	@RequestMapping
	public ModelAndView inquirySend(Map<String, Object> out, Inquiry inquiry,
			HttpServletRequest request, String info, Integer companyId)
			throws IOException {
		ExtResult rs = new ExtResult();
		SsoUser ssoUser = getCachedUser(request);
		do {
			if (ssoUser == null) {
				break;
			}
			if (companyId == null) {
				break;
			}
			inquiry.setSenderAccount(ssoUser.getAccount());
			CompanyAccount account = companyAccountService
					.queryAccountByCompanyId(companyId);
			if (account == null) {
				rs.setSuccess(false);
				break;
			}
			inquiry.setBeInquiredId(companyId);
			inquiry.setContent(getContentFix(info, COMPANY_PRICE_MAP) + "<br/>"
					+ inquiry.getContent());
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_COMPANY);

			inquiry.setReceiverAccount(account.getAccount());
			int i = inquiryService.inquiryByUser(inquiry,
					ssoUser.getCompanyId());
			if (i > 0) {
				rs.setSuccess(true);
				return printJson(rs, out);
			}
		} while (false);
		rs.setSuccess(false);
		return printJson(rs, out);
	}

	// 发送询盘
	@RequestMapping
	public ModelAndView xjsuccess(Map<String, Object> out, YuanLiaoSearch search) {
		// 可能感兴趣的供应
		search.setType("10331000");
		search.setHasPic(1);
		List<YuanliaoDto> list = yuanLiaoService.queryYuanLiaoByCondition(
				search, 5);
		out.put("list", list);
		return null;
	}

	/**
	 * 获取选框 内容组装
	 * 
	 * @param info
	 * @return
	 */
	private String getContentFix(String info, Map<Integer, String> map) {
		String contentFixTitle = "";
		String contentFixBody = "";
		// 组装正文
		if (StringUtils.isNotEmpty(info)) {
			String[] content = info.split(",");
			for (String str : content) {
				String result = map.get(Integer.valueOf(str));
				String[] resultArray = result.split("：");
				if (StringUtils.isEmpty(contentFixTitle)) {
					contentFixTitle = "你好，我想进一步了解：" + resultArray[0];
				} else {
					contentFixTitle = contentFixTitle + "、" + resultArray[0];
				}
				contentFixBody = contentFixBody + "<br/>" + resultArray[1];
			}
		}
		return "<b>" + contentFixTitle + "</b>" + contentFixBody;
	}

	private String getArea(String areaCode) {
		String str = "";
		Integer i = 8;
		String tempCode = "";
		do {
			String fix = "";
			if (StringUtils.isEmpty(areaCode)) {
				break;
			}
			i = i + 4;
			if (areaCode.length() < i) {
				break;
			}
			tempCode = areaCode.substring(0, i);
			if (i == 12) {
				fix = "省";
			} else if (i == 16) {
				fix = "市";
			}
			str = str + CategoryFacade.getInstance().getValue(tempCode) + fix;
		} while (true);
		return str;
	}
}
