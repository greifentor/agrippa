package de.ollie.agrippa.gui.vaadin;

import javax.inject.Named;

import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.service.TodoDueStatusService;
import de.ollie.agrippa.core.service.TodoDueStatusService.TodoDueStatus;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class TodoDueStatusCssClassService {

    private final TodoDueStatusService todoDueStatusService;

    public String getCssClassName(Todo todo) {
        TodoDueStatus status = todoDueStatusService.getDueStatus(todo);
        return status == TodoDueStatus.ALERT
                ? "grid-alert"
                : status == TodoDueStatus.WARN ? "grid-warn" : "grid-regular";
    }

}
