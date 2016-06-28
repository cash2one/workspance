/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-24 上午10:18:50
 */
package com.ast.ast1949.persist.products;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;

/**
 * 供求数据访问接口
 * 
 * @author Ryan
 * 
 */
public interface ProductsDAO {

	public ProductsDO queryProductsById(Integer id);

	public ProductsDO queryProductsWithOutDetailsById(Integer id);

	@SuppressWarnings("unchecked")
	public List<ProductsDO> queryProductsOfCompany(Integer companyId,
			String mainCode, PageDto page);

	public Integer queryProductsOfCompanyCount(Integer companyId,
			String mainCode);

	/**
	 * <br />
	 * 查找特定公司的供求信息，并且带图片信息 <br />
	 * 所查找的供求信息为已审核的(check_status=1)，未过期的(expire_time>=now()) <br />
	 * 发布情况正常的(is_pause=0)，没有被删除的(is_del=0)供求信息
	 * 
	 * @param companyId
	 *            :公司ID，不能为null
	 * @param maxSize
	 *            :最多查询的数量，为null时，默认查找最的10条
	 * @return 结果按refreshTime倒序排列
	 */
	public List<ProductsDto> queryProductsWithPicByCompany(Integer companyId,
			Integer maxSize);

	public Integer queryProductsWithPicByCompanyEsiteCount(Integer companyId,
			String kw, Integer sid);

	public List<ProductsDto> queryProductsWithPicByCompanyEsite(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page);

	public List<ProductsDto> queryProductsByMainCode(String mainCode,
			String typeCode, Integer maxSize);

	public List<ProductsDto> queryProductsWithCompanyForInquiry(
			Company company, ProductsDO products, PageDto<ProductsDto> page);

	public Integer queryProductsWithCompanyForInquiryCount(Company company,
			ProductsDO products);

	public List<ProductsDto> queryNewProductsIntervalDay(Date start, Date end,
			Integer maxSize);

	public List<ProductsDto> queryProductsByAdmin(Company company,
			ProductsDO products, Integer[] statusArray,
			PageDto<ProductsDto> page, String from, String to, String selectTime);

	public Integer queryProductsByAdminCount(Company company,
			ProductsDO products, Integer[] statusArray, String from, String to,
			String selectTime);

	public List<ProductsDO> queryProductsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,String title,
			PageDto<ProductsDO> page);

	public Integer queryProductsOfCompanyByStatusCount(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,String title);

	public List<ProductsDO> queryNewestProducts(String mainCode,
			String typeCode, Integer maxSize);

	public Integer countProductsByCompanyId(Integer companyId);

	public Integer countUserAddProductsToday(Integer companyId);

	/**
	 * 根据公司Id获取最后更新的时间
	 * 
	 * @param companyId
	 *            公司ID
	 * @return 最后更新的时间：<br/>
	 *         没有符合条件的记录,返回null；<br/>
	 */
	public Date queryMaxRefreshTimeByCompanyId(Integer companyId);

	public Integer insertProduct(ProductsDO products);

	public Integer batchDeleteProductsByIds(Integer[] entities,Integer companyId);

	public Integer countProuductsByTitleAndAccount(String title,String productsTypeCode, String account);

	public Integer updateProductsCheckStatusByAdmin(String checkStatus,String unpassReason, String checkPerson, Integer productId);

	public Integer updateProductsUncheckedCheckStatusByAdmin(String checkStatus, String unpassReason, String checkPerson,Integer productId);

	public Integer updateProductsIsShowInPrice(Integer id, String flag);

	public Integer updateProductsIsPause(String isPause, Integer[] ids);

	public Integer updateProductsRefreshTime(Date refreshTime,
			Integer productsId, Integer companyId);

	public Integer updateProductsRefreshTimeAndExpireTime(Integer productsId,
			Integer companyId);

	/**
	 * 更新部分供求信息
	 * 
	 * @param param
	 *            :需要更新的参数信息,不能为空
	 * @return 更新影响的行数,0 表示没有更新记录,正数n 表示更新了n条记录
	 */
	public Integer updateProductsByAdmin(ProductsDO productsDO);

	public Integer updateProductsByCompany(ProductsDO products);

	public List<Integer> queryProductsIdsOfCompany(Integer companyId,
			String categoryCode);

	public ProductsDto queryProductsWithPicById(Integer id);

	public ProductsDto queryProductsWithPicAndCompany(Integer id);

	public String queryTitleById(Integer id);

	public Integer queryProductsCount();

	public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
			String productCategory, Integer size);

	/**
	 * 用于Seo
	 * 
	 * @param cid
	 * @return
	 */
	public ProductsDO queryProductsByCid(Integer cid);

	/*********
	 * 用于myrc 发布报价时，获取最近的发布时间，
	 * 
	 * @param cid
	 * @return
	 */
	public Date queryLastGmtCreateTimeByCId(Integer cid);

	/*******
	 * 用在myrc的防止注册机的 30s 时间限制 查询数据库的当前时间
	 * 
	 * @return 返回 一个系统时间
	 */
	public Date queryNowTimeOfDatebase();

	public List<ProductsDto> queryProductsByAdminZstExpried(Integer isRecheck,
			PageDto<ProductsDto> page, ProductsDO products);

	public Integer queryProductsByAdminZstExpriedCount(Integer isRecheck,
			ProductsDO products);

	public List<ProductsDto> queryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords);

	public Integer countQueryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords);

	public List<ProductsDto> queryProductsByAreaCode(String areaCode,
			Integer maxSize);

	public List<ProductsDto> queryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode);

	public Integer countQueryProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode);

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

	// 更改图片后信息重新成为未审核
	public Integer updateUncheckByIdForMyrc(Integer id);

	public List<ProductsDto> queryProductsWithPicByCompanyEsiteWithDetails(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page);

	/**
	 * 搜索公司最新供求一条
	 */
	public ProductsDO queryProductsByCidForLatest(Integer cid,ProductsDO products);

	/*********************
	 * 查询与该公司对应的高会的供应/求购 信息
	 * 
	 * @param companyId
	 *            公司id</br>
	 * @param productsTypeCode
	 *            供求类型</br>
	 * @param maxSize
	 *            读取的条数</br>
	 * @return
	 */
	public ProductsDto queryProductsWithPicByCidAndTypeCode(Integer companyId,String productsTypeCode, Integer maxSize);

	public ProductsDto queryProductsByCidAndTypeCodeAndMainCode(Integer companyId, String mainCode, String productsTypeCode,Integer maxSize);

	/**
	 * 检索不同公司的最新供求 使用于map首页热门供求
	 */
	public List<ProductsDO> queryNoRepeatProducts(String industryCode,
			String areaCode, String keywords, String typeCode,
			PageDto<ProductsDto> page);

	public Integer countQueryNoRepeatProducts(String industryCode,
			String areaCode, String keywords, String typeCode);

	public Integer updateGaoCheckStatusByAdmin(String checkStatus,
			String checkPerson, Integer productId, String unpassReason);

	// 查询今天的供求数量
	public Integer queryTodayCopperProductsCount(String code, String from,
			String to);

	public List<ProductsDto> queryVipProductsForMyrc(String title, Integer size);

	public Integer updateProductsUncheckedCheckStatusForDelByAdmin(
			String checkStatus, String unpassReason, String checkPerson,
			Integer productId, String isDel);

	// trade 门市部化的公司 供求查询
	public List<ProductsDO> queryProductsByTypeOfCompany(String companyId,
			String productTypeCode, PageDto page);

	// trade 门市部化的公司 供求查询:查询对应记录的条数
	public Integer queryProductsByTypeOfCompany(String companyId,
			String productTypeCode);

	public Integer updateProductsIsDelByAdmin(Integer productId, String status);

	/**
	 * 搜索现货供求 list
	 * 
	 * @param page
	 * @return
	 */
	public List<ProductsDO> queryProductForSpot(ProductsDto product,
			PageDto<ProductsDto> page);

	/**
	 * 搜索现货供求 count
	 * 
	 * @return
	 */
	public Integer queryCountProductForSpot(ProductsDto product);

	public List<ProductsDto> queryProductForSpotByAdmin(Company company,ProductsDO products, PageDto<ProductsDto> page,Integer min,Integer max,String isStatus);

	public Integer queryCountProductForSpotByAdmin(Company company,ProductsDO products,Integer min,Integer max,String isStatus);

	public List<ProductsDO> queryProductsForSpotByCondition(ProductsDto dto, Integer size);
	
	public List<ProductsDO> queryProductsForPic(ProductsDO products,Integer size);
	
	public List<ProductsDto> queryProductForexportByAdmin(Company company,ProductsDO products, PageDto<ProductsDto> page,Integer min,Integer max,String isStatus);

	/**
	 * 商铺服务 所有供求搜索 包括各种状态
	 * @param companyId
	 * @param maxSize
	 * @return
	 */
	public List<ProductsDto> queryProductsWithPicByCompanyForSp(Integer companyId,String kw, Integer sid,PageDto<ProductsDto> page);
	
	public Integer queryProductsWithPicByCompanyForSpCount(Integer companyId,String kw, Integer sid);
	
	public List<ProductsDO> queryPassProductsByDate(String from,String to);
	
}
