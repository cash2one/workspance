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
import memcache
dbc=companydb()
mc = memcache.Client(['192.168.110.114:11211'],debug=0)

#toList接口每个用户返回用户状态开关,true：打开 false：关闭
os.environ['needDetails'] = 'false'

APPKEY = "BJJCdv8xja9oHTjJNaxYT5"
APPID = "77j5XCkks07Fcdj35Utlt6"
MASTERSECRET = "yyq1RkPqm86iMcs0RsTXK"
CID = "请输入您的CID"
HOST = 'http://sdk.open.api.igexin.com/apiex.htm'



def pushMessageToSingle():
    push = IGeTui(HOST, APPKEY, MASTERSECRET)
    #消息模版：
    #1.TransmissionTemplate:透传功能模板
    #2.LinkTemplate:通知打开链接功能模板
    #3.NotificationTemplate：通知透传功能模板
    #4.NotyPopLoadTemplate：通知弹框下载功能模板

    template = NotificationTemplateDemo()
    #template = LinkTemplateDemo()
    #template = TransmissionTemplateDemo()
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


def pushMessageToList():
    push = IGeTui(HOST, APPKEY, MASTERSECRET)

    #消息模版： 
    #1.TransmissionTemplate:透传功能模板  
    #2.LinkTemplate:通知打开链接功能模板  
    #3.NotificationTemplate：通知透传功能模板  
    #4.NotyPopLoadTemplate：通知弹框下载功能模板

    template = NotificationTemplateDemo()
    #template = LinkTemplateDemo()
    #template = TransmissionTemplateDemo()
    #template = NotyPopLoadTemplateDemo()

    message = IGtListMessage()
    message.data = template
    message.isOffline = True
    message.offlineExpireTime = 1000 * 3600 * 12

    target1 = Target()
    target1.appId = APPID
    target1.clientId = CID

    #target2 = Target()
    #target2.appId = APPID
    #target2.clientId = CID

    targets = [target1]
    contentId = push.getContentId(message)
    ret = push.pushMessageToList(contentId, targets)
    print ret


def pushMessageToApp():
    push = IGeTui(HOST, APPKEY, MASTERSECRET)

    #消息模版： 
    #1.TransmissionTemplate:透传功能模板  
    #2.LinkTemplate:通知打开链接功能模板  
    #3.NotificationTemplate：通知透传功能模板  
    #4.NotyPopLoadTemplate：通知弹框下载功能模板

    #template = NotificationTemplateDemo()
    #template = LinkTemplateDemo()
    template = TransmissionTemplateDemo()
    #template = NotyPopLoadTemplateDemo()

    message = IGtAppMessage()
    message.data = template
    message.isOffline = True
    message.offlineExpireTime = 1000 * 3600 * 12
    message.appIdList.extend([APPID])
    #message.phoneTypeList.extend(["ANDROID", "IOS"])
    #message.provinceList.extend(["浙江", "上海"])
    #message.tagList.extend(["开心"])

    ret = push.pushMessageToApp(message)
    print ret

#通知透传模板动作内容
def NotificationTemplateDemo():
    template = NotificationTemplate()
    template.appId = APPID
    template.appKey = APPKEY
    template.transmissionType = 0
    template.transmissionContent = u"请填入透传内容"
    template.title = u"请填入通知标题"
    template.text = u"请填入通知内容"
    template.logo = "icon.png"
    template.logoURL = ""
    template.isRing = True
    template.isVibrate = True
    template.isClearable = True
    #iOS 推送需要的PushInfo字段 前三项必填，后四项可以填空字符串
    #template.setPushInfo(actionLocKey, badge, message, sound, payload, locKey, locArgs, launchImage)
    #template.setPushInfo("open",4,"message","","","","","");
    return template

#通知链接模板动作内容
def LinkTemplateDemo():
    template = LinkTemplate()
    template.appId = APPID
    template.appKey = APPKEY
    template.title = u"中国再生资源大黄页招商"
    template.text = u"2015中国再生资源大黄页开始招商了，0571-56611688"
    template.logo = "icon.png"
    template.url = "http://www.zz91.com/zt/2015yellowpage/index.html"
    template.transmissionType = 1
    template.transmissionContent = ''
    template.isRing = True
    template.isVibrate = True
    template.isClearable = True
    #iOS 推送需要的PushInfo字段 前三项必填，后四项可以填空字符串
    #template.setPushInfo(actionLocKey, badge, message, sound, payload, locKey, locArgs, launchImage)
    #template.setPushInfo("open",4,"message","test1.wav","","","","");
    return template

#透传模板动作内容
def TransmissionTemplateDemo():
    template = TransmissionTemplate()
    template.transmissionType = 1
    template.appId = APPID
    template.appKey = APPKEY
    template.transmissionContent = '{title:"Hello H5+ Test",content:"test content",payload:{id:"1234567890"}}'
    #iOS 推送需要的PushInfo字段 前三项必填，后四项可以填空字符串
    #template.setPushInfo(actionLocKey, badge, message, sound, payload, locKey, locArgs, launchImage)
    #template.setPushInfo("",2,"","","","","","");
    return template

#通知弹框下载模板动作内容
def NotyPopLoadTemplateDemo():
    template = NotyPopLoadTemplate()
    template.appId = APPID
    template.appKey = APPKEY
    template.notyIcon = "icon.png"
    template.logoUrl = ""
    template.notyTitle = u"通知弹框下载功能标题"
    template.notyContent= u"通知弹框下载功能内容"
    template.isRing = True
    template.isVibrate = True
    template.isClearable = True
	
    template.popTitle = u"弹框标题"
    template.popContent = u"弹框内容"
    template.popImage = ""
    template.popButton1 = u"下载"
    template.popButton2 = u"取消"

    template.loadIcon = "file://icon.png"
    template.loadTitle = u"下载内容"
    template.loadUrl = "http://gdown.baidu.com/data/wisegame/c95836e06c224f51/weixinxinqing_5.apk"
    template.isAutoInstall = True
    template.isActive = False
    return template

#获取用户状态
def getUserStatus():
    push = IGeTui(HOST, APPKEY, MASTERSECRET)
    print push.getClientIdStatus(APPID, CID)

#任务停止功能
def stopTask():
    push = IGeTui(HOST, APPKEY, MASTERSECRET)
    print push.stop("OSA-0226_50RYYPFmos9eQEHZrkAf27");	   
    
    #
    #服务端支持三个接口推送
    #1.PushMessageToSingle接口：支持对单个用户进行推送
    #2.PushMessageToList接口：支持对多个用户进行推送，建议为50个用户
    #3.pushMessageToApp接口：对单个应用下的所有用户进行推送，可根据省份，标签，机型过滤推送
    #
sql="select open_id,target_account from oauth_access where  closeflag=0 and target_account='xufangyue' and open_type='app.zz91.com'"
userlist=dbc.fetchalldb(sql)
if userlist:
    for list in userlist:
        CID=list[0]
        pushMessageToSingle()
        print CID
#pushMessageToList()
#pushMessageToApp()

#获取用户状态接口
#getUserStatus()

#任务停止功能接口
#stopTask()
