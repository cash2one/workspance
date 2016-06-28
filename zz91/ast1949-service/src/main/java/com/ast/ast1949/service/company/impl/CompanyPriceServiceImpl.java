/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.persist.company.CategoryCompanyPriceDAO;
import com.ast.ast1949.persist.company.CompanyPriceDAO;
import com.ast.ast1949.service.company.CategoryCompanyPriceService;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.ParseAreaCode;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * @author yuyonghui
 * 
 */
@Component("companyPriceService")
public class CompanyPriceServiceImpl implements CompanyPriceService {

	@Autowired
	private CompanyPriceDAO companyPriceDAO;
	@Autowired
	private CategoryCompanyPriceDAO categoryCompanyPriceDAO;
	@Resource
	private CompanyService companyService;
	@Resource
	private CategoryCompanyPriceService categoryCompanyPriceService;

	final static String DEFAULT_COMPANY_PRICE_CATEGORY = "10031008";

	public Integer batchDeleteCompanyPriceById(Integer[] entities) {
		Assert.notNull(entities, "entities is not null");
		return companyPriceDAO.batchDeleteCompanyPriceById(entities);
	}

	public Integer insertCompanyPrice(String membershipCode,
			CompanyPriceDO companyPriceDO) {
		// 再次判断单位不能为空
		Assert.notNull(companyPriceDO, "companyPriceDO is not null");

		// if (membershipCode==null ||
		// AstConst.COMMON_MEMBERSHIP_CODE.equals(membershipCode)) {
		// companyPriceDO.setIsChecked(AstConst.IS_CHECKED_FALSE);
		// } else {
		// companyPriceDO.setIsChecked(AstConst.IS_CHECKED_TRUE);
		// }
		// if(companyPriceDO.getIsChecked()==null) {
		companyPriceDO.setIsChecked("0");
		// }
		companyPriceDO.setPrice(companyPriceDO.getMinPrice());
		// companyPriceDO.setProductId(0);
		if (StringUtils.isEmpty(companyPriceDO.getPriceUnit())) {
			return 0;
		}
		return companyPriceDAO.insertCompanyPrice(companyPriceDO);
	}

	public Integer updateCompanyPrice(CompanyPriceDO companyPriceDO) {
		Assert.notNull(companyPriceDO, "companyPriceDO is not null");
		return companyPriceDAO.updateCompanyPrice(companyPriceDO);
	}

	public CompanyPriceDO queryCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		CompanyPriceDO companyPriceDO = companyPriceDAO
				.queryCompanyPriceById(id);
		CategoryFacade cate = CategoryFacade.getInstance();
		// 地区
		if (companyPriceDO != null
				&& StringUtils.isNotEmpty(companyPriceDO.getAreaCode())) {
			String location = "";
			if (companyPriceDO.getAreaCode().length() > 12) {
				location = cate.getValue(companyPriceDO.getAreaCode()
						.substring(0, 12))
						+ ""
						+ cate.getValue(companyPriceDO.getAreaCode().substring(
								0, 16));
			} else if (companyPriceDO.getAreaCode().length() > 8) {
				location = cate.getValue(companyPriceDO.getAreaCode()
						.substring(0, 8))
						+ ""
						+ cate.getValue(companyPriceDO.getAreaCode().substring(
								0, 12));
			} else if (companyPriceDO.getAreaCode().length() == 8) {
				location = cate.getValue(companyPriceDO.getAreaCode()
						.substring(0, 8));
			}
			companyPriceDO.setAreaCode(location);
			companyPriceDO.getAreaCode();
		}
		return companyPriceDO;
	}

	// public List<CompanyPriceDTO> queryCompanyPriceByCondition(CompanyPriceDTO
	// companyPriceDTO) {
	// Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
	// return companyPriceDAO.queryCompanyPriceByCondition(companyPriceDTO);
	// }

	public List<CompanyPriceDTO> queryCompanyPriceForFront(
			CompanyPriceDTO companyPriceDTO) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		return companyPriceDAO.queryCompanyPriceForFront(companyPriceDTO);
	}

	public Integer queryCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		return companyPriceDAO.queryCompanyPriceRecordCount(companyPriceDTO);
	}

	@Override
	public PageDto<CompanyPriceDTO> queryCompanyPricePagiationList(
			CompanyPriceDTO companyPriceDTO, PageDto<CompanyPriceDTO> pager) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		pager = companyPriceDAO.queryCompanyPricePagiationList(companyPriceDTO,
				pager);
		// 查询省市
		for (Object obj : pager.getRecords()) {
			CompanyPriceDTO cPriceDTO = (CompanyPriceDTO) obj;
			ParseAreaCode parser = new ParseAreaCode();
			parser.parseAreaCode(cPriceDTO.getCompanyPriceDO().getAreaCode());
			cPriceDTO.setProvinceName(parser.getProvince());
		}
		return pager;
	}

	public Integer queryCompanyPriceValidityTimeById(Date refreshTime,
			Date expiredTime) throws ParseException {
		if (expiredTime == null || refreshTime == null) {
			return 20;
		}
		if (expiredTime.equals(DateUtil.getDate(AstConst.MAX_TIMT,
				AstConst.DATE_FORMATE_WITH_TIME))) {
			return -1;
		} else {
			return DateUtil.getIntervalDays(expiredTime, refreshTime);
		}
	}

	public List<CompanyPriceDO> queryCompanyPriceByCompanyIdCount(
			Integer limitSize) {
		Assert.notNull(limitSize, "limitSize is not null");
		return companyPriceDAO.queryCompanyPriceByCompanyIdCount(limitSize);
	}

	// public Integer queryMyCompanyPriceRecordCount(CompanyPriceDTO
	// companyPriceDTO) {
	// Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
	// return companyPriceDAO.queryMyCompanyPriceRecordCount(companyPriceDTO);
	// }

	public List<CompanyPriceDO> queryCompanyPriceByCompanyId(
			Map<String, Object> param) {
		Assert.notNull(param, "param is not null");
		return companyPriceDAO.queryCompanyPriceByCompanyId(param);
	}

	public List<CompanyPriceDO> queryCompanyPriceByRefreshTime(String title,
			Integer size) {
		Assert.notNull(size, "size is not null");
		return companyPriceDAO.queryCompanyPriceByRefreshTime(title, size);
	}

	public Integer updateCompanyPriceCheckStatus(int[] entities,
			String isChecked) {
		Assert.notNull(entities, "entities is not null");
		Assert.notNull(isChecked, "isChecked is not null");
		return companyPriceDAO.updateCompanyPriceCheckStatus(entities,
				isChecked);
	}

	public CompanyPriceDTO selectCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		return companyPriceDAO.selectCompanyPriceById(id);
	}

	public CompanyPriceDO queryCompanyPriceByProducts(ProductsDO productsDO,
			String areaCode, Integer productId) {
		// 产品名称 计量单位 产品价格 最小最大价格 地区 产品描述
		Assert.notNull(productsDO, "productsDO is not null");
		CompanyPriceDO companyPriceDO = new CompanyPriceDO();
		companyPriceDO.setProductId(productId);
		companyPriceDO.setAreaCode(areaCode);
		companyPriceDO.setTitle(productsDO.getTitle());
		companyPriceDO.setPrice(productsDO.getPrice());
		companyPriceDO.setPriceUnit(productsDO.getPriceUnit());
		if (productsDO.getMinPrice() != null) {
			if (productsDO.getMinPrice().longValue() > 0) {
				companyPriceDO.setMinPrice(String.valueOf(productsDO
						.getMinPrice()));
			}
		}
		if (productsDO.getMaxPrice() != null) {
			if (productsDO.getMaxPrice().longValue() > 0) {
				companyPriceDO.setMaxPrice(String.valueOf(productsDO
						.getMaxPrice()));
			}
		}
		companyPriceDO.setDetails(productsDO.getDetails());
		// companyPriceDO.setSource("0");
		return companyPriceDO;
	}

	public Integer insertCompanyPriceByAdmin(CompanyPriceDO companyPrice) {
		Assert.notNull(companyPrice, "the object companyprice can not be null");
		Assert.notNull(companyPrice.getProductId(),
				"the companyprice.productId can not be null");
		Assert.notNull(companyPrice.getAreaCode(),
				"the companyprice.areaCode can not be null");

		return companyPriceDAO.insertCompanyPriceByAdmin(companyPrice);
	}

	public Integer addProductsToCompanyPrice(CompanyPriceDO companyPriceDO,
			Company company, ProductsDO productsDO) {

		// if (company.getMembershipCode() != null) {
		// if
		// (company.getMembershipCode()==null||company.getMembershipCode().equals(AstConst.ZST_MEMBERSHIP_CODE))
		// {
		// companyPriceDO.setIsChecked(AstConst.IS_CHECKED_FALSE);
		// } else {
		// companyPriceDO.setIsChecked(AstConst.IS_CHECKED_TRUE);
		// }
		// }

		// String categoryLable = "";
		// if(productsDO.getCategoryProductsMainCode()!=null){
		// categoryLable =
		// CategoryProductsFacade.getInstance().getValue(productsDO.getCategoryProductsMainCode().substring(4));
		// }
		// if(StringUtils.isEmpty(categoryLable)){
		// companyPriceDO.setCategoryCompanyPriceCode("1003");
		// }else{
		// CategoryCompanyPriceDO categoryCompanyPrice = categoryCompanyPriceDAO
		// .queryCategoryCompanyPriceByLabel(categoryLable);
		// if (categoryCompanyPrice != null && categoryCompanyPrice.getCode() !=
		// null
		// && categoryCompanyPrice.getCode().length() > 0) {
		// companyPriceDO.setCategoryCompanyPriceCode(categoryCompanyPrice.getCode());
		// }else{
		// companyPriceDO.setCategoryCompanyPriceCode("1003");
		// }
		// }

		// 设置企业报价发布的类别
		companyPriceDO.setCategoryCompanyPriceCode(getCategoryCode(productsDO
				.getCategoryProductsMainCode()));

		if (companyPriceDO.getIsChecked() == null) {
			companyPriceDO.setIsChecked("0");
		}
		companyPriceDO.setProductId(productsDO.getId());
		companyPriceDO.setAreaCode(company.getAreaCode());
		companyPriceDO.setTitle(productsDO.getTitle());
		companyPriceDO.setPrice(productsDO.getPrice());
		companyPriceDO.setExpiredTime(productsDO.getExpireTime());
		companyPriceDO.setRefreshTime(productsDO.getRefreshTime());
		companyPriceDO.setPriceUnit(productsDO.getPriceUnit() + "/"
				+ productsDO.getQuantityUnit());
		if (productsDO.getMinPrice() != null) {
			if (productsDO.getMinPrice().longValue() > 0) {
				companyPriceDO.setMinPrice(String.valueOf(productsDO
						.getMinPrice()));
			}
		}
		if (productsDO.getMaxPrice() != null) {
			if (productsDO.getMaxPrice().longValue() > 0) {
				companyPriceDO.setMaxPrice(String.valueOf(productsDO
						.getMaxPrice()));
			}
		}
		companyPriceDO.setDetails(productsDO.getDetails());

		return companyPriceDAO.insertCompanyPrice(companyPriceDO);
	}

	@Override
	public CompanyPriceDO queryCompanyPriceByProductId(Integer productId,
			String code) {
		Assert.notNull(productId, "the productId can not be null");
		return companyPriceDAO.queryCompanyPriceByProductId(productId, code);
	}

	@Override
	public PageDto<CompanyPriceDtoForMyrc> queryCompanyPriceListByCompanyId(
			Integer companyId, PageDto<CompanyPriceDtoForMyrc> page) {
		Assert.notNull(companyId, "the companyId can not be null");
		return companyPriceDAO
				.queryCompanyPriceListByCompanyId(companyId, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageDto<CompanyPriceSearchDTO> pageCompanyPriceSearch(
			CompanyPriceSearchDTO dto, PageDto page) {
		List<CompanyPriceSearchDTO> list = companyPriceDAO
				.queryCompanyPriceSearchByFront(dto, page);
		for (CompanyPriceSearchDTO search : list) {
			if (StringUtils.isNotEmpty(search.getAreaCode())) {
				search.setAreaName(CategoryFacade.getInstance().getValue(
						search.getAreaCode()));
				if (search.getAreaCode().length() >= 8) {
					search.setCountryName(CategoryFacade.getInstance()
							.getValue(search.getAreaCode().substring(0, 8)));
				}
				if (search.getAreaCode().length() >= 12) {
					search.setProvince(CategoryFacade.getInstance().getValue(
							search.getAreaCode().substring(0, 12)));
				}
				if (search.getAreaCode().length() >= 16) {
					search.setCity(CategoryFacade.getInstance().getValue(
							search.getAreaCode().substring(0, 16)));
				}
			}
		}
		page.setRecords(list);
		page.setTotalRecords(companyPriceDAO.queryCompanypriceCount(dto));
		return page;
	}

	@Override
	public PageDto<CompanyPriceDTO> pageCompanyPriceByAdmin(String title,
			String isChecked, String categoryCode, String isVip,
			CompanyPriceSearchDTO searchDto, PageDto<CompanyPriceDTO> page) {
		if (StringUtils.isNotEmpty(searchDto.getTimeType())) {
			String from = searchDto.getFrom();
			if (StringUtils.isEmpty(from)) {
				from = DateUtil.toString(new Date(), "yyyy-MM-dd");
			} else {
				try {
					from = DateUtil.toString(
							DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
				} catch (ParseException e) {
					from = DateUtil.toString(new Date(), "yyyy-MM-dd");
				}
			}
			String to = searchDto.getTo();
			if (StringUtils.isEmpty(searchDto.getTo())) {
				to = DateUtil.toString(
						DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
			} else {
				try {
					to = DateUtil.toString(
							DateUtil.getDateAfterDays(
									DateUtil.getDate(to, "yyyy-MM-dd"), 1),
							"yyyy-MM-dd");
				} catch (ParseException e) {
					to = DateUtil.toString(
							DateUtil.getDateAfterDays(new Date(), 1),
							"yyyy-MM-dd");
				}
			}
			searchDto.setFrom(from);
			searchDto.setTo(to);
		}
		List<CompanyPriceDO> list = companyPriceDAO.queryCompanyPriceForAdmin(
				title, isChecked, categoryCode, isVip, searchDto, page);
		List<CompanyPriceDTO> nlist = new ArrayList<CompanyPriceDTO>();
		for (CompanyPriceDO obj : list) {
			CompanyPriceDTO dto = new CompanyPriceDTO();
			dto.setCompanyPriceDO(obj);
			Company company = companyService.queryCompanyById(obj
					.getCompanyId());
			if (company != null) {
				dto.setMembershipCode(company.getMembershipCode());
				dto.setCompanyName(company.getName());
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(companyPriceDAO.queryCompanyPriceCountForAdmin(
				title, isChecked, categoryCode, isVip, searchDto));
		return page;
	}

	@Override
	public Integer updateCategory(Integer id, String categoryCode) {

		return companyPriceDAO.updateCategoryCode(id, categoryCode);
	}

	@Override
	public Integer updateCompanyPriceByAdmin(CompanyPriceDO companyPriceDO) {
		return companyPriceDAO.updateCompanyPriceByAdmin(companyPriceDO);
	}

	@Override
	public List<CompanyPriceDO> queryNewestVipCompPrice(String code,
			Integer size) {
		return companyPriceDAO.queryNewestVipCompPrice(code, size);
	}

	@Override
	public List<CompanyPriceSearchDTO> queryCompanyPriceSearchByFront(
			CompanyPriceSearchDTO dto, PageDto<CompanyPriceSearchDTO> page) {
		List<CompanyPriceSearchDTO> list = companyPriceDAO
				.queryCompanyPriceSearchByFront(dto, page);
		return list;
	}

	@Override
	public List<CompanyPriceSearchDTO> queryCompanyPriceList(Integer size) {
		if (size == null) {
			size = 20;
		}
		List<CompanyPriceSearchDTO> list = companyPriceDAO
				.queryCompanyPriceList(size);
		for (CompanyPriceSearchDTO search : list) {
			String areaCode = companyService.queryAreaCodeOfCompany(search
					.getCompanyId());
			if (StringUtils.isNotEmpty(search.getAreaCode())
					&& search.getAreaCode().length() >= 8) {
				search.setAreaName(CategoryFacade.getInstance().getValue(
						search.getAreaCode()));
				if (search.getAreaCode().length() >= 8) {
					search.setCountryName(CategoryFacade.getInstance()
							.getValue(search.getAreaCode().substring(0, 8)));
				}
				if (search.getAreaCode().length() >= 12) {
					search.setProvince(CategoryFacade.getInstance().getValue(
							search.getAreaCode().substring(0, 12)));
				}
				if (search.getAreaCode().length() >= 16) {
					search.setCity(CategoryFacade.getInstance().getValue(
							search.getAreaCode().substring(0, 16)));
				}
			} else if (StringUtils.isNotEmpty(areaCode)) {
				ParseAreaCode parse = new ParseAreaCode();
				parse.parseAreaCode(areaCode);
				search.setProvinceName(parse.getProvince());
				search.setCity(parse.getCity());
				search.setCountryName(parse.getContry());
			}
		}
		return list;
	}

	@Override
	public PageDto<CompanyPriceDTO> pageCompanyPriceBySearchEngine(
			CompanyPriceDTO companyPriceDTO, PageDto<CompanyPriceDTO> page) {
		do {
			if (page == null) {
				break;
			}
			if (companyPriceDTO == null) {
				break;
			}
			CompanyPriceDO companyPriceDO = companyPriceDTO.getCompanyPriceDO();
			if (companyPriceDO == null) {
				companyPriceDO = new CompanyPriceDO();
			}
			// 产品类别不为空 转为类别名
			if (StringUtils.isNotEmpty(companyPriceDO
					.getCategoryCompanyPriceCode())) {
				companyPriceDTO.setCategoryName(categoryCompanyPriceDAO
						.queryByCode(
								companyPriceDO.getCategoryCompanyPriceCode())
						.getLabel());
			}
			// 地区类别不为空 转为地区名
			if (StringUtils.isNotEmpty(companyPriceDO.getAreaCode())) {
				companyPriceDTO.setAreaName(CategoryFacade.getInstance()
						.getValue(companyPriceDO.getAreaCode()));
			}
			// 产品名不为空
			if (StringUtils.isNotEmpty(companyPriceDTO.getTitle())) {
				companyPriceDO.setTitle(companyPriceDTO.getTitle());
			}

			// 数量
			if (page.getPageSize() == null) {
				page.setPageSize(10);
			}
			List<CompanyPriceDTO> list = new ArrayList<CompanyPriceDTO>();
			StringBuffer sb = new StringBuffer();
			SphinxClient cl = SearchEngineUtils.getInstance().getClient(
					"companyPrice");

			// 类别名搜索
			if (StringUtils.isNotEmpty(companyPriceDTO.getCategoryName())) {
				if (StringUtils.isNotEmpty(companyPriceDO.getTitle())) {
					companyPriceDO.setTitle(companyPriceDO.getTitle()
							+ companyPriceDTO.getCategoryName());
				} else {
					companyPriceDO.setTitle(companyPriceDTO.getCategoryName());
				}
			}
			// 地区名搜索
			if (StringUtils.isNotEmpty(companyPriceDTO.getAreaName())) {
				if (StringUtils.isNotEmpty(companyPriceDO.getTitle())) {
					companyPriceDO.setTitle(companyPriceDO.getTitle()
							+ companyPriceDTO.getAreaName());
				} else {
					companyPriceDO.setTitle(companyPriceDTO.getCategoryName());
				}
			}

			// 关键字标题搜索
			if (StringUtils.isNotEmpty(companyPriceDO.getTitle())) {
				sb.append("@(title,label1,label2,label3,area1,area2,area3) ")
						.append(companyPriceDO.getTitle());
			}

			// 搜索数据id 组装
			try {
				cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
				SphinxResult res = cl.Query(sb.toString(), "company_price");
				if (res == null) {
					// System.err.println ( "Error: " + cl.GetLastError() );
					page.setTotalRecords(0);
				} else {
					page.setTotalRecords(res.totalFound);
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];
						CompanyPriceDTO dto = new CompanyPriceDTO();
						CompanyPriceDO obj = companyPriceDAO
								.queryCompanyPriceById(Integer.valueOf(""
										+ info.docId));
						if (obj != null) {
							dto.setCompanyPriceDO(obj);
							Company company = companyService
									.queryCompanyById(obj.getCompanyId());
							if (company != null
									&& StringUtils
											.isNotEmpty(company.getName())) {
								dto.setCompanyName(company.getName());
							}
							if (StringUtils.isNotEmpty(obj.getAreaCode())) {
								if (obj.getAreaCode().length() >= 16) {
									dto.setProvinceName(CategoryFacade
											.getInstance().getValue(
													obj.getAreaCode()
															.substring(0, 12)));
									dto.setAreaName(CategoryFacade
											.getInstance().getValue(
													obj.getAreaCode()
															.substring(0, 16)));
								}
								if (obj.getAreaCode().length() >= 12
										&& obj.getAreaCode().length() < 16) {
									dto.setProvinceName(CategoryFacade
											.getInstance().getValue(
													obj.getAreaCode()
															.substring(0, 12)));
								}
							}
							// CategoryFacade.getInstance()
						}

						// ProductsDto
						// dto=productsDAO.queryProductsWithPicAndCompany(Integer.valueOf(""+info.docId));
						// if(dto!=null &&
						// dto.getProducts()!=null &&
						// StringUtils.isNotEmpty(dto.getProducts().getDetails())){
						// dto.getProducts().setDetails(Jsoup.clean(dto.getProducts().getDetails(),
						// Whitelist.none()));
						// }else{
						// dto = new ProductsDto();
						// ProductsDO pdto = new ProductsDO();
						// Map<String, Object>
						// resultMap=SearchEngineUtils.getInstance().resolveResult(res,info);
						// pdto.setId(Integer.valueOf(""+info.docId));
						// pdto.setTitle(resultMap.get("ptitle").toString());
						// dto.setProducts(pdto);
						// }
						if (dto.getCompanyPriceDO() != null) {
							list.add(dto);
						}
					}
				}
			} catch (SphinxException e) {
				e.printStackTrace();
			}
			page.setRecords(list);
		} while (false);
		return page;
	}

	@Override
	public PageDto<CompanyPriceDTO> pageCompanyPriceBySearchEngineWithMatchMode(
			CompanyPriceDTO companyPriceDTO, PageDto<CompanyPriceDTO> page) {
		do {
			if (page == null) {
				break;
			}
			if (companyPriceDTO == null) {
				break;
			}
			CompanyPriceDO companyPriceDO = companyPriceDTO.getCompanyPriceDO();
			if (companyPriceDO == null) {
				companyPriceDO = new CompanyPriceDO();
			}
			// 产品类别不为空 转为类别名
			if (StringUtils.isNotEmpty(companyPriceDO
					.getCategoryCompanyPriceCode())) {
				companyPriceDTO.setCategoryName(categoryCompanyPriceDAO
						.queryByCode(
								companyPriceDO.getCategoryCompanyPriceCode())
						.getLabel());
			}
			// 地区类别不为空 转为地区名
			if (StringUtils.isNotEmpty(companyPriceDO.getAreaCode())) {
				companyPriceDTO.setAreaName(CategoryFacade.getInstance()
						.getValue(companyPriceDO.getAreaCode()));
			}
			// 产品名不为空
			if (StringUtils.isNotEmpty(companyPriceDTO.getTitle())) {
				companyPriceDO.setTitle(companyPriceDTO.getTitle());
			}

			// 数量
			if (page.getPageSize() == null) {
				page.setPageSize(10);
			}
			List<CompanyPriceDTO> list = new ArrayList<CompanyPriceDTO>();
			StringBuffer sb = new StringBuffer();
			SphinxClient cl = SearchEngineUtils.getInstance().getClient();

			// 关键字标题搜索
			if (StringUtils.isNotEmpty(companyPriceDO.getTitle())) {
				sb.append("@(title) ").append(companyPriceDO.getTitle());
			}
			// 类别名搜索
			if (StringUtils.isNotEmpty(companyPriceDTO.getCategoryName())) {
				sb.append("@(label1,label2,label3) ").append(
						companyPriceDTO.getCategoryName());
			}
			// 地区名搜索
			if (StringUtils.isNotEmpty(companyPriceDTO.getAreaName())) {
				sb.append("@(area1,area2,area3) ").append(
						companyPriceDTO.getAreaName());
			}

			// 搜索数据id 组装
			try {
				cl.SetMatchMode(SphinxClient.SPH_MATCH_ANY);
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "@weight desc");
				SphinxResult res = cl.Query(sb.toString(), "company_price");
				if (res == null) {
					page.setTotalRecords(0);
				} else {
					page.setTotalRecords(res.totalFound);
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];
						CompanyPriceDTO dto = new CompanyPriceDTO();
						CompanyPriceDO obj = companyPriceDAO
								.queryCompanyPriceById(Integer.valueOf(""
										+ info.docId));
						if (obj != null) {
							dto.setCompanyPriceDO(obj);
							Company company = companyService
									.queryCompanyById(obj.getCompanyId());
							if (company != null
									&& StringUtils
											.isNotEmpty(company.getName())) {
								dto.setCompanyName(company.getName());
							}
							if (StringUtils.isNotEmpty(obj.getAreaCode())) {
								if (obj.getAreaCode().length() >= 16) {
									dto.setProvinceName(CategoryFacade
											.getInstance().getValue(
													obj.getAreaCode()
															.substring(0, 12)));
									dto.setAreaName(CategoryFacade
											.getInstance().getValue(
													obj.getAreaCode()
															.substring(0, 16)));
								}
								if (obj.getAreaCode().length() >= 12
										&& obj.getAreaCode().length() < 16) {
									dto.setProvinceName(CategoryFacade
											.getInstance().getValue(
													obj.getAreaCode()
															.substring(0, 12)));
								}
							}
							// CategoryFacade.getInstance()
						}

						// ProductsDto
						// dto=productsDAO.queryProductsWithPicAndCompany(Integer.valueOf(""+info.docId));
						// if(dto!=null &&
						// dto.getProducts()!=null &&
						// StringUtils.isNotEmpty(dto.getProducts().getDetails())){
						// dto.getProducts().setDetails(Jsoup.clean(dto.getProducts().getDetails(),
						// Whitelist.none()));
						// }else{
						// dto = new ProductsDto();
						// ProductsDO pdto = new ProductsDO();
						// Map<String, Object>
						// resultMap=SearchEngineUtils.getInstance().resolveResult(res,info);
						// pdto.setId(Integer.valueOf(""+info.docId));
						// pdto.setTitle(resultMap.get("ptitle").toString());
						// dto.setProducts(pdto);
						// }
						list.add(dto);
					}
				}
			} catch (SphinxException e) {
				e.printStackTrace();
			}
			page.setRecords(list);
		} while (false);
		return page;
	}

	@Override
	public Integer refreshCompanyPriceByProductId(Integer productId) {
		// 不可为零，不然其他生意管家所有信息全部受到影响
		if (productId == null || productId == 0) {
			return 0;
		}
		return companyPriceDAO.refreshCompanyPriceByProductId(productId);
	}

	@Override
	public Integer updateCompanyPriceCheckStatusByProductId(Integer productId,
			String isChecked) {
		// 不可为零，不然其他生意管家所有信息全部受到影响
		if (productId == null || productId == 0) {
			return 0;
		}
		return companyPriceDAO.updateCompanyPriceCheckStatusByProductId(
				productId, isChecked);
	}

	@Override
	public String getCategoryCode(String mainCode) {

		String categoryLable = "";
		int strLen = mainCode.length();
		// 是否设置为默认值，
		boolean isDefault = false;
		if (strLen == 4) {
			categoryLable = categoryCode.get(mainCode);
			if (StringUtils.isEmpty(categoryLable)) {
				isDefault = true;
			}
		} else if (strLen > 4) {
			categoryLable = categoryCode.get(mainCode);
			if (StringUtils.isEmpty(categoryLable)) {
				String str = mainCode.substring(0, strLen - 4);
				do {
					categoryLable = categoryCode.get(str);
					str = str.substring(0, str.length() - 4);
					if (StringUtils.isNotEmpty(categoryLable)
							|| str.length() == 0) {
						break;
					}
				} while (true);
			}
		}

		// 找不到类别
		if (isDefault || StringUtils.isEmpty(categoryLable)) {
			// 废金属
			if (mainCode.startsWith("1000")) {
				categoryLable = "10011008";
			}
			// 废塑料
			if (mainCode.startsWith("10011004")) {
				// 塑料颗粒
				categoryLable = "100010011009";
			} else if (mainCode.startsWith("1001")) {
				// 废塑料
				categoryLable = "100010001010";
			}
			// 废纸
			if (mainCode.startsWith("1004")) {
				categoryLable = "10021008";
			}

			// 不属于 金属、塑料、纸 的其他类别用
			if (StringUtils.isEmpty(categoryLable)) {
				categoryLable = DEFAULT_COMPANY_PRICE_CATEGORY;
			}
		}

		return categoryLable;
	}

	public PageDto<CompanyPriceSearchDTO> pageCompanyPriceDtoBySearchEngine(
			CompanyPriceSearchDTO companyPriceSearchDTO,
			PageDto<CompanyPriceSearchDTO> page) {
		do {
			if (page == null) {
				break;
			}
			if (companyPriceSearchDTO == null) {
				break;
			}
			CompanyPriceDTO companyPriceDto = new CompanyPriceDTO();
			String flag="";
			// 产品类别不为空 转为类别名
			if (StringUtils.isNotEmpty(companyPriceSearchDTO
					.getCategoryCompanyPriceCode())) {
				if(companyPriceSearchDTO.getCategoryCompanyPriceCode().equals("100410001006")){
					flag="通用塑料";
				}else if(companyPriceSearchDTO.getCategoryCompanyPriceCode().equals("100410011007")){
					flag="工程塑料";
				}else if(companyPriceSearchDTO.getCategoryCompanyPriceCode().equals("100410021004")){
					flag="特种塑料";
				}else if(companyPriceSearchDTO.getCategoryCompanyPriceCode().equals("100410031004")){
					flag="塑料合金";
				}
				String caName = "";
				/*
				 * String caName = categoryCompanyPriceDAO.queryByCode(
				 * companyPriceSearchDTO.getCategoryCompanyPriceCode())
				 * .getLabel();
				 */
				if (StringUtils.isNotEmpty(companyPriceSearchDTO
						.getCategoryCompanyPriceCode())) {
					Map<String, String> map = categoryCompanyPriceService
							.queryAllCompanyPrice();
					caName = map.get(companyPriceSearchDTO
							.getCategoryCompanyPriceCode());
				}
				if (companyPriceSearchDTO.getCategoryCompanyPriceCode().equals(
						"100010001010")) {
					caName = categoryCompanyPriceDAO.queryByCode("1000")
							.getLabel();
				}
				if (companyPriceSearchDTO.getCategoryCompanyPriceCode().equals(
						"10011008")) {
					caName = categoryCompanyPriceDAO.queryByCode("1001")
							.getLabel();
				}
				if (companyPriceSearchDTO.getCategoryCompanyPriceCode().equals(
						"10021008")) {
					caName = categoryCompanyPriceDAO.queryByCode("1002")
							.getLabel();
				}
				if (companyPriceSearchDTO.getCategoryCompanyPriceCode().equals(
						"10031008")) {
					caName = categoryCompanyPriceDAO.queryByCode("1003")
							.getLabel();
				}
				if(StringUtils.isNotEmpty(caName)&&caName.contains("/")){
					caName=caName.replace("/", "\\/");
				}
				companyPriceDto.setCategoryName(caName);
			}
			// 地区类别不为空 转为地区名
			if (StringUtils.isNotEmpty(companyPriceSearchDTO.getAreaCode())) {
				companyPriceDto.setAreaName(CategoryFacade.getInstance()
						.getValue(companyPriceSearchDTO.getAreaCode()));
			}
			// 产品名不为空
			if (StringUtils.isNotEmpty(companyPriceSearchDTO.getKeywords())) {
				companyPriceDto.setTitle(companyPriceSearchDTO.getKeywords());
			}

			// 数量
			if (page.getPageSize() == null) {
				page.setPageSize(10);
			}
			List<CompanyPriceSearchDTO> list = new ArrayList<CompanyPriceSearchDTO>();
			StringBuffer sb = new StringBuffer();
			SphinxClient cl = SearchEngineUtils.getInstance().getClient(
					"companyPrice");

			// 关键字标题搜索
			if (StringUtils.isNotEmpty(companyPriceDto.getTitle())) {
				sb.append("@(title,label1,label2,label3,area1,area2,area3) ").append(companyPriceDto.getTitle());
			}
			// 类别名搜索
			if(StringUtils.isEmpty(flag)){
			if (StringUtils.isNotEmpty(companyPriceDto.getCategoryName())) {
				sb.append("@(label1,label2,label3) ").append(
						companyPriceDto.getCategoryName());
			}
			}else{
				if(flag.equals("通用塑料")){
				sb.append(" @(label2) ").append(flag).append(" @(label3) ").append("!(ABS)!(GPPS)!(HDPE)!(LDPE)!(PP)!(PVC)");
				}else if(flag.equals("工程塑料")){
				sb.append(" @(label2) ").append(flag).append(" @(label3) ").append("!(EVA)!(PBT)!(PC)!(PMMA)!(POM)!(PPO)!(TPU)");
				}else if(flag.equals("特种塑料")){
				sb.append(" @(label2) ").append(flag).append(" @(label3) ").append("!(LCP)!(PEEK)!(PES)!(PTFE)");
				}else if(flag.equals("塑料合金")){
				sb.append(" @(label2) ").append(flag).append(" @(label3) ").append("!(PA\\/ABS)!(PC\\/ABS)!(PC\\/PBT)!(PC\\/PET)");
				}
			}
			// 类别名搜索
			sb.append(" @(label1) ").append("塑料原料");
			// 地区名搜索
			if (StringUtils.isNotEmpty(companyPriceDto.getAreaName())) {
				sb.append("@(area1,area2,area3) ").append(
						companyPriceDto.getAreaName());
			}
			// 搜索数据id 组装
			try {
				// 这是首页有时间搜索
				if (companyPriceSearchDTO.getSearchDate() != null) {
					// SimpleDateFormat sdf = new
					// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// 开始时间减8小时
					Date date = new Date(companyPriceSearchDTO.getSearchDate());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.HOUR_OF_DAY, -8);// 24小时制

					Date startDate = cal.getTime();
					// 变为时间搓 //开始搜索时间
					Long stattTime = Long.valueOf(String.valueOf(
							startDate.getTime()).substring(0, 10));
					// 结束时间
					// Date endDate= DateUtil.getDateAfterDays(cal.getTime(),
					// +1);
					// Long endTime=
					// Long.valueOf(String.valueOf(endDate.getTime()).substring(0,
					// 10));
					cl.SetFilter("refreshTime", stattTime, false);
					cal = null;
				}
				// 这是列表页的时间搜索
				if (companyPriceSearchDTO.getRefreshTime() != null) {
					// 开始时间
					Date date = new Date();
					Long endTime = Long.valueOf(String.valueOf(date.getTime())
							.substring(0, 10));
					// 结束时间
					Long startTime = Long.valueOf(String.valueOf(
							companyPriceSearchDTO.getRefreshTime().getTime())
							.substring(0, 10));
					cl.SetFilterRange("refreshTime", startTime, endTime, false);
				}
				// 根据价格搜索
				if (companyPriceSearchDTO.getFromPrice() != null
						&& companyPriceSearchDTO.getToPrice() != null) {
					cl.SetFilterRange("minPrice",
							companyPriceSearchDTO.getFromPrice(),
							companyPriceSearchDTO.getToPrice(), false);
				} else if (companyPriceSearchDTO.getFromPrice() != null
						&& companyPriceSearchDTO.getToPrice() == null) {
					cl.SetFilterRange("minPrice",
							companyPriceSearchDTO.getFromPrice(), 9999999,
							false);
				}

				cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
						"refresh_time desc");
				SphinxResult res = cl.Query(sb.toString(), "company_price");
				if (res == null) {
					page.setTotalRecords(0);
				} else {
					page.setTotalRecords(res.totalFound);
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];

						CompanyPriceSearchDTO dto = new CompanyPriceSearchDTO();
						CompanyPriceDO obj = companyPriceDAO
								.queryCompanyPriceById(Integer.valueOf(""
										+ info.docId));
						if (obj == null) {
							continue;
						}
						dto.setId(obj.getId());
						dto.setCategoryCompanyPriceCode(obj
								.getCategoryCompanyPriceCode());
						dto.setTitle(obj.getTitle());
						dto.setPostTime(obj.getPostTime());
						// 根据公司id查询公司名字
						String cpAreaCode = companyService
								.queryAreaCodeOfCompany(obj.getCompanyId());
						String cpName = companyService.queryCompanyNameById(obj
								.getCompanyId());

						dto.setCompanyName(cpName);
						dto.setCompanyId(obj.getCompanyId());
						dto.setPrice(obj.getPrice());
						dto.setMaxPrice(obj.getMaxPrice());
						dto.setMinPrice(obj.getMinPrice());
						// 针对一两条数据过滤时间
						if (companyPriceSearchDTO.getSearchDate() != null) {
							Date date = new Date(
									companyPriceSearchDTO.getSearchDate());
							Calendar cal = Calendar.getInstance();
							cal.setTime(date);
							cal.add(Calendar.HOUR_OF_DAY, -8);// 24小时制
							Date startDate = cal.getTime();
							// 变为时间搓
							Long stattTime = Long.valueOf(String.valueOf(
									startDate.getTime()).substring(0, 10));
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								if (StringUtils.isEmpty(obj.getRefreshTime()
										.toString())) {
									continue;
								}
								Date redate = sdf.parse(sdf.format(obj
										.getRefreshTime()));
								// 变为时间搓
								Long reTime = Long.valueOf(String.valueOf(
										redate.getTime()).substring(0, 10));
								if (!reTime.equals(stattTime)) {
									continue;
								}

							} catch (ParseException e) {
								e.printStackTrace();
							}
							cal = null;
						}

						// 返回的结果
						Map<String, Object> resultMap = SearchEngineUtils
								.getInstance().resolveResult(res, info);
						String reTime = resultMap.get("refresh_time")
								.toString() + "000";
						Date reDare = new Date(Long.valueOf(reTime));
						dto.setRefreshTime(reDare);

						dto.setPriceUnit(obj.getPriceUnit());
						if (StringUtils.isNotEmpty(obj.getAreaCode())
								&& obj.getAreaCode().length() >= 8) {
							dto.setAreaName(CategoryFacade.getInstance()
									.getValue(obj.getAreaCode()));
							if (obj.getAreaCode().length() >= 8) {
								dto.setCountryName(CategoryFacade.getInstance()
										.getValue(
												obj.getAreaCode().substring(0,
														8)));
							}
							if (obj.getAreaCode().length() >= 12) {
								dto.setProvince(CategoryFacade.getInstance()
										.getValue(
												obj.getAreaCode().substring(0,
														12)));
							}
							if (obj.getAreaCode().length() >= 16) {
								dto.setCity(CategoryFacade.getInstance()
										.getValue(
												obj.getAreaCode().substring(0,
														16)));
							}
						} else if (StringUtils.isNotEmpty(cpAreaCode)) {
							dto.setAreaName(CategoryFacade.getInstance()
									.getValue(cpAreaCode));
							if (cpAreaCode.length() >= 8) {
								dto.setCountryName(CategoryFacade.getInstance()
										.getValue(cpAreaCode.substring(0, 8)));
							}
							if (cpAreaCode.length() >= 12) {
								dto.setProvince(CategoryFacade.getInstance()
										.getValue(cpAreaCode.substring(0, 12)));
							}
							if (cpAreaCode.length() >= 16) {
								dto.setCity(CategoryFacade.getInstance()
										.getValue(cpAreaCode.substring(0, 16)));
							}
						}

						list.add(dto);
					}
				}
			} catch (SphinxException e) {
				e.printStackTrace();
			}
			page.setRecords(list);
		} while (false);

		return page;
	}

	// trade ,companyPrice 的Code对应情况map集合
	final static Map<String, String> categoryCode = new HashMap<String, String>();
	static {
		categoryCode.put("1001", "1000");
		categoryCode.put("1000", "1001");
		categoryCode.put("1004", "1002");
		categoryCode.put("100110001004", "100010001000");// ABS
		categoryCode.put("100110001005", "100010001001");// EVA
		categoryCode.put("100110011000", "100010001002");// PA
		categoryCode.put("100110011004", "100010001003");// PC
		categoryCode.put("100110001001", "100010001004");// PE
		categoryCode.put("100110011001", "100010001005");// PET
		categoryCode.put("100110001000", "100010001006");// PP
		categoryCode.put("100110001003", "100010001007");// PS
		categoryCode.put("100110001002", "100010001008");// PVC
		categoryCode.put("10011005", "100010001009");// 塑料助剂

		categoryCode.put("100110041003", "100010011000");// ABS再生颗粒
		categoryCode.put("100110041005", "100010011001");// EVA颗粒
		categoryCode.put("100110041006", "100010011002");// PA颗粒
		categoryCode.put("100110041009", "100010011003");// PC颗粒
		categoryCode.put("100110041001", "100010011004");// PE颗粒
		categoryCode.put("100110041007", "100010011005");// PET颗粒
		categoryCode.put("100110041000", "100010011006");// PP颗粒
		categoryCode.put("100110041004", "100010011007");// PS颗粒
		categoryCode.put("100110041002", "100010011008");// PVC颗粒
		categoryCode.put("100110041010", "100010011009");// 其它颗粒

		categoryCode.put("100010001000", "10011001");// 铁
		categoryCode.put("100010011000", "10011002");// 铜
		categoryCode.put("100010011001", "10011003");// 铝
		categoryCode.put("100010011005", "10011004");// 铅
		categoryCode.put("100010011008", "10011005");// 锌
		categoryCode.put("100010011002", "10011006");// 锡
		categoryCode.put("100010011007", "10011007");// 镍

		categoryCode.put("100410041002", "10021000");// 牛皮纸
		categoryCode.put("100410041003", "10021001");// 瓦楞纸
		categoryCode.put("100410041005", "10021002");// 淋膜纸
		categoryCode.put("100410041006", "10021003");// 商标纸
		categoryCode.put("100410041008", "10021004");// 利乐包
		categoryCode.put("100410051001", "10021005");// 书刊
		categoryCode.put("100410051003", "10021006");// 新闻纸
		categoryCode.put("100410051000", "10021007");// 废报纸
		categoryCode.put("10041003", "10021008");// 其它
		categoryCode.put("100210031002", "10031000");// 橡胶颗粒
		categoryCode.put("1011", "10031001");// 轮胎
		categoryCode.put("100310021005", "10031002");// 旧衣服
		categoryCode.put("10031007", "10031003");// 牛仔裤
		categoryCode.put("10061001", "10031004");// 破碎玻
		categoryCode.put("10061007", "10031005");// 玻璃瓶
		categoryCode.put("100510001008", "10031006");// 电池
		categoryCode.put("100510001004", "10031007");// 线路板

	}

	@Override
	public List<CompanyPriceDO> queryCompanyPriceByCondition(
			String categoryCompanyPriceCode, Integer companyId, Integer size) {
		List<CompanyPriceDO> list = companyPriceDAO
				.queryCompanyPriceByCondition(categoryCompanyPriceCode,
						companyId, size);
		for (CompanyPriceDO cp : list) {
			cp.setPrice(cp.getPrice().replace(cp.getPriceUnit(), ""));
		}
		return list;
	}

	@Override
	public String getYuanliaoCode(String code) {
		String codeLabel = yuanliaoCategory.get(code);
		if (StringUtils.isEmpty(codeLabel)) {
			codeLabel = yuanliaoCategory.get(code.substring(0,
					code.length() - 4));
		}
		if (StringUtils.isEmpty(codeLabel)) {
			codeLabel = "1004";
		}
		return codeLabel;
	}

	// yuanliao ,companyPrice 的Code对应情况map集合
	final static Map<String, String> yuanliaoCategory = new HashMap<String, String>();
	static {
		yuanliaoCategory.put("20091000", "1004"); // 塑料原料
		yuanliaoCategory.put("200910001000", "10041000"); // 通用塑料
		yuanliaoCategory.put("200910001001", "10041001"); // 工程塑料
		yuanliaoCategory.put("200910001002", "10041002"); // 特种塑料
		yuanliaoCategory.put("200910001003", "10041003"); // 塑料合金

		yuanliaoCategory.put("2009100010001000", "100410001000"); // ABS
		yuanliaoCategory.put("2009100010001001", "100410001001"); // GPPS
		yuanliaoCategory.put("2009100010001002", "100410001002"); // HDPE
		yuanliaoCategory.put("2009100010001004", "100410001003"); // LDPE
		yuanliaoCategory.put("2009100010001006", "100410001004"); // PP
		yuanliaoCategory.put("2009100010001007", "100410001005"); // PVC
		yuanliaoCategory.put("2009100010001008", "100410001006"); // 其它—通用塑料

		yuanliaoCategory.put("2009100010011012", "100410011000"); // EVA
		yuanliaoCategory.put("2009100010011025", "100410011001"); // PBT
		yuanliaoCategory.put("2009100010011026", "100410011002"); // PC
		yuanliaoCategory.put("2009100010011029", "100410011003"); // PMMA
		yuanliaoCategory.put("2009100010011030", "100410011004"); // POM
		yuanliaoCategory.put("2009100010011032", "100410011005"); // PPO
		yuanliaoCategory.put("2009100010011033", "100410011006"); // TPU
		yuanliaoCategory.put("2009100010011035", "100410011007"); // 其它—工程塑料

		yuanliaoCategory.put("2009100010021036", "100410021000"); // LCP
		yuanliaoCategory.put("2009100010021038", "100410021001"); // PEEK
		yuanliaoCategory.put("2009100010021040", "100410021002"); // PES
		yuanliaoCategory.put("2009100010021045", "100410021003"); // PTFE
		yuanliaoCategory.put("2009100010021047", "100410021004"); // 其它—特种塑料

		yuanliaoCategory.put("2009100010031048", "100410031000"); // PA/ABS
		yuanliaoCategory.put("2009100010031051", "100410031001"); // PC/ABS
		yuanliaoCategory.put("2009100010031052", "100410031002"); // PC/PBT
		yuanliaoCategory.put("2009100010031053", "100410031003"); // PC/PET
		yuanliaoCategory.put("2009100010031056", "100410031004"); // 其它—塑料合金
	}

	@Override
	public List<CompanyPriceDO> queryByCode(String code,Integer size) {
		if (StringUtils.isEmpty(String.valueOf(size))) {
			return null;
		}
		return companyPriceDAO.queryByCode(code,size);
	}

}
