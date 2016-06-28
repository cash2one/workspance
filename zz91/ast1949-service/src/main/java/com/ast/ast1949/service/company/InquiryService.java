/*
 *Modify By Rolyer 2010.03.08
 *	1、batchChangeExportStatus方法添加参数String exportPerson（操作人）
 * */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;

public interface InquiryService {
	
	/** 表示被询盘对象为供求信息 */
	final String BE_INQUIRYED_TYPE_DEFAULT = "0";
	/** 表示被询盘对象为公司 */
	final String BE_INQUIRYED_TYPE_COMPANY = "1";
	/** 表示被询盘对象为询盘本身 */
	final String BE_INQUIRYED_TYPE_INQUIRY = "2";
	/** 表示被询盘对象为企业报价 */
	final String BE_INQUIRYED_TYPE_COMPANY_PRICE = "3";
	/** 表示被询盘对象为塑料原料供求信息*/
	final String BE_INQUIRYED_TYPE_YUANLIAO = "4";
	
	public Integer inquiryByUser(Inquiry inquiry, Integer companyId);
	public Integer replyInquiry(Inquiry inquiry, Integer companyId);
	public Integer removeSentInquiry(Integer inquiryId, Boolean removeFlag);
	public Integer removeReceivedInquiry(Integer inquiryId, Boolean removeFlag);
	public Integer spamInquiry(Integer inquiryId, Boolean spamFlag);
	public Integer makeAsViewed(Integer inquiryId, Boolean viewFlag);
	public Integer countUnviewedInquiry(String beInquiredType, String receiveAccount, Integer companyId);
	public InquiryDto viewInquiry(Integer inquiryId);
	public PageDto<InquiryDto> pageInquiryByUser(Inquiry inquiry, PageDto<InquiryDto> page);
	public List<InquiryDto> queryConversation(String conversation);
	public boolean makeBlackList(Integer companyId, String account, Integer blackedCompanyId);
	public boolean removeBlackList(Integer companyId, String account, Integer blackedCompanyId);
	public List<InquiryDto> queryScrollInquiry();
	public Integer groupInquiry(Integer inquiryId, Integer groupId);
	public Integer ungroupInquiry(Integer inquiryId);
	public Inquiry queryOneInquiry(Integer id);
	
	public PageDto<Inquiry> pageAllConversation(String senderAccount,
			String receiverAccount, PageDto<Inquiry> page);
	
	public PageDto<InquiryDto> pageInquiryBySender(String senderAccount, PageDto<InquiryDto> page);
	public Inquiry queryOnMessageById(Integer id, String receiverAccount, String sendAccount,String type);
	public Inquiry queryDownMessageById(Integer id, String receiverAccount, String sendAccount,String type);
	
	public PageDto<Inquiry> pageInquiryByAdmin(Inquiry inquiry, PageDto<Inquiry> page);
	
	public PageDto<InquiryDto> pageInquiryByList(InquiryDto inquiryDto,PageDto<InquiryDto>page);
	
	public Integer countByBeinquiredTypeAndTypeID(String beInquiredType, Integer beInquiredId) ;
	
	public List<InquiryDto> queryInquiryListByUser(Inquiry inquiry);
	
	/**
	 * 从一条现有的询盘信息生成另一些供求信息的询盘
	 * 
	 * @param inquiryDO
	 *            :源询盘信息不可以为null,否则抛出异常<br/>
	 *            属性id表示源询盘信息的ID号,不可以为null或0<br/>
	 *            属性senderId表示源询盘的发送者ID,不可以为null<br/>
	 *            保存询盘时需要设置以下属性:<br/>
	 *            InquiryDO.batchSendType=@see {@link #BATCH_SEND_TYPE_1}<br/>
	 *            InquiryDO.beInquiryedType=@see {@link #BE_INQUIRY_TYPE_DEFAULT}
	 *            ,同时inquiryDO.beInquiryedId分别为productsArray里的ID<br/>
	 *            InquiryDO.inquiryedType=@see {@link #INQUIRY_TYPE_1},同时inquiryDO.inquiryedId为源询盘的ID<br/>
	 *            InquiryDO.senderId=源询盘发送者ID<br/>
	 *            InquiryDO.receiverId需要通过供求信息ID查询出来的供求信息所属于公司的ID<br/>
	 *            InquiryDO.exportStatus=@see {@link #STATUS_BEGIN_PROCESS}<br/>
	 *            询盘生成成功后需要设置源询盘信息的状态为@see {@link #STATUS_END_PROCESS}
	 * @param productsArray
	 *            :需要询盘信息的供求信息ID数组,类似"1|1,2|1,3|2"这样的字符串,用逗号分隔不同的(供求ID|公司ID),为""或null时不做处理
	 * @throws ServiceLayerException
	 */
	public void insertInquiryForProductsByInquiry(Integer productId,Inquiry inquiryDO, String productsArray,String account);
	
	/**
	 * 从一条现有的询盘信息生成另一些供求信息的询盘(对于原料供求而言)
	 * @param productId
	 * @param inquiry
	 * @param productsArray
	 * @param account
	 */
	public void insertInquiryForYuanLiaoByInquiry(Integer productId,
			Inquiry inquiry, String productsArray, String account);
	
	/******************以下老代码*****************/
	
//	/** 删除标记,永久删除 */
//	final String IS_DEL_FULL = "2";
	/** 删除标记,已删除 */
//	final String DELETED = "1";
//	/** 删除标记,未删除 */
//	final String UNDELETED = "0";
//	
//	/**删除发件信息**/
//	final String SENDER_DEL="1";
//	/**删除收件信息**/
//	final String RECEIVER_DEL="2";
//	/**后台删除信息**/
//	final String ALL_DEL="3";
//	
//	/** 阅读标记,己阅读 */
//	final String VIEWED = "1";
//	/** 阅读标记,未阅读 */
//	final String UNVIEWED = "0";
//	
//	/** 垃圾消息标记,是 */
//	final String RUBBISH = "1";
//	/** 垃圾消息标记,否 */
//	final String DISRUBBISH = "0";
//	
//	/** 表示询盘信息来源为网站客户 */
//	final String INQUIRYED_TYPE_DEFAULT = "0";
//	/** 表示询盘信息来源为某询盘信息 */
//	final String INQUIRYED_TYPE_FROM_INQUIRY = "1";
//	/** 表示询盘信息来源为某供求信息,即从供求导出的询盘 */
//	final String INQUIRYED_TYPE_FROM_PRODUCTS = "2";
//
//	/** 导出状态,未处理 */
//	final String EXPORT_STATUS_UNPROCESS = "0";
//	/** 导出状态,处理中 */
//	final String EXPORT_STATUS__PROCESSING = "1";
//	/** 导出状态,已处理 */
//	final String EXPORT_STATUS__PROCESSED = "2";
//

//
//	/** 表示询盘信息为客户自己通过网站留下的 */
//	final String BATCH_SEND_TYPE_DEFAULT = "0";
//	/** 表示询盘信息是后台管理员通过将某些客户的询盘信息导出而来的 */
//	final String BATCH_SEND_TYPE_1 = "1";
//	/** 表示询盘信息是后台管理员通过将某些客户的供求信息导出而来的 */
//	final String BATCH_SEND_TYPE_2 = "2";
//	/** 表示询盘信息是客户通过网站上提供的导出询盘功能产生的 */
//	final String BATCH_SEND_TYPE_3 = "3";
//	/**
//	 * 默认为未分组
//	 */
//	final Integer GROUP_ID_DEFAULT=0;
//
//	/**
//	 * 增加一条询盘信息
//	 * 
//	 * @param inquiryDO
//	 *            :询盘对象,不可以为空<br/>
//	 *            属性inquiryDO.senderID不可以为空,否则抛出异常 属性inquiryDO.receiverID不可以为空,否则抛出异常
//	 * @return 添加后的记录编号
//	 */
//	public Integer insertInquiry(InquiryDO inquiryDO);
//	
//	/**
//	 * 回复询盘留言
//	 * @param inquiryDO
//	 * @return
//	 */
////	public Integer insertInquiryReply(InquiryDO inquiryDO);
//
//	/**
//	 * 更新询盘分组信息
//	 * 
//	 * @param inquiryIds
//	 * @param groupId
//	 * @return
//	 */
//	public Integer updateInquiryGroup(String inquiryArrayStr, Integer groupId);
//
//	/**
//	 * 标记询盘信息删除状态
//	 * 
//	 * @param inquiryArray
//	 * @return
//	 */
//	public Integer updateInquirysDeleteStatus(String delType,String inquiryArrayStr, String isDel);
//
//	/**
//	 * 标记是否为垃圾留言
//	 * 
//	 * @param inquiryIds
//	 * @param isRubbish
//	 *            是否为垃圾 1－是；0－否。
//	 * @return
//	 */
//	public Integer updateInquiryIsRubbish(String inquiryArrayStr, String isRubbish);
//
//	/**
//	 * 设置为已读
//	 * 
//	 * @param inquiryIds
//	 *            编号
//	 * @param isViewed
//	 *            1-已读
//	 * @return
//	 */
//	public Integer updateInquiryIsViewed(String inquiryArrayStr, String isViewed);
//
//	/**
//	 * 更新导入状态
//	 * 
//	 * @param inquiryIds
//	 * @param exportStatus
//	 * @param exportPerson
//	 *            处理人帐号，当处理状态为0时要设置exportPerson为空或null
//	 * @return
//	 */
//	public Integer updateInquiryExportStatus(String inquiryArrayStr, String exportStatus,
//			String exportPerson);
//
//
//	/**
//	 * 通过询盘接受者Id 查询询盘集合  
//	 * 
//	 * @param receiverId
//	 * @return
//	 * 
//	 */
////	public List<InquiryDO> queryInquiryListByReceiverId(Integer receiverId);
//	
//	/**
//	 * 通过询盘接受者Id 查询询盘量  receiverId
//	 * 
//	 * @param receiverId 
//	 * @return
//	 * 
//	 */
////	public Integer queryInquiryListByReceiverIdCount(Integer receiverId);
//	
//	/**
//	 * 查询针对指定询盘对象的所有询盘量 BeInquiredId
//	 * 
//	 * @param id
//	 * @return
//	 */
////	public Integer queryInquiryListByBeInquiredIdCount(String beInquiredType, Integer beInquiredId);
//	
//	/**
//	 * 查询针对指定询盘对象的所有询盘 BeInquiredId
//	 * 
//	 * @param id
//	 * @return
//	 */
////	public List<InquiryDO> queryInquiryListByBeInquiredId(String beInquiredType,
////			Integer beInquiredId);
//
//	/**
//	 * 统计未读留言总数
//	 * @param beInquiredType 询盘类型
//	 * @param receiverId 接收人Id
//	 * @return
//	 */
//	public Integer queryInquiryUnviewedByReciverIdCount(String beInquiredType, Integer receiverId);
//	
//	/**
//	 * 通过id获取询盘信息，用于显示询盘信息页面 发送者信息
//	 * 
//	 * @param id
//	 *            询盘信息编号
//	 * @return inquiryDto.inquiry,inquiryDto.sender
//	 */
////	public InquiryDTO queryInquiryDTOByIdForShowMessage(Integer id);
//	public InquiryDTO queryInquiryDetailById(Integer id);
//	
//	/**
//	 * 根据ID查询询盘信息
//	 * 
//	 * @param id
//	 * @return
//	 */
////	public InquiryDO queryInquiryById(Integer id);
//
//	/**
//	 * 根据ID删除询盘信息
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public Integer deleteInquiryById(Integer id);
//	
//	/**
//	 * 通过查询条件查找出符合条件的询盘信息
//	 * 
//	 * @param inquiryDTO
//	 *            :包含查询条件的DTO,不可以为null,否则抛出异常<br/>
//	 *            询盘时间_开始(searchStartDate),询盘时间_结束(searchEndDate),发件人Email(senderEmail),收件人Email(receiverEmail),
//	 *            距离当前时间X天(deadline),是否有发布供求信息(hasProducts),询盘对象类型(inquiryDO.beInquiredType),
//	 *            导出状态(inquiryDO.exportStatus:默认非已处理),群发标记(inquiryDO.batchSendType)<br/>
//	 *            如果询盘开始时间为null,则默认查询7天内的询盘信息<br/>
//	 *            发件人Email和收件人Email需要从公司库和网站客户信息库中查询出公司ID,替换掉inquiryDO对象中的senderId和receiverId作为查询条件
//	 * @return PageDto需要设置根据此条件查询出来的记录条数总数(totalRecords),设置查询出来的记录List<InquiryDTO>
//	 */
////	public PageDto listInquiryByDTO(InquiryDTO inquiryDTO);
//
//	public PageDto queryPaginationInquiryListByReceiverAndSender(Integer senderId, Integer receiverId,
//			PageDto page);
//	
//	/**
//	 * 从一个或多个现有的供求信息生成另外其他供求信息的询盘信息
//	 * 
//	 * @param inquiryDOList
//	 *            :待生成的询盘信息列表,该列表不可以为null,否则抛出@see {@link ServiceLayerException}<br/>
//	 *            保存询盘信息时inquiryDOList中的每一个询盘信息必需满足以下条件:<br/>
//	 *            InquiryDO.batchSendType=@see {@link #BATCH_SEND_TYPE_2}<br/>
//	 *            InquiryDO.beInquiryedType=@see {@link #BE_INQUIRYED_TYPE_DEFAULT}
//	 *            ,同时inquiryDO.beInquiryedId分别为productsArray里的ID<br/>
//	 *            InquiryDO.inquiryedType=@see {@link #INQUIRYED_TYPE_2},同时inquiryDO.inquiryedId为源供求信息的ID<br/>
//	 *            InquiryDO.senderId=由源供求信息的ID查询出来的源供求信息所属公司的ID<br/>
//	 *            InquiryDO.receiverId需要通过供求信息ID查询出来的供求信息所属于公司的ID<br/>
//	 *            InquiryDO.exportStatus=@see {@link #STATUS_BEGIN_PROCESS}<br/>
//	 * @param productsArray
//	 *            :需要询盘信息的供求信息ID数组,以"1,2,3"的格式从页面提交过来,为""或null时不做处理
//	 * @throws ServiceLayerException
//	 */
//	public void createInquirysForProductsByProducts(List<InquiryDO> inquiryDOList,
//			String productsArray) throws ServiceLayerException;
//	
////	public List<InquiryForFrontDTO> queryInquiryForFrontDTO(InquiryForFrontDTO inquiryForFrontDto);
////
////	public Integer countInquiryForFrontDTO(InquiryForFrontDTO inquiryForFrontDto);
//
//	public PageDto queryPaginationInquiryListByInquiryCondition(InquiryDO inquiry,
//			PageDto page);
//	
//	/**
//	 * 根据条件获取询盘信息列表
//	 * @param inquiryDto
//	 * @return
//	 */
//	public List<InquiryDTO> queryInquiryByConditions(InquiryDTO inquiryDto);
//	
//	/**
//	 * 根据条件统计询盘信息总数
//	 * @param inquiryDto
//	 * @return
//	 */
//	public Integer countInquiryByConditions(InquiryDTO inquiryDto);
//	
//	/**
//	 * 通过编号获取询盘信息的内容
//	 * @param id 编号
//	 * @return
//	 */
//	public InquiryDO queryInquiryContentById(Integer id);
//
//	/**
//	 * 根据组编号设置询盘为未分组，删除分组时调用此方法
//	 * @param id 分组ID
//	 * @return 
//	 */
//	public Integer setInquiryUngroupedByGroupId(Integer id);
//
//	/**
//	 * 依据询盘信息将该信息的发送者列入接收者的黑名单，以后接收该发送者的信息都作垃圾留言
//	 * @param ids
//	 */
//	public void updateInquirySenderToBlackList(String inquiryIdsStr);
//
//	public void deleteInquiryBlackListByInquiryIds(String inquiryIdsStr);
	
}
