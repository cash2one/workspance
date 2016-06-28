package com.ast.ast1949.service.phone;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneDto;

/**
 *	author:kongsj
 *	date:2013-7-3
 */
public interface PhoneService {
	
	final static String EXPIRED_CONTENT = "信息已过期";
	final static String  BIND_FLAG="phone_bind_flag";
	
	/**
	 * 根据id检索一条记录
	 * @param id
	 * @return
	 */
	public Phone queryById(Integer id);

	/**
	 * 新增一条记录
	 * @param phone
	 * @return
	 */
	public Integer insert(Phone phone);

	/**
	 * 更新一条记录
	 * @param phone
	 * @return
	 */
	public Integer update(Phone phone);

	/**
	 * 分页显示数据
	 * @param phone
	 * @param page
	 * @return
	 */
	public PageDto<Phone> pageList(Phone phone, PageDto<Phone> page);
	
	/**
	 * 分页显示数据给予后台号码库绑定解绑使用
	 * @param phone
	 * @param page
	 * @return
	 */
	public PageDto<Phone> pageListForLibrary(Phone phone, PageDto<Phone> page);
	
	public Phone queryByCompanyId(Integer companyId);
	
	public Phone queryByTel(String tel);
	
	public PageDto<PhoneDto> pageQueryList(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,PageDto<PhoneDto> page, Date from, Date to,Company company,float laveFrom,float laveTo,String csAccount,Date svrFroms,Date svrTos) throws ParseException;
	public List<PhoneDto> pageQueryListl(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,PageDto<PhoneDto> page, Date from, Date to,Company company,float laveFrom,float laveTo,String csAccount) throws ParseException;

	/**
	 * 计算来电宝用户余额 慎用
	 * @param phone
	 * @return
	 */
	public Boolean countBalanceByAdmin(Phone phone);

	/**
	 * 更新短信发送费用
	 */
	public Integer updateSmsFee(String smsFee,Integer companyId);
	/**
	 * 更新phone总金额
	 * @param amount
	 * @param companyId
	 * @return
	 */
	
	public Integer updateAmountByCompanyId(String amount,Integer companyId );
	/**
	 * 开通易企通后台黑名单状态 status：1为开启
	 * @param tel
	 * @return
	 */
	public Integer openStatus(String tel);
	/**
	 * 搜索400黑名单 将其加入到易企通后台新开通的400号码中
	 * @param tel
	 * @return
	 */
	public Integer updateBlickList(String tel);
	/**
	 * 查找来电宝必杀期客户
	 * @param phone
	 * @param companyAccount
	 * @param phoneCostSvr
	 * @param page
	 * @param from
	 * @param to
	 * @param company
	 * @param laveFrom
	 * @param laveTo
	 * @param csAccount
	 * @return
	 * @throws ParseException
	 */
	public PageDto<PhoneDto> pageQueryBsList(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,PageDto<PhoneDto> page, Date from, Date to,Company company,float laveFrom,float laveTo,String csAccount) throws ParseException;
	
	/**
	 * 关闭该400号码 ，使用于解除400绑定后台页面，将 front_tel 更新为 已过期 ，同时 expired_flag字段更新为1(已过期)
	 * @param companyId
	 * @return
	 */
	public Integer closePhone(Integer companyId);
	
	public PageDto<PhoneDto> pagePhoneCallFee(PageDto<PhoneDto>page,Date from,Date to) throws ParseException;
}
