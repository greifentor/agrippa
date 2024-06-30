package de.ollie.agrippa.core.service.impl;

import java.util.UUID;

import javax.inject.Named;

import de.ollie.agrippa.core.service.UUIDFactoryService;

@Named
public class UUIDFactoryServiceImpl implements UUIDFactoryService {

	@Override
	public String createUUIDAsString() {
		return UUID.randomUUID().toString();
	}

}
