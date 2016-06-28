#-*- coding:utf-8 -*-
#----废塑料晚间评论
from getnews import get_url_content,get_content,get_inner_a,get_a_url,hand_content,remove_content_a,filter_tags,get_lexicon
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()

timedate=time.strftime('%m月%d日',time.localtime(time.time()))
#timedate=timedate.decode('utf-8')
gmt_created=datetime.date.today()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
gmt_created2=datetime.datetime.now()

def getgedihq(urlone,type,type_id,assist_type_id):
    main_url='http://www.rdqh.com'
    html_area=get_url_content(urlone)
    html_alist=get_content(r'<div class="t3"></div>(.*?)<div class="c2">',html_area)
#    print html_alist
    if html_alist:
        html_hq=''
        html_bj=''
        urls_pat=re.compile(r'<div class="tt">.*?</div>.*?</div>',re.DOTALL)
        alist=re.findall(urls_pat,html_alist)
        for als in alist:
            arearname=''
    #        print '---------'
            newtimes=''
            newtime=als.replace(' ','')
            if 'rdqh.com' in urlone:
                newtimes=newtime[-16:-6]
                simptime=newtimes.split('-')
                mothtime=simptime[1]
                if int(mothtime)<10:
                    mothtime='0'+mothtime
                daytime=simptime[2]
                if int(daytime)<10:
                    daytime='0'+daytime
                newtimes=mothtime + '-' +daytime
#            print newtimes
            if str_time==newtimes:
    #            print str_time
                title=get_inner_a(als)
                if not title:
                    continue
                a_url=get_a_url(als)
                a_url='http://www.rdqh.com/'+a_url
                if 'rdqh.com' in urlone:
                    title=title.replace('瑞达期货:','')
                    title=title.replace('瑞达期货：','')
                    a_url=re.findall('.*?html',a_url)[0]
                htmls=get_url_content(a_url)
                contents=get_content(r'<div style="text-align: right; padding-right: 1em;">(.*?)<div>',htmls)
                utf8_list=[
                       'http://news.steelcn.com',
                       'http://www.f139.c',
                       'http://paper.f139.c',
                       'http://www.esuliao.com',
                       'http://www.rdqh.com',
                       'http://www.96369.net',
                       ]
                if 'rdqh.com' in urlone:
                    contents=hand_content('免责声明.*',contents)
                    title=re.sub('<span.*?>','',title)
                    title=re.sub('</span>','',title)
                    contents=re.sub('<span.*?>','',contents)
                    contents=re.sub('</span>','',contents)
                    contents=re.sub('<FONT.*?>','',contents)
                    contents=re.sub('<font.*?>','',contents)
                    contents=re.sub('</font>','',contents)
                    contents=re.sub('</div>','',contents)
                    contents=re.sub('<p.*?>','<p>',contents)
                    contents=re.sub('<b>','',contents)
                    contents=re.sub('</b>','',contents)
                    contents=remove_content_a(contents)
                if main_url in utf8_list:
                    title=title
                    contents=contents
                else:
                    title=title.encode('utf-8')
                    contents=contents.encode('utf-8')
                
                title_end=timedate+type+'市场晚间评论：'+title
                
                sql2='select id from price where title=%s and gmt_created>=%s'
                cursor.execute(sql2,[title_end,gmt_created])
                result=cursor.fetchone()
                if not result:

                    content_query=filter_tags(contents)
                    is_checked=1
                    is_issue=0
                    lexicon=get_lexicon(title_end)
                    tags=''
                    for lec in lexicon:
                        if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
                            tags=tags+lec+','
                    tags=tags[:-1]
                    real_click_number=0
                    ip=0
                    
                    argument=[title_end,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,content_query]
                    sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
#                    cursor.execute(sql,argument)
#                    conn.commit()
                    print title_end
                    print '---------'
                    print a_url
                    print contents
                return ''
                '''
                '''
getgedihq('http://www.rdqh.com/search-连塑.html','塑料',34,0)
getgedihq('http://www.rdqh.com/search-PVC.html','PVC',34,297)
getgedihq('http://www.rdqh.com/search-PTA.html','PTA',34,309)
getgedihq('http://www.rdqh.com/search-沪胶.html','橡胶',220,0)
