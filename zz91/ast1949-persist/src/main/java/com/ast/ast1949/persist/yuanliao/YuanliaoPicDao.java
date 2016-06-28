package com.ast.ast1949.persist.yuanliao;

import java.util.List;

import com.ast.ast1949.domain.yuanliao.YuanliaoPic;

/**
 * @date 2015-08-22
 * @author shiqp
 */
public interface YuanliaoPicDao {
	
	public Integer insertYuanliaoPic(YuanliaoPic yuanliaoPic);
	
	public Integer updateYuanliaoPic(YuanliaoPic yuanliaoPic);
	
	public List<YuanliaoPic> queryYuanliaoPicByYuanliaoId(YuanliaoPic yuanliaoPic, Integer size);

}
