package de.ollie.agrippa.core.service.impl;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.UUIDFactoryService;
import de.ollie.agrippa.core.service.UserService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DefaultUserGlobalIdServiceImpl {

	private static final Logger LOG = LogManager.getLogger(DefaultUserGlobalIdServiceImpl.class);

	private final UserService userService;
	private final UUIDFactoryService uuidFactoryService;

	@PostConstruct
	void postConstruct() {
		LOG.info("Checking for default user global id.");
		userService.findDefaultUser().filter(u -> u.getGlobalId() == null).ifPresent(this::createUUIDAndUpdate);
		LOG.info("Check for default user global id done.");
	}

	private void createUUIDAndUpdate(User user) {
		user.setGlobalId(uuidFactoryService.createUUIDAsString());
		userService.update(user);
		LOG.info("- set global id for default user to: " + user.getGlobalId());
	}

}
