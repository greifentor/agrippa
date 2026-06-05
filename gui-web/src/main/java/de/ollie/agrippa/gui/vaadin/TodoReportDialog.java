package de.ollie.agrippa.gui.vaadin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import de.ollie.agrippa.gui.vaadin.masterdata.dialog.NoteDetailsDialog;

public class TodoReportDialog extends Dialog implements NoteDetailsDialog.Observer {

	public interface Observer {

		void changed(Todo todo);

	}

	private final ComponentFactory componentFactory;
	private final DueDateFormatter dueDateFormatter;
	private final LocalizationSO localization;
	private final MasterDataGUIConfiguration masterDataGUIConfiguration;
	private final ResourceManager resourceManager;
	private final Observer observer;
	private final ServiceProvider serviceProvider;
	private final SessionData session;
	private final Task task;
	private final Todo todo;
	private final TodoDueStatusCssClassService todoDueStatusCssClassService;

	private VerticalLayout todoPanel;

	public TodoReportDialog(Todo todo, Task task, ResourceManager resourceManager,
			LocalizationSO localization, ComponentFactory componentFactory,
			MasterDataGUIConfiguration masterDataGUIConfiguration, SessionData session, ServiceProvider serviceProvider,
			DueDateFormatter dueDateFormatter,
			TodoDueStatusCssClassService todoDueStatusCssClassService, Observer observer) {
		this.componentFactory = componentFactory;
		this.dueDateFormatter = dueDateFormatter;
		this.localization = localization;
		this.masterDataGUIConfiguration = masterDataGUIConfiguration;
		this.observer = observer;
		this.resourceManager = resourceManager;
		this.serviceProvider = serviceProvider;
		this.session = session;
		this.task = task;
		this.todo = todo;
		this.todoDueStatusCssClassService = todoDueStatusCssClassService;
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout.setMargin(false);
		layout.setPadding(false);
		layout.add(new H3(todo.getTitle()));
		layout.add(new Hr());
		layout.add(new Span("" + todo.getStatus()));
		layout.add(new Hr());
		layout.add(html(getDescription(todo).replace("\n", "<BR>")));
		addTodo(layout);
		layout.add(addButtons());
		add(layout);
		setWidth("50%");
		open();
	}

	private String getDescription(Todo todo) {
		return (todo == null) || (todo.getDescription() == null) ? "" : todo.getDescription();
	}

	private void addTodo(VerticalLayout parent) {
		if (todoPanel != null) {
			todoPanel.removeAll();
		} else {
			todoPanel = new VerticalLayout();
		}
		if ((todo.getStatus() != TodoStatus.REJECTED) && (todo.getStatus() != TodoStatus.SOLVED)) {
			todoPanel.setClassName(todoDueStatusCssClassService.getCssClassName(todo));
		}
		todoPanel.setSpacing(false);
		todoPanel.getStyle().set("border", "1px solid #ccc");
		todoPanel.getStyle().set("padding", "5px");
		todoPanel.getStyle().set("border-radius", "6px");
		todoPanel.add(html("<B>" + todo.getTitle() + "</B><BR>"));
		todoPanel.add(html("<I>(" + getStatus() + " - " + getPriority() + " - " + getDueDate() + "</I>)<BR>"));
		todoPanel.add(html(todo.getDescription() != null ? todo.getDescription().replace("\n", "<BR>") : "-"));
		if (hasNotesForTodo()) {
			task.getNotes().stream().filter(n -> n.getRelatedTodoId() == todo.getId())
					.sorted((n0, n1) -> compareDate(n0.getCreationDate(), n1.getCreationDate()))
					.forEach(t -> addNote(t, todoPanel));
		}
		todoPanel.add(html("<P>"));
		if (parent != null) {
			parent.add(todoPanel);
		}
	}
	
	private boolean hasNotesForTodo() {
		return task.getNotes().stream().filter(n -> n.getRelatedTodoId() == todo.getId()).count() > 0;
	}
	
	private int compareDate(LocalDateTime d0, LocalDateTime d1) {
		if ((d0 == null) && (d1 == null)) {
			return 0;
		} else if (d0 == null) {
			return Integer.MAX_VALUE;
		} else if (d1 == null) {
			return Integer.MIN_VALUE;
		}
		return d0.compareTo(d1);
	}

	private void addNote(Note note, VerticalLayout parent) {
		VerticalLayout panel = new VerticalLayout();
		panel.setSpacing(false);
		panel.getStyle().set("border", "1px solid #ccc");
		panel.getStyle().set("padding", "5px");
		panel.getStyle().set("border-radius", "6px");
		panel.add(html(note.getTitle() + " <I>("
				+ (note.getCreationDate() != null
						? DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(note.getCreationDate())
						: "-")
				+ ")</I>"));
		if (note.getDescription() != null) {
			panel.add(html(note.getDescription().replace("\n", "<BR>")));
		}
		if (note.getUrl() != null) {
			panel.add(html("<A HREF=\"" + note.getUrl() + "\">" + note.getUrl() + "</A>"));
		}
		panel.add(componentFactory.createEditButton(e -> editNote(note), session));
		parent.add(panel);
	}

	private void editNote(Note note) {
		new NoteDetailsDialog(componentFactory, masterDataGUIConfiguration, (toEdit, newItem) -> {
			note.setTitle(toEdit.getTitle());
			note.setCreationDate(toEdit.getCreationDate());
			note.setUrl(toEdit.getUrl());
			note.setType(toEdit.getType());
			note.setRelatedTodo(toEdit.getRelatedTodo());
			note.setDescription(toEdit.getDescription());
			serviceProvider.getTaskService().update(task);
			addTodo(null);
		}, session, note, serviceProvider, false, task).open();
	}

	private Label html(String s) {
		Label label = new Label();
		label.getElement().setProperty("innerHTML", s);
		return label;
	}

	private String getStatus() {
		return resourceManager.getLocalizedString("TaskReportDialog.status.label", localization)
				+ " " + todo.getStatus();
	}

	private String getPriority() {
		return resourceManager.getLocalizedString("TaskReportDialog.priority.label", localization)
				+ " " + todo.getPriority();
	}

	private String getDueDate() {
		return resourceManager.getLocalizedString("TaskReportDialog.duedate.label", localization)
				+ " " + (todo.getDueDate() != null ? dueDateFormatter.format(todo.getDueDate()) : "-");
	}

	private VerticalLayout addButtons() {
		DateTimePicker dateTimePickerDueDate = componentFactory.createDateTimePicker(
				"TodoReportDialog.field.duedate.label", localization, todo.getDueDate(), e -> {});
		Button buttonSave = componentFactory
				.createButton(resourceManager.getLocalizedString("TodoReportDialog.buttons.save.label"));
		buttonSave.addClickListener(e -> saveDueDate(dateTimePickerDueDate.getValue()));
		buttonSave.getStyle().set("margin-left", "2em");
		Button buttonAddNote = componentFactory
				.createButton(resourceManager.getLocalizedString("TodoReportDialog.buttons.add-note.label"));
		buttonAddNote.addClickListener(e -> openNoteDialog());
		Button buttonSolveTodo = componentFactory
				.createButton(resourceManager.getLocalizedString("TodoReportDialog.buttons.solve-todo.label"));
		buttonSolveTodo.addClickListener(e -> solveTodo());
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidthFull();
		buttonLayout.setMargin(false);
		buttonLayout.setPadding(false);
		buttonLayout.setJustifyContentMode(JustifyContentMode.END);
		buttonLayout.add(buttonSolveTodo, buttonAddNote, buttonSave);
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout.setMargin(false);
		layout.setPadding(false);
		layout.add(dateTimePickerDueDate, buttonLayout);
		return layout;
	}

	private void openNoteDialog() {
		new NoteDetailsDialog(componentFactory, masterDataGUIConfiguration, this, session,
				new Note().setRelatedTodo(todo), serviceProvider,
				true, task).open();
	}

	private void saveDueDate(LocalDateTime dueDate) {
		todo.setDueDate(dueDate);
		serviceProvider.getTaskService().update(task);
		if (observer != null) {
			observer.changed(todo);
		}
		close();
	}

	private void solveTodo() {
		todo.setStatus(TodoStatus.SOLVED);
		serviceProvider.getTaskService().update(task);
		if (observer != null) {
			observer.changed(todo);
		}
		close();
	}

	@Override
	public void changed(Note model, boolean newItem) {
		task.getNotes().add(model);
		serviceProvider.getTaskService().update(task);
		addTodo(null);
	}

}
