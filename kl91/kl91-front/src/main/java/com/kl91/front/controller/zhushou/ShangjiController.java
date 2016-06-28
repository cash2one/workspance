package com.kl91.front.controller.zhushou;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.auth.Kl91SsoUser;
import com.kl91.domain.company.UploadPic;
import com.kl91.domain.dto.ExtResult;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.dto.products.ProductsSolrDto;
import com.kl91.domain.products.Products;
import com.kl91.front.controller.BaseController;
import com.kl91.service.company.UploadPicService;
import com.kl91.service.products.ProductsService;
import com.kl91.service.products.ProductsSolrService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;

@Controller
public class ShangjiController extends BaseController {

	@Resource
	private ProductsService productsService;
	@Resource
	private UploadPicService uploadPicService;
	@Resource
	private ProductsSolrService productsSolrService;

	@RequestMapping
	public void fabu(Map<String, Object> out, UploadPic uploadPic) {
		out.put("uploadPic", uploadPic);
	}

	@RequestMapping
	public ModelAndView doFabu(HttpServletRequest request,
			Map<String, Object> out, Products products, UploadPic pic,
			String filePath, Integer targetType, Integer targetId, Integer cid,
			Integer id) throws Exception {
		ExtResult result = new ExtResult();

		Kl91SsoUser sessionUser = getSessionUser(request);
		if (sessionUser == null) {
			result.setData("sessionTimeOut");
		}
		products.setDeletedFlag(0);
		products.setCheckedFlag(1);
		products.setImptFlag(1);
		products.setPublishFlag(1);
		products.setNumInquiry(0);
		products.setNumView(0);
		products.setDetailsQuery("");
		products.setGmtPost(new Date());
		products.setGmtRefresh(new Date());
		if (products.getDayExpired() == -1) {
			products.setGmtExpired(DateUtil.getDate("9999-12-31 23:59:59",
					"yyyy-M-d HH:mm:ss"));
		} else {
			products.setGmtExpired(DateUtil.getDateAfterDays(new Date(),
					products.getDayExpired()));
		}
		Integer data = productsService.createProducts(products, id);
		if (data <= 0) {
			result.setData("failureInsert");
		}
		Integer i = uploadPicService.editUploadPicById(id, data,
				UploadPicService.TARGETTYPE_OF_PRODUCTS);
		if (i > 0) {
			result.setSuccess(true);
		}
		return new ModelAndView("redirect:success.htm");
	}

	@RequestMapping
	public void success(Map<String, Object> out,ProductsSearchDto searchDto,PageDto<ProductsSolrDto> page) {
			
		SeoUtil.getInstance().buildSeo("products", out);
		page.setPageSize(5);
		out.put("list1", productsSolrService.querySolrProductsByTypeCode(searchDto, page, ProductsService.BUY_TYPE_CODE));
		out.put("list2", productsSolrService.querySolrProductsByTypeCode(searchDto, page, ProductsService.SELL_TYPE_CODE));
		out.put("page", page);

	}

}
