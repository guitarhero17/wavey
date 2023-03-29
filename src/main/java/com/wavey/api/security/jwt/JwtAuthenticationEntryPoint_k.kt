package com.wavey.api.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*
// import javax.servlet.ServletException

@Component
class JwtAuthenticationEntryPoint_k : AuthenticationEntryPoint {

    @Throws(IOException::class, ServletException::class)
    override fun commence(req: HttpServletRequest, res: HttpServletResponse,
                          authException: AuthenticationException) {
        res.status = HttpServletResponse.SC_UNAUTHORIZED
        res.contentType = MediaType.APPLICATION_JSON_VALUE
        val message: String?
        // Check if the req has any exception that we have stored in the request
        val exception = req.getAttribute("exception") as Exception

        // If yes then use it to create the response message else use the authException
        if (exception != null) {
            val body: ByteArray = ObjectMapper().writeValueAsBytes(Collections.singletonMap<String, String>("cause", exception.toString()))
            res.outputStream.write(body)
        } else {
            message = if (authException.cause != null) {
                authException.cause.toString() + " " + authException.message
            } else {
                authException.message
            }
            val body: ByteArray = ObjectMapper().writeValueAsBytes(Collections.singletonMap<String, String?>("error", message))
            res.outputStream.write(body)
        }
    }
}
