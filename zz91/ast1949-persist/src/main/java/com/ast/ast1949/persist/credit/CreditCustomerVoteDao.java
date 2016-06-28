package com.ast.ast1949.persist.credit;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditCustomerVoteDto;

public interface CreditCustomerVoteDao {

	public Integer insertVote(CreditCustomerVoteDo vote);

	public CreditCustomerVoteDo queryOneVoteById(Integer id);

	public List<CreditCustomerVoteDto> queryVoteByToCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page);

	public Integer queryVoteByToCompanyCount(CreditCustomerVoteDo vote);

	public List<CreditCustomerVoteDto> queryVoteByFromCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page);

	public Integer queryVoteByFromCompanyCount(CreditCustomerVoteDo vote);

	public Integer deleteVoteByCompany(Integer id, Integer companyId, Date deadline);

	public Integer updateCreditCustomerVoteStatusById(Integer id, String checkStatus,
			String checkPerson);

	public Integer updateReplyContentByCompany(Integer id, String content);

	public Integer countVoteNumByToCompany(Integer companyId, String status, String checkStatus,
			Date deadLine, Boolean isOnLine);

	public List<CreditCustomerVoteDto> queryCreditCustomerVoteByConditions(CreditCustomerVoteDto dto, PageDto<CreditCustomerVoteDto> page);

	public Integer countCreditCustomerVoteByConditions(CreditCustomerVoteDto dto);
	// /**
	// * 添加客户评价
	// * @param creditCustomerVote客户评价CreditCustomerVoteDO信息，不可为空
	// * @return 返回添加成功的记录条数，失败为0
	// */
	// public Integer insertCreditCustomerVote(CreditCustomerVoteDo
	// creditCustomerVote);
	//	
	// /**
	// * 根据ID查询客户评价
	// * @param id为主键ID值，不可为null
	// * @return
	// */
	// public CreditCustomerVoteDo selectCreditCustomerVoteById(Integer id);
	//	
	// /**
	// * 查询客户评价
	// * @param pageDto为分页Dto信息，可为空
	// * @param creditCustomerVote为客户评价CreditCustomerVoteDO信息，不可为空
	// * @return CreditCustomerVoteDO信息集，没查询到返回为空
	// */
	// public List<CreditCustomerVoteDo> selectCreditCustomerVote(
	// PageDto pageDto, CreditCustomerVoteDo creditCustomerVote);
	//	
	// /**
	// * 查询客户评价总个数
	// * @param pageDto为分页Dto信息，可为空
	// * @param creditCustomerVote为客户评价CreditCustomerVoteDO信息，不可为空
	// * @return 返回总个数，没查询到返回为0
	// */
	// public Integer selectCreditCustomerVoteCount(
	// PageDto pageDto, CreditCustomerVoteDo creditCustomerVote);
	//	
	// /**
	// * 批量修改客户评价审核状态
	// * @param ids为主键整形数据值,不可为空
	// * @param checkStatus为审核状态，不可为null
	// * @return 修改成功的记录条数，否则为0
	// */
	// public Integer batchUpdateCreditCustomerVoteCheckStatus(Integer[] ids,
	// String checkStatus);
	//	
	// /**
	// * 批量删除客户评价
	// * @param ids为主键整形数据值,不可为空
	// * @return 删除成功的记录条数，否则为0
	// */
	// public Integer batchDeleteCreditCustomerVote(Integer[] ids);

	/**
	 * 查出一共有多少个评价
	 * 
	 * @param creditDTO
	 * @return
	 */
	// public List<CreditDTO> selectCreditCustomerDTO(CreditDTO creditDTO);
	//	
	// /**
	// * 查询一共有多少个作出的评价
	// * @param creditDTO
	// * @return
	// */
	// public List<CreditDTO> selectCreditCustomerVoteReplyDTO(CreditDTO
	// creditDTO);
	// /**
	// * 查询评价记录总数
	// */
	// public Integer countPageCreditCustomerVoteId(CreditDTO creditDTO);
	//	
	// /**
	// * 查询我作出评价记录总数
	// */
	// public Integer countPageCreditCustomerVoteReplyId(CreditDTO creditDTO);
	// /**
	// * 评价回复
	// */
	// public Integer insertCreditCustomerVoteReplyDO(CreditCustomerVoteReplyDO
	// creditCustomerVoteReplyDO);
}
