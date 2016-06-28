/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.dao.trade.MessageDao;
import com.zz91.ep.admin.dao.trade.TradeBuyDao;
import com.zz91.ep.admin.dao.trade.TradeSupplyDao;
import com.zz91.ep.admin.service.trade.MessageService;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.util.Assert;

/**
 * @author totly
 *
 * created on 2011-9-16
 */
@Component("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private CompProfileDao compProfileDao;

    @Resource
    private TradeSupplyDao tradeSupplyDao;

    @Resource
    private TradeBuyDao tradeBuyDao;
    
    @Override
    public Integer sendMessageByUser(Message message, Integer messageType) {
        Assert.notNull(message, "the message can not be null");
        Assert.notNull(message.getUid(), "the uid can not be null");
        Assert.notNull(message.getCid(), "the cid can not be null");
        Assert.notNull(message.getTargetType(), "the target_type can not be null");
        if (messageType == 0) {
            tradeSupplyDao.updateMessageCountById(message.getTargetId());
        } else if (messageType == 1) {
            tradeBuyDao.updateMessageCountById(message.getTargetId());
        } else if (messageType == 2) {
            compProfileDao.updateMessageCountById(message.getTargetId());
        } else if (messageType == 3) {

        } else if (messageType == 4) {

        } else {
           
        }
        if (message.getDelSendStatus()==null){
        	message.setDelSendStatus((short)0);
        }
        return messageDao.insertMessage(message);
    }

//    @Override
//    public Integer updateMessageBysendStatus(Integer id, Integer cid, Integer sendStatus) {
//        Assert.notNull(id, "the id can not be null");
//        Assert.notNull(cid, "the cid can not be null");
//        Assert.notNull(sendStatus, "the sendStatus can not be null");
//        return messageDao.updateMessageBysendStatus(id, cid, sendStatus);
//    }

//    @Override
//    public PageDto<MessageDto> queryMessagesByCompany(Integer cid,
//            Integer sendStatus, Integer tagetId, Integer type,
//            Integer replyStatus, PageDto<MessageDto> page) {
//        Assert.notNull(cid, "the cid can not be null");
//        Assert.notNull(sendStatus, "the sendStatus can not be null");
//        Assert.notNull(type, "the type can not be null");
//        Assert.notNull(page, "the page can not be null");
//        page.setRecords(messageDao.queryMessagesByCompany(cid, sendStatus, tagetId, type, replyStatus, page));
//        page.setTotals(messageDao.queryMessagesByCompanyCount(cid, sendStatus, tagetId, type, replyStatus));
//        return page;
//    }

//    @Override
//    public Integer updateReplyMessage(String replyMessage, Integer cid, Integer id) {
//        Assert.notNull(id, "the id can not be null");
//        Assert.notNull(id, "the cid can not be null");
//        if(replyMessage == null){
//            replyMessage="";
//        }
//        return messageDao.updateReplyMessage(replyMessage, cid, id);
//    }

//	@Override
//	public Integer queryReplyMessagesCount(Integer cid) {
//		return messageDao.queryReplyMessagesCount(cid);
//	}

	@Override
	public PageDto<MessageDto> pageAllMessage(Integer cid,String targetType, Integer targetId,
			Integer targetCid, PageDto<MessageDto> page) {
		if (page.getSort()==null){
			page.setSort("ms.gmt_created");
		}
		if (page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(messageDao.queryAllMessage(cid,targetType, targetId, targetCid, page));
		page.setTotals(messageDao.queryAllMessageCount(cid,targetType, targetId, targetCid));
		return page;
	}

	@Override
	public Message queryShortMessageById(Integer id) {
		return messageDao.queryShortMessageById(id);
	}

	@Override
	public Integer queryNoReadCount(Integer cid,Short status) {
		 Assert.notNull(cid, "the cid can not be null");
		return messageDao.queryNoReadCount(cid,status);
	}

	@Override
	public Integer queryCompfileMessageCount() {
		return messageDao.queryCompfileMessageCount();
	}

	@Override
	public List<Message> queryCompfileMessageTime(Integer start, Integer size) {
		return messageDao.queryCompfileMessageTime(start, size);
	}
}