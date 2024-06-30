package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.TodoDBO;
import de.ollie.agrippa.core.model.Todo;

/**
 * A DBO converter for todos.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class TodoDBOConverter implements ToModelConverter<Todo, TodoDBO> {

	private final TodoStatusDBOConverter todoStatusDBOConverter;

	public TodoDBO toDBO(Todo model) {
		if (model == null) {
			return null;
		}
		return new TodoDBO()
				.setId(model.getId())
				.setDescription(model.getDescription())
				.setStatus(todoStatusDBOConverter.toDBO(model.getStatus()))
				.setTitle(model.getTitle());
	}

	public List<TodoDBO> toDBO(List<Todo> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public Todo toModel(TodoDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Todo()
				.setId(dbo.getId())
				.setDescription(dbo.getDescription())
				.setStatus(todoStatusDBOConverter.toModel(dbo.getStatus()))
				.setTitle(dbo.getTitle());
	}

	@Override
	public List<Todo> toModel(List<TodoDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}