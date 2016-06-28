/*
 * 文件名称：ParamServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.common.ParamDao;
import com.zz91.ep.service.common.ParamService;
import com.zz91.util.domain.Param;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：系统参数信息相关数据操作实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("paramService")
public class ParamServiceImpl implements ParamService {

	@Resource
	private ParamDao paramDao;
	
	@Override
	public List<Param> queryUsefulParam() {
		return paramDao.queryUsefulParam();
	}

	@Override
	public List<Param> queryParamsByType(String code) {
		return paramDao.queryParamsByType(code);
	}

	@Override
	public Param queryParamByKey(String key){
		return paramDao.queryParamByKey(key);
	}

	@Override
	public String queryNameByTypeAndValue(String category, String value) {
	
		return paramDao.queryNameByTypeAndValue(category, value);
	}
}