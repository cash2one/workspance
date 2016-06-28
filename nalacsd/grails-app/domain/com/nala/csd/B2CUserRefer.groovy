package com.nala.csd

/**
 * B2C顾客咨询登记表
 * @author Kenny
 *
 */
class B2CUserRefer {

	static constraints = {
	}

	/**
	 * 创建时间
	 */
	String dateCreated

	/**
	 * 发起人
	 */
	Hero createdCS

	/**
	 * 咨询方式
	 */
	SourceTypeEnum sourceTypeEnum

	/**
	 * 顾客id
	 */
	String userId

	/**
	 * 手机号码
	 */
	String mobile

	/**
	 * 顾客姓名
	 */
	String name



	/**
	 * 咨询方式
	 * @author Kenny
	 *
	 */
	enum SourceTypeEnum {
		hotline(0, '热线接听'), im(1, '在线咨询'), email(2, '服务邮箱')

		private final Integer code;
		private final String description;

		SourceTypeEnum(Integer code, String description) {
			this.code = code;
			this.description = description;
		}

		public static SourceTypeEnum getByCode(def code){
			def res
			code = Integer.valueOf(code)
			SourceTypeEnum.values().each { status->
				if(status.code==code){
					res= status
				}
			}
			return res
		}

		public Integer getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}
}
