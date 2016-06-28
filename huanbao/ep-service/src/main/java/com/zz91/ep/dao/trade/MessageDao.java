/*
 * 文件名称：MessageDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Dao层
 * 模块描述：留言信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface MessageDao {

	/**
	 * 函数名称：insertMessage
	 * 功能描述：创建留言信息
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public Integer insertMessage(Message message);

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
     * 根据公司查询留言信息(分页)
     * cid：公司编号
     * type：消息类型
     * tagetId:目标（供求信息）编号（null时不筛选）
     * replyStatus：回复状态（null时为所有状态）
     *             0：未回复 1：已回复
     * sendStatus：操作的留言
     *             0：发出 1：收到
    */
    public List<MessageDto> queryMessagesByCompany(Integer cid, Integer sendStatus,Integer tagetId, Integer type, Integer replyStatus,Integer readStatus, PageDto<MessageDto> page);

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
    public Integer queryMessagesByCompanyCount(Integer cid, Integer sendStatus,Integer tagetId, Integer type, Integer replyStatus,Integer readStatus);
    
    /**
     * 删除留言信息（逻辑删除）
     * id：留言编号
     * sendStatus：操作的留言
     *             0：发出 1：收到
     */
    public Integer updateMessageBysendStatus(Integer id, Integer cid, Integer sendStatus);

    /**
     * 回复留言
     */
    public Integer updateReplyMessage(String replyMessage, Integer cid, Integer id);
    
    /**
     * 查看留言
     * @param id
     * @return
     */
    public MessageDto queryMessageById(Integer id,Integer sendStatus);
    
    /**
     * 修改是否阅读
     * @param readStatus
     * @return
     */
    public Integer updateReadStatus(Integer id,Integer readStatus);
    
    /**
     * 统计已回复且未读的留言
     * @param id
     * @param raplyStatus
     * @param readStatus
     * @return
     */
    public Integer queryMessageCountByReplyAndRead(Integer id,Integer raplyStatus,Integer readStatus );
}