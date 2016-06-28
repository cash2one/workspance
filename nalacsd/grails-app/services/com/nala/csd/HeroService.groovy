package com.nala.csd

class HeroService {

    static transactional = true

    def serviceMethod() {

    }

    def createHeroAndRole(Hero heroInstance, params){
        log.debug ("当前参数的数值"+ params?.accountLocked)
        heroInstance.accountExpired = false
//        heroInstance.accountLocked = false
        heroInstance.enabled = true
        heroInstance.username = heroInstance.email.substring(0, heroInstance.email.indexOf("@"))

        if (heroInstance.save(flush: true)) {
            if (params.role && params.role.id){
                def role = Role.get(params.role.id)
                if (role){
                    try{
                        HeroRole.create(heroInstance, role, true)
                    }catch (Exception e){
                        throw new RuntimeException("create role for user error!")
                    }
                    log.info("create hero and role success. user id = " + heroInstance.id + "  and role id = " + role.id )
                    return true
                }else{
                    throw new RuntimeException("can not find Role by params")
                    return false
                }
            }else{
                throw new RuntimeException("can not find Role by params")
                return false
            }
        }else {
            log.error("create hero and role fail.")
            heroInstance.errors.each{
                log.error(it);
            }
            return false
        }
    }

}
