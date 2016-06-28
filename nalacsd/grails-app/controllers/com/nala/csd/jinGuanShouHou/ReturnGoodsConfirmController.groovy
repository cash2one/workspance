package com.nala.csd.jinGuanShouHou

class ReturnGoodsConfirmController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [returnGoodsConfirmInstanceList: ReturnGoodsConfirm.list(params), returnGoodsConfirmInstanceTotal: ReturnGoodsConfirm.count()]
    }

    def create = {
        def returnGoodsConfirmInstance = new ReturnGoodsConfirm()
        returnGoodsConfirmInstance.properties = params
        return [returnGoodsConfirmInstance: returnGoodsConfirmInstance]
    }

    def save = {
        def returnGoodsConfirmInstance = new ReturnGoodsConfirm(params)
        if (returnGoodsConfirmInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), returnGoodsConfirmInstance.id])}"
            redirect(action: "show", id: returnGoodsConfirmInstance.id)
        }
        else {
            render(view: "create", model: [returnGoodsConfirmInstance: returnGoodsConfirmInstance])
        }
    }

    def show = {
        def returnGoodsConfirmInstance = ReturnGoodsConfirm.get(params.id)
        if (!returnGoodsConfirmInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), params.id])}"
            redirect(action: "list")
        }
        else {
            [returnGoodsConfirmInstance: returnGoodsConfirmInstance]
        }
    }

    def edit = {
        def returnGoodsConfirmInstance = ReturnGoodsConfirm.get(params.id)
        if (!returnGoodsConfirmInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [returnGoodsConfirmInstance: returnGoodsConfirmInstance]
        }
    }

    def update = {
        def returnGoodsConfirmInstance = ReturnGoodsConfirm.get(params.id)
        if (returnGoodsConfirmInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (returnGoodsConfirmInstance.version > version) {

                    returnGoodsConfirmInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm')] as Object[], "Another user has updated this ReturnGoodsConfirm while you were editing")
                    render(view: "edit", model: [returnGoodsConfirmInstance: returnGoodsConfirmInstance])
                    return
                }
            }
            returnGoodsConfirmInstance.properties = params
            if (!returnGoodsConfirmInstance.hasErrors() && returnGoodsConfirmInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), returnGoodsConfirmInstance.id])}"
                redirect(action: "show", id: returnGoodsConfirmInstance.id)
            }
            else {
                render(view: "edit", model: [returnGoodsConfirmInstance: returnGoodsConfirmInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def returnGoodsConfirmInstance = ReturnGoodsConfirm.get(params.id)
        if (returnGoodsConfirmInstance) {
            try {
                returnGoodsConfirmInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'returnGoodsConfirm.label', default: 'ReturnGoodsConfirm'), params.id])}"
            redirect(action: "list")
        }
    }
}
