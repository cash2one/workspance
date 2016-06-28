package com.zz91.ep.admin.service.sys.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.admin.dao.comp.CompAccountDao;
import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.dao.news.NewsDao;
import com.zz91.ep.admin.dao.sys.SysAreaDao;
import com.zz91.ep.admin.dao.trade.PhotoDao;
import com.zz91.ep.admin.dao.trade.TradeBuyDao;
import com.zz91.ep.admin.dao.trade.TradeCategoryDao;
import com.zz91.ep.admin.dao.trade.TradeSupplyDao;
import com.zz91.ep.admin.service.sys.DataGatherService;
import com.zz91.ep.admin.service.trade.IbdCompanyService;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.trade.IbdCompany;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.data.CompAccountMakeMap;
import com.zz91.ep.dto.data.CompMakeMap;
import com.zz91.ep.dto.data.IbdCompanyMakeMap;
import com.zz91.ep.dto.data.NewsMakeMap;
import com.zz91.ep.dto.data.TradeBuyMakeMap;
import com.zz91.ep.dto.data.TradeSupplyMakeMap;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.RandomUtils;
import com.zz91.util.lang.StringUtils;

@Service("dataGatherService")
public class DataGatherServiceImpl implements DataGatherService {

	@Resource
	private CompProfileDao compProfileDao;
	@Resource
	private CompAccountDao compAccountDao;
	@Resource
	private SysAreaDao sysAreaDao;
	@Resource
	private TradeCategoryDao tradeCategoryDao;
	@Resource
	private TradeBuyDao tradeBuyDao;
	@Resource
	private TradeSupplyDao tradeSupplyDao;
	@Resource
	private PhotoDao photoDao;
	@Resource
	private NewsDao newsDao;
	@Resource
	private IbdCompanyService ibdCompanyService;

	final static short LOGIC_TRUE = 1;

	final static String PWD_ENCODE = "2f7a5c8555c107af";
	final static String PWD_CLEAR = "135246";
	final static String EMAIL_DOMAIN = "@huanbao.com";

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer saveOrUpdateCompProfile(CompProfile compProfile,
			CompAccount compAccount) {
		boolean flag = false;
		String companyName = compProfile.getName();
		// CompProfile returnCompProfile =
		// compProfileDao.queryCompProfileByName(companyName);
		Integer cid = compProfileDao.queryCidByName(companyName);
		String defaultPassword = "123456";
		if (cid != null && !"".equals(cid)) {
			compProfile = compProfileDao.queryCompProfileByName(companyName);
			compProfile.setGmtModified(new Date());
			// compProfile.set 更新一些需要更新的字段
			if (compProfileDao.updateCompProfile(compProfile) > 0)
				flag = true;
		} else {
			compProfile.setGmtCreated(new Date());
			compProfile.setMainBuy((short) 0);
			compProfile.setMainSupply((short) 0);
			compProfile.setMemberCode("");
			compProfile.setMemberCodeBlock("");
			compProfile.setMessageCount(1);
			compProfile.setViewCount(1);
			compProfile.setGmtModified(new Date());
			// 保存一些还需要的字段
			compProfileDao.insertCompProfile(compProfile);
			// if(insertId>0) {
			// 随机生成一个公司所属账户
			StringBuffer account = new StringBuffer("ep_");
			account.append(RandomUtils.buildRandom(8));
			// CompAccount compAccount = new CompAccount();
			compAccount.setAccount(account.toString());
			cid = compProfileDao.queryCidByName(companyName);
			compAccount.setCid(cid);
			try {
				compAccount.setPassword(MD5.encode(defaultPassword));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			compAccount.setPasswordClear(defaultPassword);
			// 预留，可能还要set联系方式等
			if (StringUtils.isEmpty(compAccount.getEmail())) {
				compAccount.setEmail(account + "@huanbao.com");
			}
			// compAccount.setName(companyName);
			compAccount.setGmtLogin(new Date());
			compAccount.setGmtCreated(new Date());
			compAccount.setGmtModified(new Date());
			compAccount.setGmtRegister(new Date());
			compAccountDao.insertCompAccount(compAccount);
			flag = true;
			// }

		}
		return flag == true ? cid : 0;
	}

	@Override
	public String getAreaCodeByAreaName(String areaName) {
		String AreaCode = "1001100010001000";
		if (StringUtils.isNotEmpty(areaName)) {
			String temp = sysAreaDao.queryAreaCodeByAreaName(areaName);
			if (StringUtils.isNotEmpty(temp) && temp.length() == 16) {
				AreaCode = temp;
			}
		}
		return AreaCode;
	}

	@Override
	public String getProvinceCodeByProvinceName(String provinceName) {
		String provinceCode = "100110001000";
		if (StringUtils.isNotEmpty(provinceName)) {
			String temp = sysAreaDao
					.queryProvinceCodeByProvinceName(provinceName);
			if (StringUtils.isNotEmpty(temp) && temp.length() == 12) {
				provinceCode = temp;
			}
		}
		return provinceCode;
	}

	@Override
	public String getSupplyAreaCodeBySupplyAreaName(String supplyAreaName) {
		String supplyAreaCode = "1001100010001000";
		if (StringUtils.isNotEmpty(supplyAreaName)) {
			String temp = sysAreaDao
					.querySupplyAreaCodeBySupplyAreaName(supplyAreaName);
			if (StringUtils.isNotEmpty(temp) && temp.length() == 16) {
				supplyAreaCode = temp;
			}
		}
		return supplyAreaCode;
	}

	@Override
	public String getCategoryCodeByCategoryName(String categoryName) {
		String categoryCode = "10001000";
		if (StringUtils.isNotEmpty(categoryName)) {
			String temp = tradeCategoryDao
					.queryCategoryCodeByCategoryName(categoryName);
			if (StringUtils.isNotEmpty(temp)) {
				categoryCode = temp;
			}
		}
		return categoryCode;
	}

	@Override
	public boolean deleteBuyById(Integer id) {
		boolean flag = false;
		try {
			if (id > 0) {
				if (tradeBuyDao.deleteBuyById(id) > 0)
					flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	@Override
	public boolean deleteSupplyById(Integer id) {
		boolean flag = false;
		try {
			if (id > 0) {
				if (tradeSupplyDao.deleteSupplyById(id) > 0)
					flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	@Override
	public boolean validateMobileExist(String mobile) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(mobile)) {
			if (compAccountDao.queryCompAccountByMobile(mobile) > 0)
				flag = true;
		}
		return flag;
	}

	@Override
	public String getBuyCategoryCodeByCategoryName(String categoryName) {
		String categoryCode = "10011000";
		if (StringUtils.isNotEmpty(categoryName)) {
			String temp = tradeCategoryDao
					.queryBuyCategoryCodeByCategoryName(categoryName);
			if (StringUtils.isNotEmpty(temp)) {
				categoryCode = temp;
			}
		}
		return categoryCode;
	}

	@Override
	public String getSupplyCategoryCodeByCategoryName(String categoryName) {
		String categoryCode = "10001000";
		if (StringUtils.isNotEmpty(categoryName)) {
			String temp = tradeCategoryDao
					.querySupplyCategoryCodeByCategoryName(categoryName);
			if (StringUtils.isNotEmpty(temp)) {
				categoryCode = temp;
			}
		}
		return categoryCode;
	}

	@Override
	public Integer createProfile(CompProfile profile) {

		return compProfileDao.insertCompProfile(profile);
	}

	@Override
	public Integer createAccount(CompAccount account) {
		if (account == null) {
			return null;
		}
		return compAccountDao.insertCompAccount(account);
	}

	@Override
	public Integer updateAccount(CompAccount account) {
		return compAccountDao.updateBaseCompAccount(account);
	}

	@Override
	public Integer updateCompProfile(CompProfile profile) {
		return compProfileDao.updateCompProfile(profile);
	}

	@Override
	public CompAccount buildCompAccount(CompAccount account,
			CompAccountMakeMap accountMap, HSSFRow row) {
		account.setPassword(PWD_ENCODE);
		account.setPasswordClear(PWD_CLEAR);
		account.setGmtLogin(new Date());
		account.setGmtRegister(new Date());

		if (accountMap.getAccountName() != null) {
			if (row.getCell(accountMap.getAccountName())!=null){
				String tmp = row.getCell(accountMap.getAccountName())
				.getRichStringCellValue().getString();
				if (StringUtils.isNotEmpty(tmp)) {
					tmp = tmp.replace("：", "").replace("先生", "").replace("女士", "")
					.replace(")", "").replace("(", " ");
					String[] np = tmp.split(" ");
					if (np.length >= 2) {
						account.setPosition(np[1]);
						account.setName(np[0]);
					} else {
						account.setName(tmp);
					}
				}
			}
		}

		if (StringUtils.isEmpty(account.getName())) {
			account.setName("");
		}

		if (accountMap.getEmail() != null) {
			if (row.getCell(accountMap.getEmail())!=null){
				account.setEmail(row.getCell(accountMap.getEmail())
						.getRichStringCellValue().getString());
			}
		}
		if (StringUtils.isEmpty(account.getEmail())) {
			account.setEmail(account.getAccount() + EMAIL_DOMAIN);
		}

		if (accountMap.getPhone() != null) {
			if (row.getCell(accountMap.getPhone())!=null){
				try {
					Double d=row.getCell(accountMap.getPhone()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					account.setPhone(format.format(d));
				} catch (Exception e) {
					account.setPhone(String.valueOf(row.getCell(accountMap.getPhone()).getRichStringCellValue()));
				}
			}
		}

		if (accountMap.getFax() != null) {
			if (row.getCell(accountMap.getFax())!=null){
				try {
					Double d=row.getCell(accountMap.getFax()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					account.setFax(format.format(d));
				} catch (Exception e) {
					account.setFax(String.valueOf(row.getCell(accountMap.getFax()).getRichStringCellValue()));
				}
			}
		}

		if (accountMap.getMobile() != null) {
			try {
				if (row.getCell(accountMap.getMobile())!=null){
					Double d=row.getCell(accountMap.getMobile()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					account.setMobile(format.format(d));
				}
			} catch (Exception e) {
				if(row.getCell(accountMap.getMobile())!=null){
					account.setMobile(String.valueOf(row.getCell(accountMap.getMobile()).getRichStringCellValue()));
				}
			}
		}

		if (accountMap.getContact() != null) {
			if (row.getCell(accountMap.getContact())!=null){
				account.setContact(row.getCell(accountMap.getContact())
						.getRichStringCellValue().getString());
			}
		}

		return account;
	}

	@Override
	public CompProfile buildCompProfile(CompProfile profile,
			CompMakeMap compMap, HSSFRow row) {
		profile.setMemberCode("10011000");
		profile.setMemberCodeBlock("");
		profile.setMessageCount(0);
		profile.setViewCount(0);
		if (profile.getMainBuy()==null){
			profile.setMainBuy((short)0);
		}
		if (profile.getMainSupply()==null){
			profile.setMainSupply((short)0);
		}
		if (compMap.getCompName() != null) {
			if (row.getCell(compMap.getCompName()) != null)
				profile.setName(row.getCell(compMap.getCompName())
						.getRichStringCellValue().getString());
		}
		if (StringUtils.isEmpty(profile.getName())) {
			profile.setName("");
		}

		if (compMap.getAddress() != null) {
			if (row.getCell(compMap.getAddress()) != null) {
				String tmp = row.getCell(compMap.getAddress())
						.getRichStringCellValue().getString();
				profile.setAddress(Jsoup.clean(tmp, Whitelist.none()));
			}
		}

		if (compMap.getAddressZip() != null) {
			if (row.getCell(compMap.getAddressZip()) != null)
				profile.setAddressZip(row.getCell(compMap.getAddressZip())
						.getRichStringCellValue().getString());
		}

		// if(compMap.getAreaName()!=null){
		//			
		// }

		if (compMap.getDetails() != null) {
			if (row.getCell(compMap.getDetails()) != null) {
				String tmp = row.getCell(compMap.getDetails())
						.getRichStringCellValue().getString();
				String tmpquery = Jsoup.clean(tmp, Whitelist.none());
				if (tmpquery.length() > 990) {
					tmpquery = tmpquery.substring(0, 990);
				}
				if (tmp.length() > 4800) {
					tmp = tmp.substring(0, 4800);
				}
				profile.setDetailsQuery(tmpquery);
				profile.setDetails(tmp);
			}
		}

		if (compMap.getFounds() != null) {
			String num =null;
			if (row.getCell(compMap.getFounds()) != null){
				try {
					Double d=row.getCell(compMap.getFounds()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					num=format.format(d);
				} catch (Exception e) {
					num = row.getCell(compMap.getFounds()).getRichStringCellValue().getString();
				}
			}
			profile.setFunds(num);
		}

		if (compMap.getLegal() != null) {
			if (row.getCell(compMap.getLegal()) != null)
				profile.setLegal(row.getCell(compMap.getLegal())
						.getRichStringCellValue().getString());
		}

		if (compMap.getDomain() != null) {
			if (row.getCell(compMap.getDomain()) != null)
				profile.setDomain(row.getCell(compMap.getDomain())
						.getRichStringCellValue().getString());
		}

		if (compMap.getMainBrand() != null) {
			if (row.getCell(compMap.getMainBrand()) != null)
				profile.setMainBrand(row.getCell(compMap.getMainBrand())
						.getRichStringCellValue().getString());
		}

		if (compMap.getMainProductBuy() != null) {
			if (row.getCell(compMap.getMainProductBuy()) != null)
				profile.setMainProductBuy(row.getCell(
						compMap.getMainProductBuy()).getRichStringCellValue()
						.getString());
		}

		if (compMap.getMainProductSupply() != null) {
			if (row.getCell(compMap.getMainProductSupply()) != null){
				String tmp=row.getCell(compMap.getMainProductSupply()).getRichStringCellValue().getString();
				if (tmp.length() > 2000) {
					tmp = tmp.substring(0, 1990);
				}
				profile.setMainProductSupply(tmp);
			}
		}
		if(compMap.getProcessMethod()!=null) {
			if (row.getCell(compMap.getProcessMethod()) != null)
				profile.setProcessMethod(row.getCell(
						compMap.getProcessMethod())
						.getRichStringCellValue().getString());
		}
		if(compMap.getProcess()!=null) {
			if (row.getCell(compMap.getProcess()) != null)
				profile.setProcess(row.getCell(
						compMap.getProcess())
						.getRichStringCellValue().getString());
		}
		
		if(compMap.getEmployeeNum()!=null) {
			String num=null;
			if (row.getCell(compMap.getEmployeeNum()) != null){
				try {
					Double d=row.getCell(compMap.getEmployeeNum()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					num=format.format(d);
				} catch (Exception e) {
					num=row.getCell(compMap.getEmployeeNum()).getRichStringCellValue().getString();
				}
				profile.setEmployeeNum(num);
			}
		}
		if(compMap.getDeveloperNum()!=null) {
			String num = null;
			if (row.getCell(compMap.getDeveloperNum()) != null){
				try {
					Double d=row.getCell(compMap.getDeveloperNum()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					num=format.format(d);
				} catch (Exception e) {
					num=row.getCell(compMap.getDeveloperNum()).getRichStringCellValue().getString();
				}
			}
			profile.setDeveloperNum(num);
		}
		
		if(compMap.getPlantArea()!=null) {
			String num = null;
			if (row.getCell(compMap.getPlantArea()) != null){
				try {
					Double d=row.getCell(compMap.getPlantArea()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					num=format.format(d);
				} catch (Exception e) {
					num = row.getCell(compMap.getPlantArea()).getRichStringCellValue().getString();
				}
				profile.setPlantArea(num);
			}
		}
		if(compMap.getMainMarket()!=null) {
			if (row.getCell(compMap.getMainMarket()) != null) {
				String mainMarkets = row.getCell(
						compMap.getMainMarket())
						.getRichStringCellValue().getString();
				if(mainMarkets.length()>1000) {
					profile.setMainMarket(mainMarkets.substring(0, 888));
				} else {
					profile.setMainMarket(mainMarkets);
				}
				
			}
				
		}
		if(compMap.getMainCustomer()!=null) {
			if (row.getCell(compMap.getMainCustomer()) != null)
				profile.setMainCustomer(row.getCell(
						compMap.getMainCustomer())
						.getRichStringCellValue().getString());
		}
		if(compMap.getMonthOutput()!=null) {
			String num = null;
			if (row.getCell(compMap.getMonthOutput()) != null){
				try {
					Double d=row.getCell(compMap.getMonthOutput()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					num=format.format(d);
				} catch (Exception e) {
					num = row.getCell(compMap.getMonthOutput()).getRichStringCellValue().getString();
				}
			}
			profile.setMonthOutput(num);
		}
		if(compMap.getYearTurnover()!=null) {
			String num =null;
			if (row.getCell(compMap.getYearTurnover()) != null){
				try {
					Double d=row.getCell(compMap.getYearTurnover()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					num=format.format(d);
				} catch (Exception e) {
					num = row.getCell(compMap.getYearTurnover()).getRichStringCellValue().getString();
				}
			}
			profile.setYearTurnover(num);
		}
		if(compMap.getYearExports()!=null) {
			if (row.getCell(compMap.getYearExports()) != null)
				profile.setYearExports(row.getCell(
						compMap.getYearExports())
						.getRichStringCellValue().getString());
		}
		if(compMap.getQualityControl()!=null) {
			if (row.getCell(compMap.getQualityControl()) != null)
				profile.setQualityControl(row.getCell(
						compMap.getQualityControl())
						.getRichStringCellValue().getString());
		}
		if(compMap.getRegisterArea()!=null) {
			if (row.getCell(compMap.getRegisterArea()) != null)
				profile.setRegisterArea(row.getCell(
						compMap.getRegisterArea())
						.getRichStringCellValue().getString());
		}
		if(compMap.getEnterpriseType()!=null) {
			if (row.getCell(compMap.getEnterpriseType()) != null)
				profile.setEnterpriseType(row.getCell(
						compMap.getEnterpriseType())
						.getRichStringCellValue().getString());
		}
		return profile;
	}

	@Override
	public Boolean createCompAccount(Integer cid, String account,
			CompAccountMakeMap accountMap, HSSFRow row) {
		CompAccount compAccount = compAccountDao.queryAccountDetails(account);
		Integer impact = null;
		if (compAccount == null) {
			compAccount = new CompAccount();
			compAccount.setAccount(account);
			compAccount.setCid(cid);
			compAccount = buildCompAccount(compAccount, accountMap, row);
			try {
				impact = createAccount(compAccount);
			} catch (Exception e) {
				return false;
			}
		} else {
			compAccount = buildCompAccount(compAccount, accountMap, row);
			try {
				impact = updateAccount(compAccount);
			} catch (Exception e) {
				return false;
			}
		}
		if (impact != null && impact.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer createCompProfile(CompMakeMap compMap, String account,
			HSSFRow row, String sourceFlag,Integer mainUse) {
		CompAccount compAccount = null;
		compAccount = compAccountDao.queryAccountDetails(account);

		Integer cid = null;
		if (compAccount == null) {
			// 创建新公司信息
			CompProfile compProfile = new CompProfile();
			compProfile.setDelStatus(0);
			
			compProfile.setRegisterCode(sourceFlag);
			
			if (mainUse!=null){
				if (mainUse==1){
					compProfile.setMainSupply((short)1);
				}
				if (mainUse==2){
					compProfile.setMainBuy((short)1);
				}
			}
			compProfile = buildCompProfile(compProfile, compMap, row);
			try {
				cid = createProfile(compProfile);
				if (cid == null) {
					return null;
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			// 获取公司信息，并更新公司信息
			CompProfile compProfile = compProfileDao
					.queryFullProfile(compAccount.getCid());
			if(compProfile==null) {
				compProfile = new CompProfile();
				compProfile.setDelStatus(0);
			
				compProfile.setRegisterCode(sourceFlag);
				
				if (mainUse!=null){
					if (mainUse==1){
						compProfile.setMainSupply((short)1);
					}
					if (mainUse==2){
						compProfile.setMainBuy((short)1);
					}
				}
				compProfile = buildCompProfile(compProfile, compMap, row);
				try {
					cid = createProfile(compProfile);
					if (cid == null) {
						return null;
					}
				} catch (DataAccessException e) {
					return null;
				}
			}
			
			compProfile.setRegisterCode(sourceFlag);
			
			if (mainUse!=null){
				if (mainUse==1){
					compProfile.setMainSupply((short)1);
				}
				if (mainUse==2){
					compProfile.setMainBuy((short)1);
				}
			}
			compProfile = buildCompProfile(compProfile, compMap, row);
			updateCompProfile(compProfile);
			cid = compProfile.getId();
		}
		return cid;
	}

	public static void main(String[] args) {
		// String target="20.01元/台";
		String target = "http://i01.c.aliimg.com/img/ibank/2010/720/831/154138027_1649984625.summ.jpg\">http://i03.c.aliimg.com/img/ibank/2010/040/831/154138040_1649984625.summ.jpg\">http://i05.c.aliimg.com/img/ibank/2010/750/831/154138057_1649984625.summ.jpg\">";
		// String
		// target="    http://img03.hc360.cn/03/busin/955/984/b/03-95598483.JPG            http://img03.hc360.cn/03/busin/955/984/m/m_03-95598483.JPG   http://img07.hc360.cn/07/busin/955/984/m/m_07-95598487.jpg     ";
		// Pattern pattern=Pattern.compile("[+\\-]?\\d+(.\\d+)?");
		Pattern pattern = Pattern.compile("http://[\\w\\.\\-/:]+");
		Matcher matcher = pattern.matcher(target);
		// if(matcher.find()){
		// System.out.println(matcher.group());
		// }
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}

	@Override
	public Integer countTradeSupply(Integer cid, Integer uid,
			String categoryCode, String title) {
		return tradeSupplyDao.countTradeSupply(cid, uid, categoryCode, title);
	}

	@Override
	public Photo buildPhoto(Integer uid, Integer cid, Integer targetId,
			String targetType) {
		Photo photo = new Photo();
		photo.setCid(cid);
		photo.setUid(uid);
		photo.setTargetType(targetType);
		photo.setTargetId(targetId);
		photo.setPhotoAlbumId(0);
		photo.setSize(0);
		return photo;
	}

	@Override
	public TradeSupply buildTradeSupply(TradeSupply supply,
			TradeSupplyMakeMap tradeMap, HSSFRow row,String sourceFlag) {
		if (tradeMap.getDetails() != null) {
			if (row.getCell(tradeMap.getDetails()) != null) {
				String tmp = row.getCell(tradeMap.getDetails())
						.getRichStringCellValue().getString();
				if (tmp !=null && tmp.length()>5000){
					tmp.substring(0, 4990);
				}
				supply.setDetails(tmp);
			}
		}

		if (tradeMap.getProperty() != null) {
			if (row.getCell(tradeMap.getProperty()) != null) {
				String tmp = row.getCell(tradeMap.getProperty())
						.getRichStringCellValue().getString();
				if (supply.getDetails() != null) {
					tmp = supply.getDetails() + "<div>" + tmp + "</div>";
				}
				supply.setDetails(tmp);
			}
		}

		if (supply.getDetails() != null) {
			String tmp = supply.getDetails();
			tmp = Jsoup.clean(tmp, Whitelist.none());
			if (tmp != null && tmp.length() > 990) {
				tmp = tmp.substring(0, 990);
			}
			supply.setDetailsQuery(tmp);
		}

		if (tradeMap.getPrice() != null) {
			if (row.getCell(tradeMap.getPrice()) != null) {
				String tmp = row.getCell(tradeMap.getPrice())
						.getRichStringCellValue().getString();
				if (StringUtils.isNotEmpty(tmp)) {
					Pattern pattern = Pattern.compile("[+\\-]?\\d+(.\\d+)?");
					Matcher matcher = pattern.matcher(tmp);
					if (matcher.find()) {
						int price = (int) (Float.valueOf(matcher.group()) * 100);
						supply.setPriceNum(price/100);
						supply.setPriceUnits(tmp.replace(matcher.group(), ""));
					} else {
						supply.setPriceUnits(tmp);
					}
				}
			}
		}

		if (tradeMap.getTotalNum() != null) {
			if (row.getCell(tradeMap.getTotalNum()) != null) {
				String tmp = row.getCell(tradeMap.getTotalNum())
						.getRichStringCellValue().getString();
				if (StringUtils.isNotEmpty(tmp)) {
					Pattern pattern = Pattern.compile("[+\\-]?\\d+(.\\d+)?");
					Matcher matcher = pattern.matcher(tmp);
					if (matcher.find()) {
						Integer totalNum = Integer.valueOf(matcher.group());
						supply.setTotalNum(totalNum);
						supply.setTotalUnits(tmp.replace(matcher.group(), ""));
					} else {
						supply.setTotalUnits(tmp);
					}
				}
			}
		}
		
		supply.setInfoComeFrom(Integer.parseInt(sourceFlag));
		
		int pubday = -700;
		if (tradeMap.getRandomPublishDay() != null) {
			pubday = tradeMap.getRandomPublishDay();
		}
		pubday = (int) (Math.random() * pubday);
		Date pubdate = DateUtil.getDateAfterDays(new Date(), pubday);
		
		supply.setGmtPublish(pubdate);
		supply.setGmtRefresh(pubdate);
		supply.setValidDays((short) 0);
		try {
			supply.setGmtExpired(DateUtil.getDate("2050-12-31", "yyyy-MM-dd"));
		} catch (ParseException e) {
		}

		supply.setDelStatus((short) 0);
		supply.setPauseStatus((short) 0);
		supply.setCheckStatus((short) 1);
		supply.setTags("");
		supply.setTagsSys("");
		supply.setPropertyQuery("");
		supply.setMessageCount(0);
		supply.setViewCount(0);
		supply.setFavoriteCount(0);
		supply.setPlusCount(0);
		supply.setIntegrity((short) 0);

		return supply;
	}

	@Override
	public String crawlWebPhoto(String picUrl, String destRoot) {
		try {

			picUrl = picUrl.replace(".summ", "");

			String folder = buildPhotoFolder();
			String filename = picUrl.substring(picUrl.lastIndexOf("/") + 1,
					picUrl.length());

			if (StringUtils.isEmpty(filename)) {
				return null;
			}

			File file = new File(destRoot + "/crawl" + folder);
			if (!file.exists()) {
				file.mkdirs();
			}

			URL url = new URL(picUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			DataInputStream in = new DataInputStream(connection
					.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					destRoot + "/crawl" + folder + filename));

			byte[] buffer = new byte[4096];

			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();
			in.close();
			return "/crawl" + folder + filename;
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Boolean createTradeSupply(Integer cid, Integer uid,
			TradeSupplyMakeMap tradeMap, HSSFRow row,String sourceFlag) {
		do {

			if (cid == null || cid.intValue() <= 0) {
				break;
			}

			if (uid == null || uid.intValue() <= 0) {
				break;
			}

			TradeSupply supply = new TradeSupply();

			if (tradeMap.getCategoryCode() != null) {
				supply.setCategoryCode(tradeMap.getCategoryCode());
			} else {
				break;
			}

			if (tradeMap.getTitle() != null) {
				if (row.getCell(tradeMap.getTitle()) != null) {
					String title = row.getCell(tradeMap.getTitle())
							.getRichStringCellValue().getString();
					title = Jsoup.clean(title, Whitelist.none());
					supply.setTitle(title);
				}
			} else {
				supply.setTitle("");
			}

			Integer i = tradeSupplyDao.countTradeSupply(cid, uid, supply
					.getCategoryCode(), supply.getTitle());

			if (i != null && i.intValue() > 0) {
				break;
			}

			supply.setCid(cid);
			supply.setUid(uid);

			supply = buildTradeSupply(supply, tradeMap, row, sourceFlag);

			if (supply == null) {
				break;
			}

			Integer id = null;
			try {
				id = tradeSupplyDao.insertTradeSupply(supply);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}

			// 抓取图片
			if (tradeMap.getPhotoUrl() != null) {
				if (row.getCell(tradeMap.getPhotoUrl()) != null) {
					String tmp = row.getCell(tradeMap.getPhotoUrl())
							.getRichStringCellValue().getString();
					if (StringUtils.isNotEmpty(tmp)) {
						Pattern pattern = Pattern
								.compile("http://[\\w\\.\\-/:]+");
						Matcher matcher = pattern.matcher(tmp);
						while (matcher.find()) {
							String path = crawlWebPhoto(matcher.group(),
									"/usr/data/resources");
							if (StringUtils.isEmpty(path)) {
								continue;
							}
							Photo photo = buildPhoto(uid, cid, id,
									PhotoService.TARGET_SUPPLY);
							photo.setPath(path);
							photoDao.insertPhoto(photo);
						}
					}
				}
			}

			return true;
		} while (false);
		return false;
	}

	private String buildPhotoFolder() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return "/" + calendar.get(Calendar.YEAR) + "/"
				+ calendar.get(Calendar.MONTH) + "/"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "/";
	}

	@Override
	public Integer updateAlibabaEmail(String account, String email) {
		//查询邮箱是否存在
		Integer count2 = compAccountDao.countUserByEmailAndAccount(email, account);
		if(count2>0)
			return null;
		Integer count = compAccountDao.countUserByEmail(email);
		if(count>0) {
			//邮箱存在,然后更新
			String[] arr = email.split("@");
			
			String tmpEmail = arr[0] + "@tmp" + UUID.randomUUID() + ".com";
			if(compAccountDao.updateEmailToBlank(email, tmpEmail)>0)
				compAccountDao.updateEmailByAccount(account, email);
			else
				return null;
		}
		return compAccountDao.updateEmailByAccount(account, email);
	}

	@Override
	public boolean createTradeBuy(Integer cid, Integer uid,
			TradeBuyMakeMap tradeMap, HSSFRow row,String sourceFlag) {
		do {

			if (cid == null || cid.intValue() <= 0) {
				break;
			}

			if (uid == null || uid.intValue() <= 0) {
				break;
			}

			TradeBuy buy = new TradeBuy();

			if (tradeMap.getCategoryCode() != null) {
				buy.setCategoryCode(tradeMap.getCategoryCode());
			}

			if (tradeMap.getTitle() != null) {
				if (row.getCell(tradeMap.getTitle()) != null) {
					String title = row.getCell(tradeMap.getTitle())
							.getRichStringCellValue().getString();
					title = Jsoup.clean(title, Whitelist.none());
					buy.setTitle(title);
				}
			} else {
				buy.setTitle("");
			}
			
			buy.setInfoComeFrom(Integer.parseInt(sourceFlag));

			Integer i = tradeBuyDao.countTradeBuy(cid, uid, buy
					.getCategoryCode(), buy.getTitle());

			if (i != null && i.intValue() > 0) {
				break;
			}

			buy.setCid(cid);
			buy.setUid(uid);

			buy = buildTradeBuy(buy, tradeMap, row);

			if (buy == null) {
				break;
			}

			Integer id = null;
			try {
				id = tradeBuyDao.insertTradeBuy(buy);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}

			// 抓取图片
			if (tradeMap.getPhotoUrl() != null) {
				if (row.getCell(tradeMap.getPhotoUrl()) != null) {
					String tmp = row.getCell(tradeMap.getPhotoUrl())
							.getRichStringCellValue().getString();
					if (StringUtils.isNotEmpty(tmp)) {
						Pattern pattern = Pattern
								.compile("http://[\\w\\.\\-/:]+");
						Matcher matcher = pattern.matcher(tmp);
						while (matcher.find()) {
							String path = crawlWebPhoto(matcher.group(),
									"/usr/data/resources");
							if (StringUtils.isEmpty(path)) {
								continue;
							}
							Photo photo = buildPhoto(uid, cid, id,
									PhotoService.TARGET_BUY);
							photo.setPath(path);
							photoDao.insertPhoto(photo);
						}
					}
				}
			}

			return true;
		} while (false);
		return false;
	}

	@Override
	public TradeBuy buildTradeBuy(TradeBuy buy, TradeBuyMakeMap tradeMap,
			HSSFRow row) {
		if (tradeMap.getDetails() != null) {
			if (row.getCell(tradeMap.getDetails()) != null) {
				String tmp = row.getCell(tradeMap.getDetails())
						.getRichStringCellValue().getString();
				buy.setDetails(tmp);
			}
		}
		
		if (buy.getDetails()!=null){
			String tmp = buy.getDetails();
			tmp = Jsoup.clean(tmp, Whitelist.none());
			if (tmp != null && tmp.length() > 990) {
				tmp = tmp.substring(0, 990);
			}
			buy.setDetailsQuery(tmp);
		}

		if (tradeMap.getRequire() != null) {
			if (row.getCell(tradeMap.getRequire()) != null) {
				String tmp = row.getCell(tradeMap.getRequire())
						.getRichStringCellValue().getString();
				if (buy.getDetails() != null) {
					tmp = buy.getDetails() + "<br>" + tmp;
				}
				buy.setDetails(tmp);
			}
		}
		
		if (tradeMap.getQuantity()!=null){
			if (row.getCell(tradeMap.getQuantity()) != null) {
				try {
					String tmp = row.getCell(tradeMap.getQuantity())
					.getRichStringCellValue().getString();
					if (StringUtils.isNotEmpty(tmp)) {
						Pattern pattern = Pattern.compile("[+\\-]?\\d+(.\\d+)?");
						Matcher matcher = pattern.matcher(tmp);
						if (matcher.find()) {
							Integer num = Integer.valueOf(matcher.group());
							buy.setQuantity(num); 
							buy.setQuantityUntis(tmp.replace(matcher.group(), ""));
						} else {
							buy.setQuantityUntis(tmp);
						}
					}
				} catch (Exception e) {
					Double d=row.getCell(tradeMap.getQuantity()).getNumericCellValue();
					NumberFormat format = new DecimalFormat("#");
					buy.setQuantity(Integer.parseInt(format.format(d)));
				}
			}
		}
//		int pubday = -700;
//		if (tradeMap.getRandomPublishDay() != null) {
//			pubday = tradeMap.getRandomPublishDay();
//		}
//		pubday = (int) (Math.random() * pubday);
//		Date pubdate = DateUtil.getDateAfterDays(new Date(), pubday);
		
		Date pubdate=null;
		if (tradeMap.getRandomPublishDay()!=null){
			if (row.getCell(tradeMap.getRandomPublishDay())!=null){
				try {
					pubdate=row.getCell(tradeMap.getRandomPublishDay()).getDateCellValue();
				} catch (Exception e) {
					String str=row.getCell(tradeMap.getRandomPublishDay()).getRichStringCellValue().getString().replace("'", "");
					try {
						pubdate=DateUtil.getDate(str, "yyyy-MM-dd");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		Date end=null;
		if (tradeMap.getGmtExpired()!=null){
			if (row.getCell(tradeMap.getGmtExpired())!=null){
				try {
					end=row.getCell(tradeMap.getGmtExpired()).getDateCellValue();
				} catch (Exception e) {
					String str=row.getCell(tradeMap.getGmtExpired()).getRichStringCellValue().getString().replace("'", "");
					try {
						end=DateUtil.getDate(str, "yyyy-MM-dd");
						
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				buy.setGmtExpired(end);
			}
		}else {
			try {
				buy.setValidDays((short)0);
				buy.setGmtExpired(DateUtil.getDate("2050-12-31", "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		if (end!=null && pubdate!=null){
			try {
				Integer days=DateUtil.getIntervalDays(end, pubdate);
				if (days!=null && days.intValue()>=0){
					buy.setValidDays(days.shortValue());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(buy.getValidDays()==null){
			buy.setValidDays((short)0);
		}
		
		// 添加判断，当天导入且时间 在14:30 之前，则在当天14:30 更新
		Date cdate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(cdate);
		c.set(Calendar.HOUR_OF_DAY, 14);
		c.set(Calendar.MINUTE, 30);
		long cldate = c.getTime().getTime();
		if(new Date().getTime()<cldate){
			pubdate = c.getTime();
		}

		buy.setGmtPublish(pubdate);
		buy.setGmtRefresh(pubdate);
		buy.setDelStatus((short) 0);
		buy.setPauseStatus((short) 0);
		buy.setCheckStatus((short) 1);
		buy.setTagsSys("");
		buy.setMessageCount(0);
		buy.setViewCount(0);
		buy.setFavoriteCount(0);
		buy.setPlusCount(0);
		return buy;
	}

	@Override
	public boolean createNews(NewsMakeMap newsMap, HSSFRow row) {
		do {
			News news = new News();

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
			
			if (newsMap.getTitleIndex() != null) {
				if (row.getCell(newsMap.getTitleIndex()) != null) {
					String titleIndex = row.getCell(newsMap.getTitleIndex())
							.getRichStringCellValue().getString();
					titleIndex = Jsoup.clean(titleIndex, Whitelist.none());
					news.setTitleIndex(titleIndex);
				}
			} else {
				news.setTitleIndex("");
			}
			
			if(newsMap.getDescription()!=null){
				if (row.getCell(newsMap.getDescription()) != null) {
					String description = row.getCell(newsMap.getDescription())
							.getRichStringCellValue().getString();
					news.setDescription(description);
				}
			}
			if (newsMap.getDetails()!=null){
				if (row.getCell(newsMap.getDetails()) != null) {
					String details = row.getCell(newsMap.getDetails())
							.getRichStringCellValue().getString();
					news.setDetails(details);
				}
			}
			if (newsMap.getTags()!=null){
				if (row.getCell(newsMap.getTags()) != null) {
					String tags = row.getCell(newsMap.getTags())
							.getRichStringCellValue().getString();
					news.setTags(tags);
				}
			}

			if (newsMap.getNewsSource()!=null){
				if (row.getCell(newsMap.getNewsSource()) != null) {
					String newsSource = row.getCell(newsMap.getNewsSource())
							.getRichStringCellValue().getString();
					news.setNewsSource(newsSource);
				}
			}
			if (newsMap.getNewsSourceUrl()!=null){
				if (row.getCell(newsMap.getNewsSourceUrl()) != null) {
					String newsSourceUrl = row.getCell(newsMap.getNewsSourceUrl())
							.getRichStringCellValue().getString();
					news.setNewsSourceUrl(newsSourceUrl);
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
			int  random =(int)(Math.round(Math.random()*100)+200);
			
			news.setGmtPublish(pubdate);
			news.setViewCount(random);
			news.setPauseStatus(1);
			news.setAdminAccount("");
			news.setAdminName("");
			
			if (news == null) {
				break;
			}

			Integer id = null;
			try {
				id = newsDao.insertNewsByAdmin(news);
			} catch (DataAccessException e) {
				e.printStackTrace();
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}
			return true;
		} while (false);
		return false;
	}

	@Override
	public boolean createIbdCompany(IbdCompanyMakeMap companyMap, HSSFRow row) {
		
		do {
			IbdCompany comp = new IbdCompany();

			if (companyMap.getCategoryCode() != null) {
				comp.setCategoryCode(companyMap.getCategoryCode());
			} else {
				break;
			}

			if (companyMap.getName() != null) {
				if (row.getCell(companyMap.getName()) != null) {
					String name = row.getCell(companyMap.getName())
							.getRichStringCellValue().getString();
					name = Jsoup.clean(name, Whitelist.none());
					comp.setName(name);
				}
			} else {
				comp.setName("");
			}
			
			if (companyMap.getDetails()!=null){
				if (row.getCell(companyMap.getDetails()) != null) {
					String details = row.getCell(companyMap.getDetails())
							.getRichStringCellValue().getString();
					comp.setDetails(details);
				}
			}
			
			if (companyMap.getIndustry() != null) {
				if (row.getCell(companyMap.getIndustry()) != null) {
					String industry = row.getCell(companyMap.getIndustry())
							.getRichStringCellValue().getString();
					industry = Jsoup.clean(industry, Whitelist.none());
					comp.setIndustry(industry);
				}
			} else {
				comp.setIndustry("");
			}
			
			if(companyMap.getMainProduct()!=null){
				if (row.getCell(companyMap.getMainProduct()) != null) {
					String mainProduct = row.getCell(companyMap.getMainProduct())
							.getRichStringCellValue().getString();
					comp.setMainProduct(mainProduct);
				}
			}
			
			if (companyMap.getEnterpriseType()!=null){
				if (row.getCell(companyMap.getEnterpriseType()) != null) {
					String type = row.getCell(companyMap.getEnterpriseType())
							.getRichStringCellValue().getString();
					comp.setEnterpriseType(type);
				}
			}

			if (companyMap.getLegal()!=null){
					if (row.getCell(companyMap.getLegal()) != null) {
						String legal = row.getCell(companyMap.getLegal())
								.getRichStringCellValue().getString();
						comp.setLegal(legal);
					}
			}
			
			if (companyMap.getFunds()!=null){
				if (row.getCell(companyMap.getFunds()) != null) {
					String funds = row.getCell(companyMap.getFunds())
							.getRichStringCellValue().getString();
					comp.setFunds(funds);
				}
			}
			
			if (companyMap.getAddress()!=null){
				if (row.getCell(companyMap.getAddress()) != null) {
					String address = row.getCell(companyMap.getAddress())
							.getRichStringCellValue().getString();
					comp.setAddress(address);
				}
			}
			
			if (companyMap.getEmployeeNum()!=null){
				if (row.getCell(companyMap.getEmployeeNum()) != null) {
					String employeeNum = row.getCell(companyMap.getEmployeeNum())
							.getRichStringCellValue().getString();
					comp.setEmployeeNum(employeeNum);
				}
			}
			
			if (companyMap.getRegisterArea()!=null){
				if (row.getCell(companyMap.getRegisterArea()) != null) {
					String registerArea = row.getCell(companyMap.getRegisterArea())
							.getRichStringCellValue().getString();
					comp.setRegisterArea(registerArea);
				}
			}
			
			if (companyMap.getBusinessModel()!=null){
				if (row.getCell(companyMap.getBusinessModel()) != null) {
					String businessModel = row.getCell(companyMap.getBusinessModel())
							.getRichStringCellValue().getString();
					comp.setBusinessModel(businessModel);
				}
			}
			
			if (companyMap.getContact()!=null){
				if (row.getCell(companyMap.getContact()) != null) {
					String contact = row.getCell(companyMap.getContact())
							.getRichStringCellValue().getString();
					comp.setContact(contact);
				}
			}
			
			if (companyMap.getSex()!=null){
				if (row.getCell(companyMap.getSex()) != null) {
					String sex = row.getCell(companyMap.getSex())
							.getRichStringCellValue().getString();
					comp.setSex(sex);
				}
			}
			
			if (companyMap.getPosition()!=null){
				if (row.getCell(companyMap.getPosition()) != null) {
					String position = row.getCell(companyMap.getPosition())
							.getRichStringCellValue().getString();
					comp.setPosition(position);
				}
			}
			
			if (companyMap.getPhone()!=null){
				if (row.getCell(companyMap.getPhone()) != null) {
					String phone = row.getCell(companyMap.getPhone())
							.getRichStringCellValue().getString();
					comp.setPhone(phone);
				}
			}
			
			if (companyMap.getMobile()!=null){
				if (row.getCell(companyMap.getMobile()) != null) {
					String mobile = row.getCell(companyMap.getMobile())
							.getRichStringCellValue().getString();
					comp.setMobile(mobile);
				}
			}
			
			if (companyMap.getFax()!=null){
				if (row.getCell(companyMap.getFax()) != null) {
					String fax = row.getCell(companyMap.getFax())
							.getRichStringCellValue().getString();
					comp.setFax(fax);
				}
			}
			
			if (companyMap.getAddressZip()!=null){
				if (row.getCell(companyMap.getAddressZip()) != null) {
					String addressZip = row.getCell(companyMap.getAddressZip())
							.getRichStringCellValue().getString();
					comp.setAddressZip(addressZip);
				}
			}
			
			if (companyMap.getEmail()!=null){
				if (row.getCell(companyMap.getEmail()) != null) {
					String email = row.getCell(companyMap.getEmail())
							.getRichStringCellValue().getString();
					comp.setEmail(email);
				}
			}
			
			if (companyMap.getDomain()!=null){
				if (row.getCell(companyMap.getDomain()) != null) {
					String domain = row.getCell(companyMap.getDomain())
							.getRichStringCellValue().getString();
					comp.setDomain(domain);
				}
			}
			
			if (companyMap.getRegisterTime()!=null){
				if (row.getCell(companyMap.getRegisterTime())!=null){
					String regTime=row.getCell(companyMap.getRegisterTime()).getRichStringCellValue().getString().replace("'", "");
					comp.setRegisterTime(regTime);
				}
			}
			
			if (comp == null) {
				break;
			}

			Integer id = null;
			try {
				id = ibdCompanyService.createIbdCompanyByAdmin(comp);
			} catch (DataAccessException e) {
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}
			return true;
		} while (false);
		return false;
	}

	@Override
	public PageDto<TradeBuy> pageBuys(PageDto<TradeBuy> page) {
		page.setRecords(tradeBuyDao.queryBuys(page));
		page.setTotals(tradeBuyDao.queryBuysCount());
		return page;
	}
}
