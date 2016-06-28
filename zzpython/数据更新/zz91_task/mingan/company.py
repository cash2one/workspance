#!/usr/bin/env python    
# -*- coding: utf-8 -*-    
#导入smtplib和MIMEText    
from public import *
from time import ctime, sleep
import datetime
from datetime import timedelta, date 
import MySQLdb
import os
from zz91conn import database_comp,database_tags
from mingang import getmingganword
conn = database_comp()
conntags = database_tags()
cursor=conn.cursor(cursorclass = MySQLdb.cursors.DictCursor)
cursortags=conntags.cursor(cursorclass = MySQLdb.cursors.DictCursor)
def companylist():
    sql="select id,name,business,address,sale_details,buy_details,introduction from company where is_block=0 and membership_code='10051000' and id>(select maxid from update_log where id=19) order by id asc limit 0,100000"
    cursor.execute(sql)
    results = cursor.fetchall()
    for list in results:
        company_id=list['id']
        for ll in list:
            content=str(list[ll])
            gmt_created=datetime.datetime.now()
            m=getmingganword(content)
            if m:
                sql1="update company set is_block=1 where id=%s"
                cursor.execute(sql1,[company_id])
                conn.commit()
                burl="http://admin1949.zz91.com/web/zz91/crm/company/detail.htm?companyId="+str(company_id)
                sql2="insert into company_block(company_id,keyv,burl,gmt_created) values(%s,%s,%s,%s)"
                cursor.execute(sql2,[company_id,m,burl,gmt_created])
                conn.commit()
                print m
                print company_id
        sql3="update update_log set maxid=%s where id=19"
        cursor.execute(sql3,[company_id])
        conn.commit()
def huzhulist():
    sql="select id,company_id,title,content from bbs_post where is_del=0 and id>(select maxid from update_log where id=20) order by id asc limit 0,5000"
    cursor.execute(sql)
    results = cursor.fetchall()
    for list in results:
        company_id=list['company_id']
        id=list['id']
        for ll in list:
            content=str(list[ll])
            m=getmingganword(content)
            if m:
                gmt_created=datetime.datetime.now()
                sql1="update company set is_block=1 where id=%s"
                cursor.execute(sql1,[company_id])
                conn.commit()
                burl="http://admin1949.zz91.com/web/zz91/bbs/post/edit.htm?id="+str(id)+"&companyId="+str(company_id)
                sql2="insert into company_block(company_id,keyv,burl,gmt_created) values(%s,%s,%s,%s)"
                cursor.execute(sql2,[company_id,m,burl,gmt_created])
                conn.commit()
                sql1="update bbs_post set is_del=1,check_status=0 where id=%s"
                cursor.execute(sql1,[id])
                conn.commit()
                print m
                print company_id
                print id
        sql3="update update_log set maxid=%s where id=20"
        cursor.execute(sql3,[id])
        conn.commit()
def bbs_post_reply():
    sql="select id,company_id,title,content from bbs_post_reply where is_del=0 and id>(select maxid from update_log where id=21) order by id asc limit 0,50000"
    cursor.execute(sql)
    results = cursor.fetchall()
    for list in results:
        id=list['id']
        for ll in list:
            content=str(list[ll])
            m=getmingganword(content)
            if m:
                sql1="update bbs_post_reply set is_del=1 where id=%s"
                cursor.execute(sql1,[id])
                conn.commit()
                print m
                print id
        sql3="update update_log set maxid=%s where id=21"
        cursor.execute(sql3,[id])
        conn.commit()
def tagslist():
    sql="select id,tags from tags where closeflag=0 and id>(select maxid from update_log where id=1) order by id asc limit 0,50000"
    cursortags.execute(sql)
    results = cursortags.fetchall()
    for list in results:
        id=list['id']
        tags=list['tags']
        m=getmingganword(tags)
        if m:
            sql1="update tags set closeflag=1 where id=%s"
            cursortags.execute(sql1,[id])
            conntags.commit()
            print m
            print id
        sql3="update update_log set maxid=%s where id=1"
        cursortags.execute(sql3,[id])
        conntags.commit()
def daohang():
    sql="select id,label from daohang where id>(select maxid from update_log where id=22) order by id asc limit 0,50000"
    cursor.execute(sql)
    results = cursor.fetchall()
    for list in results:
        id=list['id']
        label=list['label']
        m=getmingganword(label)
        if m:
            sql1="delete from daohang where id=%s"
            cursor.execute(sql1,[id])
            conn.commit()
            print m
            print id
        sql3="update update_log set maxid=%s where id=22"
        cursor.execute(sql3,[id])
        conn.commit()
companylist()
huzhulist()
bbs_post_reply()
tagslist()
daohang()