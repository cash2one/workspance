/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.MessageDao;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("messageDao")
public class MessageDaoImpl extends BaseDao implements MessageDao {

    final static String SQL_PREFIX = "message";

    @Override
    public Integer insertMessage(Message message) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertMessage"), message);
    }

//    @Override
//    public Integer updateMessageBysendStatus(Integer id, Integer cid, Integer sendStatus) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("sendStatus", sendStatus);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMessageBysendStatus"), root);
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<MessageDto> queryMessagesByCompany(Integer cid,
//            Integer sendStatus, Integer targetId, Integer type,
//            Integer replyStatus, PageDto<MessageDto> page) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("sendStatus", sendStatus);
//        root.put("targetId", targetId);
//        root.put("type", type);
//        root.put("replyStatus", replyStatus);
//        root.put("page", page);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMessagesByCompany"), root);
//    }

//    @Override
//    public Integer queryMessagesByCompanyCount(Integer cid, Integer sendStatus,
//            Integer tagetId, Integer type, Integer replyStatus) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("sendStatus", sendStatus);
//        root.put("tagetId", tagetId);
//        root.put("type", type);
//        root.put("replyStatus", replyStatus);
//        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMessagesByCompanyCount"), root);
//    }

//    @Override
//    public Integer updateReplyMessage(String replyMessage, Integer cid, Integer id) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("replyMessage", replyMessage);
//        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateReplyMessage"), root);
//    }

//	@Override
//	public Integer queryReplyMessagesCount(Integer cid) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryReplyMessagesCount"), cid);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageDto> queryAllMessage(Integer cid,String targetType, Integer targetId,
			Integer targetCid, PageDto<MessageDto> page) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("targetType", targetType);
		root.put("targetId", targetId);
		root.put("targetCid", targetCid);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllMessage"), root);
	}

	@Override
	public Integer queryAllMessageCount(Integer cid,String targetType, Integer targetId,
			Integer targetCid) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("targetType", targetType);
		root.put("targetId", targetId);
		root.put("targetCid", targetCid);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllMessageCount"), root);
	}

	@Override
	public Message queryShortMessageById(Integer id) {
		return (Message) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryShortMessageById"), id);
	}

	@Override
	public Integer queryNoReadCount(Integer cid,Short status) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("status", status);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNoReadCount"), root);
	}

	@Override
	public Integer queryCompfileMessageCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompfileMessageCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> queryCompfileMessageTime(Integer start, Integer size) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("start", start);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompfileMessageTime"),root);
	}
}