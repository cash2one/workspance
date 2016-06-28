package com.kl91.persist.inquiry.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.inquiry.Inquiry;
import com.kl91.domain.inquiry.InquiryDto;
import com.kl91.domain.inquiry.InquirySearchDto;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.inquiry.InquiryDao;

@Component("inquiryDao")
public class InquiryDaoImpl extends BaseDaoSupport implements InquiryDao {

	private static String SQL_PREFIX = "inquiry";

	@Override
	public Integer insert(Inquiry inquiry) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insert"), inquiry);
	}

	@Override
	public Integer update(Inquiry inquiry) {
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "update"), inquiry);
	}

	@Override
	public Integer deleteByFrom(Integer[] ids, Integer deleteType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDeleteByFrom", 1);
		map.put("ids", ids);
		map.put("deleteType", deleteType);
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "batchDelete"), map);
	}

	@Override
	public Integer deleteByTo(Integer[] ids, Integer deleteType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDeleteByTo", 1);
		map.put("ids", ids);
		map.put("deleteType", deleteType);
		return (Integer) getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "batchDelete"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryDto> queryInquiry(InquirySearchDto searchDto,
			PageDto<InquiryDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryInquiry"), map);
	}

	@Override
	public Integer countNoViewedInquiry(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countNoViewedInquiry"), id);
	}

	@Override
	public Inquiry queryById(Integer id) {
		return (Inquiry) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}

	@Override
	public Integer queryInquiryCount(InquirySearchDto searchDto,
			PageDto<InquiryDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryInquiryCount"), map);
	}

	@Override
	public Integer updateViewed(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateViewed"), id);
	}

}
