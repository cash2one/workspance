package com.ast.ast1949.persist.tags.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ast.ast1949.domain.tags.TagsArticleRelation;
import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.TagsArticleRelationDto;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.tags.TagsArticleRelationDao;

@Repository("tagsArticleRelationDao")
public class TagsArticleRelationDaoImpl extends BaseDaoSupport implements TagsArticleRelationDao {

	private static String sqlPreFix="tagsArticleRelation";
	
	@Override
	public int deleteTagsArticleRelationByArticleId(String moduleCatCode,Integer id) {
		Map paramMap = new HashMap();
		paramMap.put("articleModuleCode", moduleCatCode);
		paramMap.put("articleId", id);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix,"deleteTagsArticleRelationByArticleId"), paramMap);
	}

//	@Override
//	public boolean deleteTagsArticleRelationById(Integer id) {
//		if (delete(addSqlKeyPreFix(sqlPreFix,"deleteTagsArticleRelationById"),id) > 0)
//			return true;
//		return false;
//	}

//	@Override
//	public boolean deleteTagsArticleRelationByTagIdAndArticleId(Integer tagId, Integer articleId) {
//		Map paramMap = new HashMap();
//		paramMap.put("tagId", tagId);
//		paramMap.put("articleId", articleId);
//		int i = template.delete(addSqlKeyPreFix(sqlPreFix,"deleteTagsArticleRelationByTagIdAndArticleId"), paramMap);
//		if (i > 0)
//			return true;
//		return false;
//	}

	@Override
	public int batchDeleteTagsArticleRelationByTagIds(int[] ids) {
		int impacted = 0;
		try {
			for (int delNum = 1; delNum <= ids.length; delNum++) {
				getSqlMapClient().startBatch();
				impacted += deleteTagsArticleRelationByTagId(ids[delNum]);
				//40 batch的数量
				if (delNum != 0 && delNum % 40 == 0)
					getSqlMapClient().executeBatch();
			}
			if (ids.length % 40 != 0)
				getSqlMapClient().executeBatch();
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}

	@Override
	public int deleteTagsArticleRelationByTagId(Integer id) {
		return delete(addSqlKeyPreFix(sqlPreFix,"deleteTagsArticleRelationByTagId"),id);
	}

	@Override
	public Integer insertTagsArticleRelation(TagsArticleRelation tagsArticleRelation) {
		tagsArticleRelation.setSqlKey(addSqlKeyPreFix(sqlPreFix,"insertTagsArticleRelation"));
		return insert(tagsArticleRelation);
	}

	@Override
	public Integer queryArticleCountByTagAndMoudel(TagsArticleRelationDto dto) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(sqlPreFix,"queryArticleCountByTagAndMoudel"), dto);
	}

	/**
	 * 根据标签和文章模块，查询该标签关联此模块的文章［文章ID，文章标题。。。］
	 * 
	 * @param id
	 *            标签ID
	 * @return
	 */
	@Override
	public List<TagsArticleRelationDto> queryArticleListByTagAndMoudel(TagsArticleRelationDto dto) {
		String querySql = addSqlKeyPreFix(sqlPreFix,"queryProductsArticleListByTagAndMoudel");
		if (!"10351001".equals(dto.getArticleModuleCode())) {//('10351001','供求信息');
			querySql = addSqlKeyPreFix(sqlPreFix,"queryPriceArticleListByTagAndMoudel");
		}
		return getSqlMapClientTemplate().queryForList(querySql, dto);
		/** 现在只分成两类[供求信息，报价资讯]，所以以下分类暂时不用 */
		//		if ("20010002".equals(moduleCatCode)) {//('20010002','新闻资讯行情');
		//			querySql=addSqlKeyPreFix(sqlPreFix,"queryPriceArticleListByTagAndMoudel";
		//		} else if ("20010003".equals(moduleCatCode)) {//('20010003','产品报价');
		//			querySql=addSqlKeyPreFix(sqlPreFix,"queryCompanyPriceArticleListByTagAndMoudel";
		//		} 
		//		else if ("20010004".equals(moduleCatCode)) {//('20010004','公司信息');
		//			querySql="";
		//		}

	}

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
	@Override
	public List<TagsInfoDO> queryTagListByModuleCatAndArtCat(String moduleCatCode,
			String artCatCode, Integer topNum) {
		if (moduleCatCode == null) {}
		Map paramMap = new HashMap();
		paramMap.put("moduleCatCode", moduleCatCode);
		paramMap.put("artCatCode", artCatCode);
		paramMap.put("topNum", topNum);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix,"queryTagListByModuleCatAndArtCat"), paramMap);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<TagsInfoDO> queryTagListByTagCatAndArtCat(String tagCatCode, String artCatCode,
			Integer topNum) {
		if (artCatCode == null) {}
		Map paramMap = new HashMap();
		paramMap.put("tagCatCode", tagCatCode);
		paramMap.put("artCatCode", artCatCode);
		paramMap.put("topNum", topNum);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix,"queryTagListByTagCatAndArtCat"), paramMap);
	}

	/**
	 * 查询与指定文章相关联的所有标签信息列表
	 * 
	 * @param id
	 *            文章ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TagsInfoDO> queryTagListFromTagsArticleRelationByArticleId(String artModuleCode ,Integer id) {
		Map paramMap = new HashMap();
		paramMap.put("moduleCatCode", artModuleCode);
		paramMap.put("id", id);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(sqlPreFix,"queryTagListFromTagsArticleRelationByArticleId"), paramMap);
	}

//	@Override
//	public Integer updateTagsArticleRelation(TagsArticleRelation tagArticleRelation) {
//		tagArticleRelation.setSqlKey(addSqlKeyPreFix(sqlPreFix,"updateTagsArticleRelation"));
//		return update(tagArticleRelation);
//	}

	@Override
	public void clearAllData() {
		clearAllData(addSqlKeyPreFix(sqlPreFix,"clearAllData"));
	}

//	@Override
//	public TagsArticleRelation queryTagsArticleRelationById(Integer id) {
//		return (TagsArticleRelation) super.query(addSqlKeyPreFix(sqlPreFix,"queryTagsArticleRelationById"),id);
//	}

}
