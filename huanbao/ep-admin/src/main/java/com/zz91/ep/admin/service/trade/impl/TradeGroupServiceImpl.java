/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-28
 */
package com.zz91.ep.admin.service.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradeGroupDao;
import com.zz91.ep.admin.service.trade.TradeGroupService;

/**
 * @author totly
 *
 * created on 2011-9-28
 */
@Component("tradeGroupService")
public class TradeGroupServiceImpl implements TradeGroupService {

    @Resource
    private TradeGroupDao tradeGroupDao;
    
//    @Override
//    public Integer createTradeGroup(TradeGroup tradeGroup) {
//        Assert.notNull(tradeGroup, "the tradeGroup can not be null");
//        Assert.notNull(tradeGroup.getCid(), "the tradeGroup.cid can not be null");
//        Assert.notNull(tradeGroup.getUid(), "the tradeGroup.uid can not be null");
//        return tradeGroupDao.insertTradeGroup(tradeGroup);
//    }

//    @Override
//    public Integer deleteTradeGroup(Integer gid, Integer cid) {
//        Assert.notNull(gid, "the gid can not be null");
//        return tradeGroupDao.deleteTradeGroup(gid, cid);
//    }

//    @Override
//    public List<TradeGroup> queryTradeGroupById(Integer cid) {
//        Assert.notNull(cid, "the cid can not be null");
//        return tradeGroupDao.queryTradeGroupById(cid, null);
//    }

//    @Override
//    public Integer updateTradeGroup(TradeGroup tradeGroup) {
//        Assert.notNull(tradeGroup, "the tradeGroup can not be null");
//        Assert.notNull(tradeGroup.getCid(), "the tradeGroup.cid can not be null");
//        Assert.notNull(tradeGroup.getUid(), "the tradeGroup.uid can not be null");
//        return tradeGroupDao.updateTradeGroup(tradeGroup);
//    }

//	@Override
//	public List<TradeGroupDto> queryTradeGroupDtoById(Integer cid) {
//		Assert.notNull(cid, "the cid can not be null");
//		List<TradeGroupDto> list = new ArrayList<TradeGroupDto>();
//		List<TradeGroup> flist = tradeGroupDao.queryTradeGroupById(cid, 0);
//		for (TradeGroup group:flist) {
//			TradeGroupDto groupDto = new TradeGroupDto();
//			groupDto.setCid(group.getCid());
//			groupDto.setId(group.getId());
//			groupDto.setName(group.getName());
//			groupDto.setSort(group.getSort());
//			groupDto.setChilds(tradeGroupDao.queryTradeGroupById(cid, group.getId()));
//			list.add(groupDto);
//		}
//        return list;
//	}

}