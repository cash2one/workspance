package com.nala.csd

import org.apache.commons.lang.builder.HashCodeBuilder

class HeroRole implements Serializable {

	Hero hero
	Role role

	boolean equals(other) {
		if (!(other instanceof HeroRole)) {
			return false
		}

		other.hero?.id == hero?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (hero) builder.append(hero.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static HeroRole get(long heroId, long roleId) {
		find 'from HeroRole where hero.id=:heroId and role.id=:roleId',
			[heroId: heroId, roleId: roleId]
	}

    static Role getRoleByHero(long heroId) {
        // if (heroId){
//            log.debug("------------"+heroId)
            def roles = HeroRole.executeQuery('from HeroRole where hero.id=' + heroId)
//            log.debug("此处设置断点"+ roles)
            return roles.get(0).role
        // }
    }

	static HeroRole create(Hero hero, Role role, boolean flush = false) {
        
		def hr = new HeroRole(hero: hero, role: role)

        if (!hr.save(flush: flush, insert: true)){
            hr.errors.each{
                println(it)
            }
        }
	}

	static boolean remove(Hero hero, Role role, boolean flush = false) {
		HeroRole instance = HeroRole.findByHeroAndRole(hero, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Hero hero) {
		executeUpdate 'DELETE FROM HeroRole WHERE hero=:hero', [hero: hero]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM HeroRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'hero']
		version false
	}
}
