#-*- coding:utf-8 -*- 
#单条新闻检查
from conn import savedb,get_new
from getnews import get_urls,get_content,hand_content,get_url_content

url='http://futures.jrj.com.cn/2014/01/20093416534785.shtml'
html=get_url_content(url)
re_content=r'<div class="textmain tmf14 jrj-clear" >(.*?)</div>'
content=get_content(re_content,html)
re_hand=[]
re_hand.append(r'<P><SPAN style="COLOR: #800000"><STRONG>.*?</FONT>')
re_hand.append(r'</STRONG><A href=.*?</A></SPAN>')
re_hand.append(r'<P><STRONG>.*?</STRONG>')           
for hand in re_hand:
    content=hand_content(hand,content)
title='中粮期货：1月20日邵徽翔团队交易提示'
typeid=0
print content