/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-12.
 */
package com.ast.ast1949.service.price.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.price.PriceDataDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.price.PriceDataDAO;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.price.PriceDataService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("priceDataService")
public class PriceDataServiceImpl implements PriceDataService {

	@Autowired
	private PriceDataDAO priceDataDAO;
	@Autowired
	private CompanyDAO companyDAO;

	public Integer deletePriceDataById(Integer id) {
		return priceDataDAO.deletePriceDataById(id);
	}

//	public Integer deletePriceDataByPriceId(Integer id) {
//		return priceDataDAO.countPriceDataByPriceId(id);
//	}
	
//	public Integer updatePriceDataById(PriceDataDO priceData) {
//		return priceDataDAO.updatePriceDataById(priceData);
//	}
	
	public Integer insertPriceData(PriceDataDO priceData) {
		return priceDataDAO.insertPriceData(priceData);
	}

	public List<PriceDataDO> queryPriceDataByPriceId(Integer id, PageDto<PriceDataDO> page) {
		return priceDataDAO.queryPriceDataByPriceId(id,page);
	}

	public Integer countPriceDataByPriceId(Integer id) {
		return priceDataDAO.countPriceDataByPriceId(id);
	}
	
	public ExtResult insert(PriceDataDO priceData,Integer companyId) {
		ExtResult result =new ExtResult();
		
		Assert.notNull(priceData, "the object of priceData must not be null");
		Assert.notNull(priceData.getPriceId(), "the priceId must not be null");
		Assert.notNull(priceData.getCompanyPriceId(), "the companyPriceId must not be null");
		Assert.notNull(priceData.getCompanyId(), "the companyId must not be null");
		priceData.setCompanyName(companyDAO.queryCompanyNameById(companyId));
		Company company = companyDAO.querySimpleCompanyById(companyId);
		priceData.setArea(CategoryFacade.getInstance().getValue(company.getAreaCode()));
		Integer id = 0;
		if(priceData.getId()!=null&&priceData.getId()==0) {
			id = insertPriceData(priceData);
			if(id.intValue()>0) {
				result.setSuccess(true);
			}
		} else {
			//判断是否存在
			PriceDataDO old = new PriceDataDO();
			if(priceData.getPriceId()>0&&priceData.getCompanyPriceId()>0) {
				old = queryPriceDataByPriceIdAndCompanyPriceId(priceData.getPriceId(),priceData.getCompanyPriceId());
			} else {
				old = priceDataDAO.queryPriceDataById(priceData.getId());
			}
			if(old!=null&&old.getId()>0){
				priceData.setId(old.getId());
				id=priceData.getId();
				if(priceDataDAO.updatePriceDataById(priceData)>0) {
					result.setSuccess(true);
				}
			} else {
				id = insertPriceData(priceData);
				if(id.intValue()>0) {
					result.setSuccess(true);
				}
			}
		}
		
		result.setData(id);
		return result;
	}

	public PriceDataDO queryPriceDataByPriceIdAndCompanyPriceId(
			Integer priceId, Integer companyPriceId) {
		Assert.notNull(priceId, "the priceId must not be null");
		Assert.notNull(companyPriceId, "the companyPriceId must not be null");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("priceId", priceId);
		param.put("companyPriceId", companyPriceId);
		
		return priceDataDAO.queryPriceDataByPriceIdAndCompanyPriceId(param);
	}

//	public PriceDataDO queryPriceDataById(Integer id) {
//		return priceDataDAO.queryPriceDataById(id);
//	}

}
