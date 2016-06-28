#-*- coding:utf-8 -*-
from pinyin import chinese_abstract
import MySQLdb
import time,os
import sys,re
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")

reload(sys) 
sys.setdefaultencoding('utf-8')
s=u''
def changepinyin():
    sql="select label,id,pinyin from category_products"
    cursor.execute(sql)
    alist = cursor.fetchall()
    for list in alist:
        s=unicode(list[0])
        print s
        id=list[1]
        pinyin=chinese_abstract(s)
        npinyin=list[2]
        npinyin=npinyin.replace("/","")
        npinyin=npinyin.replace("(","")
        npinyin=npinyin.replace(")","")
        pinyin=pinyin.replace("/","")
        pinyin=pinyin.replace("(","")
        pinyin=pinyin.replace(")","")
        print pinyin
        if npinyin:
            sqlc="select pinyin from category_products where pinyin=%s and id not in (%s)"
            cursor.execute(sqlc,[npinyin,id])
            blist = cursor.fetchone()
            if blist:
                sqld="update category_products set pinyin=%s where id=%s"
                cursor.execute(sqld,[npinyin+"1",id])
            else:
                sqld="update category_products set pinyin=%s where id=%s"
                cursor.execute(sqld,[pinyin,id])
        else:
            sqld="update category_products set pinyin=%s where id=%s"
            cursor.execute(sqld,[pinyin,id])
        conn.commit()
#加载表data_index拼音            
def changeindexdatapinyin():
    sql="select title,id,pinyin from data_index"
    cursor.execute(sql)
    alist = cursor.fetchall()
    for list in alist:
        s=list[0]
        id=list[1]
        pinyin=chinese_abstract(s)
        npinyin=list[2]
        npinyin=npinyin.replace("/","")
        npinyin=npinyin.replace("(","")
        npinyin=npinyin.replace(")","")
        pinyin=pinyin.replace("/","")
        pinyin=pinyin.replace("(","")
        pinyin=pinyin.replace(")","")
        #如果拼音存在
        if npinyin:
            sqlc="select pinyin from data_index where pinyin=%s and id not in (%s)"
            cursor.execute(sqlc,[npinyin,id])
            blist = cursor.fetchall()
            if blist:
                num=len(blist)+1
                sqld="update data_index set pinyin=%s where id=%s"
                cursor.execute(sqld,[npinyin+str(num),id])
            else:
                sqld="update data_index set pinyin=%s where id=%s"
                cursor.execute(sqld,[pinyin,id])
        else:
            sqld="update data_index set pinyin=%s where id=%s"
            cursor.execute(sqld,[pinyin,id])
        conn.commit()
            
def changeindexdatapinyin_category():
    sql="select label,id,pinyin from data_index_category"
    cursor.execute(sql)
    alist = cursor.fetchall()
    for list in alist:
        s=list[0]
        id=list[1]
        pinyin=chinese_abstract(s)
        npinyin=list[2]
        npinyin=npinyin.replace("/","")
        npinyin=npinyin.replace("(","")
        npinyin=npinyin.replace(")","")
        pinyin=pinyin.replace("/","")
        pinyin=pinyin.replace("(","")
        pinyin=pinyin.replace(")","")
        if npinyin:
            sqlc="select pinyin from data_index_category where pinyin=%s and id not in (%s)"
            cursor.execute(sqlc,[npinyin,id])
            blist = cursor.fetchall()
            if blist:
                num=len(blist)+1
                sqld="update data_index_category set pinyin=%s where id=%s"
                cursor.execute(sqld,[npinyin+str(num),id])
            else:
                sqld="update data_index_category set pinyin=%s where id=%s"
                cursor.execute(sqld,[pinyin,id])
        else:
            sqld="update data_index_category set pinyin=%s where id=%s"
            cursor.execute(sqld,[pinyin,id])
        conn.commit()
            
def changepinyin_price():
    sql="select name,id,pinyin from price_category"
    cursor.execute(sql)
    alist = cursor.fetchall()
    for list in alist:
        s=unicode(list[0])
        id=list[1]
        pinyin=chinese_abstract(s)
        npinyin=list[2]
        npinyin=npinyin.replace("/","")
        npinyin=npinyin.replace("(","")
        npinyin=npinyin.replace(")","")
        pinyin=pinyin.replace("/","")
        pinyin=pinyin.replace("(","")
        pinyin=pinyin.replace(")","")
        if npinyin:
            sqlc="select pinyin from price_category where pinyin=%s and id not in (%s)"
            cursor.execute(sqlc,[npinyin,id])
            blist = cursor.fetchone()
            if blist:
                sqld="update price_category set pinyin=%s where id=%s"
                cursor.execute(sqld,[npinyin+"1",id])
            else:
                sqld="update price_category set pinyin=%s where id=%s"
                cursor.execute(sqld,[pinyin,id])
        else:
            sqld="update price_category set pinyin=%s where id=%s"
            cursor.execute(sqld,[pinyin,id])
        conn.commit()
#搜索数据更新到微门户
def updatepro():
    sql="select kw,id from analysis_trade_keywords where gmt_target>'2015-8-1' order by num desc limit 0,2000"
    cursor.execute(sql)
    alist = cursor.fetchall()
    for list in alist:
        kw=list[0]
        id=list[1]
        kw=kw.replace(" ","")
        kw=kw.replace(",","")
        if kw and kw!="" and len(kw)<=6:
            pinyin=chinese_abstract(kw.decode('utf-8','ignore'))
            print pinyin
            sqlc="select pingyin from daohang where pingyin=%s"
            cursor.execute(sqlc,[pinyin])
            blist = cursor.fetchone()
            if blist==None:
                print kw
                print pinyin
                #sqld="insert into daohang(type,sid,label,pingyin) values(%s,%s,%s,%s)"
                #cursor.execute(sqld,[1,3738,kw,pinyin])
                #conn.commit()
#updatepro()
changepinyin()
#changepinyin_price()

#changepinyin_price()
#changeindexdatapinyin()
#changeindexdatapinyin_category()
#print chinese_abstract(u"钯")
#changeindexdatapinyin()
#changeindexdatapinyin_category()


