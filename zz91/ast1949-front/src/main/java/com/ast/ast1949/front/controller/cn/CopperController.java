package com.ast.ast1949.front.controller.cn;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.param.ParamUtils;
/**
 * 废铜页面单页
 * @author sj
 *
 */
@Controller
public class CopperController extends BaseController {
	@Resource
	private PriceService priceService;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyService companyService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		//今日新增废铜信息
//		out.put("copperCount", priceService.queryPriceByTitleCount("废铜"));
		PriceDO priceDO = new PriceDO();
		priceDO.setGmtOrder(new Date());
		PageDto<PriceDO> page = new PageDto<PriceDO>();
		out.put("copperCount", priceService.pagePriceBySearchEngine("铜", priceDO, page).getTotalRecords());
		// 三条行情读取http://price.zz91.com/priceList_t216_metal.htm行情综述的最新的三条；
		PageDto<PriceDO> pricePage = new PageDto<PriceDO>();
		pricePage.setPageSize(2);
		out.put("thqList", priceService.queryPriceByType(216, null, null,
				pricePage));
		// 二条动态读取http://price.zz91.com/moreList_p19_metal.htm市场动态的最新的二条
		pricePage.setPageSize(2);
		out.put("sdtList", priceService.queryPriceByType(null, 19, null,
				pricePage));
		// 短信报价 江浙沪光亮铜(Cu>99%)、广东南海光亮铜(Cu>99%) cachefragment
		
		// 2.5 广告下方三条产品信息
//		List<ProductsIndex> zgglist = productsIndexService.queryProductsDateByCode("10091001", 3);
//		for(ProductsIndex obj:zgglist){
//			obj.setAccount(companyService.queryCompanyNameById(obj.getCompanyId()));
//		}
//		out.put("zggList", zgglist);
		// http://price.zz91.com/companyprice/index.htm?categoryCompanyPriceCode=10011002搜索含“铜”最新的企业报价
		PageDto<CompanyPriceSearchDTO> cpricePage = new PageDto<CompanyPriceSearchDTO>();
		cpricePage.setPageSize(12);
		CompanyPriceSearchDTO cDto = new CompanyPriceSearchDTO();
		cDto.setCategoryCompanyPriceCode("10011002");
		out.put("qybjList", companyPriceService.queryCompanyPriceSearchByFront(cDto, cpricePage));

		// 14:24:13【*****公司】发布了一条求购废铜信息
		// 废铜http://trade.zz91.com/offerlist--mc100010011000.htm 最新废铜供求
		PageDto<ProductsDto> productPage = new PageDto<ProductsDto>();
		ProductsDO product = new ProductsDO();
//		product.setCategoryProductsMainCode("100010011000");
		product.setTitle(CategoryProductsFacade.getInstance().getValue("100010011000"));
		productPage.setPageSize(1);
		// 最新废铜供求 一条
		out.put("zxgqList", productsService.pageProductsBySearchEngine(product,null,null, productPage).getRecords());
		productPage.setPageSize(9);
		// 最新废铜供应 9条
		product.setProductsTypeCode("10331000");
		out.put("ftgyList", productsService.pageProductsBySearchEngine(product,null,null, productPage).getRecords());
		// 最新废铜求购 9条
		product.setProductsTypeCode("10331001");
		out.put("ftqgList", productsService.pageProductsBySearchEngine(product,null,null, productPage).getRecords());
		// 2.10 日评 更多http://price.zz91.com/priceList_t32_c10_metal.htm
		// 读取http://price.zz91.com/priceList_t32_c10_metal.htm 展示6条
		pricePage.setPageSize(6);
		out.put("rpList", priceService.queryPriceByType(32, null, null,pricePage));
		// 2.11 周评 更多http://price.zz91.com/priceList_t33_c10_metal.htm
		// 读取http://price.zz91.com/priceList_t33_c10_metal.ht 展示6条
		pricePage.setPageSize(6);
		out.put("zpList", priceService.queryPriceByType(33, null, null,pricePage));
		// 2.12.1 LME/期货价格 读取http://price.zz91.com/moreList_p64_metal.htm
		out.put("lmeList", getPriceWithParentList(64,null,6));
		// 2.12.2各地废铜价格 读取http://price.zz91.com/moreList_p3_t40_metal.htm
		out.put("gdbjList", getPriceWithParentList(null,40,6));
		// 2.12.3 现货铜价 读取http://price.zz91.com/moreList_p67_metal.htm
		out.put("xhList", getPriceWithParentList(67,null,6));
		// 2.13 其他废金属价格 读取http://price.zz91.com/moreList_p17_metal.htm 去除“废铜价格”
		out.put("qtList", getPriceWithParentList(17,null,8));
		// 2.14 市场价格 暂无给出
		
		// 2.16 为您推荐 高会的信息，一排供应信息，一排求购信息！ 字数限制8个字
		ProductsDO products = new ProductsDO();
		products.setProductsTypeCode("10331000");
		out.put("vipgy", companyService.queryVipNoSame(5, products));
		products.setProductsTypeCode("10331001");
		out.put("vipqg", companyService.queryVipNoSame(5, products));
		
		// 广告文字
		out.put("indexAd", ParamUtils.getInstance().getChild("index_ad"));
		return null;
	}

	private List<PriceDO> getPriceWithParentList(Integer parentId,Integer typeId,Integer size) {
		List<PriceDO> list=priceService.queryPriceByTypeId(typeId, parentId, null, size);
		return list;
	}

//	private Map<Integer, List<PriceDO>> getPriceWithParentList(Integer parentId,Integer typeId) {
//		List<PriceCategoryDO> list = priceCategoryService.queryPriceCategoryByParentId(parentId);
//		Map<Integer, List<PriceDO>> priceMap = new HashMap<Integer, List<PriceDO>>();
//		for (PriceCategoryDO category : list) {
//			Integer typeid = typeId;
//			Integer assistId = category.getId();
//			if (typeid == null) {
//				typeid = category.getId();
//				assistId = null;
//			}
//			if(typeid==85){
//				break;
//			}
//			priceMap.put(category.getId(), priceService.queryPriceByTypeId(typeid, null, assistId, 1));
//		}
//		return priceMap;
//	}
	
}
