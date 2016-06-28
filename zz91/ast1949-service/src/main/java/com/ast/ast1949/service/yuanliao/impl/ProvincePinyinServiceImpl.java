package com.ast.ast1949.service.yuanliao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.ProvincePinyin;
import com.ast.ast1949.persist.yuanliao.ProvincePinyinDao;
import com.ast.ast1949.service.yuanliao.ProvincePinyinService;

@Component("provincePinyinService")
public class ProvincePinyinServiceImpl implements ProvincePinyinService {
	@Resource
	private ProvincePinyinDao provincePinyinDao;
	@Override
	public Map<String,String> queryAllProvincePinyin() {
		List<ProvincePinyin> list = provincePinyinDao.queryAllProvincePinyin();
		Map<String,String> map = new HashMap<String,String>();
		for(ProvincePinyin pp : list){
			map.put(pp.getCode(), pp.getPinyin());
			map.put(pp.getPinyin(), pp.getCode());
		}
		return map;
	}

}
