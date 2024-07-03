package de.ollie.agrippa.core.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for notes.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public abstract class GeneratedNote<T extends Note> {

	public static final String ID = "ID";
	public static final String CREATIONDATE = "CREATIONDATE";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String TITLE = "TITLE";
	public static final String TYPE = "TYPE";

	private long id;
	private LocalDateTime creationDate = LocalDateTime.now();
	private String description;
	private String title;
	private NoteType type = NoteType.NOTE;

	protected abstract T self();

	public T setId(long id) {
		this.id = id;
		return self();
	}

	public T setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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

	public T setType(NoteType type) {
		this.type = type;
		return self();
	}

}