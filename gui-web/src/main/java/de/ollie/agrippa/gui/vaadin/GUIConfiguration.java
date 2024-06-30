package de.ollie.agrippa.gui.vaadin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Generated;
import lombok.Getter;

/**
 * A configuration for the GUI.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Configuration
@Generated
@Getter
public class GUIConfiguration {

	@Value("${gui.masterdata.background-image-file-name:main-menu-background.png}")
	private String mainMenuBackgroundFileName;

}