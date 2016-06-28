import os
from time import ctime, sleep
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
def reaccout():
    sql="select gmt_created,id from company_account where company_id=0"
    cursor.execute(sql)
    returnlist=cursor.fetchall()
    for list in returnlist:
        gmt_created=list[0]
        id=list[1]
        print gmt_created
        sql1="select id from company where gmt_created=%s"
        cursor.execute(sql1,[gmt_created])
        returnone=cursor.fetchone()
        if returnone:
            company_id=returnone[0]
            sql2="update company_account set company_id=%s where id=%s"
            cursor.execute(sql2,[company_id,id])
            conn.commit()
def repost():
    sql="SELECT account,id FROM bbs_post where company_id=0 order by id desc limit 0,2000"
    cursor.execute(sql)
    returnlist=cursor.fetchall()
    for list in returnlist:
        account=list[0]
        id=list[1]
        print account
        if account!="admin":
            sql1="select company_id from company_account where account=%s"
            cursor.execute(sql1,[account])
            returnone=cursor.fetchone()
            if returnone:
                
                company_id=returnone[0]
                print company_id
                sql2="update bbs_post set company_id=%s where id=%s"
                cursor.execute(sql2,[company_id,id])
                conn.commit()
            else:
                sql2="update bbs_post set company_id=%s where id=%s"
                cursor.execute(sql2,[1,id])
                conn.commit()
                print id
repost()
conn.close()
conn_news.close()