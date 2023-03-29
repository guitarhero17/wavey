package com.wavey.api.security.jwt

import io.jsonwebtoken.*
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JwtUtil_k {
    // randomly generated
    private val SECRET_KEY = "79A1D0A64ED708FBF172AAC01EDEF78E47E8D3047585FD0618F27BB61A4E1B66"

    // expiration of 15 minutes
    private val jwtExpirationInMs = 1000 * 60 * 15

    // expiration of 20 minutes
    private val refreshTokenExpirationInMs = 1000 * 60 * 20

    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    // is the token older than the current date
    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any?> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    // The subject is the person who is going to be authenticated
    private fun doGenerateToken(claims: Map<String, Any?>, subject: String): String {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact()
    }

    fun doGenerateRefreshToken(claims: Map<String?, Any?>?, subject: String?): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        return try {
            val username = extractUsername(token)
            username == userDetails.username && !isTokenExpired(token)
        } catch (ex: SignatureException) {
            println("ok, exception while validating token...")
            throw BadCredentialsException("INVALID_CREDENTIALS", ex)
        } catch (ex: MalformedJwtException) {
            println("ok, exception while validating token...")
            throw BadCredentialsException("INVALID_CREDENTIALS", ex)
        } catch (ex: UnsupportedJwtException) {
            println("ok, exception while validating token...")
            throw BadCredentialsException("INVALID_CREDENTIALS", ex)
        } catch (ex: IllegalArgumentException) {
            println("ok, exception while validating token...")
            throw BadCredentialsException("INVALID_CREDENTIALS", ex)
        } catch (ex: ExpiredJwtException) {
            throw ex
        }
    }
}
