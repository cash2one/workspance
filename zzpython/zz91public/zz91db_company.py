#-*- coding:utf-8 -*-
import re,datetime,time
from sphinxapi import *
from zz91tools import int_to_str,formattime
from zz91db_ast import companydb

#ast数据库函数
class functiondb:
    def __init__(self):
        self.db_comp=companydb()
    #----根据ID获得公司名
    def getcompanynamebyid(self,id):
        sql='select name from company where id=%s'
        result=self.db_comp.fetchonedb(sql,[id])
        if result:
            return result[0]
    #----根据帐号获得companyid
    def getcompany_account_byaccount(self,accout):
        sql="select company_id,contact,tel_area_code,tel,mobile,email,is_use_back_email,back_email from company_account where account=%s"
        list=self.db_comp.fetchonedb(sql,[accout])
        if list:
            email=list[5]
            is_use_back_email=list[6]
            back_email=list[7]
            if str(is_use_back_email)=="1":
                if back_email and back_email!="":
                    email=back_email
            return {'company_id':list[0],'contact':list[1],'mobile':list[4],'email':email}
        #----根据帐号获得companyid
    def getcompany_account_bycompany_id(self,company_id):
        sql="select company_id,contact,tel_area_code,tel,mobile,email,is_use_back_email,back_email from company_account where company_id=%s"
        list=self.db_comp.fetchonedb(sql,[company_id])
        if list:
            email=list[5]
            is_use_back_email=list[6]
            back_email=list[7]
            if str(is_use_back_email)=="1":
                if back_email and back_email!="":
                    email=back_email
            return {'company_id':list[0],'contact':list[1],'mobile':list[4],'email':email}
    
    #----获得公司详情
    def getcompanydetail(self,id):
        sql='select name,industry_code,area_code,regtime from company where id=%s'
        result=self.db_comp.fetchonedb(sql,[id])
        if result:
            company_name=result[0]
            industry_code=result[1]
            industry=self.getlabel(industry_code)
            area_code=result[2]
            area=self.getlabel(area_code)
            regtime=formattime(result[3])
            list={'company_name':company_name,'industry':industry,'area':area,'regtime':regtime,'industry_code':industry_code,'area_code':area_code}
            return list
    #---根据code获得类别名
    def getlabel(self,code):
        sql='select label from category where code=%s'
        result=self.db_comp.fetchonedb(sql,[code])
        if result:
            return result[0]
    #----根据code获得 供求 类别名
    def getcategorylabel(self,code):
        sql='select label from category_products where code=%s'
        result=self.db_comp.fetchonedb(sql,[code])
        if result:
            return result[0]
    #----根据parent_code获得 供求 类别名,code
    def getcategory(self,parent_code):
        sql='select code,label from category where parent_code=%s'
        resultlist=self.db_comp.fetchalldb(sql,[parent_code])
        listall=[]
        if resultlist:
            for result in resultlist:
                list={'code':result[0],'label':result[1]}
                listall.append(list)
        return listall
    #----关闭连接
    def closedb(self):
        self.db_comp.close()

#----登陆数据
class zz91login:
    def __init__(self):
        self.zzdb=functiondb()
    #----获得 废金属,废塑料,废纸...服务 类别
    def getallindustry(self):
        allindustry=self.zzdb.getcategory(1000)
        return allindustry
    #---获得中国城市地区
    def getallarea(self):
        allarea=self.zzdb.getcategory(10011000)
        return allarea
    #---读搜索引擎,获取登陆数据
    def getlogindata(self,SPHINXCONFIG,gmt_begin="",gmt_end="",frompageCount="",limitNum="",allnum=""):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        #cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetFilterRange('pgmt_target',gmt_begin,gmt_end)
        cl.SetSortMode( SPH_SORT_ATTR_DESC,'pgmt_target' )
        cl.SetGroupBy('pcompany_id',SPH_GROUPBY_ATTR )
        cl.SetLimits (frompageCount,limitNum)
        res = cl.Query ('','datalogin')
        if res:
            listall=[]
#            return res
            tagslist=res['matches']
            allcount=res['total_found']
            for match in tagslist:
                attrs=match['attrs']
                count=attrs['@count']
                login_count=attrs['plogin_count']
                company_id=attrs['@groupby']
                companydetail=self.zzdb.getcompanydetail(company_id)
                gmt_target=attrs['pgmt_target']
                list={'company_id':company_id,'companydetail':companydetail,'count':count,'login_count':login_count,'gmt_target':int_to_str(gmt_target)}
                listall.append(list)
            return {'list':listall,'count':allcount}
    #---查询登陆总数
    def getlogincount(self,SPHINXCONFIG,gmt_begin="",gmt_end="",frompageCount="",limitNum="",allnum=""):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        #cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetFilterRange('pgmt_target',gmt_begin,gmt_end)
        #cl.SetSortMode( SPH_SORT_EXTENDED,'plogin_count desc' )
        cl.SetGroupBy('pcompany_id',SPH_GROUPBY_ATTR )
        cl.SetLimits (frompageCount,limitNum,allnum)
        res = cl.Query ('','datalogin')
        #return res
        if res:
#            tagslist=res['matches']
            count=res['total_found']
            return count
    #---查询登陆记录(登陆频率)
    def getloginrecord(self,SPHINXCONFIG,gmt_begin="",gmt_end="",frompageCount="",limitNum="",allnum="",gmt_difference=""):
        cl = SphinxClient()
        cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
#        cl.SetSortMode( SPH_SORT_EXTENDED,'pgmt_target desc' )
        cl.SetFilterRange('pgmt_target',gmt_begin,gmt_end)
#        cl.SetGroupBy( 'pgmt_target desc',SPH_GROUPBY_DAY )
        cl.SetSortMode( SPH_SORT_EXTENDED,'plogin_count desc' )
        cl.SetGroupBy( 'pcompany_id',SPH_GROUPBY_ATTR )
        cl.SetLimits (frompageCount,limitNum,allnum)
        res = cl.Query ('','datalogin')
        list1=[]
        list2=[]
        list3=[]
        list4=[]
        list5=[]
        list6=[]
        list7=[]
        countall=0
        if res:
#            return res
            if res.has_key('matches'):
                tagslist=res['matches']
                countall=len(tagslist)
                for match in tagslist:
                    attrs=match['attrs']
                    count=attrs['@count']
                    login_count=attrs['plogin_count']
                    company_id=attrs['@groupby']
                    list={'company_id':company_id,'count':count,'login_count':login_count}
                    if count==gmt_difference and login_count==1:
                        #每日登陆一次
                        list1.append(list)
                    elif gmt_difference>=7 and count==1:
                        #一周登录1次
                        list2.append(list)
                    elif count>gmt_difference and login_count>1:
                        #每日登陆多次
                        list3.append(list)
                    elif gmt_difference>=90 and count>30:
                        #三月登录多次
                        list6.append(list)
                    elif gmt_difference>=30 and count>7:
                        #一月登录多次
                        list5.append(list)
                    elif gmt_difference>=7 and count>1:
                        #一周登录多次
                        list4.append(list)
                    elif gmt_difference>=90 and count==1:
                        list6.append(list)
                    elif gmt_difference>=30 and count==1:
                        list5.append(list)
                    else:
                        list7.append(list)
        return {'list1':list1,'list2':list2,'list3':list3,'list4':list4,'list5':list5,'list6':list6,'list7':list7,'count':countall}