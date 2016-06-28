package com.ast.ast1949.service.analysis;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisXiaZaiKeywords;
import com.ast.ast1949.dto.PageDto;

public interface AnalysisXiaZaiKeywordsService {
    
    /**
     * 查找某一关键字的详细搜索情况
     * @param kw：关键字，非空
     * @param start：必需存在，如果为null，默认为当前时间前一天
     * @param end：如果为null，默认等于start
     * @return
     */
    public List<AnalysisXiaZaiKeywords> queryKeywords(String kw, Date start,Date end);
    
    public PageDto<AnalysisXiaZaiKeywords> pageKeywords(Date gmtTarget, PageDto<AnalysisXiaZaiKeywords> page);
    
    public AnalysisXiaZaiKeywords summaryKeywords(Date gmtTarget);
    
    public Integer insertKeyword (String kw,Integer num);
    
    public Integer updateKeywordOfNum (AnalysisXiaZaiKeywords analysisXiaZaiKeywords);
    
    public List<AnalysisXiaZaiKeywords> queryListByFromTo(Date gmtTarget);

}
