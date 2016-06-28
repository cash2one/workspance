package com.zz91.ep.admin.service.news;

import java.util.List;

import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.ZhuantiDto;

public interface NewsZhuantiService {

	public List<ExtTreeDto> queryZhuantiCategory(String type);
	
	public PageDto<ZhuantiDto> pageZhuanti(Zhuanti zhuanti,
			PageDto<ZhuantiDto> page);
	
	public Integer create(Zhuanti zhuanti);
	
	public Integer update(Zhuanti zhuanti);
	
	public Integer recommend(Zhuanti zhuanti);
	
	public Integer attention(Zhuanti zhuanti);
	
	public Zhuanti queryZhuantiDetail(Integer id);
	
	public Integer remove(Integer id);
}
