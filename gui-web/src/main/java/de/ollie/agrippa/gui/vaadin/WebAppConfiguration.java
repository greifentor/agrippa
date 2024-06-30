package de.ollie.agrippa.gui.vaadin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Generated;

/**
 * A class for the web app configuration;
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Configuration
@Generated
@Getter
public class WebAppConfiguration {

	@Value("${app.version}")
	private String appVersion;

	@Value("${cube.url}")
	private String cubeURL;

	@Value("${maximum.jwt.validity-in-minutes:60}")
	private int maximumJWTValidityInMinutes;

}