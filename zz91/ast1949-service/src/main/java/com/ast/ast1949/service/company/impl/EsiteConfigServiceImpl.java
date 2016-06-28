/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-16
 */
package com.ast.ast1949.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteConfigDo;
import com.ast.ast1949.persist.company.EsiteConfigDao;
import com.ast.ast1949.service.company.EsiteConfigService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.StringUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-16
 */
@Component("esiteConfigService")
public class EsiteConfigServiceImpl implements EsiteConfigService{
	
	@Autowired
	EsiteConfigDao esiteConfigDao;

	@Override
	public Integer insertColumnConfig(EsiteConfigDo config) {
		Assert.notNull(config, "the config can not be null");
		if (StringUtils.isEmpty(config.getMycolumn())) {
			config.setMycolumn("sy");
		}
		return esiteConfigDao.insertColumnConfig(config);
	}

	@Override
	public Integer updateColumnConfigById(EsiteConfigDo config) {
		Assert.notNull(config, "the config can not be null");
		if (StringUtils.isEmpty(config.getMycolumn())) {
			config.setMycolumn("sy");
		}
		return esiteConfigDao.updateColumnConfigById(config);
	}

	@Override
	public Integer deleteColumnConfigByCompany(Integer companyId,
			String columnCode) {
		Assert.notNull(companyId, "the companyId can not be null");
		if (StringUtils.isEmpty(columnCode)) {
			columnCode="sy";
		}
		return esiteConfigDao.deleteColumnConfigByCompany(companyId, columnCode);
	}

	@Override
	public Integer updateBannelPicByCompanyId(String pic, Integer cid) {
		return esiteConfigDao.updateBannelPicByCompanyId(pic, cid);
	}

	@Override
	public String queryBannelPic(Integer cid) {
		return esiteConfigDao.queryBannelPic(cid);
	}

	@Override
	public Integer updateIsShowForHeadPic(Integer cid, Integer isShow) {
		return esiteConfigDao.updateIsShowForHeadPic(cid, isShow);
	}

}
