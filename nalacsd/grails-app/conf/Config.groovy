// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
import grails.plugins.springsecurity.SecurityConfigType

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

csd.logLocation="/home/nala-csd/logs"

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
		
		log4j = {
			appender.stdout = "org.apache.log4j.ConsoleAppender"
			appender.'stdout.layout'="org.apache.log4j.PatternLayout"

			root.level = org.apache.log4j.Level.INFO
			root {
				debug stdout
				debug()
				additivity = true
			}
			debug  stdout:'grails.app.controller.com.nala',
					'grails.app.services.com.nala',
					'com.nala','org.grails.plugins.oauth'


			error  'org.hibernate',
					'org.mysql',
					'org.apache',
					'net.sf',
					'org.codehaus.groovy.grails.context',
					'grails.plugin.springevents.GrailsApplicationEventMulticaster'

			error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
					'org.codehaus.groovy.grails.web.pages', //  GSP
					'org.codehaus.groovy.grails.web.sitemesh', //  layouts
					'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
					'org.codehaus.groovy.grails.web.mapping', // URL mapping
					'org.codehaus.groovy.grails.commons', // core / classloading
					'org.codehaus.groovy.grails.plugins', // plugins
					'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
					'org.springframework',
					'net.sf.ehcache.hibernate'
			warn   'org.mortbay.log'
		}
		
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        appender new org.apache.log4j.DailyRollingFileAppender(name:"defaultAppender",
				layout:pattern(conversionPattern: '%d,%C{1},%t, %c{2} - %m%n'),fileName:csd.logLocation+"/common-default.log",datePattern:"'.'yyyy-MM-dd ",threshold: org.apache.log4j.Level.INFO)
		appender new org.apache.log4j.DailyRollingFileAppender(name:"errorAppender",
				layout:pattern(conversionPattern: '%d,%C{1},%t, %c{2} - %m%n'),fileName:csd.logLocation+"/common-error.log",datePattern:"'.'yyyy-MM-dd",threshold: org.apache.log4j.Level.ERROR)
    }
	
	root {
		info 'defaultAppender','errorAppender'
		info()
		additivity = true
	}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.nala.csd.Hero'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.nala.csd.HeroRole'
grails.plugins.springsecurity.authority.className = 'com.nala.csd.Role'

grails.plugins.springsecurity.providerNames = [
        "daoAuthenticationProvider",
        "anonymousAuthenticationProvider",
        "rememberMeAuthenticationProvider"
]
grails.plugins.springsecurity.password.algorithm = 'md5'
grails.plugins.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/'
//grails.plugins.springsecurity.successHandler.alwaysUseDefaultTargetUrl='/'
grails.plugins.springsecurity.useSecurityEventListener = true
grails.plugins.springsecurity.onAuthenticationSuccessEvent = { e, appCtx ->
}
grails.plugins.springsecurity.interceptUrlMap = [
        '/jinGuanShouHou/shouhouList*' : ['ROLE_ADMIN', 'ROLE_SHOUHOU'],
        '/jinGuanShouHou/moneyList*' : ['ROLE_ADMIN', 'ROLE_HUOKUAN'],
        '/userOperate/**' : ['ROLE_ADMIN'],
        '/login/**' : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/css/**' : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/images/**' : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/js/**' : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**' : ['ROLE_TP', 'ROLE_NEIKONG', 'ROLE_CHADAN', 'ROLE_ADMIN', 'ROLE_XIAOSHOU', 'ROLE_SHENDAN', 'ROLE_SHOUHOU', 'ROLE_LIDAN', 'ROLE_ZHONGCHAPING', 'ROLE_HUOKUAN', 'ROLE_B2C', 'ROLE_TMALL'],

]
