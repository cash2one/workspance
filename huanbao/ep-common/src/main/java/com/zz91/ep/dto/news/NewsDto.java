/**
 * 
 */
package com.zz91.ep.dto.news;

import java.io.Serializable;

import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.NewsRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-10-12 
 */
/**
 * @author qizj
 * qizj@zz91.net
 * 2011-10-12
 */
public class NewsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private News news;
	
	private NewsRecommend newsRecommend;
	
	private String categoryName;
	
	private Integer rid;
	
	private String videoUrl;
   
    private	 String photoCover;
    
    private String  orPhoto;

    
	public String getOrPhoto() {
		return orPhoto;
	}

	public void setOrPhoto(String orPhoto) {
		this.orPhoto = orPhoto;
	}


	public String getPhotoCover() {
		return photoCover;
	}

	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}

	public NewsRecommend getNewsRecommend() {
		return newsRecommend;
	}

	public void setNewsRecommend(NewsRecommend newsRecommend) {
		this.newsRecommend = newsRecommend;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getRid() {
		return rid;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	
	
}
