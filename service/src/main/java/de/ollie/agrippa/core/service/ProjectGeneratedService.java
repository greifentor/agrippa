package de.ollie.agrippa.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Project;
import lombok.Generated;

/**
 * A generated service interface for Project management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface ProjectGeneratedService {

	Project create(Project model);

	List<Project> findAll();

	Page<Project> findAll(PageParameters pageParameters);

	Optional<Project> findById(Long id);

	Project update(Project model);

	void delete(Project model);

}