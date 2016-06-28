# -*- coding: UTF-8 -*-
import time
import sys,re
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
reload(sys) 
sys.setdefaultencoding('utf-8') 
import bs4
from bs4 import BeautifulSoup
from function import *
def outtable(pid,uid):
    sql="select id from update_log where reflag=1 and id=%s"
    cursor.execute(sql,[uid])
    resultc=cursor.fetchone()
    if resultc:
        return
    arrtype="(308,40,41,42,45,43,44,46,47,49,50,52,66,70,72,71,69,206,79,65,80,81,210,83,84,86,208,279,"
    arrtype=arrtype+"110,121,120,119,118,115,114,113,112,111,126,61,62,63,98,231,26,27,28,29,324)"
    #----233 期货价格不要
    #arrtype="(110)"
    sql="select id,assist_type_id from price where type_id in "+arrtype+" and  gmt_created>'2010-1-1' and is_checked='1' "
    #sql=sql+" and not exists(select priceid from price_titlefild where priceid=price.id)"
    sql=sql+" and id>(select maxid from update_log where id="+str(uid)+")"
    sql=sql+" order by id asc"
    if pid:
        sql=sql+" and id="+str(pid)
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for list in resultlist:
            try:
                priceid=list[0]
                sqld="delete from price_titlefild where priceid=%s"
                cursor.execute(sqld,[priceid])
                conn.commit()
                pricetable(list[0])
            except ValueError:
                continue
            except IndexError:
                continue
            sqlb="update update_log set maxid=%s,reflag=1 where id=%s"
            cursor.execute(sqlb,[priceid,uid])
            conn.commit()
    sqlc="update update_log set reflag=0 where id=%s"
    cursor.execute(sqlc,[uid])
    conn.commit()
    #type_id=46,51,20 特殊
    #http://price.zz91.com/priceDetails_539330.htm
    #http://price.zz91.com/priceDetails_538597.htm
    #http://price.zz91.com/priceDetails_539366.htm
    #http://price.zz91.com/priceDetails_539362.htm
#pricetable(346813)
#outtable(559990)   
outtable(None,8)

        