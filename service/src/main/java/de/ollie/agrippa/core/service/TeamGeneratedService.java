package de.ollie.agrippa.core.service;

import java.util.List;
import java.util.Optional;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.Team;
import lombok.Generated;

/**
 * A generated service interface for Team management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface TeamGeneratedService {

	Team create(Team model);

	List<Team> findAll();

	Page<Team> findAll(PageParameters pageParameters);

	Optional<Team> findById(Long id);

	Team update(Team model);

	void delete(Team model);

}