package de.ollie.agrippa.gui.vaadin.component;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import de.ollie.agrippa.core.service.NoteService;
import de.ollie.agrippa.core.service.ProjectService;
import de.ollie.agrippa.core.service.TaskService;
import de.ollie.agrippa.core.service.TeamService;
import de.ollie.agrippa.core.service.UserService;

import lombok.Generated;
import lombok.Getter;

/**
 * A service provider.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Getter
@Named
public class ServiceProvider {

	@Autowired(required = false)
	private NoteService noteService;
	@Autowired(required = false)
	private ProjectService projectService;
	@Autowired(required = false)
	private TaskService taskService;
	@Autowired(required = false)
	private TeamService teamService;
	@Autowired(required = false)
	private UserService userService;

}