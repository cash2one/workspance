package com.zz91.ep.service.comp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.comp.CompanyIndustryChainDao;
import com.zz91.ep.dao.comp.IndustryChainDao;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.domain.comp.IndustryChainDto;
import com.zz91.ep.service.comp.IndustryChainService;

@Component("IndustryChainService")
public class IndustryChainServiceImpl implements IndustryChainService {
	
	@Resource
	private IndustryChainDao industryChainDao;
	@Resource
	private CompanyIndustryChainDao companyIndustryChainDao;
	
	@Override
	public List<IndustryChain> queryIndustryChains(Integer size, String areaCode) {
			
		return industryChainDao.queryIndustryChains(size, areaCode);
	}

	@Override
	public IndustryChain queryIndustryChainName(Integer id) {
		
		return industryChainDao.queryIndustryChainById(id);
	}

	
	@Override
	public List<IndustryChainDto> queryIndustryChainByCid(Integer cid,
			String areaCode) {

		List<IndustryChainDto> list = new ArrayList<IndustryChainDto>();
		IndustryChainDto dto = null;

		Map<Integer, IndustryChain> map = new HashMap<Integer, IndustryChain>();

		if (cid != null) {

			List<Integer> chainIds = companyIndustryChainDao
					.queryIndustryChainIdByCid(cid);

			for (Integer chainId : chainIds) {

				map.put(chainId,
						industryChainDao.queryIndustryChainById(chainId));

			}
		}
		
		// TODO 不应该用like，会导致查找过多的信息，应用准确搜索
		for (IndustryChain industryChain : industryChainDao.queryIndustryChains(null, areaCode)) {

			dto = new IndustryChainDto();

			Integer id = industryChain.getId();

			if (map.get(id) != null) {
				dto.setChecked("checked");
			}

			dto.setIndustryChain(industryChain);

			list.add(dto);
		}

		return list;
	}

}
