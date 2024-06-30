package de.ollie.agrippa.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for todos.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public abstract class GeneratedTodo<T extends Todo> {

	public static final String ID = "ID";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String STATUS = "STATUS";
	public static final String TITLE = "TITLE";

	private long id;
	private String description;
	private TodoStatus status = TodoStatus.OPEN;
	private String title;

	protected abstract T self();

	public T setId(long id) {
		this.id = id;
		return self();
	}

	public T setDescription(String description) {
		this.description = description;
		return self();
	}

	public T setStatus(TodoStatus status) {
		this.status = status;
		return self();
	}

	public T setTitle(String title) {
		this.title = title;
		return self();
	}

}