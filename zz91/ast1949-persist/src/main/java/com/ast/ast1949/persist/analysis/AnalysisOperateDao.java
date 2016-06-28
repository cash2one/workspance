package com.ast.ast1949.persist.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisOperate;

/**
@author	kongsj
@date	2012-9-11
 */
public interface AnalysisOperateDao {
	public List<AnalysisOperate> queryAnalysisOperate(String account ,String start,String end);
}
