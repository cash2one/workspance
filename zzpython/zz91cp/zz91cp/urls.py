from django.conf.urls import patterns, include, url
#from zz91cp import views
import settings
# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
					
	(r'^index.html$', 'zz91cp.views.index'),	#微门户首页			
	(r'^cp/$', 'zz91cp.views.default'),
	(r'^cp/(?P<pingyin>\w+)$', 'zz91cp.views.cp'),
	(r'^cp/(?P<pingyin>\w+)/$', 'zz91cp.views.cp'),
	(r'^cp/(?P<pingyin>\w+)/price.html$', 'zz91cp.views.price'),
	(r'^cp/(?P<pingyin>\w+)/pricemore-(?P<page>\d+).html$', 'zz91cp.views.pricemore'),
	(r'^cp/(?P<pingyin>\w+)/company.html$', 'zz91cp.views.company'),
	(r'^cp/(?P<pingyin>\w+)/companymore-(?P<page>\d+).html$', 'zz91cp.views.companymore'),
	(r'^cp/(?P<pingyin>\w+)/trade.html$', 'zz91cp.views.trade'),
	(r'^cp/(?P<pingyin>\w+)/trademore-(?P<page>\d+).html$', 'zz91cp.views.trademore'),
	(r'^cp/(?P<pingyin>\w+)/picture.html$', 'zz91cp.views.picture'),
	(r'^cp/(?P<pingyin>\w+)/picturemore-(?P<page>\d+).html$', 'zz91cp.views.picturemore'),
	(r'outzstcomplist/a/$', 'zz91cp.views.outzstcomplist'),
	
	#老站新闻
	(r'^news/news(?P<newsid>\d+).html$', 'zz91cp.news.newsdetail'),
	(r'^news/bbs(?P<newsid>\d+).html$', 'zz91cp.news.bbsdetail'),
	(r'^news/guanzhu(?P<newsid>\d+).html$', 'zz91cp.news.guanzhudetail'),
	(r'^news/newslist-(?P<keywords>\w+)-(?P<page>\d+).html$', 'zz91cp.news.newslist'),
	(r'^news/newssearchfirst.html$', 'zz91cp.news.newssearchfirst'),
	(r'^news/newsindex.html$', 'zz91cp.news.newsindex'),
	(r'^news/$', 'zz91cp.news.newsindex'),
	
	
	
	
	#优质客户推荐
	(r'^carveout/$', 'zz91cp.views.carveout'),
	(r'^carveout/plastic.html$', 'zz91cp.views.carveout'),
	(r'^carveout/c-(?P<keywords>\w+)-(?P<page>\d+).html$', 'zz91cp.views.carveoutmore'),
	#微信开春推广
	(r'^weixin2014/$', 'zz91cp.views.weixin2014'),
	(r'^cp/pricetable.html$', 'zz91cp.views.pricetable'),
	
	
	#普通客户推荐
	(r'^common/$', 'zz91cp.views.commoncustomer'),
	(r'^common/plastic.html$', 'zz91cp.views.commoncustomer'),
	(r'^common/c-(?P<keywords>\w+)-(?P<page>\d+).html$', 'zz91cp.views.commoncustomermore'),
	(r'^(?!admin)(?P<path>.*)$','django.views.static.serve',{'document_root':settings.STATIC_ROOT}),
)

handler404 = 'zz91cp.views.viewer_404'
handler500 = 'zz91cp.views.viewer_500'
