package de.ollie.agrippa.core.service.impl;

import static de.ollie.agrippa.util.Check.ensure;

import javax.inject.Named;

import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.service.CurrentDateTimeService;
import de.ollie.agrippa.core.service.TodoDueStatusService;
import de.ollie.agrippa.core.service.configuration.TodoWarnLevelConfiguration;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class TodoDueStateServiceImpl implements TodoDueStatusService {

	private final CurrentDateTimeService currentDateTimeService;
	private final TodoWarnLevelConfiguration todoWarnLevelConfiguration;

	@Override
	public TodoDueStatus getDueStatus(Todo todo) {
		ensure(todo != null, "todo cannot be null!");
		if (todo.getDueDate() != null) {
			if (currentDateTimeService.now().isAfter(todo.getDueDate())) {
				return TodoDueStatus.ALERT;
			} else if (isAfterOffset(todoWarnLevelConfiguration.getWarnLevel3OffsetInMinutes(), todo)) {
				return TodoDueStatus.WARN_LEVEL_3;
			} else if (isAfterOffset(todoWarnLevelConfiguration.getWarnLevel2OffsetInMinutes(), todo)) {
				return TodoDueStatus.WARN_LEVEL_2;
			} else if (isAfterOffset(todoWarnLevelConfiguration.getWarnLevel1OffsetInMinutes(), todo)) {
				return TodoDueStatus.WARN_LEVEL_1;
			}
		}
		return TodoDueStatus.NONE;
	}

	private boolean isAfterOffset(int offset, Todo todo) {
		return currentDateTimeService.now().plusMinutes(offset).plusMinutes(1).isAfter(todo.getDueDate());
	}

}
