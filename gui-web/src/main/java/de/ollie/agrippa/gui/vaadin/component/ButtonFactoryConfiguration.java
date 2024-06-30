package de.ollie.agrippa.gui.vaadin.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * A configuration for the button factory.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Configuration
@Getter
public class ButtonFactoryConfiguration {

	@Value("${gui.button.disabled-image-file-name:button-background-disabled.png}")
	private String buttonDisabledBackgroundFileName;

	@Value("${gui.button.disabled-border-color:gray}")
	private String buttonDisabledBorderColor;

	@Value("${gui.button.enabled-image-file-name:button-background-enabled.png}")
	private String buttonEnabledBackgroundFileName;

	@Value("${gui.button.enabled-border-color:blue}")
	private String buttonEnabledBorderColor;

}