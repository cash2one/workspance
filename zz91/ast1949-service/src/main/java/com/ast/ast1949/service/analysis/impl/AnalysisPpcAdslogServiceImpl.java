package com.ast.ast1949.service.analysis.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.springframework.stereotype.Component;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPpcAdslogDto;
import com.ast.ast1949.persist.analysis.AnalysisPhoneLogDao;
import com.ast.ast1949.persist.analysis.AnalysisPpcAdslogDao;
import com.ast.ast1949.persist.phone.PhoneDao;
import com.ast.ast1949.persist.phone.PhoneLogDao;
import com.ast.ast1949.service.analysis.AnalysisPpcAdslogService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component("analysisPpcAdslogService")
public class AnalysisPpcAdslogServiceImpl implements AnalysisPpcAdslogService {
	@Resource
	private AnalysisPpcAdslogDao analysisPpcAdslogDao;
	@Resource
	private PhoneLogDao phoneLogDao;
	@Resource
	private PhoneDao phoneDao;
	@Resource
	private AnalysisPhoneLogDao analysisPhoneLogDao;
	
	
	@Override
	public PageDto<AnalysisPpcAdslogDto> pageAdslogDto(PageDto<AnalysisPpcAdslogDto> page,String from,String to,List<String> key,String sort,String dir,String tel){
		List<AnalysisPpcAdslogDto> list=new ArrayList<AnalysisPpcAdslogDto>();
		Integer companyId=null;
		//展现量
		Integer showCount=0;
		//点击量
		Integer checkCount=0;
		//电话量
		Integer telCount=0;
		if(StringUtils.isNotEmpty(tel)){
			companyId=phoneDao.queryCompanyIdByTel(tel);
		}
		for(String str:key){
			AnalysisPpcAdslogDto adslog=new AnalysisPpcAdslogDto();
			if(companyId!=null){
			    showCount=analysisPpcAdslogDao.queryShowCByCompanyIdATAA(str, companyId, from, to);
				checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdATAA(str, companyId, from, to);
				telCount=analysisPhoneLogDao.queryTelCByCompanyIdATAA(str, companyId, from, to);
			}else{
				showCount=analysisPpcAdslogDao.queryShowCByAdpositionAT(str, from, to);
				checkCount=analysisPpcAdslogDao.queryCheckCByAdpositionAT(str, from, to);
				telCount=analysisPhoneLogDao.queryTelCByAdpositionAT(str, from, to);
			}
			if(showCount==null){
				showCount=0;
			}
			if(checkCount==null){
				checkCount=0;
			}
			if(telCount==null){
				telCount=0;
			}
			//点击率
			if(showCount!=0){
			    double clickRate=Double.valueOf(String.valueOf(checkCount))/Double.valueOf(String.valueOf(showCount))*100;
				BigDecimal bd = new BigDecimal(clickRate);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				adslog.setClickRate(String.valueOf(bd));
			}else{
				adslog.setClickRate("0");
			}
			//转化率
			if(showCount!=0){
				double changeRate=Double.valueOf(String.valueOf(telCount))/Double.valueOf(String.valueOf(showCount))*100;
				BigDecimal bd = new BigDecimal(changeRate);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				adslog.setChangeRate(String.valueOf(bd));
			}else{
				adslog.setChangeRate("0");
			}
			adslog.setName(str);
			adslog.setShowcount(showCount);
			adslog.setCheckcount(checkCount);
			adslog.setTelCount(telCount);
			if(showCount==0&&checkCount==0&&telCount==0){
			}else{
				list.add(adslog);
			}
		}
		
		List<AnalysisPpcAdslogDto> listn=new ArrayList<AnalysisPpcAdslogDto>();
		if(StringUtils.isEmpty(sort)&&StringUtils.isEmpty(dir)){
			listn=list;
		}else{
			list=getDesc(list,sort);
			if("desc".equals(dir)){
				listn=list;
			}else{
				for(int j=list.size()-1;j>=0;j--){
					listn.add(list.get(j));
				}
			}
		}
		int size=20;
		if(list.size()-page.getStartIndex()<20){
			size=list.size()-page.getStartIndex();
		}
		List<AnalysisPpcAdslogDto> listR=new ArrayList<AnalysisPpcAdslogDto>();
		for(int i=page.getStartIndex();i<page.getStartIndex()+size;i++){
			listR.add(listn.get(i));
		}
		page.setRecords(listR);
		page.setTotalRecords(list.size());
		return page;
	}

	@Override
	public AnalysisPpcAdslogDto queryAllCountByTime(List<String> key,String tel,String from,String to){
		AnalysisPpcAdslogDto adslog=new AnalysisPpcAdslogDto();
		//展现量
		Integer showC=0;
		//点击量
		Integer checkC=0;
		//通话量
		Integer telC=0;
		Integer companyId=null;
		Integer showCount=0;
		Integer checkCount=0;
		if(StringUtils.isNotEmpty(tel)){
			companyId=phoneDao.queryCompanyIdByTel(tel);
		}
		if(key.size()>0){
			for(String str:key){
				if(companyId!=null){
					showCount=analysisPpcAdslogDao.queryShowCByCompanyIdATAA(str, companyId, from, to);
					checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdATAA(str, companyId, from, to);
				}else{
					showCount=analysisPpcAdslogDao.queryShowCByAdpositionAT(str, from, to);
					checkCount=analysisPpcAdslogDao.queryCheckCByAdpositionAT(str, from, to);
				}
				if(showCount!=null){
					showC=showC+showCount;
				}
				if(checkCount!=null){
					checkC=checkC+checkCount;
				}
			}	
		}else{
			showC=analysisPpcAdslogDao.queryShowCByCompanyIdAT(companyId, from, to);
			checkC=analysisPpcAdslogDao.queryCheckCByCompanyIdAT(companyId, from, to);
			if(showC==null){
				showC=0;
			}
			if(checkC==null){
				checkC=0;
			}
		}
		Integer yj=0;
		Integer wj=0;
		if(companyId==null){
			 yj=phoneLogDao.queryYJCompanyBytime(from, to);
			 wj=phoneLogDao.queryWJCompanyBytime(from, to);
		}else{
			yj=phoneLogDao.countYJCompanyBytime(companyId, from, to);
			wj=phoneLogDao.countWJCompanyBytime(companyId, from, to);
		}
		if(yj==null){
			yj=0;
		}
		if(wj==null){
			wj=0;
		}
		telC=yj+wj;
		if(showC!=0){
			Double clickRate=Double.valueOf(String.valueOf(checkC))/Double.valueOf(String.valueOf(showC))*100;
			BigDecimal bd = new BigDecimal(clickRate);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			adslog.setClickRate(String.valueOf(bd));
			Double changeRate=Double.valueOf(String.valueOf(telC))/Double.valueOf(String.valueOf(showC))*100;
			BigDecimal bds = new BigDecimal(changeRate);
			bds = bds.setScale(2, BigDecimal.ROUND_HALF_UP);
			adslog.setChangeRate(String.valueOf(bds));
		}else{
			adslog.setChangeRate("0");
			adslog.setClickRate("0");
		}
		adslog.setShowcount(showC);
		adslog.setCheckcount(checkC);
		adslog.setTelCount(telC);
		return adslog;
	}
	@Override
	 public AnalysisPpcAdslogDto getAveCount(AnalysisPpcAdslogDto dto,String from,String to){
		AnalysisPpcAdslogDto avelog=new AnalysisPpcAdslogDto();
		Integer days=0;
		try {
			days = DateUtil.getIntervalDays(DateUtil.getDate(to, "yyyy-MM-dd"), DateUtil.getDate(from, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(days>=0){
			days=days+1;
			avelog.setShowcount(dto.getShowcount()/days);
			avelog.setCheckcount(dto.getCheckcount()/days);
			avelog.setTelCount(dto.getTelCount()/days);
			avelog.setClickRate(dto.getClickRate());
			avelog.setChangeRate(dto.getChangeRate());
		}
		return avelog;
	}
	@Override
	 public List<String> getPoint(PageDto<AnalysisPpcAdslogDto> page){
		List<String> list=new LinkedList<String>();
		//横轴坐标
		String str="";
		//展现量点集
		String showCount="";
		//点击量点集
		String checkCount="";
		//点击率点集
		String clickRate="";
		//电话量点集
		String telCount="";
		//转化率点集
		String changeRate="";
		for(int i=0;i<page.getRecords().size();i++){
			showCount=showCount+"["+i+","+page.getRecords().get(i).getShowcount()+"]";
			checkCount=checkCount+"["+i+","+page.getRecords().get(i).getCheckcount()+"]";
			clickRate=clickRate+"["+i+","+page.getRecords().get(i).getClickRate()+"]";
			telCount=telCount+"["+i+","+page.getRecords().get(i).getTelCount()+"]";
			changeRate=changeRate+"["+i+","+page.getRecords().get(i).getChangeRate()+"]";
			if(page.getRecords().get(i).getName()!=null){
				str=str+"["+i+","+"'"+page.getRecords().get(i).getName()+"'"+"]";
			}else{
				str=str+"["+i+","+"'"+page.getRecords().get(i).getTime()+"'"+"]";
			}
			if(page.getRecords().size()-1!=i){
				showCount=showCount+",";
				checkCount=checkCount+",";
				clickRate=clickRate+",";
				telCount=telCount+",";
				changeRate=changeRate+",";
				str=str+",";
			}
		}
		list.add(showCount);
		list.add(checkCount);
		list.add(clickRate);
		list.add(telCount);
		list.add(changeRate);
		list.add(str);
		
		return list;
	}
	@Override
	 public List<String> getBarPoint(PageDto<AnalysisPpcAdslogDto> page){
		List<String> list=new LinkedList<String>();
		//展现量点集
		String showCount="";
		//点击量点集
		String checkCount="";
		//点击率点集
		String clickRate="";
		//电话量点集
		String telCount="";
		//转化率点集
		String changeRate="";
		//颜色集
		List<String> listc=new ArrayList<String>();
		listc.add("'#66CCCC'");
		listc.add("'#99CC99'");
		listc.add("'#66FFCC'");
		String str="[";
		for(int i=0;i<page.getRecords().size();i++){
			showCount=showCount+"["+"'"+page.getRecords().get(i).getName()+"'"+","+page.getRecords().get(i).getShowcount()+"]";
			checkCount=checkCount+"["+"'"+page.getRecords().get(i).getName()+"'"+","+page.getRecords().get(i).getCheckcount()+"]";
			clickRate=clickRate+"["+"'"+page.getRecords().get(i).getName()+"'"+","+page.getRecords().get(i).getClickRate()+"]";
			telCount=telCount+"["+"'"+page.getRecords().get(i).getName()+"'"+","+page.getRecords().get(i).getTelCount()+"]";
			changeRate=changeRate+"["+"'"+page.getRecords().get(i).getName()+"'"+","+page.getRecords().get(i).getChangeRate()+"]";
			if(i%3==0){
				str=str+listc.get(0);
			}
			if(i%3==1){
				str=str+listc.get(1);
			}
			if(i%3==2){
				str=str+listc.get(2);
			}
			if(page.getRecords().size()-1!=i){
				showCount=showCount+",";
				checkCount=checkCount+",";
				clickRate=clickRate+",";
				telCount=telCount+",";
				changeRate=changeRate+",";
				str=str+",";
			}
		}
		str=str+"]";
		list.add(showCount);
		list.add(checkCount);
		list.add(clickRate);
		list.add(telCount);
		list.add(changeRate);
		list.add(str);
		return list;
	}
	@Override
	public Map<String,Object> getAdposition(){
		String responseText = "";
		responseText = HttpUtils.getInstance().httpGet(AddressTool.getAddress("ads")+"ads/ad/position/detail.htm", HttpUtils.CHARSET_UTF8);
		//名称与id之间的映射
		Map<String,String> mapNameAndId=new HashMap<String,String>();
		//父子之间的映射
		Map<String,List<String>> mapParent=new HashMap<String,List<String>>();
		JSONArray js =JSONArray.fromObject(responseText);
		for(int i=0;i<js.size();i++){
        	JSONObject jsn=JSONObject.fromObject(js.get(i));
        	mapNameAndId.put(jsn.getString("id"), jsn.getString("name"));
        	mapNameAndId.put(jsn.getString("name"), jsn.getString("id"));
        	if(mapParent.keySet().contains(jsn.getString("parentId"))){
        		List<String> li=mapParent.get(jsn.getString("parentId"));
        		li.add(jsn.getString("id"));
        		mapParent.put(jsn.getString("parentId"),li);
        	}else{
        		List<String> li=new ArrayList<String>();
        		li.add(jsn.getString("id"));
        		mapParent.put(jsn.getString("parentId"),li);
        	}
        }
		//来电宝通用广告位和样品中心——通用广告位，需要另外加
		List<String> listE=new ArrayList<String>();
		listE.add("632");
		listE.add("639");
		mapParent.put("0", listE);
		mapNameAndId.put("632", "来电宝通用广告位");
		mapNameAndId.put("639", "通用广告位");
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("mapNameAndId", mapNameAndId);
        map.put("mapParent", mapParent);
		return map;
	}
	
	@Override
	 public PageDto<AnalysisPpcAdslogDto> pageAdslogTime(PageDto<AnalysisPpcAdslogDto> page,String from,String to,List<String> keyZ,String sort,String dir,String tel){
		Integer days=0;
		try {
			days = DateUtil.getIntervalDays(DateUtil.getDate(to, "yyyy-MM-dd"), DateUtil.getDate(from, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<AnalysisPpcAdslogDto> list=new ArrayList<AnalysisPpcAdslogDto>();
		for(int i=0;i<days+1;i++){
			AnalysisPpcAdslogDto adslog=new AnalysisPpcAdslogDto();
			String start="";
			String end="";
			Integer dae=0;
			try {
				if(i!=0){
					from=DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(from,"yyyy-MM-dd"), 1),"yyyy-MM-dd");
				}
				start=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
				end=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd 23:59:59");
				dae=DateUtil.getDayOfWeekForDate(DateUtil.getDate(start, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//展现量
			Integer showC=0;
			Integer checkC=0;
			Integer telC=0;
			Integer companyId=null;
			if(StringUtils.isNotEmpty(tel)){
				companyId=phoneDao.queryCompanyIdByTel(tel);
			}
			Integer showCount=0;
			Integer checkCount=0;
			Integer telCount=0;
			if(keyZ.size()>0){
				for(String str:keyZ){
					if(companyId!=null){
						showCount=analysisPpcAdslogDao.queryShowCByCompanyIdATAA(str,companyId,start,end);
						checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdATAA(str,companyId,start,end);
						telCount=analysisPhoneLogDao.queryTelCByCompanyIdATAA(str, companyId, start, end);
					}else{
						showCount=analysisPpcAdslogDao.queryShowCByAdpositionAT(str, start, end);
						checkCount=analysisPpcAdslogDao.queryCheckCByAdpositionAT(str,start, end);
						telCount=analysisPhoneLogDao.queryTelCByAdpositionAT(str, start, end);
					}
					if(showCount!=null){
						showC=showC+showCount;
					}
					if(checkCount!=null){
						checkC=checkC+checkCount;
					}
					if(telCount!=null){
						telC=telC+telCount;
					}
				}
			}else{
				showC=analysisPpcAdslogDao.queryShowCByCompanyIdAT(companyId, start, end);
				checkC=analysisPpcAdslogDao.queryCheckCByCompanyIdAT(companyId, start, end);
				telC=analysisPhoneLogDao.queryTelCByCompanyIdAT(companyId, start, end);
				if(showC==null){
					showC=0;
				}
				if(checkC==null){
					checkC=0;
				}
				if(telC==null){
					telC=0;
				}
			}
			//点击率
			if(showC!=0){
				double clickRate=Double.valueOf(String.valueOf(checkC))/Double.valueOf(String.valueOf(showC))*100;
				BigDecimal bd = new BigDecimal(clickRate);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				adslog.setClickRate(String.valueOf(bd));
			}else{
				adslog.setClickRate("0");
			}
			//转化率
			if(showC!=0){
				double changeRate=Double.valueOf(String.valueOf(telC))/Double.valueOf(String.valueOf(showC))*100;
				BigDecimal bd = new BigDecimal(changeRate);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				adslog.setChangeRate(String.valueOf(bd));
			}else{
				adslog.setChangeRate("0");
			}
			adslog.setShowcount(showC);
			adslog.setCheckcount(checkC);
			adslog.setTelCount(telC);
			adslog.setTime(start);
			adslog.setDate(getDate(dae));
			list.add(adslog);
		}
		//Integer showCount=analysisPpcAdslogDao.
		List<AnalysisPpcAdslogDto> listn=new ArrayList<AnalysisPpcAdslogDto>();
		if(StringUtils.isEmpty(sort)&&StringUtils.isEmpty(dir)){
			listn=list;
		}else{
			list=getDesc(list,sort);
			if("desc".equals(dir)){
				listn=list;
			}else{
				for(int j=list.size()-1;j>=0;j--){
					listn.add(list.get(j));
				}
			}
		}
		int size=20;
		if(list.size()-page.getStartIndex()<20){
			size=list.size()-page.getStartIndex();
		}
		List<AnalysisPpcAdslogDto> listR=new ArrayList<AnalysisPpcAdslogDto>();
		for(int i=page.getStartIndex();i<page.getStartIndex()+size;i++){
			listR.add(listn.get(i));
		}
		page.setRecords(listR);
		page.setTotalRecords(list.size());
		return page;
	}
	@Override
	 public List<AnalysisPpcAdslogDto> getDesc(List<AnalysisPpcAdslogDto> ads,String sort){
		if("showcount".equals(sort)){
			for(int i=0;i<ads.size();i++){
				AnalysisPpcAdslogDto ad=new AnalysisPpcAdslogDto();
				for(int j=i+1;j<ads.size();j++){
					if(ads.get(i).getShowcount()<ads.get(j).getShowcount()){
						ad=ads.get(j);
						ads.set(j, ads.get(i));
						ads.set(i, ad);
					}
				}
			}
		}else if("checkcount".equals(sort)){
			for(int i=0;i<ads.size();i++){
				AnalysisPpcAdslogDto ad=new AnalysisPpcAdslogDto();
				for(int j=i+1;j<ads.size();j++){
					if(ads.get(i).getCheckcount()<ads.get(j).getCheckcount()){
						ad=ads.get(j);
						ads.set(j, ads.get(i));
						ads.set(i, ad);
					}
				}
			}
		}else if("telCount".equals(sort)){
			for(int i=0;i<ads.size();i++){
				AnalysisPpcAdslogDto ad=new AnalysisPpcAdslogDto();
				for(int j=i+1;j<ads.size();j++){
					if(ads.get(i).getTelCount()<ads.get(j).getTelCount()){
						ad=ads.get(j);
						ads.set(j, ads.get(i));
						ads.set(i, ad);
					}
				}
			}
			
		}else if("clickRate".equals(sort)){
			for(int i=0;i<ads.size();i++){
				AnalysisPpcAdslogDto ad=new AnalysisPpcAdslogDto();
				for(int j=i+1;j<ads.size();j++){
					if(Double.valueOf(ads.get(i).getClickRate())<Double.valueOf(ads.get(j).getClickRate())){
						ad=ads.get(j);
						ads.set(j, ads.get(i));
						ads.set(i, ad);
					}
				}
			}
		}else if("changeRate".equals(sort)){
			for(int i=0;i<ads.size();i++){
				AnalysisPpcAdslogDto ad=new AnalysisPpcAdslogDto();
				for(int j=i+1;j<ads.size();j++){
					if(Double.valueOf(ads.get(i).getChangeRate())<Double.valueOf(ads.get(j).getChangeRate())){
						ad=ads.get(j);
						ads.set(j, ads.get(i));
						ads.set(i, ad);
					}
				}
			}
		}else{
			//用于时间维度
			for(int i=0;i<ads.size();i++){
				AnalysisPpcAdslogDto ad=new AnalysisPpcAdslogDto();
				for(int j=i+1;j<ads.size();j++){
					Date time=null;
					Date times=null;
					try {
						time=DateUtil.getDate(ads.get(i).getTime(), "yyyy-MM-dd");
						times=DateUtil.getDate(ads.get(j).getTime(), "yyyy-MM-dd");
					} catch (ParseException e) {
					}
					if(time.getTime()<times.getTime()){
						ad=ads.get(j);
						ads.set(j, ads.get(i));
						ads.set(i, ad);
					}
				}
			}	
		}
		return ads;
	}
	public String getDate(Integer i){
	  Map<Integer, String> map =new HashMap<Integer,String>();
	  map.put(1, "周一");
	  map.put(2, "周二");
	  map.put(3, "周三");
	  map.put(4, "周四");
	  map.put(5, "周五");
	  map.put(6, "周六");
	  map.put(0, "周日");
	  String date=map.get((i-1)%7);
	  return date;
	}

	@Override
	public AnalysisPpcAdslogDto getAvePositionCount(AnalysisPpcAdslogDto dto,List<String> key) {
		AnalysisPpcAdslogDto adslog=new AnalysisPpcAdslogDto();
		if(key.size()>0){
			adslog.setShowcount(dto.getShowcount()/key.size());
			adslog.setCheckcount(dto.getCheckcount()/key.size());
			adslog.setClickRate(dto.getClickRate());
			adslog.setTelCount(dto.getTelCount()/key.size());
			adslog.setChangeRate(dto.getChangeRate());
		}else{
			adslog.setShowcount(dto.getShowcount());
			adslog.setCheckcount(dto.getCheckcount());
			adslog.setClickRate(dto.getClickRate());
			adslog.setTelCount(dto.getTelCount());
			adslog.setChangeRate(dto.getChangeRate());
		}
		return adslog;
	}
	@Override
	public List<AnalysisPpcAdslogDto> findDataByAdATime(String id,String tel,String from,String to){
		List<AnalysisPpcAdslogDto> list=new ArrayList<AnalysisPpcAdslogDto>();
		if(StringUtils.isEmpty(tel)){
			//获取该广告位下所有公司Id
			List<Integer> listC=analysisPpcAdslogDao.queryCompanyIdById(Integer.valueOf(id));
			for(Integer li:listC){
				AnalysisPpcAdslogDto ads=findDataByTimeAAAC(id,li,from,to);
				if(ads!=null){
					list.add(ads);
				}
			}
			
		}else{
			Integer companyId=phoneDao.queryCompanyIdByTel(tel);
			if(companyId==null){
				companyId=0;
			}
			String[] string=id.split(",");
			if(string.length>1){
				id="0";
			}
			AnalysisPpcAdslogDto ads=findDataByTimeAAAC(id,companyId,from,to);
			list.add(ads);
		}
		return list;
	}
	public AnalysisPpcAdslogDto findDataByTimeAAAC(String id,Integer companyId,String from,String to){
		AnalysisPpcAdslogDto ads=new AnalysisPpcAdslogDto();
		Integer showCount=0;//展现量
		Integer checkCount=0;//点击量
		Integer telCount=0;//电话量
		String tel=phoneDao.querytelByCompanyId(companyId);
		ads.setName(tel);
		if(!"0".equals(id)){
			showCount=analysisPpcAdslogDao.queryShowCByCompanyIdATAA(id, companyId, from, to);
			checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdATAA(id, companyId, from, to);
			telCount=analysisPhoneLogDao.queryTelCByCompanyIdATAA(id, companyId, from, to);
		}else{
			showCount=analysisPpcAdslogDao.queryShowCByCompanyIdAT(companyId, from, to);
			checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdAT(companyId, from, to);
			telCount=analysisPhoneLogDao.queryTelCByCompanyIdAT(companyId, from, to);
		}
		if(showCount==null){
			showCount=0;
		}
		if(checkCount==null){
			checkCount=0;
		}
		if(telCount==null){
			telCount=0;
		}
		ads.setShowcount(showCount);
		ads.setCheckcount(checkCount);
		ads.setTelCount(telCount);
		if(showCount!=0){
		    double clickRate=Double.valueOf(String.valueOf(checkCount))/Double.valueOf(String.valueOf(showCount))*100;
		    BigDecimal bd = new BigDecimal(clickRate);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			ads.setClickRate(String.valueOf(bd)+"%");
		}else{
			ads.setClickRate("0.00%");
		}
		if(showCount!=0){
			double changeRate=Double.valueOf(String.valueOf(telCount))/Double.valueOf(String.valueOf(showCount))*100;
		    BigDecimal bd = new BigDecimal(changeRate);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			ads.setChangeRate(String.valueOf(bd)+"%");
		}else{
			ads.setChangeRate("0.00%");
		}
		if(ads.getCheckcount()==0&&ads.getShowcount()==0&&ads.getTelCount()==0){
			return null;
		}else{
			return ads;
		}
	}
	@Override
	 public List<AnalysisPpcAdslogDto> findTimeDateByAdATime(String id,String tel,String from,String to){
		Integer days=0;
		Integer companyId=null;
		if(StringUtils.isNotEmpty(tel)){
			companyId=phoneDao.queryCompanyIdByTel(tel);
		}
		List<AnalysisPpcAdslogDto> list=new ArrayList<AnalysisPpcAdslogDto>();
		try {
			days = DateUtil.getIntervalDays(DateUtil.getDate(to, "yyyy-MM-dd"), DateUtil.getDate(from, "yyyy-MM-dd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AnalysisPpcAdslogDto ads=new AnalysisPpcAdslogDto();
		for(int i=0;i<days+1;i++){
			String start=null;
			String end=null;
			try {
				if(i!=0){
					from=DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(from,"yyyy-MM-dd"), 1),"yyyy-MM-dd");
				}
				start=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
				end=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd 23:59:59");
			}catch (ParseException e) {
				e.printStackTrace();
			}
			String[] string=id.split(",");
			if(string.length==1||companyId!=null){
				if(string.length>1){
					id="0";
				}
				ads=findTimeDataByTimeAAAC(id,companyId,start,end);
				list.add(ads);
			}
		}
		 return list;
	 }
	public AnalysisPpcAdslogDto findTimeDataByTimeAAAC(String id,Integer companyId,String from,String to){
		Integer showCount=0;//展现量
		Integer checkCount=0;//点击量
		Integer telCount=0;//电话量
		AnalysisPpcAdslogDto ads=new AnalysisPpcAdslogDto();
		ads.setTime(from);
		if(companyId==null){
			showCount=analysisPpcAdslogDao.queryShowCByAdpositionAT(id, from, to);
			checkCount=analysisPpcAdslogDao.queryCheckCByAdpositionAT(id, id, to);
			telCount=analysisPhoneLogDao.queryTelCByAdpositionAT(id, from, to);
		}else{
			if(!"0".equals(id)){
				showCount=analysisPpcAdslogDao.queryShowCByCompanyIdATAA(id, companyId, from, to);
				checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdATAA(id, companyId, from, to);
				telCount=analysisPhoneLogDao.queryTelCByCompanyIdATAA(id, companyId, from, to);
			}else{
				showCount=analysisPpcAdslogDao.queryShowCByCompanyIdAT(companyId, from, to);
				checkCount=analysisPpcAdslogDao.queryCheckCByCompanyIdAT(companyId, from, to);
				telCount=analysisPhoneLogDao.queryTelCByCompanyIdAT(companyId, from, to);
			}
		}
		if(showCount==null){
			showCount=0;
		}
		if(checkCount==null){
			checkCount=0;
		}
		if(telCount==null){
			telCount=0;
		}
		ads.setShowcount(showCount);
		ads.setCheckcount(checkCount);
		ads.setTelCount(telCount);
		if(showCount!=0){
		    double clickRate=Double.valueOf(String.valueOf(checkCount))/Double.valueOf(String.valueOf(showCount))*100;
		    BigDecimal bd = new BigDecimal(clickRate);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			ads.setClickRate(String.valueOf(bd)+"%");
		}else{
			ads.setClickRate("0.00%");
		}
		if(showCount!=0){
			double changeRate=Double.valueOf(String.valueOf(telCount))/Double.valueOf(String.valueOf(showCount))*100;
		    BigDecimal bd = new BigDecimal(changeRate);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			ads.setChangeRate(String.valueOf(bd)+"%");
		}else{
			ads.setChangeRate("0.00%");
		}
		return ads;
	}
}
