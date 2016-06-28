/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.service.market.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.MarketCompany;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.market.MarketCompanyDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CompanyUploadFileDAO;
import com.ast.ast1949.persist.market.MarketCompanyDao;
import com.ast.ast1949.persist.market.MarketDao;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("marketCompanyService")
public class MarketCompanyServiceImpl implements MarketCompanyService {
    @Resource
    private MarketCompanyDao marketCompanyDao;
    @Resource
    private CompanyDAO companyDAO;
    @Resource
    private MarketDao marketDao;
    @Resource
    private CompanyAccountDao companyAccountDao;
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyUploadFileDAO companyUploadFileDAO;

	@Override
	public List<MarketCompanyDto> queryNewCompany(Integer marketId, Integer size) {
		// size 不可为空
		if(size==null){
			size=10;
		}
		//最新加入（该）市场的商户公司id集
		List<MarketCompany> list=new ArrayList<MarketCompany>();
		//公司信息和市场信息
		List<MarketCompanyDto> listR=new ArrayList<MarketCompanyDto>();
		//最新加入市场的商户
		if(marketId==null){
			list=marketCompanyDao.queryNewComapny(null,size);
		}else{
			//最新加入该市场的商户
			list=marketCompanyDao.queryNewComapny(marketId, size);
		}
		for(MarketCompany mc:list){
			MarketCompanyDto dto=new MarketCompanyDto();
			//获取公司名称
			Company company=companyDAO.queryCompanyById(mc.getCompanyId());
			//获取市场名称
			Market market=marketDao.queryMarketById(mc.getMarketId());
			dto.setCompany(company);
			dto.setMarket(market);
			listR.add(dto);
		}
		//某市场下，新加入的商户
		if(marketId!=null){
			for(MarketCompanyDto dto:listR){
				String areaCode=dto.getCompany().getAreaCode();
				String province="";
				String city="";
				if(areaCode.length()>11){
					province=CategoryFacade.getInstance().getValue(areaCode.substring(0, 12));
				}
				if(areaCode.length()>15){
					city=CategoryFacade.getInstance().getValue(areaCode.substring(0, 16));
				}
				if(areaCode.length()<8){
					province=CategoryFacade.getInstance().getValue(areaCode);
				}
				dto.getCompany().setAddress(province+" "+city);
			}
		}
		return listR;
	}
	@Override
	public List<Market> queryMarketByCompanyId(Integer companyId) {
		List<Integer> list=marketCompanyDao.queryMarketByCompanyId(companyId);
		List<Market> listR=new ArrayList<Market>();
		for(Integer li:list){
			Market market=new Market();
			market=marketDao.queryMarketById(li);
			if(market!=null){
				market.setCompanyId(companyId);
			}
			listR.add(market);
		}
		return listR;
	}
	
	@Override
	public Market queryFirstMarketByCompanyId(Integer companyId) {
		Integer mid = marketCompanyDao.queryFirstMarketByCompanyId(companyId);
		Market market = marketDao.queryMarketById(mid);
		if (market==null) {
			return null;
		}
		return market;
	}
	
	@Override
	public Integer insertMarketCompany(Integer marketId, Integer companyId) {
		MarketCompany marketCompany=marketCompanyDao.queryMarketCompanyByBothId(marketId, companyId);
		Integer flag=-2;
		if(marketCompany!=null){
			if(marketCompany.getIsQuit()==1){
				flag=marketCompanyDao.updateIsQuitByBothId(marketId, companyId, 0);
			}
		}else{
			flag=marketCompanyDao.insertMarketCompany(marketId, companyId);
		}
		if(flag>0){
			Market market=marketDao.queryMarketById(marketId);
		    marketDao.updateCompanyByMarketId(marketId, (market.getCompanyNum()+1));
		}
		return flag;
	}
	@Override
	public PageDto<CompanyDto> PageSearchCompanyByCondition(PageDto<CompanyDto> page, Integer marketId,Integer type) {
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}
		List<CompanyDto> list=new ArrayList<CompanyDto>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		try {
			cl.SetFilter("is_quit", 0, false);
			cl.SetFilter("market_id", marketId, false);
			if(type==0){
				//有效供求数排序
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "product_num desc");
			}
			//会员等级，普会按照有效供求数排序
			if(type==1){
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "viptype desc,product_num desc");
			}
			if(type==3){
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "viptype desc,zst_year desc,gmt_created desc");
			}
			cl.SetFilter("is_block", 0, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			SphinxResult res = cl.Query(sb.toString(), "company_market");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if(res.totalFound>10000){
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					//市场商户关系
					MarketCompany marketCompany=marketCompanyDao.queryMarketCompanyById(Integer.valueOf("" + info.docId));
					if(marketCompany!=null){
					if(type<3){
						Company company=companyDAO.queryCompanyById(marketCompany.getCompanyId());
						if(company!=null){
							if(StringUtils.isNotEmpty(company.getIntroduction())){
								company.setIntroduction(Jsoup.clean(company.getIntroduction(), Whitelist.none()));
							}
							CompanyDto dto=new CompanyDto();
							List<CompanyUploadFileDO> plist=companyUploadFileDAO.queryByCompanyId(company.getId());
							if(plist.size()>0){
								dto.setPic(plist.get(0));
							}
							dto.setCompany(company);
							list.add(dto);
						}
					}else{
						CompanyDto dto=new CompanyDto();
						Company company=companyDAO.queryCompanyById(marketCompany.getCompanyId());
						if(company!=null){
							if(StringUtils.isNotEmpty(company.getIntroduction())){
								company.setIntroduction(Jsoup.clean(company.getIntroduction(), Whitelist.none()));
							}
							List<CompanyUploadFileDO> plist=companyUploadFileDAO.queryByCompanyId(company.getId());
							if(plist.size()>0){
								dto.setPic(plist.get(0));
							}
							if(StringUtils.isNotEmpty(company.getAreaCode())&&company.getAreaCode().length()>11){
								dto.setAddress(CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12)));
							}
							if(StringUtils.isNotEmpty(company.getAreaCode())&&company.getAreaCode().length()>15){
								dto.setAddress(dto.getAddress()+" "+CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 16)));
							}
							dto.setCompany(company);
						}
						CompanyAccount account=companyAccountDao.queryAccountByCompanyId(marketCompany.getCompanyId());
						if(account!=null){
							dto.setAccount(account);
						}
						list.add(dto);
					}
				}
			}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		return page;
	}
	@Override
	public Integer countMarketCompany() {
		return marketCompanyDao.countMarketCompany();
	}
	@Override
	public PageDto<CompanyDto> pageCompanyByAdminMarketId( PageDto<CompanyDto> page, Integer marketId, String membershipCode,Integer isPerson) {
	
		List<CompanyDto> resultList=new ArrayList<CompanyDto>();
		
		if (marketId!=null&&marketId.intValue()>0) {
			
			List<MarketCompany> list=marketCompanyDao.pageCompanyByAdminMarketId(page, marketId,membershipCode,isPerson);
			for (MarketCompany marketCompany : list) {
				CompanyDto dto = new CompanyDto();
				dto.setNumProducts(marketCompany.getProductNum());
				if (marketCompany != null && marketCompany.getCompanyId() != null&& marketCompany.getCompanyId().intValue() > 0) {
					Company c = companyDAO.querySimpleCompanyById(marketCompany
							.getCompanyId());
					if (c == null) {
						c = new Company();
					}
					dto.setCompany(c);
					CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(marketCompany.getCompanyId());
					if (ca == null) {
						ca = new CompanyAccount();
					}
					dto.setAccount(ca);
					if (marketCompany.getMarketId()!=null&&marketCompany.getMarketId().intValue()>0) {
						Market market=marketDao.queryMarketById(marketCompany.getMarketId());
						if (market!=null) {
							dto.setMarketName(market.getName());
						}
					}
					
				}
				resultList.add(dto);	
			}
			page.setTotalRecords(marketCompanyDao.pageCompanyByAdminMarketIdCount(marketId,membershipCode,isPerson));
		}
		page.setRecords(resultList);
		return page;
	}
	@Override
	public PageDto<MarketCompanyDto> queryListMarketByadmin(PageDto<MarketCompanyDto> page, CompanyAccount companyAccount,String companyName) {
		List<MarketCompanyDto> resultList=new ArrayList<MarketCompanyDto>();
		
		List<MarketCompany> list=marketCompanyDao.queryListMarketByadmin(page, companyAccount, companyName);
		for (MarketCompany marketCompany : list) {
			if (marketCompany!=null&&marketCompany.getCompanyId()!=null&&marketCompany.getCompanyId().intValue()>0) {
				MarketCompanyDto dto=new MarketCompanyDto();
				Company c=companyService.querySimpleCompanyById(marketCompany.getCompanyId());
				if (c == null) {
					continue;
//					c = new Company();
				}
				dto.setCompany(c);
				CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(marketCompany.getCompanyId());
				if (ca!=null&&ca.getAccount()!=null) {
					dto.setAccount(ca.getAccount());
				}
				if (marketCompany.getMarketId()==null||marketCompany.getMarketId().intValue()<=0) {
					continue;
				}
				Market market=marketDao.queryMarketById(marketCompany.getMarketId());
				if (market == null) {
					market = new Market();
				}
				dto.setMarket(market);
				if (marketCompany.getGmtCreated()!=null) {
					dto.setRegTime(marketCompany.getGmtCreated());
				}
				resultList.add(dto);
			}
			
		}
		page.setRecords(resultList);
		page.setTotalRecords(marketCompanyDao.queryListMarketByadminCount(companyAccount, companyName));
		return page;
	}
	@Override
	public Integer updateIsQuitByBothId(Integer marketId, Integer companyId,Integer isQuit) {
		if(isQuit==1){
			Market market=marketDao.queryMarketById(marketId);
			MarketCompany com=marketCompanyDao.queryMarketCompanyByBothId(marketId, companyId);
			if(com.getIsQuit()==0){
				marketDao.updateCompanyByMarketId(marketId, (market.getCompanyNum()-1));
			}
		}
		return  marketCompanyDao.updateIsQuitByBothId(marketId, companyId, isQuit);
	}


}
