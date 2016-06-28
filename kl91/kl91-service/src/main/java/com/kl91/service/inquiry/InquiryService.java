package com.kl91.service.inquiry;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.inquiry.Inquiry;
import com.kl91.domain.inquiry.InquiryDto;


public interface InquiryService {
	
	public static final Integer FLAG_DELETE = 1;
	public static final Integer COMPLATE_DELETE = 2;
	
	public static final Integer INQUIRY_FOR_COMPANY = 1;
	public static final Integer INQUIRY_FOR_PRODUCTS = 0;
 
	/**
	 * 创建询盘
	 * 客户留言时操作
	 */
	public Integer createInquiry(Inquiry inquiry);
	/**
	 * 发送方删除询盘至回收站
	 * 将is_from_trash更新为1
	 */
	public Integer deleteByFrom(String id, Integer companyId);
	/**
	 * 接收方删除询盘至回收站
	 * 将is_to_trash更新为1
	 */
	public Integer deleteByTo(String id, Integer companyId);
	/**
	 * 发送方彻底删除询盘
	 * 将is_from_trash更新为2
	 */
	public Integer cpdeleteByFrom(String id, Integer companyId);
	/**
	 * 接收方彻底删除询盘
	 * 将is_from_trash更新为2
	 */
	public Integer cpdeleteByTo(String id, Integer companyId);
	/**
	 * 发件箱
	 * 生意管家发件箱询盘列表
	 */
	public PageDto<InquiryDto> queryFromByCompanyId(Integer companyId, PageDto<InquiryDto> page);
	/**
	 * 收件箱
	 * 生意管家收件箱询盘列表
	 */
	public PageDto<InquiryDto> queryToByCompanyId(Integer companyId, PageDto<InquiryDto> page);
	
	/**
	 * 回收箱
	 * 客户删除的询盘查询
	 */
	public PageDto<InquiryDto> queryTrashByCompanyId(Integer companyId, PageDto<InquiryDto> page);
	
	/**
	 * 回复询盘
	 * 客户在生意管家收件箱中回复客户发送过来的询盘
	 */
	public Integer replyInquiry(Integer id, String content);
	/**
	 * 生意管家头部,显示提示用户未读询盘的数量
	 */
	public Integer countNoViewedInquiry(Integer companyId);
	
	public Inquiry queryById(Integer id);
	
	public Integer updateViewed(Integer id);
}
 
