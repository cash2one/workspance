package com.nala.csd

import com.nala.common.utils.DateUtils
import com.nala.csd.common.utils.ParamsUtil

class ChajianPassiveController {
	
	def dataExportService
	
	def chajianPassiveCookieService

    def userOperateService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
		// params.max = Math.min(params.max ? params.int('max') : 10, 10)
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"

       // def errorTradeInstanceList
        def chajianPassiveInstanceList
        def stores
        boolean tp = false
        if (params.storeType && params.storeType == 'true'){
            tp = true
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }


        if (stores){
            chajianPassiveInstanceList = ChajianPassive.createCriteria().list(params) {
                or{
                    stores.each{
                        eq("store", it)
                    }
                }
            }
        }
        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.passive)

        [chajianPassiveInstanceList: chajianPassiveInstanceList, chajianPassiveInstanceTotal: chajianPassiveInstanceList?.totalCount, chajianCodeList: chajianCodeList, stores: stores, params: params, tp:tp]
    }

    def create = {
        def chajianPassiveInstance = new ChajianPassive()
		
        chajianPassiveInstance.properties = params
        def tp = Boolean.parseBoolean(params.storeType)
        def stores = Store.findAllByTp(tp)
		
		// cookie预存店铺id
		def storeId = request.getCookie("chajianPassive.store.id")
		if (storeId){
			def store = Store.get(storeId)
			if (store){
				chajianPassiveInstance.store = store
			}
		}
		
		// cookie预存创建人id
		def createCSId = request.getCookie("chajianPassive.createCS.id")
		if (createCSId){
			Hero hero = Hero.get(createCSId)
			if (hero){
				chajianPassiveInstance.createCS = hero
			}
		}
		
		// cookie预存联系优先级
		def contactStatusCode = request.getCookie("chajianPassive.contactStatus.code")
		if (contactStatusCode){
			ContactStatusEnum contactStatus = ContactStatusEnum.getByCode(contactStatusCode)
			if (contactStatus){
				chajianPassiveInstance.contactStatus = contactStatus
			}
		}
		
		// cookie预存查件code代码
		def chajianCodeId = request.getCookie("chajianPassive.chajianCode.id")
		if (chajianCodeId){
			ChajianCode chajianCode = ChajianCode.get(chajianCodeId)
			if (chajianCode){
				chajianPassiveInstance.chajianCode = chajianCode
			}
		}
		
		// cookie预存处理人
		def solveCSId = request.getCookie("chajianPassive.solveCS.id")
		if (solveCSId){
			Hero solveCS = Hero.get(solveCSId)
			if (solveCS){
				chajianPassiveInstance.solveCS = solveCS
			}
		}
		
		// cookie预存通知方式
		def notifyModeId = request.getCookie("chajianPassive.notifyMode.id")
		if (notifyModeId){
			NotifyMode notifyMode = NotifyMode.get(notifyModeId)
			if (notifyMode){
				chajianPassiveInstance.notifyMode = notifyMode
			}
		}
		
		// cookie预存跟踪人
		def followCSId = request.getCookie("chajianPassive.followCS.id")
		if (followCSId){
			Hero followCS = Hero.get(followCSId)
			if (followCS){
				chajianPassiveInstance.followCS = followCS
			}
		}

        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.passive)
		
        return [chajianPassiveInstance: chajianPassiveInstance, chajianCodeList: chajianCodeList,  tp:tp, stores:stores ]
    }

    def save = {
        def tp = String.valueOf(params.storeType)
        tp = Boolean.parseBoolean(tp)

//		println params	
        def chajianPassiveInstance = new ChajianPassive(params)
		
		Date curDate = new Date()
		String curDateStr = DateUtils.parseToWebFormat2(curDate)
		
		if (params.solveCS.id){
			// 有处理人，加上联系时间
			chajianPassiveInstance.contactTime = curDateStr 
		}
		
		if (params.followCS.id){
			// 有跟踪人，加上处理时间
			chajianPassiveInstance.solveTime = curDateStr
		}
		
		if (chajianPassiveInstance.contactTime){
			// 计算时间间隔
			Date contactDate = DateUtils.parseToDate(chajianPassiveInstance.contactTime)
			if (chajianPassiveInstance.dateCreated == null){
				chajianPassiveInstance.dateCreated = curDate
			}
			chajianPassiveInstance.lianxiJiange = DateUtils.DaysBetween(chajianPassiveInstance.dateCreated, contactDate)
		}
		
		if (chajianPassiveInstance.solveTime && chajianPassiveInstance.contactTime){
			// 计算时间间隔
			Date contactDate = DateUtils.parseToDate(chajianPassiveInstance.contactTime)
			Date solveDate = DateUtils.parseToDate(chajianPassiveInstance.solveTime)
			chajianPassiveInstance.jiejueJiange = DateUtils.DaysBetween(contactDate, solveDate)
		}
		
		// 写cookie
		chajianPassiveCookieService.setChajianPassiveToCookie(chajianPassiveInstance, response)
		
        if (chajianPassiveInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(chajianPassiveInstance  ,this.actionName)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), chajianPassiveInstance.id])}"
            redirect(action: "show", id: chajianPassiveInstance.id)
        }
        else {
            // 筛选后的查件代码
            def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.passive)
            render(view: "create", model: [chajianPassiveInstance: chajianPassiveInstance, chajianCodeList: chajianCodeList, tp:tp])
        }
    }

    def show = {
        def chajianPassiveInstance = ChajianPassive.get(params.id)
        def tp = chajianPassiveInstance?.store?.tp
        if (!chajianPassiveInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), params.id])}"
            redirect(action: "list")
        }
        else {
            [chajianPassiveInstance: chajianPassiveInstance, tp:tp]
        }
    }

    def edit = {
        def stores
        def chajianPassiveInstance = ChajianPassive.get(params.id)
        def storesList = Store.findAllByTp(false)
        def tp = true
        storesList.each{
            if(tp && it.tp == chajianPassiveInstance?.store?.tp ){
                tp = false
            }
        }
        stores = Store.findAllByTp(tp)

        // 筛选后的查件代码
        def chajianCodeList = ChajianCode.findAllByCodeForTable(CodeForTableEnum.passive)

        if (!chajianPassiveInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [chajianPassiveInstance: chajianPassiveInstance, params_prv: params.params_prv, chajianCodeList: chajianCodeList,  tp:tp, stores: stores]
        }
    }

    def update = {
//		println params
        def chajianPassiveInstance = ChajianPassive.get(params.id)
        if (chajianPassiveInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (chajianPassiveInstance.version > version) {
                    
                    chajianPassiveInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'chajianPassive.label', default: 'ChajianPassive')] as Object[], "Another user has updated this ChajianPassive while you were editing")
                    render(view: "edit", model: [chajianPassiveInstance: chajianPassiveInstance])
                    return
                }
            }
			
			String curDate = DateUtils.parseToWebFormat2(new Date())
			// 跟踪人从无到有，自动加解决时间
			if (params.followCS.id){
				if (!chajianPassiveInstance.followCS){
					chajianPassiveInstance.solveTime = curDate
				}
			}
			
			// 处理人从无到有，加上联系时间
			if (params.solveCS.id){
				if (!chajianPassiveInstance.solveCS){
					chajianPassiveInstance.contactTime = curDate
				}
			}
			
			chajianPassiveInstance.properties = params
			
			if (chajianPassiveInstance.contactTime){
				// 计算时间间隔
				Date contactDate = DateUtils.parseToDate(chajianPassiveInstance.contactTime)
				chajianPassiveInstance.lianxiJiange = DateUtils.DaysBetween(chajianPassiveInstance.dateCreated, contactDate)
			}
			
			if (chajianPassiveInstance.solveTime && chajianPassiveInstance.contactTime){
				// 计算时间间隔
				Date contactDate = DateUtils.parseToDate(chajianPassiveInstance.contactTime)
				Date solveDate = DateUtils.parseToDate(chajianPassiveInstance.solveTime)
				chajianPassiveInstance.jiejueJiange = DateUtils.DaysBetween(contactDate, solveDate)
			}
			
			
            if (!chajianPassiveInstance.hasErrors() && chajianPassiveInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(chajianPassiveInstance  ,this.actionName)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), chajianPassiveInstance.id])}"
				Map paramsMap = ParamsUtil.resetParamsMap(params.params_prv)
				redirect(action: "show", id: chajianPassiveInstance.id, params: paramsMap )
            }
            else {
                render(view: "edit", model: [chajianPassiveInstance: chajianPassiveInstance, params_prv: params.params_prv])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def chajianPassiveInstance = ChajianPassive.get(params.id)
        def tp = chajianPassiveInstance.store.tp
        tp = String.valueOf(tp)
        if (chajianPassiveInstance) {
            try {
                chajianPassiveInstance.delete(flush: true)
                userOperateService.operateMethodSelect(chajianPassiveInstance  ,this.actionName)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), params.id])}"
                redirect(action: "list",params: [storeType:tp])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianPassive.label', default: 'ChajianPassive'), params.id])}"
            redirect(action: "list")
        }
    }
	
	/**
	 * 导出excel
	 */
	def export = {
//		println params
        def tp = Boolean.valueOf(params.tp)
        def stores = Store.findAllByTp(tp)

		Date daysAgo = DateUtils.getDaysAgo(14)
        if (stores){

          list = ChajianPassive.createCriteria().list(){
                ge('dateCreated',daysAgo)
                or{
                    stores.each{
                        eq("store", it)
                    }
                }
            }
        }
		
		response.setHeader("Content-disposition", "attachment; filename=chajianPassive.${params.extension}")
		List fields = [
			"dateCreated", 
			"store.name", 
			"userId",
			"chajianCode.name",
			"logistics",
			"results",
			"createCS.name",
			"solveCS.name",
			"contactTime",
			"solveTime",
			"mobile",
			"notifyMode.name",
			"followUp",
			"followCS.name",
			"updateTime",
			"signUp",
			"lianxiJiange",
			"jiejueJiange"
			]
		Map labels = [
			"dateCreated":"创建时间", 
			"store.name":"店铺", 
			"userId":"顾客ID",
			"chajianCode.name":"查件代码",
			"logistics":"物流单号",
			"results":"查询备注",
			"createCS.name":"创建人",
			"solveCS.name":"处理人",
			"contactTime":"联系时间",
			"solveTime":"解决时间",
			"mobile":"手机号码",
			"notifyMode.name":"提醒方式",
			"followUp":"是否跟踪",
			"followCS.name":"跟踪人",
			"updateTime":"跟踪时间",
			"signUp":"是否签收",
			"lianxiJiange":"联系间隔",
			"jiejueJiange":"解决间隔"
			]
		dataExportService.execlExport(params,request,response,list,fields,labels)
	}
	
	/**
	* 搜索
	*/
   def search = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        // params.max = Math.min(params.max ? params.int('max') : 10, 10)
        params.offset = params.offset ? params.offset : 0
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

       def stores
       boolean tp = false
       tp  = params.tp.equals('true')
       if(tp){
           stores = Store.findAllByTp(true)
       }else{
           stores = Store.findAllByTp(false)
       }


       def searchlist = ChajianPassive.createCriteria().list(params){

            if (stores){
                or{
                    stores.each{
                        eq("store", it)
                    }
                }
            }

            // 店铺
            if (params.store_id){
                Store store = Store.get(params.store_id)
                eq('store', store)
            }
            // 顾客id
            if (params.userId){
                like('userId', "%" + params.userId + "%")
            }
            // 订单编号
            if (params.tradeId){
                like('tradeId', "%" + params.tradeId + "%")
            }
            // 处理人
            if (params.solveCSId){
                Hero hero = Hero.get(params.solveCSId)
                eq('solveCS', hero)
            }
            // 发起人
            if (params.createCSId){
                Hero hero = Hero.get(params.createCSId)
                eq('createCS', hero)
            }
            // 跟踪人
            if (params.followCSId){
                Hero hero = Hero.get(params.followCSId)
                eq('followCS', hero)
            }
            // 查件代码
            if (params.chajianCode_id){
                ChajianCode chajianCode = ChajianCode.get(params.chajianCode_id)
                eq('chajianCode', chajianCode)
            }
            // 物流单号
            if (params.logistics){
                like('logistics', '%' + params.logistics + '%')
            }
            // 通知方式
            if (params.notifyMode_id){
                NotifyMode notifyMode = NotifyMode.get(params.notifyMode_id)
                eq('notifyMode', notifyMode)
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
            // 联系时间
            if (params.contactTimeStart && params.contactTimeEnd){
                ge('contactTime',params.contactTimeStart)
                le('contactTime',params.contactTimeEnd)
            }
            // 解决时间
            if (params.solveTimeStart && params.solveTimeEnd){
                ge('solveTime',params.solveTimeStart)
                le('solveTime',params.solveTimeEnd)
            }
            // 跟踪时间
            if (params.updateTimeStart && params.updateTimeEnd){
                Date start = DateUtils.parseToDates(params.updateTimeStart)
                Date end = DateUtils.parseToDates(params.updateTimeEnd)
                ge('updateTime',start)
                le('updateTime',end)
            }
            // 联系优先级
            if (params.contactStatus){
                ContactStatusEnum cur = ContactStatusEnum.getByCode(params.contactStatus)
                eq('contactStatus', cur)
            }
            // 手机号
            if (params.mobile){
                like('mobile', '%' + params.mobile + '%')
            }

        }
        log.debug("params阐述"+params)
        render(view: "list", model: [chajianPassiveInstanceList: searchlist, chajianPassiveInstanceTotal: searchlist.totalCount, params: params, tp:tp , stores: stores])
    }
}
