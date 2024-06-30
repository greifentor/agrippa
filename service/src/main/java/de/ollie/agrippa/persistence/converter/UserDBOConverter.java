package de.ollie.agrippa.persistence.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import de.ollie.agrippa.persistence.entity.UserDBO;
import de.ollie.agrippa.core.model.User;

/**
 * A DBO converter for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
@RequiredArgsConstructor
public class UserDBOConverter implements ToModelConverter<User, UserDBO> {

	public UserDBO toDBO(User model) {
		if (model == null) {
			return null;
		}
		return new UserDBO()
				.setId(model.getId())
				.setDefaultUser(model.isDefaultUser())
				.setGlobalId(model.getGlobalId())
				.setName(model.getName())
				.setToken(model.getToken());
	}

	public List<UserDBO> toDBO(List<User> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::toDBO).collect(Collectors.toList());
	}

	@Override
	public User toModel(UserDBO dbo) {
		if (dbo == null) {
			return null;
		}
		return new User()
				.setId(dbo.getId())
				.setDefaultUser(dbo.isDefaultUser())
				.setGlobalId(dbo.getGlobalId())
				.setName(dbo.getName())
				.setToken(dbo.getToken());
	}

	@Override
	public List<User> toModel(List<UserDBO> dbos) {
		if (dbos == null) {
			return null;
		}
		return dbos.stream().map(this::toModel).collect(Collectors.toList());
	}

}