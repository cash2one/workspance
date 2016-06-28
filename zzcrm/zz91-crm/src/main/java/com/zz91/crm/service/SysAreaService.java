/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service;

import java.util.List;

import com.zz91.crm.domain.SysArea;

/**
 * @author totly created on 2011-12-10
 */
public interface SysAreaService {

    public static final Short TYPE_SELF = 0;
    public static final Short TYPE_CHILD_NEWEST = 1;
    public static final Short TYPE_CHILD_ALL = 2;

    /**
     * 查询地区/省份/城市信息
     * @param code
     * @param type 0(默认):自身(code=code)
     *             1:一级子类(code like 'code____')
     *             2:所有子类(code like 'code%')
     * @return
     */
    public List<SysArea> querySysAreaByCode(String code, Short type);
    /**
     * 事务测试
     */
    public Integer insertSysArea(SysArea sysArea) throws RuntimeException;
}