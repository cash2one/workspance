/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-29
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.MyfavoriteDTO;
import com.ast.ast1949.persist.company.MyfavoriteDAO;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("myfavoriteService")
public class MyfavoriteServiceImpl implements MyfavoriteService {

	@Autowired
	private MyfavoriteDAO myfavoriteDAO;

	public Integer bathDeleteMyfavoriteById(int entities[]) {
		Assert.notNull(entities, "entities is not null");
		return myfavoriteDAO.bathDeleteMyfavoriteById(entities);
	}

	public Integer insertMyfavorite(MyfavoriteDO myfavoriteDO, ProductsDO productsDO) {
		Assert.notNull(myfavoriteDO, "myfavoriteDO is not null");
		Assert.notNull(productsDO, "productsDO is not null");
		if (productsDO != null) {
			myfavoriteDO.setContentId(productsDO.getId());
			if (productsDO.getProductsTypeCode()!=null) {
				if (productsDO.getProductsTypeCode().equals("10331000")) {
					myfavoriteDO.setFavoriteTypeCode("10091000");
				}else if (productsDO.getProductsTypeCode().equals("10331001")) {
					myfavoriteDO.setFavoriteTypeCode("10091001");
				} 
			}
			
		}
		return myfavoriteDAO.insertMyfavorite(myfavoriteDO);
	}

	public List<MyfavoriteDTO> queryMyfavoriteByCondition(MyfavoriteDTO myfavoriteDTO) {
		Assert.notNull(myfavoriteDTO, "myfavoriteDTO is not null");
		return myfavoriteDAO.queryMyfavoriteByCondition(myfavoriteDTO);
	}

	public MyfavoriteDO queryMyfavoriteByMap(Integer contentId, Integer companyId) {
		Assert.notNull(contentId, "contentId is not null");
		Assert.notNull(companyId, "companyId is not null");
		
		return myfavoriteDAO.queryMyfavoriteByMap(contentId, companyId);
	}

	public Integer queryMyfavoriteCountByCondition(MyfavoriteDTO myfavoriteDTO) {
        Assert.notNull(myfavoriteDTO, "myfavoriteDTO is not");
		return myfavoriteDAO.queryMyfavoriteCountByCondition(myfavoriteDTO);
	}


	@Override
	public Integer insertMyCollect(MyfavoriteDO myfavoriteDO) {
		return myfavoriteDAO.insertMyfavorite(myfavoriteDO);
	}

	@Override
	public Boolean isExist(Integer companyId,Integer contentId, String favoriteTypeCode) {
		Assert.notNull(companyId, "companyId can not be null");
		Integer i = myfavoriteDAO.isExist(companyId,contentId,favoriteTypeCode);
		if(i>=1){
			return true;
		}
		return false;
	}


	@Override
	public PageDto<MyfavoriteDO> pageMyCollect(PageDto<MyfavoriteDO> page,String keywrods,
			Integer companyId, String favoriteTypeCode, Integer theday) {
		page.setTotalRecords(myfavoriteDAO.pageMyCollectCount(keywrods, companyId, favoriteTypeCode, theday));
		page.setRecords(myfavoriteDAO.pageMyCollect(page, keywrods, companyId, favoriteTypeCode, theday));
		return page;
	}
	
	@Override
	public PageDto<MyfavoriteDO> pageMyCollectForMyhuzhu(PageDto<MyfavoriteDO> page,String keywrods,Integer companyId, String favoriteTypeCode, Integer theday){
		page.setTotalRecords(myfavoriteDAO.queryMyCollectForMyhuzhuCount(keywrods, companyId, favoriteTypeCode, theday));
		page.setRecords(myfavoriteDAO.queryMyCollectForMyhuzhu(page, keywrods, companyId, favoriteTypeCode, theday));
		return page;
	}

	@Override
	public Integer deleteMyCollect(Integer compnyId, Integer id) {
		
		return myfavoriteDAO.deleteMyCollect(compnyId, id);
	}

	@Override
	public Integer countByCodeAndContentId(String favoriteTypeCode,
			Integer contentId) {
		Integer i = myfavoriteDAO.countByCodeAndContentId(favoriteTypeCode,contentId);
		if(i!=null){
			return i;
		}
		return 0;
	}

	@Override
	public Integer countByCompanyId(Integer companyId) {
		if (companyId==null) {
			return 0;
		}
		return myfavoriteDAO.countByCompanyId(companyId);
	}

	@Override
	public Integer insertMyCollectForNewhuzhuz(MyfavoriteDO favorite) {
		//该问题或帖子或废料学院被收藏过没有
		Integer i=myfavoriteDAO.isExist(favorite.getCompanyId(), favorite.getContentId(), favorite.getFavoriteTypeCode());
		if(i==0){
			//未收藏过
			return myfavoriteDAO.insertMyfavorite(favorite);
		}else{
			//收藏过
			return 0;
		}
	}

	@Override
	public Integer countNoticeByCondition(Integer companyId, String favoriteTypeCode,String keywords) {
		return myfavoriteDAO.countNoticeByCondition(companyId, favoriteTypeCode,keywords);
	}

	@Override
	public Integer deleteCollection(Integer companyId, String favoriteTypeCode, Integer contentId) {
		return myfavoriteDAO.deleteCollection(companyId, favoriteTypeCode, contentId);
	}

	@Override
	public List<MyfavoriteDO> queryYuanliaoCollectList(Integer companyId, String favoriteTypeCode) {
		return myfavoriteDAO.queryYuanliaoCollectList(companyId, favoriteTypeCode);
	}

}
