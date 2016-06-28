package com.ast.ast1949.yuanliao.controller.fragment;

/**
 * @date 2015-08-25
 * @author shiqp
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.auth.AuthAutoLogin;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.yuanliao.YuanliaoDao;
import com.ast.ast1949.persist.yuanliao.YuanliaoPicDao;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.yuanliao.CategoryYuanliaoService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.yuanliao.controller.BaseController;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class YuanliaoController extends BaseController {
	@Resource
	private DataIndexService dataIndexService;
	@Resource
	private YuanLiaoService yuanliaoService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private PriceService priceService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CategoryYuanliaoService categoryYuanliaoService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private AuthService authService;
	@Resource
	private YuanliaoDao yuanliaoDao;
	@Resource
	private YuanliaoPicDao yuanliaoPicDao;

	@RequestMapping
	public ModelAndView queryCategoryYuanliao(Map<String, Object> out,
			HttpServletRequest request, String code) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> mapy = YuanliaoFacade.getInstance().getChild(code);
		// 类别属性(厂家（产地）)
		List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		for (String cc : mapy.keySet()) {
			CategoryYuanliao yl = new CategoryYuanliao();
			yl.setCode(cc);
			yl.setLabel(mapy.get(cc));
			list.add(yl);
		}
		map.put("list", list);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView status(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		PageCacheUtil.setNoCache(response);

		do {
			SsoUser ssoUser = getCachedUser(request);

			if (ssoUser != null) {
				model.put("isLogin", 1);
				model.put("yousuyuanUser", ssoUser);
				break;
			}
			String cookie = HttpUtils.getInstance().getCookie(request,
					AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
			AuthAutoLogin aal = authService.queryAutoLoginByCookie(cookie);

			if (aal == null || StringUtils.isEmpty(aal.getCompanyAccount())
					|| StringUtils.isEmpty(aal.getPassword())) {
				break;
			}

			String a = "";
			try {
				a = companyAccountService.validateUser(aal.getCompanyAccount(),
						MD5.encode(aal.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				break;
			} catch (UnsupportedEncodingException e) {
				break;
			} catch (AuthorizeException e) {
				break;
			}

			if (StringUtils.isEmpty(a)) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("front"));
			}
			ssoUser = companyAccountService.initSessionUser(a);

			if (ssoUser != null) {
				String key = UUID.randomUUID().toString();
				String ticket = "";
				try {
					ticket = MD5.encode(a + aal.getPassword() + key);
				} catch (NoSuchAlgorithmException e) {
				} catch (UnsupportedEncodingException e) {
				}
				ssoUser.setTicket(ticket);
				HttpUtils.getInstance().setCookie(response,
						SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
				MemcachedUtils.getInstance().getClient()
						.set(ticket, 1 * 60 * 60, ssoUser);
			}
		} while (false);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView queryCategory(Map<String, Object> out,
			HttpServletRequest request, String code) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> mapy = CategoryFacade.getInstance().getChild(code);
		List<CategoryDO> list = new ArrayList<CategoryDO>();
		for (String cc : mapy.keySet()) {
			CategoryDO c = new CategoryDO();
			c.setCode(cc);
			c.setLabel(mapy.get(cc));
			list.add(c);
		}
		map.put("list", list);
		return printJson(map, out);
	}

	/**
	 * 塑料原料首页推荐模块管理
	 * 
	 * @param request
	 * @param out
	 * @param code
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView indexByCode(HttpServletRequest request,
			Map<String, Object> out, String code, Integer size)
			throws IOException {
		if (size != null && size.intValue() > 10) {
			size = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<DataIndexDO> list = dataIndexService.queryDataByCode(code, null,
				size);
		if ("102310041000".equals(code) || "102310041001".equals(code)) {
			List<CompanyDto> listR = new ArrayList<CompanyDto>();
			for (DataIndexDO di : list) {
				String url = di.getLink();
				Company company = new Company();
				CompanyDto dto = new CompanyDto();
				if (url.contains("http://company.zz91.com/compinfo")) {// 普会
					url = url.replace("http://company.zz91.com/compinfo", "")
							.replace(".htm", "");
					company = companyService.queryCompanyById(Integer
							.valueOf(url));
				} else if (url.contains("http://www.zz91.com/ppc/index")) {
					url = url.replace("http://www.zz91.com/ppc/index", "")
							.replace(".htm", "");
					company = companyService.queryCompanyById(Integer
							.valueOf(url));
				} else {
					url = url.replace("http://", "").replace(".zz91.com", "")
							.replace("/", "");
					Integer companyId = companyDAO
							.queryCompanyIdByDomainZz91(url);
					if (companyId == null) {
						continue;
					}
					company = companyService.queryCompanyById(companyId);
				}
				if (company == null) {
					continue;
				}
				dto.setCompany(company);
				String areaLabel = "";
				if (company.getAreaCode().length() >= 16) {
					areaLabel = CategoryFacade.getInstance().getValue(
							company.getAreaCode().substring(0, 12))
							+ " "
							+ CategoryFacade.getInstance().getValue(
									company.getAreaCode().substring(0, 16));
				} else if (company.getAreaCode().length() >= 12) {
					areaLabel = CategoryFacade.getInstance().getValue(
							company.getAreaCode().substring(0, 8))
							+ " "
							+ CategoryFacade.getInstance().getValue(
									company.getAreaCode().substring(0, 12));
				}
				dto.setAreaLabel(areaLabel);
				listR.add(dto);
			}
			map.put("list", listR);
		} else if ("102310091000".equals(code) || "102310091001".equals(code)
				|| "102310091002".equals(code) || "102310091003".equals(code)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			List<CompanyDto> list2 = new ArrayList<CompanyDto>();
			for (DataIndexDO pp : list) {
				if (pp.getTitle() != null
						&& !StringUtils.isContainCNChar(pp.getTitle())
						&& pattern.matcher(pp.getTitle()).matches()) {
					Company company = companyService.queryCompanyById(Integer
							.valueOf(pp.getTitle()));
					if (company != null) {
						CompanyDto dto = new CompanyDto();
						// 公司
						dto.setCompany(company);
						// 地区
						String location = "";
						CategoryFacade cate = CategoryFacade.getInstance();
						if (StringUtils.isNotEmpty(company.getAreaCode())) {
							if (company.getAreaCode().length() > 12) {
								location = cate.getValue(company.getAreaCode()
										.substring(0, 12))
										+ ""
										+ cate.getValue(company.getAreaCode()
												.substring(0, 16));
							} else if (company.getAreaCode().length() > 8) {
								location = cate.getValue(company.getAreaCode()
										.substring(0, 8))
										+ ""
										+ cate.getValue(company.getAreaCode()
												.substring(0, 12));
							} else if (company.getAreaCode().length() == 8) {
								location = cate.getValue(company.getAreaCode()
										.substring(0, 8));
							}
							dto.setAddress(location);
							if (pp.getLink() != null) {
								dto.setMarketWords(pp.getLink());
							}
						}
						list2.add(dto);
					}
				}
			}
			map.put("list", list2);
		} else {
			map.put("list", list);
		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView indexByCodeId(HttpServletRequest request,
			Map<String, Object> out, String code, Integer size)
			throws IOException {
		if (size != null && size.intValue() > 10) {
			size = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompanyDto> li = new ArrayList<CompanyDto>();
		List<DataIndexDO> list = dataIndexService.queryDataByCode(code, null,
				size);
		if (list.size() > 0) {
			String id = list.get(0).getTitle();
			if (id != null) {
				String[] arrId = id.split(",");
				for (int i = 0; i < arrId.length; i++) {
					CompanyDto dto = new CompanyDto();
					Company company = companyDAO.queryCompanyById(Integer
							.valueOf(arrId[i]));
					if (company != null) {
						dto.setCompany(company);
						if (company.getIntroduction() != null) {
							company.setIntroduction(StringUtils
									.removeHTML(company.getIntroduction()));
						}
						String location = "";
						if (StringUtils.isNotEmpty(company.getAreaCode())) {
							if (company.getAreaCode().length() > 12) {
								location = CategoryFacade.getInstance()
										.getValue(
												company.getAreaCode()
														.substring(0, 12))
										+ " "
										+ CategoryFacade.getInstance()
												.getValue(
														company.getAreaCode()
																.substring(0,
																		16));
							} else if (company.getAreaCode().length() > 8) {
								location = CategoryFacade.getInstance()
										.getValue(
												company.getAreaCode()
														.substring(0, 8))
										+ " "
										+ CategoryFacade.getInstance()
												.getValue(
														company.getAreaCode()
																.substring(0,
																		12));
							} else if (company.getAreaCode().length() == 8) {
								location = CategoryFacade.getInstance()
										.getValue(
												company.getAreaCode()
														.substring(0, 8));
							}
						}
						company.setAreaCode(location);
						List<Yuanliao> offerList = yuanliaoDao
								.queryYuanliaoBYCompanyId(company.getId());
						List<YuanliaoDto> yuanliao = new ArrayList<YuanliaoDto>();
						if (offerList.size() > 0) {
							int count = 2;
							if (offerList.size() < 2) {
								count = 1;
							}
							for (int b = 0; b < count; b++) {
								YuanliaoDto dto2 = new YuanliaoDto();
								dto2.setYuanliao(offerList.get(b));
								YuanliaoPic pic = new YuanliaoPic();
								pic.setYuanliaoId(offerList.get(b).getId());
								pic.setIsDel(0);
								pic.setCheckStatus(1);
								List<YuanliaoPic> listPic = yuanliaoPicDao
										.queryYuanliaoPicByYuanliaoId(pic, 1);
								if (listPic.size() > 0) {
									dto2.setPicAddress(listPic.get(0)
											.getPicAddress());
								}
								yuanliao.add(dto2);
							}
							dto.setList(yuanliao);
						}
						li.add(dto);
					}
				}
			}
		}
		map.put("list", li);
		return printJson(map, out);
	}

	/**
	 * 
	 * @param request
	 * @param out
	 * @param code
	 *            类别
	 * @param size
	 *            条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryYuanliaoByCondition(HttpServletRequest request,
			Map<String, Object> out, String code, Integer companyId,
			Integer size, Integer pic, Integer isVip, Integer noCompanyId)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PageDto<YuanliaoDto> page = new PageDto<YuanliaoDto>();
		YuanLiaoSearch search = new YuanLiaoSearch();
		search.setHasPic(1);// 有图片
		search.setCode(code);
		// search.setCategoryYuanliaoCode(code);
		search.setIsVip(isVip);
		search.setCompanyId(companyId);
		// search.setNoCompanyId(noCompanyId);
		page.setPageSize(size);
		try {
			page = yuanliaoService.searchByEngine(search, page);
		} catch (Exception e) {
		}
		map.put("list", page.getRecords());
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryCompanyPriceByCondition(
			HttpServletRequest request, Map<String, Object> out,
			String categoryCompanyPriceCode, Integer companyId, Integer size)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PageDto<CompanyPriceSearchDTO> page = new PageDto<CompanyPriceSearchDTO>();
		CompanyPriceSearchDTO companyPriceSearchDTO = new CompanyPriceSearchDTO();
		companyPriceSearchDTO
				.setCategoryCompanyPriceCode(categoryCompanyPriceCode);
		page.setPageSize(size);
		page = companyPriceService.pageCompanyPriceDtoBySearchEngine(
				companyPriceSearchDTO, page);
		map.put("list", page.getRecords());
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryPriceByCondition(HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PriceDO> list = new ArrayList<PriceDO>();
		// 获取余姚塑料城（324）
		List<PriceDO> listyy = priceService.queryListByTypeId(324, 7);
		for (PriceDO price : listyy) {
			if (price.getTitle().contains("【HDPE】")
					|| price.getTitle().contains("【LDPE】")
					|| price.getTitle().contains("【PS】")
					|| price.getTitle().contains("【PVC】")) {
				list.add(price);
			}
		}
		// 国内石化出厂价(61)
		List<PriceDO> listcc = priceService.queryListByTypeId(61, 7);
		for (PriceDO price : listcc) {
			if (price.getTitle().contains("【ABS】")
					|| price.getTitle().contains("【HDPE】")
					|| price.getTitle().contains("【LLDPE】")
					|| price.getTitle().contains("【PVC】")) {
				list.add(price);
			}
		}
		map.put("list", list);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView querySimilarCategory(HttpServletRequest request,
			Map<String, Object> out, String keyword) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isContainCNChar(keyword)) {
			// 解密
			keyword = StringUtils.decryptUrlParameter(keyword);
		}
		// List<CategoryYuanliao> list =
		// categoryYuanliaoService.querySimilarCategory(keyword);
		// map.put("list", list);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView top(HttpServletRequest request, Map<String, Object> out) {

		return null;
	}
}
