/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.persist.company.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.persist.company.CategoryCompanyPriceDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("categoryCompanyPriceDAO")
public class CategoryCompanyPriceDAOImpl extends SqlMapClientDaoSupport
		implements CategoryCompanyPriceDAO {


	@SuppressWarnings("unchecked")
	public List<CategoryCompanyPriceDO> queryCategoryCompanyPrice() {

		return getSqlMapClientTemplate().queryForList("categoryCompanyPrice.queryCategoryCompanyPrice");
	}

	@SuppressWarnings("unchecked")
	public List<CategoryCompanyPriceDO> queryCategoryCompanyPriceByCode(
			String code) {

		return getSqlMapClientTemplate().queryForList("categoryCompanyPrice.queryCategoryCompanyPriceByCode", code);
	}

	public CategoryCompanyPriceDO queryByCode(String code) {

		return (CategoryCompanyPriceDO) getSqlMapClientTemplate().queryForObject("categoryCompanyPrice.queryByCode", code);
	}

	public int deleteCategoryCompanyPriceByCode(String code) {
        Assert.notNull(code, "code is not null");
		return getSqlMapClientTemplate().delete("categoryCompanyPrice.deleteCategoryCompanyPriceByCode", code);
	}

	public int insertCategoryCompanyPrice(CategoryCompanyPriceDO categories) {
		Assert.notNull(categories, "categories is not null");
		return  Integer.valueOf(getSqlMapClientTemplate().insert("categoryCompanyPrice.insertCategoryCompanyPrice", categories).toString());
	}

	@SuppressWarnings("unchecked")
	public List<CategoryCompanyPriceDO> selectCategoriesByPreCode(String preCode) {
		Assert.notNull(preCode, "preCode is not null");
		return  getSqlMapClientTemplate().queryForList("categoryCompanyPrice.selectCategoriesByPreCode", preCode);
	}

	public String selectMaxCodeByPreCode(String preCode) throws IOException {
		Assert.notNull(preCode, "preCode is not null");
		String s = (String) getSqlMapClientTemplate().queryForObject(
				"categoryCompanyPrice.selectMaxCodeByPreCode", preCode);
		return s;
	}

	public int updateCategoryCompanyPrice(CategoryCompanyPriceDO categoryDO) {
		Assert.notNull(categoryDO, "categoryDO is not null");
		return getSqlMapClientTemplate().update("categoryCompanyPrice.updateCategoryCompanyPrice", categoryDO);
	}

	public CategoryCompanyPriceDO selectCategoryCompanyPriceById(Integer id) {
         Assert.notNull(id, "id is not null");
		return (CategoryCompanyPriceDO) getSqlMapClientTemplate().queryForObject("categoryCompanyPrice.selectCategoryCompanyPriceById", id);
	}

	public CategoryCompanyPriceDO queryCategoryCompanyPriceByLabel(String label) {
		return (CategoryCompanyPriceDO) getSqlMapClientTemplate().queryForObject("categoryCompanyPrice.queryCategoryCompanyPriceByLabel",label);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryCompanyPriceDO> queryAllCompanyPrice() {
		return getSqlMapClientTemplate().queryForList("categoryCompanyPrice.queryAllCompanyPrice");
	}

}
