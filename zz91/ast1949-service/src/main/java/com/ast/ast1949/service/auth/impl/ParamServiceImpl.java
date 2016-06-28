/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-2
 */
package com.ast.ast1949.service.auth.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.auth.ParamDao;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

/**
 * @author Mr.Mar (x03570227@gmail.com)
 *
 */
@Component("paramService")
public class ParamServiceImpl implements ParamService {

	@Autowired
	private ParamDao paramDao;

//	private String dEFAULT_CACHE_SERVER = "192.168.2.9";
//	private int dEFAULT_CACHE_SERVER_PORT = 11211;

	private static Logger LOG = Logger.getLogger(ParamServiceImpl.class);


	public void initCache() {
		LOG.info("Init system param cache begin...");

		List<Param> paramList = paramDao.listParam(new Param());

		Map<String, Map<String, String>> paramCache = new LinkedHashMap<String, Map<String,String>>();
//		String cacheServer = dEFAULT_CACHE_SERVER;
//		int cacheServerPort = dEFAULT_CACHE_SERVER_PORT;

		for(Param p:paramList){
			if(paramCache.keySet().contains(p.getTypes())){
//				Map<String, String> paramMap=paramCache.get(p.getTypes());
				paramCache.get(p.getTypes()).put(p.getKey(), p.getValue());
			}else {
				Map<String, String> paramMap = new LinkedHashMap<String, String>();
				paramMap.put(p.getKey(), p.getValue());
				paramCache.put(p.getTypes(), paramMap);
			}
		}

//		Param.paramCache=paramCache;

//		CacheConst.DEFAULT_SERVER = paramCache.get("baseConfig").get("cache_server");
//		CacheConst.DEFAULT_SERVER_PORT = Integer.valueOf(paramCache.get("baseConfig").get("cache_server_port"));
//
//		MemcachedClient cacheClient ;
//		try {
//			cacheClient = new MemcachedClient(
//					new InetSocketAddress(CacheConst.DEFAULT_SERVER,CacheConst.DEFAULT_SERVER_PORT));
//		} catch (IOException e) {
//			throw new ServiceLayerException("连接缓存服务器发生错误...",e);
//		}finally{
//
//		}

//		cacheClient.set(CacheConst.CACHE_PARAM, CacheConst.CACHED_TIMEOUT_LONG, paramCache);
//		cacheClient.set(CacheConst.CACHE_PARAM_IS_OLD, CacheConst.CACHED_TIMEOUT_LONG, false);

		LOG.info("Init system param cache end...");
	}

	public void destroyCache(){
		LOG.info("Shutdown system, delete param cache begin...");

//		MemcachedClient cacheClient ;
//		try {
//			cacheClient = new MemcachedClient(new InetSocketAddress(CacheConst.DEFAULT_SERVER,CacheConst.DEFAULT_SERVER_PORT));
//		} catch (IOException e) {
//			throw new ServiceLayerException("连接缓存服务器发生错误...",e);
//		}finally{
//
//		}
//		cacheClient.delete(CacheConst.CACHE_PARAM);
//		cacheClient.delete(CacheConst.CACHE_PARAM_IS_OLD);

		LOG.info("Shutdown system, delete param cache end...");

	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.service.param.ParamService#insertParam(com.ast.ast1949.domain.param.Param)
	 */
	public Integer insertParam(Param param) {
		return paramDao.insertParam(param);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.service.param.ParamService#listAllParam()
	 */
	public List<Param> listParamByTypes(String types) {
		Assert.notNull(types, "types can not be null");
		Param param=new Param();
		param.setTypes(types);
		return paramDao.listParam(param);
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.service.param.ParamService#updateParam(com.ast.ast1949.domain.param.Param)
	 */
	public Integer updateParam(Param param) {
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

	@Override
	public List<Param> queryUsefulParam() {
		Param p=new Param();
		p.setIsuse(1);
		return paramDao.listParam(p);
	}

}
