package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.AnalysisBbsTop;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-8-17 
 */
public interface AnalysisBbsTopService {
	public List<AnalysisBbsTop> queryBbsTopsByType(String category,Integer max);
}
