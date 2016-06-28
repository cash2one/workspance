/*
 * 文件名称：IbdCategoryDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午5:49:43
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.IbdCategoryDao;
import com.zz91.ep.domain.trade.IbdCategory;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：行业买家库
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("ibdCategoryDao")
public class IbdCategoryDaoImpl extends BaseDao implements IbdCategoryDao {

	final static String SQL_PREFIX = "ibdcategory";
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<IbdCategory> queryCategoryByParentCode(String categoryCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByParentCode"), categoryCode);
	}

}