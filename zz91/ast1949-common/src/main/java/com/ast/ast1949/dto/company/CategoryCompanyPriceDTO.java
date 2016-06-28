/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.CompanyPriceDO;

/**
 * @author yuyonghui
 *
 */
public class CategoryCompanyPriceDTO {
		private CategoryCompanyPriceDO categoryCompanyPrice;
		private CompanyPriceDO companyPrice;
		/**
		 * @return the categoryCompanyPrice
		 */
		public CategoryCompanyPriceDO getCategoryCompanyPrice() {
			return categoryCompanyPrice;
		}
		/**
		 * @param categoryCompanyPrice the categoryCompanyPrice to set
		 */
		public void setCategoryCompanyPrice(CategoryCompanyPriceDO categoryCompanyPrice) {
			this.categoryCompanyPrice = categoryCompanyPrice;
		}
		/**
		 * @return the companyPrice
		 */
		public CompanyPriceDO getCompanyPrice() {
			return companyPrice;
		}
		/**
		 * @param companyPrice the companyPrice to set
		 */
		public void setCompanyPrice(CompanyPriceDO companyPrice) {
			this.companyPrice = companyPrice;
		}
		
		
	
}
