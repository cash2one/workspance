import os,re,sys
import datetime
from datetime import timedelta, date 
reload(sys)
sys.setdefaultencoding('UTF-8')
execfile("/mnt/python/zz91_task/conn_server.py")
##过滤HTML中的标签
#将HTML中标签等信息去掉
#@param htmlstr HTML字符串.
def filter_tags(htmlstr):
    #先过滤CDATA
    re_cdata=re.compile('//<!\[CDATA\[[^>]*//\]\]>',re.I) #匹配CDATA
    re_script=re.compile('<\s*script[^>]*>[^<]*<\s*/\s*script\s*>',re.I)#Script
    re_style=re.compile('<\s*style[^>]*>[^<]*<\s*/\s*style\s*>',re.I)#style
    re_br=re.compile('<br\s*?/?>')#处理换行
    re_h=re.compile('</?\w+[^>]*>')#HTML标签
    re_comment=re.compile('<!--[^>]*-->')#HTML注释
    s=re_cdata.sub('',htmlstr)#去掉CDATA
    s=re_script.sub('',s) #去掉SCRIPT
    s=re_style.sub('',s)#去掉style
    s=re_br.sub('\n',s)#将br转换为换行
    s=re_h.sub('',s) #去掉HTML 标签
    s=re_comment.sub('',s)#去掉HTML注释
    #去掉多余的空行
    blank_line=re.compile('\n+')
    s=blank_line.sub('\n',s)
    s=replaceCharEntity(s)#替换实体
    return s

##替换常用HTML字符实体.
#使用正常的字符替换HTML中特殊的字符实体.
#你可以添加新的实体字符到CHAR_ENTITIES中,处理更多HTML字符实体.
#@param htmlstr HTML字符串.
def replaceCharEntity(htmlstr):
    CHAR_ENTITIES={'nbsp':' ','160':' ',
                'lt':'<','60':'<',
                'gt':'>','62':'>',
                'amp':'&','38':'&',
                'quot':'"','34':'"',}
   
    re_charEntity=re.compile(r'&#?(?P<name>\w+);')
    sz=re_charEntity.search(htmlstr)
    while sz:
        entity=sz.group()#entity全称，如&gt;
        key=sz.group('name')#去除&;后entity,如&gt;为gt
        try:
            htmlstr=re_charEntity.sub(CHAR_ENTITIES[key],htmlstr,1)
            sz=re_charEntity.search(htmlstr)
        except KeyError:
            #以空串代替
            htmlstr=re_charEntity.sub('',htmlstr,1)
            sz=re_charEntity.search(htmlstr)
    return htmlstr
#--回复数
def gethuzhureplaycout(bbs_post_id):
    sqlr="select count(0) from bbs_post_reply where bbs_post_id=%s and is_del='0' and check_status in ('1','2')"
    cursor.execute(sqlr,str(bbs_post_id))
    rlist = cursor.fetchone()
    if rlist:
        return rlist[0]
    else:
        return 0
def updatebbs():
    sqlaa="select id,content from bbs_post where DATEDIFF(CURDATE(),gmt_modified)<1"

    cursor.execute(sqlaa)
    results = cursor.fetchall()
    for list in results:
        id=list[0]

        content=filter_tags(list[1])
        #
        sqld="update bbs_post set content_query=%s where id=%s"
        cursor.execute(sqld,[content,id])
        conn.commit()
        print id
def getpostcount(company_id):
    sql="select count(0) from bbs_post where company_id=%s"
    cursor.execute(sql,[company_id])
    results = cursor.fetchone()
    if results:
        return results[0]
    else:
        return 0
def getreplycount(company_id):
    sql="select count(0) from bbs_post_reply where company_id=%s"
    cursor.execute(sql,[company_id])
    results = cursor.fetchone()
    if results:
        return results[0]
    else:
        return 0
def updatebbsreply():
    sqlaa="select id,company_id from bbs_post where DATEDIFF(CURDATE(),gmt_modified)<1"

    cursor.execute(sqlaa)
    results = cursor.fetchall()
    for list in results:
        id=list[0]
        reply_count=gethuzhureplaycout(id)
        company_id=list[1]
        post_number=getpostcount(company_id)
        reply_number=getreplycount(company_id)
        sqlp="update bbs_user_profiler set post_number=%s,reply_number=%s where company_id=%s"
        cursor.execute(sqlp,[post_number,reply_number,company_id])
        #
        sqld="update bbs_post set reply_count=%s where id=%s"
        cursor.execute(sqld,[reply_count,id])
        conn.commit()
        print id

updatebbsreply()
updatebbs()

        