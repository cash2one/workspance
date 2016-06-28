#-*- coding:utf-8 -*-
from public import *
from sphinxapi import *
from time import ctime, sleep
from zz91db_ast import companydb
from zz91settings import SPHINXCONFIG
import memcache
dbc=companydb()
mc = memcache.Client(['cache.zz91server.com:11211'],debug=0)
def tags():
    serverid=SPHINXCONFIG['serverid']
    port=SPHINXCONFIG['port']
    cl = SphinxClient()
    cl.SetServer ( serverid, port )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl.SetSortMode( SPH_SORT_EXTENDED,"id desc" )
    #cl.SetFilter('check_status',[1])
    #cl.SetFilter('is_pause',[0])
    
    c2 = SphinxClient()
    c2.SetServer ( serverid, port )
    c2.SetMatchMode ( SPH_MATCH_BOOLEAN )
    c2.SetSortMode( SPH_SORT_EXTENDED,"id desc" )
    c2.SetFilter('check_status',[1])
    c2.SetFilter('is_pause',[0])
    
    ppsctagsid=mc.get("ppctagsid")
    if ppsctagsid==None:
        ppsctagsid=1473811
    #cl.SetFilterRange('id',1,ppsctagsid)
    print ppsctagsid
    cl.SetLimits (0,20000,20000)
    res = cl.Query ('','offersearch_ppc')
    if res:
        countall=res['total_found']
        print countall
        if res.has_key('matches'):
            tagslist=res['matches']
            for match in tagslist:
                id=match['id']
                sql="select tags from products where id=%s"
                resultlist=dbc.fetchonedb(sql,[id])
                if resultlist:
                    tags=resultlist[0]
                    if tags:
                        if ',' in tags:
                            arrtags=tags.split(',')
                            for l in arrtags:
                                onetags=l
                                if len(onetags)<=20:
                                    sqlc="select id from phone_tags where tags=%s"
                                    resultt=dbc.fetchonedb(sqlc,[onetags])
                                    if not resultt:
                                        c2.SetLimits (0,1,1)
                                        res2 = c2.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+onetags,'offersearch_ppc')
                                        if res2:
                                            listcount=res2['total_found']
                                            if listcount>0:
                                                sqlp="insert into phone_tags(tags,count) values(%s,%s)"
                                                dbc.updatetodb(sqlp,[onetags,listcount])
                                
                #print id
                                print onetags
                mc.set("ppctagsid",id,60*60*10)
tags()

        