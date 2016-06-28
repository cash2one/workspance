package com.zz91.ep.admin.dao.news;

import com.zz91.ep.domain.news.NewsRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-10-13 
 */
public interface NewsRecommendDao {
	/**
	 * 添加一条推荐信息(后台)
	 * @param newId 资讯编号
	 * @param type 推荐类型
	 * @return
	 */
	public Integer insertRecommend(NewsRecommend recommend);
	/**
	 * 删除推荐资讯(后台)
	 * @param id 编号
	 * @return
	 */
	public Integer deleteRecommendById(Integer id,Integer rid);
	
//	public Integer deleteRecommendById(Integer id, String type);
	
//	public NewsRecommend queryRecommendByNewsId(Integer id);
	
	public NewsRecommend queryRecommendByNewsIdAndType(Integer id, String type);
}
