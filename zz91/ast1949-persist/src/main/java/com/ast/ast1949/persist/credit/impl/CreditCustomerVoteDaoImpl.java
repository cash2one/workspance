package com.ast.ast1949.persist.credit.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditCustomerVoteDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.credit.CreditCustomerVoteDao;
import com.ast.ast1949.util.Assert;

@Component("creditCustomerVoteDao")
public class CreditCustomerVoteDaoImpl extends BaseDaoSupport implements CreditCustomerVoteDao {

	final static String SQL_PREFIX = "creditCustomerVote";

	@Override
	public Integer deleteVoteByCompany(Integer id, Integer companyId, Date deadline) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("companyId", companyId);
		root.put("deadline", deadline);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteVoteByCompany"),
				root);
	}

	@Override
	public Integer insertVote(CreditCustomerVoteDo vote) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertVote"), vote);
	}

	@Override
	public CreditCustomerVoteDo queryOneVoteById(Integer id) {

		return (CreditCustomerVoteDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryOneVoteById"), id);
	}

	@Override
	public List<CreditCustomerVoteDto> queryVoteByFromCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("vote", vote);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryVoteByFromCompany"), root);
	}

	@Override
	public Integer queryVoteByFromCompanyCount(CreditCustomerVoteDo vote) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("vote", vote);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryVoteByFromCompanyCount"), root);
	}

	@Override
	public List<CreditCustomerVoteDto> queryVoteByToCompany(CreditCustomerVoteDo vote,
			PageDto<CreditCustomerVoteDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("vote", vote);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryVoteByToCompany"), root);
	}

	@Override
	public Integer queryVoteByToCompanyCount(CreditCustomerVoteDo vote) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("vote", vote);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryVoteByToCompanyCount"), root);
	}

	@Override
	public Integer updateCreditCustomerVoteStatusById(Integer id, String checkStatus,
			String checkPerson) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(checkStatus, "the checkStatus must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("checkStatus", checkStatus);
		param.put("checkPerson", checkPerson);

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateCreditCustomerVoteStatusById"), param);
	}

	@Override
	public Integer updateReplyContentByCompany(Integer id, String content) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("replyContent", content);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateReplyContentByCompany"), root);
	}

	@Override
	public Integer countVoteNumByToCompany(Integer companyId, String status, String checkStatus,
			Date deadLine, Boolean isOnLine) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("toCompanyId", companyId);
		root.put("status", status);
		root.put("checkStatus", checkStatus);
		root.put("deadDate", deadLine);
		root.put("isOnLine", isOnLine);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countVoteNumByToCompany"), root);
	}

	@Override
	public Integer countCreditCustomerVoteByConditions(CreditCustomerVoteDto dto) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countCreditCustomerVoteByConditions"), root);
	}

	@Override
	public List<CreditCustomerVoteDto> queryCreditCustomerVoteByConditions(CreditCustomerVoteDto dto,PageDto<CreditCustomerVoteDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCreditCustomerVoteByConditions"), root);
	}

	// public final static int DEFAULT_BATCH_SIZE = 20;
	//
	// public Integer batchDeleteCreditCustomerVote(Integer[] ids) {
	// Assert.notNull(ids, "The ids can not be null");
	// int impacted = 0;
	// int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1)
	// / DEFAULT_BATCH_SIZE;
	// try {
	// for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
	// getSqlMapClient().startBatch();
	// int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
	// int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
	// endIndex = endIndex > ids.length ? ids.length : endIndex;
	// for (int i = beginIndex; i < endIndex; i++) {
	// impacted += getSqlMapClientTemplate().delete(
	// "creditCustomerVote.deleteCreditCustomerVote", ids[i]);
	// }
	// getSqlMapClient().executeBatch();
	// }
	// } catch (Exception e) {
	// throw new PersistLayerException("batch deleteCreditCustomerVote failed.",
	// e);
	// }
	// return impacted;
	// }
	//
	// public Integer batchUpdateCreditCustomerVoteCheckStatus(Integer[] ids,
	// String checkStatus) {
	// Assert.notNull(ids, "The ids can not be null");
	// Assert.notNull(checkStatus, "The checkStatus can not be null");
	// int impacted = 0;
	// Map<String, Object> map = new HashMap<String, Object>();
	// int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1)
	// / DEFAULT_BATCH_SIZE;
	// try {
	// for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
	// getSqlMapClient().startBatch();
	// int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
	// int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
	// endIndex = endIndex > ids.length ? ids.length : endIndex;
	// for (int i = beginIndex; i < endIndex; i++) {
	// map.put("id", ids[i]);
	// map.put("checkStatus", checkStatus);
	// impacted += getSqlMapClientTemplate().update(
	// "creditCustomerVote.updateCreditCustomerVoteCheckStatus", map);
	// }
	// getSqlMapClient().executeBatch();
	// }
	// } catch (Exception e) {
	// throw new
	// PersistLayerException("batch updateCreditCustomerVoteCheckStatus failed.",
	// e);
	// }
	// return impacted;
	// }
	//
	// public Integer insertCreditCustomerVote(CreditCustomerVoteDo
	// creditCustomerVote) {
	// Assert.notNull(creditCustomerVote,
	// "The creditCustomerVote can not be null");
	// return Integer.valueOf(getSqlMapClientTemplate().insert(
	// "creditCustomerVote.insertCreditCustomerVote",
	// creditCustomerVote).toString());
	// }
	//
	// @SuppressWarnings("unchecked")
	// public List<CreditCustomerVoteDo> selectCreditCustomerVote(PageDto
	// pageDto,
	// CreditCustomerVoteDo creditCustomerVote) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("pageDto", pageDto);
	// map.put("creditCustomerVote", creditCustomerVote);
	// return
	// getSqlMapClientTemplate().queryForList("creditCustomerVote.selectCreditCustomerVote",
	// map);
	// }
	//
	// public CreditCustomerVoteDo selectCreditCustomerVoteById(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return (CreditCustomerVoteDo) getSqlMapClientTemplate().queryForObject(
	// "creditCustomerVote.selectCreditCustomerVoteById", id);
	// }
	//
	// public Integer selectCreditCustomerVoteCount(PageDto pageDto,
	// CreditCustomerVoteDo creditCustomerVote) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("pageDto", pageDto);
	// map.put("creditCustomerVote", creditCustomerVote);
	// return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
	// "creditCustomerVote.selectCreditCustomerVoteCount", map).toString());
	// }
	/**
	 * 查询出一共有多少条评价
	 */
	// @SuppressWarnings("unchecked")
	// public List<CreditDTO> selectCreditCustomerDTO(CreditDTO creditDTO) {
	//
	// return
	// getSqlMapClientTemplate().queryForList("creditCustomerVote.selectCreditCustomerVoteDTO",creditDTO);
	// }
	//	
	// /**
	// * 查询一共有多少条作出的评价
	// */
	// @SuppressWarnings("unchecked")
	// public List<CreditDTO> selectCreditCustomerVoteReplyDTO(CreditDTO
	// creditDTO){
	// return
	// getSqlMapClientTemplate().queryForList("creditCustomerVote.selectCreditCustomerVoteReplyDTO",creditDTO);
	// }

	// /**
	// * 查询评价记录总数
	// */
	// public Integer countPageCreditCustomerVoteId(CreditDTO creditDTO){
	// return (Integer)
	// getSqlMapClientTemplate().queryForObject("creditCustomerVote.countPageCreditCustomerVoteId",creditDTO);
	// }
	//	
	// /**
	// * 查询我作出的评价记录总数
	// */
	// public Integer countPageCreditCustomerVoteReplyId(CreditDTO creditDTO){
	// return (Integer)
	// getSqlMapClientTemplate().queryForObject("creditCustomerVote.countPageCreditCustomerVoteReplyId",creditDTO);
	// }
	//	
	// /**
	// * 评价回复
	// */
	// public Integer insertCreditCustomerVoteReplyDO(CreditCustomerVoteReplyDO
	// creditCustomerVoteReplyDO){
	// return (Integer)
	// getSqlMapClientTemplate().insert("creditCustomerVote.insertCreditCustomerVoteReply",creditCustomerVoteReplyDO);
	// }
}
