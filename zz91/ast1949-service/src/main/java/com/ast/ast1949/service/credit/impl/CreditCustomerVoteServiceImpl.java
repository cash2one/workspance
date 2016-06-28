package com.ast.ast1949.service.credit.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditCustomerVoteDto;
import com.ast.ast1949.persist.credit.CreditCustomerVoteDao;
import com.ast.ast1949.persist.credit.CreditIntegralDetailsDao;
import com.ast.ast1949.service.credit.CreditCustomerVoteService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;

@Component("creditCustomerVoteService")
public class CreditCustomerVoteServiceImpl implements CreditCustomerVoteService {

	@Autowired
	CreditCustomerVoteDao creditCustomerVoteDao;
	
	@Autowired
	CreditIntegralDetailsDao creditIntegralDetailsDao;

	static final int DELETE＿DEADLINE_DAY = -15;
	final static String INTEGRAL_KEY ="customer_vote_";
	
	//0:好评;1:中评;2:差评
	final static Integer OPERATION_INTEGRAL_0=2;
	final static Integer OPERATION_INTEGRAL_1=0;
	final static Integer OPERATION_INTEGRAL_2=-2;

	@Override
	public Integer deleteVoteByFromCompany(Integer id, Integer fromCompanyId) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(fromCompanyId, "the fromCompanyId can not be null");
		Date deadDate = DateUtil.getDateAfterDays(new Date(),
				DELETE＿DEADLINE_DAY);
		//TODO 删除被评价都的积分（负分），如果差评已经被审核通过
		return creditCustomerVoteDao.deleteVoteByCompany(id, fromCompanyId,
				deadDate);
	}

	@Override
	public PageDto<CreditCustomerVoteDto> pageVoteByFromCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page) {
		Assert.notNull(vote.getFromCompanyId(), "the vote.fromCompanyId can not be null");
		Assert.notNull(page, "the object page can not be null");
		page.setTotalRecords(creditCustomerVoteDao.queryVoteByFromCompanyCount(vote));
		List<CreditCustomerVoteDto> list = creditCustomerVoteDao.queryVoteByFromCompany(vote, page);
		Date now = new Date();
		for(CreditCustomerVoteDto obj:list){
			try {
				obj.setDateDiff(DateUtil.getIntervalDays(now, obj.getVote().getGmtCreated()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public PageDto<CreditCustomerVoteDto> pageVoteByToCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page) {
		Assert.notNull(vote.getToCompanyId(), "the vote.toCompanyId can not be null");
		Assert.notNull(page, "the object page can not be null");
		page.setTotalRecords(creditCustomerVoteDao.queryVoteByToCompanyCount(vote));
		List<CreditCustomerVoteDto> list  = creditCustomerVoteDao.queryVoteByToCompany(vote, page);
		Date now = new Date();
		for(CreditCustomerVoteDto obj:list){
			try {
				obj.setDateDiff(DateUtil.getIntervalDays(now, obj.getVote().getGmtCreated()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Integer replyToVote(Integer id, String content) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(content, "the content can not be null");
		return creditCustomerVoteDao.updateReplyContentByCompany(id, content);
	}

	@Override
	public Integer updateCheckStatusByAdmin(Integer id, String checkStatus,
			String checkPerson,Integer companyId,String status) {
		Integer im = creditCustomerVoteDao.updateCreditCustomerVoteStatusById(id, checkStatus, checkPerson);
		if(im.intValue()>0) {
			 if(im.intValue()>0) {
				creditIntegralDetailsDao.deleteIntegralByOperation(INTEGRAL_KEY, id, companyId);
				CreditIntegralDetailsDo detail = new CreditIntegralDetailsDo();
				detail.setCompanyId(companyId);
				detail.setOperationKey(INTEGRAL_KEY+status);
				detail.setRelatedId(id);
				if(status!=null) {
					if("0".equals(status)) {
						detail.setIntegral(OPERATION_INTEGRAL_0);
					} else if ("1".equals(status)){
						detail.setIntegral(OPERATION_INTEGRAL_1);
					} else {
						detail.setIntegral(OPERATION_INTEGRAL_2);
					}
				}
				creditIntegralDetailsDao.insertIntegral(detail);
			}
		}
		return im;
	}
	
	

	@Override
	public Integer voteToCompany(CreditCustomerVoteDo vote) {
		Assert.notNull(vote, "the object vote can not be null");
		Assert.notNull(vote.getToCompanyId(),
				"the vote.toCompanyId can not be null");
		return creditCustomerVoteDao.insertVote(vote);
	}

	@Override
	public int countVoteNumByToCompany(Integer companyId, String status,
			String checkStatus, Boolean isOnLine) {
		Assert.notNull(companyId, "the companyId can not be null");
		Date deadDate = null;
		if (isOnLine != null) {
			deadDate = DateUtil.getDateAfterDays(new Date(),
					DELETE＿DEADLINE_DAY);
		}

		Integer num = creditCustomerVoteDao.countVoteNumByToCompany(companyId,
				status, checkStatus, deadDate, isOnLine);
		if(num!=null){
			return num.intValue();
		}
		return 0;
	}

//	@Override
//	public Integer countCreditCustomerVoteByConditions(CreditCustomerVoteDto dto) {
//		return creditCustomerVoteDao.countCreditCustomerVoteByConditions(dto);
//	}

	@Override
	public PageDto<CreditCustomerVoteDto> pageCreditCustomerVoteByConditions(CreditCustomerVoteDto dto, PageDto<CreditCustomerVoteDto> page){
		page.setRecords(creditCustomerVoteDao.queryCreditCustomerVoteByConditions(dto,page));
		page.setTotalRecords(creditCustomerVoteDao.countCreditCustomerVoteByConditions(dto));
		return page;
	}

	// @Autowired
	// private CreditCustomerVoteDao creditCustomerVoteDAO;
	//	
	// public Boolean batchDeleteCreditCustomerVote(String ids) {
	// Assert.notNull(ids, "The ids can not be null");
	// String[] str = ids.split(",");
	// Integer[] i = new Integer[str.length];
	// for(int m=0; m<str.length; m++){
	// i[m] = Integer.valueOf(str[m]);
	// }
	// if(creditCustomerVoteDAO.batchDeleteCreditCustomerVote(i)>0){
	// return true;
	// }
	// return false;
	// }
	//
	// public Boolean batchUpdateCreditCustomerVoteCheckStatus(String ids,
	// String checkStatus) {
	// Assert.notNull(ids, "The ids can not be null");
	// Assert.notNull(checkStatus, "The checkStatus can not be null");
	// String[] str = ids.split(",");
	// Integer[] i = new Integer[str.length];
	// for(int m=0; m<str.length; m++){
	// i[m] = Integer.valueOf(str[m]);
	// }
	// if(creditCustomerVoteDAO.batchUpdateCreditCustomerVoteCheckStatus(i,
	// checkStatus)>0){
	// return true;
	// }
	// return false;
	// }
	//
	// public Integer insertCreditCustomerVote(
	// CreditCustomerVoteDo creditCustomerVote) {
	// Assert.notNull(creditCustomerVote,
	// "The creditCustomerVote can not be null");
	// return
	// creditCustomerVoteDAO.insertCreditCustomerVote(creditCustomerVote);
	// }
	//
	// public List<CreditCustomerVoteDo> selectCreditCustomerVote(PageDto
	// pageDto,
	// CreditCustomerVoteDo creditCustomerVote) {
	// if(pageDto.getSort()==null){
	// pageDto.setSort("id");
	// }
	// return creditCustomerVoteDAO.selectCreditCustomerVote(pageDto,
	// creditCustomerVote);
	// }
	//
	// public CreditCustomerVoteDo selectCreditCustomerVoteById(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return creditCustomerVoteDAO.selectCreditCustomerVoteById(id);
	// }
	//
	// public Integer selectCreditCustomerVoteCount(PageDto pageDto,
	// CreditCustomerVoteDo creditCustomerVote) {
	// return
	// creditCustomerVoteDAO.selectCreditCustomerVoteCount(pageDto,creditCustomerVote);
	// }

	// /**
	// * 查询客户评价个数
	// */
	// public List<CreditDTO> selectCreditCustomerDTO(CreditDTO creditDTO) {
	// return creditCustomerVoteDAO.selectCreditCustomerDTO(creditDTO);
	// }
	//	
	// /**
	// * 查询一共有多少个作出的评价
	// * @param creditDTO
	// * @return
	// */
	// public List<CreditDTO> selectCreditCustomerVoteReplyDTO(CreditDTO
	// creditDTO){
	// return creditCustomerVoteDAO.selectCreditCustomerVoteReplyDTO(creditDTO);
	// }

	// /**
	// * 查询记录总数
	// */
	// public Integer countPageCreditCustomerVoteId(CreditDTO creditDTO){
	// return creditCustomerVoteDAO.countPageCreditCustomerVoteId(creditDTO);
	// }
	//	
	// /**
	// *查询我作出的评价记录总数
	// */
	// public Integer countPageCreditCustomerVoteReplyId(CreditDTO creditDTO){
	// return
	// creditCustomerVoteDAO.countPageCreditCustomerVoteReplyId(creditDTO);
	// }
	//	
	//	
	// /**
	// * 评价回复
	// */
	// public Integer insertCreditCustomerVoteReplyDO(CreditCustomerVoteReplyDO
	// creditCustomerVoteReplyDO){
	// return
	// creditCustomerVoteDAO.insertCreditCustomerVoteReplyDO(creditCustomerVoteReplyDO);
	// }
}
