package com.ast.ast1949.persist.autocaiji;

import java.util.List;

import com.ast.ast1949.domain.autocaiji.AutoCaiji;
import com.ast.ast1949.dto.PageDto;

public interface AutoCaijiDAO {

    public List<AutoCaiji> queryPageLog(PageDto<AutoCaiji> page,String from,String to);
    
    public List<AutoCaiji> queryListLog(String from,String to);
    
    public Integer queryCount(String from,String to);
}
