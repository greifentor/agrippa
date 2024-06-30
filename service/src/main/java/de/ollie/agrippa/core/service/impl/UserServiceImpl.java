package de.ollie.agrippa.core.service.impl;

import java.util.Optional;

import javax.inject.Named;

import de.ollie.agrippa.core.model.User;
import lombok.RequiredArgsConstructor;

/**
 * A service interface implementation for User management.
 */
@Named
@RequiredArgsConstructor
public class UserServiceImpl extends UserGeneratedServiceImpl {

	@Override
	public Optional<User> findDefaultUser() {
		return persistencePort.findAll().stream().filter(u -> u.isDefaultUser()).findFirst();
	}
}