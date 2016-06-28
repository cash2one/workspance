package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneSeoKeyWords;
import com.ast.ast1949.dto.PageDto;

public interface PhoneSeoKeyWordDao {
	/**
	 * 插入 phone_seo_keywords 记录
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public Integer insert (PhoneSeoKeyWords phoneSeoKeyWords);
	
	/**
	 * 通过id 查找关键字
	 * @param id
	 * @return
	 */
	public PhoneSeoKeyWords queryPhoneSeoKeyWordById(Integer id);
	
	/**
	 * 修改 phone_seo_keywords 表
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public Integer updateSeoKeyWord(PhoneSeoKeyWords phoneSeoKeyWords);
	/**
	 * 查找关键字
	 * @param phoneSeoKeyWords
	 * @param page
	 * @return
	 */
	public List<PhoneSeoKeyWords> queryList(PhoneSeoKeyWords phoneSeoKeyWords,PageDto<PhoneSeoKeyWords> page);
	/**
	 * 统计总数
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public Integer queryListCount(PhoneSeoKeyWords phoneSeoKeyWords);
	
	/**
	 * 查找 PhoneSeoKeyWords 
	 * @param phoneSeoKeyWords
	 * @return
	 */
	public PhoneSeoKeyWords queryKeyWords(String pinyin, String title);
	/**
	 *通过id 删除seo关键字
	 * @return
	 */
	public Integer delSeoKeyWords(Integer id);
}
