package com.ast.ast1949.persist.tags;

import java.util.List;

import com.ast.ast1949.domain.tags.TagsArticleRelation;
import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.TagsArticleRelationDto;

public interface TagsArticleRelationDao {

	//==============================更新标签关联信息相关方法======================================
	/**
	 * 新增标签文章信息关联关系
	 * 
	 * @param tagArticleRelation
	 * @return
	 */
	Integer insertTagsArticleRelation(TagsArticleRelation tagsArticleRelation);

	/**
	 * 更新标签文章关联信息
	 * 
	 * @param tagArticleRelation
	 * @return
	 */
//	Integer updateTagsArticleRelation(TagsArticleRelation tagArticleRelation);

	/**
	 * 删除与指定文章相关的所有关联信息
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	int deleteTagsArticleRelationByArticleId(String moduleCatCode,Integer id);

	/**
	 * 删除与指定标签相关的所有关联信息
	 * 
	 * @param id
	 *            标签ID
	 * @return
	 */
	int deleteTagsArticleRelationByTagId(Integer id);
	/**
	 * 批量删除标签相关的所有关联信息
	 * @param ids
	 * @return
	 */
	int batchDeleteTagsArticleRelationByTagIds(int[] ids);

	/**
	 * 删除指定ID的标签文章关联信息
	 * 
	 * @param id
	 *            关联信息ID
	 * @return
	 */
//	boolean deleteTagsArticleRelationById(Integer id);

	/**
	 * 删除指定文章中的某个特定标签
	 * @param tagId
	 * @param articleId
	 * @return
	 */
//	boolean deleteTagsArticleRelationByTagIdAndArticleId(Integer tagId, Integer articleId);
	//==============================查询标签相关方法===================================================
	/**
	 * 查询标签文章关联关系信息
	 */
//	TagsArticleRelation queryTagsArticleRelationById(Integer id);

	/**
	 * 查询与指定文章相关联的所有标签信息列表
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	List<TagsInfoDO> queryTagListFromTagsArticleRelationByArticleId(String artModuleCode,Integer id);

	/**
	 * 根据标签分类和关联信息的产品分类，查询标签信息列表
	 * 
	 * @param tagCatCode
	 *            可以为空
	 * @param artCatCode
	 *            不能为空
	 * @param topNum
	 * @return
	 */
	List<TagsInfoDO> queryTagListByTagCatAndArtCat(String tagCatCode, String artCatCode,
			Integer topNum);

	/**
	 * 根据文章模块分类和关联信息的产品分类，查询标签信息列表
	 * 
	 * @param moduleCatCode
	 *            不能为空
	 * @param artCatCode
	 *            可以为空
	 * @param topNum
	 * @return
	 */
	List<TagsInfoDO> queryTagListByModuleCatAndArtCat(String moduleCatCode, String artCatCode,
			Integer topNum);
	
	//==============================查询标签关联的文章信息相关方法===========================================
	/**
	 * 根据标签和文章模块，查询该标签关联此模块的文章信息量
	 * 
	 * @param moduleCatCode
	 * @param tagId
	 * @return
	 */
	Integer queryArticleCountByTagAndMoudel(TagsArticleRelationDto dto);

//	/**
//	 * 查询供应或求购的信息量
//	 * 
//	 * @param buyOrSale
//	 * @param tagId
//	 * @return
//	 */
//	Integer queryProductsArticleCountByProductsType(String buyOrSale, Integer tagId);

	/**
	 * 根据标签和文章模块，查询该标签关联此模块的文章［文章ID，文章标题。。。］
	 * 
	 * @param moduleCatCode
	 *            模块ID
	 * @param tagId
	 *            标签ID
	 * @return
	 */
	List<TagsArticleRelationDto> queryArticleListByTagAndMoudel(TagsArticleRelationDto dto);

//	/**
//	 * 根据供应还是求购，查询该标签关联的供求信息模块下的文章［文章ID，文章标题。。。］
//	 * 
//	 * @param buyOrSale 供应，求购
//	 * @param tagId 标签ID
//	 * @return
//	 */
//	List<TagsArticleRelationDto> queryProductsArticleListByProductsType(String buyOrSale,
//			Integer tagId, Integer topNum);

	void clearAllData();

}
