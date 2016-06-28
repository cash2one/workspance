package com.nala.csd


import com.nala.common.utils.DateUtils
import com.nala.csd.common.utils.ParamsUtil
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver

/**
 * 异常单
 * @author Kenny
 *
 */
class ErrorTradeController {

	def dataExportService

	def errorTradeCookieService
	
	def dataImportService

    def userOperateService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {

        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        params.sort = params.sort ? params.sort : "dateCreated"
        params.order = params.order ? params.order : "desc"

        def errorTradeInstanceList
        def stores
        boolean tp = false
        if (params.storeType && params.storeType == 'true'){
            tp = true
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }


        if (stores){
            errorTradeInstanceList = ErrorTrade.createCriteria().list(params) {
                or{
                    stores.each{
                        eq("store", it)
                     }
                }
            }
        }

       [errorTradeInstanceList: errorTradeInstanceList, errorTradeInstanceTotal: errorTradeInstanceList.totalCount, tp: tp, stores: stores, params: params]
	}

	def create = {
		def errorTradeInstance = new ErrorTrade()
        errorTradeInstance.properties = params
        def tp = Boolean.parseBoolean(params.storeType)
        def stores = Store.findAllByTp(tp)

		// cookie预存店铺id
		def storeId = request.getCookie("errorTrade.store.id")
		if (storeId){
			def store = Store.get(storeId)
			if (store){
				errorTradeInstance.store = store
			}
		}

		// cookie预存创建人id
		def createCSId = request.getCookie("errorTrade.createCS.id")
		if (createCSId){
			Hero hero = Hero.get(createCSId)
			if (hero){
				errorTradeInstance.createCS = hero
			}
		}

		// cookie预存异常原因
		def errorReasonId = request.getCookie("errorTrade.errorReason.id")
		if (errorReasonId){
			ErrorReasonForTrade errorReason = ErrorReasonForTrade.get(errorReasonId)
			if (errorReason){
				errorTradeInstance.errorReason = errorReason
			}
		}

		// cookie预存处理人
		def solveCSId = request.getCookie("errorTrade.solveCS.id")
		if (solveCSId){
			Hero hero = Hero.get(solveCSId)
			if (hero){
				errorTradeInstance.solveCS = hero
			}
		}

		// cookie预存处理方式
		def solveTypeId = request.getCookie("errorTrade.solveType.id")
		if (solveTypeId){
			SolveType solveType = SolveType.get(solveTypeId)			
			if (solveType){
				errorTradeInstance.solveType = solveType
			}
		}
        return [errorTradeInstance: errorTradeInstance, tp:tp, stores:stores ]
	}

	def save = {
        log.debug ("params"+ params.dealPriority)
        if(params.dealPriority){
            DealPriority dealPriority  = DealPriority.get(Integer.parseInt(params.dealPriority))
            params.dealPriority = dealPriority
        }

		def errorTradeInstance = new ErrorTrade(params)
        Date curDate = new Date()
		String curDateStr = DateUtils.parseToWebFormat2(curDate)
//        def tp = String.valueOf(params.tp)
//        tp = Boolean.parseBoolean(tp)
        def tp = String.valueOf(params.storeType)
        tp = Boolean.parseBoolean(tp)
		// 有处理人，加解决时间
		if (params.solveCS.id){
			errorTradeInstance.solveTime = curDateStr
		}

		// 写cookie
		errorTradeCookieService.seterrorTradeToCookie(errorTradeInstance, response)

		if (errorTradeInstance.solveTime){
			// 计算时间间隔
			Date contactDate = DateUtils.parseToDate(errorTradeInstance.solveTime)

			if (!errorTradeInstance.dateCreated){
				errorTradeInstance.dateCreated = curDate
			}

			errorTradeInstance.jiejueJiange = DateUtils.DaysBetween(errorTradeInstance.dateCreated, contactDate)
		}

		if (errorTradeInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(errorTradeInstance  ,this.actionName)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), errorTradeInstance.id])}"
            log.debug 'save..'
            log.debug tp
			redirect(action: "show", id: errorTradeInstance.id ,params:[tp:tp])
		}
		else {
			render(view: "create", model: [errorTradeInstance: errorTradeInstance, tp:tp])
		}
	}

	def show = {
		def errorTradeInstance = ErrorTrade.get(params.id)
        def tp = errorTradeInstance?.store?.tp
      //  tp = params?.tp
        if (params.tp){
            tp = params.tp
        }
        tp = String.valueOf(tp)
        tp = Boolean.parseBoolean(tp)
        if (!errorTradeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), params.id])}"
			redirect(action: "list")
		}
		else {
			[errorTradeInstance: errorTradeInstance, tp:tp]
		}
	}

	def edit = {
        def stores
        def errorTradeInstance = ErrorTrade.get(params.id)
        def storesList = Store.findAllByTp(false)
        def tp = true
        storesList.each{
            if(tp && it.tp == errorTradeInstance?.store?.tp ){
                tp = false
            }
        }
        stores = Store.findAllByTp(tp)
//        if (storesList.contains(errorTradeInstance.store)){
//            stores = StoreList
//        }else{
//            stores = Store.findAllByTp(true)
//        }
		if (!errorTradeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [errorTradeInstance: errorTradeInstance, params_prv: params.params_prv, tp:tp, stores: stores]
		}
	}

	def update = {
		def errorTradeInstance = ErrorTrade.get(params.id)
		if (errorTradeInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (errorTradeInstance.version > version) {

					errorTradeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'errorTrade.label', default: 'ErrorTrade')]
					as Object[], "Another user has updated this ErrorTrade while you were editing")
					render(view: "edit", model: [errorTradeInstance: errorTradeInstance])
					return
				}
			}

			Date curDate = new Date()
			String curDateStr = DateUtils.parseToWebFormat2(curDate)

			// 处理人从无到有，加上解决时间
			if (params.solveCS.id){
				if (!errorTradeInstance.solveCS){
					errorTradeInstance.solveTime = curDateStr
				}
			}

            if (params.dealPriority){
                DealPriority dealPriority = DealPriority.get(Integer.parseInt(params.dealPriority))
                params.dealPriority = dealPriority
            }

			errorTradeInstance.properties = params

			if (errorTradeInstance.solveTime){
				// 计算时间间隔
				Date contactDate = DateUtils.parseToDate(errorTradeInstance.solveTime)
				errorTradeInstance.jiejueJiange = DateUtils.DaysBetween(errorTradeInstance.dateCreated, contactDate)
			}

			if (!errorTradeInstance.hasErrors() && errorTradeInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(errorTradeInstance ,this.actionName)
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), errorTradeInstance.id])}"
				
				Map paramsMap = ParamsUtil.resetParamsMap(params.params_prv)
				redirect(action: "show", id: errorTradeInstance.id, params: paramsMap )
			}
			else {
				render(view: "edit", model: [errorTradeInstance: errorTradeInstance, params_prv: params.params_prv])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def errorTradeInstance = ErrorTrade.get(params.id)
        def tp = errorTradeInstance.store.tp
        log.debug '-------'
        log.debug tp
        tp = String.valueOf(tp)
		if (errorTradeInstance) {
			try {
				errorTradeInstance.delete(flush: true)
                userOperateService.operateMethodSelect(errorTradeInstance ,this.actionName)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), params.id])}"
				log.debug tp
               // render(view:)
                redirect(action: "list", params: [storeType:tp])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorTrade.label', default: 'ErrorTrade'), params.id])}"
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
        def list
        if (stores){
		     list = ErrorTrade.createCriteria().list(){
			    ge('dateCreated',daysAgo)
                or{
                    stores.each{
                        eq("store", it)
                    }
                }
		    }
        }
		//def list = ErrorTrade.getAll()

		response.setHeader("Content-disposition", "attachment; filename=errorTrade.${params.extension}")
		List fields = [
            "dealPriority.name",
			"dateCreated",
			"store.name",
			"userId",
			"tradeId",
			"errorReason.name",
			"content",
			"createCS.username",
			"contactTimes.description",
			"solveCS.username",
			"solveStatus.description",
			"solveType.name",
			"buyerRemarks",
			"sellerRemarks",
			"solveTime",
			"jiejueJiange"
		]
		Map labels = [

					"dateCreated": "创建时间",
					"store.name": "店铺",
					"userId": "顾客ID",
					"tradeId": "订单编号",
                "dealPriority.name": "处理优先级",
					"errorReason.name": "异常原因",
					"content": "异常描述",
					"createCS.username": "发起人",
					"contactTimes.description": "联系次数",
					"solveCS.username": "处理人",
					"solveStatus.description": "完成进度",
					"solveType.name": "处理方式",
					"buyerRemarks":"买家留言",
					"sellerRemarks":"卖家留言",
					"solveTime":"解决时间",
					"jiejueJiange":"创建时间到解决时间的间隔"
					
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

        log.debug '--------------------'
        log.debug params.tp
	    def stores
        boolean tp = false
        tp  = params.tp.equals('true')
        if(tp){
             stores = Store.findAllByTp(true)
        }else{
             stores = Store.findAllByTp(false)
        }

        def searchlist = ErrorTrade.createCriteria().list(params){

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

            if (params.dealPriority){
                DealPriority dealPriority = DealPriority.get(params.dealPriority)
                eq('dealPriority', dealPriority)
            }

			// 发起人
			if (params.createCSId){
				Hero hero = Hero.get(params.createCSId)
				eq('createCS', hero)
			}
			// 处理人
			if (params.solveCSId){
				Hero hero = Hero.get(params.solveCSId)
				eq('solveCS', hero)
			}
			// 订单编号
			if (params.tradeId){
				like('tradeId', '%' + params.tradeId + '%')
			}
			// 完成进度
			if (params.solveStatus){
				SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
				eq('solveStatus', cur)
			}
			// 异常原因
			if (params.errorReason_id){
				ErrorReasonForTrade errorReasonForTrade = ErrorReasonForTrade.get(params.errorReason_id)
				eq('errorReason', errorReasonForTrade)
			}
			// 异常描述
			if (params.content){
				like('content', '%' + params.content + '%')
			}
			// 商品SKU
			if (params.itemSKUs){
				like('itemSKUs', '%' + params.itemSKUs + '%')
			}
			// 登记时间
			if (params.dateCreatedStart && params.dateCreatedEnd){
				Date start = DateUtils.parseToDates(params.dateCreatedStart)
				Date end = DateUtils.parseToDates(params.dateCreatedEnd)
				ge('dateCreated',start)
				le('dateCreated',end)
			}
			// 完成进度
			if (params.solveStatus){
				SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
				eq('solveStatus', cur)
			}

            //处理方式
            if (params.solveType){
                  SolveType solveType = SolveType.get(Integer.valueOf(params.solveType))
                  eq('solveType', solveType)
            }
		}
       render(view: "list", model: [errorTradeInstanceList: searchlist, errorTradeInstanceTotal: searchlist.totalCount, params: params, tp:tp , stores: stores])
	}

	/**
	 * 导入异常单首页
	 */
	def importIndex = {
        def tp = params.storeType
        log.debug tp
        tp = Boolean.parseBoolean(tp)
        render(view:"importIndex", model:[tp:tp ] ) }

	/**
	 * 导入异常单
	 */
	def importExcel = {
		List<ErrorTrade> list
        def tp = params.storeType
        log.debug tp
		if(params.excel){
			request.setCharacterEncoding("gbk")
			def f = request.getFile('excel')
			log.debug "file size: " + f.getSize()
			if(!f.empty) {
				log.debug "获取excel中的数据"

				try{
					list = dataImportService.importErrorTrade(f.getInputStream())
					log.debug "list.size = " + list.size()
				}catch(Exception e){
					log.error e
					flash.message = '导入的文件类型或格式不正确'
					render(view:"importIndex")
					return
				}
			}
		}

		// render(view:"importIndex")
		def listCount = list ? list.size() : 0
		flash.message = "共上传成功${listCount}条数据！"
		redirect(action: "list", params:[storeType: tp] )
	}
	
	/**
	 * 重置所有商品的sku到errortrade的itemskus字段
	 * 该搜索功能上线时使用
	 */
	def resetAllItemSKUs = {
		List<ErrorTrade> list = ErrorTrade.getAll()
		list.each{
			def tmpErrorTrade = it
			def items = it.items
			items.each {
				def item = it
				tmpErrorTrade.itemSKUs += (item.sku + " ")
			}
			if (!tmpErrorTrade.hasErrors() && tmpErrorTrade.save(flush: true)) {
                log.info("save success.")
			}else{
				log.info("save error.")
			}
		}
	}
}
