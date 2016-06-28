#!/usr/bin/python
#coding:utf-8
import requests

url="https://sendcloud.sohu.com/webapi/mail.send.xml"

# 不同于登录SendCloud站点的帐号，您需要登录后台创建发信子帐号，使用子帐号和密码才可以进行邮件的发送。
params = {"api_user": "postmaster@asto.sendcloud.org", \
    "api_key" : "BygCUb6B",\
    "from" : "service@zz91.com", \
    "fromname" : "zz91", \
    "subject" : "主题", \
    "html": "正文" ,\
    "template_invoke_name":"zz91xunpan",\
    "substitution_vars":'{"to": ["kangxy@asto.com.cn"],"sub" : { "%company%" : ["约翰"], "%contact%" : ["kang"]} }'\
}

r = requests.post(url, data=params)
print r