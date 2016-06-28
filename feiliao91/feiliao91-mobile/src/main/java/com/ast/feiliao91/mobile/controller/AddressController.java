/**
 * @author zhujq
 * @description 地址控制器
 */
package com.ast.feiliao91.mobile.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.AddressDto;
import com.ast.feiliao91.service.auth.AuthUserService;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.MyException;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.lang.StringUtils;

@Controller
public class AddressController extends BaseController{
	@Resource
	private AddressService addressService;
	@Resource
	private AuthUserService authUserService;
	
	/**
	 * 获得所有地址
	 * @param request
	 * @param out
	 * @param addressType
	 * @return
	 */
	@RequestMapping
	public ModelAndView addressList(HttpServletRequest request,
			Map<String, Object> out,Integer addressType) {
		SsoUser user = getCachedUser(request);
		PageDto<AddressDto> page = new PageDto<AddressDto>();
		page.setPageSize(8);
		page = addressService.getByPage(page,user.getCompanyId(),addressType);
		out.put("page",page);
		out.put("addressType",addressType);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView addressListJson(HttpServletRequest request,
			Map<String, Object> out,Integer addressType) throws IOException{
		SsoUser user = getCachedUser(request);
		PageDto<AddressDto> page = new PageDto<AddressDto>();
		page = addressService.getByPage(page,user.getCompanyId(),addressType);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView getMoreAddress(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out,Integer startIndex,Integer addressType) throws IOException{
		SsoUser user = getCachedUser(request);
		PageDto<AddressDto> page = new PageDto<AddressDto>();
		page.setStartIndex(startIndex); // 获取后面的数据
		page.setPageSize(5);
		page = addressService.getByPage(page,user.getCompanyId(),addressType);
		return printJson(page, out);
	}
	
	/**
	 * 添加或编辑收获地址合并页面
	 * @param request
	 * @param out
	 * @param id（如果有id则为编辑页面）
	 * @return
	 */
	@RequestMapping
	public ModelAndView addAddress(HttpServletRequest request,
			Map<String, Object> out,Integer id,Integer addressType){
		if(id != null){
			Address address = addressService.selectAddress(id);
			out.put("add",address);
			out.put("aid",id);
			if(StringUtils.isNotEmpty(address.getAreaCode())){
				if (address.getAreaCode().length()>8 && address.getAreaCode().length()<13) {
					out.put("addStr",CategoryFacade.getInstance().getValue(address.getAreaCode()));
				}else if (address.getAreaCode().length()>12 && address.getAreaCode().length()<17) {
					//省市
					out.put("addStr",CategoryFacade.getInstance().getValue(address.getAreaCode().substring(0, 12))+">"+CategoryFacade.getInstance().getValue(address.getAreaCode()));
				}else if (address.getAreaCode().length()>16) {
					//省市区
					out.put("addStr",CategoryFacade.getInstance().getValue(address.getAreaCode().substring(0, 12))+">"+CategoryFacade.getInstance().getValue(address.getAreaCode().substring(0, 16))+">"+CategoryFacade.getInstance().getValue(address.getAreaCode()));
				}
			}
		}
		out.put("addressType",addressType);
		return null;
	}
	
	/**
	 * 保存地址
	 * @param request
	 * @param out
	 * @param aid
	 * @param addressType 收发货类型
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping
	public ModelAndView saveAddress(HttpServletRequest request,
			Map<String, Object> out,Integer aid,Address address,Integer isDefaultFlag,Integer addressType) throws MyException{
		boolean success = false;
		SsoUser user = getCachedUser(request);
		do {
			if(address == null){
				break;
			}
			if(isDefaultFlag == null){
				isDefaultFlag = 0;
			}
			if(aid != null){
				//编辑保存
				Address address1 = addressService.selectAddress(aid);
				address1.setName(address.getName());
				address1.setMobile(address.getMobile());
				address1.setAreaCode(address.getAreaCode());
				address1.setAddress(address.getAddress());
				address1.setZipCode(address.getZipCode());
				Integer i = addressService.updateAll(address1);
				if (i>0) {
					success = true;
				}
				if(isDefaultFlag==1){
					//收发或类型
					if(addressType==0){
						//设置默认收货地址
						addressService.updateDefault(address1);
					}
					if(addressType==1){
						//设置默认发货地址
						addressService.updateDefaultDel(address1);
					}
				}
			}else {
				//添加保存
				address.setCompanyId(user.getCompanyId());
				address.setIsDel(0);
				Integer i = addressService.insert(address,addressType);
				if (i>0) {
					success = true;
				}
				if(isDefaultFlag==1){
					//收发或类型
					if(addressType==0){
						//设置默认收货地址
						addressService.updateDefault(address);
					}
					if(addressType==1){
						//设置默认发货地址
						addressService.updateDefaultDel(address);
					}
				}
			}
		} while (false);
		if (success) {
			return new ModelAndView("redirect:/address/saveSuccess.htm");
		}
		return new ModelAndView("redirect:/address/addAddress.htm");
	}
	
	@RequestMapping
	public ModelAndView saveSuccess(HttpServletRequest request,
			Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView test(HttpServletRequest request,
			Map<String, Object> out){
		System.err.println("=========================");
		String a="sdfsdf@dfs.com";
		System.err.println(authUserService.queryAccountByEmail(a));
		System.err.println("=========================");
		return null;
	}
}
