/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-24 下午04:36:15
 */
package com.ast.ast1949.service.products;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.KeywordSearchDto;
import com.ast.ast1949.dto.products.ProductsDto;

/**
 * 供求信息服务接口
 * 
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
public interface ProductsService {

	final static String PRODUCTS_TYPE_OFFER = "10331000"; // 供应
	final static String PRODUCTS_TYPE_BUY = "10331001"; // 求购
	final static String PRODUCTS_TYPE_COOPERATION = "10331002"; // 合作

	final static String GOOD_TYPE_CODE_SPOT = "10361001"; // 现货
	final static String GOOD_TYPE_CODE_FUTURES = "10361000"; // 期货

	final static String CHECK_PASS = "1";
	final static String CHECK_WAIT = "0";
	final static String CHECK_FAILD = "2";

	public ProductsDto queryProductsDetailsById(Integer id);

	public ProductsDO queryProductsById(Integer id);

	public ProductsDO queryProductsWithOutDetailsById(Integer id);

	/**
	 * 查找同一公司的供求信息（正常状态），前台使用
	 */
	public List<ProductsDto> queryProductsOfCompany(Integer companyId,
			Integer maxSize);

	public List<ProductsDO> queryProductsByIdWithConditon(Integer companyId);

	public List<ProductsDto> queryProductsByMainCode(String mainCode,
			String typeCode, Integer maxSize);

	public PageDto<ProductsDto> pageProductsWithCompanyForInquiry(
			Company company, ProductsDO products, PageDto<ProductsDto> page);

	public List<ProductsDto> queryNewProductsOnWeek(Integer maxSize);

	public PageDto<ProductsDto> pageProductsByAdmin(Company company,
			ProductsDO products, String statusArray, PageDto<ProductsDto> page,
			String from, String to, String selectTime) throws ParseException;

	/**
	 * 搜索所有过期高会的供求信息
	 * 
	 * @param pageDTO
	 * @return
	 */
	public PageDto<ProductsDto> pageProductsByAdminZstExpried(
			Integer isRecheck, PageDto<ProductsDto> page, ProductsDO products);

	public PageDto<ProductsDO> pageProductsOfCompany(Integer companyId,
			PageDto<ProductsDO> page);

	public PageDto<ProductsDto> pageProductsByCode(Integer companyId,
			String productsTypeCode, PageDto<ProductsDto> page, String hide);

	public PageDto<ProductsDO> pageProductsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,
			String title, PageDto<ProductsDO> page);

	/**
	 * 搜索出该公司的所有供求
	 * 
	 * @param companyId
	 * @param account
	 * @param checkStatus
	 * @param isExpired
	 * @param isPause
	 * @param isPostFromInquiry
	 * @param groupId
	 * @param title
	 * @param isHide
	 *            改供求是否隐藏 1:表示隐藏
	 * @return
	 */
	public PageDto<ProductsDO> productsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,
			String title, String isHide, PageDto<ProductsDO> page);

	public Map<String, Integer> countProductsOfCompanyByStatus(
			Integer companyId, String account, String isPostFromInquiry,
			Integer initCount);

	public List<ProductsDO> queryNewestProducts(String mainCode,
			String typeCode, Integer maxSize);

	public Integer countProductsByCompanyId(Integer companyid);

	public PageDto<ProductsDto> pageProductsWithPicByCompanyEsite(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page);

	public Boolean queryUserIsAddProducts(Integer id, String membershipCode);

	public Date queryMaxRefreshTimeByCompanyId(Integer companyId);

	public Integer publishProductsByCompany(ProductsDO products,
			String membershipCode, String areaCode);

	public Integer publishProductsFromInquiry(ProductsDO products);

	public Integer batchDeleteProductsByIds(Integer[] entities,
			Integer companyId);

	public Integer insertProductsPicRelation(Integer productId, Integer[] picIds);

	public boolean isProductsAlreadyExists(String title,
			String productsTypeCode, String account);

	public Integer updateProductsCheckStatusByAdmin(String checkStatus,
			String unpassReason, String checkPerson, Integer productId);

	public Integer updateProductsUncheckedCheckStatusByAdmin(
			String checkStatus, String unpassReason, String checkPerson,
			Integer productId);

	public Integer updateProductsIsShowInPrice(Integer id, String flag);

	public Integer updateProductsIsPause(String isPause, Integer[] ids);

	public Integer updateProductsIsPauseByAdmin(String isPause, Integer id);

	public Integer refreshProducts(Integer productsId, Integer companyId,
			String membershipCode) throws ParseException;

	/**
	 * 更改供求刷新时间，如果该供求是已隐藏怎修改is_pause=0
	 * 
	 * @param productsId
	 * @return
	 */
	public Integer updateIsPauseAndrefreshTimeById(Integer productsId);

	public Integer updateProductByAdmin(ProductsDO productsDO);

	public Integer updateProductByAdminCheck(ProductsDO productsDO);

	public Integer updateProductsByCompany(ProductsDO products,
			String membershipCode);

	public List<Integer> queryProductsIdsOfCompany(Integer companyId,
			String categoryCode);

	/**
	 * 
	 * @param product
	 *            产品其他属性
	 * @param areaCode
	 *            公司地区code
	 * @param havePic
	 *            是否要图片供求
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageProductsBySearchEngine(ProductsDO product,
			String areaCode, Boolean havePic, PageDto<ProductsDto> page);

	/**
	 * 商铺服务供求分页page
	 * 
	 * @param product
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageSPProductsBySearchEngine(
			ProductsDO product, PageDto<ProductsDto> page);

	/**
	 * 搜索最新高会供求
	 * 
	 * @param productTypeCode
	 *            供/求 类别
	 * @param size
	 * @return
	 */
	public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
			String productCategory, Integer size);

	/**
	 * 搜索最新高会供求<br/>
	 * 会 对公司去掉重复
	 * 
	 * @param productsTypeCode
	 *            供/求 类别
	 * @param size
	 * @return
	 */
	public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
			Integer size);

	/**
	 * 主要用于Seo
	 * 
	 * @param cid
	 * @return
	 */
	public ProductsDO queryProductsByCid(Integer cid);

	/**
	 * 检索公司最新供求
	 * 
	 * @param cid
	 * @param products
	 *            供求属性
	 */
	public ProductsDO queryProductsByCidForLatest(Integer cid,
			ProductsDO products);

	/****************
	 * myrc 发布报价时，获取最后记录的时间
	 * 
	 * @param cid
	 * @return
	 */
	public boolean queryLastGmtCreateTimeByCId(Integer cid);

	// 优质客户推荐
	public PageDto<ProductsDto> pageProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords);

	/***********
	 * 按照指定地区查询 出对应的供求信息 用在再生地图
	 * 
	 * @param mainCode
	 *            主类别
	 * @param typeCode
	 *            供求类别
	 * @param areaCode
	 *            地区编号
	 * @param maxSize
	 *            返回的记录条数
	 * @return
	 */
	public List<ProductsDto> queryProductsByAreaCode4Map(String mainCode,
			String title, String typeCode, String areaCode, Integer maxSize);

	// 修改图片或者上传图片的时候审核状态为未审核
	public Integer updateUncheckByIdForMyrc(Integer id);

	/*****
	 * esite 带有详细信息的 供求查询
	 * 
	 * @param companyId
	 * @param kw
	 * @param sid
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageProductsWithPicByCompanyEsiteWithDetails(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page);

	public Integer updateGaoCheckStatusByAdmin(String checkStatus,
			String checkPerson, Integer productId, String unpassReason);

	/**
	 * 废金属首页使用的 热门供应 搜索 最近登录的客户的主营业务为废金属的客户，再检索客户的供求一条
	 */
	public List<ProductsDto> queryHotProducts(String mainCode,
			String productsTypeCode, Integer maxSize);

	/**
	 * 生意管家首页最最新供求
	 */

	public List<ProductsDto> queryVipProductsForMyrc(String title, Integer size);

	public Integer updateProductsCheckStatusForDelByAdmin(String checkStatus,
			String unpassReason, String checkPerson, Integer productId,
			String isDel);

	/***
	 * trade 门市部 化 ：公司供求信息 分类查询
	 * 
	 * @param companyId
	 * @param productTypeCode
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDO> pageProductsByTyepOfCompany(String companyId,
			String productTypeCode, PageDto<ProductsDO> page);

	/**
	 * 删除 或者 恢复 供求
	 */
	public Integer updateProductsIsDelByAdmin(Integer productId, String status);

	/**
	 * 搜索现货供求 page 前台用
	 * 
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageProductsForSpot(ProductsDO product,
			PageDto<ProductsDto> page, String isTe, String isHot, String isYou);

	/**
	 * 搜索 特 热 优 类别的现货供求
	 * 
	 * @param isTe
	 * @param isHot
	 * @param isYou
	 * @param size
	 * @return
	 */
	public List<ProductsDto> queryProductsForSpotByCondition(String isTe,
			String isHot, String isYou, Integer size);

	/**
	 * 搜索现货供求 page 后台用
	 * 
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageProductsForSpotByAdmin(Company company,
			ProductsDO products, PageDto<ProductsDto> page, Integer min,
			Integer max, String isStatus);

	// 根据类别code查询今日的供求数量
	public Integer queryTodayCopperProductsCount(String code, Date time);

	public List<ProductsDto> queryProductsForPic(ProductsDO products,
			Integer size);

	public PageDto<ProductsDto> queryProductForexportByAdmin(Company company,
			ProductsDO products, PageDto<ProductsDto> page, Integer min,
			Integer max, String isStatus);

	public List<ProductsDto> buildLIst();

	public List<ProductsDto> querypicByKeyWord(ProductsDO productsDO,
			boolean havePic, Integer pagesize);

	public PageDto<ProductsDto> pageLHProductsBySearchEngine(
			ProductsDO product, String areaCode, Boolean havePic,
			PageDto<ProductsDto> page);

	public List<ProductsDO> queryProductsByTyepOfCompany(String companyId,
			String productTypeCode, PageDto<ProductsDO> page);

	// 分页
	public PageDto<ProductsDto> pageProductsWithPicByCompanyForSp(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page);

	public List<ProductsDO> queryPassProductsByDate(String date);

	// 后台标签为空，自动获取组装获取后台标签
	public String getTagAdmin(ProductsDto productsDto);

	/**
	 * 自动审核检验供求标题是否重复 一般存在供求大于1条的情况。判定为重复
	 * 
	 * @param title
	 * @param productsTypeCode
	 * @param account
	 * @param from
	 * @param to
	 * @return false 重复存在不能发，true 重复不存在，可以发布
	 */
	public Boolean countProuductsByTitleAndAccount(String title,
			String productsTypeCode, String account);

	/**
	 * 发布供求过程中 检验标题 是否重复
	 * 
	 * @param title
	 * @param account
	 * @return
	 */
	public Boolean validateTitleAndAccount(String title, String account);

	public List<ProductsDto> queryProductsWithPicAndTypeCode(
			String productsTypeCode, Integer maxSize);

	public List<ProductsDto> queryProductsWithPicAndTypeCodeAndVip(
			String productsTypeCode, Integer maxSize);

	/**
	 * 搜索含有图片的供求信息
	 * */

	public List<ProductsDto> queryProductbyHavePic(Integer size);

	/**
	 * @param product
	 *            产品其他属性
	 * @param areaCode
	 *            公司地区code
	 * @param havePic
	 *            是否要图片供求
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pageProductsBySearchEngine(ProductsDO product,
			String areaCode, Boolean havePic, PageDto<ProductsDto> page,
			Boolean duplicate);

	/**
	 * 搜索样品供求
	 * 
	 * @param product
	 * @param havePic
	 * @param page
	 * @return
	 */
	public PageDto<ProductsDto> pagePPCProductsBySearchEngine(
			ProductsDO product, Boolean havePic, PageDto<ProductsDto> page);

	/**
	 * 根据关键字搜索(zz91后台搜索)
	 * 
	 * @param page
	 * @param keyword
	 * @return
	 */
	public PageDto<KeywordSearchDto> pageKwproductBySearchEngine(
			PageDto<KeywordSearchDto> page, String keyword);

	/**
	 * 根据关键字搜索(zz91后台数据导出)
	 * 
	 * @param keyword
	 * @return
	 */
	public List<KeywordSearchDto> kwproductBySearchEngine(String keyword);

	/**
	 * 统计过期供求数量
	 * 
	 * @param products
	 * @return
	 */
	public Integer countExpireProductByCompanyId(ProductsDO products);

	/**
	 * 统计长期有效但长期未刷新的供求数量
	 * 
	 * @param products
	 * @return
	 */
	public Integer countValidProductByCompanyId(ProductsDO products);

	/**
	 * 根据刷新时间下载供求信息已过期普会的信息
	 * 
	 * @param refreshFrom
	 *            刷新时间始终
	 * @param refreshTo
	 *            刷新时间终
	 * @return
	 */

	public List<CompanyAccount> downLoadCompanyAccountByProduct(
			String refreshFrom, String refreshTo);

	/**
	 * 最新供应/求购 根据发布时间查询
	 * 
	 * @param productsTypeCode
	 * @param size
	 * @return
	 */
	public List<ProductsDO> queryProductsByType(String productsTypeCode,
			Integer size);

	/**
	 * 最新供应/求购 根据刷新时间查询
	 * 
	 * @param productsTypeCode
	 * @param size
	 * @return
	 */
	public List<ProductsDO> queryProductsByTypeFresh(String productsTypeCode,
			Integer size);

	/**
	 * 新的刷新供求方法， 会在product_expire（供求有效期）中添加一条记录
	 * 
	 * @param id
	 * @param companyId
	 * @return
	 * @throws ParseException
	 */
	public Integer updateNewRefreshTimeById(Integer id, Integer companyId)
			throws ParseException;

	/**
	 * 判断供求的题目是否存在
	 * 
	 * @param title
	 * @return
	 */
	public Integer hasTitle(String title);

	/**
	 * 统计该客户隐藏信息条数
	 * 
	 * @param companyId
	 * @return
	 */
	public Integer countHideProductBycompanyId(Integer companyId);

	/**
	 * 统计各供求发布来源条数
	 * 
	 * @param from
	 * @param to
	 * @param sourceTypeCode
	 * @return
	 */
	public Integer countProductBySoucre(Date from, Date to,
			String sourceTypeCode);

	/**
	 * 查询公司最新供求
	 * 
	 * @param cid
	 * @return
	 */
	public List<ProductsDO> queryNewProducts(Integer cid);

}
