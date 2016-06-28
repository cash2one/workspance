package com.nala.csd

class ErrorTradeService {

    static transactional = true

    def serviceMethod() {

    }
	
	/**
	 * 把一个item关联到error trade对象
	 * @param item
	 * @param errorTrade
	 * @return
	 */
	def addItemToErrorTrade(Item item, ErrorTrade errorTrade) {		
		// item关联到error trade
		
		if (item && errorTrade){
			def curItems = errorTrade.items
			if (curItems){
				// 已有items，要判断是否已存在相同的item
				boolean add = true
				curItems.each {
					if (item.sku == it.sku){
						add = false
					}
				}
				if (add){
					errorTrade.addToItems(item)
					errorTrade.itemSKUs += (item.sku + " ")
				}else{
					log.warn("关联失败，errorTrade id=${errorTrade.id} 已经存在相同的item sku=${item.sku}!")
				}
			}else{
				errorTrade.addToItems(item)
				errorTrade.itemSKUs += (item.sku + " ")
			}
		}
		
		return errorTrade
	}
}
