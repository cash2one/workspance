package com.kl91.service.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.company.CompanySearchDto;
import com.kl91.domain.dto.company.CompanySolrDto;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SorlUtil;

@Component("companySolrService")
public class CompanySolrServiceImpl {
	private static final String SOLR_DB = "kl91Companys";

	public List<CompanySolrDto> findIndexProducts(CompanySearchDto searchDto,
			PageDto<CompanySolrDto> page) {
		SolrServer server = SorlUtil.getInstance().getSolrServer(SOLR_DB);
		SolrQuery query = new SolrQuery();
		do {
			if (searchDto == null) {
				break;
			}
			if (StringUtils.isNotEmpty(searchDto.getKeywords())) {
				query.setQuery(searchDto.getKeywords());
				query.setHighlight(true);
				query.addHighlightField("companyName");
				query.addHighlightField("introduction");
				query.setHighlightSimplePre("<strong style='color:red;'>");
				query.setHighlightSimplePost("</strong>");
			} else {
				query.setQuery("*:*");
			}
		} while (false);
		// productTypeCode搜索
		do {
			if (StringUtils.isEmpty(searchDto.getIndustryCode())) {
				break;
			}
			if (searchDto.getIndustryCode().length() == 4) {
				query.addFilterQuery("industryCode4:"
						+ searchDto.getIndustryCode());
			}
			if (searchDto.getIndustryCode().length() == 8) {
				query.addFilterQuery("industryCode8:"
						+ searchDto.getIndustryCode());
			}
			if (searchDto.getIndustryCode().length() == 12) {
				query.addFilterQuery("industryCode12:"
						+ searchDto.getIndustryCode());
			}
		} while (false);

		query.addSortField("id", ORDER.desc);
		query.setStart(page.getStartIndex());
		query.setRows(page.getPageSize());
		QueryResponse rsp;
		try {
			rsp = server.query(query);
		} catch (SolrServerException e) {
			return null;
		}
		List<CompanySolrDto> beans = rsp.getBeans(CompanySolrDto.class);
		page.setTotalRecords((int) rsp.getResults().getNumFound());
		do {
			if (StringUtils.isEmpty(searchDto.getKeywords())) {
				break;
			}
			Map<String, Map<String, List<String>>> highLightMap = rsp
					.getHighlighting();
			for (CompanySolrDto bean : beans) {
				Map<String, List<String>> beanMap = new HashMap<String, List<String>>();
				String idString = bean.getId().toString();
				if (idString != null) {
					beanMap = highLightMap.get(idString);
				}
				if (beanMap.get("companyName") != null) {
					bean.setHighLightTitle(beanMap.get("companyName").get(0));
				}
				if (beanMap.get("introduction") != null) {
					bean.setHighLightIntro(beanMap.get("introduction").get(0));
				}
			}
		} while (false);
		return beans;
	}
}
