/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-18
 */
package com.ast.ast1949.service.tags;

import java.util.List;

import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.TagsInfoDTO;

/**
 * @author yuyonghui
 *
 */
public interface TagsInfoService {
	
	/**
	 * 添加一条标签信息
	 *
	 * @param tagsInfoDO为标签对象
	 *            ,tagsInfoDO.name不可为空<br/>
	 * @return 添加后的记录编号,当返回值小于等于0时,表示插入无效， 当返回值大于0时,表示插入成功
	 */
//	public Integer insertTagsInfo(TagsInfoDO tagsInfoDO);

	/**
	 * 更新一条标签信息
	 *
	 * @param tagsInfoDO
	 *            更新后的标签对象
	 * @return 更新操作的影响行数
	 */
	public Integer updateTagsInfo(TagsInfoDO tagsInfoDO);

	/**
	 * 插入标签（如果不存在）和关联记录
	 * @param tagsInfoDto
	 * @return
	 */
//	public void insertOrUpdateTags(TagsDTO tagsDto);
	
	/**
	 * 根据id查询标签信息
	 *
	 * @param id为传入主键值
	 *            ,不可为0<br/>
	 * @return TagsInfoDO,当没找到数据时返回null
	 */
	public TagsInfoDO queryTagsInfoById(Integer id);

	/**
	 * 根据标签名查询标签信息
	 *
	 * @param name为传入的标签名
	 *            ,不可为空<br/>
	 * @return TagsInfoDO,当没找到数据时返回null
	 */
	public TagsInfoDO queryTagsInfoByName(String name);

	/**
	 * 根据标签分类查询标签信息列表
	 * @param tagsInfoTto
	 * @return
	 */
	public List<TagsInfoDO> queryTagsInfoByType(String tagType,Integer topNum);
	/**
	 * 根据关联信息查找所有对应的标签
	 * @param tagsRelateArticleDO<br>
	 * 	tagsRelateArticleDO.article 对应的文章Id<br>
	 *  tagsRelateArticleDO.tagsArticleCategoryCode 对应信息的类型,如资讯,报价<br>
	 *  tagsRelateArticleDO.isAdmin 是否为后台发布的标签，如果不指定该参数，则查找所有标签<br>
	 *  tagsRelateArticleDO.tagsCategoryProductsCode 如果为供求标签时的供求大类,不指定时就不考虑该参数
	 * @return 标签信息
	 */
//	public List<TagsInfoDO> queryTagsInfoByArticleIdAndCategoryCode(TagsRelateArticleDTO tagsRelateArticleDTO);
	/**
	 * <h3>分页查询标签信息</h3>
	 * 按条件搜索资讯信息
	 *
	 * @param news
	 *            条件、分页参数
	 * @return 标签信息
	 */
	public List<TagsInfoDO> queryTagsInfoByCondition(TagsInfoDTO tags);

	/**
	 * <h3>分页查询标签信息</h3>
	 * 从缓存按条件搜索资讯信息
	 *
	 * @param news
	 *            条件、分页参数
	 * @return 标签信息
	 */
	public List<TagsInfoDO> queryTagsInfoByConditionFromCache(TagsInfoDTO tags);

	/**
	 * <h3>统计标签信息记录数</h3>
	 *按条件查询记录总数
	 * @param news
	 *            条件、分页参数
	 * @return 返回记录总数
	 */
	public Integer getRecordCountByCondition(TagsInfoDTO tags);

	/**
	 * 根据id删除标签
	 *
	 * @param id
	 *            标签表的主键
	 * @return 删除操作的影响行数
	 */
//	public Integer deleteTagsInfoById(Integer id);

	/**
	 * 批量删除指定标签
	 * @param entities 不能为null或""，否则抛出异常;<br/>
	 * @return 当返回值大于0时，删除成功;否则,删除失败。
	 */
	public Integer batchDeleteTagsInfoByIds(int[] ids);

	/**
	 * 查询最新的N个标签（创建时间降序）
	 * @param topNum
	 * @return
	 */
	public List<TagsInfoDO> queryLastTags(Integer topNum);


}
