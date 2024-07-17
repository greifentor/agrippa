package de.ollie.agrippa.gui;

import java.time.LocalDateTime;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ollie.agrippa.core.service.JWTService.AuthorizationData;
import de.ollie.agrippa.core.service.UserService;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class AccessGranter {

    public static final Logger LOG = LogManager.getLogger(AccessGranter.class);

    public final UserService userService;

    public void grantAccess(SessionData session) {
        LOG.info("grant access check.");
        if (session.getAuthorizationData() == null) {
            userService.findAll().stream().filter(u -> u.isDefaultUser()).findFirst().ifPresent(u -> {
                session.setAuthorizationData(new AuthorizationData("Agrippa", LocalDateTime.now(), u, new String[0]));
                session.setValidUntil(LocalDateTime.now().plusYears(1));
                LOG.info("access granted.");
            });
        }
    }

}
