package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneSeoKeyWords;
import com.ast.ast1949.dto.PageDto;

public interface PhoneSeoKeyWordService {
	public Integer insert(PhoneSeoKeyWords phoneSeoKeyWords);
	/**
	 * 通过id 查找seo关键字
	 * @param id
	 * @return
	 */
	public PhoneSeoKeyWords queryPhoneSeoKeyWordById(Integer id);
	
	/**
	 * 修改Phone_seo_keywords 表
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public Integer updateSeoKeyWord(PhoneSeoKeyWords phoneSeoKeyWords);
	
	/**
	 * 查找关键字
	 * @param page
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public PageDto<PhoneSeoKeyWords> queryList(PageDto<PhoneSeoKeyWords>page,PhoneSeoKeyWords phoneSeoKeyWords);
	
	/**
	 * 统计总数
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public Integer queryListCount(PhoneSeoKeyWords phoneSeoKeyWords);
	/**
	 * 通过id 删除seo关键字
	 * @param id
	 * @return
	 */
	public Integer delSeoKeyWordsById(Integer id);
}
