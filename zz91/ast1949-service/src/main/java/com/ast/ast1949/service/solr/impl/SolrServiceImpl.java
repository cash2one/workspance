package com.ast.ast1949.service.solr.impl;

import org.springframework.stereotype.Service;

import com.ast.ast1949.service.solr.SolrService;

@Service("solrService")
public class SolrServiceImpl implements SolrService{

//	@Override
//	public List<CategorySolrDto> queryCategory(String keywords,String code){
//		SolrServer server = SorlUtil.getInstance().getSolrServer("/zzcategory");
//		SolrQuery query = new SolrQuery();
//		if (StringUtils.isNotEmpty(keywords)) {
//			query.setQuery(keywords);
//			query.setHighlight(true);
//			query.addHighlightField("label");
//		} else {
//			query.setQuery("*:*");
//		}
//		query.addSortField("gmtCreated", ORDER.desc);
//		query.setStart(0);
//		QueryResponse rsp;
//		try {
//			rsp = server.query(query);
//		} catch (SolrServerException e) {
//			return null;
//		}
//		List<CategorySolrDto> beans = rsp.getBeans(CategorySolrDto.class);
//		return beans;
//			
//	}

}
