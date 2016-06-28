package com.zz91.ep.admin.service.crm.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.crm.CRMRightDao;
import com.zz91.ep.admin.dao.crm.CRMSvrDao;
import com.zz91.ep.admin.service.crm.CRMSvrService;
import com.zz91.ep.domain.crm.CrmRight;
import com.zz91.ep.domain.crm.CrmSvr;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.util.Assert;
@Component("crmSvrService")
public class CRMSvrServiceImpl implements CRMSvrService {

	@Resource private CRMSvrDao crmSvrDao;
	@Resource private CRMRightDao crmRightDao;
	@Override
	public Integer createCrmSvr(CrmSvr crmSvr, String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		String code=crmSvrDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			crmSvr.setCode(parentCode+String.valueOf(codeInt));
		}else{
			crmSvr.setCode(parentCode+"1000");
		}
		
		return crmSvrDao.insertCrmSvr(crmSvr);
	}

	@Override
	public Integer deleteCrmSvr(String code) {
		crmSvrDao.deleteCrmRightOfCrmSvr(code);
		return crmSvrDao.deleteCrmSvr(code);
	}

	@Override
	public List<CrmSvr> queryCrmSvr() {
		return crmSvrDao.queryCrmSvrList();
	}

	@Override
	public Integer updateCrmSvr(CrmSvr CrmSvr) {
		return crmSvrDao.updateCrmSvr(CrmSvr);
	}

	@Override
	public Integer updatecrmSvrRight(Integer crmSvrId, Integer crmRightId,
			Boolean checked) {
		if(checked==null){
			checked=false;
		}
		
		Integer i=0;
		if(checked){
			i=crmSvrDao.insertCrmSvrCrmRight(crmSvrId, crmRightId);
		}else{
			i=crmSvrDao.deleteCrmSvrCrmRight(crmSvrId, crmRightId);
		}
		
		return i;
	}

	@Override
	public List<ExtTreeDto> queryRightTreeNode(String parentCode,
			Integer crmSvrId) {
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		if(crmSvrId==null || crmSvrId.intValue()<=0){
			return nodeList;
		}
		List<Integer> crmSvrRight = crmSvrDao.queryCrmRightIdOfCrmSvr(crmSvrId);
		List<CrmRight> rightList = crmRightDao.queryChildRight(parentCode);
		
		if(rightList==null){
			return nodeList;
		}
		
		for(CrmRight right:rightList){
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(right.getId()));
			node.setData(right.getCode());
			node.setText(right.getName());
			Integer i = crmRightDao.countChildRight(right.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			if(crmSvrRight.contains(right.getId())){
				node.setChecked(true);
			}else{
				node.setChecked(false);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public List<ExtTreeDto> queryCrmSvrNode(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		List<CrmSvr> list=crmSvrDao.queryCrmSvrChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(CrmSvr crmSvr:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(crmSvr.getId()));
			node.setText(crmSvr.getName());
			node.setData(crmSvr.getCode());
			Integer i = crmSvrDao.countCrmSvrChild(crmSvr.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public CrmSvr queryOneCrmSvr(String code) {
		return crmSvrDao.queryOneCrmSvr(code);
	}

	@Override
	public String queryCloseApi(Integer crmSvrId) {
		Assert.notNull(crmSvrId, "crmSvrId不能为空呀");
		return crmSvrDao.queryCloseApi(crmSvrId);
	}

	@Override
	public String queryOpenApi(Integer crmSvrId) {
		Assert.notNull(crmSvrId, "crmSvrId不能为空呀");
		return crmSvrDao.queryOpenApi(crmSvrId);
	}

}
