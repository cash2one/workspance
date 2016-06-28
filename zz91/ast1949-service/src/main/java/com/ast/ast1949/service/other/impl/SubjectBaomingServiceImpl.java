package com.ast.ast1949.service.other.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.other.SubjectBaoming;
import com.ast.ast1949.persist.other.SubjectBaomingDao;
import com.ast.ast1949.service.other.SubjectBaomingService;

@Component("SubjectBaomingService")
public class SubjectBaomingServiceImpl implements SubjectBaomingService{
	@Autowired
   private SubjectBaomingDao subjectBaomingDao;
	@Override
	public Integer createNewBaoming(String title, String content) {
	
		return subjectBaomingDao.createNewBaoming(title, content);
	}
}
