package de.ollie.agrippa.gui.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
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
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A main menu view for the application.
 */
@Generated
@Route(MainMenuView.URL)
@RequiredArgsConstructor
public class MainMenuView extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

	@AllArgsConstructor
	@Getter
	private class TaskTodoData {
		private Task task;
		private Todo todo;
	}

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
		List<TaskTodoData> l = new ArrayList<>();
		taskService.findAll().stream().filter(t -> t.getTaskStatus() != TaskStatus.SOLVED).forEach(t -> addTodo(l, t));
		Grid<TaskTodoData> grid = new Grid<>();
		addColumn(grid, ttd -> ttd.getTodo().getStatus().name() + " (" + ttd.getTask().getTaskStatus() + ")", 0, "10%");
		addColumn(grid, ttd -> ttd.getTask().getProject().getTitle(), 1, "20%");
		addColumn(grid, ttd -> ttd.getTask().getTitle(), 2, "20%");
		addColumn(grid, ttd -> ttd.getTodo().getTitle(), 3, "50%");
		grid.setItems(l);
		grid.setPageSize(20);
		grid.setWidthFull();
		grid.setMultiSort(true);
		layout.add(grid);
		return layout;
	}

	private void addColumn(Grid<TaskTodoData> grid, ValueProvider<TaskTodoData, ?> valueProvider, int columnNr,
			String width) {
		grid
				.addColumn(valueProvider)
				.setHeader(
						resourceManager
								.getLocalizedString(
										"MainMenuView.gridTaskTodos.column." + columnNr + ".label",
										session.getLocalization()))
				.setSortable(true)
				.setWidth(width);
	}

	private void addTodo(List<TaskTodoData> l, Task task) {
		task
				.getTodos()
				.stream()
				.filter(t -> t.getStatus() != TodoStatus.SOLVED)
				.forEach(t -> l.add(new TaskTodoData(task, t)));
	}

	private void switchToMasterData() {
		getUI().ifPresent(ui -> ui.navigate(MasterDataView.URL));
	}

}