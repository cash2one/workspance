import os,sys
from time import ctime, sleep
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
import datetime,requests
reload(sys)
sys.setdefaultencoding('UTF-8')
def huzhureply(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchone()
    if resultc:
        return
    sql="select company_id,tocompany_id,bbs_post_id,id,gmt_created,title from bbs_post_reply where  id>(select maxid from update_log where id=%s) order by id asc"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchall()
    if resultc:
        for list in resultc:
            try:
                company_id=list[0]
                tocompany_id=list[1]
                bbs_post_id=list[2]
                id=list[3]
                gmt_created=list[4]
                title=list[5]
                #安全加密判断
                nowdate=datetime.datetime.now()
                mc.set("scretkey",nowdate,10)
                #回复互助获得钱包
                try:
                    payload={'company_id':str(company_id),'ftype':'27','fee':'0.5','more':'1','key':str(nowdate)}
                    r= requests.get("http://app.zz91.com/sendfee.html",params=payload)
                    print payload
                    print r.url
                except:
                    a=1
                if not tocompany_id:
                    sqld="select company_id from bbs_post where id=%s"
                    cursor.execute(sqld,[bbs_post_id])
                    resultd=cursor.fetchone()
                    if resultd:
                        tocompany_id=resultd[0]
                if title and title!="":
                    content='您在互助社区发布的信息"'+title+'"有人给你回复了。'
                else:
                    content='您在互助社区发布的信息有人给你回复了。'
                if tocompany_id!=0 and tocompany_id:
                    qvalue=['社区回复提醒','/huzhuview/'+str(bbs_post_id)+'.htm',gmt_created,content+'>>点此查看<<',tocompany_id]
                    if (str(tocompany_id)!="0" and tocompany_id):
                        sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                        cursor.execute(sqlm,qvalue)
                        conn.commit()
                sqlc="update update_log set maxid=%s,reflag=1 where id=%s"
                cursor.execute(sqlc,[id,uid])
                conn.commit()
            except ValueError:
                continue
            except IndexError:
                continue
    sqlc="update update_log set reflag=0 where id=%s"
    cursor.execute(sqlc,[uid])
    conn.commit()
def huzhupost(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchone()
    if resultc:
        return
    sql="select company_id,account,id from bbs_post where id>(select maxid from update_log where id=%s) order by id asc "
    cursor.execute(sql,[uid])
    resultc=cursor.fetchall()
    nowdate=datetime.datetime.now()
    if resultc:
        for list in resultc:
            company_id=list[0]
            account=list[1]
            id=list[2]
            if account!="admin":
                #安全加密判断
                mc.set("scretkey",nowdate,10)
                #回复互助获得钱包
                try:
                    payload={'company_id':str(company_id),'ftype':'26','fee':'0.5','more':'1','key':str(nowdate)}
                    r= requests.get("http://app.zz91.com/sendfee.html",params=payload)
                    print payload
                    print r.url
                    print r.text
                except:
                    a=1
            sqlc="update update_log set maxid=%s,reflag=1 where id=%s"
            cursor.execute(sqlc,[id,uid])
            conn.commit()
    sqlc="update update_log set reflag=0 where id=%s"
    cursor.execute(sqlc,[uid])
    conn.commit()

huzhupost(15)
huzhureply(11)


