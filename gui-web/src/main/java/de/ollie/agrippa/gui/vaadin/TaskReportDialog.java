package de.ollie.agrippa.gui.vaadin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.ProjectLink;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.localization.ResourceManager;

public class TaskReportDialog extends Dialog {

	private final DueDateFormatter dueDateFormatter;
	private final LocalizationSO localization;
	private final ResourceManager resourceManager;
	private final TodoDueStatusCssClassService todoDueStatusCssClassService;

	public TaskReportDialog(Task task, ResourceManager resourceManager,
			LocalizationSO localization, DueDateFormatter dueDateFormatter, TodoDueStatusCssClassService todoDueStatusCssClassService) {
		this.dueDateFormatter = dueDateFormatter;
		this.localization = localization;
		this.resourceManager = resourceManager;
		this.todoDueStatusCssClassService = todoDueStatusCssClassService;
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout.setMargin(false);
		layout.setPadding(false);
		layout.add(new H3(task.getTitle()));
		layout.add(new Hr());
		layout.add(new Span("" + task.getTaskStatus()));
		layout.add(new Hr());
		layout.add(html(task.getDescription().replace("\n", "<BR>")));
		if (!task.getTodos().isEmpty()) {
			layout.add(new Hr());
			task.getTodos().stream().sorted((n0, n1) -> compareDate(n0.getDueDate(), n1.getDueDate()))
					.forEach(t -> addTodo(t, task, layout));
		}
		if (hasNotesWithNoTodoContext(task)) {
			layout.add(new Hr());
			layout.add(html("<B>Notes</B>"));
			task.getNotes().stream().filter(n -> n.getRelatedTodoId() < 0)
					.sorted((n0, n1) -> compareDate(n0.getCreationDate(), n1.getCreationDate())).forEach(n -> addNote(n, layout));
		}
		layout.add(html("<P>&nbsp;"));
		add(layout);
		setWidth("50%");
		open();
	}

	private void addTodo(Todo todo, Task task, VerticalLayout parent) {
		VerticalLayout panel = new VerticalLayout();
	panel.setClassName(todoDueStatusCssClassService.getCssClassName(todo));
		panel.setSpacing(false);
		panel.getStyle().set("border", "1px solid #ccc");
		panel.getStyle().set("padding", "5px");
		panel.getStyle().set("border-radius", "6px");
		panel.add(html("<B>" + todo.getTitle() + "</B><BR>"));
		panel.add(html("<I>(" + getStatus(todo) + " - " + getPriority(todo) + " - "
				+ getDueDate(todo) + "</I>)<BR>"));
		panel.add(html(todo.getDescription() != null ? todo.getDescription().replace("\n", "<BR>") : "-"));
		if(hasNotesForTodo(task, todo)) {
			task.getNotes().stream().filter(n -> n.getRelatedTodoId() == todo.getId())
					.sorted((n0, n1) -> compareDate(n0.getCreationDate(), n1.getCreationDate())).forEach(t -> addNote(t, panel));
		}
		panel.add(html("<P>"));
		parent.add(panel);
	}
	
	private boolean hasNotesForTodo(Task task, Todo todo) {
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
						? DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm").format(note.getCreationDate())
						: "-")
				+ ")</I>"));
		if (note.getDescription() != null) {
			panel.add(html(note.getDescription().replace("\n", "<BR>")));
		}
		if (note.getUrl() != null) {
			panel.add(html("<A HREF=\"" + note.getUrl() + "\">" + note.getUrl() + "</A>"));
		}
		parent.add(panel);
	}

	private Label html(String s) {
		Label label = new Label();
		label.getElement().setProperty("innerHTML", s);
		return label;
	}

	private String getStatus(Todo todo) {
		return resourceManager.getLocalizedString("TaskReportDialog.status.label", localization)
				+ " " + todo.getStatus();
	}

	private String getPriority(Todo todo) {
		return resourceManager.getLocalizedString("TaskReportDialog.priority.label", localization)
				+ " " + todo.getPriority();
	}

	private String getDueDate(Todo todo) {
		return resourceManager.getLocalizedString("TaskReportDialog.duedate.label", localization)
				+ " " + (todo.getDueDate() != null ? dueDateFormatter.format(todo.getDueDate()) : "-");
	}
	
	private boolean hasNotesWithNoTodoContext(Task task) {
		return task.getNotes().stream().filter(n -> n.getRelatedTodoId() < 0).count() > 0;
	}

	private HorizontalLayout createLinkLayout(ProjectLink projectLink) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidthFull();
		layout.setMargin(false);
		layout.setPadding(false);
		Span span = new Span(projectLink.getDescription());
		span.setWidth("75%");
		setMargin(span);
		layout.add(createLabel(getLinksAsHTML(projectLink)));
		layout.add(span);
		return layout;
	}

	private void setMargin(HtmlComponent c) {
		c.getStyle().set("margin", "10px 0px 10px");
	}

	private Component createLabel(String s) {
		Label label = new Label();
		label.getElement().setProperty("innerHTML", s);
		label.setWidth("33%");
		setMargin(label);
		return label;
	}

	private String getLinksAsHTML(ProjectLink pl) {
		return "<A HREF=\"" + pl.getUrl() + "\">" + pl.getTitle() + "</A>";
	}

}
