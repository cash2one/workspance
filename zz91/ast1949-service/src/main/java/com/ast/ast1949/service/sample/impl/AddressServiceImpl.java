package com.ast.ast1949.service.sample.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Address;
import com.ast.ast1949.persist.sample.AddressDAO;
import com.ast.ast1949.service.sample.AddressService;

@Component("addressService")
public class AddressServiceImpl implements AddressService {

	@Resource
	private AddressDAO addressDao;

	@Override
	public Integer insert(Address address) {
		if("1".equals(address.getIsdefault())){
			addressDao.updateDefault(address.getCompanyId(), address.getFlag(), address.getId());
		}
		return addressDao.insert(address);
	}

	@Override
	public int updateByPrimaryKey(Address record) {
		if("1".equals(record.getIsdefault())){
			addressDao.updateDefault(record.getCompanyId(), record.getFlag(), record.getId());
		}
		return addressDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Address record) {
		if("1".equals(record.getIsdefault())){
			addressDao.updateDefault(record.getCompanyId(), record.getFlag(), record.getId());
		}
		return addressDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Address selectByPrimaryKey(Integer id) {
		return addressDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return addressDao.deleteByPrimaryKey(id);
	}

	
	/**
	 * 默认地址读取--通过companyId、flag、isdefault=Y 来获取一条地址，若没有则通过companyId、flag获取最新一条
	 * @param companyId 公司ID
	 * @param flag 收货地址-"B"、发货地址-"S"
	 * @return 
	 */
	@Override
	public Address selectDefault(Integer companyId, String flag) {
		return addressDao.selectDefault(companyId, flag);
	}

	@Override
	public List<Address> findListByCompanyIdAndFlag(Integer companyId, String flag) {
		return addressDao.findListByCompanyIdAndFlag(companyId, flag);
	}
	
	@Override
	public Address queryAimAddressByCompanyId(Integer companyId){
		return addressDao.queryAimAddressByCompanyId(companyId);
	}
}
