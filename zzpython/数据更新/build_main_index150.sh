#!/bin/sh
cd /usr/apps/python/weixin/
python update.py
sh /usr/apps/python/get_zz91_price/restart.sh
python /usr/apps/python/price/updatetoday.py
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate huzhu >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate offersearch_new >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate category_products >> /usr/apps/coreseek/var/log/build_main_index.log

/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate company >> /usr/apps/coreseek/var/log/build_main_index.log


/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate tagslist >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate offersearch_ppc >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate question >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate datalogin >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate daohangkeywords >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate huzhureply >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate keywordsearch >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate offersearch_new_vip >> /usr/apps/coreseek/var/log/build_main_index.log


/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate phone_log >> /usr/apps/coreseek/var/log/build_main_index.log

/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate bbs_post_tags >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate market >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate company_market >> /usr/apps/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate product_market >> /usr/apps/coreseek/var/log/build_main_index.log

#/var/pythoncode/kill.sh
#/usr/local/coreseek/bin/searchd --config /usr/apps/coreseek/etc/mysql_index150_1.conf
#/usr/local/coreseek/bin/searchd --config /usr/apps/coreseek/etc/mysql_index150.conf

