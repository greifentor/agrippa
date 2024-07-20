package de.ollie.agrippa.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.service.port.persistence.ProjectPersistencePort;
import de.ollie.agrippa.core.service.ProjectService;
import lombok.Generated;

/**
 * A generated service interface implementation for Project management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ProjectGeneratedServiceImpl implements ProjectService {

	@Inject
	protected ProjectPersistencePort persistencePort;

	@Override
	public Project create(Project model) {
		return persistencePort.create(model);
	}

	@Override
	public List<Project> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<Project> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<Project> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public Project update(Project model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(Project model) {
		persistencePort.delete(model);
	}

	@Override
	public Optional<Project> findByTitle(String title) {
		return persistencePort.findByTitle(title);
	}

}