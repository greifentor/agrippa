package de.ollie.agrippa.gui.vaadin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
import com.vaadin.flow.shared.Registration;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.model.Team;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.TodoPriority;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.core.service.CurrentDateService;
import de.ollie.agrippa.core.service.CurrentDateTimeService;
import de.ollie.agrippa.core.service.TaskService;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.AccessGranter;
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
@CssImport(themeFor = "vaadin-grid", value = "./styles/vaadin-grid-styles.css")
@RequiredArgsConstructor
public class MainMenuView extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

    private static final String GRID_PREFERENCE_ID_PRIORITY = "mainMenuView.grid.priority";
    private static final String GRID_PREFERENCE_ID_PROJECT = "mainMenuView.grid.project";
    private static final String GRID_PREFERENCE_ID_TEAM = "mainMenuView.grid.team";
    private static final String GRID_PREFERENCE_ID_TITLE = "mainMenuView.grid.title";
    private static final String GRID_PREFERENCE_ID_LINKS = "mainMenuView.grid.links";
    private static final String GRID_PREFERENCE_ID_TODO_AND_TASK_STATUS = "mainMenuView.grid.todoAndTaskStatus";
    private static final String GRID_PREFERENCE_ID_TODO_TITLE = "mainMenuView.grid.todoTitle";

    @AllArgsConstructor
    @Getter
    static class TaskTodoData {
        private Task task;
        private Todo todo;

        String getTodoAndTaskStatus() {
            return (getTodo().getStatus() == null
                    ? ""
                    : "(" + getTodo().getStatus().ordinal() + ") " + getTodo().getStatus().name()) + " ("
                    + getTask().getTaskStatus() + ")";
        }

        String getPriorityStr() {
            return getTodo().getPriority() == null
                    ? ""
                    : "(" + getTodo().getPriority().ordinal() + ") " + getTodo().getPriority().name();
        }

        List<Note> getLinkNotes() {
            return getTask().getNotes()
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

    private final AccessGranter accessGranter;
    private final ButtonFactory buttonFactory;
    private final ComponentFactory componentFactory;
    private final CurrentDateTimeService currentDateTimeService;
    private final CurrentDateService currentDateService;
    private final GUIConfiguration guiConfiguration;
    private final MasterDataGUIConfiguration masterDataGUIConfiguration;
    private final ResourceManager resourceManager;
    private final ServiceProvider serviceProvider;
    private final SessionData session;
    private final TaskService taskService;
    private final TodoDueStatusCssClassService todoDueStatusCssClassService;
    private final TaskTodoDataGridViewComparator taskTodoDataGridViewComparator;
    private final WebAppConfiguration configuration;

    private Grid<TaskTodoData> grid;
    private TaskTodoDataFilter filter;
    private Column<TaskTodoData> priorityColumn;
    private Column<TaskTodoData> projectTitleColumn;
    private Column<TaskTodoData> taskLinksColumn;
    private Column<TaskTodoData> taskTitleColumn;
    private Column<TaskTodoData> teamColumn;
    private Column<TaskTodoData> todoTitleColumn;
    private Column<TaskTodoData> todoAndTaskStatusColumn;

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        LOG.debug("setParameter");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        accessGranter.grantAccess(session);
        UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(session, beforeEnterEvent);
        LOG.info("created");
        setWidthFull();
        getStyle().set("background-image", "url('" + guiConfiguration.getMainMenuBackgroundFileName() + "')");
        getStyle().set("background-size", "cover");
        getStyle().set("background-attachment", "fixed");
        Button buttonMasterData = buttonFactory.createButton(
                resourceManager.getLocalizedString("main-menu.button.master-data.text", session.getLocalization()));
        buttonMasterData.addClickListener(event -> switchToMasterData());
        buttonMasterData.setWidthFull();
        Button buttonNewTask = buttonFactory.createButton(
                resourceManager.getLocalizedString("main-menu.button.new-task.text", session.getLocalization()));
        buttonNewTask.addClickListener(event -> newTask());
        buttonNewTask.setWidthFull();
        ButtonGrid buttonGridMasterData = new ButtonGrid(4, buttonMasterData, buttonNewTask);
        buttonGridMasterData.setMargin(false);
        buttonGridMasterData.setWidthFull();
        Component todoOverviewLayout = createTodoOverviewLayout();
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add(
                new HeaderLayout(
                        buttonFactory.createLogoutButton(resourceManager, this::getUI, session, LOG),
                        resourceManager.getLocalizedString("commons.header.main-menu.label", session.getLocalization())
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
        layout.getStyle()
                .set("box-shadow",
                        "10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
        layout.setMargin(false);
        layout.setPadding(true);
        layout.setSizeFull();
        grid = new Grid<>();
        todoAndTaskStatusColumn = addColumn(grid, ttd -> ttd.getTodoAndTaskStatus(), 0, "7%");
        priorityColumn = addColumn(grid, ttd -> ttd.getPriorityStr(), 1, "7%");
        teamColumn = addColumn(grid,
                ttd -> (ttd.getTask().getTeam() != null ? ttd.getTask().getTeam().getTitle() : "-"),
                2,
                "9%");
        projectTitleColumn = addColumnWithComponent(grid,
                ttd -> ttd.getTask().getProject().getProjectLinks().isEmpty()
                        ? createLabel(ttd.getTask().getProject().getTitle())
                        : createProjectButton(ttd),
                3,
                "18%",
                null,
                false);
        taskTitleColumn = addColumn(grid, ttd -> ttd.getTask().getTitle(), 4, "15%");
        taskLinksColumn = addColumnWithComponent(grid,
                ttd -> createLabel(getLinksAsHTML(ttd)),
                5,
                "10%",
                (ttd0, ttd1) -> ttd0.getLinksString().compareTo(ttd1.getLinksString()),
                true);
        todoTitleColumn = addColumn(grid, ttd -> ttd.getTodo().getTitle(), 6, "20%");
        addColumnWithComponent(grid, ttd -> createDueDateLabel(ttd), 7, "8%", null, false);
        addColumnWithComponent(grid, ttd -> createTaskButton(ttd), 8, "6%", null, false);
        grid.setClassNameGenerator(ttd -> todoDueStatusCssClassService.getCssClassName(ttd.getTodo()));
        updateGrid();
        grid.setAllRowsVisible(true);
        grid.setWidthFull();
        grid.setMultiSort(true);
        grid.addItemClickListener(e -> edit(e.getItem()));
        layout.add(grid);
        return layout;
    }

    private String getLinksAsHTML(TaskTodoData ttd) {
        return ttd.getLinkNotes()
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

    private Button createProjectButton(TaskTodoData ttd) {
        Project project = ttd.getTask().getProject();
        Button b = componentFactory.createButton(project.getTitle());
        b.addClickListener(e -> new ProjectLinksDialog(project, resourceManager, session.getLocalization()));
        b.setWidthFull();
        return b;
    }

    private Button createTaskButton(TaskTodoData ttd) {
        Button b = componentFactory.createButton(resourceManager
                .getLocalizedString("MainMenuView.gridTaskTodos.column.task.button.label", session.getLocalization()));
        b.addClickListener(e -> {
            QueryParameters parameters = new QueryParameters(Map.of("id", List.of("" + ttd.getTask().getId())));
            session.addReturnUrl(MainMenuView.URL);
            getUI().ifPresent(ui -> ui.navigate(TaskMaintenanceView.URL, parameters));
        });
        return b;
    }

    private Component createDueDateLabel(TaskTodoData ttd) {
        String s = "-";
        if (ttd.getTodo().getDueDate() != null) {
            if (isToday(ttd.getTodo().getDueDate())) {
                s = ttd.getTodo().getDueDate().toString().substring(11);
            } else {
                s = ttd.getTodo().getDueDate().toString().replace("T", " ");
            }
        }
        return new Span(s);
    }

    private boolean isToday(LocalDateTime d) {
        LocalDate today = currentDateService.now();
        return today.equals(LocalDate.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth()));
    }

    private void updateGrid() {
        List<TaskTodoData> l = new ArrayList<>();
        for (Task t : taskService.findAll()
                .stream()
                .filter(t -> (t.getTaskStatus() != TaskStatus.REJECTED) && (t.getTaskStatus() != TaskStatus.SOLVED))
                .collect(Collectors.toList())) {
            addTodo(l, t);
        }
        l = l.stream().sorted(taskTodoDataGridViewComparator).collect(Collectors.toList());
        GridListDataView<TaskTodoData> dataView = grid.setItems(l);
        filter = new TaskTodoDataFilter(dataView);
        HeaderRow headerRow = grid.getHeaderRows().size() < 2 ? grid.appendHeaderRow() : grid.getHeaderRows().get(1);
        headerRow.getCell(priorityColumn)
                .setComponent(createFilterHeader(filter::setTodoPriority,
                        GRID_PREFERENCE_ID_PRIORITY,
                        p -> resourceManager.getLocalizedString("TodoPriority." + p.name() + ".label"),
                        TodoPriority.values()));
        headerRow.getCell(projectTitleColumn)
                .setComponent(createFilterHeader(filter::setProject,
                        GRID_PREFERENCE_ID_PROJECT,
                        p -> p != null ? p.getTitle() : "",
                        serviceProvider.getProjectService().findAll()));
        headerRow.getCell(teamColumn)
                .setComponent(createFilterHeader(filter::setTeam,
                        GRID_PREFERENCE_ID_TEAM,
                        t -> t != null ? t.getTitle() : "",
                        serviceProvider.getTeamService().findAll()));
        headerRow.getCell(taskTitleColumn)
                .setComponent(createFilterHeader(filter::setTaskTitle, GRID_PREFERENCE_ID_TITLE));
        headerRow.getCell(taskLinksColumn)
                .setComponent(createFilterHeader(filter::setTaskLinks, GRID_PREFERENCE_ID_LINKS));
        headerRow.getCell(todoAndTaskStatusColumn)
                .setComponent(
                        createFilterHeader(filter::setTodoAndTaskStatus, GRID_PREFERENCE_ID_TODO_AND_TASK_STATUS));
        headerRow.getCell(todoTitleColumn)
                .setComponent(createFilterHeader(filter::setTodoTitle, GRID_PREFERENCE_ID_TODO_TITLE));
    }

    @SuppressWarnings("unchecked")
    private <T> T getPreference(String id) {
        return (T) session.findParameter(id).orElse(null);
    }

    private Column<TaskTodoData> addColumn(Grid<TaskTodoData> grid, ValueProvider<TaskTodoData, ?> valueProvider,
            int columnNr, String width) {
        return grid.addColumn(valueProvider)
                .setHeader(
                        resourceManager.getLocalizedString("MainMenuView.gridTaskTodos.column." + columnNr + ".label",
                                session.getLocalization()))
                .setSortable(true)
                .setWidth(width);
    }

    private Column<TaskTodoData> addColumnWithComponent(Grid<TaskTodoData> grid,
            SerializableFunction<TaskTodoData, Component> renderer, int columnNr, String width,
            Comparator<TaskTodoData> comparator, boolean sortable) {
        Column<TaskTodoData> column = grid.addColumn(new ComponentRenderer<Component, TaskTodoData>(renderer))
                .setHeader(
                        resourceManager.getLocalizedString("MainMenuView.gridTaskTodos.column." + columnNr + ".label",
                                session.getLocalization()))
                .setSortable(sortable)
                .setWidth(width);
        if (comparator != null) {
            column.setComparator(comparator);
        }
        return column;
    }

    private void addTodo(List<TaskTodoData> l, Task task) {
        task.getTodos()
                .stream()
                .filter(t -> (t.getStatus() != TodoStatus.REJECTED) && (t.getStatus() != TodoStatus.SOLVED))
                .forEach(t -> l.add(new TaskTodoData(task, t)));
    }

    private void switchToMasterData() {
        getUI().ifPresent(ui -> ui.navigate(MasterDataView.URL));
    }

    private void newTask() {
        session.addReturnUrl(URL);
        getUI().ifPresent(ui -> ui.navigate(TaskMaintenanceView.URL));
    }

    private Component createFilterHeader(Consumer<String> filterChangeConsumer, String id) {
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.setWidthFull();
        textField.addValueChangeListener(e -> {
            filterChangeConsumer.accept(e.getValue());
            session.setParameter(id, textField.getValue());
        });
        String preference = getPreference(id);
        if (preference != null) {
            textField.setValue(preference);
        }
        return textField;
    }

    private <T> Component createFilterHeader(Consumer<T> filterChangeConsumer, String id,
            ItemLabelGenerator<T> renderer, T... selectableObjects) {
        return createFilterHeader(filterChangeConsumer, id, renderer, List.of(selectableObjects));
    }

    private <T> Component createFilterHeader(Consumer<T> filterChangeConsumer, String id,
            ItemLabelGenerator<T> renderer, List<T> selectableObjects) {
        ComboBox<T> comboBox = new ComboBox<>(null, selectableObjects);
        comboBox.setClearButtonVisible(true);
        comboBox.setItemLabelGenerator(renderer);
        comboBox.setWidthFull();
        comboBox.addValueChangeListener(e -> {
            filterChangeConsumer.accept(e.getValue());
            session.setParameter(id, comboBox.getValue());
        });
        T preference = getPreference(id);
        if (preference != null) {
            System.out.println(preference + " - " + id);
            comboBox.setValue(preference);
        }
        return comboBox;
    }

    private void edit(TaskTodoData ttd) {
        new TodoDetailsDialog(componentFactory, masterDataGUIConfiguration, (t, b) -> {
            ttd.getTodo().setDescription(t.getDescription());
            ttd.getTodo().setDueDate(t.getDueDate());
            ttd.getTodo().setPriority(t.getPriority());
            ttd.getTodo().setStatus(t.getStatus());
            ttd.getTodo().setTitle(t.getTitle());
            taskService.update(ttd.getTask());
            updateGrid();
        }, session, ttd.todo, serviceProvider, false).open();
    }

    private Registration registration;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        LOG.info("onAttach");
        super.onAttach(attachEvent);
        UI.getCurrent().setPollInterval(60000);
        registration = UI.getCurrent().addPollListener(pollEvent -> pollEvent.getSource().access(() -> {
            LOG.info("poll event detected");
            updateGrid();
        }));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
        super.onDetach(detachEvent);
        LOG.info("onDetach");
    }

}

class TaskTodoDataFilter {
    private GridListDataView<TaskTodoData> dataView;

    private Project project;
    private String taskLinks;
    private String taskTitle;
    private Team team;
    private String todoTitle;
    private String todoAndTaskStatus;
    private TodoPriority todoPriority;

    public TaskTodoDataFilter(GridListDataView<TaskTodoData> dataView) {
        this.dataView = dataView;
        this.dataView.addFilter(this::test);
    }

    public void setDataView(GridListDataView<TaskTodoData> dataView) {
        this.dataView = dataView;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public void setTeam(Team team) {
        this.team = team;
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

    public void setTodoPriority(TodoPriority todoPriority) {
        this.todoPriority = todoPriority;
        this.dataView.refreshAll();
    }

    public boolean test(TaskTodoData taskTodoData) {
        boolean matchesProject = matches(taskTodoData.getTask().getProject(), project);
        boolean matchesTaskLinks = matches(taskTodoData.getLinksString(), taskLinks);
        boolean matchesTaskTitle = matches(taskTodoData.getTask().getTitle(), taskTitle);
        boolean matchesTeam = matches(
                (taskTodoData.getTask().getTeam() != null ? taskTodoData.getTask().getTeam() : null),
                team);
        boolean matchesTodoTitle = matches(taskTodoData.getTodo().getTitle(), todoTitle);
        boolean matchesTodoAndTaskStatus = matches(taskTodoData.getTodoAndTaskStatus(), todoAndTaskStatus);
        boolean matchesTodoPriority = matches(taskTodoData.getTodo().getPriority(), todoPriority);

        return matchesProject && matchesTaskLinks && matchesTaskTitle && matchesTeam && matchesTodoTitle
                && matchesTodoAndTaskStatus && matchesTodoPriority;
    }

    private <T> boolean matches(T value, T searchTerm) {
        return searchTerm == null || searchTerm.equals(value);
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty() || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}