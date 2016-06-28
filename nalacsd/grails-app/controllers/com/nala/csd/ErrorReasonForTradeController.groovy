package com.nala.csd

class ErrorReasonForTradeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [errorReasonForTradeInstanceList: ErrorReasonForTrade.list(params), errorReasonForTradeInstanceTotal: ErrorReasonForTrade.count()]
    }

    def create = {
        def errorReasonForTradeInstance = new ErrorReasonForTrade()
        errorReasonForTradeInstance.properties = params
        return [errorReasonForTradeInstance: errorReasonForTradeInstance]
    }

    def save = {
        def errorReasonForTradeInstance = new ErrorReasonForTrade(params)
        if (errorReasonForTradeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), errorReasonForTradeInstance.id])}"
            redirect(action: "show", id: errorReasonForTradeInstance.id)
        }
        else {
            render(view: "create", model: [errorReasonForTradeInstance: errorReasonForTradeInstance])
        }
    }

    def show = {
        def errorReasonForTradeInstance = ErrorReasonForTrade.get(params.id)
        if (!errorReasonForTradeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), params.id])}"
            redirect(action: "list")
        }
        else {
            [errorReasonForTradeInstance: errorReasonForTradeInstance]
        }
    }

    def edit = {
        def errorReasonForTradeInstance = ErrorReasonForTrade.get(params.id)
        if (!errorReasonForTradeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [errorReasonForTradeInstance: errorReasonForTradeInstance]
        }
    }

    def update = {
        def errorReasonForTradeInstance = ErrorReasonForTrade.get(params.id)
        if (errorReasonForTradeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (errorReasonForTradeInstance.version > version) {
                    
                    errorReasonForTradeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade')] as Object[], "Another user has updated this ErrorReasonForTrade while you were editing")
                    render(view: "edit", model: [errorReasonForTradeInstance: errorReasonForTradeInstance])
                    return
                }
            }
            errorReasonForTradeInstance.properties = params
            if (!errorReasonForTradeInstance.hasErrors() && errorReasonForTradeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), errorReasonForTradeInstance.id])}"
                redirect(action: "show", id: errorReasonForTradeInstance.id)
            }
            else {
                render(view: "edit", model: [errorReasonForTradeInstance: errorReasonForTradeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def errorReasonForTradeInstance = ErrorReasonForTrade.get(params.id)
        if (errorReasonForTradeInstance) {
            try {
                errorReasonForTradeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'errorReasonForTrade.label', default: 'ErrorReasonForTrade'), params.id])}"
            redirect(action: "list")
        }
    }
}
