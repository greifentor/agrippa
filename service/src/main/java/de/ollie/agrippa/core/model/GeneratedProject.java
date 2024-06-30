package de.ollie.agrippa.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for projects.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public abstract class GeneratedProject<T extends Project> {

	public static final String ID = "ID";
	public static final String USER = "USER";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String TITLE = "TITLE";

	private long id;
	private User user;
	private String description;
	private String title;

	protected abstract T self();

	public T setId(long id) {
		this.id = id;
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

	public T setTitle(String title) {
		this.title = title;
		return self();
	}

}