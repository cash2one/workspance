package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.goods.PictureService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.velocity.AddressTool;

@Controller
public class CertificationController extends BaseController {
	@Resource
	private CompanyInfoService companyInfoService;
	@Resource
	private PictureService pictureService;
	@Resource
	private AddressService addressService;
	@Resource
	private CompanyAccountService companyAccountService;

	/**
	 * 完善认证信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView rzxinxi(Map<String, Object> out,
			HttpServletRequest request) {
		SsoUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade"));
		}
		// 判断是否完善地址信息和认证信息
		Boolean addflag = false;
		List<Address> address = addressService.selectByDelId(user
				.getCompanyId());
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(user
				.getCompanyId());
		Integer i = companyInfo.getCreditStatus();
		if (address.size() > 0) {
			addflag = true;
		}
		out.put("flag", addflag);
		out.put("i", i);
		if (addflag && i == 2) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("trade") + "/goods/post_step1.htm");
		}
		SeoUtil.getInstance().buildSeo("renzhen", out);
		return null;
	}

	/**
	 * 添加公司认证信息
	 */

	@RequestMapping
	public ModelAndView addBus(String companyName, String registerNum,
			String registerAddress, String legal, String registerCapital,
			String serviceType, String establishTimeStr, String startTimeStr,
			String endTimeStr, String organization, String inspectionTimeStr,
			String business, String applicant, String picAddress,
			HttpServletRequest request, Map<String, Object> out)
			throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		Map<String, Object> put = new HashMap<String, Object>();
		ExtResult rs = new ExtResult();
		// 公司名称
		put.put("companyName", companyName);
		// 注册号
		put.put("registerNum", registerNum);
		// 注册地址
		put.put("registerAddress", registerAddress);
		// 法人代表
		put.put("legal", legal);
		// 注册资本
		put.put("registerCapital", registerCapital);
		// 公司类型
		put.put("serviceType", serviceType);
		// 成立时间
		put.put("establishTimeStr", establishTimeStr);
		// 营业期限起始时间
		put.put("startTimeStr", startTimeStr);
		// 营业期结束时间
		put.put("endTimeStr", endTimeStr);
		// 登记机关
		put.put("organization", organization);
		// 年检时间
		put.put("inspectionTimeStr", inspectionTimeStr);
		// 经营范围
		put.put("business", business);
		// 申请人
		put.put("applicant", applicant);
		// 图片处理
		if (picAddress != null) {
			List<String> list = pictureService.selecPicById(picAddress);
			String address = "";
			if (list != null) {
				for (String addr : list) {
					address = address + addr + ",";
				}
				if (address != null || address != "") {
					address = address.substring(0, address.length() - 1);
				}
			}
			// 图片地址
			put.put("picAddress", address);
			put.put("picAddressId", picAddress);
		}
		JSONObject json = JSONObject.fromObject(put);
		String regit = json.toString();
		CompanyInfo info = new CompanyInfo();
		info.setId(ssoUser.getCompanyId());
		info.setCreditStatus(1);
		info.setCreditType(1);
		// 查询认证信息是否存在
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(ssoUser
				.getCompanyId());
		Map<String, Object> tu = getMap(companyInfo.getCreditInfo());
		if (tu == null) {
			tu = new HashMap<String, Object>();
		}
		tu.put("bus", regit);
		info.setCreditInfo(JSONObject.fromObject(tu).toString());
		companyInfoService.updateValidate(info);
		rs.setSuccess(true);
		return printJson(rs, out);
	}

	@RequestMapping
	public ModelAndView certificationBus(Map<String, Object> out,
			HttpServletRequest request, String flag) {
		SsoUser ssoUser = getCachedUser(request);
		// 查询认证信息是否存在
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(ssoUser
				.getCompanyId());
		out.put("ss", 1);
		// 查询认证信息
		Map<String, Object> map = getMap(companyInfo.getCreditInfo());
		if (map != null) {
			Map<String, Object> map2 = getMap((String) map.get("bus"));
			if (map2 != null) {
				if (companyInfo.getCreditStatus() == 1) {
					return new ModelAndView("/certification/sh");
				}
				if (companyInfo.getCreditStatus() == 3 && flag == null) {
					out.put("url", "certificationBus");
					return new ModelAndView("/certification/rzfail");
				}
				// 获取图片地址
				List<String> picAddress = new ArrayList<String>();
				if (StringUtils.isNotEmpty((String) map2.get("picAddress"))) {
					if (map2.get("picAddress").toString().contains(",")) {
						String[] arr = map2.get("picAddress").toString()
								.split(",");
						for (int i = 0; i < arr.length; i++) {
							picAddress.add(arr[i]);
						}
					} else {
						picAddress.add(map2.get("picAddress").toString());
					}
				}
				// 获取图片id
				List<String> picAddressId = new ArrayList<String>();
				if (StringUtils.isNotEmpty((String) map2.get("picAddressId"))) {
					String[] arr = map2.get("picAddressId").toString()
							.split(",");
					for (int i = 0; i < arr.length; i++) {
						picAddressId.add(arr[i]);
					}
				}

				if (picAddressId.size() < 1) {
					picAddressId = pictureService.selectByAddr(picAddress);
				}

				Map<Object, Object> map4 = new HashMap<Object, Object>();
				if (picAddress.size() > 0 && picAddressId.size() > 0) {
					for (int i = 0; i < picAddress.size(); i++) {
						map4.put(picAddress.get(i), picAddressId.get(i));
					}
				}
				// 图片信息
				out.put("bus", map2);
				out.put("picAddress", map4);
			}
		}
		SeoUtil.getInstance().buildSeo("certificationBus", out);
		return null;
	}

	@RequestMapping
	public ModelAndView certificationOne(Map<String, Object> out,
			HttpServletRequest request, String flag) {
		SsoUser ssoUser = getCachedUser(request);
		// 查询认证信息是否存在
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(ssoUser
				.getCompanyId());
		out.put("ss", 2);
		// 查询认证信息
		Map<String, Object> map = getMap(companyInfo.getCreditInfo());
		if (map != null) {
			Map<String, Object> map2 = getMap((String) map.get("one"));
			if (map2 != null) {
				if (companyInfo.getCreditStatus() == 1) {
					return new ModelAndView("/certification/sh");
				}
				if (companyInfo.getCreditStatus() == 3 && flag == null) {
					out.put("url", "certificationOne");
					return new ModelAndView("/certification/rzfail");
				}
				List<String> picAddress = new ArrayList<String>();
				if (StringUtils.isNotEmpty((String) map2.get("picAddress"))) {
					if (map2.get("picAddress").toString().contains(",")) {
						String[] arr = map2.get("picAddress").toString()
								.split(",");
						for (int i = 0; i < arr.length; i++) {
							picAddress.add(arr[i]);
						}
					} else {
						picAddress.add(map2.get("picAddress").toString());
					}
				}

				// 获取图片id
				List<String> picAddressId = new ArrayList<String>();
				if (StringUtils.isNotEmpty((String) map2.get("picAddressId"))) {
					String[] arr = map2.get("picAddressId").toString()
							.split(",");
					for (int i = 0; i < arr.length; i++) {
						picAddressId.add(arr[i]);
					}
				}

				if (picAddressId.size() < 1) {
					picAddressId = pictureService.selectByAddr(picAddress);
				}

				Map<Object, Object> map4 = new HashMap<Object, Object>();
				if (picAddress.size() > 0 && picAddressId.size() > 0) {
					for (int i = 0; i < picAddress.size(); i++) {
						map4.put(picAddress.get(i), picAddressId.get(i));
					}
				}
				// 图片信息
				out.put("one", map2);
				out.put("picAddress", map4);
			}
		}
		SeoUtil.getInstance().buildSeo("certificationOne", out);
		return null;
	}

	/**
	 * 添加个人认证信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addOne(String companyName, String companyAddress,
			String idCard, String name, String code, String phone,
			String codePhone, String mobile, String operation,
			String serviceType, String business, String applicant,
			String maxsex, String picAddress, HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		SsoUser ssoUser = getCachedUser(request);
		Map<String, Object> put = new HashMap<String, Object>();
		ExtResult rs = new ExtResult();
		// 公司id
		put.put("companyName", companyName);
		// 公司地址
		put.put("companyAddress", companyAddress);
		// 身份证
		put.put("idCard", idCard);
		// 联系人
		put.put("name", name);
		// 性别
		put.put("maxsex", maxsex);
		// 电话处理
		put.put("code", code);
		put.put("phone", phone);
		put.put("codePhone", codePhone);
		// 手机号码
		put.put("mobile", mobile);
		// 主营业务
		put.put("operation", operation);
		// 服务类型
		put.put("serviceType", serviceType);
		// 经营范围
		put.put("business", business);
		// 申请人
		put.put("applicant", applicant);
		if (picAddress != null) {
			List<String> list = pictureService.selecPicById(picAddress);
			String address = "";
			if (list != null) {
				for (String addr : list) {
					address = address + addr + ",";
				}
				if (address != null || address != "") {
					address = address.substring(0, address.length() - 1);
				}
			}
			// 图片地址
			put.put("picAddress", address);
			put.put("picAddressId", picAddress);
		}
		JSONObject json = JSONObject.fromObject(put);
		String regit = json.toString();
		CompanyInfo info = new CompanyInfo();
		info.setId(ssoUser.getCompanyId());
		info.setCreditStatus(1);
		info.setCreditType(0);
		// 查询认证信息是否存在
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(ssoUser
				.getCompanyId());
		Map<String, Object> tu = getMap(companyInfo.getCreditInfo());
		if (tu == null) {
			tu = new HashMap<String, Object>();
		}
		tu.put("one", regit);
		info.setCreditInfo(JSONObject.fromObject(tu).toString());
		companyInfoService.updateValidate(info);
		rs.setSuccess(true);
		return printJson(rs, out);
	}

	/**
	 * 公司信息管理
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView companyInfo(HttpServletRequest request,
			Map<String, Object> out) {
		SsoUser user = getCachedUser(request);
		CompanyInfo companyInfo = companyInfoService.queryInfoByid(user
				.getCompanyId());
		CompanyAccount companyAccount = companyAccountService
				.queryAccountByCompanyId(user.getCompanyId());
		if (companyAccount != null
				&& StringUtils.isNotEmpty(companyAccount.getTel())) {
			if (companyAccount.getTel().contains("-")) {
				String[] arr = companyAccount.getTel().split("-");
				if (arr.length == 2) {
					out.put("tel0", arr[0]);
					out.put("tel1", arr[1]);
				} else if (arr.length == 1) {
					out.put("tel0", arr[0]);
				}
			}
		}
		if (companyAccount != null
				&& StringUtils.isNotEmpty(companyAccount.getFax())) {
			String[] arr = companyAccount.getFax().split("-");
			if (arr.length == 2) {
				out.put("fax0", arr[0]);
				out.put("fax1", arr[1]);
			} else if (arr.length == 1) {
				out.put("fax0", arr[0]);
			}
		}
		out.put("companyInfo", companyInfo);
		String address = "";
		if (companyInfo != null
				&& StringUtils.isNotEmpty(companyInfo.getArea())) {
			if (companyInfo.getArea().length() > 12) {
				address = CategoryFacade.getInstance().getValue(
						companyInfo.getArea().substring(0, 12))
						+ " "
						+ CategoryFacade.getInstance().getValue(
								companyInfo.getArea().substring(0, 16))
						+ " "
						+ CategoryFacade.getInstance().getValue(
								companyInfo.getArea().substring(0, 20));
			} else if (companyInfo.getArea().length() > 8) {
				address = CategoryFacade.getInstance().getValue(
						companyInfo.getArea().substring(0, 8))
						+ " "
						+ CategoryFacade.getInstance().getValue(
								companyInfo.getArea().substring(0, 12));
			} else if (companyInfo.getArea().length() == 8) {
				address = CategoryFacade.getInstance().getValue(
						companyInfo.getArea().substring(0, 8));
			}
		}
		out.put("addr", address);
		out.put("companyAccount", companyAccount);
		return null;
	}

	/**
	 * 公司管理信息修改
	 * 
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateCompanyInfo(HttpServletRequest request,
			Map<String, Object> out, CompanyInfo companyInfo,
			CompanyAccount companyAccount, String phoneTel, String otherPhone,
			String faxTel, String otherFax) throws IOException {
		ExtResult rs = new ExtResult();
		do {
			if (companyAccount.getCompanyId() == null) {
				rs.setSuccess(false);
				break;
			}
			// 修改公司信息
			String tel = "";
			String fax = "";
			if (StringUtils.isNotEmpty(phoneTel)
					|| StringUtils.isNotEmpty(otherPhone)) {
				tel = phoneTel + "-" + otherPhone;
			}
			if (StringUtils.isNotEmpty(faxTel)
					|| StringUtils.isNotEmpty(otherFax)) {
				fax = faxTel + "-" + otherFax;
			}
			if (StringUtils.isNotEmpty(tel)) {
				companyAccount.setTel(tel);
			}
			if (StringUtils.isNotEmpty(fax)) {
				companyAccount.setFax(fax);
			}
			companyInfo.setId(companyAccount.getCompanyId());
			Integer i = companyInfoService.updateCompanyByAdmin(companyInfo);
			Integer j = companyAccountService
					.updateByCompanyAccount(companyAccount);
			if (i > 0 && j > 0) {
				rs.setSuccess(true);
			}
		} while (false);
		return printJson(rs, out);
	}

	/**
	 * 审核页面
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sh(Map<String, Object> out, Boolean addflag, Integer i) {

		SeoUtil.getInstance().buildSeo("sh", out);
		return null;
	}

	/**
	 * 审核失败页面
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView rzfail(Map<String, Object> out, Boolean addflag,
			Integer i) {

		SeoUtil.getInstance().buildSeo("rzfail", out);
		return null;
	}

	// 将json字符串转换为map类型
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

}
