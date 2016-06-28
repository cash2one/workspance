package com.zz91.ep.admin.service.comp.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.service.comp.EsiteService;
@Component("esiteService")
public class EsiteServiceImpl implements EsiteService {
	
	@Resource
	private CompProfileDao compProfileDao;
	
	final static String PROTOCOL = "http://";

//	@Override
//	public void initBaseConfig(Integer companyId, String pageCode,
//			Map<String, Object> out) {
//		//TODO 
//	}

//	@Override
//	public Integer initCidFromDomain(String domain, Integer cid) {
//		if(cid!=null && cid.intValue()>0){
//			return cid;
//		}
//		return compProfileDao.queryCidByDomain(domain);
//	}

//	@Override
//	public void initServerAddress(String serverName, int port,
//			String contextPath, Map<String, Object> out) {
//		String esiteAddress = null;
//		if (port == 80) {
//			esiteAddress = PROTOCOL + serverName + contextPath;
//		} else {
//			esiteAddress = PROTOCOL + serverName + ":" + port + contextPath;
//		}
//		out.put("esiteAddress", esiteAddress);
//	}

}
