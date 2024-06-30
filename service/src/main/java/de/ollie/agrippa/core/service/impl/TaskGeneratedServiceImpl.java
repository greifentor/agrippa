package de.ollie.agrippa.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.service.port.persistence.TaskPersistencePort;
import de.ollie.agrippa.core.service.TaskService;
import lombok.Generated;

/**
 * A generated service interface implementation for Task management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class TaskGeneratedServiceImpl implements TaskService {

	@Inject
	protected TaskPersistencePort persistencePort;

	@Override
	public Task create(Task model) {
		return persistencePort.create(model);
	}

	@Override
	public List<Task> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<Task> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<Task> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public Task update(Task model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(Task model) {
		persistencePort.delete(model);
	}

}