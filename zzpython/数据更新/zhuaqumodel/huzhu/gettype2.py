#-*- coding:utf-8 -*-
#----抓取和讯网汇率
from public import *
#from zz91db_ast import companydb
#from zz91db_130 import otherdb
from zz91db_test import testdb
from zz91tools import date_to_str,get_content,get_inner_a,get_a_url,get_inner_a,gettags,filter_tags,gethtmlfunction
import os,datetime,time,re,urllib2

dbt=testdb()
#dbc=otherdb()
nowpath=os.path.dirname(__file__)
execfile(nowpath+"getfunction.py")
zzz=zzmodel()
#def get_url_content(url):
#    i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
#                 "Referer": 'http://www.baidu.com'}
#    req = urllib2.Request(url, headers=i_headers)
#    html=urllib2.urlopen(req).read()
#    try:
#        html2=html.decode('gbk').encode('utf-8')
#    except:
#        html2=html
#    return html2

##最简单方式
#def get_url_content(url):
#    try:
#        f = urllib2.urlopen(url, timeout=5).read()
#    except urllib2.URLError, e:
#        print e.reason
#        return ''
#    return f.decode('gbk').encode('utf-8')

def gethtml():
    typeid=17
    sql='insert into bbs_zhishi(typeid,title,keywords,content,content_query,sortrank,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)'
    htmlbegin=gethtmlfunction('http://xueyuan.qqzssl.com/xueyuan/sjcs/')
    urls_pat=re.compile('goIfr\((.*?)\)',re.DOTALL)
    contentlist=re.findall(urls_pat,htmlbegin)
    for contentid in contentlist:
        if contentid.isdigit()==True:
            hrefurl='http://xueyuan.qqzssl.com/xueYuan/showinfo.aspx?nid='+contentid
            chtml=gethtmlfunction(hrefurl)
            if chtml:
                print contentid
                ccontent=get_content('<P style="TEXT-ALIGN: center; FONT-WEIGHT: bold; font-site: 14px">(.*?)</div>',chtml)
                content=''
                if ccontent:
                    #print ccontent
                    ccontentall=ccontent.split('</P>')
                    title=ccontentall[0]
                    contentl=ccontentall[1:]
                    content='</P>'.join(contentl)
                    content=''.join(content.split())
                if not content:
                    content=get_content('<TABLE height=15 cellSpacing=0 cellPadding=0 width=100% border=0>.*?</form>',chtml)
                    title=get_content('<TD  width="90%" align="center"><b>(.*?)</b>',chtml)
                    if content:
                        content=re.sub('<FONT.*?>','',content)
                        content=content.replace('<SPAN.*?>','')
                        content=content.replace('<P align=center>','')
                        content=content.replace('<STRONG>','')
                        content=content.replace('</STRONG>','')
                        content=content.replace('</SPAN>','')
                        content=content.replace('</FONT>','')
                        content=content.replace('</div>','')
                        ccontentall=content.split('</P>')
                        title=ccontentall[0]
                        contentl=ccontentall[1:]
                        content='</P>'.join(contentl)
                        content=''.join(content.split())
                if not content:
                    content=get_content('<TABLE borderColor=#f5f5f5 height=319 cellSpacing=0 cellPadding=0 width=769 border=1>.*?</div>',chtml)
                    title=get_content('<BR><FONT color=#0000ff>(.*?)</FONT>',chtml)
#                    return
                    if content:
                        contentall=content.split('</P>')
                        contentl=contentall[3:]
                        content='</P>'.join(contentl)
                        content=content.replace('</div>','')
                
#                print title
#                print content
#                continue
                if title and content:
                    sql2='select id from bbs_zhishi where title=%s and typeid=%s'
                    result=dbt.fetchonedb(sql2,[title,typeid])
                    if not result:
                        
                        
                        sortrank=int(time.time())
                        gmt_date=datetime.date.today()
                        gmt_created=datetime.datetime.now()
                        keywords=gettags(title)
                        content_query=filter_tags(content)
                        argument=[typeid,title,keywords,content,content_query,sortrank,gmt_date,gmt_created,gmt_created]
                        dbt.updatetodb(sql,argument)

#                        print '---'
#                        print content


if __name__=="__main__":
#    gettypeallpage()
    gethtml()