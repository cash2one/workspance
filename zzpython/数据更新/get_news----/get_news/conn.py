#-*- coding:utf-8 -*- 
from tools import hand_content_end
from getnews import get_img_url
import MySQLdb,random,time,jieba,re
time3=time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
time2=time.strftime('%Y-%m-%d',time.localtime(time.time()))
time1=time.time()

#'''def database():
#    conn = MySQLdb.connect(host='122.225.11.195', user='zz91news', passwd='4ReLhW3QLyaaECzU',db='zz91news',charset='utf8')
#    return conn'''
#
def database():
    conn = MySQLdb.connect(host='192.168.110.2', user='zz91news', passwd='4ReLhW3QLyaaECzU',db='zz91news',charset='utf8')
    return conn

#def database():
#    conn = MySQLdb.connect(host='192.168.2.40', user='root', passwd='10534jun',db='zz91news',charset='utf8')
#    return conn

def close(conn,cursor):
    cursor.close()
    conn.close()

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

def select_one(sql,title,typeid):
    conn=database()
    cursor=conn.cursor()
    cursor.execute(sql,[title,typeid])
    resultlist=cursor.fetchone()
    close(conn,cursor)
    return resultlist

def get_new(title,typeid):
    time3d=time1-3600*24*3
    time3d=str(time3d)
    conn=database()
    cursor=conn.cursor()
    sql='select title from dede_archives where title=%s and pubdate>%s'
    cursor.execute(sql,[title,time3d])
    resultlist=cursor.fetchone()
    close(conn,cursor)
    return resultlist

def get_lexicon(strt):
    result = jieba.cut(strt)
    participle=" / ".join(result)
    lexicon=participle.split(" / ")
    return lexicon

def savedb(typeid,typeid2,main_url,a,title,content):
    content=hand_content_end(main_url,a,content)
#    print a
    print title
#    print content
#    title=''
    if typeid and a and title and content and len(content)>100:
        conn=database()
        cursor=conn.cursor()

        #title分词
        sortrankl=random.randint(0,3600*3)
        lexicon=get_lexicon(title)
        keywords=''
        for lec in lexicon:
            if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
                keywords=keywords+lec+','
                
                sql='select id from tags where name=%s'
                cursor.execute(sql,[lec])
                resultl=cursor.fetchone()
                if not resultl:
                    print lec
                    gmt_created=time1-sortrankl
                    sql='insert into tags(name,gmt_created) values(%s,%s)'
                    cursor.execute(sql,[lec,gmt_created])
                    conn.commit()
        
        keywords=keywords[:-1]
        
        pubdate=time1-sortrankl
#        pubdate=1392825600-sortrankl
        sortrank=random.randint(000000000,999999999)
        sql='insert into dede_arctiny(typeid,typeid2,mid,senddate,sortrank) values(%s,%s,1,%s,%s)'
        cursor.execute(sql,[typeid,typeid2,time1,sortrank])
        conn.commit()
        sql='select id from dede_arctiny where typeid=%s and typeid2=%s and sortrank=%s'
        cursor.execute(sql,[typeid,typeid2,sortrank])
        resultlist=cursor.fetchone()
        if resultlist:
            id=resultlist[0]
            try:
                #soup=BeautifulSoup(content)
                #description=soup.get_text()
                #print description
                description=filter_tags(content)
                sql='insert into dede_archives(id,ismake,title,typeid,typeid2,pubdate,senddate,voteid,keywords,description) values(%s,-1,%s,%s,%s,%s,%s,0,%s,%s)'
                cursor.execute(sql,[id,title,typeid,typeid2,pubdate,pubdate,keywords,description])
                conn.commit()
                sql='insert into dede_addonarticle(aid,typeid,body) values(%s,%s,%s)'
                cursor.execute(sql,[id,typeid,content])  
                conn.commit()
                txt='save suc '+time3
                f=open('print/'+ time2 +'-out.txt','ab')
                print >>f,txt
                print "save suc"+time3
                f.close()
            except:
                txt='sava news content error '+ a + 'typeid: ' + str(typeid)+time3
                f=open('print/'+ time2 +'-out.txt','ab')
                print >>f,txt
                print "data"
                f.close()
            img_url=get_img_url(content)#获得新闻图片
            if img_url:
                img_url=img_url[0]
                try:
                    sql="update dede_archives set flag='p',ismake=-1,litpic=%s where id=%s"
                    cursor.execute(sql,[img_url,id])
                    conn.commit()
                except:
                    print 'save img error'
    else:
        txt='content is null!'+ a + 'typeid: ' + str(typeid) + time3
        f=open('print/'+ time2 +'-out.txt','ab')
        print >>f,txt
        f.close()