/**
 * @author shiqp
 * @date 2016-01-19
 */
package com.ast.feiliao91.service.goods.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.persist.goods.PictureDao;
import com.ast.feiliao91.service.goods.PictureService;
import com.zz91.util.lang.StringUtils;
@Component("pictureService")
public class PictureServiceImpl implements PictureService {
	@Resource
	private PictureDao pictureDao;

	@Override
	public Integer createPicture(Picture picture) {
		return pictureDao.insertPicture(picture);
	}

//	@Override
//	public void dealPicture(String pid,Integer id, String targetType) {
//		String[] pList = pid.split(",");
//		for(String s : pList){
//			//检索图片信息
//			Picture pic = pictureDao.queryById(Integer.valueOf(s));
//			if(pic!=null){ //图片存在
//					if(pic.getTargetId()!=null&&id!=pic.getTargetId()){ //相册上传的图片,需要创建新的图片信息
//						Picture picture = new Picture();
//						picture.setCompanyId(pic.getCompanyId());
//						picture.setTargetId(id);
//						picture.setTargetType(targetType);
//						picture.setPicAddress(pic.getPicAddress());
//						pictureDao.insertPicture(picture);		
//					}else if(id!=pic.getTargetId()){ //本地上传的图片，只需更新目标编号
//						pictureDao.updateTargetId(pic.getId(), id);
//					}
//				
//			}
//		}
//		
//	}
	
	@Override
	public void dealPicture(String pid,Integer id, String targetType) {
		String[] pList = pid.split(",");
		int i = 0;
		for(String s : pList){
			if (i>5) {
				break;
			}
			//检索图片信息(zhujq改)
			if(StringUtils.isEmpty(s)){
				continue;
			}
			Picture pic = pictureDao.queryById(Integer.valueOf(s));
			if(pic==null){
				continue;
			}
			if(pic.getTargetId()==null){
				continue;
			}
			if (StringUtils.isEmpty(targetType)) {
				continue;
			}
			if(targetType.equals(pic.getTargetType())){
				//本地上传的图片，只需更新目标编号
				pictureDao.updateTargetId(pic.getId(), id,targetType);
			}else{
				//相册上传的图片,需要创建新的图片信息
				Picture picture = new Picture();
				picture.setCompanyId(pic.getCompanyId());
				picture.setTargetId(id);
				picture.setTargetType(targetType);
				picture.setPicAddress(pic.getPicAddress());
				pictureDao.insertPicture(picture);		
			}
			i++;
		}
	}
	
	@Override
	public void deleteAllPicInThisGoods(Integer targetId){
		pictureDao.deleteAllPicInThisGoods(targetId);
	}
	
	@Override
	public PageDto<Picture> pagePictureList(PageDto<Picture> page, Integer companyId, String targetType) {
		List<Picture> list = pictureDao.queryPicList(page, companyId, targetType);
		page.setRecords(list);
		page.setTotalRecords(pictureDao.countPicList(companyId, targetType));
		return page;
	}

	@Override
	public List<Picture> queryPictureByCondition(Integer targetId, String targetType, Integer companyId, Integer size) {
		return pictureDao.queryPictureByCondition(targetId, targetType, companyId, size);
	}

	@Override
	public List<String> selecPicById(String picAddress) {
		List<String> picture = new ArrayList<String>();
		String [] ar = picAddress.split(",");
		for(String s:ar){
		Picture pic = pictureDao.queryById(Integer.valueOf(s));
		if(pic!=null){
			if(pic.getIsDel().equals(0)){
			picture.add(pic.getPicAddress());
			}
		}
		}
		return picture;
	}

	@Override
	public Integer deletePic(Integer id) {
		return pictureDao.deletePic(id);
	}

	@Override
	public List<String> selectByAddr(List<String> picAddress) {
		List<String> list = new ArrayList<String>();
		if(picAddress!=null){
		for(String arr :picAddress){
			Integer id =pictureDao.selectByAddr(arr);
			if(id!=null){
				list.add(String.valueOf(id));
			}
		}
		}
		return list;
	}
	
	@Override
	public Integer updateTargetIdZeroById(Integer Id){
		Integer i = 0;
		if (Id == null) {
			return i;
		}
		Integer id = pictureDao.updateSellPostGoodsPic(Id,0);
		if(id!=null){
			i=1;
		}
		return i;
	}
	
	@Override
	public List<Picture> queryPictureByAdmin(Integer goodsId,Integer companyId){
		return pictureDao.queryPictureByAdmin(goodsId,companyId);
	}
	
	@SuppressWarnings("unused")
	@Override
	public String batchUpdatePicStatus(String ids, String checkPerson,Integer status){
		
		StringBuffer sb = new StringBuffer();
		String[] str = ids.split(",");
		for (String s : str) {
			Integer i = pictureDao.batchUpdatePicStatus(Integer.valueOf(s), checkPerson,
					status);
			if (i == 1) {
				sb.append(s);
			}
		}
		if (sb == null) {
			return null;
		}
		return sb.toString();
	}
}
