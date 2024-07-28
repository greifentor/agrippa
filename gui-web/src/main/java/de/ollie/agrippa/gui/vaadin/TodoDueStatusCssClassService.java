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
		switch (status) {
		case ALERT:
			return "grid-alert";
		case WARN_LEVEL_1:
			return "grid-warn-1";
		case WARN_LEVEL_2:
			return "grid-warn-2";
		case WARN_LEVEL_3:
			return "grid-warn-3";
		default:
			return "grid-regular";
		}
	}

}
