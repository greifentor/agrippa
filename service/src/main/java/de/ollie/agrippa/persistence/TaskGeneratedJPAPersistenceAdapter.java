package de.ollie.agrippa.persistence;

import static de.ollie.agrippa.util.Check.ensure;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.service.exception.NotNullConstraintViolationException;
import de.ollie.agrippa.core.service.port.persistence.TaskPersistencePort;
import de.ollie.agrippa.persistence.converter.PageConverter;
import de.ollie.agrippa.persistence.converter.PageParametersToPageableConverter;
import de.ollie.agrippa.persistence.converter.TaskDBOConverter;
import de.ollie.agrippa.persistence.entity.TaskDBO;
import de.ollie.agrippa.persistence.repository.TaskDBORepository;
import lombok.Generated;

/**
 * A generated JPA persistence adapter for tasks.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class TaskGeneratedJPAPersistenceAdapter implements TaskPersistencePort {

	@Inject
	protected TaskDBOConverter converter;
	@Inject
	protected TaskDBORepository repository;

	@Inject
	protected PageParametersToPageableConverter pageParametersToPageableConverter;

	protected PageConverter<Task, TaskDBO> pageConverter;

	@PostConstruct
	public void postConstruct() {
		pageConverter = new PageConverter<>(converter);
	}

	@Override
	public Task create(Task model) {
		model.setId(-1);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public List<Task> findAll() {
		return converter.toModel(repository.findAll());
	}

	@Override
	public Page<Task> findAll(PageParameters pageParameters) {
		return pageConverter.convert(repository.findAll(pageParametersToPageableConverter.convert(pageParameters)));
	}

	@Override
	public Optional<Task> findById(Long id) {
		return repository.findById(id).map(dbo -> converter.toModel(dbo));
	}

	@Override
	public Task update(Task model) {
		ensure(
				model.getProject() != null,
				() -> new NotNullConstraintViolationException("Task field project cannot be null.", "Task", "project"));
		ensure(
				model.getUser() != null,
				() -> new NotNullConstraintViolationException("Task field user cannot be null.", "Task", "user"));
		ensure(
				model.getTaskStatus() != null,
				() -> new NotNullConstraintViolationException("Task field taskStatus cannot be null.", "Task", "taskStatus"));
		ensure(
				model.getTitle() != null,
				() -> new NotNullConstraintViolationException("Task field title cannot be null.", "Task", "title"));
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	@Override
	public void delete(Task model) {
		repository.delete(converter.toDBO(model));
	}

}