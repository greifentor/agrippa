package de.ollie.agrippa.core.service.port.persistence;

import java.util.List;
import java.util.Optional;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Project;
import lombok.Generated;

/**
 * A generated persistence port interface for Project CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ProjectGeneratedPersistencePort {

	Project create(Project model);

	List<Project> findAll();

	Page<Project> findAll(PageParameters pageParameters);

	Optional<Project> findById(Long id);

	Project update(Project model);

	void delete(Project model);

	Optional<Project> findByTitle(String title);

}