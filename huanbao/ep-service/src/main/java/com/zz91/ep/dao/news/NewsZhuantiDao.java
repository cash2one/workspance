package com.zz91.ep.dao.news;

import java.util.List;

import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.PageDto;

public interface NewsZhuantiDao {
	
	public static final String ATTENTION_Y="Y";
	public static final String ATTENTION_N="N";
	public static final String RECOMMEND_Y="Y";
	public static final String RECOMMEND_N="N";

	/**
	 * 根据类别查询专题列表
	 * @param category
	 * @param page
	 * @return
	 */
	public List<Zhuanti> queryByCategory(String category,
			PageDto<Zhuanti> page);
	/**
	 * 根据类别查询专题数量
	 * @param category
	 * @return
	 */
	public Integer queryByCategoryCount(String category);
	
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
}
