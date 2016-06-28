package com.ast.ast1949.service.phone.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.LdbLevel;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.domain.phone.PhoneCallClickLog;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneDto;
import com.ast.ast1949.persist.analysis.AnalysisPpcLogDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.phone.PhoneCallClickLogDao;
import com.ast.ast1949.persist.phone.PhoneClickLogDao;
import com.ast.ast1949.persist.phone.PhoneCostSvrDao;
import com.ast.ast1949.persist.phone.PhoneDao;
import com.ast.ast1949.persist.phone.PhoneLogDao;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.phone.LdbLevelService;
import com.ast.ast1949.service.phone.PhoneBlacklistService;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.util.NumberUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
/**
 * author:kongsj date:2013-7-3
 */
@Component("phoneService")
public class PhoneServiceImpl implements PhoneService {
	
	// 接口私钥：
	private static String apiKey = "a16d751a0e879ca533aeba5f0e985c5e";
	// 用户ID：userkey
	private static String userKey = "11683e186d";
	private static String BLANK_URL_ADD = "http://api.maxvox.com.cn/api/add_private_black";
//		private static String BLANK_URL_DEL = "http://api.maxvox.com.cn/api/del_private_black";
	private static String BLANK_URL_OPEN = "http://api.maxvox.com.cn/api/update_black_status";
	
	// 组装string 的map
	private static Map<String, String> map = new TreeMap<String, String>();

	static {
		map.put("userkey", userKey);
	}
	@Resource
	private PhoneDao phoneDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyDAO companyDao;
	@Resource
	private PhoneLogDao phoneLogDao;
	@Resource
	private PhoneClickLogDao phoneClickLogDao;
	@Resource
	private PhoneCostSvrDao phoneCostSvrDao;
	@Resource
	private AnalysisPpcLogDao analysisPpcLogDao;
	@Resource
	private PhoneCallClickLogDao phoneCallClickLogDao;
	@Resource
	private PhoneBlacklistService phoneBlacklistService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private LdbLevelService ldbLevelService;
	
	@Override
	public Integer insert(Phone phone) {
		do {
			if (phone.getCompanyId() == null && StringUtils.isEmpty(phone.getAccount())) {
				break;
			}
			if (StringUtils.isEmpty(phone.getAccount())) {
				break;
			}
			// 已经开通过 可能涉及 400号码转移 执行更新语句 
			Phone obj = phoneDao.queryByCompanyId(phone.getCompanyId());
			if (obj != null) {
				if (StringUtils.isNotEmpty(phone.getTel())) {
					obj.setTel(phone.getTel());
					obj.setFrontTel(phone.getFrontTel());
					obj.setExpireFlag(0);
					return phoneDao.update(obj);
				}
			}
			if (phone.getCompanyId() == null) {
				break;
			}
			if (StringUtils.isEmpty(phone.getTel())) {
				break;
			}
			
			// 新增一个400 号码
			return phoneDao.insert(phone);
		} while (false);
		return 0;
	}

	@Override
	public PageDto<Phone> pageList(Phone phone, PageDto<Phone> page) {
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("id");
		}
		page.setTotalRecords(phoneDao.queryListCount(phone));
		page.setRecords(phoneDao.queryList(phone, page));
		return page;
	}

	@Override
	public Phone queryById(Integer id) {
		if (id == null) {
			return null;
		}
		return phoneDao.queryById(id);
	}

	@Override
	public Integer update(Phone phone) {
		if (phone.getId() == null) {
			return 0;
		}
		return phoneDao.update(phone);
	}

	@Override
	public Phone queryByCompanyId(Integer companyId) {
		if (companyId == null) {
			return null;
		}
		return phoneDao.queryByCompanyId(companyId);
	}

	@Override
	public Phone queryByTel(String tel) {
		if (StringUtils.isEmpty(tel)) {
			return null;
		}
		return phoneDao.queryByTel(tel);
	}

	@Override
	public Boolean countBalanceByAdmin(Phone phone) {
		PageDto<PhoneCostSvr> page = new PageDto<PhoneCostSvr>();
		PhoneCostSvr phoneCostSvr = new PhoneCostSvr();
		phoneCostSvr.setCompanyId(phone.getCompanyId());
		page.setSort("id");
		page.setDir("asc");
		List<PhoneCostSvr> list = phoneCostSvrDao.queryListByAdmin(	phoneCostSvr, page);

		// 电话消费
		String callFee = phoneLogDao.countCallFeeWithOutToday(phone.getTel(),phone.getCompanyId());
		if (callFee == null || StringUtils.isEmpty(callFee)) {
			callFee = "0";
		}
		Float feeCall = Float.valueOf(callFee);

		// 点击消费
		Integer i = phoneClickLogDao.countClick(phone.getCompanyId());
		if (i == null) {
			i = 0;
		}
		Float feeClick = Float.valueOf(i);
		//点击号码付费
		Integer cc=phoneCallClickLogDao.countCallClickFee(phone.getCompanyId());
		if(cc==null){
			cc=0;
		}
		Float callClick=Float.valueOf(cc);
		// 总消费
		Float totalFee = feeCall + feeClick+ callClick;
		
		// 更新用户所有充值余额费用，恢复为初始
		phoneCostSvrDao.updateLaveFull(phone.getCompanyId());
		
		for (PhoneCostSvr obj : list) {
			Float total = Float.valueOf(obj.getFee());

			Float lave = total - totalFee;

			if (lave > 0) {
				obj.setLave(Float.valueOf(String.valueOf(lave)));
				phoneCostSvrDao.updateSvr(obj);
				return true;
			} else if (lave == 0) {
				obj.setLave(Float.valueOf(String.valueOf(lave)));
				obj.setIsLack(PhoneCostSvrService.IS_LACK);
				phoneCostSvrDao.updateSvr(obj);
				return true;
			} else {
				obj.setLave(0f);
				obj.setIsLack(PhoneCostSvrService.IS_LACK);
				phoneCostSvrDao.updateSvr(obj);
				totalFee = Math.abs(lave);
			}
		}

		if (totalFee > 0) {
			phone.setBalance("欠费：" + totalFee);
			phoneDao.update(phone);
		}

		return true;
	}

	@Override
	public PageDto<PhoneDto> pageQueryList(Phone phone,
			CompanyAccount cpAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, Date from, Date to,Company searchCompany,float laveFrom,float laveTo,String csAccount,Date svrFroms,Date svrTos) throws ParseException {
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("id");
		}
		// 月消费存在 ，则当月份时间 set
		if (StringUtils.isNotEmpty(phone.getMonthFeeFrom())||StringUtils.isNotEmpty(phone.getMonthFeeTo())) {
			Date monthTo = DateUtil.getDate(new Date(), "yyyy-MM-01");
			Date monthFrom = DateUtil.getDateAfterMonths(monthTo, -1);
			phone.setMonthFrom(monthFrom);
			phone.setMonthTo(monthTo);
		}
		
		List<PhoneDto> list = phoneDao.queryAllList(phone, cpAccount,phoneCostSvr, page,searchCompany,laveFrom,laveTo,csAccount,svrFroms,svrTos);
		for (PhoneDto phoneDto : list) {
			// 查询出对应的人
			CompanyAccount companyAccount = companyAccountDao.queryAccountByCompanyId(phoneDto.getPhone().getCompanyId());
			if (companyAccount == null) {
				companyAccount = new CompanyAccount();
			}
			phoneDto.setCompanyAccount(companyAccount);

			// 统计流量
			Integer pv = analysisPpcLogDao.queryAllPvByCid(phoneDto.getPhone().getCompanyId());
			if (pv == null) {
				pv = 0;
			}
			phoneDto.setPv(pv);
			
			// 统计 当前400号码 上个月 已接电话的费用
			PhoneLog phoneLog = new PhoneLog();
			if (from == null || to == null || from.after(to)) {
				to = DateUtil.getDate(new Date(),"yyyy-MM-01");
				from = DateUtil.getDateAfterMonths(to, -1);
			}else{
				to=DateUtil.getDate(new Date(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1).getTime()-1), "yyyy-MM-dd HH:mm:ss");
			}
			phoneLog.setTel(phoneDto.getPhone().getTel());
			phoneLog.setCompanyId(phoneDto.getPhone().getCompanyId());
			phoneLog.setStartTime(from);
			phoneLog.setEndTime(to);
			String callString = phoneLogDao.countEveCallFee(phoneLog);
			if (StringUtils.isNotEmpty(callString)) {
				BigDecimal bds = new BigDecimal(callString);
				bds = bds.setScale(2, BigDecimal.ROUND_HALF_UP);
				callString = bds.toString();
			} else {
				callString = "0.00";
			}
			phoneDto.setSumEveCallFee(callString);

			// 统计 当前400号码 未接电话的个数，去重复已接号码，再去自己号码(057156633055 057156633056 057156633060) 
			Integer mc=phoneLogDao.queryDtoListCounts(phoneLog,companyAccount.getMobile());
			if(mc!=null){
				String missCall=String.valueOf(mc);
				phoneDto.setMissCall(missCall);
			}else{
				mc=0;
				phoneDto.setMissCall("0");
			}

			//统计 当前400号码 已接电话的个数
			phoneLog.setState("1");
			Integer yj=phoneLogDao.queryDtoListCount(phoneLog);
			if(yj==null){
				yj=0;
			}

			//统计某用户总电话的个数
			Integer ap=mc+yj;
			if(ap!=0){
			phoneDto.setAllPhone(String.valueOf(ap));
			}else{
				phoneDto.setAllPhone("0");
			}

			//接通率
			if(ap!=0){
				Double phoneRate=Double.valueOf(String.valueOf(yj))/Double.valueOf(String.valueOf(ap));
				BigDecimal bds=new BigDecimal(phoneRate);
				bds = bds.setScale(4, BigDecimal.ROUND_HALF_UP);
				phoneDto.setPhoneRate(String.valueOf(bds));
			}else{
				phoneDto.setPhoneRate("0.0000");
			}
			
			// 获取积分表 7天内接听率
			LdbLevel ll = ldbLevelService.queryByCompanyId(phoneDto.getPhone().getCompanyId());
			if (ll!=null) {
				phoneDto.setPhoneRateForSevenDay(""+ll.getPhoneRate());
			}

			//统计未接电话点击的费用
			Integer cc=phoneCallClickLogDao.countCallClickFee(phoneDto.getPhone().getCompanyId());
			if(cc!=null){
				Float callClick=Float.valueOf(cc);
				BigDecimal bd = new BigDecimal(callClick);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneDto.setSumCallClickFee(String.valueOf(bd));
			}

			// 公司名称
			String name = companyDao.queryCompanyNameById(phoneDto.getPhone().getCompanyId());
			Company company = new Company();
			if (StringUtils.isNotEmpty(name)) {
				company.setName(name);
				phoneDto.setCompany(company);
			} else {
				phoneDto.setCompany(company);
			}

			// 计算通话总费用
			if (StringUtils.isNotEmpty(phoneDto.getPhone().getTel())) {
				String sum = phoneLogDao.countCallFee(phoneDto.getPhone().getTel(),phoneDto.getPhone().getCompanyId());
				if (StringUtils.isNotEmpty(sum)) {
					BigDecimal bd = new BigDecimal(sum);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					sum = bd.toString();
				} else if (sum == null) {
					sum = "";
				}
				phoneDto.setSumCallFee(sum);
			}

			// 计算点击总费用
			if (phoneDto.getPhone().getCompanyId() != null) {
				phoneDto.setSumClickFee(String.valueOf(phoneClickLogDao.countClick(phoneDto.getPhone().getCompanyId())));
			}

			// 计算所有费用
			Float sumAllFee = getStringToFloat(phoneDto.getSumCallFee()) + getStringToFloat(phoneDto.getSumClickFee())+getStringToFloat(phoneDto.getSumCallClickFee());
			BigDecimal bd = new BigDecimal(sumAllFee.toString());
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			String sumAllFeeString = bd.toString();
			phoneDto.setSumAllFee(sumAllFeeString);
			
			// 计算客户用户是否 余额为零
			String lave=phoneCostSvrDao.sumLaveByCompanyId(phoneDto.getPhone().getCompanyId());
			if (lave==null) {
				lave = "0";
			}
			Integer laveInt = Double.valueOf(lave).intValue();
			if(laveInt==0){
				if (crmCompanySvrService.validateLDB(phoneDto.getPhone().getCompanyId(), CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE)) {
					phoneDto.setIsLave("1");
				}
			}
		}

		// 统计总金额
		PhoneDto dto = new PhoneDto();
		Phone newPhone = new Phone();
		CompanyAccount account = new CompanyAccount();
		Company company = new Company();
		newPhone.setAmount(phoneDao.queryAllPhoneAmount());
		dto.setPhone(newPhone);
		dto.setCompanyAccount(account);
		dto.setCompany(company);
		dto.setPv(0);
		dto.setSumCallFee("");
		dto.setSumClickFee("");

		// 统计当月已接电话的费用
//		PhoneLog phoneLog = new PhoneLog();
//		Date stTime = DateUtil.getDate(DateUtil.getDateAfterMonths(new Date(), -1),"yyyy-MM-01 00:00:00");
//		Date enTime = DateUtil.getDate(DateUtil.getDate(new Date(), "yyyy-MM-01 00:00:00"),"yyyy-MM-01 00:00:00");
//		phoneLog.setTel(dto.getPhone().getTel());
//		phoneLog.setStartTime(stTime);
//		phoneLog.setEndTime(enTime);
//		phoneLog.setState("1");
//		String callString = phoneLogDao.countCallFeeByTime(phoneLog);

		// 统计 至今以来所有 来电宝消费金额
		Float allCallFee = Float.valueOf(phoneLogDao.countAllCallFee());
		dto.setSumCallFee(String.valueOf(allCallFee));

		// 统计出来点击消费金额
		Integer al=phoneClickLogDao.countAllClick();
		if(al!=null){
			Float allClick = Float.valueOf(al);
			dto.setSumClickFee(String.valueOf(allClick));
		}else{
			dto.setSumClickFee("0.00");
		}

		//统计出来总的未接电话点击消费
		Integer ccf=phoneCallClickLogDao.sumCallFee();
		if(ccf!=null){
			Float callClickFee = Float.valueOf(ccf);
			dto.setSumCallClickFee(String.valueOf(callClickFee));
		}else{
			dto.setSumCallClickFee("0.00");
		}
		String sumAllFell = NumberUtil.formatCurrency((allCallFee + Float.valueOf(dto.getSumClickFee())+ Float.valueOf(dto.getSumCallClickFee())),"#####.00");
		dto.setSumAllFee(sumAllFell);
		List<PhoneDto> dtoList = new ArrayList<PhoneDto>();
		dtoList.add(dto);
		dtoList.addAll(list);
		page.setTotalRecords(phoneDao.queryAllListCount(phone, cpAccount,phoneCostSvr, searchCompany,laveFrom,laveTo,csAccount));
		page.setRecords(dtoList);
		return page;
	}
	
	private Float getStringToFloat(String fee) {
		if (StringUtils.isEmpty(fee)) {
			return 0f;
		}
		if ("null".equals(fee)) {
			return 0f;
		}
		return Float.valueOf(fee);
	}

	@Override
	public Integer updateSmsFee(String smsFee, Integer companyId) {
		if (companyId == null || companyId == 0) {
			return 0;
		}
		return phoneDao.updateSmsFee(smsFee, companyId);
	}

	@Override
	public Integer updateAmountByCompanyId(String amount, Integer companyId) {
		if (companyId == 0 || companyId == null) {
			return 0;
		}
		return phoneDao.updateAmountByCompanyId(amount, companyId);
	}
	/**
	 * 开通易企通后台黑名单状态 status：1为开启
	 * @param tel
	 * @return
	 */
	@Override
	public Integer openStatus(String tel) {
	  String longStr = String.valueOf(new Date().getTime());
	
		map.put("sn", longStr);
		map.put("tel", tel);
		map.put("status", "1");
		String urlStr1 = "";
		for (String key : map.keySet()) {
			urlStr1 = urlStr1 + key + "=" + map.get(key);
		}
			urlStr1 += apiKey;

			try {
				urlStr1 = MD5.encode(urlStr1, MD5.LENGTH_32);
					} catch (NoSuchAlgorithmException e) {
					} catch (UnsupportedEncodingException e) {
			}

		NameValuePair[] data1 = new NameValuePair[] {
		new BasicNameValuePair("sn", longStr),
		new BasicNameValuePair("sign", urlStr1),
		new BasicNameValuePair("userkey", userKey),
		new BasicNameValuePair("tel", tel),
		new BasicNameValuePair("status", "1")
		};
		  try{
			  HttpUtils.getInstance().httpPost(BLANK_URL_OPEN, data1,HttpUtils.CHARSET_UTF8);
		    } catch (Exception ex) {
		        //加入处理错误程式码
		    } 
		return 1;
   }
	@Override
	public Integer updateBlickList(String tel){
		/**
		 * 搜索400黑名单 将其加入到新开通的400号码中
		 */
		// 搜索出所有的黑名单400号码
		String longStr = String.valueOf(new Date().getTime());
		String phoneString="";
		PageDto<PhoneBlacklist> page = new PageDto<PhoneBlacklist>();
		page.setPageSize(100); // 处理100个黑名单
		page=phoneBlacklistService.page(new PhoneBlacklist(), page);
		List<PhoneBlacklist> list= page.getRecords();
		if(list !=null){
			PhoneBlacklist phoneBlacklist=new PhoneBlacklist();
			phoneBlacklist =list.get(0);
			phoneString=phoneBlacklist.getPhone();
	
			if(list.get(1)!=null){
				for(int i=1;i<list.size();i++){
					phoneBlacklist=list.get(i);
					if(phoneBlacklist.getPhone()!=null){
						phoneString= phoneString+","+phoneBlacklist.getPhone();
					}	
				}
			}
			map.put("sn", longStr);
			map.put("tel", tel);
			map.put("phone", phoneString);

			String urlStr = "";
			for (String key : map.keySet()) {
				urlStr = urlStr + key + "=" + map.get(key);
			}
			urlStr += apiKey;

			try {
				urlStr = MD5.encode(urlStr, MD5.LENGTH_32);
				} catch (NoSuchAlgorithmException e) {
				} catch (UnsupportedEncodingException e) {
			}
		

			NameValuePair[] data = new NameValuePair[] {
				new BasicNameValuePair("sn", longStr),
				new BasicNameValuePair("sign", urlStr),
				new BasicNameValuePair("userkey", userKey),
				new BasicNameValuePair("tel", tel),
				new BasicNameValuePair("phone", phoneString)
				};
			 try{
				 HttpUtils.getInstance().httpPost(BLANK_URL_ADD, data,HttpUtils.CHARSET_UTF8);
			    } catch (Exception ex) {
			        //加入处理错误程式码
			    } 
		}
		return 1;
	}

	@Override
	public List<PhoneDto> pageQueryListl(Phone phone,
			CompanyAccount cpAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, Date from, Date to,Company searchCompany,float laveFrom,float laveTo,String csAccount)throws ParseException {
		List<PhoneDto> list = phoneDao.queryAllListl(phone, cpAccount,phoneCostSvr, page,searchCompany,laveFrom,laveTo,csAccount);
		for (PhoneDto phoneDto : list) {
			// 查询出公司信息
			Company company = companyDao.queryCompanyById(phoneDto.getPhone().getCompanyId());
			if (company == null) {
				company = new Company();
			}
			phoneDto.setCompany(company);
            CompanyAccount companyAccount=companyAccountDao.queryAccountByCompanyId(phoneDto.getPhone().getCompanyId());
            if (companyAccount == null) {
            	companyAccount = new CompanyAccount();
			}
			phoneDto.setCompanyAccount(companyAccount);
			 //余额
			String lave=phoneCostSvrDao.sumLaveByCompanyId(phoneDto.getPhone().getCompanyId());
			if(StringUtils.isNotEmpty(lave)){
				BigDecimal bdl = new BigDecimal(lave);
				bdl = bdl.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneDto.getPhone().setBalance(bdl.toString());
			}else{
				phoneDto.getPhone().setBalance("0.00");
			}
			//来电宝最新的服务信息
			PhoneCostSvr phoneCSvr=phoneCostSvrDao.queryPhoneService(phoneDto.getPhone().getCompanyId());
			if(phoneCSvr!=null){
				phoneDto.getCompany().setGmtCreated(phoneCSvr.getGmtCreated());
			}
			//是否过期
			if("0.00".equals(phoneDto.getPhone().getBalance())||"信息已过期".equals(phoneDto.getPhone().getFrontTel())){
				phoneDto.setIsOut("是");
			}else{
				phoneDto.setIsOut("否");
			}
			// 统计每月已接电话的费用
			PhoneLog phoneLog = new PhoneLog();
			if (from == null || to == null || from.after(to)) {
				from = DateUtil.getDate(DateUtil.getDateAfterDays(new Date(), -1),"yyyy-MM-dd");
				to = DateUtil.getDate(DateUtil.getDateAfterDays(new Date(), -1),"yyyy-MM-dd 23:59:59");
			}
			phoneLog.setTel(phoneDto.getPhone().getTel());
			phoneLog.setStartTime(from);
			phoneLog.setEndTime(to);
			phoneLog.setState("1");
			phoneLog.setCallSn("1");
			String callString = phoneLogDao.countEveCallFee(phoneLog);
			if (StringUtils.isNotEmpty(callString)) {
				BigDecimal bds = new BigDecimal(callString);
				bds = bds.setScale(2, BigDecimal.ROUND_HALF_UP);
				callString = bds.toString();
			} else {
				callString = "0.00";
			}
			phoneDto.setSumEveCallFee(callString);
			// 统计流量
			AnalysisPpcLog analysisPpcLog=new AnalysisPpcLog();
			analysisPpcLog.setCid(phoneDto.getPhone().getCompanyId());
			analysisPpcLog.setFrom(from);
			  analysisPpcLog.setTo(to);
			Integer pv = analysisPpcLogDao.sumPvByTimeACid(analysisPpcLog);
			if (pv == null) {
				pv = 0;
			}
			phoneDto.setPv(pv);
			//统计某用户未接电话的个数，去重复，去自己
			Integer mc=phoneLogDao.queryDtoListCounts(phoneLog,companyAccount.getMobile());
			if(mc!=null){
				String missCall=String.valueOf(mc);
				phoneDto.setMissCall(missCall);
			}else{
				mc=0;
				phoneDto.setMissCall("0");
			}
			//统计某用户已接电话的个数
			phoneLog.setState("1");
			Integer yj=phoneLogDao.queryDtoListCount(phoneLog);
			if(yj==null){
				yj=0;
			}
			phoneDto.getCompany().setAddress(String.valueOf(yj));
			//统计某用户总电话的个数
			Integer ap=mc+yj;
			if(ap!=0){
			phoneDto.setAllPhone(String.valueOf(ap));
			}else{
				phoneDto.setAllPhone("0");
			}
			//接通率
			if(ap!=0){
				Double phoneRate=Double.valueOf(String.valueOf(yj))/Double.valueOf(String.valueOf(ap))*100;
				BigDecimal bds=new BigDecimal(phoneRate);
				bds = bds.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneDto.setPhoneRate(String.valueOf(bds)+"%");
			}else{
				phoneDto.setPhoneRate("0.00%");
			}
            //统计未接电话点击的费用
			PhoneCallClickLog phoneCallClickLog=new PhoneCallClickLog();
			phoneCallClickLog.setCompanyId(phoneDto.getPhone().getCompanyId());
			phoneCallClickLog.setFrom(from);
			phoneCallClickLog.setTo(to);
			Integer cc=phoneCallClickLogDao.sumCallClickFee(phoneCallClickLog);
			if(cc!=null){
				Float callClick=Float.valueOf(cc);
				BigDecimal bd = new BigDecimal(callClick);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneDto.setSumCallClickFee(String.valueOf(bd));
			}	
		

			// 计算点击总费用
			PhoneClickLog phoneClickLog=new PhoneClickLog();
			phoneClickLog.setCompanyId(phoneDto.getPhone().getCompanyId());
			phoneClickLog.setFrom(from);
			phoneClickLog.setTo(to);
			if (phoneDto.getPhone().getCompanyId() != null) {
				phoneDto.setSumClickFee(String.valueOf(phoneClickLogDao.queryCountClick(phoneClickLog)));
			}

			// 计算所有费用
			Float sumAllFee = getStringToFloat(phoneDto.getSumEveCallFee())
					+ getStringToFloat(phoneDto.getSumClickFee())+getStringToFloat(phoneDto.getSumCallClickFee());
			BigDecimal bd = new BigDecimal(sumAllFee.toString());
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			String sumAllFeeString = bd.toString();
			phoneDto.setSumAllFee(sumAllFeeString);

		}
		List<PhoneDto> dtoList = new ArrayList<PhoneDto>();
		PhoneDto pd=new PhoneDto();
		Company company=new Company();
		Phone pho=new Phone();
		pd.setPhone(pho);
		pd.setCompany(company);
		pd.getPhone().setBalance("总消费：");
		float sumFee=0f;
		for(PhoneDto li:list){
			sumFee=sumFee+getStringToFloat(li.getSumAllFee());
		}
		BigDecimal bd = new BigDecimal(sumFee);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		pd.setSumAllFee(bd.toString());;;
		dtoList.addAll(list);
		dtoList.add(pd);
		return dtoList;
	}

	@Override
	public PageDto<PhoneDto> pageQueryBsList(Phone phone,
			CompanyAccount cpAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, Date from, Date to, Company searchCompany,
			float laveFrom, float laveTo, String csAccount)
			throws ParseException {
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("id");
		}
		// 月消费存在 ，则当月份时间 set
		if (StringUtils.isNotEmpty(phone.getMonthFeeFrom())
				|| StringUtils.isNotEmpty(phone.getMonthFeeTo())) {
			Date monthFrom = DateUtil.getDate(new Date(), "yyyy-MM-01");
			Date monthTo = DateUtil.getDateAfterMonths(monthFrom, 1);
			phone.setMonthFrom(monthFrom);
			phone.setMonthTo(monthTo);
		}
		String fromTime = "";
		String toTime = "";
		if (from != null) {
			fromTime = DateUtil.toString(from, "yyyy-MM-dd");
		}
		if (to != null) {
			toTime = DateUtil.toString(DateUtil.getDateAfterDays(to, 1),
					"yyyy-MM-dd");
		}
		List<PhoneDto> list = phoneDao.queryAllBsList(phone, cpAccount,
				phoneCostSvr, page, searchCompany, laveFrom, laveTo, csAccount,
				fromTime, toTime);
		for (PhoneDto phoneDto : list) {
			// 查询出对应的人
			CompanyAccount companyAccount = companyAccountDao
					.queryAccountByCompanyId(phoneDto.getPhone().getCompanyId());
			if (companyAccount == null) {
				companyAccount = new CompanyAccount();
			}
			phoneDto.setCompanyAccount(companyAccount);

			// 统计流量
			Integer pv = analysisPpcLogDao.queryAllPvByCid(phoneDto.getPhone()
					.getCompanyId());
			if (pv == null) {
				pv = 0;
			}
			phoneDto.setPv(pv);

			// 统计 当前400号码 上个月 已接电话的费用
			PhoneLog phoneLog = new PhoneLog();
			if (from == null || to == null || from.after(to)) {
				to = DateUtil.getDate(new Date(), "yyyy-MM-01");
				from = DateUtil.getDateAfterMonths(to, -1);
			}
			phoneLog.setTel(phoneDto.getPhone().getTel());
			phoneLog.setCompanyId(phoneDto.getPhone().getCompanyId());
			phoneLog.setStartTime(from);
			phoneLog.setEndTime(to);
			String callString = phoneLogDao.countEveCallFee(phoneLog);
			if (StringUtils.isNotEmpty(callString)) {
				BigDecimal bds = new BigDecimal(callString);
				bds = bds.setScale(2, BigDecimal.ROUND_HALF_UP);
				callString = bds.toString();
			} else {
				callString = "0.00";
			}
			phoneDto.setSumEveCallFee(callString);

			// 统计 当前400号码 未接电话的个数，去重复已接号码，再去自己号码(057156633055 057156633056
			// 057156633060)
			Integer mc = phoneLogDao.queryDtoListCounts(phoneLog,
					companyAccount.getMobile());
			if (mc != null) {
				String missCall = String.valueOf(mc);
				phoneDto.setMissCall(missCall);
			} else {
				mc = 0;
				phoneDto.setMissCall("0");
			}

			// 统计 当前400号码 已接电话的个数
			phoneLog.setState("1");
			Integer yj = phoneLogDao.queryDtoListCount(phoneLog);
			if (yj == null) {
				yj = 0;
			}

			// 统计某用户总电话的个数
			Integer ap = mc + yj;
			if (ap != 0) {
				phoneDto.setAllPhone(String.valueOf(ap));
			} else {
				phoneDto.setAllPhone("0");
			}

			// 接通率
			if (ap != 0) {
				Double phoneRate = Double.valueOf(String.valueOf(yj))
						/ Double.valueOf(String.valueOf(ap));
				BigDecimal bds = new BigDecimal(phoneRate);
				bds = bds.setScale(4, BigDecimal.ROUND_HALF_UP);
				phoneDto.setPhoneRate(String.valueOf(bds));
			} else {
				phoneDto.setPhoneRate("0.0000");
			}

			// 统计未接电话点击的费用
			Integer cc = phoneCallClickLogDao.countCallClickFee(phoneDto
					.getPhone().getCompanyId());
			if (cc != null) {
				Float callClick = Float.valueOf(cc);
				BigDecimal bd = new BigDecimal(callClick);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneDto.setSumCallClickFee(String.valueOf(bd));
			}

			// 公司名称
			String name = companyDao.queryCompanyNameById(phoneDto.getPhone()
					.getCompanyId());
			Company company = new Company();
			if (StringUtils.isNotEmpty(name)) {
				company.setName(name);
				phoneDto.setCompany(company);
			} else {
				phoneDto.setCompany(company);
			}

			// 计算通话总费用
			if (StringUtils.isNotEmpty(phoneDto.getPhone().getTel())) {
				String sum = phoneLogDao.countCallFee(phoneDto.getPhone()
						.getTel(), phoneDto.getPhone().getCompanyId());
				if (StringUtils.isNotEmpty(sum)) {
					BigDecimal bd = new BigDecimal(sum);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					sum = bd.toString();
				} else if (sum == null) {
					sum = "";
				}
				phoneDto.setSumCallFee(sum);
			}

			// 计算点击总费用
			if (phoneDto.getPhone().getCompanyId() != null) {
				phoneDto.setSumClickFee(String.valueOf(phoneClickLogDao
						.countClick(phoneDto.getPhone().getCompanyId())));
			}

			// 计算所有费用
			Float sumAllFee = getStringToFloat(phoneDto.getSumCallFee())
					+ getStringToFloat(phoneDto.getSumClickFee())
					+ getStringToFloat(phoneDto.getSumCallClickFee());
			BigDecimal bd = new BigDecimal(sumAllFee.toString());
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			String sumAllFeeString = bd.toString();
			phoneDto.setSumAllFee(sumAllFeeString);
		}

		// 统计总金额
		PhoneDto dto = new PhoneDto();
		Phone newPhone = new Phone();
		CompanyAccount account = new CompanyAccount();
		Company company = new Company();
		newPhone.setAmount(phoneDao.queryAllPhoneAmount());
		dto.setPhone(newPhone);
		dto.setCompanyAccount(account);
		dto.setCompany(company);
		dto.setPv(0);
		dto.setSumCallFee("");
		dto.setSumClickFee("");

		// 统计当月已接电话的费用
		// PhoneLog phoneLog = new PhoneLog();
		// Date stTime = DateUtil.getDate(DateUtil.getDateAfterMonths(new
		// Date(), -1),"yyyy-MM-01 00:00:00");
		// Date enTime = DateUtil.getDate(DateUtil.getDate(new Date(),
		// "yyyy-MM-01 00:00:00"),"yyyy-MM-01 00:00:00");
		// phoneLog.setTel(dto.getPhone().getTel());
		// phoneLog.setStartTime(stTime);
		// phoneLog.setEndTime(enTime);
		// phoneLog.setState("1");
		// String callString = phoneLogDao.countCallFeeByTime(phoneLog);

		// 统计 至今以来所有 来电宝消费金额
		Float allCallFee = Float.valueOf(phoneLogDao.countAllCallFee());
		dto.setSumCallFee(String.valueOf(allCallFee));

		// 统计出来点击消费金额
		Integer al = phoneClickLogDao.countAllClick();
		if (al != null) {
			Float allClick = Float.valueOf(al);
			dto.setSumClickFee(String.valueOf(allClick));
		} else {
			dto.setSumClickFee("0.00");
		}

		// 统计出来总的未接电话点击消费
		Integer ccf = phoneCallClickLogDao.sumCallFee();
		if (ccf != null) {
			Float callClickFee = Float.valueOf(ccf);
			dto.setSumCallClickFee(String.valueOf(callClickFee));
		} else {
			dto.setSumCallClickFee("0.00");
		}
		String sumAllFell = NumberUtil.formatCurrency(
				(allCallFee + Float.valueOf(dto.getSumClickFee()) + Float
						.valueOf(dto.getSumCallClickFee())), "#####.00");
		dto.setSumAllFee(sumAllFell);
		List<PhoneDto> dtoList = new ArrayList<PhoneDto>();
		dtoList.add(dto);
		dtoList.addAll(list);
		page.setTotalRecords(phoneDao.queryAllBsListCount(phone, cpAccount,
				phoneCostSvr, searchCompany, laveFrom, laveTo, csAccount,
				fromTime, toTime));
		page.setRecords(dtoList);
		return page;
	}

	@Override
	public PageDto<Phone> pageListForLibrary(Phone phone, PageDto<Phone> page) {
		page = pageList(phone, page);
		for (Phone obj : page.getRecords()) {
			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(obj.getCompanyId());
			if (ca!=null) {
				obj.setContact(ca.getContact());
				obj.setEmail(ca.getEmail());
			}
			obj.setBalance(phoneCostSvrDao.sumLaveByCompanyId(obj.getCompanyId()));
		}
		return page;
	}

	@Override
	public Integer closePhone(Integer companyId) {
		if (companyId==null) {
			return 0;
		}
		return phoneDao.updateClose(EXPIRED_CONTENT, companyId);
	}

	@Override
	public PageDto<PhoneDto> pagePhoneCallFee(PageDto<PhoneDto>page,Date from,Date to) throws ParseException{
		
		// 月消费存在 ，则当月份时间 set
		if (from == null || to == null || from.after(to)) {
			to = DateUtil.getDate(new Date(),"yyyy-MM-01");
			from = DateUtil.getDateAfterMonths(to, -1);
		}else{
			to=DateUtil.getDate(new Date(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1).getTime()-1), "yyyy-MM-dd HH:mm:ss");
		}
		String froms=DateUtil.toString(from, "yyyy-MM-dd");
		String tos=DateUtil.toString(DateUtil.getDateAfterDays(to, 1), "yyyy-MM-dd");
		List<PhoneDto> list = phoneDao.pagePhoneCallFee( page,froms,tos);
		for (PhoneDto phoneDto : list) {
			// 查询出对应的人
			CompanyAccount companyAccount = companyAccountDao.queryAccountByCompanyId(phoneDto.getPhone().getCompanyId());
			if (companyAccount == null) {
				companyAccount = new CompanyAccount();
			}
			phoneDto.setCompanyAccount(companyAccount);

			// 统计流量
			Integer pv = analysisPpcLogDao.queryAllPvByCid(phoneDto.getPhone().getCompanyId());
			if (pv == null) {
				pv = 0;
			}
			phoneDto.setPv(pv);
			
			// 统计 当前400号码 上个月 已接电话的费用
			PhoneLog phoneLog = new PhoneLog();
			
			phoneLog.setTel(phoneDto.getPhone().getTel());
			phoneLog.setCompanyId(phoneDto.getPhone().getCompanyId());
			phoneLog.setStartTime(from);
			phoneLog.setEndTime(to);
			String callString = phoneLogDao.countEveCallFee(phoneLog);
			if (StringUtils.isNotEmpty(callString)) {
				BigDecimal bds = new BigDecimal(callString);
				bds = bds.setScale(2, BigDecimal.ROUND_HALF_UP);
				callString = bds.toString();
			} else {
				callString = "0.00";
			}
			phoneDto.setSumEveCallFee(callString);

			// 统计 当前400号码 未接电话的个数，去重复已接号码，再去自己号码(057156633055 057156633056 057156633060) 
			Integer mc=phoneLogDao.queryDtoListCounts(phoneLog,companyAccount.getMobile());
			if(mc!=null){
				String missCall=String.valueOf(mc);
				phoneDto.setMissCall(missCall);
			}else{
				mc=0;
				phoneDto.setMissCall("0");
			}

			//统计 当前400号码 已接电话的个数
			phoneLog.setState("1");
			Integer yj=phoneLogDao.queryDtoListCount(phoneLog);
			if(yj==null){
				yj=0;
			}

			//统计某用户总电话的个数
			Integer ap=mc+yj;
			if(ap!=0){
			phoneDto.setAllPhone(String.valueOf(ap));
			}else{
				phoneDto.setAllPhone("0");
			}

			//接通率
			if(ap!=0){
				Double phoneRate=Double.valueOf(String.valueOf(yj))/Double.valueOf(String.valueOf(ap));
				BigDecimal bds=new BigDecimal(phoneRate);
				bds = bds.setScale(4, BigDecimal.ROUND_HALF_UP);
				phoneDto.setPhoneRate(String.valueOf(bds));
			}else{
				phoneDto.setPhoneRate("0.0000");
			}

			//统计未接电话点击的费用
			Integer cc=phoneCallClickLogDao.countCallClickFee(phoneDto.getPhone().getCompanyId());
			if(cc!=null){
				Float callClick=Float.valueOf(cc);
				BigDecimal bd = new BigDecimal(callClick);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				phoneDto.setSumCallClickFee(String.valueOf(bd));
			}

			// 公司名称
			String name = companyDao.queryCompanyNameById(phoneDto.getPhone().getCompanyId());
			Company company = new Company();
			if (StringUtils.isNotEmpty(name)) {
				company.setName(name);
				phoneDto.setCompany(company);
			} else {
				phoneDto.setCompany(company);
			}

			// 计算通话总费用
			if (StringUtils.isNotEmpty(phoneDto.getPhone().getTel())) {
				String sum = phoneLogDao.countCallFee(phoneDto.getPhone().getTel(),phoneDto.getPhone().getCompanyId());
				if (StringUtils.isNotEmpty(sum)) {
					BigDecimal bd = new BigDecimal(sum);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					sum = bd.toString();
				} else if (sum == null) {
					sum = "";
				}
				phoneDto.setSumCallFee(sum);
			}

			// 计算点击总费用
			if (phoneDto.getPhone().getCompanyId() != null) {
				phoneDto.setSumClickFee(String.valueOf(phoneClickLogDao.countClick(phoneDto.getPhone().getCompanyId())));
			}

			// 计算所有费用
			Float sumAllFee = getStringToFloat(phoneDto.getSumCallFee()) + getStringToFloat(phoneDto.getSumClickFee())+getStringToFloat(phoneDto.getSumCallClickFee());
			BigDecimal bd = new BigDecimal(sumAllFee.toString());
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			String sumAllFeeString = bd.toString();
			phoneDto.setSumAllFee(sumAllFeeString);
			
			// 计算客户用户是否 余额为零
			String lave=phoneCostSvrDao.sumLaveByCompanyId(phoneDto.getPhone().getCompanyId());
			if (lave==null) {
				lave = "0";
			}
			Integer laveInt = Double.valueOf(lave).intValue();
			if(laveInt==0){
				if (crmCompanySvrService.validateLDB(phoneDto.getPhone().getCompanyId(), CrmCompanySvrService.LDB_CODE, CrmCompanySvrService.LDB_FIVE_CODE)) {
					phoneDto.setIsLave("1");
				}
			}
		}
		page.setRecords(list);
		page.setTotalRecords(phoneDao.queryListCount(null));
		return page;
	}
}
