python trade_supply1.py

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate supplyPreTreeDay

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf delta_huanbao_trade_buy --rotate
/usr/local/coreseek/bin/indexer --merge huanbao_trade_buy delta_huanbao_trade_buy --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate  --merge-dst-range deleted 0 0
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate buyPreTreeDay


/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate compNewsForArticles

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate mblogInfoForName

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate mblogForContent

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate followInfoForName

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index_huanbao.conf --rotate huanbao_new_exhibit

