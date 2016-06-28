package com.ast.ast1949.persist.analysis;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisXiaZaiKeywords;
import com.ast.ast1949.dto.PageDto;

public interface AnalysisXiaZaiDao {
    
    public List<AnalysisXiaZaiKeywords> queryKeywords(Date gmtTarget, PageDto<AnalysisXiaZaiKeywords> page);
    public List<AnalysisXiaZaiKeywords> queryKeywordsRang(String kw, Date start,Date end);
    public Integer queryKeywordsCount(Date gmtTarget);
    public Integer summaryKeywords(Date gmtTarget);
    public Integer insertKeyword (AnalysisXiaZaiKeywords analysisXiaZaiKeywords);
    public Integer updateKeywordOfNum (AnalysisXiaZaiKeywords analysisXiaZaiKeywords);
    public List<AnalysisXiaZaiKeywords> queryListByFromTo(Date gmtTarget);
}
