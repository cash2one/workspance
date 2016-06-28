/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.service.market.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.MarketDo;
import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.domain.market.MarketSubscribe;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketSearchDto;
import com.ast.ast1949.dto.market.MarketSubscribeDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.market.MarketCompanyDao;
import com.ast.ast1949.persist.market.MarketDao;
import com.ast.ast1949.persist.market.MarketPicDao;
import com.ast.ast1949.persist.market.MarketSubscribeDao;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.service.market.MarketService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("marketService")
public class MarketServiceImpl implements MarketService {
    @Resource
    private MarketDao marketDao;
    @Resource
    private CompanyDAO companyDAO;
    @Resource
    private ProductsService productsService;
    @Resource
    private CompanyAccountDao companyAccountDao;
    @Resource
    private CompanyUploadFileService companyUploadFileService;
    @Resource
    private MarketPicDao marketPicDao;
    @Resource
    private MarketCompanyDao marketCompanyDao;
    @Resource
    private MarketSubscribeDao marketSubscribeDao;
	@Override
	public Integer countMarketByCondition(String area, String industry, String category) {
		return marketDao.countMarketByCondition(area, industry, category);
	}
	@Override
	public List<Market> queryMarketByCondition(String industry, String area, Integer size) {
		List<Market> list=marketDao.queryMarketByCondition(industry, area, size);
		if(StringUtils.isNotEmpty(area)){
			for(Market m:list){
				List<MarketPic> plist=marketPicDao.queryPicByMarketId(m.getId());
				if(plist.size()>0){
					m.setPic(plist.get(0));
				}
			}
		}
		return list;
	}
	@Override
	public Integer getBoxFlag(Integer companyId) {
		Integer flag=0;
		//信息完整度 i
		Company company = companyDAO.queryCompanyById(companyId);
		CompanyAccount companyAccount = companyAccountDao.queryAccountByCompanyId(companyId);
		Integer i = 0;
		// 联系人：未填写 10
		if (StringUtils.isNotEmpty(companyAccount.getContact())) {
			i = i + 10;
		}
		// 固定电话：未填写 5
		if (StringUtils.isNotEmpty(companyAccount.getTel())) {
			i = i + 5;
		}
		// 传真：未填写 5
		if (StringUtils.isNotEmpty(companyAccount.getFax())) {
			i = i + 5;
		}
		// 手机：未填写 10
		if (StringUtils.isNotEmpty(companyAccount.getMobile())) {
			i = i + 10;
		}
		// 公司名称：未填写 10
		if (StringUtils.isNotEmpty(company.getName())) {
			i = i + 10;
		}
		// 主营行业：未填写 5
		if (StringUtils.isNotEmpty(company.getIndustryCode())) {
			i = i + 5;
		}
		// 公司类型：未选择 5
		if (StringUtils.isNotEmpty(company.getServiceCode())) {
			i = i + 5;
		}
		// 国家/地区：未选择 5
		if (StringUtils.isNotEmpty(company.getAreaCode())) {
			i = i + 5;
		}
		// 地址：未填写 5
		if (StringUtils.isNotEmpty(company.getAddress())) {
			i = i + 5;
		}
		// 邮编：未填写 5
		if (StringUtils.isNotEmpty(company.getAddressZip())) {
			i = i + 5;
		}
		// QQ号码：未填写 5
		if (StringUtils.isNotEmpty(companyAccount.getQq())) {
			i = i + 5;
		}
		// 公司简介：未填写 5
		if (StringUtils.isNotEmpty(company.getIntroduction())) {
			i = i + 5;
		}
		// 主营业务：未填写 5
		if (StringUtils.isNotEmpty(company.getBusiness())) {
			i = i + 5;
		}
		// 企业图片：未上传 10
		List<CompanyUploadFileDO> picList = companyUploadFileService.queryByCompanyId(companyId);
		if (picList.size() > 0) {
			i = i + 10;
		}
		// 供求信息：未发布 10
		Integer countProducts = productsService.countProductsByCompanyId(companyId);
		if (countProducts > 0) {
			i = i + 10;
		}
		if(i>=60){
			if(company.getAreaCode().length()>15){
				if(!"10001000".equals(company.getIndustryCode())&&!"10001001".equals(company.getIndustryCode())&&!"10001007".equals(company.getIndustryCode())){
					flag=2;
				}
			}else if(company.getAreaCode().length()==8&&!"10011000".equals(company.getAreaCode())){
				if(!"10001000".equals(company.getIndustryCode())&&!"10001001".equals(company.getIndustryCode())&&!"10001007".equals(company.getIndustryCode())){
					flag=2;
				}
				
			}else{
				flag=3;
			}
		}else{
			flag=1;
		}
		return flag;
	}
	@Override
	public PageDto<Market> pageSearchOfMarket(String provice, String category, String industry,Integer flag,Integer id, PageDto<Market> page,String keywords,Integer dir) {
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}
		List<Market> list=new ArrayList<Market>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		//所属行业非空
		if(StringUtils.isNotEmpty(industry)){
	        sb.append(" @(industry) ").append(industry);
	    }
		if(StringUtils.isNotEmpty(provice)){
			 sb.append(" @(area) ").append('"'+provice+'"');
		}
		if(StringUtils.isNotEmpty(category)){
			 sb.append(" @(category) ").append(category);
		}
		if(StringUtils.isNotEmpty(keywords)){
			sb.append(" @(name,industry,area,category,business,introduction,address) ").append(keywords);
		}
		try {
			if(StringUtils.isEmpty(provice)&&StringUtils.isEmpty(category)&&id!=null){
				cl.SetFilter("pid", id, true);
			}
			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("check_status", 1, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_EXTENDED2);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			if(flag==1){
				if(dir==1){
					cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "product_num asc");
				}else{
					cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "product_num desc");
				}
			}else{
				if(dir==1){
					cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "company_num asc");
				}else{
					cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "company_num desc");
				}
			}
			SphinxResult res = cl.Query(sb.toString(), "market");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if(res.totalFound>10000){
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					Market market=marketDao.queryMarketById(Integer.valueOf("" + info.docId));
					List<MarketPic> plist=marketPicDao.queryPicByMarketId(market.getId());
					if(plist.size()>0){
						market.setPic(plist.get(0));
					}
					if(market!=null){
						list.add(market);
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
	public Market queryMarketById(Integer id) {
		return marketDao.queryMarketById(id);
	}
	@Override

	public PageDto<Market> queryAllMarket(PageDto<Market> page, Integer type) {
		page.setRecords(marketDao.queryAllMarket(page, null));
		page.setTotalRecords(marketDao.countAllMarket(page, null));
		return page;
	}
	@Override
	public MarketDo queryNumByProvince(String area) {
		MarketDo marketDo=new MarketDo();
		marketDo.setMarketNum(marketDao.countMarketByProvice(area));
		Integer companyNum=marketDao.countCompanyByProvice(area);
		if(companyNum==null){
			companyNum=0;
		}
	    marketDo.setCompanyNum(companyNum);
	    Integer productNum=marketDao.countProductByProvice(area);
	    if(productNum==null){
	    	productNum=0;
	    }
	    marketDo.setProductNum(productNum);
		return marketDo;
	}
	@Override
	public List<Market> queryMarketByProOrCate(String area, String category) {
		return marketDao.queryMarketByProOrCate(area, category);
	}
    @Override
	public PageDto<Market> pageQueryMark(Market market, PageDto<Market> page,Integer hasPic) {
		List<Market> list=marketDao.pageQueryMarket(market, page,hasPic);
		for(Market m : list){
			if(m!=null && m.getCompanyId()!=0){
				CompanyAccount account = companyAccountDao.queryAccountByCompanyId(m.getCompanyId());
				m.setCompanyAccount(account.getAccount());
			}else{
				m.setCompanyAccount("后台添加");
			}
		}
		page.setRecords(list);
		page.setTotalRecords(marketDao.pageQueryMarketCount(market,hasPic));
		return page;
	}

	@Override
	public Integer updateMarket(Market market) {
		return marketDao.updateMarkt(market);
	}

	@Override
	public Integer insertMarket(Market market) {
		
		return marketDao.insertMarket(market);
	}
	@Override
	public Market queryMarketByWords(String words) {
		return marketDao.queryMarketByWords(words);
	}
	@Override
	public Integer sumProductNum() {
		return marketDao.sumProductNum();
	}
	
	@Override
	public Integer insertSubscribe(Integer companyId, Integer type, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer deleteSubscribe(Integer id, Integer companyId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<MarketSubscribe> querySubscribeByCompanyId(Integer companyId, Integer type, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PageDto<MarketSubscribeDto> pageSubscribeByAdmin(MarketSearchDto searchDto,
			PageDto<MarketSubscribeDto> page) {
		List<MarketSubscribe> list = marketSubscribeDao.queryByAdmin(searchDto,page);
		List<MarketSubscribeDto> resultList = new ArrayList<MarketSubscribeDto>();
		for (MarketSubscribe marketSubscribe : list) {
			MarketSubscribeDto dto = new MarketSubscribeDto();
			Company company = companyDAO.queryCompanyById(marketSubscribe.getCompanyId());
			CompanyAccount account = companyAccountDao.queryAccountByCompanyId(marketSubscribe.getCompanyId());
			List<MarketSubscribe> l = marketSubscribeDao.queryByCompanyId(marketSubscribe.getCompanyId(),5);
			String keywordsTags = "";
			String marketTags = "";
			for (MarketSubscribe ms : l) {
				if(ms.getType().intValue()==1){
					//为订阅关键字
					keywordsTags = keywordsTags + ms.getKeys() + ",";
				}
				if (ms.getType().intValue()==2) {
					//为订阅市场
					marketTags = marketTags + ms.getKeys() + ",";
				}
			}
			dto.setSubscribe(marketSubscribe);
			dto.setCompany(company);
			dto.setAccount(account);
			dto.setKeywordsTags(keywordsTags);
			dto.setMarketTags(marketTags);
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(marketSubscribeDao.queryCountByAdmin(searchDto));
		return page;
	}
}