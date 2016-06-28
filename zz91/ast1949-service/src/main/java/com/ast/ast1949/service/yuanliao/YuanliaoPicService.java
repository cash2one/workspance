package com.ast.ast1949.service.yuanliao;

import java.util.List;

import com.ast.ast1949.domain.yuanliao.YuanliaoPic;

/**
 * @date 2015-08-27
 * @author shiqp
 */
public interface YuanliaoPicService {
	public Integer insertYuanliaoPic(YuanliaoPic yuanliaoPic);
	
	public List<YuanliaoPic> queryYuanliaoPicByYuanliaoId(YuanliaoPic yuanliaoPic,Integer size);
	
	public Integer updateYuanliaoPic(YuanliaoPic yuanliaoPic);

}
