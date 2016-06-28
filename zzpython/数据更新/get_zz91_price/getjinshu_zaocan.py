#-*- coding:utf-8 -*-
#----废金属早参
from public import *
from zz91conn import database_comp
from zz91tools import filter_tags,date_to_strall
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()

timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate=timedate.decode('utf-8')
timedate2=time.strftime('%Y-%m-%d',time.localtime(time.time()))
gmt_created=datetime.date.today()
gmt_created2=datetime.datetime.now()

def getpl_ping(type,typeid,numb,arg='',arg2='',fnumb=''):
    sql='select id,title,content from price where type_id=%s and assist_type_id=0 and gmt_created>=%s'
    cursor.execute(sql,[typeid,gmt_created])
    resultlist=cursor.fetchall()
    pl_ping=''
    if resultlist:
        js=0
        for result in resultlist:
            id=result[0]
            title=result[1]
#            print title
            if type==u'铜':
                if u'COMEX市场报道' in title or u'LME市场报道' in title:
                    continue
                if u'国际贵金属市场行情' in title:
                    continue
            if arg==4:
                if u'油市早报' in title or u'油市分析' in title:
                    continue
            if type in title and js<numb:
                if (u'月' not in title and u'日' not in title) or arg2:
                    if arg2=='pass2' and u'早间评论' in title:
                        continue
                    js=js+1
                    if fnumb:
                        pl_ping=pl_ping+u'<p>'+str(fnumb)+u'、<a href="http://jiage.zz91.com/detail/'+str(id)+u'.html" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
                        fnumb=fnumb+1
                    elif numb==10:
                        pl_ping=pl_ping+u'<p>'+str(js)+u'、<a href="http://jiage.zz91.com/detail/'+str(id)+u'.html" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
                    else:
                        pl_ping=pl_ping+u'<p><a href="http://jiage.zz91.com/detail/'+str(id)+u'.html" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
                    if arg==2:
                        content=result[2]
                        content=re.sub('<.*?div>','',content)
                        content=re.sub('</div>','',content)
                        inner_p=re.findall('<p>.*?</p>',content)
                        jb=0
                        if type==u'钢材市场早间评论':
                            jb=1
                        if inner_p:
                            inner_p=inner_p[jb]
                            pl_ping=pl_ping+inner_p
                        else:
                            inner_p=re.findall('.*?</p>',content)
                            if inner_p:
                                inner_p='<p>'+inner_p[jb]
                                pl_ping=pl_ping+inner_p
                    elif arg==3:
                        content=result[2].replace(u'<p> &nbsp;</p>','')
                        pl_ping=pl_ping+content      
    return pl_ping

def getfeigang10():
    sql='select id,title,gmt_order,tags from price where type_id=216 and gmt_created>=%s'
    cursor.execute(sql,[gmt_created])
    resultlist=cursor.fetchall()
    fnumb=0
    text=''
    argtime=''
    gmt_arg2=''
    for result in resultlist:
#        id=result[0]
        title1=result[1]
        '''
            tags=result[3]
            if '早间评论' in title1:
                continue
            if ',钢材' in tags:
                fnumb+=1
                text=text+u'<p>'+str(fnumb)+u'、<a href="http://jiage.zz91.com/detail/"+str(id)+u".html" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title1+u'</a></p>'
        return text
        '''
        gmt_order=date_to_strall(result[2])
        gmt_arg=gmt_order[:-2]
            
        if 'COMEX市场' in title1:
            continue
        if 'LME市场' in title1:
            continue
        if '国际贵金属市场行情' in title1:
            continue
        if '早间评论' in title1:
            continue
        if '钢' in title1: 
            if gmt_arg2==gmt_arg:
                from_gmt=gmt_arg+u'00'
                end_gmt=gmt_arg+u'59'
                sql2='select id,title from price where type_id=216 and gmt_order>=%s and gmt_order<=%s'
                cursor.execute(sql2,[from_gmt,end_gmt])
                resultlist2=cursor.fetchall()
                for result2 in resultlist2:
                    id=result2[0]
                    title=result2[1]
                    fnumb+=1
                    text=text+u'<p>'+str(fnumb)+u'、<a href="http://jiage.zz91.com/detail/'+str(id)+u'.html" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
                if fnumb<5:
                    return getpl_ping(u'钢',216,10,'',u'pass2')
                else:
                    return text
            else:
                gmt_arg2=gmt_arg

def getjinshuzaocan():
    #文章标题
    main_title=timedate+u'废金属行情早参'
    #----塑料市场现货评论]
    tong_pinglun=u'<p>【<a href="http://price.zz91.com/moreList_p3_t40_metal.htm" style="color:blue" target="_blank"><u>铜</u></a>/废铜市场热点评论】</p>'
    tong_pinglun1=getpl_ping(u'期铜市场早间评论',216,1,2,u'pass')
    tong_dongtai=u'<p>【铜/废铜市场热点动态】</p>'
    tong_dongtai1=getpl_ping(u'COMEX市场报道',216,1,'',1,1)
    tong_dongtai2=getpl_ping(u'LME市场报道',216,1,'',1,2)
    tong_dongtai3=getpl_ping(u'铜',216,2,'','',3)
    gang_dongtai=u'<p>【<a href="http://price.zz91.com/moreList_p3_t45_metal.htm" style="color:blue" target="_blank"><u>钢</u></a>/废钢市场热点动态】</p>'
    gang_dongtai1=getfeigang10()
    gang_pinglun=u'<p>【钢材市场早间评论】</p>'
    gang_pinglun1=getpl_ping(u'钢材市场早间评论',216,1,2,u'pass')
    
    qianxin_pinglun=u'<p>【<a href="http://price.zz91.com/moreList_p3_t43_metal.htm" style="color: blue;" target="_blank">锌</a>/废<a href="http://price.zz91.com/moreList_p3_t43_metal.htm" style="color:blue" target="_blank"><u>铅</u></a>锌市场热点评论】</p>'
    qianxin_pinglun1=getpl_ping(u'废铅锌市场分析预测',216,1,3,u'pass')
    lv_pinglun=u'<p>【<a href="http://price.zz91.com/moreList_p3_t41_metal.htm" style="color:blue" target="_blank"><u>铝</u></a>/废铝市场热点评论】</p>'
    lv_pinglun1=getpl_ping(u'期铝市场早间评论',216,1,2,u'pass')
    jinshu_guancha=u'<p>【国际金属市场观察】</p>'
    jinshu_guancha1=getpl_ping(u'国际贵金属市场行情',216,1,2,u'pass')
#    print jinshu_guancha1
    
    zancanall=tong_pinglun
    if tong_pinglun1:
        zancanall=zancanall+tong_pinglun1
    zancanall=zancanall+tong_dongtai
    if tong_dongtai1:
        zancanall=zancanall+tong_dongtai1
    if tong_dongtai2:
        zancanall=zancanall+tong_dongtai2
    if tong_dongtai3:
        zancanall=zancanall+tong_dongtai3
    zancanall=zancanall+gang_dongtai
    if gang_dongtai1:
        zancanall=zancanall+gang_dongtai1
    zancanall=zancanall+gang_pinglun
    if gang_pinglun1:
        zancanall=zancanall+gang_pinglun1
    zancanall=zancanall+qianxin_pinglun
    if qianxin_pinglun1:
        zancanall=zancanall+qianxin_pinglun1
    zancanall=zancanall+lv_pinglun
    if lv_pinglun1:
        zancanall=zancanall+lv_pinglun1
    zancanall=zancanall+jinshu_guancha
    if jinshu_guancha1:
        zancanall=zancanall+jinshu_guancha1
    
    title=timedate+'废金属行情早参'
    type_id=32
    assist_type_id=242
    tags='金属,金属行情,金属行情早参,早参,行情早参'
    from jinshu_price import insertto_price
    insertto_price(title,type_id,assist_type_id,zancanall,tags)
#    print zancanall
    return 54

if __name__=="__main__":
    getjinshuzaocan()
    conn.close()