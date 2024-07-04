package de.ollie.agrippa.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Team;
import de.ollie.agrippa.core.service.port.persistence.TeamPersistencePort;
import de.ollie.agrippa.core.service.TeamService;
import lombok.Generated;

/**
 * A generated service interface implementation for Team management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class TeamGeneratedServiceImpl implements TeamService {

	@Inject
	protected TeamPersistencePort persistencePort;

	@Override
	public Team create(Team model) {
		return persistencePort.create(model);
	}

	@Override
	public List<Team> findAll() {
		return persistencePort.findAll();
	}

	@Override
	public Page<Team> findAll(PageParameters pageParameters) {
		return persistencePort.findAll(pageParameters);
	}

	@Override
	public Optional<Team> findById(Long id) {
		return persistencePort.findById(id);
	}

	@Override
	public Team update(Team model) {
		return persistencePort.update(model);
	}

	@Override
	public void delete(Team model) {
		persistencePort.delete(model);
	}

}