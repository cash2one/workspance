#!/bin/sh
#���籦�ͻ������Զ�ˢ��
#python /usr/apps/python/ppc/auto_updateproducts.py

#����3�����¹���
python /usr/apps/python/offer/offer_index.py >> /usr/apps/log/offer_index.log
python /usr/apps/python/question/questioncount.py >> /usr/apps/log/offer_index.log

#���¸��µ���ͨ��Ա�Ĺ���
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_offersearch --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge offersearch_new delta_offersearch --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#ˢ��ʱ��3���⣬�������޸ĵȸ��µĹ���
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_offersearch_vip --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge offersearch_new delta_offersearch_vip --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#3���ڸ߻ṩ��
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate offersearch_new_vip >> /usr/apps/log/buildindex.log

#ѯ����Ϣ
#/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_question --rotate
#/usr/local/web/coreseek/bin/indexer --merge question delta_question --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate

#����
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_huzhu --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge huzhu delta_huzhu --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#����
/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_price --rotate >> /usr/apps/log/buildindex.log
/usr/local/web/coreseek/bin/indexer --merge price delta_price --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate >> /usr/apps/log/buildindex.log

#����վ��̨��Ѷ�Զ����µ��µ����ݱ�
sh /usr/apps/python/oldnewsupdate.sh >> /usr/apps/log/buildindex.log
