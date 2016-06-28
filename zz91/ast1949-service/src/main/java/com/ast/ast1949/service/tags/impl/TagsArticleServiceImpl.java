package com.ast.ast1949.service.tags.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.tags.TagsArticleRelation;
import com.ast.ast1949.domain.tags.TagsInfoDO;
import com.ast.ast1949.dto.tags.CategoryTagDto;
import com.ast.ast1949.dto.tags.TagsArticleRelationDto;
import com.ast.ast1949.persist.tags.TagsArticleRelationDao;
import com.ast.ast1949.persist.tags.TagsInfoDAO;
import com.ast.ast1949.persist.tags.TagsStatisticDao;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.tags.TagsArticleService;
import com.ast.ast1949.util.Assert;

@Component("tagsArticleService")
public class TagsArticleServiceImpl implements TagsArticleService {

	@Resource
	private TagsArticleRelationDao tagsArticleRelationDao;
	@Resource
	private TagsStatisticDao tagsStatisticDao;
	@Resource
	private TagsInfoDAO tagsInfoDAO;

	@Override
	public int deleteTagsArticleRelationByArticleId(String moduleCatCode,Integer articleId) {
		return tagsArticleRelationDao.deleteTagsArticleRelationByArticleId(moduleCatCode,articleId);
	}

//	@Override
//	public boolean deleteTagsArticleRelationById(Integer id) {
//		return tagsArticleRelationDao.deleteTagsArticleRelationById(id);
//	}

//	@Override
//	public int deleteTagsArticleRelationByTagId(Integer tagId) {
//		return tagsArticleRelationDao.deleteTagsArticleRelationByTagId(tagId);
//	}

//	@Override
//	public boolean deleteTagsArticleRelationByTagIdAndArticleId(Integer tagId, Integer articleId) {
//		return tagsArticleRelationDao
//				.deleteTagsArticleRelationByTagIdAndArticleId(tagId, articleId);
//	}

	@Override
	public boolean insertTagsArticleRelation(TagsArticleRelation relation) {
		//新增标签关联信息时先检查标签是否存在，不存在则新建一个标签信息
		Assert.notNull(relation, "标签关联信息不能为空。");
		Assert.notNull(relation.getTagName(), "标签名称不能为空。");
		Assert.notNull(relation.getArticleId(), "标签关联的文章不能为空。");
		TagsInfoDO tagInfo = null;
		if (relation.getTagId() == null || relation.getTagId() <= 0) {
			tagInfo = tagsInfoDAO.queryTagsInfoByName(relation.getTagName());
		} else {
			tagInfo = tagsInfoDAO.queryTagsInfoById(relation.getTagId());
		}
		if (tagInfo == null || !tagInfo.getName().equals(relation.getTagName())) {
			tagInfo = new TagsInfoDO();
			tagInfo.setName(relation.getTagName());
			Integer tagId = tagsInfoDAO.insertTagsInfo(tagInfo);
			//如果标签创建失败直接返回关联建立失败
			if (tagId == null || tagId <= 0)
				return false;
			relation.setTagId(tagId);
		}
		relation.setTagId(tagInfo.getId());
		int i=tagsArticleRelationDao.insertTagsArticleRelation(relation);
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public Integer queryArticleCountByTagAndMoudel(TagsArticleRelationDto dto) {
		Assert.notNull(dto, "参数不能为空。");
		Assert.notNull(dto.getArticleModuleCode(), "请指定要查询的标签关联模块。");
		return tagsArticleRelationDao.queryArticleCountByTagAndMoudel(dto);
	}

	@Override
	public List<TagsArticleRelationDto> queryArticleListByTagAndMoudel(TagsArticleRelationDto dto) {
		Assert.notNull(dto, "参数不能为空。");
		Assert.notNull(dto.getArticleModuleCode(), "请指定要查询的标签关联模块。");
		return tagsArticleRelationDao.queryArticleListByTagAndMoudel(dto);
	}

	@Override
	public List<TagsInfoDO> queryPopularSearchTagList(Integer topNum) {
		//'200200010005':'搜索标签 一周热门搜索标签
		return tagsStatisticDao.queryWeeklyStatTagsByStatCat("200200010005", 6);
	}

	//	@Override
	//	public Integer queryProductsArticleCountByProductsType(String buyOrSale, Integer tagId) {
	//		return tagsArticleRelationDao.queryProductsArticleCountByProductsType(buyOrSale, tagId);
	//	}
	//
	//	@Override
	//	public List<TagsArticleRelationDto> queryProductsArticleListByProductsType(String buyOrSale,
	//			Integer tagId, Integer topNum) {
	//		return tagsArticleRelationDao.queryProductsArticleListByProductsType(buyOrSale, tagId,
	//				topNum);
	//	}

	@Override
	public List<TagsInfoDO> queryTagListByArtCat(String artCatCode, Integer topNum) {
		return tagsArticleRelationDao.queryTagListByTagCatAndArtCat(null, artCatCode, topNum);
	}

	@Override
	public List<TagsInfoDO> queryTagListByModuleCatAndArtCat(String moduleCatCode,
			String artCatCode, Integer topNum) {
		return tagsArticleRelationDao.queryTagListByModuleCatAndArtCat(moduleCatCode, artCatCode,
				topNum);
	}

//	@Override
//	public List<TagsInfoDO> queryTagListByStatCatAndArtCat(String statCatCode, String artCatCode,
//			Integer topNum) {
//		return tagsStatisticDao.queryTagListByStatCatAndArtCat(statCatCode, artCatCode, topNum);
//	}

	@Override
	public List<TagsInfoDO> queryTagListByTagCatAndArtCat(String tagCatCode, String artCatCode,
			Integer topNum) {
//		Assert.isTrue(CategoryTagDto.STAT.equalsIgnoreCase(queryType);
		return tagsArticleRelationDao.queryTagListByTagCatAndArtCat(tagCatCode, artCatCode, topNum);
		//		List<CategoryTagDto> categoryTagList = new ArrayList<CategoryTagDto>();
		//		//如果产品分类为空则查询所有一级产品分类
		//		if (artCatCode == null || artCatCode.trim().length() <= 0) {
		//			//获取产品分类主类别列表
		//			List<CategoryProductsDO> categoryList = CategoryFacade.getInstance()
		//					.listCategoryProductsLength(4);
		//			for (CategoryProductsDO category : categoryList) {
		//				CategoryTagDto dto = new CategoryTagDto();
		//				dto.setCode(category.getCode());
		//				dto.setTagList(tagsArticleRelationDao.queryTagListByTagCatAndArtCat(tagCatCode,
		//						category.getCode(), topNum));
		//				categoryTagList.add(dto);
		//			}
		//		} else {
		//			CategoryTagDto dto = new CategoryTagDto();
		//			dto.setCode(tagCatCode);
		//			dto.setTagList(tagsArticleRelationDao.queryTagListByTagCatAndArtCat(tagCatCode,
		//					artCatCode, topNum));
		//			categoryTagList.add(dto);
		//		}
		//		return categoryTagList;
	}

	@Override
	public List<CategoryTagDto> queryTagListGroupArtCatByQType(CategoryTagDto categoryTagDto) {
		String queryType = categoryTagDto.getQueryType();
		Assert.isTrue(CategoryTagDto.STAT.equalsIgnoreCase(queryType)
				|| CategoryTagDto.MODULE.equalsIgnoreCase(queryType)
				|| CategoryTagDto.CATEGORY.equalsIgnoreCase(queryType), "查询类型参数值错误。");
		Assert.isTrue(categoryTagDto.getCode() != null
				&& categoryTagDto.getCode().trim().length() > 0, "标签类型参数值错误。");
		if (categoryTagDto.getTopNum() <= 0) {
			categoryTagDto.setTopNum(24);
		}
		//［产品分类｛金属，塑料。。。｝］［标签列表。。。。］
		List<CategoryTagDto> categoryTagList = new ArrayList<CategoryTagDto>();
		Map<String, String> categoryMap = CategoryProductsFacade.getInstance().getChild(null);
		if (CategoryTagDto.STAT.equalsIgnoreCase(queryType)) {
			for (Entry<String, String> map : categoryMap.entrySet()) {
				CategoryTagDto catTag = new CategoryTagDto();
				catTag.setCode(map.getKey());//分类编码
				catTag.setLabel(map.getValue());//分类名称
				//查询标签列表
				List<TagsInfoDO> tagList = tagsStatisticDao.queryTagListByStatCatAndArtCat(categoryTagDto.getCode(), map.getKey(),
						categoryTagDto.getTopNum());
				catTag.setTagList(tagList);
				categoryTagList.add(catTag);
			}
		}
		if (CategoryTagDto.CATEGORY.equalsIgnoreCase(queryType)) {
			for (Entry<String, String> map : categoryMap.entrySet()) {
				CategoryTagDto catTag = new CategoryTagDto();
				catTag.setCode(map.getKey());//分类编码
				catTag.setLabel(map.getValue());//分类名称
				//查询标签列表
				List<TagsInfoDO> tagList = queryTagListByTagCatAndArtCat(categoryTagDto.getCode(), map.getKey(),
						categoryTagDto.getTopNum());
				catTag.setTagList(tagList);
				categoryTagList.add(catTag);
			}
		}
		if (CategoryTagDto.MODULE.equalsIgnoreCase(queryType)) {
			for (Entry<String, String> map : categoryMap.entrySet()) {
				CategoryTagDto catTag = new CategoryTagDto();
				catTag.setCode(map.getKey());//分类编码
				catTag.setLabel(map.getValue());//分类名称
				//查询标签列表
				List<TagsInfoDO> tagList = tagsArticleRelationDao.queryTagListByModuleCatAndArtCat(categoryTagDto.getCode(), map.getKey(),
						categoryTagDto.getTopNum());
				catTag.setTagList(tagList);
				categoryTagList.add(catTag);
			}
		}
		return categoryTagList;
	}

	@Override
	public List<CategoryTagDto> queryTagListByQTypeAndCat(CategoryTagDto categoryTagDto) {
		//		［标签大类｛统计，标签类别，模块｝］［标签列表。。。。］
		String queryType = categoryTagDto.getQueryType();
		//查询类型只能为［stat,module,category］
		Assert.isTrue(CategoryTagDto.STAT.equalsIgnoreCase(queryType)
				|| CategoryTagDto.MODULE.equalsIgnoreCase(queryType)
				|| CategoryTagDto.CATEGORY.equalsIgnoreCase(queryType), "查询类型参数值错误。");
		//标签分类：［'0'：'默认', '1'： '数量标签'， '2'：'颜色标签', '3'： '稀贵标签'］
		//统计分类：［'200200010004':'点击标签','200200010006':'关联标签'］
		//模块分类：［'1035':'归类信息方便查询',
		//				'10351000':'新闻资讯行情',
		//				'10351001':'供求信息'
		//				'10351002':'企业报价'
		//				'10351003':'报价']
		//查询此分类及其子类的标签列表
		Assert.isTrue(categoryTagDto.getCode() != null
				&& categoryTagDto.getCode().trim().length() > 0, "标签类型参数值错误。");

		if (categoryTagDto.getTopNum() <= 0) {
			categoryTagDto.setTopNum(30);
		}
		if (CategoryTagDto.STAT.equalsIgnoreCase(queryType)) {
			return queryTagListGroupByStatCat(categoryTagDto);
		}
		if (CategoryTagDto.CATEGORY.equalsIgnoreCase(queryType)) {
			return queryTagListGroupByTagCat(categoryTagDto);
		}
		if (CategoryTagDto.MODULE.equalsIgnoreCase(queryType)) {
			return queryTagListGroupByModuleCat(categoryTagDto);
		}
		return new ArrayList<CategoryTagDto>();
	}

	/**
	 * 查询统计类别下标签列表
	 * 
	 * @return
	 */
	private List<CategoryTagDto> queryTagListGroupByStatCat(CategoryTagDto categoryTagDto) {
		List<CategoryTagDto> categoryTagDtoList = new ArrayList<CategoryTagDto>();
		//统计分类应该按给定的分类CODE查询此分类下直接的二级分类，此处暂时写定
		//		 com.ast.ast1949.service.facade.CategoryFacade		
		//统计分类：［'200200010004':'点击标签','200200010005':'搜索标签','200200010006':'关联标签'］
		String[] statCode = {"200200010004","200200010006"};
		String[] statLabel = {"标签点击量","标签关联文章量"};
		//获取标签类别的标签统计类别列表
		List<CategoryDO> categoryList = new ArrayList<CategoryDO>();
		for (int i = 0; i < statCode.length; i++) {
			CategoryDO cat1 = new CategoryDO();
			cat1.setCode(statCode[i]);
			cat1.setLabel(statLabel[i]);
			categoryList.add(cat1);
		}
		//获取标签统计下的标签列表
		for (CategoryDO category : categoryList) {
			CategoryTagDto catTag = new CategoryTagDto();
			catTag.setCode(category.getCode());//分类编码
			catTag.setLabel(category.getLabel());//分类名称
			//查询标签列表
			List<TagsInfoDO> tagList = tagsStatisticDao.queryTagListByStatCatAndArtCat(category.getCode(), null,
					categoryTagDto.getTopNum());
			catTag.setTagList(tagList);
			categoryTagDtoList.add(catTag);
		}
		return categoryTagDtoList;
	}

	/**
	 * 查询模块类别下标签列表
	 * 
	 * @return
	 */
	private List<CategoryTagDto> queryTagListGroupByModuleCat(CategoryTagDto categoryTagDto) {
		List<CategoryTagDto> categoryTagDtoList = new ArrayList<CategoryTagDto>();
		//模块分类应该按给定的分类CODE查询此分类下直接的二级分类，此处暂时写定
		//		 com.ast.ast1949.service.facade.CategoryFacade;
		//模块分类：
		//				'10351000':'新闻资讯行情',
		//				'10351001':'供求信息'
		//				'10351002':'企业报价'
		//				'10351003':'报价']
		String[] statCode = {"10351001","10351003"};
		String[] statLabel = {"供求信息","产品报价"};
		//获取标签类别的信息类别列表
		List<CategoryDO> categoryList = new ArrayList<CategoryDO>();
		for (int i = 0; i < statCode.length; i++) {
			CategoryDO cat1 = new CategoryDO();
			cat1.setCode(statCode[i]);
			cat1.setLabel(statLabel[i]);
			categoryList.add(cat1);
		}
		//获取标签关联信息分类下的标签列表
		for (CategoryDO category : categoryList) {
			CategoryTagDto catTag = new CategoryTagDto();
			catTag.setCode(category.getCode());//分类编码
			catTag.setLabel(category.getLabel());//分类名称
			//查询标签列表
			List<TagsInfoDO> tagList = tagsArticleRelationDao.queryTagListByModuleCatAndArtCat(category.getCode(), null,
					categoryTagDto.getTopNum());
			catTag.setTagList(tagList);
			categoryTagDtoList.add(catTag);
		}
		return categoryTagDtoList;
	}

	/**
	 * 查询标签类别下标签列表
	 * 
	 * @return
	 */
	private List<CategoryTagDto> queryTagListGroupByTagCat(CategoryTagDto categoryTagDto) {
		List<CategoryTagDto> categoryTagDtoList = new ArrayList<CategoryTagDto>();
		//标签分类查询参数表中的标签分类参数，此处暂时写定
		//		com.ast.ast1949.service.facade.ParamFacade;
		//标签分类：［'0'：'默认', '1'： '数量标签'， '2'：'颜色标签', '3'： '稀贵标签','4':'热门标签'］
		String[] statCode = {"1","2","3","4"};
		String[] statLabel = {"数量标签","颜色标签","稀有标签","热门标签"};

		//获取标签类别的信息类别列表
		List<CategoryDO> categoryList = new ArrayList<CategoryDO>();
		for (int i = 0; i < statCode.length; i++) {
			CategoryDO cat1 = new CategoryDO();
			cat1.setCode(statCode[i]);
			cat1.setLabel(statLabel[i]);
			categoryList.add(cat1);
		}
		//获取标签关联信息分类下的标签列表
		for (CategoryDO category : categoryList) {
			CategoryTagDto catTag = new CategoryTagDto();
			catTag.setCode(category.getCode());//分类编码
			catTag.setLabel(category.getLabel());//分类名称
			//查询标签列表
			List<TagsInfoDO> tagList = tagsInfoDAO.queryTagsInfoByType(category.getCode(),
					categoryTagDto.getTopNum());
			catTag.setTagList(tagList);
			categoryTagDtoList.add(catTag);
		}
		return categoryTagDtoList;
	}

	@Override
	public List<TagsInfoDO> queryTagListFromTagsArticleRelationByArticleId(String artModuleCode,
			Integer id) {
		Assert.notNull(artModuleCode, "文章归属模块不能为空。");
		Assert.notNull(id, "文章ID不能为空");
		return tagsArticleRelationDao.queryTagListFromTagsArticleRelationByArticleId(artModuleCode,
				id);
	}

//	@Override
//	public TagsArticleRelation queryTagsArticleRelationById(Integer id) {
//		return tagsArticleRelationDao.queryTagsArticleRelationById(id);
//	}

	@Override
	public List<TagsInfoDO> queryWeeklyTagTopList(String statCatCode, Integer topNum) {
		//一击标签排行榜
		return tagsStatisticDao.queryWeeklyStatTagsByStatCat(statCatCode, topNum);
	}

//	@Override
//	public Integer updateTagsArticleRelation(TagsArticleRelation relation) {
//		return tagsArticleRelationDao.updateTagsArticleRelation(relation);
//	}
}
