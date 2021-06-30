package com.wavey.api.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshedTokenResponse {
	
	private final String jwt;
}
