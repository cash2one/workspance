package com.ast.ast1949.service.sample;

import java.util.List;

import com.ast.ast1949.domain.sample.Address;

public interface AddressService {
	Integer insert(Address address);

	int updateByPrimaryKey(Address record);

	int updateByPrimaryKeySelective(Address record);

	Address selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	Address selectDefault(Integer companyId, String flag);

	List<Address> findListByCompanyIdAndFlag(Integer companyId, String flag);

	public Address queryAimAddressByCompanyId(Integer companyId);
}
