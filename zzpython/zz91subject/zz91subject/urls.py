from django.conf.urls import *
from zz91subject import views
import settings
# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
	(r'^zt/addclick/$', 'zz91subject.views.addclick'),
	(r'^zt/$', 'zz91subject.views.default1'),
	(r'zhuantimore(?P<typeid>\w+).html$', 'zz91subject.views.zhuantimore'),
	(r'zhuantimore(?P<typeid>\w+)-(?P<page>\w+).html$', 'zz91subject.views.zhuantimore'),
    # Example:
    # (r'^subject/', include('zz91subject.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # (r'^admin/', include(admin.site.urls)),
	(r'zt/baomingsave_get/$', 'zz91subject.views.baomingsave_get'),
	(r'zt/baomingsave/$', 'zz91subject.views.baomingsave'),
	(r'zt/baomingsave.html$', 'zz91subject.views.baomingsave'),
	(r'zt/miaosha_save/$', 'zz91subject.views.miaosha_save'),
	(r'zt/baoming_save/$', 'zz91subject.views.baoming_save'),
	(r'zt/huangye_save/$', 'zz91subject.views.huangye_save'),
	(r'^(?P<subjectname>\w+)/(?P<pagename>\w+)/$', 'zz91subject.views.subject'),
	(r'^(?P<subjectname>\w+)/(?P<pagename>\w+)$', 'zz91subject.views.subject'),
	(r'^(?P<subjectname>\w+)/(?P<pagename>\w+).html$', 'zz91subject.views.subject'),
    
    (r'^zt/(?P<subjectname>\w+)/(?P<pagename>\w+)/$', 'zz91subject.views.subject'),
    (r'^zt/(?P<subjectname>\w+)/(?P<pagename>\w+)$', 'zz91subject.views.subject'),
    (r'^zt/(?P<subjectname>\w+)/(?P<pagename>\w+).html$', 'zz91subject.views.subject'),
    
    (r'^zt/(?!admin)(?P<path>.*)$','django.views.static.serve',{'document_root':settings.STATIC_ROOT}),
	(r'^(?!admin)(?P<path>.*)$','django.views.static.serve',{'document_root':settings.STATIC_ROOT}),
	
)
