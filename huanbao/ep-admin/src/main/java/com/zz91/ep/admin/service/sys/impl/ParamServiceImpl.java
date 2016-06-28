package com.zz91.ep.admin.service.sys.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zz91.ep.admin.dao.sys.ParamDao;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.util.Assert;
import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

@Service("paramService")
public class ParamServiceImpl implements ParamService {

	@Resource
	private ParamDao paramDao;
	private static Logger LOG = Logger.getLogger(ParamServiceImpl.class);
	@Override
	public List<Param> queryUsefulParam() {
		List<Param> list = paramDao.queryAllParam();
		return list;
	}

	@Override
	public List<Param> queryParamByType(String type) {
		return paramDao.queryParamByType(type);
	}
	
	public void initCache() {
		LOG.info("Init system param cache begin...");

		List<Param> paramList = paramDao.listParam(new Param());

		Map<String, Map<String, String>> paramCache = new LinkedHashMap<String, Map<String,String>>();

		for(Param p:paramList){
			if(paramCache.keySet().contains(p.getTypes())){
				paramCache.get(p.getTypes()).put(p.getKey(), p.getValue());
			}else {
				Map<String, String> paramMap = new LinkedHashMap<String, String>();
				paramMap.put(p.getKey(), p.getValue());
				paramCache.put(p.getTypes(), paramMap);
			}
		}


		LOG.info("Init system param cache end...");
	}

	public void destroyCache(){
		LOG.info("Shutdown system, delete param cache begin...");


		LOG.info("Shutdown system, delete param cache end...");

	}

	public Integer insertParam(Param param) {
		if(param.getIsuse()==null) {
			param.setIsuse(0);
		}
		return paramDao.insertParam(param);
	}

	public List<Param> listParamByTypes(String types) {
		Assert.notNull(types, "types can not be null");
		Param param=new Param();
		param.setTypes(types);
		return paramDao.listParam(param);
	}

	public Integer updateParam(Param param) {
		if(param.getIsuse()==null) {
			param.setIsuse(0);
		}
		return paramDao.updateParam(param);
	}

	public List<ParamType> listAllParamTypes() {
		return paramDao.listAllParamType();
	}

	public Integer deleteParam(Integer id) {
		Assert.notNull(id, "id can not be null");
		return paramDao.deleteParamById(id);
	}

	public Param listOneParam(Integer id) {
		Assert.notNull(id, "id can not be null");
		return paramDao.listOneParamById(id);
	}

	public String backupToSqlString() {
		List<ParamType> type = paramDao.listAllParamType();
		List<Param> param = paramDao.listParam(new Param());
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from param;");
		sb.append("delete from param_type;");
		
		for(ParamType t:type){
			sb.append("insert into param_type(`key`,name,gmt_created) values('")
				.append(t.getKey()).append("','").append(t.getName())
				.append("',now());");
		}
		
		sb.append("insert into param(name,types,`key`,value,sort,isuse,gmt_created) values");
		int i=0;
		for(Param p:param){
			if(i>0){
				sb.append(",");
			}
			sb.append("('")
				.append(p.getName()).append("','")
				.append(p.getTypes()).append("','")
				.append(p.getKey()).append("','")
				.append(p.getValue()).append("',")
				.append(p.getSort()).append(",")
				.append(p.getIsuse()).append(",now())");
			i++;
		}
		sb.append(";");
		return sb.toString();
	}

//	@Override
//	public String queryValueByTypeAndKey(String type, String key) {
//		return paramDao.queryValueByTypeAndKey(type, key);
//	}
}
