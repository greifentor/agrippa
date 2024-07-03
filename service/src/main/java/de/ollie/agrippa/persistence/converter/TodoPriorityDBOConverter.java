package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.agrippa.persistence.entity.TodoPriorityDBO;
import de.ollie.agrippa.core.model.TodoPriority;

/**
 * A DBO enum converter for todoprioritys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class TodoPriorityDBOConverter {

	public TodoPriorityDBO toDBO(TodoPriority model) {
		return model == null ? null : TodoPriorityDBO.valueOf(model.name());
	}

	public TodoPriority toModel(TodoPriorityDBO dbo) {
		return dbo == null ? null : TodoPriority.valueOf(dbo.name());
	}

}