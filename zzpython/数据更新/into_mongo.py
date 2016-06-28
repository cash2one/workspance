#-*- coding:utf-8 -*-
import MySQLdb
import pymongo
#from DBUtils.PooledDB import PooledDB

conn = pymongo.Connection("127.0.0.1",27017)
mdb=conn.ast

#取出
def select(tablename):
    db = MySQLdb.connect(user='root', db='ast', passwd='zj88friend', host='192.168.2.10', charset="utf8")
    cursor=db.cursor(cursorclass = MySQLdb.cursors.DictCursor)
    sql='select * from '+tablename
    cursor.execute(sql) 
    result=cursor.fetchall() 
    insert(result,tablename)
    
#插入
def insert(result,tablename):
    #建表
    collection=mdb[tablename]
    if result:
        #找出mongodb中的所有id：
        mongo_idlist=[]
        a=collection.find({},{'id':1})
        for b in a:
            mongo_idlist.append(b['id'])
        print mongo_idlist        
        for list in result:
            if list['id'] not in mongo_idlist:
                collection.insert(list)
        
if __name__=='__main__': 
    mysql_tablelist=['bbs_post_categorys','bbs_post_category','bbs_post_type','category','category_company_price','category_garden','category_products','chart_category','data_index','data_index_category','ip_area','mail_template','pay_wallettype','price_category','price_category_attr']
    for table in mysql_tablelist:     
        select(table)









