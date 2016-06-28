import os
from time import ctime, sleep
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
def formattime(value,flag):
    if value:
        if (flag==1):
            return value.strftime( '%-Y-%-m-%-d')
        else:
            return value.strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
    else:
        return ''
#昨天
def getYesterday():   
    today=datetime.date.today()   
    oneday=datetime.timedelta(days=1)   
    yesterday=today-oneday    
    return yesterday  

#今天     
def getToday():   
    return datetime.date.today()     
 
#获取给定参数的前几天的日期，返回一个list   
def getDaysByNum(num):   
    today=datetime.date.today()   
    oneday=datetime.timedelta(days=1)       
    li=[]        
    for i in range(0,num):   
        #今天减一天，一天一天减   
        today=today-oneday   
        #把日期转换成字符串   
        #result=datetostr(today)   
        li.append(datetostr(today))   
    return li   
 
#将字符串转换成datetime类型  
def strtodatetime(datestr,format):       
    return datetime.datetime.strptime(datestr,format)   
 
#时间转换成字符串,格式为2008-08-02   
def datetostr(date):     
    return   str(date)[0:10]   
 
#两个日期相隔多少天，例：2008-10-03和2008-10-01是相隔两天  
def datediff(beginDate,endDate):   
    format="%Y-%m-%d";
    bd=strtodatetime(beginDate,format)   
    ed=strtodatetime(endDate,format)       
    oneday=datetime.timedelta(days=1)   
    count=0 
    while bd!=ed:   
        ed=ed-oneday   
        count+=1 
    return count   
 
#获取两个时间段的所有时间,返回list  
def getDays(beginDate,endDate):   
    format="%Y-%m-%d";   
    bd=strtodatetime(beginDate,format)   
    ed=strtodatetime(endDate,format)   
    oneday=datetime.timedelta(days=1)    
    num=datediff(beginDate,endDate)+1    
    li=[]   
    for i in range(0,num):    
        li.append(datetostr(ed))   
        ed=ed-oneday   
    return li   
 
#获取当前年份 是一个字符串  
def getYear():   
    return str(datetime.date.today())[0:4]    
 
#获取当前月份 是一个字符串  
def getMonth():   
    return str(datetime.date.today())[5:7]   
 
#获取当前天 是一个字符串  
def getDay():
    return str(datetime.date.today())[8:10]      
def getNow():   
    return datetime.datetime.now()

def updateweixin():
    #---积分数
    def getscorecount(account):
        if account==None:
            return 0
        wdate=str(getToday())
        format="%Y-%m-%d";
        nowday=strtodatetime(wdate,format)
        sql="select sum(score) from weixin_score where account=%s and validity>'"+str(nowday)+"'"
        cursor.execute(sql,[account])
        returnlist=cursor.fetchone()
        if returnlist:
            if returnlist[0]==None:
                inconescore=0
            else:
                inconescore=returnlist[0]
        else:
            inconescore=0
        
        sql="select sum(score) from weixin_prizelog where account=%s"
        cursor.execute(sql,[account])
        returnlist=cursor.fetchone()
        if returnlist:
            xfscore=returnlist[0]
            if xfscore:
                leascore=int(xfscore)
            else:
                leascore=0
        else:
            leascore=0
            
        return inconescore-leascore
    def getqiandao(account):
        datalist=getDays("2014-4-26","2014-5-19")
        datalist=getDaysByNum(2)
        for d in datalist:
            scorecount=getscorecount(account)
            
            sqld="select id from  weixin_qiandao where DATE_FORMAT(gmt_created,'%Y-%m-%d')='"+d+"' and account='"+account+"'"
            cursor.execute(sqld)
            resultd=cursor.fetchone()
            if resultd==None:
                if scorecount>3:
                    sqla="select id from weixin_score where rules_code='qiaodao' and account='"+account+"' and DATE_FORMAT(gmt_created,'%Y-%m-%d')='"+d+"'"
                    cursor.execute(sqla)
                    resulta=cursor.fetchone()
                    if resulta==None:
                        sqlp="insert into weixin_score(account,rules_code,score,gmt_created,validity) values(%s,%s,%s,%s,%s)"
                        cursor.execute(sqlp,[account,'qiaodao','-3',d,'9999-1-1'])
                        conn.commit()
                        print account
    
    #getqiandao('PoOO')
       
    sql="select max(account) from weixin_qiandao group by account"
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for list in resultlist:
            account=list[0]
            scorecount=getscorecount(account)
            sql="select id from weixin_scoresall where account=%s"
            cursor.execute(sql,[account])
            resulta=cursor.fetchone()
            if resulta:
                sqla="update weixin_scoresall set score=%s where account=%s"
                cursor.execute(sqla,[scorecount,account])
                conn.commit()
            else:
                sqla="insert into weixin_scoresall(score,account) values(%s,%s)"
                cursor.execute(sqla,[scorecount,account])
                conn.commit()
            getqiandao(list[0])
    """ 
    sql="select account,gmt_created from weixin_qiandao where gmt_created>'2014-4-24'"
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for list in resultlist:
            account=list[0]
            gmt_created=list[1]
            frmdate=formattime(gmt_created,1)
            oneday=datetime.timedelta(days=1)
            
            yesdady=strtodatetime(frmdate,"%Y-%m-%d")-oneday
            
            
            print yesdady
    """
updateweixin()
conn.close()
conn_news.close()
            
            