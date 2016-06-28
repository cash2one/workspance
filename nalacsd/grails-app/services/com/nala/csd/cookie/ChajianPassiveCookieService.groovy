package com.nala.csd.cookie

import com.nala.csd.ChajianPassive

class ChajianPassiveCookieService {

    static transactional = true

    def serviceMethod() {

    }
	
	/**
	 * 把查件被动表中填写的部分信息写入到cookie
	 * 有效期30天
	 * @param chajianPassive
	 * @return
	 */
	def setChajianPassiveToCookie(ChajianPassive chajianPassive, response){
		if (chajianPassive){
			if (chajianPassive.store){
				// 店铺
				response.setCookie("chajianPassive.store.id", chajianPassive.store.id + "", 60*60*24*30)
			}
			if (chajianPassive.contactStatus){
				// 联系优先级
				response.setCookie("chajianPassive.contactStatus.code", chajianPassive.contactStatus.code + "", 60*60*24*30)
			}
			if (chajianPassive.createCS){
				// 创建人
				response.setCookie("chajianPassive.createCS.id", chajianPassive.createCS.id + "", 60*60*24*30)
			}
			if (chajianPassive.chajianCode){
				// 查件代码
				response.setCookie("chajianPassive.chajianCode.id", chajianPassive.chajianCode.id + "", 60*60*24*30)
			}
			if (chajianPassive.solveCS){
				// 处理人
				response.setCookie("chajianPassive.solveCS.id", chajianPassive.solveCS.id + "", 60*60*24*30)
			}
			if (chajianPassive.notifyMode){
				// 通知方式
				response.setCookie("chajianPassive.notifyMode.id", chajianPassive.notifyMode.id + "", 60*60*24*30)
			}
			if (chajianPassive.followCS){
				// 跟踪人
				response.setCookie("chajianPassive.followCS.id", chajianPassive.followCS.id + "", 60*60*24*30)
			}
		}
		
	}
}
