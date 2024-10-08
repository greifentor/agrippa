package de.ollie.agrippa.core.model;

import java.util.ArrayList;

import java.util.List;

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
	public static final String PROJECTLINKS = "PROJECTLINKS";

	private long id;
	private User user;
	private String description;
	private String title;
	private List<ProjectLink> projectLinks = new ArrayList<>();

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

	public T setProjectLinks(List<ProjectLink> projectLinks) {
		this.projectLinks = projectLinks;
		return self();
	}

}