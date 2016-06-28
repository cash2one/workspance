import os
from time import ctime, sleep
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
import memcache
import datetime,requests
def companyquestioncount():
    sql="select account,company_id from company_account where exists(select company_id from  inquiry_count where company_id=company_account.company_id)  order by id asc"
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for list in resultlist:
            company_id=list[1]
            account=list[0]
            qcount=0
            gmt_created=datetime.datetime.now()
            gmt_modified=datetime.datetime.now()
            sqlc="select count(0) from inquiry where receiver_account=%s"
            cursor.execute(sqlc,[account])
            resultq=cursor.fetchone()
            if resultq:
                qcount=resultq[0]
            qvalue=[company_id,qcount,gmt_created,gmt_modified]
            sqlq="select id from inquiry_count where company_id=%s"
            cursor.execute(sqlq,[company_id])
            resultc=cursor.fetchone()
            if resultc:
                sqlu="update inquiry_count set qcount=%s,gmt_modified=%s where company_id=%s"
                cursor.execute(sqlu,[qcount,gmt_modified,company_id])
                conn.commit()
            else:
                sqlu="insert into inquiry_count(company_id,qcount,gmt_created,gmt_modified) values(%s,%s,%s,%s)"
                cursor.execute(sqlu,qvalue)
                conn.commit()
            print "suc"
            
def questioncountupdate(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchone()
    if resultc:
        return 1
    sql="select id,receiver_account,sender_account from inquiry where DATEDIFF(CURDATE(),gmt_created)<=1 and id>(select maxid from update_log where id=%s) order by id asc"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchall()
    if resultc:
        for list in resultc:
            try:
                qid=list[0]
                receiver_account=list[1]
                sender_account=list[2]
                
                sqlp="select company_id from company_account where account=%s"
                cursor.execute(sqlp,[sender_account])
                resultp=cursor.fetchone()
                if resultp:
                    fromcompany_id=resultp[0]
                    #安全加密判断
                    nowdate=str(datetime.datetime.now())
                    #回复互助获得钱包
                    try:
                        payload={'company_id':str(fromcompany_id),'ftype':'25','fee':'0.5','more':'1','key':str(nowdate)}
                        r= requests.get("http://app.zz91.com/sendfee.html",params=payload)
                    except:
                        a=1
                    
                sqlp="select company_id from company_account where account=%s"
                cursor.execute(sqlp,[receiver_account])
                resultp=cursor.fetchone()
                if resultp:
                    company_id=resultp[0]
                gmt_created=datetime.datetime.now()
                gmt_modified=datetime.datetime.now()
                qvalue=[company_id,1,gmt_created,gmt_modified]
                #//保存会话组
                if company_id>fromcompany_id:
                    conversation_group=str(company_id)+"-"+str(fromcompany_id)
                else:
                    conversation_group=str(fromcompany_id)+"-"+str(company_id)
                sqlg="update inquiry set conversation_group=%s where id=%s"
                cursor.execute(sqlg,[conversation_group,qid])
                conn.commit()
                
                sqlq="select id from inquiry_count where company_id=%s"
                cursor.execute(sqlq,[company_id])
                resultq=cursor.fetchone()
                if resultq:
                    sqlu="update inquiry_count set qcount=qcount+1,gmt_modified=%s where company_id=%s"
                    cursor.execute(sqlu,[gmt_modified,company_id])
                    conn.commit()
                else:
                    sqlu="insert into inquiry_count(company_id,qcount,gmt_created,gmt_modified) values(%s,%s,%s,%s)"
                    cursor.execute(sqlu,qvalue)
                    conn.commit()
                updateopenfloat(company_id,0)
                #---更新到APP的消息提醒里
                sqlc="select count(0) from inquiry where receiver_account=%s and is_viewed=0 "
                cursor.execute(sqlc,[receiver_account])
                resultq=cursor.fetchone()
                if resultq:
                    qcount=resultq[0]
                    if qcount>0 and company_id:
                        if company_id!=0 and str(company_id)!="0":
                            qvalue=['留言提醒','/myrc_leavewords/',gmt_created,'您有<font color=#f00>'+str(qcount)+'</font>条新留言，>>点此查看<<',company_id]
                            sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                            cursor.execute(sqlm,qvalue)
                            conn.commit()
                sqlc="update update_log set maxid=%s,reflag=1 where id=%s"
                cursor.execute(sqlc,[qid,uid])
                conn.commit()
                print "suc"
            except ValueError:
                continue
    sqlc="update update_log set reflag=0 where id=%s"
    cursor.execute(sqlc,[uid])
    conn.commit()
#----更新弹窗
def updateopenfloat(company_id,viewed):
    sql="select id from bbs_post_viewed where company_id=%s and bbs_post_id=0"
    cursor.execute(sql,[company_id])
    resultlist=cursor.fetchone()
    gmt_created=datetime.datetime.now()
    if resultlist:
        sqlu="update bbs_post_viewed set is_viewed=%s,gmt_created=%s where company_id=%s and bbs_post_id=0"
        cursor.execute(sqlu,[viewed,gmt_created,company_id])
        conn.commit()
    else:
        sqlu="insert into bbs_post_viewed(company_id,bbs_post_id,is_viewed,gmt_created) values(%s,%s,%s,%s)"
        cursor.execute(sqlu,[company_id,0,viewed,gmt_created])
        conn.commit()
companyquestioncount()

            
            