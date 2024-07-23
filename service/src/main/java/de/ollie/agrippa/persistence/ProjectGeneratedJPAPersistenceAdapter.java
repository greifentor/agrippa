package de.ollie.agrippa.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.Reason;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.ValidationFailure;
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
		ensureNoViolationsFound(model);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	private void ensureNoViolationsFound(Project model) {
		List<ValidationFailure> failures = new ArrayList<>();
		if (model.getUser() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Project", "user"));
		}
		if (model.getTitle() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Project", "title"));
		}
		if (!findByTitle(model.getTitle())
				.filter(project -> project.getId() != model.getId())
				.isEmpty()) {
			failures.add(new ValidationFailure(Reason.UNIQUE, "Project", "title"));
		}
		if ((model.getTitle() != null) && model.getTitle().isBlank()) {
			failures.add(new ValidationFailure(Reason.NOT_BLANK, "Project", "title"));
		}
		if (!failures.isEmpty()) {
			throw new PersistenceFailureException("" + model.getId(), failures);
		}
	}

	@Override
	public void delete(Project model) {
		repository.delete(converter.toDBO(model));
	}

	@Override
	public Optional<Project> findByTitle(String title) {
		return Optional.ofNullable(converter.toModel(repository.findByTitle(title).orElse(null)));
	}

}