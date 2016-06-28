/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-9 上午11:52:41
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.IndustryChainDao;
import com.zz91.ep.admin.service.trade.IndustryChainService;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.dto.PageDto;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;

@Component("industryChainService")
public class IndustryChainServiceImpl implements IndustryChainService {

	@Resource
	private IndustryChainDao industryChainDao;
	
	@Override
	public Integer createIndustryChain(IndustryChain chain) {
		Assert.notNull(chain, "object chain must not be null");
		if(chain.getOrderby()==null){
			chain.setOrderby(0);
		}
		if (chain.getShowIndex()==null){
			chain.setShowIndex(0);
		}
		if (chain.getDelStatus()==null){
			chain.setDelStatus((short)0);
		}
		chain.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, chain.getAreaCode()));
		return industryChainDao.insertIndustryChain(chain);
	}

	@Override
	public Integer updateDelStatusById(Integer id,Short status) {
		Assert.notNull(id, "the id is must not be null");
		Assert.notNull(status, "the status is must not be null");
		return industryChainDao.updateDelStatusById(id,status);
	}

	@Override
	public PageDto<IndustryChain> pageIndustryChains(String areaCode,PageDto<IndustryChain> page,Integer cid) {
		page.setRecords(industryChainDao.queryIndustryChains(areaCode, page,cid));
		page.setTotals(industryChainDao.queryIndustryChainsCount(areaCode,cid));
		return page;
	}

	@Override
	public Integer updateIndustryChain(IndustryChain chain) {
		Assert.notNull(chain, "object chain must not be null");
		if(chain.getOrderby()==null){
			chain.setOrderby(0);
		}
		if (chain.getShowIndex()==null){
			chain.setShowIndex(0);
		}
		if (chain.getDelStatus()==null){
			chain.setDelStatus((short)0);
		}
		chain.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, chain.getAreaCode()));
		return industryChainDao.updateIndustryChain(chain);
	}

	@Override
	public Integer updateShowIndexById(Integer id,Short showIndex) {
		Assert.notNull(id, "the id is must not be null");
		Assert.notNull(showIndex, "the showIndex is must not be null");
		return industryChainDao.updateShowIndexById(id, showIndex);
	}

	@Override
	public List<IndustryChain> querySimpChain() {
		return industryChainDao.querySimpChain();
	}

	@Override
	public List<IndustryChain> querySimpChainByAreaCode(String areaCode) {
		List<IndustryChain> list = industryChainDao.querySimpChainByAreaCode(areaCode);
		for(IndustryChain ic : list){
			ic.setCategoryName(ic.getAreaName()+ic.getCategoryName());
		}
		return list;
	}

}
