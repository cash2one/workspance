package com.nala.csd.cookie

import com.nala.csd.ErrorTrade

class ErrorTradeCookieService {

	static transactional = true

	def serviceMethod() {
	}

	/**
	 * 把异常单中填写的部分信息写入到cookie
	 * 有效期30天
	 * @param errorTrade
	 * @return
	 */
	def seterrorTradeToCookie(ErrorTrade errorTrade, response){
		if (errorTrade){
			if (errorTrade.store){
				// 店铺
				response.setCookie("errorTrade.store.id", errorTrade.store.id + "", 60*60*24*30)
			}
			if (errorTrade.errorReason){
				// 异常原因
				response.setCookie("errorTrade.errorReason.id", errorTrade.errorReason.id + "", 60*60*24*30)
			}
			if (errorTrade.createCS){
				// 创建人
				response.setCookie("errorTrade.createCS.id", errorTrade.createCS.id + "", 60*60*24*30)
			}
			if (errorTrade.solveCS){
				// 处理人
				response.setCookie("errorTrade.solveCS.id", errorTrade.solveCS.id + "", 60*60*24*30)
			}
			if (errorTrade.solveType){
				// 处理人
				response.setCookie("errorTrade.solveType.id", errorTrade.solveType.id + "", 60*60*24*30)
			}

		}
	}
}
