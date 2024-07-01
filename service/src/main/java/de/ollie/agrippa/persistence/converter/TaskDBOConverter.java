package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.TaskDBO;
import de.ollie.agrippa.core.model.Task;

/**
 * A DBO converter for tasks.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class TaskDBOConverter implements ToModelConverter<Task, TaskDBO> {

	private final UserDBOConverter userDBOConverter;
	private final TaskStatusDBOConverter taskStatusDBOConverter;
	private final ProjectDBOConverter projectDBOConverter;
	private final NoteDBOConverter noteDBOConverter;
	private final TodoDBOConverter todoDBOConverter;

	public TaskDBO toDBO(Task model) {
		if (model == null) {
			return null;
		}
		return new TaskDBO()
				.setId(model.getId())
				.setProject(projectDBOConverter.toDBO(model.getProject()))
				.setUser(userDBOConverter.toDBO(model.getUser()))
				.setDescription(model.getDescription())
				.setTaskStatus(taskStatusDBOConverter.toDBO(model.getTaskStatus()))
				.setTitle(model.getTitle())
				.setNotes(noteDBOConverter.toDBO(model.getNotes()))
				.setTodos(todoDBOConverter.toDBO(model.getTodos()));
	}

	public List<TaskDBO> toDBO(List<Task> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public Task toModel(TaskDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Task()
				.setId(dbo.getId())
				.setProject(projectDBOConverter.toModel(dbo.getProject()))
				.setUser(userDBOConverter.toModel(dbo.getUser()))
				.setDescription(dbo.getDescription())
				.setTaskStatus(taskStatusDBOConverter.toModel(dbo.getTaskStatus()))
				.setTitle(dbo.getTitle())
				.setNotes(noteDBOConverter.toModel(dbo.getNotes()))
				.setTodos(todoDBOConverter.toModel(dbo.getTodos()));
	}

	@Override
	public List<Task> toModel(List<TaskDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}