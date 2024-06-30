package de.ollie.agrippa.persistence;

import static de.ollie.agrippa.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.service.exception.NotNullConstraintViolationException;
import de.ollie.agrippa.core.service.port.persistence.ProjectPersistencePort;
import de.ollie.agrippa.persistence.converter.PageConverter;
import de.ollie.agrippa.persistence.converter.PageParametersToPageableConverter;
import de.ollie.agrippa.persistence.converter.ProjectDBOConverter;
import de.ollie.agrippa.persistence.entity.ProjectDBO;
import de.ollie.agrippa.persistence.repository.ProjectDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for projects.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class ProjectGeneratedJPAPersistenceAdapter implements ProjectPersistencePort {

	@Inject
	protected ProjectDBOConverter converter;
	@Inject
	protected ProjectDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<Project, ProjectDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public Project create(Project model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<Project> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<Project> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<Project> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public Project update(Project model) {
		ensure(
				model.getUser() != null,
				() -> new NotNullConstraintViolationException("Project field user cannot be null.", "Project", "user"));
		ensure(
				model.getTitle() != null,
				() -> new NotNullConstraintViolationException("Project field title cannot be null.", "Project", "title"));
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(Project model) {
		repository.delete(converter.toDBO(model));
	}

}