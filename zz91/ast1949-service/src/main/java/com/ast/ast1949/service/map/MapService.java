/****************
 * 再生地图接口    
 */
package com.ast.ast1949.service.map;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.products.ProductsDto;

public interface MapService {
	
	final static int SEARCH_EXPIRED = 20000;
	final static String URL_ENCODE = "utf-8";

	// 优质公司分页
	public PageDto<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords);

	public PageDto<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode);

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

	// 优质客户推荐
	public PageDto<ProductsDto> pageProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords);

	/**
	 * 根据地区、类别 搜索 最新供求 用于再生地图首页 热门供求
	 */
	public List<ProductsDto> indexHotProductsByArea(String industryCode,
			String areaCode, String keywords, String typeCode, Integer size,
			PageDto<ProductsDto> page);

	/**
	 * 根据地区、类别 搜索 最新供求 用于再生地图首页 热门供求 分页
	 */
	public PageDto<ProductsDto> pageHotProductsByArea(String industryCode,
			String areaCode, String keywords, String typeCode, Integer size,
			PageDto<ProductsDto> page);

	/**
	 * 根据地区、类别 搜索 高会 用于再生地图首页 热门推荐网商
	 */
	public List<ProductsDto> indexHotCompanysByArea(String industryCode,
			String areaCode, String keywords, Integer size,
			PageDto<ProductsDto> page);

	/**
	 * 根据地区、类别 搜索 高会 用于再生地图首页 热门推荐网商 分页
	 */
	public PageDto<ProductsDto> pageHotCompanysByArea(String industryCode,
			String areaCode, String keywords, Integer size,
			PageDto<ProductsDto> page);

	/**
	 * 根据地区、类别 搜索 最新企业报价 用于再生地图首页 网商报价
	 */
	public List<CompanyPriceDTO> indexLatestCompanysPriceByArea(
			String industryCode, String areaCode, Integer size);

	/***
	 * 根据关键字查询
	 * 
	 * @param keyworld
	 * @param size
	 * @return
	 */
	public List<PostDto> queryPostByKey(String keyworld, Integer size);
	
	/**
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * 
	 */
	public PageDto<ProductsDto> queryBySearchEnegine(String province,String keywords,String productsCode,PageDto<ProductsDto> page) throws MalformedURLException, IOException;
}
