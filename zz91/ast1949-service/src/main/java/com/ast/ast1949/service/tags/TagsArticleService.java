package com.ast.ast1949.service.tags;

import java.util.List;

import com.ast.ast1949.domain.tags.TagsArticleRelation;
import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.CategoryTagDto;
import com.ast.ast1949.dto.tags.TagsArticleRelationDto;

public interface TagsArticleService {

	public final static String MODEL_PRODUCT = "10351001";
	/**
	 * 删除与指定文章相关的所有关联信息
	 */
	public int deleteTagsArticleRelationByArticleId(String moduleCatCode,Integer articleId);

	/**
	 * 删除指定ID的标签文章关联信息
	 * 
	 * @param id
	 *            关联信息ID
	 * @return
	 */
//	public boolean deleteTagsArticleRelationById(Integer id);

	/**
	 * 删除指定ID的标签文章关联信息
	 * 
	 * @param tagId
	 * @param articleId
	 * @return
	 */
//	public boolean deleteTagsArticleRelationByTagIdAndArticleId(Integer tagId, Integer articleId);

	/**
	 * 删除与指定标签相关的所有关联信息
	 * 
	 * @param tagId
	 * @return
	 */
//	public int deleteTagsArticleRelationByTagId(Integer tagId);

	/**
	 * 新增标签文章信息关联关系
	 * 
	 * @param relation
	 * @return
	 */
	public boolean insertTagsArticleRelation(TagsArticleRelation relation);

	/**
	 * 更新标签文章关联信息
	 * 
	 * @param relation
	 * @return
	 */
//	public Integer updateTagsArticleRelation(TagsArticleRelation relation);

	/**
	 * 查询标签文章关联关系信息
	 */
//	public TagsArticleRelation queryTagsArticleRelationById(Integer id);

	//==============================查询标签关联的文章信息相关方法===========================================
	/**
	 * 根据标签和文章模块，查询该标签关联此模块的文章信息量
	 * 
	 * @param moduleCatCode
	 * @param tagId
	 * @return
	 */
	public Integer queryArticleCountByTagAndMoudel(TagsArticleRelationDto dto);

	/**
	 * 根据标签和文章模块，查询该标签关联此模块的文章［文章ID，文章标题。。。］
	 * 
	 * @param moduleCatCode
	 *            模块ID
	 * @param tagId
	 *            标签ID
	 * @return
	 */
	public List<TagsArticleRelationDto> queryArticleListByTagAndMoudel(TagsArticleRelationDto dto);

	/**
	 * 查询供应或求购的信息量
	 * 
	 * @param buyOrSale
	 * @param tagId
	 * @return
	 */
	//	Integer queryProductsArticleCountByProductsType(String buyOrSale, Integer tagId);

	/**
	 * 根据供应还是求购，查询该标签关联的供求信息模块下的文章［文章ID，文章标题。。。］
	 * 
	 * @param buyOrSale
	 *            供应，求购
	 * @param tagId
	 *            标签ID
	 * @return
	 */
	//	List<TagsArticleRelationDto> queryProductsArticleListByProductsType(String buyOrSale,
	//			Integer tagId, Integer topNum);

	//==============================查询标签相关方法===================================================
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
	public List<TagsInfoDO> queryTagListByModuleCatAndArtCat(String moduleCatCode, String artCatCode,
			Integer topNum);

	/**
	 * 根据标签统计分类和关联信息的产品分类，查询标签信息列表
	 * 
	 * @param statCatCode
	 *            不能为空
	 * @param artCatCode
	 *            可以为空
	 * @param topNum
	 * @return
	 */
//	public List<TagsInfoDO> queryTagListByStatCatAndArtCat(String statCatCode, String artCatCode,
//			Integer topNum);

	/**
	 * 根据标签分类和关联信息的产品分类，查询标签信息列表
	 * 
	 * @param tagCatCode
	 *            可以为空
	 * @param artCatCode
	 *            可以为空
	 * @param topNum
	 * @return
	 */
	public List<TagsInfoDO> queryTagListByTagCatAndArtCat(String tagCatCode, String artCatCode,
			Integer topNum);

	/**
	 * 根据查询分类和标签类别下关联文章的产品分类，查询标签列表
	 * ［产品分类｛金属，塑料。。。｝］［标签列表。。。。］
	 * @param categoryTagDto
	 *            dto.queryType:stat 统计类型 category标签类型 module模块类型
	 * @return
	 */
	public List<CategoryTagDto> queryTagListGroupArtCatByQType(CategoryTagDto categoryTagDto);

	/**
	 * 根据查询分类和标签类别 查询此分类及其子类的标签列表
	 * ［标签大类｛统计，标签类别，模块｝］［标签列表。。。。］
	 * @param categoryTagDto
	 *            dto.queryType:stat 统计类型 category标签类型 module模块类型
	 * @return
	 */
	public List<CategoryTagDto> queryTagListByQTypeAndCat(CategoryTagDto categoryTagDto);

	/**
	 * 根据关联文章产品分类查询标签列表
	 * 
	 * @param artCatCode
	 * @param topNum
	 * @return
	 */
	public List<TagsInfoDO> queryTagListByArtCat(String artCatCode, Integer topNum);

	/**
	 * 查询与指定文章相关联的所有标签信息列表
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	public List<TagsInfoDO> queryTagListFromTagsArticleRelationByArticleId(String artModuleCode, Integer id);

	/**
	 * 查询热门搜索标签
	 * 
	 * @param topNum
	 * @return
	 */
	public List<TagsInfoDO> queryPopularSearchTagList(Integer topNum);

	/**
	 * 查询标签一周排行榜
	 * 
	 * @param statCatCode
	 *            ['200200010004':'点击量','200200010005':'搜索量','200200010006':'关联量']
	 * @param topNum
	 * @return
	 */
	public List<TagsInfoDO> queryWeeklyTagTopList(String statCatCode, Integer topNum);

	//	/**
	//	 * 列出用户最近游览的标签
	//	 * 
	//	 * @param queryType
	//	 *            ［ip/accountid］
	//	 * @param queryString
	//	 * @return
	//	 */
	//	List<TagsInfoDTO> queryTagsLastView(String queryType, String queryString);

	//	/**
	//	 * 获取最新的标签信息
	//	 */
	//	List<TagsInfoDTO> queryLastTags(TagsInfoDTO tagsInfoDto);

	//	/**
	//	 * 列出目标标签的相关标签（相关度关联规则：标签名称包含目标标签）
	//	 */
	//	List<TagsInfoDTO> queryLastTagsByTagName(TagsInfoDTO tagsInfoDto);
	//	/**
	//	 * 根据给定的标签名称查找标签信息
	//	 */
	//	TagsInfoDTO queryTagByName(String tagName);

}
