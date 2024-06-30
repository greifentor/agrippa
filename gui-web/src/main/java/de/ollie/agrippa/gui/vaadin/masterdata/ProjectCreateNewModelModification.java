package de.ollie.agrippa.gui.vaadin.masterdata;

import javax.inject.Named;

import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.service.UserService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ProjectCreateNewModelModification implements MaintenanceViewCreateNewModelModification<Project> {

	private final UserService userService;

	@Override
	public Project modify(Project model) {
		return model.setUser(userService.findDefaultUser().orElse(null));
	}

}
