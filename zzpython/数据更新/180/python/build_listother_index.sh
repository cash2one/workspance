#!/bin/sh
#/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate news >> /usr/apps/coreseek/var/log/build_list_news.log
#/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate newstags >> /usr/apps/coreseek/var/log/build_list_newstags.log
python /usr/apps/python/ppc/auto_updateproducts.py
python /usr/apps/python/huzhu/update.py
