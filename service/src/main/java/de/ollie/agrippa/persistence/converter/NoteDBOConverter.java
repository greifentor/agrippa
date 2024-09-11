package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.NoteDBO;
import de.ollie.agrippa.core.model.Note;

/**
 * A DBO converter for notes.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class NoteDBOConverter implements ToModelConverter<Note, NoteDBO> {

	private final TodoDBOConverter todoDBOConverter;
	private final NoteTypeDBOConverter noteTypeDBOConverter;

	public NoteDBO toDBO(Note model) {
		if (model == null) {
			return null;
		}
		return new NoteDBO()
				.setId(model.getId())
				.setRelatedTodo(todoDBOConverter.toDBO(model.getRelatedTodo()))
				.setCreationDate(model.getCreationDate())
				.setDescription(model.getDescription())
				.setTitle(model.getTitle())
				.setType(noteTypeDBOConverter.toDBO(model.getType()))
				.setUrl(model.getUrl());
	}

	public List<NoteDBO> toDBO(List<Note> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public Note toModel(NoteDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Note()
				.setId(dbo.getId())
				.setRelatedTodo(todoDBOConverter.toModel(dbo.getRelatedTodo()))
				.setCreationDate(dbo.getCreationDate())
				.setDescription(dbo.getDescription())
				.setTitle(dbo.getTitle())
				.setType(noteTypeDBOConverter.toModel(dbo.getType()))
				.setUrl(dbo.getUrl());
	}

	@Override
	public List<Note> toModel(List<NoteDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}