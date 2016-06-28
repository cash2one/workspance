#-*- coding:utf-8 -*-
class zznews:
    #----初始化ast数据库
    def __init__(self):
        self.dbn=dbn
        self.dbc=dbc
    def getnewscolumn(self):
        #获得缓存
        zz91app_getnewscolumn=cache.get("zz91app_getnewscolumn")
        if zz91app_getnewscolumn:
            return zz91app_getnewscolumn
        sql='select id,typename,keywords from dede_arctype where reid=184 and id not in (190,191,192,193,194,195,196,235) order by sortrank limit 0,8'
        resultlist=self.dbn.fetchalldb(sql)
        if resultlist:
            listall=[]
            numb=0
            for result in resultlist:
                numb=numb+1
                id=result[0]
                typename=result[1].decode('utf8','ignore')
                url=result[2]
                newslist=self.getnewslist(frompageCount=0,limitNum=5,typeid=[id])
                list={'typeid':id,'typename':typename,'url':url,'numb':numb,'newslist':newslist}
                listall.append(list)
            #设置缓存
            cache.set("zz91app_getnewscolumn",listall,60*10)
            return listall
    #----资讯列表(搜索引擎)
    def getnewslist(self,keywords="",frompageCount="",limitNum="",typeid="",typeid2="",allnum="",arg='',flag='',type='',MATCH=""):
        cl = SphinxClient()
        news=spconfig['name']['news']['name']
        serverid=spconfig['name']['news']['serverid']
        port=spconfig['name']['news']['port']
        cl.SetServer ( serverid, port )
        if MATCH!="":
            cl.SetMatchMode ( SPH_MATCH_ANY )
        else:
            cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        
        if (typeid):
            if typeid==['196']:
                keywords="p"
                typeid=""
            elif typeid==['195']:
                return self.getcompanynews(frompageCount,limitNum)
            else:
                cl.SetFilter('typeid',typeid)
        if (typeid2):
            cl.SetFilter('typeid2',[typeid2])
        cl.SetFilter('deleted',[0])
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
                res = cl.Query ('@(title,description) '+keywords+'&@(flag) "'+str(flag)+'"',news)
            else:
                if arg==1:
                    res = cl.Query ('@(title,description) '+keywords,news)
                else:
                    res = cl.Query ('@(title) '+keywords,news)
        else:
            if flag:
                res = cl.Query ('@(flag) "'+str(flag)+'"',news)
            else:
                res = cl.Query ('',news)
        listall_news=[]
        listcount_news=0
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                for match in tagslist:
                    id=match['id']
                    subcontent=self.getsubcontent(id,160).decode('utf8','ignore')
                    subcontent=subcontent.replace('　','')
                    subcontent=subcontent.replace('\n','')
                    subcontent=subcontent.replace('\r','')
                    subcontent=subcontent.replace('\t','')
                    subcontent=subcontent.replace('\\r\\n\\r\\n\\t','')
                    subcontent=subcontent.replace('\\','')
                    
                    
                    murl="http://app.zz91.com/news/newsdetail"+str(id)+".htm"
                    attrs=match['attrs']
                    title=attrs['ptitle'].decode('utf8','ignore')
                    click=attrs['click']
                    pubdate=attrs['pubdate']
                    pic=attrs['litpic']
                    if pic!="" and pic:
                        if "http://" in pic:
                            litpic="http://pyapp.zz91.com/app/changepic.html?url=" +pic+"&width=300&height=300"
                        else:
                            litpic="http://pyapp.zz91.com/app/changepic.html?url=http://news.zz91.com/" +pic+"&width=300&height=300"
                    else: 
                        #litpic="http://img0.zz91.com/front/images/global/noimage.gif"
                        litpic=None
                    pubdate2=time.strftime('%Y-%m-%d', time.localtime(pubdate))
                    list1={'title':title,'click':click,'id':id,'pubdate':pubdate2,'murl':murl,'litpic':litpic,'subcontent':subcontent}
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
        if limitNum==1:
            return ''
        return {'list':listall_news,'count':listcount_news}
    def getsubcontent(self,id,len):
        #获得缓存
        #zz91app_getsubcontent=cache.get("zz91app_getsubcontent"+str(id)+str(len))
        #if zz91app_getsubcontent:
            #return zz91app_getsubcontent
        sql="select body from dede_addonarticle where aid=%s"
        alist = self.dbn.fetchonedb(sql,[id])
        content=""
        if alist:
            content=alist[0]
            if content:
                content=content.replace("　", "")
                content=subString(filter_tags(content),len)
            #设置缓存
            #cache.set("zz91app_getsubcontent"+str(id)+str(len),content,60*10)
        return content
        """
        sql='select description from dede_archives where id=%s'
        result=self.dbn.fetchonedb(sql,[id])
        if result:
            body=result[0]
            if body:
                return body[:len]
            else:
                return ""
        """
    #获得一条资讯
    def getonenews(self):
        sql='select id,title from dede_archives order by id desc'
        result=self.dbn.fetchonedb(sql)
        if result:
            return {'id':result[0],'title':result[1]}
        else:
            return ''
    #获取新闻栏目名称(一期)
    def get_typename(self,id):
        #获得缓存
        zz91app_get_typename=cache.get("zz91app_get_typename"+str(id))
        if zz91app_get_typename:
            return zz91app_get_typename
        sql='select typename from dede_arctype where id=%s'
        result=self.dbn.fetchonedb(sql,[id])
        if result:
            #设置缓存
            cache.set("zz91app_get_typename"+str(id),result[0],60*10)
            return result[0]
    #获得新闻栏目id列表
    def getcolumnid(self):
        sql='select id from dede_arctype where reid=184 and id not in (190,191,192,193,194,195,196,235) order by sortrank limit 0,8'
        resultlist=self.dbn.fetchalldb(sql)
        if resultlist:
            listall=[]
            for result in resultlist:
                listall.append(result[0])
            return listall
        
    #获得类别列表
    def getcolumnlist(self):
        sql='select id,typename from dede_arctype where reid=184 and id not in (190,191,192,193,194,195,196,235) order by sortrank limit 0,20'
        resultlist=self.dbn.fetchalldb(sql)
        if resultlist:
            listall=[]
            for result in resultlist:
                list={'id':result[0],'typename':result[1]}
                listall.append(list)
            return listall
    
    #最新图片新闻
    def get_newsimgsone(self):
        cursor_news = conn_news.cursor()
        sql="select id,title,pubdate from dede_archives where flag='p' order by pubdate desc limit 0,9"
        cursor_news.execute(sql)
        alist = cursor_news.fetchone()
        list=None
        if alist:
            id=alist[0]
            title=alist[1]
            contentlist=getnewscontent(id,cursor_news)
            if contentlist:
                content=contentlist['content']
                result=get_img_url(content)
                mobileweburl="http://m.zz91.com/news/newsdetail"+str(id)+".htm?type=news"
                if result:
                    imglll=result[0]
                    if "uploads/media/img_news" in imglll:
                        img_url="http://news.zz91.com"+imglll
                    else:
                        img_url=imglll
                else:
                    img_url=""
                list={'id':id,'title':title,'picurl':img_url,'url':mobileweburl}
        cursor_news.close()
        return list
    #----最终页相关阅读(4大类别)(一期)
    def get_typenews(self,typeid="",typeid2=""):
        culvalue=[]
        sql="select id,title,click,litpic from dede_archives where id>0 "
        if typeid:
            sql+=" and typeid=%s"
            culvalue.append(typeid)
        if typeid2:
            sql+=" and typeid2=%s"
            culvalue.append(typeid2)
        sql+=" order by id desc limit 0,5"
        if culvalue!=[]:
            resultlist=self.dbn.fetchalldb(sql,culvalue)
        else:
            resultlist=self.dbn.fetchalldb(sql)
        listall=[]
        if resultlist:
            for result in resultlist:
                subcontent=self.getsubcontent(result[0],80).decode('utf8','ignore')
                pic=result[3]
                if pic!="" and pic:
                    if "http://" in pic:
                        litpic="http://pyapp.zz91.com/app/changepic.html?url=" +pic+"&width=300&height=300"
                    else:
                        litpic="http://pyapp.zz91.com/app/changepic.html?url=http://news.zz91.com/" +pic+"&width=300&height=300"
                else: 
                    litpic=None
                list={'id':result[0],'title':result[1].decode('utf8','ignore'),'click':result[2],'subcontent':subcontent,'litpic':litpic}
                listall.append(list)
        return listall
    
    #----推荐资讯（app置顶资讯）
    def get_topnews(self,typeid="",typeid2=""):
        cl = SphinxClient()
        news=spconfig['name']['news']['name']
        serverid=spconfig['name']['news']['serverid']
        port=spconfig['name']['news']['port']
        cl.SetServer ( serverid, port )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        
        if (typeid):
            cl.SetFilter('typeid',[int(typeid)])
        if (typeid2):
            cl.SetFilter('typeid2',[typeid2])
        cl.SetFilter('deleted',[0])
        cl.SetSortMode( SPH_SORT_ATTR_DESC ,'pubdate' )
        cl.SetLimits (0,3,3)
        res = cl.Query ('@(flag) "c"',news)
        listall_news=[]
        listcount_news=0
        if res:
            if res.has_key('matches'):
                tagslist=res['matches']
                for match in tagslist:
                    id=match['id']
                    subcontent=self.getsubcontent(id,80).decode('utf8','ignore')
                    murl="http://app.zz91.com/news/newsdetail"+str(id)+".htm"
                    attrs=match['attrs']
                    title=attrs['ptitle'].decode('utf8','ignore')
                    click=attrs['click']
                    pubdate=attrs['pubdate']
                    pic=attrs['litpic']
                    if pic!="" and pic:
                        if "http://" in pic:
                            litpic="http://pyapp.zz91.com/app/changepic.html?url=" +pic+"&width=300&height=300"
                        else:
                            litpic="http://pyapp.zz91.com/app/changepic.html?url=http://news.zz91.com/" +pic+"&width=300&height=300"
                    else: 
                        litpic=None
                    pubdate2=time.strftime('%Y-%m-%d', time.localtime(pubdate))
                    list1={'title':title,'click':click,'id':id,'pubdate':pubdate2,'murl':murl,'litpic':litpic,'subcontent':subcontent}
                    listall_news.append(list1)
                listcount_news=res['total_found']
        return listall_news
    
    #----新闻最终页上一篇下一篇(一期)
    def getarticalup(self,id,typeid):
        #获得缓存
        zz91app_getarticalup=cache.get("zz91app_getarticalup"+str(id)+str(typeid))
        if zz91app_getarticalup:
            return zz91app_getarticalup
        sqlt="select id,title from dede_archives where typeid=%s and id>%s order by id limit 0,1"
        resultu = self.dbn.fetchonedb(sqlt,[typeid,id])
        if resultu:
            list={'id':resultu[0],'title':resultu[1]}
            #设置缓存
            cache.set("zz91app_getarticalup"+str(id)+str(typeid),list,60*10)
            return list
    def getarticalnx(self,id,typeid):
        #获得缓存
        zz91app_getarticalnx=cache.get("zz91app_getarticalnx"+str(id)+str(typeid))
        if zz91app_getarticalnx:
            return zz91app_getarticalnx
        sqlt="select id,title from dede_archives where typeid=%s and id<%s order by id desc limit 0,1"
        resultn = self.dbn.fetchonedb(sqlt,[typeid,id])
        if resultn:
            list={'id':resultn[0],'title':resultn[1]}
            #设置缓存
            cache.set("zz91app_getarticalnx"+str(id)+str(typeid),list,60*10)
            return list
    #新闻内容
    def getnewscontent(self,id):
        newscontent=cache.get("newscontent"+str(id))
        newscontent=None
        if (newscontent==None):
            sqlt="select title,pubdate,click from dede_archives where id=%s"
            alist = self.dbn.fetchonedb(sqlt,[id])
            title=""
            pubdate=""
            click=""
            if alist:
                title=alist[0]
                pubdate=int_to_strall(alist[1])
                click=alist[2]
            else:
                title=""
                pubdate="2016-01-29 06:45:01"
                click=0
            sql="select body from dede_addonarticle where aid=%s"
            alist = self.dbn.fetchonedb(sql,[id])
            content=""
            if alist:
                content=alist[0]
                #content=getreplacepic(content)
                content=replacepic(content)
            newscontent={'title':title,'pubdate':pubdate,'content':content,'click':click}
            cache.set("newscontent"+str(id),newscontent,60*60)
        return newscontent
    #----新闻加点击数
    def newsclick_add(self,id):
        sql="update dede_archives set click=click+1 where id=%s"
        self.dbn.updatetodb(sql,[id])
    #----读取企业新闻
    def getcompanynews(self,fromcount,limitcount):
        #获得缓存
        zz91app_getcompanynews=cache.get("zz91app_getcompanynews")
        if zz91app_getcompanynews:
            return zz91app_getcompanynews
        sql="select count(0) from esite_news"
        listcount = self.dbc.fetchonedb(sql)[0]
        sql="select a.id,a.company_id,a.title,a.post_time,b.domain_zz91 from esite_news as a left join company as b on a.company_id=b.id order by id desc limit "+str(fromcount)+","+str(limitcount)+""
        result = self.dbc.fetchalldb(sql)
        listall=[]
        if result:
            for list in result:
                id=list[0]
                company_id=list[1]
                title=list[2]
                title10=title[:10]
                post_time=list[3]
                short_time=getMonth(post_time)+"-"+getDay(post_time)
                domain_zz91=list[4]
                list={'id':id,'title':title,'domain_zz91':domain_zz91,'pubdate':getMonth(post_time)+"-"+getDay(post_time),'short_time':short_time,'title10':title10}
                listall.append(list)
        #设置缓存
        cache.set("zz91app_getcompanynews",{'list':listall,'count':listcount},60*10)
        return {'list':listall,'count':listcount}