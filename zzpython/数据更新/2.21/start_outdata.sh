#!/bin/sh
cd /usr/apps/python
python /usr/apps/python/start.py
cd /usr/apps/python
python /usr/apps/python/sms_log.py


/usr/local/coreseek/bin/indexer  --config /usr/apps/coreseek/etc/crm_pymssql.conf --rotate crminfo_delta
/usr/local/coreseek/bin/indexer --merge crminfo crminfo_delta --config /usr/apps/coreseek/etc/crm_pymssql.conf --rotate

