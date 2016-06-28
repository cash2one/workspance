package com.ast.feiliao91.service.company.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.AddressDto;
import com.ast.feiliao91.dto.company.CompanyDto;
import com.ast.feiliao91.persist.company.AddressDao;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.MyException;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.lang.StringUtils;

@Component("addressService")
public class AddressServiceImpl implements AddressService {
	@Resource
	private AddressDao addressDao;

	@Override
	public String getArea(String areaCode) {
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

	@Override
	public Integer insert(Address address, Integer type) throws MyException {
		if (type == null) {
			return 0;
		}
		if (0 == type) {
			address.setAddressType(0);
			List<Address> ad = addressDao.selectById(address.getCompanyId());
			if (ad.size() >= 20)
				throw new MyException("提示:最多只能保存20条发货地址");
			if (address.getIsDefault() != null) {
				if (address.getIsDefault() == 1) {
					List<Address> add = addressDao.selectById(address
							.getCompanyId());
					if (add != null) {
						for (Address as : add) {
							addressDao.setIsDefault(as.getId());
						}
					}
				}
			}
			// return addressDao.insert(address);
		} else if (1 == type) {
			address.setAddressType(1);
			List<Address> ad = addressDao.selectByDelId(address.getCompanyId());
			if (ad.size() >= 20)
				throw new MyException("提示:最多只能保存20条发货地址");
		}
		try {
			if (address.getTel()!=null&&StringUtils.isContainCNChar(address.getTel())) {
				address.setTel("");
			}
			
		} catch (UnsupportedEncodingException e) {
			address.setTel("");
		}
		return addressDao.insert(address);
	}

	@Override
	public Integer updateAll(Address address) {
		try {
			if (StringUtils.isNotEmpty(address.getTel())
					&& StringUtils.isContainCNChar(address.getTel())) {
				address.setTel("");
			}
		} catch (UnsupportedEncodingException e) {
			address.setTel("");
		}
		if (address.getIsDefault() != null && address.getIsDefault() == 1) {
			List<Address> add = addressDao.selectById(address.getCompanyId());
			if (add != null) {
				for (Address as : add) {
					addressDao.setIsDefault(as.getId());
				}
			}
		}
		return addressDao.updateAll(address);
	}

	@Override
	public Integer deleteById(Integer id) {
		Address address = new Address();
		address.setId(id);
		address.setIsDel(1);
		return addressDao.updateAll(address);
	}

	@Override
	public List<Address> selectById(Integer companyId) {
		return addressDao.selectById(companyId);
	}

	@Override
	public Address selectDefaultAddress(Integer companyId) {
		return addressDao.selectDefaultAddress(companyId);
	}

	@Override
	public Integer updateDefault(Address address) {
		if (address.getId() != null && address.getCompanyId() != null) {
			List<Address> address1 = addressDao.selectById(address
					.getCompanyId());
			for (Address adr : address1) {
				addressDao.updateAll(new Address(adr.getId(), 0));

			}
			return addressDao.updateAll(new Address(address.getId(), 1));
		} else {
			return null;
		}
	}

	@Override
	public List<Address> selectByDelId(Integer companyId) {
		return addressDao.selectByDelId(companyId);
	}

	@Override
	public Address selectDefaultDelAddress(Integer companyId) {
		return addressDao.selectDefaultDelAddress(companyId);
	}

	@Override
	public Integer updateDefaultDel(Address address) {
		if (address.getId() != null && address.getCompanyId() != null) {
			List<Address> address1 = addressDao.selectByDelId(address
					.getCompanyId());
			for (Address adr : address1) {
				addressDao.updateAll(new Address(adr.getId(), 0));
			}
			return addressDao.updateAll(new Address(address.getId(), 1));
		} else {
			return null;
		}
	}

	@Override
	public Integer selectShopCount(Integer companyId) {
		return addressDao.selectShopCount(companyId);
	}

	@Override
	public Integer selectHairCount(Integer companyId) {
		return addressDao.selectHairCount(companyId);
	}

	@Override
	public Address selectAddress(Integer id) {
		return addressDao.selectAddress(id);
	}

	@Override
	public List<AddressDto> queryAddressByCondition(Integer companyId,
			Integer addressType) {
		List<AddressDto> resultlist = new ArrayList<AddressDto>();
		List<Address> list = addressDao.queryAddressByCondition(companyId,
				addressType);
		for (Address addr : list) {
			AddressDto dto = new AddressDto();
			dto.setAddress(addr);
			// 地区码转省、市
			if (StringUtils.isNotEmpty(addr.getAreaCode())) {
				dto.setAreaLabel(getCity(addr.getAreaCode()));
			}
			// 加入到结果集
			resultlist.add(dto);
		}
		return resultlist;
	}

	/**
	 * 获取解码后的地区
	 * 
	 * @param code
	 * @return
	 */
	public String getCity(String code) {
		String area = "";
		if (code.length() > 11) {
			area = CategoryFacade.getInstance().getValue(code.substring(0, 12));
		} else {
			area = CategoryFacade.getInstance().getValue(code.substring(0, 8));
		}
		if (!"香港".equals(area) && !"澳门".equals(area) && !"台湾".equals(area)
				&& !"北京".equals(area) && !"天津".equals(area)
				&& !"上海".equals(area) && !"重庆".equals(area)) {
			if (code.length() > 15) {
				area = area
						+ " "
						+ CategoryFacade.getInstance().getValue(
								code.substring(0, 16));
			}
		}
		return area;
	}
	
	/**
	 * 获得解码后的区县
	 */
	private String getDistrict(String code) {
		String area = "";
		if (code.length() > 11) {
			area = CategoryFacade.getInstance().getValue(code.substring(0, 12));
		} else {
			area = CategoryFacade.getInstance().getValue(code.substring(0, 8));
		}
		if (!"香港".equals(area) && !"澳门".equals(area) && !"台湾".equals(area)
				&& !"北京".equals(area) && !"天津".equals(area)
				&& !"上海".equals(area) && !"重庆".equals(area)) {
			if (code.length() == 16) {
				area = area
						+ ""
						+ CategoryFacade.getInstance().getValue(
								code.substring(0, 16));
			}
			if(code.length() == 20){
				area = area
						+ CategoryFacade.getInstance().getValue(
								code.substring(0, 16))
						+CategoryFacade.getInstance().getValue(
								code.substring(0, 20));
			}
		}
		return area;
	}
	
	@Override
	public PageDto<AddressDto> getByPage(PageDto<AddressDto> page, Integer companyId, Integer addressType){
		List<Address> list = addressDao.getByPage(page,companyId,addressType);
		List<AddressDto> resultList = new ArrayList<AddressDto>();
		for (Address Address : list) {
			AddressDto dto = new AddressDto();
			dto.setAddress(Address);
			if (StringUtils.isNotEmpty(Address.getAreaCode())) {
				if (StringUtils.isNotEmpty(Address.getAddress())) {
					dto.setAreaLabel(getDistrict(Address.getAreaCode())+Address.getAddress());
				}else{
					dto.setAreaLabel(getDistrict(Address.getAreaCode()));
				}
			}
			resultList.add(dto);
		}
		page.setRecords(resultList);
		return page;
	}
}
