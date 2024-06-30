package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import lombok.Generated;

import de.ollie.agrippa.persistence.entity.TaskStatusDBO;
import de.ollie.agrippa.core.model.TaskStatus;

/**
 * A DBO enum converter for taskstatuss.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class TaskStatusDBOConverter {

	public TaskStatusDBO toDBO(TaskStatus model) {
		return model == null ? null : TaskStatusDBO.valueOf(model.name());
	}

	public TaskStatus toModel(TaskStatusDBO dbo) {
		return dbo == null ? null : TaskStatus.valueOf(dbo.name());
	}

}