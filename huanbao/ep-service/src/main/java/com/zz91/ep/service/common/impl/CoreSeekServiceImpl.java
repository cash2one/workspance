/**
 * @author kongsj
 * @date 2014年7月24日
 * 
 */
package com.zz91.ep.service.common.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompanyNormDto;
import com.zz91.ep.dto.search.SearchCompanyDto;
import com.zz91.ep.service.common.CoreSeekService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("coreSeekService")
public class CoreSeekServiceImpl implements CoreSeekService{

	@Resource
	private CompProfileDao compProfileDao;
	
	@Override
	public PageDto<CompanyNormDto> pageCompany(	SearchCompanyDto search,PageDto<CompanyNormDto> page) {
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		List<CompanyNormDto> resultList = new ArrayList<CompanyNormDto>();
		try {
			
			String keywords = search.getKeywords();
			if (StringUtils.isNotEmpty(keywords)&&!StringUtils.isContainCNChar(keywords)) {
				keywords = StringUtils.decryptUrlParameter(keywords);
			}
			
			sb.append("@(pname,pdetails,area_label1,area_label2,area_label3,area_label4,area_label5,parea_name,pprovince_name,indeustry_name) ").append(keywords);
			
			cl.SetFilter("del_status", 0, false);
			
			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStart(),page.getLimit(),50000);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "member_code desc,gmt_created asc");
			// 供应刷新时间三天内
			SphinxResult res=cl.Query(sb.toString(), "huanbao_company");
			if (res==null) {
				return new PageDto<CompanyNormDto>();
			}else{
				page.setTotals(res.totalFound);
				for( int i=0; i<res.matches.length; i++ ){
					CompanyNormDto dto = new CompanyNormDto();
					SphinxMatch info = res.matches[i];
					CompProfile comp = compProfileDao.queryCompProfileById(Integer.valueOf(""+info.docId));
					if (comp==null) {
						continue;
					}
					dto.setCompany(comp);
					resultList.add(dto);
				}
			}
		} catch (SphinxException e) {
			return new PageDto<CompanyNormDto>();
		} catch (UnsupportedEncodingException e) {
			return new PageDto<CompanyNormDto>();
		}
		
		page.setRecords(resultList);
		return page;
	}
}
