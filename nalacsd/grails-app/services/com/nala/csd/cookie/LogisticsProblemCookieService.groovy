package com.nala.csd.cookie

import com.nala.csd.LogisticsProblem

class LogisticsProblemCookieService {

	static transactional = true

	def serviceMethod() {
	}

	/**
	 * 把快递问题件中填写的部分信息写入到cookie
	 * 有效期30天
	 * @param logisticsProblem
	 * @return
	 */
	def setlogisticsProblemToCookie(LogisticsProblem logisticsProblem, response){
		if (logisticsProblem){
			if (logisticsProblem.store){
				// 店铺
				response.setCookie("logisticsProblem.store.id", logisticsProblem.store.id + "", 60*60*24*30)
			}
			if (logisticsProblem.solveCS){
				// 处理人
				response.setCookie("logisticsProblem.solveCS.id", logisticsProblem.solveCS.id + "", 60*60*24*30)
			}
			if (logisticsProblem.followCS){
				// 跟踪人
				response.setCookie("logisticsProblem.followCS.id", logisticsProblem.followCS.id + "", 60*60*24*30)
			}
			if (logisticsProblem.notifyMode){
				// 通知方式
				response.setCookie("logisticsProblem.notifyMode.id", logisticsProblem.notifyMode.id + "", 60*60*24*30)
			}

		}
	}
}
