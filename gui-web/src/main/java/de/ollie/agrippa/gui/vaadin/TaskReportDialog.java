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

	public TaskReportDialog(Task task, ResourceManager resourceManager,
			LocalizationSO localization, DueDateFormatter dueDateFormatter) {
		this.dueDateFormatter = dueDateFormatter;
		this.localization = localization;
		this.resourceManager = resourceManager;
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout.setMargin(false);
		layout.setPadding(false);
		add(new H3(task.getTitle()));
		add(new Hr());
		add(new Span("" + task.getTaskStatus()));
		add(new Hr());
		add(html(task.getDescription().replace("\n", "<BR>")));
		add(new Hr());
		task.getTodos().stream().sorted((n0, n1) -> compareDate(n0.getDueDate(), n1.getDueDate()))
				.forEach(t -> addTodo(t, task));
		add(new Hr());
		add(html("<P><UL>"));
		task.getNotes().stream().filter(n -> n.getRelatedTodoId() < 0)
				.sorted((n0, n1) -> compareDate(n0.getCreationDate(), n1.getCreationDate())).forEach(this::addNote);
		add(html("</UL>"));
		add(layout);
		setWidth("50%");
		open();
	}

	private void addTodo(Todo todo, Task task) {
		add(html("<B>" + todo.getTitle() + "</B><BR>"));
		add(html("<I>(" + getStatus(todo) + " - " + getPriority(todo) + " - "
				+ getDueDate(todo) + "</I>)<BR>"));
		add(html(todo.getDescription().replace("\n", "<BR>")));
		add(html("<UL>"));
		task.getNotes().stream().filter(n -> n.getRelatedTodoId() == todo.getId())
				.sorted((n0, n1) -> compareDate(n0.getCreationDate(), n1.getCreationDate())).forEach(this::addNote);
		add(html("</UL>"));
		add(html("<P>"));
	}

	private int compareDate(LocalDateTime d0, LocalDateTime d1) {
		if ((d0 == null) && (d1 == null)) {
			return 0;
		} else if (d0 == null) {
			return Integer.MIN_VALUE;
		} else if (d1 == null) {
			return Integer.MAX_VALUE;
		}
		return d0.compareTo(d1);
	}

	private void addNote(Note note) {
		add(htmlList("<LI>" + note.getTitle() + " <I>("
				+ (note.getCreationDate() != null
						? DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm").format(note.getCreationDate())
						: "-")
				+ ")</I><BR>"));
		if (note.getDescription() != null) {
			add(htmlList(note.getDescription().replace("\n", "<BR>")));
		}
		if (note.getUrl() != null) {
			add(htmlList("<A HREF=\"" + note.getUrl() + "\">" + note.getUrl() + "</A>"));
		}
		add(htmlList("</LI>"));
	}

	private Label html(String s) {
		Label label = new Label();
		label.getElement().setProperty("innerHTML", s);
		return label;
	}

	private Label htmlList(String s) {
		Label label = new Label();
		label.getElement().setProperty("innerHTML", s);
		label.getStyle().set("margin-left", "20px");
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
