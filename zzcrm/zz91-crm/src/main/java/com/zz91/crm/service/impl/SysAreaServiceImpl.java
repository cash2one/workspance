package com.zz91.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.crm.dao.SysAreaDao;
import com.zz91.crm.domain.SysArea;
import com.zz91.crm.service.SysAreaService;
import com.zz91.util.Assert;

@Service("sysAreaService")

public class SysAreaServiceImpl implements SysAreaService {

    @Resource private SysAreaDao sysAreaDao;

    @Override
    public List<SysArea> querySysAreaByCode(String code, Short type) {
        Assert.notNull(code, "code不能为null吧!");
        Assert.notNull(type, "type不能为null吧!");
        return sysAreaDao.querySysAreaByCode(code, type);
    }

	@Override
	public Integer insertSysArea(SysArea sysArea) {
		return sysAreaDao.insertSysArea(sysArea);
	}
    
}