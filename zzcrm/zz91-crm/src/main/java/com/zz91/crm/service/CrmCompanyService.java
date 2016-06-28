/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service;

import java.util.List;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmCompanyBackup;
import com.zz91.crm.dto.CommCompanyDto;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmCompanyDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;
import com.zz91.crm.exception.LogicException;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmCompanyService {

    public static final Short REGIST_STATUS_CHECK = 1;//手动录入数据已审核
    public static final Short REGIST_STATUS_UNCHECK = 0;//手动录入数据未审核
    public static final Integer DEFAULT_LIMIT = 20;

    /**
     * 手动录入数据
     * @param company
     * @return
     */
    public Integer createCompany(CrmCompany company);

    /**
     * 系统导入数据(事务)
     * @param company
     * @return
     * @throws Exception 
     */
    public Integer createCompanySys(CrmCompanyBackup company);

    /**
     * 审核手动录入客户
     * @param id
     * @param status
     * @param memberCode 
     * @return
     */
    public Integer updateRegistStatus(Integer id, Short status, String memberCode);

    /**
     * 合并相同公司
     * @param id[] 要合并的公司编号
     * @param newId 合并给什么公司
     * @return
     * @throws Exception 
     */
    public Integer updateRepeatIds(Integer id[], Integer newId);

    /**
     * 查询我(部门)的私人库公司信息
     * @param search
     * @param page
     * @return
     */
    public PageDto<SaleCompanyDto> searchMyCompany(CompanySearchDto search, PageDto<SaleCompanyDto> page);

    /**
     * 根据条件查询公司信息
     * @param search
     * @param page
     * @return
     */
    public PageDto<CommCompanyDto> searchCommCompany(CompanySearchDto search, PageDto<CommCompanyDto> page);

    /**
     * 根据公司Id查询公司信息
     * @param cid
     * @return
     */
    public CrmCompany queryCompanyById(Integer id);
    
    /**
     * 更新公司信息
     * @param crmCompany
     * @return
     */
    public Integer updateCompany(CrmCompany crmCompany);

    /**
     * 查询形似公司信息
     * @param cname 公司名称
     * @param name 联系人名称
     * @param email 邮箱
     * @param mobile 手机号码
     * @param phone 电话号码
     * @param fax  传真号码
     * @param limit
     * @return
     */
    public List<CrmCompany> queryCommCompany(String cname, String name, String email, String mobile, String phone, String fax, Integer limit);

    /**
     * 查询相同联系方式的公司信息
     * @param mobile
     * @param phone
     * @return
     */
    public List<SaleCompanyDto> queryCommCompanyByContact(Integer cid, Integer limit);
    
    /**
     * 查询手机或邮箱是否存在
     */
	public boolean queryMobileAndEmail(String mobile, String email);
	
	/**
	 *根据Id查询是否存在记录数量
	 * @param id
	 * @param ctype
	 * @return
	 */
	public Integer queryCountById(Integer id,Short ctype);
	
	/**
	 * 查询公司信息（简单查询，用户修改信息比较）
	 */
	public CrmCompany queryCrmCompById(Integer id);
	
	/**
     * 更新公司信息所属库
     * @param id
     * @param newType
     * @return
     */
	public Integer updateCtypeById(Integer id, Short newType);
	
	/**
	 * 根据小记最后记录星级更新客户星级等
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
	public Integer updateStarByCid(Integer id,Short star,Short source,Short maturity,Short promote, String saleAccount, String saleName, Short kp, Short callType);
	
	/**
     * 查寻客户星级
     * @param id
     * @return
     */
	public CrmCompany queryStarById(Integer id);

	/**
	 * 修改销售状态
	 * @param id
	 * @param saleStatus
	 * @return
	 */
	public Integer updateSaleStatusById(Integer id, Short saleStatus) throws LogicException;
	
	/**
	 * 查询曾经是四星或五星的用户
	 * @param search
	 * @param page
	 * @return
	 */
	public PageDto<CommCompanyDto> searchOnceFourOrFive(CompanySearchDto search, PageDto<CommCompanyDto> page);

	/**
	 * 查询公司基本信息(导出用)
	 * @return
	 */
	public List<CrmCompanyDto> querySimplyComp();

	/**
	 * 时间处理
	 * @param searchdto
	 */
	public void timeHandle(CompanySearchDto searchdto);

	/**
	 * 高级查询字段排序
	 * @param page
	 * @param sr
	 * @param sortMode
	 */
	public void sortByField(PageDto<SaleCompanyDto> page,PageDto<CommCompanyDto> page2,String sr, Integer sortMode);

	/**
	 * @param page
	 * @param company
	 * @return
	 */
	public PageDto<SaleCompanyDto> pageComp(PageDto<SaleCompanyDto> page,
			CrmCompany company);
	
}