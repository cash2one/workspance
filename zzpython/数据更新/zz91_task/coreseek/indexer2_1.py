# -*- coding: UTF-8 -*-
import time
import sys,re,os
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
reload(sys) 
sys.setdefaultencoding('utf-8') 
#import bs4
#from bs4 import BeautifulSoup

os.system("python /mnt/python/zz91_task/offer/offer_index.py")

def updateproducts():
    sql="SELECT UNIX_TIMESTAMP(max(p.gmt_modified)) FROM products_s as p where p.viptype<1 AND UNIX_TIMESTAMP(p.gmt_modified)>( SELECT max_doc_id FROM counter1 WHERE id=1) AND not exists(select id from company where p.company_id = id and is_block ='1')"
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index.conf delta_offersearch --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge offersearch_new delta_offersearch --config /mnt/coreseek/etc/mysql1_index.conf --rotate --merge-dst-range deleted 0 0")
            sqlc="update counter1 set max_doc_id=%s where id=1"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updateproducts()
def updateproductsvip():
    sql="SELECT UNIX_TIMESTAMP(max(p.gmt_modified)) FROM products_s as p where p.viptype>=1 AND UNIX_TIMESTAMP(p.gmt_modified)>( SELECT max_doc_id FROM counter1 WHERE id=12) AND not exists(select id from company where p.company_id = id and is_block ='1')"
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        print max_doc_id
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index.conf --rotate offersearch_new_vip")
            sqlc="update counter1 set max_doc_id=%s where id=12"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updateproductsvip() 
def updatequestion():
    sql="SELECT max(id) FROM inquiry where id>( SELECT max_doc_id FROM counter1 WHERE id=3) "
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        print max_doc_id
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index.conf delta_question --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge question delta_question --config /mnt/coreseek/etc/mysql1_index.conf --rotate --merge-dst-range deleted 0 0")
            sqlc="update counter1 set max_doc_id=%s where id=3"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit() 
updatequestion()
def updatehuzhu():
    sql="SELECT UNIX_TIMESTAMP(max(p.gmt_modified)) FROM bbs_post as p where UNIX_TIMESTAMP(p.gmt_modified)>( SELECT max_doc_id FROM counter1 WHERE id=4) AND not exists(select id from company where p.company_id = id and is_block ='1')"
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        print max_doc_id
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index.conf delta_huzhu --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge huzhu delta_huzhu --config /mnt/coreseek/etc/mysql1_index.conf --rotate --merge-dst-range deleted 0 0")
            sqlc="update counter1 set max_doc_id=%s where id=4"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updatehuzhu()
def updateyuanliao():
    sql="SELECT UNIX_TIMESTAMP(max(yl.gmt_modified)) FROM yuanliao as yl where UNIX_TIMESTAMP(yl.gmt_modified)>( SELECT max_doc_id FROM counter1 WHERE id=14) AND not exists(select id from company where yl.company_id = id and is_block ='1')"
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if resultlist:
        max_doc_id=resultlist[0]
        print max_doc_id
        if max_doc_id:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index.conf delta_yuanliao --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge yuanliao delta_yuanliao --config /mnt/coreseek/etc/mysql1_index.conf --rotate --merge-dst-range deleted 0 0")
            sqlc="update counter1 set max_doc_id=%s where id=14"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updateyuanliao()
conn.close()
conn_news.close()
