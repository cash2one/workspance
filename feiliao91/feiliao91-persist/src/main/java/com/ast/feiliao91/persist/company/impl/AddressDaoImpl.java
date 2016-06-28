package com.ast.feiliao91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.AddressDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.AddressDao;

@Component("addressDao")
public class AddressDaoImpl extends BaseDaoSupport implements AddressDao {
	final static String SQL_PREFIX = "address";

	@Override
	public Integer insert(Address address) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"), address);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> selectById(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "selectById"), companyId);
	}

	@Override
	public Integer updateAll(Address address) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateAll"), address);
	}

	@Override
	public Address selectDefaultAddress(Integer companyId) {
		return (Address) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectDefaultAddress"),companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> selectByDelId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "selectByDelId"),companyId);
	}

	@Override
	public Address selectDefaultDelAddress(Integer companyId) {
		return (Address) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectDefaultDelAddress"),companyId);
	}

	@Override
	public Integer setIsDefault(Integer id) {
		 return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "setIsDefault"),id);
	}

	@Override
	public Integer selectShopCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectShopCount"),companyId);
	}

	@Override
	public Integer selectHairCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectHairCount"),companyId);
	}

	@Override
	public Address selectAddress(Integer id) {
		return (Address) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectAddress"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> queryAddressByCondition(Integer companyId, Integer addressType) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("addressType", addressType);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAddressByCondition"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Address> getByPage(PageDto<AddressDto> page, Integer companyId, Integer addressType){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		map.put("addressType", addressType);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "getByPage"), map);
	}
}
