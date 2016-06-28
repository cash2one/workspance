#-*- coding:utf-8 -*- 
from getnews import get_img_url,getsimilarity
from conn import database,databasesex
import random,time,jieba,re,os,sys,datetime

from simptools import time1,time2,time3,filter_tags,newspath,imgpath,getsimplecontent
conn=database()
cursor=conn.cursor()

connsex=databasesex()
cursorsex=connsex.cursor()
reload(sys)
sys.setdefaultencoding('utf8')

def get_similaritybody(body,url):
    description=filter_tags(body).decode('utf8')
    description="".join(description.split())[:90]
    time3d=int(time1-3600*24)
    sql='select description from dede_archives where pubdate>%s'
    cursor.execute(sql,[time3d])
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            description1=result[0]
            similarity=0
            if description1:
                description1="".join(description1.split())[:90]
    #            print description
    #            print '---------'
    #            print description1
                similarity=getsimilarity(description,description1)
#            print 'description similarity ' + str(similarity) +' %' + ' ' + url
            if similarity>60:
                txt='same content'+str(similarity)+'% '+time3+url
                f=open(newspath+ time2 +'-outsame.txt','ab')
                print >>f,txt
                f.close()
                listall='1'
    return listall
def get_new4(title):
    sql='select id from dede_archives where title=%s'
    cursorsex.execute(sql,[title])
    resultlist=cursorsex.fetchone()
    if resultlist:
        return resultlist
    
def get_new3(title,typeid):
    sql='select id from dede_archives where title=%s and typeid=%s'
    cursorsex.execute(sql,[title,typeid])
    resultlist=cursorsex.fetchone()
    if resultlist:
        return resultlist

def get_new2(title,typeid):
    sql='select id from dede_archives where title=%s and typeid=%s'
    cursor.execute(sql,[title,typeid])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist

def get_new(title,typeid):
    time3d=int(time1-3600*24*3)
    sql='select id from dede_archives where title=%s and pubdate>%s'
    cursor.execute(sql,[title,time3d])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist

def get_lexicon(strt):
    result = jieba.cut(strt,cut_all=False)
    participle=" / ".join(result)
    lexicon=participle.split(" / ")
    return lexicon

def convert(ch):
    length = 0
    with open(r'seoword5.txt') as f:
        for line in f:
            if ch in line:
                sst=line.split(',')[0]
                if ch==sst:
#                    print ch
                    return line[length:len(line)]
def seoreplace(html):
    lexicon=get_lexicon(html)
    for lex in lexicon:
        lex=lex.encode('utf8')
        ss=convert(lex)
        if ss and len(lex)>1 and re.match('[ \u4e00 -\u9fa5]+',lex)==None:
            sst=ss.split(',')
            if len(sst)>2:
                number=random.randint(1,(len(sst)-1))
                sst=sst[number]
            else:
                sst=sst[-1]
            sst="".join(sst.split())
            lex="".join(lex.split())
            html=html.replace(lex,sst)
    return html

def get_keywords1():
    sql='select typename from dede_arctype where reid in (191,192,193,194)'
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            listall.append(result[0])
    return listall


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
            txt='fenci ok' + str(pubdate)
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            sql='insert into dede_archives(id,ismake,title,typeid,typeid2,pubdate,senddate,sortrank,voteid,keywords,description) values(%s,-1,%s,%s,%s,%s,%s,%s,0,%s,%s)'
            cursor.execute(sql,[id,title,typeid,typeid2,pubdate,pubdate,pubdate,keywords,description])
            conn.commit()
            txt='save suc title'+time3
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            sql='insert into dede_addonarticle(aid,typeid,body) values(%s,%s,%s)'
            cursor.execute(sql,[id,typeid,content])
            conn.commit()
            txt='save suc content'+time3
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            img_url=get_img_url(content)#获得新闻图片
            if img_url:
                img_url=img_url[0]
                sql="update dede_archives set flag='p',ismake=-1,litpic=%s where id=%s"
                cursor.execute(sql,[img_url,id])
                conn.commit()
                
def savedbsex(typeid,typeid2,main_url,url_a,title,content,keywords=""):
    sortrankl=random.randint(0,3600*3)
    
    #title分词
    
    lexicon=get_lexicon(title)
    keywords=''
    for lec in lexicon:
        if 'nbsp' in lec:
            continue
        if len(lec)>1 and re.match('\d',lec)==None and re.match("[,\.;\:\"'!’‘；：’“、？《》+=-]",lec)==None:
            keywords=keywords+lec+','
            
            sql='select id from tags where name=%s'
            cursorsex.execute(sql,[lec])
            resultl=cursorsex.fetchone()
            if not resultl:
                gmt_created=datetime.datetime.now()
                sql='insert into tags(name,gmt_created) values(%s,%s)'
                cursorsex.execute(sql,[lec,gmt_created])
                connsex.commit()
            
    keywords=keywords[:-1]
    """
    keywordslist=get_keywords1()
    tkeywords=''
    for keywd in keywordslist:
        if keywd in content:
            tkeywords=tkeywords+','+keywd
    if tkeywords:
        keywords=tkeywords[1:]
    """
    pubdate=time1-sortrankl
    pubdate=int(pubdate)
    sortrank=random.randint(000000000,999999999)
    
    sql='insert into dede_arctiny(typeid,typeid2,mid,senddate,sortrank) values(%s,%s,1,%s,%s)'
    cursorsex.execute(sql,[typeid,typeid2,time1,sortrank])
    connsex.commit()
    sql='select id from dede_arctiny where typeid=%s and typeid2=%s and sortrank=%s'
    cursorsex.execute(sql,[typeid,typeid2,sortrank])
    resultlist=cursorsex.fetchone()
    if resultlist:
        id=resultlist[0]
        description=''
        if content:
            description=filter_tags(content)
        if description and len(description)>30:
            txt='fenci ok' + str(pubdate)
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            sql='insert into dede_archives(id,ismake,title,typeid,typeid2,pubdate,senddate,sortrank,voteid,keywords,description) values(%s,-1,%s,%s,%s,%s,%s,%s,0,%s,%s)'
            cursorsex.execute(sql,[id,title,typeid,typeid2,pubdate,pubdate,pubdate,keywords,description])
            connsex.commit()
            txt='save suc title'+time3
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            simplecontent=getsimplecontent(content,main_url)
            sql='insert into dede_addonarticle(aid,typeid,body) values(%s,%s,%s)'
            cursorsex.execute(sql,[id,typeid,simplecontent])
            connsex.commit()
            txt='save suc content'+time3
            f=open(newspath+ time2 +'-out.txt','ab')
            print >>f,txt
            f.close()
            img_url=get_img_url(content)#获得新闻图片
            if img_url:
                img_url=img_url[0]
                sql="update dede_archives set flag='p',ismake=-1,litpic=%s where id=%s"
                cursorsex.execute(sql,[img_url,id])
                connsex.commit()