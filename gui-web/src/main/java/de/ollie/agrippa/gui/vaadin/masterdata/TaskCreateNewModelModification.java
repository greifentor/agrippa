package de.ollie.agrippa.gui.vaadin.masterdata;

import javax.inject.Named;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.service.UserService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class TaskCreateNewModelModification implements MaintenanceViewCreateNewModelModification<Task> {

	private final UserService userService;

	@Override
	public Task modify(Task model) {
		return model.setUser(userService.findDefaultUser().orElse(null));
	}

}
