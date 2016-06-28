package com.zz91.ep.service.news;

import java.util.List;

import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;

public interface NewsZhuantiService {

	/**
	 * 根据类别查询专题列表
	 * @param category
	 * @param size
	 * @return
	 */
	public List<Zhuanti> queryByCategory(String category,
			Integer size);
	
	/**
	 * 查询关注专题列表
	 * @param size
	 * @return
	 */
	public List<Zhuanti> queryAttention(Integer size);
	
	/**
	 * 查询推荐专题列表
	 * @param size
	 * @return
	 */
	public List<Zhuanti> queryRecommend(Integer size);
	/**
	 * 根据类别分页查询专题列表
	 * @param category
	 * @param page
	 * @return
	 */
	public PageDto<Zhuanti> pageByCategory(String category,PageDto<Zhuanti> page);
}
