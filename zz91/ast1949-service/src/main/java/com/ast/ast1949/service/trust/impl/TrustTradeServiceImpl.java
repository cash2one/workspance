/**
 * @author shiqp
 * @date 2015-07-22
 */
package com.ast.ast1949.service.trust.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.domain.trust.TrustTrade;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustTradeDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.trust.TrustBuyDao;
import com.ast.ast1949.persist.trust.TrustDealerDao;
import com.ast.ast1949.persist.trust.TrustPicDao;
import com.ast.ast1949.persist.trust.TrustRelateDealerDao;
import com.ast.ast1949.persist.trust.TrustTradeDao;
import com.ast.ast1949.service.trust.TrustTradeService;

@Component("trustTradeService")
public class TrustTradeServiceImpl implements TrustTradeService {
	@Resource
	private TrustTradeDao trustTradeDao;
	@Resource
	private TrustBuyDao trustBuyDao;
	@Resource
	private TrustDealerDao trustDealerDao;
	@Resource
	private TrustRelateDealerDao trustRelateDealerDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private TrustPicDao trustPicDao;
	@Resource
	private CompanyAccountDao companyAccuntDao;

	@Override
	public Integer createTrustTrade(TrustTrade trustTrade) {
		trustTrade.setGmtCreated(new Date());
		trustTrade.setGmtModified(new Date());
		return trustTradeDao.createTrustTrade(trustTrade);
	}

	@Override
	public PageDto<TrustTradeDto> queryTradeList(Integer isDel, PageDto<TrustTradeDto> page, TrustBuySearchDto searchDto) {
		List<TrustTrade> list = trustTradeDao.queryTradeInfo(isDel, page, searchDto);
		List<TrustTradeDto> listResult = new ArrayList<TrustTradeDto>();
		for(TrustTrade tt : list){
			TrustTradeDto dto = new TrustTradeDto();
			dto.setTrade(tt);
			Integer hasPic = trustPicDao.countpicList(tt.getId());
			dto.setHasPic(hasPic);
			TrustBuy buy = trustBuyDao.queryById(tt.getBuyId());
			if(buy!=null){
				dto.setBuy(buy);
				Integer rd= trustRelateDealerDao.queryRelateDealerByBuyNo(buy.getBuyNo());
				if(rd!=null){
					TrustDealer dealer = trustDealerDao.queryById(rd);
					if(dealer!=null){
						dto.setDealer(dealer);
					}
				}else{
					dto.setDealer(new TrustDealer());
				}
				if(buy.getCompanyId()>0){
					Company cc = companyDAO.queryCompanyById(buy.getCompanyId());
					CompanyAccount account = companyAccuntDao.queryAccountByCompanyId(buy.getCompanyId());
					if(cc!=null){
						dto.setToCompany(cc);
					}else{
						dto.setToCompany(new Company());
					}
					if(account!=null){
						dto.getBuy().setMobile(account.getMobile());
						dto.getBuy().setCompanyContact(account.getContact());
					}
				}else{
					Company cc = new Company();
					cc.setName(buy.getCompanyName());
					dto.setToCompany(cc);
				}
			}else{
				dto.setBuy(new TrustBuy());
				dto.setCompany(new Company());
			}
			Company c = companyDAO.queryCompanyById(tt.getCompanyId());
			if(c!=null){
				dto.setCompany(c);
			}else{
				dto.setCompany(new Company());
			}
			listResult.add(dto);
		}
		page.setRecords(listResult);
		page.setTotalRecords(trustTradeDao.countTradeInfo(isDel, searchDto));
		return page;
	}

	@Override
	public Integer updateTrustTradeInfo(TrustTrade trustTrade) {
		return trustTradeDao.updateTrustTradeInfo(trustTrade);
	}

	@Override
	public TrustTrade queryOneTrade(Integer buyId) {
		return trustTradeDao.queryOneTrade(buyId);
	}

}
