package com.ast.ast1949.service.news;

import java.util.List;

import com.ast.ast1949.domain.news.NewsTech;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.news.NewsTechDTO;

public interface NewsTechService {
	
	/**
	 * 搜索一条技术资讯信息
	 * @param id
	 * @return
	 */
	public NewsTech queryById(Integer id);

	/**
	 * 插入一条新技术资讯
	 * @param newsTech
	 * @return
	 */
	public Integer createOneTech(NewsTech newsTech);

	/**
	 * 更新一条技术资讯 
	 * @param newsTech 
	 * 属性id不得为空
	 * @return
	 */
	public Integer updateOneTech(NewsTech newsTech);
	
	/**
	 * 根据条件，检索技术资讯列表
	 * @param newsTechDTO
	 * @param page
	 * @return
	 */
	public List<NewsTech> queryTechList(NewsTechDTO newsTechDTO,Integer size);

	/**
	 * 根据条件，检索技术资讯列表 带分页
	 * @param newsTechDTO
	 * @return
	 */
	public PageDto<NewsTech> pageTech(NewsTechDTO newsTechDTO,PageDto<NewsTech> page);
	
	/**
	 * 查询上一篇
	 */
	public NewsTech queryOnNewsTechById(Integer id);
	
	/**
	 * 查询上一篇
	 */
	public NewsTech queryDownNewsTechById(Integer id);
	 /**
	  * 更新文章的点击量
	  */
	public Integer updateViewCount(Integer id,Integer viewCount);
	
	/**
	 * 首页根据点击量排序
	 * @param newsTechDTO
	 * @param size
	 * @return
	 */
	public List<NewsTech> queryTechForIndex(NewsTechDTO newsTechDTO, Integer size);
	
	public Integer delete(Integer id);
	
	/**
	 * 后台值更新内容
	 */
	public Integer updateContent(Integer id,String content);
}
