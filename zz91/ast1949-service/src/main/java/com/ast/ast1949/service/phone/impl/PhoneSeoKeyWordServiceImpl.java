package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneSeoKeyWords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneSeoKeyWordDao;
import com.ast.ast1949.service.phone.PhoneSeoKeyWordService;
@Component("phoneSeoKeyWordService")
public class PhoneSeoKeyWordServiceImpl implements PhoneSeoKeyWordService{
	@Resource
	private PhoneSeoKeyWordDao phoneSeoKeyWordDao;
	@Override
	public PhoneSeoKeyWords queryPhoneSeoKeyWordById(Integer id) {
		return phoneSeoKeyWordDao.queryPhoneSeoKeyWordById(id);
	}

	@Override
	public Integer updateSeoKeyWord(PhoneSeoKeyWords phoneSeoKeyWords) {
		Integer i=0;
		Integer j=0;
		do {
			
			if (phoneSeoKeyWords==null|| phoneSeoKeyWords.getId()==null) {
				 break;
			}
			if(phoneSeoKeyWords.getPinYin()==null){
				break;
			}
			PhoneSeoKeyWords seokw=phoneSeoKeyWordDao.queryKeyWords(phoneSeoKeyWords.getPinYin(), null);
				if (seokw==null||seokw.getId().equals(phoneSeoKeyWords.getId())) {
					i=1;
				}else {
					j=2;
				}
				
			if (i==1) {
				 
				 j=phoneSeoKeyWordDao.updateSeoKeyWord(phoneSeoKeyWords);
			}
		} while (false);
		return j;
	}

	@Override
	public PageDto<PhoneSeoKeyWords> queryList(PageDto<PhoneSeoKeyWords> page,
			PhoneSeoKeyWords phoneSeoKeyWords) {
		page.setRecords(phoneSeoKeyWordDao.queryList(phoneSeoKeyWords, page));
		page.setTotalRecords(phoneSeoKeyWordDao.queryListCount(phoneSeoKeyWords));
		return page;
	}

	@Override
	public Integer queryListCount(PhoneSeoKeyWords phoneSeoKeyWords) {
		return phoneSeoKeyWordDao.queryListCount(phoneSeoKeyWords);
	}

	@Override
	public Integer insert(PhoneSeoKeyWords phoneSeoKeyWords) {
		Integer j=0;
		do {
			if(phoneSeoKeyWords.getPinYin()==null){
				break;
			}
			if(phoneSeoKeyWords.getTitle()==null){
				break;
			}
			PhoneSeoKeyWords seokw=phoneSeoKeyWordDao.queryKeyWords(phoneSeoKeyWords.getPinYin(), null);
				if (seokw==null) {
					j=phoneSeoKeyWordDao.insert(phoneSeoKeyWords);
				}else {
					j=-1;
				}
		} while (false);
		
		return j;
	}

	@Override
	public Integer delSeoKeyWordsById(Integer id) {
		Integer i=0;
		if (id!=null&&id.intValue()>0) {
			i=phoneSeoKeyWordDao.delSeoKeyWords(id);
		}
		return i;
	}

}
