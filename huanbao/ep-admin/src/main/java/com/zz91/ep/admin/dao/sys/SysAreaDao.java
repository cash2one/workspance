package com.zz91.ep.admin.dao.sys;

import java.util.List;

import com.zz91.ep.domain.sys.SysArea;

public interface SysAreaDao {

	/**
	 * 查询所有地区类别
	 * @return
	 */
	public List<SysArea> queryAreaAll();

	/**
	 * 通过地区名称查询地区code
	 * @param areaName
	 * @return
	 */
	public String queryAreaCodeByAreaName(String areaName);

	/**
	 * 通过省份名称查询省份code
	 * @param provinceName
	 * @return
	 */
	public String queryProvinceCodeByProvinceName(String provinceName);

	/**
	 * 通过供应商地址名称查询code
	 * @param supplyAreaName
	 * @return
	 */
	public String querySupplyAreaCodeBySupplyAreaName(String supplyAreaName);

	public List<SysArea> queryChildArea(String parentCode);

	public String queryMaxCodeByPreCode(String code);


}
