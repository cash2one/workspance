package com.ast.ast1949.service.photo.impl;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.photo.PhotoAbumDao;
import com.ast.ast1949.service.photo.PhotoAbumService;
import com.zz91.util.file.PicMarkUtils;

@Component("photoAbumService")
public class PhotoAbumServiceImpl implements PhotoAbumService{
	@Resource
	private PhotoAbumDao photoAbumDao;
	@Resource
	private CompanyDAO companyDAO;
	@Override
	public PageDto<PhotoAbum> queryPhotoAbumList(PageDto<PhotoAbum> page,
			Integer albumId, Integer companyId) {
		if (companyId!=null&&companyId.intValue()>0) {

			page.setTotalRecords(photoAbumDao.queryPhotoAbumListCount(albumId, companyId));
			if(page.getStartIndex()>=page.getTotalRecords()){
				page.setCurrentPage(page.getCurrentPage()-1);
				page.setStartIndex(page.getStartIndex()-page.getPageSize());;
			}
			List<PhotoAbum> list=photoAbumDao.queryPhotoAbumList(page, albumId, companyId);
			if (list!=null) {
				page.setRecords(list);
			}

			page.setTotalRecords(photoAbumDao.queryPhotoAbumListCount(albumId, companyId));

		}
		return page;
		
	}

	@Override
	public Integer insert(PhotoAbum photoAbum) {
		Integer i=0;
		if (photoAbum!=null) {
//			if (photoAbum.getCompanyId()==null||photoAbum.getCompanyId()==0) {
//				return 0;
//			}
			i=photoAbumDao.insert(photoAbum);
		}
		return i;
	}
	
	@Override
	public Integer queryPhotoAbumListCount(Integer albumId,Integer companyId){
		Integer i=0;
		if (albumId!=null&&companyId!=null&&companyId.intValue()>0) {
			i=photoAbumDao.queryPhotoAbumListCount(albumId, companyId);
		}
		return i;
	}
    @Override
    public Integer delPhotoAbum(Integer id){
    	Integer i=0;
    	if (id!=null&&id.intValue()>0) {
			i=photoAbumDao.delPhotoAbum(id);
		}
    	return i;
    }

	@Override
	public List<PhotoAbum> queryList(Integer albumId, Integer companyId) {
		
		return photoAbumDao.queryList(albumId, companyId);
	}

	@Override
	public PageDto<PhotoAbum> queryPagePhotoAbum(PageDto<PhotoAbum> page,Integer albumId, Integer companyId) {
		if (companyId!=null&&companyId.intValue()>0) {
			List<PhotoAbum> list=photoAbumDao.queryPagePhotoAbum(page, albumId, companyId);
			if (list!=null) {
				page.setRecords(list);
			}
			page.setTotalRecords(photoAbumDao.queryPagePhotoAbumCount(albumId, companyId));
			
		}
		return page;
	}
	
	@Override
	public PhotoAbum queryPhotoAbum(Integer albumId,Integer companyId){
		PhotoAbum photoAbum=new PhotoAbum();
		if (albumId!=null&&companyId!=null&&companyId.intValue()>0) {
			photoAbum=photoAbumDao.queryPhotoAbum(albumId, companyId);
		}
		return photoAbum;
	}

	@Override
	public PhotoAbum queryPhotoAbumById(Integer id) {
		PhotoAbum photoAbum=new PhotoAbum();
		if (id!=null) {
			photoAbum=photoAbumDao.queryPhotoAbumById(id);
		}
		return photoAbum;
	}

	@Override
	public Integer updateIsMarkById(Integer id) {
		Integer i=0;
		if (id!=null) {
			i=photoAbumDao.updateIsMarkById(id);
		}
		return i;
	}
	
	@Override
	public  Boolean waterMark(Integer companyId,Integer photoAbumId, String path){
		Company company = companyDAO.queryCompanyById(companyId);
		Color lightGrey = new Color(0, 0, 0);
		Boolean result=false;
		if (company != null) {
			//打水印是否成功 1：成功  0:失败
			Integer markFlag=0;
			Boolean waterMark=false;
			path="/usr/data/resources/"+path;
			Boolean mark=PicMarkUtils.pressText(path, company.getName(),"simsun", Font.BOLD, 50, lightGrey, 0, 0,(float) 0.6);
			if (mark) {
				markFlag=1;
			}
			if ("10051000".equals(company.getMembershipCode())) {
				waterMark=PicMarkUtils.pressText(path,"http://www.zz91.com", "simsun",Font.BOLD, 20, lightGrey, 1, 1,(float) 1.0);
			} else if ("10051003".equals(company.getMembershipCode())) {
				Color lightRed = new Color(255, 0, 0);
				waterMark=PicMarkUtils.pressText(path,"http://www.zz91.com/ppc/index"+ company.getId() + ".htm","simhei", Font.BOLD, 20, lightGrey, 1,1, (float) 0.8);
			} else {
				waterMark=PicMarkUtils.pressText(path,"http://" + company.getDomainZz91()+ ".zz91.com", "simhei",Font.BOLD, 20, lightGrey, 1, 1,(float) 1.0);
			}
			if (waterMark) {
				markFlag=1;
			}
			//更新photo_abum 相册管理表中是否已打水印标记  is_mark 1: 已打水印 0: 未打水印
			if (markFlag==1) {
				photoAbumDao.updateIsMarkById(photoAbumId);
				result=true;
			}
		}
		return result;
		
	}
	@Override
	public void updateCompanyIdById(Integer id, Integer companyId) {
		photoAbumDao.updateCompanyIdById(id, companyId);
	}

}
