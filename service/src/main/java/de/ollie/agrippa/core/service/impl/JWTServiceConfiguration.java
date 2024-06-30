package de.ollie.agrippa.core.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Generated;
import lombok.Getter;

/**
 * A class for the JWT service configuration.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Configuration
@Generated
@Getter
public class JWTServiceConfiguration {

	@Value("${jwt.service.secret}")
	private String secret;
	@Value("${jwt.service.base-validity-in-minutes:5}")
	private int baseValidityInMinutes;
	@Value("${test.mode:false}")
	private boolean testMode;

}