#-*- coding:utf-8 -*-
class zzmessage:
    #----初始化ast数据库
    def __init__(self):
        self.dbc=dbc
    #----系统推送消息数量
    def getmessagecount(self,company_id):
        sql="select count(0) from app_message where company_id in (0,%s) and not exists(select mid from app_message_view where mid=app_message.id and company_id=%s and is_views=1)"
        count=self.dbc.fetchnumberdb(sql,[company_id,company_id])
        
        return count
    #更新消息状态
    def updatemessages(self,mid,company_id):
        #更新monggodb
        collection=dbmongo.appmessages
        collection.update({"id": int(mid)}, {"$set": {"isview": 1}});
        sql="select id from app_message_view where mid=%s and company_id=%s"
        result=dbc.fetchonedb(sql,[mid,company_id])
        if result:
            sqlu="update app_message_view set is_views=1 where mid=%s and company_id=%s"
            dbc.updatetodb(sqlu,[mid,company_id])
        else:
            gmt_created=datetime.datetime.now()
            sqlu="insert into app_message_view (mid,company_id,gmt_created,is_views) values(%s,%s,%s,%s)"
            dbc.updatetodb(sqlu,[mid,company_id,gmt_created,1])
        
    #一键更新未读
    def updatemessagesall(self,company_id):
        sql1='select id from app_message where company_id in (%s,0) '
        result=dbc.fetchalldb(sql1,[company_id])
        if result:
            for list in result:
                self.updatemessages(list[0],company_id)
    #----系统推送消息列表
    def getmessagelist(self,company_id="",frompageCount=0,limitNum=20,isviews=None,mtype=0):
        
        sql1='select count(0) from app_message where company_id in (%s,0) '
        result1=self.dbc.fetchonedb(sql1,[company_id])
        if result1:
            count=result1[0]
        else:
            count=0
        sql="select id,title,url,content,company_id,gmt_created from app_message where company_id in (%s,0)"
        if isviews=="1":
            sql+=" and exists(select id from app_message_view where mid=app_message.id and is_views=1)"
        if isviews=="0":
            sql+=" and not exists(select id from app_message_view where mid=app_message.id and is_views=1)"
        sql+=" order by id desc limit %s,%s"
        resultlist=self.dbc.fetchalldb(sql,[company_id,frompageCount,limitNum])
        
        listall=[]
        for result in resultlist:
            id=result[0]
            title=result[1]
            url=result[2]
            url=url.replace("?","awena")
            url=url.replace("&","aanda")
            content=result[3]
            gmt_created=formattime(result[5],3)
            isviews=self.getmessageview(id,company_id)
            list={'id':id,'title':title,'url':url,'content':content,'gmt_created':gmt_created,'isviews':isviews}
            listall.append(list)
        return {'list':listall,'count':count}
    def getnoviewmessagecount(self,company_id="",mtype=""):
        collection=dbmongo.appmessages
        searchlist={}
        if not mtype:
            mtype=0
        if company_id:
            searchlist["company_id"]={"$in":[int(company_id)]}
            searchlist["type"]=int(mtype)
            searchlist["isview"]=0
        count=collection.find(searchlist).count()
        if mtype==0:
            sql1='select count(0) from app_message where company_id=0 and not exists(select id from app_message_view where mid=app_message.id and company_id=%s and is_views=1)'
            result1=self.dbc.fetchonedb(sql1,[company_id])
            if result1:
                count1=int(result1[0])
            else:
                count1=0
            if count:
                count=count+count1
            else:
                count=count1
        return count
    #----系统推送消息列表
    def getmessagelistmongo(self,company_id="",frompageCount=0,limitNum=20,isviews=None,mtype=0):
        collection=dbmongo.appmessages
        searchlist={}
        if not mtype:
            mtype=0
        if company_id:
            searchlist["company_id"]={"$in":[int(company_id),0]}
            searchlist["type"]=int(mtype)
        if isviews=="1":
            searchlist["isview"]=1
        if isviews=="0":
            searchlist["isview"]=0
        
        count=collection.find(searchlist).count()
        resultlist=collection.find(searchlist).sort("id",-1).skip(frompageCount).limit(limitNum)
        """
        sql1='select count(0) from app_message where company_id in (%s,0) '
        result1=self.dbc.fetchonedb(sql1,[company_id])
        if result1:
            count=result1[0]
        else:
            count=0
        sql="select id,title,url,content,company_id,gmt_created from app_message where company_id in (%s,0)"
        if isviews=="1":
            sql+=" and exists(select id from app_message_view where mid=app_message.id and is_views=1)"
        if isviews=="0":
            sql+=" and not exists(select id from app_message_view where mid=app_message.id and is_views=1)"
        sql+=" order by id desc limit %s,%s"
        resultlist=self.dbc.fetchalldb(sql,[company_id,frompageCount,limitNum])
        
        listall=[]
        for result in resultlist:
            id=result[0]
            title=result[1]
            url=result[2]
            url=url.replace("?","awena")
            url=url.replace("&","aanda")
            content=result[3]
            gmt_created=formattime(result[5],3)
            isviews=self.getmessageview(id,company_id)
            list={'id':id,'title':title,'url':url,'content':content,'gmt_created':gmt_created,'isviews':isviews}
            listall.append(list)
        """
        listall=[]
        for result in resultlist:
            id=result['id']
            title=result['title']
            url=result['url']
            url=url.replace("?","awena")
            url=url.replace("&","aanda")
            content=result['content']
            gmt_created=formattime(result['gmt_created'],3)
            #isview=result['isview']
            isviews=self.getmessageview(id,company_id)
            list={'id':id,'title':title,'url':url,'content':content,'gmt_created':gmt_created,'isviews':isviews}
            listall.append(list)
        return {'list':listall,'count':count,'testlist':searchlist}
    #----是否查看
    def getmessageview(self,mid,company_id):
        sql='select id from app_message_view where company_id=%s and is_views=1 and mid=%s'
        resultlist=self.dbc.fetchonedb(sql,[company_id,mid])
        listall=[]
        if resultlist:
            return 1
        else:
            return None
             
    def getmessagelist1(self,company_id):
        sql1='select count(0) from app_message_view where company_id=%s and is_views=0'
        count=self.dbc.fetchnumberdb(sql1,company_id)
        sql='select id,mid,is_views,gmt_created from app_message_view where company_id=%s and is_views=0'
        resultlist=self.dbc.fetchalldb(sql,company_id)
        listall=[]
        for result in resultlist:
            id=result[0]
            mid=result[1]
            messagedetail=self.getmessagedetail(mid)
            is_views=result[2]
            gmt_created=formattime(result[3],3)
            list={'id':id,'mid':mid,'is_views':is_views,'messagedetail':messagedetail,'gmt_created':gmt_created}
            listall.append(list)
        return {'count':count,'list':listall,'txt':'系统推送消息'}
    def getmessagedetail(self,id):
        sql='select title,url,gmt_created,content from app_message where id=%s'
        result=self.dbc.fetchonedb(sql,[id])
        list=None
        if result:
            title=result[0]
            url=result[1]
            gmt_created=formattime(result[2],3)
            content=result[3]
            list={'title':title,'url':url,'gmt_created':gmt_created,'content':content}
        return list
    
    
    
    #判断当前用户是否选取了主营行业类别
    def has_industry_code(self,company_id):
        sql='select industry_code from company where id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])
        if result[0]  and result[0]!=0 and result[0]!='0':
            return 1
        else:
            return None
    #如果没有主营行业类别则进行选取行业类别(10个)
    def get_ten_lei(self):
        sql="select code,label from category where parent_code='1000'"
        resultlist=self.dbc.fetchalldb(sql)
        listall=[]
        if resultlist:
            for result in resultlist:
                code=result[0]
                label=result[1]
                list={"code":code,"label":label}
                listall.append(list)
            return {'list':listall}
    #获得主营行业类别
    def getindustrycode(self,company_id):
        sql='select industry_code from company where id=%s'
        result=self.dbc.fetchonedb(sql,[company_id])
        if result:
            return result[0]
    #获得相对应行业的推送供求
    def getapppushlist(self,industry_code):
        sql='select id,code,title,weburl,content,gmt_created from app_push_tuijian where code=%s order by id desc limit 0,9'
        resultlist=self.dbc.fetchalldb(sql,[industry_code])
        listall=[]
        if resultlist:
            for result in resultlist:
                id=result[0]
                industry_code=result[1]
                title=result[2]
                weburl=result[3]
                content=result[4]
                gmt_created=formattime(result[5],2)
                list={"id":id,"industry_code":industry_code,"title":title,"weburl":weburl,"content":content,"gmt_created":gmt_created}
                listall.append(list)
            return {'list':listall}
        
        
        
        
        
        