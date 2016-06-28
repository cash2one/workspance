#from django.conf.urls import patterns, include, url
from django.conf.urls import *
import settings
#----sex
urlpatterns = patterns('zjfriendweb.views',
    (r'^$', 'index'),
    (r'^list(?P<typeid>\d+).html$', 'index'),
    (r'^(?P<typedir>\w+)/$', 'index'),
    (r'^(?P<typedir>\w+)/(?P<typedir1>\w+)/$', 'index'),
    (r'^(?P<typedir>\w+)/(?P<typedir1>\w+)/(?P<typedir2>\w+)/$', 'index'),
    
    (r'^(?P<typedir>\w+)$', 'index'),
    (r'^(?P<typedir>\w+)/(?P<typedir1>\w+)$', 'index'),
    (r'^(?P<typedir>\w+)/(?P<typedir1>\w+)/(?P<typedir2>\w+)$', 'index'),
    
    (r'^(?P<typedir>\w+)/(?P<id>\d+).html$', 'detail'),
    (r'^(?P<typedir>\w+)/(?P<typedir1>\w+)/(?P<id>\d+).html$', 'detail'),
    (r'^(?P<typedir>\w+)/(?P<typedir1>\w+)/(?P<typedir2>\w+)/(?P<id>\d+).html$', 'detail'),
    (r'^detail/(?P<id>\d+).html$', 'detail'),
)

urlpatterns += patterns('',
    (r'^(?!admin)(?P<path>.*)$','django.views.static.serve',{'document_root':settings.STATIC_ROOT}),
)
