package com.ast.ast1949.persist.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisProductTypeCode;

/**
@author	kongsj
@date	2012-9-11
 */
public interface AnalysisProductTypeCodeDao {
	public List<AnalysisProductTypeCode> queryAnalysisProductTypeCode(String account ,String start,String end);
}
