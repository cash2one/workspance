package com.kl91.service.products.impl;

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
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.dto.products.ProductsSolrDto;
import com.kl91.service.products.ProductsService;
import com.kl91.service.products.ProductsSolrService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SorlUtil;

@Component("productsSolrService")
public class ProductsSolrServiceImpl implements ProductsSolrService {
	private static final String SOLR_DB = "kl91Products";

	public List<ProductsSolrDto> findIndexProducts(ProductsSearchDto searchDto,
			PageDto<ProductsSolrDto> page) {
		SolrServer server = SorlUtil.getInstance().getSolrServer(SOLR_DB);
		SolrQuery query = new SolrQuery();
		do {
			if (searchDto == null) {
				break;
			}
			if (StringUtils.isNotEmpty(searchDto.getKeywords())) {
				query.setQuery(searchDto.getKeywords());
				query.setHighlight(true);
				query.addHighlightField("title");
				query.addHighlightField("detailsQuery");
				query.setHighlightSimplePre("<strong style='color:red;'>");
				query.setHighlightSimplePost("</strong>");
			} else {
				query.setQuery("*:*");
			}
		} while (false);
		// productTypeCode搜索
		do {
			if (StringUtils.isEmpty(searchDto.getProductsTypeCode())) {
				break;
			}
			if (searchDto.getProductsTypeCode().length() == 4) {
				query.addFilterQuery("productTypeCode4:"
						+ searchDto.getProductsTypeCode());
			}
			if (searchDto.getProductsTypeCode().length() == 8) {
				query.addFilterQuery("productTypeCode8:"
						+ searchDto.getProductsTypeCode());
			}
			if (searchDto.getProductsTypeCode().length() == 12) {
				query.addFilterQuery("productTypeCode12:"
						+ searchDto.getProductsTypeCode());
			}
		} while (false);

		// 根据公司ID
		if (searchDto.getCid() != null) {
			query.addFilterQuery("cid:" + searchDto.getCid());
		}

		// 是否删除
		if (searchDto.getDeletedFlag() != null) {
			query.addFilterQuery("deletedFlag:" + searchDto.getDeletedFlag());
		}

		// 是否审核
		if (searchDto.getCheckedFlag() != null) {
			query.addFilterQuery("checkedFlag:" + searchDto.getCheckedFlag());
		}

		// 是否发布
		if (searchDto.getPublishFlag() != null) {
			query.addFilterQuery("publishFlag:" + searchDto.getPublishFlag());
		}
		// 导入状态
		if (searchDto.getImptFlag() != null) {
			query.addFilterQuery("imptFlag:" + searchDto.getImptFlag());
		}

		// 地区搜索
		if (StringUtils.isNotEmpty(searchDto.getAreaCode())) {
			query.addFilterQuery("areaCode:" + searchDto.getAreaCode());
		}

		// 按供求类型搜索
		if (StringUtils.isNotEmpty(searchDto.getTypeCode())) {
			query.addFilterQuery("typeCode:" + searchDto.getTypeCode());
		}

		// 按最小价格存在搜索
		if (searchDto.getMinprice() != null) {
			String solrSql = "price:[" + searchDto.getMinprice() + " TO ";
			if (searchDto.getMaxPrice() != null) {
				solrSql += searchDto.getMaxPrice();
			} else {
				solrSql += "*";
			}
			solrSql += " ] ";
			query.addFilterQuery(solrSql);
		} else {
			if (searchDto.getMaxPrice() != null) {
				query.addFilterQuery("price:[ 0 TO" + searchDto.getMaxPrice()
						+ " ] ");
			}
		}

		query.addSortField("gmtRefresh", ORDER.desc);
		query.setStart(page.getStartIndex());
		query.setRows(page.getPageSize());
		QueryResponse rsp;
		try {
			rsp = server.query(query);
		} catch (SolrServerException e) {
			return null;
		}
		List<ProductsSolrDto> beans = rsp.getBeans(ProductsSolrDto.class);
		page.setTotalRecords((int) rsp.getResults().getNumFound());
		do {
			if (StringUtils.isEmpty(searchDto.getKeywords())) {
				break;
			}
			Map<String, Map<String, List<String>>> highLightMap = rsp
					.getHighlighting();
			for (ProductsSolrDto bean : beans) {
				Map<String, List<String>> beanMap = new HashMap<String, List<String>>();
				String idString = bean.getId().toString();
				if (idString != null) {
					beanMap = highLightMap.get(idString);
				}
				if (beanMap.get("name") != null) {
					bean.setHighLightTitle(beanMap.get("title").get(0));
				}
				if (beanMap.get("detailsQuery") != null) {
					bean.setHighLightDetails(beanMap.get("detailsQuery").get(0));
				}
			}
		} while (false);
		return beans;
	}

	@Override
	public List<ProductsSolrDto> querySolrProductsByCompanyId(Integer companyId,PageDto<ProductsSolrDto> page) {
		ProductsSearchDto searchDto = new ProductsSearchDto();
		searchDto.setCheckedFlag(ProductsService.CHECKED_SUCCESS);
		searchDto.setDeletedFlag(ProductsService.DELETE_FAILTURE);
		searchDto.setPublishFlag(ProductsService.PUBLISH_FLAG);
		searchDto.setCid(companyId);
		return findIndexProducts(searchDto, page);
	}

	@Override
	public List<ProductsSolrDto> querySolrProductsByTypeCode(
			ProductsSearchDto searchDto, PageDto<ProductsSolrDto> page,String typeCode) {
		searchDto.setCheckedFlag(ProductsService.CHECKED_SUCCESS);
		searchDto.setDeletedFlag(ProductsService.DELETE_FAILTURE);
		searchDto.setPublishFlag(ProductsService.PUBLISH_FLAG);
		searchDto.setTypeCode(typeCode);
		return findIndexProducts(searchDto, page);
	}

	@Override
	public List<ProductsSolrDto> queryProductsByTypeCodeAndKeywords(
			ProductsSearchDto searchDto, PageDto<ProductsSolrDto> page,
			String typeCode, String keywords) {
		searchDto.setCheckedFlag(ProductsService.CHECKED_SUCCESS);
		searchDto.setDeletedFlag(ProductsService.DELETE_FAILTURE);
		searchDto.setPublishFlag(ProductsService.PUBLISH_FLAG);
		searchDto.setTypeCode(typeCode);
		searchDto.setKeywords(keywords);
		return findIndexProducts(searchDto, page);
	}

}
