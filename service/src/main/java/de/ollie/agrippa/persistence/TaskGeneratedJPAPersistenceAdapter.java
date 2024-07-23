package de.ollie.agrippa.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.Reason;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.ValidationFailure;
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
		ensureNoViolationsFound(model);
		return converter.toModel(repository.save(converter.toDBO(model)));
	}

	private void ensureNoViolationsFound(Task model) {
		List<ValidationFailure> failures = new ArrayList<>();
		if (model.getProject() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Task", "project"));
		}
		if (model.getUser() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Task", "user"));
		}
		if (model.getTaskStatus() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Task", "taskStatus"));
		}
		if (model.getTitle() == null) {
			failures.add(new ValidationFailure(Reason.NOT_NULL, "Task", "title"));
		}
		if (!failures.isEmpty()) {
			throw new PersistenceFailureException("" + model.getId(), failures);
		}
	}

	@Override
	public void delete(Task model) {
		repository.delete(converter.toDBO(model));
	}

}