/**
 * @author shiqp 日期:2014-11-11
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.dto.PageDto;

public interface BbsPostTagsService {
	/**
	 * 热门标签
	 * 
	 * @param size
	 * @return
	 */
	public List<BbsPostTags> queryTagsByArticleC(Integer size);

	/**
	 * 根据标签名获取标签信息
	 * 
	 * @param tagName
	 * @return
	 */
	public BbsPostTags queryTagByName(String tagName,Integer category,Integer isDel);
	/**
	 * 大类别下的标签（相关标签）
	 * @param category
	 * @param size
	 * @return
	 */
	public List<BbsPostTags> queryTagByCategory(Integer category,Integer size);
	/**
	 * 处理标签，已经有的，文章数加1；没有的插入新的标签，文章数为1
	 * @param tags
	 * @param category
	 */
	public void dealTags(String tags,Integer categoryId);
	/**
	 * 根据id获取标签信息
	 * @param id
	 * @return
	 */
	public BbsPostTags queryTagById(Integer id);
	/**
	 * 大家都在关注
	 * @param size
	 * @return
	 */
	public List<BbsPostTags> queryTagsByMark(Integer size);
	
	/**
	 * coreseek 搜索引擎方法
	 * @return
	 */
	public PageDto<BbsPostTags> pageTagsBySearchEngine(BbsPostTags bbsPostTags,PageDto<BbsPostTags> page);
	
	/**
	 * 增加一次关注数
	 */
	public Integer up(Integer id);
	
	/**
	 * 减少一次关注数
	 */
	public Integer down(Integer id);
	/**
	 * 标签的列表页
	 * @param category
	 * @param page
	 * @return
	 */
    public PageDto<BbsPostTags> pageTagsByCategory(Integer category,PageDto<BbsPostTags> page);
    /**
     * 添加标签
     * @param tags
     * @return
     */
    public Integer addTags(BbsPostTags tags);
    /**
     * 删除标签
     * @param id
     * @return
     */
	public Integer deleteTags(Integer id);
	 /**
     * 修改标签的类别及名称
     * @param tags
     * @return
     */
    public Integer updateNameAndCategory(BbsPostTags tags);
    /**
	 * 更新标签信息
	 * @param bbsPostTags
	 */
	public Integer updateTag(BbsPostTags bbsPostTags);

}
