package com.wavey.api.security.data;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.wavey.api.user.data.User;

// Source: https://labs.micromata.de/best-practices/tutorial-spring-security/jpa-und-spring-security/
@SuppressWarnings("serial")
public class AuthenticatedUser extends User implements UserDetails {
	
	public AuthenticatedUser(User user) {
        super(user.getUsername(), user.getPassword());
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
