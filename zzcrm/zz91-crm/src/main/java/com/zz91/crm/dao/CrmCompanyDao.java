/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.dto.CommCompanyDto;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmCompanyDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmCompanyDao {

	public static final Short CTYPE_DEFAULT = 0; // 刚注册客户库
	public static final Short CTYPE_SELF = 1; // 个人库
	public static final Short CTYPE_PUBLIC = 2; // 公海库
	public static final Short CTYPE_VIP = 3; // 高级客户库
	public static final Short CTYPE_DISABLE = 4; // 废品池客户
	public static final Short STATUS_DISABLE = 0; // 无效
	public static final Short STATUS_ABLE = 1; // 有效
	public static final Short REGISTER_TYPE1 = 0; // 手动录入
	public static final Short REGISTER_TYPE2 = 1; // 环保网注册
	public static final Short CTYPE_DISACTIVE = 5; // 未激活客户库
	public static final Short SALE_STATUS_TRUE = 0; // 销售状态
	public static final Short SALE_STATUS_FALSE = 1; // 非销售状态
	public static final Integer REPEAT_FALSE = 0; // 没有重复公司

	/**
	 * 添加公司信息
	 * 
	 * @param company
	 * @return
	 */
	public Integer createCompany(CrmCompany company);

	/**
	 * 更新审核状态
	 * 
	 * @param id
	 * @param status
	 * @param memberCode
	 * @return
	 */
	public Integer updateRegistStatus(Integer id, Short status,
			String memberCode);

	/**
	 * 查询我的私人库公司信息
	 * 
	 * @param search
	 * @param page
	 * @return
	 */
	public List<SaleCompanyDto> queryMyCompany(CompanySearchDto search,
			PageDto<SaleCompanyDto> page);

	/**
	 * 查询私人库公司信息数
	 * 
	 * @param search
	 * @return
	 */
	public Integer queryMyCompanyCount(CompanySearchDto search);

	/**
	 * 查询显示公司信息
	 * 
	 * @param search
	 * @param page
	 * @return
	 */
	public List<CommCompanyDto> queryCommCompany(CompanySearchDto search,
			PageDto<CommCompanyDto> page);

	/**
	 * 查询客户数
	 * 
	 * @param search
	 * @return
	 */
	public Integer queryCommCompanyCount(CompanySearchDto search);

	/**
	 * 根据公司Id查询公司信息
	 * 
	 * @param cid
	 * @return
	 */
	public CrmCompany queryCompanyById(Integer id, Short status);

	/**
	 * 更新公司信息
	 * 
	 * @param crmCompany
	 * @return
	 */
	public Integer updateCompany(CrmCompany crmCompany);

	/**
	 * 查询形似公司信息
	 * 
	 * @param cname
	 * @param name
	 * @param email
	 * @param mobile
	 * @param phone
	 * @param fax
	 * @param limit
	 * @return
	 */
	public List<CrmCompany> queryCommCompanyByConditions(String cname,
			String name, String email, String mobile, String phone, String fax,
			Integer limit);

	/**
	 * 查询相同联系方式的公司信息
	 * 
	 * @param mobile
	 * @param phone
	 * @param limit
	 * @return
	 */
	public List<SaleCompanyDto> queryCommCompanyByContact(String mobile,
			String phone, Integer cid, Integer limit);

	/**
	 * 合并公司信息
	 * 
	 * @param id
	 * @param newId
	 * @return
	 */
	public Integer updateRepeatId(Integer id, Integer newId);

	/**
	 * 更新公司信息所属库
	 * 
	 * @param id
	 * @param newType
	 * @return
	 */
	public Integer updateCtypeById(Integer id, Short newType);

	/**
	 * 设置为无效联系
	 * 
	 * @param id
	 * @param status
	 *            0:有效 1:无效
	 * @return
	 */
	public Integer updateDisableContactById(Integer id, Short status);

	/**
	 * 根据Cid查询公司ID
	 * 
	 * @param cid
	 * @return
	 */
	public Integer queryIdByCid(Integer cid);

	/**
	 * 根据Cid更新最新关联关系
	 * 
	 * @param cid
	 * @param saleCompId
	 * @return
	 */
	public Integer updateSaleCompIdById(Integer cid, Integer saleCompId);

	/**
	 * 根据ID取联系方式(phone,moblie)
	 * 
	 * @param cid
	 */
	public CrmCompany queryContactById(Integer cid);

	/**
	 * 查询数量根据手机和邮箱
	 * 
	 * @param mobile
	 * @param email
	 * @return
	 */
	public Integer queryCountByMobileAndEmail(String mobile, String email);

	/**
	 * 查询id记录数量
	 * 
	 * @param id
	 * @param ctype
	 * @return
	 */
	public Integer queryCountById(Integer id, Short ctype);

	/**
	 * 查询公司信息
	 */
	public CrmCompany queryCompById(Integer id);

	/**
	 * 更新客户星级
	 * 
	 * @param id
	 * @param star
	 * @param source
	 * @param maturity
	 * @param promote
	 * @param saleName
	 * @param saleAccount
	 * @param kp
	 * @param callType
	 * @return
	 */
	public Integer updateStarByCid(Integer id, Short star, Short source,
			Short maturity, Short promote, String saleAccount, String saleName,
			Short kp, Short callType);

	/**
	 * 查看客户星级
	 * 
	 * @param cid
	 * @return
	 */
	public CrmCompany queryStarById(Integer id);

	/**
	 * 修改销售状态
	 * 
	 * @param id
	 * @param saleStatus
	 * @return
	 */
	public Integer updateSaleStatusById(Integer id, Short saleStatus);

	/**
	 * 查询曾经是四星或五星的客户
	 * 
	 * @param search
	 * @param page
	 * @return
	 */
	public List<CommCompanyDto> queryOnceFourOrFive(CompanySearchDto search,
			PageDto<CommCompanyDto> page);

	/**
	 * @param search
	 * @return
	 */
	public Integer queryOnceFourOrFiveCount(CompanySearchDto search);

	/**
	 * 查询公司信息(转四星,转五星客户统计用)
	 * 
	 * @param search
	 * @param page
	 * @return
	 */
	public CrmCompany querySimplyCompById(Integer id);

	/**
	 * 查询公司基本信息(导出)
	 * 
	 * @return
	 */
	public List<CrmCompanyDto> querySimplyComp();

	/**
	 * 查询公司名称
	 * 
	 * @param id
	 * @return
	 */
	public String queryCnameById(Integer id);

	/**
	 * @param page
	 * @param company
	 * @return
	 */
	public List<SaleCompanyDto> queryBaseComp(PageDto<SaleCompanyDto> page,
			CrmCompany company);

	/**
	 * @param company
	 * @return
	 */
	public Integer queryBaseCompCount(CrmCompany company);
}