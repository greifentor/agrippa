package de.ollie.agrippa.gui.vaadin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.core.service.TaskService;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.MainMenuView.TaskTodoData;
import de.ollie.agrippa.gui.vaadin.component.Button;
import de.ollie.agrippa.gui.vaadin.component.ButtonFactory;
import de.ollie.agrippa.gui.vaadin.component.ButtonGrid;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import de.ollie.agrippa.gui.vaadin.component.TextField;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataView;
import de.ollie.agrippa.gui.vaadin.masterdata.TaskMaintenanceView;
import de.ollie.agrippa.gui.vaadin.masterdata.dialog.TodoDetailsDialog;
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
	class TaskTodoData {
		private Task task;
		private Todo todo;

		String getTodoAndTaskStatus() {
			return "(" + getTodo().getStatus().ordinal() + ") " + getTodo().getStatus().name() + " ("
					+ getTask().getTaskStatus() + ")";
		}

		String getPriorityStr() {
			return "(" + getTodo().getPriority().ordinal() + ") " + getTodo().getPriority().name();
		}

		List<Note> getLinkNotes() {
			return getTask()
					.getNotes()
					.stream()
					.filter(n -> (n.getType() == NoteType.LINK) && (n.getUrl() != null))
					.collect(Collectors.toList());
		}

		String getLinksString() {
			return getLinkNotes().stream().map(Note::getUrl).reduce((s0, s1) -> s0 + "," + s1).orElse("-");
		}
	}

	public static final String URL = "agrippa/menu";

	private static final Logger LOG = LogManager.getLogger(MainMenuView.class);

	private final ButtonFactory buttonFactory;
	private final ComponentFactory componentFactory;
	private final GUIConfiguration guiConfiguration;
	private final MasterDataGUIConfiguration masterDataGUIConfiguration;
	private final ResourceManager resourceManager;
	private final ServiceProvider serviceProvider;
	private final SessionData session;
	private final TaskService taskService;
	private final WebAppConfiguration configuration;

	private Grid<TaskTodoData> grid;
	private TaskTodoDataFilter filter;
	private Column<TaskTodoData> priorityColumn;
	private Column<TaskTodoData> projectTitleColumn;
	private Column<TaskTodoData> taskLinksColumn;
	private Column<TaskTodoData> taskTitleColumn;
	private Column<TaskTodoData> teamTitleColumn;
	private Column<TaskTodoData> todoTitleColumn;
	private Column<TaskTodoData> todoAndTaskStatusColumn;

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
		grid = new Grid<>();
		todoAndTaskStatusColumn = addColumn(grid, ttd -> ttd.getTodoAndTaskStatus(), 0, "7%");
		priorityColumn = addColumn(grid, ttd -> ttd.getPriorityStr(), 1, "7%");
		teamTitleColumn =
				addColumn(
						grid,
						ttd -> (ttd.getTask().getTeam() != null ? ttd.getTask().getTeam().getTitle() : "-"),
						2,
						"9%");
		projectTitleColumn = addColumn(grid, ttd -> ttd.getTask().getProject().getTitle(), 3, "14%");
		taskTitleColumn = addColumn(grid, ttd -> ttd.getTask().getTitle(), 4, "18%");
		taskLinksColumn =
				addColumnWithComponent(
						grid,
						ttd -> createLabel(getLinksAsHTML(ttd)),
						5,
						"10%",
						(ttd0, ttd1) -> ttd0.getLinksString().compareTo(ttd1.getLinksString()),
						true);
		todoTitleColumn = addColumn(grid, ttd -> ttd.getTodo().getTitle(), 6, "30%");
		addColumnWithComponent(grid, ttd -> createTaskButton(ttd), 7, "5%", null, false);
		updateGrid();
		grid.setAllRowsVisible(true);
		grid.setWidthFull();
		grid.setMultiSort(true);
		grid.addItemClickListener(e -> edit(e.getItem()));
		layout.add(grid);
		return layout;
	}

	private String getLinksAsHTML(TaskTodoData ttd) {
		return ttd
				.getLinkNotes()
				.stream()
				.map(n -> "<A HREF=\"" + n.getUrl() + "\">" + n.getTitle() + "</A>")
				.reduce((s0, s1) -> s0 + ", " + s1)
				.orElse("");
	}

	private Component createLabel(String s) {
		Label label = new Label();
		label.getElement().setProperty("innerHTML", s);
		return label;
	}

	private Button createTaskButton(TaskTodoData ttd) {
		Button b =
				componentFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"MainMenuView.gridTaskTodos.column.task.button.label",
												session.getLocalization()));
		b.addClickListener(e -> {
			QueryParameters parameters = new QueryParameters(Map.of("id", List.of("" + ttd.getTask().getId())));
			session.addReturnUrl(MainMenuView.URL);
			getUI().ifPresent(ui -> ui.navigate(TaskMaintenanceView.URL, parameters));
		});
		return b;
	}

	private void updateGrid() {
		List<TaskTodoData> l = new ArrayList<>();
		for (Task t : taskService
				.findAll()
				.stream()
				.filter(t -> t.getTaskStatus() != TaskStatus.SOLVED)
				.collect(Collectors.toList())) {
			addTodo(l, t);
		}
		l =
				l
						.stream()
						.sorted((ttd0, ttd1) -> ttd0.getPriorityStr().compareTo(ttd1.getPriorityStr()))
						.collect(Collectors.toList());
		GridListDataView<TaskTodoData> dataView = grid.setItems(l);
		filter = new TaskTodoDataFilter(dataView);
		HeaderRow headerRow = grid.getHeaderRows().size() < 2 ? grid.appendHeaderRow() : grid.getHeaderRows().get(1);
		headerRow.getCell(priorityColumn).setComponent(createFilterHeader("Priority", filter::setTodoPriority));
		headerRow.getCell(projectTitleColumn).setComponent(createFilterHeader("Project", filter::setProjectTitle));
		headerRow.getCell(teamTitleColumn).setComponent(createFilterHeader("Team", filter::setTeamTitle));
		headerRow.getCell(taskTitleColumn).setComponent(createFilterHeader("Task", filter::setTaskTitle));
		headerRow.getCell(taskLinksColumn).setComponent(createFilterHeader("Links", filter::setTaskLinks));
		headerRow
				.getCell(todoAndTaskStatusColumn)
				.setComponent(createFilterHeader("Name", filter::setTodoAndTaskStatus));
		headerRow.getCell(todoTitleColumn).setComponent(createFilterHeader("Todo", filter::setTodoTitle));
	}

	private Column<TaskTodoData> addColumn(Grid<TaskTodoData> grid, ValueProvider<TaskTodoData, ?> valueProvider,
			int columnNr, String width) {
		return grid
				.addColumn(valueProvider)
				.setHeader(
						resourceManager
								.getLocalizedString(
										"MainMenuView.gridTaskTodos.column." + columnNr + ".label",
										session.getLocalization()))
				.setSortable(true)
				.setWidth(width);
	}

	private Column<TaskTodoData> addColumnWithComponent(Grid<TaskTodoData> grid,
			SerializableFunction<TaskTodoData, Component> renderer, int columnNr, String width,
			Comparator<TaskTodoData> comparator, boolean sortable) {
		Column<TaskTodoData> column =
				grid
						.addColumn(new ComponentRenderer<Component, TaskTodoData>(renderer))
						.setHeader(
								resourceManager
										.getLocalizedString(
												"MainMenuView.gridTaskTodos.column." + columnNr + ".label",
												session.getLocalization()))
						.setSortable(sortable)
						.setWidth(width);
		if (comparator != null) {
			column.setComparator(comparator);
		}
		return column;
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

	private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
		TextField textField = new TextField();
		textField.setValueChangeMode(ValueChangeMode.EAGER);
		textField.setClearButtonVisible(true);
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		textField.setWidthFull();
		textField.getStyle().set("max-width", "100%");
		textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
		return textField;
	}

	private void edit(TaskTodoData ttd) {
		new TodoDetailsDialog(componentFactory, masterDataGUIConfiguration, (t, b) -> {
			ttd.getTodo().setDescription(t.getDescription());
			ttd.getTodo().setPriority(t.getPriority());
			ttd.getTodo().setStatus(t.getStatus());
			ttd.getTodo().setTitle(t.getTitle());
			taskService.update(ttd.getTask());
			updateGrid();
		}, session, ttd.todo, serviceProvider, false).open();
	}

}

class TaskTodoDataFilter {
	private GridListDataView<TaskTodoData> dataView;

	private String projectTitle;
	private String taskLinks;
	private String taskTitle;
	private String teamTitle;
	private String todoTitle;
	private String todoAndTaskStatus;
	private String todoPriorityStr;

	public TaskTodoDataFilter(GridListDataView<TaskTodoData> dataView) {
		this.dataView = dataView;
		this.dataView.addFilter(this::test);
	}

	public void setDataView(GridListDataView<TaskTodoData> dataView) {
		this.dataView = dataView;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
		this.dataView.refreshAll();
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
		this.dataView.refreshAll();
	}

	public void setTaskLinks(String taskLinks) {
		this.taskLinks = taskLinks;
		this.dataView.refreshAll();
	}

	public void setTeamTitle(String teamTitle) {
		this.teamTitle = teamTitle;
		this.dataView.refreshAll();
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
		this.dataView.refreshAll();
	}

	public void setTodoAndTaskStatus(String todoAndTaskStatus) {
		this.todoAndTaskStatus = todoAndTaskStatus;
		this.dataView.refreshAll();
	}

	public void setTodoPriority(String todoPriorityStr) {
		this.todoPriorityStr = todoPriorityStr;
		this.dataView.refreshAll();
	}

	public boolean test(TaskTodoData taskTodoData) {
		boolean matchesProjectTitle = matches(taskTodoData.getTask().getProject().getTitle(), projectTitle);
		boolean matchesTaskLinks = matches(taskTodoData.getLinksString(), taskLinks);
		boolean matchesTaskTitle = matches(taskTodoData.getTask().getTitle(), taskTitle);
		boolean matchesTeamTitle =
				matches(
						(taskTodoData.getTask().getTeam() != null ? taskTodoData.getTask().getTeam().getTitle() : "-"),
						teamTitle);
		boolean matchesTodoTitle = matches(taskTodoData.getTodo().getTitle(), todoTitle);
		boolean matchesTodoAndTaskStatus = matches(taskTodoData.getTodoAndTaskStatus(), todoAndTaskStatus);
		boolean matchesTodoPriority = matches(taskTodoData.getPriorityStr(), todoPriorityStr);

		return matchesProjectTitle && matchesTaskLinks && matchesTaskTitle && matchesTeamTitle && matchesTodoTitle
				&& matchesTodoAndTaskStatus && matchesTodoPriority;
	}

	private boolean matches(String value, String searchTerm) {
		return searchTerm == null || searchTerm.isEmpty() || value.toLowerCase().contains(searchTerm.toLowerCase());
	}
}