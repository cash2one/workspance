/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.service.company;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;

/**
 * @author yuyonghui
 *
 */
public interface CompanyPriceService {
	
	
	/**
	 * 根据登录者查询 企业报价
	 * @param companyId
	 * @return 结果集
	 *
	 */
//	public List<CompanyPriceDTO> queryCompanyPriceByCondition(CompanyPriceDTO companyPriceDTO);

	/**
	 * 跟据条件分页查询企业报价
	 * @param companyPriceDTO
	 *            条件 可以为空 validTime ,areaName ,provinceName ,title ,price 可以为空
	 * @return 结果集
	 *
	 */
	@Deprecated
	public List<CompanyPriceDTO> queryCompanyPriceForFront(CompanyPriceDTO companyPriceDTO);

	/**
	 * myrc 记录总数
	 * @param companyPriceDTO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
//	public Integer queryMyCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO);

    /**
     *    根据Id,companyId 查询企业报价信息 
     * @param id   不能为空
     * @param companyId  可以为空
     * @return  CompanyPriceDO
     */
	public CompanyPriceDO queryCompanyPriceById(Integer id);

	/**
	 * 查寻 该公司其他企业报价
	 * @param param
	 * @return CompanyPriceDO
	 */
	public List<CompanyPriceDO> queryCompanyPriceByCompanyId(Map<String, Object> param);

	/**
	 * 根据 queryCompanyPriceById 获取 CompanyPriceDO 然后通过运算获取有效期
	 * @param id
	 * @param companyId
	 * @return days 返回天数
	 * @throws ParseException 
	 */
	public Integer queryCompanyPriceValidityTimeById(Date refreshTime, Date expiredTime) throws ParseException;

	/**
	 * 添加企业报价
	 * @param companyPriceDO
	 * @return i >o 添加成功 else 失败
	 */

	public Integer insertCompanyPrice(String membershipCode,CompanyPriceDO companyPriceDO);

	/**
	 * 修改企业报价
	 * @param companyPriceDO
	 * @return
	 */
	public Integer updateCompanyPrice(CompanyPriceDO companyPriceDO);
	
	/**
	 * 批量删除企业报价
	 * @param entities
	 * @return
	 */
	public Integer batchDeleteCompanyPriceById(Integer[] entities);

	/**
	 * @param companyPriceDO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
	@Deprecated
	public Integer queryCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO);

	/**
	 * 企业报价排名
	 * @param limitSize
	 * @return
	 */
	public List<CompanyPriceDO> queryCompanyPriceByCompanyIdCount(Integer limitSize);
	
	/**
	 *    按刷新时间显示企业报价
	 * @param title  不能为空
	 * @param size  查询条数 不可以为空
	 * @return 成功 List<CompanyPriceDO> 失败返回null
	 */
	public List<CompanyPriceDO> queryCompanyPriceByRefreshTime(String title,Integer size);
	
	/**
	 *     审核企业报价
	 * @param entities  不能为空
	 * @param isChecked  0 或1 0 代表未审核 1为审核
	 * @return  如果大于0  修改成功 否则修改失败
	 */
	public Integer updateCompanyPriceCheckStatus(int[] entities,String isChecked);
	
	/**
	 *   根据Id查询企业报价信息，公司名称
	 *   
	 * @param id  不能为空
	 *     主要用于后台修改
	 * @return  CompanyPriceDTO
	 */
	public CompanyPriceDTO selectCompanyPriceById(Integer id);
	
	/**
	 *   把供求信息转换为企业报价
	 * @param productsDO  
	 * @return   如果>0 添加成功  否则添加失败
	 */
	public CompanyPriceDO queryCompanyPriceByProducts(ProductsDO productsDO,String areaCode,Integer productId);
	
	/**
	 * 后台管理员添加企业报价
	 * @param companyPrice:企业报价信息，不能为null
	 * @return
	 */
	public Integer insertCompanyPriceByAdmin(CompanyPriceDO companyPrice);

	/**
	 * 添加供求为企业报价报价
	 * @param companyPriceDO
	 * @param companyDO
	 * @param productsDOT
	 * @return
	 */
	public Integer addProductsToCompanyPrice(CompanyPriceDO companyPriceDO,Company companyDO,ProductsDO productsDO);

	/**
	 * 根据供求ID查询企业报价信息
	 * @return
	 */
	public CompanyPriceDO queryCompanyPriceByProductId(Integer productId);
	/**
	 * 根据公司ID查询报价的分页信息列表
	 * @param id
	 * @param page
	 * @return
	 */
	public PageDto<CompanyPriceDtoForMyrc> queryCompanyPriceListByCompanyId(Integer companyId, PageDto<CompanyPriceDtoForMyrc> page);
	@Deprecated
	public PageDto<CompanyPriceDTO> queryCompanyPricePagiationList(CompanyPriceDTO companyPriceDTO, PageDto<CompanyPriceDTO> pager);
	
	public PageDto<CompanyPriceSearchDTO> pageCompanyPriceSearch(CompanyPriceSearchDTO companyPriceSearchDTO, PageDto<CompanyPriceSearchDTO> page);
	
	public PageDto<CompanyPriceDO> pageCompanyPriceByAdmin(String title,String isChecked,String categoryCode,PageDto<CompanyPriceDO> page);

	public Integer updateCategory(Integer id, String categoryCode);
	
	public Integer updateCompanyPriceByAdmin(CompanyPriceDO companyPriceDO);
	/**
	 * 搜索最新高会企业报价
	 * @param code 报价类别
	 * @param size 记录数
	 * @return
	 */
	public List<CompanyPriceDO> queryNewestVipCompPrice(String code,Integer size);

	public List<CompanyPriceSearchDTO> queryCompanyPriceSearchByFront(CompanyPriceSearchDTO dto, PageDto<CompanyPriceSearchDTO> page);
	
	/**
	 * 使用搜索引擎检索企业报价
	 * @param companyPriceDO
	 * @param page
	 * @return
	 */
	public PageDto<CompanyPriceDTO> pageCompanyPriceBySearchEngine(CompanyPriceDTO CompanyPriceDTO,PageDto<CompanyPriceDTO> page);
	
}
