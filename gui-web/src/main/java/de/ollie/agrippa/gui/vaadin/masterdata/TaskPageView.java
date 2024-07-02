package de.ollie.agrippa.gui.vaadin.masterdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.PageParameters;
import de.ollie.agrippa.core.service.TaskService;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.UserAuthorizationChecker;
import de.ollie.agrippa.gui.vaadin.component.Button;
import de.ollie.agrippa.gui.vaadin.component.ButtonFactory;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout;
import de.ollie.agrippa.gui.vaadin.component.HeaderLayout.HeaderLayoutMode;
import de.ollie.agrippa.gui.vaadin.component.MasterDataButtonLayout;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import de.ollie.agrippa.gui.vaadin.component.RemoveConfirmDialog;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * A view for paginated task lists.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Route(TaskPageView.URL)
@RequiredArgsConstructor
public class TaskPageView extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final String URL = "agrippa/masterdata/tasks";

	private static final Logger logger = LogManager.getLogger(TaskPageView.class);
	private static final String PARAMETER_FILTER = "TaskPageView.Filter";

	@Autowired(required = false)
	private MasterDataGridFieldRenderer<Task> masterDataGridFieldRenderer;

	private final ButtonFactory buttonFactory;
	private final ResourceManager resourceManager;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final TaskService service;
	private final SessionData session;

	private Button buttonAdd;
	private Button buttonDuplicate;
	private Button buttonEdit;
	private Button buttonRemove;
	private Grid<Task> grid;
	private TextField textFieldFilter;
	private VerticalLayout mainLayout;

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		logger.debug("setParameter");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(session, beforeEnterEvent);
		mainLayout = new VerticalLayout();
		getStyle().set("background-image", "url('" + guiConfiguration.getBackgroundFileName() + "')");
		getStyle().set("background-size", "cover");
		getStyle().set("background-attachment", "fixed");
		textFieldFilter = new TextField();
		textFieldFilter.addKeyUpListener(event -> {
			if (event.getKey() == Key.ENTER) {
				updateGrid(0);
			}
		});
		textFieldFilter.setWidthFull();
		session.findParameter(PARAMETER_FILTER).ifPresent(s -> textFieldFilter.setValue(s.toString()));
		textFieldFilter.addValueChangeListener(event -> updateGrid(0));
		buttonAdd = buttonFactory.createAddButton(resourceManager, event -> addRecord(), session);
		buttonDuplicate = buttonFactory.createButton(resourceManager.getLocalizedString("commons.button.duplicate.text", session.getLocalization()));
		buttonDuplicate.addClickListener(event -> duplicateRecord());
		buttonEdit = buttonFactory.createEditButton(resourceManager, event -> editRecord(), session);
		buttonRemove = buttonFactory.createRemoveButton(resourceManager, event -> removeRecord(), session);
		grid = new Grid<>();
		grid
				.addColumn(model -> getHeaderString("TITLE", model, () -> model.getTitle()))
				.setHeader(resourceManager.getLocalizedString("TaskPageView.grid.header.title.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("PROJECT", model, () -> model.getProject()))
				.setHeader(resourceManager.getLocalizedString("TaskPageView.grid.header.project.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("TASKSTATUS", model, () -> model.getTaskStatus()))
				.setHeader(resourceManager.getLocalizedString("TaskPageView.grid.header.taskstatus.label", session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getHeaderString("DESCRIPTION", model, () -> model.getDescription()))
				.setHeader(resourceManager.getLocalizedString("TaskPageView.grid.header.description.label", session.getLocalization()))
				.setSortable(true);
		grid.setMultiSort(true);
		grid.setWidthFull();
		grid.addSelectionListener(this::enabledButtons);
		grid.getStyle().set("-moz-border-radius", "4px");
		grid.getStyle().set("-webkit-border-radius", "4px");
		grid.getStyle().set("border-radius", "4px");
		grid.getStyle().set("border", "1px solid #A9A9A9");
		MasterDataButtonLayout buttonLayout = new MasterDataButtonLayout(buttonAdd, buttonEdit, buttonDuplicate, buttonRemove);
		buttonLayout.setMargin(false);
		buttonLayout.setWidthFull();
		mainLayout.setMargin(false);
		mainLayout.setSizeFull();
		setSizeFull();
		VerticalLayout filterLayout = new VerticalLayout();
		filterLayout.getStyle().set("-moz-border-radius", "4px");
		filterLayout.getStyle().set("-webkit-border-radius", "4px");
		filterLayout.getStyle().set("border-radius", "4px");
		filterLayout.getStyle().set("border", "1px solid #A9A9A9");
		filterLayout
				.getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		filterLayout.setMargin(false);
		filterLayout.setWidthFull();
		filterLayout.add(textFieldFilter);
		VerticalLayout dataLayout = new VerticalLayout();
		dataLayout.getStyle().set("-moz-border-radius", "4px");
		dataLayout.getStyle().set("-webkit-border-radius", "4px");
		dataLayout.getStyle().set("border-radius", "4px");
		dataLayout.getStyle().set("border", "1px solid #A9A9A9");
		dataLayout
				.getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		dataLayout.setMargin(false);
		dataLayout.setWidthFull();
		dataLayout.add(grid, buttonLayout);
		mainLayout.add(
				new HeaderLayout(
						buttonFactory.createBackButton(resourceManager, this::getUI, MasterDataView.URL, session),
						buttonFactory.createLogoutButton(resourceManager, this::getUI, session, logger),
						resourceManager.getLocalizedString("TaskPageView.header.label", session.getLocalization()),
						HeaderLayoutMode.PLAIN),
				filterLayout,
				dataLayout);
		updateGrid(0);
		setButtonEnabled(buttonDuplicate, false);
		setButtonEnabled(buttonEdit, false);
		setButtonEnabled(buttonRemove, false);
		setContent(mainLayout);
		textFieldFilter.focus();
	}

	private Object getHeaderString(String fieldName, Task aTable, Supplier<?> f) {
		return masterDataGridFieldRenderer != null && masterDataGridFieldRenderer.hasRenderingFor(fieldName)
				? masterDataGridFieldRenderer.getHeaderString(fieldName, aTable)
				: f.get();
	}

	private void enabledButtons(SelectionEvent<Grid<Task>, Task> event) {
		if (event.getFirstSelectedItem().isEmpty()) {
			setButtonEnabled(buttonAdd, true);
			setButtonEnabled(buttonDuplicate, false);
			setButtonEnabled(buttonEdit, false);
			setButtonEnabled(buttonRemove, false);
		} else {
			setButtonEnabled(buttonAdd, false);
			setButtonEnabled(buttonDuplicate, true);
			setButtonEnabled(buttonEdit, true);
			setButtonEnabled(buttonRemove, true);
		}
	}

	private void setButtonEnabled(Button button, boolean enabled) {
		button.setEnabled(enabled);
		if (enabled) {
			button.setBackgroundImage(guiConfiguration.getButtonEnabledBackgroundFileName());
			button.setBorderColor(guiConfiguration.getButtonEnabledBorderColor());
		} else {
			button.setBackgroundImage(guiConfiguration.getButtonDisabledBackgroundFileName());
			button.setBorderColor(guiConfiguration.getButtonDisabledBorderColor());
		}
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		logger.info("Task page layout opened for user '{}'.", session.getUserName());
		super.onAttach(attachEvent);
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		logger.info("onDetach");
		super.onDetach(detachEvent);
		getElement().removeFromTree();
	}

	private void updateGrid(int pageNumber) {
		grid
				.setItems(
						service
								.findAll(new PageParameters().setEntriesPerPage(Integer.MAX_VALUE).setPageNumber(pageNumber))
								.getEntries()
								.stream()
								.filter(this::isMatching)
								.collect(Collectors.toList()));
	}

	private boolean isMatching(Task model) {
		List<String> patterns =
				getWords(textFieldFilter.getValue()).stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
		if (patterns.isEmpty()) {
			return true;
		}
		boolean b = true;
		for (String pattern : patterns) {
			b &= isMatchingPattern(pattern.toLowerCase(), model);
		}
		return b;
	}

	private List<String> getWords(String s) {
		List<String> l = new ArrayList<>();
		if (s != null) {
			StringTokenizer st = new StringTokenizer(s, " ");
			while (st.hasMoreTokens()) {
				l.add(st.nextToken());
			}
		}
		return l;
	}

	private boolean isMatchingPattern(String pattern, Task model) {
		boolean result = false;
		result = result || getHeaderString(Task.PROJECT, model, () -> model.getProject()).toString().toLowerCase().contains(pattern);
		result = result || getHeaderString(Task.TASKSTATUS, model, () -> model.getTaskStatus()).toString().toLowerCase().contains(pattern);
		result = result || getHeaderString(Task.TITLE, model, () -> model.getTitle()).toString().toLowerCase().contains(pattern);
		return result;
	}

	private void addRecord() {
		session.setParameter(PARAMETER_FILTER, textFieldFilter.getValue());
		getUI().ifPresent(ui -> ui.navigate(TaskMaintenanceView.URL));
	}

	private void duplicateRecord() {
		session.setParameter(PARAMETER_FILTER, textFieldFilter.getValue());
		grid.getSelectedItems().stream().findFirst().ifPresent(model -> {
			QueryParameters parameters =
					new QueryParameters(Map.of("id", List.of("" + model.getId()), "duplicate", List.of("true")));
			getUI().ifPresent(ui -> ui.navigate(TaskMaintenanceView.URL, parameters));
		});
	}

	private void editRecord() {
		session.setParameter(PARAMETER_FILTER, textFieldFilter.getValue());
		grid.getSelectedItems().stream().findFirst().ifPresent(model -> {
			QueryParameters parameters = new QueryParameters(Map.of("id", List.of("" + model.getId())));
			getUI().ifPresent(ui -> ui.navigate(TaskMaintenanceView.URL, parameters));
		});
	}

	private void removeRecord() {
		grid.getSelectedItems().stream().findFirst().ifPresent(model -> {
			new RemoveConfirmDialog(buttonFactory, () -> {
				service.delete(model);
				updateGrid(0);
				textFieldFilter.focus();
			}, resourceManager, session).open();
		});
	}

}