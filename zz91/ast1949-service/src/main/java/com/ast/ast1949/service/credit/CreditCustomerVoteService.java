package com.ast.ast1949.service.credit;

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditCustomerVoteDto;

public interface CreditCustomerVoteService {

	/**
	 * 给客户做出评价
	 */
	public Integer voteToCompany(CreditCustomerVoteDo vote);

	/**
	 * 对其他客户给出的评价做出解释
	 */
	public Integer replyToVote(Integer id, String content);

	/**
	 * 分页查找其他公司对自己做出的评价，并且带有自己做出的解释信息
	 */
	public PageDto<CreditCustomerVoteDto> pageVoteByToCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page);

	/**
	 * 分页查找本公司对其他公司做出的评价信息
	 */
	public PageDto<CreditCustomerVoteDto> pageVoteByFromCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page);

	/**
	 * 删除本公司对其他公司做出的评价（只有做出评价的15天内可以删除）
	 */
	public Integer deleteVoteByFromCompany(Integer id, Integer fromCompanyId);

	/**
	 * 更新审核状态
	 * @param id 记录编号
	 * @param checkStatus 审核状态
	 * @param checkPerson 审核人
	 * @param companyId 公司编号
	 * @param status 评价类型 好评 中评 差评
	 * @return
	 */
	public Integer updateCheckStatusByAdmin(Integer id, String checkStatus,
			String checkPerson,Integer companyId,String status);

	public int countVoteNumByToCompany(Integer companyId, String status,
			String checkStatus, Boolean isOnLine);
	
	public PageDto<CreditCustomerVoteDto> pageCreditCustomerVoteByConditions(CreditCustomerVoteDto dto, PageDto<CreditCustomerVoteDto> page);

}
