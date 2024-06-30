package de.ollie.agrippa.core.model;

import de.ollie.agrippa.core.model.AuthorizationUser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A model for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Generated
@ToString(callSuper = true)
public class User extends GeneratedUser<User> implements AuthorizationUser {

	@Override
	public User self() {
		return this;
	}

}