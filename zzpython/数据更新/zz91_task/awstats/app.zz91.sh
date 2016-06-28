python /usr/apps/python/log/getlog_app.py
mv  /var/log/nginx/mobileapp.access.log /var/log/nginx/app.zz91.access_`date +%Y%m%d`.log
[ ! -f /var/run/nginx.pid ] || kill -USR1 `cat /var/run/nginx.pid`
/usr/tools/awstats/wwwroot/cgi-bin/awstats.pl -update -config=app.zz91.com
/usr/tools/awstats/tools/awstats_buildstaticpages.pl -update  \
-config=app.zz91.com -lang=cn -dir=/usr/data/awstats  \
-awstatsprog=/usr/tools/awstats/wwwroot/cgi-bin/awstats.pl
rm /var/log/nginx/app.zz91.access_`date +%Y%m%d`.log