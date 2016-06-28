#!/bin/sh
cd /usr/apps/python/weixin/
python update.py
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate huzhu >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate offersearch_new >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate category_products >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate company >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate price >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate company_price >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate tagslist >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate offersearch_ppc >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate question >> /usr/apps/coreseek/var/log/build_main_index.log

