package com.ast.ast1949.service.sample.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Custominfo;
import com.ast.ast1949.persist.sample.CustominfoDAO;
import com.ast.ast1949.service.sample.CustominfoService;

@Component("custominfoService")
public class CustominfoServiceImpl implements CustominfoService {

	@Resource
	private CustominfoDAO custominfoDao;

	@Override
	public Integer insert(Custominfo custominfo) {
		return custominfoDao.insert(custominfo);
	}

	@Override
	public int updateByPrimaryKey(Custominfo record) {
		return custominfoDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Custominfo record) {
		return custominfoDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Custominfo selectByPrimaryKey(Integer id) {
		return custominfoDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return custominfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public Custominfo selectByOrderSeq(String orderSeq) {
		return custominfoDao.selectByOrderSeq(orderSeq);
	}

	@Override
	public Custominfo selectByOrderSeqAndFlagAndType(String orderSeq, String flag, String type) {
		return custominfoDao.selectByOrderSeqAndFlagAndType(orderSeq,flag,type);
	}
	@Override
	public List<Custominfo> selectByOrderSeqAndType(String  orderSeq,String type){
		return custominfoDao.selectByOrderSeqAndType( orderSeq,type);
	}
}
