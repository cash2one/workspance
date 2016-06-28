package com.ast.ast1949.dto.tags;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TagsArticleRelationDto extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//	  `id` INT(20) 
	private Integer tagId;//  `tag_id` INT(20)  '标签ID' ,
	private String tagName;// `tag_name` VARCHAR(45)  '标签名称' ,	
	private String tagCatCode;
	private String tagCateLabel;
	
	private Integer articleId;// `article_id` INT(20)  '文章ID' ,
	private String articleTitle;//`article_title` VARCHAR(200)  '文章标题',
	private String articleModuleCode;//`art_moudule_code` VARCHAR(200)  '文章所属版块' ,
	private String articleModuleLabel;
	private String articleCategoryCode;//`art_cat_code` VARCHAR(200) '文章内容分类' ,
	private String articleCategoryLabel;	
	private String articleContentSummary;//文章内容摘要
	private Date refreshTime;//文章最后更新时间
	
	private Integer companyId;//公司ID
	private String companyName;//公司名称
	private String companyMembershipCode;//公司会员类型
	
	private String statTypeCode;
	private String statTypeLabel;
	private Integer statScore;
	
	private Integer creator;// `creator` INT(20)  '创建人' ,
	private Date gmtCreated; //gmt_created  DATETIME '创建时间' ,
	private Date gmtModified;//gmt_modified DATETIME '修改时间' ,
	
	private Integer startIndex;//查询返回记录起始位置
	private Integer topNum;//查询返回记录量
	private String productsTypeCode;//供求信息类型，供应／求购CODE
	private String buyOrSale;//供求信息类型，供应／求购 名称
	public String getBuyOrSale() {
		return buyOrSale;
	}
	public void setBuyOrSale(String buyOrSale) {
		this.buyOrSale = buyOrSale;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagCatCode() {
		return tagCatCode;
	}
	public void setTagCatCode(String tagCatCode) {
		this.tagCatCode = tagCatCode;
	}
	public String getTagCateLabel() {
		return tagCateLabel;
	}
	public void setTagCateLabel(String tagCateLabel) {
		this.tagCateLabel = tagCateLabel;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleModuleCode() {
		return articleModuleCode;
	}
	public void setArticleModuleCode(String articleModuleCode) {
		this.articleModuleCode = articleModuleCode;
	}
	public String getArticleModuleLabel() {
		return articleModuleLabel;
	}
	public void setArticleModuleLabel(String articleModuleLabel) {
		this.articleModuleLabel = articleModuleLabel;
	}
	public String getArticleCategoryCode() {
		return articleCategoryCode;
	}
	public void setArticleCategoryCode(String articleCategoryCode) {
		this.articleCategoryCode = articleCategoryCode;
	}
	public String getArticleCategoryLabel() {
		return articleCategoryLabel;
	}
	public void setArticleCategoryLabel(String articleCategoryLabel) {
		this.articleCategoryLabel = articleCategoryLabel;
	}
	public String getArticleContentSummary() {
		return articleContentSummary;
	}
	public void setArticleContentSummary(String articleContentSummary) {
		this.articleContentSummary = articleContentSummary;
	}
	public Date getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyMembershipCode() {
		return companyMembershipCode;
	}
	public void setCompanyMembershipCode(String companyMembershipCode) {
		this.companyMembershipCode = companyMembershipCode;
	}
	public String getStatTypeCode() {
		return statTypeCode;
	}
	public void setStatTypeCode(String statTypeCode) {
		this.statTypeCode = statTypeCode;
	}
	public String getStatTypeLabel() {
		return statTypeLabel;
	}
	public void setStatTypeLabel(String statTypeLabel) {
		this.statTypeLabel = statTypeLabel;
	}
	public Integer getStatScore() {
		return statScore;
	}
	public void setStatScore(Integer statScore) {
		this.statScore = statScore;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getTopNum() {
		return topNum;
	}
	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}
	public String getProductsTypeCode() {
		return productsTypeCode;
	}
	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}
	
}
