package com.nala.csd.event

import com.nala.csd.Hero
import org.springframework.context.ApplicationEvent
import com.nala.csd.TabEnum
import com.nala.csd.MemoEnum

/**
 * Created with IntelliJ IDEA.
 * User: xiaocheng
 * Date: 12-5-9
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 * 用户操作事件记录
 */
class UserOperateEvent extends ApplicationEvent {

    /*
    * 用户的角色
    * */
    Hero  hero

    /*
    * 用户登陆ip
    * */
    String clientIp

    /*
    * 登陆时间
    * */
    Date loginTime

    /*
    * 操作时间
    * */
    Date date

    /*
    * 操作的表
    * */
    String tabName

    /*
    * 操作表的id
    * */
    String tId

    /*
     * 操作说明
     * */
    String memo

    UserOperateEvent(Hero hero,String clientIp, Date loginTime, Date date, TabEnum  tabName, String tId, MemoEnum memo) {
       // UserOperateEvent(heroName, clientIp, loginTime , date, tabName, tId,  memo)
        super(String.valueOf(Math.random()) )
        this.clientIp = clientIp
        this.loginTime = loginTime
        this.hero = hero
        this.date = date
        this.tabName = tabName
        this.tId = tId
        this.memo = memo
    }
}
