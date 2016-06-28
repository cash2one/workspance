package com.nala.csd

class ChajianCodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def userOperateService
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [chajianCodeInstanceList: ChajianCode.list(params), chajianCodeInstanceTotal: ChajianCode.count()]
    }

    def create = {
        def chajianCodeInstance = new ChajianCode()
        chajianCodeInstance.properties = params
        return [chajianCodeInstance: chajianCodeInstance]
    }

    def save = {
        def chajianCodeInstance = new ChajianCode(params)
        if (chajianCodeInstance.save(flush: true)) {
            userOperateService.operateMethodSelect(chajianCodeInstance ,this.actionName)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), chajianCodeInstance.id])}"
            redirect(action: "show", id: chajianCodeInstance.id)
        }
        else {
            render(view: "create", model: [chajianCodeInstance: chajianCodeInstance])
        }
    }

    def show = {
        def chajianCodeInstance = ChajianCode.get(params.id)
        if (!chajianCodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), params.id])}"
            redirect(action: "list")
        }
        else {
            [chajianCodeInstance: chajianCodeInstance]
        }
    }

    def edit = {
        def chajianCodeInstance = ChajianCode.get(params.id)
        if (!chajianCodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [chajianCodeInstance: chajianCodeInstance]
        }
    }

    def update = {
        def chajianCodeInstance = ChajianCode.get(params.id)
        if (chajianCodeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (chajianCodeInstance.version > version) {
                    chajianCodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'chajianCode.label', default: 'ChajianCode')] as Object[], "Another user has updated this ChajianCode while you were editing")
                    render(view: "edit", model: [chajianCodeInstance: chajianCodeInstance])
                    return
                }
            }
            chajianCodeInstance.properties = params
            if (!chajianCodeInstance.hasErrors() && chajianCodeInstance.save(flush: true)) {
                userOperateService.operateMethodSelect(chajianCodeInstance ,this.actionName)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), chajianCodeInstance.id])}"
                redirect(action: "show", id: chajianCodeInstance.id)
            }
            else {
                render(view: "edit", model: [chajianCodeInstance: chajianCodeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def chajianCodeInstance = ChajianCode.get(params.id)
        if (chajianCodeInstance) {
            try {
                chajianCodeInstance.delete(flush: true)
                userOperateService.operateMethodSelect(chajianCodeInstance ,this.actionName)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chajianCode.label', default: 'ChajianCode'), params.id])}"
            redirect(action: "list")
        }
    }
}
