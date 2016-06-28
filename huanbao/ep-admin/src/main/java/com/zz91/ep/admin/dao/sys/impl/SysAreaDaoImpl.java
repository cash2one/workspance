package com.zz91.ep.admin.dao.sys.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.sys.SysAreaDao;
import com.zz91.ep.domain.sys.SysArea;

@Repository("sysAreaDao")
public class SysAreaDaoImpl extends BaseDao implements SysAreaDao {

	final static String SQL_PREFIX="sysArea";
	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> queryAreaAll() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAreaAll"));
	}
	@Override
	public String queryProvinceCodeByProvinceName(String provinceName) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryProvinceCodeByProvinceName"), provinceName);
	}
	@Override
	public String querySupplyAreaCodeBySupplyAreaName(String supplyAreaName) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyAreaCodeBySupplyAreaName"), supplyAreaName);
	}
	@Override
	public String queryAreaCodeByAreaName(String areaName) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAreaCodeByAreaName"), areaName);
	}
	@Override
	public String queryMaxCodeByPreCode(String code) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxCodeByPreCode"), code);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> queryChildArea(String parentCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryChildArea"), parentCode);
	}

}
