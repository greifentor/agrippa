package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.TeamDBO;
import de.ollie.agrippa.core.model.Team;

/**
 * A DBO converter for teams.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class TeamDBOConverter implements ToModelConverter<Team, TeamDBO> {

	public TeamDBO toDBO(Team model) {
		if (model == null) {
			return null;
		}
		return new TeamDBO()
				.setId(model.getId())
				.setDescription(model.getDescription())
				.setTitle(model.getTitle());
	}

	public List<TeamDBO> toDBO(List<Team> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public Team toModel(TeamDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new Team()
				.setId(dbo.getId())
				.setDescription(dbo.getDescription())
				.setTitle(dbo.getTitle());
	}

	@Override
	public List<Team> toModel(List<TeamDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}