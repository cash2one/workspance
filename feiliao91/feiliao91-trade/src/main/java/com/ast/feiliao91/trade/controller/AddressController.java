package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.MyException;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class AddressController extends BaseController {
	@Resource
	private AddressService addressService;

	/**
	 * 添加收发货地址
	 * 
	 * 修改地址
	 * 
	 * @param address
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addAddress(Address address, HttpServletRequest request,
			Map<String, Object> out, String code, String phone,
			String phoneCode, String addressType) throws IOException {
		ExtResult rs = new ExtResult();
		String code1 = "";
		String phoneCode1 = "";
		if (StringUtils.isNotEmpty(code)) {
			code1 = code;
		}
		if (StringUtils.isNotEmpty(phoneCode)) {
			phoneCode1 = phoneCode;
		}
		SsoUser ssoUser = getCachedUser(request);
		address.setCompanyId(ssoUser.getCompanyId());
		if ((address.getId() != null)) {
			// 修改地址
			if (StringUtils.isNotEmpty(phone) || StringUtils.isNotEmpty(code1)
					|| StringUtils.isNotEmpty(phoneCode1)) {
				String tel = code1 + "-" + phone + "-" + phoneCode1;
				address.setTel(tel);
			}
			addressService.updateAll(address);
			rs.setSuccess(true);
		} else {
			try {
				// 插入地址
				if (StringUtils.isNotEmpty(phone)) {
					String tel = code1 + "-" + phone + "-" + phoneCode1;
					address.setTel(tel);
				}
				addressService.insert(address, address.getAddressType());
				rs.setSuccess(true);
			} catch (MyException e) {
				rs.setData(e.getMessage());
				return printJson(rs, out);
			}
		}
		if (address.getAddressType() == 0) {
			rs.setData("get");
		} else {
			rs.setData("out");
		}
		return printJson(rs, out);
	}

	/**
	 * 查询所有收货地址
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView addressGet(HttpServletRequest request,
			Map<String, Object> out) {
		Integer size = null;
		SsoUser ssoUser = getCachedUser(request);
		List<Address> addr = new ArrayList<Address>();
		List<Address> address = addressService.selectById(ssoUser
				.getCompanyId());
		if (address.size() > 0) {
			for (Address ad : address) {
				ad.setAreaCode(getArea(ad.getAreaCode()));
				if (StringUtils.isNotEmpty(ad.getMobile())) {
					ad.setMobile(addHide(ad.getMobile()));
				}
				if (StringUtils.isNotEmpty(ad.getTel())) {
					String[] arr = ad.getTel().split("-");
					if (arr.length == 3) {
						ad.setTel(addHide(arr[0] + arr[1] + arr[2]));
					} else if (arr.length == 2) {
						ad.setTel(addHide(arr[0] + arr[1]));
					} else if (arr.length == 1) {
						ad.setTel(arr[0]);
					}
				}
				addr.add(ad);
			}
			for (int i = 0; i < address.size(); i++) {
				if (address.get(i).getIsDefault() != null
						&& address.get(i).getIsDefault() == 1) {
					size = i;
					break;
				}
			}
		}
		Integer count = address.size();
		out.put("count", count);
		out.put("countsize", 20 - count);
		out.put("size", size);
		out.put("addr", addr);
		out.put("ssoUser", ssoUser);
		SeoUtil.getInstance().buildSeo("addressGet", out);
		return null;
	}

	/**
	 * 查询所有发货地址
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping
	public ModelAndView addressOut(HttpServletRequest request,
			Map<String, Object> out) {
		SeoUtil.getInstance().buildSeo("addressOut", out);
		Integer size = null;
		SsoUser ssoUser = getCachedUser(request);
		List<Address> addr = new ArrayList<Address>();
		List<Address> address = addressService.selectByDelId(ssoUser
				.getCompanyId());
		if (address.size() > 0) {
			for (Address ad : address) {
				ad.setAreaCode(getArea(ad.getAreaCode()));
				if (StringUtils.isNotEmpty(ad.getMobile())) {
					ad.setMobile(addHide(ad.getMobile()));
				}
				if (StringUtils.isNotEmpty(ad.getTel())) {
					String[] arr = ad.getTel().split("-");
					if (arr.length == 3) {
						ad.setTel(addHide(arr[0] + arr[1] + arr[2]));
					} else if (arr.length == 2) {
						ad.setTel(addHide(arr[0] + arr[1]));
					} else if (arr.length == 1) {
						ad.setTel(arr[0]);
					}
				}
			}
			for (int i = 0; i < address.size(); i++) {
				if (address.get(i).getIsDefault() != null
						&& address.get(i).getIsDefault() == 1) {
					size = i;
					break;
				}
			}
		}
		Integer count = address.size();
		out.put("count", count);
		out.put("countsize", 20 - count);
		out.put("size", size);
		out.put("addr", address);
		out.put("ssoUser", ssoUser);
		SeoUtil.getInstance().buildSeo("addressOut", out);
		return null;
	}

	/**
	 * 根据ID删除相应地址
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteAddress(Integer id, Map<String, Object> out)
			throws IOException {
		ExtResult rs = new ExtResult();
		if (id != null) {
			// 删除地址
			addressService.deleteById(id);
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 设置默认收货地址
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateDefaultShopAddress(Address address,
			Map<String, Object> out) throws IOException {
		ExtResult rs = new ExtResult();
		if (address.getId() != null && address.getCompanyId() != null) {
			addressService.updateDefault(address);
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 设置默发收货地址
	 * 
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateDefaultDelAddress(Address address,
			Map<String, Object> out) throws IOException {
		ExtResult rs = new ExtResult();
		if (address.getId() != null && address.getCompanyId() != null) {
			addressService.updateDefaultDel(address);
			rs.setSuccess(true);
		}
		return printJson(rs, out);
	}

	/**
	 * 修改地址
	 */
	@RequestMapping
	public ModelAndView updateAddress(HttpServletRequest request, Integer id,
			Map<String, Object> out, String code) {
		// 根据id查询地址信息
		Integer size = null;
		Address addre = addressService.selectAddress(id);
		if (addre != null) {
			if (StringUtils.isNotEmpty(addre.getTel())) {
				String[] ar = addre.getTel().split("-");
				if (ar.length == 3) {
					out.put("tel0", ar[0]);
					out.put("tel1", ar[1]);
					out.put("tel2", ar[2]);
				} else if (ar.length == 2) {
					out.put("tel0", ar[0]);
					out.put("tel1", ar[1]);
				} else if (ar.length == 1) {
					out.put("tel0", ar[0]);
				}
			}
			out.put("addre", addre);
		}
		SsoUser ssoUser = getCachedUser(request);
		out.put("ssoUser", ssoUser);
		if (StringUtils.isNotEmpty(code)) {
			if (code.equals("1")) {
				// 查询所有收货地址
				List<Address> addr = new ArrayList<Address>();
				List<Address> address = addressService.selectById(ssoUser
						.getCompanyId());
				for (Address ad : address) {
					ad.setAreaCode(getArea(ad.getAreaCode()));
					if (StringUtils.isNotEmpty(ad.getMobile())) {
						ad.setMobile(addHide(ad.getMobile()));
					}
					if (StringUtils.isNotEmpty(ad.getTel())) {
						String[] arr = ad.getTel().split("-");
						if (arr.length == 3) {
							ad.setTel(addHide(arr[0] + arr[1] + arr[2]));
						} else if (arr.length == 2) {
							ad.setTel(addHide(arr[0] + arr[1]));
						} else if (arr.length == 1) {
							ad.setTel(arr[0]);
						}
					}
					for (int i = 0; i < address.size(); i++) {
						if (address.get(i).getIsDefault() != null
								&& address.get(i).getIsDefault() == 1) {
							size = i;
							break;
						}
					}
					SeoUtil.getInstance().buildSeo("addressGet", out);
					Integer count = address.size();
					out.put("count", count);
					out.put("countsize", 20 - count);
					out.put("size", size);
					addr.add(ad);
				}
				out.put("addr", addr);
				return new ModelAndView("/address/addressGet");
			} else if (code.equals("2")) {
				// 查询所有发货地址
				List<Address> addr = new ArrayList<Address>();
				List<Address> address = addressService.selectByDelId(ssoUser
						.getCompanyId());
				for (Address ad : address) {
					ad.setAreaCode(getArea(ad.getAreaCode()));
					if (StringUtils.isNotEmpty(ad.getMobile())) {
						ad.setMobile(addHide(ad.getMobile()));
					}
					if (StringUtils.isNotEmpty(ad.getTel())) {
						String[] arr = ad.getTel().split("-");
						if (arr.length == 3) {
							ad.setTel(addHide(arr[0] + arr[1] + arr[2]));
						} else if (arr.length == 2) {
							ad.setTel(addHide(arr[0] + arr[1]));
						} else if (arr.length == 1) {
							ad.setTel(arr[1]);
						}
					}
					addr.add(ad);
				}
				for (int i = 0; i < address.size(); i++) {
					if (address.get(i).getIsDefault() != null
							&& address.get(i).getIsDefault() == 1) {
						size = i;
						break;
					}
				}
				SeoUtil.getInstance().buildSeo("addressOut", out);
				Integer count = address.size();
				out.put("count", count);
				out.put("countsize", 20 - count);
				out.put("size", size);
				out.put("addr", addr);
				return new ModelAndView("/address/addressOut");
			}
		}
		return new ModelAndView("/address/addressGet");
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

	/**
	 * 加星隐藏保密隐私
	 * 
	 * @param str
	 * @return
	 */
	private String addHide(String str) {
		if (str.length() < 6) {
			return str;
		}
		int start = 0;
		if (str.length() > 7) {
			start = 3;
		} else {
			start = 3 - (8 - str.length());
		}
		int end = 0;
		if (str.length() < 9) {
			end = 1;
		} else {
			end = 1 + (str.length() - 8);
		}
		str = str.substring(0, start) + "****"
				+ str.substring(str.length() - end, str.length());
		return str;
	}

}
