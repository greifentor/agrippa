package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.ProjectDBO;
import de.ollie.agrippa.core.model.Project;

/**
 * A DBO converter for projects.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ProjectDBOConverter implements ToModelConverter<Project, ProjectDBO> {

	private final UserDBOConverter userDBOConverter;

	public ProjectDBO toDBO(Project model) {
		if (model == null) {
			return null;
		}
		return new ProjectDBO()
				.setId(model.getId())
				.setUser(userDBOConverter.toDBO(model.getUser()))
				.setDescription(model.getDescription())
				.setTitle(model.getTitle());
	}

	public List<ProjectDBO> toDBO(List<Project> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public Project toModel(ProjectDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Project()
				.setId(dbo.getId())
				.setUser(userDBOConverter.toModel(dbo.getUser()))
				.setDescription(dbo.getDescription())
				.setTitle(dbo.getTitle());
	}

	@Override
	public List<Project> toModel(List<ProjectDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}