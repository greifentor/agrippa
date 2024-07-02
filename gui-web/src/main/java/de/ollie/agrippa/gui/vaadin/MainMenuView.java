package de.ollie.agrippa.gui.vaadin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.core.service.TaskService;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.component.Button;
import de.ollie.agrippa.gui.vaadin.component.ButtonFactory;
import de.ollie.agrippa.gui.vaadin.component.ButtonGrid;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataView;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A main menu view for the application.
 */
@Generated
@Route(MainMenuView.URL)
@RequiredArgsConstructor
public class MainMenuView extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final String URL = "agrippa/menu";

	private static final Logger LOG = LogManager.getLogger(MainMenuView.class);

	private final ButtonFactory buttonFactory;
	private final GUIConfiguration guiConfiguration;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final TaskService taskService;
	private final WebAppConfiguration configuration;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		LOG.debug("setParameter");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(session, beforeEnterEvent);
		LOG.info("created");
		setWidthFull();
		getStyle().set("background-image", "url('" + guiConfiguration.getMainMenuBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		getStyle().set("background-attachment", "fixed");
		Button buttonMasterData =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"main-menu.button.master-data.text",
												session.getLocalization()));
		buttonMasterData.addClickListener(event -> switchToMasterData());
		buttonMasterData.setWidthFull();
		ButtonGrid buttonGridMasterData = new ButtonGrid(4, buttonMasterData);
		buttonGridMasterData.setMargin(false);
		buttonGridMasterData.setWidthFull();
		Component todoOverviewLayout = createTodoOverviewLayout();
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout
				.add(
						new HeaderLayout(
								buttonFactory.createLogoutButton(resourceManager, this::getUI, session, LOG),
								resourceManager
										.getLocalizedString("commons.header.main-menu.label", session.getLocalization())
										.replace("{0}", configuration.getAppVersion()),
								HeaderLayoutMode.PLAIN),
						buttonGridMasterData,
						todoOverviewLayout);
		setContent(mainLayout);
		LOG.info("main menu view opened for user '{}'.", session.getUserName());
	}

	private Component createTodoOverviewLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.getStyle().set("-moz-border-radius", "4px");
		layout.getStyle().set("-webkit-border-radius", "4px");
		layout.getStyle().set("border-radius", "4px");
		layout.getStyle().set("border", "1px solid gray");
		layout
				.getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		layout.setMargin(false);
		layout.setPadding(true);
		layout.setSizeFull();
		taskService
				.findAll()
				.stream()
				.filter(t -> t.getTaskStatus() != TaskStatus.SOLVED)
				.forEach(t -> addTodo(layout, t));
		return layout;
	}

	private void addTodo(VerticalLayout layout, Task task) {
		task
				.getTodos()
				.stream()
				.filter(t -> t.getStatus() != TodoStatus.SOLVED)
				.forEach(t -> layout.add(createTodoLayout(task, t)));
	}

	private Component createTodoLayout(Task task, Todo todo) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(false);
		layout.setPadding(false);
		layout.setWidthFull();
        layout.add(createLabel(todo.getStatus().name() + " (" + task.getTaskStatus() + ")", "10%"));
        layout.add(createLabel(task.getProject().getTitle(), "20%"));
        layout.add(createLabel(task.getTitle(), "20%"));
        layout.add(createLabel(todo.getTitle(), "50%"));
		return layout;
	}

	private Component createLabel(String text, String width) {
		Label label = new Label(text);
		label.setWidth(width);
		return label;
	}

	private void switchToMasterData() {
		getUI().ifPresent(ui -> ui.navigate(MasterDataView.URL));
	}

}