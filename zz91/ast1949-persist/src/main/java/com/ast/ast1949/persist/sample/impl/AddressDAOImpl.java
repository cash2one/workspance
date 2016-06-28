package com.ast.ast1949.persist.sample.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Address;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.AddressDAO;

@Component("addressDao")
public class AddressDAOImpl extends BaseDaoSupport implements AddressDAO {

	public Integer insert(Address record) {
		return (Integer)getSqlMapClientTemplate().insert("sample_address.insert", record);
	}

	public int updateByPrimaryKey(Address record) {
		int rows = getSqlMapClientTemplate().update("sample_address.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(Address record) {
		int rows = getSqlMapClientTemplate().update("sample_address.updateByPrimaryKeySelective", record);
		return rows;
	}

	public Address selectByPrimaryKey(Integer id) {
		Address key = new Address();
		key.setId(id);
		Address record = (Address) getSqlMapClientTemplate().queryForObject("sample_address.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		Address key = new Address();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("sample_address.deleteByPrimaryKey", key);
		return rows;
	}

	@Override
	public Address selectDefault(Integer companyId, String flag) {
		Address key = new Address();
		key.setCompanyId(companyId);
		key.setFlag(flag);
		key.setIsdefault("1");
		Address  address = (Address) getSqlMapClientTemplate().queryForObject("sample_address.selectDefault", key);

		if (address == null) {
			key.setIsdefault(null);
			address = (Address) getSqlMapClientTemplate().queryForObject("sample_address.selectDefault", key);
		}

		return address;
	}

	@Override
	public int updateDefault(Integer companyId, String flag, Integer id) {
		Address record = new Address();
		record.setCompanyId(companyId);
		record.setFlag(flag);
		record.setId(id);
		int rows = getSqlMapClientTemplate().update("sample_address.updateDefault", record);
		return rows;
	}

	@Override
	public List<Address> findListByCompanyIdAndFlag(Integer companyId, String flag) {
		Address record = new Address();
		record.setCompanyId(companyId);
		record.setFlag(flag);
		return  (List<Address>) getSqlMapClientTemplate().queryForList("sample_address.findListByCompanyIdAndFlag", record);
	}
	
	@Override
	public Address queryAimAddressByCompanyId(Integer companyId){
		return (Address) getSqlMapClientTemplate().queryForObject("sample_address.queryAimAddressByCompanyId", companyId);
	}
}