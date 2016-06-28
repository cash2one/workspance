package com.ast.ast1949.service.autocaiji.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.autocaiji.AutoCaiji;
import com.ast.ast1949.persist.autocaiji.AutoCaijiDAO;
import com.ast.ast1949.service.autocaiji.AutoCaijiService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
@Component("autoCaijiService")
public class AutoCaijiServiceImpl implements AutoCaijiService {

    @Resource
    private AutoCaijiDAO autoCaijiDAO;

    @Override
    public List<AutoCaiji> queryListByFromTo(String fromStr, String toStr) throws ParseException{
        Date from = null, to = null;
        if (StringUtils.isNotEmpty(fromStr)) {
            try {
                from = DateUtil.getDate(fromStr, "yyyy-MM-dd");
            } catch (ParseException e) {
                from = new Date();
            }
        } else {
            from = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
        }
        if (StringUtils.isNotEmpty(toStr)) {
            try {
                to = DateUtil.getDate(toStr, "yyyy-MM-dd");
            } catch (ParseException e) {
                to = new Date();
            }
        } else {
            to = DateUtil.getDate(new Date(), "yyyy-MM-dd");
        }
        Integer id;
        try {
            id = DateUtil.getIntervalDays(from, to);
        } catch (ParseException e) {
            id = 31;
        }
        if (id.intValue() > 30) {
            from = DateUtil.getDateAfterDays(to, -30);
        }
        List<AutoCaiji> list =  autoCaijiDAO.queryListLog(DateUtil.toString(from, "yyyy-MM-dd"),DateUtil.toString(to, "yyyy-MM-dd"));
        
        List<AutoCaiji> newList = new ArrayList<AutoCaiji>();
        fromStr = DateUtil.toString(from, "yyyy-MM-dd");
        toStr = DateUtil.toString(to, "yyyy-MM-dd");
        if (fromStr.equals(toStr)) {
            for (AutoCaiji autoCaiji : list) {
                JSONObject jb = JSONObject.fromObject(autoCaiji.getLog());
                if ("success".equals(autoCaiji.getType())) {
                    autoCaiji.setDefaultTime(jb.get("defaultTime").toString());
                    autoCaiji.setEarlyTime(jb.get("catchTime").toString());
                    autoCaiji.setLateTime(jb.get("catchTime").toString());
                } else {
                    autoCaiji.setDefaultTime(jb.get("defaultTime").toString());
                    autoCaiji.setNum(1);
                }
            }
            return list;
        } else {
            for (AutoCaiji autoCaiji : list) {
                Integer flg = 0;
                String title = autoCaiji.getTitle();
                String url = autoCaiji.getUrl();
                JSONObject jb = JSONObject.fromObject(autoCaiji.getLog());
                String time = jb.get("defaultTime").toString();
                autoCaiji.setDefaultTime(jb.get("defaultTime").toString());
                if (newList.size() == 0) {
                    if ("success".equals(autoCaiji.getType())) {
                        autoCaiji.setEarlyTime(jb.get("catchTime").toString());
                        autoCaiji.setLateTime(jb.get("catchTime").toString());
                    } else {
                        autoCaiji.setNum(1);
                    }
                    newList.add(autoCaiji);
                } else {
                    for (AutoCaiji otherautoCaiji : newList) {
                        if(url.equals(otherautoCaiji.getUrl()) && title.equals(otherautoCaiji.getTitle())
                                && time.equals(otherautoCaiji.getDefaultTime())) {
                            if ("success".equals(autoCaiji.getType())) {
                                otherautoCaiji.setNum(otherautoCaiji.getNum());
                                String catchTime = jb.get("catchTime").toString();
                                if (StringUtils.isNotEmpty(otherautoCaiji.getEarlyTime()) && StringUtils.isNotEmpty(otherautoCaiji.getLateTime())) {
                                    String earlyTime = otherautoCaiji.getEarlyTime();
                                    String lateTime = otherautoCaiji.getLateTime();
                                    Date t1 = (Date) DateUtil.getDate(catchTime, "HH:mm");
                                    Date t2 = (Date) DateUtil.getDate(earlyTime, "HH:mm");
                                    Date t3 = (Date) DateUtil.getDate(lateTime, "HH:mm");
                                    if (t1.getTime()-t2.getTime() < 0) {
                                        otherautoCaiji.setEarlyTime(catchTime);
                                    } else if (t1.getTime()-t3.getTime() > 0){
                                        otherautoCaiji.setLateTime(catchTime);
                                    }
                                } else {
                                    otherautoCaiji.setEarlyTime(catchTime);
                                    otherautoCaiji.setLateTime(catchTime);
                                }
                                flg = 1;
                                break;
                            } else {
                                if (otherautoCaiji.getNum() != null) {
                                    otherautoCaiji.setNum(otherautoCaiji.getNum()+1);
                                } else {
                                    otherautoCaiji.setNum(1);
                                }
                                otherautoCaiji.setEarlyTime(otherautoCaiji.getEarlyTime());
                                otherautoCaiji.setLateTime(otherautoCaiji.getLateTime());
                                flg = 1;
                                break;
                            }
                        }
                    }
                    if (flg == 0) {
                        if ("success".equals(autoCaiji.getType())) {
                            autoCaiji.setEarlyTime(jb.get("catchTime").toString());
                            autoCaiji.setLateTime(jb.get("catchTime").toString());
                        } else {
                            autoCaiji.setNum(1);
                        }
                        newList.add(autoCaiji);
                    }
                }
            }
            return newList;
        }
    }

   /* @Override
    public PageDto<AutoCaiji> queryPageByFromTo(PageDto<AutoCaiji> page,
            String fromStr, String toStr) throws ParseException {
        Date from = null, to = null;
        if (StringUtils.isNotEmpty(fromStr)) {
            try {
                from = DateUtil.getDate(fromStr, "yyyy-MM-dd");
            } catch (ParseException e) {
                from = new Date();
            }
        } else {
            from = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
        }
        if (StringUtils.isNotEmpty(toStr)) {
            try {
                to = DateUtil.getDate(toStr, "yyyy-MM-dd");
            } catch (ParseException e) {
                to = new Date();
            }
        } else {
            to = DateUtil.getDate(new Date(), "yyyy-MM-dd");
        }
        if (to.getTime() - from.getTime() < 0) {
            to = from;
        }
        Integer id;
        try {
            id = DateUtil.getIntervalDays(from, to);
        } catch (ParseException e) {
            id = 31;
        }
        if (id.intValue() > 30) {
            from = DateUtil.getDateAfterDays(to, -30);
        }
        if (type.equals("success")) {
            List<AutoCaiji> list = autoCaijiDAO.queryListLog(DateUtil.toString(from, "yyyy-MM-dd"),DateUtil.toString(to, "yyyy-MM-dd"));
            List<AutoCaiji> newList = new ArrayList<AutoCaiji>();
            fromStr = DateUtil.toString(from, "yyyy-MM-dd");
            toStr = DateUtil.toString(to, "yyyy-MM-dd");
            if (fromStr.equals(toStr)) {
                for (AutoCaiji autoCaiji : list) {
                    JSONObject jb = JSONObject.fromObject(autoCaiji.getLog());
                    autoCaiji.setDefaultTime(jb.get("defaultTime").toString());
                    autoCaiji.setEarlyTime(jb.get("catchTime").toString());
                    autoCaiji.setLateTime(jb.get("catchTime").toString());
                }
                page.setTotalRecords(list.size());
                page.setRecords(list);
            } else {
                for (AutoCaiji autoCaiji : list) {
                    Integer flg = 0;
                    String title = autoCaiji.getTitle();
                    String url = autoCaiji.getUrl();
                    JSONObject jb = JSONObject.fromObject(autoCaiji.getLog());
                    String time = jb.get("defaultTime").toString();
                    autoCaiji.setDefaultTime(jb.get("defaultTime").toString());
                    if (newList.size() == 0) {
                        autoCaiji.setEarlyTime(jb.get("catchTime").toString());
                        autoCaiji.setLateTime(jb.get("catchTime").toString());
                        newList.add(autoCaiji);
                    } else {
                        for (AutoCaiji otherautoCaiji : newList) {
                            if(url.equals(otherautoCaiji.getUrl()) && title.equals(otherautoCaiji.getTitle())
                                    && time.equals(otherautoCaiji.getDefaultTime())) {
                                String catchTime = jb.get("catchTime").toString();
                                String earlyTime = otherautoCaiji.getEarlyTime();
                                String lateTime = otherautoCaiji.getLateTime();
                                Date t1 = (Date) DateUtil.getDate(catchTime, "HH:mm");
                                Date t2 = (Date) DateUtil.getDate(earlyTime, "HH:mm");
                                Date t3 = (Date) DateUtil.getDate(lateTime, "HH:mm");
                                if (t1.getTime()-t2.getTime() < 0) {
                                    otherautoCaiji.setEarlyTime(catchTime);
                                } else if (t1.getTime()-t3.getTime() > 0){
                                    otherautoCaiji.setLateTime(catchTime);
                                }
                                flg = 1;
                                break;
                            } 
                        }
                        if (flg == 0) {
                            autoCaiji.setEarlyTime(jb.get("catchTime").toString());
                            autoCaiji.setLateTime(jb.get("catchTime").toString());
                            newList.add(autoCaiji);
                        }
                    }
                }
                page.setTotalRecords(newList.size());
                page.setRecords(newList);
            }
        } else {
            page.setTotalRecords(autoCaijiDAO.queryCount(DateUtil.toString(from, "yyyy-MM-dd"),DateUtil.toString(to, "yyyy-MM-dd")));
            page.setRecords(autoCaijiDAO.queryPageLog(page,DateUtil.toString(from, "yyyy-MM-dd"),DateUtil.toString(to, "yyyy-MM-dd")));
        }
        return page;
    }*/

}
