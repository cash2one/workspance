package com.kl91.front.controller.zhushou;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.UploadPic;
import com.kl91.domain.dto.ExtResult;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsDto;
import com.kl91.domain.products.Products;
import com.kl91.front.controller.BaseController;
import com.kl91.service.company.UploadPicService;
import com.kl91.service.products.ProductsService;
import com.zz91.util.datetime.DateUtil;

@Controller
public class GuanliController extends BaseController {

	@Resource
	private ProductsService productsService;
	@Resource
	private UploadPicService uploadPicService;

	@RequestMapping
	public void guanli(Map<String, Object> out, PageDto<ProductsDto> page,
			Products products, Integer checkedFlag, Integer publishFlag) {
		page.setPageSize(5);
		do {
			if (publishFlag == null) {
				publishFlag = ProductsService.PUBLISH_FLAG;
			}
//			if (checkedFlag == null) {
//				checkedFlag = ProductsService.CHECKED_SUCCESS;
//			}
			productsService.queryProductsForList(
					ProductsService.DELETE_FAILTURE, publishFlag, checkedFlag,
					page);
		} while (false);
		out.put("page", page);
		out.put("products", products);
		out.put("checkedFlag", checkedFlag);
		out.put("publishFlag", publishFlag);
	}

//	@RequestMapping
//	public void shenghez(Map<String, Object> out, PageDto<ProductsDto> page,
//			Products products, Integer checkedFlag, Integer publishFlag,
//			Integer deletedFlag) {
//		page.setPageSize(5);
//		productsService.queryProductsForList(ProductsService.DELETE_FAILTURE,
//				ProductsService.NOT_PUBLISH_FLAG,
//				ProductsService.CHECKED_WAITING, page);
//		out.put("page", page);
//		out.put("products", products);
//		out.put("checkedFlag", checkedFlag);
//		out.put("publishFlag", publishFlag);
//	}
//
//	@RequestMapping
//	public void shenfail(Map<String, Object> out, PageDto<ProductsDto> page,
//			Products products, Integer checkedFlag, Integer publishFlag,
//			Integer deletedFlag) {
//		page.setPageSize(5);
//		productsService.queryProductsForList(ProductsService.DELETE_FAILTURE,
//				ProductsService.NOT_PUBLISH_FLAG,
//				ProductsService.CHECKED_FAILTURE, page);
//		out.put("page", page);
//		out.put("products", products);
//		out.put("checkedFlag", checkedFlag);
//		out.put("publishFlag", publishFlag);
//	}
//
//	@RequestMapping
//	public void nofabu(Map<String, Object> out, PageDto<ProductsDto> page,
//			Products products, Integer checkedFlag, Integer publishFlag,
//			Integer deletedFlag) {
//		page.setPageSize(5);
//		productsService.queryProductsForList(ProductsService.DELETE_FAILTURE,
//				ProductsService.NOT_PUBLISH_FLAG,
//				ProductsService.CHECKED_SUCCESS, page);
//		out.put("page", page);
//		out.put("products", products);
//		out.put("checkedFlag", checkedFlag);
//		out.put("publishFlag", publishFlag);
//	}

	@RequestMapping
	public ModelAndView xiugai(Map<String, Object> out, Integer id) {
		if (id != null) {
			Products products = productsService.queryById(id);
			out.put("products", products);
			UploadPic uploadPic = uploadPicService.queryUploadPicByTargetId(id);
			out.put("uplaodPic", uploadPic);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView doXiugai(HttpServletRequest request,
			Map<String, Object> out, Products products, Integer id,
			Boolean nfileFlag, Integer pid, Integer cid,
			Integer postlimitetime, Integer deletedFlag, Integer checkedFlag,
			Integer imptFlag, Integer publishFlag, String detailsQuery)
			throws ParseException {
		ExtResult result = new ExtResult();

		products.setId(pid);
		products.setDeletedFlag(deletedFlag);
		products.setCid(2);
		products.setCheckedFlag(checkedFlag);
		products.setImptFlag(imptFlag);
		products.setPublishFlag(publishFlag);
		products.setDetailsQuery(detailsQuery);
		products.setGmtPost(new Date());
		products.setGmtRefresh(new Date());
		products.setGmtExpired(DateUtil.getDate("9999-12-31 23:59:59",
				"yyyy-M-d HH:mm:ss"));

		productsService.editProducts(products, nfileFlag, id);

		Integer i = uploadPicService.editUploadPicById(id, pid,
				UploadPicService.TARGETTYPE_OF_PRODUCTS);

		if (i > 0) {
			result.setSuccess(true);
		}
		return new ModelAndView("redirect:success.htm");
	}

	@RequestMapping
	public ModelAndView batchToDel(Map<String, Object> out,
			HttpServletRequest request, String id) {
		do {
			if (id == null) {
				break;
			}
			out.put("dcount", productsService.deleteMost(id,
					ProductsService.DELETE_SUCCESS));
		} while (false);
		// return new ModelAndView("redirect:" + request.getHeader("referer"));
		return new ModelAndView("forward:guanli.htm");
	}

	@RequestMapping
	public ModelAndView batchToNoPub(Map<String, Object> out,
			HttpServletRequest request, String id) {
		do {
			if (id == null) {
				break;
			}
			out.put("ccount", productsService.updateProductsIsNoPub(id,
					ProductsService.NOT_PUBLISH_FLAG));
		} while (false);
		// return new ModelAndView("redirect:" + request.getHeader("referer"));
		return new ModelAndView("forward:guanli.htm");
	}

	@RequestMapping
	public ModelAndView batchToPub(Map<String, Object> out,
			HttpServletRequest request, String id) {
		do {
			if (id == null) {
				break;
			}
			out.put("ccount", productsService.updateProductsIsNoPub(id,
					ProductsService.PUBLISH_FLAG));
		} while (false);
		// return new ModelAndView("redirect:" + request.getHeader("referer"));
		return new ModelAndView("forward:guanli.htm");
	}

	@RequestMapping
	public void success(Map<String, Object> out) {
		
	}

}
