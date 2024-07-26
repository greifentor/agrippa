package de.ollie.agrippa.core.service.impl;

import static de.ollie.agrippa.util.Check.ensure;

import javax.inject.Named;

import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.service.CurrentDateTimeService;
import de.ollie.agrippa.core.service.TodoDueStatusService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class TodoDueStateServiceImpl implements TodoDueStatusService {

    private final CurrentDateTimeService currentDateTimeService;

    public TodoDueStatus getDueStatus(Todo todo) {
        ensure(todo != null, "todo cannot be null!");
        if (todo.getDueDate() != null) {
            if (currentDateTimeService.now().isAfter(todo.getDueDate())) {
                return TodoDueStatus.ALERT;
            } else if (currentDateTimeService.now().plusHours(8).plusMinutes(1).isAfter(todo.getDueDate())) {
                return TodoDueStatus.WARN;
            }
        }
        return TodoDueStatus.NONE;
    }

}
