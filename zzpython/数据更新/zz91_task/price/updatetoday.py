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
def outtable(pid):
    arrtype="(308,40,41,42,45,43,44,46,47,49,50,52,66,70,72,71,69,206,79,65,80,81,210,83,84,86,208,279,"
    arrtype=arrtype+"110,121,120,119,118,115,114,113,112,111,126,61,62,63,98,231,26,27,28,29,324)"
    #----233 期货价格不要
    sql="select id,assist_type_id from price where type_id in "+arrtype+" and DATEDIFF(CURDATE(),gmt_modified)<=3 and is_checked=1 "
    #sql=sql+" and not exists(select priceid from price_titlefild where priceid=price.id)"
    #sql=sql+" and not exists(select priceid from price_list where priceid=price.id)"
    #sql=sql+" and id>(select maxid from update_log where id=8)"
    #sql=sql+" order by id asc"
    if pid:
        sql=sql+" and id in ("+str(pid)+")"
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for list in resultlist:
            priceid=list[0]
            
            print priceid
            #sqld="delete from price_list where priceid=%s"
            #cursor.execute(sqld,[priceid])
            #sqld="delete from price_titlefild where priceid=%s"
            #cursor.execute(sqld,[priceid])
            pricetable(list[0])
            #sqlb="update update_log set maxid=%s where id=8"
            #cursor.execute(sqlb,[priceid])
            #conn.commit()
outtable(None)

        