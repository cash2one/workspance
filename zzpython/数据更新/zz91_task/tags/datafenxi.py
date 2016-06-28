#-*- coding:utf-8 -*-
#----抓取和讯网汇率
from public import *
from sphinxapi import *
from time import ctime, sleep
from zz91db_ast import companydb
from zz91db_tags import zztagsdb
from pinyin import chinese_abstract
from zz91settings import SPHINXCONFIG
import memcache
#from zz91db_130 import otherdb
#from zz91db_test import testdb
#from zz91class import pinyin
dbc=companydb()
dbt=zztagsdb()
mc = memcache.Client(['cache.zz91server.com:11211'],debug=0)
#pinyin=pinyin()

def getfenxi():
    sql='select id,tags,category_products_main_code from products where check_status=1 and id>=1400000 and id<1400000 order by id'
    sql1='select id from tags where tags=%s'
    sql2='update tags set closeflag=1 where id=%s'
    sql3='insert into tags_category(category_products_main_code,tags_id,pingyin) values(%s,%s,%s)'
    sql4='select id,category_products_main_code from tags_category where tags_id=%s'
    sql5='update tags_category set category_products_main_code=%s where id=%s'
    resultlist=dbc.fetchalldb(sql)
    for result in resultlist:
        tags=result[1]
        tags=''.join(tags.split())
        if tags:
            id=result[0]
            cmaincode=result[2]
            if ',' in tags:
                tagslist=tags.split(',')
                for tg in tagslist:
                    if tg:
                        result1=dbt.fetchonedb(sql1,[tg])
                        if result1:
                            tag_id=result1[0]
                            print id
                            print tag_id
                            print tg
                            print cmaincode
                            tg_pinyin=chinese_abstract(tg)
                            print tg_pinyin
                            print '---'
                            dbt.updatetodb(sql2,[tag_id])
                            result4=dbt.fetchonedb(sql4,[tag_id])
                            if result4:
                                id4=result4[0]
                                cmaincode4=result4[1]
                                if cmaincode and not cmaincode4:
                                    dbt.updatetodb(sql5,[cmaincode,id4])
                            else:
                                dbt.updatetodb(sql3,[cmaincode,tag_id,tg_pinyin])
#                            return
            else:
                tg=tags
                result1=dbt.fetchonedb(sql1,[tg])
                if result1:
                    tag_id=result1[0]
                    print id
                    print tag_id
                    print tg
                    print cmaincode
                    tg_pinyin=chinese_abstract(tg)
                    print tg_pinyin
                    print '---'
                    dbt.updatetodb(sql2,[tag_id])
                    result4=dbt.fetchonedb(sql4,[tag_id])
                    if result4:
                        id4=result4[0]
                        cmaincode4=result4[1]
                        if cmaincode and not cmaincode4:
                            dbt.updatetodb(sql5,[cmaincode,id4])
                    else:
                        dbt.updatetodb(sql3,[cmaincode,tag_id,tg_pinyin])
#                    return
#关闭标签
def tagslist():
    serverid=SPHINXCONFIG['serverid']
    port=SPHINXCONFIG['port']
    cl = SphinxClient()
    cl.SetServer ( serverid, port )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl.SetFilter('check_status',[1])
    cl.SetFilter('is_pause',[0])
    utagsid=mc.get("tagsupdate")
    print utagsid
    sql="select tags,id from tags where id<="+str(utagsid)+" and closeflag=1 order by id desc"
    resultlist=dbt.fetchalldb(sql)
    for result in resultlist:
        tag_id=result[1]
        tags=result[0]
        mc.set("tagsupdate",tag_id,60*60*10)
        
        cl.SetLimits (0,1,1)
        res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+tags,'offersearch_new,offersearch_new_vip')
        if res:
            listcount=res['total_found']
            lentags=len(tags)
            if listcount>0:
                sql2='update tags set closeflag=0 where id=%s'
                dbt.updatetodb(sql2,[tag_id])
            else:
                sql2='update tags set closeflag=1 where id=%s'
                dbt.updatetodb(sql2,[tag_id])
            print tag_id
            #sleep(1)
def utags():
    utagsid=mc.get("tagsupdate1")
    utagsid=153703
    sql="select tags_id,id from tags_category where id<="+str(utagsid)+" order by id desc"
    resultlist=dbt.fetchalldb(sql) 
    for result in resultlist:
         tag_id=result[0]
         id=result[1]
         sql2='update tags set closeflag=0 where id=%s'
         dbt.updatetodb(sql2,[tag_id])
         print id
         mc.set("tagsupdate1",id,60)
#过期供求隐藏
def updateprohide():
    serverid=SPHINXCONFIG['serverid']
    port=SPHINXCONFIG['port']
    print serverid
    #cl = SphinxClient()
    #cl.SetServer ( serverid, port )
    list={}
    i=0
    sql="select product_id from products_hide order by id asc limit 0,100"
    resultlist=dbc.fetchalldb(sql) 
    for result in resultlist:
        cl = SphinxClient()
        cl.SetServer ( serverid, port )
        product_id=result[0]
        print product_id
        #list[int(product_id)]=[1,1]
        res = cl.UpdateAttributes ('offersearch_new', ['is_del','is_pause'], {int(product_id):[1,1]})
    #print list
    #res = cl.UpdateAttributes ('offersearch_new', ['is_del','is_pause'],list )
updateprohide()
