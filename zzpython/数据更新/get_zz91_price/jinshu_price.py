#-*- coding:utf-8 -*-
#----佛山废金属,上海和金,废镍,废电瓶
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url,filter_tags
import datetime,time,re
from bs4 import BeautifulSoup

conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
gmt_created=datetime.date.today()
str_time=time.strftime('%m-%d',time.localtime(time.time()))

#----获得不带0的日期
def getsimptime(arg='%m月,%d日'):
    arglist=arg.split(',')
    arg1=arglist[0]
    arg2=arglist[1]
    timed1=time.strftime(arg1,time.localtime(time.time()))
    timed2=time.strftime(arg2,time.localtime(time.time()))
    if timed1[:1]=='0':
        timed1=timed1[1:]
    if timed2[:1]=='0':
        timed2=timed2[1:]
    simptime=timed1+timed2
    return simptime

def getisnew(title):
    sql='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql,[title,gmt_created])
    result=cursor.fetchone()
    if result:
        return 1
    
def insertto_price(title,type_id,assist_type_id,content,tags):
    gmt_created2=datetime.datetime.now()
    sql='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql,[title,gmt_created])
    result=cursor.fetchone()
    if not result:
        content_query=filter_tags(content)
        is_checked=0
        is_issue=0
        real_click_number=0
        ip=0
        is_remark=1
        argument=[title,type_id,assist_type_id,content,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,is_remark,content_query]
        sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
        cursor.execute(sql,argument)
        conn.commit()

def jinshu_tableall(content):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        tablestr+="<table border=1 cellpadding=0 cellspacing=0>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                if j==5:
                    textname2=re.findall('[\d]+',textname)
                    if textname2:
                        textname2=textname2[0]
                        if int(textname2)>0:
                            textname='↑'+textname
                        if int(textname2)<0:
                            textname='↓'+textname
                tablestr+="<td >"+textname+"</td>"
                j+=1
            i+=1
            tablestr+="</tr>"
        tablestr=tablestr+'</table>'
    return tablestr

def jinshu_table(content,keywd):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        tablestr+="<table border=1 cellpadding=0 cellspacing=0>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                if keywd==u'废铜':
                    if not j==4:
                        tablestr+="<td >"
                        tablestr+=textname
                        tablestr+="</td>"
                elif keywd==u'合金':
                    if j==6:
                        textname2=re.findall('[\d]+',textname)
                        if textname2:
                            textname2=textname2[0]
                            if int(textname2)>0:
                                textname='↑'+textname
                            if int(textname2)<0:
                                textname='↓'+textname
                    tablestr+="<td >"
                    tablestr+=textname
                    tablestr+="</td>"
                else:
                    tablestr+="<td >"
                    tablestr+=textname
                    tablestr+="</td>"
                j+=1
            i+=1
            tablestr+="</tr>"
        tablestr=tablestr+'</table>'
    return tablestr

def jinshu_table3(content):
    soup = BeautifulSoup(content)
    tablestr=""
    tablestr2=""
    for table in soup.findAll('table'):
        tablestr+="<table border=1 cellpadding=0 cellspacing=0>"
        tablestr2+="<table border=1 cellpadding=0 cellspacing=0>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            tablestr2+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                if i==1:
                    tablestr2+="<td >"+textname+"</td>"
                    tablestr+="<td >"+textname+"</td>"
                if i>1:
                    if i>13:
                        tablestr2+="<td >"+textname+"</td>"
                    else:
                        tablestr+="<td >"+textname+"</td>"
                j+=1
            i+=1
            tablestr+="</tr>"
            tablestr2+="</tr>"
    tablestr=tablestr+'</table>'
    tablestr2=tablestr2+'</table>'
    return {'tb':tablestr,'tb2':tablestr2}

def jinshu_table2(content,keywd):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        if keywd not in [u'废铅']:
            tablestr+="<table border=1 cellpadding=0 cellspacing=0>"
        i=1
        for row in table.findAll('tr'):
            if keywd==u'废铝':
                if i<29 and i not in [8,11]:
                    tablestr+="<tr>"
            if keywd==u'废锌':
                if i not in [6]:
                    tablestr+="<tr>"
            if keywd==u'废铅':
                if i not in [1]:
                    tablestr+="<tr>"
            if keywd==u'废铁':
                if i not in [3,4]:
                    tablestr+="<tr>"
                if i==2:
                    hang2='<tr>'
                if i==5:
                    hang5='<tr>'
                if i==6:
                    hang6='<tr>'
                if i==7:
                    hang7='<tr>'
                if i==8:
                    hang8='<tr>'
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                if keywd==u'废铝':
                    if i<29 and i not in [8,11]:
                        tablestr+="<td >"
                        tablestr+=textname
                        tablestr+="</td>"
                if keywd==u'废锌':
                    if i not in [6]:
                        tablestr+="<td >"
                        tablestr+=textname
                        tablestr+="</td>"
                if keywd==u'废铅':
                    if i not in [1]:
                        tablestr+="<td >"
                        tablestr+=textname
                        tablestr+="</td>"
                if keywd==u'废铁':
                    if i not in [3,4]:
#                        print textname
                        tablestr+="<td >"
                        tablestr+=textname
                        tablestr+="</td>"
                    if i==2:
                        hang2=hang2+'<td>'+textname+'</td>'
                    if i==5:
                        hang5=hang5+'<td>'+textname+'</td>'
                    if i==6:
                        hang6=hang6+'<td>'+textname+'</td>'
                    if i==7:
                        hang7=hang7+'<td>'+textname+'</td>'
                    if i==8:
                        hang8=hang8+'<td>'+textname+'</td>'
                j+=1
            if keywd==u'废铝':
                if i<29 and i not in [8,11]:
                        tablestr+="</tr>"
            if keywd==u'废锌':
                if i not in [6]:
                    tablestr+="</tr>"
            if keywd==u'废铅':
                if i not in [1]:
                    tablestr+="</tr>"
            if keywd==u'废铁':
                if i not in [3,4]:
                    tablestr+="</tr>"
                if i==2:
                    hang2='</tr>'
                if i==5:
                    hang5='</tr>'
                if i==6:
                    hang6='</tr>'
                if i==7:
                    hang7='</tr>'
                if i==8:
                    hang8='</tr>'    
            i+=1
        tablestr+='</table>'
    if keywd==u'废铁':
#        return '<table border=1 cellpadding=0 cellspacing=0>'+hang8+hang2+hang6+hang5+hang7
        return tablestr
    else:
        return tablestr

def getliyuan():
    timedate2=str_time.replace('-','.')
    title='全国各地废电瓶价格行情（'+timedate2+'）'
    nresult=getisnew(title)
    if nresult:
        return ''
    oneurl='http://www.ly10000.com.cn/index_inc/price_quotes/metal_market_list.php?metalTopCategoryName=%C7%A6%CF%B5%C6%B7%D6%D6&metalName=%B7%CF%B5%E7%C6%BF'
    html_all=get_url_content(oneurl)
#    print html_all
    html_list=html_all.split('</tr>')
    for als in html_list:
        str_timek=time.strftime('%m/%d',time.localtime(time.time()))
        if str_timek in als:
            if '利源各地废电瓶价格行情(不含税)' in als:
                a_url=get_a_url(als)
#                print a_url
                if a_url:
                    a_url='http://www.ly10000.com.cn/index_inc/price_quotes/'+a_url
                    htmls=get_url_content(a_url)
                    content=get_content('<span id="fontbody" style="font-size:14px; ">.*?</TABLE>',htmls)
                    content=jinshu_tableall(content)
                    type_id=52
                    assist_type_id=0
                    tags='废电瓶,废电瓶价格,废电瓶价格行情,今日废电瓶价格,废电瓶回收'
                    insertto_price(title,type_id,assist_type_id,content,tags)
                    return 12

def quanqiujin(type_id,assist_type_id,keywd,stitle,tags):
    gmt_created2=datetime.datetime.now()
    geturlone=u'http://www.ometal.com/bin/new/searchkey.asp?type='+keywd+u'&newsort=7'
    html_area=get_url_content(geturlone.encode('gbk'))
#    print html_area
    html_alist=get_content(u'<table width="96%" border="0" cellpadding="0" class="s105">(.*?)</table>',html_area)
#    print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        area_id=''
        alist=html_alist.split('<td align="left" class="s105">·')
#        alist=re.findall(u'<td align="center">.*?<\/td><td align="center" width="150">',html_alist)
        for als in alist:
            arearname=''
#            print '---------'
            newtimes=''
#            newtime=als.replace(' ','')
#            print newtime
#            newtimes=newtime[10:20]
            als=re.sub('<span.*?>','',als)
            als=re.sub('</span>','',als)
            
            title=get_inner_a(als)
#            print title
#            shorttime=timedate.replace('月0','月')
#            shorttime=shorttime.replace('日','')
            if not title:
                continue
            simptime=getsimptime()
            if simptime in title and stitle in title:
#                print shorttime
                a_url=get_a_url(als)
                if a_url:
                    a_url='http://www.ometal.com/'+a_url
#                print title
#                print a_url

                htmls=get_url_content(a_url.decode('utf-8').encode('gbk'))
                htmls=htmls.lower()
                if '废电瓶' in title:
                    timedate2=str_time.replace('-','.')
                    title_end='全国各地废电瓶价格行情（'+timedate2+'）'
                    contents=get_content('<div id="fontzoom">.*?</span>',htmls)
                    contentlist=contents.split('<BR>')
                    tablestr='<table border=1 cellpadding=0 cellspacing=0>'
                    for content in contentlist[:-1]:
                        tablestr+='<tr>'
                        content1=content.split('&nbsp;')
                        jj=1
                        for ct1 in content1:
                            if '-' in ct1 and '废电瓶' in ct1:
                                ctt1=re.findall('[0-9\ ]+.[0-9\ ]+-[0-9\ ]+.[0-9\ ]+',ct1)
                                if ctt1:
                                    ctt1=ctt1[0]
                                    ctt2=ct1.replace(ctt1,'')
                                    tablestr+='<td>'+ctt1+'</td>'
                                    tablestr+='<td>'+ctt2+'</td>'
                            else:
                                if jj==5:
                                    cttt1=re.findall('[\d]+',ct1)
                                    if cttt1:
                                        cttt2=cttt1[0]
                                        if int(cttt2)>0:
                                            ct1='↑'+ct1
                                        if int(cttt2)<0:
                                            ct1='↓'+ct1
                                tablestr+='<td>'+ct1+'</td>'
                            jj+=1
                    content=tablestr.replace('<td><div id="fontzoom"></td>','')
#                    print content
                else:
                    contents=get_content(u'<div id="fontzoom">.*?</table>',htmls)
#                    print contents
                    if not contents:
                        continue
                    if '上海现货-主要合金' in stitle:
                        contents=contents.replace(str_time,timedate)
                        contents=contents.replace('th','td')
                    elif '废镍' in stitle:
                        pass
                    else:
                        contents=contents.replace('+','↑')
                        contents=contents.replace('-','↓')
                    
                    content=jinshu_table(contents,keywd)
                    if keywd in [u'废铝',u'废铁',u'废锌',u'废铅']:
                        content=jinshu_table2(content,keywd)
    
                    if '佛山地区' in stitle:
                        stitle=stitle.replace('佛山地区','广东佛山')
                    if '废铝锌合金' in stitle:
                        stitle=stitle.replace('废铝锌合金','废铝')
                    if '废镍' in stitle:
                        stitle='全国各地废镍'
                    if '上海现货-主要合金' in stitle:
                        title_end=timedate+'上海现货(SMM)合金产品行情'
                    else:
                        title_end=timedate+stitle+'价格'
                    
#                    print title_end
#                    print content
                    
                    if keywd in [u'废铅',u'废锌',u'清远']:
                        return content
                sql2='select id from price where title=%s and gmt_created>=%s'
                cursor.execute(sql2,[title_end,gmt_created])
                result=cursor.fetchone()
                if not result:
#                    print title_end
#                    print content
                    content_query=filter_tags(contents)
                    is_checked=0
                    is_issue=0
                    real_click_number=0
                    ip=0
                    is_remark=1
                    
                    argument=[title_end,type_id,assist_type_id,content,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,is_remark,content_query]
                    sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql,argument)
                    conn.commit()
                    
                    return title_end+' 更新'
                    '''
                    '''
def getjshupall():
    txt10=getliyuan()
    gmt_created2=datetime.datetime.now()
    #----佛山废金属
    txt1=''
    txt2=''
    txt3=''
    txt4=''
    txt5=''
    txt6=''
    txt7=''
    txt8=''
    txt9=''
    
    txt6=quanqiujin(47,0,u'废镍','中国废镍市场价格','废金属,废镍价格,废镍行情,废镍,镍')
    txt1=quanqiujin(40,180,u'废铜','佛山地区废铜','铜,废金属行情,废铜价格,废铜行情,广东废铜,佛山地区,紫铜价格')
    txt2=quanqiujin(41,180,u'废铝','佛山地区废铝锌合金','废铝,废金属,废铝价格,废铝行情,废铝回收,佛山地区')
    txt3=quanqiujin(42,180,u'废铁','佛山地区废铁','废金属,废铁价格,行情,废铁,广东地区')
    txt4=quanqiujin(44,180,u'废不锈钢','佛山地区废不锈钢','废金属,废不锈钢价格,行情,废不锈钢,佛山地区')
    txt5=quanqiujin(84,53,u'合金','上海现货-主要合金','金属现货,上海合金')
    #---废铅和锌
    title_main=timedate+'广东佛山废锌和铅价格'
    sql2='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql2,[title_main,gmt_created])
    result=cursor.fetchone()
    if not result:
        feixin=quanqiujin(43,180,u'废锌','佛山地区废锌','废金属,废锌/铅价格,广东废铅,废锌/铅,佛山地区')
        feiqian=quanqiujin(43,180,u'废铅','佛山地区废铅','废金属,废锌/铅价格,广东废铅,废锌/铅,佛山地区')
        if feixin and feiqian:
            feixin=feixin.replace('</table>','')
            content=feixin+feiqian
            #print content
            content_query=filter_tags(content)
            is_checked=0
            is_issue=0
            real_click_number=0
            ip=0
            is_remark=1
            
            argument=[title_main,43,180,content,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,'废金属,废锌/铅价格,广东废铅,废锌/铅,佛山地区',is_remark,content_query]
            sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
            cursor.execute(sql,argument)
            conn.commit()
            txt7=title_main+' 完成'
#    txt10=quanqiujin(52,0,u'废电瓶','各地废电瓶市场','废电瓶,废电瓶价格,废电瓶价格行情,今日废电瓶价格,废电瓶回收')
    tonglv=quanqiujin(52,0,u'清远','清远地区废铜','废电瓶,废电瓶价格,废电瓶价格行情,今日废电瓶价格,废电瓶回收')
    if tonglv:
#        print tonglv
        tonglv2=jinshu_table3(tonglv)
        qingyuan_tong=tonglv2['tb']
        qingyuan_lv=tonglv2['tb2']
        qingyuan_tongtitle=timedate+'清远废铜价格'
        qingyuan_lvtitle=timedate+'清远废铝价格'
        
        sql3='select id from price where title=%s and gmt_created>=%s'
        cursor.execute(sql3,[qingyuan_tongtitle,gmt_created])
        result3=cursor.fetchone()
    #    print qingyuan_tongtitle
    #    print qingyuan_tong
        if not result3:
            content_query=filter_tags(qingyuan_tong)
            is_checked=0
            is_issue=0
            real_click_number=0
            ip=0
            is_remark=1
            
            argument=[qingyuan_tongtitle,40,59,qingyuan_tong,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,'沪铜,废铜,废铜价格行情,废紫铜,废紫铜价格,回收废紫铜',is_remark,content_query]
            sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
            cursor.execute(sql,argument)
            conn.commit()
            txt8=qingyuan_tongtitle+' 完成'
        sql4='select id from price where title=%s and gmt_created>=%s'
        cursor.execute(sql4,[qingyuan_lvtitle,gmt_created])
        result4=cursor.fetchone()
    #    print qingyuan_lvtitle
    #    print qingyuan_lv
        if not result4:
            content_query=filter_tags(qingyuan_lv)
            is_checked=0
            is_issue=0
            real_click_number=0
            ip=0
            is_remark=1
            
            argument=[qingyuan_lvtitle,41,59,qingyuan_lv,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,'废金属,废铝价格,废铝行情,废铝回收,广东清远,废铝',is_remark,content_query]
            sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
            cursor.execute(sql,argument)
            conn.commit()
            txt9=qingyuan_lvtitle+' 完成'
    
    listall=[]
    if txt1:
        listall.append(14)
    if txt2:
        listall.append(15)
    if txt3:
        listall.append(16)
    if txt4:
        listall.append(17)
    if txt5:
        listall.append(19)
    if txt6:
        listall.append(13)
    if txt7:
        listall.append(18)
    if txt8:
        listall.append(20)
    if txt9:
        listall.append(21)
    if txt10:
        listall.append(12)
    return listall

if __name__=="__main__":
    #print quanqiujin(43,180,u'废锌','佛山地区废锌','废金属,废锌/铅价格,广东废铅,废锌/铅,佛山地区')
    #print quanqiujin(43,180,u'废铅','佛山地区废铅','废金属,废锌/铅价格,广东废铅,废锌/铅,佛山地区')
    getjshupall()
    #getliyuan()