package com.ast.ast1949.persist.news;

import java.util.List;

import com.ast.ast1949.domain.news.NewsTech;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.news.NewsTechDTO;

public interface NewsTechDao {
	/**
	 * 根据id检索数据
	 * @param id
	 * @return
	 */
	public NewsTech queryById(Integer id);

	/**
	 * 插入一条新技术资讯
	 * @param newsTech
	 * @return
	 */
	public Integer insert(NewsTech newsTech);

	/**
	 * 更新一条资讯 
	 * @param newsTech 
	 * 属性id不得为空
	 * @return
	 */
	public Integer update(NewsTech newsTech);
	
	/**
	 * 根据条件，检索技术资讯列表
	 * @param newsTechDTO
	 * @param page
	 * @return
	 */
	public List<NewsTech> queryByCondition(NewsTechDTO newsTechDTO,PageDto<NewsTech> page);

	/**
	 * 根据条件，检索技术资讯列表条数
	 * @param newsTechDTO
	 * @return
	 */
	public Integer queryCountCondition(NewsTechDTO newsTechDTO);
	
	public NewsTech queryOnNewsTechById(Integer id);
	
	public NewsTech queryDownNewsTechById(Integer id);
	
	public Integer updateTechViewCount(Integer id,Integer viewCount);
	
	public List<NewsTech> queryTechForIndex(NewsTechDTO newsTechDTO,PageDto<NewsTech> page);
	
	public Integer delete(Integer id);
	
	public Integer updateContent(Integer id, String content);
}
