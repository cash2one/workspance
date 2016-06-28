package com.ast.ast1949.service.phone.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneCostSvrDao;
import com.ast.ast1949.persist.phone.PhoneCsBsDao;
import com.ast.ast1949.service.phone.PhoneCostSvrService;
import com.zz91.util.lang.StringUtils;

@Component("phoneCostSvrService")
public class PhoneCostSvrServiceImpl implements PhoneCostSvrService{

	@Resource
	private PhoneCostSvrDao phoneCostSvrDao;
	@Resource
	private PhoneCsBsDao phoneCsBsDao;
	@Override
	public Integer insert(PhoneCostSvr phoneCostSvr) {
		if(phoneCostSvr.getCompanyId()==null||phoneCostSvr.getCrmCompanyServiceId()==null){
			return 0;
		}
		// 未欠费状态设置
		phoneCostSvr.setIsLack(PhoneCostSvrService.NO_LACK);
		// 初始余额为 充值金额
		phoneCostSvr.setLave(phoneCostSvr.getFee());
		return phoneCostSvrDao.insert(phoneCostSvr);
	}

	@Override
	public PageDto<PhoneCostSvr> pageByAdmin(PhoneCostSvr phoneCostSvr,
			PageDto<PhoneCostSvr> page) {
		page.setTotalRecords(phoneCostSvrDao.queryListCountByAdmin(phoneCostSvr));
		page.setRecords(phoneCostSvrDao.queryListByAdmin(phoneCostSvr,page));
		return page;
	}

	@Override
	public Integer closeSvr(Integer id, Integer companyId) {
		if(id==null||companyId==null){
			return 0;
		}
		PhoneCostSvr phoneCostSvr = new PhoneCostSvr();
		phoneCostSvr.setId(id);
		phoneCostSvr.setCompanyId(companyId);
		phoneCostSvr.setLave(0.0f);
		phoneCostSvr.setIsLack(IS_LACK);
		
		//将 余额为0的记录 记录下这个时间插入到必杀期表（phone_cs_bs） 用于后台来电宝必杀期 luog
//		PhoneCsBs phoneCsBs=new PhoneCsBs();
		//targetId 为phone_cost_service 表的id
//		phoneCsBs.setTargetId(id);
//		phoneCsBs.setCompanyId(companyId);
//		phoneCsBsDao.insert(phoneCsBs);
		
		return phoneCostSvrDao.updateSvr(phoneCostSvr);
	}

	/**
	 * 根据消费计算余额
	 * 注：临界值问题。
	 * 如果余额不足时：
	 * 1、记录A的余额不够，则检索是否有其他充值记录 B
	 * 2、有记录B，将余额不足的记录剩余金额加到B记录总金额
	 * 3、扣除B记录的费用
	 * 4、得到最新余额
	 * 5、关闭记录A 调用closeSvr方法
	 * @param id
	 * @param companyId
	 * @param reFee
	 * @return 返回 1：成功 ；返回 0：失败 余额不足
	 */
	@Override
	public Integer reduceFee(Integer id, Integer companyId, Float reFee) {
		List<PhoneCostSvr> list = phoneCostSvrDao.queryListByCost(companyId);
		Float f = 0f; //计算结果
		Float l = 0f;
		for(PhoneCostSvr obj : list){
			f = obj.getLave()-reFee + l;
			// 余额足够 扣费结束
			if(f>0){
				obj.setLave(f);
				return phoneCostSvrDao.updateSvr(obj);
			}
			// 余额不够
			if(f<0){
				l = l + obj.getLave();
				// 关闭该充值记录
				closeSvr(obj.getId(), companyId);
				continue;
			}
		}
		return 0;
	}

	@Override
	public PhoneCostSvr queryById(Integer id) {
		if(id==null||id==0){
			return null;
		}
		return phoneCostSvrDao.queryById(id);
	}

	@Override
	public Integer updateFee(Integer id,Integer companyId, Float telFee, Float clickFee) {
		if(id==null||companyId==null){
			return 0;
		}
		PhoneCostSvr phoneCostSvr =new PhoneCostSvr();
		phoneCostSvr.setCompanyId(companyId);
		phoneCostSvr.setId(id);
		phoneCostSvr.setClickFee(clickFee);
		phoneCostSvr.setTelFee(telFee);
		return phoneCostSvrDao.updateSvr(phoneCostSvr);
	}

	@Override
	public Integer update(PhoneCostSvr phoneCostSvr) {
		return phoneCostSvrDao.updateSvr(phoneCostSvr);
	}

	@Override
	public PhoneCostSvr queryByCompanyId(Integer companyId) {
		if(companyId==null){
			return null;
		}
		List<PhoneCostSvr> list =phoneCostSvrDao.queryListByCost(companyId);
		if(list==null||list.size()<1){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public String countFeeByCompanyId(Integer companyId){
		if(companyId==0||companyId==null){
			return null;
		}
		return phoneCostSvrDao.countFeeByCompanyId(companyId);
	}
	public Float reduceFee(Integer companyId){
		//需要扣的钱
		Float fee=5f;
		//先判断一下点击费用是否为1元
		List<PhoneCostSvr> list=phoneCostSvrDao.queryListByCost(companyId);
		for(PhoneCostSvr pcs:list){
			if(pcs.getClickFee()==1){
				fee=1f;
			}
		}
		Integer i=reduceFee(0,companyId,fee);
		if(i==1){
			return fee;
		}else{
			return 0f;
		}
	}
	
	@Override
	public Float sumLave(Integer companyId){
		do {
			if (companyId==null||companyId<=0) {
				break;
			}
			String str = phoneCostSvrDao.sumLaveByCompanyId(companyId);
			if (StringUtils.isNotEmpty(str)) {
				return Float.valueOf(phoneCostSvrDao.sumLaveByCompanyId(companyId));
			}
		} while (false);
		return 0f;
	}

	@Override
	public Integer reduceServiceFee(Integer companyId, Float reFee) {
		Float f = 0f; //计算结果
		Float l = 0f; //没扣部分
		List<PhoneCostSvr> list = phoneCostSvrDao.queryListByCost(companyId);
		for(PhoneCostSvr svr : list){
			f = svr.getLave() - reFee + l ;
			//余额足够，扣费结束
			if(f > 0){
				svr.setFee(svr.getFee()-reFee);
				svr.setLave(f);
				return phoneCostSvrDao.updateSvr(svr);
			}
			
			//余额不够
			if(f < 0){
				l = l + svr.getLave();
				//关闭该充值记录
				closeSvr(svr.getId(), companyId);
				continue;
			}
		}
		return 0;
	}

	@Override
	public PhoneCostSvr queryGmtZeroByCompanyId(Integer companyId) {
		if(companyId==null){
			return null;
		}
		List<PhoneCostSvr> list =phoneCostSvrDao.queryGmtZeroByCompanyId(companyId);
		if(list==null||list.size()<1){
			return null;
		}
		return list.get(0);
	}
	
}
