/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface MessageDao {

    /**
     * 创建留言信息
     */
    public Integer insertMessage(Message message);

    /**
     * 根据公司查询留言信息(分页)
     * cid：公司编号
     * type：消息类型
     * tagetId:目标（供求信息）编号（null时不筛选）
     * replyStatus：回复状态（null时为所有状态）
     *             0：未回复 1：已回复
     * sendStatus：操作的留言
     *             0：发出 1：收到
    */
//    public List<MessageDto> queryMessagesByCompany(Integer cid, Integer sendStatus,Integer tagetId, Integer type, Integer replyStatus, PageDto<MessageDto> page);

    /**
     * 根据公司查询留言信息数(分页)
     * cid：公司编号
     * type：消息类型
     * tagetId:目标（供求信息）编号（null时不筛选）
     * replyStatus：回复状态（null时为所有状态）
     *             0：未回复 1：已回复
     * sendStatus：操作的留言
     *             0：发出 1：收到
    */
//    public Integer queryMessagesByCompanyCount(Integer cid, Integer sendStatus,Integer tagetId, Integer type, Integer replyStatus);

    /**
     * 删除留言信息（逻辑删除）
     * id：留言编号
     * sendStatus：操作的留言
     *             0：发出 1：收到
     */
//    public Integer updateMessageBysendStatus(Integer id, Integer cid, Integer sendStatus);

    /**
     * 回复留言
     */
//    public Integer updateReplyMessage(String replyMessage, Integer cid, Integer id);

    /**
     * 查询未回复留言数
     * @param cid
     * @return
     */
//	public Integer queryReplyMessagesCount(Integer cid);
	/**
	 * 查看留言(不同类型:供应,求购,公司)
	 * @param cid 
	 * @param targetType
	 * @param targetId
	 * @param targetCid
	 * @param page
	 * @return
	 */
	public List<MessageDto> queryAllMessage(Integer cid, String targetType, Integer targetId,Integer targetCid, PageDto<MessageDto> page);
    /**
     * 查看留言信息数量
     * @param cid 
     * @param targetType
     * @param targetId
     * @param targetCid
     * @return
     */
    public Integer queryAllMessageCount(Integer cid, String targetType,Integer targetId,Integer targetCid);
    /**
     * 查询标题和留言信息
     * @param id
     * @return
     */
    public Message queryShortMessageById(Integer id);
    /**
     * 查看未读留言
     * @param cid
     * @param status 
     * @return
     */
	public Integer queryNoReadCount(Integer cid, Short status);

	/**
	 * 查询公司前一天询盘数量
	 * @return
	 */
	public Integer queryCompfileMessageCount();

	/**
	 * 查询公司询盘时间
	 * @param start
	 * @param size
	 * @return
	 */
	public List<Message> queryCompfileMessageTime(Integer start, Integer size);
}