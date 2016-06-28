package com.zz91.ep.admin.service.crm.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.crm.CRMMemberDao;
import com.zz91.ep.admin.dao.crm.CRMRightDao;
import com.zz91.ep.admin.service.crm.CRMMemberService;
import com.zz91.ep.domain.crm.CrmMember;
import com.zz91.ep.domain.crm.CrmRight;
import com.zz91.ep.dto.ExtTreeDto;

@Component("crmMemberService")
public class CRMMemberServiceImpl implements CRMMemberService {

	@Resource private CRMMemberDao crmMemberDao;
	@Resource private CRMRightDao crmRightDao;
	@Override
	public Integer createCrmMember(CrmMember crmMember, String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		String code=crmMemberDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			crmMember.setCode(parentCode+String.valueOf(codeInt));
		}else{
			crmMember.setCode(parentCode+"1000");
		}
		
		return crmMemberDao.insertCrmMember(crmMember);
	}

	@Override
	public Integer deleteCrmMember(String crmMemberCode) {
		crmMemberDao.deleteCrmRightOfCrmMember(crmMemberCode);
		return crmMemberDao.deleteCrmMember(crmMemberCode);
	}

	@Override
	public List<CrmMember> queryCrmMember() {
		return crmMemberDao.queryCrmMemberList();
	}

	@Override
	public Integer updateCrmMember(CrmMember crmMember) {
		return crmMemberDao.updateCrmMember(crmMember);
	}

	@Override
	public Integer updateCrmMemberRight(String crmMemberCode, Integer crmRightId,
			Boolean checked) {
		if(checked==null){
			checked=false;
		}
		
		Integer i=0;
		if(checked){
			i=crmMemberDao.insertCrmMemberCrmRight(crmMemberCode, crmRightId);
		}else{
			i=crmMemberDao.deleteCrmMemberCrmRight(crmMemberCode, crmRightId);
		}
		
		return i;
	}

	@Override
	public List<ExtTreeDto> queryRightTreeNode(String parentCode, String memberCode) {
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		if(memberCode==null){
			return nodeList;
		}
		List<Integer> crmRight = crmMemberDao.queryCrmRightIdOfCrmMember(memberCode);
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
			if(crmRight.contains(right.getId())){
				node.setChecked(true);
			}else{
				node.setChecked(false);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public List<ExtTreeDto> queryCrmMemberNode(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		List<CrmMember> list=crmMemberDao.queryCrmMemberChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(CrmMember crmMember:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(crmMember.getId()));
			node.setText(crmMember.getName());
			node.setData(crmMember.getCode());
			Integer i = crmMemberDao.countCrmMemberChild(crmMember.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public CrmMember queryOneCrmMember(String memberCode) {
		return crmMemberDao.queryOneCrmMember(memberCode);
	}

	@Override
	public List<CrmMember> queryChildMembers(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		return crmMemberDao.queryCrmMemberChild(parentCode);
	}

}
