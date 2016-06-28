package com.nala.csd

class ExpressProblemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [expressProblemInstanceList: ExpressProblem.list(params), expressProblemInstanceTotal: ExpressProblem.count()]
    }

    def create = {
        def expressProblemInstance = new ExpressProblem()
        expressProblemInstance.properties = params
        return [expressProblemInstance: expressProblemInstance]
    }

    def save = {
        def expressProblemInstance = new ExpressProblem(params)
        if (expressProblemInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), expressProblemInstance.id])}"
            redirect(action: "show", id: expressProblemInstance.id)
        }
        else {
            render(view: "create", model: [expressProblemInstance: expressProblemInstance])
        }
    }

    def show = {
        def expressProblemInstance = ExpressProblem.get(params.id)
        if (!expressProblemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), params.id])}"
            redirect(action: "list")
        }
        else {
            [expressProblemInstance: expressProblemInstance]
        }
    }

    def edit = {
        def expressProblemInstance = ExpressProblem.get(params.id)
        if (!expressProblemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [expressProblemInstance: expressProblemInstance]
        }
    }

    def update = {
        def expressProblemInstance = ExpressProblem.get(params.id)
        if (expressProblemInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (expressProblemInstance.version > version) {
                    
                    expressProblemInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'expressProblem.label', default: 'ExpressProblem')] as Object[], "Another user has updated this ExpressProblem while you were editing")
                    render(view: "edit", model: [expressProblemInstance: expressProblemInstance])
                    return
                }
            }
            expressProblemInstance.properties = params
            if (!expressProblemInstance.hasErrors() && expressProblemInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), expressProblemInstance.id])}"
                redirect(action: "show", id: expressProblemInstance.id)
            }
            else {
                render(view: "edit", model: [expressProblemInstance: expressProblemInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def expressProblemInstance = ExpressProblem.get(params.id)
        if (expressProblemInstance) {
            try {
                expressProblemInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'expressProblem.label', default: 'ExpressProblem'), params.id])}"
            redirect(action: "list")
        }
    }
}
