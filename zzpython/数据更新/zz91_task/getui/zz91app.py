__author__ = 'wei'
# -*- coding: utf-8 -*-
#更新时间为2013年11月07日
#增加了IOS的离线消息推送,IOS不支持IGtNotyPopLoadTemplate模板
#更新时间为2013年02月24日
#1.增加了通知弹框下载模板 
#2.统一了toapp的接口时间，单位为毫秒 
#3.允许ios用户离线状态下的apn转发message字段为空，
#4.增加了查询用户状态接口，
#5.任务停止功能，
#6.增加ToList接口每个用户返回用户状态的功能
#
#

from igt_push import *
from igetui.template import *
from igetui.template.igt_base_template import *
from igetui.template.igt_transmission_template import *
from igetui.template.igt_link_template import *
from igetui.template.igt_notification_template import *
from igetui.template.igt_notypopload_template import *
from igetui.igt_message import *
from igetui.igt_target import *
from igetui.template import *

#zz91
from public import *
from sphinxapi import *
from time import ctime, sleep
from zz91db_ast import companydb
from zz91settings import SPHINXCONFIG
import memcache,datetime
dbc=companydb()
mc = memcache.Client(['192.168.110.7:11211'],debug=0)

#--获得公司id
def getcompanyid(account):
    sql="select company_id from  company_account where account=%s"
    result=dbc.fetchonedb(sql,[account])
    if result:
        return result[0]
#----系统推送消息列表
def getmessagelist(company_id=0):
    sql="select id,title,url,content,company_id,gmt_created from app_message where company_id in (%s,0) and DATEDIFF(CURDATE(),gmt_created)<=10 and not exists(select id from app_message_view where app_message.id=mid and is_push=1 and company_id=%s) and title not in ('感谢您的安装') order by id desc"
    result=dbc.fetchonedb(sql,[company_id,company_id])
    list=None
    if result:
        id=result[0]
        title=result[1]
        url=result[2]
        content=result[3]
        content=content.replace('"','#')
        content=content.replace(',','，')
        list={'id':id,'title':title,'url':url,'content':content}
    return list
def getmessagelistall(company_id=0):
    sql="select id,title,url,content,company_id,gmt_created from app_message where "
    if company_id==0:
        sql+=" company_id=0 and is_push=0 order by id desc"
        result=dbc.fetchonedb(sql)
        if result:
            id=result[0]
            title=result[1]
            url=result[2]
            content=result[3]
            content=content.replace('"','#')
            content=content.replace(',','，')
            list={'id':id,'title':title,'url':url,'content':content}
            return list
        return None
    sql+=" id>(select maxid from update_log where id=12) and not exists(select id from app_message_view where app_message.id=mid and is_push=1) and title not in ('感谢您的安装') order by id desc"
    result=dbc.fetchalldb(sql)
    listall=[]
    if result:
        for listd in result:
            id=listd[0]
            title=listd[1]
            url=listd[2]
            content=listd[3]
            company_id=listd[4]
            content=content.replace('"','#')
            content=content.replace(',','，')
            list={'id':id,'title':title,'url':url,'content':content,'company_id':company_id}
            listall.append(list)
    return listall

#toList接口每个用户返回用户状态开关,true：打开 false：关闭
os.environ['needDetails'] = 'false'

APPKEY = "BJJCdv8xja9oHTjJNaxYT5"
APPID = "77j5XCkks07Fcdj35Utlt6"
MASTERSECRET = "yyq1RkPqm86iMcs0RsTXK"
CID = "请输入您的CID"
HOST = 'http://sdk.open.api.igexin.com/apiex.htm'



def pushMessageToSingle(content):
    push = IGeTui(HOST, APPKEY, MASTERSECRET)
    #消息模版：
    #1.TransmissionTemplate:透传功能模板
    #2.LinkTemplate:通知打开链接功能模板
    #3.NotificationTemplate：通知透传功能模板
    #4.NotyPopLoadTemplate：通知弹框下载功能模板

    #template = NotificationTemplateDemo()
    #template = LinkTemplateDemo()
    template = TransmissionTemplateDemo(content)
    #template = NotyPopLoadTemplateDemo()
    
    message = IGtSingleMessage()
    message.isOffline = True
    message.offlineExpireTime = 1000 * 3600 * 12
    message.data = template

    target = Target()
    target.appId = APPID
    target.clientId = CID

    ret = push.pushMessageToSingle(message, target)
    print ret



#透传模板动作内容
def TransmissionTemplateDemo(content):
    template = TransmissionTemplate()
    template.transmissionType = 1
    template.appId = APPID
    template.appKey = APPKEY
    template.transmissionContent = content #
    #iOS 推送需要的PushInfo字段 前三项必填，后四项可以填空字符串
    #template.setPushInfo(actionLocKey, badge, message, sound, payload, locKey, locArgs, launchImage)
    #template.setPushInfo("",2,"","","","","","");
    return template

    #
def getaccount(company_id):
    sqlc="select account from company_account where company_id=%s"
    userlist=dbc.fetchonedb(sqlc,[company_id])
    if userlist:
        return userlist[0]

def persontuisong():
    alllist=getmessagelistall(company_id=1)
    for list in alllist:
        company_id=list['company_id']
        account=getaccount(company_id)
        if account:
            sql="select open_id,target_account from oauth_access where closeflag=0 and target_account=%s and open_type='app.zz91.com' and appsystem='Android' order by id desc"
            userlist=dbc.fetchonedb(sql,[account])
            if userlist:
                CID=userlist[0]
                id=list['id']
                print CID
                gmt_created=datetime.datetime.now()
                content='{title:"'+list['title']+'",content:"'+list['content']+'",payload:\''+list['url']+'\'}'
                pushMessageToSingle(content)
                sql="select id from app_message_view where mid=%s and company_id=%s"
                result=dbc.fetchonedb(sql,[id,company_id])
                if result:
                    sql="update app_message_view set is_push=1 where id=%s"
                    dbc.updatetodb(sql,[result[0]])
                else:
                    sql="insert into app_message_view(mid,company_id,is_push,gmt_created) values(%s,%s,%s,%s)"
                    dbc.updatetodb(sql,[id,company_id,1,gmt_created])
                sqlc="update update_log set maxid=%s where id=12"
                dbc.updatetodb(sqlc,[id])
def systuisong():
    alllist=getmessagelistall(company_id=0)
    if alllist:
        sql="select open_id,target_account from oauth_access where closeflag=0 and open_type='app.zz91.com' and appsystem='Android' order by id desc"
        userlist=dbc.fetchalldb(sql)
        if userlist:
            for list in userlist:
                CID=list[0]
                company_id=getcompanyid(list[1])
                if company_id:
                    list=alllist
                    if list:
                        id=list['id']
                        print CID
                        gmt_created=datetime.datetime.now()
                        content='{title:"'+list['title']+'",content:"'+list['content']+'",payload:\''+list['url']+'\'}'
                        pushMessageToSingle(content)
                        sql="select id from app_message_view where mid=%s and company_id=%s"
                        result=dbc.fetchonedb(sql,[id,company_id])
                        if result:
                            sql="update app_message_view set is_push=1 where id=%s"
                            dbc.updatetodb(sql,[result[0]])
                        else:
                            sql="insert into app_message_view(mid,company_id,is_push,gmt_created) values(%s,%s,%s,%s)"
                            dbc.updatetodb(sql,[id,company_id,1,gmt_created])
                        sql="update app_message set is_push=1 where id=%s"
                        dbc.updatetodb(sql,[id])
persontuisong()
systuisong()
