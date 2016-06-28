package com.nala.csd

/**
 * 自定义查件代码
 * 查件被动表使用
 * @author Kenny
 *
 */
class ChajianCode {

    static constraints = {
        name blank: false, unique: true
    }
	
	String name

    /**
     * 此查件代码的使用对象
     */
    CodeForTableEnum codeForTable


}

enum CodeForTableEnum {
    initiative(0, '主动查件'), passive(1, '被动查件')

    private final Integer code;
    private final String description;

    CodeForTableEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CodeForTableEnum getByDescription(def description){
        def res
        
        CodeForTableEnum.values().each {

            if (it.description == description){
                res = it
            }
        }
        
        return res
    }

    public static CodeForTableEnum getByCode(def code){
        def res
        code = Integer.valueOf(code)
        CodeForTableEnum.values().each { status->
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
