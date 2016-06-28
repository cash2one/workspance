/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 上午09:16:01
 */
package com.ast1949.shebei.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import com.ast1949.shebei.dao.CompanyDao;
import com.ast1949.shebei.dao.NewsDao;
import com.ast1949.shebei.dao.ProductsDao;
import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.domain.News;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.data.CompanyMakeMap;
import com.ast1949.shebei.dto.data.NewsMakeMap;
import com.ast1949.shebei.dto.data.ProductsMakeMap;
import com.ast1949.shebei.service.DataGatherService;
import com.zz91.util.datetime.DateUtil;

@Component("dataGatherService")
public class DataGatherServiceImpl implements DataGatherService {

	@Resource
	private  NewsDao newsDao;
	@Resource
	private ProductsDao productsDao;
	@Resource
	private CompanyDao companyDao;
	
	public long buildShowTime(long seed){
		
		long now_zero= DateUtil.getTheDayZero(new Date(seed), 0);
		
		long nowtime=seed-(now_zero*1000);
		
			//6
			if(nowtime <= 21600000){
				return seed+2143;
			}
			//9
			if(nowtime <= 32400000){
				return seed+1799;
			}
			//11
			if(nowtime <= 39600000){
				return seed+411;
			}
			//13
			if(nowtime <= 46800000){
				return seed+1799;
			}
			//17
			if(nowtime <= 61200000){
				return seed+411;
			}
			//19
			if(nowtime <= 68400000){
				return seed+1799;
			}
			//21
			if(nowtime <= 75600000){
				return seed+411;
			}
			//24
			return seed+1799;
			
	}
	@Override
	public long createNews(NewsMakeMap newsMap, HSSFRow row, long seed) {
		do {
			News news = new News();

//			10:00:00->10->410毫秒 = 1s
//			
//			showtime=10000000000000 + 1000
			
			if (newsMap.getCategoryCode() != null) {
				news.setCategoryCode(newsMap.getCategoryCode());
			} else {
				break;
			}

			if (newsMap.getTitle() != null) {
				if (row.getCell(newsMap.getTitle()) != null) {
					String title = row.getCell(newsMap.getTitle())
							.getRichStringCellValue().getString();
					title = Jsoup.clean(title, Whitelist.none());
					news.setTitle(title);
				}
			} else {
				news.setTitle("");
			}
			
			if(newsMap.getDescription()!=null){
				if (row.getCell(newsMap.getDescription()) != null) {
					String description = row.getCell(newsMap.getDescription())
							.getRichStringCellValue().getString();
					news.setDescription(description);
				}
			}
			
			if (newsMap.getType()!=null){
				news.setType(newsMap.getType().shortValue());
			}
			if (newsMap.getDetails()!=null){
				if (row.getCell(newsMap.getDetails()) != null) {
					String details = row.getCell(newsMap.getDetails())
							.getRichStringCellValue().getString();
					news.setDetails(details);
					details = Jsoup.clean(details, Whitelist.none());
					if (details.length()>1000){
						details=details.substring(0, 990);
					}
					news.setDetailsQuery(details);
				}
			}
			
			if (newsMap.getTags()!=null){
				if (row.getCell(newsMap.getTags()) != null) {
					String tags = row.getCell(newsMap.getTags())
							.getRichStringCellValue().getString();
					news.setTags(tags);
				}
			}

			if (newsMap.getSource()!=null){
				if (row.getCell(newsMap.getSource()) != null) {
					String source = row.getCell(newsMap.getSource())
							.getRichStringCellValue().getString();
					news.setSource(source);
				}
			}
			
			if (newsMap.getSourceUrl()!=null){
				if (row.getCell(newsMap.getSourceUrl()) != null) {
					String sourceUrl = row.getCell(newsMap.getSourceUrl())
							.getRichStringCellValue().getString();
					news.setSourceUrl(sourceUrl);
				}
			}
			
			if (newsMap.getAuth()!=null){
				if (row.getCell(newsMap.getAuth()) != null) {
					String auth = row.getCell(newsMap.getAuth())
							.getRichStringCellValue().getString();
					news.setAuth(auth);
				}
			}
			
			Date pubdate=null;
			if (newsMap.getGmtPublish()!=null){
				if (row.getCell(newsMap.getGmtPublish())!=null){
					try {
						pubdate=row.getCell(newsMap.getGmtPublish()).getDateCellValue();
					} catch (Exception e) {
						String str=row.getCell(newsMap.getGmtPublish()).getRichStringCellValue().getString().replace("'", "");
						try {
							pubdate=DateUtil.getDate(str, "yyyy-MM-dd");
						} catch (ParseException e1) {
							try {
								pubdate=DateUtil.getDate(new Date(), "yyyy-MM-dd");
							} catch (ParseException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}
			
			//200-300随机数
//			int  random =(int)(Math.round(Math.random()*100)+200);
			
			news.setGmtPublish(pubdate);
			news.setViewCount(0);
			
			if (news == null) {
				break;
			}

			seed=buildShowTime(seed);
			news.setGmtShow(new Date(seed));
			
			Integer id = null;
			try {
				id = newsDao.insertNews(news);
			} catch (DataAccessException e) {
//				e.printStackTrace();
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}
			
			return seed;
		} while (false);
		return 0;
	}
	
	@Override
	public Date queryNewsMaxGmtShow() {
		return newsDao.queryMaxGmtShow();
	}
	@Override
	public Date queryProductsMaxGmtShow() {
		return productsDao.queryMaxGmtShow();
	}
	@Override
	public Date queryCompanyMaxGetShow() {
		return null;
	}
	@Override
	public long createProducts(ProductsMakeMap productsMap,Integer cid, HSSFRow row,
			long seed) {
		do {
			Products product = new Products();

			product.setCid(cid);
			if (productsMap.getCategoryCode() != null) {
				product.setCategoryCode(productsMap.getCategoryCode());
			} else {
				break;
			}

			if (productsMap.getProductType() != null) {
				product.setProductsType(productsMap.getProductType());
			} else {
				break;
			}
			
			if (productsMap.getTitle() != null) {
				if (row.getCell(productsMap.getTitle()) != null) {
					String title = row.getCell(productsMap.getTitle())
							.getRichStringCellValue().getString();
					title = Jsoup.clean(title, Whitelist.none());
					product.setTitle(title);
				}
			} else {
				product.setTitle("");
			}
			
			if (productsMap.getDetails()!=null){
				if (row.getCell(productsMap.getDetails()) != null) {
					String details = row.getCell(productsMap.getDetails())
							.getRichStringCellValue().getString();
					product.setDetails(details);
				}
			}
			
			if(productsMap.getPrice()!=null){
				if (row.getCell(productsMap.getPrice()) != null) {
					String price = row.getCell(productsMap.getPrice())
							.getRichStringCellValue().getString();
					product.setPrice(price);
				}
			}
			
			if(productsMap.getPriceMax()!=null){
				if (row.getCell(productsMap.getPriceMax()) != null) {
					String priceMax = row.getCell(productsMap.getPriceMax())
							.getRichStringCellValue().getString();
					product.setPriceMax(priceMax);
				}
			}
			
			if(productsMap.getPriceUnit()!=null){
				if (row.getCell(productsMap.getPriceUnit()) != null) {
					String priceUnit = row.getCell(productsMap.getPriceUnit())
							.getRichStringCellValue().getString();
					product.setPriceUnit(priceUnit);
				}
			}
			
			if(productsMap.getQuantity()!=null){
				if (row.getCell(productsMap.getQuantity()) != null) {
					String quantity = row.getCell(productsMap.getQuantity())
							.getRichStringCellValue().getString();
					product.setQuantity(quantity);
				}
			}
			
			if(productsMap.getQuantityUnit()!=null){
				if (row.getCell(productsMap.getQuantityUnit()) != null) {
					String quantityUnit = row.getCell(productsMap.getQuantityUnit())
							.getRichStringCellValue().getString();
					product.setQuantityUnit(quantityUnit);
				}
			}
			
			if(productsMap.getSource()!=null){
				if (row.getCell(productsMap.getSource()) != null) {
					String source = row.getCell(productsMap.getSource())
							.getRichStringCellValue().getString();
					product.setSource(source);
				}
			}
		
			if (productsMap.getTags()!=null){
				if (row.getCell(productsMap.getTags()) != null) {
					String tags = row.getCell(productsMap.getTags())
							.getRichStringCellValue().getString();
					product.setTags(tags);
				}
			}
			
			if (productsMap.getArea()!=null){
				if (row.getCell(productsMap.getArea()) != null) {
					String area = row.getCell(productsMap.getArea())
							.getRichStringCellValue().getString();
					product.setArea(area);
				}
			}
			
			if (productsMap.getQuality()!=null){
				if (row.getCell(productsMap.getQuality()) != null) {
					String quality = row.getCell(productsMap.getQuality())
							.getRichStringCellValue().getString();
					product.setQuality(quality);
				}
			}
			
			Date pubdate=null;
			if (productsMap.getGmtPublish()!=null){
				if (row.getCell(productsMap.getGmtPublish())!=null){
					try {
						pubdate=row.getCell(productsMap.getGmtPublish()).getDateCellValue();
					} catch (Exception e) {
						String str=row.getCell(productsMap.getGmtPublish()).getRichStringCellValue().getString().replace("'", "");
						try {
							pubdate=DateUtil.getDate(str, "yyyy-MM-dd");
						} catch (ParseException e1) {
							try {
								pubdate=DateUtil.getDate(new Date(), "yyyy-MM-dd");
							} catch (ParseException e2) {
							}
						}
					}
				}
			}
			
			Date expired=null;
			if (productsMap.getGmtExpired()!=null){
				if (row.getCell(productsMap.getGmtExpired())!=null){
					try {
						expired=row.getCell(productsMap.getGmtExpired()).getDateCellValue();
					} catch (Exception e) {
						String str=row.getCell(productsMap.getGmtExpired()).getRichStringCellValue().getString().replace("'", "");
						try {
							expired=DateUtil.getDate(str, "yyyy-MM-dd");
						} catch (ParseException e1) {
							try {
								expired=DateUtil.getDate(new Date(), "yyyy-MM-dd");
							} catch (ParseException e2) {
							}
						}
					}
				}
			}
			product.setGmtExpired(expired);
			product.setGmtPublish(pubdate);
			
			if (expired!=null && pubdate!=null){
				try {
					Integer day=DateUtil.getIntervalDays(expired, pubdate);
					product.setExpiredDay(day.toString());
				} catch (ParseException e) {
				}
			}
		
			if (product == null) {
				break;
			}

			seed=buildShowTime(seed);
			product.setGmtShow(new Date(seed));
			
			Integer id = null;
			try {
				id = productsDao.insertProducts(product);
			} catch (DataAccessException e) {
//				e.printStackTrace();
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}
			
			return seed;
		} while (false);
		return 0;
	}
	
	@Override
	public long createCompanys(CompanyMakeMap compMap, String account, HSSFRow row, long seed) {
		do {
			Integer cid = companyDao.queryCompIdByAccount(account);
			
			if (cid!=null && cid>0){
				continue;
			}
			
			Company company = new Company();
			company.setAccount(account);

			if (compMap.getCategoryCode() != null) {
				company.setCategoryCode(compMap.getCategoryCode());
			} else {
				break;
			}
			
			if (compMap.getName()!=null){
				if (row.getCell(compMap.getName()) != null) {
					String name = row.getCell(compMap.getName())
							.getRichStringCellValue().getString();
					company.setName(name);
				}
			}
			
			if (compMap.getDetails()!=null){
				if (row.getCell(compMap.getDetails()) != null) {
					String details = row.getCell(compMap.getDetails())
							.getRichStringCellValue().getString();
					company.setDetails(details);
				}
			}
			
			if (compMap.getMainBuy()!=null){
				company.setMainBuy(compMap.getMainBuy());
			}else {
				company.setMainBuy((short)0);
			}
			
			if (compMap.getMainSupply()!=null){
				company.setMainSupply(compMap.getMainSupply());
			}else {
				company.setMainSupply((short)0);
			}
			
			if(compMap.getMainProductBuy()!=null){
				if (row.getCell(compMap.getMainProductBuy()) != null) {
					String mainProductBuy = row.getCell(compMap.getMainProductBuy())
							.getRichStringCellValue().getString();
					company.setMainProductBuy(mainProductBuy);
				}
			}
			
			if(compMap.getMainProductSupply()!=null){
				if (row.getCell(compMap.getMainProductSupply()) != null) {
					String mainProductSupply = row.getCell(compMap.getMainProductSupply())
							.getRichStringCellValue().getString();
					company.setMainProductSupply(mainProductSupply);
				}
			}
			
			if(compMap.getContact()!=null){
				if (row.getCell(compMap.getContact()) != null) {
					String contact = row.getCell(compMap.getContact())
							.getRichStringCellValue().getString();
					company.setContact(contact);
				}
			}else {
				company.setContact("");
			}
			
			if (compMap.getSex() != null) {
				company.setSex(compMap.getSex());
			}
			
			if (compMap.getMobile() != null) {
				if(row.getCell(compMap.getMobile())!=null){
					try {
						Double d=row.getCell(compMap.getMobile()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						company.setMobile(format.format(d));
					} catch (Exception e) {
						company.setMobile(String.valueOf(row.getCell(compMap.getMobile()).getRichStringCellValue()));
					}
				}
			}
			
			if (compMap.getPhone() != null) {
				if (row.getCell(compMap.getPhone())!=null){
					try {
						Double d=row.getCell(compMap.getPhone()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						company.setPhone(format.format(d));
					} catch (Exception e) {
						company.setPhone(String.valueOf(row.getCell(compMap.getPhone()).getRichStringCellValue()));
					}
				}
			}

			if (compMap.getFax() != null) {
				if (row.getCell(compMap.getFax())!=null){
					try {
						Double d=row.getCell(compMap.getFax()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						company.setFax(format.format(d));
					} catch (Exception e) {
						company.setFax(String.valueOf(row.getCell(compMap.getFax()).getRichStringCellValue()));
					}
				}
			}
			
			if(compMap.getAddress()!=null){
				if (row.getCell(compMap.getAddress()) != null) {
					String address = row.getCell(compMap.getAddress())
							.getRichStringCellValue().getString();
					company.setAddress(address);
				}
			}
			
			if(compMap.getAddressZip()!=null){
				if (row.getCell(compMap.getAddressZip()) != null) {
					String addressZip = row.getCell(compMap.getAddressZip())
							.getRichStringCellValue().getString();
					company.setAddressZip(addressZip);
				}
			}
		
			if (company == null) {
				break;
			}

			seed=buildShowTime(seed);
			company.setGmtShow(new Date(seed));
			
			Integer id = null;
			try {
				id = companyDao.insertCompany(company);
			} catch (DataAccessException e) {
//					e.printStackTrace();
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}
			
			return seed;
		} while (false);
		return 0;
	}

}
