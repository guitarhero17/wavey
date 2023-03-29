package com.wavey.api.security.data

import com.wavey.api.user.data.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

// Source: https://labs.micromata.de/best-practices/tutorial-spring-security/jpa-und-spring-security/
class AuthenticatedUser_k(user: User) : User(user.username, user.password), UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return AuthorityUtils.createAuthorityList("ROLE_USER")
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
