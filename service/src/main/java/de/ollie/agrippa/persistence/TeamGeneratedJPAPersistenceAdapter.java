package de.ollie.agrippa.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Team;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.Reason;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.ValidationFailure;
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
		ensureNoViolationsFound(model);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	private void ensureNoViolationsFound(Team model) {
		List<ValidationFailure> failures = new ArrayList<>();
		if (model.getTitle() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Team", "title"));
		}
		if (!failures.isEmpty()) {
			throw new PersistenceFailureException("" + model.getId(), failures);
		}
	}

	@Override
	public void delete(Team model) {
		repository.delete(converter.toDBO(model));
	}

}