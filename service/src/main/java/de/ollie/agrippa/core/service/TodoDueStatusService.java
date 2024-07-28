package de.ollie.agrippa.core.service;

import de.ollie.agrippa.core.model.Todo;

public interface TodoDueStatusService {

	public enum TodoDueStatus {
		NONE,
		WARN_LEVEL_1,
		WARN_LEVEL_2,
		WARN_LEVEL_3,
		ALERT;
	}

	TodoDueStatus getDueStatus(Todo todo);

}
