package com.ast.ast1949.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyLottery;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyLotteryDao;
import com.ast.ast1949.service.company.CompanyLotteryService;
import com.ast.ast1949.util.Assert;

@Component("companyLotteryService")
public class CompanyLotteryServiceImpl implements CompanyLotteryService{
	
	@Resource
	private CompanyLotteryDao companyLotteryDao;
	
	@Override
	public Integer addOne(Integer companyId,String lotteryCode,String companyAccount,String csAccount) {
		CompanyLottery companyLottery = new CompanyLottery();
		companyLottery.setCompanyId(companyId);
		companyLottery.setCompanyAccount(companyAccount);
		companyLottery.setLotteryCode(lotteryCode);
		companyLottery.setCsAccount(csAccount);
		// 默认为关闭状态
		companyLottery.setStatus("0");
		return companyLotteryDao.insert(companyLottery);
	}


	@Override
	public PageDto<CompanyLottery> pageCompanyLottery(CompanyLottery companyLottery, PageDto<CompanyLottery> page) {
		page.setTotalRecords(companyLotteryDao.queryCount(companyLottery));
		page.setRecords(companyLotteryDao.query(companyLottery, page));
		return page;
	}
	
	@Override
	public Integer openOneLottery(Integer id) {
		Assert.notNull(id, "id must not be null");
		return companyLotteryDao.updateStatus(id,"1");
	}

	@Override
	public Integer closeOneLottery(Integer id) {
		Assert.notNull(id, "id must not be null");
		return companyLotteryDao.updateStatus(id,"0");
	}

	@Override
	public Integer sucOneLottery(Integer id) {
		Assert.notNull(id, "id must not be null");
		return companyLotteryDao.updateStatus(id,"2");
	}


	@Override
	public List<CompanyLottery> queryCompanyLotteryed(Integer size) {
		if(size==null||size>200){
			size = 50;
		}
		return companyLotteryDao.queryCompanyLotteryed(size);
	}

	@Override
	public Integer queryCountLotteryByCompanyId(Integer companyId) {
		return companyLotteryDao.queryCountLotteryByCompanyId(companyId);
	}


	@Override
	public CompanyLottery queryLotteryByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "companyId must not be null");
		return companyLotteryDao.queryLotteryByCompanyId(companyId);
	}

	@Override
	public Integer updateLottery(String lottery, Integer id) {
		Assert.notNull(id, "id must not be null");
		Assert.notNull(lottery, "id must not be null");
		CompanyLottery companyLottery = new CompanyLottery();
		companyLottery.setId(id);
		companyLottery.setLottery(lottery);
		return companyLotteryDao.update(companyLottery);
	}

}
