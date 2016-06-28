package com.zz91.ep.service.crm.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.crm.CRMRightDao;
import com.zz91.ep.dao.sys.SysProjectDao;
import com.zz91.ep.domain.crm.CrmRight;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.service.crm.CRMRightService;
import com.zz91.util.lang.StringUtils;

@Service("crmRightService")
public class CRMRightServiceImpl implements CRMRightService {

	@Resource
	private CRMRightDao crmRightDao;
	@Resource
	private SysProjectDao sysProjectDao;
	//TODO
	@Deprecated
	@Override
	public String[] getCrmRightListByCompanyIdAndMemberCode(Integer companyId,
			String memberCode, String memberCodeBlock, String project) {

		String[] rightArr = {};
		Set<String> set = new HashSet<String>();
		String rightCode=sysProjectDao.queryRightByProject(project);
		
		try {
			// 如果memberCodeBlock不为空，则不返回服务权限，只返回用户权限
			if (StringUtils.isNotEmpty(memberCodeBlock)) {
				List<String> crmMemberRight = crmRightDao
						.queryCrmRightListByMemberCode(memberCodeBlock, rightCode);
				for (String content: crmMemberRight) {
					if (StringUtils.isNotEmpty(content)) {
						CollectionUtils.addAll(set, content.split("\\|"));
					}
				}
				rightArr = set.toArray(rightArr);
			} else if (StringUtils.isNotEmpty(memberCode)) {	//	如果memberCode不为空，则返回会员权限和服务权限的并集

				List<String> crmSvrRight = crmRightDao
						.queryCrmRightListBycompanyId(companyId, rightCode);
				List<String> crmMemberRight = crmRightDao
						.queryCrmRightListByMemberCode(memberCode, rightCode);

				for (String content : crmSvrRight) {
					if (StringUtils.isNotEmpty(content)) {
						CollectionUtils.addAll(set, content.split("\\|"));
					}
				}
				for (String content: crmMemberRight) {
					if (StringUtils.isNotEmpty(content)) {
						CollectionUtils.addAll(set, content.split("\\|"));
					}
				}
				rightArr = set.toArray(rightArr);
			}
		} catch (Exception e) {
		}
		return rightArr;
	}


	@Override
	public Integer createRight(CrmRight right, String parentCode) {
		if (parentCode == null) {
			parentCode = "";
		}
		String code = crmRightDao.queryMaxCodeOfChild(parentCode);
		if (code != null && code.length() > 0) {
			code = code.substring(parentCode.length());
			Integer codeInt = Integer.valueOf(code);
			codeInt++;
			right.setCode(parentCode + String.valueOf(codeInt));
		} else {
			right.setCode(parentCode + "1000");
		}

		if (right.getSort() == null) {
			right.setSort(0);
		}

		return crmRightDao.insertRight(right);
	}

	@Override
	public Integer deleteRightByCode(String code) {
		if (StringUtils.isEmpty(code) || code.length() <= 0) {
			return null;
		}

		crmRightDao.deleteCrmSvrRightByCode(code);
		crmRightDao.deleteMemberRightByCode(code);
		return crmRightDao.deleteRightByCode(code);
	}

	@Override
	public CrmRight queryOneRight(String code) {
		return crmRightDao.queryOneRight(code);
	}

	@Override
	public Integer updateRight(CrmRight right) {
		return crmRightDao.updateRight(right);
	}


	@Override
	public List<ExtTreeDto> queryTreeNode(String parentCode) {
		List<CrmRight> rightList = crmRightDao.queryChildRight(parentCode);
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		
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
			nodeList.add(node);
		}
		
		return nodeList;
	}
}
