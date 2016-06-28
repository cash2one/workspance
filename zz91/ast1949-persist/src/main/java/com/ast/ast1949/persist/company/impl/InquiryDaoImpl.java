/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25
 */
package com.ast.ast1949.persist.company.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.InquiryDao;
import com.ast.ast1949.util.DateUtil;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
@Component("inquiryDao")
public class InquiryDaoImpl extends BaseDaoSupport implements InquiryDao {
	
	final static Integer DEFAULT_BATCH_SIZE = 100;
	
	final static String SQL_PREFIX = "inquiry";
	
	@Override
	public Integer countUnviewedInquiry(String beInquiredType,
			String receiverAccount, Integer companyId) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("beInquiredType", beInquiredType);
		root.put("receiverAccount", receiverAccount);
//		root.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countUnviewedInquiry"), root);
	}

	@Override
	public Integer deleteBlackList(Integer companyId, String account, Integer blackedCompanyId) {
		Map<String,Object> root=new HashMap<String, Object>();
//		root.put("companyId", companyId);
		root.put("account", account);
		root.put("blackedCompanyId", blackedCompanyId);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteBlackList"),root);
	}

	@Override
	public Integer insertBlackList(Integer companyId, String account, Integer blackedCompanyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("account", account);
		root.put("blackedCompanyId", blackedCompanyId);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertBlackList"), root);
	}
	
	@Override
	public Integer insertInquiry(Inquiry inquiry) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertInquiry"), inquiry);
	}
	
	@Override
	public Integer batchInsert(List<Inquiry> list){
		int impacted = 0;
		int batchNum = (list.size() + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > list.size() ? list.size(): endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					insertInquiry(list.get(i));
					impacted++;
				}
				getSqlMapClient().executeBatch();
			}
		} catch (SQLException e) {
		}
		return impacted;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryDto> queryConversation(String conversation, Integer max) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("conversationGroup", conversation);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryConversation"),root);
	}
	
	@Override
	public Inquiry queryInquiry(Integer inquiryId) {
		return (Inquiry) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryInquiry"), inquiryId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryDto> queryInquiryByUser(Inquiry inquiry,PageDto<InquiryDto> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("inquiry", inquiry);
		root.put("page",page);
//		root.put("startDate",DateUtil.getDateAfter(new Date(), Calendar.YEAR, -1), "yyyy-mm");
//		root.put("endDate",DateUtil.toString(DateUtil.getDateAfter(new Date(), Calendar.YEAR, 0), "yyyy"));
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryByUser"),root);
	}
	
	@Override
	public Integer queryInquiryByUserCount(Inquiry inquiry) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("inquiry", inquiry);
//		root.put("startDate",DateUtil.toString(DateUtil.getDateAfter(new Date(), Calendar.YEAR, -1), "yyyy"));
//		root.put("endDate",DateUtil.toString(DateUtil.getDateAfter(new Date(), Calendar.YEAR, 0), "yyyy"));
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryByUserCount"), root);
	}
	
	@Override
	public Integer updateInquiryGroup(Integer inquiryId, Integer groupId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", inquiryId);
		root.put("groupId", groupId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateInquiryGroup"),root);
	}
	
	@Override
	public Integer updateIsRubbish(Integer inquiryId, String isRubbish) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id",inquiryId);
		root.put("isRubbish",isRubbish);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsRubbish"), root);
	}
	
	@Override
	public Integer updateReceiverDel(Integer inquiryId, String isReceiverDel) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", inquiryId);
		root.put("isReceiverDel",isReceiverDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateReceiverDel"), root);
	}
	
	@Override
	public Integer updateSenderDel(Integer inquiryId, String isSenderDel) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", inquiryId);
		root.put("isSenderDel", isSenderDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateSenderDel"), root);
	}
	
	@Override
	public Integer updateViewed(Integer inquiryId, String isViewed) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", inquiryId);
		root.put("isViewed", isViewed);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateViewed"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Inquiry> queryScrollInquiry(String batchSendType, int size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("batchSendType", batchSendType);
		root.put("limit", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryScrollInquiry"),root);
	}
	
	@Override
	public Integer countBlackedCompany(String account, Integer blockedCompanyId){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("account", account);
		root.put("blackedCompanyId", blockedCompanyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countBlackedCompany"), root);
	}

	@Override
	public Integer updateIsReplyed(Integer inquiryId, String isReplyed) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("inquiryId", inquiryId);
		root.put("isReplyed", isReplyed);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsReplyed"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inquiry> queryAllConversation(String senderAccount,
			String receiverAccount, PageDto<Inquiry> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("senderAccount", senderAccount);
		root.put("receiverAccount", receiverAccount);
		root.put("page", page);
		root.put("dateline", DateUtil.getDateAfterMonths(new Date(), -12));
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllConversation"), root);
	}

	@Override
	public Integer queryAllConversationCount(String senderAccount,
			String receiverAccount) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("senderAccount", senderAccount);
		root.put("receiverAccount", receiverAccount);
		root.put("dateline", DateUtil.getDateAfterMonths(new Date(), -12));
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAllConversationCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inquiry> queryInquiryBySenderUser(String senderAccount,
			PageDto<InquiryDto> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("senderAccount", senderAccount);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryBySenderUser"), root);
	}

	@Override
	public Integer queryInquiryBySenderUserCount(String senderAccount) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("senderAccount", senderAccount);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryBySenderUserCount"), root);
	}

	@Override
	public Inquiry queryDownMessageById(Integer id, String receiverAccount, String sendAccount,String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("receiverAccount", receiverAccount);
		root.put("sendAccount", sendAccount);
		root.put("type", type);
		return (Inquiry) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryDownMessageById"), root);
	}

	@Override
	public Inquiry queryOnMessageById(Integer id, String receiverAccount, String sendAccount,String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("receiverAccount", receiverAccount);
		root.put("sendAccount", sendAccount);
		root.put("type", type);
		return (Inquiry) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOnMessageById"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inquiry> queryInquiryByAdmin(Inquiry inquiry,
			PageDto<Inquiry> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("inquiry", inquiry);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryByAdmin"), root);
	}

	@Override
	public Integer queryInquiryByAdminCount(Inquiry inquiry) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("inquiry", inquiry);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryByAdminCount"), root);
	}

	@Override
	public Integer countInquiryByList(InquiryDto inquiryDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inquiryDto", inquiryDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countInquiryByList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inquiry> queryInquiryByList(InquiryDto inquiryDto,
			PageDto<InquiryDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inquiryDto", inquiryDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryByList"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryDto> queryInquiryListByUser(Inquiry inquiry){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("inquiry", inquiry);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryListByUser"),root);
	}
	
	@Override
	public Integer countInquiryByCondition(String from ,String to ,String account){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		map.put("account", account);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "countInquiryByCondition"), map).size();
		
	}
	
	@Override
	public Inquiry queryExportInquiryFromProduct(String sendAccount,Integer targetId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sendAccount", sendAccount);
		map.put("targetId", targetId);
		return (Inquiry) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryExportInquiryFromProduct"), map);
	}

	@Override
	public Integer countByBeinquiredTypeAndTypeID(String beInquiredType, Integer beInquiredId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beInquiredType", beInquiredType);
		map.put("beInquiredId", beInquiredId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countByBeinquiredTypeAndTypeID"), map);
	}

	@Override
	public Integer countInquiryByCidAtime(Integer targetId, String from,String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", targetId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countInquiryByCidAtime"), map); 
	}
	
	/**********************以下老代码***********/
//	final private int DEFAULT_BATCH_SIZE = 20;
//	private final static String sqlPreFix = "inquiry";
//
//	@Override
//	public List<InquiryBlackList> queryInquiryBlackListByAccountId(Integer accountId) {
//		if (accountId == null)
//			return new ArrayList<InquiryBlackList>();
//		return getSqlMapClientTemplate().queryForList(
//				addSqlKeyPreFix(sqlPreFix, "queryInquiryBlankListByAccountId"), accountId);
//	}
//
//	@Override
//	public void deleteInquiryBlankListByAccountIdAndBlackAccountId(Integer accountId,
//			Integer blackAccountId) {
//		if (accountId != null && blackAccountId != null) {
//			Map paramMap = new HashMap();
//			paramMap.put("accountId", accountId);
//			paramMap.put("blackAccountId", blackAccountId);
//			getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteInquiryBlankListByAccountIdAndBlackAccountId"),
//					paramMap);
//		}
//	}
//
//	@Override
//	public void deleteInquiryBlackListByInquiryIds(List<Integer> inquiryIds) {
//		if (inquiryIds != null&&inquiryIds.size()>0){
//			Map paramMap = new HashMap();
//			paramMap.put("inquiryIds", inquiryIds);
//			List<InquiryBlackList> list = getSqlMapClientTemplate().queryForList(
//					addSqlKeyPreFix(sqlPreFix, "queryInquirySenderAndReceiverList"), paramMap);
//			for (InquiryBlackList a : list) {
//				//System.out.println(a.getAccountId()+","+a.getBlackAccountId());
//				deleteInquiryBlankListByAccountIdAndBlackAccountId(a.getAccountId(),
//						a.getBlackAccountId());
//			}
//		}
//	}
//
//	@Override
//	public Integer insertInquiry(InquiryDO inquiryDO) {
//		List<InquiryBlackList> list = queryInquiryBlackListByAccountId(inquiryDO.getReceiverId());
//		for (InquiryBlackList a : list) {
//			if (a.getBlackAccountId().equals(inquiryDO.getSenderId()))
//				inquiryDO.setIsRubbish("1");
//		}
//		inquiryDO.setSqlKey(addSqlKeyPreFix(sqlPreFix, "insertInquiry"));
//		return insert(inquiryDO);
//	}
//
//	@Override
//	public Set<Integer> insertInquirys(List<InquiryDO> inquiryDOList) {
//		Set<Integer> impacted = new HashSet<Integer>();
//		for (InquiryDO inquiry : inquiryDOList) {
//			try {
//				inquiry.setSqlKey(addSqlKeyPreFix(sqlPreFix, "insertInquiry"));
//				impacted.add(insert(inquiry));
//			} catch (Exception e) {}
//		}
//		return impacted;
//	}
//
//	@Override
//	public Integer updateInquiry(InquiryDO inquiryDO) {
//		inquiryDO.setSqlKey(addSqlKeyPreFix(sqlPreFix, "updateInquiry"));
//		return update(inquiryDO);
//	}
//
//	@Override
//	public Integer updateInquiryExportStatus(List<Integer> inquiryIds, String exportStatus,
//			String exportPerson) {
//		if ("0".equals(exportStatus))
//			exportPerson = null;
//		Map paramMap = new HashMap();
//		paramMap.put("inquiryIds", inquiryIds);
//		paramMap.put("exportStatus", exportStatus);
//		paramMap.put("exportPerson", exportPerson);
//		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateInquiryExportStatus"), paramMap);
//	}
//
//	@Override
//	public Integer updateInquiryGroup(List<Integer> inquiryIds, Integer groupId) {
//		Map paramMap = new HashMap();
//		paramMap.put("inquiryIds", inquiryIds);
//		paramMap.put("groupId", groupId);
//		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateInquiryGroup"), paramMap);
//	}
//
//	@Override
//	public Integer updateInquiryIsDel(String delType, List<Integer> inquiryIds, String isDel) {
//		Map paramMap = new HashMap();
//		paramMap.put("inquiryIds", inquiryIds);
//		paramMap.put("isDel", isDel);
//		if ("1".equals(delType))
//			return getSqlMapClientTemplate()
//					.update(addSqlKeyPreFix(sqlPreFix, "updateInquiryIsSenderDel"), paramMap);
//		if ("2".equals(delType))
//			return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateInquiryIsReceiverDel"),
//					paramMap);
//		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateInquiryAllDel"), paramMap);
//	}
//
//	@Override
//	public Integer updateInquiryIsRubbish(List<Integer> inquiryIds, String isRubbish) {
//		Map paramMap = new HashMap();
//		paramMap.put("inquiryIds", inquiryIds);
//		paramMap.put("isRubbish", isRubbish);
//		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateInquiryIsRubbish"), paramMap);
//	}
//
//	@Override
//	public void updateInquirySenderToBlackList(List<Integer> inquiryIds) {
//		if (inquiryIds == null || inquiryIds.size() < 1)
//			return;
//		Map paramMap = new HashMap();
//		paramMap.put("inquiryIds", inquiryIds);
//		getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "insertInquiryBlackListByInquiryIds"), paramMap);
//	}
//
//	@Override
//	public Integer updateInquiryIsViewed(List<Integer> inquiryIds, String isViewed) {
//		Map paramMap = new HashMap();
//		paramMap.put("inquiryIds", inquiryIds);
//		paramMap.put("isViewed", isViewed);
//		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateInquiryIsViewed"), paramMap);
//	}
//
//	@Override
//	public Integer deleteInquiryById(Integer id) {
//		return delete(addSqlKeyPreFix(sqlPreFix, "deleteInquiryById"), id);
//	}
//
////	@Override
////	public InquiryDO queryInquiryById(Integer id) {
////		return (InquiryDO) query(addSqlKeyPreFix(sqlPreFix, "queryInquiryById"), id);
////	}
//
////	@Override
////	public List<InquiryDO> queryInquiryListByBeInquiredId(String beInquiredType,
////			Integer beInquiredId) {
////		Map paramMap = new HashMap();
////		paramMap.put("beInquiredType", beInquiredType);
////		paramMap.put("beInquiredId", beInquiredId);
////		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryInquiryListByBeInquiredId"),
////				paramMap);
////	}
//
////	@Override
////	public Integer queryInquiryListByBeInquiredIdCount(String beInquiredType, Integer beInquiredId) {
////		Map paramMap = new HashMap();
////		paramMap.put("beInquiredType", beInquiredType);
////		paramMap.put("beInquiredId", beInquiredId);
////		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix,
////				"queryInquiryListByBeInquiredIdCount"), paramMap);
////	}
//
////	@Override
////	public List<InquiryDO> queryInquiryListByReceiverId(Integer receiverId) {
////		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryInquiryByBeInquiredId"),
////				receiverId);
////	}
//
////	@Override
////	public Integer queryInquiryListByReceiverIdCount(Integer receiverId) {
////		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix,
////				"queryInquiryListByReceiverIdCount"), receiverId);
////	}
//
//	@Override
//	public Integer queryInquiryUnviewedByReciverIdCount(String beInquiredType, Integer receiverId) {
//		Map paramMap = new HashMap();
//		paramMap.put("beInquiredType", beInquiredType);
//		paramMap.put("receiverId", receiverId);
//		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix,
//				"queryInquiryUnviewedByReciverIdCount"), paramMap);
//	}
//
//	@Override
//	public InquiryDTO queryInquiryDetailById(Integer id) {
//		return (InquiryDTO) getSqlMapClientTemplate().queryForObject(
//				"inquiry.queryInquiryDetailById", id);
//	}
//
//	@Override
//	public PaginationResult queryPaginationInquiryListByReceiverAndSender(Integer senderId,
//			Integer receiverId, PageDto page) {
//		String where = " (iq.sender_id=?1 and iq.receiver_id=?2) or ( iq.sender_id=?2 and iq.receiver_id=?1 )";
//		LinkedHashMap<String, String> orderBy = null;
//		if (page.getSort() != null) {
//			orderBy = new LinkedHashMap<String, String>();
//			orderBy.put(page.getSort(), page.getDir());
//		}
//		return queryPaginationData(
//				addSqlKeyPreFix(sqlPreFix, "queryInquiryListByReceiverAndSender"), page
//						.getStartIndex(), page.getPageSize(), where, new Object [] {senderId,
//						receiverId}, orderBy);
//	}
//
//	@Override
//	public PaginationResult queryPaginationInquiryListByInquiryCondition(InquiryDO inquiry,
//			PageDto page) {
//		Map<String, Object[]> whereStmtModule = getWhereStmtModuleFromInquiry(inquiry);
//		String where = "";
//		Object[] params = null;
//		for (Entry<String, Object[]> entry : whereStmtModule.entrySet()) {
//			where = entry.getKey();
//			params = entry.getValue();
//		}
//		LinkedHashMap<String, String> orderBy = null;
//		if (page.getSort() != null) {
//			orderBy = new LinkedHashMap<String, String>();
//			orderBy.put(page.getSort(), page.getDir());
//		}
//		return queryPaginationData(
//				addSqlKeyPreFix(sqlPreFix, "queryInquiryListByInquiryCondition"), page
//						.getStartIndex(), page.getPageSize(), where, params, orderBy);
//	}
//
//	private Map<String, Object[]> getWhereStmtModuleFromInquiry(InquiryDO inquiry) {
//		Map<String, Object[]> rest = new HashMap<String, Object[]>();
//		StringBuilder whereBuf = new StringBuilder();
//		int pos = 1;
//		Object[] params = new Object [10];
//		if (inquiry.getSenderId() != null) {//发件人
//			whereBuf.append(" iq.sender_id=?").append(pos);
//			params[pos - 1] = inquiry.getSenderId();
//			pos++;
//
//		}
//		if (inquiry.getReceiverId() != null) {//收件人ID
//			if (pos > 1)
//				whereBuf.append(" and");
//			whereBuf.append(" iq.receiver_id=?").append(pos);
//			params[pos - 1] = inquiry.getReceiverId();
//			pos++;
//		}
//		if (inquiry.getGroupId() != null) {//分组条件
//			if (pos > 1)
//				whereBuf.append(" and");
//			if (inquiry.getGroupId() == -1) {
//				whereBuf.append(" iq.group_id>2");
//			} else {
//				whereBuf.append(" iq.group_id=?").append(pos);
//				params[pos - 1] = inquiry.getGroupId();
//				pos++;
//			}
//		}
//		if (StringUtils.isNotEmpty(inquiry.getIsViewed())) {//阅读标记
//			if (pos > 1)
//				whereBuf.append(" and");
//			whereBuf.append(" iq.is_viewed=?").append(pos);
//			params[pos - 1] = inquiry.getIsViewed();
//			pos++;
//		}
//		if (StringUtils.isNotEmpty(inquiry.getIsRubbish())) {//垃圾信息标记
//			if (pos > 1)
//				whereBuf.append(" and");
//			whereBuf.append(" iq.is_rubbish=?").append(pos);
//			params[pos - 1] = inquiry.getIsRubbish();
//			pos++;
//		}
//		//		if (pos > 1)
//		//			whereBuf.append(" and");
//		//		whereBuf.append(" (iq.is_receiver_del='0' or iq.is_sender_del='0') ");
//		if (StringUtils.isNotEmpty(inquiry.getIsReceiverDel())) {//收件人标记删除
//			if (pos > 1)
//				whereBuf.append(" and");
//			whereBuf.append(" iq.is_receiver_del=?").append(pos);
//			params[pos - 1] = inquiry.getIsReceiverDel();
//			pos++;
//		} else if (StringUtils.isNotEmpty(inquiry.getIsSenderDel())) {//发件人删除标记
//			if (pos > 1)
//				whereBuf.append(" and");
//			whereBuf.append(" iq.is_sender_del=?").append(pos);
//			params[pos - 1] = inquiry.getIsSenderDel();
//			pos++;
//		}
//
//		rest.put(whereBuf.toString(), params);
//		return rest;
//	}
//
//	@Override
//	public Integer countInquiryByConditions(InquiryDTO inquiryDto) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(
//				"inquiry.countInquiryByConditions", inquiryDto);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<InquiryDTO> queryInquiryByConditions(InquiryDTO inquiryDto) {
//		return getSqlMapClientTemplate().queryForList("inquiry.queryInquiryByConditions",
//				inquiryDto);
//	}
//
//	@Override
//	public InquiryDO queryInquiryContentById(Integer id) {
//		Assert.notNull(id, "The companyId can not be null");
//		return (InquiryDO) getSqlMapClientTemplate().queryForObject(
//				"inquiry.queryInquiryContentById", id);
//	}
//
//	@Override
//	public Integer setInquiryUngroupedByGroupId(Integer id) {
//		Assert.notNull(id, "The id can not be null");
//		return getSqlMapClientTemplate().update("inquiry.setInquiryUngroupedByGroupId", id);
//	}
	
}
