#-*- coding:utf-8 -*- 
from news import get__list,get_news_all,show_img,get_one_new,select_count,get_all_result,get_one_result
import time

#获取4大类别
def get_kind_list(arg='',arg2='list'):
    if arg=='4id':
        sql='select id,typename,keywords from dede_arctype where reid=183 order by id limit 0,4'
        kind_list=get__list(sql,'','3id')
        return kind_list
    if arg=='list4':
        sql='select id,typename,keywords from dede_arctype where reid=183 order by id limit 0,4'
        kind_list=get__list(sql,'','list4')
        return kind_list
    else:
        sql='select id,typename,keywords from dede_arctype where reid=183 order by id limit 0,4'
    kind_list=get__list(sql)
    return kind_list

#获取8个主栏目
def get_column_list(arg=''):
    if arg=='3':
        sql='select id,typename,keywords from dede_arctype where reid=184 order by sortrank limit 3,2'
    elif arg=='3id':
        sql='select id,typename,keywords from dede_arctype where reid=184 order by sortrank limit 0,8'
        column_list=get__list(sql,'','3id')
        return column_list
    elif arg=='c4id':
        sql='select id,typename,keywords from dede_arctype where topid=184 order by sortrank limit 0,4'
        column_list=get__list(sql,'','list4')
        return column_list
    elif arg=='4id':
        sql='select id,typename,keywords from dede_arctype where reid=184 order by sortrank limit 0,4'
        column_list=get__list(sql)
        return column_list
    else:
        sql='select id,typename,keywords from dede_arctype where reid=184 order by sortrank limit 0,6'
    column_list=get__list(sql)
    return column_list

#获取栏目名称
def get_now_column(url='',id=''):
    now_column=''
    if url: 
        sql='select id,typename,keywords from dede_arctype where keywords=%s'
        now_column=get__list(sql,url)[0]
    if id:
        sql='select id,typename,keywords from dede_arctype where id=%s'
        now_column=get__list(sql,id)
    if now_column:
        return now_column

#获取推荐6条资讯
def choose_six(typeid='',url2='',arg=''):
    if typeid:
        if arg=='4id':
            sql='select id,title,pubdate,keywords,shorttitle from dede_archives where flag in ("i","i,p") and typeid2=%s order by sortrank desc limit 0,6'
        else:
            sql='select id,title,pubdate,keywords,shorttitle from dede_archives where flag in ("d","d,p") and typeid=%s order by sortrank desc limit 0,6'
    else:
        sql='select id,title,pubdate,keywords,shorttitle from dede_archives where flag in ("c","c,p") order by sortrank desc limit 0,6'
    if url2:
        listall=get_news_all(sql,typeid,'keywords',url2)
    else:
        listall=get_news_all(sql,typeid,'keywords')
    return listall

#获取推荐6条特别推荐
def special_six(argument,arg=''):
    if arg=='4id':
        sql='select id,title,pubdate,keywords from dede_archives where flag in ("e","p,e") and typeid=%s order by sortrank desc limit 0,6'
    elif arg=='4kid':
        sql='select id,title,pubdate,keywords from dede_archives where flag in ("k","p,k") and typeid2=%s order by sortrank desc limit 0,6'
    listall=get_news_all(sql,argument,'keywords')
    if listall:
        return listall

#该栏目 本周最热资讯
def get_hot_thisweek_type(typeid,arg=''):
#    time7d=time.time()-3600*24*7
    if arg=='4id':
        sql='select id,title,pubdate from dede_archives where typeid2=%s order by click desc,pubdate desc limit 0,10'
    else:
        sql='select id,title,pubdate from dede_archives where typeid=%s order by click desc,pubdate desc limit 0,10'
    listall=get_news_all(sql,[typeid],'title7')
    return listall

#滚动图片
def get_roll_img(arg):
    sql='select id,title,pubdate,litpic from dede_archives where typeid2=%s and flag in ("p,s","s,p") order by sortrank desc limit 0,4'
    img_list=show_img(sql,arg)
    return img_list

#获取4大种类6条,10条新闻
def get_kinds(typeid,arg='',id=''):
    if arg=='3':
        sql='select id,title,pubdate from dede_archives where typeid=%s order by pubdate desc limit 0,10'
    elif arg=='law10':
        sql='select id,title,pubdate from dede_archives where typeid=%s order by pubdate desc limit 0,10'
    elif arg=='1':
        sql='select id,title,pubdate from dede_archives where typeid=%s and typeid2=%s order by pubdate desc limit 1,6'
        listall=get_news_all(sql,[typeid,id],'title7')
        return listall
    elif arg=='2':
        sql='select id,title,pubdate from dede_archives where typeid=%s and typeid2=%s order by pubdate desc limit 1,6'
        listall=get_news_all(sql,[id,typeid],'title7')
        return listall
    else:
        sql='select id,title,pubdate from dede_archives where typeid2=%s order by pubdate desc limit 1,6'
    listall=get_news_all(sql,[typeid],'title7')
    return listall

def get_kinds_one(typeid,arg='',id=''):
    sql='select id,title,pubdate from dede_archives where typeid=%s and typeid2=%s order by pubdate desc limit 0,1'
    if arg=='1':
        listall=get_news_all(sql,[typeid,id])[0]
    else:
        listall=get_news_all(sql,[id,typeid])[0]
    id=listall['id']
    one_new=get_one_new(id,'title7_h')
    return one_new

#获取4大种类10个关键词
def get_keywords(typename):
    sql='select id,typename,keywords from dede_arctype where keywords=%s order by sortrank limit 0,10'
    keywords=get__list(sql,typename)
    return keywords

def get_keywords2():
    sql="select id,typename,keywords from dede_arctype where reid in (191,192,193,194) order by sortrank"
    keywords=get__list(sql)
    return keywords

def get_about(typeid,typeid2):#该栏目资讯相关阅读
    sql='select id,title,pubdate from dede_archives where typeid=%s and typeid2=%s order by pubdate desc limit 0,10'
    listall=get_news_all(sql,[typeid,typeid2])
    return listall

def get_hot_thisweek(typeid):#本周热门资讯排行榜
    sql="select id,title,pubdate from dede_archives where typeid=%s order by click,pubdate desc limit 0,10"
    listall=get_news_all(sql,typeid,'about')
    return listall

    
def get_special(sql):
    all_result=get_all_result(sql)
    listall=[]
    if all_result:
        for one_result in all_result:
            list={'typeid':one_result[0],'title':one_result[1],'filename':one_result[2],'litpic':one_result[3],'description':one_result[4]}
            listall.append(list)
    return listall

def get_special_list():
    sql="select typeid,title,filename,litpic,description from dede_archives where exists(select aid from dede_addonspec where dede_archives.id=aid) order by pubdate desc"
    special=get_special(sql)
    return special

def get_special_tui():
    sql='select typeid,title,filename,litpic,description from dede_archives where  flag="p,d" order by sortrank desc limit 0,4'
    special=get_special(sql)
    return special

def get_special_te():
    sql='select typeid,title,filename,litpic,description from dede_archives where flag="p,d" order by sortrank desc limit 0,2'
    special=get_special(sql)
    return special
