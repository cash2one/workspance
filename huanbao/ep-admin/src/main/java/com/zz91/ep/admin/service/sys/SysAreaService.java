package com.zz91.ep.admin.service.sys;

import java.util.List;

import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.dto.ExtTreeDto;

public interface SysAreaService {

	/**
	 * 查询所有地区信息
	 * @return
	 */
	public List<SysArea> queryAreaAll();

	public List<ExtTreeDto> queryAreaNode(String parentCode);
	
	public List<SysArea> queryAreaChild(String parentCode);
	
	public String queryProvinceCodeByProvinceName(String provinceName);
	
}
