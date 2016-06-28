数据库使用说明
==============
博易的运行是不需要专门的数据库的，它的数据默认全部存放在一组XML里面。为了满足用户更多的需求，博易又支持了扩展数据库。这就意味着您可以将多种数据库用来支持博易的运行。下面我们给出如何将博易运行于 SQLite 之上的解决方案。 

1.在 http://sourceforge.net/projects/sqlite-dotnet2 下载 ADO.NET 2.0 Provider。

2.找到下载包中的 System.Data.SQLite.DLL 拷贝到程序 Bin 目录。

3.将本目录下的 BlogEngine.s3db 数据库文件拷贝到程序目录下的 App_Data 目录下。

4.重命名本目录下的 SQLiteWeb.Config 为 Web.config 并覆盖程序目录下的 Web.config 文件（此操作前请先备份原文件）。

5.运行并更改密码（初始账号 Admin 密码 admin）。

只需完成以上5个步骤即可！ 


将博易v1.6的数据库升级到博易v1.6.5的解决方案
============================================
打开博易运行的数据库，运行这个 SQL 脚本（SQLiteUpgradeFrom1.4.5.0To1.4.0.0.txt）即可。该脚本的运行需要 SQLite 数据库管理工具，我们推荐您使用 SQLite Admin。免费下载地址 http://sqliteadmin.orbmu2k.de/


更多支持请关注博易官方网站 http://www.BlogYi.net 。


http://www.BlogYi.net 
SpoonySonny
16:26 2008-3-27