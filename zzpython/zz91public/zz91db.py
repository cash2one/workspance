#-*- coding:utf-8 -*-
import re,datetime,time
from sphinxapi import *
def filter_style(htmlstr):
    #先过滤CDATA
    re_cdata=re.compile('//<!\[CDATA\[[^>]*//\]\]>',re.I) #匹配CDATA
    re_script=re.compile('<\s*script[^>]*>[^<]*<\s*/\s*script\s*>',re.I)#Script
    re_style=re.compile('<\s*style[^>]*>[^<]*<\s*/\s*style\s*>',re.I)#style
    re_style1=re.compile('style=[^>]*',re.I)#style
    #re_br=re.compile('<br\s*?/?>')#处理换行
    #re_h=re.compile('</?\w+[^>]*>')#HTML标签
    re_comment=re.compile('<!--[^>]*-->')#HTML注释
    s=re_cdata.sub('',htmlstr)#去掉CDATA
    s=re_script.sub('',s) #去掉SCRIPT
    s=re_style.sub('',s)#去掉style
    s=re_style1.sub('',s)#去掉style
    #s=re_br.sub('\n',s)#将br转换为换行
    #s=re_h.sub('',s) #去掉HTML 标签
    s=re_comment.sub('',s)#去掉HTML注释
    #去掉多余的空行
    blank_line=re.compile('\n+')
    s=blank_line.sub('\n',s)
    #s=replaceCharEntity(s)#替换实体
    return s
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

def subString(string,length):   
    if length >= len(string):   
        return string   
    result = ''  
    i = 0  
    p = 0  
    while True:   
        ch = ord(string[i])   
        #1111110x   
        if ch >= 252:   
            p = p + 6  
        #111110xx   
        elif ch >= 248:   
            p = p + 5  
        #11110xxx   
        elif ch >= 240:   
            p = p + 4  
        #1110xxxx   
        elif ch >= 224:   
            p = p + 3  
        #110xxxxx   
        elif ch >= 192:
            p = p + 2  
        else:   
            p = p + 1       
        if p >= length:   
            break;
        else:   
            i = p   
    return string[0:i]
#关键字加亮  搜索引擎
def getlightkeywords(cl,docs,words,index):
    opts = {'before_match':'<font color=red>', 'after_match':'</font>', 'chunk_separator':' ... ', 'limit':400, 'around':15}
    tagscrl = cl.BuildExcerpts(docs, index, words, opts)
    if tagscrl:
        return tagscrl[0]
    else:
        return docs

def getToday():   
    return datetime.date.today()
def int_to_str(intDate):
    return time.strftime('%Y-%m-%d', time.localtime(intDate))
def getjiami(strword):
    return strword.encode('utf8','ignore').encode("hex")
def formattime(value,flag=''):
    if value:
        if (flag==1):
            return value.strftime('%Y-%m-%d')
        else:
            return value.strftime('%Y-%m-%d %H:%M:%S')
    else:
        return ''

def search_strong(search,title):
    search=search.replace(' ','')
    if re.findall('^[A-Z]+',search):
        title=re.sub(search,'<font color="red">'+search+'</font>',title)
        if '<font' not in title:
            losearch=search.lower()
            title=re.sub(losearch,'<font color="red">'+losearch+'</font>',title)
    elif re.findall('^[a-z]+',search):
        title=re.sub(search,'<font color="red">'+search+'</font>',title)
        if '<font' not in title:
            upsearch=search.upper()
            title=re.sub(upsearch,'<font color="red">'+upsearch+'</font>',title)
    else:
        searcht=split_str(search.decode('utf8'))
        title=title.decode('utf8')
        for s1 in searcht:
            title=title.replace(s1,u'<font color="red">'+s1+u'</font>')
    return title

def split_str(chinese):#传入的汉字分成一个一个的
    c=[]
    l=1
    len_c=range(0,len(chinese)/l)
    for lenn in len_c:
        ag=lenn*l
        ed=lenn*l+l
        c.append(chinese[ag:ed])
    return c 


class zz91news:
    def __init__ (self):
        from zz91conn import database_news
        self.conn_news=database_news()
        self.cursor_news=self.conn_news.cursor()
    def delnews(self,id):
        sql='delete from dede_arctiny where id=%s'
        self.cursor_news.execute(sql,[id])
        sql='delete from dede_archives where id=%s'
        self.cursor_news.execute(sql,[id])
        sql='delete from dede_addonarticle where aid=%s'
        self.cursor_news.execute(sql,[id])
        self.conn_news.commit()
    def delnewstype(self,id):
        sql='delete from dede_arctype where id=%s'
        self.cursor_news.execute(sql,[id])
        self.conn_news.commit()
    def updatetype(self,typename,sortrank,typeid):
        sql='update dede_arctype set typename=%s,sortrank=%s where id=%s'
        self.cursor_news.execute(sql,[typename,sortrank,typeid])
        self.conn_news.commit()
    def addtype(self,typename,sortrank,reid,topid):
        sql='insert into dede_arctype(typename,sortrank,reid,topid) values(%s,%s,%s,%s)'
        self.cursor_news.execute(sql,[typename,sortrank,reid,topid])
        self.conn_news.commit()
    def updatenews(self,title,shorttitle,litpic,click,writer,typeid,typeid2,body,id):
        sql='update dede_archives set title=%s,shorttitle=%s,litpic=%s,click=%s,writer=%s,typeid=%s,typeid2=%s where id=%s'
        self.cursor_news.execute(sql,[title,shorttitle,litpic,click,writer,typeid,typeid2,id])
        sql1='update dede_addonarticle set body=%s where aid=%s'
        self.cursor_news.execute(sql1,[body,id])
        self.conn_news.commit()
    def quickupdate(self,strattlist,title,keywords,shorttitle,id):
        sql='update dede_archives set flag=%s,title=%s,keywords=%s,shorttitle=%s where id=%s'
        self.cursor_news.execute(sql,[strattlist,title,keywords,shorttitle,id])
        self.conn_news.commit()
    def addnews(self,title,shorttitle,litpic,click,writer,typeid,typeid2,body,pubdate):
        sortrank=int(time.time())
        sql='insert into dede_arctiny(typeid,typeid2,mid,senddate,sortrank) values(%s,%s,%s,%s,%s)'
        self.cursor_news.execute(sql,[typeid,typeid2,1,sortrank,sortrank])
        self.conn_news.commit()
        sql2='select id from dede_arctiny where sortrank=%s'
        self.cursor_news.execute(sql2,sortrank)
        result=self.cursor_news.fetchone()
        if result:
            id=result[0]
            sql3='insert into dede_archives(id,title,shorttitle,litpic,click,writer,typeid,typeid2,pubdate,sortrank) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
            self.cursor_news.execute(sql3,[id,title,shorttitle,litpic,click,writer,typeid,typeid2,pubdate,sortrank])
            sql4='insert into dede_addonarticle(aid,body) values(%s,%s)'
            self.cursor_news.execute(sql4,[id,body])
            self.conn_news.commit()
    
    #----资讯列表(搜索引擎)
    def getnewslist(self,SPHINXCONFIG,keywords="",frompageCount="",limitNum="",typeid="",typeid2="",allnum="",arg='',flag='',type='',MATCH=""):
#        if keywords:
#            keywords=keywords.upper()
#        if '%' in keywords:
#            keywords=keywords.replace('%','%%')
        news=SPHINXCONFIG['name']['news']['name']
        serverid=SPHINXCONFIG['name']['news']['serverid']
        port=SPHINXCONFIG['name']['news']['port']
        cl = SphinxClient()
        cl.SetServer ( serverid, port )
        if MATCH!="":
            cl.SetMatchMode ( SPH_MATCH_ANY )
        else:
            cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        if (typeid):
            cl.SetFilter('typeid',typeid)
        if (typeid2):
            cl.SetFilter('typeid2',[typeid2])
        cl.SetFilter('typeid',[235,184],True)
        if MATCH!="":
            cl.SetSortMode( SPH_SORT_EXTENDED ,'@weight DESC,pubdate desc' )
        else:
            cl.SetSortMode( SPH_SORT_ATTR_DESC ,'pubdate' )
        if (allnum):
            cl.SetLimits (frompageCount,limitNum,allnum)
        else:
            cl.SetLimits (frompageCount,limitNum)
        if (keywords):
            if flag:
                res = cl.Query ('@(title,description) '+keywords+'&@(flag) "p"',news)
            else:
                if arg==1:
                    res = cl.Query ('@(title,description) '+keywords,news)
                else:
                    res = cl.Query ('@(title) '+keywords,news)
        else:
            if flag:
                res = cl.Query ('@(flag) "p"',news)
            else:
                res = cl.Query ('',news)
        listall_news=[]
        listcount_news=0
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                for match in tagslist:
                    id=match['id']
                    subcontent=self.getsubcontent(id,20)
                    newsurl=self.get_newstype(id)
                    weburl="http://news.zz91.com"
                    if newsurl:
                        if newsurl["url2"]:
                            weburl+="/"+newsurl["url2"]
                        weburl+="/"+newsurl["url"]+"/newsdetail1"+str(id)+".htm"
                    attrs=match['attrs']
                    title=attrs['ptitle']
#                    if re.findall('^[A-Z]+',keywords):
#                    else:
#                        title=re.sub(keywords,'<font color="red">'+keywords+'</font>',title)
                    click=attrs['click']
                    pubdate=attrs['pubdate']
                    pic=attrs['litpic']
                    if pic!="" and pic:
                        litpic="http://news.zz91.com/" +pic
                    else: 
                        litpic="http://img0.zz91.com/front/images/global/noimage.gif"
                    #litpic=''
                    pubdate2=time.strftime('%Y-%m-%d', time.localtime(pubdate))
                    list1={'title':title,'click':click,'id':id,'pubdate':pubdate2,'weburl':weburl,'litpic':litpic,'subcontent':subcontent}
                    if type:
                        if keywords in title or arg==1 or arg==2:
                            listall_news.append(list1)
                            title=search_strong(keywords,title)
                            list1['title']=title
                    else:
                        listall_news.append(list1)
                    if limitNum==1:
                        return list1
                listcount_news=res['total_found']
        return {'list':listall_news,'count':listcount_news}
    def getsubcontent(self,id,len):
        sql='select description from dede_archives where id=%s'
        self.cursor_news.execute(sql,[id])
        result=self.cursor_news.fetchone()
        if result:
            body=result[0]
            if body:
                return body[:len]
            else:
                return ""
        
    #----获取资讯url
    def get_newstype(self,id):
        sql='select typeid,typeid2 from dede_archives where id=%s'
        self.cursor_news.execute(sql,[id])
        result=self.cursor_news.fetchone()
        if result:
            typeid=result[0]
            typeid2=result[1]
            sql2='select typename,keywords from dede_arctype where id=%s'
            self.cursor_news.execute(sql2,[typeid])
            result2=self.cursor_news.fetchone()
            if result2:
                list={'typename':result2[0],'url':result2[1],'typeid':typeid,'typeid2':typeid2,'url2':'','typename2':''}
                if typeid2!='0':
                    sql3='select keywords,typename from dede_arctype where id=%s'
                    self.cursor_news.execute(sql3,[typeid2])
                    result3=self.cursor_news.fetchone()
                    if result3:
                        list['url2']=result3[0]
                        list['typename2']=result3[1]
                return list
    def get_typetags(self,url):
        sql1='select typename from dede_arctype where keywords=%s'
        self.cursor_news.execute(sql1,[url])
        result=self.cursor_news.fetchone()
        listall=[]
        if result:
            keywords=result[0]
            sql='select id,typename from dede_arctype where keywords=%s'
            self.cursor_news.execute(sql,[keywords])
            resultlist=self.cursor_news.fetchall()
            if resultlist:
                for result in resultlist:
                    typename=result[1]
                    list={'id':result[0],'typename':typename,'typename_hex':getjiami(typename),}
                    listall.append(list)
        return listall

    def gettypelist(self):
        listall=[]
        sql='select id,typename,sortrank from dede_arctype where topid=0 order by sortrank,id desc'
        self.cursor_news.execute(sql)
        resultlist=self.cursor_news.fetchall()
        if resultlist:
            for result in resultlist:
                id=result[0]
                nexttype=self.getnexttype(id)
                typename=result[1]
                sortrank=result[2]
                list={'id':result[0],'typename':typename,'sortrank':sortrank,'nexttype':nexttype}
                listall.append(list)
        return {'list':listall}
    def getnexttype(self,reid):
        listall=[]
        sql='select id,typename,sortrank from dede_arctype where reid=%s order by sortrank'
        self.cursor_news.execute(sql,[reid])
        resultlist=self.cursor_news.fetchall()
        if resultlist:
            for result in resultlist:
                typename=result[1]
                sortrank=result[2]
                list={'id':result[0],'typename':typename,'sortrank':sortrank}
                listall.append(list)
        return listall
    def gettypedetail(self,id):
        sql='select id,typename,sortrank from dede_arctype where id=%s'
        self.cursor_news.execute(sql,[id])
        result=self.cursor_news.fetchone()
        if result:
            list={'id':result[0],'typename':result[1],'sortrank':result[2]}
            return list
    #----资讯列表(数据库)
    def get_news_all(self,frompageCount,limitNum,pubdate='',pubdate2='',writer='',flag='',title='',typeid='',typeid2=''):
        argument=[]
        sql1='select count(0) from dede_archives where id>0'
        sql='select id,title,senddate,click,writer,flag,shorttitle,keywords from dede_archives where id>0'
        if pubdate:
            argument.append(pubdate)
            sql1=sql1+' and senddate>=%s'
            sql=sql+' and senddate>=%s'
        if pubdate2:
            argument.append(pubdate2)
            sql1=sql1+' and senddate<=%s'
            sql=sql+' and senddate<=%s'
        if writer:
            argument.append(writer)
            sql1=sql1+' and writer=%s'
            sql=sql+' and writer=%s'
        if flag:
            argument.append(flag)
            sql1=sql1+' and find_in_set(%s,flag)'
            sql=sql+' and find_in_set(%s,flag)'
        if typeid:
#            argument.append(typeid)
            sql1=sql1+' and typeid='+typeid
            sql=sql+' and typeid='+typeid
        if typeid2:
#            argument.append(typeid)
            sql1=sql1+' and typeid2='+typeid2
            sql=sql+' and typeid2='+typeid2
        if title:
#            argument.append(title)
            sql1=sql1+' and title like "%'+title+'%"'
            sql=sql+' and title like "%'+title+'%"'
        sql=sql+' order by id desc'
        if limitNum:
            sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_news.execute(sql1,argument)
        else:
            self.cursor_news.execute(sql1)
        result1=self.cursor_news.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        if argument:
            self.cursor_news.execute(sql,argument)
        else:
            self.cursor_news.execute(sql)
        resultlist=self.cursor_news.fetchall()
        listall=[]
        if resultlist:
            js=0
            for result in resultlist:
                id=result[0]
                title=result[1]
                intdate=result[2]
                click=result[3]
                writer=result[4]
                flag=result[5]
                shorttitle=result[6]
                keywords=result[7]
                if flag:
                    flaglist=flag.split(',')
                    flagnamestr=''
                    for fl in flaglist:
                        flagname=self.getflagname(fl)
                        flagnamestr=flagnamestr+' '+flagname
                pubdate=int_to_str(intdate)
                newsurl=self.get_newstype(id)
                weburl="http://news.zz91.com"
                typeid=''
                typeid2=''
                typename=''
                typename2=''
                if newsurl:
                    typeid=newsurl['typeid']
                    typename=newsurl['typename']
                    typeid2=newsurl['typeid2']
                    if newsurl["url2"]:
                        typename2=newsurl['typename2']
                        weburl+="/"+newsurl["url2"]
                    weburl+="/"+newsurl["url"]+"/newsdetail1"+str(id)+".htm"
                list={'id':id,'intdate':intdate,'pubdate':pubdate,'title':title,'weburl':weburl,'click':click,'writer':writer,'flaglist':'','flagnamestr':'','js':js,'typeid':typeid,'typeid2':typeid2,'typename':typename,'typename2':typename2,'shorttitle':shorttitle,'keywords':keywords}
                if flag:
                    list['flaglist']=flaglist
                    list['flagnamestr']=flagnamestr
                listall.append(list)
                js=js+1
        return {'list':listall,'count':count}
    def getattlist(self):
        sql='select sortid,att,attname from dede_arcatt order by sortid'
        self.cursor_news.execute(sql)
        resultlist=self.cursor_news.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'id':result[0],'att':result[1],'attname':result[2]}
                listall.append(list)
        return listall
    def getflagname(self,att):
        sql='select attname from dede_arcatt where att=%s'
        self.cursor_news.execute(sql,[att])
        result=self.cursor_news.fetchone()
        if result:
            return result[0]
    def getnewsdetail(self,id):
        sql='select body from dede_addonarticle where aid=%s'
        self.cursor_news.execute(sql,[id])
        result1=self.cursor_news.fetchone()
        if result1:
            body=result1[0]
        else:
            return ''
        sql='select title,typeid,typeid2,flag,litpic,writer,click,shorttitle from dede_archives where id=%s'
        self.cursor_news.execute(sql,[id])
        result=self.cursor_news.fetchone()
        if result:
            title=result[0]
            typeid=result[1]
            typename=self.gettypename(typeid)
            typeid2=result[2]
            typename2=''
            if typeid2!='0':
                typename2=self.gettypename(typeid2)
            flag=result[3]
            litpic=result[4]
            writer=result[5]
            click=result[6]
            shorttitle=result[7]
            list={'body':body,'title':title,'typeid':typeid,'typename':typename,'typeid2':typeid2,'typename2':typename2,'flag':flag,'litpic':litpic,'writer':writer,'click':click,'shorttitle':shorttitle}
            return list
    def gettypename(self,id):
        sql='select typename from dede_arctype where id=%s'
        self.cursor_news.execute(sql,[id])
        result=self.cursor_news.fetchone()
        if result:
            return result[0]

class zz91other:
    def __init__ (self):
        from zz91conn import database_other
        self.conn_other=database_other()
        self.cursor_other=self.conn_other.cursor()
    def addwebartical(self,title,litpic,weburl,typeid,content,gmt_created,updatetime,sortrank,wtype):
        sql='insert into website(name,pic,url,typeid,content,gmt_created,updatetime,sortrank,wtype) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)'
        self.cursor_other.execute(sql,[title,litpic,weburl,typeid,content,gmt_created,updatetime,sortrank,wtype])
        self.conn_other.commit()
    def updateartical(self,title,litpic,weburl,typeid,content,updatetime,sortrank,wtype,artid):
        sql='update website set name=%s,pic=%s,url=%s,typeid=%s,content=%s,updatetime=%s,sortrank=%s,wtype=%s where id=%s'
        self.cursor_other.execute(sql,[title,litpic,weburl,typeid,content,updatetime,sortrank,wtype,artid])
        self.conn_other.commit()
        
    def getsearch_keywords(self,frompageCount,limitNum,pagetype='',gmt_begin='',gmt_end=''):
        sql1='select count(0) from search_keywords where id>0'
        sql='select id,webtype,keywords,ip,gmt_created,numb from search_keywords where id>0'
        argument=[]
        if pagetype:
            sql=sql+' and webtype=%s'
            sql1=sql1+' and webtype=%s'
            argument.append(pagetype)
        if gmt_begin and gmt_end:
            sql=sql+' and gmt_created>=%s and gmt_created<=%s'
            sql1=sql1+' and gmt_created>=%s and gmt_created<=%s'
            argument.append(gmt_begin)
            argument.append(gmt_end)
            sql1=sql1.replace('count(0)', 'count(distinct keywords)')
            sql=sql.replace('numb', 'sum(numb)')
            sql=sql+' group by keywords'
        sql=sql+' order by gmt_created desc limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_other.execute(sql1,argument)
        else:
            self.cursor_other.execute(sql1)
        result=self.cursor_other.fetchone()
        if result:
            count=result[0]
        else:
            count=0
        if argument:
            self.cursor_other.execute(sql,argument)
        else:
            self.cursor_other.execute(sql)
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                typeid=result[1]
                typename=self.gettypename(typeid)
                list={'id':result[0],'typeid':typeid,'typename':typename,'keywords':result[2],'ip':result[3],'gmt_created':formattime(result[4],1),'numb':result[5]}
                listall.append(list)
        return {'list':listall,'count':count}
    def addsearch_keywords(self,webtype,keywords,ip,gmt_created,detailtime,numb):
        sql1='select id,ip,numb from search_keywords where keywords=%s and gmt_created=%s'
        self.cursor_other.execute(sql1,[keywords,gmt_created])
        result=self.cursor_other.fetchone()
        if result:
            id=result[0]
            ip1=result[1]
            ip=ip1+','+ip
            numb1=result[2]
            numb=numb1+numb
            sql='update search_keywords set ip=%s,numb=%s where id=%s'
            self.cursor_other.execute(sql,[ip,numb,id])
        else:
            sql='insert into search_keywords(webtype,keywords,ip,gmt_created,detailtime,numb) values(%s,%s,%s,%s,%s,%s)'
            self.cursor_other.execute(sql,[webtype,keywords,ip,gmt_created,detailtime,numb])
        self.conn_other.commit()
    def getpagerundetail(self,id):
        sql='select typeid,in_page_ip from dataanalysis_page where id=%s'
        self.cursor_other.execute(sql,[id])
        result=self.cursor_other.fetchone()
        if result:
            typeid=result[0]
            typename=self.gettypename(typeid)
            iplist=result[1]
            list={'typeid':typeid,'typename':typename,'iplist':iplist.split(',')}
            return list
    def getpagenextdetail(self,upid):
        sql='select id,typeid,numb,gmt_created from data_pagerun where upid=%s order by id'
        self.cursor_other.execute(sql,[upid])
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                typeid=result[1]
                typename=self.gettypename(typeid)
                list={'id':result[0],'typeid':typeid,'typename':typename,'numb':result[2],'gmt_created':result[3]}
                listall.append(list)
        return listall

    #----获得多级栏目嵌套
    def getalltypelist(self,wtype):
        listall=[]
        sql='select id,typename from webtype where wtype=%s'
        self.cursor_other.execute(sql,[wtype])
        resultlist=self.cursor_other.fetchall()
        if resultlist:
            for result in resultlist:
                listall2=[]
                id=result[0]
                resultlist2=self.getnexttype(id)
                if resultlist2:
                    for result2 in resultlist2:
                        listall3=[]
                        id2=result2['id']
                        listall3=self.getnexttype(id2)
                        list2={'id':id2,'typename':result2['typename'],'typelist':''}
                        if listall3:
                            list2['typelist']=listall3
                        listall2.append(list2)
                list={'id':id,'typename':result[1],'typelist':''}
                if listall2:
                    list['typelist']=listall2
                listall.append(list)
        return listall
    def getnexttype(self,typeid):
        listall=[]
        sql='select id,typename from webtype where reid=%s'
        self.cursor_other.execute(sql,[typeid])
        resultlist=self.cursor_other.fetchall()
        if resultlist:
            for result in resultlist:
                id=result[0]
                listweb=[]
                list={'id':id,'typename':result[1]}
                listall.append(list)
        return listall
    def getaccountip(self,account):
        sql='select a.numb from data_ip as a,data_account as b where a.account_id=b.id and b.numb=%s'
        self.cursor_other.execute(sql,[account])
        result=self.cursor_other.fetchone()
        if result:
            return result[0]
    def getvisiturl(self,frompageCount,limitNum,account,arg=''):
        if arg==1:
            ip=self.getaccountip(account)
            sql1='select count(0) from data_visiturl where ip=%s'
            self.cursor_other.execute(sql1,[ip])
        else:
            sql1='select count(0) from data_visiturl where account=%s'
            self.cursor_other.execute(sql1,[account])
        result1=self.cursor_other.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        if arg==1:
            sql='select url,urltime,gmt_created from data_visiturl where ip=%s order by urltime desc limit '+str(frompageCount)+','+str(limitNum)
            self.cursor_other.execute(sql,[ip])
        else:
            sql='select url,urltime,gmt_created from data_visiturl where account=%s order by urltime desc limit '+str(frompageCount)+','+str(limitNum)
            self.cursor_other.execute(sql,[account])
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'url':result[0],'urltime':formattime(result[1]),'gmt_created':formattime(result[2],1)}
                listall.append(list)
        return {'list':listall,'count':count}

    def deldata(self,gmt_created,gmt_created2):
        sql='delete from dataanalysis_page where gmt_created>=%s and gmt_created<=%s'
        self.cursor_other.execute(sql,[gmt_created,gmt_created2])
        sql='delete from data_pagerun where gmt_created>=%s and gmt_created<=%s'
        self.cursor_other.execute(sql,[gmt_created,gmt_created2])
        self.conn_other.commit()
        
    def delipvisit(self,gmt_created,gmt_created2):
        sql='delete from data_ipvisit where gmt_created>=%s and gmt_created<=%s'
        self.cursor_other.execute(sql,[gmt_created,gmt_created2])
        self.conn_other.commit()
    def deldatasis(self,id):
        sql='delete from dataanalysis where id=%s'
        self.cursor_other.execute(sql,[id])
        self.conn_other.commit()
    def getstatistics_page(self,gmt_created='',pubdate='',pubdate2=''):
        sql='select id,typeid,gmt_created,sum(in_page),sum(out_page),sum(log_page),sum(reg_page),in_page_ip from dataanalysis_page where id>0'
        argument=[]
        if gmt_created:
            sql=sql+' and gmt_created=%s'
            argument.append(gmt_created)
        else:
#            sql="select id,typeid,gmt_created,sum(in_page),sum(out_page),sum(log_page),sum(reg_page) from dataanalysis_page where gmt_created between %s and %s group by typeid"
            if pubdate:
                argument.append(pubdate)
                sql=sql+' and gmt_created>=%s'
            if pubdate2:
                argument.append(pubdate2)
                sql=sql+' and gmt_created<=%s'
        sql=sql+' group by typeid'
        sql=sql+' order by typeid'
        if pubdate and pubdate2:
            self.cursor_other.execute(sql,argument)
        else:
            return ''
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                typeid=result[1]
                typename=self.gettypename(typeid)
                iplist=result[7]
                if iplist:
                    iplist=iplist.split(',')
                list={'id':result[0],'typeid':typeid,'typename':typename,'gmt_created':formattime(result[2],1),'in_page':result[3],'out_page':result[4],'log_page':result[5],'reg_page':result[6],'iplist':iplist}
                listall.append(list)
        return listall
    def get_pagecount(self):
        sql='select count(0) from dataanalysis_page'
        self.cursor_other.execute(sql)
        result=self.cursor_other.fetchone()
        if result:
            return result[0]/21
    def gettypename(self,id):
        sql='select name from pagetype where id=%s'
        self.cursor_other.execute(sql,[id])
        result=self.cursor_other.fetchone()
        if result:
            return result[0]
    def getpagedetail(self,wherepage,typeid,pagecount,gmt_created,gmt_created2):
        sql='select typeid,numb from data_pagerun where wherepage=%s and reid=%s and pagecount=%s and gmt_created>=%s and gmt_created<=%s'
        self.cursor_other.execute(sql,[wherepage,typeid,pagecount,gmt_created,gmt_created2])
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                typeid=result[0]
                typename=self.gettypename(typeid)
                list={'typeid':typeid,'numb':result[1],'typename':typename}
                listall.append(list)
        return listall
    def gettypelist(self,frompageCount,limitNum):
        sql1='select count(0) from pagetype'
        self.cursor_other.execute(sql1)
        result1=self.cursor_other.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        sql='select id,name,url from pagetype order by id'
        sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        self.cursor_other.execute(sql)
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'id':result[0],'name':result[1],'url':result[2]}
                listall.append(list)
        return {'list':listall,'count':count}
    def gettypelist2(self,idlist):
        sql='select id,name,url from pagetype where id in %s'
        self.cursor_other.execute(sql,[idlist])
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'id':result[0],'name':result[1],'url':result[2]}
                listall.append(list)
        return listall
    def getjumpoutlist(self):
        sql1='select count(0) from jumpout'
        self.cursor_other.execute(sql)
        result1=self.cursor_other.fetchone()
        if result1:
            count=result[0]
        else:
            count=0
        sql='select id,gmt_created,reg,login,propub,comprice from jumpout limit 0,10'
        self.cursor_other.execute(sql)
        resultlist=self.cursor_other.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'id':result[0],'gmt_created':result[1]}
                listall.append(list)
        return {'list':listall,'count':count}
    def getdatalist(self,frompageCount='',limitNum='',pubdate='',pubdate2=''):
        sql1='select count(0) from dataanalysis where id>0'
        argument=[]
        if pubdate:
            sql1=sql1+' and gmt_created>=%s'
            argument.append(pubdate)
        if pubdate2:
            sql1=sql1+' and gmt_created<=%s'
            argument.append(pubdate2)
        if argument:
            self.cursor_other.execute(sql1,argument)
        else:
            self.cursor_other.execute(sql1)
        result1=self.cursor_other.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        listall=[]
        sql='select id,all_ipcount,reg_count,noreg_count,gmt_created,alr_count from dataanalysis where id>0'
        argument=[]
        if pubdate:
            sql=sql+' and gmt_created>=%s'
            argument.append(pubdate)
        if pubdate2:
            sql=sql+' and gmt_created<=%s'
            argument.append(pubdate2)
        sql=sql+' order by gmt_created desc'
        if limitNum:
            sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_other.execute(sql,argument)
        else:
            self.cursor_other.execute(sql)
        resultlist=self.cursor_other.fetchall()
        if resultlist:
            for result in resultlist:
                conversion=str(float(result[2])/(result[3]+result[2])*100)+"%"
                list={'id':result[0],'all_ipcount':result[1],'reg_count':result[2],'noreg_count':result[3],'gmt_created':formattime(result[4],1),'conversion':conversion,'alr_count':result[5]}
                listall.append(list)
        return {'list':listall,'count':count}
    def yearlogin(self,year):
        sql='select count from yearlogin where year=%s'
        self.cursor_other.execute(sql,[year])
        result1=self.cursor_other.fetchone()
        if result1:
            return result1[0]
    def getwebsitelist(self,frompageCount,limitNum,typeid='',recommend='',wtype='',order='',desc=''):
        sql1='select count(0) from website where isdelete=0'
        if typeid:
            sql1=sql1+' and typeid='+str(typeid)
        if wtype:
            sql1=sql1+' and wtype='+str(wtype)
        if recommend:
            sql1=sql1+' and recommend='+str(recommend)
        self.cursor_other.execute(sql1)
        result1=self.cursor_other.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        listall=[]
        sql='select id,typeid,name,url,pic,gmt_created,sortrank,recommend from website where isdelete=0'
        if typeid:
            sql=sql+' and typeid='+str(typeid)
        if wtype:
            sql=sql+' and wtype='+str(wtype)
        if recommend:
            sql=sql+' and recommend='+str(recommend)
        if order:
            if desc==None:
                sql=sql+' order by '+order+' desc'
            else:
                sql=sql+' order by '+order+' '+desc
        else:
            sql=sql+' order by sortrank,updatetime desc,gmt_created desc'
        sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        self.cursor_other.execute(sql)
        resultlist=self.cursor_other.fetchall()
        js=1
        if resultlist:
            for result in resultlist:
                typeid1=result[1]
                js=js+1
#                typename=gettypedetail(typeid1)['typename']
                list={'id':result[0],'typeid':typeid1,'typename':'','name':result[2],'url':result[3],'pic':result[4],'gmt_created':formattime(result[5],1),'sortrank':result[6],'recommend':result[7],'js':js}
                listall.append(list)
        return {'list':listall,'count':count}
    def getipvisit(self,pubdate='',pubdate2=''):
        sql1='select count(0) from data_ipvisit where id>0'
        sql='select pagecount,sum(allip),sum(loginip),sum(notloginip),gmt_created,sortrank from data_ipvisit where id>0'
        argument=[]
        if pubdate:
            argument.append(pubdate)
            sql=sql+' and gmt_created>=%s'
            sql1=sql1+' and gmt_created>=%s'
        if pubdate2:
            argument.append(pubdate2)
            sql=sql+' and gmt_created<=%s'
            sql1=sql1+' and gmt_created<=%s'
        sql=sql+' group by pagecount order by sortrank'
        self.cursor_other.execute(sql1,argument)
        result1=self.cursor_other.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        listall=[]
        self.cursor_other.execute(sql,argument)
        resultlist=self.cursor_other.fetchall()
        if resultlist:
            for result in resultlist:
                pagecount=result[0]
                allip=result[1]
                loginip=result[2]
                notloginip=result[3]
                sortrank=result[4]
                if pagecount==u'IP总数':
                    allip=str(int(allip))+' 个'
                    loginip=str(int(loginip))+' 个'
                    notloginip=str(int(notloginip))+' 个'
                elif pagecount == u'平均页面访问量':
                    allip=str(allip)+' 个'
                    loginip=str(loginip)+' 个'
                    notloginip=str(notloginip)+' 个'
                else:
                    allip=str(allip)+'%'
                    loginip=str(loginip)+'%'
                    notloginip=str(notloginip)+'%'
                list={'pagecount':pagecount,'allip':allip,'loginip':loginip,'notloginip':notloginip,'gmt_created':formattime(result[4],1)}
                listall.append(list)
        return {'list':listall,'count':count}

class aqsiq:
    def __init__ (self):
        from zz91conn import database_other
        self.conn_aqsiq=database_other()
        self.cursor_aqsiq=self.conn_aqsiq.cursor()
    #----新闻最终页上一篇下一篇
    def getarticalup(self,id,typeid):
        sqlt="select id,ntitle from aqsiq_news where ntype=%s and id>%s order by id limit 0,1"
        self.cursor_aqsiq.execute(sqlt,[typeid,id])
        resultu = self.cursor_aqsiq.fetchone()
        if resultu:
            id=resultu[0]
            weburl='aqsiq_detail'+str(id)+'.html'
            list={'id':id,'title':resultu[1],'weburl':weburl}
            return list
    def getarticalnx(self,id,typeid):
        sqlt="select id,ntitle from aqsiq_news where ntype=%s and id<%s order by id desc limit 0,1"
        self.cursor_aqsiq.execute(sqlt,[typeid,id])
        resultn = self.cursor_aqsiq.fetchone()
        if resultn:
            id=resultn[0]
            weburl='aqsiq_detail'+str(id)+'.html'
            list={'id':id,'title':resultn[1],'weburl':weburl}
            return list
    def getaqsiqnews(self,frompageCount='',limitNum='',ntitle='',typeid='',isdelete='',order=''):
        listall=[]
        argument=[]
        sql1='select count(0) from aqsiq_news where isdelete=0'
        sql='select id,ntitle,ndate,ntype from aqsiq_news where isdelete=0'
        if ntitle:
#            argument.append(ntitle)
            sql1=sql1+" and ntitle like '%"+ntitle+"%'"
            sql=sql+" and ntitle like '%"+ntitle+"%'"
        if typeid:
#            argument.append(typeid)
            sql1=sql1+' and ntype='+typeid
            sql=sql+' and ntype='+typeid
        if isdelete:
#            argument.append(typeid)
            sql1=sql1.replace('isdelete=0','isdelete=1')
            sql=sql.replace('isdelete=0','isdelete=1')
        if order:
            sql=sql+' order by '+order
        else:
            sql=sql+' order by id desc'
        sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_aqsiq.execute(sql1,argument)
        else:
            self.cursor_aqsiq.execute(sql1)
        result1=self.cursor_aqsiq.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        if argument:
            self.cursor_aqsiq.execute(sql,argument)
        else:
            self.cursor_aqsiq.execute(sql)
        resultlist=self.cursor_aqsiq.fetchall()
        if resultlist:
            numb=0
            for result in resultlist:
                numb=numb+1
                id=result[0]
                ntype=result[3]
                typename=self.getaqsiqtypename(ntype)
                weburl='aqsiq_detail'+str(id)+'.html'
                list={'id':id,'ntitle':result[1],'ndate':formattime(result[2],1),'ntype':ntype,'typename':typename,'weburl':weburl,'numb':numb}
                listall.append(list)
        return {'list':listall,'count':count}

    def getaqsiqdetail(self,id):
        sql="select id,ntitle,ndate,ncontent,ntype from aqsiq_news where id=%s"
        self.cursor_aqsiq.execute(sql,[id])
        result=self.cursor_aqsiq.fetchone()
        if result:
            ntype=result[4]
            typename=self.getaqsiqtypename(ntype)
            list={'id':result[0],'ntitle':result[1],'ndate':formattime(result[2]),'ncontent':filter_style(result[3]),'ntype':ntype,'typename':typename}
            return list
    def updateaqsiq_news(self,artid,ntitle,ncontent,ntype):
        sql='update aqsiq_news set ntitle=%s,ncontent=%s,ntype=%s where id=%s'
        self.cursor_aqsiq.execute(sql,[ntitle,ncontent,ntype,artid])
        self.conn_aqsiq.commit()
    def addaqsiq_news(self,ndate,ntitle,ncontent,ntype):
        sql='insert into aqsiq_news(ndate,ntitle,ncontent,ntype) values(%s,%s,%s,%s)'
        self.cursor_aqsiq.execute(sql,[ndate,ntitle,ncontent,ntype])
        self.conn_aqsiq.commit()
    def del_aqsiq(self,id,isdelete):
        sql='update aqsiq_news set isdelete=%s where id=%s'
        self.cursor_aqsiq.execute(sql,[isdelete,id])
        self.conn_aqsiq.commit()
    def redel_aqsiq(self,id):
        sql='delete from aqsiq_news where id=%s'
        self.cursor_aqsiq.execute(sql,[id])
        self.conn_aqsiq.commit()
    def getaqsiqtypename(self,id):
        sql='select typename from webtype where id=%s'
        self.cursor_aqsiq.execute(sql,[id])
        result=self.cursor_aqsiq.fetchone()
        if result:
            return result[0]
    def gettypelist(self,wtype):
        sql='select id,typename from webtype where wtype=%s order by sortrank,id desc'
        self.cursor_aqsiq.execute(sql,[wtype])
        resultlist=self.cursor_aqsiq.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                listall2=[]
                typeid=result[0]
                sql2='select id,ntitle from aqsiq_news where isdelete=0 and ntype=%s order by id desc limit 0,3'
                self.cursor_aqsiq.execute(sql2,[typeid])
                resultlist2=self.cursor_aqsiq.fetchall()
                if resultlist2:
                    for result2 in resultlist2:
                        aid=result2[0]
                        weburl='aqsiq_detail'+str(aid)+'.html'
                        list2={'id':aid,'title':result2[1],'weburl':weburl}
                        listall2.append(list2)
                list={'id':typeid,'typename':result[1],'artical':listall2}
                listall.append(list)
        return listall
class zz91tags:
    def __init__(self):
        from zz91conn import database_comp
        self.conn=database_comp()
        self.cursor=self.conn.cursor()
    #----微门户关键词
    def getcplist(self,SPHINXCONFIG,keywords,limitcount):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetLimits (0,limitcount,limitcount)
        if keywords=='':
            res = cl.Query ('','daohangkeywords')
        else:
            res = cl.Query ('@(label) '+keywords,'daohangkeywords')
        listall=[]
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                listall_news=[]
                for match in tagslist:
                    id=match['id']
                    attrs=match['attrs']
                    label=attrs['plabel']
                    pingyin=attrs['ppinyin']
                    if pingyin!="":
                        list={'label':label,'pingyin':pingyin}
                        listall.append(list)
        if listall==[]:
            res = cl.Query ('','daohangkeywords')
            if res:
                if res.has_key('matches'):
                    tagslist=res['matches']
                    listall_news=[]
                    for match in tagslist:
                        id=match['id']
                        attrs=match['attrs']
                        label=attrs['plabel']
                        pingyin=attrs['ppinyin']
                        if pingyin!="":
                            list={'label':label,'pingyin':pingyin}
                            listall.append(list)
        return listall

class zz91company:
    def __init__(self):
        from zz91conn import database_comp
        self.conn_comp=database_comp()
        self.cursor_comp=self.conn_comp.cursor()
    def getcompanydetail(self,company_id):
        sql='select account,email from company_account where company_id=%s'
        self.cursor_comp.execute(sql,[company_id])
        result=self.cursor_comp.fetchone()
        if result:
            account=result[0]
            mail=result[1]
            list={'account':account,'mail':mail}
            return list
    def getaccountscore(self,frompageCount,limitNum,account='',nickname='',contact='',mobile=''):
        listall=[]
        count=0
        if nickname or contact or mobile:
            if nickname:
                accountlist=self.getaccountbynickname(nickname)
            if contact:
                accountlist=self.getaccountbycontact(contact)
            if mobile:
                accountlist=self.getaccountbymobile(mobile)
            if accountlist:
                for ac in accountlist:
                    sql3='select score,account from weixin_scoresall where account=%s'
                    self.cursor_comp.execute(sql3,[ac])
                    result=self.cursor_comp.fetchone()
                    if result:
                        account=result[1]
                        score=result[0]
                        mobile=self.getweixinmobile(account)
                        list={'score':score,'account':account,'mobile':mobile,'nickname':nickname}
                        listall.append(list)
            if listall:
                count=len(listall)
            return {'list':listall,'count':count}
        argument=[]
        sql1='select count(0) from weixin_scoresall'
        sql='select score,account from weixin_scoresall'
        if account:
            argument.append(account)
            sql1=sql1+' where account=%s'
            sql=sql+' where account=%s'
#        sql=sql+' order by gmt_created desc'
        sql=sql+' order by score desc'
        sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_comp.execute(sql1,argument)
        else:
            self.cursor_comp.execute(sql1)
        result1=self.cursor_comp.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        if argument:
            self.cursor_comp.execute(sql,argument)
        else:
            self.cursor_comp.execute(sql)
        resultlist=self.cursor_comp.fetchall()
        if resultlist:
            numb=0
            for result in resultlist:
                account=result[1]
                score=result[0]
                mobile=self.getweixinmobile(account)
                nickname=self.getnickname(account)
                companyname=self.getcompanyname(account)
                companyurl=self.getcompanyurl(account)
                list={'score':score,'account':account,'mobile':mobile,'nickname':nickname,'numb':'','companyname':companyname,'companyurl':companyurl}
                if account not in ['fenghui12039','PoOO']:
                    numb=numb+1
                    list['numb']=numb
                    listall.append(list)
        return {'list':listall,'count':count}
    def getweixinscore(self,frompageCount,limitNum,account=''):
        argument=[]
        sql1='select count(0) from weixin_score'
        sql='select id,account,gmt_created,score,validity  from weixin_score'
        if account:
            argument.append(account)
            sql1=sql1+' where account=%s'
            sql=sql+' where account=%s'
        sql=sql+' order by gmt_created desc'
        sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_comp.execute(sql1,argument)
        else:
            self.cursor_comp.execute(sql1)
        result1=self.cursor_comp.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        if argument:
            self.cursor_comp.execute(sql,argument)
        else:
            self.cursor_comp.execute(sql)
        resultlist=self.cursor_comp.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                account=result[1]
                mobile=self.getweixinmobile(account)
                list={'id':result[0],'account':account,'gmt_created':formattime(result[2]),'score':result[3],'validity':formattime(result[4],1),'mobile':mobile}
                listall.append(list)
        return {'list':listall,'count':count}
        
    def getcompanyname(self,account):
        sql='select company_id from company_account where account=%s'
        self.cursor_comp.execute(sql,[account])
        result=self.cursor_comp.fetchone()
        if result:
            company_id=result[0]
            sql1='select name from company where id=%s'
            self.cursor_comp.execute(sql1,[company_id])
            result1=self.cursor_comp.fetchone()
            if result1:
                return result1[0]
    def getcompanynamecomid(self,company_id):
        sql1='select name from company where id=%s'
        self.cursor_comp.execute(sql1,[company_id])
        result1=self.cursor_comp.fetchone()
        if result1:
            return result1[0]

    def getcompanyurl(self,account):
        sql='select company_id from company_account where account=%s'
        self.cursor_comp.execute(sql,[account])
        result=self.cursor_comp.fetchone()
        if result:
            company_id=result[0]
            sql1='select domain_zz91 from company where id=%s'
            self.cursor_comp.execute(sql1,[company_id])
            result1=self.cursor_comp.fetchone()
            if result1:
                url2=result1[0]
                if url2:
                    url='http://'+url2+'.zz91.com'
                    return url
            return 'http://company.zz91.com/compinfo'+str(company_id)+'.htm'
    def getprize(self,id):
        sql='select title from weixin_prize where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
    def getprizetype(self):
        sql='select id,title from weixin_prize'
        self.cursor_comp.execute(sql)
        resultlist=self.cursor_comp.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'id':result[0],'title':result[1]}
                listall.append(list)
        return listall
    def getweixinmobile(self,account):
        sql='select mobile from weixin_account where account=%s'
        self.cursor_comp.execute(sql,[account])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
        sql='select mobile from company_account where account=%s'
        self.cursor_comp.execute(sql,[account])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
    def getaccountbycontact(self,contact):
        sql='select account from company_account where contact=%s'
        self.cursor_comp.execute(sql,[contact])
        resultlist=self.cursor_comp.fetchall()
        if resultlist:
            listall=[]
            for result in resultlist:
                listall.append(result[0])
            return listall
    def getaccountbynickname(self,nickname):
        sql='select account from bbs_user_profiler where nickname=%s'
        self.cursor_comp.execute(sql,[nickname])
        resultlist=self.cursor_comp.fetchall()
        if resultlist:
            listall=[]
            for result in resultlist:
                listall.append(result[0])
            return listall
    def getaccountbymobile(self,mobile):
        sql='select account from weixin_account where mobile=%s'
        self.cursor_comp.execute(sql,[mobile])
        resultlist=self.cursor_comp.fetchall()
        if resultlist:
            listall=[]
            for result in resultlist:
                listall.append(result[0])
            return listall
        sql='select account from company_account where mobile=%s'
        self.cursor_comp.execute(sql,[mobile])
        resultlist=self.cursor_comp.fetchall()
        if resultlist:
            listall=[]
            for result in resultlist:
                listall.append(result[0])
            return listall
    def getnickname(self,account):
        sql='select nickname from bbs_user_profiler where account=%s'
        self.cursor_comp.execute(sql,[account])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
        return ''
    def hasaccount(self,account):
        sql='select id from weixin_scoresall where account=%s'
        self.cursor_comp.execute(sql,[account])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
    def addwexinscore(self,account,score):
        sql='insert into weixin_score(account,gmt_created,score,validity,rules_code) values(%s,%s,%s,%s,%s)'
        gmt_created=getToday()
        validity='9999-01-01'
        self.cursor_comp.execute(sql,[account,gmt_created,score,validity,'huzhu_answer'])
        self.conn_comp.commit()
    def updateprizelog(self,id):
        sql='update weixin_prizelog set ischeck=1 where id=%s'
        self.cursor_comp.execute(sql,[id])
        self.conn_comp.commit()
    def getscoreexchange(self,frompageCount,limitNum,account='',prizeid='',ischeck='',order=''):
        argument=[]
        sql1='select count(0) from weixin_prizelog where id>0'
        sql='select id,account,gmt_created,score,ischeck,prizeid from weixin_prizelog where id>0'
        if ischeck and not ischeck=='2':
            argument.append(ischeck)
            sql1=sql1+' and ischeck=%s'
            sql=sql+' and ischeck=%s'
        if account:
            argument.append(account)
            sql1=sql1+' and account=%s'
            sql=sql+' and account=%s'
        if prizeid:
            argument.append(prizeid)
            sql1=sql1+' and prizeid=%s'
            sql=sql+' and prizeid=%s'
        if order:
            sql=sql+' order by '+order+' desc'
        else:
            sql=sql+' order by gmt_created desc'
        sql=sql+' limit '+str(frompageCount)+','+str(limitNum)
        if argument:
            self.cursor_comp.execute(sql1,argument)
        else:
            self.cursor_comp.execute(sql1)
        result1=self.cursor_comp.fetchone()
        if result1:
            count=result1[0]
        else:
            count=0
        if argument:
            self.cursor_comp.execute(sql,argument)
        else:
            self.cursor_comp.execute(sql)
        resultlist=self.cursor_comp.fetchall()
        listall=[]
        if resultlist:
            numb=0
            for result in resultlist:
                numb=numb+1
                ischeck=result[4]
                prizeid=result[5]
                account=result[1]
                prizename=self.getprize(prizeid)
                mobile=self.getweixinmobile(account)
                companyname=self.getcompanyname(account)
                companyurl=self.getcompanyurl(account)
                if ischeck==1:
                    ischeck='是'
                else:
                    ischeck='否'
                list={'id':result[0],'prizename':prizename,'account':account,'gmt_created':formattime(result[2]),'score':result[3],'ischeck':ischeck,'mobile':mobile,'numb':numb,'companyname':companyname,'companyurl':companyurl}
                listall.append(list)
        return {'list':listall,'count':count}
    
    def gettypecodelist(self,category_code):
        sql='select title,link from data_index where category_code=%s'
        self.cursor_comp.execute(sql,[category_code])
        resultlist=self.cursor_comp.fetchall()
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'title':result[0],'link':result[1],'title_hex':getjiami(result[0])}
                listall.append(list)
        return listall
    def getpricecategory(self,id):
        sql='select type_id from price where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            type_id=result[0]
            sql1='select name from price_category where id=%s'
            self.cursor_comp.execute(sql1,[type_id])
            result1=self.cursor_comp.fetchone()
            if result1:
                return result1[0]
#------最新报价信息
    def getpricelist(self,SPHINXCONFIG,kname='',frompageCount='',limitNum='',maxcount=100000):
        cl = SphinxClient()
        price=SPHINXCONFIG['name']['price']['name']
        serverid=SPHINXCONFIG['name']['price']['serverid']
        port=SPHINXCONFIG['name']['price']['port']
        cl.SetServer ( serverid, port )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetSortMode( SPH_SORT_EXTENDED,"gmt_order desc" )
        cl.SetLimits (frompageCount,limitNum,maxcount)
        if kname:
            res = cl.Query ('@(title,tags,content_query) '+kname+'',price)
        else:
            res = cl.Query ('',price)
        listall_baojia=[]
        havelist=None
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                
                for match in tagslist:
                    td_id=match['id']
                    pricecategory=self.getpricecategory(td_id)
                    attrs=match['attrs']
                    title=attrs['ptitle']
                    pcontent=self.getpricecontent(td_id,98)
#                    title=getlightkeywords(cl,[title],kname,"company_price")
                    gmt_time=attrs['gmt_time']
                    list1={'td_title':title,'fulltitle':title,'td_id':td_id,'td_time':gmt_time,'url':'http://price.zz91.com/priceDetails_'+str(td_id)+'.htm','pcontent':pcontent,'pricecategory':pricecategory}
                    listall_baojia.append(list1)
                    havelist=listall_baojia
            listcount_baojia=res['total_found']
            if (listcount_baojia==0):
                
                res = cl.Query ('','price')
                if res:
                    if res.has_key('matches'):
                        tagslist=res['matches']
                        listall_baojia=[]
                        for match in tagslist:
                            td_id=match['id']
                            attrs=match['attrs']
                            title=attrs['ptitle']
                            pricecategory=self.getpricecategory(td_id)
                            pcontent=self.getpricecontent(td_id,98)
                            title=getlightkeywords(cl,[title],kname,"company_price")
                            gmt_time=attrs['gmt_time']
                            list1={'td_title':title,'fulltitle':title,'td_id':td_id,'td_time':gmt_time,'url':'http://price.zz91.com/priceDetails_'+str(td_id)+'.htm','pcontent':pcontent,'pricecategory':pricecategory}
                            listall_baojia.append(list1)
                    listcount_baojia=0
            return {'list':listall_baojia,'count':listcount_baojia,'havelist':havelist}
    def getpricecontent(self,id,len):
        sql='select content from price where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            return filter_tags(result[0]).replace(' ','')[:len]+'......'
    def getcomppricedetails(self,id,len):
        sql='select details from company_price where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            if result[0]:
                return result[0][:len]+'......'
    def getcomppricecategory(self,id):
        sql='select category_company_price_code from company_price where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            type_id=result[0]
            sql1='select label from category_company_price where code =%s'
            self.cursor_comp.execute(sql1,[type_id])
            result1=self.cursor_comp.fetchone()
            if result1:
                return result1[0]
    def getpricelist_company(self,SPHINXCONFIG,kname='',frompageCount='',limitNum='',maxcount=100000):
        #------企业报价
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetSortMode( SPH_SORT_EXTENDED,"post_time desc" )
        cl.SetLimits (frompageCount,limitNum,maxcount)
        res = cl.Query ('@(title,label1,label2,label3,area1,area2,area3) '+kname+'','company_price')
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                listall_baojia=[]
                for match in tagslist:
                    td_id=match['id']
                    pcontent=""
                    pcontent=self.getcomppricedetails(td_id,108)
                    pricecategory=self.getcomppricecategory(td_id)
                    attrs=match['attrs']
                    fulltitle=attrs['ptitle']
                    company_id=attrs['company_id']
                    companyname=self.getcompanynamecomid(company_id)
                    province=attrs['province']
                    city=attrs['city']
                    title=subString(fulltitle,60)
                    title=getlightkeywords(cl,[title],kname,"company_price")

                    gmt_time=attrs['ppost_time']
                    price_unit=attrs['price_unit']
                    min_price=attrs['min_price']
                    max_price=attrs['max_price']
                    #if (price=="" or price=="none"):
                    price=min_price+"-"+max_price+price_unit
                    #td_time=gmt_time.strftime('%Y-%m-%d')
                    list1={'td_title':title,'province':province,'city':city,'companyname':companyname,'company_id':company_id,'fulltitle':fulltitle,'td_id':td_id,'td_time':gmt_time,'price':price,'url':'http://price.zz91.com/companyprice/priceDetails'+str(td_id)+'.htm','pcontent':pcontent,'pricecategory':pricecategory}
                    listall_baojia.append(list1)
            listcount_baojia=res['total_found']
            return {'list':listall_baojia,'count':listcount_baojia}

#----趋势图报价
    def getchartpricelist(self,SPHINXCONFIG,keywords='',frompageCount='',limitNum='',SPHINXCONFIG2=''):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
#        cl.SetSortMode( SPH_SORT_ATTR_DESC ,'postdate desc' )
        cl.SetLimits (frompageCount,limitNum)
        if keywords:
            res = cl.Query ('@(label,label1) '+keywords,'pricelist')
        else:
            res = cl.Query ('','pricelist')
        listall=[]
        count=0
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                js=0
                for match in tagslist:
                    attrs=match['attrs']
                    title=attrs['ptitle']
                    label=attrs['plabel']
                    if not label:
                        label=attrs['plabel1']
                    priceid=attrs['priceid']
                    price=attrs['price']
                    price2=attrs['pprice2']
                    area=attrs['parea']
                    spec=attrs['spec']
                    spec1=attrs['spec1']
                    postdate=attrs['postdate']
                    othertext=attrs['othertext']
                    company_numb=''
                    if SPHINXCONFIG2:
                        company_numb=self.getpricelist_company_count(SPHINXCONFIG2,label)
                    list={'title':title,'label':label,'priceid':priceid,'price':'','area':area,'spec':spec,'spec1':spec1,'postdate':int_to_str(postdate),'othertext':othertext,'evennumber':'','company_numb':company_numb}
                    price=price.split('-')[0]
                    price=filter(str.isdigit,price)
                    if price:
                        js=js+1
                        if js%2==0:
                            evennumber=1
                        else:
                            evennumber=0
                        list['evennumber']=evennumber
                        list['price']=price
                        listall.append(list)
                count=res['total_found']
        return {'list':listall,'count':count}
    def getareapricelist(self,SPHINXCONFIG,keywords='',frompageCount='',limitNum='',arealist='',gmt_begin='',gmt_end=''):
        listall2=[]
        for area in arealist:
            cl = SphinxClient()
            cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
            cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
            if gmt_begin and gmt_end:
                cl.SetFilterRange('postdate',gmt_begin,gmt_end)
            cl.SetLimits (frompageCount,limitNum)
            if area:
                res = cl.Query ('@(area) '+area,'pricelist')
            else:
                res = cl.Query ('','pricelist')
            listall=[]
            count=0
            if res:
                if res.has_key('matches'):
                    tagslist=res['matches']
                    for match in tagslist:
                        attrs=match['attrs']
                        title=attrs['ptitle']
                        label=attrs['plabel']
                        if not label:
                            label=attrs['plabel1']
                        priceid=attrs['priceid']
                        price=attrs['price']
                        price2=attrs['pprice2']
                        area=attrs['parea']
                        spec=attrs['spec']
                        spec1=attrs['spec1']
                        postdate=attrs['postdate']
                        othertext=attrs['othertext']
                        list={'title':title,'label':label,'priceid':priceid,'price':'','area':area,'spec':spec,'spec1':spec1,'postdate':int_to_str(postdate),'othertext':othertext}
                        price=price.split('-')[0]
                        price=filter(str.isdigit,price)
                        if price:
                            list['price']=price
                            listall.append(list)
            list2={'list':listall,'area':area}
            listall2.append(list2)
        return listall2

        #企业报价条数
    def getpricelist_company_count(self,SPHINXCONFIG,kname=""):
        #------最新报价信息
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetSortMode( SPH_SORT_EXTENDED,"post_time desc" )
        cl.SetLimits (0,1)
        res = cl.Query ('@(title,label1,label2,label3,area1,area2,area3) '+kname+'','company_price')
        if res:
            return res['total_found']
        return 0
    def getarealist(self,SPHINXCONFIG,frompageCount,limitNum):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
#        cl.SetSortMode( SPH_SORT_ATTR_DESC ,'postdate desc' )
        cl.SetGroupBy( 'parea',SPH_GROUPBY_ATTR )
        cl.SetLimits (frompageCount,limitNum)
        res = cl.Query ('','pricelist')
        listall=[]
        numb=0
        if res:
#            return res
            if res.has_key('matches'):
                tagslist=res['matches']
                for match in tagslist:
                    attrs=match['attrs']
                    area=attrs['parea']
                    if area:
                        numb=numb+1
                        list={'area':area,'numb':numb}
                        listall.append(list)
        return listall

    def getbbslist(self,SPHINXCONFIG,kname='',limitcount='',gmt_begin='',gmt_end='',arg=1):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        if gmt_begin and gmt_end:
            cl.SetFilterRange('post_time',gmt_begin,gmt_end)
        cl.SetSortMode( SPH_SORT_ATTR_DESC,"visited_count" )
        cl.SetLimits (0,limitcount,limitcount)
        if kname:
            res = cl.Query ('@(title,tags) '+kname,'huzhu')
        else:
            res = cl.Query ('','huzhu')
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                listall_news=[]
                for match in tagslist:
                    id=match['id']
                    attrs=match['attrs']
                    title=attrs['ptitle']
                    visited_count=attrs['visited_count']
                    gmt_time=attrs['ppost_time']
                    list1={'title':title,'id':id,'gmt_time':gmt_time}
                    if arg==1:
                        if not '我画你猜' in title:
                            listall_news.append(list1)
                    else:
                        listall_news.append(list1)
                return listall_news

        #---公司库首页有图片的最新供求列表
    def getindexofferlist_pic(self,SPHINXCONFIG,kname="",pdt_type="",limitcount="",membertype=""):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetSortMode( SPH_SORT_EXTENDED," refresh_time desc" )
        cl.SetGroupBy("company_id",SPH_GROUPBY_ATTR)
        
        cl.SetLimits (0,limitcount,limitcount)
        if (pdt_type!=None and pdt_type!=""):
            cl.SetFilter('pdt_kind',[int(pdt_type)])
        cl.SetFilterRange('havepic',1,100)
        cl.SetFilterRange('haveprice',2,1000)
        if membertype:
            searchindex="offersearch_new_vip"
            cl.SetFilterRange('length_price',1,100000)
        else:
            searchindex="offersearch_new,offersearch_new_vip"
        if (kname==None):
            res = cl.Query ('',searchindex)
        else:
            res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+kname,searchindex)
        if res:
            if res.has_key('matches'):
                itemlist=res['matches']
                listall_offerlist=[]
                for match in itemlist:
                    pid=match['id']
                    price=self.getofferprice(pid)
                    attrs=match['attrs']
                    pdt_date=attrs['pdt_date']
                    pdt_kind=attrs['pdt_kind']
                    kindtxt=''
                    if (pdt_kind=='1'):
                        kindtxt="供应"
                    else:
                        kindtxt="求购"
                    title=subString(attrs['ptitle'],40)
                    sql1="select pic_address from products_pic where product_id=%s order by is_default desc,id desc"
                    self.cursor_comp.execute(sql1,[pid])
                    productspic = self.cursor_comp.fetchone()
                    if productspic:
                        pdt_images=productspic[0]
                    else:
                        pdt_images=""
                    if (pdt_images == '' or pdt_images == '0'):
                        pdt_images='http://img0.zz91.com/front/images/global/noimage.gif'
                    else:
                        pdt_images='http://pyapp.zz91.com/img/120x120/img1.zz91.com/'+pdt_images+''
                    list={'id':pid,'title':title,'gmt_time':pdt_date,'kindtxt':kindtxt,'fulltitle':attrs['ptitle'],'pdt_images':pdt_images,'price':price}
                    listall_offerlist.append(list)
                return listall_offerlist
    def getofferprice(self,id):
        sql="select p.min_price,p.max_price,p.price_unit from products as p where p.id=%s"
        self.cursor_comp.execute(sql,[id])
        plist = self.cursor_comp.fetchone()
        if plist:
            #价格范围判断
            allprice=""
            min_price=plist[0]
            if (min_price==None):
                min_price=''
            else:
                min_price=str(min_price)
                if (min_price!='0.0'):
                    allprice=allprice+min_price
            max_price=plist[1]
            if (max_price==None):
                max_price=''
            else:
                max_price=str(max_price)
                if (max_price!='0.0' and max_price!=min_price):
                    allprice=allprice+'-'+max_price
            price_unit=plist[2]
            #
            if (price_unit==None):
                price_unit=''
            else:
                if (allprice!=''):
                    allprice=allprice+price_unit
            if (allprice==""):
                allprice="电议或面议"
            return allprice

    def offerlist(self,SPHINXCONFIG,kname="",pdt_type="",limitcount="",havepic="",fromlimit=""):
        #-------------供求列表
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetSortMode( SPH_SORT_EXTENDED," refresh_time desc" )
        cl.SetGroupBy('company_id',SPH_GROUPBY_ATTR )
        if (fromlimit):
            cl.SetLimits (fromlimit,limitcount+fromlimit,limitcount+fromlimit)
        else:
            cl.SetLimits (0,limitcount,limitcount)
        if (pdt_type!="" and pdt_type!=None):
            cl.SetFilter('pdt_kind',[int(str(pdt_type))])
        if (havepic):
            cl.SetFilterRange('havepic',1,100)
        if (kname=='' or kname==None):
            res = cl.Query ('','offersearch_new_vip')
        else:
            res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+kname,'offersearch_new,offersearch_new_vip')
        if res:
            if res.has_key('matches'):
                itemlist=res['matches']
                listall_offerlist=[]
                numb=0
                arg=''
                for match in itemlist:
                    numb=numb+1
                    if numb==1:
                        arg='l'
                    if numb==2:
                        arg='r'
                    pid=match['id']
                    attrs=match['attrs']
                    company_id=attrs['company_id']
                    parea=self.getproducts_area(company_id)
                    companyname=self.getcompanyname_byid(company_id)
                    
                    pdt_date=int_to_str(attrs['refresh_time'])
                    short_time=pdt_date[5:]
                    
#                    products_detail=getproducts_detail(pid)
                    
                    productspic=self.getpic_address(pid)
                    if productspic:
                        pdt_images=productspic
                    else:
                        pdt_images=""
                    if (pdt_images == '' or pdt_images == '0'):
                        pdt_images='http://img0.zz91.com/front/images/global/noimage.gif'
                    else:
                        pdt_images='http://pyapp.zz91.com/img/100x100/img1.zz91.com/'+pdt_images+''
                    pic_address=pdt_images
                    title=attrs['ptitle']
                    viptype=self.getviptype(company_id)
                    list={'id':pid,'title':title,'gmt_time':pdt_date,'short_time':short_time,'fulltitle':attrs['ptitle'],'pic_address':pic_address,'products_detail':'','arg':arg,'parea':parea,'viptype':viptype,'companyname':companyname}
                    listall_offerlist.append(list)
                return listall_offerlist
    def getviptype(self,company_id):
        sql='select membership_code,domain_zz91 from company where id=%s'
        self.cursor_comp.execute(sql,[company_id])
        result=self.cursor_comp.fetchone()
        if result:
            viptype=result[0]
            domain_zz91=result[1]
            if domain_zz91:
                domain_zz91='http://'+domain_zz91+'.zz91.com/'
            else:
                domain_zz91='http://company.zz91.com/compinfo'+str(company_id)+'.htm'
            arrviptype={'vippic':'','vipname':'','isweixin':'','zstNum':'','compurl':'','domain_zz91':domain_zz91}
            if (viptype == '10051000'):
                arrviptype['vippic']=None
                arrviptype['vipname']='普通会员'
            if (viptype == '10051001'):
                arrviptype['vippic']='http://img.zz91.com/zz91images/recycle.gif'
                arrviptype['vipname']='再生通'
            if (viptype == '100510021000'):
                arrviptype['vippic']='http://img.zz91.com/zz91images/pptSilver.gif'
                arrviptype['vipname']='银牌品牌通'
            if (viptype == '100510021001'):
                arrviptype['vippic']='http://img.zz91.com/zz91images/pptGold.gif'
                arrviptype['vipname']='金牌品牌通'
            if (viptype == '100510021002'):
                arrviptype['vippic']='http://img.zz91.com/zz91images/pptDiamond.gif'
                arrviptype['vipname']='钻石品牌通'
            if (viptype == '10051000'):
                arrviptype['vipcheck']=None
            else:
                arrviptype['vipcheck']=1
            
            compurl=self.getcompurl(company_id)
            arrviptype['compurl']=compurl
            
            sqld="select qq,account from company_account where company_id=%s"
            self.cursor_comp.execute(sqld,[company_id])
            compqq = self.cursor_comp.fetchone()
            if compqq:
                qq=compqq[0]
                if qq:
                    qq=qq.strip()
                if (qq==""):
                    qq=None
                if (qq):
                    arrviptype['qq']=qq
                account=compqq[1]
                bindweixin=self.isbindweixin(account)
                if bindweixin:
                    arrviptype['isweixin']=1
                
            #年限
            sql2="select sum(zst_year) from crm_company_service where company_id="+str(company_id)+" and apply_status=1"
            self.cursor_comp.execute(sql2)
            zstNumvalue = self.cursor_comp.fetchone()
            if zstNumvalue:
                zst_year=zstNumvalue[0]
            arrviptype['zstNum']=zst_year
            return arrviptype
    #----是否绑定微信
    def isbindweixin(self,account):
        sql="select id from oauth_access where target_account=%s and open_type='weixin.qq.com'"
        self.cursor_comp.execute(sql,[account]);
        plist=self.cursor_comp.fetchone()
        if plist:
            return 1
        else:
            return None
    def getcompanyname_byid(self,company_id):
        sql='select name from company where id=%s'
        self.cursor_comp.execute(sql,[company_id])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
    #----供求地区详情
    def getproducts_area(self,company_id):
        sql='select area_code from company where id=%s'
        self.cursor_comp.execute(sql,[company_id])
        result=self.cursor_comp.fetchone()
        if result:
            area_code=result[0]
            area_code1=area_code[:-4]
            sql1='select label from category where code=%s'
            self.cursor_comp.execute(sql1,[area_code1])
            result1=self.cursor_comp.fetchone()
            if result1:
                label=result1[0]
                sql2='select label from category where code=%s'
                self.cursor_comp.execute(sql2,[area_code])
                result2=self.cursor_comp.fetchone()
                if result2:
                    return label+' '+result2[0]
    def getpic_address(self,product_id):
        sql='select pic_address from products_pic where product_id=%s and check_status=1 order by is_default desc,id desc'
        self.cursor_comp.execute(sql,[product_id])
        ldbresult=self.cursor_comp.fetchone()
        if ldbresult:
            return ldbresult[0]
    def getcompurl(self,id):
        sql='select domain_zz91 from company where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0]
    def getoffertype(self,SPHINXCONFIG,kname="",pdt_type="",limitcount="",havepic="",fromlimit=""):
        #-------------供求类型
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetSortMode( SPH_SORT_EXTENDED," refresh_time desc" )
        if (fromlimit):
            cl.SetLimits (fromlimit,limitcount+fromlimit,limitcount+fromlimit)
        else:
            cl.SetLimits (0,limitcount,limitcount)
        if (pdt_type!="" and pdt_type!=None):
            cl.SetFilter('pdt_kind',[int(str(pdt_type))])
        if (havepic):
            cl.SetFilterRange('havepic',1,100)
        if (kname=='' or kname==None):
            res = cl.Query ('','offersearch_new_vip')
        else:
            res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+kname,'offersearch_new,offersearch_new_vip')
        if res:
            if res.has_key('matches'):
                itemlist=res['matches']
                for match in itemlist:
                    pid=match['id']
                    offertype_code=self.getoffermain_code(pid)
                    return offertype_code
    def getoffermain_code(self,id):
        sql='select category_products_main_code from products where id=%s'
        self.cursor_comp.execute(sql,[id])
        result=self.cursor_comp.fetchone()
        if result:
            return result[0][:4]
    #相关供求类别
    def getcategorylist(self,SPHINXCONFIG,kname='',limitcount=''):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_ANY )
        #cl.SetSortMode( SPH_SORT_EXTENDED,"sort desc" )
        if (limitcount!=''):
            cl.SetLimits (0,limitcount,limitcount)
        if (kname!=""):
            res = cl.Query (''+kname,'category_products')
        else:
            res = cl.Query ('','category_products')
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                listall=[]
                for match in tagslist:
                    id=match['id']
                    attrs=match['attrs']
                    label=attrs['plabel']
                    code=attrs['pcode']
                    list1={'id':id,'code':code,'label':label,'label_hex':label.encode("hex")}
                    listall.append(list1)
                return listall
