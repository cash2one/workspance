package com.nala.csd

class ExpressController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [expressInstanceList: Express.list(params), expressInstanceTotal: Express.count()]
    }

    def create = {
        def expressInstance = new Express()
        expressInstance.properties = params
        return [expressInstance: expressInstance]
    }

    def save = {
        def expressInstance = new Express(params)
        if (expressInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'express.label', default: 'Express'), expressInstance.id])}"
            redirect(action: "show", id: expressInstance.id)
        }
        else {
            render(view: "create", model: [expressInstance: expressInstance])
        }
    }

    def show = {
        def expressInstance = Express.get(params.id)
        if (!expressInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'express.label', default: 'Express'), params.id])}"
            redirect(action: "list")
        }
        else {
            [expressInstance: expressInstance]
        }
    }

    def edit = {
        def expressInstance = Express.get(params.id)
        if (!expressInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'express.label', default: 'Express'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [expressInstance: expressInstance]
        }
    }

    def update = {
        def expressInstance = Express.get(params.id)
        if (expressInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (expressInstance.version > version) {
                    
                    expressInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'express.label', default: 'Express')] as Object[], "Another user has updated this Express while you were editing")
                    render(view: "edit", model: [expressInstance: expressInstance])
                    return
                }
            }
            expressInstance.properties = params
            if (!expressInstance.hasErrors() && expressInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'express.label', default: 'Express'), expressInstance.id])}"
                redirect(action: "show", id: expressInstance.id)
            }
            else {
                render(view: "edit", model: [expressInstance: expressInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'express.label', default: 'Express'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def expressInstance = Express.get(params.id)
        if (expressInstance) {
            try {
                expressInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'express.label', default: 'Express'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'express.label', default: 'Express'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'express.label', default: 'Express'), params.id])}"
            redirect(action: "list")
        }
    }
}
