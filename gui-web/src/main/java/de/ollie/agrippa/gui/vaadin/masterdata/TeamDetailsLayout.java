package de.ollie.agrippa.gui.vaadin.masterdata;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.agrippa.core.model.Team;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.core.service.TeamService;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.component.AbstractMasterDataBaseLayout;
import de.ollie.agrippa.gui.vaadin.component.ButtonFactory;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import de.ollie.agrippa.gui.vaadin.component.RemoveConfirmDialog;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@RequiredArgsConstructor
public class TeamDetailsLayout extends AbstractMasterDataBaseLayout {

	private final ButtonFactory buttonFactory;
	private final ComponentFactory componentFactory;
	private final Team model;
	private final ServiceProvider serviceProvider;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final Observer observer;
	private final DetailsLayoutComboBoxItemLabelGenerator<Team> comboBoxItemLabelGenerator;

	private TextField textFieldTitle;
	private TextArea textAreaDescription;

	@Override
	public void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		createButtons();
		textFieldTitle = createTextField("TeamDetailsLayout.field.title.label", model.getTitle());
		textAreaDescription = createTextArea("TeamDetailsLayout.field.description.label", model.getDescription());
		getStyle().set("-moz-border-radius", "4px");
		getStyle().set("-webkit-border-radius", "4px");
		getStyle().set("border-radius", "4px");
		getStyle().set("border", "1px solid #A9A9A9");
		getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		setMargin(false);
		setWidthFull();
		add(
				textFieldTitle,
				textAreaDescription,
				getMasterDataButtonLayout(model.getId() > 0));
		textFieldTitle.focus();
	}

	@Override
	protected ButtonFactory getButtonFactory() {
		return buttonFactory;
	}

	@Override
	protected ResourceManager getResourceManager() {
		return resourceManager;
	}

	@Override
	protected SessionData getSessionData() {
		return session;
	}

	@Override
	protected void remove() {
		new RemoveConfirmDialog(buttonFactory, () -> {
			serviceProvider.getTeamService().delete(model);
			observer.remove();
		}, resourceManager, session).open();
	}

	@Override
	protected void save() {
		model.setTitle(textFieldTitle.getValue());
		model.setDescription(textAreaDescription.getValue());
		try {
			observer.save(serviceProvider.getTeamService().update(model));
		} catch (PersistenceFailureException pfe) {
			PopupNotification.showError(pfe.getLocalizedMessage(resourceManager, session.getLocalization()));
		}
	}

}