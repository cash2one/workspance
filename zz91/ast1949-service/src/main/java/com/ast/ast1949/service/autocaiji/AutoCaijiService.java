package com.ast.ast1949.service.autocaiji;

import java.text.ParseException;
import java.util.List;

import com.ast.ast1949.domain.autocaiji.AutoCaiji;
import com.ast.ast1949.dto.PageDto;

public interface AutoCaijiService {

    
    /**
     * 根据开始时间结束时间 搜索时间内的所有list 
     * 慎用。。使用于导数据
     * @param from
     * @param to
     * @return
     */
    public List<AutoCaiji> queryListByFromTo(String from,String to) throws ParseException;
    
    /*public PageDto<AutoCaiji> queryPageByFromTo(PageDto<AutoCaiji> page, String from,String to) throws ParseException;*/
}
