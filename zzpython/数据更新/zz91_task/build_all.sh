#!/bin/sh
cd /mnt/python/zz91_task/weixin/
python update.py
sh /mnt/python/get_zz91_price/restart.sh
python /mnt/python/zz91_task/price/updatetoday.py
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate huzhu >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate offersearch_new >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate category_products >> /mnt/coreseek/var/log/build_main_index.log

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate company >> /mnt/coreseek/var/log/build_main_index.log


/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate tagslist >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate offersearch_ppc >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate question >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate datalogin >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate daohangkeywords >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate huzhureply >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate keywordsearch >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate offersearch_new_vip >> /mnt/coreseek/var/log/build_main_index.log


/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate phone_log >> /mnt/coreseek/var/log/build_main_index.log

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate bbs_post_tags >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate market >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate company_market >> /mnt/coreseek/var/log/build_main_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql_index.conf --rotate product_market >> /mnt/coreseek/var/log/build_main_index.log

