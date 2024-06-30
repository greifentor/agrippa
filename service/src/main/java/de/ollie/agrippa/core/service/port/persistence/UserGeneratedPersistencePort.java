package de.ollie.agrippa.core.service.port.persistence;

import java.util.List;
import java.util.Optional;

import de.ollie.agrippa.core.model.Page;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.model.User;
import lombok.Generated;

/**
 * A generated persistence port interface for User CRUD operations.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface UserGeneratedPersistencePort {

	User create(User model);

	List<User> findAll();

	Page<User> findAll(PageParameters pageParameters);

	Optional<User> findById(Long id);

	User update(User model);

	void delete(User model);

	Optional<User> findByGlobalId(String globalId);

}