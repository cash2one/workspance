package com.nala.csd.data

import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * 导出excel
 * @author Kenny
 *
 */
class DataExportService {

    static transactional = true

    def serviceMethod() {

    }
	
	def exportService
	def execlExport(params,request,response,trades,fields,labels){
			
			log.debug "response.contentType = " + response.contentType
			log.debug "request = " + request
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
			def upperCase = { domain, value->
				return value.toUpperCase()
			}
	
			Map formatters = [author: upperCase]
			Map parameters = [title: "Message detail", "column.widths": [0.3, 0.3, 0.2,0.2,0.2,0.3,0.2,0.2]]
	
			exportService.export(params.format, response.outputStream,trades, fields, labels, formatters, parameters)
			
			log.debug "end service"
			log.info("export data success!")
	}
}
