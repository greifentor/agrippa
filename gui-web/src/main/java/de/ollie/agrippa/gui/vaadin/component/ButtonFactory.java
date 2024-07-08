package de.ollie.agrippa.gui.vaadin.component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Named;

import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.QueryParameters;

import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.SessionData.ReturnUrlData;
import de.ollie.agrippa.gui.vaadin.ApplicationStartView;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A button factory.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ButtonFactory {

	private final ButtonFactoryConfiguration buttonFactoryConfiguration;

	public Button createButton(String text) {
		Button button =
				new Button(text)
						.setBackgroundColor("white")
						.setBorder("solid 1px")
						.setBorderColor(buttonFactoryConfiguration.getButtonEnabledBorderColor())
						.setColor("black")
						.setBackgroundImage(buttonFactoryConfiguration.getButtonEnabledBackgroundFileName());
		return button;
	}

	public Button createAddButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.add.text", action, sessionData);
	}

	public Button createBackButton(ResourceManager resourceManager, Supplier<Optional<UI>> uiSupplier, String urlBack,
			SessionData sessionData) {
		Button buttonBack =
				createButton(
						resourceManager.getLocalizedString("commons.button.back.text", sessionData.getLocalization()));
		buttonBack.addClickListener(event -> uiSupplier.get().ifPresent(ui -> ui.navigate(urlBack)));
		return buttonBack;
	}

	public Button createBackButton(ResourceManager resourceManager, Supplier<Optional<UI>> uiSupplier,
			Supplier<ReturnUrlData> urlBack, SessionData sessionData) {
		Button buttonBack =
				createButton(
						resourceManager.getLocalizedString("commons.button.back.text", sessionData.getLocalization()));
		buttonBack.addClickListener(event -> uiSupplier.get().ifPresent(ui -> {
			ReturnUrlData rud = urlBack.get();
			ui.navigate(rud.getUrl(), new QueryParameters(rud.getParameters()));
		}));
		return buttonBack;
	}

	public Button createBackButton(ResourceManager resourceManager, Supplier<Optional<UI>> uiSupplier, String urlBack,
			SessionData sessionData, QueryParameters parameters) {
		Button buttonBack =
				createButton(
						resourceManager.getLocalizedString("commons.button.back.text", sessionData.getLocalization()));
		buttonBack.addClickListener(event -> uiSupplier.get().ifPresent(ui -> ui.navigate(urlBack, parameters)));
		return buttonBack;
	}

	public Button createCancelButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.cancel.text", action, sessionData);
	}

	public Button createDuplicateButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.duplicate.text", action, sessionData);
	}

	public Button createEditButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.edit.text", action, sessionData);
	}

	public Button createLogoutButton(ResourceManager resourceManager, Supplier<Optional<UI>> uiSupplier,
			SessionData sessionData, Logger logger) {
		Button buttonLogout =
				createButton(
						resourceManager
								.getLocalizedString("commons.button.logout.text", sessionData.getLocalization()));
		buttonLogout.addClickListener(event -> uiSupplier.get().ifPresent(ui -> {
			ui.navigate(ApplicationStartView.URL);
		}));
		return buttonLogout;
	}

	public Button createRemoveButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.remove.text", action, sessionData);
	}

	public Button createResourcedButton(ResourceManager resourceManager, String resourceId,
			Consumer<ClickEvent<?>> action, SessionData sessionData) {
		Button button = createButton(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		button.addClickListener(action::accept);
		return button;
	}

	public Button createSaveButton(ResourceManager resourceManager, Consumer<ClickEvent<?>> action,
			SessionData sessionData) {
		return createResourcedButton(resourceManager, "commons.button.save.text", action, sessionData);
	}

}