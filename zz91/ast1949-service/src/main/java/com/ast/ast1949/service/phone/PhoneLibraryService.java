/**
 * @author kongsj
 * @date 2014年9月3日
 * 
 */
package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneLibrary;
import com.ast.ast1949.dto.PageDto;

public interface PhoneLibraryService {

	final static Integer TYPE_STATUS_YES = 1;
	final static Integer TYPE_STATUS_NO = 0;

	public PageDto<PhoneLibrary> pageList(PhoneLibrary phoneLibrary,PageDto<PhoneLibrary> page);

	public Integer createPhoneLibrary(PhoneLibrary phoneLibrary);

	public Integer updateForStatusByTel(String tel, Integer status);
	
	public Integer updateForStatusById(Integer id, Integer status);
	
	public Integer updatePhoneLibraryById(Integer id,String tel,String called);
	
	public PhoneLibrary queryById(Integer id);
	
	public String queryCalledByTel(String tel);
	/**
	 * 根据id 真删除改400号码
	 * @param id
	 * @return
	 */
	public Integer delNumber(Integer id);
	
}
