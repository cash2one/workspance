/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.service.bbs.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.persist.bbs.BbsSignDao;
import com.ast.ast1949.service.bbs.BbsSignService;
@Component("bbsSignService")
public class BbsSignServiceImpl implements BbsSignService {
	@Resource
	private BbsSignDao bbsSignDao;
	@Override
	public BbsSignDO querySignByCompanyId(Integer companyId) {
		return bbsSignDao.querySignByCompanyId(companyId);
	}

}
