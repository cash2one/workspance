/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate pricelist >> /mnt/coreseek/var/log/build_index.log
#/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate pricelisttoday >> /mnt/coreseek/var/log/build_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate company_price >> /mnt/coreseek/var/log/build_index.log

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate price >> /mnt/coreseek/var/log/build_index.log

/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate news >> /mnt/coreseek/var/log/build_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate sex >> /mnt/coreseek/var/log/build_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate newstags >> /mnt/coreseek/var/log/build_index.log
/usr/local/coreseek/bin/indexer --config /mnt/coreseek/etc/mysql1_index2.conf --rotate price_data >> /mnt/coreseek/var/log/build_index.log
