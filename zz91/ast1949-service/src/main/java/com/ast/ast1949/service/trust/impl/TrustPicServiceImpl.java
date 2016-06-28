/**
 * @author shiqp
 * @date 2015-07-25
 */
package com.ast.ast1949.service.trust.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustPic;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.trust.TrustPicDao;
import com.ast.ast1949.service.trust.TrustPicService;

@Component("trustPicService")
public class TrustPicServiceImpl implements TrustPicService {
	@Resource
	private TrustPicDao trustPicDao;

	@Override
	public Integer createTradePic(TrustPic trustPic) {
		if(trustPic.getPicId()==null){
			trustPic.setPicId(0);
		}
		return trustPicDao.createTradePic(trustPic);
	}

	@Override
	public Integer updateTradeInfo(TrustPic trustPic) {
		return trustPicDao.updateTradeInfo(trustPic);
	}

	@Override
	public PageDto<TrustPic> pageTradePicInfo(PageDto<TrustPic> page, Integer tradeId) {
		page.setDir("desc");
		page.setSort("is_default");
		page.setRecords(trustPicDao.querypicList(page, tradeId));
		page.setTotalRecords(trustPicDao.countpicList(tradeId));
		return page;
	}

	@Override
	public Integer updateTradeIdByPicAddress(Integer tradeId, String picAddress) {
		return trustPicDao.updateTradeIdByPicAddress(tradeId, picAddress);
	}

	@Override
	public TrustPic queryOnePic(Integer tradeId) {
		return trustPicDao.queryOnePic(tradeId);
	}

	@Override
	public TrustPic queryById(Integer id) {
		if (id==null) {
			return null;
		}
		return trustPicDao.queryById(id);
	}
}
