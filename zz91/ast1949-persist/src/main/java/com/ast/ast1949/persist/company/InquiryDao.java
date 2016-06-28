package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;

public interface InquiryDao {
	
	public Integer insertInquiry(Inquiry inquiry);
	public Integer updateInquiryGroup(Integer inquiryId, Integer groupId);
	public Integer updateSenderDel(Integer inquiryId, String isSenderDel);
	public Integer updateReceiverDel(Integer inquiryId, String isReceiverDel);
	public Integer updateIsRubbish(Integer inquiryId,String isRubbish);
	public Integer updateViewed(Integer inquiryId, String isViewed);
	public Integer countUnviewedInquiry(String beInquiredType, String receiverAccount, Integer companyId);
	public Inquiry queryInquiry(Integer inquiryId);
	/**
	 * 允许的查询条件：group_id, be_inquiry_type, sender_account, is_rubbish, 
	 * is_viewed, is_sender_del, is_receiver_del, is_replyed
	 * 必需的条件：inquiry_type=0, 一年内的留言
	 */
	public List<InquiryDto> queryInquiryByUser(Inquiry inquiry, PageDto<InquiryDto> page);
	public Integer queryInquiryByUserCount(Inquiry inquiry);
	public List<InquiryDto> queryConversation(String conversation, Integer max);
	public Integer insertBlackList(Integer companyId, String account, Integer blackedCompanyId);
	public Integer deleteBlackList(Integer companyId, String account, Integer blackedCompanyId);
	public List<Inquiry> queryScrollInquiry(String batchSendType, int size);
	public Integer countBlackedCompany(String account, Integer blockedCompanyId);
	public Integer updateIsReplyed(Integer inquiryId, String isReplyed);
	
	public List<Inquiry> queryAllConversation(String senderAccount, 
			String receiverAccount, PageDto<Inquiry> page);
	public Integer queryAllConversationCount(String senderAccount, String receiverAccount);
	
	public List<Inquiry> queryInquiryBySenderUser(String senderAccount, PageDto<InquiryDto> page);
	public Integer queryInquiryBySenderUserCount(String senderAccount);
	/**
	 * 询盘后台管理页面
	 * @param inquiry
	 * @param page
	 * @return
	 */
	public List<Inquiry> queryInquiryByList(InquiryDto inquiryDto, PageDto<InquiryDto> page);
	
	public Integer countInquiryByList(InquiryDto inquiryDto);
	
	public Inquiry queryDownMessageById(Integer id, String receiverAccount, String sendAccount,String type);
	public Inquiry queryOnMessageById(Integer id, String receiverAccount, String sendAccount,String type);
	
	public List<Inquiry> queryInquiryByAdmin(Inquiry inquiry, PageDto<Inquiry> page);
	
	public Integer queryInquiryByAdminCount(Inquiry inquiry);
	
	public List<InquiryDto> queryInquiryListByUser(Inquiry inquiry);
	
	public Integer countInquiryByCondition(String from, String to, String account);
	
	public Integer countByBeinquiredTypeAndTypeID(String beInquiredType,Integer  beInquiredId);
	
	/**
	 * 批量导入询盘
	 * @param list
	 * @return
	 */
	public Integer batchInsert(List<Inquiry> list);
	
	/**
	 * 检索
	 */
	public Inquiry queryExportInquiryFromProduct(String sendAccount, Integer targetId);
	
	/******************以下老代码**************/
	/**
	 * 增加一条询盘信息
	 * 
	 * @param inquiryDO
	 *            :询盘对象,不可以为空<br/>
	 *            属性inquiryDO.senderID不可以为空,否则抛出异常 属性inquiryDO.receiverID不可以为空,否则抛出异常
	 * @return 添加后的记录编号
	 */
//	public Integer insertInquiry(InquiryDO inquiryDO);

	/**
	 * 一次增加多条询盘信息
	 * 
	 * @param inquiryDOList
	 *            :待添加的询盘信息列表,不可以为null,否则抛出异常
	 * @return 新增的询盘ID列表
	 */
//	public Set<Integer> insertInquirys(List<InquiryDO> inquiryDOList);
//
//	/**
//	 * 更新询盘信息
//	 * 
//	 * @param inquiryDO
//	 * @return
//	 */
//	public Integer updateInquiry(InquiryDO inquiryDO);
//
//	/**
//	 * 更新询盘分组信息
//	 * 
//	 * @param inquiryIds
//	 * @param groupId
//	 * @return
//	 */
//	public Integer updateInquiryGroup(List<Integer> inquiryIds, Integer groupId);
//
//	/**
//	 * 标记是否为垃圾留言
//	 * 
//	 * @param inquiryIds
//	 * @param isRubbish
//	 *            是否为垃圾 1－是；0－否。
//	 * @return
//	 */
//	public Integer updateInquiryIsRubbish(List<Integer> inquiryIds, String isRubbish);
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
//	public Integer updateInquiryIsViewed(List<Integer> inquiryIds, String isViewed);
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
//	public Integer updateInquiryExportStatus(List<Integer> inquiryIds, String exportStatus,
//			String exportPerson);
//
//	/**
//	 * 标记询盘删除标记
//	 * 
//	 * @param inquiryIds
//	 * @param isDel
//	 *            1－删除 0－正常
//	 * @return
//	 */
//	public Integer updateInquiryIsDel(String delType,List<Integer> inquiryIds, String isDel);
//
//	/**
//	 * 根据ID删除询盘信息
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public Integer deleteInquiryById(Integer id);
//	/**
//	 * 根据ID查询询盘信息
//	 * 
//	 * @param id
//	 * @return
//	 */
////	public InquiryDO queryInquiryById(Integer id);
//
//	/**
//	 * 查询针对指定询盘对象的所有询盘 BeInquiredId
//	 * 
//	 * @param id
//	 * @return
//	 */
////	public List<InquiryDO> queryInquiryListByBeInquiredId(String beInquiredType, Integer beInquiredId);
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
//	 * 通过询盘接受者Id 查询询盘集合 company_contacts ID
//	 * 
//	 * @param receiverId company_contactsID
//	 * @return
//	 * 
//	 */
////	public List<InquiryDO> queryInquiryListByReceiverId(Integer receiverId);
//
//	/**
//	 * 通过询盘接受者Id 查询询盘量 company_contactsID
//	 * 
//	 * @param receiverId company_contactsID
//	 * @return
//	 * 
//	 */
////	public Integer queryInquiryListByReceiverIdCount(Integer receiverId);
//	
//	//------------------------------------------------------------------------------------
//
//	/**
//	 * 统计未读留言总数
//	 * 
//	 * @param param
//	 *            参数：<br/>
//	 *            receiverId 接收人Id<br/>
//	 *            beInquiredType 询盘类型<br/>
//	 * @return
//	 */
//	public Integer queryInquiryUnviewedByReciverIdCount(String beInquiredType,Integer receiverId);
//
//	/**
//	 * 根据询盘查询条件查询出符合条件的询盘信息
//	 * 
//	 * @param inquiryDTO
//	 *            :包含查询条件的DTO,不能为null否则抛出异常<br/>
//	 *            inquiryDTO.inquiryDO不能为null,否则抛出异常
//	 * @return 符合条件的结果集
//	 */
//	/**
//	 * 查询发送者和接收者之间通信分页记录
//	 * @param senderId
//	 * @param receiverId
//	 * @param page
//	 * @return
//	 */
//	public PaginationResult queryPaginationInquiryListByReceiverAndSender(Integer senderId,
//			Integer receiverId, PageDto page);
////	public List<InquiryDTO> listInquiryByDTO(InquiryDTO inquiryDTO);
//
//	/**
//	 * 通过id获取询盘信息，用于显示询盘信息页面
//	 * 
//	 * @param id
//	 *            询盘信息编号
//	 * @return
//	 */
//	public InquiryDTO queryInquiryDetailById(Integer id);
//	
//	/**
//	 * 根据询盘查询条件查询出符合条件的询盘信息总数
//	 * 
//	 * @param inquiryDTO
//	 *            :包含查询条件的DTO,不能为null否则抛出异常<br/>
//	 *            inquiryDTO.inquiryDO不能为null,否则抛出异常
//	 * @return 记录总数
//	 */
////	public Integer countInquiryByDTO(InquiryDTO inquiryDTO);
////
////	public Integer countInquiryForFrontDTO(InquiryForFrontDTO inquiryForFrontDto);
////	public InquiryDTO queryInquiryDTOByIdForShowMessage(Integer id);
//
////	public List<InquiryForFrontDTO> queryInquiryForFrontDTO(InquiryForFrontDTO inquiryForFrontDto);
//
//	public PaginationResult queryPaginationInquiryListByInquiryCondition(InquiryDO inquiry,
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
//	/**
//	 * 根据组编号设置询盘为未分组，删除分组时调用此方法
//	 * @param id 分组ID
//	 * @return 
//	 */
//	public Integer setInquiryUngroupedByGroupId(Integer id);
//
//	public void updateInquirySenderToBlackList(List<Integer> ids);
//	
//	public List<InquiryBlackList> queryInquiryBlackListByAccountId(Integer receiverId);
//
//	public void deleteInquiryBlankListByAccountIdAndBlackAccountId(Integer accountId,
//			Integer blackAccountId);
//
//	public void deleteInquiryBlackListByInquiryIds(List<Integer> inquiryIds);
	public Integer countInquiryByCidAtime(Integer targetId,String from,String to);
	
}
