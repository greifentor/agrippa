package de.ollie.agrippa.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Task;
import lombok.Generated;

/**
 * A generated service interface for Task management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface TaskGeneratedService {

	Task create(Task model);

	List<Task> findAll();

	Page<Task> findAll(PageParameters pageParameters);

	Optional<Task> findById(Long id);

	Task update(Task model);

	void delete(Task model);

}