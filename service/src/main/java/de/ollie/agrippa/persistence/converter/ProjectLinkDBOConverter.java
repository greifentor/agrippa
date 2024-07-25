package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.ProjectLinkDBO;
import de.ollie.agrippa.core.model.ProjectLink;

/**
 * A DBO converter for project_links.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class ProjectLinkDBOConverter implements ToModelConverter<ProjectLink, ProjectLinkDBO> {

	public ProjectLinkDBO toDBO(ProjectLink model) {
		if (model == null) {
			return null;
		}
		return new ProjectLinkDBO()
				.setId(model.getId())
				.setDescription(model.getDescription())
				.setTitle(model.getTitle())
				.setUrl(model.getUrl());
	}

	public List<ProjectLinkDBO> toDBO(List<ProjectLink> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public ProjectLink toModel(ProjectLinkDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new ProjectLink()
				.setId(dbo.getId())
				.setDescription(dbo.getDescription())
				.setTitle(dbo.getTitle())
				.setUrl(dbo.getUrl());
	}

	@Override
	public List<ProjectLink> toModel(List<ProjectLinkDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}