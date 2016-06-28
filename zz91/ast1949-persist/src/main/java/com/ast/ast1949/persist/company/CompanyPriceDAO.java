/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.persist.company;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;

/**
 * @author yuyonghui
 *
 */
public interface CompanyPriceDAO {

	
	/**
	 *      企业报价后台分页列表查询
	 * @param companyPriceDTO  不能为空
	 *         条件 ：page  分页参数不能为空
	 *         title 按名称查询   可以为空
	 *         
	 * @return   List<CompanyPriceDTO>  
	 */
    public  List<CompanyPriceDO> queryCompanyPriceForAdmin(String title,String isChecked,String categoryCode,String isVip,CompanyPriceSearchDTO searchDto,PageDto<CompanyPriceDTO> page);
    /**
     *        获取后台企业报价总记录数
     * @param companyPriceDTO 不能为空
     * @return  返回记录总数
     */
    public Integer queryCompanyPriceCountForAdmin(String title,String isChecked,String categoryCode,String isVip,CompanyPriceSearchDTO searchDto);
	/**
	 *   根据登录者查询 企业报价
	 * @param companyId
	 * @return  结果集
	 * 
	 */
//	public List<CompanyPriceDTO> queryCompanyPriceByCondition(CompanyPriceDTO companyPriceDTO);
	
	/**
	 *          跟据条件分页查询企业报价
	 * @param companyPriceDTO  条件  可以为空
	 *            validTime ,areaName ,provinceName ,title ,price  可以为空                                             
	 * @return   结果集
	 * 
	 */
    @Deprecated
	public List<CompanyPriceDTO> queryCompanyPriceForFront(CompanyPriceDTO companyPriceDTO);
    
     /**
      *    根据Id,companyId 查询企业报价信息 
      * @param id   不能为空
      * @param companyId  可以为空
      * @return  CompanyPriceDO
      */
	public CompanyPriceDO queryCompanyPriceById(Integer id);
	/**
	 *   查寻 该公司其他企业报价
	 * @param param
	 * @return CompanyPriceDO
	 */
	public List<CompanyPriceDO> queryCompanyPriceByCompanyId(Map<String, Object> param);
	/**
	 *   添加企业报价
	 * @param companyPriceDO
	 * @return i >o  添加成功
	 *          else 失败    
	 */
	public Integer insertCompanyPrice(CompanyPriceDO companyPriceDO);
	
	/**
	 *   修改企业报价
	 * @param companyPriceDO
	 * @return
	 */
	public Integer updateCompanyPrice(CompanyPriceDO companyPriceDO);
	/**
	 *   批量删除企业报价
	 * @param entities
	 * @return  i >o  修改成功
	 *          else 失败    
	 */
	public Integer batchDeleteCompanyPriceById(Integer[] entities);
	
	/**
	 * 
	 * @param code
	 * @return   成功：返回影响行数；<br/>
	 *           失败：返回0.
	 */
	public Integer queryCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO);
	/**
	 *    myrc 记录总数
	 * @param companyPriceDTO
	 * @return 成功：返回影响行数；<br/>
	 *           失败：返回0.
	 */
//	public Integer  queryMyCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO);
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
	
	public Integer insertCompanyPriceByAdmin(CompanyPriceDO companyPrice);
	
	/**
	 * 根据供求ID,类别查询企业报价信息
	 * @return
	 */
	public CompanyPriceDO queryCompanyPriceByProductId(Integer productId,String code);
	
	public PageDto<CompanyPriceDtoForMyrc> queryCompanyPriceListByCompanyId(Integer companyId,
			PageDto<CompanyPriceDtoForMyrc> page);
	public PageDto<CompanyPriceDTO> queryCompanyPricePagiationList(CompanyPriceDTO companyPriceDTO, PageDto<CompanyPriceDTO> pager);
	
	public List<CompanyPriceSearchDTO> queryCompanyPriceSearchByFront(CompanyPriceSearchDTO companyPriceSearchDTO,PageDto<CompanyPriceSearchDTO> page);
	public Integer queryCompanypriceCount(CompanyPriceSearchDTO companyPriceSearchDTO);
	
	public Integer updateCategoryCode(Integer id, String categoryCode);
	
	public Integer updateCompanyPriceByAdmin(CompanyPriceDO companyPriceDO);
	public List<CompanyPriceDO> queryNewestVipCompPrice(String code,Integer size);
	
	/**
	 * 刷新企业报价刷新时间
	 * @param productId
	 * @return
	 */
	public Integer refreshCompanyPriceByProductId(Integer productId);
	
	/**
	 * 更新企业报价审核状态 本方法只能更新productId存在的
	 * @param productId 供求Id 
	 * @param isChecked
	 * @return
	 */
	public Integer updateCompanyPriceCheckStatusByProductId(Integer productId,String isChecked);

	/**
	 * 查询高会的报价
	 *   
	 *  
	 * 
	 */
	
	public List<CompanyPriceSearchDTO>  queryCompanyPriceList(Integer size);
	
    /**
     * 统计有发布报价的公司
     * @param page
     * @return
     */
	public List<Integer> companyPrice(PageDto<CompanyDto> page);
	/**
	 * 统计有发布报价的公司数量
	 * @return
	 */
	public Integer companyPriceCount();
	/**
	 * 塑料原料的企业报价
	 * @param categoryCompanyPriceCode
	 * @param companyId
	 * @param size
	 * @return
	 */
	public List<CompanyPriceDO> queryCompanyPriceByCondition(String categoryCompanyPriceCode, Integer companyId, Integer size);
	/**
	 *
	 * @param code 
	 * @param size
	 * @return
	 */
	public List<CompanyPriceDO> queryByCode(String code, Integer size);
	
}
