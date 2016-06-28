package com.nala.csd

import com.nala.common.utils.DateUtils

class B2CConsultController {
	
	def dataExportService
	
	def b2CConsultCookieService

    def  userOperateService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"
		[b2CConsultInstanceList: B2CConsult.list(params), b2CConsultInstanceTotal: B2CConsult.count()]
	}

	def create = {
		def b2CConsultInstance = new B2CConsult()
		b2CConsultInstance.properties = params
		
		// cookie预存创建人
		def createCSId = request.getCookie("b2CConsult.createCS.id")
		if (createCSId){
			Hero hero = Hero.get(createCSId)
			if (hero){
				b2CConsultInstance.createCS = hero
			}
		}
		
		return [b2CConsultInstance: b2CConsultInstance]
	}

	def save = {
		def b2CConsultInstance = new B2CConsult(params)
		
		// 写cookie
		b2CConsultCookieService.setB2CConsultToCookie(b2CConsultInstance, response)
		
		
		if (b2CConsultInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(b2CConsultInstance ,this.actionName)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), b2CConsultInstance.id])}"
			redirect(action: "show", id: b2CConsultInstance.id)
		}
		else {
			render(view: "create", model: [b2CConsultInstance: b2CConsultInstance])
		}
	}

	def show = {
		def b2CConsultInstance = B2CConsult.get(params.id)
		if (!b2CConsultInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), params.id])}"
			redirect(action: "list")
		}
		else {
			[b2CConsultInstance: b2CConsultInstance]
		}
	}

	def edit = {
		def b2CConsultInstance = B2CConsult.get(params.id)
		if (!b2CConsultInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [b2CConsultInstance: b2CConsultInstance]
		}
	}

	def update = {
		def b2CConsultInstance = B2CConsult.get(params.id)
		if (b2CConsultInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (b2CConsultInstance.version > version) {

					b2CConsultInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'b2CConsult.label', default: 'B2CConsult')]
					as Object[], "Another user has updated this B2CConsult while you were editing")
					render(view: "edit", model: [b2CConsultInstance: b2CConsultInstance])
					return
				}
			}
			b2CConsultInstance.properties = params
			if (!b2CConsultInstance.hasErrors() && b2CConsultInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(b2CConsultInstance ,this.actionName)
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), b2CConsultInstance.id])}"
				redirect(action: "show", id: b2CConsultInstance.id)
			}
			else {
				render(view: "edit", model: [b2CConsultInstance: b2CConsultInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def b2CConsultInstance = B2CConsult.get(params.id)
		if (b2CConsultInstance) {
			try {
				b2CConsultInstance.delete(flush: true)
                userOperateService.operateMethodSelect(b2CConsultInstance ,this.actionName)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsult.label', default: 'B2CConsult'), params.id])}"
			redirect(action: "list")
		}
	}

	/**
	 * 搜索
	 */
	def search = {
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
		params.offset = params.offset ? params.offset : 0
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"

		def searchlist = B2CConsult.createCriteria().list(params){
			// 顾客id
			if (params.userId){
				like('userId', "%" + params.userId + "%")
			}
			// 处理人
			if (params.solveCSId){
				Hero hero = Hero.get(params.solveCSId)
				eq('solveCS', hero)
			}			
			// 问题类型
			if (params.questionStatusId){
				int typeId = Integer.parseInt(params.questionStatusId)
				def type = QuestionType.get(typeId)
				eq('questionStatus', type)
			}
			// 咨询方式
			if (params.questionSourceId){
				QuestionSource questionSource = QuestionSource.get(params.questionSourceId)
				if (questionSource){
					eq('questionSource', questionSource)
				}
			}
			// 电话
			if (params.phone){
				like('phone', "%" + params.phone + "%")
			}
			// 姓名
			if (params.name){
				like('name', "%" + params.name + "%")
			}
			// 订单编号
			if (params.tradeId){
				like('tradeId', "%" + params.tradeId + "%")
			}
			// 处理结果
			if (params.b2CConsultResultId){
				B2CConsultResult b2CConsultResult = B2CConsultResult.get(params.b2CConsultResultId)
				if (b2CConsultResult){
					eq('b2CConsultResult', b2CConsultResult)
				}
			}
			// 未解决说明
			if (params.noSolveReason){
				like('noSolveReason', "%" + params.noSolveReason + "%")
			}
			// 登记时间
			if (params.dateCreatedStart && params.dateCreatedEnd){
				Date start = DateUtils.parseToDates(params.dateCreatedStart)
				Date end = DateUtils.parseToDates(params.dateCreatedEnd)
				ge('dateCreated',start)
				le('dateCreated',end)
			}			
		}

		render(view: "list", model: [b2CConsultInstanceList: searchlist, b2CConsultInstanceTotal: searchlist.totalCount, params: params])
	}

	/**
	 * 导出所有数据
	 */
	def export = {
		Date daysAgo = DateUtils.getDaysAgo(14)
		def list = B2CConsult.createCriteria().list(){ ge('dateCreated',daysAgo) }

		response.setHeader("Content-disposition", "attachment; filename=b2cConsult.${params.extension}")
		List fields = [
			"dateCreated",
			"createCS.name",
			"questionSource.name",
			"userId",
			"phone",
			"name",
			"questionStatus.questionDescription",
			"tradeId",
			"b2CConsultResult.name",
			"noSolveReason"
		]
		Map labels = [
			"dateCreated":"创建时间",
			"createCS.name":"发起人",
			"questionSource.name":"咨询方式",
			"userId":"会员ID",
			"phone":"电话",
			"name":"姓名",
			"questionStatus.questionDescription":"问题类型",
			"tradeId":"订单编号",
			"b2CConsultResult.name":"处理结果",
			"noSolveReason":"未解决说明"

		]
		dataExportService.execlExport(params,request,response,list,fields,labels)
	}
}
