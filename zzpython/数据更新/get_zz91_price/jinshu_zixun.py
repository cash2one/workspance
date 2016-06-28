#-*- coding:utf-8 -*-
#----废铜资讯2条
from public import *
from zz91conn import database_comp
from zz91tools import date_to_str,get_url_content,get_content,get_inner_a,get_a_url,filter_tags,remove_content_a,get_lexicon
import datetime,time,re
from bs4 import BeautifulSoup

conn=database_comp()
cursor = conn.cursor()
timedate=time.strftime('%m月%d日',time.localtime(time.time()))
gmt_created=datetime.date.today()
str_time=time.strftime('%m-%d',time.localtime(time.time()))
gmt_created2=datetime.datetime.now()    

def getjinfei(type_id,assist_type_id,tags):
    geturlone='http://www.metalscrap.com.cn/chinese/News_List.asp?typeid=320'
    html_area=get_url_content(geturlone)
#    print html_area
    html_alist=get_content(r'<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" >(.*?)</table>',html_area)
#    print html_alist
    maintxt=[]
    js_numb=6
    if html_alist:
        alist=html_alist.split('</tr>')
#        alist=re.findall(u'<td align="center">.*?<\/td><td align="center" width="150">',html_alist)
        for als in alist:
            arearname=''
#            print '---------'
            newtimes=''
            newtime=als.replace(' ','')
            if str_time in newtime:
#                print newtime
                title=get_inner_a(als)
                if not title:
                    continue
                if 'LME市场报道' in title:
                    continue
                if 'COMEX市场报道' in title:
                    continue
                if '铜铝铅锌锡镍' in title:
                    continue
                if '铜' not in title:
                    continue
                a_url=get_a_url(als)
                if a_url:
                    a_url='http://www.metalscrap.com.cn/chinese/'+a_url
#                print title
#                print a_url

                htmls=get_url_content(a_url.decode('utf-8').encode('gbk'))
                contents=get_content(r'<div align="left">(.*?)<table width="100%"  border="0" cellspacing="0" cellpadding="0" style="margin:30px 0px">',htmls)
                contents=re.sub('<script.*?>','',contents)
                contents=re.sub('</script>','',contents)
                contents=re.sub('</div>','',contents)
                contents=re.sub('</td>','',contents)
                contents=re.sub('</tr>','',contents)
                contents=re.sub('</table>','',contents)
                contents=re.sub('&nbsp;','',contents)
                contents=remove_content_a(contents)
                if len(contents.split('BR'))>5:
                    contents='<P>'+re.sub('<BR>','</P><P>',contents)+'</P>'
                    contents=re.sub('<P>\ </P>','',contents)
                
#                print title
#                print contents
                sql2='select id from price where title=%s and gmt_created>=%s'
                cursor.execute(sql2,[title,gmt_created])
                result=cursor.fetchone()
                if not result:
#                    print contents
                    content_query=filter_tags(contents)
                    is_checked=0
                    is_issue=0
                    real_click_number=0
                    ip=0
                    is_remark=1
                    
                    argument=[title,type_id,assist_type_id,contents,gmt_created2,gmt_created2,gmt_created2,is_checked,is_issue,tags,is_remark,content_query]
                    sql='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,is_remark,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql,argument)
                    conn.commit()
                    js_numb+=1
                    maintxt.append(js_numb)
    return maintxt
                    

def getjinshu_zixun():
    maintxt=getjinfei(216,0,'期铜,铜,沪铜,融资铜,铜价')
    if maintxt:
        return maintxt
    return []
    
if __name__=="__main__":
    getjinshu_zixun()