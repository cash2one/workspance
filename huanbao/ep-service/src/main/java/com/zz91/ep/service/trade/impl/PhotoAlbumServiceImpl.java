package com.zz91.ep.service.trade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.trade.PhotoAlbumDao;
import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.domain.trade.PhotoAlbum;
import com.zz91.ep.dto.trade.PhotoAlbumDto;
import com.zz91.ep.service.trade.PhotoAlbumService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

@Component("photoAlbumService")
public class PhotoAlbumServiceImpl implements PhotoAlbumService {

	@Resource
	private PhotoAlbumDao photoAlbumDao;
	@Resource
	private PhotoDao photoDao;

	

	@Override
	public Integer getSurplusAlbum(Integer cid) {
		Assert.notNull(cid, "cid不能为空");
		Integer num = photoAlbumDao.getAlbumNum(cid);
		if(num==null){
			num=0;
		}
		return (5 - num);

	}

	@Override
	public void updateAlbumName(Integer albumId, String name) {
		photoAlbumDao.updateAlbumName(albumId, name);
	}

	@Override
	public void deleteAlbumAndPhoto(Integer albumId, Integer cid) {
		photoDao.updateAlbum(albumId, 0, cid);
		photoAlbumDao.deleteAlbumAndPhoto(albumId);
	}

	@Override
	public List<PhotoAlbumDto> queryAlbumByCid(Integer cid, Integer albumId) {
		
		Assert.notNull(cid, "cid不能为空");
		
		List<PhotoAlbumDto> list = new ArrayList<PhotoAlbumDto>();
		
		
		
		List<PhotoAlbum> albums = photoAlbumDao.queryAlbumByCid(cid, albumId);
		
		Map<Integer, PhotoAlbumDto> map = photoDao.queryPhotoByCid(cid);
		
		
		
		PhotoAlbumDto albumDto = null;
		int j = -1;
		for (int i = 0; i < albums.size(); i++) {
			j = albums.get(i).getId();
				albumDto = map.get(j);
				if(albumDto==null){
				albumDto = new PhotoAlbumDto();
				//albumDto.setPhotoCover("/huanbao/images/myesite/nopic.jpg");
				albumDto.setPhotoNum(0);
				}
			albumDto.setPhotoAlbum(albums.get(i));
			list.add(albumDto);
		}
		return list;
		
	}

	@Override
	public void createAlbum(PhotoAlbum album) {
		if (StringUtils.isEmpty(album.getAlbumType())) {
			album.setAlbumType("supply");
		}
		photoAlbumDao.insertAlbum(album);
	}

	@Override
	public String queryNameById(Integer albumId) {

		return photoAlbumDao.queryNameById(albumId);

	}

	@Override
	public List<PhotoAlbumDto> querySystemAlbum(Integer cid) {
		Map<String, String> sysAlbumMap = ParamUtils.getInstance().getChild("photo_album_type");
		
		List<PhotoAlbumDto> list = new ArrayList<PhotoAlbumDto>();
		
		for(String id:sysAlbumMap.keySet()){
			PhotoAlbumDto dto=new PhotoAlbumDto();
			PhotoAlbum album=new PhotoAlbum();
			album.setId(Integer.valueOf(id));
			album.setName(sysAlbumMap.get(id));
			dto.setPhotoAlbum(album);
			dto.setPhotoNum(photoDao.queryPhotosByCidCount(cid, Integer.valueOf(id), null));
			dto.setPhotoCover(photoDao.queryPathByAlbumId(cid, Integer.valueOf(id)));
			list.add(dto);
		}
		
		return list;
	}

	@Override
	public List<PhotoAlbumDto> queryUserAlbum(Integer cid) {
		
		List<PhotoAlbum> albumList=photoAlbumDao.queryAlbumByCid(cid, null);
		
		List<PhotoAlbumDto> list = new ArrayList<PhotoAlbumDto>();
		for(PhotoAlbum obj:albumList){
			PhotoAlbumDto dto=new PhotoAlbumDto();
			dto.setPhotoAlbum(obj);
			dto.setPhotoNum(photoDao.queryPhotosByCidCount(cid, obj.getId(), null));
			dto.setPhotoCover(photoDao.queryPathByAlbumId(cid, obj.getId()));
			list.add(dto);
		}
		return list;
	}
}
