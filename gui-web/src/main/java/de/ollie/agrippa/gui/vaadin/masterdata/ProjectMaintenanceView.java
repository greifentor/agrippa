package de.ollie.agrippa.gui.vaadin.masterdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.UserAuthorizationChecker;
import de.ollie.agrippa.gui.vaadin.component.AbstractMasterDataBaseLayout;
import de.ollie.agrippa.gui.vaadin.component.ButtonFactory;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A dialog to edit Project details.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Route(ProjectMaintenanceView.URL)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(value = "./styles/vaadin-text-area-styles.css", themeFor = "vaadin-text-area")
@CssImport(value = "./styles/vaadin-combo-box-styles.css", themeFor = "vaadin-combo-box")
@CssImport(value = "./styles/vaadin-checkbox-styles.css", themeFor = "vaadin-checkbox")
@RequiredArgsConstructor
public class ProjectMaintenanceView extends AbstractMasterDataBaseLayout implements ProjectDetailsLayout.Observer {

	public static final String URL = "agrippa/masterdata/projects/details";

	private static final Logger logger = LogManager.getLogger(ProjectMaintenanceView.class);

	@Autowired(required = false)
	private MaintenanceViewCreateNewModelModification<Project> createNewModelModification;
	@Autowired(required = false)
	private MaintenanceViewRenderer<Project> maintenanceViewRenderer;
	@Autowired(required = false)
	private DetailsLayoutComboBoxItemLabelGenerator<Project> comboBoxItemLabelGenerator;

	private final ButtonFactory buttonFactory;
	private final ComponentFactory componentFactory;
	private final ResourceManager resourceManager;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final ServiceProvider serviceProvider;
	private final SessionData session;

	private Project model;

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
	public void doSetParameter(BeforeEvent event) {
		long id =
				parametersMap.containsKey("id") && (parametersMap.get("id").size() > 0)
						? Long.parseLong(parametersMap.get("id").get(0))
						: -1;
		model = serviceProvider.getProjectService().findById(id).orElse(createNewModel());
		if (parametersMap.containsKey("duplicate") && "true".equals(parametersMap.get("duplicate").get(0))) {
			model.setId(-1);
		}
	}

	private Project createNewModel() {
		Project model = new Project();
		if (createNewModelModification != null) {
			model = createNewModelModification.modify(model);
		}
		return model;
	}

	@Override
	public void doBeforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(getSessionData(), beforeEnterEvent);
		setMargin(false);
		setSizeFull();
		getStyle().set("background-image", "url('" + guiConfiguration.getBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		getStyle().set("background-attachment", "fixed");
		add(
				new HeaderLayout(
						buttonFactory.createBackButton(resourceManager, this::getUI, ProjectPageView.URL, session),
						buttonFactory.createLogoutButton(resourceManager, this::getUI, session, logger),
						resourceManager
								.getLocalizedString(
										"ProjectMaintenanceView.header.prefix.label",
										session.getLocalization())
								+ getHeaderSuffix(model),
						HeaderLayoutMode.PLAIN),
				getDetailsLayout(model));
	}

	private String getHeaderSuffix(Project model) {
		return maintenanceViewRenderer != null ? maintenanceViewRenderer.getHeaderSuffix(model) : "" + model.getTitle();
	}

	private AbstractMasterDataBaseLayout getDetailsLayout(Project model) {
		return new ProjectDetailsLayout(
				buttonFactory,
				componentFactory,
				model,
				serviceProvider,
				guiConfiguration,
				resourceManager,
				session,
				this,
				comboBoxItemLabelGenerator);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		logger.info("onAttach");
		super.onAttach(attachEvent);
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		logger.info("onDetach");
		super.onDetach(detachEvent);
		getElement().removeFromTree();
	}

	@Override
	public void save(Object model) {
		getUI().ifPresent(ui -> ui.navigate(ProjectPageView.URL));
	}

	@Override
	public void save() {
		save(model);
	}

	@Override
	public void remove() {
		serviceProvider.getProjectService().delete(model);
		getUI().ifPresent(ui -> ui.navigate(ProjectPageView.URL));
	}

}