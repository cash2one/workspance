package com.nala.csd

class Hero {

	transient springSecurityService

    /**
     * 邮箱
     */
    String email
    /**
     * csd系统采用邮箱登录，所以考虑到spring security默认用username登录，所以这两个值是一样的，都是邮箱
     */
    String username

    /**
     * 姓名
     */
    String name

	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
        name blank: false
		username blank: true
		password blank: false
        email unique:true, blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		HeroRole.findAllByHero(this).collect { it.role } as Set
	}

    def getFirstAuthorities() {
        def list = HeroRole.findAllByHero(this)
        if (list){
            return list.get(0).role
        }else{
            return null
        }
    }

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

    def ddgetRole(long heroId) {
        log.debug("用户id"+heroId)
        Role role = HeroRole.getRoleByHero(heroId)
        return role
    }

}
