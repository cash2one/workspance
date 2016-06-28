/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.SysArea;

/**
 * @author totly created on 2011-12-10
 */
public interface SysAreaDao {

    /**
     * 查询地区/省份/城市信息
     * @param code
     * @param type 0(默认):自身(code=code)
     *             1:一级子类(code like 'code____')
     *             2:所有子类(code like 'code%')
     * @return
     */
    public List<SysArea> querySysAreaByCode(String code, Short type);

	public Integer insertSysArea(SysArea sysArea);

	/**
	 * 查询地区名称
	 * @param code
	 * @return
	 */
	public String queryNameByCode(String code);

	/**
	 * @param name
	 * @return
	 */
	public String queryCodeByName(String name);
}
