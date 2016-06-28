#-*- coding:utf-8 -*-
from public import *
from time import ctime, sleep
from zz91db_ast import companydb
from zz91conn import database_mongodb
dbc=companydb()
#连接集合（表）
dbmongo=database_mongodb()
collection=dbmongo.appmessages
#print collection.count()
#collection.remove({})


def insertmongolist(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    resultc=dbc.fetchonedb(sql,[uid])
    if resultc:
        return
    sql="select id,company_id,title,content,url,is_push,gmt_created from app_message where id>(select maxid from update_log where id=%s) order by id asc"
    result=dbc.fetchalldbmain(sql,[uid])
    if result:
        for l in result:
            title=l[2]
            type=0
            if '留言提醒' in title:
                type=1
            if '社区回复提醒' in title:
                type=2
            if '钱包提醒' in title:
                type=3
            
            list={'id':l[0],
                  'company_id':l[1],
                  'title':l[2],
                  'content':l[3],
                  'url':l[4],
                  'is_push':l[5],
                  'gmt_created':l[6],
                  'type':type,
                  'isview':0
                  }
            listcount=collection.find({"id":l[0]}).count()
            if listcount==0:
                collection.insert(list)
            sqla="update update_log set maxid=%s,reflag=1 where id=%s"
            dbc.updatetodb(sqla,[l[0],uid])
            listcount=collection.count()
            print listcount
    sqlc="update update_log set reflag=0 where id=%s"
    dbc.updatetodb(sqlc,[uid])
def updateview(uid):
    sql="select id from update_log where reflag=1 and id=%s"
    resultc=dbc.fetchonedb(sql,[uid])
    if resultc:
        return
    sql="select id,mid,company_id,is_views,is_push from app_message_view where id>(select maxid from update_log where id=%s) order by id asc"
    result=dbc.fetchalldbmain(sql,[uid])
    if result:
        for l in result:
            id=l[0]
            mid=l[1]
            is_views=l[3]
            if not is_views:
                is_views=0
            is_push=l[4]
            if not is_push:
                is_push=0
            sqlm="select company_id from app_message where id=%s"
            resultm=dbc.fetchonedbmain(sqlm,[mid])
            if resultm:
                company_id=resultm[0]
                
                if str(company_id)!="0":
                    print company_id
                    collection.update({"id": mid}, {"$set": {"isview": int(is_views),"is_push": int(is_push)}});
            sqla="update update_log set maxid=%s,reflag=1 where id=%s"
            dbc.updatetodb(sqla,[l[0],uid])
    sqlc="update update_log set reflag=0 where id=%s"
    dbc.updatetodb(sqlc,[uid])
insertmongolist(13)
updateview(14)
