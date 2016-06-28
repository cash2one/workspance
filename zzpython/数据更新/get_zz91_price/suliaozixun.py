#-*- coding:utf-8 -*-
#----塑料资讯-橡胶,原油
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url,remove_content_a,remove_content_div,filter_tags,gettags
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
gmt_created=datetime.date.today()
gmt_created2=datetime.datetime.now()

def getgedihq(urlone):
    html_area=get_url_content(urlone)
    re_alist=''
    if 'jrj.com' in urlone:
        re_alist=u'<ul class="jrj-l1 tab-ts jrj-f14">(.*?)</ul>'
        re_detail='<li>(.*?)</li>'
        re_content=r'<div class="textmain tmf14 jrj-clear" >(.*?)</div>'
    html_alist=get_content(re_alist,html_area)
#    print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        alist=re.findall(re_detail,html_alist)
        for als in alist:
#            print '---------'
            newtimes=''
            newtime=als.replace(' ','')
#            print newtime
            if 'jrj.com' in urlone:
                newtimes=newtime[15:20]
#            print newtimes
            if str_time==newtimes:
#                print str_time
                title=get_inner_a(als)
                
                if not title:
                    continue
                a_url=get_a_url(als)
                if u'原油' in title:
#                    print title
#                    print a_url
                
                    htmls=get_url_content(a_url,1)
#                    print htmls
                    contents=get_content(re_content,htmls)
                    if 'jrj.com' in urlone:
                        contents=contents.decode('gbk')
                    contents=remove_content_a(contents)
                    contents=re.sub('<IMG.*?>','',contents)
                    contents=re.sub('<P align=center>','',contents)
                    contents=re.sub('<SPAN.*?>','',contents)
                    contents=re.sub('</SPAN>','',contents)
                    contents=re.sub('<FONT.*?>','',contents)
                    contents=re.sub('</FONT>','',contents)
                    contents=re.sub('&nbsp;','',contents)
                    contents=''.join(contents.split())
                    sql2='select id from price where title=%s and gmt_created>=%s'
                    cursor.execute(sql2,[title,gmt_created])
                    result=cursor.fetchone()
                    if not result:
                        type_id=217
                        assist_type_id=0
                        content_query=filter_tags(contents)
                        is_checked=0
                        is_issue=0
                        lexicon=get_lexicon(title)
                        tags=''
                        for lec in lexicon:
                            if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
                                tags=tags+lec+','
                        tags=tags[:-1]
                        real_click_number=0
                        ip=0
                        argument=[title,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,content_query]
                        sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                        cursor.execute(sql,argument)
                        conn.commit()
                    return ''


def getgedihq2(urlone):
    html_area=get_url_content(urlone)
    re_alist=''
    if 'jrj.com' in urlone:
        re_alist=u'<ul class="jrj-l1 tab-ts jrj-f14">(.*?)</ul>'
        re_detail='<li>(.*?)</li>'
        re_content=r'<div class="textmain tmf14 jrj-clear" >(.*?)</div>'
    html_alist=get_content(re_alist,html_area)
#    print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        alist=re.findall(re_detail,html_alist)
        for als in alist:
#            print '---------'
            newtimes=''
            newtime=als.replace(' ','')
#            print newtime
            if 'jrj.com' in urlone:
                newtimes=newtime[15:20]
#            print newtimes
            if str_time==newtimes:
#                print str_time
                title=get_inner_a(als)
                
                if not title:
                    continue
                a_url=get_a_url(als)
                if u'橡胶' in title:
#                    print title
#                    print a_url
                
                    htmls=get_url_content(a_url,1)
#                    print htmls
                    contents=get_content(re_content,htmls)
                    if not contents:
                        continue
                    if 'jrj.com' in urlone:
                        contents=contents.decode('gbk')
                    contents=remove_content_a(contents)
                    contents=re.sub('<IMG.*?>','',contents)
                    contents=re.sub('<P align=center>','',contents)
                    contents=re.sub('<SPAN.*?>','',contents)
                    contents=re.sub('</SPAN>','',contents)
                    contents=re.sub('<FONT.*?>','',contents)
                    contents=re.sub('</FONT>','',contents)
                    contents=re.sub('&nbsp;','',contents)
                    contents=''.join(contents.split())
#                    print contents
                    sql2='select id from price where title=%s and gmt_created>=%s'
                    cursor.execute(sql2,[title,gmt_created])
                    result=cursor.fetchone()
                    if not result:
                        type_id=217
                        assist_type_id=0
                        content_query=filter_tags(contents)
                        is_checked=0
                        is_issue=0
                        lexicon=get_lexicon(title)
                        tags=''
                        for lec in lexicon:
                            if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
                                tags=tags+lec+','
                        tags=tags[:-1]
                        real_click_number=0
                        ip=0
                        argument=[title,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,content_query]
                        sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                        cursor.execute(sql,argument)
                        conn.commit()
                        return title
                    return ''
                    '''
                    '''

def getgedihq4(urlone):
    html_area=get_url_content(urlone)
    re_alist=''
    if '100ppi.com' in urlone:
        re_alist=r'<ul style="clear:both">(.*?)</div>'
        re_detail=''
        re_content=r'<div class="nd-c" style="overflow:hidden;width:588px;">(.*?)</div>'
    html_alist=get_content(re_alist,html_area)
#    print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        
        if '100ppi.com' in urlone:
            alist=html_alist.split('</li>')
        else:
            alist=re.findall(re_detail,html_alist)
        for als in alist:
#            print '---------'
            newtimes=''
            newtime=als.replace(' ','')
            if 'jrj.com' in urlone:
                newtimes=newtime[15:20]
            if 'hc360.com' in urlone:
                newtimes=newtime[-17:-12]
            if '100ppi.com' in urlone:
                als=re.sub('\[.*?\]','',als)
                newtimes=newtime[-17:-12]
#            print newtime
            if str_time==newtimes:
#                print str_time
                title=get_inner_a(als)
                
                if not title:
                    continue
                a_url=get_a_url(als)
                if '100ppi.com' in urlone:
                    a_url='http://www.100ppi.com/news/'+a_url
                    title=title.decode('utf-8')
#                print title
#                print a_url
                if u'原油' in title:
#                    print title
#                    print a_url
                
                    htmls=get_url_content(a_url,1)
#                    print htmls
                    contents=get_content(re_content,htmls)
                    if 'jrj.com' in urlone:
                        contents=contents.decode('gbk')
                    if 'hc360.com' in urlone:
                        contents=contents.decode('gbk')
                    if '100ppi.com' in urlone:
#                        contents=contents.decode('utf-8')
                        if '<img' in contents:
                            continue
                     
#                    print contents
#                    return ''
                    #---jrj
                    contents=remove_content_a(contents)
                    contents=re.sub('<IMG.*?>','',contents)
                    contents=re.sub('<P align=center>','',contents)
                    contents=re.sub('<SPAN.*?>','',contents)
                    contents=re.sub('</SPAN>','',contents)
                    contents=re.sub('<FONT.*?>','',contents)
                    contents=re.sub('</FONT>','',contents)
                    contents=re.sub('&nbsp;','',contents)
                    #----huicong
                    contents=re.sub('<DIV.*?>','',contents)
                    contents=re.sub('<div.*?>','',contents)
                    contents=re.sub('<IMG.*?>','',contents)
                    contents=re.sub('<img.*?>','',contents)
                    contents=re.sub('<span.*?>','',contents)
                    contents=re.sub('</span>','',contents)
                    contents=re.sub(u'慧聪橡胶网讯：','',contents)
                    contents=re.sub(u'相关阅读：.*','',contents)
                    #----public
                    contents=''.join(contents.split())
                    if '100ppi.com' in urlone:
                        if len(contents)<500:
                            continue
                        contents=re.sub('生意社.*?讯','',contents)
                        contents=re.sub('\(文章来源：.*?\)','',contents)
                        contents=''.join(contents.decode('utf-8').split())
#                    print contents
#                    return ''
                    sql2='select id from price where title=%s and gmt_created>=%s'
                    cursor.execute(sql2,[title,gmt_created])
                    result=cursor.fetchone()
                    if not result:
                        type_id=217
                        assist_type_id=0
                        content_query=filter_tags(contents)
                        is_checked=0
                        is_issue=0
                        tags=gettags(title)
                        real_click_number=0
                        ip=0
                        argument=[title,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,content_query]
                        sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                        cursor.execute(sql,argument)
                        conn.commit()
                        return title
                    return ''
                    '''
                    '''

def getgedihq3(urlone):
    html_area=get_url_content(urlone)
    re_alist=''
    if 'hc360.com' in urlone:
        re_alist=r'<div class="main">(.*?)</ul>'
        re_detail='<h3>(.*?)</h3>'
        re_content=r'<div id="artical">(.*?)</div>'
    html_alist=get_content(re_alist,html_area)
#    print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        alist=re.findall(re_detail,html_alist)
        for als in alist:
#            print '---------'
            newtimes=''
            newtime=als.replace(' ','')
            if 'jrj.com' in urlone:
                newtimes=newtime[15:20]
            if 'hc360.com' in urlone:
                newtimes=newtime[-17:-12]
#            print newtimes
            if str_time==newtimes:
#                print str_time
                title=get_inner_a(als)
                
                if not title:
                    continue
                a_url=get_a_url(als)
#                print title
#                print a_url
                if u'橡胶' in title or u'轮胎' in title or u'胶' in title:
#                    print title
#                    print a_url
                
                    htmls=get_url_content(a_url,1)
#                    print htmls
                    contents=get_content(re_content,htmls)
                    if 'jrj.com' in urlone:
                        contents=contents.decode('gbk')
                    if 'hc360.com' in urlone:
                        contents=contents.decode('gbk')
                    #---jrj
                    contents=remove_content_a(contents)
                    contents=re.sub('<IMG.*?>','',contents)
                    contents=re.sub('<P align=center>','',contents)
                    contents=re.sub('<SPAN.*?>','',contents)
                    contents=re.sub('</SPAN>','',contents)
                    contents=re.sub('<FONT.*?>','',contents)
                    contents=re.sub('</FONT>','',contents)
                    contents=re.sub('&nbsp;','',contents)
                    #----huicong
                    contents=re.sub('<DIV.*?>','',contents)
                    contents=re.sub('<div.*?>','',contents)
                    contents=re.sub('<IMG.*?>','',contents)
                    contents=re.sub('<img.*?>','',contents)
                    contents=re.sub('<span.*?>','',contents)
                    contents=re.sub('</span>','',contents)
                    contents=re.sub(u'慧聪橡胶网讯：','',contents)
                    contents=re.sub(u'相关阅读：.*','',contents)
                    #----public
                    contents=''.join(contents.split())
#                    print contents
                    sql2='select id from price where title=%s and gmt_created>=%s'
                    cursor.execute(sql2,[title,gmt_created])
                    result=cursor.fetchone()
                    if not result:
                        type_id=217
                        assist_type_id=0
                        content_query=filter_tags(contents)
                        is_checked=0
                        is_issue=0
                        tags=gettags(title)
                        real_click_number=0
                        ip=0
                        argument=[title,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,content_query]
                        sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                        cursor.execute(sql,argument)
                        conn.commit()
                        return title
                    return ''
                    '''
                    '''

def getsuliaozixun2():
    txt1=getgedihq3('http://www.search.hc360.com/seinterface.cgi?word=%CF%F0%BD%BA&class=%D0%D0%D2%B5%D7%CA%D1%B6&x=34&y=4&ind=%CF%F0%BD%BA&w=%CF%F0%BD%BA&c=%D0%D0%D2%B5%D7%CA%D1%B6')
    txt2=getgedihq4('http://www.100ppi.com/news/list-11--45-1.html')
    listall=[]
    if txt1:
        listall.append(11)
    else:
        txt3=getgedihq2('http://futures.jrj.com.cn/list/nyhgzx.shtml')
        if txt3:
            listall.append(11)
    if txt2:
        listall.append(10)
    return listall

if __name__=="__main__":
    getsuliaozixun2()
    conn.close()
