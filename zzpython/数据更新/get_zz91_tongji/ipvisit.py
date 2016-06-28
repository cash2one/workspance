#-*- coding:utf-8 -*-
#----开始时间2014-05-01
#---IP访问页面个数
from public import *
from zz91conn import database_other
from zz91settings import logpath
from zz91tools import date_to_str

conn=database_other()
cursor = conn.cursor()

def getlistip(gmt_created):
    set1 = set()
    set2 = set()
    list1=[]
    list2=[]
    datalist=open(logpath+'run.'+gmt_created)
    for line in datalist:
        linedir=eval(line)
        ip=linedir['ip']
        datadir=eval(linedir['data'])
        account=datadir['account']
        countdate=datadir['date']
        url=datadir['url']
        lt1={'ip':ip,'account':account,'countdate':countdate,'url':url}
        if account:
            set2.add(ip)
            list2.append(lt1)
        else:
            list1.append(lt1)
            set1.add(ip)
    datalist.close()
    js_1=0
    js_2=0
    js_3=0
    js_4=0
    js_5=0
    js_6=0
    js_7=0
    js_8=0
    for s1 in set1:
        js=0
        for i1 in list1:
            if s1==i1['ip']:
                js=js+1
        if js==1:
            js_1=js_1+1
        if js==2:
            js_2=js_2+1
        if js==3:
            js_3=js_3+1
        if js==4:
            js_4=js_4+1
        if js==5:
            js_5=js_5+1
        if js==6:
            js_6=js_6+1
        if js==7:
            js_7=js_7+1
        if js>7:
            js_8=js_8+1
    allip=len(set1)
    average=float(len(list1))/allip
    js1_1=0
    js1_2=0
    js1_3=0
    js1_4=0
    js1_5=0
    js1_6=0
    js1_7=0
    js1_8=0
    for s2 in set2:
        js1=0
        for i2 in list2:
            if s2==i2['ip']:
                js1=js1+1
        if js1==1:
            js1_1=js1_1+1
        if js1==2:
            js1_2=js1_2+1
        if js1==3:
            js1_3=js1_3+1
        if js1==4:
            js1_4=js1_4+1
        if js1==5:
            js1_5=js1_5+1
        if js1==6:
            js1_6=js1_6+1
        if js1==7:
            js1_7=js1_7+1
        if js1>7:
            js1_8=js1_8+1
    allip1=len(set2)
    average1=float(len(list2))/allip1
    js2_1=js1_1+js_1
    js2_2=js1_2+js_2
    js2_3=js1_3+js_3
    js2_4=js1_4+js_4
    js2_5=js1_5+js_5
    js2_6=js1_6+js_6
    js2_7=js1_7+js_7
    js2_8=js1_8+js_8
    allip2=allip1+allip
    average2=float(len(list1)+len(list2))/allip2
    '''
    print js_1
    print js1_1
    print js2_1
    '''
    sql='insert into data_ipvisit(pagecount,allip,loginip,notloginip,gmt_created,sortrank) values(%s,%s,%s,%s,%s,%s)'
    cursor.execute(sql,['1个',float(js2_1)*100/allip2,float(js1_1)*100/allip1,float(js_1)*100/allip,gmt_created,1])
    cursor.execute(sql,['2个',float(js2_2)*100/allip2,float(js1_2)*100/allip1,float(js_2)*100/allip,gmt_created,2])
    cursor.execute(sql,['3个',float(js2_3)*100/allip2,float(js1_3)*100/allip1,float(js_3)*100/allip,gmt_created,3])
    cursor.execute(sql,['4个',float(js2_4)*100/allip2,float(js1_4)*100/allip1,float(js_4)*100/allip,gmt_created,4])
    cursor.execute(sql,['5个',float(js2_5)*100/allip2,float(js1_5)*100/allip1,float(js_5)*100/allip,gmt_created,5])
    cursor.execute(sql,['6个',float(js2_6)*100/allip2,float(js1_6)*100/allip1,float(js_6)*100/allip,gmt_created,6])
    cursor.execute(sql,['7个',float(js2_7)*100/allip2,float(js1_7)*100/allip1,float(js_7)*100/allip,gmt_created,7])
    cursor.execute(sql,['8个及以上',float(js2_8)*100/allip2,float(js1_8)*100/allip1,float(js_8)*100/allip,gmt_created,8])
    cursor.execute(sql,['平均页面访问量',average2,average1,average2,gmt_created,99])
    cursor.execute(sql,['IP总数',allip2,allip1,allip,gmt_created,100])
    conn.commit()
    
def getipvisitall():
    from zz91tools import getpastday
    try:
        daylist=getpastday(1)
        for dday in daylist:
            dday_str=date_to_str(dday)
            sql='select id from data_ipvisit where gmt_created=%s'
            cursor.execute(sql,[dday_str])
            result=cursor.fetchone()
            if not result:
                getlistip(dday_str)
    except IOError as e:
        print e
    return 'IP页面访问个数'

if __name__=="__main__":
    getipvisitall()