package com.ast.ast1949.service.analysis.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisXiaZaiKeywords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.analysis.AnalysisXiaZaiDao;
import com.ast.ast1949.service.analysis.AnalysisXiaZaiKeywordsService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.datetime.DateUtil;

@Component("analysisXiaZaiService")
public class AnalysisXiaZaiKeywordsServiceImpl implements
        AnalysisXiaZaiKeywordsService {

    @Resource
    private AnalysisXiaZaiDao analysisXiaZaiDao;
    @Override
    public List<AnalysisXiaZaiKeywords> queryKeywords(String kw, Date start,
            Date end) {
        if(start==null){
            try {
                start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
            } catch (ParseException e) {
            }
        }
        if(end==null){
            end = start; 
        }
        return analysisXiaZaiDao.queryKeywordsRang(kw,start,end);
    }

    @Override
    public PageDto<AnalysisXiaZaiKeywords> pageKeywords(Date gmtTarget,
            PageDto<AnalysisXiaZaiKeywords> page) {
        Assert.notNull(gmtTarget, "the gmtTarget can not be null");
        page.setTotalRecords(analysisXiaZaiDao.queryKeywordsCount(gmtTarget));
        page.setRecords(analysisXiaZaiDao.queryKeywords(gmtTarget,page));
        return page;
    }
    
    @Override
    public AnalysisXiaZaiKeywords summaryKeywords(Date gmtTarget) {
        AnalysisXiaZaiKeywords keywords=new AnalysisXiaZaiKeywords();
        
        keywords.setNum(analysisXiaZaiDao.summaryKeywords(gmtTarget));
        keywords.setKw("总计：");
        return keywords;
    }
    @Override
    public Integer insertKeyword(String kw, Integer num) {
        AnalysisXiaZaiKeywords analysisXiaZaiKeywords = new AnalysisXiaZaiKeywords();
        try {
            Date targetDate = DateUtil.getDate(new Date(), "yyyy-MM-dd 00:00:00");
            analysisXiaZaiKeywords.setGmtTarget(targetDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        analysisXiaZaiKeywords.setKw(kw);
        analysisXiaZaiKeywords.setNum(num);
        
        return analysisXiaZaiDao.insertKeyword(analysisXiaZaiKeywords);
    }

    @Override
    public Integer updateKeywordOfNum(
            AnalysisXiaZaiKeywords analysisXiaZaiKeywords) {
        // TODO Auto-generated method stub
        return analysisXiaZaiDao.updateKeywordOfNum(analysisXiaZaiKeywords);
    }

    @Override
    public List<AnalysisXiaZaiKeywords> queryListByFromTo(Date gmtTarget) {
        
        return analysisXiaZaiDao.queryListByFromTo(gmtTarget);
    }

   

    

}
