package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.agrippa.persistence.entity.NoteTypeDBO;
import de.ollie.agrippa.core.model.NoteType;

/**
 * A DBO enum converter for notetypes.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class NoteTypeDBOConverter {

	public NoteTypeDBO toDBO(NoteType model) {
		return model == null ? null : NoteTypeDBO.valueOf(model.name());
	}

	public NoteType toModel(NoteTypeDBO dbo) {
		return dbo == null ? null : NoteType.valueOf(dbo.name());
	}

}