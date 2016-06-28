/*
 * 文件名称：MessageServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.trade.MessageDao;
import com.zz91.ep.dao.trade.TradeBuyDao;
import com.zz91.ep.dao.trade.TradeSupplyDao;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.util.Assert;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：留言信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("messageService")
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
    
    @Override
    public PageDto<MessageDto> queryMessagesByCompany(Integer cid,
            Integer sendStatus, Integer tagetId, Integer type,
            Integer replyStatus,Integer readStatus, PageDto<MessageDto> page) {
        Assert.notNull(cid, "the cid can not be null");
        Assert.notNull(sendStatus, "the sendStatus can not be null");
        Assert.notNull(page, "the page can not be null");
        page.setSort("ms.gmt_created");
		page.setDir("desc");
        page.setRecords(messageDao.queryMessagesByCompany(cid, sendStatus, tagetId, type, replyStatus,readStatus, page));
        page.setTotals(messageDao.queryMessagesByCompanyCount(cid, sendStatus, tagetId, type,readStatus,replyStatus));
        return page;
    }
    
    @Override
    public Integer updateMessageBysendStatus(Integer id, Integer cid, Integer sendStatus) {
        Assert.notNull(id, "the id can not be null");
        Assert.notNull(cid, "the cid can not be null");
        Assert.notNull(sendStatus, "the sendStatus can not be null");
        return messageDao.updateMessageBysendStatus(id, cid, sendStatus);
    }
    
    @Override
    public Integer updateReplyMessage(String replyMessage, Integer cid, Integer id) {
        Assert.notNull(id, "the id can not be null");
        Assert.notNull(cid, "the cid can not be null");
        if(replyMessage == null){
            replyMessage="";
        }
        return messageDao.updateReplyMessage(replyMessage, cid, id);
    }

	@Override
	public List<MessageDto> queryNewestMessage(Integer messageType, Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return messageDao.queryNewestMessage(messageType, size);
	}

	@Override
	public MessageDto queryMessageByIdAndSendStatus(Integer id,
			Integer sendStatus) {
		Assert.notNull(sendStatus, "the sendStatus can not be null");
		Assert.notNull(id, "the id can not be null");
		MessageDto dto = messageDao.queryMessageById(id, sendStatus);
			if(dto!=null){
				if(dto.getMessage().getReadStatus()==0){
					 messageDao.updateReadStatus(id,1);
				}
			}
		return dto;
	}



}