/**
 * @author kongsj
 * @date 2014年9月3日
 * 
 */
package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneLibrary;
import com.ast.ast1949.dto.PageDto;

public interface PhoneLibraryDao {
	public List<PhoneLibrary> queryList(PhoneLibrary phoneLibrary,PageDto<PhoneLibrary> page);

	public Integer queryListCount(PhoneLibrary phoneLibrary);

	public Integer update(PhoneLibrary phoneLibrary);

	public PhoneLibrary queryById(Integer id);

	public Integer insert(PhoneLibrary phoneLibrary);

	PhoneLibrary queryByTel(String tel);
	/**
	 * 根据id 真删除该400号码
	 * @param id
	 * @return
	 */
	public Integer delNumber(Integer id);
}
