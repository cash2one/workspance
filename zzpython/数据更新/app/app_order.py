#-*- coding:utf-8 -*-
#----商行定制定时任务
from public import *
from sphinxapi import *
from zz91tools import int_to_str,subString,filter_tags

def getapp_companylist(dbc):
    sql='select company_id from app_company'
    resultlist=dbc.fetchalldb(sql)
    listall=[]
    for result in resultlist:
        listall.append(result[0])
    return listall

def getapp_order(dbc,datetoday,nowtime):
    app_companylist=getapp_companylist(dbc)
#    company_id=969597
    for company_id in app_companylist:
        sql='select businesskeywords,pricekeywords from app_order where company_id=%s'
        sql2='insert into app_custom(did,company_id,is_views,type,gmt_created) values(%s,%s,%s,%s,%s)'
        sql3='select id from app_custom where did=%s and type=%s and gmt_created=%s'
        result=dbc.fetchonedb(sql,company_id)
        if result:
    #        print result
            businesskeywords=result[0]
            businesslist=businesskeywords.split(',')[:5]
            for business in businesslist:
                result2=dbc.fetchonedb(sql3,[business,1,datetoday])
                if not result2:
                    companykeyword=getcompanykeyword(dbc,business)
                    offlist=getindexofferlist(kname=companykeyword)
                    if offlist:
                        dbc.updatetodb(sql2,[business,company_id,0,1,datetoday])
            pricekeywords=result[1]
            pricelist=pricekeywords.split(',')[:5]
            for price in pricelist:
                result4=dbc.fetchonedb(sql3,[price,2,datetoday])
                if not result4:
                    prlist=getpricelist(dbc,category_id=[int(price)])
                    if prlist:
                        dbc.updatetodb(sql2,[price,company_id,0,2,datetoday])

#----商机定制主类
def getcompanykeyword(dbc,id):
    sql="select title from data_index where id=%s"
    datalist=dbc.fetchonedb(sql,[id])
    if datalist:
        return datalist[0]

#--价格分类
def getpricecatename(dbc,id):
    sql="select name from price_category where id=%s"
    alist=dbc.fetchonedb(sql,[id])
    if alist:
        return alist[0]

#----最新供求
def getindexofferlist(kname='',pdt_type='',company_id='',limitcount=1):
    port = spconfig['port']
    cl = SphinxClient()
    cl.SetServer ( spconfig['serverid'], port )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl.SetSortMode( SPH_SORT_EXTENDED," refresh_time desc" )
    cl.SetLimits (0,limitcount,limitcount)
    if pdt_type:
        cl.SetFilter('pdt_kind',[int(pdt_type)])
    if company_id:
        cl.SetFilter('company_id',[int(company_id)])
    if kname:
        res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+kname,'offersearch_new,offersearch_new_vip')
    else:
        res = cl.Query ('','offersearch_new,offersearch_new_vip')
    listcount=0
    listall_offerlist=[]
    if res:
        if res.has_key('matches'):
            listcount=res['total_found']
            itemlist=res['matches']
            for match in itemlist:
                pid=match['id']
                murl='http://m.zz91.com/detail/?id='+str(pid)
                attrs=match['attrs']
                pdt_date=int_to_str(attrs['refresh_time'])
                title=subString(attrs['ptitle'],40)
                list={'id':pid,'murl':murl,'title':title,'gmt_time':pdt_date,'fulltitle':attrs['ptitle']}
                if limitcount==1:
                    list['count']=listcount
                    return list
                listall_offerlist.append(list)
    if limitcount==1:
        return ''
    else:
        return {'list':listall_offerlist,'count':listcount}

#报价列表 翻页
def getpricelist(dbc,keywords="",frompageCount=0,limitNum=1,category_id="",allnum=10000,assist_type_id=""):
    port = spconfig['port']
    cl = SphinxClient()
    cl.SetServer ( spconfig['serverid'], port )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    if (category_id):
        cl.SetFilter('type_id',category_id)
    if (assist_type_id):
        cl.SetFilter('assist_type_id',[assist_type_id])
    cl.SetSortMode( SPH_SORT_EXTENDED,"gmt_time desc" )
    cl.SetLimits (frompageCount,limitNum,allnum)
    if (keywords):
        res = cl.Query ('@(title,tags) '+keywords,'price')
    else:
        res = cl.Query ('','price')
    listall=[]
    listcount=0
    if res:
        if res.has_key('matches'):
            listcount=res['total_found']
            tagslist=res['matches']
            for match in tagslist:
                id=match['id']
                sql="select content,tags from price where id=%s and is_checked=1"
                alist = dbc.fetchonedb(sql,id)
                content=""
                tags=""
                if alist:
                    content=subString(filter_tags(alist[0]),50)
                    tags=alist[1]
                attrs=match['attrs']
                title=attrs['ptitle']
                gmt_time=attrs['gmt_time']
                list1={'title':title,'id':id,'gmt_time':gmt_time,'content':content,'tags':tags}
                if limitNum==1:
                    list1['count']=listcount
                    return list1
                listall.append(pricecontent)
    if limitNum==1:
        return ''
    else:
        return {'list':listall,'count':listcount}

if __name__=="__main__":
    getapp_order()