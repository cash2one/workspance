/**
 * @author shiqp
 * @date 2016-01-08
 */
package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.ShoppingDto;
import com.ast.feiliao91.service.goods.ShoppingService;
import com.zz91.util.seo.SeoUtil;

@Controller
public class RootController extends BaseController {
	@Resource
	private ShoppingService shoppingService;

	@RequestMapping
	public void index(HttpServletRequest request, Map<String, Object> out, Integer index) {
		if (index == null) {
			index = 1;
		}
		// 获取登录信息
		SsoUser user = getCachedUser(request);
		PageDto<ShoppingDto> page = new PageDto<ShoppingDto>();
		page.setPageSize(10);
		page.setSort("gmt_created");
		page.setDir("desc");
		page.setStartIndex((index - 1) * page.getPageSize());
		page = shoppingService.pageShopping(page, user.getCompanyId());
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("shop_car",out);
	}
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request, Map<String,Object> out, String idString) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		do{
			SsoUser user = getCachedUser(request);
			if(user== null){
				break;
			}
			Integer flag = shoppingService.deleteShopping(idString);
			map.put("flag", flag);
		}while(false);
		return printJson(map, out);
	}
}
