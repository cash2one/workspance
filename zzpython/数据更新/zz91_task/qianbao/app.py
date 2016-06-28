import os,sys
from time import ctime, sleep
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
reload(sys)
sys.setdefaultencoding('UTF-8')
def message(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchone()
    if resultc:
        return 1
    sql="select company_id,fee,ftype,id,gmt_created from pay_mobileWallet where  id>(select maxid from update_log where id=%s) order by id asc"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchall()
    nowdate=datetime.datetime.now()
    if resultc:
        for list in resultc:
            try:
                company_id=list[0]
                fee=list[1]
                
                ftype=list[2]
                qid=list[3]
                gmt_created=list[4]
                sqlq="select name from  pay_wallettype where id=%s"
                cursor.execute(sqlq,[ftype])
                resultq=cursor.fetchone()
                if resultq:
                    fname=resultq[0]
                if fee>0:
                    content='您的钱包在"'+fname+'"中获得'+str(fee)+'元。'
                else:
                    content='您的钱包在"'+fname+'"中扣除'+str(fee)+'元。'
                if fee!=0:
                    qvalue=['钱包提醒','/qianbao/zhangdan.html',gmt_created,content+'>>点此查看<<',company_id]
                    if (str(company_id)!="0"):
                        sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                        cursor.execute(sqlm,qvalue)
                        conn.commit()
            except ValueError:
                continue
            sqlc="update update_log set maxid=%s,reflag=1 where id=%s"
            cursor.execute(sqlc,[qid,uid])
            conn.commit()
            print qid
    sqlc="update update_log set reflag=0 where id=%s"
    cursor.execute(sqlc,[uid])
    conn.commit()
message(9)

#os.system("python /usr/apps/python/ldb/ldb.py")
#os.system("python /usr/apps/python/huzhu/appmessages.py")
#os.system("python /usr/apps/python/messages/update.py")