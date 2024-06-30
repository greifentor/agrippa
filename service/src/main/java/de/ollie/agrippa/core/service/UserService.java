package de.ollie.agrippa.core.service;

import java.util.Optional;

import de.ollie.agrippa.core.model.User;

/**
 * A service interface for User management.
 */
public interface UserService extends UserGeneratedService {

	Optional<User> findDefaultUser();
}