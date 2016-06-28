部署说明：

***************************************************************************************************************************************
1、在/usr/tools/tomcat/conf/Catalina/localhost里添加文件sorl.xml.

<?xml version="1.0" encoding="UTF-8"?>  
<Context docBase="${catalina.home}/webapps/solr.war" debug="0" crossContext="true" >    
    <Environment name="solr/home" type="java.lang.String" value="${catalina.home}/huanbao-search/config" override="true" />    
</Context>

注意：修改server.xml中添加URIEncoding="UTF-8"
 <Connector port="8080" protocol="HTTP/1.1" 
               connectionTimeout="20000" 
               redirectPort="8443" URIEncoding="UTF-8"/>

***************************************************************************************************************************************

2、将huanbao-search文件夹拷贝到/usr/tools/tomcat目录下（满足上面sorl.xml配置要求）

***************************************************************************************************************************************

3、减压缩sorl.war项目，将/deploy/lib目录下的jar包拷贝到压缩后的sorl/WEB-INF/lib目录下。

***************************************************************************************************************************************

4、将减压缩后的sorl拷贝到webapps目录下,启动Tomcat,访问路径http://localhost：8080/sorl，成功！


***************************************************************************************************************************************

注意：
    配置路径的几个要点点：
    1、tomcat/conf/Catalina/localhost/sorl.xml文件：配置sorlweb项目路径
    2、huanbao-search/config下solr.xml：配置加载搜索索引（多个）
    3、schema.xml文件中中文分词词库的路径

***************************************************************************************************************************************

导入mysql数据命令
http://localhost:8080/solr/company/dataimport  为了获得当前的状态
http://localhost:8080/solr/company/dataimport?command=reload-config 为了重新读取配置
http://localhost:8080/solr/company/dataimport?command=full-import 为了启动一个完整的索引
http://localhost:8080/solr/company/dataimport?command=full-import&clean=false 参数clean=false表示不删除，但也会删除相同id的（在 scheam.xml 的uniqueKey 声明的）
http://localhost:8080/solr/company/dataimport?command=delta-import  为了启动增量索引

***************************************************************************************************************************************

