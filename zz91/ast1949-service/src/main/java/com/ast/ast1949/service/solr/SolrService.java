package com.ast.ast1949.service.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import com.ast.ast1949.dto.solr.CategorySolrDto;

public interface SolrService {
	@Deprecated
	public List<CategorySolrDto> queryCategory(String keywords,String code) throws SolrServerException;

}
