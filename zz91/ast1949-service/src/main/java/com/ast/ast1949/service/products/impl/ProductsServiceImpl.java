/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-1 下午05:00:23
 */
package com.ast.ast1949.service.products.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.products.ProductAddPropertiesDAO;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.persist.products.ProductsSpotDao;
import com.ast.ast1949.persist.spot.SpotInfoDao;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.analzyer.IKAnalzyerUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
import com.zz91.util.tags.TagsUtils;

/**
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Component("productsService")
public class ProductsServiceImpl implements ProductsService {

    final static Integer CARDINAL_NUMBER = 300;
    final static Integer TOTAL_RECORDS = 100000;

    @Resource
    private ProductsDAO productsDAO;
    @Resource
    private ProductsPicDAO productsPicDAO;
    @Resource
    private CompanyDAO companyDAO;
    @Resource
    private ProductsSpotDao productsSpotDao;
    @Resource
    private CompanyAccountDao companyAccountDao;
    @Resource
    private SpotInfoDao spotInfoDao;
    @Resource
    private ProductAddPropertiesDAO productAddPropertiesDAO;

    @Override
    public ProductsDto queryProductsDetailsById(Integer id) {
        Assert.notNull(id, "the id can not be null");

        List<ProductAddProperties> pap = productAddPropertiesDAO.queryByPid(id);
        ProductsDO products = productsDAO.queryProductsById(id);
        if (products == null) {
            return null;
        }
        String grade = "";
        for (int i = 0; i < pap.size(); i++) {
            if (pap.get(i).getProperty().equals("品位")) {
                if (pap.get(i).getContent() != null) {
                    grade = pap.get(i).getContent().trim();
                }
            }
        }

        Date start = products.getRefreshTime();
        Date end = products.getExpireTime();
        long day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        if (day > 90) {
            day = -1;
        }
        ProductsSpot productsSpot = productsSpotDao.queryByProductId(id);
        SpotInfo spotInfo = new SpotInfo();
        ProductsDto dto = new ProductsDto();
        dto.setProducts(products);
        dto.setPostlimittime(day);
        dto.setGrade(grade);
        if (productsSpot != null) {
            spotInfo = spotInfoDao.queryOne(productsSpot.getId());
            if(spotInfo!=null){
	            if (spotInfo.getLevel() != null){
	                spotInfo.setLevel(spotInfo.getLevel().trim());
	            }
	            if (spotInfo.getShape() != null) {
	                spotInfo.setShape(spotInfo.getShape().trim());
	            }
	            dto.setSpotInfo(spotInfo);
            }
        } else {
            dto.setSpotInfo(spotInfo);
        }
        dto.setProductsTypeLabel(CategoryFacade.getInstance().getValue(
                products.getProductsTypeCode()));
        dto.setGoodsTypeLabel(CategoryFacade.getInstance().getValue(
                products.getGoodsTypeCode()));
        dto.setManufactureLabel(CategoryFacade.getInstance().getValue(
                products.getManufacture()));
        dto.setCategoryProductsMainLabel(CategoryProductsFacade.getInstance()
                .getValue(products.getCategoryProductsMainCode()));
        dto.setCategoryProductsAssistLabel(CategoryProductsFacade.getInstance()
                .getAssistValue(products.getCategoryProductsAssistCode()));
        return dto;
    }

    @Override
    public ProductsDO queryProductsById(Integer id) {
        Assert.notNull(id, "the id can not be null");
        return productsDAO.queryProductsById(id);
    }

    @Override
    public ProductsDO queryProductsWithOutDetailsById(Integer id) {
        Assert.notNull(id, "the id can not be null");
        return productsDAO.queryProductsWithOutDetailsById(id);
    }

    @Override
    public List<ProductsDto> queryProductsOfCompany(Integer companyId,
            Integer maxSize) {
        if (maxSize == null) {
            maxSize = 10;
        }
        List<ProductsDto> list = productsDAO.queryProductsWithPicByCompany(
                companyId, maxSize);
        List<ProductsDto> nlist = new ArrayList<ProductsDto>();
        for (ProductsDto dto : list) {
            dto.getProducts().setDetails(
                    Jsoup.clean(
                            productsDAO.queryProductsById(
                                    dto.getProducts().getId()).getDetails(),
                            Whitelist.none()));
            nlist.add(dto);
        }
        return list;
    }

    @Override
    public PageDto<ProductsDto> pageProductsWithPicByCompanyEsite(
            Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
        page.setTotalRecords(productsDAO
                .queryProductsWithPicByCompanyEsiteCount(companyId, kw, sid));
        page.setRecords(productsDAO.queryProductsWithPicByCompanyEsite(
                companyId, kw, sid, page));
        return page;
    }

    @Override
    public List<ProductsDto> queryProductsByMainCode(String mainCode,
            String typeCode, Integer maxSize) {

        List<ProductsDto> productsList = productsDAO.queryProductsByMainCode(
                mainCode, typeCode, maxSize);

        CategoryFacade categoryFacade = CategoryFacade.getInstance();
        for (ProductsDto dto : productsList) {
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
            dto.setCategoryProductsMainLabel(CategoryProductsFacade
                    .getInstance().getValue(
                            dto.getProducts().getCategoryProductsMainCode()));
            dto.setCategoryProductsAssistLabel(CategoryProductsFacade
                    .getInstance().getValue(
                            dto.getProducts().getCategoryProductsAssistCode()));
        }

        return productsList;
    }

    @Override
    public PageDto<ProductsDto> pageProductsWithCompanyForInquiry(
            Company company, ProductsDO products, PageDto<ProductsDto> page) {
        if (page.getSort() == null) {
            page.setSort("p.refresh_time");
        }
        if (page.getDir() == null) {
            page.setDir("desc");
        }
        page.setRecords(productsDAO.queryProductsWithCompanyForInquiry(company,
                products, page));
        page.setTotalRecords(productsDAO
                .queryProductsWithCompanyForInquiryCount(company, products));
        return page;
    }

    @Override
    public List<ProductsDto> queryNewProductsOnWeek(Integer maxSize) {
        Date now = new Date();
        Date start = DateUtil.getDateAfterDays(now, 0);
        Date end = DateUtil.getDateAfterDays(now, -7);
        List<ProductsDto> list = productsDAO.queryNewProductsIntervalDay(start,
                end, maxSize);
        if (list == null) {
            return null;
        }
        CategoryFacade categoryFacade = CategoryFacade.getInstance();
        for (ProductsDto dto : list) {
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
        }
        return list;
    }

    @Override
    public PageDto<ProductsDto> pageProductsByAdmin(Company company,
            ProductsDO products, String statusArray, PageDto<ProductsDto> page,
            String from, String to, String selectTime) throws ParseException {
        if (to != null) {
            try {
                to = DateUtil.toString(
                        DateUtil.getDateAfterDays(
                                DateUtil.getDate(to, "yyyy-MM-dd"), 1),
                        "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isEmpty(statusArray)) {
            statusArray = ",";
        }

        List<ProductsDto> list = productsDAO.queryProductsByAdmin(company,
                products, StringUtils.StringToIntegerArray(statusArray), page,
                from, to, selectTime);

        if (list == null) {
            return null;
        }

        CategoryFacade categoryFacade = CategoryFacade.getInstance();
        for (ProductsDto dto : list) {
            String status = "";
            do {
                if (dto == null) {
                    break;
                }
                if (dto.getProducts() == null) {
                    break;
                }
                // 公司是否属于黑名单
                if ("1".equals(dto.getCompany().getIsBlock())) {
                    dto.getCompany().setName(
                            dto.getCompany().getName() + "(拉黑)");
                }
                // 信息已删除 或者 账户被禁用
                if (dto.getProducts().getIsDel() == true) {
                    status = "(已删除)";
                    break;
                }
                // 暂不发布
                if (dto.getProducts().getIsPause() == true) {
                    status = "(暂不发布)";
                    break;
                }
                // 是否过期
                long expired = dto.getProducts().getExpireTime().getTime();
                long today = new Date().getTime();
                long result = today - expired;
                if (result > 0) {
                    status = "(已过期)";
                    break;
                }
            } while (false);
            dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
        }
        page.setRecords(list);
        page.setTotalRecords(productsDAO.queryProductsByAdminCount(company,
                products, StringUtils.StringToIntegerArray(statusArray), from,
                to, selectTime));

        return page;
    }

    @Override
    public PageDto<ProductsDto> pageProductsByAdminZstExpried(
            Integer isRecheck, PageDto<ProductsDto> page, ProductsDO products) {
        List<ProductsDto> list = productsDAO.queryProductsByAdminZstExpried(
                isRecheck, page, products);
        if (list == null) {
            return null;
        }

        CategoryFacade categoryFacade = CategoryFacade.getInstance();
        for (ProductsDto dto : list) {
            String status = "";
            do {
                // 公司是否属于黑名单
                if ("1".equals(dto.getCompany().getIsBlock())) {
                    dto.getCompany().setName(
                            dto.getCompany().getName() + "(黑名单)");
                }
                // 信息已删除 或者 账户被禁用
                if (dto.getProducts().getIsDel() == true
                        || "1".equals(dto.getCompany().getIsBlock())) {
                    status = "(已删除)";
                    break;
                }
                // 暂不发布
                if (dto.getProducts().getIsPause() == true) {
                    status = "(暂不发布)";
                    break;
                }
                // 是否过期
                long expired = dto.getProducts().getExpireTime().getTime();
                long today = new Date().getTime();
                long result = today - expired;
                if (result > 0) {
                    status = "(已过期)";
                    break;
                }
            } while (false);
            dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
        }
        page.setRecords(list);
        page.setTotalRecords(productsDAO.queryProductsByAdminZstExpriedCount(
                isRecheck, products));
        return page;
    }

    public PageDto<ProductsDO> pageProductsOfCompany(Integer companyId,
            PageDto<ProductsDO> page) {
        page.setRecords(productsDAO.queryProductsOfCompany(companyId, null,
                page));
        page.setTotalRecords(productsDAO.queryProductsOfCompanyCount(companyId,
                null));
        return page;
    }

    public PageDto<ProductsDO> pageProductsOfCompanyByStatus(Integer companyId,
            String account, String checkStatus, String isExpired,
            String isPause, String isPostFromInquiry, Integer groupId,
            String title, PageDto<ProductsDO> page) {
        // 排序
        if (page.getDir() == null) {
            page.setDir("desc");
        }
        if (page.getSort() == null) {
            page.setSort("real_time");
        }
        if (isPostFromInquiry == null) {
            isPostFromInquiry = "0";
        }
        List<ProductsDO> list = productsDAO.queryProductsOfCompanyByStatus(
                companyId, account, checkStatus, isExpired, isPause,
                isPostFromInquiry, groupId, title, page);
        for (ProductsDO obj : list) {
            Integer i = productsPicDAO.countPicIsNoPass(obj.getId());
            if (i > 0) {
                obj.setIsPicPass(0);
            }
        }
        page.setRecords(list);
        page.setTotalRecords(productsDAO.queryProductsOfCompanyByStatusCount(
                companyId, account, checkStatus, isExpired, isPause,
                isPostFromInquiry, groupId, title));
        return page;
    }

    public Map<String, Integer> countProductsOfCompanyByStatus(
            Integer companyId, String account, String isPostFromInquiry,
            Integer initCount) {
        if (isPostFromInquiry == null) {
            isPostFromInquiry = "0";
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("checkStatus0", productsDAO
                .queryProductsOfCompanyByStatusCount(companyId, account, "0",
                        "0", "0", isPostFromInquiry, null, ""));
        map.put("checkStatus1", productsDAO
                .queryProductsOfCompanyByStatusCount(companyId, account, "1",
                        "0", "0", isPostFromInquiry, null, ""));
        map.put("checkStatus2", productsDAO
                .queryProductsOfCompanyByStatusCount(companyId, account, "2",
                        "0", "0", isPostFromInquiry, null, ""));
        map.put("expired", productsDAO
                .queryProductsOfCompanyByStatusCount(companyId, account, null,
                        "1", "0", isPostFromInquiry, null, ""));
        map.put("pause", productsDAO.queryProductsOfCompanyByStatusCount(
                companyId, account, null, null, "1", isPostFromInquiry, null,
                ""));

        return map;
    }

    public List<ProductsDO> queryNewestProducts(String mainCode,
            String typeCode, Integer maxSize) {
        if (maxSize == null || maxSize.intValue() > 500) {
            maxSize = 20;
        }
        return productsDAO.queryNewestProducts(mainCode, typeCode, maxSize);
    }

    public Integer countProductsByCompanyId(Integer companyId) {
        Assert.notNull(companyId, "The companyId must not be null");
        return productsDAO.queryProductsOfCompanyByStatusCount(companyId, null,
                "1", "0", "0", "0", null, "");
    }

    public Boolean queryUserIsAddProducts(Integer id, String membershipCode) {
        // Assert.notNull(company, "company is not null");
        // String membershipCode = company.getMembershipCode();
        if (StringUtils.isEmpty(membershipCode)) {
            membershipCode = AstConst.COMMON_MEMBERSHIP_CODE;
        }

        Integer max = Integer.valueOf(MemberRuleFacade.getInstance().getValue(
                membershipCode, "publish_products_max_day"));

        Integer size = productsDAO.countUserAddProductsToday(id);
        if (size != null && size.intValue() < max.intValue()) {
            return true;
        } else {
            return false;
        }
    }

    public Date queryMaxRefreshTimeByCompanyId(Integer companyId) {
        return productsDAO.queryMaxRefreshTimeByCompanyId(companyId);
    }

    public Integer publishProductsByCompany(ProductsDO products,
            String membershipCode, String areaCode) {

        // 去除标题头部空格
        products.setTitle(products.getTitle().trim());

        if (products.getCompanyId() == null) {
            return null;
        }
        if (products.getAccount() == null) {
            return null;
        }
        if (products.getMaxPrice() == null) {
            products.setMaxPrice(0f);
        }
        if (products.getMinPrice() == null) {
            products.setMinPrice(0f);
        }

        // try {
        // // 敏感词验证 关键字
        // if(SensitiveUtils.validateSensitiveFilter(products.getTitle())){
        // products.setTitle((String)
        // SensitiveUtils.getSensitiveValue(products.getTitle(), "*"));
        // }
        //
        // // 敏感词验证 详细信息
        // if(SensitiveUtils.validateSensitiveFilter(products.getDetails())){
        // products.setDetails((String)
        // SensitiveUtils.getSensitiveValue(products.getDetails(),"*"));
        // }
        //
        // // 敏感词验证 标签
        // if(SensitiveUtils.validateSensitiveFilter(products.getTags())){
        // products.setTags((String)
        // SensitiveUtils.getSensitiveValue(products.getTags(),"*"));
        // }
        // } catch (Exception e) {
        //
        // }

        products.setPrice(String.valueOf(products.getMinPrice()));
        products.setPostType("0");
        products.setIsPause(false);
        if (products.getIsPostWhenViewLimit() == null) {
            products.setIsPostWhenViewLimit(false);
        }
        products.setIsPostFromInquiry(false);
        products.setIsDel(false);
        products.setCheckStatus(MemberRuleFacade.getInstance().getValue(
                membershipCode, "new_products_check"));
        if (ProductsService.CHECK_WAIT.equals(products.getCheckStatus())) {
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                if (SensitiveUtils.validateSensitiveFilter(products.getTitle())) {
                    products.setCheckStatus(ProductsService.CHECK_FAILD);
                    map.putAll(SensitiveUtils.getSensitiveFilter(products
                            .getTitle()));
                }
                if (SensitiveUtils.validateSensitiveFilter(products
                        .getDetails())) {
                    products.setCheckStatus(ProductsService.CHECK_FAILD);
                    map.putAll(SensitiveUtils.getSensitiveFilter(products
                            .getDetails()));
                }
                if (SensitiveUtils.validateSensitiveFilter(products.getTags())) {
                    products.setCheckStatus(ProductsService.CHECK_FAILD);
                    map.putAll(SensitiveUtils.getSensitiveFilter(products
                            .getTags()));
                }
                if (map.size() > 0) {
                    products.setCheckPerson("zz91-auto-check");
                    products.setUnpassReason("您发布的供求含有敏感词“"
                            + map.get("sensitiveSet") + "”，不符合我们网站的审核要求，请修改信息");
                }
            } catch (IOException e) {
                products.setCheckStatus(ProductsService.CHECK_WAIT);
            } catch (Exception e) {
                products.setCheckStatus(ProductsService.CHECK_WAIT);
            }
        }
        products.setUncheckedCheckStatus("0");

        // 过期时间判断
        if (products.getExpireTime() == null) {
            try {
                products.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",
                        AstConst.DATE_FORMATE_WITH_TIME));
            } catch (ParseException e) {
            }
        }

        Integer id = productsDAO.insertProduct(products);
        if (id != null && id.intValue() <= 0) {
            return 0;
        }

        CompanyPriceDO companyPriceDO = new CompanyPriceDO();
        companyPriceDO.setProductId(id);
        companyPriceDO.setAccount(products.getAccount());
        companyPriceDO.setCompanyId(products.getCompanyId());
        Company c = new Company();
        c.setMembershipCode(membershipCode);
        c.setAreaCode(areaCode);
        // 当报价存在并大于1 的情况下，添加到企业报价中
        // if(products.getMinPrice().longValue()>0){
        // companyPriceService.addProductsToCompanyPrice(companyPriceDO,c,
        // products);
        // }
        return id;
    }

    public Integer publishProductsFromInquiry(ProductsDO products) {
        if (products.getCompanyId() == null) {
            return null;
        }
        if (products.getAccount() == null) {
            return null;
        }
        products.setSourceTypeCode("");
        products.setPostType("1");
        products.setIsPause(false);
        products.setIsPostWhenViewLimit(false);
        products.setIsPostFromInquiry(true);
        products.setIsDel(false);
        if (products.getCheckStatus() == null) {
            products.setCheckStatus("0");
        }
        if (products.getUncheckedCheckStatus() == null) {
            products.setUncheckedCheckStatus("0");
        }
        return productsDAO.insertProduct(products);
    }

    public Integer batchDeleteProductsByIds(Integer[] entities,
            Integer companyId) {
        return productsDAO.batchDeleteProductsByIds(entities, companyId);
    }

    public Integer insertProductsPicRelation(Integer productId, Integer[] picIds) {
        Assert.notNull(productId, "the product id can not be null");
        int impact = 0;
        int j = 0;
        for (int i : picIds) {
            if (i > 0) {
                j = productsPicDAO.updateProductsIdById(productId, i);
                if (j > 0) {
                    impact++;
                }
            }
        }
        return impact;
    }

    public boolean isProductsAlreadyExists(String title,
            String productsTypeCode, String account) {
        if (StringUtils.isEmpty(title)) {
            return false;
        }
        if (StringUtils.isEmpty(productsTypeCode)) {
            productsTypeCode = "10331000";
        }
        Integer n = productsDAO.countProuductsByTitleAndAccount(title,
                productsTypeCode, account);
        if (n != null && n.intValue() > 0) {
            return true;
        }
        return false;
    }

    public Integer updateProductsCheckStatusByAdmin(String checkStatus,
            String unpassReason, String checkPerson, Integer productId) {
        Assert.notNull(productId, "the product id can not be null");
        return productsDAO.updateProductsCheckStatusByAdmin(checkStatus,
                unpassReason, checkPerson, productId);
    }

    public Integer updateProductsUncheckedCheckStatusByAdmin(
            String checkStatus, String unpassReason, String checkPerson,
            Integer productId) {
        return productsDAO.updateProductsUncheckedCheckStatusByAdmin(
                checkStatus, unpassReason, checkPerson, productId);
    }

    public Integer updateProductsIsShowInPrice(Integer id, String flag) {
        Assert.notNull(id, "the id can not be null");
        if (flag == null) {
            flag = "0";
        }
        return productsDAO.updateProductsIsShowInPrice(id, flag);
    }

    public Integer updateProductsIsPause(String isPause, Integer[] ids) {
        if (StringUtils.isEmpty(isPause)) {
            return null;
        }
        return productsDAO.updateProductsIsPause(isPause, ids);
    }

    public Integer refreshProducts(Integer productsId, Integer companyId,
            String membershipCode) {
        ProductsDO products = productsDAO
                .queryProductsWithOutDetailsById(productsId);
        if (products == null) {
            return null;
        }
        if (products.getRefreshTime() == null) {
            products.setRefreshTime(new Date());
        }
        Date now = new Date();
        Long interval = Long.valueOf(MemberRuleFacade.getInstance().getValue(
                membershipCode, "refresh_product_interval"));
        long intervalNow = now.getTime() - products.getRefreshTime().getTime();
        if (intervalNow > (interval.longValue() * 1000)) {
            if (AstConst.MAX_TIMT.equals(DateUtil.toString(
                    products.getExpireTime(), AstConst.DATE_FORMATE_WITH_TIME))) {
                return productsDAO.updateProductsRefreshTime(now, productsId,
                        companyId);
            } else {
                return productsDAO.updateProductsRefreshTimeAndExpireTime(
                        productsId, companyId);
            }
        }
        return null;
    }

    public Integer updateProductByAdmin(ProductsDO productsDO) {
        Assert.notNull(productsDO.getId(), "The id can not be null");
        return productsDAO.updateProductsByAdmin(productsDO);
    }

    public Integer updateProductsByCompany(ProductsDO products,
            String membershipCode) {
        products.setCheckStatus(MemberRuleFacade.getInstance().getValue(
                membershipCode, "update_products_check"));
        products.setUncheckedCheckStatus("0");
        if (products.getCategoryProductsAssistCode() == null) {
            products.setCategoryProductsAssistCode("");
        }
        if (StringUtils.isEmpty(products.getPriceUnit())) {
            products.setPriceUnit("元");
        }
        if (StringUtils.isEmpty(products.getQuantityUnit())) {
            products.setQuantityUnit("吨");
        }
        if (ProductsService.CHECK_WAIT.equals(products.getCheckStatus())) {
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                if (SensitiveUtils.validateSensitiveFilter(products.getTitle())) {
                    products.setCheckStatus(ProductsService.CHECK_FAILD);
                    map = SensitiveUtils
                            .getSensitiveFilter(products.getTitle());
                }
                if (SensitiveUtils.validateSensitiveFilter(products
                        .getDetails())) {
                    products.setCheckStatus(ProductsService.CHECK_FAILD);
                    map.putAll(SensitiveUtils.getSensitiveFilter(products
                            .getDetails()));
                }
                if (SensitiveUtils.validateSensitiveFilter(products.getTags())) {
                    products.setCheckStatus(ProductsService.CHECK_FAILD);
                    map.putAll(SensitiveUtils.getSensitiveFilter(products
                            .getTags()));
                }
                if (map.size() > 0 && map.get("sensitiveSet") != null) {
                    products.setUnpassReason("您发布的供求含有敏感词“"
                            + map.get("sensitiveSet") + "”，不符合我们网站的审核要求，请修改信息");
                }
            } catch (IOException e) {
                products.setCheckStatus(ProductsService.CHECK_WAIT);
            } catch (Exception e) {
                products.setCheckStatus(ProductsService.CHECK_WAIT);
            }
        }
        // try {
        // // 敏感词验证 关键字
        // if(SensitiveUtils.validateSensitiveFilter(products.getTitle())){
        // products.setTitle((String)
        // SensitiveUtils.getSensitiveValue(products.getTitle(), "*"));
        // }
        //
        // // 敏感词验证 详细信息
        // if(SensitiveUtils.validateSensitiveFilter(products.getDetails())){
        // products.setDetails((String)
        // SensitiveUtils.getSensitiveValue(products.getDetails(),"*"));
        // }
        //
        // // 敏感词验证 标签
        // if(SensitiveUtils.validateSensitiveFilter(products.getTags())){
        // products.setTags((String)
        // SensitiveUtils.getSensitiveValue(products.getTags(),"*"));
        // }
        // } catch (IOException e) {
        // return 0;
        // } catch (Exception e) {
        // return 0;
        // }
        return productsDAO.updateProductsByCompany(products);
    }

    public List<Integer> queryProductsIdsOfCompany(Integer companyId,
            String categoryCode) {
        Assert.notNull(companyId, "the companyId can not be null");
        return productsDAO.queryProductsIdsOfCompany(companyId, categoryCode);
    }

    /**
     * 轮回排序，去重复公司
     */
    @Override
    public PageDto<ProductsDto> pageLHProductsBySearchEngine(
            ProductsDO product, String areaCode, Boolean havePic,
            PageDto<ProductsDto> page) {
        if (page.getPageSize() == null) {
            page.setPageSize(10);
        }

        StringBuffer sb = new StringBuffer();
        SphinxClient cl = SearchEngineUtils.getInstance().getClient();

        List<ProductsDto> list = new ArrayList<ProductsDto>();
        try {
            if (StringUtils.isNotEmpty(product.getTitle())) {
                sb.append(
                        "@(title,tags,label0,label1,label2,label3,label4,city,province) ")
                        .append(product.getTitle());
            }

            if (StringUtils.isNotEmpty(product.getProductsTypeCode())) {
                if ("10331000".equals(product.getProductsTypeCode())) {
                    cl.SetFilter("pdt_kind", 1, true);
                } else {
                    cl.SetFilter("pdt_kind", 0, true);
                }
            }

            // 是否要图片
            if (havePic != null && havePic) {
                cl.SetFilterRange("havepic", 1, 5000, false);
                // cl.SetFilter("havepic", 1, true);
            }

            // 地区
            if (StringUtils.isNotEmpty(areaCode)) {
                sb = checkStringBuffer(sb);
                sb.append("@(province,city)").append(areaCode);
            }

            // group by 去重复公司
            // cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);

            // 所使用的服务
            if (StringUtils.isNotEmpty(product.getCrmCompanySvr())) {
                cl.SetFilter("crm_service_code",
                        Integer.valueOf(product.getCrmCompanySvr()), true);
            }

            cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
            // cl.SetLimits(page.getStartIndex(),
            // page.getPageSize()*CARDINAL_NUMBER, TOTAL_RECORDS);
            if (StringUtils.isEmpty(sb.toString())) {
                cl.SetLimits(page.getStartIndex(), 1000, 1000);
            } else {
                if (page.getPageSize() > 300) {
                    cl.SetLimits(page.getStartIndex(), TOTAL_RECORDS,
                            TOTAL_RECORDS);
                } else {
                    cl.SetLimits(page.getStartIndex(), page.getPageSize()
                            * CARDINAL_NUMBER, TOTAL_RECORDS);
                }
            }
            cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
            // 判断是否高会
            // if(product.getIsVip()){
            // cl.SetFilterRange("viptype", 1, 5, false);
            // }

            SphinxResult res = cl.Query(sb.toString(),
                    "offersearch_new,offersearch_new_vip");

            if (res == null) {
                page.setTotalRecords(0);
            } else {
                page.setTotalRecords(res.totalFound);
                Set<Integer> idSet = new HashSet<Integer>();
                for (int i = 0; i < res.matches.length; i++) {
                    // 列表数据 足够 跳出循环
                    if (list.size() >= page.getPageSize()) {
                        break;
                    }
                    // 伪轮回排序 获取不同公司的供求 即一间公司一条供求
                    do {
                        SphinxMatch info = res.matches[i];
                        ProductsDto dto = productsDAO
                                .queryProductsWithPicAndCompany(Integer
                                        .valueOf("" + info.docId));
                        if (dto == null || dto.getProducts() == null) {
                            break;
                        }
                        Integer companyId = dto.getProducts().getCompanyId();
                        if (idSet.contains(companyId)) {
                            break;
                        }
                        idSet.add(companyId);
                        if (dto != null
                                && dto.getProducts() != null
                                && StringUtils.isNotEmpty(dto.getProducts()
                                        .getDetails())) {
                            dto.getProducts().setDetails(
                                    Jsoup.clean(dto.getProducts().getDetails(),
                                            Whitelist.none()));
                        } else {
                            dto = new ProductsDto();
                            ProductsDO pdto = new ProductsDO();
                            Map<String, Object> resultMap = SearchEngineUtils
                                    .getInstance().resolveResult(res, info);
                            pdto.setId(Integer.valueOf("" + info.docId));
                            pdto.setTitle(resultMap.get("ptitle").toString());
                            dto.setProducts(pdto);
                        }
                        list.add(dto);
                    } while (false);
                }
            }
            page.setRecords(list);
        } catch (SphinxException e) {
            e.printStackTrace();
        }

        return page;
    }

    /**
     * 通用搜索引擎方法 有重复公司
     */
    @Override
    public PageDto<ProductsDto> pageProductsBySearchEngine(ProductsDO product,
            String areaCode, Boolean havePic, PageDto<ProductsDto> page) {
        if (page.getPageSize() == null) {
            page.setPageSize(10);
        }

        // 限制最大页数
        if (page.getStartIndex() != null
                && page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
            page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
        }

        StringBuffer sb = new StringBuffer();
        SphinxClient cl = SearchEngineUtils.getInstance().getClient();

        List<ProductsDto> list = new ArrayList<ProductsDto>();
        try {

            if (StringUtils.isNotEmpty(product.getTitle())) {
                sb.append(
                        "@(title,tags,label0,label1,label2,label3,label4,city,province) ")
                        .append(product.getTitle());
            }

            if (StringUtils.isNotEmpty(product.getProductsTypeCode())) {
                if ("10331000".equals(product.getProductsTypeCode())) {
                    cl.SetFilter("pdt_kind", 1, true);
                } else {
                    cl.SetFilter("pdt_kind", 0, true);
                }
            }

            // 是否要图片
            if (havePic != null && havePic) {
                cl.SetFilterRange("havepic", 1, 5000, false);
                // cl.SetFilter("havepic", 1, true);
            }

            // 地区
            if (StringUtils.isNotEmpty(areaCode)) {
                sb = checkStringBuffer(sb);
                sb.append("@(province,city)").append(areaCode);
            }

            // group by 去重复公司
            // cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);

            // 所使用的服务
            if (StringUtils.isNotEmpty(product.getCrmCompanySvr())) {
                cl.SetFilter("crm_service_code",
                        Integer.valueOf(product.getCrmCompanySvr()), true);
            }

            cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
            cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
            cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
            // 判断是否高会
            // if(product.getIsVip()){
            // cl.SetFilterRange("viptype", 1, 5, false);
            // }

            SphinxResult res = cl.Query(sb.toString(),
                    "offersearch_new,offersearch_new_vip");

            if (res == null) {
                page.setTotalRecords(0);
            } else {
                page.setTotalRecords(res.totalFound);
                for (int i = 0; i < res.matches.length; i++) {
                    do {
                        SphinxMatch info = res.matches[i];
                        ProductsDto dto = productsDAO
                                .queryProductsWithPicAndCompany(Integer
                                        .valueOf("" + info.docId));

                        if (dto != null && dto.getCompany() != null) {
                            String a = dto.getCompany().getAreaCode();
                            String areaLabel = "";
                            if (a.length() >= 12) {
                                areaLabel += CategoryFacade.getInstance()
                                        .getValue(a.substring(0, 12));
                            }
                            if (a.length() >= 16) {
                                areaLabel += CategoryFacade.getInstance()
                                        .getValue(a.substring(0, 16));
                            }
                            dto.setAreaLabel(areaLabel);

                        }
                        if (dto == null || dto.getProducts() == null) {
                            break;
                        }
                        if (dto != null
                                && dto.getProducts() != null
                                && StringUtils.isNotEmpty(dto.getProducts()
                                        .getDetails())) {
                            dto.getProducts().setDetails(
                                    Jsoup.clean(dto.getProducts().getDetails(),
                                            Whitelist.none()));
                        } else {
                            dto = new ProductsDto();
                            ProductsDO pdto = new ProductsDO();
                            Map<String, Object> resultMap = SearchEngineUtils
                                    .getInstance().resolveResult(res, info);
                            pdto.setId(Integer.valueOf("" + info.docId));
                            pdto.setTitle(resultMap.get("ptitle").toString());
                            dto.setProducts(pdto);

                        }
                        list.add(dto);
                    } while (false);
                }
            }
            page.setRecords(list);
        } catch (SphinxException e) {
            e.printStackTrace();
        }

        return page;
    }

    /**
     * 利用搜索引擎搜索list
     */
    @Override
    public List<ProductsDto> querypicByKeyWord(ProductsDO productsDO,
            boolean havePic, Integer pagesize) {
        PageDto<ProductsDto> page = new PageDto<ProductsDto>();
        page.setPageSize(pagesize);
        page = pageLHProductsBySearchEngine(productsDO, null, havePic, page);
        return page.getRecords();
    }

    @Override
    public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
            String productCategory, Integer size) {
        if (size == null) {
            size = 10;
        }
        if (size > 50) {
            size = 50;
        }
        if (productCategory != null && productCategory.length() > 4) {
            productCategory = productCategory.substring(0, 4);
        }
        return productsDAO.queryNewestVipProducts(productTypeCode,
                productCategory, size);
        // return pageProductsBySearchEngine(product,null,page).getRecords();
    }

    @Override
    public ProductsDO queryProductsByCid(Integer cid) {
        return productsDAO.queryProductsByCid(cid);
    }

    @Override
    public ProductsDO queryProductsByCidForLatest(Integer cid,
            ProductsDO products) {
        return productsDAO.queryProductsByCidForLatest(cid, products);
    }

    @Override
    public boolean queryLastGmtCreateTimeByCId(Integer cid) {

        boolean flag = false;
        Date date = productsDAO.queryLastGmtCreateTimeByCId(cid);
        if (date == null) {
            return true;
        }
        long lastTimes = DateUtil.getMillis(date);
        // long nowTime = DateUtil.getMillis(new Date());
        long nowTime = DateUtil.getMillis(productsDAO.queryNowTimeOfDatebase());
        long timeTemp = (nowTime - lastTimes) / 1000;
        // flag ==true说明可以插入
        if (timeTemp > 30) {
            flag = true;
        }

        return flag;
    }

    @Override
    public PageDto<ProductsDto> pageProductsWithCompany(Company company,
            ProductsDO products, PageDto<ProductsDto> page,
            String industryCode, String areaCode, String keywords) {
        page.setRecords(productsDAO.queryProductsWithCompany(company, products,
                page, industryCode, areaCode, keywords));
        page.setTotalRecords(productsDAO.countQueryProductsWithCompany(company,
                products, page, industryCode, areaCode, keywords));
        return page;
    }

    public List<ProductsDto> queryProductsByAreaCode4Map(String mainCode,
            String title, String typeCode, String areaCode, Integer maxSize) {

        List<ProductsDto> productsList = productsDAO
                .queryProductsByAreaCode4Map(mainCode, title, typeCode,
                        areaCode, maxSize);

        CategoryFacade categoryFacade = CategoryFacade.getInstance();
        for (ProductsDto dto : productsList) {
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
            dto.setCategoryProductsMainLabel(CategoryProductsFacade
                    .getInstance().getValue(
                            dto.getProducts().getCategoryProductsMainCode()));
            dto.setCategoryProductsAssistLabel(CategoryProductsFacade
                    .getInstance().getValue(
                            dto.getProducts().getCategoryProductsAssistCode()));
        }

        return productsList;
    }

    @Override
    public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
            Integer size) {

        List<Company> list = companyDAO.queryCompanyZstMemberAsc((size + 10));
        ProductsDto p = null;
        List<ProductsDto> proList = new ArrayList<ProductsDto>();
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            p = productsDAO.queryProductsWithPicByCidAndTypeCode(id,
                    productTypeCode, 1);
            if (p != null) {
                proList.add(p);
            }
            if (proList.size() == 14) {
                break;
            }
        }

        return proList;
    }

    @Override
    public Integer updateUncheckByIdForMyrc(Integer id) {
        return productsDAO.updateUncheckByIdForMyrc(id);
    }

    public PageDto<ProductsDto> pageProductsWithPicByCompanyEsiteWithDetails(
            Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
        page.setTotalRecords(productsDAO
                .queryProductsWithPicByCompanyEsiteCount(companyId, kw, sid));
        List<ProductsDto> list = productsDAO
                .queryProductsWithPicByCompanyEsiteWithDetails(companyId, kw,
                        sid, page);
        for (ProductsDto obj : list) {
            obj.getProducts().setDetails(
                    Jsoup.clean(obj.getProducts().getDetails(),
                            Whitelist.none()));
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ProductsDO> queryNoRepeatProducts(String industryCode,
            String areaCode, String keywords, String typeCode,
            PageDto<ProductsDto> page) {
        return productsDAO.queryNoRepeatProducts(industryCode, areaCode,
                keywords, typeCode, page);
    }

    public Integer countQueryNoRepeatProducts(String industryCode,
            String areaCode, String keywords, String typeCode) {
        // 主营金属
        // if("1000".equals(industryCode)){
        // industryCode = "10001001";
        // }
        // 主营塑料
        // if("1001".equals(industryCode)){
        // industryCode = "10001000";
        // }
        // 主营综合
        if ("1002".equals(industryCode)) {
            industryCode = "zh";
        }
        return productsDAO.countQueryNoRepeatProducts(industryCode, areaCode,
                keywords, typeCode);
    }

    @Override
    public Integer updateGaoCheckStatusByAdmin(String checkStatus,
            String checkPerson, Integer productId, String unpassReason) {
        return productsDAO.updateGaoCheckStatusByAdmin(checkStatus,
                checkPerson, productId, unpassReason);
    }

    @Override
    public List<ProductsDto> queryHotProducts(String mainCode,
            String productsTypeCode, Integer maxSize) {
        // 查询 会员
        List<Company> com = companyDAO
                .queryCompanyZstMemberByLastLoginTime(maxSize);
        // 根据会员查询产品
        List<ProductsDto> list = new ArrayList<ProductsDto>();
        for (Company company : com) {
            ProductsDto pro = productsDAO
                    .queryProductsByCidAndTypeCodeAndMainCode(company.getId(),
                            mainCode, productsTypeCode, 1);
            if (pro != null) {
                list.add(pro);
            }
        }

        return list;
    }

    @Override
    public List<ProductsDto> queryVipProductsForMyrc(String title, Integer size) {
        return productsDAO.queryVipProductsForMyrc(title, size);
    }

    @Override
    public Integer updateProductsCheckStatusForDelByAdmin(String checkStatus,
            String unpassReason, String checkPerson, Integer productId,
            String isDel) {
        return productsDAO.updateProductsUncheckedCheckStatusForDelByAdmin(
                checkStatus, unpassReason, checkPerson, productId, isDel);
    }

    @Override
    public PageDto<ProductsDO> pageProductsByTyepOfCompany(String companyId,
            String productTypeCode, PageDto<ProductsDO> page) {
        if (page.getSort() == null) {
            page.setSort("p.refresh_time");
        }
        if (page.getDir() == null) {
            page.setDir("desc");
        }
        if (StringUtils.isEmpty(productTypeCode)) {
            productTypeCode = "10331000";
        }
        List<ProductsDO> list = productsDAO.queryProductsByTypeOfCompany(
                companyId, productTypeCode, page);
        page.setRecords(list);
        page.setTotalRecords(productsDAO.queryProductsByTypeOfCompany(
                companyId, productTypeCode));
        return page;
    }

    @Override
    public List<ProductsDO> queryProductsByTyepOfCompany(String companyId,
            String productTypeCode, PageDto<ProductsDO> page) {
        if (page.getSort() == null) {
            page.setSort("p.refresh_time");
        }
        if (page.getDir() == null) {
            page.setDir("desc");
        }
        if (StringUtils.isEmpty(productTypeCode)) {
            productTypeCode = "10331000";
        }
        List<ProductsDO> list = productsDAO.queryProductsByTypeOfCompany(
                companyId, productTypeCode, page);
        return list;
    }

    @Override
    public Integer updateProductsIsDelByAdmin(Integer productId, String status) {
        Assert.notNull(productId, "the product id can not be null");
        return productsDAO.updateProductsIsDelByAdmin(productId, status);
    }

    private StringBuffer checkStringBuffer(StringBuffer sb) {
        if (sb.length() != 0) {
            sb.append("&");
        }
        return sb;
    }

    @Override
    public PageDto<ProductsDto> pageProductsForSpot(ProductsDO product,
            PageDto<ProductsDto> page, String isTe, String isHot, String isYou) {
        if (page == null) {
            page = new PageDto<ProductsDto>();
        }
        page.setSort("refresh_time");
        page.setDir("desc");
        ProductsDto dto = new ProductsDto();
        if (StringUtils.isNotEmpty(isTe)) {
            dto.setIsTe(isTe);
        }
        if (StringUtils.isNotEmpty(isHot)) {
            dto.setIsHot(isHot);
        }
        if (StringUtils.isNotEmpty(isYou)) {
            dto.setIsYou(isYou);
        }
        dto.setProducts(product);
        List<ProductsDO> list = productsDAO.queryProductForSpot(dto, page);
        List<ProductsDto> nlist = new ArrayList<ProductsDto>();
        for (ProductsDO obj : list) {
            ProductsDto productsDto = new ProductsDto();
            productsDto.setProducts(obj);
            CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(obj
                    .getCompanyId());
            productsDto.setCompany(companyDto.getCompany());
            productsDto.setCompanyContacts(companyDto.getAccount());
            productsDto.setCoverPicUrl(productsPicDAO
                    .queryPicPathByProductId(obj.getId()));
            // 获取保证金字段信息
            ProductsSpot ps = productsSpotDao.queryByProductId(obj.getId());
            if (ps == null) {
                productsDto.setIsBail(null);
            } else {
                productsDto.setIsBail(ps.getIsBail());
            }
            nlist.add(productsDto);
        }
        page.setRecords(nlist);
        page.setTotalRecords(productsDAO.queryCountProductForSpot(dto));
        return page;
    }

    @Override
    public List<ProductsDto> queryProductsForSpotByCondition(String isTe,
            String isHot, String isYou, Integer size) {
        ProductsDto dto = new ProductsDto();
        if (StringUtils.isNotEmpty(isTe)) {
            dto.setIsTe(isTe);
        }
        if (StringUtils.isNotEmpty(isHot)) {
            dto.setIsHot(isHot);
        }
        if (StringUtils.isNotEmpty(isYou)) {
            dto.setIsYou(isYou);
        }
        List<ProductsDO> list = productsDAO.queryProductsForSpotByCondition(
                dto, size);
        List<ProductsDto> nlist = new ArrayList<ProductsDto>();
        for (ProductsDO obj : list) {
            ProductsDto productsDto = new ProductsDto();
            productsDto.setProducts(obj);
            CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(obj
                    .getCompanyId());
            productsDto.setCompany(companyDto.getCompany());
            productsDto.setCompanyContacts(companyDto.getAccount());
            productsDto.setCoverPicUrl(productsPicDAO
                    .queryPicPathByProductId(obj.getId()));
            // 获取保证金字段信息
            ProductsSpot ps = productsSpotDao.queryByProductId(obj.getId());
            if (ps == null) {
                productsDto.setIsBail(null);
            } else {
                productsDto.setIsBail(ps.getIsBail());
            }
            productsDto.setProductsSpot(ps);
            nlist.add(productsDto);
        }
        return nlist;
    }

    @Override
    public PageDto<ProductsDto> pageProductsForSpotByAdmin(Company company,
            ProductsDO products, PageDto<ProductsDto> page, Integer min,
            Integer max, String isStatus) {
        List<ProductsDto> list = productsDAO.queryProductForSpotByAdmin(
                company, products, page, min, max, isStatus);
        for (ProductsDto dto : list) {
            CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(dto
                    .getProducts().getCompanyId());
            dto.setCompany(companyDto.getCompany());
            dto.setCompanyContacts(companyDto.getAccount());
            String status = "";
            do {
                // 公司是否属于黑名单
                if ("1".equals(dto.getCompany().getIsBlock())) {
                    dto.getCompany().setName(
                            dto.getCompany().getName() + "(黑名单)");
                }
                if ("1".equals(dto.getProductsSpot().getIsBail())) {
                    status += "(保)";
                }
                if ("1".equals(dto.getProductsSpot().getIsHot())) {
                    status += "(热)";
                }
                if ("1".equals(dto.getProductsSpot().getIsTe())) {
                    status += "(特)";
                }
                if ("1".equals(dto.getProductsSpot().getIsYou())) {
                    status += "(优)";
                }
                // 信息已删除 或者 账户被禁用
                // if(dto.getProducts().getIsDel()==true||"1".equals(dto.getCompany().getIsBlock())){
                // status = "(已删除)";
                // break;
                // }
                // // 暂不发布
                // if(dto.getProducts().getIsPause()==true){
                // status = "(暂不发布)";
                // break;
                // }
                // // 是否过期
                // long expired=dto.getProducts().getExpireTime().getTime();
                // long today=new Date().getTime();
                // long result= today - expired;
                // if(result>0){
                // status = "(已过期)";
                // break;
                // }
            } while (false);
            CategoryFacade categoryFacade = CategoryFacade.getInstance();
            CategoryProductsFacade categoryProductsFacade = CategoryProductsFacade
                    .getInstance();
            dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
            dto.setCategoryProductsMainLabel(categoryProductsFacade
                    .getValue(dto.getProducts().getCategoryProductsMainCode()));
        }
        page.setRecords(list);
        page.setTotalRecords(productsDAO.queryCountProductForSpotByAdmin(
                company, products, min, max, isStatus));
        return page;
    }

    @Override
    public Integer queryTodayCopperProductsCount(String code, Date time) {
        String from = "";
        String to = "";
        if (time != null) {
            from = DateUtil.toString(DateUtil.getDateAfterDays(time, -1),
                    "yyyy-MM-dd");
            to = DateUtil.toString(time, "yyyy-MM-dd");
        }
        return productsDAO.queryTodayCopperProductsCount(code, from, to);
    }

    @Override
    public List<ProductsDto> queryProductsForPic(ProductsDO products,
            Integer size) {
        List<ProductsDto> list = new ArrayList<ProductsDto>();
        List<ProductsDO> plist = productsDAO
                .queryProductsForPic(products, size);
        for (ProductsDO obj : plist) {
            ProductsDto dto = new ProductsDto();
            if (obj != null && obj.getId() != null) {
                String picPath = productsPicDAO.queryPicPathByProductId(obj
                        .getId());
                dto.setCoverPicUrl(picPath);
                dto.setProducts(obj);
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public PageDto<ProductsDto> queryProductForexportByAdmin(Company company,
            ProductsDO products, PageDto<ProductsDto> page, Integer min,
            Integer max, String isStatus) {
        List<ProductsDto> list = productsDAO.queryProductForexportByAdmin(
                company, products, page, min, max, isStatus);
        for (ProductsDto dto : list) {
            CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(dto
                    .getProducts().getCompanyId());
            dto.setCompany(companyDto.getCompany());
            dto.setCompanyContacts(companyDto.getAccount());
            String status = "";
            do {
                // 公司是否属于黑名单
                if ("1".equals(dto.getCompany().getIsBlock())) {
                    dto.getCompany().setName(
                            dto.getCompany().getName() + "(黑名单)");
                }
            } while (false);
            CategoryFacade categoryFacade = CategoryFacade.getInstance();
            CategoryProductsFacade categoryProductsFacade = CategoryProductsFacade
                    .getInstance();
            dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
            dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
                    .getProductsTypeCode()));
            dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
                    .getAreaCode()));
            dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
                    .getMembershipCode()));
            dto.setCategoryProductsMainLabel(categoryProductsFacade
                    .getValue(dto.getProducts().getCategoryProductsMainCode()));
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ProductsDto> buildLIst() {
        List<ProductsDto> listgy = productsDAO.queryNewestVipProducts(
                "10331000", "1001", 20);
        List<ProductsDto> listqg = productsDAO.queryNewestVipProducts(
                "10331001", "1001", 20);
        List<ProductsDto> list = new ArrayList<ProductsDto>();
        for (int i = 0; i < listgy.size(); i++) {
            list.add(listgy.get(i));
            list.add(listqg.get(i));
        }
        return list;
    }

    @Override
    public PageDto<ProductsDto> pageSPProductsBySearchEngine(
            ProductsDO product, PageDto<ProductsDto> page) {
        page = pageProductsBySearchEngine(product, null, null, page);
        for (ProductsDto dto : page.getRecords()) {
            Integer companyId = dto.getProducts().getCompanyId();
            dto.setCompanyContacts(companyAccountDao
                    .queryAccountByCompanyId(companyId));
            Company company = companyDAO.queryCompanyById(companyId);
            dto.setCompany(company);
            // 公司地区
            if (company != null
                    && StringUtils.isNotEmpty(company.getAreaCode())) {
                if (company.getAreaCode().length() >= 12) {
                    dto.setAreaLabel(CategoryFacade.getInstance().getValue(
                            company.getAreaCode().substring(0, 12)));
                }
                if (company.getAreaCode().length() >= 16) {
                    dto.setAreaLabel(dto.getAreaLabel()
                            + CategoryFacade.getInstance().getValue(
                                    company.getAreaCode().substring(0, 16)));
                }
            }
            // 产品其他信息
            if (dto.getProducts() != null && dto.getProducts().getId() != null) {
                dto.setProducts(productsDAO.queryProductsById(dto.getProducts()
                        .getId()));
            }
            // tags list 获取
            String tags = "";

            if (dto.getProducts() != null
                    && dto.getProducts().getTags() != null) {
                tags += dto.getProducts().getTags();
            }
            if (dto.getProducts() != null
                    && dto.getProducts().getTagsAdmin() != null) {
                tags += "," + dto.getProducts().getTagsAdmin();
            }
            Map<String, String> map = TagsUtils.getInstance().encodeTags(tags,
                    "utf-8");
            for (String key : map.keySet()) {
                map.put(key, CNToHexUtil.getInstance().encode(key));
            }
            dto.setTagsMap(map);
        }
        return page;
    }

    @Override
    public PageDto<ProductsDto> pageProductsWithPicByCompanyForSp(
            Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {

        page.setTotalRecords(productsDAO
                .queryProductsWithPicByCompanyForSpCount(companyId, kw, sid));
        List<ProductsDto> list = productsDAO
                .queryProductsWithPicByCompanyForSp(companyId, kw, sid, page);
        for (ProductsDto obj : list) {
            obj.getProducts().setDetails(
                    Jsoup.clean(obj.getProducts().getDetails(),
                            Whitelist.none()));
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ProductsDO> queryPassProductsByDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date = new Date();
        try {
            date = DateUtil.getDate(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            return null;
        }
        String to = DateUtil.toString(DateUtil.getDateAfterDays(date, 1),
                "yyyy-MM-dd");
        return productsDAO.queryPassProductsByDate(dateStr, to);
    }
	
	@Override
	public String getTagAdmin(ProductsDto productsDto){
		List<String> keyList = null;
		String title = productsDto.getProducts().getTitle();
		// 以 大小 逗号 分隔，均分成多个关键字进行关键字切词
		title = title.replace("，", ",");
		String [] key = title.split(",");
		String tagsAdmin = "";
		Set<String> kList = new HashSet<String>();
		for(String kTitle :key){
			try{
				keyList = IKAnalzyerUtils.getAnalzyerList(kTitle);
			}catch (Exception e) {
				keyList =null;
			}
			for(String str:keyList){
				// 文字长度大于等于2
				if(StringUtils.isEmpty(str)||str.length()<=1){
					continue;
				}
				// 大写
				str = str.toUpperCase();
				kList.add(str);
			}
			for(String str:kList){
				tagsAdmin += str+",";
			}
		}
		tagsAdmin += CategoryProductsFacade.getInstance().getValue(productsDto.getProducts().getCategoryProductsMainCode());
		if(StringUtils.isNotEmpty(productsDto.getCategoryProductsAssistLabel())){
			tagsAdmin += ","+productsDto.getCategoryProductsAssistLabel();
		}
		return tagsAdmin;
	}
}
