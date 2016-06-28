/*
 * 文件名称：SysAreaServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.common.SysAreaDao;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.service.common.SysAreaService;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：地区信息相关数据操作实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("sysAreaService")
public class SysAreaServiceImpl implements SysAreaService {

	@Resource
	private SysAreaDao sysAreaDao;

	@Override
	public List<SysArea> queryAllSysAreas() {
		return null;
	}

	@Override
	public List<SysArea> querySysAreasByCode(String code) {
		return sysAreaDao.querySysAreasByCode(code);
	}

	@Override
	public String queryNameByCode(String code) {
		return sysAreaDao.queryNameByCode(code);
	}
	
	@Override
	public SysArea getSysAreaByCode(String code){
		return sysAreaDao.getSysAreaByCode(code);
	}

}