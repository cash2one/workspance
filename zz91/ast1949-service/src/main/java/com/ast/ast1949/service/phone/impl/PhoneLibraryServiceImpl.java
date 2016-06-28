/**
 * @author kongsj
 * @date 2014年9月3日
 * 
 */
package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneLibrary;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneLibraryDao;
import com.ast.ast1949.service.phone.PhoneLibraryService;

@Component("phoneLibraryService")
public class PhoneLibraryServiceImpl implements PhoneLibraryService {

	@Resource
	private PhoneLibraryDao phoneLibraryDao;
	
	@Override
	public PageDto<PhoneLibrary> pageList(PhoneLibrary phoneLibrary,
			PageDto<PhoneLibrary> page) {
		page.setRecords(phoneLibraryDao.queryList(phoneLibrary, page));
		page.setTotalRecords(phoneLibraryDao.queryListCount(phoneLibrary));
		return page;
	}

	@Override
	public Integer createPhoneLibrary(PhoneLibrary phoneLibrary) {
		PhoneLibrary obj = phoneLibraryDao.queryByTel(phoneLibrary.getTel());
		if(obj!=null){
			return 0;
		}
		phoneLibrary.setStatus(PhoneLibraryService.TYPE_STATUS_YES);
		return phoneLibraryDao.insert(phoneLibrary);
	}

	@Override
	public Integer updateForStatusByTel(String tel, Integer status) {
		PhoneLibrary phoneLibrary =  phoneLibraryDao.queryByTel(tel);
		if(phoneLibrary!=null){
			phoneLibrary.setStatus(status);
			return phoneLibraryDao.update(phoneLibrary);
		}
		return 0;
	}

	@Override
	public PhoneLibrary queryById(Integer id) {
		return phoneLibraryDao.queryById(id);
	}

	@Override
	public Integer updateForStatusById(Integer id, Integer status) {
		PhoneLibrary phoneLibrary =  phoneLibraryDao.queryById(id);
		if(phoneLibrary!=null){
			phoneLibrary.setStatus(status);
			return phoneLibraryDao.update(phoneLibrary);
		}
		return 0;
	}


	@Override
	public String queryCalledByTel(String tel) {
		PhoneLibrary obj = phoneLibraryDao.queryByTel(tel);
		if (obj!=null) {
			return obj.getCalled();
		}
		return "";
	}


	@Override
	public Integer updatePhoneLibraryById(Integer id,String tel,String called){
		if(id!=null){
			PhoneLibrary phoneLibrary=new PhoneLibrary();
			phoneLibrary.setId(id);
			phoneLibrary.setCalled(called);
			phoneLibrary.setTel(tel);
			return phoneLibraryDao.update(phoneLibrary);
		}else{
			return 0;
		}
		
	}

	@Override
	public Integer delNumber(Integer id) {
		if(id!=null){
			return phoneLibraryDao.delNumber(id);
		}else {
			return 0;
		}
	}

}
