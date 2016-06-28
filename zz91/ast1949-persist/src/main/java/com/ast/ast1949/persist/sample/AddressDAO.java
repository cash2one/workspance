package com.ast.ast1949.persist.sample;

import java.util.List;

import com.ast.ast1949.domain.sample.Address;

public interface AddressDAO {
	Integer insert(Address record);

	int updateByPrimaryKey(Address record);

	int updateByPrimaryKeySelective(Address record);

	Address selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 默认地址读取--通过companyId、flag、isdefault=Y 来获取一条地址，若没有则通过companyId、flag获取最新一条
	 * @param companyId 公司ID
	 * @param flag 收货地址-"B"、发货地址-"S"
	 * @return 
	 */
	Address selectDefault(Integer companyId,String flag);
	
	
	/**
	 * 此ID之外的地址都置为非默认
	 * @param companyId
	 * @param flag
	 * @param id
	 * @return
	 */
	int updateDefault(Integer companyId,String flag,Integer id);

	/**
	 * 
	 * @param companyId
	 * @param flag
	 * @return
	 */
	List<Address> findListByCompanyIdAndFlag(Integer companyId, String flag);
	
	/** 
	 * @param companyId
	 */
	public Address queryAimAddressByCompanyId(Integer companyId);
	
}