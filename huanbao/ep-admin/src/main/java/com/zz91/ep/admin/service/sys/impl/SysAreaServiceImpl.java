package com.zz91.ep.admin.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.admin.dao.sys.SysAreaDao;
import com.zz91.ep.admin.service.sys.SysAreaService;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.util.lang.StringUtils;

@Service("sysAreaService")
public class SysAreaServiceImpl implements SysAreaService {

	@Resource private SysAreaDao sysAreaDao;
	
	@Override
	public List<SysArea> queryAreaAll() {
		return sysAreaDao.queryAreaAll();
	}
	
	@Override
	public List<ExtTreeDto> queryAreaNode(String parentCode) {
		
		if(parentCode==null){
			parentCode="";
		}
		
		List<SysArea> areaList=sysAreaDao.queryChildArea(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		String childCode=null;
		for(SysArea sysArea:areaList){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(sysArea.getId()));
			node.setText(sysArea.getName());
			node.setData(sysArea.getCode());
			childCode = sysAreaDao.queryMaxCodeByPreCode(sysArea.getCode());
			if(StringUtils.isEmpty(childCode)){
				node.setLeaf(true);
			}else{
				node.setLeaf(false);
			}
			nodeList.add(node);
		}
		return nodeList;
	}
	@Override
	public List<SysArea> queryAreaChild(String parentCode) {
		return sysAreaDao.queryChildArea(parentCode);
	}

	@Override
	public String queryProvinceCodeByProvinceName(String provinceName) {
		return sysAreaDao.queryProvinceCodeByProvinceName(provinceName);
	}

}
