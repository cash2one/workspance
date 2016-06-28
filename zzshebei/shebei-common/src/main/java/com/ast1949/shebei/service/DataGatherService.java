/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 上午09:10:59
 */
package com.ast1949.shebei.service;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.ast1949.shebei.dto.data.CompanyMakeMap;
import com.ast1949.shebei.dto.data.NewsMakeMap;
import com.ast1949.shebei.dto.data.ProductsMakeMap;

public interface DataGatherService {
	
	/**
	 * 创建一条资讯记录
	 * @param newsMap
	 * @param row
	 * @param seed
	 * @return
	 */
	public long createNews(NewsMakeMap newsMap, HSSFRow row, long seed);

	/**
	 * 查询资讯信息最大展示时间
	 * @return
	 */
	public Date queryNewsMaxGmtShow();

	/**查询供求信息最大展示时间
	 * @return
	 */
	public Date queryProductsMaxGmtShow();

	/**
	 * 查询公司信息最大展示时间
	 * @return
	 */
	public Date queryCompanyMaxGetShow();

	/**
	 * 创建一条产品信息记录
	 * @param productsMap
	 * @param cid 
	 * @param row
	 * @param seed
	 * @return
	 */
	public long createProducts(ProductsMakeMap productsMap, Integer cid, HSSFRow row,
			long seed);

	/**
	 * 创建一条公司信息
	 * @param compMap
	 * @param account 
	 * @param row
	 * @param seed
	 * @return
	 */
	public long createCompanys(CompanyMakeMap compMap, String account, HSSFRow row, long seed);
}
