package com.nala.csd.common.utils

class ParamsUtil {
	/**
	 * 把params参数字符串转换成map
	 * @param params_prv
	 * @return
	 */
	static def resetParamsMap(String params_prv){
		Map map = new HashMap<String, String>()
		
		/**
		 * ["sort":"dateCreated", "max":"100", "tradeId":"", "store_id":"", "errorReason_id":"", "content":"", "dateCreatedStart":"", "finish":"", "order":"desc", "userId":"", "dateCreatedEnd":"", "solveCSId":"", "offset":"100", "itemSKUs":"", "createCSId":"18", "id":"1144", "action":"show", "controller":"errorTrade"]
		 */
		if (params_prv){
			String orgData = params_prv.substring(1, params_prv.length() - 1)
			String[] dataArray = orgData.split(", ")
			if (dataArray.length > 0){
				dataArray.each{
					// "sort":"dateCreated"
					// "max":100
					// "dateCreatedStart":"2012-03-17 14:44:46"
					def tmpParam = it.substring(1, it.length())
					if (tmpParam.endsWith("\"")){
						tmpParam = tmpParam.substring(0, tmpParam.length() - 1)
					}

					// sort":"dateCreated
					// max":100
					// dateCreatedStart":"2012-03-17 14:44:46
					String[] kv = tmpParam.split("\":")
					if (kv && kv.length == 2){
						String key = kv[0]
						String value = kv[1]
						if (value.startsWith("\"")){
							value = value.substring(1, value.length())
						}						
						if (value.length() > 0){
							map.put(key, value)
							// println  "key:${key} value:${value}"
						}
					}
				}
			}
		}
		
		return map
	}

}
