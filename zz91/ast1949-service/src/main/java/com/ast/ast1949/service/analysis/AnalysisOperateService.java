package com.ast.ast1949.service.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisOperate;

/**
 * @author kongsj
 * @date 2012-9-11
 */
public interface AnalysisOperateService {
	public List<AnalysisOperate> queryAnalysisOperate(String account,String start, String end);
}
