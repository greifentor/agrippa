package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.agrippa.persistence.entity.TodoStatusDBO;
import de.ollie.agrippa.core.model.TodoStatus;

/**
 * A DBO enum converter for todostatuss.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class TodoStatusDBOConverter {

	public TodoStatusDBO toDBO(TodoStatus model) {
		return model == null ? null : TodoStatusDBO.valueOf(model.name());
	}

	public TodoStatus toModel(TodoStatusDBO dbo) {
		return dbo == null ? null : TodoStatus.valueOf(dbo.name());
	}

}