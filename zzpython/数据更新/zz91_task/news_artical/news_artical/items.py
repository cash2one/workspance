# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy
from scrapy.item import Item, Field


class xinxi(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

class gangmao(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

class gangchang(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

class benwang(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

class zoushi(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

class zongheng(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

class gedi(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass
    
class gangwei(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass
#钢厂动态
class gangchangdongtai(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#中国钢材价格网
class gangcaijiage(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass 
#回收商
class huishoushang(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass
#回收商塑料
class huishoushang_suliao(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#环球塑化pvc123
class pvc123(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#环球塑化pvc123——科技
class pvc123_keji(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#废旧网
class feijiuwang(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass


#慧聪咨讯
class huicong(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass


#钢联咨讯
class ganglian(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#我爱钢铁
class woaigangtie(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#铜咨询
class tongzixun(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#富宝铜铝
class fubaotonglv(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#富宝铅锌
class fubaoqianxin(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#富宝铁镍
class fubaotienie(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#富宝贵金属
class fubaoguijinshu(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#中国铝业1
class zgly1(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#中国铝业1
class zgly2(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#chanjing新闻
class chanjingxinwen(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#回收商其他
class huishoushang_qita(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#废旧网其他
class feijiuwang_qita(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass

#废旧网废纸
class feijiuwang_feizhi(scrapy.Item):
    title=Field()
    artical=Field()
    pubdate=Field()
    pass















