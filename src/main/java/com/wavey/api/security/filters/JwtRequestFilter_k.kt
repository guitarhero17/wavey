package com.wavey.api.security.filters

import com.wavey.api.security.business.WaveyUserDetailsService
import com.wavey.api.security.jwt.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtRequestFilter_k : OncePerRequestFilter() {
    @Autowired
    private val waveyUserDetailsService: WaveyUserDetailsService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        try {
            val jwt = getJwtFromRequest(req)
            val username = getUsernameFrowJwtToken(jwt)

            // getting the user details for the username
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = waveyUserDetailsService!!.loadUserByUsername(username)

                // proceeding with the normal flow of operation only if the jwt is valid
                if (jwtUtil!!.validateToken(jwt, userDetails)) {
                    val upAuthToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities)
                    upAuthToken.details = WebAuthenticationDetailsSource().buildDetails(req)
                    SecurityContextHolder.getContext().authentication = upAuthToken
                }
            }
        } catch (ex: ExpiredJwtException) {
            val isRefreshToken = req.getHeader("refreshToken")
            val requestURL = req.requestURL.toString()
            if (isRefreshToken != null && isRefreshToken == "true" && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, req)
            } else {
                req.setAttribute("exception", ex)
            }
        } catch (ex: BadCredentialsException) {
            req.setAttribute("exception", ex)
        } catch (ex: Exception) {
            println("Exception when authorizing:")
            println(ex)
            println(getJwtFromRequest(req))
        }
        // continuing the chain
        chain.doFilter(req, res)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

//            return SecurityCipher.decrypt(accessToken);
            bearerToken.substring(7) ?: return null
        } else null
    }

    private fun getUsernameFrowJwtToken(jwt: String?): String? {
        return if (jwt != null) {
            jwtUtil!!.extractUsername(jwt)
        } else {
            null
        }
    }

    // In reference to: https://www.javainuse.com/webseries/spring-security-jwt/chap7
    private fun allowForRefreshToken(ex: ExpiredJwtException, req: HttpServletRequest) {
        // Creating a UsernamePasswordAuthenticationToken with null values.
        val upAuthToken = UsernamePasswordAuthenticationToken(
                null, null, null)
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated.
        SecurityContextHolder.getContext().authentication = upAuthToken
        // Setting the claims so that in controller we will be using it to create a new JWT
        req.setAttribute("claims", ex.claims)
    }
}
