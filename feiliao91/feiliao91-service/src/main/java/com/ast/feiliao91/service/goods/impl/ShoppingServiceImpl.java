/**
 * @author shiqp
 * @date 2016-01-31
 */
package com.ast.feiliao91.service.goods.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanyService;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.domain.goods.Shopping;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.ShoppingBuyDto;
import com.ast.feiliao91.dto.goods.ShoppingDto;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.persist.goods.PictureDao;
import com.ast.feiliao91.persist.goods.ShoppingDao;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.ast.feiliao91.service.goods.PictureService;
import com.ast.feiliao91.service.goods.ShoppingService;
import com.zz91.util.lang.StringUtils;

@Component("shoppingService")
public class ShoppingServiceImpl implements ShoppingService {
	@Resource
	private ShoppingDao shoppingDao;
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private CompanyServiceDao companyServiceDao;
	@Resource
	private PictureService pictureService;
	@Resource
	private PictureDao pictureDao;

	@Override
	public PageDto<ShoppingDto> pageShopping(PageDto<ShoppingDto> page, Integer buyCompanyId) {
		// 结果列表
		List<ShoppingDto> resultList = new ArrayList<ShoppingDto>();
		// 卖方公司集
		List<Integer> clist = shoppingDao.querySellCompanyId(page, buyCompanyId);
		for (Integer in : clist) {
			ShoppingDto dto = new ShoppingDto();
			// 卖方公司信息
			CompanyInfo info = companyInfoDao.queryById(in);
			dto.setInfo(info);

			Integer i = companyServiceDao.queryServiceCount(in, CompanyServiceService.SEVEN_DAY_SERVICE);
			if (i > 0) {
				dto.setSevenDayFlag(1);
			}
			Integer j = companyServiceDao.queryServiceCount(in, CompanyServiceService.BZJ_SERVICE);
			if (j > 0) {
				dto.setBzjFlag(1);
			}
			// 购物单集合
			List<Shopping> slist = shoppingDao.queryShoppingByBothId(in, buyCompanyId, null);
			dto.setList(slist);
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(shoppingDao.countSellCompanyId(buyCompanyId));
		return page;
	}

	@Override
	public Integer createShopping(Shopping shopping) {
		Integer flag = 0;
		float quality = Float.valueOf(shopping.getNumber());
		// 之前是否已经存在
		List<Shopping> list = shoppingDao.queryShoppingByBothId(null, shopping.getBuyCompanyId(), shopping.getGoodId());
		// 产品信息
		Goods good = goodsDao.queryGoodById(shopping.getGoodId());
		if (good != null) {
			shopping.setTitle(good.getTitle());
			shopping.setPrice(good.getPrice());
			shopping.setSellCompanyId(good.getCompanyId());
			shopping.setHasTax(good.getHasTax());
			quality = Float.valueOf(good.getQuantity()) - quality;
		}
		// 服务信息
		String cserver = "";
		List<CompanyService> slist = companyServiceDao.queryCompanyServiceListByCompanyId(shopping.getBuyCompanyId(),
				10);
		for (CompanyService cs : slist) {
			cserver = cserver + cs.getServiceCode() + ",";
		}
		if (StringUtils.isNotEmpty(cserver)) {
			shopping.setServiceCodeList(cserver.substring(0, cserver.length() - 1));
		}
		// 图片信息
		// 封面图片
		List<Picture> plist = pictureService.queryPictureByCondition(shopping.getGoodId(), PictureService.TYPE_GOOD,
				null, 1);
		if (plist != null && plist.size() > 0) {
			shopping.setPicAddress(plist.get(0).getPicAddress());
		}
		// 购物单总金额
		shopping.setMoney(getMoney(shopping.getNumber(), shopping.getPrice()));
		for (Shopping shop : list) {
			// 如果存在
			if (hasShopping(shop, shopping)) {
				shopping.setNumber(getNumberOrMoney(shop.getNumber(), shopping.getNumber()));
				shopping.setMoney(getNumberOrMoney(shop.getMoney(), shopping.getMoney()));
				// 更新购物单
				flag = shoppingDao.updateShoppingInfo(shop.getId(), null, shopping.getNumber(), shopping.getMoney());
			}
		}
		if (flag < 1) {
			// 创建购物单
			flag = shoppingDao.insertShoppingMenu(shopping);
		}
		if (flag > 0) {
			Goods gds = new Goods();
			gds.setId(good.getId());
			DecimalFormat decimalFormat = new DecimalFormat(".00");
			gds.setQuantity(decimalFormat.format(quality));
			goodsDao.updateGoods(gds);
		}
		return flag;
	}

	public String getNumberOrMoney(String olddate, String newdate) {
		try {
			float data = Float.valueOf(olddate) + Float.valueOf(newdate);
			DecimalFormat decimalFormat = new DecimalFormat(".00");
			return decimalFormat.format(data);
		} catch (Exception e) {
			return "";
		}
	}

	public String getMoney(String num, String price) {
		try {
			float data = Float.valueOf(price) * Float.valueOf(num);
			DecimalFormat decimalFormat = new DecimalFormat(".00");
			return decimalFormat.format(data);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 有无相同的订单 true表示有，false表示没有
	 * 
	 * @param oldShop
	 * @param newShop
	 * @return
	 */
	public boolean hasShopping(Shopping oldShop, Shopping newShop) {
		// 标题是否一致
		if (!oldShop.getTitle().equals(newShop.getTitle())) {
			return false;
		}
		// 属性是否一致
		if (!oldShop.getAttributes().equals(newShop.getAttributes())) {
			return false;
		}
		// 服务是否一致
		if (!(oldShop.getServiceCodeList() == newShop.getServiceCodeList())) {
			return false;
		}
		// 价格是否一致
		if (!oldShop.getPrice().equals(newShop.getPrice())) {
			return false;
		}
		// 封面是否一致
		if (!(oldShop.getPicAddress() == newShop.getPicAddress())) {
			return false;
		}
		return true;
	}

	@Override
	public Integer countShoppingByCondition(Integer buyCompanyId, Integer sellCompanyId, Integer goodId) {
		return shoppingDao.countShoppingByCondition(buyCompanyId, sellCompanyId, goodId);
	}

	@Override
	public Integer deleteShopping(String idString) {
		Integer flag = 0;
		String[] string = idString.split(",");
		for (String s : string) {
			flag = shoppingDao.updateIsDel(Integer.valueOf(s), 1);
		}
		return flag;
	}

	@Override
	public List<ShoppingDto> queryShopping(String ids) {
		if (StringUtils.isEmpty(ids)) {
			return null;
		}
		Integer[] idArray = StringUtils.StringToIntegerArray(ids);
		List<ShoppingDto> list = new ArrayList<ShoppingDto>();

		Map<Integer, Goods> goodsMap = new HashMap<Integer, Goods>(); // 商品id
		Map<Integer, CompanyInfo> companyMap = new HashMap<Integer, CompanyInfo>(); // 公司map，按时间顺序
		Map<Integer, List<ShoppingBuyDto>> tempListMap = new HashMap<Integer, List<ShoppingBuyDto>>(); // 购物单map
		for (Integer id : idArray) {
			ShoppingBuyDto buyDto = new ShoppingBuyDto();
			// 购物车信息
			Shopping shopping = shoppingDao.queryById(id);
			if (shopping == null) {
				continue;
			}
			// 购物单集合
			List<ShoppingBuyDto> dtoList = tempListMap.get(shopping.getSellCompanyId());
			if (dtoList == null) {
				dtoList = new ArrayList<ShoppingBuyDto>();
			}

			buyDto.setShopping(shopping);
			// 商品信息
			Goods goods = goodsMap.get(shopping.getGoodId());
			if (goods == null) {
				goods = goodsDao.queryById(shopping.getGoodId());
			}
			if (goods == null) {
				continue;
			}
			String fare=goods.getFare();
			if(fare==null){
				fare="-1";
			}
			if("-1".equals(goods.getFare())){
				fare="0";
			}
			buyDto.setFare(Integer.valueOf(fare));
			goodsMap.put(shopping.getGoodId(), goods);
			buyDto.setGoods(goods);
			// 封面图片
			List<Picture> picList = pictureDao.queryPictureByCondition(goods.getId(), PictureService.TYPE_GOOD,
					goods.getCompanyId(), 1);
			if (picList != null && picList.size() > 0) {
				buyDto.setPicAddress(picList.get(0).getPicAddress());
			}
			dtoList.add(buyDto); // 列表封装

			if (companyMap.get(shopping.getSellCompanyId()) == null) {
				// 卖方公司信息
				CompanyInfo info = companyInfoDao.queryById(shopping.getSellCompanyId());
				if (info == null) {
					continue;
				}
				companyMap.put(info.getId(), info);
			}
			tempListMap.put(shopping.getSellCompanyId(), dtoList);
		}
		// 组装公司信息
		for (Integer cid : companyMap.keySet()) {
			ShoppingDto dto = new ShoppingDto();
			CompanyInfo info = companyMap.get(cid);
			if (info == null) {
				continue;
			}
			dto.setInfo(info);
			List<ShoppingBuyDto> dtoList = tempListMap.get(cid);
			if (dtoList == null) {
				continue;
			}
			// 检索服务 7天包退 保证金
			Integer i = companyServiceDao.queryServiceCount(cid, CompanyServiceService.SEVEN_DAY_SERVICE);
			if (i > 0) {
				dto.setSevenDayFlag(1);
			}
			Integer j = companyServiceDao.queryServiceCount(cid, CompanyServiceService.BZJ_SERVICE);
			if (j > 0) {
				dto.setBzjFlag(1);
			}

			dto.setDtoList(dtoList);
			list.add(dto);
		}

		return list;
	}

}
