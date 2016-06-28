package com.nala.csd

import com.nala.csd.jinGuanShouHou.Coupon

class UserOperate {

    static constraints = {

        hero nullable: true
        clientIp nullable:true
        loginTime  nullable:true
        date nullable : true
        tabName nullable: true
        tId nullable: true
        memo nullable: true
    }

    /*
    * 用户的角色
    * */
    Hero hero

    /*
    * 登陆ip
    * */
    String clientIp

    /*
    * 登陆实际那
    * */
    Date loginTime

    /*
    * 操作时间
    * */
    Date date

    /*
    * 操作的表
    * */
    TabEnum tabName

    /*
    * 操作表的id
    * */
    String tId

    /*
    * 操作说明
    * */

    MemoEnum  memo



    }
enum TabEnum {

    JinGuanShouHou(0,'com.nala.csd.JinGuanShouHou', '金冠店售后记录列表 '),
    ChajianPassive(1,'com.nala.csd.ChajianPassive', '查件被动表'),
    ChajianInitiative(2,'com.nala.csd.ChajianInitiative', '主动查件表') ,
    LogisticsProblem(3,'com.nala.csd.LogisticsProblem', '快递问题件'),
    ErrorTrade(4,'com.nala.csd.ErrorTrade', '总异常单记录列表'),
//    TPErrorTrade(5,'com.nala.csd.TPErrorTrade', 'TP店异常单记录列表'),
    TmallShouhou(6, 'com.nala.csd.TmallShouhou','总售后登记表') ,
//    TPShouhou(7,'com.nala.csd.TPShouhou', 'TP店售后登记表') ,
    B2CConsult(8,'com.nala.csd.B2CConsult', 'B2C顾客咨询登记表'),
    ReturnGoods(9,'com.nala.csd.jinGuanShouHou.ReturnGoods', 'returnGoods(附)'),
    Resend(10,'com.nala.csd.jinGuanShouHou.Resend','resend(附)'),
    Remit(11,'com.nala.csd.jinGuanShouHou.Remit','Remit(附)'),
    Refund(12,'com.nala.csd.jinGuanShouHou.Refund','Refund(附)'),
    Coupon(13,'com.nala.csd.jinGuanShouHou.Coupon','Coupon(附)'),
    ExpressProblem(14,'com.nala.csd.ExpressProblem', 'ExpressProblem(附)'),
    Express(15,'com.nala.csd.Express','Express(附)'),
    Item(16,'com.nala.csd.Item','Item(附)')

    private final Integer code;
    private final String summary
    private final String description;

    TabEnum(Integer code,String summary, String description) {
        this.code = code;
        this.summary = summary
        this.description = description;
    }

    public static TabEnum getByCode(def code){
        def res
        code = Integer.valueOf(code)
        TabEnum.values().each { status->
            if(status.code==code){
                res= status
            }
        }
        return res
    }

    public Integer getCode() {
        return code;
    }

    public String getSummary() {
        return summary
    }
    public String getDescription() {
        return description;
    }
}

enum MemoEnum {

    add(0, '增加'),
    delete(1, '删除'),
    update(2, '更改')

    private final Integer code;
    private final String description;

    MemoEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MemoEnum getByCode(def code){
        def res
        code = Integer.valueOf(code)
        MemoEnum.values().each { status->
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
