package com.ast.ast1949.persist.yuanliao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.yuanliao.YuanLiaoExportInquiryDao;

@Component("yuanLiaoExportInquiryDao")
public class YuanLiaoExportInquiryDaoImpl extends BaseDaoSupport implements YuanLiaoExportInquiryDao {
	
	private final String SQL_FIX = "yuanLiaoExportInquiry";
	
	@Override
	public Integer countByYuanLiaoId(Integer yuanLiaoId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByYuanLiaoId"), yuanLiaoId);
	}
	
	@SuppressWarnings("unchecked")
	public List<YuanLiaoExportInquiry> queryByYuanLiaoId(Integer id){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByYuanLiaoId"), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<YuanLiaoExportInquiry> queryList(Integer yuanLiaoId,
			Integer companyId, PageDto<YuanliaoDto> page){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("yuanLiaoId", yuanLiaoId);
		map.put("companyId", companyId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}
	
	public Integer countList(Integer yuanLiaoId, Integer companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("yuanLiaoId", yuanLiaoId);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countList"), map);
	}
}
