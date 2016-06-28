package com.ast.ast1949.service.products.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsSpotDao;
import com.ast.ast1949.service.products.ProductsSpotService;
import com.zz91.util.Assert;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("productsSpotService")
public class ProductsSpotServiceImpl implements ProductsSpotService {

	final static Integer AN_HOUR_SECONDS = 5;
	@Resource
	private ProductsSpotDao productsSpotDao;
	@Resource
	private ProductsDAO productsDAO;

	@Override
	public Integer addOneSpot(Integer productId) {
		Assert.notNull(productId, "productId can not be null");
		ProductsSpot productsSpot = productsSpotDao.queryByProductId(productId);
		if (productsSpot != null) {
			return 0;
		}
		ProductsSpot obj = new ProductsSpot();
		obj.setProductId(productId);
		return productsSpotDao.insert(obj);
	}

	@Override
	public Integer removeOneSpot(Integer id) {
		Assert.notNull(id, "delete id can not be null");
		return productsSpotDao.delete(id);
	}

	@Override
	public ProductsSpot queryByProductId(Integer productId) {
		Assert.notNull(productId, "productId can not be null");
		return productsSpotDao.queryByProductId(productId);
	}

	@Override
	public Integer updateIsBailByProductsId(String isBail, Integer productId) {
		return productsSpotDao.updateIsBailByProductsId(isBail, productId);
	}

	@Override
	public Integer updateIsHotByProductsId(String isHot, Integer productId) {
		return productsSpotDao.updateIsHotByProductsId(isHot, productId);
	}

	@Override
	public Integer updateIsTeByProductsId(String isTe, Integer productId) {
		return productsSpotDao.updateIsTeByProductsId(isTe, productId);
	}

	@Override
	public Integer updateIsYouByProductsId(String isYou, Integer productId) {
		return productsSpotDao.updateIsYouByProductsId(isYou, productId);
	}

	@Override
	public void buildBaseData(Map<String, Object> out) throws ParseException {
		// 入驻商家总数:家
		Integer count = 0;
		Set<Integer> idSet = new HashSet<Integer>();
		if (MemcachedUtils.getInstance().getClient().get("zz91_front@spotSJ") != null) {
			count = (Integer) MemcachedUtils.getInstance().getClient().get(
					"zz91_front@spotSJ");
		} else {
			Set<Integer> companySet = new HashSet<Integer>();

			getIDSet(idSet);

			for (Integer productId : idSet) {
				ProductsDO productsDO = productsDAO
						.queryProductsById(productId);
				if (productsDO != null) {
					companySet.add(productsDO.getCompanyId());
				}
			}
			count = companySet.size() + 100;
			MemcachedUtils.getInstance().getClient().set("zz91_front@spotSJ",
					AN_HOUR_SECONDS, count);
		}
		out.put("sj", count);
		// 供应货物总量:吨
		if (MemcachedUtils.getInstance().getClient().get("zz91_front@spotHW") != null) {
			count = (Integer) MemcachedUtils.getInstance().getClient().get(
					"zz91_front@spotHW");
		} else {
			count = 0;
			if (idSet.size() < 1) {
				getIDSet(idSet);
			}
			for (Integer productId : idSet) {
				ProductsDO productsDO = productsDAO
						.queryProductsById(productId);
				if (productsDO != null && productsDO.getQuantity() != null
						&& StringUtils.isNumber(productsDO.getQuantity())
						&& productsDO.getQuantity().length() <= 6) {
					count = count + Integer.valueOf(productsDO.getQuantity());
				}
			}
			MemcachedUtils.getInstance().getClient().set("zz91_front@spotHW",
					AN_HOUR_SECONDS, count);
		}
		count = count / 10000;
		out.put("hw", Math.abs(count));
		// 现存货物价值:万
		if (MemcachedUtils.getInstance().getClient().get("zz91_front@spotHJZ") != null) {
			count = (Integer) MemcachedUtils.getInstance().getClient().get(
					"zz91_front@spotHJZ");
		} else {
			count = 0;
			if (idSet.size() < 1) {
				getIDSet(idSet);
			}
			for (Integer productId : idSet) {
				ProductsDO productsDO = productsDAO
						.queryProductsById(productId);
				if (productsDO != null) {
					Integer i = 0;
					if (StringUtils.isNumber(productsDO.getQuantity())
							&& productsDO.getQuantity().length() <= 6) {
						i = Integer.valueOf(productsDO.getQuantity());
					}
					if (productsDO.getMinPrice() != null
							&& productsDO.getMinPrice() > 0) {
						Float f = null;
						if (productsDO.getMaxPrice() != null
								&& productsDO.getMaxPrice() > 0) {
							f = (productsDO.getMinPrice() + productsDO
									.getMaxPrice()) / 2;
						} else {
							f = productsDO.getMinPrice();
						}
						count = count + i * f.intValue();
					}
					if (StringUtils.isNumber(productsDO.getQuantity())
							&& productsDO.getQuantity().length() <= 6) {
						count = count
								+ Integer.valueOf(productsDO.getQuantity());
					}
				}
			}
			count = count / 100000000;
			MemcachedUtils.getInstance().getClient().set("zz91_front@spotHJZ",
					AN_HOUR_SECONDS, count);
		}
		out.put("hjz", Math.abs(count));
		// 实时访客数量:8000
		if (MemcachedUtils.getInstance().getClient().get("zz91_front@spotFK") != null) {
			count = (Integer) MemcachedUtils.getInstance().getClient().get(
					"zz91_front@spotFK");
		} else {
			count = 0;
			Random random = new Random();
			count = random.nextInt(200) + 100;
			MemcachedUtils.getInstance().getClient().set("zz91_front@spotFK",
					AN_HOUR_SECONDS, count);
		}
		out.put("fk", count);
		// 历史访问人数
		Integer history = 0;
		if (MemcachedUtils.getInstance().getClient().get("zz91_front@spotHis") != null) {
			history = (Integer) MemcachedUtils.getInstance().getClient().get(
					"zz91_front@spotHis");
		} else {
			// 基础数据
			history = 12151;
			Date start = DateUtil.getDate("2012-12-10", "yyyy-MM-dd");
			Date from = DateUtil.getDate(new Date(), "yyyy-MM-dd");
			Date today = new Date();
			int i = DateUtil.getIntervalDays(today, start);
			// 每日叠加 79人
			i = i * 2509;
			// 每一个小时增加0-100个人(随机数)
			int hour = (int) ((today.getTime() - from.getTime()) / (1000 * 60 * 60));
			// 超过一天，去掉24个小时
			if (hour > 24) {
				hour = hour % 24;
			}
			int hourTotal = 0;
			for (int j = 0; i < hour; j++) {
				Random random = new Random();
				hourTotal = hourTotal + random.nextInt(100);
			}
			history = history + i + hourTotal;
			MemcachedUtils.getInstance().getClient().set("zz91_front@spotHis",
					AN_HOUR_SECONDS, history);
		}
		out.put("his", history);
	}

	private void getIDSet(Set<Integer> idSet) {
		Integer size = 10;
		Integer start = 0;
		do {
			List<ProductsSpot> list = productsSpotDao.querySpot(start, size);
			if (list == null || list.size() == 0) {
				break;
			}
			for (ProductsSpot obj : list) {
				idSet.add(obj.getProductId());
			}
			start = start + size;
		} while (true);
	}

	@Override
	public ProductsSpot queryById(Integer id) {
		return productsSpotDao.queryById(id);
	}

	@Override
	public Integer updateViewCountById(Integer id) {
		Assert.notNull(id, "id must not be null");
		return productsSpotDao.updateViewCountById(id);
	}

	@Override
	public Integer queryViewCountById(Integer id) {
		Assert.notNull(id, "id must not be null");
		return productsSpotDao.queryViewCountById(id);
	}

}
