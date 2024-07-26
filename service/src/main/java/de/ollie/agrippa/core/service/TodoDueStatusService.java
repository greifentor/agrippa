package de.ollie.agrippa.core.service;

import de.ollie.agrippa.core.model.Todo;

public interface TodoDueStatusService {

    public enum TodoDueStatus {
        NONE,
        WARN,
        ALERT;
    }

    TodoDueStatus getDueStatus(Todo todo);

}
