package de.ollie.agrippa.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A model for notes.
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Note extends GeneratedNote<Note> {

    public Note() {
        super();
        setCreationDate(getCreationDate().withNano(0));
    }

	@Override
	public Note self() {
		return this;
	}

	public long getRelatedTodoId() {
		return getRelatedTodo() == null ? Long.MIN_VALUE : getRelatedTodo().getId();
	}

}