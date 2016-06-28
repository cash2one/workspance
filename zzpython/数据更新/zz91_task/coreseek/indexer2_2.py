# -*- coding: UTF-8 -*-
import time
import sys,re,os
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")


reload(sys) 
sys.setdefaultencoding('utf-8')

def updateprice():
    sql="SELECT UNIX_TIMESTAMP(max(p.gmt_modified)) FROM price as p where p.is_checked='1' and UNIX_TIMESTAMP(p.gmt_modified)>( SELECT max_doc_id FROM counter1 WHERE id=5) "
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf delta_price --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge price delta_price --config /mnt/coreseek/etc/mysql1_index2.conf --rotate --merge-dst-range deleted 0 0")
            sqlc="update counter1 set max_doc_id=%s where id=5"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updateprice()
def updatepricelist():
    sql="SELECT UNIX_TIMESTAMP(max(p.gmt_modified)) FROM price_list as p where UNIX_TIMESTAMP(p.gmt_modified)>( SELECT max_doc_id FROM counter1 WHERE id=13) "
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf pricelisttoday --rotate")
            sqlc="update counter1 set max_doc_id=%s where id=13"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updatepricelist()

def updatenews():
    sql="SELECT max(p.id) FROM dede_archives as p where p.id>( SELECT max_doc_id FROM counter1 WHERE id=9) "
    cursornews.execute(sql)
    resultlist=cursornews.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf delta_news --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge news delta_news --config /mnt/coreseek/etc/mysql1_index2.conf --rotate")
            sqlc="update counter1 set max_doc_id=%s where id=9"
            cursor.execute(sqlc,[max_doc_id])
            conn_news.commit()
updatenews()
def updatecompanyprice():
    sql="SELECT UNIX_TIMESTAMP(max(p.refresh_time)) FROM company_price as p where UNIX_TIMESTAMP(p.refresh_time)>( SELECT max_doc_id FROM counter1 WHERE id=11) and p.is_checked='1' and not exists(select id from company where p.company_id = id and is_block ='1')"
    cursor.execute(sql)
    resultlist=cursor.fetchone()
    if (resultlist!=None):
        max_doc_id=resultlist[0]
        if max_doc_id!=None:
            os.system("/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf delta_company_price --rotate")
            os.system("/usr/local/coreseek/bin/indexer --merge company_price delta_company_price --config /mnt/coreseek/etc/mysql1_index2.conf --rotate --merge-dst-range deleted 0 0")
            sqlc="update counter1 set max_doc_id=%s where id=11"
            cursor.execute(sqlc,[max_doc_id])
            conn.commit()
updatecompanyprice()
conn.close()
conn_news.close()






