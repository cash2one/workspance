package nala.csd

import com.nala.csd.Hero

class CSDTagLib {

    def springSecurityService

    def username = {
        Hero hero = springSecurityService.currentUser
        if (hero){
            out << hero.name
        }else{
            out << ""
        }
    }

    def userid = {
        Hero hero = springSecurityService.currentUser
        if (hero){
            out << hero.id
        }else{
            out << ""
        }
    }

}
