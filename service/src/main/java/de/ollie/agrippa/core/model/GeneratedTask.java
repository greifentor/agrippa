package de.ollie.agrippa.core.model;

import java.util.ArrayList;

import java.util.List;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for tasks.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public abstract class GeneratedTask<T extends Task> {

	public static final String ID = "ID";
	public static final String PROJECT = "PROJECT";
	public static final String USER = "USER";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String TASKSTATUS = "TASKSTATUS";
	public static final String TITLE = "TITLE";
	public static final String NOTES = "NOTES";
	public static final String TODOS = "TODOS";

	private long id;
	private Project project;
	private User user;
	private String description;
	private TaskStatus taskStatus = TaskStatus.OPEN;
	private String title;
	private List<Note> notes = new ArrayList<>();
	private List<Todo> todos = new ArrayList<>();

	protected abstract T self();

	public T setId(long id) {
		this.id = id;
		return self();
	}

	public T setProject(Project project) {
		this.project = project;
		return self();
	}

	public T setUser(User user) {
		this.user = user;
		return self();
	}

	public T setDescription(String description) {
		this.description = description;
		return self();
	}

	public T setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
		return self();
	}

	public T setTitle(String title) {
		this.title = title;
		return self();
	}

	public T setNotes(List<Note> notes) {
		this.notes = notes;
		return self();
	}

	public T setTodos(List<Todo> todos) {
		this.todos = todos;
		return self();
	}

}