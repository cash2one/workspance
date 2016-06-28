package com.ast.ast1949.dto.tags;

import java.util.List;

import com.ast.ast1949.domain.tags.TagsInfoDO;

/**
 * 分类标签列表DTO
 * 
 * @author liuwb
 *
 */
public class CategoryTagDto {

	public static final String STAT="stat";
	public static final String CATEGORY="category";
	public static final String MODULE="module";
	
	private String code;
	private String label;
	private int showIndex;
	private String abbreviation;
	private List<TagsInfoDO> tagList;
	private List<CategoryTagDto> categoryTagList;
	
	private String queryType;
	private int topNum;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getShowIndex() {
		return showIndex;
	}
	public void setShowIndex(int showIndex) {
		this.showIndex = showIndex;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public List<TagsInfoDO> getTagList() {
		return tagList;
	}
	public void setTagList(List<TagsInfoDO> tagList) {
		this.tagList = tagList;
	}
	public List<CategoryTagDto> getCategoryTagList() {
		return categoryTagList;
	}
	public void setCategoryTagList(List<CategoryTagDto> categoryTagList) {
		this.categoryTagList = categoryTagList;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public int getTopNum() {
		return topNum;
	}
	public void setTopNum(int topNum) {
		this.topNum = topNum;
	}
	
}
