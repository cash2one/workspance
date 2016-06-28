/*
 * 文件名称：Common.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：全局公用信息实体类（主要显示前台页面片段缓存）。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CommonDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 编号
	private String title;// 标题
	private String titleIndex;// 标题缩写
	private String description;// 简单描述
	private String code;// 类别code
	private String name;// 类别名称
	private String videoUrl;//视频地址
	private String orPhoto;//图片地址
	private String details;
	
	

	public String getOrPhoto() {
		return orPhoto;
	}

	public void setOrPhoto(String orPhoto) {
		this.orPhoto = orPhoto;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleIndex() {
		return titleIndex;
	}

	public void setTitleIndex(String titleIndex) {
		this.titleIndex = titleIndex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}