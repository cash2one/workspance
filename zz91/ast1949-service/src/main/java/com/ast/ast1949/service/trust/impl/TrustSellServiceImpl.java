/**
 * @author kongsj
 * @date 2015年5月9日
 * 
 */
package com.ast.ast1949.service.trust.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.domain.trust.TrustRelateDealer;
import com.ast.ast1949.domain.trust.TrustRelateSell;
import com.ast.ast1949.domain.trust.TrustSell;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustSellDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.trust.TrustBuyDao;
import com.ast.ast1949.persist.trust.TrustDealerDao;
import com.ast.ast1949.persist.trust.TrustRelateDealerDao;
import com.ast.ast1949.persist.trust.TrustRelateSellDao;
import com.ast.ast1949.persist.trust.TrustSellDao;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.trust.TrustSellService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

@Component("trustSellService")
public class TrustSellServiceImpl implements TrustSellService{

	@Resource
	private TrustSellDao trustSellDao;
	@Resource
	private TrustBuyDao trustBuyDao;
	@Resource
	private TrustDealerDao trustDealerDao;
	@Resource
	private TrustRelateSellDao trustRelateSellDao;
	@Resource
	private TrustRelateDealerDao trustRelateDealerDao;
	@Resource
	private CompanyDAO companyDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	
	
	@Override
	public Integer publishTrustSell(Integer companyId, Integer buyId,String content) {
		if (buyId==null||StringUtils.isEmpty(content)) {
			return 0;
		}
		if (companyId==null) {
			companyId = 0;
		}
		try {
			if (!StringUtils.isContainCNChar(content)) {
				// 解密
				content = StringUtils.decryptUrlParameter(content);
				if (!StringUtils.isContainCNChar(content)) {
					content = URLDecoder.decode(content, HttpUtils.CHARSET_UTF8);
				}
			}
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
		TrustBuy tb =  trustBuyDao.queryById(buyId);
		if (tb==null) {
			return 0;
		}
		// 一个人只能给一个采购商供货留言
		List<Map<String,String>> list = trustRelateSellDao.queryByCompanyIdAndBuyId(companyId, buyId);
		if (list!=null&&list.size()>0) {
			return 0;
		}
		TrustSell trustSell = new TrustSell();
		trustSell.setCompanyId(companyId);
		trustSell.setContent(content);
		trustSell.setStatus(STATUS_00);
		Integer i = trustSellDao.insert(trustSell);
		// 插入成功将 供货id与 采购id关联
		if (i>0) {
			TrustRelateSell relateSell=new TrustRelateSell();
			relateSell.setBuyId(buyId);
			relateSell.setSellId(i);
			TrustRelateSell obj = trustRelateSellDao.queryBySellId(i);
			if (obj!=null) {
				return 0;
			}
			i=trustRelateSellDao.insert(relateSell);
		}
		return i;
	}
	
	@Override
	public Integer publishTrustSellByAccount(String account,Integer buyId,String content){
		if (StringUtils.isEmpty(account)||buyId==null||buyId<1||StringUtils.isEmpty(content)) {
			return 0;
		}
		CompanyAccount ca = companyAccountDao.queryAccountByAccount(account);
		if (ca==null||ca.getCompanyId()==null||ca.getCompanyId()==0) {
			return 0;
		}
		return publishTrustSell(ca.getCompanyId(),buyId,content);
	}

	@Override
	public PageDto<TrustSellDto> pageByCondition(TrustBuySearchDto searchDto,PageDto<TrustSellDto> page) {
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("ts.id");
		}
		if (StringUtils.isEmpty(page.getDir())) {
			page.setDir("desc");
		}
		
		page.setTotalRecords(trustSellDao.queryCountByCondition(searchDto));
		List<TrustSellDto> list =  trustSellDao.queryByCondition(searchDto, page);
		for (TrustSellDto obj: list) {
			TrustDealer dealer=new TrustDealer();
			TrustBuy buy=trustBuyDao.queryById(obj.getRelateSell().getBuyId());
			if(buy!=null){
				//采购单信息
				obj.setBuy(buy);
				//采购单交易员联系人id
				if(StringUtils.isNotEmpty(buy.getBuyNo())){
					Integer dealerId=trustRelateDealerDao.queryRelateDealerByBuyNo(buy.getBuyNo());
					if(dealerId!=null){
						//交易员信息
						dealer=trustDealerDao.queryById(dealerId);
					}
					if (dealer!=null) {
						obj.setDealer(dealer);
					}else{
						obj.setDealer(new TrustDealer());
					}
				}
				if(StringUtils.isNotEmpty(buy.getCode())){
					obj.setCategoryName(CategoryProductsFacade.getInstance().getValue(buy.getCode()));
				}
				if(buy.getCompanyId()!=null&&buy.getCompanyId()!=0){
					Company toCompany=companyDao.queryCompanyById(buy.getCompanyId());
					if (toCompany==null) {
						obj.setToCompany(new Company());
					}else{
						obj.setToCompany(toCompany);
					}
					CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(buy.getCompanyId());
					if (ca!=null) {
						obj.setToCompanyContact(ca.getContact());
						obj.setToCompanyMobile(ca.getMobile());
					}
				}else{
					Company c = new Company();
					c.setId(0);
					c.setName(buy.getCompanyName());
					obj.setToCompany(c);
					obj.setToCompanyContact(buy.getCompanyContact());
					obj.setToCompanyMobile(buy.getMobile());
				}
			}else{
				obj.setBuy(new TrustBuy());
				obj.setDealer(new TrustDealer());
				obj.setCategoryName("");
				obj.setToCompany(new Company());
			}
			Company company=companyDao.queryCompanyById(obj.getSell().getCompanyId());
			if(company!=null){
				obj.setCompany(company);
			}
			obj.setRelateDealer(new TrustRelateDealer());
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Integer editByStatus(Integer id, String status) {
		if (id==null||StringUtils.isEmpty(status)) {
			return 0;
		}
		return trustSellDao.updateByStatus(id, status);
	}

	@Override
	public PageDto<TrustSellDto> pageSupplyByCondition(TrustSell sell, PageDto<TrustSellDto> page) {
		List<TrustSellDto> list=trustSellDao.querySupplyByCondition(sell, page);
		for(TrustSellDto dto:list){
			//获取采购信息
			TrustBuy buy=trustBuyDao.queryById(dto.getRelateSell().getBuyId());
			if(buy!=null&&StringUtils.isNotEmpty(buy.getBuyNo())){
				dto.setBuy(buy);
				Integer dealerId=trustRelateDealerDao.queryRelateDealerByBuyNo(buy.getBuyNo());
				if(dealerId!=null){
					TrustDealer dealer=trustDealerDao.queryById(dealerId);
					if(dealer!=null){
						dto.setDealer(dealer);
					}
				}
			}
		}
		page.setRecords(list);
		page.setTotalRecords(trustSellDao.countSupplyByCondition(sell));
		return page;
	}

	@Override
	public Integer countByCompanyId(Integer companyId) {
		if (companyId==null||companyId<=0) {
			return 0;
		}
		TrustSell sell = new TrustSell();
		sell.setCompanyId(companyId);
		return trustSellDao.countSupplyByCondition(sell);
	}

	@Override
	public List<Integer> queryBuyIdByCompanyId(Integer companyId) {
		return trustSellDao.queryBuyIdByCompanyId(companyId);
	}
	
}
