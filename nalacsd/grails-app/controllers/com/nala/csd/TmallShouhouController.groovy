package com.nala.csd

import com.nala.common.utils.DateUtils
import com.nala.csd.jinGuanShouHou.ReturnGoods
import com.nala.csd.jinGuanShouHou.Resend
import com.nala.csd.jinGuanShouHou.Remit
import com.nala.csd.jinGuanShouHou.Refund
import com.nala.csd.common.utils.ParamsUtil

class TmallShouhouController {
	
	def dataExportService
	
	def tmallShouhouCookieService

    def userOperateService

    def springSecurityService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
		params.sort = params.sort ? params.sort : "dateCreated"
		params.order = params.order ? params.order : "desc"

        log.debug(params)
        def tmallShouhouInstanceList
        def stores
        boolean tp = false
        if (params?.tp && params?.tp == 'true'){
            tp = true
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }

        log.debug('tpStores.size = ' + stores.size())

        if (stores){
            tmallShouhouInstanceList = TmallShouhou.createCriteria().list(params) {
                or{
                    stores.each{
                        eq("store", it)
                        log.debug it
                    }
                }
            }
        }

       log.debug('tmallShouhouInstanceList.size() = ' + tmallShouhouInstanceList?.size())
       def tmallNum=0
        if (tmallShouhouInstanceList){
            tmallNum = tmallShouhouInstanceList.totalCount
        }

		[tmallShouhouInstanceList: tmallShouhouInstanceList, tmallShouhouInstanceTotal: tmallNum, tp: tp, stores: stores, params: params]
	}

	def create = {
		def tmallShouhouInstance = new TmallShouhou()
		tmallShouhouInstance.properties = params
		
		// cookie预存店铺id
		def storeId = request.getCookie("tmallShouhou.store.id")
		if (storeId){
			def store = Store.get(storeId)
			if (store){
				tmallShouhouInstance.store = store
			}
		}
		
		// cookie预存创建人
		def createCSId = request.getCookie("tmallShouhou.createCS.id")
		if (createCSId){
			Hero hero = Hero.get(createCSId)
			if (hero){
				tmallShouhouInstance.createCS = hero
			}
		}

        def stores
        boolean tp =false
        log.info("当前tp-->${params?.tp}")
        if (params?.tp && params.tp == 'true'){
            tp = true
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }

        def userId = springSecurityService?.currentUser?.id

        def questionTypeData = ""
        QuestionType.list().each {
            questionTypeData += "\""
            questionTypeData += it.questionDescription
            questionTypeData += "\", "
        }

        log.debug ("问题类型" + questionTypeData)
        log.info("tp-->${tp}")
		return [tmallShouhouInstance: tmallShouhouInstance, tp: tp, questionTypeData: questionTypeData ,stores: stores, params: params, userId: userId]
	}

	def save = {

        def solveType
        if (params.solveType){
            solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
        }
        params.solveType = solveType
        def tmallShouhouInstance = new TmallShouhou(params)
        def stores
        boolean tp = false
        if (params?.tp && params.tp == 'true'){
            tp = true
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }
        // 问题类型快速选择
        if (params.questionStatusDescription){
            def questionType = QuestionType.findByQuestionDescription(params.questionStatusDescription)
            if (questionType){
                tmallShouhouInstance.questionStatus = questionType
            }else{
                flash.message = "未找到匹配的问题类型"
                render(view: "create", model: [tmallShouhouInstance: jinGuanShouHouInstance])
                return
            }
        }

		// 写cookie
		tmallShouhouCookieService.setTmallShouhouToCookie(tmallShouhouInstance, response)

        // 处理方式选择了"换货补发" or "退货打款", 增加退货信息
        def returnGoods
        if (params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("4")))){
            returnGoods = new ReturnGoods(params)

            if (params.returnDateStr){
                Date returnDate = DateUtils.parseToDayDate(params.returnDateStr)
                returnGoods.returnDate = returnDate
            }

            if (params.returnWuliuNo){
                returnGoods.wuliuNo = params.returnWuliuNo
            }

            if (params.postageNumStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.postageNumStr)
                    fNum = fNum * 100
                    returnGoods.postageNum = fNum.intValue()
                }catch(Exception e){
                    log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                    flash.message = "退货邮费金额错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, returnGoods: returnGoods, tp: tp, stores: stores, params: params])
                    return
                }
            }

            if (returnGoods.detail
                    || returnGoods.returnDate
                    || returnGoods.wuliuNo
                    || returnGoods.postageRole
                    || returnGoods.postageNum){
                if (!returnGoods.save(flush: true)){
                    returnGoods.errors.each {
                        log.error it
                    }
                    flash.message = "保存退货信息失败！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, returnGoods: returnGoods, tp: tp, stores: stores, params: params])
                    return
                }else{
                    userOperateService.operateMethodSelect(returnGoods ,this.actionName)
                }
                tmallShouhouInstance.returnGoods = returnGoods
            }
        }

        // 处理方式选择了“直接补发” or “换货补发”，增加补发信息
        def resend
        if (params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("2")))){
            resend = new Resend(params)

            if (params.bufaDateStr){
                Date returnDate = DateUtils.parseToDayDate(params.bufaDateStr)
                resend.date = returnDate
            }

            if (params.bufaWuliuNo){
                resend.wuliuNo = params.bufaWuliuNo
            }

            if (params.bufaPostageNumStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.bufaPostageNumStr)
                    fNum = fNum * 100
                    resend.postageNum = fNum.intValue()
                }catch(Exception e){
                    log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                    flash.message = "补发邮费金额错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, resend: resend, tp: tp, stores: stores, params: params])

                    return
                }
            }

            if (resend.date
                    || resend.wuliuNo
                    || resend.postageNum
                    || resend.goodsDetail
                    || resend.address
                    || resend.resender){
                if (!resend.save(flush: true)){
                    resend.errors.each {
                        log.error it
                    }
                    flash.message = "保存补发信息失败！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, resend: resend, tp: tp, stores: stores, params: params])
                    return
                }else{
                    userOperateService.operateMethodSelect(resend ,this.actionName)
                }
                tmallShouhouInstance.resend = resend
            }

        }

        // 处理方式选择了“退货打款” or “退货退款” or “直接打款”，增加打款信息
        def remit
        if (params.solveType
                && (params.solveType.id.equals(Long.parseLong("4")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("6")) || params.solveType.id.equals(Long.parseLong("3")))){
            remit = new Remit(params)

            if (params.remitDateStr){
                Date remitDate = DateUtils.parseToDate(params.remitDateStr)
                remit.remitDate = remitDate
            }

            if (params.remitNumProductStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumProductStr)
                    fNum = fNum * 100
                    remit.remitNumProduct = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumProductStr = ${params.remitNumProductStr} to double error."
                    flash.message = "打款金额(产品)错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit, tp: tp, stores: stores, params: params])

                    return
                }
            }
            if (params.remitNumPostageStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumPostageStr)
                    fNum = fNum * 100
                    remit.remitNumPostage = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumPostageStr = ${params.remitNumPostageStr} to double error."
                    flash.message = "打款金额(邮费)错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit, tp: tp, stores: stores, params: params])

                    return
                }
            }
            if (params.remitNumCommissionStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.remitNumCommissionStr)
                    fNum = fNum * 100
                    remit.remitNumCommission = fNum.intValue()
                }catch(Exception e){
                    log.error "params.remitNumCommissionStr = ${params.remitNumCommissionStr} to double error."
                    flash.message = "打款手续费错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit, tp: tp, stores: stores, params: params])

                    return
                }
            }

            if (remit.remitDate
                    || remit.remitNumPostage
                    || remit.remitNumProduct
                    || remit.remitNumCommission
                    || remit.remitOperator){
                if (!remit.save(flush: true)){
                    remit.errors.each {
                        log.error it
                    }
                    flash.message = "保存打款信息失败！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit, tp: tp, stores: stores, params: params])
                    return
                } else{
                    userOperateService.operateMethodSelect(remit ,this.actionName)
                }
                tmallShouhouInstance.remit = remit
            }
        }

        // 处理方式选择了“退货退款” or “直接退款”，增加退款信息
        def refund
        if (params.solveType && ( params.solveType.id.equals(Long.parseLong("7")) || params.solveType.id.equals(Long.parseLong("5")))){
            refund = new Refund(params)

            if (params.refundDateStr){
                Date refundDate = DateUtils.parseToDate(params.refundDateStr)
                refund.refundDate = refundDate
            }

            if (params.refundNumProductStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.refundNumProductStr)
                    fNum = fNum * 100
                    refund.refundNumProduct = fNum.intValue()
                }catch(Exception e){
                    log.error "params.refundNumProductStr = ${params.refundNumProductStr} to double error."
                    flash.message = "退款金额(产品)错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, refund: refund, tp: tp, stores: stores, params: params])

                    return
                }
            }

            if (params.refundNumPostageStr){
                Float fNum = 0.0
                try {
                    fNum = Float.parseFloat(params.refundNumPostageStr)
                    fNum = fNum * 100
                    refund.refundNumPostage = fNum.intValue()
                }catch(Exception e){
                    log.error "params.refundNumPostageStr = ${params.refundNumPostageStr} to double error."
                    flash.message = "退款金额(邮费)错误！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, refund: refund, tp: tp, stores: stores, params: params])

                    return
                }
            }

            if (refund.refundDate
                    || refund.refundNumProduct
                    || refund.refundNumPostage
                    || refund.refundOperator){
                if (!refund.save(flush: true)){
                   refund.errors.each {
                        log.error it
                    }
                    flash.message = "保存退款信息失败！"
                    render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, refund: refund, tp: tp, stores: stores, params: params])
                    return
                }else{
                    userOperateService.operateMethodSelect(refund,this.actionName)
                }
                tmallShouhouInstance.refund = refund
            }
        }
		
		if (tmallShouhouInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(tmallShouhouInstance  ,this.actionName)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), tmallShouhouInstance.id])}"
			redirect(action: "show", id: tmallShouhouInstance.id)
		}
		else {
			render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, tp: tp, stores: stores, params: params])
		}
	}

	def show = {
		def tmallShouhouInstance = TmallShouhou.get(params.id)

		if (!tmallShouhouInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), params.id])}"
			redirect(action: "list")
		}
		else {
			[tmallShouhouInstance: tmallShouhouInstance]
		}
	}

	def edit = {
		def tmallShouhouInstance = TmallShouhou.get(params.id)
		if (!tmallShouhouInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), params.id])}"
			redirect(action: "list")
		}
		else {
            def userId = springSecurityService?.currentUser?.id
			return [tmallShouhouInstance: tmallShouhouInstance, userId: userId]
		}
	}

	def update = {
        def tmallShouhouInstance = TmallShouhou.get(params.id)
        if (params.solveType){
            JinTmallSolveType solveType = JinTmallSolveType.get(Integer.valueOf(params.solveType))
            params.solveType = solveType
        }
		if (tmallShouhouInstance) {
            // 退货信息
            def returnGoods
            if (tmallShouhouInstance.returnGoods
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("4")))){
                returnGoods = tmallShouhouInstance.returnGoods
                if (!returnGoods){
                    returnGoods = new ReturnGoods()
                }
                returnGoods.properties = params

                if (params.returnDateStr){
                    Date returnDate = DateUtils.parseToDayDate(params.returnDateStr)
                    returnGoods.returnDate = returnDate
                }

                if (params.returnWuliuNo){
                    returnGoods.wuliuNo = params.returnWuliuNo
                }

                if (params.postageNumStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.postageNumStr)
                        fNum = fNum * 100
                        returnGoods.postageNum = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                        flash.message = "退货邮费金额错误！"
                        render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance, returnGoods: returnGoods])
                        return
                    }
                }

                if (returnGoods.detail
                        || returnGoods.returnDate
                        || returnGoods.wuliuNo
                        || returnGoods.postageRole
                        || returnGoods.postageNum){
                    if (!returnGoods.save(flush: true)){
                        returnGoods.errors.each {
                            log.error it
                        }
                        flash.message = "保存退货信息失败！"
                        render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance, returnGoods: returnGoods])
                        return
                    }else{
                        userOperateService.operateMethodSelect(returnGoods,this.actionName)
                    }
                    tmallShouhouInstance.returnGoods = returnGoods
                }
            }

            // 补发信息
            def resend
            if (tmallShouhouInstance.resend
                    || params.solveType && ( params.solveType.id.equals(Long.parseLong("3")) || params.solveType.id.equals(Long.parseLong("2")))){
                resend = tmallShouhouInstance.resend
                if (!resend){
                    resend = new Resend()
                }
                resend.properties = params

                if (params.bufaDateStr){
                    Date returnDate = DateUtils.parseToDayDate(params.bufaDateStr)
                    resend.date = returnDate
                }

                if (params.bufaWuliuNo){
                    resend.wuliuNo = params.bufaWuliuNo
                }

                if (params.bufaPostageNumStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.bufaPostageNumStr)
                        fNum = fNum * 100
                        resend.postageNum = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.postageNumStr = ${params.postageNumStr} to double error."
                        flash.message = "补发邮费金额错误！"
                        if (returnGoods){
                            render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance, returnGoods: returnGoods, resend: resend])
                        }else{
                            render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance, resend: resend])
                        }

                        return
                    }
                }

                if (resend.date
                        || resend.wuliuNo
                        || resend.postageNum
                        || resend.goodsDetail
                        || resend.address
                        || resend.resender){
                    if (!resend.save(flush: true)){
                       resend.errors.each {
                            log.error it
                        }
                        flash.message = "保存补发信息失败！"
                        render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance, resend: resend])
                        return
                    } else{
                        userOperateService.operateMethodSelect(resend ,this.actionName)
                    }
                    tmallShouhouInstance.resend = resend
                }
            }

            // 打款信息
            def remit
            if (tmallShouhouInstance.remit
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("4")) || params.solveType.id.equals(Long.parseLong("5")) || params.solveType.id.equals(Long.parseLong("6")) || params.solveType.id.equals(Long.parseLong("3")))){
                remit = tmallShouhouInstance.remit
                if (!remit){
                    remit = new Remit()
                }
                remit.properties = params

                if (params.remitDateStr){
                    Date remitDate = DateUtils.parseToDate(params.remitDateStr)
                    remit.remitDate = remitDate
                }

                if (params.remitNumProductStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumProductStr)
                        fNum = fNum * 100
                        remit.remitNumProduct = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumProductStr = ${params.remitNumProductStr} to double error."
                        flash.message = "打款金额(产品)错误！"
                        render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit])

                        return
                    }
                }
                if (params.remitNumPostageStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumPostageStr)
                        fNum = fNum * 100
                        remit.remitNumPostage = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumPostageStr = ${params.remitNumPostageStr} to double error."
                        flash.message = "打款金额(邮费)错误！"
                        render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit])

                        return
                    }
                }
                if (params.remitNumCommissionStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.remitNumCommissionStr)
                        fNum = fNum * 100
                        remit.remitNumCommission = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.remitNumCommissionStr = ${params.remitNumCommissionStr} to double error."
                        flash.message = "打款手续费错误！"
                        render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit])

                        return
                    }
                }

                if (remit.remitDate
                        || remit.remitNumPostage
                        || remit.remitNumProduct
                        || remit.remitNumCommission
                        || remit.remitOperator){
                    if (!remit.save(flush: true)){
                        remit.errors.each {
                            log.error it
                        }
                        flash.message = "保存打款信息失败！"
                        render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance, remit: remit])
                        return
                    } else{
                        userOperateService.operateMethodSelect(remit ,this.actionName)
                    }
                    tmallShouhouInstance.remit = remit
                }

            }

            // 退款信息
            def refund
            if (tmallShouhouInstance.refund
                    || params.solveType && (params.solveType.id.equals(Long.parseLong("7")) || params.solveType.id.equals(Long.parseLong("5")))){
                refund = tmallShouhouInstance.refund
                if (!refund){
                    refund = new Refund()
                }
                refund.properties = params

                if (params.refundDateStr){
                    Date refundDate = DateUtils.parseToDate(params.refundDateStr)
                    refund.refundDate = refundDate
                }

                if (params.refundNumProductStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.refundNumProductStr)
                        fNum = fNum * 100
                        refund.refundNumProduct = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.refundNumProductStr = ${params.refundNumProductStr} to double error."
                        flash.message = "退款金额(产品)错误！"
                        render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, refund: refund])

                        return
                    }
                }

                if (params.refundNumPostageStr){
                    Float fNum = 0.0
                    try {
                        fNum = Float.parseFloat(params.refundNumPostageStr)
                        fNum = fNum * 100
                        refund.refundNumPostage = fNum.intValue()
                    }catch(Exception e){
                        log.error "params.refundNumPostageStr = ${params.refundNumPostageStr} to double error."
                        flash.message = "退款金额(邮费)错误！"
                        render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, refund: refund])

                        return
                    }
                }

                if (refund.refundDate
                        || refund.refundNumPostage
                        || refund.refundNumProduct
                        || refund.refundOperator){
                    if (!refund.save(flush: true)){
                       refund.errors.each {
                            log.error it
                        }
                        flash.message = "保存退款信息失败！"
                        render(view: "create", model: [tmallShouhouInstance: tmallShouhouInstance, refund: refund])
                        return
                    } else{
                        userOperateService.operateMethodSelect(refund  ,this.actionName)
                    }
                    tmallShouhouInstance.refund = refund
                }
            }
            
			if (params.version) {
				def version = params.version.toLong()
				if (tmallShouhouInstance.version > version) {

					tmallShouhouInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'tmallShouhou.label', default: 'TmallShouhou')]
					as Object[], "Another user has updated this TmallShouhou while you were editing")
					render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance])
					return
				}
			}

            // 问题类型选择了错发、漏发，并且有处理人，一定要填问题原因
            Integer qs = Integer.parseInt(params['questionStatus.id'])
            if (qs == 19 || qs == 18){
                if (params?.solveCS?.id){
                    if (!params.questionReason.id){
                        flash.message = "注意：问题类型是错发或者漏发，并且有处理人，一定要填问题原因！"
                        render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance])
                        return
                    }
                }

            }


			tmallShouhouInstance.properties = params
			if (!tmallShouhouInstance.hasErrors() && tmallShouhouInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(tmallShouhouInstance ,this.actionName)
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), tmallShouhouInstance.id])}"
                def userId = springSecurityService?.currentUser?.id
				redirect(action: "show", id: tmallShouhouInstance.id, userId:userId)
			}
			else {
				render(view: "edit", model: [tmallShouhouInstance: tmallShouhouInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def tmallShouhouInstance = TmallShouhou.get(params.id)
        log.debug("打印信息-->${params}")
		if (tmallShouhouInstance) {
			try {
				tmallShouhouInstance.delete(flush: true)
                userOperateService.operateMethodSelect(tmallShouhouInstance  ,this.actionName)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), params.id])}"
				if (params?.tp){
                    redirect(action: "list", params: [tp: 'true'])
                }else{
                    redirect(action: "list")
                }
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tmallShouhou.label', default: 'TmallShouhou'), params.id])}"
            if (params?.tp){
                redirect(action: "list", params: [tp: 'true'])
            }else{
                redirect(action: "list")
            }
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

        def stores
        boolean tp = false
        def tpStr = params.tp
        if (tpStr == 'true'){
            tp = true
        }

        if (tp){
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }
        log.debug('tpStores.size = ' + stores.size())

        def searchlist = getSearchList(params, stores)

        render(view: "list", model: [tmallShouhouInstanceList: searchlist, tmallShouhouInstanceTotal: searchlist.totalCount, params: params, tp: tp, stores: stores])
	}

    def getSearchList(params, stores){
        def returnGoodsList
        if (params.wuliuNo){
            returnGoodsList = ReturnGoods.createCriteria().list {
                like('wuliuNo', "%" + params.wuliuNo + "%")
            }
        }


        def searchlist = TmallShouhou.createCriteria().list(params){
            if (stores){
                or{
                    stores.each{
                        eq("store", it)
                    }
                }
            }

            // 顾客id
            if (params.userId){
                like('userId', "%" + params.userId + "%")
            }
            // 订单id
            if (params.tradeId){
                like('tradeId', "%" + params.tradeId + "%")
            }
            // 登记人
            if (params.createCSId){
                Hero hero = Hero.get(params.createCSId)
                eq('createCS', hero)
            }
            // 店铺
            if (params.store_id){
                Store store = Store.get(params.store_id)
                eq('store', store)
            }
            // 问题类型
            if (params.questionStatusId){
                int typeId = Integer.parseInt(params.questionStatusId)
                def type = QuestionType.get(typeId)
                eq('questionStatus', type)
            }
            // 商品明细
            if (params.itemDetail){
                like('itemDetail', "%" + params.itemDetail + "%")
            }
            // 处理方式
            if (params.solveType){
                JinTmallSolveType cur = JinTmallSolveType.get(Integer.valueOf(params.solveType))
                eq('solveType', cur)
            }
            // 货款操作记录
            if (params.moneyRecordType){
                MoneyRecordTypeEnum cur = MoneyRecordTypeEnum.getByCode(params.moneyRecordType)
                eq('moneyRecordType', cur)
            }
            // 信息错误说明
            if (params.wrongReason){
                like('wrongReason', "%" + params.wrongReason + "%")
            }
            // 完成进度
            if (params.solveStatus){
                SolveStatusEnum cur = SolveStatusEnum.getByCode(params.solveStatus)
                eq('solveStatus', cur)
            }
            // 未完成说明
            if (params.noSolveReason){
                like('noSolveReason', "%" + params.noSolveReason + "%")
            }
            // 退回快递和单号
            if (params.wuliuNo){
                if (returnGoodsList){
                    or{
                        like('wuliuNo', "%" + params.wuliuNo + "%")
                        returnGoodsList.each{
                            eq("returnGoods", it)
                        }
                    }
                }else{
                    like('wuliuNo', "%" + params.wuliuNo + "%")
                }
            }

            // 登记时间
            if (params.dateCreatedStart && params.dateCreatedEnd){
                Date start = DateUtils.parseToDates(params.dateCreatedStart)
                Date end = DateUtils.parseToDates(params.dateCreatedEnd)
                ge('dateCreated',start)
                le('dateCreated',end)
            }

        }

        return searchlist
    }

    final static Map labels = [
            "dateCreated":"创建时间",
            "createCS.name":"创建人",
            "store.name":"店铺名称",
            "userId":"顾客id",
            "tradeId":"订单编号",
            "questionStatus.questionDescription":"问题类型",
            "questionReason.name":"问题原因",
            "itemDetail":"商品明细",
            "solveType.name":"处理方式",
            "moneyRecordType.description":"货款操作记录",
            "wrongReason":"信息错误说明",
            "solveStatus.description":"完成进度",
            "noSolveReason":"未完成说明",
            "wuliuNo":"退回快递和单号",
            "lianxiJiange":"创建时间到联系时间的间隔",
            "jiejueJiange":"联系时间到解决时间的间隔",
            "returnGoods.detail":"收到退货明细",
            "returnGoods.returnDate":"收到退货日期",
            "returnGoods.wuliuNo":"退货快递和单号",
            "returnGoods.used":"是否使用",
            "returnGoods.postageRole":"邮费承担方",
            "returnGoods.postageNum":"邮费金额(分)",
            "resend.date":"补发日期",
            "resend.wuliuNo":"补发单号",
            "resend.postageNum":"补发邮费(分)",
            "resend.goodsDetail":"补发产品明细",
            "resend.address":"补发地址",
            "resend.resender.name":"补发人",
            "remit.remitDate":"打款时间",
            "remit.remitNum":"打款金额(分)",
            "remit.remitOperator.name":"打款操作人",
            "remit.remitNumProduct":"打款金额(产品)(分)",
            "remit.remitNumPostage":"打款金额(邮费)(分)",
            "remit.remitNumCommission":"打款手续费(分)",
            "refund.refundDate":"退款时间",
            "refund.refundNum":"退款金额(分)",
            "refund.refundOperator.name":"退款操作人",
            "refund.refundNumProduct":"退款金额(产品)(分)",
            "refund.refundNumPostage":"退款金额(邮费)(分)"
    ]

    final static List fields = [
            "dateCreated",
            "createCS.name",
            "store.name",
            "userId",
            "tradeId",
            "questionStatus.questionDescription",
            "questionReason.name",
            "itemDetail",
            "solveType.name",
            "moneyRecordType.description",
            "wrongReason",
            "solveStatus.description",
            "noSolveReason",
            "wuliuNo",
            "jiejueJiange",
            "returnGoods.detail",
            "returnGoods.returnDate",
            "returnGoods.wuliuNo",
            "returnGoods.used",
            "returnGoods.postageRole",
            "returnGoods.postageNum",
            "resend.date",
            "resend.wuliuNo",
            "resend.postageNum",
            "resend.goodsDetail",
            "resend.address",
            "resend.resender.name",
            "remit.remitDate",
            "remit.remitNum",
            "remit.remitOperator.name",
            "remit.remitNumProduct",
            "remit.remitNumPostage",
            "remit.remitNumCommission",
            "refund.refundDate",
            "refund.refundNum",
            "refund.refundOperator.name",
            "refund.refundNumProduct",
            "refund.refundNumPostage"

    ]
	/**
	 * 导出所有数据
	 */
	def export = {
        log.debug '------------导出数据开始'
        log.debug params
        def stores

        boolean tp = false
        def tpStr = params.tp
        if (tpStr == 'true'){
            tp = true
        }

        if (tp){
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }

        log.debug('stores.size() = ' + stores.size())

		Date daysAgo = DateUtils.getDaysAgo(14)
		def list = TmallShouhou.createCriteria().list(){
            ge('dateCreated',daysAgo)

            if (stores){
                or{
                    stores.each{
                        eq("store", it)
                    }
                }
            }
        }

		response.setHeader("Content-disposition", "attachment; filename=TmallShouhou.${params.extension}")

		dataExportService.execlExport(params,request,response,list,fields,labels)
	}

    /**
     * 导出当前搜索数据
     */
    def exportCurData = {
        Map searchParams = ParamsUtil.resetParamsMap(params.searchParams)

        log.debug '导出当前搜索数据'
        log.debug params
        log.debug searchParams
        def stores

        boolean tp = false
        def tpStr = searchParams.tp
        if (tpStr == 'true'){
            tp = true
        }

        if (tp){
            stores = Store.findAllByTp(true)
        }else{
            stores = Store.findAllByTp(false)
        }

        log.debug('stores.size() = ' + stores.size())

        searchParams["max"] = 10000
        def list = getSearchList(searchParams, stores)

        log.debug("list.size() = " + list.size())
        log.debug("params.extension = " + params.extension)

        response.setHeader("Content-disposition", "attachment; filename=TmallShouhou.${params.extension}")

        dataExportService.execlExport(params,request,response,list,fields,labels)

    }
}
