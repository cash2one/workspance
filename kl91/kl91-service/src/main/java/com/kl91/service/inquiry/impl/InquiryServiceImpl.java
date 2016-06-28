package com.kl91.service.inquiry.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.inquiry.Inquiry;
import com.kl91.domain.inquiry.InquiryDto;
import com.kl91.domain.inquiry.InquirySearchDto;
import com.kl91.persist.inquiry.InquiryDao;
import com.kl91.service.company.CompanyService;
import com.kl91.service.inquiry.InquiryService;
import com.zz91.util.lang.StringUtils;

@Component("inquiryService")
public class InquiryServiceImpl implements InquiryService {

	@Resource
	private InquiryDao inquiryDao;
	@Resource
	private CompanyService companyService;

	@Override
	public Integer createInquiry(Inquiry inquiry) {
		return inquiryDao.insert(inquiry);
	}

	@Override
	public Integer deleteByFrom(String id, Integer companyId) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return inquiryDao.deleteByFrom(ids, FLAG_DELETE);
	}

	@Override
	public Integer deleteByTo(String id, Integer companyId) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return inquiryDao.deleteByTo(ids, FLAG_DELETE);
	}

	@Override
	public Integer cpdeleteByFrom(String id, Integer companyId) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return inquiryDao.deleteByFrom(ids, COMPLATE_DELETE);
	}

	@Override
	public Integer cpdeleteByTo(String id, Integer companyId) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return inquiryDao.deleteByTo(ids, COMPLATE_DELETE);
	}

	@Override
	public PageDto<InquiryDto> queryFromByCompanyId(Integer companyId,
			PageDto<InquiryDto> page) {
		InquirySearchDto searchDto = new InquirySearchDto();
		searchDto.setFromCid(companyId);
		searchDto.setTrashFlag(0);
		page.setTotalRecords(inquiryDao.queryInquiryCount(searchDto, page));
		List<InquiryDto> list = inquiryDao.queryInquiry(searchDto, page);
		for (InquiryDto obj : list) {
			// obj.setToCname(companyService.queryById(obj.getToCid())
			// .getAccount());
			Company company = companyService.queryById(obj.getToCid());
			if (company != null) {
				obj.setToCname(company.getAccount());
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public PageDto<InquiryDto> queryToByCompanyId(Integer companyId,
			PageDto<InquiryDto> page) {
		InquirySearchDto searchDto = new InquirySearchDto();
		searchDto.setToCid(companyId);
		searchDto.setTrashFlag(0);
		page.setTotalRecords(inquiryDao.queryInquiryCount(searchDto, page));
		List<InquiryDto> list = inquiryDao.queryInquiry(searchDto, page);
		for (InquiryDto obj : list) {
			Company company = companyService.queryById(obj.getFromCid());
			if (company != null) {
				obj.setFromCname(company.getAccount());
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public PageDto<InquiryDto> queryTrashByCompanyId(Integer companyId,
			PageDto<InquiryDto> page) {
		InquirySearchDto searchDto = new InquirySearchDto();
		searchDto.setTrashCid(companyId);
		page.setTotalRecords(inquiryDao.queryInquiryCount(searchDto, page));
		List<InquiryDto> list = inquiryDao.queryInquiry(searchDto, page);
		for (InquiryDto obj : list) {
			if (obj.getFromCid().equals(companyId)) {
				obj.setFromCname(companyService.queryById(obj.getFromCid())
						.getAccount());
			} else if (obj.getToCid().equals(companyId)) {
				obj.setToCname(companyService.queryById(obj.getToCid())
						.getAccount());
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Integer replyInquiry(Integer id, String content) {
		Inquiry inquiry = new Inquiry();
		inquiry.setId(id);
		inquiry.setContentReply(content);
		return inquiryDao.update(inquiry);
	}

	@Override
	public Integer countNoViewedInquiry(Integer companyId) {
		Integer i = inquiryDao.countNoViewedInquiry(companyId);
		if (i > 0) {
			return i;
		} else {
			return 0;
		}
	}

	@Override
	public Inquiry queryById(Integer id) {
		return inquiryDao.queryById(id);
	}

	@Override
	public Integer updateViewed(Integer id) {
		return inquiryDao.updateViewed(id);
	}

}
