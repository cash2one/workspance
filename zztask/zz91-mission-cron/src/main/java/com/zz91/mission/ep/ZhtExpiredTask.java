package com.zz91.mission.ep;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * <br />任务描述：
 * <br />中环通服务过期恢复为普通用户
 * @author zhouzk
 *
 * created on 2013-12-25
 */
public class ZhtExpiredTask implements ZZTask {
    
    public static String DB="ep";
    final static String DATE_FORMATE="yyyy-MM-dd";
    
    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        
        baseDate=DateUtil.getDate(baseDate, DATE_FORMATE);
        String baseDateStr=DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd HH:mm:ss");
        //所有到期时间是昨天的服务且id为2的数据
        final Set<Integer> allData=new HashSet<Integer>();
        //所有到期时间大于等于今天的服务且id为2的数据
        final Set<Integer> otherData=new HashSet<Integer>();
        //公司表所有开通中环通服务会员
        final Set<Integer> allMember=new HashSet<Integer>();
        //公司表所有中环通过期服务会员
        final Set<Integer> expiredMember=new HashSet<Integer>();
        
        String sql="select cid from crm_comp_svr where crm_svr_id= '2' and gmt_end='"+baseDateStr+"' and apply_status=1";
        queryCid(sql, allData);
        sql="select cid from crm_comp_svr where crm_svr_id= '2' and gmt_end > '"+baseDateStr+"' and apply_status=1";
        queryCid(sql, otherData);
        sql="select id from comp_profile where member_code = '10011001'";
        queryCid(sql, allMember);
        
        //过滤出全部过期且没有续费的会员
        for(Integer i:allData){
            if(otherData.contains(i)){
                allData.remove(i);
            }
        }
        //得出公司表中所有中环通过期会员
        for (Integer j : allData) {
            if (allMember.contains(j)) {
                expiredMember.add(j);
            }
        }
        //更新所有过期会员
        for(Integer id:expiredMember){
             // 公司表 更新
            DBUtils.insertUpdate("ep", "update comp_profile set member_code='10011000',gmt_modified=now() where id="+id);
        }
        
        return true;
    }
    
    private void queryCid(String sql, final Set<Integer> set){
        DBUtils.select(DB, sql, new IReadDataHandler() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while(rs.next()){
                    set.add(rs.getInt(1));
                }
            }
        });
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    public static void main(String[] args){
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");

        ZhtExpiredTask task=new ZhtExpiredTask();
        try {
            task.exec(DateUtil.getDate("2014-5-24", "yyyy-MM-dd"));
        } catch (ParseException e) {
        } catch (Exception e) {
        }
    }
}