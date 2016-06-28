#-*- coding:utf-8 -*-
#----废塑料早参
from public import *
from zz91conn import database_comp
from zz91tools import filter_tags
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()

timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate=timedate.decode('utf-8')
gmt_created=datetime.date.today()
gmt_created2=datetime.datetime.now()

def getpl_ping(type,typeid,numb,arg='',arg2='',arg3=''):
    sql='select id,title,content from price where type_id=%s and gmt_created>=%s'
    cursor.execute(sql,[typeid,gmt_created])
    resultlist=cursor.fetchall()
    pl_ping=''
    if resultlist:
        js=0
        for result in resultlist:
            id=result[0]
            title=result[1]
            if arg==4:
                if u'油市早报' in title or u'油市分析' in title:
                    continue
            if type in title and js<numb:
#                print title
                js=js+1
                pl_ping=pl_ping+u'<p>'+u'<a href="http://jiage.zz91.com/detail/'+str(id)+u'.html" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
#                pl_ping=pl_ping+u'<p>'+str(js)+u'、<a href="http://price.zz91.com/priceDetails_'+str(id)+u'.htm" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
                if arg==2:
                    content=result[2]
                    inner_p=re.findall('<p>.*?</p>',content)
                    if inner_p:
                        if arg3:
                            if len(inner_p)<2:
                                inner_p=inner_p[0]
                            else:
                                inner_p=inner_p[arg3]
                        else:
                            inner_p=inner_p[0]
                        pl_ping=pl_ping+inner_p
    return pl_ping

def getsuliaozaocan():
    #文章标题
    main_title=timedate+u'废塑料行情早参'
   #----塑料市场现货评论
    ppinglun_head=u'【塑料市场早间<a href="http://price.zz91.com/priceList_t34_plastic.htm" style="color:blue" target="_blank"><u>评论</u></a>】'
    pvc_zaoping=getpl_ping(u'PVC早评',217,1,2,1,1)
    pe_zaoping=getpl_ping(u'PE早评',217,1,2,1,1)
    ps_zaoping=getpl_ping(u'PS早评',217,1,2,1,0)
    pp_zaoping=getpl_ping(u'PP早评',217,1,2,1,1)
    abs_zaoping=getpl_ping(u'ABS早评',217,1,2,1,0)
    #----塑料市场评论3条
    plastic_ping=u'<p>【塑料市场 <a href="http://price.zz91.com/priceList_t34_plastic.htm" style="color: blue;" target="_blank">评论</a>】'
    pl_ping=getpl_ping(u'塑料',217,3)
    yuanyou_xiangjiao=u'<p>【原<a href="http://price.zz91.com/priceList_t190_paper.htm" style="color: blue;" target="_blank">油</a>/橡胶】</p>'
    xiangjiao=getpl_ping(u'橡胶',220,2,2)
    yuanyou1=getpl_ping(u'油市早报',220,1,2)
    yuanyou2=getpl_ping(u'油市分析',220,1,2)
    yuanyou3=getpl_ping(u'橡胶市场',220,1,2)
    
    you_jiao=u'<p>【原油/橡胶行业动态】</p>'
    youdongtai=getpl_ping(u'油',217,1,4)
    jiaodongtai=getpl_ping(u'胶',217,1,4)
    
    zancanall=ppinglun_head
    if pvc_zaoping:
        zancanall+=pvc_zaoping
    if pe_zaoping:
        zancanall+=pe_zaoping
    if ps_zaoping:
        zancanall+=ps_zaoping
    if pp_zaoping:
        zancanall+=pp_zaoping
    if abs_zaoping:
        zancanall+=abs_zaoping
    zancanall+=plastic_ping
    if pl_ping:
        zancanall+=pl_ping
    zancanall+=yuanyou_xiangjiao
    if xiangjiao:
        zancanall+=xiangjiao
    if yuanyou1:
        zancanall+=yuanyou1
    if yuanyou2:
        zancanall+=yuanyou2
    zancanall+=you_jiao
    if youdongtai:
        zancanall+=youdongtai
    if jiaodongtai:
        zancanall+=jiaodongtai
    from jinshu_price import insertto_price
    insertto_price(main_title,217,0,zancanall,'塑料,PVC,PP,PE,ABS,PTA,废橡胶,原油')
#    print zancanall
    return 55
if __name__=="__main__":
    getsuliaozaocan()
    conn.close()