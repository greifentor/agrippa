package de.ollie.agrippa.gui.vaadin;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.ollie.agrippa.core.service.JWTService.AuthorizationData;
import de.ollie.agrippa.core.service.UserService;
import de.ollie.agrippa.gui.SessionData;
import lombok.RequiredArgsConstructor;

/**
 * A start view for the application.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Route(ApplicationStartView.URL)
@PreserveOnRefresh
@VaadinSessionScope
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(value = "./styles/vaadin-text-area-styles.css", themeFor = "vaadin-area-field")
@RequiredArgsConstructor
public class ApplicationStartView extends VerticalLayout implements BeforeEnterObserver {

	public static final Logger LOG = LogManager.getLogger(ApplicationStartView.class);
	public static final String URL = "agrippa";

	public final SessionData session;
	public final UserService userService;

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		LOG.info("before enter");
		if (session.getAuthorizationData() == null) {
			userService.findAll().stream().filter(u -> u.isDefaultUser()).findFirst().ifPresent(u -> {
				session.setAuthorizationData(new AuthorizationData("Agrippa", LocalDateTime.now(), u, new String[0]));
				session.setValidUntil(LocalDateTime.now().plusYears(1));
			});
		}
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		LOG.info("attached");
		refresh();
	}

	private void refresh() {
		removeAll();
		getUI().ifPresent(ui -> ui.navigate(MainMenuView.URL));
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		LOG.info("detached");
	}

}