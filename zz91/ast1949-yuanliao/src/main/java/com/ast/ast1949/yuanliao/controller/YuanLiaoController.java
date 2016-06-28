/**
 * @author shiqp
 * @date 2015-08-18
 */
package com.ast.ast1949.yuanliao.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyAccessViewService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.service.yuanliao.ProvincePinyinService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.service.yuanliao.YuanliaoPicService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.PageCacheUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoConst;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.auth.frontsso.SsoUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONObject;

@Controller
public class YuanLiaoController extends BaseController {
	@Resource
	private YuanLiaoService yuanliaoService;
	@Resource
	private YuanliaoPicService yuanliaoPicService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CreditFileService creditFileService;
	@Resource
	private CompanyAttestService companyAttestService;
	@Resource
	private CreditIntegralDetailsService creditIntegralDetailsService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyAccessViewService companyAccessViewService;
	@Resource
	private PhoneCostSvrService phoneCostSvrService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneLogService phoneLogService;
	@Resource
	private PhoneClickLogService phoneClickLogService;
	@Resource
	private MyfavoriteService myfavoriteService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private PhoneClickLogService PhoneClickLogService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private ProvincePinyinService provincePinyinService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;
	@Resource
	private DataIndexService dataIndexService;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private AuthService authService;
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request) {
		Map<String, String> map = YuanliaoFacade.getInstance().getChild(
				"20091000");
		Map<String, Map<String, String>> mapResult = new HashMap<String, Map<String, String>>();
		for (String s : map.keySet()) {
			mapResult.put(s, YuanliaoFacade.getInstance().getChild(s));
		}
		out.put("mapResult", mapResult);
		out.put("map", map);
		// 类别中的拼音，与编码之间的关系
		Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
		out.put("mapMain", mapMain);
		SeoUtil.getInstance().buildSeo(out);
		return null;
	}

	@RequestMapping
	public ModelAndView detail(Map<String, Object> out,
			HttpServletRequest request, Integer id) throws IOException {
		YuanliaoDto yld = new YuanliaoDto();
		Yuanliao yuanliao = yuanliaoService.queryYuanliaoById(id);
		do {
			if (yuanliao == null || yuanliao.getIsDel() == 1
					|| yuanliao.getIsPause() == 1
					|| yuanliao.getCheckStatus() != 1) {
				break;
			}
			yld.setYuanliao(yuanliao);
			// 该原料供求的图片
			YuanliaoPic pic = new YuanliaoPic();
			pic.setYuanliaoId(id);
			pic.setCheckStatus(1);
			pic.setIsDel(0);
			List<YuanliaoPic> listpic = yuanliaoPicService
					.queryYuanliaoPicByYuanliaoId(pic, 5);
			out.put("listpic", listpic);
			// 标签
			List<String> listTag = new ArrayList<String>();
			if (StringUtils.isNotEmpty(yuanliao.getTags())) {
				for (String s : yuanliao.getTags().split(",")) {
					if (StringUtils.isNotEmpty(s)) {
						listTag.add(s);
					}
				}
			}
			out.put("listTag", listTag);
			// 货源所在地
			String address = null;
			if (StringUtils.isNotEmpty(yuanliao.getLocation())) {
				if (yuanliao.getLocation().length() > 12) {
					address = CategoryFacade.getInstance().getValue(
							yuanliao.getLocation().substring(0, 12))
							+ CategoryFacade.getInstance().getValue(
									yuanliao.getLocation().substring(0, 16));
				} else if (yuanliao.getLocation().length() > 8
						&& yuanliao.getLocation().length() < 13) {
					address = CategoryFacade.getInstance().getValue(
							yuanliao.getLocation().substring(0, 8))
							+ CategoryFacade.getInstance().getValue(
									yuanliao.getLocation().substring(0, 12));
				} else {
					if (yuanliao.getLocation().length() == 8) {
						address = CategoryFacade.getInstance().getValue(
								yuanliao.getLocation().substring(0, 8));
					}
				}
			}
			yld.setAddress(address);
			// 厂家（产地）
			if (StringUtils.isNotEmpty(yuanliao.getCategoryMainDesc())) {
				yld.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(
						yuanliao.getCategoryMainDesc()));
			} else {
				yld.setCategoryMainLabel(yuanliao.getCategoryAssistDesc());
			}
			// 类型
			if (StringUtils.isNotEmpty(yuanliao.getType())) {
				yld.setTypeLabel(CategoryFacade.getInstance().getValue(
						yuanliao.getType()));
			}
			// 加工级别名称
			yld.setProcessLabel(getLabel(yuanliao.getProcessLevel()));
			// 用途级别名称
			yld.setUsefulLabel(getLabel(yuanliao.getUsefulLevel()));
			// 特性级别名称
			yld.setCharaLabel(getLabel(yuanliao.getCharaLevel()));
			// 公司信息
			Company company = companyService.queryCompanyById(yuanliao
					.getCompanyId());
			yld.setCompany(company);
			// 诚信指数
			if (!"10051000".equals(company.getMembershipCode())
					&& !"10051003".equals(company.getMembershipCode())) {
				// 诚信指数算法：注册信息工商/个人（10分）+每个证书（5分）+ 再生通服务年限
				// 荣誉证书
				List<CreditFileDo> fileList = creditFileService
						.queryFileByCompany(company.getId());
				// 审核通过证书
				List<CreditFileDo> passList = new ArrayList<CreditFileDo>();
				for (CreditFileDo file : fileList) {
					if ("1".equals(file.getCheckStatus())) {
						passList.add(file);
					}
				}
				// 未过期证书
				List<CreditFileDo> NoExpiredList = new ArrayList<CreditFileDo>();
				for (CreditFileDo file : passList) {
					if (file.getEndTime() != null
							&& file.getEndTime().getTime()
									- new Date().getTime() < 0) {
					} else {
						NoExpiredList.add(file);
					}
				}
				// 认证信息
				CompanyAttest companyAttest = new CompanyAttest();
				companyAttest.setCompanyId(company.getId());
				companyAttest.setCheckStatus("1");
				CompanyAttest attest = companyAttestService
						.queryOneInfo(companyAttest);
				Integer attestIntegral = 0;
				if (attest != null && "1".equals(attest.getCheckStatus())) {
					attestIntegral += 10;
				}
				attestIntegral += NoExpiredList.size()
						* 5
						+ creditIntegralDetailsService
								.countIntegralByOperationKey(company.getId(),
										"service_zst");
				out.put("attestIntegral", attestIntegral);
			}
			String province = "";
			String city = "";
			if (company.getAreaCode().length() > 12) {
				province = CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 12));
				city = CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 16));
			} else if (company.getAreaCode().length() == 12) {
				province = CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 12));
			} else if (company.getAreaCode().length() == 8) {
				province = CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 8));
			}
			out.put("province", province);
			out.put("city", city);
			out.put("yld", yld);
			// 废塑料编码
			String code = URLEncoder.encode("废塑料", HttpUtils.CHARSET_UTF8);
			out.put("code", code);
			// 企业报价类别
			CategoryCompanyPriceDO ccp = categoryCompanyPriceService
					.queryCategoryCompanyPriceByLabel(YuanliaoFacade
							.getInstance().getValue(
									yuanliao.getCategoryYuanliaoCode()
											.substring(0, 12)));
			if (ccp != null) {
				out.put("priceCode", ccp.getCode());
			}
			// 三级类别
			if (yuanliao.getCategoryYuanliaoCode().length() > 11) {
				String categoryS = yuanliao.getCategoryYuanliaoCode()
						.substring(0, 12);
				out.put("categoryS", categoryS);
			}
			// 上一条 下一条
			List<Yuanliao> list = yuanliaoService
					.queryYuanliaoBYCompanyId(yuanliao.getCompanyId());
			out.put("list", list);
			// 判断是否认证通过
			if (companyAttestService.validatePassOrNot(company.getId())) {
				out.put("isAttest", 1);
			}
			SsoUser sessionUser = getCachedUser(request);
			out.put("sessionUser", sessionUser);
			// 帐号信息
			CompanyAccount account = companyAccountService
					.queryAccountByCompanyId(yuanliao.getCompanyId());
			out.put("account", account);
			// 入驻的产业带
			List<Market> listMk = marketCompanyService
					.queryMarketByCompanyId(yuanliao.getCompanyId());
			if (listMk.size() > 0) {
				out.put("market", listMk.get(0));
			}
			// 类别中的拼音，与编码之间的关系
			Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
			out.put("mapMain", mapMain);
			// 当前位置
			if (yuanliao.getCategoryYuanliaoCode().length() > 11) {
				out.put("fisrtCode", yuanliao.getCategoryYuanliaoCode()
						.substring(0, 12));
				out.put("fisrtLabel",
						YuanliaoFacade.getInstance().getValue(
								yuanliao.getCategoryYuanliaoCode().substring(0,
										12)));
				out.put("secondCode", yuanliao.getCategoryYuanliaoCode());
				out.put("secondLabel",
						YuanliaoFacade.getInstance().getValue(
								yuanliao.getCategoryYuanliaoCode()));
			}
			// 判断是否再生通会员
			if (crmCompanySvrService.validatePeriod(yuanliao.getCompanyId(),
					"10001003")) {
				out.put("isZSVip", 1);
			}
			// 联系方式
			out.put("zuheString", getCompanyInfo(company, account));

			// seo三要素
			String seotitle = CategoryFacade.getInstance().getValue(
					yuanliao.getYuanliaoTypeCode())
					+ yuanliao.getTitle();
			String seoKeword = CategoryFacade.getInstance().getValue(
					yuanliao.getYuanliaoTypeCode())
					+ yuanliao.getTitle();
			String seoDesc = company.getName()
					+ CategoryFacade.getInstance().getValue(
							yuanliao.getYuanliaoTypeCode())
					+ yuanliao.getTitle() + ",";
			if (StringUtils.isNotEmpty(yuanliao.getCategoryMainDesc())) {
				seoDesc = seoDesc
						+ "厂家："
						+ YuanliaoFacade.getInstance().getValue(
								yuanliao.getCategoryMainDesc()) + ",";
			} else {
				seoDesc = seoDesc + "厂家：" + yuanliao.getCategoryAssistDesc()
						+ ",";
			}
			if (StringUtils.isNotEmpty(yuanliao.getTradeMark())) {
				seoDesc = seoDesc + "牌号：" + yuanliao.getTradeMark() + ",";
			}
			if (yuanliao.getPrice() != null && yuanliao.getPrice() != 0) {
				seoDesc = seoDesc + "产品价格：" + yuanliao.getPrice()
						+ yuanliao.getPriceUnit() + "/" + yuanliao.getUnit()
						+ ",";
			} else if (yuanliao.getMinPrice() != null) {
				seoDesc = seoDesc + "产品价格：" + yuanliao.getMinPrice() + "-"
						+ yuanliao.getMaxPrice() + yuanliao.getPriceUnit()
						+ "/" + yuanliao.getUnit() + ",";
			} else {
				seoDesc = seoDesc + "产品价格：" + "面议" + ",";
			}
			if (yuanliao.getQuantity() != 0) {
				seoDesc = seoDesc
						+ CategoryFacade.getInstance().getValue(
								yuanliao.getYuanliaoTypeCode()) + "数量："
						+ yuanliao.getQuantity() + yuanliao.getUnit() + ",";
			} else {
				seoDesc = seoDesc
						+ CategoryFacade.getInstance().getValue(
								yuanliao.getYuanliaoTypeCode()) + "数量：" + "不限"
						+ ",";
			}
			if (StringUtils.isNotEmpty(address)) {
				seoDesc = seoDesc + "货物所在地：" + address + ",";
			}
			SeoUtil.getInstance().buildSeo("detail", new String[] { seotitle },
					new String[] { seoKeword }, new String[] { seoDesc }, out);
			return null;
		} while (true);
		return new ModelAndView("/common/error");
	}

	public String getLabel(String code) {
		String label = "";
		if (StringUtils.isNotEmpty(code)) {
			for (String s : code.split(",")) {
				if (StringUtils.isNotEmpty(label)) {
					label = label + ","
							+ CategoryFacade.getInstance().getValue(s);
				} else {
					label = CategoryFacade.getInstance().getValue(s);
				}
			}
		}
		return label;

	}

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
				if (company.getAreaCode().length() > 12) {
					areaLabel = CategoryFacade.getInstance().getValue(
							company.getAreaCode().substring(0, 12))
							+ " "
							+ CategoryFacade.getInstance().getValue(
									company.getAreaCode().substring(0, 16));
				} else if (company.getAreaCode().length() > 8) {
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
		} else {
			map.put("list", list);
		}
		return printJson(map, out);
	}

	@RequestMapping
	public String getCompanyInfo(Company company, CompanyAccount account) {
		// 组合联系方式中的内容 ，外层是发布着会员类型、内层为查看者
		String zuheString = "";
		List<String> list = new ArrayList<String>();
		// 检验该普会是否有微信权限访问 该公司
		if ("10051000".equals(company.getMembershipCode())) {
			// 普通会员
			if (StringUtils.isNotEmpty(account.getTel())) {
				if (StringUtils.isNotEmpty(account.getTelAreaCode())) {
					list.add("<td>电话：" + account.getTelAreaCode() + "-"
							+ account.getTel() + "</td>");
				} else {
					list.add("<td>电话：" + account.getTel() + "</td>");
				}
			}
			list.add("<td>公司商铺：" + "<a href=\"http://company.zz91.com/compinfo"
					+ account.getCompanyId() + ".htm\" target=\"_blank\">"
					+ "http://company.zz91com/compinfo"
					+ account.getCompanyId() + ".htm </a></td>");
			if (StringUtils.isNotEmpty(account.getMobile())) {
				list.add("<td>手机：" + account.getMobile() + "</td>");
			}
			if (StringUtils.isNotEmpty(account.getEmail())) {
				list.add("<td>电子邮箱：" + account.getEmail() + "</td>");
			}
		} else if ("10051003".equals(company.getMembershipCode())) {
			Phone phone = phoneService.queryByCompanyId(account.getCompanyId());
			// 来电宝会员
			list.add("<td>电话：<img src=\"http://img0.zz91.com/zz91/polymer/images/icon_ldb.jpg\" /><font class=\"red bold f14\">"
					+ phone.getTel() + "</font></td>");
			list.add("<td>公司商铺：" + "<a href=\"http://www.zz91.com/ppc/index"
					+ account.getCompanyId() + ".htm\" target=\"_blank\">"
					+ "www.zz91.com/ppc/index" + account.getCompanyId()
					+ ".htm </a></td>");
		} else {
			// 高会
			// 区号、电话、公司商铺（http://"+data.company.domainZz91+".zz91.com）、手机、企业网站、电子邮箱
			if (StringUtils.isNotEmpty(account.getTel())) {
				if (StringUtils.isNotEmpty(account.getTelAreaCode())) {
					list.add("<td>电话：" + account.getTelAreaCode() + "-"
							+ account.getTel() + "</td>");
				} else {
					list.add("<td>电话：" + account.getTel() + "</td>");
				}
			}
			if (StringUtils.isNotEmpty(company.getDomainZz91())) {
				list.add("<td>公司商铺：" + "<a href=\"http://"
						+ company.getDomainZz91()
						+ ".zz91.com\" target=\"_blank\">" + "http://"
						+ company.getDomainZz91() + ".zz91.com </a></td>");
			}
			if (StringUtils.isNotEmpty(account.getMobile())) {
				list.add("<td>手机：" + account.getMobile() + "</td>");
			}
			if (StringUtils.isNotEmpty(company.getWebsite())) {
				list.add("<td>企业网站：" + company.getWebsite() + "</td>");
			}
			if (StringUtils.isNotEmpty(account.getEmail())) {
				list.add("<td>电子邮箱：" + account.getEmail() + "</td>");
			}
		}
		for (Integer i = 0; i < list.size(); i++) {
			if (i % 2 == 0) {
				// 偶数
				zuheString = zuheString + "<tr>" + list.get(i);
			} else {
				// 基数
				zuheString = zuheString + list.get(i) + "</tr>";
			}
		}
		return zuheString;
	}

	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}

	@RequestMapping
	public ModelAndView collectYuanliao(HttpServletRequest request,
			Integer yuanliaoId, Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		SsoUser ssoUser = getCachedUser(request);
		Integer i = 0;
		do {
			if (ssoUser == null) {
				map.put("collect", "login");
				break;
			}
			Yuanliao yl = yuanliaoService.queryYuanliaoById(yuanliaoId);
			if (yl == null) {
				break;
			}
			boolean boo = myfavoriteService.isExist(ssoUser.getCompanyId(),
					yuanliaoId, "10091015");
			if (boo) {
				map.put("collect", "has");
				break;
			}
			MyfavoriteDO favor = new MyfavoriteDO();
			favor.setAccount(ssoUser.getAccount());
			favor.setCompanyId(ssoUser.getCompanyId());
			favor.setContentId(yuanliaoId);
			favor.setContentTitle(yl.getTitle());
			favor.setFavoriteTypeCode("10091015");
			i = myfavoriteService.insertMyCollect(favor);
		} while (false);
		if (i > 0) {
			map.put("collect", "success");
		}
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView initCollect(HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		SsoUser ssoUser = getCachedUser(request);
		String idlist = ",";
		if (ssoUser != null) {
			List<MyfavoriteDO> list = myfavoriteService
					.queryYuanliaoCollectList(ssoUser.getCompanyId(),
							"10091014");
			for (MyfavoriteDO my : list) {
				idlist = idlist + my.getContentId() + ",";
			}
		}
		map.put("idlist", idlist);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, YuanLiaoSearch search,
			Map<String, Object> out, PageDto<YuanliaoDto> page,
			String mainCode, String pro, Integer aId) throws Exception {
		// 省份-拼音关系
		Map<String, String> mapProvince = provincePinyinService
				.queryAllProvincePinyin();
		out.put("propinyin", mapProvince);
		// 类别中的拼音，与编码之间的关系
		Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
		out.put("mapMain", mapMain);
		if (StringUtils.isNotEmpty(mainCode)
				|| StringUtils.isNotEmpty(mapMain.get(pro))) {
			if (StringUtils.isEmpty(mainCode)) {
				mainCode = pro;
				pro = "";
			}
			search.setCode(mapMain.get(mainCode));
		} else {
			aId = null;
		}
		// 厂家（产地）与编号之间的关系
		Map<String, String> mapC = YuanliaoFacade.CATE_MAP;
		out.put("mapC", mapC);
		if (aId != null) {
			search.setCode(mapC.get(String.valueOf(aId)));
		}
		if (StringUtils.isNotEmpty(pro)) {
			search.setProvince(mapProvince.get(pro));
		}
		// 类别or厂家（产地）
		Map<String, String> mapc = new HashMap<String, String>();
		if (StringUtils.isEmpty(search.getCode())) {
			mapc = YuanliaoFacade.getInstance().getChild("20091000");
			search.setCode("20091000");
		} else if (search.getCode().length() < 20) {
			mapc = YuanliaoFacade.getInstance().getChild(search.getCode());
		} else {
			mapc = YuanliaoFacade.getInstance().getChild(
					search.getCode()
							.substring(0, search.getCode().length() - 4));
		}
		out.put("mapc", mapc);
		out.put("codelength", search.getCode().length());
		// 上一级别
		Map<String, String> maps = new HashMap<String, String>();
		if (search.getCode().length() > 16) {
			maps = YuanliaoFacade.getInstance().getChild(
					search.getCode().substring(0, 12));
		} else if (search.getCode().length() > 8) {
			maps = YuanliaoFacade.getInstance().getChild(
					search.getCode()
							.substring(0, search.getCode().length() - 4));
		}
		out.put("maps", maps);
		// 省份
		Map<String, String> mapp = CategoryFacade.getInstance().getChild(
				"10011000");
		if (mapp == null) {
			mapp = new HashMap<String, String>();
		}
		out.put("mapp", mapp);

		if (StringUtils.isEmpty(search.getType())) {
			search.setType("10331000");
		}
		page = yuanliaoService.pageSearchYuanliaoList(search, page);
		out.put("page", page);
		out.put("search", search);
		out.put("mainCode", mainCode);
		out.put("pro", pro);
		out.put("aId", aId);
		if (search.getCode().length() > 12) {
			out.put("headCode", mapMain.get(search.getCode().substring(0, 12)));
		}
		// 当前位置
		if (search.getCode().length() > 8) {
			out.put("fisrtCode", search.getCode().substring(0, 12));
			out.put("fisrtLabel",
					YuanliaoFacade.getInstance().getValue(
							search.getCode().substring(0, 12)));
			if (search.getCode().length() > 12) {
				out.put("secondLabel",
						YuanliaoFacade.getInstance().getValue(
								search.getCode().substring(0, 16)));
			}
			if (search.getCode().length() > 16) {
				out.put("thirdLabel",
						YuanliaoFacade.getInstance().getValue(
								search.getCode().substring(0, 20)));
			}
		}
		out.put("limit", search.getLimit());
		if (StringUtils.isNotEmpty(search.getCode())) {
			out.put("codelabel",
					YuanliaoFacade.getInstance().getValue(search.getCode()));
		}
		if (StringUtils.isNotEmpty(search.getProvince())) {
			out.put("provineLabel",
					CategoryFacade.getInstance().getValue(search.getProvince()));
		}
		if (StringUtils.isNotEmpty(search.getType())) {
			out.put("typeLabel",
					CategoryFacade.getInstance().getValue(search.getType()));
		}
		out.put("hrefUrl", "###");
		// 三要素
		String[] seoContent = new String[2];
		seoContent[0] = "";
		if (StringUtils.isNotEmpty(search.getProvince())) {
			seoContent[0] = CategoryFacade.getInstance().getValue(
					search.getProvince());
		}
		seoContent[0] = seoContent[0]
				+ CategoryFacade.getInstance().getValue(search.getType());
		if (search.getCode().length() > 16) {
			seoContent[0] = seoContent[0]
					+ YuanliaoFacade.getInstance().getValue(search.getCode());
			seoContent[0] = seoContent[0]
					+ YuanliaoFacade.getInstance().getValue(
							search.getCode().substring(0, 16)) + "原料";
			seoContent[1] = "_"
					+ YuanliaoFacade.getInstance().getValue(
							search.getCode().substring(0, 12)) + "原料";
		} else if (search.getCode().length() > 12) {
			seoContent[0] = seoContent[0]
					+ YuanliaoFacade.getInstance().getValue(search.getCode())
					+ "原料";
			seoContent[1] = "_"
					+ YuanliaoFacade.getInstance().getValue(
							search.getCode().substring(0, 12)) + "原料";
		} else if (search.getCode().length() > 8) {
			seoContent[0] = seoContent[0]
					+ YuanliaoFacade.getInstance().getValue(search.getCode())
					+ "原料";
			seoContent[1] = "";
		} else {
			seoContent[0] = seoContent[0] + "塑料原料" + "原料";
			seoContent[1] = "";
		}
		SeoUtil.getInstance().buildSeo("list", seoContent, seoContent,
				seoContent, out);
		return null;
	}

	@RequestMapping
	public ModelAndView search(HttpServletRequest request,
			YuanLiaoSearch search, Map<String, Object> out,
			PageDto<YuanliaoDto> page) throws Exception {
		SsoUser sessionUser = getCachedUser(request);
		out.put("sessionUser", sessionUser);
		if (!StringUtils.isContainCNChar(search.getKeyword())) {
			// 解密
			search.setKeyword(StringUtils.decryptUrlParameter(search
					.getKeyword()));
			String keyword = search.getKeyword();
			out.put("keyword",
					URLEncoder.encode(keyword, HttpUtils.CHARSET_UTF8));
		}
		// 省份
		Map<String, String> mapp = CategoryFacade.getInstance().getChild(
				"10011000");
		if (mapp == null) {
			mapp = new HashMap<String, String>();
		}
		if (StringUtils.isEmpty(search.getType())) {
			search.setType("10331000");
		}
		// 类别中的拼音，与编码之间的关系
		Map<String, String> mapMain = YuanliaoFacade.PINYIN_MAP;
		out.put("mapMain", mapMain);
		out.put("mapp", mapp);
		page = yuanliaoService.pageSearchYuanliaoList(search, page);
		out.put("page", page);
		out.put("search", search);
		out.put("province",
				CategoryFacade.getInstance().getValue(search.getProvince()));
		out.put("limit", search.getLimit());
		out.put("hrefUrl", "###");
		SeoUtil.getInstance().buildSeo("search",
				new String[] { search.getKeyword() }, new String[] {},
				new String[] {}, out);
		return null;
	}

	@RequestMapping
	public ModelAndView queryCategoryYuanliaoByParentCode(
			Map<String, Object> out, HttpServletRequest request,
			String parentCode) throws IOException {
		Map<String, String> map = YuanliaoFacade.getInstance().getChild(
				parentCode);
		if (map == null) {
			map = new HashMap<String, String>();
		}
		List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		for (String s : map.keySet()) {
			CategoryYuanliao yl = new CategoryYuanliao();
			yl.setCode(s);
			yl.setLabel(map.get(s));
			list.add(yl);
		}
		return printJson(list, out);
	}

	@RequestMapping
	public ModelAndView publishYuanliao(Map<String, Object> out,
			HttpServletRequest request, Yuanliao yuanliao, Integer time)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		if (time != null) {
			// 过期时间
			yuanliao.setExpireTime(DateUtil.getDateAfterDays(new Date(), time));
		}
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			yuanliao.setAccount(sessionUser.getAccount());
			yuanliao.setCompanyId(sessionUser.getCompanyId());
			yuanliao.setSourceTypeCode(2);
			if (StringUtils.isNotEmpty(yuanliao.getTags())) {
				String tags = "";
				for (String s : yuanliao.getTags().split(",")) {
					if (StringUtils.isNotEmpty(s)) {
						if (StringUtils.isNotEmpty(tags)) {
							tags = tags + "," + s;
						} else {
							tags = s;
						}

					}
				}
				yuanliao.setTags(tags);
			}
			Integer i = yuanliaoService.insertYuanliao(yuanliao);
			if (i > 0) {
				map.put("publish", "yes");
			} else {
				map.put("publish", "no");
			}
		} else {
			map.put("publish", "login");
		}
		return printJson(map, out);
	}

	/**
	 * 登录页面
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void login(String account, Map<String, Object> out, String url,
			String flag) throws UnsupportedEncodingException {
		// 设置seo
		if (StringUtils.isNotEmpty(account)
				&& !StringUtils.isContainCNChar(account)) {
			account = StringUtils.decryptUrlParameter(account);
		}
		out.put("account", account);
		out.put("url", url);
		SeoUtil.getInstance().buildSeo("login", out);
	}

	/**
	 * 登录页面
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView miniLogin(String account, Map<String, Object> out,
			String url, String flag) throws UnsupportedEncodingException {
		// 设置seo
		if (StringUtils.isNotEmpty(account)
				&& !StringUtils.isContainCNChar(account)) {
			account = StringUtils.decryptUrlParameter(account);
		}
		out.put("account", account);
		out.put("url", url);
		SeoUtil.getInstance().buildSeo("login", out);
		return null;
	}

	/**
	 * 登陆
	 * 
	 * @throws IOException
	 * 
	 */
	@RequestMapping
	public ModelAndView doLogin(HttpServletRequest request, String account,
			String password, HttpServletResponse response,
			Map<String, Object> out, String mini) throws IOException {
		String url = request.getParameter("url");
		ExtResult rs = new ExtResult();
		String tacket = "";
		do {
			if (StringUtils.isEmpty(password)) {
				out.put("error", AuthorizeException
						.getMessage(AuthorizeException.NEED_PASS));
				rs.setData(AuthorizeException
						.getMessage(AuthorizeException.NEED_PASS));
				break;
			}
			String ip = HttpUtils.getInstance().getIpAddr(request);
			try {
				tacket = MD5.encode(account + password + ip);
			} catch (Exception e) {
				e.printStackTrace();
			}
			SsoUser ssoUser = null;
			try {
				ssoUser = SsoUtils.getInstance().validateUser(response,
						account, password, null,
						HttpUtils.getInstance().getIpAddr(request));
			} catch (Exception e) {
				out.put("error", AuthorizeException
						.getMessage(AuthorizeException.ERROR_LOGIN));
				rs.setData(AuthorizeException
						.getMessage(AuthorizeException.ERROR_LOGIN));
			}
			if (ssoUser != null) {
				// 登陆信息设置
				String cookie = HttpUtils.getInstance().getCookie(request,
						AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
				if (StringUtils.isEmpty(cookie)) {
					HttpUtils.getInstance().setCookie(response,
							AuthService.AUTO_LOGIN_KEY, tacket,
							AuthService.DOMAIN, 30 * 24 * 60 * 60);
				}
				setSessionUser(request, ssoUser);
				if (StringUtils.isEmpty(url)) {
					url = AddressTool.getAddress("yuanliao");
				}
				if (mini != null && mini.equals("1")) {
					rs.setSuccess(true);
					return printJson(rs, out);
				}
				return new ModelAndView("redirect:" + url);
			}
		} while (false);
		if (mini != null && mini.equals("1")) {
			rs.setSuccess(false);
			return printJson(rs, out);
		}
		out.put("url", url);
		out.put("account", account);
		return new ModelAndView("login");
	}

	@RequestMapping
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 清除改cookie
		String cookie = HttpUtils.getInstance().getCookie(request,
				AuthService.AUTO_LOGIN_KEY, AuthService.DOMAIN);
		authService.removeAuthLoginByCookie(cookie);

		SsoUtils.getInstance().logout(request, response, null);
		PageCacheUtil.setNoCache(response);
		return new ModelAndView("redirect:"
				+ AddressTool.getAddress("yuanliao"));
	}

	@RequestMapping
	public ModelAndView accessToQQLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String referUrl) {
		PageCacheUtil.setNoCDNCache(response);

		if (StringUtils.isEmpty(referUrl)) {
			referUrl = request.getHeader("referer");
		}

		// 获取qqClientId 错了可能就导入其他网站去了 后台可配置
		String qqClientId = ParamUtils.getInstance().getValue("oauth_config",
				"qqClientId");
		if (StringUtils.isEmpty(qqClientId)) {
			// 默认
			qqClientId = "100345758";
		}
		// 统计点击次数代码
		LogUtil.getInstance().log("qq_login", "qq_login_click");

		return new ModelAndView(
				"redirect:"
						+ "http://openapi.qzone.qq.com/oauth/show?which=ConfirmPage&display=pc&client_id="
						+ qqClientId
						+ "&response_type=code&scope=get_user_info&redirect_uri="
						+ AddressTool.getAddress("yuanliao")+"/qqLogin.htm"
						+ "?referUrl=" + referUrl);
	}

	@RequestMapping("qqLogin.htm")
	public ModelAndView qqLogin(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, String code,
			String deskUrl, String accessToken, String account, String error,
			String referUrl) throws UnsupportedEncodingException {
		do {
			if (StringUtils.isEmpty(code) && StringUtils.isEmpty(accessToken)) {
				break;
			}
			if (StringUtils.isNotEmpty(code)
					&& StringUtils.isEmpty(accessToken)) {
				// 根据code 获取access_token(登录状态) 和openId(与qq号码绑定的关联id)
				String domain = request.getServerName();
				String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=100345758&client_secret=220387a3670793e0f337c616ce21b3a6&redirect_uri=http://"
						+ domain + "/qqLogin.htm&code=" + code;
				String accessTokenResult;
				accessTokenResult = HttpUtils.getInstance().httpGet(url,HttpUtils.CHARSET_UTF8);
				if (StringUtils.isEmpty(accessTokenResult)) {
					break;
				}
				if (accessTokenResult.indexOf("error") != -1) {
					return new ModelAndView("redirect:"
							+ AddressTool.getAddress("yuanliao"));
				}
				accessToken = getQQAccessToken(accessTokenResult);
			}
			// 获取不到access_token 跳回首页
			if (StringUtils.isEmpty(accessToken)) {
				break;
			}
			// 账号重新绑定
			if (StringUtils.isNotEmpty(account)) {
				out.put("account", account);
				break;
			}
			String openId = getQQOpenId(accessToken);
			// 获取不到openId 跳回首页
			if (StringUtils.isEmpty(openId)) {
				break;
			}
			OauthAccess oa = oauthAccessService.queryAccessByOpenIdAndType(
					openId, OauthAccessService.OPEN_TYPE_QQ);
			// openId关联账号不存在, 跳转至 绑定账号或者重新注册账号
			if (oa == null || StringUtils.isEmpty(oa.getTargetAccount())) {
				break;
			}
			// 自动登录
			account = oa.getTargetAccount();
			SsoUser ssoUser = companyAccountService.validateQQLogin(account,
					HttpUtils.getInstance().getIpAddr(request));
			if (ssoUser == null) {
				break;
			}
			HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY,
					ssoUser.getTicket(), SsoConst.SSO_DOMAIN, null);
			setSessionUser(request, ssoUser);

			// 登记登陆信息
			LogUtil.getInstance().log("" + ssoUser.getCompanyId(), "login",
					HttpUtils.getInstance().getIpAddr(request),
					"type:'qq-login';account:'" + ssoUser.getAccount() + "'");
			if (referUrl != null && referUrl.contains("url=")) {
				referUrl = referUrl.substring(referUrl.indexOf("url=")
						+ "url=".length());
			}
			return new ModelAndView("redirect:" + referUrl);
		} while (false);
		if (StringUtils.isNotEmpty(error)) {
			out.put("error", StringUtils.decryptUrlParameter(error));
		}
		out.put("deskUrl", deskUrl);
		out.put("referUrl", referUrl);
		out.put("accessToken", accessToken);
		return new ModelAndView("qqlogin/login");
	}

	@RequestMapping
	public ModelAndView doQQRegister(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			CompanyAccount companyAccount, Company company, String accessToken,
			String passwordConfirm, String referUrl)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		do {
			if (companyAccount.getAccount() == null) {
				companyAccount.setAccount(companyAccount.getMobile());
			}

			// 注册来源
			company.setRegfromCode("10031042");

			try {

				// 转中文地址
				CategoryFacade cate = CategoryFacade.getInstance();
				String location = "";
				if (company.getAreaCode() != null) {
					if (company.getAreaCode().length() > 12) {
						location = cate.getValue(company.getAreaCode().substring(0,
								12))
								+ ""
								+ cate.getValue(company.getAreaCode().substring(0,
										16));
					} else if (company.getAreaCode().length() > 8) {
						location = cate.getValue(company.getAreaCode().substring(0,
								8))
								+ ""
								+ cate.getValue(company.getAreaCode().substring(0,
										12));
					} else if (company.getAreaCode().length() == 8) {
						location = cate.getValue(company.getAreaCode().substring(0,
								8));
					}
					company.setAddress(location+company.getAddress());
				}
				String username = companyAccountService.registerUser(
						companyAccount.getAccount(), companyAccount.getEmail(),
						passwordConfirm, passwordConfirm, companyAccount,
						company, HttpUtils.getInstance().getIpAddr(request));
				if (StringUtils.isEmpty(username)) {
					model.put("error", "注册失败");
					break;
				}
				out.put("username", username);
				companyValidateService.sendValidateByEmail(
						companyAccount.getAccount(), companyAccount.getEmail());

				// 统计注册次数
				LogUtil.getInstance().log("qq_login", "qq_login_register");

				// 自动登录
				SsoUser ssoUser = SsoUtils.getInstance().validateUser(response,
						companyAccount.getAccount(), passwordConfirm, null,
						HttpUtils.getInstance().getIpAddr(request));
				String password = authService.queryPassword(companyAccount
						.getAccount());

				if (ssoUser != null) {
					// 登记登陆信息
					LogUtil.getInstance().log(
							"" + ssoUser.getCompanyId(),
							"login",
							HttpUtils.getInstance().getIpAddr(request),
							"type:'qq-reg-login';account:'"
									+ ssoUser.getAccount() + "'");

					String key = UUID.randomUUID().toString();
					String ticket = "";
					try {
						ticket = MD5.encode(companyAccount.getAccount()
								+ password + key);
					} catch (NoSuchAlgorithmException e) {
					} catch (UnsupportedEncodingException e) {
					}
					ssoUser.setTicket(ticket);
					HttpUtils.getInstance().setCookie(response,
							SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN,
							null);
					MemcachedUtils.getInstance().getClient()
							.set(ticket, 1 * 60 * 60, ssoUser);
				}

				// 增加积分
				scoreChangeDetailsService
						.saveChangeDetails(new ScoreChangeDetailsDo(ssoUser
								.getCompanyId(), null, "base_register", null,
								null, null));
			} catch (Exception e) {
				model.put("error", "注册失败");
				break;
			}

			if (accessToken.indexOf("error") != -1) {
				return new ModelAndView("redirect:"
						+ AddressTool.getAddress("yuanliao"));
			}
			out.put("accessToken", accessToken);
			// 获取 openId
			String openId = getQQOpenId(accessToken);
			Integer i = oauthAccessService.addOneAccess(openId,
					OauthAccessService.OPEN_TYPE_QQ,
					companyAccount.getAccount());
			if (i == null || i <= 0) {
				model.put("error", "QQ绑定失败");
				break;
			}
			// 清除accessToken
			OauthAccessService.OPEN_ID_MAP.remove(accessToken);
			out.put("logined", 1);
			out.put("referUrl", referUrl);
			if (referUrl != null && referUrl.contains("url=")) {
				referUrl = referUrl.substring(referUrl.indexOf("url=")
						+ "url=".length());
			}
			model.put("url", referUrl);
			return printJson(model, out);
		} while (false);
		out.put("accessToken", accessToken);
		out.put("referUrl", referUrl);
		return printJson(model, out);
	}

	@RequestMapping
	public ModelAndView doQQBind(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String account, String password, String accessToken,
			String referUrl, String regfromCode) throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		do {
			// 自动登录
			SsoUser user = null;
			try {
				user = SsoUtils.getInstance().validateUser(response, account,
						password, AstConst.COOKIE_AGE,
						HttpUtils.getInstance().getIpAddr(request));
			} catch (NoSuchAlgorithmException e) {
			} catch (AuthorizeException e) {
				model.put("error",
						AuthorizeException.getMessage(e.getMessage()));
			} catch (IOException e) {
			}
			if (user == null) {
				break;
			}
			setSessionUser(request, user);

			if (accessToken.indexOf("error") != -1) {
				model.put("error", "登陆错误");
				break;
			}

			String openId = getQQOpenId(accessToken);
			// 确定账号是否已经绑定过
			OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(
					account, OauthAccessService.OPEN_TYPE_QQ);
			if (oa != null) {
				// 账号已经绑定过
				model.put("error", "账号已经绑定");
				out.put("error", "账号已经绑定");
				out.put("referUrl", referUrl);
				break;
			}
			// 执行 绑定操作 update 或者 insert openid
			oa = oauthAccessService.queryAccessByOpenIdAndType(openId,
					OauthAccessService.OPEN_TYPE_QQ);

			// 登记登陆信息
			LogUtil.getInstance().log("" + user.getCompanyId(), "login",
					HttpUtils.getInstance().getIpAddr(request),
					"type:'qq-bind-login';account:'" + user.getAccount() + "'");

			// QQ报价而绑定
			if (referUrl.indexOf("priceDetails") != -1) {
				regfromCode = "10031033";
			}

			// QQ询盘而绑定
			else if (referUrl.indexOf("inquiryApi") != -1
					|| referUrl.indexOf("productdetails") != -1) {
				regfromCode = "10031034";
			}
			// QQ发布产品而绑定
			else if (referUrl.indexOf("postoffer_step1") != -1) {
				regfromCode = "10031036";
			}
			// QQ登录绑定
			else
				regfromCode = "10031028";

			companyService.updateRegFromCode(
					companyAccountService.queryCompanyIdByAccount(account),
					regfromCode);

			if (oa != null) {
				oauthAccessService.updateByOpenId(openId, account);
			} else {
				oauthAccessService.addOneAccess(openId,
						OauthAccessService.OPEN_TYPE_QQ, account);
			}

			// 清除accessToken
			OauthAccessService.OPEN_ID_MAP.remove(accessToken);
			out.put("logined", 1);
			// 统计注册次数
			LogUtil.getInstance().log("qq_login", "qq_login_bind");
			model.put("url", AddressTool.getAddress("yuanliao"));
			return printJson(model, out);
		} while (false);
		// 1、账号不存在 或者 账号密码错误 绑定失败 2、清空登录状态
		SsoUtils.getInstance().logout(request, response, null);
		out.put("account", account);
		out.put("accessToken", accessToken);
		out.put("referUrl", referUrl);
		return printJson(model, out);
	}

	private String getQQAccessToken(String accessTokenUrl) {
		String[] str = accessTokenUrl.split("&");
		accessTokenUrl = str[0].replace("access_token=", "");
		return accessTokenUrl;
	}

	public String getQQOpenId(String accessToken) {
		String openId = "";
		do {
			openId = OauthAccessService.OPEN_ID_MAP.get(accessToken);
			if (StringUtils.isNotEmpty(openId)) {
				break;
			}

			String str = null;
			str = HttpUtils.getInstance().httpGet("https://graph.qq.com/oauth2.0/me?access_token=" + accessToken, HttpUtils.CHARSET_UTF8);
			// 取大括号的内容 正则
			Pattern pattern = Pattern.compile("\\{[^}]+\\}");
			Matcher matcher = pattern.matcher(str);
			matcher.find();
			str = matcher.group();
			JSONObject jobj = JSONObject.fromObject(str);
			try {
				openId = jobj.getString("openid");
			} catch (Exception e) {
				openId = null;
			}
			if (StringUtils.isNotEmpty(openId)) {
				OauthAccessService.OPEN_ID_MAP.put(accessToken, openId);
			}
			return openId;
		} while (false);
		return openId;
	}

}
