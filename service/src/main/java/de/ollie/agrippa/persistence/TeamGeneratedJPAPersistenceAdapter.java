package de.ollie.agrippa.persistence;

import static de.ollie.agrippa.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Team;
import de.ollie.agrippa.core.service.exception.NotNullConstraintViolationException;
import de.ollie.agrippa.core.service.port.persistence.TeamPersistencePort;
import de.ollie.agrippa.persistence.converter.PageConverter;
import de.ollie.agrippa.persistence.converter.PageParametersToPageableConverter;
import de.ollie.agrippa.persistence.converter.TeamDBOConverter;
import de.ollie.agrippa.persistence.entity.TeamDBO;
import de.ollie.agrippa.persistence.repository.TeamDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for teams.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class TeamGeneratedJPAPersistenceAdapter implements TeamPersistencePort {

	@Inject
	protected TeamDBOConverter converter;
	@Inject
	protected TeamDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<Team, TeamDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public Team create(Team model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<Team> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<Team> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<Team> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public Team update(Team model) {
		ensure(
				model.getTitle() != null,
				() -> new NotNullConstraintViolationException("Team field title cannot be null.", "Team", "title"));
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(Team model) {
		repository.delete(converter.toDBO(model));
	}

}