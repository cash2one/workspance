package com.ast.ast1949.service.phone.impl;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneLogDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.phone.PhoneBlacklistDao;
import com.ast.ast1949.persist.phone.PhoneCallClickLogDao;
import com.ast.ast1949.persist.phone.PhoneClickLogDao;
import com.ast.ast1949.persist.phone.PhoneDao;
import com.ast.ast1949.persist.phone.PhoneLogDao;
import com.ast.ast1949.service.phone.PhoneLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 *	author:kongsj
 *	date:2013-7-13
 */
@Component("phoneLogService")
public class PhoneLogServiceImpl implements PhoneLogService{

	@Resource
	private PhoneLogDao phoneLogDao;
	@Resource
	private PhoneClickLogDao phoneClickLogDao;
	@Resource
	private PhoneService phoneService;
	@Resource
	private PhoneCallClickLogDao phoneCallClickLogDao;
	@Resource
	private PhoneDao phoneDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private PhoneBlacklistDao 	phoneBlacklistDao;
	
	@Override
	public Integer insert(PhoneLog phoneLog) {
		if(StringUtils.isEmpty(phoneLog.getCallSn())){
			return 0;
		}
		PhoneLog obj = phoneLogDao.queryByCallSn(phoneLog.getCallSn());
		if(obj!=null){
			return 0;
		}
		return phoneLogDao.insert(phoneLog);
	}

	@Override
	public PageDto<PhoneLog> pageList(PhoneLog phoneLog, PageDto<PhoneLog> page) {
		page.setTotalRecords(phoneLogDao.queryListCount(phoneLog));
		page.setRecords(phoneLogDao.queryList(phoneLog, page));
		return page;
	}
	
	@Override
	public PageDto<PhoneLogDto> pageListByDto(PhoneLog phoneLog, PageDto<PhoneLogDto> page) {
		page.setTotalRecords(phoneLogDao.queryListCount(phoneLog));//总数
		List<PhoneLogDto> pList = new ArrayList<PhoneLogDto>();
		List<PhoneLogDto> resultList=phoneLogDao.queryDtoListForFront(phoneLog, page);
		for (PhoneLogDto phoneLogDto : resultList) {
			PhoneLogDto dto = new PhoneLogDto();
			dto.setPhoneLog(phoneLogDto.getPhoneLog());
			//获得来电号码
			String callerId = phoneLogDto.getPhoneLog().getCallerId();
			//判断是否在黑名单里(带有通话记录id)
			Integer i = phoneBlacklistDao.isExistByPhone(callerId,phoneLogDto.getPhoneLog().getId());
			if(i>0){
				dto.setIsBlack(1);
			}else{
				dto.setIsBlack(0);
			}
			pList.add(dto);
		}
		page.setRecords(pList);
		return page;
	}

	@Override
	public String countBalance(Phone phone) {
		if(phone==null){
			return "0";
		}
		if(StringUtils.isEmpty(phone.getTel())&&phone.getCompanyId()!=null){
			phone = phoneService.queryByCompanyId(phone.getCompanyId());
		}
		if(phone==null){
			return "0";
		}
		Float total = Float.valueOf(phone.getAmount());
		String callFee = phoneLogDao.countCallFee(phone.getTel(),phone.getCompanyId());
		if(callFee==null||StringUtils.isEmpty(callFee)){
			callFee = "0";
		}
		Float fee = Float.valueOf(callFee);
		Integer i = phoneClickLogDao.countClick(phone.getCompanyId());
		if(i==null){
			i= 0; 
		}
		Float feeClick = Float.valueOf(i);
		Integer cc=phoneCallClickLogDao.countCallClickFee(phone.getCompanyId());
		if(cc==null){
			cc=0;
		}
		Float callClick = Float.valueOf(cc);
		Float ft=total-fee-feeClick-callClick;
		BigDecimal bd = new BigDecimal(ft);
		bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bd);
	}

	@Override
	public PageDto<PhoneLogDto> pageDtoList(PhoneLog phoneLog,
			PageDto<PhoneLogDto> page) {
		//用于显示差值
		List<PhoneLogDto> dtoList=phoneLogDao.queryDtoList(phoneLog, page);
		long countDiffMinute=0;
		for (PhoneLogDto phoneLogDto : dtoList) {
			//判断是否在黑名单里(带有通话记录id)
			Integer i = phoneBlacklistDao.isExistByPhone(phoneLogDto.getPhoneLog().getCallerId(),phoneLogDto.getPhoneLog().getId());
			if(i>0){
				phoneLogDto.setIsBlack(1);
			}
			//计算出通话时间差
			try {
				Date endTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getEndTime(), "yyyy-MM-dd HH:mm:ss");
				Date startTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getStartTime(), "yyyy-MM-dd HH:mm:ss");
				//获取秒
				long diffS=(endTime.getTime()-startTime.getTime())/1000;
				long diffTime=((diffS/60)+(diffS%60==0?0:1));
				phoneLogDto.setDiffMinute(String.valueOf(diffTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//来电地区
			if(!"未知".equals(phoneLogDto.getPhoneLog().getProvince())){
				String address=phoneLogDto.getPhoneLog().getProvince()+" "+phoneLogDto.getPhoneLog().getCity();
				phoneLogDto.getPhoneLog().setAddress(address);
			}else{
				phoneLogDto.getPhoneLog().setAddress("未知");
			}
			if("0".equals(phoneLogDto.getPhoneLog().getCallSn())){
				phoneLogDto.getPhoneLog().setAddress("月租");
			}
			
		}
		//用于统计所有的通话时间
		List<PhoneLogDto> phonedtoList=phoneLogDao.queryDtoList(phoneLog, null);	
		for (PhoneLogDto phoneLogDto : phonedtoList) {
			if ("0".equals(phoneLogDto.getPhoneLog().getState())) {
				continue;
			}
			//计算出通话时间差
			try {
				Date endTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getEndTime(), "yyyy-MM-dd HH:mm:ss");
				Date startTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getStartTime(), "yyyy-MM-dd HH:mm:ss");
				//获取秒
				long diffS=(endTime.getTime()-startTime.getTime())/1000;
				long diffTime=((diffS/60)+(diffS%60==0?0:1));
				countDiffMinute+=diffTime;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		//用于统计
		PhoneLog pLog=new PhoneLog();
		pLog.setTel(phoneLog.getTel());
		String callString=phoneLogDao.countCallFeeByTime(phoneLog);
		if(StringUtils.isNotEmpty(callString)){
			BigDecimal bd = new BigDecimal(callString);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			String sumAllFell=bd.toString();
			pLog.setCallFee(sumAllFell);	
		}else{
			pLog.setCallFee(callString);
		}
		
		//统计电话个数和月租个数
		if(StringUtils.isNotEmpty(phoneLog.getCallSn()) && phoneLog.getCallSn().equals("0")){
			pLog.setCallerId("月租数:"+String.valueOf(phoneLogDao.queryPhoneLogIsCount(phoneLog)));	
		}else{
			pLog.setCallerId("电话个数:"+String.valueOf(phoneLogDao.queryPhoneLogListCount(phoneLog)));
		}
		PhoneLogDto phoneLogDto=new PhoneLogDto();
		phoneLogDto.setPhoneLog(pLog);
		phoneLogDto.setDiffMinute(String.valueOf(countDiffMinute));
		dtoList.add(phoneLogDto);
		
		page.setTotalRecords(phoneLogDao.queryDtoListCount(phoneLog));
		page.setRecords(dtoList);
		return page;
	}
	
	@Override
	public PageDto<PhoneLogDto> queryAllFee(PhoneLog phoneLog,PageDto<PhoneLogDto> page) {
		List<PhoneLogDto> list=new ArrayList<PhoneLogDto>();
		 //查询出总的消费明细不包括月租
		PhoneLogDto phoneLogDto=new PhoneLogDto();
		phoneLog.setCallSn("1");
		String allFee=phoneLogDao.countCallFeeByCallSn(phoneLog);
		phoneLogDto.setAllFee(allFee);
		 //查询出总的月租
		phoneLog.setCallSn("0");
		String allMFee=phoneLogDao.countCallFeeByCallSn(phoneLog);
		phoneLogDto.setAllMFee(allMFee);
		//查询出总的
		String countAllFell=phoneLogDao.countAllCallFee();
		phoneLogDto.setCountcallFee(countAllFell);
		list.add(phoneLogDto);
		page.setRecords(list);
		page.setTotalRecords(list.size());
		return page;
	}

	@Override
	public PageDto<PhoneLogDto> queryPhoneCost(PhoneLog phoneLog,
			PageDto<PhoneLogDto> page) {
		//查询出通话成本
		long countDiffMinute=0;
		List<PhoneLogDto> dtoList=new ArrayList<PhoneLogDto>();
		PhoneLogDto phoneLogDTO=new PhoneLogDto();
		phoneLog.setCallSn("1");
		phoneLog.setState("1");
		List<PhoneLogDto> phonedtoList=phoneLogDao.queryDtoList(phoneLog, null);
		for (PhoneLogDto phoneLogDto : phonedtoList) {
			//计算出通话时间差
			try {
				Date endTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getEndTime(), "yyyy-MM-dd HH:mm:ss");
				Date startTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getStartTime(), "yyyy-MM-dd HH:mm:ss");
				//获取秒
				long diffS=(endTime.getTime()-startTime.getTime())/1000;
				long diffTime=((diffS/60)+(diffS%60==0?0:1));
				countDiffMinute+=diffTime;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		Double sumPhoneCose=countDiffMinute * 0.15;
		phoneLogDTO.setAllFee(parseDoubleValue(sumPhoneCose));
		phoneLogDTO.setStringFellName("通话成本");
		dtoList.add(phoneLogDTO);
		
		//月租成本
		phoneLog.setCallSn("0");
		String mFee=phoneLogDao.countCallFeeByTime(phoneLog); 
		PhoneLogDto phoneLogDTO1=new PhoneLogDto();
		if(StringUtils.isEmpty(mFee)){
			mFee="0";
		}
		phoneLogDTO1.setAllFee(mFee);
		phoneLogDTO1.setStringFellName("月租成本");
		dtoList.add(phoneLogDTO1);
		
		//分机未接成本
		PhoneLogDto phoneLogDTO2=new PhoneLogDto();
		//统计分机未接的通话分钟
		long countPhoneMinute=0;
		
		phoneLog.setCallSn("1");
		phoneLog.setState("0");
		List<PhoneLogDto> phoneList=phoneLogDao.queryDtoList(phoneLog, null);
		for (PhoneLogDto phoneLogDto : phoneList) {
			//计算出通话时间差
			try {
				Date endTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getEndTime(), "yyyy-MM-dd HH:mm:ss");
				Date startTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getStartTime(), "yyyy-MM-dd HH:mm:ss");
				//获取秒
				long diffS=(endTime.getTime()-startTime.getTime())/1000;
				long diffTime=((diffS/60)+(diffS%60==0?0:1));
				countPhoneMinute+=diffTime;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		Double sumPhoneFee=countPhoneMinute * 0.15;
		phoneLogDTO2.setAllFee(parseDoubleValue(sumPhoneFee));
		phoneLogDTO2.setStringFellName("分机未接成本");
		dtoList.add(phoneLogDTO2);
		
		//分机月租成本
		PhoneLogDto phoneLogDTO4=new PhoneLogDto();
		phoneLog.setCallerId("0");
		phoneLog.setState("1");
		Integer countTelRent =	phoneLogDao.queryCountTelRentByPhoneLog(phoneLog);
		Integer sumTelRent =countTelRent*2; 
		phoneLogDTO4.setAllFee(parseDoubleValue(Double.valueOf(String.valueOf(sumTelRent))));
		phoneLogDTO4.setStringFellName("分机月租");
		dtoList.add(phoneLogDTO4);
		//总计
		PhoneLogDto phoneLogDTO3=new PhoneLogDto();
		Double sumAllFee=(sumPhoneCose+sumPhoneFee+Double.valueOf(mFee)+Double.valueOf(String.valueOf(sumTelRent)));
		phoneLogDTO3.setAllFee(parseDoubleValue(sumAllFee));
		phoneLogDTO3.setStringFellName("总计");
		dtoList.add(phoneLogDTO3);
		
		page.setRecords(dtoList);
		page.setTotalRecords(4);
		return page;
	}

	@Override
	public PageDto<PhoneLogDto> queryCallPhoneRate(PhoneLog phoneLog,
			PageDto<PhoneLogDto> page) {
		
		List<PhoneLogDto> dtoList=new ArrayList<PhoneLogDto>();
		
		//查询出已接电话
		PhoneLogDto phoneLogDto=new PhoneLogDto();
		phoneLog.setCallSn("1");
		phoneLog.setState("1");
		Integer countPhoneLog=phoneLogDao.queryDtoListCount(phoneLog);
		phoneLogDto.setStringFellName("已接电话");
		phoneLogDto.setAllFee(String.valueOf(countPhoneLog));
		dtoList.add(phoneLogDto);
		
		//查询出未接电话(主机+分机)
		PhoneLogDto phoneLogDto2=new PhoneLogDto();
		phoneLog.setCallSn("1");
		phoneLog.setState("0");
		Integer countMissCall=0;
		if(phoneLog.getTel()!=null){
			Phone phone=phoneDao.queryByTel(phoneLog.getTel());
			CompanyAccount ca=companyAccountDao.queryAccountByCompanyId(phone.getCompanyId());
			if (ca!=null) {
				countMissCall=phoneLogDao.queryDtoListCounts(phoneLog,ca.getMobile());
			}
		}else{
			countMissCall=phoneLogDao.queryDtoListCount(phoneLog);
		}
		phoneLogDto2.setStringFellName("未接电话");
		phoneLogDto2.setAllFee(String.valueOf(countMissCall));
		dtoList.add(phoneLogDto2);
		
		//接通率
		PhoneLogDto phoneLogDto3=new PhoneLogDto();
		if(countPhoneLog==0){
			phoneLogDto3.setAllFee("0.0000");
		}else{
			Double phoneRate=Double.valueOf(String.valueOf(countPhoneLog))/Double.valueOf(String.valueOf(countMissCall+countPhoneLog));
			BigDecimal bd=new BigDecimal(phoneRate);
			bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
			phoneLogDto3.setAllFee(String.valueOf(bd));
		}
		phoneLogDto3.setStringFellName("接通率");
		dtoList.add(phoneLogDto3);
		
		//电话总量
		PhoneLogDto phoneLogDto4=new PhoneLogDto();
		Integer allPhone=countPhoneLog+countMissCall;
		phoneLogDto4.setStringFellName("电话总量");
		phoneLogDto4.setAllFee(String.valueOf(allPhone));
		dtoList.add(phoneLogDto4);
		
		page.setRecords(dtoList);
		page.setTotalRecords(4);
		return page;
	}
	
	public String parseDoubleValue(Double value){
		BigDecimal bd=new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bd);
	}
	public PageDto<PhoneLog> queryListByTel(String tel,PageDto<PhoneLog> page){
		page.setTotalRecords(phoneLogDao.countListByTel(tel));
		List<PhoneLog> list=phoneLogDao.queryListByTel(tel, page);
		for(PhoneLog pl:list){
			//来电地区
			if(!"未知".equals(pl.getProvince())){
				String address=pl.getProvince()+" "+pl.getCity();
				pl.setAddress(address);
			}else{
				pl.setAddress("未知地区");
			}
			if(StringUtils.isEmpty(pl.getAddress())){
				pl.setAddress("未知地区");
			}
		}
		page.setRecords(list);
		return page;
	}
	@Override
	public PhoneLog queryPhoneLogByCallSn(String callSn){
		return phoneLogDao.queryPhoneLogByCallSn(callSn);
	}

	@Override
	public List<PhoneLogDto> exportPhoneLog(Integer companyId,PhoneLog phoneLog,PageDto<PhoneLogDto> page) {
		//用于显示差值
		List<PhoneLogDto> dtoList=null;
		if(companyId!=0){
			dtoList=phoneLogDao.exportPhoneLog(phoneLog.getTel(),phoneLog.getStartTime(),phoneLog.getEndTime(), page);
		}else{
			dtoList=phoneLogDao.exportAllPhoneLog(phoneLog.getStartTime(), phoneLog.getEndTime(), page);
		}
		for (PhoneLogDto phoneLogDto : dtoList) {
		//计算出通话时间差
			try {
					Date endTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getEndTime(), "yyyy-MM-dd HH:mm:ss");
					Date startTime=DateUtil.getDate(phoneLogDto.getPhoneLog().getStartTime(), "yyyy-MM-dd HH:mm:ss");
					//获取秒
					long diffS=(endTime.getTime()-startTime.getTime())/1000;
					long diffTime=((diffS/60)+(diffS%60==0?0:1));
					phoneLogDto.setDiffMinute(String.valueOf(diffTime));
				} catch (ParseException e) {
					e.printStackTrace();
			  	}
			if(!"未知".equals(phoneLogDto.getPhoneLog().getProvince())){
				String address=phoneLogDto.getPhoneLog().getProvince()+" "+phoneLogDto.getPhoneLog().getCity();
				phoneLogDto.getPhoneLog().setAddress(address);
			}else{
				phoneLogDto.getPhoneLog().setAddress("未知");
			}
			if("0".equals(phoneLogDto.getPhoneLog().getCallSn())){
				phoneLogDto.getPhoneLog().setAddress("月租");
			}
		}
		return dtoList;
	}
	//根据手机号码获取地址
	public String getAddress(String tel) throws ParserConfigurationException, SAXException, HttpException, IOException {
		String telN;
		String address;
		if (tel.length() == 12) {
			telN = tel.substring(1);
		} else {
			telN = tel;
		}
		Pattern pattern = Pattern.compile("1\\d{10}");
		if (StringUtils.isNotEmpty(tel) && StringUtils.isNumber(telN)) {
			Matcher matcher = pattern.matcher(telN);
			if (matcher.matches()) {
				String url = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile="
						+ telN;
				String result = HttpUtils.getInstance().httpGet(url, "GBK");
				StringReader stringReader = new StringReader(result);
				InputSource inputSource = new InputSource(stringReader);
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
				Document document = documentBuilder.parse(inputSource);
				String retmsg = document.getElementsByTagName("retmsg").item(0)
						.getFirstChild().getNodeValue();
				if (retmsg.equals("OK")) {
					String province = document.getElementsByTagName("province").item(0)
							.getFirstChild().getNodeValue().trim();
					String city = document.getElementsByTagName("city").item(0)
							.getFirstChild().getNodeValue().trim();
					address=province+city;
					return address;
				} else {
					return null;
				}
			}
		}
		return null;
	}
	
	@Override
	public Integer insertPhoneBlackList(String ids,String callers,String checkPerson, String blackReason){
		StringBuffer sb = new StringBuffer();
		String[] str = ids.split(",");
		String[] caller = callers.split(",");
		for (int a = 0; a < str.length; a++) {
			//设置此通来电为0元
			Integer i = phoneLogDao.updateCallFeeById(Integer.valueOf(str[a]));
			//将来电号码拉进黑名单
			PhoneBlacklist phoneBlacklist = new PhoneBlacklist();
			phoneBlacklist.setPhone(caller[a]);
			phoneBlacklist.setPhoneLogId(Integer.valueOf(str[a]));
			phoneBlacklist.setCheckPerson(checkPerson);
			phoneBlacklist.setBlackReason(blackReason);
			Integer j = phoneBlacklistDao.insert(phoneBlacklist);
			if (i == 1 && j>0) {
				sb.append(str[a]);
			}
		}
//		for (String s : str) {
//			//设置此通来电为0元
//			Integer i = phoneLogDao.updateCallFeeById(Integer.valueOf(s));
//			//将来电号码拉进黑名单
//			PhoneBlacklist phoneBlacklist = new PhoneBlacklist();
//			phoneBlacklist.setPhone(callerId);
//			phoneBlacklist.setCheckPerson(checkPerson);
//			phoneBlacklist.setBlackReason(blackReason);
//			Integer j = phoneBlacklistDao.insert(phoneBlacklist);
//			if (i == 1 && j>0) {
//				sb.append(s);
//			}
//		}
		if (sb.length()==0) {
			return 0;
		}
		return 1;
	}
}
