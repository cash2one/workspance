package com.nala.csd

import com.nala.csd.event.UserOperateEvent
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.security.core.context.SecurityContextHolder

class UserOperateService {

    static transactional = true
    def userOperateListenerService
    def springSecurityService
    def serviceMethod() {

    }
     /*
     *    用户操作方法
     *    新建一个事件传递用户名，时间，表名，表id,memo
     * */
     def operateMethodSelect(instance, String actionName){
         if(actionName.equals('save')){
            operateSaveMethod(instance)
         } else if(actionName.equals('delete')){
            operateDeleteMethod(instance)
         } else if(actionName.equals('update') ){
            operateUpdateMethod(instance)
         }
     }


     def operateSaveMethod(instance) {
        Hero  hero = springSecurityService.currentUser
        def session=RequestContextHolder.currentRequestAttributes().getSession()
       //  log.debug '------------------'
      //  log.debug SecurityContextHolder.getContext().getAuthentication().authorities
        String clientIp = session['clientIp']
        Date loginTime = session['loginTime']
        Date date = new Date()
        log.debug springSecurityService.currentUser
      //  log.debug '对象的类名'
        log.debug  instance.class
        def ins =  instance.class.toString().substring(6)
     //   log.debug '处理之后的类'
      //  log.debug ins
        def tabName=TabEnum.values().find{ status-> status.summary == ins}
        String tId = String.valueOf(instance.id)
        def memo = MemoEnum.getByCode(0)
        def event = new UserOperateEvent(hero, clientIp, loginTime , date, tabName, tId,  memo)
        publishEvent(event)
    }

    def operateDeleteMethod(instance) {
        Hero  hero = springSecurityService.currentUser
        def session=RequestContextHolder.currentRequestAttributes().getSession()
        String clientIp = session['clientIp']
        Date loginTime = session['loginTime']
        Date date = new Date()
        log.debug springSecurityService.currentUser
        def ins =  instance.class.toString().substring(6)
        def tabName=TabEnum.values().find{ status-> status.summary == ins}
        String tId = String.valueOf(instance.id)
        def memo = MemoEnum.getByCode(1)
        def event = new UserOperateEvent(hero, clientIp, loginTime , date, tabName, tId,  memo)
        publishEvent(event)
   }

    def operateUpdateMethod(instance) {
        Hero  hero = springSecurityService.currentUser
        def session=RequestContextHolder.currentRequestAttributes().getSession()
        String clientIp = session['clientIp']
        Date loginTime = session['loginTime']
        Date date = new Date()
        log.debug springSecurityService.currentUser
        def ins =  instance.class.toString().substring(6)
        def tabName=TabEnum.values().find{ status-> status.summary == ins}
        String tId = String.valueOf(instance.id)
        def memo = MemoEnum.getByCode(2)
        def event = new UserOperateEvent(hero, clientIp, loginTime , date, tabName, tId,  memo)
        publishEvent(event)
     }
}
