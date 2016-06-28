#-*- coding:utf-8 -*-
#----抓取和讯网汇率
from public import *
from sphinxapi import *
from zz91db_ast import companydb
from zz91db_tags import zztagsdb
from pinyin import chinese_abstract
#from zz91db_130 import otherdb
#from zz91db_test import testdb
#from zz91class import pinyin
dbc=companydb()
dbt=zztagsdb()
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

def tagslist():
    sql="select tags,id from tags where closeflag=0"
    resultlist=dbt.fetchalldb(sql)
    for result in resultlist:
        tag_id=result[1]
        serverid=SPHINXCONFIG['serverid']
        port=SPHINXCONFIG['port']
        cl = SphinxClient()
        cl.SetServer ( serverid, port )
        cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
        cl.SetLimits (0,1,1)
        res = cl.Query ('@(tags) '+result[0],'offersearch_new,offersearch_new_vip')
        if res:
            listcount=res['total_found']
            print listcount
            #sql2='update tags set closeflag=1 where id=%s'
            #dbt.updatetodb(sql2,[tag_id])
tagslist()
