/*
 * 文件名称：TradeGroupServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午3:34:09
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.trade.TradeGroupDao;
import com.zz91.ep.domain.trade.TradeGroup;
import com.zz91.ep.dto.trade.TradeGroupDto;
import com.zz91.ep.service.trade.TradeGroupService;
import com.zz91.util.Assert;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：用户自定义产品类别操作实现类
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("tradeGroupService")
public class TradeGroupServiceImpl implements TradeGroupService {
	
    @Resource
    private TradeGroupDao tradeGroupDao;
    
	@Override
	public List<TradeGroupDto> queryTradeGroupDtoById(Integer cid) {
		Assert.notNull(cid, "the cid can not be null");
		List<TradeGroupDto> list = new ArrayList<TradeGroupDto>();
		List<TradeGroup> flist = tradeGroupDao.queryTradeGroupById(cid, 0);
		for (TradeGroup group:flist) {
			TradeGroupDto groupDto = new TradeGroupDto();
			groupDto.setCid(group.getCid());
			groupDto.setId(group.getId());
			groupDto.setName(group.getName());
			groupDto.setSort(group.getSort());
			groupDto.setChilds(tradeGroupDao.queryTradeGroupById(cid, group.getId()));
			list.add(groupDto);
		}
		return list;
	}

	@Override
	public String queryNameById(Integer id) {
		return tradeGroupDao.queryNameById(id);
	}
	
	@Override
    public List<TradeGroup> queryTradeGroupById(Integer cid) {
        Assert.notNull(cid, "the cid can not be null");
        return tradeGroupDao.queryTradeGroupById(cid, null);
    }
	

    @Override
    public Integer createTradeGroup(TradeGroup tradeGroup) {
        Assert.notNull(tradeGroup.getCid(), "the tradeGroup.cid can not be null");
        Assert.notNull(tradeGroup.getUid(), "the tradeGroup.uid can not be null");
        return tradeGroupDao.insertTradeGroup(tradeGroup);
    }

    @Override
    public Integer updateTradeGroup(TradeGroup tradeGroup) {
        Assert.notNull(tradeGroup, "the tradeGroup can not be null");
        Assert.notNull(tradeGroup.getCid(), "the tradeGroup.cid can not be null");
        Assert.notNull(tradeGroup.getUid(), "the tradeGroup.uid can not be null");
        return tradeGroupDao.updateTradeGroup(tradeGroup);
    }
    
    @Override
    public Integer deleteTradeGroup(Integer gid, Integer cid) {
        Assert.notNull(gid, "the gid can not be null");
        return tradeGroupDao.deleteTradeGroup(gid, cid);
    }

	@Override
	public TradeGroup queryTradeGroup(Integer id) {
		
		return tradeGroupDao.queryTradeGroup(id);
	}

	@Override
	public List<TradeGroup> queryTradeGroupByCid(Integer cid, Integer parentId) {
		Assert.notNull(cid, "the cid can not be null");
		List<TradeGroup> flist = tradeGroupDao.queryTradeGroupById(cid, parentId);
		return flist;
	}

	@Override
	public Boolean childAvalible(Integer cid, Integer parentId) {
		Integer childNum=tradeGroupDao.queryChildCount(cid, parentId);
		if(childNum!=null && childNum.intValue()>0){
			return true;
		}
		return false;
	}
	
	
}
