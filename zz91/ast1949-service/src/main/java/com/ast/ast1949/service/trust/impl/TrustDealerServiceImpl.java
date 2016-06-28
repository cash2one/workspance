/**
 * @author kongsj
 * @date 2015年5月11日
 * 
 */
package com.ast.ast1949.service.trust.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.trust.TrustDealerDao;
import com.ast.ast1949.service.trust.TrustDealerService;

@Component("trustDealerService")
public class TrustDealerServiceImpl implements TrustDealerService{

	@Resource
	private TrustDealerDao trustDealerDao;
	
	@Override
	public Integer createOneDealer(String name, String tel, String qq) {
		TrustDealer trustDealer = new TrustDealer();
		trustDealer.setName(name);
		trustDealer.setTel(tel);
		trustDealer.setQq(qq);
		return trustDealerDao.insert(trustDealer);
	}

	@Override
	public Integer updateOneDealer(TrustDealer trustDealer) {
		if (trustDealer.getId()==null) {
			return 0;
		}
		return trustDealerDao.update(trustDealer);
	}

	@Override
	public PageDto<TrustDealer> pageByCondition(TrustDealer trustDealer,
			PageDto<TrustDealer> page) {
		page.setTotalRecords(trustDealerDao.queryCountByCondition(trustDealer));
		page.setRecords(trustDealerDao.queryByCondition(trustDealer, page));
		return page;
	}

	@Override
	public List<TrustDealer> queryAllDealer() {
		return trustDealerDao.queryAllDealer();
	}

	@Override
	public Integer deleteDealer(Integer id) {
		return trustDealerDao.deleteDealer(id);
	}
}
