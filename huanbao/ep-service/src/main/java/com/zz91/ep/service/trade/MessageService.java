/*
 * 文件名称：MessageService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：留言信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
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
	 * 函数名称：sendMessageByUser
	 * 功能描述：创建不同的留言信息
	 * 输入参数：messageType
	 * 			0:给供应信息留言;
	 *			1:给求购信息留言;
     *          2:给公司留言;
     *          3:给网站留言，反馈问题/建议;
     *          4:给CRM/小秘书留言)
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
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
    * readStatus  阅读状态
    * 			 0未读 1已读
   */
   public PageDto<MessageDto> queryMessagesByCompany(Integer cid, Integer sendStatus,Integer tagetId, Integer type, Integer replyStatus,Integer readStatus, PageDto<MessageDto> page);
   
   
   /**
    * 
    * 函数名称：updateMessageBysendStatus
    * 功能描述：删除留言
    * 输入参数：@param test1 参数1
    * 　　　　　.......
    * 　　　　　@param test2 参数2
    * 异　　常：[按照异常名字的字母顺序]
    * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
    * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
    */
   public Integer updateMessageBysendStatus(Integer id, Integer cid, Integer sendStatus);
   
   /**
    * 
    * 函数名称：updateReplyMessage
    * 功能描述：回复留言
    * 输入参数：@param test1 参数1
    * 　　　　　.......
    * 　　　　　@param test2 参数2
    * 异　　常：[按照异常名字的字母顺序]
    * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
    * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
    */
   public Integer updateReplyMessage(String replyMessage, Integer cid, Integer id);

	/**
	 * 函数名称：queryNewestMessage
	 * 功能描述：创建最新的留言信息
	 * 输入参数：
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<MessageDto> queryNewestMessage(Integer messageType, Integer size);
	
	/**
	 * sendStatus：操作的留言
	 * 0：发出 1：收到
	 * @param id
	 * @param sendStatus
	 * @return
	 */
	public MessageDto queryMessageByIdAndSendStatus(Integer id,Integer sendStatus);
	

}