/**
 * @author shiqp 日期:2014-11-11
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.dto.PageDto;

public interface BbsPostTagsDao {
	/**
	 * 热门标签
	 * @param size
	 * @return
	 */
	public List<BbsPostTags> queryTagsByArticleC(Integer size);
	/**
	 * 根据标签名获取标签信息
	 * @param tagName
	 * @return
	 */
	public BbsPostTags queryTagByName(String tagName,Integer category,Integer isDel);
	/**
	 * 大类别下的标签
	 * @param category
	 * @param size
	 * @return
	 */
	public List<BbsPostTags> queryTagByCategory(Integer category,Integer size);
	/**
	 * 插入新标签
	 * @param bbsPostTags
	 * @return
	 */
	public Integer insertTag(BbsPostTags bbsPostTags);
	/**
	 * 更新标签信息
	 * @param bbsPostTags
	 */
	public Integer updateTag(BbsPostTags bbsPostTags);
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
	 * 更改标签 关注次数
	 * @param id
	 * @param noticeCount
	 * @return
	 */
	public Integer updateNoticeCountById(Integer id, Integer noticeCount);
	/**
	 * 更新标签 删除状态-1
	 * @param id
	 * @param isDel
	 * @return
	 */
    public Integer updateIsDelById(Integer id, Integer isDel);
    /**
     * 标签列表页
     * @param category
     * @param page
     * @return
     */
    public List<BbsPostTags> queryAllTagsByCategory(Integer category,PageDto<BbsPostTags> page);
    /**
     * 标签列表的个数（符合条件）
     * @param category
     * @return
     */
    public Integer countAllTagsByCategory(Integer category);
    /**
     * 修改标签的类别及名称
     * @param tags
     * @return
     */
    public Integer updateNameAndCategory(BbsPostTags tags);

}
