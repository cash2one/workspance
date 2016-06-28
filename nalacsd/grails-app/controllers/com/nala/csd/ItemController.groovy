package com.nala.csd

class ItemController {
	
	def errorTradeService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        [itemInstanceList: Item.list(params), itemInstanceTotal: Item.count()]
    }

    def create = {
        def itemInstance = new Item()
        itemInstance.properties = params
        return [itemInstance: itemInstance, errorTradeId: params.errorTradeId]
    }

    def save = {
        def itemInstance = new Item(params)
        if (itemInstance.save(flush: true)) {
			
			// item关联到error trade			
			ErrorTrade errorTrade = ErrorTrade.get(params.errorTradeId)
			errorTrade = errorTradeService.addItemToErrorTrade(itemInstance, errorTrade)
			
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])}"
            redirect(controller:"errorTrade", action: "edit", id: params.errorTradeId)
        }
        else {
            render(view: "create", model: [itemInstance: itemInstance, errorTradeId: params.errorTradeId])
        }
    }

    def show = {
        def itemInstance = Item.get(params.id)
        if (!itemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(action: "list")
        }
        else {
            [itemInstance: itemInstance]
        }
    }

    def edit = {
        def itemInstance = Item.get(params.id)
        if (!itemInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(controller:"errorTrade", action: "edit", id: params.errorTradeId)
        }
        else {
            return [itemInstance: itemInstance, errorTradeId: params.errorTradeId]
        }
    }

    def update = {
        def itemInstance = Item.get(params.id)
        if (itemInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (itemInstance.version > version) {
                    
                    itemInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'item.label', default: 'Item')] as Object[], "Another user has updated this Item while you were editing")
                    render(view: "edit", model: [itemInstance: itemInstance, errorTradeId: params.errorTradeId])
                    return
                }
            }
            itemInstance.properties = params
            if (!itemInstance.hasErrors() && itemInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])}"
                redirect(controller:"errorTrade", action: "edit", id: params.errorTradeId)
            }
            else {
                render(view: "edit", model: [itemInstance: itemInstance, errorTradeId: params.errorTradeId])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(controller:"errorTrade", action: "edit", id: params.errorTradeId)
        }
    }

    def delete = {
        def itemInstance = Item.get(params.id)
        if (itemInstance) {
            try {
                itemInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
            redirect(action: "list")
        }
    }
}
