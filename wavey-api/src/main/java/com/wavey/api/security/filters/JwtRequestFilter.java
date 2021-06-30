package com.wavey.api.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wavey.api.security.business.WaveyUserDetailsService;
import com.wavey.api.security.jwt.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private WaveyUserDetailsService waveyUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;


	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
			
		try {
			
			String jwt = getJwtFromRequest(req);
			String username = getUsernameFrowJwtToken(jwt);
			
			// getting the user details for the user name
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = this.waveyUserDetailsService.loadUserByUsername(username);
				
				// proceeding with the normal flow of operation only if the jwt is valid
				if (jwtUtil.validateToken(jwt, userDetails)) {
					
					UsernamePasswordAuthenticationToken upAuthToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					upAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(upAuthToken);
				}
			}
		} catch (ExpiredJwtException ex) {
			
			String isRefreshToken = req.getHeader("refreshToken");
			String requestURL = req.getRequestURL().toString();
			
			if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
				allowForRefreshToken(ex, req);
			} else {
				req.setAttribute("exception", ex);				
			}
			
		} catch (BadCredentialsException ex) {
			req.setAttribute("exception", ex);
		} catch (Exception ex) {
			System.out.println("Exception when authorizing:");
			System.out.println(ex);
			System.out.println(getJwtFromRequest(req));
		}
		// continuing the chain
		chain.doFilter(req, res);
	}
	
	
	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring(7);
            if (accessToken == null) return null;

//            return SecurityCipher.decrypt(accessToken);
            return accessToken;
        }
        return null;
    }
	
	
	private String getUsernameFrowJwtToken(String jwt) {
		if (jwt != null) {
			return jwtUtil.extractUsername(jwt);			
		} else {
			return null;
		}
	}
	
	// In reference to: https://www.javainuse.com/webseries/spring-security-jwt/chap7
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest req) {
		// Creating a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken upAuthToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated.
		SecurityContextHolder.getContext().setAuthentication(upAuthToken);
		// Setting the claims so that in controller we will be using it to create a new JWT
		req.setAttribute("claims", ex.getClaims());

	}
}
