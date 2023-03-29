package com.wavey.api.security.business

import com.wavey.api.security.data.AuthenticatedUser
import com.wavey.api.user.data.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class WaveyUserDetailsService_k : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository!!.findByUsername(username)
        return user?.let { AuthenticatedUser(it) }
                ?: throw UsernameNotFoundException("The user $username does not exist.")
    }
}
