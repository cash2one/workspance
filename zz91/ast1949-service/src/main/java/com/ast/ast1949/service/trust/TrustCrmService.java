package com.ast.ast1949.service.trust;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.trust.TrustCrm;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCrmDto;
import com.ast.ast1949.dto.trust.TrustCrmSearchDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;

public interface TrustCrmService {

	final static Integer IS_PUBLIC = 1;
	final static Integer NO_PUBLIC = 0;
	final static Integer IS_RUBBISH = 1;
	final static Integer NO_RUBBISH = 0;

	/**
	 * 添加一个公司，公司id必须是唯一的 星级默认为 1星
	 */
	public Integer create(Integer companyId, String crmAccount);
	
	/**
	 * 添加一个公司，公司id必须是唯一的 星级默认为 1星,放入公海给所有人捞
	 */
	public Integer importToCrm(Integer companyId);

	/**
	 * 分页获取公司信息
	 */
	public PageDto<TrustCrmDto> page(TrustCrmSearchDto searchDto, PageDto<TrustCrmDto> page);

	/**
	 * 捞公海，客户不属于任何人 公海状态0 设置为 1
	 */
	public Integer assignAccount(Integer companyId, String crmAccount);

	/**
	 * 丢公海 清空该客户 的 服务人员帐号 公海状态1 设置为 0
	 */
	public Integer lost(Integer companyId);

	/**
	 * 丢废品池 清空该客户的 服务人员帐号 ，丢到废品池
	 */
	public Integer destory(Integer companyId);

	/**
	 * 小计记录结束更新 公司状态 更新下次联系时间 更改星级
	 * 没有公司信息的情况，则初始化一个 公司信息
	 */
	public Integer updateAfterContact(Integer companyId, Integer star, Date gmtNextVisit);
	
	/**
	 * 根据公司id获取 crm信息
	 */
	public TrustCrm queryByCompanyId(Integer companyId);

	/**
	 * 分配客户 管理层，公海状态0 设置为 1
	 * @param companyId
	 * @param crmAccount
	 * @return
	 */
	public Integer assignAccountByRight(Integer companyId, String crmAccount);
	
	/**
	 * 统计采购小计页面 日常通用小计获取 
	 * 以flag为界定，值为C：则检索公司小计，值为B：则检索采购小计
	 */
	public PageDto<TrustCsLogDto> pageLog(TrustCsLogSearchDto searchDto,PageDto<TrustCsLogDto> page);

	/**
	 * 检索指定人员 某月工作小计日清单一览
	 * @param year 时间 	年 	整型例如 “2015”
	 * @param month 时间 	月 	整型例如 “8”
	 * @param account 帐号
	 * @return
	 */
	List<Map<String, Object>> queryMonthLog(Integer year, Integer month, String account);
}
