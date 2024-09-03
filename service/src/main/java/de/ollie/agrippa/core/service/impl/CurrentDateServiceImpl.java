package de.ollie.agrippa.core.service.impl;

import java.time.LocalDate;

import javax.inject.Named;

import de.ollie.agrippa.core.service.CurrentDateService;

@Named
public class CurrentDateServiceImpl implements CurrentDateService {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }

}
