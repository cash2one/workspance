package com.nala.csd

import com.nala.common.utils.DateUtils
import com.nala.csd.common.utils.ParamsUtil

import java.util.List;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.*

class LogisticsProblemController {

	def dataImportService
	def autoCreateService
	def dataExportService
	
	def logisticsProblemCookieService

    def userOperateService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"

		[logisticsProblemInstanceList: LogisticsProblem.list(params), logisticsProblemInstanceTotal: LogisticsProblem.count()]
	}

	def create = {
		def logisticsProblemInstance = new LogisticsProblem()
		logisticsProblemInstance.properties = params

		// cookie预存店铺id
		def storeId = request.getCookie("logisticsProblem.store.id")
		if (storeId){
			def store = Store.get(storeId)
			if (store){
				logisticsProblemInstance.store = store
			}
		}
		
		// cookie预存处理人
		def solveCSId = request.getCookie("logisticsProblem.solveCS.id")
		if (solveCSId){
			Hero hero = Hero.get(solveCSId)
			if (hero){
				logisticsProblemInstance.solveCS = hero
			}
		}
		
		// cookie预存跟踪人
		def followCSId = request.getCookie("logisticsProblem.followCS.id")
		if (followCSId){
			Hero hero = Hero.get(followCSId)
			if (hero){
				logisticsProblemInstance.followCS = hero
			}
		}
		
		// cookie预存通知方式
		def notifyModeId = request.getCookie("logisticsProblem.notifyMode.id")
		if (notifyModeId){
			NotifyMode notifyMode = NotifyMode.get(notifyModeId)
			if (notifyMode){
				logisticsProblemInstance.notifyMode = notifyMode
			}
		}
		
		return [logisticsProblemInstance: logisticsProblemInstance]
	}

	def save = {
		def logisticsProblemInstance = new LogisticsProblem(params)

		// 物流公司
		def express
		if (params.express_id){
			express = Express.get(params.express_id)
		}else{
			flash.message = "请选择物流公司!"
			redirect(action: "create")
			return
		}
		if (!params.expressProblem_id || !params.expressProblem_type || !params.expressProblem_description){
			flash.message = "请填写问题物流信息(物流单号、问题类型、问题描述)!"
			redirect(action: "create")
			return
		}

		Date curDate = new Date()
		String curDateStr = DateUtils.parseToWebFormat2(curDate)

		logisticsProblemInstance.dateCreated = curDate

		if (params.solveType){
			// 有处理方式，加上解决时间
			logisticsProblemInstance.solveTime = curDateStr
		}

		if (params.followCS && params.followCS.id){
			// 有跟踪人，加上跟踪时间
			logisticsProblemInstance.followTime = curDateStr
		}

		if (logisticsProblemInstance.solveTime){
			// 计算时间间隔
			Date solveTime = DateUtils.parseToDate(logisticsProblemInstance.solveTime)
			logisticsProblemInstance.jiejueJiange = DateUtils.DaysBetween(logisticsProblemInstance.dateCreated, solveTime)
		}

		// 物流信息
		ExpressProblem ep = new ExpressProblem()
		def tmpEP = ExpressProblem.executeQuery("from ExpressProblem e where logisticsID='" + params.expressProblem_id + "'")
		if (!tmpEP){
			log.debug "--------------------"
			log.debug params.expressProblem_id
			log.debug params.expressProblem_type
			log.debug params.expressProblem_description

			ep.express = express
			ep.setLogisticsID(params.expressProblem_id)
			ep.setProblemType(params.expressProblem_type)
			ep.setDescription(params.expressProblem_description)

			if (!ep.save(flush: true)){
                log.error("save ExpressProblem error: " + row.getCell(0))
				ep.errors.each { log.error it }
				flash.message = "物流信息保存失败，请重试！"
				redirect(action: "create")
				return
			}else{
                userOperateService.operateMethodSelect(ep  ,this.actionName)
            }
		}else{
			log.info ("logisticsID has already exists : " + params.expressProblem_id + ". Reject this data!")
			flash.message = "该物流单号${params.expressProblem_id}已经存在，请勿重复添加！"
			redirect(action: "create")
			return

		}
		logisticsProblemInstance.expressProblem = ep

        if (params.sendGoodsDateStr){
            Date sendGoodsDate = DateUtils.parseToDayDate(params.sendGoodsDateStr)
            logisticsProblemInstance.sendGoodsDate = sendGoodsDate
        }

		// 写cookie
		logisticsProblemCookieService.setlogisticsProblemToCookie(logisticsProblemInstance, response)
		
		if (logisticsProblemInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(logisticsProblemInstance  ,this.actionName)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), logisticsProblemInstance.id])}"
			redirect(action: "show", id: logisticsProblemInstance.id, params: params)
		}
		else {
			render(view: "create", model: [logisticsProblemInstance: logisticsProblemInstance, offset: params.offset])
		}
	}

	def show = {
		def logisticsProblemInstance = LogisticsProblem.get(params.id)
		if (!logisticsProblemInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), params.id])}"
			redirect(action: "list")
		}
		else {
			[logisticsProblemInstance: logisticsProblemInstance, offset: params.offset]
		}
	}

	def edit = {
		log.debug(params)
		def logisticsProblemInstance = LogisticsProblem.get(params.id)
		if (!logisticsProblemInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [logisticsProblemInstance: logisticsProblemInstance, express: logisticsProblemInstance.expressProblem.express, params_prv: params.params_prv]
		}
	}

	def update = {
		def logisticsProblemInstance = LogisticsProblem.get(params.id)
		if (logisticsProblemInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (logisticsProblemInstance.version > version) {

					logisticsProblemInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'logisticsProblem.label', default: 'LogisticsProblem')]
					as Object[], "Another user has updated this LogisticsProblem while you were editing")
					render(view: "edit", model: [logisticsProblemInstance: logisticsProblemInstance])
					return
				}
			}


			String curDate = DateUtils.parseToWebFormat2(new Date())
			// 跟踪人从无到有，自动加跟踪时间
			if (params.followCS.id){
				if (!logisticsProblemInstance.followCS){
					logisticsProblemInstance.followTime = curDate
				}
			}else{
				// 把跟踪人更新成空的，不允许更新
				// todo
			}

			// 有处理方式，加处理时间
			if (params.solveType){
				if (!logisticsProblemInstance.solveTime){
					logisticsProblemInstance.solveTime = curDate

					// 计算时间间隔
					Date solveTime = DateUtils.parseToDate(logisticsProblemInstance.solveTime)
					logisticsProblemInstance.jiejueJiange = DateUtils.DaysBetween(logisticsProblemInstance.dateCreated, solveTime)
				}
			}

			def express
			if (params.express_id){
				express = Express.get(params.express_id)
			}else{
				flash.message = "请选择物流公司!"
				render(view: "edit", model: [logisticsProblemInstance: logisticsProblemInstance])
				return
			}
			if (!params.expressProblem_id || !params.expressProblem_type || !params.expressProblem_description){
				flash.message = "请填写问题物流信息(物流单号、问题类型、问题描述)!"
				render(view: "edit", model: [logisticsProblemInstance: logisticsProblemInstance])
				return
			}

			log.debug "--------------------"
			log.debug params.expressProblem_id
			log.debug params.expressProblem_type
			log.debug params.expressProblem_description

			logisticsProblemInstance.expressProblem.setLogisticsID(params.expressProblem_id)
			logisticsProblemInstance.expressProblem.setProblemType(params.expressProblem_type)
			logisticsProblemInstance.expressProblem.setDescription(params.expressProblem_description)

			logisticsProblemInstance.properties = params

			if (!logisticsProblemInstance.expressProblem.save(flush: true)){
                log.error("save ExpressProblem error: " + row.getCell(0))
				logisticsProblemInstance.expressProblem.errors.each { log.error it }
				flash.message = "物流信息保存失败，请重试！"
				render(view: "edit", model: [logisticsProblemInstance: logisticsProblemInstance])
				return
			} else{
                userOperateService.operateMethodSelect(logisticsProblemInstance.expressProblem  ,this.actionName)
            }

            if (params.sendGoodsDateStr){
                Date sendGoodsDate = DateUtils.parseToDayDate(params.sendGoodsDateStr)
                logisticsProblemInstance.sendGoodsDate = sendGoodsDate
            }

			if (!logisticsProblemInstance.hasErrors() && logisticsProblemInstance.save(flush: true)) {
                userOperateService.operateMethodSelect( logisticsProblemInstance  ,this.actionName)
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), logisticsProblemInstance.id])}"
				
				Map paramsMap = ParamsUtil.resetParamsMap(params.params_prv)				
				redirect(action: "show", id: logisticsProblemInstance.id, params: paramsMap )
			}
			else {
				render(view: "edit", model: [logisticsProblemInstance: logisticsProblemInstance, params_prv: params.params_prv])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def logisticsProblemInstance = LogisticsProblem.get(params.id)
		if (logisticsProblemInstance) {
			def expressProblem = logisticsProblemInstance.expressProblem

			try {
				logisticsProblemInstance.delete(flush: true)
                userOperateService.operateMethodSelect(logisticsProblemInstance ,this.actionName)
				expressProblem.delete(flush: true)
                userOperateService.operateMethodSelect(logisticsProblemInstance.expressProblem  ,this.actionName)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logisticsProblem.label', default: 'LogisticsProblem'), params.id])}"
			redirect(action: "list")
		}
	}

	def importIndex = { render(view:"importIndex") }

	/**
	 * 导入问题件
	 */
	def importExcel = {

		if (!params.express || !params.express.id){
			flash.message = "请选择物流公司!"
			render(view:"importIndex")
			return
		}

		List<ExpressProblem> list
		if(params.excel){
			request.setCharacterEncoding("gbk")
			def f = request.getFile('excel')
			log.debug "file size: " + f.getSize()
			if(!f.empty) {
				log.debug "获取excel中的数据"

				// 哪家快递公司的
				def curExpress = Express.get(params.express.id)

				try{
					list = dataImportService.importExpressProblem(f.getInputStream(), curExpress)
					log.debug "list.size = " + list.size()
				}catch(Exception e){
					log.error e
					flash.message = '导入的文件类型或格式不正确'
					render(view:"importIndex")
					return
				}

				if (list){
					autoCreateService.serviceMethod(list)
				}
			}
		}

		// render(view:"importIndex")
		def listCount = list ? list.size() : 0
		flash.message = "共上传成功${listCount}条数据！"
		redirect(action: "list")
	}

	/**
	 * 导出excel
	 */
	def export = {
		Date daysAgo = DateUtils.getDaysAgo(14)
		def list = LogisticsProblem.createCriteria().list(){
			ge('dateCreated',daysAgo)
		}
		// def list = LogisticsProblem.getAll()

		response.setHeader("Content-disposition", "attachment; filename=logisticsProblem.${params.extension}")
		List fields = [
			"dateCreated",
			"store.name",
			"userId",
			"expressProblem.logisticsID",
			"expressProblem.problemType",
			"expressProblem.description",
			"solveTime",
			"remark",
			"solveType",
			"mobile",
			"receiveInfo",
            "sendGoodsDate",
			"notifyMode.name",
			"followUp",
			"followCS.name",
			"followTime",
			"signUp",
			"jiejueJiange"
		]
		Map labels = [
			"dateCreated":"创建时间",
			"store.name":"店铺",
			"userId":"顾客id",
			"expressProblem.logisticsID":"物流单号",
			"expressProblem.problemType":"问题类型",
			"expressProblem.description":"问题描述",
			"solveTime":"解决时间",
			"remark":"备注",
			"solveType":"处理方式",
			"mobile":"手机号",
			"receiveInfo":"收件信息",
            "sendGoodsDate":"发货日期",
			"notifyMode.name":"提醒类型",
			"followUp":"是否跟踪",
			"followCS.name":"跟踪人",
			"followTime":"跟踪时间",
			"signUp":"是否签收",
			"jiejueJiange":"解决间隔"
		]
		dataExportService.execlExport(params,request,response,list,fields,labels)
	}


	/**
	 * 搜索
	 */
	def search = {
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
		params.offset = params.offset ? params.offset : 0
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"

		def expressProblemList
		if (params.expressProblem_id || params.express_id){
			// 先搜索问题件
			expressProblemList = ExpressProblem.createCriteria().list(){
				// 物流单号
				if (params.expressProblem_id){
					like('logisticsID', "%" + params.expressProblem_id + "%")
				}
				// 物流公司
				if (params.express_id){
					def tmpExpress = Express.get(params.express_id)
					if (tmpExpress){
						eq('express', tmpExpress)
					}
				}
			}
		}


		def searchlist = LogisticsProblem.createCriteria().list(params){
			// 店铺
			if (params.store_id){
				Store store = Store.get(params.store_id)
				eq('store', store)
			}
			// 顾客id
			if (params.userId){
				like('userId', "%" + params.userId + "%")
			}
			// 处理方式
			if (params.solveType){
				like('solveType', "%" + params.solveType + "%")
			}
			// 通知方式
			if (params.notifyMode_id){
				NotifyMode notifyMode = NotifyMode.get(params.notifyMode_id)
				eq('notifyMode', notifyMode)
			}
			// 处理人
			if (params.solveCSId){
				Hero hero = Hero.get(params.solveCSId)
				eq('solveCS', hero)
			} 
			// 跟踪人
			if (params.followCSId){
				Hero hero = Hero.get(params.followCSId)
				eq('followCS', hero)
			} 
			// 跟踪
			if (params.followUp){
				boolean followUp = params.followUp
				eq('followUp', followUp)
			}
			// 签收
			if (params.signUp){
				boolean signUp = params.signUp
				eq('signUp', signUp)
			}
			// 登记时间
			if (params.dateCreatedStart && params.dateCreatedEnd){
				Date start = DateUtils.parseToDates(params.dateCreatedStart)
				Date end = DateUtils.parseToDates(params.dateCreatedEnd)
				ge('dateCreated',start)
				le('dateCreated',end)
			}
			// 跟踪时间
			if (params.followTimeStart && params.followTimeEnd){
				ge('followTime',params.followTimeStart)
				le('followTime',params.followTimeEnd)
			}
			// 解决时间
			if (params.solveTimeStart && params.solveTimeEnd){
				ge('solveTime',params.solveTimeStart)
				le('solveTime',params.solveTimeEnd)
			}
			// 物流单号或者快递公司
			if (expressProblemList){
				'in'("expressProblem", expressProblemList)
			}
			// 手机号
			if (params.mobile){
				like('mobile', '%' + params.mobile + '%')
			}
 
		}


		render(view: "list", model: [logisticsProblemInstanceList: searchlist, logisticsProblemInstanceTotal: searchlist.totalCount, params: params])
	}



}
