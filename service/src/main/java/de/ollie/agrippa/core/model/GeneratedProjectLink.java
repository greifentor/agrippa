package de.ollie.agrippa.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for project_links.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public abstract class GeneratedProjectLink<T extends ProjectLink> {

	public static final String ID = "ID";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String TITLE = "TITLE";
	public static final String URL = "URL";

	private long id;
	private String description;
	private String title;
	private String url;

	protected abstract T self();

	public T setId(long id) {
		this.id = id;
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

	public T setUrl(String url) {
		this.url = url;
		return self();
	}

}