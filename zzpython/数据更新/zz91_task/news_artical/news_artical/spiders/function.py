# -*- coding: utf-8 -*-
import urllib,datetime,time,random,sys,os
from time import sleep
import re,urllib2,urllib
from bs4 import BeautifulSoup
from conn import img_path
reload(sys)
sys.setdefaultencoding('utf8')
sys.path.append('/mnt/pythoncode/zz91public/')
import jieba
from zz91conn import database_news
conn=database_news()
cursor = conn.cursor()
#本地图片保存地址
imgpath=img_path()
img_path=imgpath['path']
imgweburl=imgpath['weburl']
time1=time.time()
savetime=time.strftime('%m-%d',time.localtime(time.time()))
time3=time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
time2=time.strftime('%Y-%m-%d',time.localtime(time.time()))
#保存图片
def getimglist(html):
    artical=re.sub("<(?!img|br|p|/p|P|/P).*?>","",html).strip()
    try:
        soup=BeautifulSoup(artical)
        listall_img=soup.find_all("img") 
        for list in listall_img:
            soup_imgurl=BeautifulSoup(str(list),"lxml")
            url=soup_imgurl.img["src"]
            imgtype=url.split('.')
            imgtypes=''
            if imgtype:
                imgtypes=imgtype[-1]
            if imgtypes:
                img_name=str(random.randint(000000000,999999999))+'.'+imgtypes
            else:
                img_name=str(random.randint(000000000,999999999))+'.jpg'
            #
            file_name=img_path+img_name
            imgweburl1=str(imgweburl)+img_name
            #将文章中的img标签换成本地链接
            artical=re.sub(url,imgweburl1,artical)
            #file_name=os.path.join(img_path,img_name)
            #url="http://img1.zz91.com/ads/1441036800000/ab59b93e-0db8-49bf-84c8-215e67fd4668.jpg"
            try:
                urllib.urlretrieve(url,file_name)#图片下载
            except:
                print "err"
    except:
        listall_img=''
    return artical
def get_img_url(html):
    artical=re.sub("<(?!img|br|p|/p|P|/P).*?>","",html).strip()
    try:
        soup=BeautifulSoup(artical)
        listall_img=soup.find_all("img")
        imglist=[] 
        for list in listall_img:
            soup_imgurl=BeautifulSoup(str(list),"lxml")
            url=soup_imgurl.img["src"]
            imglist.append(url)
    except:
        imglist=[]
    return imglist
def get_lexicon(strt):
    result = jieba.cut(strt)
    participle=" / ".join(result)
    lexicon=participle.split(" / ")
    return lexicon
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
def get_keywords1():
    sql='select typename from dede_arctype where reid in (191,192,193,194)'
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            listall.append(result[0])
    return listall
#保存数据
def savedb(typeid,typeid2,main_url,url_a,title,content):
    #title分词
    sortrankl=random.randint(0,3600*3)
    lexicon=get_lexicon(title)
    keywords=''
    for lec in lexicon:
        if 'nbsp' in lec:
            continue
        if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
            keywords=keywords+lec+','
            sql='select id from tags where name=%s'
            cursor.execute(sql,[lec])
            resultl=cursor.fetchone()
            if not resultl:
                gmt_created=time1-sortrankl
                sql='insert into tags(name,gmt_created) values(%s,%s)'
                cursor.execute(sql,[lec,gmt_created])
                conn.commit()
    keywords=keywords[:-1]
    keywordslist=get_keywords1()
    tkeywords=''
    for keywd in keywordslist:
        if keywd in content.decode('utf8'):
            tkeywords=tkeywords+','+keywd
    if tkeywords:
        keywords=tkeywords[1:]
    pubdate=time1-sortrankl
    pubdate=int(pubdate)
    sortrank=random.randint(000000000,999999999)
    
    sql="select id from dede_archives where title=%s"
    cursor.execute(sql,[title])
    resultlist=cursor.fetchone()
    if resultlist:
        return
    sql="select id from zhuaqu_url where url=%s"
    cursor.execute(sql,[url_a])
    resultlist=cursor.fetchone()
    if resultlist:
        return
    else:
        sql="insert into zhuaqu_url(url) values(%s)"
        cursor.execute(sql,[url_a])
        conn.commit()
    sql='insert into dede_arctiny(typeid,typeid2,mid,senddate,sortrank) values(%s,%s,1,%s,%s)'
    cursor.execute(sql,[typeid,typeid2,time1,sortrank])
    conn.commit()
    sql='select id from dede_arctiny where typeid=%s and typeid2=%s and sortrank=%s'
    cursor.execute(sql,[typeid,typeid2,sortrank])
    resultlist=cursor.fetchone()
    if resultlist:
        id=resultlist[0]
        description=''
        if content:
            description=filter_tags(content)
        if description and len(description)>30:
            sql='insert into dede_archives(id,ismake,title,typeid,typeid2,pubdate,senddate,sortrank,voteid,keywords,description) values(%s,-1,%s,%s,%s,%s,%s,%s,0,%s,%s)'
            cursor.execute(sql,[id,title,typeid,typeid2,pubdate,pubdate,pubdate,keywords,description])
            conn.commit()

            sql='insert into dede_addonarticle(aid,typeid,body) values(%s,%s,%s)'
            cursor.execute(sql,[id,typeid,content])
            conn.commit()
            
            img_url=get_img_url(content)#获得新闻图片
            if img_url:
                img_url=img_url[0]
                sql="update dede_archives set flag='p',ismake=-1,litpic=%s where id=%s"
                cursor.execute(sql,[img_url,id])
                conn.commit()
