#!/bin/sh
#来电宝客户供求自动刷新
#python /usr/apps/python/ppc/auto_updateproducts.py

#更新3天最新供求
python /usr/apps/python/offer/offer_index.py >> /usr/apps/log/offer_index.log
python /usr/apps/python/question/questioncount.py >> /usr/apps/log/offer_index.log

#最新更新的普通会员的供求
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_offersearch --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge offersearch_new delta_offersearch --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#刷新时间3天外，但做了修改等更新的供求
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_offersearch_vip --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge offersearch_new delta_offersearch_vip --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#3天内高会供求
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate offersearch_new_vip >> /usr/apps/log/buildindex.log

#询盘信息
#/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_question --rotate
#/usr/local/web/coreseek/bin/indexer --merge question delta_question --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate

#互助
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_huzhu --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge huzhu delta_huzhu --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#报价
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_price --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge price delta_price --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#老网站后台资讯自动更新到新的数据表
sh /usr/apps/python/oldnewsupdate.sh >> /usr/apps/log/buildindex.log
