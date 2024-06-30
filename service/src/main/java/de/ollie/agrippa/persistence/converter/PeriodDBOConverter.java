package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.agrippa.persistence.entity.PeriodDBO;
import de.ollie.agrippa.core.model.Period;

/**
 * A DBO enum converter for periods.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class PeriodDBOConverter {

	public PeriodDBO toDBO(Period model) {
		return model == null ? null : PeriodDBO.valueOf(model.name());
	}

	public Period toModel(PeriodDBO dbo) {
		return dbo == null ? null : Period.valueOf(dbo.name());
	}

}