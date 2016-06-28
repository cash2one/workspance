package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.PhotoAlbum;

public class PhotoAlbumDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PhotoAlbum photoAlbum;
	private String photoCover;
	private Integer photoNum;
	
	public Integer getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(Integer photoNum) {
		this.photoNum = photoNum;
	}

	public PhotoAlbum getPhotoAlbum() {
		return photoAlbum;
	}

	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}

	public String getPhotoCover() {
		return photoCover;
	}

	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}

	
}
