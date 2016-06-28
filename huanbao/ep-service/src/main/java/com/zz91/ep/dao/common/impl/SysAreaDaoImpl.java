/*
 * 文件名称：SysAreaDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午6:02:28
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.common.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.SysAreaDao;
import com.zz91.ep.domain.sys.SysArea;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：地区信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("sysAreaDao")
public class SysAreaDaoImpl extends BaseDao implements SysAreaDao {

	final static String SQL_PREFIX="sysarea";

	@Override
	public List<SysArea> queryAllSysAreas() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> querySysAreasByCode(String code) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySysAreasByCode"),code);
	}

	@Override
	public String queryNameByCode(String code) {
		
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByCode"),code);
	}
	
	@Override
	public SysArea getSysAreaByCode(String code){
		return (SysArea)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getSysAreaByCode"),code);
	}

}
