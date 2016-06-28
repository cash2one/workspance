/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface MessageService {

    public static final int TARGET_SUPPLY = 0;
     
    public static final int TARGET_BUY = 1;
     
    public static final int TARGET_COMP = 2;
     
    public static final int TARGET_EP = 3;
     
    public static final int TARGET_VIP = 4;
    
    public static final int SENDSTATUS_SEND = 0;
    
    public static final int SENDSTATUS_RECEIVE = 1;
    
    public static final int REPLYSTATUS_NOT = 0;
    
    public static final int REPLYSTATUS_YES = 1;
    /**
     * 创建不同的留言信息 (0:给供应信息留言;1:给求购信息留言;
     *                 2:给公司留言;3:给网站留言，反馈问题/建议;
     *                 4:给CRM/小秘书留言)
     */
    public Integer sendMessageByUser(Message message, Integer messageType);

    /**
     * 根据公司查询留言信息(分页)
     * cid：公司编号
     * type：消息类型(0：供应留言 1：求购留言 2：公司留言 3：网站留言/问题反馈 4：CRM留言/小秘书)
     * tagetId:目标（供求信息）编号（null时不筛选）
     * replyStatus：回复状态（null时为所有状态）
     *             0：未回复 1：已回复
     * sendStatus：操作的留言
     *             0：发出 1：收到
    */
//    public PageDto<MessageDto> queryMessagesByCompany(Integer cid, Integer sendStatus,Integer tagetId, Integer type, Integer replyStatus, PageDto<MessageDto> page);

    /**
     * 根据公司查询未回复数
     * cid：公司编号
    */
//    public Integer queryReplyMessagesCount(Integer cid);

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
     * 查看留言信息
     * @param cid 
     * @param targetType 留言类型
     * @param targetId 供求留言(供求ID)
     * @param targetCid 公司id
     * @param page
     * @return
     */
    public PageDto<MessageDto> pageAllMessage(Integer cid, String targetType,Integer targetId,Integer targetCid,PageDto<MessageDto> page);
    /**
     * 查询留言标题和留言内容
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
    public Integer queryNoReadCount(Integer cid,Short status);

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
	public List<Message> queryCompfileMessageTime(
			Integer start, Integer size);

}