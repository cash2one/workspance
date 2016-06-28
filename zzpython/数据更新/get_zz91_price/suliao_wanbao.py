#-*- coding:utf-8 -*-
#----废塑料晚报
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()

timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate=timedate.decode('utf-8')
gmt_created=datetime.date.today()
str_time=time.strftime('%m-%d',time.localtime(time.time()))

def getzaoping_plastic(kind,content_p):
    titles=timedate+kind+u'现货市场早间评论'
    sql='select id,title,content from price where title=%s'
    cursor.execute(sql,[titles])
    result=cursor.fetchone()
    if result:
        id=result[0]
        title=result[1]
        content=result[2]
#        print id
#        print title
#        print content
        pvccontent=re.findall(content_p,content)
        if pvccontent:
            pvccontent=pvccontent[0].replace(u'【阿里巴巴】','')
#            print pvccontent
            pvc_pinglun=u'<p><a href="http://price.zz91.com/priceDetails_'+str(id)+u'.htm" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+timedate+u'PVC现货市场早间评论</a></p><p>'+pvccontent
            #----PVC现货市场早间评论
            return pvc_pinglun
def getpl_ping(type,typeid,assist_type_id,numb,arg='',arg2=''):
    sql='select id,title,content from price where type_id=%s and assist_type_id=%s and gmt_created>=%s order by id desc'
    cursor.execute(sql,[typeid,assist_type_id,gmt_created])
    resultlist=cursor.fetchall()
    if resultlist:
        js=0
        pl_ping=''
        for result in resultlist:
            inner_p1=''
            inner_p3=''
            id=result[0]
            title=result[1]
            if type in title and js<numb:
                if arg==1:
                    gointo=1
                elif u'月' not in title and u'日' not in title:
                    gointo=1
                else:
                    gointo=0
                if gointo==1:
                    js=js+1
#                    print title
                    pl_ping=pl_ping+u'<p><a href="http://price.zz91.com/priceDetails_'+str(id)+u'.htm" style="font-family: arial, tahoma, helvetica, sans-serif; line-height: 16px; white-space: nowrap; background-color: rgb(223, 232, 246);" target="_blank">'+title+u'</a></p>'
                    content=result[2]
                    if arg==2:
#                        inner_p=get_content('一.*',content)
                        inner_p=content.split('</p>')
#                        print content
                        if inner_p:
                            inner_p1='</p>'.join(inner_p[0:3]+inner_p[5:12])
                            pl_ping=pl_ping+inner_p1
                    elif arg2:
                        inner_p5=re.findall(arg2,content)
                        if inner_p5:
                            inner_p6=inner_p5[0]
                            pl_ping=pl_ping+u'<p>'+inner_p6
                        
#        print pl_ping
        return pl_ping

arealist=[
          {'id':127,'name':u'浙江'},
          {'id':321,'name':u'常州'},
          {'id':320,'name':u'南京'},
          {'id':319,'name':u'东莞'},
          {'id':318,'name':u'宁波'},
          {'id':317,'name':u'汕头'},
          {'id':315,'name':u'顺德'},
          {'id':142,'name':u'河南'},
          {'id':138,'name':u'河北'},
          {'id':132,'name':u'山东'},
          {'id':130,'name':u'广东'},
          {'id':129,'name':u'上海'},
          {'id':128,'name':u'江苏'},
          {'id':322,'name':u'齐鲁'},
          ]
kind2list=[
          {'id':301,'name':u'EPS'},
          {'id':302,'name':u'EVA'},
          {'id':303,'name':u'PP-R'},
          {'id':304,'name':u'LLDPE'},
          {'id':305,'name':u'HIPS'},
          {'id':306,'name':u'GPPS'},
          {'id':307,'name':u'TPU'},
          {'id':309,'name':u'PTA'},
          {'id':313,'name':u'ABS/PS'},
          {'id':300,'name':u'PE'},
          {'id':299,'name':u'PMMA'},
          {'id':298,'name':u'PA'},
          {'id':289,'name':u'POM'},
          {'id':290,'name':u'PET'},
          {'id':291,'name':u'PP'},
          {'id':292,'name':u'LDPE'},
          {'id':293,'name':u'PC'},
          {'id':294,'name':u'PS'},
          {'id':295,'name':u'HDPE'},
          {'id':296,'name':u'ABS'},
          {'id':297,'name':u'PVC'},
          {'id':323,'name':u'CPP'},
           ]

def getgedihq():
    geturlone='http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-2'
    html_area=get_url_content(geturlone)
    html_alist=get_content(u'<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0">(.*?)<table width="100%" border="0" cellspacing="0" cellpadding="0">',html_area)
    if html_alist:
        html_hq=''
        html_bj=''
        alist=re.findall('<td>.*?</td>',html_alist)
        for als in alist:
            arearname=''
    #        print '---------'
            newtimes=''
            newtime=als.replace(' ','')
            newtimes=newtime[-18:-13]
            if str_time==newtimes:
    #            print str_time
                title=get_inner_a(als)
                if title and u'地区' in title:
                    title=title.replace(' ','').decode('utf-8')
                else:
                    continue
    #            print '---------'
#                print title
                a_url=get_a_url(als)
                a_url='http://china.worldscrap.com/modules/cn/plastic/'+a_url
                htmls=get_url_content(a_url)
                contents=get_content(' <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">.*?</div>',htmls).decode('utf-8')
                contents=contents.replace('<td class="line_height_new">','<td class="line_height_new"><br /><br />')
                contents1=contents.split(u'<br /><br />')
                if contents1 and len(contents1)>1:
                    content2=''
                    if len(contents1)<4:
                        content2=contents1[1]
                    if len(contents1)>3:
                        content2=contents1[1]+'<br />'+contents1[3]
                    content_dt=content2.split(u'。')
                    if content_dt and len(content_dt)>2:
                        arearname=re.findall(u'..地区',content2)
                        if arearname:
                            arearname=arearname[0]
                            for area in arealist:
                                area_name=area['name']
                                if area_name+u'地区'==arearname:
                                    area_id=area['id']
#                            print arearname
                            arearn=u'【'+arearname+u'】'
                            html_hq=html_hq+'<p>'+arearn
#                            print arearn
                        if not arearname:
                            arearname=re.findall(u'..地区',title)
                            if arearname:
                                arearname=arearname[0]
                        content_dt1=u'。'.join(content_dt[:-2])+u' 。 </p>'
                        content_dt1=content_dt1[4:]
                        content_dt2=u'<p> '+arearname+content_dt[-2].replace(u'世界再生网','')+u'。</p>'
                        re_h=re.compile('</?\w+[^>]*>')#HTML标签
                        content_dt1=re_h.sub('',content_dt1) #去掉HTML 标签
                        content_dt1=content_dt1+'</p>'
                        html_hq=html_hq+content_dt1
                        html_hq=html_hq.replace(u'世界再生网讯','').replace(u'&nbsp;','')
                        html_bj=html_bj+content_dt2

        return {'hq':html_hq,'bj':html_bj}

def getsuliaowanbao():
    #文章标题
    main_title=timedate+u'废塑料行情晚报'
    #----塑料市场现货评论
    ppinglun_head=u'<p>【塑料&nbsp;&nbsp; <a href="http://price.zz91.com/priceList_t233_plastic.htm" style="color: blue;" target="_blank">期货</a>快报】</p>'
    suliao_wanbao=getpl_ping(u'塑料市场晚间评论',34,0,1,1,u'盘面情况：.*?</p>')
    pvc_wanbao=getpl_ping(u'PVC市场晚间评论',34,297,1,1,u'盘面走势：.*?</p>')
    pta_wanbao=getpl_ping(u'PTA市场晚间评论',34,309,1,1,u'盘面情况：.*?</p>')
    xiangjiao_wanbao=getpl_ping(u'橡胶市场晚间评论',220,0,1,1,u'国内盘面：.*?</p>')
    plastic_ping=u'<p>【塑料现货市场】</p>'
    PP_wanb=getpl_ping(u'【PP】',217,291,1,2)
    PVC_wanb=getpl_ping(u'【PVC】',217,297,1,2)
    HDPE_wanb=getpl_ping(u'【HDPE】',217,295,1,2)
    LLDPE_wanb=getpl_ping(u'【LLDPE】',217,304,1,2)
    ABS_wanb=getpl_ping(u'【ABS/PS】',217,313,1,2)
    
    feisuliao_hq=u'<div><span style="color: rgb(0, 0, 255);">【废塑料行情】</span></div>'
    feisuliao_bj=u'<div>【废塑料市场最新报价】</div>'
    #                        print content_dt1
    #                        print content_dt2
    bjhq=getgedihq()
    hangqing=bjhq['hq']
    baijia=bjhq['bj']


    wanbaoall=ppinglun_head
    if suliao_wanbao:
        wanbaoall+=suliao_wanbao
    if pvc_wanbao:
        wanbaoall+=pvc_wanbao
    if pta_wanbao:
        wanbaoall+=pta_wanbao
    if xiangjiao_wanbao:
        wanbaoall+=xiangjiao_wanbao
    if plastic_ping:
        wanbaoall+=plastic_ping
    if PP_wanb:
        wanbaoall+=PP_wanb
    if PVC_wanb:
        wanbaoall+=PVC_wanb
    if HDPE_wanb:
        wanbaoall+=HDPE_wanb
    if LLDPE_wanb:
        wanbaoall+=LLDPE_wanb
    if ABS_wanb:
        wanbaoall+=ABS_wanb
    wanbaoall+=feisuliao_hq+hangqing+feisuliao_bj+baijia
#    print wanbaoall
    from jinshu_price import insertto_price
    insertto_price(main_title,217,0,wanbaoall,'塑料,PVC,PP,PE,ABS,PTA,废橡胶,原油')
    return 56

if __name__=="__main__":
    getsuliaowanbao()
    conn.close()
