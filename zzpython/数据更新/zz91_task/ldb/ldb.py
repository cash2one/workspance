import os,sys
from time import ctime, sleep
import datetime
from datetime import timedelta, date
import time
execfile("/mnt/python/zz91_task/conn_server.py")
import memcache,requests
reload(sys)
sys.setdefaultencoding('UTF-8')
#时间戳转为正常时间
def timestamp_datetime(value):
    h=int(value / 3600)
    m=int(value / 60)
    s=int(value-h*3600-m*60)
    return str(h)+"时"+str(m)+"分"+str(s)+"秒"
#----获得来电宝帐号总余额
def getldblaveall(company_id):
    #----查看未接来电费用
    sqls='select sum(click_fee) from phone_call_click_fee where company_id=%s'
    cursor.execute(sqls,[company_id])
    results=cursor.fetchone()
    phone_call_click_fee=0
    if results:
        phone_call_click_fee=results[0]
        if phone_call_click_fee==None:
            phone_call_click_fee=0
    #----电话费用
    sqls='select sum(call_fee) from phone_log where company_id=%s'
    cursor.execute(sqls,[company_id])
    results=cursor.fetchone()
    call_fee=0
    if results:
        call_fee=results[0]
        if call_fee==None:
            call_fee=0
    #----点击查看联系方式费用
    sqls='select sum(click_fee) from phone_click_log where company_id=%s'
    cursor.execute(sqls,[company_id])
    results=cursor.fetchone()
    phone_click_fee_fee=0
    if results:
        phone_click_fee_fee=results[0]
        if phone_click_fee_fee==None:
            phone_click_fee_fee=0
                        
    sqls='select sum(amount) from phone where company_id=%s'
    cursor.execute(sqls,[company_id])
    results=cursor.fetchone()
    lave=0
    if results:
        lave=results[0]
        if lave:
            lave=lave-phone_call_click_fee-phone_click_fee_fee-call_fee
        else:
            lave=0
    return '%.2f'%lave

def getcompanyidcaller(caller_id):
    sql="select company_id from company_account where mobile=%s"
    cursor.execute(sql,[caller_id])
    resultc=cursor.fetchone()
    if resultc:
        return resultc[0]
def ldbmessage(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchone()
    if resultc:
        return
    sql="select id,company_id,caller_id,province,city,tel,call_fee,start_time,state,gmt_created,end_time,call_sn from phone_log where id>(select maxid from update_log where id=%s) order by id asc"
    #sql="select id,company_id,caller_id,province,city,tel,call_fee,start_time,state,gmt_created,end_time,call_sn from phone_log where id=237809 order by id asc"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchall()
    nowdate=datetime.datetime.now()
    if resultc:
        for list in resultc:
            try:
                qid=list[0]
                company_id=list[1]
                caller_id=list[2]
                call_fee=list[6]
                fromcompany_id=getcompanyidcaller(caller_id)
                if fromcompany_id:
                    #大于一分钟
                    if int(call_fee)>=4:
                        
                        #安全加密判断
                        nowdate=datetime.datetime.now()
                        mc.set("scretkey",nowdate,10)
                        #拨打400获得钱包
                        try:
                            payload={'company_id':str(fromcompany_id),'ftype':'24','fee':'1','more':'1','key':str(nowdate)}
                            r= requests.get("http://app.zz91.com/sendfee.html",params=payload)
                        except:
                            a=1
                
                province=list[3]
                city=list[4]
                tel=list[5]
                call_fee='%.2f'%list[6]
                start_time=list[7]
                end_time=list[10]
                state=list[8]
                gmt_created=list[9]
                call_sn=list[10]
                
                start_timeint=time.mktime(time.strptime(str(start_time),'%Y-%m-%d %H:%M:%S'))
                end_timeint=time.mktime(time.strptime(str(end_time),'%Y-%m-%d %H:%M:%S'))
                yerflag=0
                timecha=timestamp_datetime(end_timeint-start_timeint)
                if str(state)=="0" and str(call_sn)!="0":
                    title="来电未接提醒"
                    ldburl="/ldb_weixin/phonerecords.html"
                    content="尊敬的来电宝会员，您的"+province+"（"+city+"）客户"+caller_id[0:7]+"***在  "+str(start_time)+" 来电"
                if str(state)=="1" and str(call_sn)!="0":
                    title="来电宝消费提醒"
                    yerflag=1
                    ldburl="/ldb_weixin/phonerecords.html"
                    feeall=getldblaveall(company_id)
                    content="尊敬的来电宝会员，您与"+str(province)+"（"+str(city)+"）客户"+str(caller_id[0:7])+"***在  "+str(start_time)+" 通话"+str(timecha)+"，消费"+str(call_fee)+"元，您当前的余额为"+str(feeall)+"元。"
                if str(call_sn)=="0":
                    title="月租提醒"
                    yerflag=1
                    ldburl="/ldb_weixin/phonerecords.html"
                    feeall=getldblaveall(company_id)
                    content="尊敬的来电宝会员，您的账号已于"+str(start_time)+"扣除"+caller_id+call_fee+"元，您当前的余额为"+str(feeall)+"元。"
                if yerflag==1:
                    if feeall<150:
                        title="余额提醒"
                        ldburl="/ldb_weixin/balance.html"
                        content="尊敬的来电宝会员，截止到"+str(start_time)+"，您的余额不足150元。请您及时交费，以免余额不足时被停机而错失商机。如有疑问欢迎来电：0571-56633067。"
                        if str(company_id)!="0" and company_id:
                            qvalue=[title,ldburl,gmt_created,content+'>>点此查看<<',company_id]
                            if (str(company_id)!="0"):
                                sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                                cursor.execute(sqlm,qvalue)
                                conn.commit()
            
                if str(company_id)!="0" and company_id:
                    qvalue=[title,ldburl,gmt_created,content+'>>点此查看<<',company_id]
                    if (str(company_id)!="0"):
                        sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                        cursor.execute(sqlm,qvalue)
                        conn.commit()
                print qid
            except ValueError:
                continue
            except IndexError:
                continue
            sqlc="update update_log set maxid=%s,reflag=1 where id=%s"
            cursor.execute(sqlc,[qid,uid])
            conn.commit()
    sqlc="update update_log set reflag=0 where id=%s"
    cursor.execute(sqlc,[uid])
    conn.commit()

ldbmessage(10)
