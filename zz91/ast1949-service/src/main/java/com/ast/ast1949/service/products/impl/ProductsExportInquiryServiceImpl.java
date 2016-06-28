package com.ast.ast1949.service.products.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.InquiryDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsExportInquiryDao;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.products.ProductsExportInquiryService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("productsExportInquiryService")
public class ProductsExportInquiryServiceImpl implements ProductsExportInquiryService{

	@Resource
	private ProductsExportInquiryDao productsExportInquiryDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private InquiryDao inquiryDao;
	@Resource
	private CompanyDAO companyDAO;
	
	@Override
	public Integer countByProductId(Integer productId) {
		if (productId==null) {
			return 0;
		}
		return productsExportInquiryDao.countByProductId(productId);
	}

	@Override
	public Integer exportInquiry(Integer productId, Integer[] targetIdArray,String account) {

		Integer fromCompanyId = getCompanyIdByProductId(productId);
		ProductsDO productsDO = productsDAO.queryProductsById(productId);
		Integer count = 0;
		List<ProductsExportInquiry> exportList = new ArrayList<ProductsExportInquiry>();
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
		Set<String> existSet = new HashSet<String>();
		for (Integer targetId : targetIdArray) {
			Integer toCompanyId = getCompanyIdByProductId(targetId);
			// 同一条供求 跟 同一家公司 一月一次
			Integer i = productsExportInquiryDao.countByProductIdAndToCompId(productId,toCompanyId);
			if(i>0){
				continue;
			}
			
			if(existSet.contains(productId+","+toCompanyId)){
				continue;
			}
			
			existSet.add(productId+","+toCompanyId);
			
			// 接收方最多收到10条询盘
			i = productsExportInquiryDao.countByCompanyId(toCompanyId);
			if(i>=10){
				continue;
			}

			// 导出日志 数据 装载
			ProductsExportInquiry obj = new ProductsExportInquiry();
			obj.setFromCompanyId(fromCompanyId);
			obj.setProductId(productId);
			obj.setTargetId(targetId);
			obj.setToCompanyId(toCompanyId);
			exportList.add(obj);
			
			// 询盘类 数据 装载
			Inquiry inquiry = new Inquiry();
			inquiry.setTitle(productsDO.getTitle());
			inquiry.setContent(productsDO.getDetails());
			inquiry.setInquiredType("0");
			
			inquiry.setSenderAccount(productsDO.getAccount()); //发送人
			inquiry.setReceiverAccount(ProductsExportInquiryService.COMPANY_ID_WITH_NAME.get(toCompanyId)); //接收人
			inquiry.setBeInquiredId(targetId);
			inquiry.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT);
			
			inquiry.setBatchSendType("0");
			inquiry.setExportStatus("0");
			inquiry.setExportPerson(account);
			
			inquiry.setIsRubbish("0");
			inquiry.setIsViewed("0");
			inquiry.setIsSenderDel("0");
			inquiry.setIsReceiverDel("0");
			inquiry.setIsReplyed("0");
			inquiryList.add(inquiry);
			
			//计数器+1
			count ++;

		}
		// 导出询盘
		inquiryDao.batchInsert(inquiryList);
		// 执行导出
		productsExportInquiryDao.batchInsert(exportList);
		
		return count;
	}

	@Override
	public ProductsExportInquiry queryByProductId(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Integer getCompanyIdByProductId(Integer productId){
		Integer companyId =  ProductsExportInquiryService.PROCUCT_WITH_COMPANY.get(productId);
		if(companyId==null){
			ProductsDO obj = productsDAO.queryProductsById(productId);
			if (obj!=null) {
				companyId=obj.getCompanyId();
				ProductsExportInquiryService.PROCUCT_WITH_COMPANY.put(productId, companyId);
				ProductsExportInquiryService.COMPANY_ID_WITH_NAME.put(companyId, obj.getAccount());
			}else{
				companyId = 0;
			}
		}
		return companyId;
	}

	@Override
	public PageDto<ProductsDto> pageProductsExport(Integer productId,Integer companyId,PageDto<ProductsDto> page) {
		
		List<ProductsExportInquiry> list = productsExportInquiryDao.queryList(productId,companyId,page);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>();
		String fromTitle = "";
		ProductsDO productsDO = new ProductsDO();
		for (ProductsExportInquiry obj :list) {
			ProductsDto dto = new ProductsDto();
			
			
			ProductsDO products =  productsDAO.queryProductsById(obj.getTargetId());
			Company company =  companyDAO.queryCompanyById(obj.getToCompanyId());
			
			
			// 设置该供求 title
			if(productId!=null&&StringUtils.isEmpty(fromTitle)){
				productsDO = productsDAO.queryProductsById(productId);
				fromTitle = productsDO.getTitle();
			}
			if(StringUtils.isNotEmpty(fromTitle)){
				dto.setFromTitle(fromTitle);
				Inquiry inquiry =  inquiryDao.queryExportInquiryFromProduct(productsDO.getAccount(), obj.getTargetId());
				products.setDetails(inquiry.getContent());
			}
			
			dto.setProducts(products);
			dto.setCompany(company);
			dto.setCountInquiry(productsExportInquiryDao.countByProductId(dto.getProducts().getId()));
			if(obj.getGmtCreated()!=null){
				dto.setGmtInquiryStr(DateUtil.toString(obj.getGmtCreated(), "yyyy-MM-dd"));
			}
			
			
			nlist.add(dto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(productsExportInquiryDao.countList(productId, companyId));
		
		return page;
	}

}
