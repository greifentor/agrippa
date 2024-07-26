package de.ollie.agrippa.core.service.impl;

import java.time.LocalDateTime;

import javax.inject.Named;

import de.ollie.agrippa.core.service.CurrentDateTimeService;

@Named
public class CurrentDateTimeServiceImpl implements CurrentDateTimeService {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}
