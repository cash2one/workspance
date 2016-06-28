#!/bin/sh
#python /usr/apps/python/ppc/auto_updateproducts.py

#python /usr/apps/python/offer/offer_index.py >> /usr/apps/log/offer_index.log
#python /usr/apps/python/question/questioncount.py >> /usr/apps/log/offer_index.log

#/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf delta_offersearch --rotate >> /usr/apps/log/buildindex.log
#/usr/local/coreseek/bin/indexer --merge offersearch_new delta_offersearch --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate >> /usr/apps/log/buildindex.log

#/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf delta_offersearch_vip --rotate >> /usr/apps/log/buildindex.log
#/usr/local/coreseek/bin/indexer --merge offersearch_new delta_offersearch_vip --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate >> /usr/apps/log/buildindex.log

#/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate offersearch_new_vip >> /usr/apps/log/buildindex.log

#/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf delta_question --rotate
#/usr/local/coreseek/bin/indexer --merge question delta_question --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate

#/usr/local/coreseek/bin/indexer --config /usr/apps/coreseek/etc/mysql_index150.conf delta_huzhu --rotate >> /usr/apps/log/buildindex.log
#/usr/local/coreseek/bin/indexer --merge huzhu delta_huzhu --config /usr/apps/coreseek/etc/mysql_index150.conf --rotate >> /usr/apps/log/buildindex.log

#python /usr/apps/python/price/outtable.py

python /usr/apps/python/coreseek/indexer.py
python /usr/apps/python/getui/zz91app.py

#sh /usr/apps/python/oldnewsupdate.sh >> /usr/apps/log/buildindex.log
