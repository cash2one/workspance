#-*- coding:utf-8 -*-
from zz91db_130 import otherdb
from zz91tools import formattime
import datetime

class getlog:
    def __init__(self):
        self.db=otherdb()
    def dellog(self,gmt_begin,gmt_end):
        sql='delete from log_auto where gmt_created>=%s and gmt_created<%s'
        self.db.updatetodb(sql,[gmt_begin,gmt_end])
    def addlognews(self,title,get_time,typeid,details,sortrank):
        sql='insert into log_news(title,get_time,typeid,details,sortrank) values(%s,%s,%s,%s,%s)'
        self.db.updatetodb(sql,[title,get_time,typeid,details,sortrank])
    def updatelognews(self,title,get_time,typeid,details,sortrank,id):
        sql='update log_news set title=%s,get_time=%s,typeid=%s,details=%s,sortrank=%s where id=%s'
        self.db.updatetodb(sql,[title,get_time,typeid,details,sortrank,id])
    def addlogtype(self,name,sortrank):
        sql='insert into log_type(name,sortrank) values(%s,%s)'
        self.db.updatetodb(sql,[name,sortrank])
    def updatelogtype(self,name,sortrank,id):
        sql='update log_type set name=%s,sortrank=%s where id=%s'
        self.db.updatetodb(sql,[name,sortrank,id])
    def insertinto(self,details_id,gmt_date):
        sql2='select id from log_auto where details_id=%s and gmt_date=%s and is_ok=0'
        result2=self.db.fetchonedb(sql2,[details_id,gmt_date])
        if result2:
            id=result2[0]
            sql4='update log_auto set is_ok=1 where id=%s'
            self.db.updatetodb(sql4,[id])
    def getlogtypelist(self):
        sql1='select count(0) from log_type'
        count=self.db.fetchnumberdb(sql1)
        sql='select id,name,sortrank from log_type'
        resultlist=self.db.fetchalldb(sql)
        listall=[]
        for result in resultlist:
            list={'id':result[0],'name':result[1],'sortrank':result[2]}
            listall.append(list)
        return listall
    def getlognewslist(self,frompageCount,limitNum,typeid='',title='',details=''):
        sqlarg=''
        if typeid:
            sqlarg+=' and typeid='+typeid
        if title:
            sqlarg+=' and title like"%'+title+'%"'
        if details:
            sqlarg+=' and details like"%'+details+'%"'
        sql1='select count(0) from log_news where id>0'+sqlarg
        count=self.db.fetchnumberdb(sql1)
        sql='select id,title,typeid,details,sortrank,get_time from log_news where id>0'+sqlarg
        sql+=' limit '+str(frompageCount)+','+str(limitNum)
        resultlist=self.db.fetchalldb(sql)
        listall=[]
        for result in resultlist:
            typeid=result[2]
            typename=self.getlogtypename(typeid)
            list={'id':result[0],'title':result[1],'typeid':typeid,'typename':typename,'details':result[3],'sortrank':result[4],'get_time':result[5]}
            listall.append(list)
        return {'list':listall,'count':count}
    def getloglist(self,frompageCount,limitNum,gmt_begin='',gmt_end='',typeid='',is_ok='',title='',details=''):
        sqlarg=''
        argument=[]
        if gmt_begin:
            sqlarg+=' and gmt_created>=%s'
            argument.append(gmt_begin)
        if gmt_end:
            sqlarg+=' and gmt_created<%s'
            argument.append(gmt_end)
        if typeid:
            sqlarg+=' and typeid=%s'
            argument.append(typeid)
        if is_ok:
            sqlarg+=' and is_ok=%s'
            argument.append(is_ok)
        if title:
            sqlarg+=' and title like"%'+title+'%"'
#            argument.append(title)
        if details:
            sqlarg+=' and details like"%'+details+'%"'
#            argument.append(details)
        sql1='select count(0) from log_auto where id>0'+sqlarg
        count=self.db.fetchnumberdb(sql1,argument)
        sql='select id,typeid,title,gmt_date,is_ok,details_id,gmt_created from log_auto where id>0'+sqlarg
        sql+=' order by gmt_created desc'
        sql+=' limit '+str(frompageCount)+','+str(limitNum)
        resultlist=self.db.fetchalldb(sql,argument)
        listall=[]
        for result in resultlist:
            typeid=result[1]
            is_ok=result[4]
            details_id=result[5]
            details=self.getnewsdetail(details_id)
            typename=self.getlogtypename(typeid)
            list={'id':result[0],'typeid':typeid,'typename':typename,'title':result[2],'gmt_date':formattime(result[3],1),'is_ok':is_ok,'details':details,'gmt_created':formattime(result[6])}
            listall.append(list)
        return {'list':listall,'count':count}
    def getlogtypename(self,id):
        sql='select name from log_type where id=%s'
        result=self.db.fetchonedb(sql,[id])
        if result:
            return result[0]
    def gettypedatail(self,id):
        sql='select name,sortrank from log_type where id=%s'
        result=self.db.fetchonedb(sql,[id])
        if result:
            return {'name':result[0],'sortrank':result[1]}
    def getnewsdatail(self,id):
        sql='select title,typeid,details,sortrank,get_time from log_news where id=%s'
        result=self.db.fetchonedb(sql,[id])
        if result:
            typeid=result[1]
            typename=self.getlogtypename(typeid)
            return {'title':result[0],'details':result[2],'typeid':typeid,'typename':typename,'sortrank':result[3],'get_time':result[4]}
    def getnewsdetail(self,id):
        sql='select details from log_news where id=%s'
        result=self.db.fetchonedb(sql,[id])
        if result:
            return result[0]
        