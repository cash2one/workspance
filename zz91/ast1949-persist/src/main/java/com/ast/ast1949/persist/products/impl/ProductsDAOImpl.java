/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-1 上午09:45:47
 */
package com.ast.ast1949.persist.products.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupportMultipleDataSource;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.util.Assert;
import com.zz91.util.datetime.DateUtil;

/**
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Component("productsDAO")
public class ProductsDAOImpl extends BaseDaoSupportMultipleDataSource implements ProductsDAO {

	private static String sqlPreFix = "products";
	
//	@Resource
//	private BaseDaoSupportMultipleDataSource baseDaoSupportMultipleDataSource;

	final private int DEFAULT_BATCH_SIZE = 20;


	public ProductsDO queryProductsById(Integer id) {
		return (ProductsDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsById"), id);
	}

	public ProductsDO queryProductsWithOutDetailsById(Integer id) {
		return (ProductsDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsWithOutDetailsById"),
				id);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDO> queryProductsOfCompany(Integer companyId,
			String mainCode, PageDto page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("categoryProductsMainCode", mainCode);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsOfCompany"), root);
	}

	public Integer queryProductsOfCompanyCount(Integer companyId,
			String mainCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("categoryProductsMainCode", mainCode);
		return (Integer) getSqlMapClientTemplate()
				.queryForObject(
						addSqlKeyPreFix(sqlPreFix,
								"queryProductsOfCompanyCount"), root);
	}

	@SuppressWarnings("unchecked")
    public List<ProductsDto> queryProductsByCode(Integer companyId,String productsTypeCode , String mainCode, PageDto page,String hide){
	    Map<String, Object> root = new HashMap<String, Object>();
        root.put("companyId", companyId);
        root.put("categoryProductsMainCode", mainCode);
        root.put("productsTypeCode", productsTypeCode);
        root.put("hide", hide);
        root.put("page", page);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsByCode"), root);
	}
	
    public Integer queryProductsByCodeCount(Integer companyId,String productsTypeCode , String mainCode,String hide){
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("companyId", companyId);
        root.put("productsTypeCode", productsTypeCode);
        root.put("categoryProductsMainCode", mainCode);
        root.put("hide", hide);
        return (Integer) getSqlMapClientTemplate()
                .queryForObject(
                        addSqlKeyPreFix(sqlPreFix,
                                "queryProductsByCodeCount"), root);
    }
	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryProductsWithPicByCompany(Integer companyId,
			Integer maxSize) {
		Assert.notNull(companyId, "the companyId can not be null");
		if (maxSize == null) {
			maxSize = 10;
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicByCompany"),
				root);
	}
	@SuppressWarnings("unchecked")
    public List<ProductsDO> queryProductsByIdWithConditon (Integer companyId) {
	    return getSqlMapClientTemplate().queryForList(
                addSqlKeyPreFix(sqlPreFix, "queryProductsByIdWithConditon"),companyId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsWithPicByCompanyForSp(Integer companyId,String kw, Integer sid,PageDto<ProductsDto> page){
		Assert.notNull(companyId, "the companyId can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("page", page);
		root.put("kw", kw);
		root.put("seriesId", sid);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicByCompanyForSp"),
				root);
	}
	
	public Integer queryProductsWithPicByCompanyEsiteCount(Integer companyId,
			String kw, Integer sid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("kw", kw);
		root.put("seriesId", sid);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsWithPicByCompanyEsiteCount"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryProductsWithPicByCompanyEsite(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("kw", kw);
		root.put("seriesId", sid);
		root.put("page", page);
		return getSqlMapClientTemplate()
				.queryForList(
						addSqlKeyPreFix(sqlPreFix,
								"queryProductsWithPicByCompanyEsite"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryProductsByMainCode(String mainCode,
			String typeCode, Integer maxSize) {
		if (maxSize == null || maxSize.intValue() <= 0) {
			maxSize = 10;
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryProductsMainCode", mainCode);
		root.put("productsTypeCode", typeCode);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsByMainCode"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryProductsWithCompanyForInquiry(
			Company company, ProductsDO products, PageDto<ProductsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("products", products);
		root.put("page", page);
		return getSqlMapClientTemplate()
				.queryForList(
						addSqlKeyPreFix(sqlPreFix,"queryProductsWithCompanyForInquiry"), root);
	}

	public Integer queryProductsWithCompanyForInquiryCount(Company company,
			ProductsDO products) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("products", products);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsWithCompanyForInquiryCount"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryNewProductsIntervalDay(Date start, Date end,
			Integer maxSize) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("end", end);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate()
				.queryForList(
						addSqlKeyPreFix(sqlPreFix,
								"queryNewProductsIntervalDay"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryProductsByAdmin(Company company,
			ProductsDO products, Integer[] statusArray,
			PageDto<ProductsDto> page, String from, String to, String selectTime) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("products", products);
		root.put("statusArray", statusArray);
		root.put("page", page);
		root.put("from", from);
		root.put("to", to);
		root.put("selectTime", selectTime);
		try {
			return getSqlMapClient2().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsByAdmin"), root);
		} catch (SQLException e) {
			return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsByAdmin"), root);
		}
	}
	
	@Override
	public ProductsDto queryProductsByAdminId(ProductsDO products) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("products", products);
		return (ProductsDto) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductsByAdminId"), root);
	}

	public Integer queryProductsByAdminCount(Company company,
			ProductsDO products, Integer[] statusArray, String from, String to,
			String selectTime) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("products", products);
		root.put("statusArray", statusArray);
		root.put("from", from);
		root.put("to", to);
		root.put("selectTime", selectTime);
		try {
			return (Integer) getSqlMapClient2().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductsByAdminCount"), root);
		} catch (SQLException e) {
			return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductsByAdminCount"), root);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsByAdminZstExpried(Integer isRecheck,
			PageDto<ProductsDto> page, ProductsDO products) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("isRecheck", isRecheck);
		root.put("page", page);
		root.put("products", products);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsByAdminZstExpried"),
				root);
	}

	@Override
	public Integer queryProductsByAdminZstExpriedCount(Integer isRecheck,
			ProductsDO products) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("isRecheck", isRecheck);
		root.put("products", products);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsByAdminZstExpriedCount"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDO> queryProductsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,String title,
			PageDto<ProductsDO> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("isExpired", isExpired);
		root.put("isPause", isPause);
		root.put("isPostFromInquiry", isPostFromInquiry);
		root.put("groupId", groupId);
		root.put("title", title);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsOfCompanyByStatus"),
				root);
	}
	@SuppressWarnings("unchecked")
	public List<ProductsDO> productsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,String title,String isHide,
			PageDto<ProductsDO> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("isExpired", isExpired);
		root.put("isPause", isPause);
		root.put("isPostFromInquiry", isPostFromInquiry);
		root.put("groupId", groupId);
		root.put("title", title);
		root.put("isHide", isHide);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "productsOfCompanyByStatus"),
				root);
	}
	
	public Integer productsOfCompanyByStatusCount(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,String title,String isHide) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("isExpired", isExpired);
		root.put("isPause", isPause);
		root.put("isPostFromInquiry", isPostFromInquiry);
		root.put("groupId", groupId);
		root.put("title", title);
		root.put("isHide", isHide);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,
						"productsOfCompanyByStatusCount"), root);
	}
	
	public Integer queryProductsOfCompanyByStatusCount(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,String title) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("account", account);
		root.put("checkStatus", checkStatus);
		root.put("isExpired", isExpired);
		root.put("isPause", isPause);
		root.put("isPostFromInquiry", isPostFromInquiry);
		root.put("groupId", groupId);
		root.put("title", title);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsOfCompanyByStatusCount"), root);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDO> queryNewestProducts(String mainCode,
			String typeCode, Integer maxSize) {
		if (maxSize == null) {
			maxSize = 10;
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryProductsMainCode", mainCode);
		root.put("productsTypeCode", typeCode);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryNewestProducts"), root);
	}

	public Integer countProductsByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countProductsByCompanyId"),
				companyId);
	}

	public Integer countUserAddProductsToday(Integer companyId) {
		Assert.notNull(companyId, "companyId is not null");
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countUserAddProductsToday"),
				companyId);
	}

	public Date queryMaxRefreshTimeByCompanyId(Integer companyId) {
		return (Date) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryMaxRefreshTimeByCompanyId"),
				companyId);
	}

	public Integer insertProduct(ProductsDO productDO) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(sqlPreFix, "insertProduct"), productDO);
	}

	public Integer batchDeleteProductsByIds(Integer[] entities,Integer companyId) {
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					Map<String, Object> map = new HashMap<String, Object>(); 
					map.put("id", entities[i]);
					map.put("companyId", companyId);
					impacted += getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "deleteProductById"),map);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete products failed.", e);
		}
		return impacted;
	}

	public Integer countProuductsByTitleAndAccount(String title,
			String productsTypeCode, String account,String from,String to) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("title", title);
		root.put("account", account);
		root.put("productsTypeCode", productsTypeCode);
		root.put("from", from);
		root.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countProuductsByTitleAndAccount"),
				root);
	}

	public Integer updateProductsCheckStatusByAdmin(String checkStatus,
			String unpassReason, String checkPerson, Integer productId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("checkStatus", checkStatus);
		root.put("unpassReason", unpassReason);
		root.put("checkPerson", checkPerson);
		root.put("productId", productId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateProductsCheckStatusByAdmin"),
				root);
	}

	public Integer updateProductsUncheckedCheckStatusByAdmin(
			String checkStatus, String unpassReason, String checkPerson,
			Integer productId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("uncheckedCheckStatus", checkStatus);
		root.put("unpassReason", unpassReason);
		root.put("checkPerson", checkPerson);
		root.put("productId", productId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix,
						"updateProductsUncheckedCheckStatusByAdmin"), root);
	}

	public Integer updateProductsIsShowInPrice(Integer id, String flag) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("isShowInPrice", flag);
		return getSqlMapClientTemplate()
				.update(
						addSqlKeyPreFix(sqlPreFix,
								"updateProductsIsShowInPrice"), root);
	}

	public Integer updateProductsIsPause(String isPause, Integer[] ids) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("isPause", isPause);
		root.put("ids", ids);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateProductsIsPause"), root);
	}
	
	public Integer updateProductsIsPauseByAdmin(String isPause, Integer id) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("isPause", isPause);
		root.put("id", id);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateProductsIsPauseByAdmin"), root);
	}

	public Integer updateProductsRefreshTime(Date refreshTime,
			Integer productsId, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("refreshTime", refreshTime);
		root.put("productsId", productsId);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateProductsRefreshTime"), root);
	}

	public Integer updateProductsRefreshTimeAndExpireTime(Integer productsId,
			Integer companyId,String checkStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("productsId", productsId);
		root.put("companyId", companyId);
		root.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix,
						"updateProductsRefreshTimeAndExpireTime"), root);
	}
	
	
	public Integer updateRefreshTimeOrIsPause(Integer productsId,
			Integer companyId,String isPause) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("productsId", productsId);
		root.put("companyId", companyId);
		root.put("isPause", isPause);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix,
						"updateRefreshTimeOrIsPause"), root);
	}
	
	public Integer updateProductsByAdmin(ProductsDO productsDO) {
		Assert.notNull(productsDO.getId(), "The id can not be null");
		return getSqlMapClientTemplate()
				.update(addSqlKeyPreFix(sqlPreFix, "updateProductsByAdmin"),
						productsDO);
	}

	public Integer updateProductsByAdminCheck(ProductsDO productsDO) {
		Assert.notNull(productsDO.getId(), "The id can not be null");
		return getSqlMapClientTemplate()
				.update(addSqlKeyPreFix(sqlPreFix, "updateProductsByAdminCheck"),
						productsDO);
	}
	
	public Integer updateProductsByCompany(ProductsDO products) {

		return getSqlMapClientTemplate()
				.update(addSqlKeyPreFix(sqlPreFix, "updateProductsByCompany"),
						products);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> queryProductsIdsOfCompany(Integer companyId,
			String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("categoryCode", categoryCode);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsIdsOfCompany"), root);
	}

	@Override
	public ProductsDto queryProductsWithPicById(Integer id) {
		return (ProductsDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicById"), id);
	}

	@Override
	public ProductsDto queryProductsWithPicAndCompany(Integer id) {
		return (ProductsDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicAndCompany"),
				id);
	}

	@Override
	public String queryTitleById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryTitleById"), id);
	}

	@Override
	public Integer queryProductsCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
			String productCategory, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productTypeCode", productTypeCode);
		map.put("productCategory", productCategory);
		map.put("size", size);
		try {
			map.put("date", DateUtil.getDateAfterDays(DateUtil.getDate(
					new Date(), "yyyy-MM-dd"), -7));
		} catch (ParseException e) {
			return null;
		}
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryNewestVipProducts"), map);
	}

	@Override
	public ProductsDO queryProductsByCid(Integer cid) {
		return (ProductsDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsByCid"), cid);
	}
	
	@Override
	public ProductsDO queryProductsByCidForLatest(Integer cid,ProductsDO products) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("products", products);
		return (ProductsDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryProductsByCidForLatest"), map);
	}

	@Override
	public Date queryLastGmtCreateTimeByCId(Integer cid) {
		return (Date) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryLastGmtCreateTimeByCId"), cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		map.put("products", products);
		map.put("page", page);
		map.put("industryCode", industryCode);
		map.put("areaCode", areaCode);
		map.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsWithCompany"), map);
	}

	@Override
	public Integer countQueryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		map.put("products", products);
		map.put("page", page);
		map.put("industryCode", industryCode);
		map.put("areaCode", areaCode);
		map.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "countQueryProductsWithCompany"),
				map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsByAreaCode4Map(String mainCode,
			String title, String typeCode, String areaCode, Integer maxSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryProductsMainCode", mainCode);
		map.put("title", title);
		map.put("productsTypeCode", typeCode);
		map.put("areaCode", areaCode);
		map.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix, "queryProductsByAreaCode4Map"), map);

	}

	@Override
	public Date queryNowTimeOfDatebase() {
		return (Date) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix, "queryNowTime"));
	}

	@Override
	public Integer updateUncheckByIdForMyrc(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix, "updateUncheckByIdForMyrc"), id);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsDto> queryProductsWithPicByCompanyEsiteWithDetails(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("kw", kw);
		root.put("seriesId", sid);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsWithPicByCompanyEsiteWithDetails"), root);

	}

	@Override
	public ProductsDto queryProductsWithPicByCidAndTypeCode(Integer companyId,
			String productsTypeCode, Integer maxSize) {
		Assert.notNull(companyId, "the companyId can not be null");
		if (maxSize == null) {
			maxSize = 10;
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("productsTypeCode", productsTypeCode);
		root.put("maxSize", maxSize);
		return (ProductsDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsWithPicByCidAndTypeCode"), root);
	}

	@Override
	public Integer countQueryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductsDto> queryProductsByAreaCode(String areaCode,
			Integer maxSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductsDto> queryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateGaoCheckStatusByAdmin(String checkStatus,
			String checkPerson, Integer productId,String unpassReason) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("uncheckedCheckStatus", checkStatus);
		root.put("checkPerson", checkPerson);
		root.put("unpassReason", unpassReason);
		root.put("productId", productId);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateGaoCheckStatusByAdmin"),root);
	}

	@Override
	public ProductsDto queryProductsByCidAndTypeCodeAndMainCode(
			Integer companyId, String mainCode, String productsTypeCode,
			Integer maxSize) {

		Assert.notNull(companyId, "the companyId can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("mainCode", mainCode);
		root.put("productsTypeCode", productsTypeCode);
		root.put("maxSize", maxSize);
		return (ProductsDto) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix,"queryProductsByCidAndTypeCode"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryVipProductsForMyrc(String title, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryNewestVipProductsForMyrc"),map);
	}

	@Override
	public Integer updateProductsUncheckedCheckStatusForDelByAdmin(
			String checkStatus, String unpassReason, String checkPerson,
			Integer productId, String isDel) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("uncheckedCheckStatus", checkStatus);
		root.put("checkPerson", checkPerson);
		root.put("unpassReason", unpassReason);
		root.put("productId", productId);
		root.put("isDel", isDel);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateProductsUncheckedCheckStatusForDelByAdmin"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryProductsByTypeOfCompany(String companyId,
			String productTypeCode, PageDto page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("productTypeCode", productTypeCode);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix,
						"queryProductsByCompany"), root);
	}

	@Override
	public Integer queryProductsByTypeOfCompany(String companyId,
			String productTypeCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("productTypeCode", productTypeCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductsByCompanyCount"),root);
	}

	@Override
	public Integer updateProductsIsDelByAdmin(Integer productId, String status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productId", productId);
		map.put("status", status);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateProductsIsDelByAdmin"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryProductForSpot(ProductsDto product,PageDto<ProductsDto> page) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("products", product.getProducts());
		map.put("dto", product);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductForSpot"), map);
	}

	@Override
	public Integer queryCountProductForSpot(ProductsDto product) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("products", product.getProducts());
		map.put("dto", product);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryCountProductForSpot"),map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryProductsForSpotByCondition(ProductsDto dto,Integer size) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dto", dto);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsForSpotByCondition"),map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductForSpotByAdmin(Company company,ProductsDO products,PageDto<ProductsDto> page,Integer min,Integer max,String isStatus) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("products", products);
		map.put("company", company);
		map.put("min", min);
		map.put("max", max);
		map.put("isStatus", isStatus);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductForSpotByAdmin"), map);
	}

	@Override
	public Integer queryCountProductForSpotByAdmin(Company company,ProductsDO products,Integer min,Integer max,String isStatus) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("products", products);
		map.put("company", company);
		map.put("min", min);
		map.put("max", max);
		map.put("isStatus", isStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryCountProductForSpotByAdmin"),map);
	}

	@Override
	public Integer queryTodayCopperProductsCount(String code, String from,
			String to) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("from", from);
		root.put("to", to);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryTodayCopperProductsCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryProductsForPic(ProductsDO products,Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("products", products);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsForPic"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductForexportByAdmin(Company company,
			ProductsDO products, PageDto<ProductsDto> page, Integer min,
			Integer max, String isStatus) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("products", products);
		map.put("company", company);
		map.put("max", max);
		map.put("isStatus", isStatus);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductForexportByAdmin"), map);
	}
	
	public Integer queryProductsWithPicByCompanyForSpCount(Integer companyId,String kw, Integer sid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("kw", kw);
		root.put("seriesId", sid);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicByCompanyForSpCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryPassProductsByDate(String from, String to) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("from", from);
		root.put("to", to);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryPassProductsByDate"), root);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsWithPicAndTypeCode(
			String productsTypeCode, Integer maxSize) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("productsTypeCode", productsTypeCode);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicAndTypeCode"),root);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryProductsWithPicAndTypeCodeAndVip(
			String productsTypeCode, Integer maxSize) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("productsTypeCode", productsTypeCode);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsWithPicAndTypeCodeAndVip"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDto> queryNewProductsBysize(Integer size) {
		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryNewProductsBysize"),size);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> productsCompany(PageDto<CompanyDto> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "productsCompany"),root);
	}
	
	@Override
	public Integer productsCompanyCount(){
	     return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "productsCompanyCount"));
	}

	@Override
	public Integer updateProductsRefreshTimeExpireTime(Integer productsId,
			Integer companyId,String isPause) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("productsId", productsId);
		root.put("companyId", companyId);
		root.put("isPause", isPause);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(sqlPreFix,
						"updateProductsRefreshTimeExpireTime"), root);
	}

	@Override
	public Integer countExpireProductByCompanyId(ProductsDO products) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countExpireProductByCompanyId"), products);
	}

	@Override
	public Integer countValidProductByCompanyId(ProductsDO products) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countValidProductByCompanyId"), products);
	}
	public Integer countProductByRefreshTime(String refershFrom, String refershTo) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("refershFrom", refershFrom);
		root.put("refershTo", refershTo);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countProductByRefreshTime"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> productByRefreshTime(String refreshFrom, String refreshTo) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("refreshFrom", refreshFrom);
		root.put("refreshTo", refreshTo);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "productByRefreshTime"), root);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryProductsByType(String productsTypeCode,Integer size) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productsTypeCode", productsTypeCode);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsByType"), map);
	}

	@Override
	public Integer updateNewRefreshTimeById(ProductsDO productsDO) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateNewRefreshTimeById"), productsDO);
	}

	@Override
	public Integer hasTitle(String title) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "hasTitle"), title);
	}

	@Override
	public Integer countProductBySoucre(Date from, Date to,String sourceTypeCode) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("from", from);
		map.put("to", to);
		map.put("sourceTypeCode", sourceTypeCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "countProductBySoucre"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryNewProducts(Integer cid) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryNewProducts"), cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsDO> queryProductsByTypeFresh(String productsTypeCode,
			Integer size) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productsTypeCode", productsTypeCode);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryProductsByType"), map);
	}
	
}
