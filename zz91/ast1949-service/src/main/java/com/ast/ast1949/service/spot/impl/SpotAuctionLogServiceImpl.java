package com.ast.ast1949.service.spot.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionLogDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.spot.SpotAuctionLogDao;
import com.ast.ast1949.service.spot.SpotAuctionLogService;

/**
 *	author:kongsj
 *	date:2013-3-15
 */
@Component("spotAuctionLogService")
public class SpotAuctionLogServiceImpl implements SpotAuctionLogService{

	@Resource
	private SpotAuctionLogDao spotAuctionLogDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	
	@Override
	public PageDto<SpotAuctionLogDto> pageAuctionLog(
			SpotAuctionLog spotAuctionLog, PageDto<SpotAuctionLogDto> page) {
		page.setTotalRecords(spotAuctionLogDao.queryCountByCondition(spotAuctionLog));
		List<SpotAuctionLog> list = spotAuctionLogDao.queryByCondition(spotAuctionLog, page);
		page.setRecords(coverDoToDto(list));
		return page;
	}

	@Override
	public List<SpotAuctionLogDto> queryByAuctionIdAndSize(Integer auctionId,
			Integer size) {
		List<SpotAuctionLog> list = spotAuctionLogDao.queryByAuctionIdAndSize(auctionId, size);
		return coverDoToDto(list);
	}

	@Override
	public Integer queryCountByAuctionId(Integer auctionId) {
		Integer i = spotAuctionLogDao.queryCountByAuctionId(auctionId);
		if(i>0){
			return i;
		}
		return 0;
	}

	private List<SpotAuctionLogDto> coverDoToDto(List<SpotAuctionLog> list){
		List<SpotAuctionLogDto> nlist = new ArrayList<SpotAuctionLogDto>();
		for(SpotAuctionLog obj:list){
			SpotAuctionLogDto dto = new SpotAuctionLogDto();
			dto.setSpotAuctionLog(obj);
			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(obj.getCompanyId());
			if(ca!=null){
				dto.setMobile(ca.getMobile());
				dto.setContact(ca.getContact());
			}
			nlist.add(dto);
		}
		return nlist;
	}

	@Override
	public Integer insert(SpotAuctionLog spotAuctionLog) {
		Integer i = spotAuctionLogDao.queryCountByCompanyIdAndAuctionId(spotAuctionLog.getCompanyId(), spotAuctionLog.getSpotAuctionId());
		// 如果出过报价则不能重复出价
		if(i>0){
			return 0;
		}
		return spotAuctionLogDao.insert(spotAuctionLog);
	}
}
