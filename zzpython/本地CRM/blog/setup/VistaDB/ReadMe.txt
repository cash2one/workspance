数据库使用说明
==============
博易的运行是不需要专门的数据库的，它的数据默认全部存放在一组XML里面。为了满足用户更多的需求，博易又支持了扩展数据库。这就意味着您可以将多种数据库用来支持博易的运行。下面我们给出如何将博易运行于 VistaDB 之上的解决方案。 

1.在 http://vistadb.net 下载 VistaDB Express 并安装。

2.找到安装目录下的 VistaDB.NET20.dll 拷贝到程序 Bin 目录。

3.将本目录下的 BlogEngine.vdb3 数据库文件拷贝到程序目录下的 App_Data 目录下。

4.重命名本目录下的 VistaDBWeb.Config 为 Web.config 并覆盖程序目录下的 Web.config 文件（此操作前请先备份原文件）。

5.运行并更改密码（初始账号 admin 密码 admin）。

只需完成以上5个步骤即可！ 


将博易v1.6的数据库升级到博易v1.6.5的解决方案
============================================
打开博易运行的数据库，运行这个 SQL 脚本（VistaDBUpgradeTo1.4.5.0From1.4.0.0.vsql3）即可。


更多支持请关注博易官方网站 http://www.BlogYi.net 。


http://www.BlogYi.net 
SpoonySonny
16:26 2008-3-27


Running BlogEngine.NET 1.4.5 using VistaDB Express:

If you wish to use VistaDB or VistaDB Express to store all your blog data, this is 
where you want to be.  Included in this folder is a default VistaDB database, that 
you can use to get you started with your blog.  In addition, you will find a sample
web.config file with the needed changes to use VistaDB and an upgrade script for 
current VistaDB users who wish to upgrade from 1.4 to 1.4.5

Instructions for new setup:

1. If you don't already have VistaDB or VistaDB Express installed locally, download 
VistaDB Express from vistadb.net and install it locally.
2. Find VistaDB.NET20.dll on your PC and copy it to your blog's Bin folder. 
3. Copy BlogEngine.vdb3 from the VistaDB folder to your App_Data folder.
4. Rename VistaDBWeb.Config to Web.config and copy it to your blog folder.  (This will
overwrite your existing web.config file.  If this is not a new installation, make sure 
you have a backup.)
5. Surf out to your Blog and see the welcome post.
6. Login with the username admin and password admin.  Change the password.

Upgrading from 1.4.0

1. If you don't already have VistaDB or VistaDB Express installed locally, download 
VistaDB Express from vistadb.net and install it locally.
2. Open your BlogEngine.vdb3 database and execute the upgrade script against it.  (You will 
likely need to copy your BlogEngine.vdb3 file from your web server, perform the update, and 
copy it back out depending on your setup.
3. The web.config file has changed from 1.4.0 to 1.4.5.  It will likely be easiest to start
with the sample web.config file as described above, but if you have other changes in it, 
you'll need to merge them.

Important Upgrade Note: Upgrading will cause you to lose setting in your extensions and 
widget framework.  Please make note of these so you can put them back in place after 
the upgrade.

Additional information can be found at http://dotnetblogengine.net

Notice:

While BlogEngine.NET is open source and VistaDB Express is free to use, there are a few restrictions.  
VistaDB Express is only free to use for non commercial uses.  If you are commercial, you will need to 
purchase a license to use it.  In addition, the VistaDB Express license requires that you place a link 
back to them in your product.  A link back the vistadb.net in your page footer or side bar would show 
your appreciation.