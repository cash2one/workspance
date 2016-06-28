/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-15 下午01:30:36
 */
package com.zz91.ep.admin.service.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.impl.CompProfileDaoImpl;
import com.zz91.ep.admin.dao.trade.CompanyIndustryChainDao;
import com.zz91.ep.admin.service.trade.CompanyIndustryChainService;
import com.zz91.ep.domain.comp.CompanyIndustryChain;
import com.zz91.util.Assert;

@Component("companyIndustryChainService")
public class CompanyIndustryChainServiceImpl implements
		CompanyIndustryChainService {

	@Resource
	private CompanyIndustryChainDao companyIndustryChainDao;
	@Resource
	private CompProfileDaoImpl compProfileDaoImpl;

	@Override
	public Integer createCompIndustryChain(CompanyIndustryChain chain) {
		Assert.notNull(chain, "the chain must not be null");
		Assert.notNull(chain.getChainId(),
				"the chain.getChainId() must not be null");
		Assert.notNull(chain.getCid(), "the chain.getCid() must not be null");
		Integer i = 0;
		Integer count = companyIndustryChainDao.queryCountByCidAndChainId(
				chain.getCid(), chain.getChainId());
		if (count > 0) {
			i = -1;
		} else {
			if (chain.getDelStatus() == null) {
				chain.setDelStatus((short) 0);
			}
			i = companyIndustryChainDao.insertCompIndustryChain(chain);
		}
		// 更新公司更新时间
		compProfileDaoImpl.updateProfileGmtModified(chain.getCid());
		return i;
	}

	@Override
	public Integer updateCompIndustryChain(CompanyIndustryChain chain) {
		Assert.notNull(chain, "the chain must not be null");
		Assert.notNull(chain.getId(), "the chain.getId() must not be null");
		Assert.notNull(chain.getChainId(),
				"the chain.getChainId() must not be null");
		Assert.notNull(chain.getCid(), "the chain.getCid() must not be null");
		// 更新公司更新时间
		compProfileDaoImpl.updateProfileGmtModified(chain.getCid());

		return companyIndustryChainDao.updateCompIndustryChain(chain);
	}

	@Override
	public Integer updateDelStatusByCidAndChainId(Integer cid, Integer chainId,
			Short delStatus) {
		Assert.notNull(cid, "the cid must not be null");
		Assert.notNull(chainId, "the chainId must not be null");
		Assert.notNull(delStatus, "the delStatus must not be null");
		// 更新公司更新时间
		compProfileDaoImpl.updateProfileGmtModified(cid);

		return companyIndustryChainDao.updateDelStatusByCidAndChainId(cid,
				chainId, delStatus);
	}

}
