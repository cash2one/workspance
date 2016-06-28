package com.ast.ast1949.service.price.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.MyfavoriteDAO;
import com.ast.ast1949.persist.price.PriceOfferDao;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.price.PriceOfferService;
import com.zz91.util.lang.StringUtils;

@Component("priceOfferService")
public class PriceOfferServiceImpl implements PriceOfferService {
	@Resource
	public PriceOfferDao priceOfferDao;
	@Resource
	public MyfavoriteDAO myfavoriteDAO;
	@Resource
	public CompanyDAO companyDAO;

	@Override
	public Integer insertPriceOffer(PriceOffer offer) {
		return priceOfferDao.insertPriceOffer(offer);
	}

	@Override
	public PageDto<PriceOffer> queryOfferByCompanyId(PageDto<PriceOffer> page, Integer companyId, String keywords) {
		 List<PriceOffer> list=priceOfferDao.queryOfferByCompanyId(page, companyId, keywords);
		 for(PriceOffer offer:list){
			 if(StringUtils.isNotEmpty(offer.getCategory())){
				 offer.setCategoryName(CategoryFacade.getInstance().getValue(offer.getCategory()));
			 }
		 }
		 page.setRecords(list);
		 page.setTotalRecords(priceOfferDao.countOfferByCompanyId(companyId,keywords));
		 return page;
	}

	@Override
	public PriceOffer queryOfferById(Integer id) {
		PriceOffer offer = priceOfferDao.queryOfferById(id);
		offer.setCategoryName(CategoryFacade.getInstance().getValue(offer.getCategory()));
		return offer;
	}

	@Override
	public Integer updateOfferById(PriceOffer offer) {
		return priceOfferDao.updateOfferById(offer);
	}

	@Override
	public void updateDownloadNumById(Integer id, Integer downloadNum) {
		priceOfferDao.updateDownloadNumById(id, downloadNum);
	}

	@Override
	public Integer updateIsDelByid(Integer id, Integer isDel) {
		return priceOfferDao.updateIsDelByid(id, isDel);
	}

	@Override
	public PageDto<PriceOffer> queryOfferByCompanyIdAndType(PageDto<PriceOffer> page, Integer companyId, String favoriteTypeCode,String keywords) {
		List<MyfavoriteDO> myfavorite=myfavoriteDAO.queryNoticeByCondition(page, companyId, favoriteTypeCode, keywords);
		List<PriceOffer> list=new ArrayList<PriceOffer>();
		for(MyfavoriteDO fav:myfavorite){
			PriceOffer offer=priceOfferDao.queryOfferById(fav.getContentId());
			if(StringUtils.isNotEmpty(offer.getCategory())){
				 offer.setCategoryName(CategoryFacade.getInstance().getValue(offer.getCategory()));
			 }
			Company company=companyDAO.queryCompanyById(companyId);
			offer.setCompany(company);
			list.add(offer);
		}
		page.setRecords(list);
		page.setTotalRecords(myfavoriteDAO.countNoticeByCondition(companyId, favoriteTypeCode, keywords));
		return page;
	}

	@Override
	public PageDto<PriceOffer> pageOfferByCondition(PageDto<PriceOffer> page, PriceOffer priceOffer, String from, String to,String menberShip) {
		List<PriceOffer> list = priceOfferDao.listOfferByCondition(page, priceOffer, from, to, menberShip);
		for(PriceOffer offer:list){
			Company company=companyDAO.queryCompanyById(offer.getCompanyId());
			offer.setCompany(company);
			offer.setCategoryName(CategoryFacade.getInstance().getValue(offer.getCategory()));
		}
		page.setRecords(list);
		page.setTotalRecords(priceOfferDao.countOfferByCondition(priceOffer, from, to, menberShip));
		return page;
	}

	@Override
	public Integer updateCheckInfo(Integer id, Integer checkStatus,String checkPerson,String checkReason,Integer isDel) {
		return priceOfferDao.updateCheckInfo(id, checkStatus, checkPerson, checkReason,isDel);
	}

}
