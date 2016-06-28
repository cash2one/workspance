/**
 * 
 */
package com.ast.ast1949.myrc.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.myrc.util.FrontConst;

/**
 * @author yuyh Email: afterhui@gmail.com
 * 
 */
@Controller
public class BizexpressController extends BaseController {

	//@Autowired
	//private ProductsService productsService;

	/**
	 * 一个用户只有一个买家必看的信息（即一个定制的供求商机，只能后台管理设定，用户自己不能设置）
	 * @param page
	 * @param subscribeId
	 * @param keywords
	 * @param request
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public void list(String subscribeId, String keywords, HttpServletRequest request,
			Map<String, Object> out) throws UnsupportedEncodingException {
		out.put(FrontConst.MYRC_SUBTITLE, "买家必看");
		//买家必看
//		SsoUser sessionUser = getCachedUser(request);
//		CompanyAccount myaccount = companyAccountService.queryAccountByAccount(sessionUser.getAccount());
//		out.put("myaccount", myaccount);
//		keywords = StringUtils.decryptUrlParameter(StringUtils.getNotNullValue(keywords));
//		if (!StringUtils.isNumber(subscribeId) || StringUtils.isEmpty(keywords)) {
//		
//			SubscribeDO subscribeDO = subscribeService
//					.queryKeywordsByAccount(sessionUser.getAccount());
//			
//			if (subscribeDO == null) {
//				return;
//			}
//			//out.put("subscribe", subscribeDO);
//			subscribeId = subscribeDO.getId().toString();
//			keywords = StringUtils.getNotNullValue(subscribeDO.getKeywords());
//			
//		}
//		out.put("subscribeId", subscribeId);
//		out.put("keywords", keywords);
//		out.put("suffixUrl", "subcribeId=" + subscribeId + "&keywords=" + keywords);
//		page.setPageSize(10);
//		ProductsListItemForFrontDTO searchDto = new ProductsListItemForFrontDTO();
//		searchDto.setKeywords(keywords);
////		page = productSearchService.search(searchDto, page);
////		for (Object obj : page.getRecords()) {
////			ProductsListItemForFrontDTO productsListItemForFront = (ProductsListItemForFrontDTO) obj;
////			ParseAreaCode parser = new ParseAreaCode();
////			parser.parseAreaCode(productsListItemForFront.getAreaCode());
////			productsListItemForFront.setProvince(parser.getProvince());
////			productsListItemForFront.setArea(parser.getCity());
////		}
//		out.put("stringUtils", new StringUtils());
//		//page = productsService.pageProductsAndCompanyByTtitle(subscribeDO.getKeywords(), page);
//		out.put("totalPage", page.getTotalRecords());
//		out.put("page", page);
//		out.put("results", page.getRecords());
		
	}

}
