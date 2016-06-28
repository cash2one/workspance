package com.zz91.ep.admin.dao.news;

import java.util.List;

import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.ZhuantiDto;

public interface NewsZhuantiDao {
	
	public static final String ATTENTION_Y="Y";
	public static final String ATTENTION_N="N";
	public static final String RECOMMEND_Y="Y";
	public static final String RECOMMEND_N="N";

	public List<Zhuanti> queryZhuanti(Zhuanti zhuanti,
			PageDto<ZhuantiDto> page);
	
	public Integer queryZhuantiCount(Zhuanti zhuanti);
	
	public Integer insert(Zhuanti zhuanti);

	public Integer update(Zhuanti zhuanti);
	
	public Integer updateAttention(Zhuanti zhuanti);
	
	public Integer updateRecommend(Zhuanti zhuanti);
	
	public Zhuanti queryZhuantiDetail(Integer id);
	
	public Integer delete(Integer id);
}
