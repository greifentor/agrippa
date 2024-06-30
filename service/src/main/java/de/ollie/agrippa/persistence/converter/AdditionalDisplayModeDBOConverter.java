package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.agrippa.persistence.entity.AdditionalDisplayModeDBO;
import de.ollie.agrippa.core.model.AdditionalDisplayMode;

/**
 * A DBO enum converter for additionaldisplaymodes.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class AdditionalDisplayModeDBOConverter {

	public AdditionalDisplayModeDBO toDBO(AdditionalDisplayMode model) {
		return model == null ? null : AdditionalDisplayModeDBO.valueOf(model.name());
	}

	public AdditionalDisplayMode toModel(AdditionalDisplayModeDBO dbo) {
		return dbo == null ? null : AdditionalDisplayMode.valueOf(dbo.name());
	}

}