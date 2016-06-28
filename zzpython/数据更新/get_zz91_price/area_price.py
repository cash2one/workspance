#-*- coding:utf-8 -*-
#----各个地区废塑料报价,来源网站:http://china.worldscrap.com/modules/cn/plastic/cndick_index.php
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url,filter_tags,gettags
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
timedate=timedate.decode('utf-8')
gmt_created=datetime.date.today()
gmt_created2=datetime.datetime.now()

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


def suliaoareaprice():
    geturlone='http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-2'
    html_area=get_url_content(geturlone)
    html_alist=get_content(u'<table width="96%" border="0" align="center" cellpadding="3" cellspacing="0">(.*?)<table width="100%" border="0" cellspacing="0" cellpadding="0">',html_area)
    maintxt=[]
    if html_alist:
        html_hq=''
        html_bj=''
        area_id=''
        alist=re.findall('<td>.*?</td>',html_alist)
        for als in alist:
            arearname=''
    #        print '---------'
            newtimes=''
            newtime=als.replace(' ','')
            newtimes=newtime[-18:-13]
            
            str_time=time.strftime('%m-%d',time.localtime(time.time()))
#            str_time='06-25'
            
            if str_time==newtimes:
    #            print str_time
                title=get_inner_a(als).decode('utf-8')
                if title and u'地区' in title:
                    title=title.replace(' ','')
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
#                print content1[0]
                if contents1 and len(contents1)>1:
                    content2=''
                    if len(contents1)<4:
                        content2=contents1[1]
                    if len(contents1)>3:
                        content2=contents1[1]+'<br />'+contents1[3]
                    content_dt=content2.split(u'。')
                    if content_dt:
                        arearname=re.findall(u'..地区',content2)
                        if arearname:
                            arearname=arearname[0]
                        if not arearname:
                            arearname=re.findall(u'..地区',title)
                            if arearname:
                                arearname=arearname[0]
                        tareaname=re.findall(u'..地区',title)
                        if tareaname and arearname:
                            tareaname=tareaname[0]
                            title=title.replace(tareaname,arearname)
                        if arearname:
                            for area in arealist:
                                area_name=area['name']
                                if area_name+u'地区'==arearname:
                                    area_id=area['id']
#                            print arearname
                            arearn=u'【'+arearname+u'】'
                            html_hq=html_hq+arearn
#                            print arearn
                        content_dt1=u'。'.join(content_dt[:-2])+u' 。 </p>'
                        content_dt1=content_dt1[4:]
#                        print arearname
#                        print content_dt[-2]
                        content_dt2=u'<p> '+arearname+content_dt[-2].replace(u'世界再生网','')+u'。</p>'
#                        html_hq=html_hq+content_dt1
#                        html_bj=html_bj+content_dt2

                        content_end=content_dt1+content_dt2
                        content_end=content_end.replace(u'世界再生网讯','').replace(u'&nbsp;','')
                        content_end=content_end.replace(u'世界再生网','')
                        title=re.sub(u'.*?日','',title)
                        title_end=timedate+title.replace(u'行情评述',u'动态')
                        kind_id=''
                        for kd2 in kind2list:
                            kind_name=kd2['name']
                            if kind_name in title:
                                kind_id=kd2['id']
                                break
                                
#                        print '---------'
#                        print area_id
#                        print kind_id
#                        print title_end
                        sql='select id from price where title=%s and gmt_created>=%s'
                        cursor.execute(sql,[title_end,gmt_created])
                        result=cursor.fetchone()
                        if not result:
                            content_end=''.join(content_end.split())
                            content_end=content_end.replace('</p><p>','')
#                            print content_end
                            content_query=filter_tags(content_end)
                            is_checked=0
                            is_issue=0
                            tags=gettags(title_end)
                            real_click_number=0
                            is_remark=1
                            ip=0
                            
#                            print '---------'
#                            print area_id
#                            print kind_id
                            argument=[title_end,area_id,kind_id,content_end,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,real_click_number,is_remark,ip,content_query]
                            sql2='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,real_click_number,is_remark,ip,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                            cursor.execute(sql2,argument)
                            conn.commit()
                            maintxt.append(1)
    if maintxt:
        return 30
            

if __name__=="__main__":
    suliaoareaprice()