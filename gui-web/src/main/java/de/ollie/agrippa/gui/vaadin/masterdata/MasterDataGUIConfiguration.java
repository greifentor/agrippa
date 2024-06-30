package de.ollie.agrippa.gui.vaadin.masterdata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Generated;
import lombok.Getter;

/**
 * A configuration for the master data GUI.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Configuration
@Generated
@Getter
public class MasterDataGUIConfiguration {

	@Value("${gui.masterdata.background-image-file-name:master-data-background.png}")
	private String backgroundFileName;

	@Value("${gui.masterdata.button.disabled-image-file-name:button-background-disabled.png}")
	private String buttonDisabledBackgroundFileName;

	@Value("${gui.masterdata.button.disabled-border-color:gray}")
	private String buttonDisabledBorderColor;

	@Value("${gui.masterdata.button.enabled-image-file-name:button-background-enabled.png}")
	private String buttonEnabledBackgroundFileName;

	@Value("${gui.masterdata.button.enabled-border-color:blue}")
	private String buttonEnabledBorderColor;

}