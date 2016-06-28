package com.kl91.persist.inquiry;

import java.util.List;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.inquiry.Inquiry;
import com.kl91.domain.inquiry.InquiryDto;
import com.kl91.domain.inquiry.InquirySearchDto;

public interface InquiryDao {

	public Integer insert(Inquiry inquiry);

	public Integer update(Inquiry inquiry);

	public Integer deleteByFrom(Integer[] ids, Integer deleteType);

	public Integer deleteByTo(Integer[] ids, Integer deleteType);

	public List<InquiryDto> queryInquiry(InquirySearchDto searchDto, PageDto<InquiryDto> page);
	
	public Integer queryInquiryCount(InquirySearchDto searchDto, PageDto<InquiryDto> page);

	public Integer countNoViewedInquiry(Integer id);
	
	public Inquiry queryById(Integer id);
	
	public Integer updateViewed(Integer id);
}
