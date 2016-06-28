

/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf delta_question --rotate
/usr/local/web/coreseek/bin/indexer --merge question delta_question --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate
