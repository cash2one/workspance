INSERT INTO param_type
   (`key`, `name`, `gmt_created`)
VALUES
   ('baseConfig', '基本配置', "NULL");

INSERT INTO param
   (`name`, `types`, `key`, `value`, `sort`, `isuse`, `gmt_created`)
VALUES
   ('域名', 'baseConfig', 'domain', 'zz91.net', 0, 1, "NULL");

INSERT INTO param
   (`name`, `types`, `key`, `value`, `sort`, `isuse`, `gmt_created`)
VALUES
   ('图片服务器', 'baseConfig', 'imgServer', 'img.zz91.net', 1, 1, "NULL");

INSERT INTO param
   (`name`, `types`, `key`, `value`, `sort`, `isuse`, `gmt_created`)
VALUES
   ('缓存服务器地址', 'baseConfig', 'cache_server', '192.168.2.9', 2, 1, "NULL");

INSERT INTO param
   (`name`, `types`, `key`, `value`, `sort`, `isuse`, `gmt_created`)
VALUES
   ('缓存服务器端口', 'baseConfig', 'cache_server_port', '11211', 3, 1, "NULL");

INSERT INTO param
   (`name`, `types`, `key`, `value`, `sort`, `isuse`, `gmt_created`)
VALUES
   ('后台列表分页大小', 'baseConfig', 'grid_page_size', '20', 4, 1, "NULL");

