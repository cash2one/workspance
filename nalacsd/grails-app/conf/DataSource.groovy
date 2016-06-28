dataSource {
    pooled = true
	driverClassName  = "com.mysql.jdbc.Driver"
	username = "root"
	password = "zj88friend"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            logSql = false
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://192.168.2.10:3306/nalacsd?autoReconnect=true&useUnicode=true&characterEncoding=UTF8"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://192.168.2.10:3306/nalacsd?autoReconnect=true&useUnicode=true&characterEncoding=UTF8"
        }
    }
}
