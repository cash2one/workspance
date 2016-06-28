/*
 * 文件名称：MessageDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.MessageDao;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;

/**
 * 项目名称：中国环保网 模块编号：数据操作Dao层 模块描述：留言信息实现类。 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Component("messageDao")
public class MessageDaoImpl extends BaseDao implements MessageDao {

	final static String SQL_PREFIX = "message";

	@Override
	public Integer insertMessage(Message message) {
		return (Integer) getSqlMapClientTemplate().insert(
				buildId(SQL_PREFIX, "insertMessage"), message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageDto> queryMessagesByCompany(Integer cid,
			Integer sendStatus, Integer targetId, Integer type,
			Integer replyStatus, Integer readStatus ,PageDto<MessageDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("sendStatus", sendStatus);
		root.put("targetId", targetId);
		root.put("type", type);
		root.put("replyStatus", replyStatus);
		root.put("readStatus", readStatus);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryMessagesByCompany"), root);
	}

	@Override
	public Integer queryMessagesByCompanyCount(Integer cid, Integer sendStatus,
			Integer tagetId, Integer type, Integer replyStatus,Integer readStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("sendStatus", sendStatus);
		root.put("tagetId", tagetId);
		root.put("type", type);
		root.put("replyStatus", replyStatus);
		root.put("readStatus", readStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryMessagesByCompanyCount"), root);
	}

	@Override
	public Integer updateMessageBysendStatus(Integer id, Integer cid,
			Integer sendStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("sendStatus", sendStatus);
		return getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateMessageBysendStatus"), root);
	}

	@Override
	public Integer updateReplyMessage(String replyMessage, Integer cid,
			Integer id) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("replyMessage", replyMessage);
		return (Integer) getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateReplyMessage"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MessageDto> queryNewestMessage(Integer messageType, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("messageType", messageType);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryNewestMessage"), root);
	}

	@Override
	public MessageDto queryMessageById(Integer id,Integer sendStatus) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("sendStatus", sendStatus);
		return (MessageDto)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMessageById"), map);
	}

	@Override
	public Integer updateReadStatus(Integer id ,Integer readStatus) {
		Map<String, Integer> root = new HashMap<String, Integer>();
		root.put("id", id);
		root.put("readStatus", readStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateReadStatus"),root);
	}

	@Override
	public Integer queryMessageCountByReplyAndRead(Integer id,
			Integer raplyStatus, Integer readStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("raplyStatus", raplyStatus);
		root.put("readStatus", readStatus);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMessageCountByReplyAndRead"),root);
	}


}