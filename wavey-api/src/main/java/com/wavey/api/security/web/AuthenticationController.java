package com.wavey.api.security.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wavey.api.security.business.WaveyUserDetailsService;
import com.wavey.api.security.jwt.JwtUtil;
import com.wavey.api.security.models.AuthenticationRequest;
import com.wavey.api.security.models.AuthenticationResponse;
import com.wavey.api.user.data.UserRepository;

import io.jsonwebtoken.impl.DefaultClaims;
import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private WaveyUserDetailsService waveyUserDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping("/authenticate")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception {
		
		final String principal = authReq.getUsername();
		final String credentials = authReq.getPassword();
		
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(principal, credentials));			
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		final UserDetails userDetails = waveyUserDetailsService.loadUserByUsername(principal);
		final String token = jwtTokenUtil.generateToken(userDetails);
		final String principalId = userRepository.findByUsername(principal).getId().toString();
		
		return ResponseEntity.ok(new AuthenticationResponse(token, principalId));
	}

	
	@GetMapping("/refreshtoken")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> refreshToken(HttpServletRequest req) throws Exception {
		
		// extracting the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) req.getAttribute("claims");
		if (claims != null) {
			Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
			String principal = expectedMap.get("sub").toString();
			String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, principal);
			final String principalId = userRepository.findByUsername(principal).getId().toString();
			return ResponseEntity.ok(new AuthenticationResponse(token, principalId));

		} else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentUserName = "anonymous";
			
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
			    currentUserName = authentication.getName();
			}
			final String principalId = userRepository.findByUsername(currentUserName).getId().toString();
			Exception ex = (Exception) req.getAttribute("exception");
			
			if (ex != null) {
				return ResponseEntity.ok(ex.toString());
			} else {
				return ResponseEntity.ok(new JSONObject()
						.appendField("valid", "The jwt is probably still valid and there is no need to refresh it yet")
						.appendField("id", principalId));
			}
		}
		
	}
	
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	

}
