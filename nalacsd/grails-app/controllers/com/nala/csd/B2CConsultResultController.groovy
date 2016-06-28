package com.nala.csd

class B2CConsultResultController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [b2CConsultResultInstanceList: B2CConsultResult.list(params), b2CConsultResultInstanceTotal: B2CConsultResult.count()]
    }

    def create = {
        def b2CConsultResultInstance = new B2CConsultResult()
        b2CConsultResultInstance.properties = params
        return [b2CConsultResultInstance: b2CConsultResultInstance]
    }

    def save = {
        def b2CConsultResultInstance = new B2CConsultResult(params)
        if (b2CConsultResultInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), b2CConsultResultInstance.id])}"
            redirect(action: "show", id: b2CConsultResultInstance.id)
        }
        else {
            render(view: "create", model: [b2CConsultResultInstance: b2CConsultResultInstance])
        }
    }

    def show = {
        def b2CConsultResultInstance = B2CConsultResult.get(params.id)
        if (!b2CConsultResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            [b2CConsultResultInstance: b2CConsultResultInstance]
        }
    }

    def edit = {
        def b2CConsultResultInstance = B2CConsultResult.get(params.id)
        if (!b2CConsultResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [b2CConsultResultInstance: b2CConsultResultInstance]
        }
    }

    def update = {
        def b2CConsultResultInstance = B2CConsultResult.get(params.id)
        if (b2CConsultResultInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (b2CConsultResultInstance.version > version) {
                    
                    b2CConsultResultInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult')] as Object[], "Another user has updated this B2CConsultResult while you were editing")
                    render(view: "edit", model: [b2CConsultResultInstance: b2CConsultResultInstance])
                    return
                }
            }
            b2CConsultResultInstance.properties = params
            if (!b2CConsultResultInstance.hasErrors() && b2CConsultResultInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), b2CConsultResultInstance.id])}"
                redirect(action: "show", id: b2CConsultResultInstance.id)
            }
            else {
                render(view: "edit", model: [b2CConsultResultInstance: b2CConsultResultInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def b2CConsultResultInstance = B2CConsultResult.get(params.id)
        if (b2CConsultResultInstance) {
            try {
                b2CConsultResultInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'b2CConsultResult.label', default: 'B2CConsultResult'), params.id])}"
            redirect(action: "list")
        }
    }
}
